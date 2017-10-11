package models.configuracoes;

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
import models.recursosHumanos.Funcionario;
import tools.enums.EnumCompartilhamento;
import tools.enums.EnumLogRetornoStatus;
import tools.enums.EnumTipoOpeAuditoria;
import tools.utils.LogRetorno;
import tools.utils.Util;

public class OperacaoFinanceiroDAO implements InterfaceDAO{

	private LocalDateTime ts_now; 
	private BigInteger utctag;
	private EntityManager em = null;

	public LogRetorno insert(Object objeto){

		OperacaoFinanceiro operacaoFinanceiro = (OperacaoFinanceiro) objeto;
		LogRetorno logRet = new LogRetorno();
		SequencialDAO seqDAO = new SequencialDAO();
		try {
			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);
			
			em = ConnectionHib.emf.createEntityManager();
			em.getTransaction().begin();
			
			Date dataServer  = (Date) em.createNativeQuery(ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL").getSingleResult();
			
			em.createQuery("UPDATE Sequencial s SET s.cnfOperacaoFinaceiro = s.cnfOperacaoFinaceiro+1 WHERE s.codemp= :codemp AND s.checkDelete = :checkDelete")
			.setParameter("codemp", DadosGlobais.empresaLogada.getCodigo())
			.setParameter("checkDelete", DadosGlobais.empresaLogada.getCheckDelete())
			.executeUpdate();
			
			utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));		
			
			ts_now = Util.dateToLocalDateTime(dataServer);
			
			Integer codigo = seqDAO.getLastInsertCodigo(DadosGlobais.empresaLogada.getCodigo(), "cnfOperacaoFinaceiro") + 1;
			
			operacaoFinanceiro.setCodemp(DadosGlobais.empresaLogada.getCodigo());
			operacaoFinanceiro.setCodigo(codigo);
			operacaoFinanceiro.setUtctag(utctag);
			operacaoFinanceiro.setCheckDelete(Util.checkDeleteCreate());
			operacaoFinanceiro.setFlagAtivo(1);
			operacaoFinanceiro.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
	        operacaoFinanceiro.setLastMovto(ts_now);
			
			em.persist(operacaoFinanceiro);
			
			em.getTransaction().commit();
			
			logRet.setStatus(EnumLogRetornoStatus.SUCESSO);
			
			logRet.setMsg(EnumLogRetornoStatus.SUCESSO.name());
			
			logRet.setLastRecord(codigo);
			
