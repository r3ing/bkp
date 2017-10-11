package models.financeiro; 


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

public class CaixaBancoDAO implements InterfaceDAO{

	private LocalDateTime ts_now; 
	private BigInteger utctag;
	private EntityManager em = null;

	public LogRetorno insert(Object objeto){

		CaixaBanco caixa = (CaixaBanco) objeto;
		LogRetorno logRet = new LogRetorno();
		SequencialDAO seqDAO = new SequencialDAO();
		try {
			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);
			
			em = ConnectionHib.emf.createEntityManager();
			em.getTransaction().begin();
			
			Date dataServer  = (Date) em.createNativeQuery(ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL").getSingleResult();
			
			em.createQuery("UPDATE Sequencial s SET s.fcCaixaBanco = s.fcCaixaBanco+1 WHERE s.codemp= :codemp AND s.checkDelete = :checkDelete")
			.setParameter("codemp", DadosGlobais.empresaLogada.getCodigo())
			.setParameter("checkDelete", DadosGlobais.empresaLogada.getCheckDelete())
			.executeUpdate();
			
			utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));		
			
			ts_now = Util.dateToLocalDateTime(dataServer);
			
			Integer codigo = seqDAO.getLastInsertCodigo(DadosGlobais.empresaLogada.getCodigo(), "fcCaixaBanco") + 1;
			
			caixa.setCodemp(DadosGlobais.empresaLogada.getCodigo());
			caixa.setCodigo(codigo);
			caixa.setUtctag(utctag);
			caixa.setCheckDelete(Util.checkDeleteCreate());
			caixa.setFlagAtivo(1);
			caixa.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
	        caixa.setLastMovto(ts_now);
			
			em.persist(caixa);
			
			em.getTransaction().commit();
			
			logRet.setStatus(EnumLogRetornoStatus.SUCESSO);
			
			logRet.setMsg(EnumLogRetornoStatus.SUCESSO.name());
			
			logRet.setLastRecord(codigo);
			
