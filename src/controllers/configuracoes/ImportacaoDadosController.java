package controllers.configuracoes;


import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import org.apache.commons.lang3.CharSet;
import org.controlsfx.control.textfield.CustomTextField;

import application.DadosGlobais;
import connect.ConnectionHib;
import controllers.utils.ProgressBarForm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.configuracoes.LayoutArquivoCab;
import models.configuracoes.LayoutArquivoCabDAO;
import models.configuracoes.NivelAcesso;
import models.configuracoes.TabelaAuxiliarCstIcm;
import models.configuracoes.TabelasAuxiliaresDAO;
import tools.controls.ComboBoxFilter;
import tools.enums.EnumLogRetornoStatus;
import tools.utils.LogRetorno;
import tools.utils.Util;

public class ImportacaoDadosController implements Initializable 
{

    @FXML
	private AnchorPane anchorPanePrincipal, 
					   anchorPaneListagem, 
					   anchorPaneDetalhes,
					   apImportacaoDados,
                       apInterno;
    
	@FXML
	private CustomTextField txtSearchLayout,	
	 						txtFileName;
	
	@FXML
	private TextArea tAreaFileContent;

    @FXML
    private Pane titleBar;
    
    @FXML
    private Button btnInsert,
				   btnSave,
				   btnCancel;  
    
    @FXML
    private ScrollPane scrPane; 
    
    @FXML
    private Label lblSearchedWord,
    			  lblSearchCount;

	@FXML
	private ProgressBar pBar;
	
	private NivelAcesso nivAcessoPermissao;
	
	private static Stage stg;
	
	String[][] csvFileData = null;
	
	LayoutArquivoCab entLayArqCab;
	private TabelasAuxiliaresDAO entidadeDao = new TabelasAuxiliaresDAO();	
	
	Util utl = new Util();
		
	Integer lacCodigo;
		
	LocalDateTime lacLastMovto;
						
	int lacOperationType,
		lacTypeArquivoDados;
	
	String lacDescricao,
		   lacComandoSql,
		   lacSeparador,
		   lacTabela;		
    
    public ImportacaoDadosController(NivelAcesso nivAcesso, TabPane o_tabPrincipal)
    {   
    	    this.nivAcessoPermissao = nivAcesso;  
	}
    
    @FXML
    void actionBtnInsert(ActionEvent event) 
    {
    	 Util.closeWindow(apImportacaoDados);
    }
        

    @FXML
    void actionBtnSave(ActionEvent event) 
    {

		if ( nivAcessoPermissao.getFlagUpdate().equals(1)) 
		{

			if ( !Util.noEmpty(txtFileName) && !txtFileName.isDisable() && entLayArqCab != null && csvFileData != null ) 
			{							    

					Task<String> TarefaRefresh = new Task<String>() 
					{

						LogRetorno logRet = new LogRetorno();

						@Override
						protected String call() throws Exception 
						{	   					    
							logRet = entidadeDao.saveLayoutsData(entLayArqCab, csvFileData);

							return "-";
						}

						@Override
						protected void succeeded() 
						{
							stg.close();
							pBar.setProgress(1);
							if ( logRet.getStatus().equals(EnumLogRetornoStatus.SUCESSO) ) 
							{
								 Util.showNotification(DadosGlobais.resourceBundle.getString("notificationSucess"));							
								 
								 initTxtsLayoutsStates();								
							} 
							else 
							{
								 utl.showAlert(logRet.getMsg(), "error");
							}
							
						}

						@Override
						protected void failed()
						{
							stg.close();
							utl.tratamentoExcecao(exceptionProperty().getValue().toString(),"[ GrupoController.actionBtnSave() - INSERT ]");
							pBar.setProgress(0);
						}

						@Override
						protected void cancelled() 
						{
							pBar.setProgress(0);
							super.cancelled();
						}

					};
					Thread t = new Thread(TarefaRefresh);
					t.setDaemon(true);
					t.start();
					stg = ProgressBarForm.showProgressBar(UsuarioController.class, TarefaRefresh,DadosGlobais.resourceBundle.getString("infInsertRegistro"), false);
					pBar.setProgress(-1);				
			}
			else{}
		} 
		else 
		{
			utl.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
		}
		
    }
    
