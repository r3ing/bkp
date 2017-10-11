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


public class LayoutArquivoCabDAO //implements InterfaceDAO
{

	private LocalDateTime ts_now; 
	private BigInteger utctag;
	private EntityManager em = null;

	public LogRetorno insert(Object objeto, TableView <TabelasModel> tvSelectedTabelaFields)
	{

		LayoutArquivoCab larquivoCab = (LayoutArquivoCab) objeto;
		LogRetorno logRet = new LogRetorno();
		SequencialDAO seqDAO = new SequencialDAO();
		
		try 
		{
			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);
			
			em = ConnectionHib.emf.createEntityManager();
			em.getTransaction().begin();
			
			Date dataServer  = (Date) em.createNativeQuery(ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL").getSingleResult();
			
			em.createQuery("UPDATE Sequencial s SET s.cnfLayoutArquivos = s.cnfLayoutArquivos+1 WHERE s.codemp= :codemp AND s.checkDelete = :checkDelete")
			.setParameter("codemp", DadosGlobais.empresaLogada.getCodigo())
			.setParameter("checkDelete", DadosGlobais.empresaLogada.getCheckDelete())
			.executeUpdate();
			
			utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));		
			
			ts_now = Util.dateToLocalDateTime(dataServer);
			
			Integer codigo = seqDAO.getLastInsertCodigo(DadosGlobais.empresaLogada.getCodigo(), "cnfLayoutArquivos") + 1;
			
			larquivoCab.setCodemp(DadosGlobais.empresaLogada.getCodigo());
			larquivoCab.setCodigo(codigo);
			larquivoCab.setUtctag(utctag);
			larquivoCab.setCheckDelete(Util.checkDeleteCreate());
			larquivoCab.setFlagAtivo(1);
			larquivoCab.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
			larquivoCab.setLastMovto(ts_now);
			
		    //-----------------------------LAYOUT ARQUIVO DET-------------------------------------		    
		   
		    List<LayoutArquivoDet> listLayoutArquivoDet = FXCollections.observableArrayList();
		    //new HashSet<LayoutArquivoDet>();
			ObservableList<TableColumn<TabelasModel,?>> cols = tvSelectedTabelaFields.getColumns();
			int rowCount = tvSelectedTabelaFields.getItems().size();			
			
			for ( int f = 0 ; f < rowCount ; f++ ) 
    		{	
        		  CheckBox chk = (CheckBox)cols.get(0).getCellData(f); 
        		  
        		  if( chk.isSelected() )
        		  {	  
	        		  String fieldName = cols.get(1).getCellData(f).toString();
	        		  String fieldType = cols.get(2).getCellData(f).toString();
	        		  CustomTextField txt = (CustomTextField)cols.get(3).getCellData(f);
		        		  
				        		  LayoutArquivoDet lad = new LayoutArquivoDet();

			        		      	  lad.setLayoutArquivoCab(larquivoCab);
				        		  
					        		  lad.setCodemp(larquivoCab.getCodemp());
				        		  	  
					        		  lad.setFlagAtivo(larquivoCab.getFlagAtivo());
					        		  lad.setLastCoduser(larquivoCab.getLastCoduser());
					        		  lad.setLastMovto(larquivoCab.getLastMovto());
					        		  lad.setUtctag(larquivoCab.getUtctag());		
					        		  
					        		  //--------------------THIS ARE THE VALUES OF THE TABLE----------------
					        		  lad.setDescColuna(fieldName);
					        		  lad.setTipoDados(fieldType);
					        		  lad.setPosicaoFile(txt.getText());

				        		  	  lad.setCheckDelete(Util.checkDeleteCreate());
					        		  
				        		  listLayoutArquivoDet.add(lad);		  
	        		  
        		  }
        		  else{}

			}		 			
    		
			larquivoCab.setLayoutArquivoDet(listLayoutArquivoDet);
  			
  			em.persist(larquivoCab);
			
			em.getTransaction().commit();
			
			logRet.setStatus(EnumLogRetornoStatus.SUCESSO);
			
			logRet.setMsg(EnumLogRetornoStatus.SUCESSO.name());
			
			logRet.setLastRecord(codigo);
			