			logRet.setObjeto(operacaoFinanceiro);
			
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
	 * Metodo para listar tudos os GRUPOS, dependendo do valor do
	 * flag_Ativo e compartilhamento
	 * 
	 * @param flagAtivo
	 * @return
	 */
	public List<OperacaoFinanceiro> getList(List<Integer> flagAtivo) {
		List<OperacaoFinanceiro> list = null;
		try {	
		em = ConnectionHib.emf.createEntityManager();		
		
		list = em.createNamedQuery("OperacaoFinanceiroAll")
					.setParameter("flag", flagAtivo)
					.setParameter("codemp",Util.getCompartilhamentoEntidade(EnumCompartilhamento.OPERACOES_FINANCEIRO))
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

			List<OperacaoFinanceiro> list = em.createNamedQuery("OperacaoFinanceiroLast")
					.setMaxResults(1)
					.setParameter("codemp",Util.getCompartilhamentoEntidade(EnumCompartilhamento.OPERACOES_FINANCEIRO))
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
	 * Metodo para buscar Operacao Finaceiro pelo CODIGO
	 * 
	 * @param codigo
	 * @return Operacao Financeiro buscado ou null
	 */
	@SuppressWarnings("unchecked")
	public LogRetorno getById(Integer codigo) {
		
		LogRetorno logRet = new LogRetorno();

		try{
			em = ConnectionHib.emf.createEntityManager();
			
			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);

			List<OperacaoFinanceiro> list = em.createNamedQuery("OperacaoFinanceiroById")
					.setParameter("codigo", codigo)
					.setParameter("codemp",Util.getCompartilhamentoEntidade(EnumCompartilhamento.OPERACOES_FINANCEIRO))
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
	 * Metodo para buscar Operaca Finaceiro filtrado por tipo Operacao ContasReceber pelo CODIGO
	 * 
	 * @param codigo
	 * @return Operacao Financeiro buscado ou null
	 */
	@SuppressWarnings("unchecked")
	public LogRetorno getOperacoesById(Integer codigo) {
		
		LogRetorno logRet = new LogRetorno();

		try{
			em = ConnectionHib.emf.createEntityManager();
			
			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);

			List<OperacaoFinanceiro> list = em.createNamedQuery("OperacaoContasReceberById")
					.setParameter("codigo", codigo)
					.setParameter("codemp",Util.getCompartilhamentoEntidade(EnumCompartilhamento.OPERACOES_FINANCEIRO))
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
		
		OperacaoFinanceiro operacaoFinanceiro = (OperacaoFinanceiro) objeto;
	
		LogRetorno logRet = new LogRetorno();
		
		try {
			em = ConnectionHib.emf.createEntityManager();
			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);
			em.getTransaction().begin();
			OperacaoFinanceiroPK operacaoFinanceiroPK = new OperacaoFinanceiroPK();
			operacaoFinanceiroPK.setCodigo(operacaoFinanceiro.getCodigo());
//			operacaoFinanceiroPK.setCodemp(operacaoFinanceiro.getCodemp());
			operacaoFinanceiroPK.setCheckDelete(operacaoFinanceiro.getCheckDelete());

		    OperacaoFinanceiro newOperacaoFinanceiro = em.find(OperacaoFinanceiro.class, operacaoFinanceiroPK);
			
			if(newOperacaoFinanceiro != null && newOperacaoFinanceiro.getFlagAtivo() == 1){
			
			Date dataServer  = (Date) em.createNativeQuery(ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL").getSingleResult();
			utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));		
			ts_now = Util.dateToLocalDateTime(dataServer);
			
			operacaoFinanceiro.setLastMovto(ts_now);
			operacaoFinanceiro.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
			operacaoFinanceiro.setUtctag(utctag);
			
			em.merge(operacaoFinanceiro);
			
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
		
		OperacaoFinanceiro operacaoFinanceiro = (OperacaoFinanceiro) objeto;
		
		try {
			
			em = ConnectionHib.emf.createEntityManager();
			
			em.getTransaction().begin();
			
			Date dataServer  = (Date) em.createNativeQuery(ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL").getSingleResult();
			utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));			
			ts_now = Util.dateToLocalDateTime(dataServer);
			
			em.createQuery("UPDATE OperacaoFinanceiro u "
					+ "SET u.flagAtivo = "+operacaoFinanceiro.getFlagAtivo()+","
					+ "u.lastMovto = :dtAlteracao,"
				    + "u.lastCoduser = "+DadosGlobais.usuarioLogado.getCodigo()+","
				    + "u.utctag = "+utctag+" "
					+ "WHERE u.codigo= :codigo "
					+ "AND u.recordNo = :recordNo "
					+ "AND u.codemp = :codemp ")
			.setParameter("dtAlteracao", ts_now)
			.setParameter("codigo", operacaoFinanceiro.getCodigo())
			.setParameter("recordNo", operacaoFinanceiro.getRecordNo())
			.setParameter("codemp", operacaoFinanceiro.getCodemp()).executeUpdate();
			
			Integer tipoOperacao = null;
			String valorAnterior = null;
			String valorNovo = null;
			if (operacaoFinanceiro.getFlagAtivo() == 1) {
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
			auditoria.setTipoRegistro(DadosGlobais.resourceBundle.getString("miCadOperacaoFinanceiro").toUpperCase());
			auditoria.setDtMovto(Util.geraDataMovto(ts_now));
			auditoria.setValorAnterior(valorAnterior);
			auditoria.setValorNovo(valorNovo);
			auditoria.setHistorico(operacaoFinanceiro.getCodigo() + "-" + operacaoFinanceiro.getDescricao());
			auditoria.setDocCodigo(String.valueOf(operacaoFinanceiro.getCodigo()));
	
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
	public List<OperacaoFinanceiro> filterByColumn (String column, String filter, int action, List<Integer> ativo) {
		 					
		List<OperacaoFinanceiro> listFilter = null;
		try {
			em = ConnectionHib.emf.createEntityManager();
			if (action == 1) {
				if (filter.equals("")) {
					listFilter = em
							.createQuery("SELECT c FROM OperacaoFinanceiro c WHERE c." + column
									+ " LIKE CONCAT('%', :filter, '%') AND c.codemp IN (:codemp) AND c.flagAtivo IN (:flag)")
							.setParameter("filter", (filter.equals("")) ? "%" : filter)
							.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.OPERACOES_FINANCEIRO))
							.setParameter("flag", ativo)
							.getResultList();
				} else {

					listFilter = em
							.createQuery("SELECT c FROM OperacaoFinanceiro c WHERE c." + column
									+ "= :filter AND c.codemp IN (:codemp) AND c.flagAtivo IN (:flag)")
							.setParameter("filter", Integer.parseInt(filter))
							.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.OPERACOES_FINANCEIRO))
							.setParameter("flag", ativo)
							.getResultList();

				}

			} else {
				listFilter = em
						.createQuery("SELECT c FROM OperacaoFinanceiro c WHERE c." + column
								+ " LIKE CONCAT('%', :filter, '%') AND c.codemp IN (:codemp) AND c.flagAtivo IN (:flag)")
						.setParameter("filter", (filter.equals("")) ? "%" : filter)
						.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.OPERACOES_FINANCEIRO))
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
	
	@SuppressWarnings("unchecked")
	public List<OperacaoFinanceiro> filterContasReceberByColumn (String column, String filter, int action, List<Integer> ativo) {
		 					
		List<OperacaoFinanceiro> listFilter = null;
		try {
			em = ConnectionHib.emf.createEntityManager();
			if (action == 1) {
				if (filter.equals("")) {
					listFilter = em
							.createQuery("SELECT o FROM OperacaoFinanceiro o WHERE o." + column
									+ " LIKE CONCAT('%', :filter, '%') AND f.codemp IN (:codemp) AND o.tipoOperacao = 0 AND f.flagAtivo IN (:flag)")
							.setParameter("filter", (filter.equals("")) ? "%" : filter)
							.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.OPERACOES_FINANCEIRO))
							.setParameter("flag", ativo)
							.getResultList();
				} else {

					listFilter = em
							.createQuery("SELECT o FROM OperacaoFinanceiro o WHERE o." + column
									+ "= :filter AND o.codemp IN (:codemp) AND o.tipoOperacao = 0 AND o.flagAtivo IN (:flag)")
							.setParameter("filter", Integer.parseInt(filter))
							.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.OPERACOES_FINANCEIRO))
							.setParameter("flag", ativo)
							.getResultList();

				}

			} else {
				listFilter = em
						.createQuery("SELECT o FROM OperacaoFinanceiro o WHERE o." + column
								+ " LIKE CONCAT('%', :filter, '%') AND o.codemp IN (:codemp) AND o.tipoOperacao = 0 AND o.flagAtivo IN (:flag)")
						.setParameter("filter", (filter.equals("")) ? "%" : filter)
						.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.OPERACOES_FINANCEIRO))
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
