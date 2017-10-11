package models.configuracoes;  


import java.lang.reflect.Method;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.apache.axis2.databinding.types.soapencoding.DateTime;
import org.controlsfx.control.textfield.CustomTextField;

import application.DadosGlobais;
import connect.ConnectionHib;
import interfaces.InterfaceDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.compras.ItemCodBar;
import models.configuracoes.Auditoria;
import models.configuracoes.SequencialDAO;
import tools.enums.EnumCompartilhamento;
import tools.enums.EnumLogRetornoStatus;
import tools.enums.EnumTipoOpeAuditoria;
import tools.utils.LogRetorno;
import tools.utils.TabelasModel;
import tools.utils.Util;


public class TabelaAuxiliarCfopDAO
{

	private LocalDateTime ts_now; 
	private BigInteger utctag;
	private EntityManager em = null;

	public LogRetorno insert(Object objeto, String cfop)
	{

		TabelaAuxiliarCfop taCfop = (TabelaAuxiliarCfop) objeto;
		LogRetorno logRet = new LogRetorno();
		
		try 
		{
			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);
			
			em = ConnectionHib.emf.createEntityManager();
			em.getTransaction().begin();
			
			Date dataServer  = (Date) em.createNativeQuery(ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL").getSingleResult();
				
			utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));		
			
			ts_now = Util.dateToLocalDateTime(dataServer);

			taCfop.setCfop(cfop);
			
			taCfop.setCodemp(DadosGlobais.empresaLogada.getCodigo());
			taCfop.setUtctag(utctag);
			taCfop.setCheckDelete(Util.checkDeleteCreate());
			taCfop.setFlagAtivo(1);
			taCfop.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
			taCfop.setVersao(1);
			taCfop.setLastMovto(ts_now);	    

  			em.persist(taCfop);
			
			em.getTransaction().commit();
			
			logRet.setStatus(EnumLogRetornoStatus.SUCESSO);
			
			logRet.setMsg(EnumLogRetornoStatus.SUCESSO.name());
			
			logRet.setLastRecord(Integer.parseInt(taCfop.getCfop()));
			
