package models.compras; 

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import application.DadosGlobais;
import connect.ConnectionHib;
import interfaces.InterfaceDAO;
import models.configuracoes.Auditoria;
import models.configuracoes.SequencialDAO;
import tools.enums.EnumCompartilhamento;
import tools.enums.EnumLogRetornoStatus;
import tools.enums.EnumTipoOpeAuditoria;
import tools.utils.LogRetorno;
import tools.utils.Util;

public class FornecedorDAO implements InterfaceDAO {

	private LocalDateTime ts_now;
	private BigInteger utctag;
	private EntityManager em = null;

	public LogRetorno insert(Object objeto) {

		Fornecedor fornecedor = (Fornecedor) objeto;
		LogRetorno logRet = new LogRetorno();
		SequencialDAO seqDAO = new SequencialDAO();
		try {
			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);

			em = ConnectionHib.emf.createEntityManager();
			em.getTransaction().begin();

			Date dataServer = (Date) em.createNativeQuery(
					ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL")
					.getSingleResult();

			em.createQuery(
					"UPDATE Sequencial s SET s.cpFornecedor = s.cpFornecedor+1 WHERE s.codemp= :codemp AND s.checkDelete = :checkDelete")
					.setParameter("codemp", DadosGlobais.empresaLogada.getCodigo())
					.setParameter("checkDelete", DadosGlobais.empresaLogada.getCheckDelete()).executeUpdate();

			utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));

			ts_now = Util.dateToLocalDateTime(dataServer);

			Integer codigo = seqDAO.getLastInsertCodigo(DadosGlobais.empresaLogada.getCodigo(), "cpFornecedor") + 1;

			fornecedor.setCodemp(DadosGlobais.empresaLogada.getCodigo());
			fornecedor.setCodigo(codigo);
			fornecedor.setUtctag(utctag);
			fornecedor.setCheckDelete(Util.checkDeleteCreate());
			fornecedor.setFlagAtivo(1);
			fornecedor.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
			fornecedor.setLastMovto(ts_now);

			em.persist(fornecedor);

			em.getTransaction().commit();

			logRet.setStatus(EnumLogRetornoStatus.SUCESSO);

			logRet.setMsg(EnumLogRetornoStatus.SUCESSO.name());

			logRet.setLastRecord(codigo);

