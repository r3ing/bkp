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
import tools.enums.EnumCompartilhamento;
import tools.enums.EnumLogRetornoStatus;
import tools.enums.EnumTipoOpeAuditoria;
import tools.utils.LogRetorno;
import tools.utils.Util;

public class OperacaoSaidaDAO implements InterfaceDAO{

	private LocalDateTime ts_now; 
	private BigInteger utctag;
	private EntityManager em = null;

	public LogRetorno insert(Object objeto){

		OperacaoSaida operacaoSaida = (OperacaoSaida) objeto;
		LogRetorno logRet = new LogRetorno();
		SequencialDAO seqDAO = new SequencialDAO();
		try {
			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);
			
			em = ConnectionHib.emf.createEntityManager();
			em.getTransaction().begin();
			
			Date dataServer  = (Date) em.createNativeQuery(ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL").getSingleResult();
			
			em.createQuery("UPDATE Sequencial s SET s.cnfOperacaoSaida = s.cnfOperacaoSaida+1 WHERE s.codemp= :codemp AND s.checkDelete = :checkDelete")
			.setParameter("codemp", DadosGlobais.empresaLogada.getCodigo())
			.setParameter("checkDelete", DadosGlobais.empresaLogada.getCheckDelete())
			.executeUpdate();
			
			utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));		
			
			ts_now = Util.dateToLocalDateTime(dataServer);
			
			Integer codigo = seqDAO.getLastInsertCodigo(DadosGlobais.empresaLogada.getCodigo(), "cnfOperacaoSaida") + 1;
			
			operacaoSaida.setCodemp(DadosGlobais.empresaLogada.getCodigo());
			operacaoSaida.setCodigo(codigo);
			operacaoSaida.setUtctag(utctag);
			operacaoSaida.setCheckDelete(Util.checkDeleteCreate());
			operacaoSaida.setFlagAtivo(1);
			operacaoSaida.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
			operacaoSaida.setLastMovto(ts_now);
			
			em.persist(operacaoSaida);
			
			em.getTransaction().commit();
			
			logRet.setStatus(EnumLogRetornoStatus.SUCESSO);
			
			logRet.setMsg(EnumLogRetornoStatus.SUCESSO.name());
			
			logRet.setLastRecord(codigo);
			