    @FXML
    void actionBtnCancel(ActionEvent event) 
    {		 
			if ( nivAcessoPermissao.getFlagUpdate().equals(1)) 
			{
	
				if ( !Util.noEmpty(txtFileName) && !txtFileName.isDisable() && entLayArqCab != null && csvFileData != null ) 
				{							    
	
						Task<String> TarefaRefresh = new Task<String>() 
						{
	
							LogRetorno logRet = new LogRetorno();
	
							@Override
							protected String call() throws Exception 
							{	   					    
								logRet = initTxtsLayoutsStates();
	
								return "-";
							}
	
							@Override
							protected void succeeded() 
							{
								stg.close();
								pBar.setProgress(1);
								if ( logRet.getStatus().equals(EnumLogRetornoStatus.SUCESSO) ) 
								{
									 Util.showNotification(DadosGlobais.resourceBundle.getString("notificationSucess"));							
									 									 
									 btnSave.setDisable(false);
									 btnCancel.setDisable(false);
									 btnInsert.setDisable(true);								
								} 
								else 
								{
									 utl.showAlert(logRet.getMsg(), "error");
								}
								
							}
	
							@Override
							protected void failed()
							{
								stg.close();
								utl.tratamentoExcecao(exceptionProperty().getValue().toString(),"[ GrupoController.actionBtnSave() - INSERT ]");
								pBar.setProgress(0);
							}
	
							@Override
							protected void cancelled() 
							{
								pBar.setProgress(0);
								super.cancelled();
							}
	
						};
						Thread t = new Thread(TarefaRefresh);
						t.setDaemon(true);
						t.start();
						stg = ProgressBarForm.showProgressBar(UsuarioController.class, TarefaRefresh,DadosGlobais.resourceBundle.getString("infInsertRegistro"), false);
						pBar.setProgress(-1);				
				}
				else{}
			} 
			else 
			{
				utl.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
			}				 
		 
    }    
    
    
	@FXML
	/**
	 * AÇÃO DO BOTAO INSERIR PRESENTE NA ABA DETALHES (btnInsert) - ATALHO F5
	 */
	void actionBtnClose(ActionEvent event) 
	{
		Util.fechaTelaCadastro(anchorPaneListagem, anchorPaneDetalhes);	
	} 
	
	LayoutArquivoCab getLayoutArquivoCabFounded()
	{
		
		    LayoutArquivoCab result = null;
			ObservableList<ComboBoxFilter> list = FXCollections.observableArrayList();
			
			list.add(new ComboBoxFilter("Código", 1, "codigo"));
			list.add(new ComboBoxFilter("Descrição", 2, "descricao"));
			list.add(new ComboBoxFilter("Cód Empresa", 3, "codemp"));
			
			if( utl != null )
			{		
					Object obj = utl.showSearchGetParameters("LayoutArquivo", "descricao", LayoutArquivoCabDAO.class,list);
					
					if ( obj != null )						
						result = (LayoutArquivoCab) obj;
					else
						result = null; 
			}
			else
				result = null; 
			
			return result;		
	}
	
	LogRetorno initTxtsLayoutsStates()
	{		
		 LogRetorno logRet = new LogRetorno();
		 
		 txtFileName.clear();
		 tAreaFileContent.clear();
		 txtFileName.setText("");
	     txtFileName.setDisable(true);
	     tAreaFileContent.setDisable(true);			 
		 btnSave.setDisable(true);
		 btnCancel.setDisable(true);
		 btnInsert.setDisable(true);
		 
		 logRet.setStatus(EnumLogRetornoStatus.SUCESSO);
		 
		 logRet.setMsg(EnumLogRetornoStatus.SUCESSO.name());
			
		 return logRet;     
	}
	
	void initLayoutsData()
	{		
		    initTxtsLayoutsStates();		
		    
			entLayArqCab = getLayoutArquivoCabFounded();
			
			if( entLayArqCab != null )
			{
				lacCodigo = entLayArqCab.getCodigo();
									
				lacOperationType = entLayArqCab.getFlagTipoOperacao();
				lacTypeArquivoDados = entLayArqCab.getTipoArquivoDados();
				
				lacComandoSql = entLayArqCab.getComandoSql();
				lacSeparador = entLayArqCab.getSeparador();
				lacTabela = entLayArqCab.getTabela();	
				
				txtSearchLayout.setText(lacCodigo.toString());
				txtFileName.setDisable(false);
				tAreaFileContent.setDisable(false);
				
			}
			else
			{				
				txtSearchLayout.setText("");
				txtFileName.setDisable(true);
				tAreaFileContent.setDisable(true);				
			}
		
	}
	