			logRet.setObjeto(larquivoCab);
			
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
	public List<LayoutArquivoCab> getList(List<Integer> flagAtivo) 
	{
		List<LayoutArquivoCab> list = null;
		try {	
		em = ConnectionHib.emf.createEntityManager();		
		
		list = em.createNamedQuery("LayoutArquivoCabAll")
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

			List<LayoutArquivoCab> list = em.createNamedQuery("LayoutArquivoCabLast")
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
	public LogRetorno getById(Integer codigo) 
	{
		
		LogRetorno logRet = new LogRetorno();

		try
		{
			em = ConnectionHib.emf.createEntityManager();
			
			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);

			List<LayoutArquivoCab> list = em.createNamedQuery("LayoutArquivoCabById")
					.setParameter("codigo", codigo)
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
			throw e;
		
		}finally
		{
			em.close();
		}
		return logRet;
	}

	public LogRetorno update(Object objeto, TableView <TabelasModel> tvSelectedTabelaFields)
	{
		
		   LayoutArquivoCab larquivoCab = (LayoutArquivoCab) objeto;
	
		   LogRetorno logRet = new LogRetorno();
		
		   try 
		   {
				em = ConnectionHib.emf.createEntityManager();
				logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);
				em.getTransaction().begin();
				
				LayoutArquivoCabPK larquivoCabPK = new LayoutArquivoCabPK();
				larquivoCabPK.setCodigo(larquivoCab.getCodigo());
				larquivoCabPK.setCheckDelete(larquivoCab.getCheckDelete());
	
				LayoutArquivoCab newLarquivoCab = em.find(LayoutArquivoCab.class, larquivoCabPK);
				
				if( newLarquivoCab != null && newLarquivoCab.getFlagAtivo() == 1 )
				{			
					Date dataServer  = (Date) em.createNativeQuery(ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL").getSingleResult();
					utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));		
					ts_now = Util.dateToLocalDateTime(dataServer);
					
					larquivoCab.setLastMovto(ts_now);
					larquivoCab.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
					larquivoCab.setUtctag(utctag);				
					
					List<LayoutArquivoDet> listLayoutArquivoDetOrigem = larquivoCab.getLayoutArquivoDet();
					ObservableList<TableColumn<TabelasModel,?>> cols = tvSelectedTabelaFields.getColumns();
					int rowCount = tvSelectedTabelaFields.getItems().size();	
			
					for ( int f = 0 ; f < rowCount ; f++ ) 
		    		{	
		        		  CheckBox chk = (CheckBox)cols.get(0).getCellData(f); 
		        		  String fieldName = cols.get(1).getCellData(f).toString(),
		        				 fieldType = cols.get(2).getCellData(f).toString();
		        		  CustomTextField txt = (CustomTextField)cols.get(3).getCellData(f);
		        		  
		        		  LayoutArquivoDet lad = getFromList( listLayoutArquivoDetOrigem, fieldName, fieldType );
		        		  
		        		  if ( chk.isSelected() ) 
		        		  {	 		        			   
		        			   if( lad != null )
		        			   {	
			        		       lad.setLayoutArquivoCab(larquivoCab);
			        		       lad.setPosicaoFile(txt.getText());
			        		       
			        		       em.merge(lad);			        		       
		        			   }	   
		        			   else
		        			   {	
		        				   lad = new LayoutArquivoDet();
		        				   
			        				   lad.setLayoutArquivoCab(larquivoCab);
			        				   
					        		   lad.setCodemp(larquivoCab.getCodemp());			        		  	  
					        		   lad.setFlagAtivo(larquivoCab.getFlagAtivo());
					        		   lad.setLastCoduser(larquivoCab.getLastCoduser());
					        		   lad.setLastMovto(larquivoCab.getLastMovto());
					        		   lad.setUtctag(larquivoCab.getUtctag());	
					        		   
				        		       lad.setDescColuna(fieldName);
				        		       lad.setTipoDados(fieldType);
				        		       lad.setPosicaoFile(txt.getText());					        		   
				        		   
					        		   lad.setCheckDelete(Util.checkDeleteCreate());
					        		   
					        		   em.merge(lad);		   
		        				   
					        		   listLayoutArquivoDetOrigem.add(lad);	 
		        			   }	
		        			   
		        		   }
		        		   else
		        		   {   
		        			   if( lad != null )
		        			   {	
									em.createQuery("DELETE FROM LayoutArquivoDet lad WHERE lad.layoutArquivoCab = :layoutArquivoCab AND lad.checkDelete  = :checkDelete")
									.setParameter("layoutArquivoCab",lad.getLayoutArquivoCab())
									.setParameter("checkDelete",lad.getCheckDelete())
									.executeUpdate();
		        			   }     
		        			   else{}
		        		   }		
					}
		
					larquivoCab.setLayoutArquivoDet(listLayoutArquivoDetOrigem);
					
					em.merge(larquivoCab);
					
					em.getTransaction().commit();
					
					logRet.setStatus(EnumLogRetornoStatus.SUCESSO);
					logRet.setMsg("Sucesso");
				
				}
				else
				{
					logRet.setStatus(EnumLogRetornoStatus.ERRO);
					logRet.setMsg(DadosGlobais.resourceBundle.getString("errorUpdate"));
				}
				
		   }
		   catch ( Exception e ) 
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

