package models.configuracoes; 


import java.lang.reflect.Method;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

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


public class TabelasAuxiliaresDAO
{

	private LocalDateTime ts_now; 
	private BigInteger utctag;

	Method getSetMethodByName(Class<?> entityTable, String methodName)
	{					    
		    Method myMethod = null;
		    
		    if( entityTable != null )
		    { 		    
			    Method[] methods = entityTable.getMethods();
			    
			    if( methods != null)
			    { 				
					for ( int f = 0; f < methods.length; f++ ) 
					{
						  myMethod = methods[f];
						  String strMethodName = myMethod.getName();
						  
						  if( strMethodName.equalsIgnoreCase(methodName) )
							  break; 
						  else{}						 
					}			
			    }
			    else{}
			    
		    }
		    else{}
			
			return myMethod;		
	}		
	
	void invokeSetMethod(Class<?> entityTable, Object objEtntityTable,  String strMethodName, Object parameter)
	{	    
		    Method method = getSetMethodByName(entityTable, strMethodName);	   
		    
		    if( method != null )
		    {			    	
		    	method.setAccessible(true);
			    Class<?> pType[] = method.getParameterTypes();
			    
		        if( pType.length > 0 )
		        {	
		        	Class<?> paramType = pType[0];
		        	
		        	try  
		        	{		        		
		        		Object param = paramType.cast(parameter);		        		
						method.invoke(objEtntityTable, param);						
					} 
		        	catch (Exception e) 
		        	{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        	
		        }    
		        else{}							    	
		        
		    }
		    else{}			
	}	
	
	Class<?> getTableByName( String tableName )
	{	
	    	Metamodel mm = ConnectionHib.emf.getMetamodel();
	    	Set<EntityType<?>> entities = mm.getEntities();
	    	Iterator<EntityType<?>> it = entities.iterator();
	    	Class<?> javaClass = null;
	    	
	        while ( it.hasNext() ) 
	        {		            	
		        	EntityType<?> et = it.next();
		        	javaClass = et.getJavaType();	
		        	String simpleName = javaClass.getSimpleName(); 	
		        	if( simpleName.equalsIgnoreCase(tableName) )
		        		break;
		        	else{}
	        }
	        
	        return javaClass; 		
	}	
	
	public LogRetorno saveLayoutsData( LayoutArquivoCab entLayArqCab, String[][] csvFileData )
	{		
			LogRetorno logRet = new LogRetorno();		
			
			if( entLayArqCab.getFlagTipoOperacao() == 0 )//import
			{						
				ObservableList<LayoutArquivoDet> ladList = FXCollections.observableArrayList(entLayArqCab.getLayoutArquivoDet());
				Class<?> entityTable = getTableByName( entLayArqCab.getTabela() );			
						
				if( entityTable != null )
				{					
					for ( int it = 0 ; it < csvFileData.length ; it ++ ) 
					{
							EntityManager em = null;
							
							try 
							{
							    Object objEtntityTable = entityTable.newInstance(),
							    	   objValue = null;
							    
								logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);
								
								em = ConnectionHib.emf.createEntityManager();
			  		        	em.getTransaction().begin();
			  		        				  					
			  					Date dataServer  = (Date) em.createNativeQuery(ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL").getSingleResult();			  						
			  					utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));				  					
			  					ts_now = Util.dateToLocalDateTime(dataServer);			  		        	
							    
							    invokeSetMethod(entityTable, objEtntityTable, "setLastMovto", ts_now);
							    invokeSetMethod(entityTable, objEtntityTable, "setFlagAtivo", 1);
							    invokeSetMethod(entityTable, objEtntityTable, "setLastCoduser", DadosGlobais.usuarioLogado.getCodigo());
							    invokeSetMethod(entityTable, objEtntityTable, "setUtctag", utctag);							    
							    invokeSetMethod(entityTable, objEtntityTable, "setCheckDelete", Util.checkDeleteCreate());
							    invokeSetMethod(entityTable, objEtntityTable, "setCodemp", DadosGlobais.empresaLogada.getCodigo());
							    
							    invokeSetMethod(entityTable, objEtntityTable, "setVersao", 1);
							    
								for ( int l = 0 ; l < ladList.size() ; l++ ) 
								{     
									  LayoutArquivoDet lad = ladList.get(l);
									  
					  				    String ladDescColuna = lad.getDescColuna(),
					  				   		   ladPosicaoFile = lad.getPosicaoFile();							  				   
					  				    int Col = Integer.parseInt(ladPosicaoFile);
					  				    
					  				    String strValue = csvFileData[it][Col],
					  				    	   methodName = "set"+ladDescColuna;
					  				    
					  				    objValue = strValue;	
					  				    
					  				    invokeSetMethod(entityTable, objEtntityTable, methodName, objValue);					  				    
								}										
												    	
								em.persist(objEtntityTable);
								
			  		        	em.getTransaction().commit();
			  					
			  					logRet.setStatus(EnumLogRetornoStatus.SUCESSO);
			  					
			  					logRet.setMsg(EnumLogRetornoStatus.SUCESSO.name());
			  					
			  					logRet.setObjeto(objEtntityTable);
			  					
							} 
							catch ( Exception e ) 
							{
								    e.printStackTrace();
								    em.getTransaction().rollback();			
								   // throw e;
							}
							finally 
							{			
								em.close();
							}					
				     }			
					
				}
				else{}
			
			}
			else{}
		
		return logRet;
		
	}		
}