	boolean initArquivoDados()
	{		
		   boolean result = false;
		
		   if( entLayArqCab != null )
		   {				   
				if( lacTypeArquivoDados == 0 )//CSV
				{	
					csvFileData = actionLoadCSVFile(tAreaFileContent, null, txtFileName, lacSeparador); 
					
					if( csvFileData != null )
						result = true;
					else
						result = false;
				}	
				/*else if( lacTypeArquivoDados == 1 )//OTRO
				{
				
				}*/	
				else
					result = true;	
				
		   }
		   else
			   result = true;
		   
		   return result;
		
	}
	
	public static String[][] actionLoadCSVFile(TextArea fileContent, TabelaAuxiliarCstIcm entidadeBean, CustomTextField txtCsvFile, String cvsSeparator) 
	{
		
			String csvFile = "",
				   csvData[][] = null;
			FileChooser fileChooser = new FileChooser();
			
			fileChooser.setTitle("Select the file");
			fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV", "*.csv"));
	
			File file = fileChooser.showOpenDialog(null);
	
			if ( file != null ) 
			{	
				
				csvFile = file.getPath();
				txtCsvFile.setText(csvFile);
				
				//---------------------PROCESS CSV FILE---------------

		        try
		        {			        	
			        	List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.ISO_8859_1);			        	
	                    int cntLines = lines.size();
	                    
	                    if( cntLines > 0 )
	                    { 	
	                    	String strFirstLine = lines.get(0),
	                    		   colValues[] = strFirstLine.split(cvsSeparator);
	                    	
	                    	if( colValues != null ) 
	                    	{
		                    	int cntCols = colValues.length;	
		                    	
			                    csvData = new String[cntLines][cntCols];
			                    String tmpData = "";
			                    
					            for ( int f = 0 ; f < cntLines ; f++ )
					            {
					            	
						                String lin = lines.get(f), 
						                	   array[] = lin.split(cvsSeparator);
						                
						                for ( int c = 0 ; c < cntCols ; c++ )
						                { 	
						                	  csvData[f][c] = array[c];
						                	  tmpData += csvData[f][c].toString() + " | ";
						                } 
						                
						                tmpData += '\n';
					            }	
								
								fileContent.setText(tmpData);
								
	                    	}
	                    	else
	                    		csvData = null;
	                    }
	                    else
	                    	csvData = null;
		        } 
		        catch (IOException e) 
		        {
		               e.printStackTrace();
		               csvData = null;
		        }
		
			}
   
			return csvData;		
	}	
    

	public void initialize(URL location, ResourceBundle resources ) 
	{	
				btnInsert.setDisable(true); 
				btnSave.setDisable(true); 
				btnCancel.setDisable(true); 
				initTxtsLayoutsStates();
				
				txtSearchLayout.setOnKeyPressed(new EventHandler<KeyEvent>()
				{
					@Override
					public void handle(KeyEvent event) 
					{
						if( event.getCode() == KeyCode.ENTER)
							initLayoutsData();
						else{}
						
					}
					
				});
						
				Button btnSearch = Util.customSearchTextField("right",null, txtSearchLayout);
				btnSearch.setOnAction(new EventHandler<ActionEvent>() 
				{			
					@Override
					public void handle(ActionEvent event) 
					{
						   initLayoutsData();
					}
					
				});		
				
				txtFileName.setOnKeyPressed(new EventHandler<KeyEvent>() 
				{		
					public void handle(KeyEvent event) 
					{
						if( event.getCode() == KeyCode.ENTER )
						{
							boolean disableBtns = !initArquivoDados();
							btnSave.setDisable(disableBtns);
							btnInsert.setDisable(true);
							btnCancel.setDisable(disableBtns);
						}	
						else{}
					}
					
				});
				
				Button btnLoadFile = Util.customLoadTextField("right", null, txtFileName);
				btnLoadFile.setOnAction(new EventHandler<ActionEvent>() 
				{		
					@Override
					public void handle(ActionEvent event)
					{
						 boolean disableBtns = !initArquivoDados();
						 btnSave.setDisable(disableBtns);
						 btnInsert.setDisable(true);
						 btnCancel.setDisable(disableBtns);				   
					}
					
				});			
	}

}