			logRet.setObjeto(caixa);
			
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
	 * Metodo para listar tudos os PLANO_PAGAMENTO, dependendo do valor do
	 * flag_Ativo e compartilhamento
	 * 
	 * @param flagAtivo
	 * @return
	 */
	public List<CaixaBanco> getList(List<Integer> flagAtivo) {
		List<CaixaBanco> list = null;  
		try {	
		em = ConnectionHib.emf.createEntityManager();		
		
		list = em.createNamedQuery("CaixaBancoAll")
					.setParameter("flag", flagAtivo)
					.setParameter("codemp",Util.getCompartilhamentoEntidade(EnumCompartilhamento.CAIXA_BANCO))
					.getResultList();

		}
		catch (Exception e) {
			throw e;	
		
		} finally {
			em.close();
		}

		return list;
	}

	
	@SuppressWarnings("unchecked")
	public LogRetorno getLast(){
		
		LogRetorno logRet = new LogRetorno();

		try{
			em = ConnectionHib.emf.createEntityManager();
			
			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);

			List<CaixaBanco> list = em.createNamedQuery("CaixaBancoLast")
					.setMaxResults(1)
					.setParameter("codemp",Util.getCompartilhamentoEntidade(EnumCompartilhamento.CAIXA_BANCO))
					.getResultList();

			if (!list.isEmpty()){
				if(list.size() > 1){
					logRet.setStatus(EnumLogRetornoStatus.REGDUPLICADO);
					logRet.setObjeto(list.get(0));
					logRet.setMsg(DadosGlobais.resourceBundle.getString("errorDuplicateRecord"));
				}
				else if(list.size() == 1){
					logRet.setStatus(EnumLogRetornoStatus.SUCESSO);
					logRet.setObjeto(list.get(0));
				}

			}else{
				logRet.setStatus(EnumLogRetornoStatus.ERRO);
				logRet.setMsg(DadosGlobais.resourceBundle.getString("errorSelect"));
			}
		}
		catch (Exception e){
			throw e;
		
		}finally{
			em.close();
		}
		return logRet;
	}

	/**
	 * Metodo para buscar CaixaBanco pelo CODIGO
	 * 
	 * @param codigo
	 * @return caixabanco buscado ou null
	 */
	@SuppressWarnings("unchecked")
	public LogRetorno getById(Integer codigo) {
		
		LogRetorno logRet = new LogRetorno();

		try{
			em = ConnectionHib.emf.createEntityManager();
			
			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);

			List<CaixaBanco> list = em.createNamedQuery("CaixaBancoById")
					.setParameter("codigo", codigo)
					.setParameter("codemp",Util.getCompartilhamentoEntidade(EnumCompartilhamento.CAIXA_BANCO))
					.getResultList();

			if (!list.isEmpty()){
				if(list.size() > 1){
					logRet.setStatus(EnumLogRetornoStatus.REGDUPLICADO);
					logRet.setObjeto(list.get(0));
					logRet.setMsg(DadosGlobais.resourceBundle.getString("errorDuplicateRecord"));
				}
				else if(list.size() == 1){
					logRet.setStatus(EnumLogRetornoStatus.SUCESSO);
					logRet.setObjeto(list.get(0));
				}

			}else{
				logRet.setStatus(EnumLogRetornoStatus.ERRO);
				logRet.setMsg(DadosGlobais.resourceBundle.getString("errorSelect"));
			}
		}
		catch (Exception e){
			throw e;
		
		}finally{
			em.close();
		}
		return logRet;
	}

	public LogRetorno update(Object objeto){
		
		CaixaBanco caixa = (CaixaBanco) objeto;
	
		LogRetorno logRet = new LogRetorno();
		
		try {
			em = ConnectionHib.emf.createEntityManager();
			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);
			em.getTransaction().begin();
			CaixaBancoPK caixaPK = new CaixaBancoPK();
			caixaPK.setCodigo(caixa.getCodigo());
			caixaPK.setCodemp(caixa.getCodemp());
			caixaPK.setCheckDelete(caixa.getCheckDelete());

		    CaixaBanco newSecao = em.find(CaixaBanco.class, caixaPK);
			
			if(newSecao != null && newSecao.getFlagAtivo() == 1){
			
			Date dataServer  = (Date) em.createNativeQuery(ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL").getSingleResult();
			utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));		
			ts_now = Util.dateToLocalDateTime(dataServer);
			
			caixa.setLastMovto(ts_now);
			caixa.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
			caixa.setUtctag(utctag);
			
			em.merge(caixa);
			
			em.getTransaction().commit();
			
			logRet.setStatus(EnumLogRetornoStatus.SUCESSO);
			logRet.setMsg("Sucesso");
			}else{
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
		
		CaixaBanco caixa = (CaixaBanco) objeto;
		
		try {
			
			em = ConnectionHib.emf.createEntityManager();
			
			em.getTransaction().begin();
			
			Date dataServer  = (Date) em.createNativeQuery(ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL").getSingleResult();
			utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));			
			ts_now = Util.dateToLocalDateTime(dataServer);
			
			em.createQuery("UPDATE CaixaBanco u "
					+ "SET u.flagAtivo = "+caixa.getFlagAtivo()+","
					+ "u.lastMovto = :dtAlteracao,"
				    + "u.lastCoduser = "+DadosGlobais.usuarioLogado.getCodigo()+","
				    + "u.utctag = "+utctag+" "
					+ "WHERE u.codigo= :codigo "
					+ "AND u.recordNo = :recordNo "
					+ "AND u.codemp = :codemp ")
			.setParameter("dtAlteracao", ts_now)
			.setParameter("codigo", caixa.getCodigo())
			.setParameter("recordNo", caixa.getRecordNo())
			.setParameter("codemp", caixa.getCodemp()).executeUpdate();
			
			Integer tipoOperacao = null;
			String valorAnterior = null;
			String valorNovo = null;
			if (caixa.getFlagAtivo() == 1) {
				tipoOperacao = EnumTipoOpeAuditoria.REATIVACAO.idTipoOpe;//Reativação
				valorAnterior = DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.logExclusao").toUpperCase();//Inativo
				valorNovo = DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.logAtivacao").toUpperCase();//Ativo
			} else {
				tipoOperacao = EnumTipoOpeAuditoria.EXCLUSAO.idTipoOpe;//Exclusão
				valorAnterior = DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.logAtivacao").toUpperCase();//Ativo
				valorNovo = DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.logExclusao").toUpperCase();//Inativo
			}
			
			//Cria auditoria
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
			auditoria.setTipoRegistro(DadosGlobais.resourceBundle.getString("miCadCaixaBanco").toUpperCase());
			auditoria.setDtMovto(Util.geraDataMovto(ts_now));
			auditoria.setValorAnterior(valorAnterior);
			auditoria.setValorNovo(valorNovo);
			auditoria.setHistorico(caixa.getCodigo() + "-" + caixa.getDescricao());
			auditoria.setDocCodigo(String.valueOf(caixa.getCodigo()));
	
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
	public List<CaixaBanco> filterByColumn (String column, String filter, int action, List<Integer> ativo) {
		 					
		List<CaixaBanco> listFilter = null;
		try {
			em = ConnectionHib.emf.createEntityManager();
			if (action == 1) {
				if (filter.equals("")) {
					listFilter = em
							.createQuery("SELECT r FROM CaixaBanco r WHERE r." + column
									+ " LIKE CONCAT('%', :filter, '%') AND r.codemp IN (:codemp) AND r.flagAtivo IN (:flag)")
							.setParameter("filter", (filter.equals("")) ? "%" : filter)
							.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.CAIXA_BANCO))
							.setParameter("flag", ativo)
							.getResultList();
				} else {

					listFilter = em
							.createQuery("SELECT r FROM CaixaBanco r WHERE r." + column
									+ "= :filter AND r.codemp IN (:codemp) AND r.flagAtivo IN (:flag)")
							.setParameter("filter", Integer.parseInt(filter))
							.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.CAIXA_BANCO))
							.setParameter("flag", ativo)
							.getResultList();

				}

			} else {
				listFilter = em
						.createQuery("SELECT r FROM CaixaBanco r WHERE r." + column
								+ " LIKE CONCAT('%', :filter, '%') AND r.codemp IN (:codemp) AND r.flagAtivo IN (:flag)")
						.setParameter("filter", (filter.equals("")) ? "%" : filter)
						.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.CAIXA_BANCO))
						.setParameter("flag", ativo)
						.getResultList();
			}
		} catch (Exception e) {
			throw e;	
		} finally {
			em.close();
		}

		return listFilter;
	}
	
}