			logRet.setObjeto(fornecedor);

		} catch (Exception e) {
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
	public List<Fornecedor> getList(List<Integer> flagAtivo) {
		List<Fornecedor> list = null;
		try {
			em = ConnectionHib.emf.createEntityManager();

			list = em.createNamedQuery("FornecedorAll").setParameter("flag", flagAtivo)
					.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.FORNECEDORES))
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

			List<Fornecedor> list = em.createNamedQuery("FornecedorLast").setMaxResults(1)
					.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.FORNECEDORES))
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
	 * Metodo para buscar fornecedor pelo CODIGO
	 * 
	 * @param codigo
	 * @return fornecedor buscado ou null
	 */
	@SuppressWarnings("unchecked")
	public LogRetorno getById(Integer codigo) {

		LogRetorno logRet = new LogRetorno();

		try {
			em = ConnectionHib.emf.createEntityManager();

			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);

			List<Fornecedor> list = em.createNamedQuery("FornecedorById").setParameter("codigo", codigo)
					.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.FORNECEDORES))
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
	 * Metodo para buscar Transportadora pelo CODIGO
	 * 
	 * @param codigo
	 * @return Transportadora buscado ou null
	 */
	@SuppressWarnings("unchecked")
	public LogRetorno getTransportadoraById(Integer codigo) {

		LogRetorno logRet = new LogRetorno();

		try {
			em = ConnectionHib.emf.createEntityManager();

			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);

			List<Fornecedor> list = em.createNamedQuery("TransportadoraById").setParameter("codigo", codigo)
					.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.FORNECEDORES))
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

	public LogRetorno update(Object objeto) {

		Fornecedor fornecedor = (Fornecedor) objeto;

		LogRetorno logRet = new LogRetorno();

		try {
			em = ConnectionHib.emf.createEntityManager();
			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);
			em.getTransaction().begin();
			FornecedorPK fornecedorPK = new FornecedorPK();
			fornecedorPK.setCodigo(fornecedor.getCodigo());
			// fornecedorPK.setCodemp(fornecedor.getCodemp());
			fornecedorPK.setCheckDelete(fornecedor.getCheckDelete());

			Fornecedor newFornecedor = em.find(Fornecedor.class, fornecedorPK);

			if (newFornecedor != null && newFornecedor.getFlagAtivo() == 1) {

				Date dataServer = (Date) em.createNativeQuery(
						ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL")
						.getSingleResult();
				utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));
				ts_now = Util.dateToLocalDateTime(dataServer);

				fornecedor.setLastMovto(ts_now);
				fornecedor.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
				fornecedor.setUtctag(utctag);

				em.merge(fornecedor);

				em.getTransaction().commit();

				logRet.setStatus(EnumLogRetornoStatus.SUCESSO);
				logRet.setMsg("Sucesso");
			} else {
				logRet.setStatus(EnumLogRetornoStatus.ERRO);
				logRet.setMsg(DadosGlobais.resourceBundle.getString("errorUpdate"));
			}
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}

		return logRet;
	}

	public void delete(Object objeto) {

		Fornecedor fornecedor = (Fornecedor) objeto;

		try {

			em = ConnectionHib.emf.createEntityManager();

			em.getTransaction().begin();

			Date dataServer = (Date) em.createNativeQuery(
					ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL")
					.getSingleResult();
			utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));
			ts_now = Util.dateToLocalDateTime(dataServer);

			em.createQuery("UPDATE Fornecedor u " + "SET u.flagAtivo = " + fornecedor.getFlagAtivo() + ","
					+ "u.lastMovto = :dtAlteracao," + "u.lastCoduser = " + DadosGlobais.usuarioLogado.getCodigo() + ","
					+ "u.utctag = " + utctag + " " + "WHERE u.codigo= :codigo " + "AND u.recordNo = :recordNo "
					+ "AND u.codemp = :codemp ").setParameter("dtAlteracao", ts_now)
					.setParameter("codigo", fornecedor.getCodigo()).setParameter("recordNo", fornecedor.getRecordNo())
					.setParameter("codemp", fornecedor.getCodemp()).executeUpdate();

			Integer tipoOperacao = null;
			String valorAnterior = null;
			String valorNovo = null;
			if (fornecedor.getFlagAtivo() == 1) {
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
			auditoria.setTipoRegistro(DadosGlobais.resourceBundle.getString("miCadFornecedor").toUpperCase());
			auditoria.setDtMovto(Util.geraDataMovto(ts_now));
			auditoria.setValorAnterior(valorAnterior);
			auditoria.setValorNovo(valorNovo);
			auditoria.setHistorico(fornecedor.getCodigo() + "-" + fornecedor.getRazao());
			auditoria.setDocCodigo(String.valueOf(fornecedor.getCodigo()));

			em.persist(auditoria);
			em.getTransaction().commit();

		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}

	}

	public Fornecedor getFornecedorByCNPJ(String cnpj) {

		Fornecedor fornecedor = null;
		try {

			em = ConnectionHib.emf.createEntityManager();
			@SuppressWarnings("unchecked")
			List<Fornecedor> listFornecedores = em.createNamedQuery("FornecedorByCNPJ").setParameter("cpfCnpj", cnpj)
					.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.FORNECEDORES))
					.getResultList();

			if (!listFornecedores.isEmpty()) {
				fornecedor = listFornecedores.get(0);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			em.close();
		}
		return fornecedor;
	}

	@SuppressWarnings("unchecked")
	public List<Fornecedor> filterByColumn(String column, String filter, int action, List<Integer> ativo) {

		List<Fornecedor> listFilter = null;
		try {
			em = ConnectionHib.emf.createEntityManager();
			if (action == 1) {
				if (filter.equals("")) {
					listFilter = em
							.createQuery("SELECT f FROM Fornecedor f WHERE f." + column
									+ " LIKE CONCAT('%', :filter, '%') AND f.codemp IN (:codemp) AND f.flagAtivo IN (:flag)")
							.setParameter("filter", (filter.equals("")) ? "%" : filter)
							.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.FORNECEDORES))
							.setParameter("flag", ativo).getResultList();
				} else {

					listFilter = em
							.createQuery("SELECT f FROM Fornecedor d WHERE f." + column
									+ "= :filter AND f.codemp IN (:codemp) AND f.flagAtivo IN (:flag)")
							.setParameter("filter", Integer.parseInt(filter))
							.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.FORNECEDORES))
							.setParameter("flag", ativo).getResultList();

				}

			} else {
				listFilter = em
						.createQuery("SELECT f FROM Fornecedor f WHERE f." + column
								+ " LIKE CONCAT('%', :filter, '%') AND f.codemp IN (:codemp) AND f.flagAtivo IN (:flag)")
						.setParameter("filter", (filter.equals("")) ? "%" : filter)
						.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.FORNECEDORES))
						.setParameter("flag", ativo).getResultList();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			em.close();
		}

		return listFilter;
	}

	public List<Fornecedor> filterTrasnportadoraByColumn(String column, String filter, int action,
			List<Integer> ativo) {

		List<Fornecedor> listFilter = null;
		try {
			em = ConnectionHib.emf.createEntityManager();
			if (action == 1) {
				if (filter.equals("")) {
					listFilter = em
							.createQuery("SELECT f FROM Fornecedor f WHERE f." + column
									+ " LIKE CONCAT('%', :filter, '%') AND f.codemp IN (:codemp) AND f.flagTransportadora = 1 AND f.flagAtivo IN (:flag)")
							.setParameter("filter", (filter.equals("")) ? "%" : filter)
							.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.FORNECEDORES))
							.setParameter("flag", ativo).getResultList();
				} else {

					listFilter = em
							.createQuery("SELECT f FROM Fornecedor f WHERE f." + column
									+ "= :filter AND f.codemp IN (:codemp) AND f.flagTransportadora = 1 AND f.flagAtivo IN (:flag)")
							.setParameter("filter", Integer.parseInt(filter))
							.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.FORNECEDORES))
							.setParameter("flag", ativo).getResultList();

				}

			} else {
				listFilter = em
						.createQuery("SELECT f FROM Fornecedor f WHERE f." + column
								+ " LIKE CONCAT('%', :filter, '%') AND f.codemp IN (:codemp) AND f.flagTransportadora = 1 AND f.flagAtivo IN (:flag)")
						.setParameter("filter", (filter.equals("")) ? "%" : filter)
						.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.FORNECEDORES))
						.setParameter("flag", ativo).getResultList();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			em.close();
		}

		return listFilter;
	}

}
