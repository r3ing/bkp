package models.vendas;


import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

import application.DadosGlobais;
import connect.ConnectionHib;
import models.configuracoes.Auditoria;
import models.configuracoes.SequencialDAO;
import tools.enums.EnumCompartilhamento;
import tools.enums.EnumLogRetornoStatus;
import tools.enums.EnumTipoOpeAuditoria;
import tools.utils.LogRetorno;
import tools.utils.Util;

public class CidadeDAO {
	
	private LogRetorno logRet = new LogRetorno();

	private LocalDateTime ts_now;
	private BigInteger utctag;
	private EntityManager em;

	public LogRetorno insert(Object objeto) {

		Cidade cidade = (Cidade) objeto;
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
					"UPDATE Sequencial s SET s.vdCidade = s.vdCidade+1 WHERE s.codemp= :codemp AND s.checkDelete = :checkDelete")
					.setParameter("codemp", DadosGlobais.empresaLogada.getCodigo())
					.setParameter("checkDelete", DadosGlobais.empresaLogada.getCheckDelete()).executeUpdate();

			utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));

			ts_now = Util.dateToLocalDateTime(dataServer);

			Integer codigo = seqDAO.getLastInsertCodigo(DadosGlobais.empresaLogada.getCodigo(), "vdCidade") + 1;

			cidade.setCodemp(DadosGlobais.empresaLogada.getCodigo());
			cidade.setCodigo(codigo);
			cidade.setUtctag(utctag);
			cidade.setCheckDelete(Util.checkDeleteCreate());
			cidade.setFlagAtivo(1);
			cidade.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
			cidade.setLastMovto(ts_now);

			em.persist(cidade);

			em.getTransaction().commit();

			logRet.setStatus(EnumLogRetornoStatus.SUCESSO);

			logRet.setMsg(EnumLogRetornoStatus.SUCESSO.name());

			logRet.setLastRecord(codigo);

			logRet.setObjeto(cidade);

		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
		return logRet;
	}

	@SuppressWarnings("unchecked")
	public List<Cidade> getList(List<Integer> flagAtivo) {
		List<Cidade> list = null;
		try {
			em = ConnectionHib.emf.createEntityManager();

			list = em.createNamedQuery("CidadeAll").setParameter("flag", flagAtivo)
					.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.CIDADES))
					.getResultList();

		} catch (Exception e) {
			// e.printStackTrace();
			throw e;
		} finally {
			em.close();
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Cidade> filterByColumn(String column, String filter, int action, List<Integer> ativo) throws Exception {

		List<Cidade> listFilter = null;
		try {
			em = ConnectionHib.emf.createEntityManager();
			if (action == 1) {
				if (filter.equals("")) {
					listFilter = em
							.createQuery("SELECT d FROM Cidade d INNER JOIN FETCH d.uf WHERE d." + column
									+ " LIKE CONCAT('%', :filter, '%') AND d.uf.codemp IN (:codemp) AND d.flagAtivo IN (:flag)")
							.setParameter("filter", (filter.equals("")) ? "%" : filter)
							.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.CIDADES))
							.setParameter("flag", ativo).getResultList();
				} else {

					listFilter = em
							.createQuery("SELECT d FROM Cidade d INNER JOIN FETCH d.uf WHERE d." + column
									+ "= :filter AND d.uf.codemp IN (:codemp) AND d.flagAtivo IN (:flag)")
							.setParameter("filter", Integer.parseInt(filter))
							.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.CIDADES))
							.setParameter("flag", ativo).getResultList();

				}

			} else if (action == 2){
				listFilter = em
						.createQuery("SELECT d FROM Cidade d INNER JOIN FETCH d.uf WHERE d." + column
								+ " LIKE CONCAT('%', :filter, '%') AND d.uf.codemp IN (:codemp) AND d.flagAtivo IN (:flag)")
						.setParameter("filter", (filter.equals("")) ? "%" : filter)
						.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.CIDADES))
						.setParameter("flag", ativo).getResultList();
				

			} else if (action == 3){
				listFilter = em
						.createQuery("SELECT d FROM Cidade d INNER JOIN FETCH d.uf WHERE d.uf.sigla LIKE CONCAT('%', :filter, '%') AND d.uf.codemp IN (:codemp) AND d.flagAtivo IN (:flag)")
						.setParameter("filter", (filter.equals("")) ? "%" : filter)
						.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.CIDADES))
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

	@SuppressWarnings("unchecked")
	public LogRetorno getLast() {

		LogRetorno logRet = new LogRetorno();

		try {
			em = ConnectionHib.emf.createEntityManager();

			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);

			List<Pais> list = em.createNamedQuery("CidadeLast").setMaxResults(1)
					.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.CIDADES))
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
	 * Metodo para buscar Cidade pelo CODIGO
	 * 
	 * @param codigo
	 * @return departamento buscado ou null
	 */
	@SuppressWarnings("unchecked")
	public LogRetorno getById(int codigo) throws Exception {
		Cidade cidade = null;

		try {
			try {
				em = ConnectionHib.emf.createEntityManager();
			} catch (Exception e) {

			}
			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);
			logRet.setMsg("Inicio Processo");

			List<Cidade> list = em.createNamedQuery("CidadeById")
					.setParameter("codigo", codigo)
					.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.CIDADES))
					.getResultList();

			if (!list.isEmpty()) {
				if (list.size() > 1) {
					logRet.setStatus(EnumLogRetornoStatus.REGDUPLICADO);
					logRet.setObjeto(list.get(0));
					logRet.setMsg("O código retornou mais de um resultado, verifique integridade do cadastro!");
				} else if (list.size() == 1) {
					logRet.setStatus(EnumLogRetornoStatus.SUCESSO);
					logRet.setObjeto(list.get(0));
				}

			} else {
				logRet.setStatus(EnumLogRetornoStatus.ERRO);
				logRet.setMsg("Erro ao Cadastrar Registro");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return logRet;
	}
	
	/**
	 * MÉTODO PARA BUSCAR CIDADE PELO CODIGO IBGE
	 * @param codIBGE
	 * @return
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	public LogRetorno getByIBGE(String codIBGE){
		Cidade cidade = null;

		try {
			try {
				em = ConnectionHib.emf.createEntityManager();
			} catch (Exception e) {

			}
			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);
			logRet.setMsg("Inicio Processo");

			List<Cidade> list = em.createNamedQuery("CidadeByIBGE")
					.setParameter("codigoIbge", codIBGE)
					.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.CIDADES))
					.getResultList();

			if (!list.isEmpty()) {
				if (list.size() > 1) {
					logRet.setStatus(EnumLogRetornoStatus.REGDUPLICADO);
					logRet.setObjeto(list.get(0));
					logRet.setMsg("O código retornou mais de um resultado, verifique integridade do cadastro!");
				} else if (list.size() == 1) {
					logRet.setStatus(EnumLogRetornoStatus.SUCESSO);
					logRet.setObjeto(list.get(0));
				}

			} else {
				logRet.setStatus(EnumLogRetornoStatus.ERRO);
				logRet.setMsg("Erro ao Cadastrar Registro");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return logRet;
	}

	public LogRetorno update(Object objeto) {

		Cidade cidade = (Cidade) objeto;

		LogRetorno logRet = new LogRetorno();

		try {
			em = ConnectionHib.emf.createEntityManager();
			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);
			em.getTransaction().begin();
			CidadePK cidadePK = new CidadePK();
			cidadePK.setCodigo(cidade.getCodigo());
			cidadePK.setCheckDelete(cidade.getCheckDelete());

			Cidade newCidade = em.find(Cidade.class, cidadePK);

			if (newCidade != null && newCidade.getFlagAtivo() == 1) {

				Date dataServer = (Date) em.createNativeQuery(
						ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL")
						.getSingleResult();
				utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));
				ts_now = Util.dateToLocalDateTime(dataServer);

				cidade.setLastMovto(ts_now);
				cidade.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
				cidade.setUtctag(utctag);

				em.merge(cidade);

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

		Cidade cidade = (Cidade) objeto;

		try {

			em = ConnectionHib.emf.createEntityManager();

			em.getTransaction().begin();

			Date dataServer = (Date) em.createNativeQuery(
					ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL")
					.getSingleResult();
			utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));
			ts_now = Util.dateToLocalDateTime(dataServer);

			em.createQuery("UPDATE Cidade u " + "SET u.flagAtivo = " + cidade.getFlagAtivo() + ","
					+ "u.lastMovto = :dtAlteracao," + "u.lastCoduser = " + DadosGlobais.usuarioLogado.getCodigo() + ","
					+ "u.utctag = " + utctag + " " + "WHERE u.codigo= :codigo " + "AND u.recordNo = :recordNo "
					+ "AND u.codemp = :codemp ").setParameter("dtAlteracao", ts_now)
					.setParameter("codigo", cidade.getCodigo()).setParameter("recordNo", cidade.getRecordNo())
					.setParameter("codemp", cidade.getUf().getCodemp()).executeUpdate();

			Integer tipoOperacao = null;
			String valorAnterior = null;
			String valorNovo = null;
			if (cidade.getFlagAtivo() == 1) {
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
			auditoria.setTipoRegistro(DadosGlobais.resourceBundle.getString("miCadPais").toUpperCase());
			auditoria.setDtMovto(Util.geraDataMovto(ts_now));
			auditoria.setValorAnterior(valorAnterior);
			auditoria.setValorNovo(valorNovo);
			auditoria.setHistorico(cidade.getCodigo() + "-" + cidade.getDescricao());
			auditoria.setDocCodigo(String.valueOf(cidade.getCodigo()));

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
	public LogRetorno getCidadeByName(String nomeCidadea, String ufCidade) {
		List<Cidade> list = null;
					
		try {
			em = ConnectionHib.emf.createEntityManager();
			
			list = em.createNamedQuery("CidadeByName")
					.setParameter("descricao", nomeCidadea)
					.setParameter("uf", ufCidade)
					.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.CIDADES))
					.getResultList();
			
			if (!list.isEmpty()) {
				if (list.size() > 1) {
					logRet.setStatus(EnumLogRetornoStatus.REGDUPLICADO);
					logRet.setObjeto(list.get(0));
					logRet.setMsg("O código retornou mais de um resultado, verifique integridade do cadastro!");
				} else if (list.size() == 1) {
					logRet.setStatus(EnumLogRetornoStatus.SUCESSO);
					logRet.setObjeto(list.get(0));
				}

			} else {
				logRet.setStatus(EnumLogRetornoStatus.ERRO);
				logRet.setMsg("Erro ao Consultar Registro");
			}
				
			
		} catch (Exception e) {
			 e.printStackTrace();
			throw e;
		} finally {
			em.close();
		}

		return logRet;
	}



}