			logRet.setObjeto(operacaoSaida);
			
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
	 * Metodo para listar tudos os GRUPOS, dependendo do valor do
	 * flag_Ativo e compartilhamento
	 * 
	 * @param flagAtivo
	 * @return
	 */
	public List<OperacaoSaida> getList(List<Integer> flagAtivo) {
		List<OperacaoSaida> list = null;
		try {	
		em = ConnectionHib.emf.createEntityManager();		
		
		list = em.createNamedQuery("OperacaoSaidaAll")
					.setParameter("flag", flagAtivo)
					.setParameter("codemp",Util.getCompartilhamentoEntidade(EnumCompartilhamento.OPERACOES_DE_SAIDA))
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

			List<OperacaoSaida> list = em.createNamedQuery("OperacaoSaidaLast")
					.setMaxResults(1)
					.setParameter("codemp",Util.getCompartilhamentoEntidade(EnumCompartilhamento.OPERACOES_DE_SAIDA))
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
	 * Metodo para buscar departamento pelo CODIGO
	 * 
	 * @param codigo
	 * @return departamento buscado ou null
	 */
	@SuppressWarnings("unchecked")
	public LogRetorno getById(Integer codigo) {
		
		LogRetorno logRet = new LogRetorno();

		try{
			em = ConnectionHib.emf.createEntityManager();
			
			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);

			List<OperacaoSaida> list = em.createNamedQuery("OperacaoSaidaById")
					.setParameter("codigo", codigo)
					.setParameter("codemp",Util.getCompartilhamentoEntidade(EnumCompartilhamento.OPERACOES_DE_SAIDA))
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
		
		OperacaoSaida operacaoSaida = (OperacaoSaida) objeto;
	
		LogRetorno logRet = new LogRetorno();
		
		try {
			em = ConnectionHib.emf.createEntityManager();
			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);
			em.getTransaction().begin();
			OperacaoSaidaPK operacaoSaidaPK = new OperacaoSaidaPK();
			operacaoSaidaPK.setCodigo(operacaoSaida.getCodigo());
			//OperacaoSaidaPK.setCodemp(operacaoSaida.getCodemp());
			operacaoSaidaPK.setCheckDelete(operacaoSaida.getCheckDelete());

		    OperacaoSaida newOperacaoSaida = em.find(OperacaoSaida.class, operacaoSaidaPK);
			
			if(newOperacaoSaida != null && newOperacaoSaida.getFlagAtivo() == 1){
			
			Date dataServer  = (Date) em.createNativeQuery(ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL").getSingleResult();
			utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));		
			ts_now = Util.dateToLocalDateTime(dataServer);
			
			operacaoSaida.setLastMovto(ts_now);
			operacaoSaida.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
			operacaoSaida.setUtctag(utctag);
			
			em.merge(operacaoSaida);
			
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
		
		OperacaoSaida operacaoSaida = (OperacaoSaida) objeto;
		
		try {
			
			em = ConnectionHib.emf.createEntityManager();
			
			em.getTransaction().begin();
			
			Date dataServer  = (Date) em.createNativeQuery(ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL").getSingleResult();
			utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));			
			ts_now = Util.dateToLocalDateTime(dataServer);
			
			em.createQuery("UPDATE OperacaoSaida u "
					+ "SET u.flagAtivo = "+operacaoSaida.getFlagAtivo()+","
					+ "u.lastMovto = :dtAlteracao,"
				    + "u.lastCoduser = "+DadosGlobais.usuarioLogado.getCodigo()+","
				    + "u.utctag = "+utctag+" "
					+ "WHERE u.codigo= :codigo "
					+ "AND u.recordNo = :recordNo "
					+ "AND u.codemp = :codemp ")
			.setParameter("dtAlteracao", ts_now)
			.setParameter("codigo", operacaoSaida.getCodigo())
			.setParameter("recordNo", operacaoSaida.getRecordNo())
			.setParameter("codemp", operacaoSaida.getCodemp()).executeUpdate();
			
			Integer tipoOperacao = null;
			String valorAnterior = null;
			String valorNovo = null;
			if (operacaoSaida.getFlagAtivo() == 1) {
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
			auditoria.setTipoRegistro(DadosGlobais.resourceBundle.getString("miCadOperacaoSaida").toUpperCase());
			auditoria.setDtMovto(Util.geraDataMovto(ts_now));
			auditoria.setValorAnterior(valorAnterior);
			auditoria.setValorNovo(valorNovo);
			auditoria.setHistorico(operacaoSaida.getCodigo() + "-" + operacaoSaida.getDescricao());
			auditoria.setDocCodigo(String.valueOf(operacaoSaida.getCodigo()));
	
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
	public List<OperacaoSaida> filterByColumn (String column, String filter, int action, List<Integer> ativo) {
				 					
		List<OperacaoSaida> listFilter = null;
		try {
			em = ConnectionHib.emf.createEntityManager();
			if (action == 1) {
				if (filter.equals("")) {
					listFilter = em
							.createQuery("SELECT d FROM OperacaoSaida d WHERE d." + column
									+ " LIKE CONCAT('%', :filter, '%') AND d.codemp IN (:codemp) AND d.flagAtivo IN (:flag)")
							.setParameter("filter", (filter.equals("")) ? "%" : filter)
							.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.OPERACOES_DE_SAIDA))
							.setParameter("flag", ativo)
							.getResultList();
				} else {

					listFilter = em
							.createQuery("SELECT d FROM OperacaoSaida d WHERE d." + column
									+ "= :filter AND d.codemp IN (:codemp) AND d.flagAtivo IN (:flag)")
							.setParameter("filter", Integer.parseInt(filter))
							.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.OPERACOES_DE_SAIDA))
							.setParameter("flag", ativo)
							.getResultList();

				}

			} else {
				listFilter = em
						.createQuery("SELECT d FROM OperacaoSaida d WHERE d." + column
								+ " LIKE CONCAT('%', :filter, '%') AND d.codemp IN (:codemp) AND d.flagAtivo IN (:flag)")
						.setParameter("filter", (filter.equals("")) ? "%" : filter)
						.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.OPERACOES_DE_SAIDA))
						.setParameter("flag", ativo)
						.getResultList();
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