			logRet.setObjeto(taCfop);
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			em.getTransaction().rollback();			
			throw e;
		}
		finally 
		{			
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
	public List<TabelaAuxiliarCfop> getList(List<Integer> flagAtivo) 
	{
		List<TabelaAuxiliarCfop> list = null;
		try {	
		em = ConnectionHib.emf.createEntityManager();		
		
		list = em.createNamedQuery("TabelaAuxiliarCfopAll")
					.setParameter("flag", flagAtivo)
					.setParameter("codemp",Util.getCompartilhamentoEntidade(EnumCompartilhamento.LAYOUT_ARQUIVO_CAB))
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

			List<TabelaAuxiliarCfop> list = em.createNamedQuery("TabelaAuxiliarCfopLast")
					.setMaxResults(1)
					.setParameter("codemp",Util.getCompartilhamentoEntidade(EnumCompartilhamento.LAYOUT_ARQUIVO_CAB))
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
	public LogRetorno getById(String codigo) 
	{
		
		LogRetorno logRet = new LogRetorno();

		try
		{
			em = ConnectionHib.emf.createEntityManager();
			
			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);

			List<TabelaAuxiliarCfop> list = em.createNamedQuery("TabelaAuxiliarCfopById")
					.setParameter("cfop", codigo)
					.setParameter("codemp",Util.getCompartilhamentoEntidade(EnumCompartilhamento.LAYOUT_ARQUIVO_CAB))
					.getResultList();

			if (!list.isEmpty())
			{
				if(list.size() > 1)
				{
					logRet.setStatus(EnumLogRetornoStatus.REGDUPLICADO);
					logRet.setObjeto(list.get(0));
					logRet.setMsg(DadosGlobais.resourceBundle.getString("errorDuplicateRecord"));
				}
				else if(list.size() == 1)
				{
					logRet.setStatus(EnumLogRetornoStatus.SUCESSO);
					logRet.setObjeto(list.get(0));
				}

			}else
			{
				logRet.setStatus(EnumLogRetornoStatus.ERRO);
				logRet.setMsg(DadosGlobais.resourceBundle.getString("errorSelect"));
			}
		}
		catch (Exception e)
		{     
			  e.printStackTrace();
			  throw e;
		
		}finally
		{
			em.close();
		}
		return logRet;
	}

	public LogRetorno update(Object objeto,  String cfop)
	{		
		   TabelaAuxiliarCfop taCfop = (TabelaAuxiliarCfop) objeto;
	
		   LogRetorno logRet = new LogRetorno();
		
		   try 
		   {
				em = ConnectionHib.emf.createEntityManager();
				logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);
				
				em.getTransaction().begin();							
				
				/*em.createQuery("UPDATE TabelaAuxiliarCfop t SET t.cfop = :cfop, t.descricao = :descricao WHERE t.codemp = :codemp AND t.checkDelete = :checkDelete")
				.setParameter("cfop", cfop)
				.setParameter("descricao", taCfop.getDescricao())
				.setParameter("codemp", taCfop.getCodemp())
				.setParameter("checkDelete", taCfop.getCheckDelete())
				.executeUpdate();*/	
				
				Date dataServer  = (Date) em.createNativeQuery(ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL").getSingleResult();				
				utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));
				ts_now = Util.dateToLocalDateTime(dataServer);

				taCfop.setCfop(cfop);
				
				taCfop.setCodemp(DadosGlobais.empresaLogada.getCodigo());
				taCfop.setUtctag(utctag);
				taCfop.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
				taCfop.setLastMovto(ts_now);	
				
				em.merge(taCfop);			
				
				em.getTransaction().commit();
				
				logRet.setStatus(EnumLogRetornoStatus.SUCESSO);
				logRet.setMsg("Sucesso");	
				
		   }
		   catch ( Exception e ) 
		   {				
			       logRet.setStatus(EnumLogRetornoStatus.ERRO);
				   logRet.setMsg(DadosGlobais.resourceBundle.getString("errorUpdate"));
				
				   e.printStackTrace();
				   em.getTransaction().rollback();	
				   
				   throw e;
		   }
		   finally 
		   {
				em.close();
		   }
			
		   return logRet;
		   
	}

	
	public void delete(Object objeto) 
	{		
		TabelaAuxiliarCfop taCfop = (TabelaAuxiliarCfop) objeto;
		
		try 
		{
			
			em = ConnectionHib.emf.createEntityManager();
			
			em.getTransaction().begin();
			
			Date dataServer  = (Date) em.createNativeQuery(ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL").getSingleResult();
			utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));			
			ts_now = Util.dateToLocalDateTime(dataServer);
			
			em.createQuery("UPDATE TabelaAuxiliarCfop u "
					+ "SET u.flagAtivo = "+taCfop.getFlagAtivo()+","
					+ "u.lastMovto = :dtAlteracao,"
				    + "u.lastCoduser = "+DadosGlobais.usuarioLogado.getCodigo()+","
				    + "u.utctag = "+utctag+" "
					+ "WHERE u.cfop= :cfop "
					+ "AND u.checkDelete = :checkDelete "
					+ "AND u.codemp = :codemp ")
			.setParameter("dtAlteracao", ts_now)
			.setParameter("cfop", taCfop.getCfop())
			.setParameter("checkDelete", taCfop.getCheckDelete())
			.setParameter("codemp", taCfop.getCodemp()).executeUpdate();
			
			Integer tipoOperacao = null;
			String valorAnterior = null;
			String valorNovo = null;
			if (taCfop.getFlagAtivo() == 1) 
			{
				tipoOperacao = EnumTipoOpeAuditoria.REATIVACAO.idTipoOpe;//Reativação
				valorAnterior = DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.logExclusao").toUpperCase();//Inativo
				valorNovo = DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.logAtivacao").toUpperCase();//Ativo
			} 
			else 
			{
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
			auditoria.setTipoRegistro(DadosGlobais.resourceBundle.getString("miCadGrupo").toUpperCase());
			auditoria.setDtMovto(Util.geraDataMovto(ts_now));
			auditoria.setValorAnterior(valorAnterior);
			auditoria.setValorNovo(valorNovo);
			auditoria.setHistorico(taCfop.getCfop() + "-" + taCfop.getDescricao());
			auditoria.setDocCodigo(String.valueOf(taCfop.getCfop()));
	
			em.persist(auditoria);
			em.getTransaction().commit();
			
		} 
		catch (Exception e) 
		{
			em.getTransaction().rollback();
			throw e;
		} 
		finally 
		{
			em.close();
		}

	}

	@SuppressWarnings("unchecked")
	public List<TabelaAuxiliarCfop> filterByColumn (String column, String filter, int action, List<Integer> ativo) 
	{		 					
		List<TabelaAuxiliarCfop> listFilter = null;
		try {
			em = ConnectionHib.emf.createEntityManager();
			if (action == 1) {
				if (filter.equals("")) {
					listFilter = em
							.createQuery("SELECT d FROM TabelaAuxiliarCfop d WHERE d." + column
									+ " LIKE CONCAT('%', :filter, '%') AND d.codemp IN (:codemp) AND d.flagAtivo IN (:flag)")
							.setParameter("filter", (filter.equals("")) ? "%" : filter)
							.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.LAYOUT_ARQUIVO_CAB))
							.setParameter("flag", ativo)
							.getResultList();
				} else {

					listFilter = em
							.createQuery("SELECT d FROM TabelaAuxiliarCfop d WHERE d." + column
									+ "= :filter AND d.codemp IN (:codemp) AND d.flagAtivo IN (:flag)")
							.setParameter("filter", Integer.parseInt(filter))
							.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.LAYOUT_ARQUIVO_CAB))
							.setParameter("flag", ativo)
							.getResultList();

				}

			} else {
				listFilter = em
						.createQuery("SELECT d FROM TabelaAuxiliarCfop d WHERE d." + column
								+ " LIKE CONCAT('%', :filter, '%') AND d.codemp IN (:codemp) AND d.flagAtivo IN (:flag)")
						.setParameter("filter", (filter.equals("")) ? "%" : filter)
						.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.LAYOUT_ARQUIVO_CAB))
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