	private LayoutArquivoDet getFromList(List<LayoutArquivoDet> listLayoutArquivoDetOrigem, String fieldName, String fieldType) 
	{
		    LayoutArquivoDet result = null;
		   
		    for ( int i = 0 ; i < listLayoutArquivoDetOrigem.size() ; i++ ) 
		    {
		    	   LayoutArquivoDet item = listLayoutArquivoDetOrigem.get(i);
	 			   
	 			   if( item != null )
	 			   {	 			   
		     		   String valLadDescColuna = item.getDescColuna(),
			        			  valLadTipoDados = item.getTipoDados();					        			   
		 			   
		 			   if( valLadDescColuna.equalsIgnoreCase(fieldName) && valLadTipoDados.equalsIgnoreCase(fieldType) )
		 			   {	
		 				   result = item;
		                   break;
		 			   }    
		 			   else
		 				   result = null;	
	 			   }
	 			   else
	 				   result = null;
	 				   
		    }
		   
		    return result;
	}

	public void delete(Object objeto) 
	{		
		LayoutArquivoCab larquivoCab = (LayoutArquivoCab) objeto;
		
		try 
		{
			
			em = ConnectionHib.emf.createEntityManager();
			
			em.getTransaction().begin();
			
			Date dataServer  = (Date) em.createNativeQuery(ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL").getSingleResult();
			utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));			
			ts_now = Util.dateToLocalDateTime(dataServer);
			
			em.createQuery("UPDATE LayoutArquivoCab u "
					+ "SET u.flagAtivo = "+larquivoCab.getFlagAtivo()+","
					+ "u.lastMovto = :dtAlteracao,"
				    + "u.lastCoduser = "+DadosGlobais.usuarioLogado.getCodigo()+","
				    + "u.utctag = "+utctag+" "
					+ "WHERE u.codigo= :codigo "
					+ "AND u.checkDelete = :checkDelete "
					+ "AND u.codemp = :codemp ")
			.setParameter("dtAlteracao", ts_now)
			.setParameter("codigo", larquivoCab.getCodigo())
			.setParameter("checkDelete", larquivoCab.getCheckDelete())
			.setParameter("codemp", larquivoCab.getCodemp()).executeUpdate();
			
			Integer tipoOperacao = null;
			String valorAnterior = null;
			String valorNovo = null;
			if (larquivoCab.getFlagAtivo() == 1) 
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
			//auditoria.setTipoRegistro(DadosGlobais.resourceBundle.getString("miCadGrupo").toUpperCase());
			auditoria.setDtMovto(Util.geraDataMovto(ts_now));
			auditoria.setValorAnterior(valorAnterior);
			auditoria.setValorNovo(valorNovo);
			auditoria.setHistorico(larquivoCab.getCodigo() + "-" + larquivoCab.getDescricao());
			auditoria.setDocCodigo(String.valueOf(larquivoCab.getCodigo()));
	
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
	public List<LayoutArquivoCab> filterByColumn (String column, String filter, int action, List<Integer> ativo) 
	{		 					
		List<LayoutArquivoCab> listFilter = null;
		try {
			em = ConnectionHib.emf.createEntityManager();
			if (action == 1) {
				if (filter.equals("")) {
					listFilter = em
							.createQuery("SELECT d FROM LayoutArquivoCab d WHERE d." + column
									+ " LIKE CONCAT('%', :filter, '%') AND d.codemp IN (:codemp) AND d.flagAtivo IN (:flag)")
							.setParameter("filter", (filter.equals("")) ? "%" : filter)
							.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.LAYOUT_ARQUIVO_CAB))
							.setParameter("flag", ativo)
							.getResultList();
				} else {

					listFilter = em
							.createQuery("SELECT d FROM LayoutArquivoCab d WHERE d." + column
									+ "= :filter AND d.codemp IN (:codemp) AND d.flagAtivo IN (:flag)")
							.setParameter("filter", Integer.parseInt(filter))
							.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.LAYOUT_ARQUIVO_CAB))
							.setParameter("flag", ativo)
							.getResultList();

				}

			} else {
				listFilter = em
						.createQuery("SELECT d FROM LayoutArquivoCab d WHERE d." + column
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
