package models.vendas;


import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import application.DadosGlobais;
import connect.ConnectionHib;
import interfaces.InterfaceDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.compras.ItemCodBar;
import models.configuracoes.Auditoria;
import models.configuracoes.SequencialDAO;
import tools.enums.EnumCompartilhamento;
import tools.enums.EnumLogRetornoStatus;
import tools.enums.EnumTipoOpeAuditoria;
import tools.utils.LogRetorno;
import tools.utils.Util;

public class ClienteDAO {

	private LocalDateTime ts_now;
	private BigInteger utctag;
	private EntityManager em = null;

	public LogRetorno insert(Object objeto, List<ClienteEndEntrega> listClienteEntrega) {
		LogRetorno logRet = new LogRetorno();
	
			Cliente cliente = (Cliente) objeto;

			SequencialDAO seqDAO = new SequencialDAO();
			try {

				logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);

				em = ConnectionHib.emf.createEntityManager();
				em.getTransaction().begin();

				Date dataServer = (Date) em.createNativeQuery(
						ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL")
						.getSingleResult();

				em.createQuery(
						"UPDATE Sequencial s SET s.vdCliente = s.vdCliente+1 WHERE s.codemp= :codemp AND s.checkDelete = :checkDelete")
						.setParameter("codemp", DadosGlobais.empresaLogada.getCodigo())
						.setParameter("checkDelete", DadosGlobais.empresaLogada.getCheckDelete()).executeUpdate();

				utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));

				ts_now = Util.dateToLocalDateTime(dataServer);

				Integer codigo = seqDAO.getLastInsertCodigo(DadosGlobais.empresaLogada.getCodigo(), "vdCliente") + 1;

				cliente.setCodemp(DadosGlobais.empresaLogada.getCodigo());
				cliente.setCodigo(codigo);
				cliente.setUtctag(utctag);
				cliente.setCheckDelete(Util.checkDeleteCreate());
				cliente.setFlagAtivo(1);
				cliente.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
				cliente.setLastMovto(ts_now);

				Set<ClienteEndEntrega> clienteEndEntrega = new HashSet<ClienteEndEntrega>();

				if (!listClienteEntrega.isEmpty()) {
					for (int i = 0; i < listClienteEntrega.size(); i++) {
						listClienteEntrega.get(i).setCodemp(cliente.getCodemp());
						listClienteEntrega.get(i).setCliente(cliente);
						listClienteEntrega.get(i).setCheckDelete(Util.checkDeleteCreate());
						listClienteEntrega.get(i).setFlagAtivo(1);
						listClienteEntrega.get(i).setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
						listClienteEntrega.get(i).setLastMovto(ts_now);
						listClienteEntrega.get(i).setUtctag(utctag);
						clienteEndEntrega.add(listClienteEntrega.get(i));
					}
					cliente.setClienteEndEntregas(clienteEndEntrega);
				}

				em.persist(cliente);

				em.getTransaction().commit();

				logRet.setStatus(EnumLogRetornoStatus.SUCESSO);

				logRet.setMsg(EnumLogRetornoStatus.SUCESSO.name());

				logRet.setLastRecord(codigo);

