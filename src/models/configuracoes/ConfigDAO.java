package models.configuracoes;

import java.math.BigInteger;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.persistence.EntityManager;
import org.hibernate.HibernateException;
import application.DadosGlobais;
import connect.ConnectionHib;
import tools.utils.Util;
import interfaces.InterfaceDAO;
import models.configuracoes.Auditoria;
import models.configuracoes.SequencialDAO;
import tools.enums.EnumCompartilhamento;
import tools.enums.EnumLogRetornoStatus;
import tools.enums.EnumTipoOpeAuditoria;
import tools.utils.LogRetorno;

public class ConfigDAO implements InterfaceDAO{

	private LocalDateTime ts_now; 
	private BigInteger utctag;
	private EntityManager em = null;

	public LogRetorno insert(Object objeto){

//		Config config = (Config) objeto;
		LogRetorno logRet = new LogRetorno();
//		SequencialDAO seqDAO = new SequencialDAO();
//		try {
//			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);
//			
//			em = ConnectionHib.emf.createEntityManager();
//			em.getTransaction().begin();
//			
//			Date dataServer  = (Date) em.createNativeQuery(ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL").getSingleResult();
//			
//			em.createQuery("UPDATE Sequencial s SET s.cpConfig = s.cpConfig+1 WHERE s.codemp= :codemp AND s.checkDelete = :checkDelete")
//			.setParameter("codemp", DadosGlobais.empresaLogada.getCodigo())
//			.setParameter("checkDelete", DadosGlobais.empresaLogada.getCheckDelete())
//			.executeUpdate();
//			
//			utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));		
//			
//			ts_now = Util.dateToLocalDateTime(dataServer);
//			
//			Integer codigo = seqDAO.getLastInsertCodigo(DadosGlobais.empresaLogada.getCodigo(), "cpConfig") + 1;
//			
//			config.setCodemp(DadosGlobais.empresaLogada.getCodigo());
//			config.setCodigo(codigo);
//			config.setUtctag(utctag);
//			config.setCheckDelete(Util.checkDeleteCreate());
//			config.setFlagAtivo(1);
//			config.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
//	        config.setLastMovto(ts_now);
//			
//			em.persist(config);
//			
//			em.getTransaction().commit();
//			
//			logRet.setStatus(EnumLogRetornoStatus.SUCESSO);
//			
//			logRet.setMsg(EnumLogRetornoStatus.SUCESSO.name());
//			
//			logRet.setLastRecord(codigo);
//			
//			logRet.setObjeto(config);
//			
//		} catch (Exception e) {
//			em.getTransaction().rollback();
//			throw e;
//		} finally {
//			em.close();
//		}
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
	public List<Config> getList(List<Integer> flagAtivo) {
		List<Config> list = null;
		try {	
		em = ConnectionHib.emf.createEntityManager();		
		
		list = em.createNamedQuery("ConfigAll")
					.setParameter("flag", flagAtivo)
					.setParameter("codemp", DadosGlobais.empresaLogada.getCodigo())
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
//
//		try{
//			em = ConnectionHib.emf.createEntityManager();
//			
//			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);
//
//			List<Config> list = em.createNamedQuery("ConfigLast")
//					.setMaxResults(1)
//					.setParameter("codemp",Util.getCompartilhamentoEntidade(EnumCompartilhamento.CONFIGS))
//					.getResultList();
//
//			if (!list.isEmpty()){
//				if(list.size() > 1){
//					logRet.setStatus(EnumLogRetornoStatus.REGDUPLICADO);
//					logRet.setObjeto(list.get(0));
//					logRet.setMsg(DadosGlobais.resourceBundle.getString("errorDuplicateRecord"));
//				}
//				else if(list.size() == 1){
//					logRet.setStatus(EnumLogRetornoStatus.SUCESSO);
//					logRet.setObjeto(list.get(0));
//				}
//
//			}else{
//				logRet.setStatus(EnumLogRetornoStatus.ERRO);
//				logRet.setMsg(DadosGlobais.resourceBundle.getString("errorSelect"));
//			}
//		}
//		catch (Exception e){
//			throw e;
//		
//		}finally{
//			em.close();
//		}
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

			List<Config> list = em.createNamedQuery("ConfigById")
					.setParameter("codigo", codigo)
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
		
		Config config = (Config) objeto;
	
		LogRetorno logRet = new LogRetorno();
		
		try {
			em = ConnectionHib.emf.createEntityManager();
			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);
			em.getTransaction().begin();
			
			EmpresaPK empPK = new EmpresaPK();
			empPK.setCodigo(config.getEmpresa().getCodigo());
			empPK.setCheckDelete(config.getEmpresa().getCheckDelete());			

		    Config newConfig = em.find(Config.class, empPK);
			
			if(newConfig != null && newConfig.getFlagAtivo() == 1){
			
			Date dataServer  = (Date) em.createNativeQuery(ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL").getSingleResult();
			utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));		
			ts_now = Util.dateToLocalDateTime(dataServer);
			
			config.setLastMovto(ts_now);
			config.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
			config.setUtctag(utctag);
			System.out.println("certificad :::: "+config.getGerCertificadoDigital());
			em.merge(config);
			
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
//		
//		Config config = (Config) objeto;
//		
//		try {
//			
//			em = ConnectionHib.emf.createEntityManager();
//			
//			em.getTransaction().begin();
//			
//			Date dataServer  = (Date) em.createNativeQuery(ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL").getSingleResult();
//			utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));			
//			ts_now = Util.dateToLocalDateTime(dataServer);
//			
//			em.createQuery("UPDATE Config u "
//					+ "SET u.flagAtivo = "+config.getFlagAtivo()+","
//					+ "u.lastMovto = :dtAlteracao,"
//				    + "u.lastCoduser = "+DadosGlobais.usuarioLogado.getCodigo()+","
//				    + "u.utctag = "+utctag+" "
//					+ "WHERE u.codigo= :codigo "
//					+ "AND u.recordNo = :recordNo "
//					+ "AND u.codemp = :codemp ")
//			.setParameter("dtAlteracao", ts_now)
//			.setParameter("codigo", config.getCodigo())
//			.setParameter("recordNo", config.getRecordNo())
//			.setParameter("codemp", config.getCodemp()).executeUpdate();
//			
//			Integer tipoOperacao = null;
//			String valorAnterior = null;
//			String valorNovo = null;
//			if (config.getFlagAtivo() == 1) {
//				tipoOperacao = EnumTipoOpeAuditoria.REATIVACAO.idTipoOpe;//Reativação
//				valorAnterior = DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.logExclusao").toUpperCase();//Inativo
//				valorNovo = DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.logAtivacao").toUpperCase();//Ativo
//			} else {
//				tipoOperacao = EnumTipoOpeAuditoria.EXCLUSAO.idTipoOpe;//Exclusão
//				valorAnterior = DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.logAtivacao").toUpperCase();//Ativo
//				valorNovo = DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.logExclusao").toUpperCase();//Inativo
//			}
//			
//			//Cria auditoria
//			Auditoria auditoria = new Auditoria();
//			auditoria.setCheckDelete(Util.checkDeleteCreate());
//			auditoria.setLastMovto(ts_now);
//			auditoria.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
//			auditoria.setUtctag(utctag);
//			auditoria.setFlagAtivo(1);
//			auditoria.setCodemp(DadosGlobais.empresaLogada.getCodigo());
//			auditoria.setCodUsuario(DadosGlobais.usuarioLogado.getCodigo());
//			auditoria.setNomeUsuario(DadosGlobais.usuarioLogado.getNome());
//			auditoria.setSistema("EPTUS");
//			auditoria.setTipoOperacao(tipoOperacao);
//			auditoria.setTipoRegistro(DadosGlobais.resourceBundle.getString("miCadConfig").toUpperCase());
//			auditoria.setDtMovto(Util.geraDataMovto(ts_now));
//			auditoria.setValorAnterior(valorAnterior);
//			auditoria.setValorNovo(valorNovo);
//			auditoria.setHistorico(config.getCodigo() + "-" + config.getDescricao());
//			auditoria.setDocCodigo(String.valueOf(config.getCodigo()));
//	
//			em.persist(auditoria);
//			em.getTransaction().commit();
//			
//		} catch (Exception e) {
//			em.getTransaction().rollback();
//			throw e;
//		} finally {
//			em.close();
//		}

	}

	@SuppressWarnings("unchecked")
	public List<Config> filterByColumn (String column, String filter, int action, List<Integer> ativo) {
		 					
		List<Config> listFilter = null;
//		try {
//			em = ConnectionHib.emf.createEntityManager();
//			if (action == 1) {
//				if (filter.equals("")) {
//					listFilter = em
//							.createQuery("SELECT d FROM Config d WHERE d." + column
//									+ " LIKE CONCAT('%', :filter, '%') AND d.id.codemp IN (:codemp) AND d.flagAtivo IN (:flag)")
//							.setParameter("filter", (filter.equals("")) ? "%" : filter)
//							.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.CONFIGS))
//							.setParameter("flag", ativo)
//							.getResultList();
//				} else {
//
//					listFilter = em
//							.createQuery("SELECT d FROM Config d WHERE d." + column
//									+ "= :filter AND d.id.codemp IN (:codemp) AND d.flagAtivo IN (:flag)")
//							.setParameter("filter", Integer.parseInt(filter))
//							.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.CONFIGS))
//							.setParameter("flag", ativo)
//							.getResultList();
//
//				}
//
//			} else {
//				listFilter = em
//						.createQuery("SELECT d FROM Config d WHERE d." + column
//								+ " LIKE CONCAT('%', :filter, '%') AND d.id.codemp IN (:codemp) AND d.flagAtivo IN (:flag)")
//						.setParameter("filter", (filter.equals("")) ? "%" : filter)
//						.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.CONFIGS))
//						.setParameter("flag", ativo)
//						.getResultList();
//			}
//		} catch (Exception e) {
//			throw e;	
//		} finally {
//			em.close();
//		}

		return listFilter;
	}
	
}

