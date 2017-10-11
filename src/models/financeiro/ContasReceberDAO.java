package models.financeiro;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import application.DadosGlobais;
import br.com.samuelweb.nfe.util.ObjetoUtil;
import connect.ConnectionHib;
import interfaces.InterfaceDAO;
import javafx.collections.ObservableList;
import models.configuracoes.Auditoria;
import models.configuracoes.SequencialDAO;
import models.vendas.Cliente;
import tools.enums.EnumCompartilhamento;
import tools.enums.EnumLogRetornoStatus;
import tools.enums.EnumTipoOpeAuditoria;
import tools.utils.LogRetorno;
import tools.utils.Util;

public class ContasReceberDAO implements InterfaceDAO {

	private LocalDateTime ts_now;
	private BigInteger utctag;
	private EntityManager em = null;
	public static final BigDecimal ONE_HUNDRED = new BigDecimal(100.0000).setScale(2);

	public LogRetorno insert(Object objeto) {

		ContasReceber contasReceber = (ContasReceber) objeto;
		LogRetorno logRet = new LogRetorno();
		SequencialDAO seqDAO = new SequencialDAO();

		try {
			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);

			em = ConnectionHib.emf.createEntityManager();
			em.getTransaction().begin();

			Date dataServer = (Date) em.createNativeQuery(
					ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL")
					.getSingleResult();

			// em.createQuery("UPDATE Sequencial s SET s.fcContasReceber =
			// s.fcContasReceber + 1 WHERE s.codemp= :codemp AND s.checkDelete =
			// :checkDelete")
			// .setParameter("codemp", DadosGlobais.empresaLogada.getCodigo())
			// .setParameter("checkDelete",
			// DadosGlobais.empresaLogada.getCheckDelete())
			// .executeUpdate();

			utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));

			ts_now = Util.dateToLocalDateTime(dataServer);

			// Integer codigo =
			// seqDAO.getLastInsertCodigo(DadosGlobais.empresaLogada.getCodigo(),
			// "fcContasReceber") + 1;

			contasReceber.setCodemp(DadosGlobais.empresaLogada.getCodigo());
			// contasReceber.setCodigo(codigo);
			contasReceber.setUtctag(utctag);
			contasReceber.setCheckDelete(Util.checkDeleteCreate());
			contasReceber.setFlagAtivo(1);
			contasReceber.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
			contasReceber.setLastMovto(ts_now);
			contasReceber.setDataLancamento(Util.geraDataMovto());

			em.persist(contasReceber);

			em.getTransaction().commit();

			logRet.setStatus(EnumLogRetornoStatus.SUCESSO);

			logRet.setMsg(EnumLogRetornoStatus.SUCESSO.name());

			// logRet.setLastRecord(codigo);

			logRet.setObjeto(contasReceber);

		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();