				logRet.setObjeto(cliente);

			} catch (Exception e) {
				e.printStackTrace();
				em.getTransaction().rollback();
				throw e;
			} finally {
				em.close();
			}
			
		return logRet;
	}

	@SuppressWarnings("unchecked")
	/**
	 * Metodo para listar tudos os GRUPOS, dependendo do valor do flag_Ativo e
	 * compartilhamento
	 * 
	 * @param flagAtivo
	 * @return
	 */
	public List<Cliente> getList(List<Integer> flagAtivo) {
		List<Cliente> list = null;
		try {
			em = ConnectionHib.emf.createEntityManager();

			list = em.createNamedQuery("ClienteAll").setParameter("flag", flagAtivo)
					.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.CLIENTES))
					.getResultList();

		} catch (Exception e) {
			throw e;

		} finally {
			em.close();
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	public LogRetorno getLast() {

		LogRetorno logRet = new LogRetorno();

		try {
			em = ConnectionHib.emf.createEntityManager();

			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);

			List<Cliente> list = em.createNamedQuery("ClienteLast").setMaxResults(1)
					.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.CLIENTES))
					.getResultList();

			if (!list.isEmpty()) {
				if (list.size() > 1) {
					logRet.setStatus(EnumLogRetornoStatus.REGDUPLICADO);
					logRet.setObjeto(list.get(0));
					logRet.setMsg(DadosGlobais.resourceBundle.getString("errorDuplicateRecord"));
				} else if (list.size() == 1) {
					logRet.setStatus(EnumLogRetornoStatus.SUCESSO);
					logRet.setObjeto(list.get(0));
				}

			} else {
				logRet.setStatus(EnumLogRetornoStatus.ERRO);
				logRet.setMsg(DadosGlobais.resourceBundle.getString("errorSelect"));
			}
		} catch (Exception e) {
			throw e;

		} finally {
			em.close();
		}
		return logRet;
	}

	/**
	 * Metodo para buscar cliente pelo CODIGO
	 * 
	 * @param codigo
	 * @return cliente buscado ou null
	 */
	@SuppressWarnings("unchecked")
	public LogRetorno getById(Integer codigo) {

		LogRetorno logRet = new LogRetorno();

		try {
			em = ConnectionHib.emf.createEntityManager();

			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);

			List<Cliente> list = em.createNamedQuery("ClienteById").setParameter("codigo", codigo)
					.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.CLIENTES))
					.getResultList();
			if (!list.isEmpty()) {
				if (list.size() > 1) {
					logRet.setStatus(EnumLogRetornoStatus.REGDUPLICADO);
					logRet.setObjeto(list.get(0));
					logRet.setMsg(DadosGlobais.resourceBundle.getString("errorDuplicateRecord"));
				} else if (list.size() == 1) {
					logRet.setStatus(EnumLogRetornoStatus.SUCESSO);
					logRet.setObjeto(list.get(0));
				}

//				List<ClienteEndEntrega> listEnderecosEntrega = em.createNamedQuery("EndrecosByCliente")
//						.setParameter("cliente", list.get(0)).getResultList();
//
//				Set<ClienteEndEntrega> endEntregas = new HashSet<ClienteEndEntrega>();
//				endEntregas.addAll(listEnderecosEntrega);
//
//				list.get(0).setClienteEndEntregas(endEntregas);

			} else {
				logRet.setStatus(EnumLogRetornoStatus.ERRO);
				logRet.setMsg(DadosGlobais.resourceBundle.getString("errorSelect"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;

		} finally {
			em.close();
		}
		return logRet;
	}
	
	

	public LogRetorno update(Object objeto, List<ClienteEndEntrega> list, List<ClienteEndEntrega> alteredList) {

		Cliente cliente = (Cliente) objeto;

		LogRetorno logRet = new LogRetorno();

		try {
			em = ConnectionHib.emf.createEntityManager();
			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);
			em.getTransaction().begin();
			ClientePK clientePK = new ClientePK();
			clientePK.setCodigo(cliente.getCodigo());
			clientePK.setCheckDelete(cliente.getCheckDelete());

			Cliente newCliente = em.find(Cliente.class, clientePK);

			if (newCliente != null && newCliente.getFlagAtivo() == 1) {

				Date dataServer = (Date) em.createNativeQuery(
						ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL")
						.getSingleResult();
				utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));
				ts_now = Util.dateToLocalDateTime(dataServer);

				cliente.setLastMovto(ts_now);
				cliente.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
				cliente.setUtctag(utctag);

				// If only the Cliente is modified
				if (list.containsAll(alteredList) && list.size() == alteredList.size()) {

					em.merge(cliente);
					em.getTransaction().commit();

				} else {

					ObservableList<ClienteEndEntrega> lisNew = FXCollections.observableArrayList();

					// Check if all list ClienteEndEntrega is new
					if (list.isEmpty() && !alteredList.isEmpty())
						lisNew.addAll(alteredList);

					// Separate new ClienteEndEntrega
					boolean nCe = true;
					for (ClienteEndEntrega newCe : alteredList) {
						for (ClienteEndEntrega oldCe : list) {
							if (!newCe.getCheckDelete().equals(oldCe.getCheckDelete())) {
								nCe = false;
							} else {
								nCe = true;
								break;
							}
						}
						if (!nCe)
							lisNew.add(newCe);
					}

					Set<ClienteEndEntrega> clienteEndEntrega = new HashSet<ClienteEndEntrega>();

					if (!lisNew.isEmpty()) {

						for (int i = 0; i < lisNew.size(); i++) {
							if (lisNew.get(i).getFlagAtivo().equals(1)) {
								lisNew.get(i).setCodemp(cliente.getCodemp());
								lisNew.get(i).setCliente(cliente);
								lisNew.get(i).setCheckDelete(Util.checkDeleteCreate());
								lisNew.get(i).setFlagAtivo(1);
								lisNew.get(i).setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
								lisNew.get(i).setLastMovto(ts_now);
								lisNew.get(i).setUtctag(utctag);
								clienteEndEntrega.add(lisNew.get(i));
							}
						}
						if (!clienteEndEntrega.isEmpty())
							cliente.setClienteEndEntregas(clienteEndEntrega);
					}

					for (ClienteEndEntrega ce : cliente.getClienteEndEntregas())
						em.merge(ce);

					em.merge(cliente);

					em.getTransaction().commit();
				}

				logRet.setStatus(EnumLogRetornoStatus.SUCESSO);
				logRet.setMsg("Sucesso");
			} else {
				logRet.setStatus(EnumLogRetornoStatus.ERRO);
				logRet.setMsg(DadosGlobais.resourceBundle.getString("errorUpdate"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}

		return logRet;
	}

	public void delete(Object objeto) {

		Cliente cliente = (Cliente) objeto;

		try {

			em = ConnectionHib.emf.createEntityManager();

			em.getTransaction().begin();

			Date dataServer = (Date) em.createNativeQuery(
					ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL")
					.getSingleResult();
			utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));
			ts_now = Util.dateToLocalDateTime(dataServer);

			em.createQuery("UPDATE Cliente c " + "SET c.flagAtivo = " + cliente.getFlagAtivo() + ","
					+ "c.lastMovto = :dtAlteracao," + "c.lastCoduser = " + DadosGlobais.usuarioLogado.getCodigo() + ","
					+ "c.utctag = " + utctag + " " + "WHERE c.codigo= :codigo " + "AND c.recordNo = :recordNo "
					+ "AND c.codemp = :codemp ").setParameter("dtAlteracao", ts_now)
					.setParameter("codigo", cliente.getCodigo()).setParameter("recordNo", cliente.getRecordNo())
					.setParameter("codemp", cliente.getCodemp()).executeUpdate();

			Integer tipoOperacao = null;
			String valorAnterior = null;
			String valorNovo = null;
			if (cliente.getFlagAtivo() == 1) {
				tipoOperacao = EnumTipoOpeAuditoria.REATIVACAO.idTipoOpe;// Reativação
				valorAnterior = DadosGlobais.resourceBundle
						.getString("controllers.configuracoes.AuditoriaController.logExclusao").toUpperCase();// Inativo
				valorNovo = DadosGlobais.resourceBundle
						.getString("controllers.configuracoes.AuditoriaController.logAtivacao").toUpperCase();// Ativo
			} else {
				tipoOperacao = EnumTipoOpeAuditoria.EXCLUSAO.idTipoOpe;// Exclusão
				valorAnterior = DadosGlobais.resourceBundle
						.getString("controllers.configuracoes.AuditoriaController.logAtivacao").toUpperCase();// Ativo
				valorNovo = DadosGlobais.resourceBundle
						.getString("controllers.configuracoes.AuditoriaController.logExclusao").toUpperCase();// Inativo
			}

			// Cria auditoria
			Auditoria auditoria = new Auditoria();
			auditoria.setCheckDelete(Util.checkDeleteCreate());
			auditoria.setLastMovto(ts_now);
			auditoria.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
			auditoria.setUtctag(utctag);
			auditoria.setFlagAtivo(1);
			auditoria.setCodemp(DadosGlobais.empresaLogada.getCodigo());
			auditoria.setCodUsuario(DadosGlobais.usuarioLogado.getCodigo());
			auditoria.setNomeUsuario(DadosGlobais.usuarioLogado.getNome());
			auditoria.setSistema("EPTUS");
			auditoria.setTipoOperacao(tipoOperacao);
			auditoria.setTipoRegistro(DadosGlobais.resourceBundle.getString("miCadCliente").toUpperCase());
			auditoria.setDtMovto(Util.geraDataMovto(ts_now));
			auditoria.setValorAnterior(valorAnterior);
			auditoria.setValorNovo(valorNovo);
			auditoria.setHistorico(cliente.getCodigo() + "-" + cliente.getRazao());
			auditoria.setDocCodigo(String.valueOf(cliente.getCodigo()));

			em.persist(auditoria);
			em.getTransaction().commit();

		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}

	}

	@SuppressWarnings("unchecked")
	public List<Cliente> getClienteByCNPJ(String cnpj) {

		//Cliente cliente = null;
		List<Cliente> listClientes = null;
		
		try {

			em = ConnectionHib.emf.createEntityManager();
						
			listClientes = em.createNamedQuery("ClienteByCNPJ").setParameter("cpfCnpj", cnpj)
					.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.CLIENTES))
					.getResultList();

//			if (!listClientes.isEmpty()) {
//				cliente = listClientes.get(0);
//			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			em.close();
		}
		return listClientes;
	}

	@SuppressWarnings("unchecked")
	public List<Cliente> filterByColumn(String column, String filter, int action, List<Integer> ativo) {

		List<Cliente> listFilter = null;
		try {
			em = ConnectionHib.emf.createEntityManager();
			if (action == 1) {
				if (filter.equals("")) {
					listFilter = em
							.createQuery("SELECT c FROM Cliente c WHERE c." + column
									+ " LIKE CONCAT('%', :filter, '%') AND c.codemp IN (:codemp) AND c.flagAtivo IN (:flag)")
							.setParameter("filter", (filter.equals("")) ? "%" : filter)
							.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.CLIENTES))
							.setParameter("flag", ativo).getResultList();
				} else {

					listFilter = em
							.createQuery("SELECT c FROM Cliente c WHERE c." + column
									+ "= :filter AND c.codemp IN (:codemp) AND c.flagAtivo IN (:flag)")
							.setParameter("filter", Integer.parseInt(filter))
							.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.CLIENTES))
							.setParameter("flag", ativo).getResultList();

				}

			} else {
				listFilter = em
						.createQuery("SELECT c FROM Cliente c WHERE c." + column
								+ " LIKE CONCAT('%', :filter, '%') AND c.codemp IN (:codemp) AND c.flagAtivo IN (:flag)")
						.setParameter("filter", (filter.equals("")) ? "%" : filter)
						.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.CLIENTES))
						.setParameter("flag", ativo).getResultList();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			em.close();
		}

		return listFilter;
	}

}
