package models.configuracoes;


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


public class TabelaAuxiliarCstCofinDAO
{

	private LocalDateTime ts_now; 
	private BigInteger utctag;
	private EntityManager em = null;

	public LogRetorno insert(Object objeto, String cst)
	{

		TabelaAuxiliarCstCofin entity = (TabelaAuxiliarCstCofin) objeto;
		LogRetorno logRet = new LogRetorno();
		
		try 
		{
			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);
			
			em = ConnectionHib.emf.createEntityManager();
			em.getTransaction().begin();
			
			Date dataServer  = (Date) em.createNativeQuery(ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL").getSingleResult();
				
			utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));		
			
			ts_now = Util.dateToLocalDateTime(dataServer);

			entity.setCst(cst);
			
			entity.setCodemp(DadosGlobais.empresaLogada.getCodigo());
			entity.setUtctag(utctag);
			entity.setCheckDelete(Util.checkDeleteCreate());
			entity.setFlagAtivo(1);
			entity.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
			entity.setVersao(1);
			entity.setLastMovto(ts_now);	    

  			em.persist(entity);
			
			em.getTransaction().commit();
			
			logRet.setStatus(EnumLogRetornoStatus.SUCESSO);
			
			logRet.setMsg(EnumLogRetornoStatus.SUCESSO.name());
			
			logRet.setLastRecord(Integer.parseInt(entity.getCst()));
			
			logRet.setObjeto(entity);
			
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
	public List<TabelaAuxiliarCstCofin> getList(List<Integer> flagAtivo) 
	{
		List<TabelaAuxiliarCstCofin> list = null;
		try {	
		em = ConnectionHib.emf.createEntityManager();		
		
		list = em.createNamedQuery("TabelaAuxiliarCstCofinAll")
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

			List<TabelaAuxiliarCstCofin> list = em.createNamedQuery("TabelaAuxiliarCstCofinLast")
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

			List<TabelaAuxiliarCfop> list = em.createNamedQuery("TabelaAuxiliarCstCofinById")
					.setParameter("cst", codigo)
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

	public LogRetorno update(Object objeto,  String cst)
	{		
		   TabelaAuxiliarCstCofin entity = (TabelaAuxiliarCstCofin) objeto;
	
		   LogRetorno logRet = new LogRetorno();
		
		   try 
		   {
				em = ConnectionHib.emf.createEntityManager();
				logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);
				
				em.getTransaction().begin();							
				
					/*em.createQuery("UPDATE TabelaAuxiliarCstCofin t SET t.cst = :cst, t.descricao = :descricao WHERE t.codemp = :codemp AND t.checkDelete = :checkDelete")
					.setParameter("cst", cst)
					.setParameter("descricao", entity.getDescricao())
					.setParameter("codemp", entity.getCodemp())
					.setParameter("checkDelete", entity.getCheckDelete())
					.executeUpdate();*/
				
					Date dataServer  = (Date) em.createNativeQuery(ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL").getSingleResult();				
					utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));
					ts_now = Util.dateToLocalDateTime(dataServer);
	
					entity.setCst(cst);
					
					entity.setCodemp(DadosGlobais.empresaLogada.getCodigo());
					entity.setUtctag(utctag);
					entity.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
					entity.setLastMovto(ts_now);	
					
					em.merge(entity);	
					
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
		TabelaAuxiliarCstCofin entity = (TabelaAuxiliarCstCofin) objeto;
		
		try 
		{
			
			em = ConnectionHib.emf.createEntityManager();
			
			em.getTransaction().begin();
			
			Date dataServer  = (Date) em.createNativeQuery(ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL").getSingleResult();
			utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));			
			ts_now = Util.dateToLocalDateTime(dataServer);
			
			em.createQuery("UPDATE TabelaAuxiliarCstCofin u "
					+ "SET u.flagAtivo = "+entity.getFlagAtivo()+","
					+ "u.lastMovto = :dtAlteracao,"
				    + "u.lastCoduser = "+DadosGlobais.usuarioLogado.getCodigo()+","
				    + "u.utctag = "+utctag+" "
					+ "WHERE u.cst= :cst "
					+ "AND u.checkDelete = :checkDelete "
					+ "AND u.codemp = :codemp ")
			.setParameter("dtAlteracao", ts_now)
			.setParameter("cst", entity.getCst())
			.setParameter("checkDelete", entity.getCheckDelete())
			.setParameter("codemp", entity.getCodemp()).executeUpdate();
			
			Integer tipoOperacao = null;
			String valorAnterior = null;
			String valorNovo = null;
			if (entity.getFlagAtivo() == 1) 
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
			auditoria.setHistorico(entity.getCst() + "-" + entity.getDescricao());
			auditoria.setDocCodigo(String.valueOf(entity.getCst()));
	
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
	public List<TabelaAuxiliarCstCofin> filterByColumn (String column, String filter, int action, List<Integer> ativo) 
	{		 					
		List<TabelaAuxiliarCstCofin> listFilter = null;
		try {
			em = ConnectionHib.emf.createEntityManager();
			if (action == 1) {
				if (filter.equals("")) {
					listFilter = em
							.createQuery("SELECT d FROM TabelaAuxiliarCstCofin d WHERE d." + column
									+ " LIKE CONCAT('%', :filter, '%') AND d.codemp IN (:codemp) AND d.flagAtivo IN (:flag)")
							.setParameter("filter", (filter.equals("")) ? "%" : filter)
							.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.LAYOUT_ARQUIVO_CAB))
							.setParameter("flag", ativo)
							.getResultList();
				} else {

					listFilter = em
							.createQuery("SELECT d FROM TabelaAuxiliarCstCofin d WHERE d." + column
									+ "= :filter AND d.codemp IN (:codemp) AND d.flagAtivo IN (:flag)")
							.setParameter("filter", Integer.parseInt(filter))
							.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.LAYOUT_ARQUIVO_CAB))
							.setParameter("flag", ativo)
							.getResultList();

				}

			} else {
				listFilter = em
						.createQuery("SELECT d FROM TabelaAuxiliarCstCofin d WHERE d." + column
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