			throw e;
		} finally {
			em.close();
		}
		return logRet;
	}

	public LogRetorno insert(Object objeto, boolean noDocAuto) {

		ContasReceber contasReceber = (ContasReceber) objeto;
		LogRetorno logRet = new LogRetorno();
		SequencialDAO seqDAO = new SequencialDAO();
		int noDoc = 0;

		try {
			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);

			em = ConnectionHib.emf.createEntityManager();
			em.getTransaction().begin();

			Date dataServer = (Date) em.createNativeQuery(
					ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL")
					.getSingleResult();

			utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));
			ts_now = Util.dateToLocalDateTime(dataServer);

			if (noDocAuto) {

				em.createQuery(
						"UPDATE Sequencial s SET s.fcContasReceber = s.fcContasReceber + 1 WHERE s.codemp= :codemp AND s.checkDelete = :checkDelete")
						.setParameter("codemp", DadosGlobais.empresaLogada.getCodigo())
						.setParameter("checkDelete", DadosGlobais.empresaLogada.getCheckDelete()).executeUpdate();

				noDoc = seqDAO.getLastInsertCodigo(DadosGlobais.empresaLogada.getCodigo(), "fcContasReceber") + 1;
			}

			if (noDocAuto)
				contasReceber.setNoDocto(noDoc);

			contasReceber.setCodemp(DadosGlobais.empresaLogada.getCodigo());
			contasReceber.setUtctag(utctag);
			contasReceber.setCheckDelete(Util.checkDeleteCreate());
			contasReceber.setFlagAtivo(1);
			contasReceber.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
			contasReceber.setLastMovto(ts_now);
			contasReceber.setDataLancamento(Util.geraDataMovto());

			em.persist(contasReceber);

			em.getTransaction().commit();

			logRet.setStatus(EnumLogRetornoStatus.SUCESSO);

			logRet.setMsg(EnumLogRetornoStatus.SUCESSO.name());

			logRet.setObjeto(contasReceber);

		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();

			throw e;
		} finally {
			em.close();
		}
		return logRet;
	}

	public LogRetorno insertLot(ObservableList<ContasReceber> list, boolean noDocAuto) {

		LogRetorno logRet = new LogRetorno();
		SequencialDAO seqDAO = new SequencialDAO();
		int noDoc = 0;

		try {
			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);

			em = ConnectionHib.emf.createEntityManager();
			em.getTransaction().begin();

			Date dataServer = (Date) em.createNativeQuery(
					ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL")
					.getSingleResult();

			utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));
			ts_now = Util.dateToLocalDateTime(dataServer);

			if (noDocAuto) {

				em.createQuery(
						"UPDATE Sequencial s SET s.fcContasReceber = s.fcContasReceber + 1 WHERE s.codemp= :codemp AND s.checkDelete = :checkDelete")
						.setParameter("codemp", DadosGlobais.empresaLogada.getCodigo())
						.setParameter("checkDelete", DadosGlobais.empresaLogada.getCheckDelete()).executeUpdate();

				noDoc = seqDAO.getLastInsertCodigo(DadosGlobais.empresaLogada.getCodigo(), "fcContasReceber") + 1;

			}

			for (ContasReceber contasReceber : list) {

				if (noDocAuto)
					contasReceber.setNoDocto(noDoc);

				contasReceber.setCodemp(DadosGlobais.empresaLogada.getCodigo());
				// contasReceber.setCodigo(codigo);
				contasReceber.setUtctag(utctag);
				contasReceber.setCheckDelete(Util.checkDeleteCreate());
				contasReceber.setFlagAtivo(1);
				contasReceber.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
				contasReceber.setLastMovto(ts_now);
				contasReceber.setDataLancamento(Util.geraDataMovto());

				em.persist(contasReceber);

			}

			em.getTransaction().commit();

			logRet.setStatus(EnumLogRetornoStatus.SUCESSO);
			logRet.setMsg(EnumLogRetornoStatus.SUCESSO.name());
			logRet.setListaObjetos(list);

		} catch (Exception e) {

			logRet.setStatus(EnumLogRetornoStatus.ERRO);
			logRet.setMsg(DadosGlobais.resourceBundle.getString("errorInsert"));

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
	public List<ContasReceber> getList(List<Integer> flagAtivo) {
		List<ContasReceber> list = null;
		try {
			em = ConnectionHib.emf.createEntityManager();

			// list =
			// em.createNamedQuery("ContasReceberAll").setParameter("flag",
			// flagAtivo)
			// .setParameter("codemp",
			// Util.getCompartilhamentoEntidade(EnumCompartilhamento.CONTAS_RECEBER))
			// .getResultList();
			
			list = em.createQuery("SELECT c FROM  ContasReceber c LEFT JOIN FETCH c.cliente LEFT JOIN FETCH c.opeFinanceiro LEFT JOIN FETCH c.portador LEFT JOIN FETCH c.secao LEFT JOIN FETCH c.vendedor LEFT JOIN FETCH c.planoPagamento LEFT JOIN FETCH c.planoConta WHERE c.flagAtivo IN (:flag) AND c.codemp IN (:codemp)")
					.setParameter("flag", flagAtivo)
					.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.CONTAS_RECEBER))
					.getResultList();
			
			for (int i = 0; i < list.size(); i++) {
				
				list.get(i).setAtraso((int) (LocalDate.now().toEpochDay() - list.get(i).getDataVencimento().toLocalDate().toEpochDay()));
				
				BigDecimal percJuros = BigDecimal.valueOf(9.00);
				
				if(DadosGlobais.empresaLogada.getConfig().getFinTipointevaloIntegerajuros().equals(0)){
					
					percJuros = percJuros.divide(BigDecimal.valueOf(30.00), 2, RoundingMode.HALF_EVEN).setScale(2);
					
				}else{
					percJuros = BigDecimal.valueOf(5.00);
				}				
				
				//if(list.get(i).getAtraso().compareTo(0) > 0)					
					list.get(i).setJuros((list.get(i).getValorLiquido().multiply((percJuros.divide(ONE_HUNDRED,4, RoundingMode.HALF_EVEN)))).multiply(BigDecimal.valueOf(list.get(i).getAtraso().doubleValue())));
			
				
			
				list.get(i).setValorTotal(list.get(i).getValorLiquido().add(list.get(i).getJuros()));
				
				list.get(i).setSaldo(list.get(0).getValorTotal().subtract(list.get(i).getValorPagto()));
			}
			
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

			List<ContasReceber> list = em.createNamedQuery("ContasReceberLast").setMaxResults(1)
					.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.CONTAS_RECEBER))
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
	 * Metodo para buscar departamento pelo CODIGO
	 * 
	 * @param codigo
	 * @return departamento buscado ou null
	 */
	@SuppressWarnings("unchecked")
	public LogRetorno getById(Integer codigo) {

		// LogRetorno logRet = new LogRetorno();

		// try {
		// em = ConnectionHib.emf.createEntityManager();
		//
		// logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);
		//
		// List<ContasReceber> list =
		// em.createNamedQuery("ContasReceberById").setParameter("codigo",
		// codigo)
		// .setParameter("codemp",
		// Util.getCompartilhamentoEntidade(EnumCompartilhamento.CONTAS_RECEBER))
		// .getResultList();
		//
		// if (!list.isEmpty()) {
		// if (list.size() > 1) {
		// logRet.setStatus(EnumLogRetornoStatus.REGDUPLICADO);
		// logRet.setObjeto(list.get(0));
		// logRet.setMsg(DadosGlobais.resourceBundle.getString("errorDuplicateRecord"));
		// } else if (list.size() == 1) {
		// logRet.setStatus(EnumLogRetornoStatus.SUCESSO);
		// logRet.setObjeto(list.get(0));
		// }
		//
		// } else {
		// logRet.setStatus(EnumLogRetornoStatus.ERRO);
		// logRet.setMsg(DadosGlobais.resourceBundle.getString("errorSelect"));
		// }
		// } catch (Exception e) {
		// throw e;
		//
		// } finally {
		// em.close();
		// }
		return null;
	}

	@SuppressWarnings("unchecked")
	public LogRetorno update(Object objeto) {

		ContasReceber contasReceber = (ContasReceber) objeto;

		LogRetorno logRet = new LogRetorno();

		try {
			em = ConnectionHib.emf.createEntityManager();
			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);
			em.getTransaction().begin();
			ContasReceberPK contasReceberPK = new ContasReceberPK();
			contasReceberPK.setCheckDelete(contasReceber.getCheckDelete());
			contasReceberPK.setNoDocto(contasReceber.getNoDocto());
			contasReceberPK.setCliente(contasReceber.getCliente());

			ContasReceber newContasReceber = em.find(ContasReceber.class, contasReceberPK);

			if (newContasReceber != null && newContasReceber.getFlagAtivo() == 1) {

				Date dataServer = (Date) em.createNativeQuery(
						ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL")
						.getSingleResult();
				utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));
				ts_now = Util.dateToLocalDateTime(dataServer);

				contasReceber.setLastMovto(ts_now);
				contasReceber.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
				contasReceber.setUtctag(utctag);

				em.merge(contasReceber);

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

		ContasReceber contasReceber = (ContasReceber) objeto;

		try {

			em = ConnectionHib.emf.createEntityManager();

			em.getTransaction().begin();

			Date dataServer = (Date) em.createNativeQuery(
					ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL")
					.getSingleResult();
			utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));
			ts_now = Util.dateToLocalDateTime(dataServer);

			em.createQuery("UPDATE ContasReceber u " + "SET u.flagAtivo = " + contasReceber.getFlagAtivo() + ","
					+ "u.lastMovto = :dtAlteracao," + "u.lastCoduser = " + DadosGlobais.usuarioLogado.getCodigo() + ","
					+ "u.utctag = " + utctag + " " + "WHERE u.codigo= :codigo " + "AND u.recordNo = :recordNo "
					+ "AND u.codemp = :codemp ").setParameter("dtAlteracao", ts_now)
					// .setParameter("codigo", contasReceber.getCodigo())
					.setParameter("recordNo", contasReceber.getRecordNo())
					.setParameter("codemp", contasReceber.getCodemp()).executeUpdate();

			Integer tipoOperacao = null;
			String valorAnterior = null;
			String valorNovo = null;
			if (contasReceber.getFlagAtivo() == 1) {
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
			auditoria.setTipoRegistro(DadosGlobais.resourceBundle.getString("miCadContasReceber").toUpperCase());
			auditoria.setDtMovto(Util.geraDataMovto(ts_now));
			auditoria.setValorAnterior(valorAnterior);
			auditoria.setValorNovo(valorNovo);
			// auditoria.setHistorico(contasReceber.getCodigo() + "-" +
			// contasReceber.getDescricao());
			// auditoria.setDocCodigo(String.valueOf(contasReceber.getCodigo()));

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
	public List<ContasReceber> filterByColumn(String column, String filter, int action, List<Integer> ativo) {

		List<ContasReceber> listFilter = null;
		try {
			em = ConnectionHib.emf.createEntityManager();
			if (action == 1) {
				if (filter.equals("")) {
					listFilter = em
							.createQuery("SELECT c FROM ContasReceber c WHERE c." + column
									+ " LIKE CONCAT('%', :filter, '%') AND c.codemp IN (:codemp) AND c.flagAtivo IN (:flag)")
							.setParameter("filter", (filter.equals("")) ? "%" : filter)
							.setParameter("codemp",
									Util.getCompartilhamentoEntidade(EnumCompartilhamento.CONTAS_RECEBER))
							.setParameter("flag", ativo).getResultList();
				} else {

					listFilter = em
							.createQuery("SELECT c FROM ContasReceber c WHERE c." + column
									+ "= :filter AND c.codemp IN (:codemp) AND c.flagAtivo IN (:flag)")
							.setParameter("filter", Integer.parseInt(filter))
							.setParameter("codemp",
									Util.getCompartilhamentoEntidade(EnumCompartilhamento.CONTAS_RECEBER))
							.setParameter("flag", ativo).getResultList();

				}

			} else {
				listFilter = em
						.createQuery("SELECT c FROM ContasReceber c WHERE c." + column
								+ " LIKE CONCAT('%', :filter, '%') AND c.codemp IN (:codemp) AND c.flagAtivo IN (:flag)")
						.setParameter("filter", (filter.equals("")) ? "%" : filter)
						.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.CONTAS_RECEBER))
						.setParameter("flag", ativo).getResultList();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			em.close();
		}

		return listFilter;
	}

	@SuppressWarnings("unchecked")
	public LogRetorno getByDocumentParcel(Integer noDocumento, Integer noParcela, Cliente cli) {

		LogRetorno logRet = new LogRetorno();

		try {
			em = ConnectionHib.emf.createEntityManager();

			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);

			List<ContasReceber> list = em.createNamedQuery("ContasReceberByDocumentParcel")
					.setParameter("noDocto", noDocumento).setParameter("noParcela", noParcela)
					.setParameter("cliente", cli)
					.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.CONTAS_RECEBER))
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
				logRet.setStatus(EnumLogRetornoStatus.EMPTY_DATA);
				logRet.setMsg(DadosGlobais.resourceBundle.getString("errorSelect"));
			}
		} catch (Exception e) {
			throw e;

		} finally {
			em.close();
		}
		return logRet;
	}

	@SuppressWarnings("unchecked")
	public LogRetorno getByDocument(Integer noDocumento, Cliente cli) {

		LogRetorno logRet = new LogRetorno();

		try {
			em = ConnectionHib.emf.createEntityManager();

			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);

			List<ContasReceber> list = em.createNamedQuery("ContasReceberByDocument")
					.setParameter("noDocto", noDocumento).setParameter("cliente", cli)
					.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.CONTAS_RECEBER))
					.getResultList();

			if (!list.isEmpty()) {
				logRet.setStatus(EnumLogRetornoStatus.SUCESSO);
				logRet.setListaObjetos(list);
			} else {
				logRet.setStatus(EnumLogRetornoStatus.EMPTY_DATA);
				logRet.setMsg(DadosGlobais.resourceBundle.getString("errorSelect"));
			}
		} catch (Exception e) {

			logRet.setStatus(EnumLogRetornoStatus.ERRO);
			logRet.setMsg("ERROR");

			throw e;

		} finally {
			em.close();
		}
		return logRet;
	}

	public LogRetorno operations(List<Object> objects) {

		// ContasReceber contasReceber = (ContasReceber) objeto;

		LogRetorno logRet = new LogRetorno();

		List<Object> listCR = new ArrayList();

		try {

			em = ConnectionHib.emf.createEntityManager();
			em.getTransaction().begin();
			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);

			for (int i = 0; i < objects.size(); i++) {

				ContasReceber contasReceber = (ContasReceber) objects.get(i);

				ContasReceberPK contasReceberPK = new ContasReceberPK();
				contasReceberPK.setCheckDelete(contasReceber.getCheckDelete());
				contasReceberPK.setNoDocto(contasReceber.getNoDocto());
				contasReceberPK.setCliente(contasReceber.getCliente());

				ContasReceber newContasReceber = em.find(ContasReceber.class, contasReceberPK);

				if (newContasReceber != null && newContasReceber.getFlagAtivo() == 1) {

					Date dataServer = (Date) em.createNativeQuery(ConnectionHib.SGBD.toLowerCase().equals("mysql")
							? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL").getSingleResult();
					utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));
					ts_now = Util.dateToLocalDateTime(dataServer);

					contasReceber.setLastMovto(ts_now);
					contasReceber.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
					contasReceber.setUtctag(utctag);

					em.merge(contasReceber);
					listCR.add(contasReceber);

				} else {
					logRet.setStatus(EnumLogRetornoStatus.ERRO);
					logRet.setMsg(DadosGlobais.resourceBundle.getString("errorUpdate"));
					return logRet;
				}
			}

			em.getTransaction().commit();
			logRet.setStatus(EnumLogRetornoStatus.SUCESSO);
			logRet.setListaObjetos(listCR);

		} catch (Exception e) {

			logRet.setStatus(EnumLogRetornoStatus.ERRO);
			logRet.setMsg("ERROR");

			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}

		return logRet;
	}

}
