package controllers.application;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import com.lowagie.text.html.WebColors;
import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;

import application.DadosGlobais;
import connect.ConnectionHib;
import controllers.compras.DepartamentoController;
import controllers.utils.ProgressBarForm;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;
import models.application.InstallDAO;
import models.configuracoes.Empresa;
import models.configuracoes.NivelAcesso;
import models.configuracoes.NivelAcessoDAO;
import models.configuracoes.Usuario;
import tools.controls.ComboBoxFilter;
import tools.criptografia.CriptografiaAes;
import tools.utils.LogRetorno;
import tools.utils.Util;

public class InstallController implements Initializable{

	 @FXML
	 private AnchorPane panePrincipal;
	 
    @FXML
    private Button btnClose;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab tab1;

    @FXML
    private RadioButton rdbMysql0;

    @FXML
    private ToggleGroup grpTipoBanco;

    @FXML
    private RadioButton rdbOracle1;

    @FXML
    private TextField txtIpServer;

    @FXML
    private TextField txtPorta;

    @FXML
    private TextField txtSid;

    @FXML
    private Spinner<Integer> spinCodemp;

    @FXML
    private TextField txtBaseDados;

    @FXML
    private TextField txtUser;

    @FXML
    private Pane paneProfessional;

    @FXML
    private RadioButton rdbProfessional1;

    @FXML
    private ToggleGroup grpVersaoSistema;

    @FXML
    private Pane paneEnterprise;

    @FXML
    private RadioButton rdbEnterprise2;

    @FXML
    private Pane paneUltimate;

    @FXML
    private RadioButton rdbUltimate3;

    @FXML
    private Tab tab2;

    @FXML
    private RadioButton rdbManaus0;

    @FXML
    private ToggleGroup grpCentralAtendimento;

    @FXML
    private RadioButton rdbBoaVista1;

    @FXML
    private Tab tab3;

    @FXML
    private RadioButton rdbInstalInicial0;

    @FXML
    private ToggleGroup grpTipoInstalacao;

    @FXML
    private RadioButton rdbUpgrade1;

    @FXML
    private RadioButton rdbConvDados3;

    @FXML
    private Tab tab4;

    @FXML
    private TextField txtSerie1;

    @FXML
    private TextField txtSerie2;

    @FXML
    private TextField txtSerie3;

    @FXML
    private TextField txtSerie4;

    @FXML
    private Tab tab5;

    @FXML
    private TextField txtRazaoSocial;

    @FXML
    private TextField txtNomeFant;

    @FXML
    private TextField txtCidade;

    @FXML
    private ComboBox<ComboBoxFilter> cboxUF;

    @FXML
    private TextField txtPwdHash;

    @FXML
    private TextField txtCnpj;

    @FXML
    private TextField txtVersao;

    @FXML
    private CheckBox chkAtivaMensal;

    @FXML
    private ComboBox<ComboBoxFilter> cboxDiaLibMen;

    @FXML
    private Button btnAvancar;

    @FXML
    private Button btnRetornar;

    @FXML
    private ProgressBar pgbPaso1;

   
    @FXML
    private ProgressBar pBar;
    
    @FXML
    private MaterialDesignIconView iconDatabase;
    @FXML
    private MaterialDesignIconView iconCentralAtendimento;
    @FXML
    private MaterialDesignIconView iconTipoInstalacao;
    @FXML
    private MaterialDesignIconView iconNumeroSerie;
    @FXML
    private MaterialDesignIconView iconEmpresa;

	//int i = 0;
	static Stage stg;
	
	String centralAtendimento;
	String tipoInstalacao;
	String numeroSerie;
	String tipoVersaoSistema;
	Integer codempInstall;
	
	Util util = new Util();
	LogRetorno logRet = new LogRetorno();
	LogRetorno logAvanca = new LogRetorno();
	Empresa empresa = new Empresa();
	InstallDAO instDao = new InstallDAO();
	Usuario userSuporte = new Usuario();
	
	
	private MenuBar menuPrincipal;
	
	private List<String> listMenu;
	
	
	public InstallController() {
		// TODO Auto-generated constructor stub
	}
	
	 @FXML
	    void keyReleasedTxtSerie1(KeyEvent event) {
		
		 String texto = util.stringAlfaNumerica(txtSerie1.getText());
		 
		
		 
		 if (txtSerie1.getText().length() > 5) {
	
			 txtSerie1.setText(texto.substring(0, 6));
			 txtSerie2.requestFocus();	
			
		 }
			 
		 
		 if(texto.length() > 23){
		
			 if (texto.length() > 6)
			 txtSerie2.setText(texto.substring(6, 12));
			 
			 if (texto.length() > 12)
			 txtSerie3.setText(texto.substring(12, 18));
			 
			 if (texto.length() > 18)
			 txtSerie4.setText(texto.substring(18, 24));
		}
			 
		
						
			
	    }

	    @FXML
	    void keyReleasedTxtSerie2(KeyEvent event) {
	    	util.onlyAlphanumeric(event);
	    	if (txtSerie2.getText().length() > 5) {
				txtSerie3.requestFocus();
			}
	    }

	    @FXML
	    void keyReleasedTxtSerie3(KeyEvent event) {
	    	
	    	if (txtSerie3.getText().length() > 5) {
				txtSerie4.requestFocus();
			}
	    }

	    @FXML
	    void keyReleasedTxtSerie4(KeyEvent event) {
	    	
	    	
	    }

	    @FXML
	    void keyTypedTxtSerie1(KeyEvent event) {
	    	util.onlyAlphanumeric(event);
	    }

	    @FXML
	    void keyTypedTxtSerie2(KeyEvent event) {
	    	util.onlyAlphanumeric(event);
	    }

	    @FXML
	    void keyTypedTxtSerie3(KeyEvent event) {
	    	util.onlyAlphanumeric(event);
	    }

	    @FXML
	    void keyTypedTxtSerie4(KeyEvent event) {
	    	util.onlyAlphanumeric(event);
	    }
	
	@FXML
	void actionBtnClose(ActionEvent event) {
		System.exit(0);
	}

	@FXML
	void actionBtnRetornar(ActionEvent event) {
		
		int i = tabPane.getSelectionModel().getSelectedIndex();
		
		if (i > 0) {
			i--;
			tabPane.getSelectionModel().select(i);
			tabPane.getTabs().get(i + 1).setDisable(true);
			tabPane.getTabs().get(i).setDisable(false);
			btnAvancar.setDisable(false);
			
			if (i == 0) {
				btnRetornar.setDisable(true);
			}
		}

	}
	
	
public void criaEstrutura(){
	
Task<String> tarefaCargaPg = new Task<String>() {
			
	//File fGrid = new File("C:\\EptusAM2\\");
		
			@Override
			protected String call() throws Exception {
			
//					if (!fGrid.exists()){
//						fGrid.mkdir();
//					}
				instDao.criaEstruturaPastas();
					return "-";

			}

			@Override
			protected void succeeded() {
				
				stg.close();
				pBar.setProgress(1);
			
				testaServidor();
			}

			@Override
			protected void failed() {
				
				stg.close();
				//Util.alertError("Erro");
				util.showAlert("Erro", "error");
				pBar.setProgress(0);
				
				
			}

			@Override
			protected void cancelled() {
				pBar.setProgress(0);
				super.cancelled();
			}

		};
		
		Thread t = new Thread(tarefaCargaPg);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(DepartamentoController.class, tarefaCargaPg,"Criando Estrutura de Arquivos",false);
		pBar.setProgress(-1);
		
					
	}
	
	
	public void testaServidor(){
		
		
		Task<String> tarefaCargaPg = new Task<String>() {
						
			@Override
			protected String call() throws Exception {
			
									
					logRet = instDao.verificaServidor();
					
				
				return "-";

			}

			@Override
			protected void succeeded() {
				stg.close();
				pBar.setProgress(1);
				if(logRet.getStatus() == 1){
					
					conectaBancoDados();
				}
				
			}

			@Override
			protected void failed() {
				stg.close();
//				Util.alertError("Erro ao Conectar ao Banco de Dados");
				util.showAlert("Erro ao Conectar ao Banco de Dados", "error");				
				pBar.setProgress(0);
			}

			@Override
			protected void cancelled() {
				pBar.setProgress(0);
				super.cancelled();
			}

		};
		Thread t = new Thread(tarefaCargaPg);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(DepartamentoController.class, tarefaCargaPg,"Verificando Servidor",false);
		pBar.setProgress(-1);
	}
	
	public void conectaBancoDados(){
		
		Task<String> tarefaCargaPg = new Task<String>() {
			
			@Override
			protected String call() throws Exception {
									
					logRet = instDao.verificaBaseDados();
				
					return "-";

			}

			@Override
			protected void succeeded() {
				stg.close();
				pBar.setProgress(1);
			
			if(logRet.getStatus()==1){
				
					empresa = instDao.checaEmp(spinCodemp.getValue());
					
				    avancaTela(1);
			}else{
				
				empresa = new Empresa();
				
				criaBaseDados();
				
			}
			}

			@Override
			protected void failed() {
			
				stg.close();
				pBar.setProgress(0);
//				Util.alertError("Erro ao Conectar ao Banco de Dados, verifique a conexão de rede!");
				util.showAlert("Erro ao Conectar ao Banco de Dados, verifique a conexão de rede!", "error");	
				
			}

			@Override
			protected void cancelled() {
				pBar.setProgress(0);
				super.cancelled();
			}

		};
		Thread t = new Thread(tarefaCargaPg);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(DepartamentoController.class, tarefaCargaPg,
				"Verificando Banco de Dados",false);
		pBar.setProgress(-1);
	}
	
	
	public void criaBaseDados(){
		
		Task<String> tarefaCargaPg = new Task<String>() {
			
					
			@Override
			protected String call() throws Exception {
				
				
					logRet = instDao.criaBanco();	
					
				
				return "-";

			}

			@Override
			protected void succeeded() {
				stg.close();
				pBar.setProgress(1);
							
				criaTabelas();
				
				
			}

			@Override
			protected void failed() {
				stg.close();
//				Util.alertError("Erro ao Conectar ao Banco de Dados, verifique a conexão de rede!");
				util.showAlert("Erro ao Conectar ao Banco de Dados, verifique a conexão de rede!", "error");
				pBar.setProgress(0);
			}

			@Override
			protected void cancelled() {
				pBar.setProgress(0);
				super.cancelled();
			}

		};
		Thread t = new Thread(tarefaCargaPg);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(DepartamentoController.class, tarefaCargaPg,
				"Criando Banco de Dados",false);
		pBar.setProgress(-1);
	}
	
	public void criaTabelas(){
		
		Task<String> tarefaCargaPg = new Task<String>() {
			
						
			@Override
			protected String call() throws Exception {
				
					logRet = instDao.criaTabelas();
				
				return "-";

			}

			@Override
			protected void succeeded() {
				stg.close();
				pBar.setProgress(1);
				avancaTela(1);
			}

			@Override
			protected void failed() {
				stg.close();
//				Util.alertError("Erro ao Conectar ao Banco de Dados, verifique a conexão de rede!");
				util.showAlert("Erro ao Conectar ao Banco de Dados, verifique a conexão de rede!", "error");
				pBar.setProgress(0);
			}

			@Override
			protected void cancelled() {
				pBar.setProgress(0);
				super.cancelled();
			}

		};
		Thread t = new Thread(tarefaCargaPg);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(DepartamentoController.class, tarefaCargaPg,
				"Criando Tabelas",false);
		pBar.setProgress(-1);
	}
	
	
	public void insereEmpresa(){
		
		empresa.setCodigo(codempInstall);
    	empresa.setCodemp(codempInstall);
		empresa.setRazaoSocial(txtRazaoSocial.getText());
		empresa.setNomeFantasia(txtNomeFant.getText());
		empresa.setCnpj(txtCnpj.getText());
		empresa.setCidade(txtCidade.getText());
		empresa.setUf(cboxUF.getSelectionModel().getSelectedItem().getValue());
		empresa.setVersao(txtVersao.getText());
		empresa.setCrcChecksys(CriptografiaAes.codifica(txtRazaoSocial.getText()));
		empresa.setSistema((grpVersaoSistema.getSelectedToggle().getUserData().toString().equals("1") ? "Professional" : 
		grpVersaoSistema.getSelectedToggle().getUserData().toString().equals("2") ? "Enterprise" : "Ultimate"));
		
		empresa.setCep("");
		empresa.setBairro("");
		empresa.setCentralSac((grpCentralAtendimento.getSelectedToggle().getUserData().equals("0") ? "Eptus da Amazônia - Manaus" : "Eptus da Amazônia - Boa Vista"));
		empresa.setTelefoneSac((grpCentralAtendimento.getSelectedToggle().getUserData().equals("0") ? "(92) 3333-9999" : "(95) 3180-0202"));
		empresa.setCnaeFiscal("");
		empresa.setCodEnquadraporte(0);
		empresa.setCodNatjuridica("");
		empresa.setDescReduzida("");
		empresa.setEmailEmail("");
		empresa.setEmailFlagAutenticacao(0);
		empresa.setEmailHostsai("");
		empresa.setEmailLogin("");
		empresa.setEmailNome("");
		empresa.setEmailPassword("");
		empresa.setEmailPortsai("");
		empresa.setEndereco("");
		empresa.setEndNumero("");
		empresa.setFone("");
		empresa.setIe("");
		empresa.setIncMunicipal("");
		empresa.setIndTipoativ(0);
		empresa.setLinhaAdicional("");
		empresa.setRegimeTrib(0);
		empresa.setSuframa("");
		empresa.setWebHomepage("");
		empresa.setCelular("");
		empresa.setCodCidade(13038);
		
		String hash = txtNomeFant.getText()+txtRazaoSocial.getText()+txtCnpj.getText()+txtCidade.getText()+cboxUF.getSelectionModel().getSelectedItem().getValue()+txtVersao.getText();
		empresa.setCrcChecksys(CriptografiaAes.codifica(hash));
		
		Task<String> tarefaCargaPg = new Task<String>() {
			
						
			@Override
			protected String call() throws Exception {
					
				if(tipoInstalacao.equals("0"))
					logRet = instDao.criaEmp(empresa);
				if(tipoInstalacao.equals("1"))
					logRet = instDao.atualizarEmp(empresa);
				
				return "-";

			}

			@Override
			protected void succeeded() {
				stg.close();
				pBar.setProgress(1);
				
			}

			@Override
			protected void failed() {
				stg.close();
//				Util.alertError("Erro ao Criar Empresa");
				util.showAlert("Erro ao Criar Empresa", "error");
				pBar.setProgress(0);
			}

			@Override
			protected void cancelled() {
				pBar.setProgress(0);
				super.cancelled();
			}

		};
		Thread t = new Thread(tarefaCargaPg);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(DepartamentoController.class, tarefaCargaPg,
				"Criando Empresa",false);
		pBar.setProgress(-1);
	}
	
public void insereUsuarioSuporte(){
		
	userSuporte.setNome("SUPORTE EPTUS");
	
	userSuporte.setIdioma("PT");
	
	userSuporte.setCodEmpDispLogin(spinCodemp.getValue().toString());
	
		
		Task<String> tarefaCargaPg = new Task<String>() {
			
						
			@Override
			protected String call() throws Exception {
				
					logRet = instDao.criaEmp(empresa);
				
				return "-";

			}

			@Override
			protected void succeeded() {
				stg.close();
				pBar.setProgress(1);
				
			}

			@Override
			protected void failed() {
				stg.close();
//				Util.alertError("Erro ao Criar Empresa");
				util.showAlert("Erro ao Criar Empresa", "error");
				pBar.setProgress(0);
			}

			@Override
			protected void cancelled() {
				pBar.setProgress(0);
				super.cancelled();
			}

		};
		Thread t = new Thread(tarefaCargaPg);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(DepartamentoController.class, tarefaCargaPg,
				"Criando Empresa",false);
		pBar.setProgress(-1);
	}
	
//	public void atualizaTabelas(){
//		
//		Task<String> tarefaCargaPg = new Task<String>() {
//			
//			
//						
//			@Override
//			protected String call() throws Exception {
//				
//				logRet = instDao.atualizaTabelas();
//				
//				return "-";
//
//			}
//
//			@Override
//			protected void succeeded() {
//				stg.close();
//				pBar.setProgress(1);	
//				
//			}
//
//			@Override
//			protected void failed() {
//				stg.close();
//				Util.alertError("Erro ao Conectar ao Banco de Dados, verifique a conexão de rede!");
//				pBar.setProgress(0);
//			}
//
//			@Override
//			protected void cancelled() {
//				pBar.setProgress(0);
//				super.cancelled();
//			}
//
//		};
//		Thread t = new Thread(tarefaCargaPg);
//		t.setDaemon(true);
//		t.start();
//		stg = ProgressBarForm.showProgressBar(DepartamentoController.class, tarefaCargaPg,
//				"Criando Tabelas");
//		pBar.setProgress(-1);
//	}
//	
//	
//	public void finalizaInstalacao(){
//		
//		empresa.setCodigo(codempInstall);
//    	empresa.setCodemp(codempInstall);
//		empresa.setRazaoSocial(txtRazaoSocial.getText());
//		empresa.setNomeFantasia(txtNomeFant.getText());
//		empresa.setCnpj(txtCnpj.getText());
//		empresa.setCidade(txtCidade.getText());
//		empresa.setUf(cboxUF.getSelectionModel().getSelectedItem().getValue());
//		empresa.setVersao(txtVersao.getText());
//		
//		String hash = txtNomeFant.getText()+txtRazaoSocial.getText()+txtCnpj.getText()+txtCidade.getText()+cboxUF.getSelectionModel().getSelectedItem().getValue()+txtVersao.getText();
//		empresa.setCrcChecksys(CriptografiaAes.codifica(hash));
//		
//		Task<String> tarefaCargaPg = new Task<String>() {			
//						
//			@Override
//			protected String call() throws Exception {
//				
//				//logRet = instDao.atualizarEmp(empresa);
//				
//				return "-";
//
//			}
//
//			@Override
//			protected void succeeded() {
//				stg.close();
//				pBar.setProgress(1);
//				avancaTela(1);
//			}
//
//			@Override
//			protected void failed() {
//				stg.close();
//				Util.alertError("Erro ao Conectar ao Banco de Dados, verifique a conexão de rede!");
//				pBar.setProgress(0);
//			}
//
//			@Override
//			protected void cancelled() {
//				pBar.setProgress(0);
//				super.cancelled();
//			}
//
//		};
//		Thread t = new Thread(tarefaCargaPg);
//		t.setDaemon(true);
//		t.start();
//		stg = ProgressBarForm.showProgressBar(DepartamentoController.class, tarefaCargaPg,
//				"Criando Tabelas");
//		pBar.setProgress(-1);
//	}
//	
	public void avancaTela(int i){
		
		tabPane.getSelectionModel().select(i);
		tabPane.getTabs().get(i - 1).setDisable(true);
		tabPane.getTabs().get(i).setDisable(false);	
		btnRetornar.setDisable(false);
		
		
		switch (i) {
		case 1:
		
			//VERIFIED
			iconDatabase.setGlyphName("CHECK");
			pgbPaso1.setProgress(0.35);

			break;
		case 2:
			if(empresa.getCodigo() != null){
				grpTipoInstalacao.selectToggle(rdbUpgrade1);
				rdbInstalInicial0.setDisable(true);
				rdbUpgrade1.setDisable(false);
			}else{
				grpTipoInstalacao.selectToggle(rdbInstalInicial0);
				rdbUpgrade1.setDisable(true);
				rdbInstalInicial0.setDisable(false);
			}
		     iconCentralAtendimento.setGlyphName("CHECK");
			pgbPaso1.setProgress(0.55);
			
			break;	
		case 3:
			
			iconTipoInstalacao.setGlyphName("CHECK");
			pgbPaso1.setProgress(0.70);
			
			break;
		case 4:
			
			iconNumeroSerie.setGlyphName("CHECK");
			pgbPaso1.setProgress(1);
			
			break;			

		default:
			break;
		}
		
		if(i == 4)
			btnAvancar.setText("FINALIZAR");
	}
	
	//@FXML
	//private TelaPrincipalEptusController testeTelaController;
	
	@FXML
	void actionBtnAvancar(ActionEvent event) throws InstantiationException, IllegalAccessException {
		
		
		
			//aba Gestao de Banco de Dados
			if(tabPane.getSelectionModel().getSelectedIndex() == 0){
				
				if(!util.noEmpty(txtIpServer, txtPorta, txtBaseDados, txtUser)){
									
					instDao = new InstallDAO("MySQL", txtIpServer.getText(), txtPorta.getText(), txtBaseDados.getText(), txtUser.getText(), "18110002");
						
				    criaEstrutura();
					
					tipoVersaoSistema = grpVersaoSistema.getSelectedToggle().getUserData().toString();
					codempInstall = spinCodemp.getValue();
					
				}
			
				}else
				
					//ABA CENTRAL DE ATENDIMENTO
					if(tabPane.getSelectionModel().getSelectedIndex() == 1){
					
					centralAtendimento = grpCentralAtendimento.getSelectedToggle().getUserData().toString();
					
					
					avancaTela(2);					
					
				}else
				
			//ABA TIPO INSTALACAO	
			if(tabPane.getSelectionModel().getSelectedIndex() == 2){
							
					tipoInstalacao = grpTipoInstalacao.getSelectedToggle().getUserData().toString();
					
					avancaTela(3);
					
					if(tipoInstalacao.equals("1")){
					String numserie = empresa.getNoSerie().replace("-", "");
					
					
					txtSerie1.setText(numserie.substring(0, 6));
					txtSerie2.setText(numserie.substring(6, 12));
					txtSerie3.setText(numserie.substring(12, 18));
					txtSerie4.setText(numserie.substring(18, 24));
					}
				}else
			
		
			//NUMERO DE SERIE		
			if(tabPane.getSelectionModel().getSelectedIndex() == 3){
				
				
				
				//if(!util.noEmpty(txtSerie1,txtSerie2, txtSerie3, txtSerie4)){
				if(tipoInstalacao.equals("1")){
				txtRazaoSocial.setText(empresa.getRazaoSocial());
				txtNomeFant.setText(empresa.getNomeFantasia());
				txtCnpj.setText(empresa.getCnpj());
				txtCidade.setText(empresa.getCidade());
				}
				avancaTela(4);
					
				//}
				
				
				
			}else
			
			
				if(tabPane.getSelectionModel().getSelectedIndex() == 4){
					
					numeroSerie = txtSerie1.getText()+"-"+txtSerie2.getText()+"-"+txtSerie3.getText()+"-"+txtSerie4.getText();
					
					empresa.setNoSerie(numeroSerie);
								

					if(!util.noEmpty(txtRazaoSocial, txtNomeFant, txtCnpj,txtCidade)  && !util.noEmpty(txtPwdHash)){

						if((chkAtivaMensal.isSelected() && !util.noEmpty(cboxDiaLibMen)) || !chkAtivaMensal.isSelected()){
						if(!util.noEmpty(txtVersao)){
						    	
									
//							if(tipoInstalacao.equals("0")){
//								insereEmpresa();
//							}
//
//							if(tipoInstalacao.equals("1")){
//								atualizaTabelas();
//							}
							insereEmpresa();
//							util.alertInf("Instalacao Finalizada");
							util.showAlert("Instalacao Finalizada", "information");
							//btnAvancar.setDisable(true);
							//btnRetornar.setDisable(true);
							//	}
						}
					
					//System.exit(0);
				//	System.out.println(empresa.getCodemp());
				}
				
					}
				
			}
			
	}
	@FXML
	void actionBtnFinalizar(ActionEvent event){
		event.consume();
	}

	
	public void populaCboxUF() {

		ObservableList<ComboBoxFilter> listUF = FXCollections.observableArrayList();

		listUF.add(new ComboBoxFilter("AM", 0, "AM"));
		listUF.add(new ComboBoxFilter("RR", 1, "RR"));
		listUF.add(new ComboBoxFilter("RO", 2, "RO"));
		cboxUF.getItems().addAll(listUF);
		cboxUF.getSelectionModel().selectFirst();
		cboxUF.setConverter(new StringConverter<ComboBoxFilter>() {
			@Override
			public ComboBoxFilter fromString(String string) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String toString(ComboBoxFilter object) {
				// TODO Auto-generated method stub
				if (object != null) {
					return object.getValue();
				} else {
					return "";
				}
			}
		});
	}
	
	public void populaCboxDiaAtiva() {

		ObservableList<ComboBoxFilter> listDias = FXCollections.observableArrayList();

		for (int i = 1; i < 32; i++) {
			listDias.add(new ComboBoxFilter(Integer.toString(i), 0, Integer.toString(i)));
		}
		cboxDiaLibMen.getItems().addAll(listDias);
		cboxDiaLibMen.getSelectionModel().selectFirst();
		cboxDiaLibMen.setConverter(new StringConverter<ComboBoxFilter>() {
			@Override
			public ComboBoxFilter fromString(String string) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String toString(ComboBoxFilter object) {
				// TODO Auto-generated method stub
				if (object != null) {
					return object.getValue();
				} else {
					return "";
				}
			}
		});
	}
	


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		
		
	//INICIA O VALOR DO SPINER CODEMP E DEFINE O VALOR MAIOR
	SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, 1);		
	spinCodemp.setValueFactory(valueFactory);
	    
	  //inicializa os valores dos radios buttons
	 util.defineRadioButton(grpTipoBanco, grpCentralAtendimento, grpTipoInstalacao, grpVersaoSistema);
	  
	 util.setDefaultStyle(txtSerie1,txtSerie2,txtSerie3,txtSerie4);
	  
	 util.maxCharacters(6, txtSerie2,txtSerie3,txtSerie4);
	 
	 
	// DEFINE OS TEXTFIELDS QUE SERAO MAIUSCULO
	util.whriteUppercase(txtSerie1,txtSerie2,txtSerie3,txtSerie4, txtRazaoSocial, txtNomeFant, txtCidade);
	 
	 populaCboxUF();
	 populaCboxDiaAtiva();
	 
	    txtBaseDados.setText("eptus3");
	    txtIpServer.setText("Localhost");
	    txtPorta.setText("3306");
	    txtUser.setText("root");
	    
	    txtSerie1.setText("123456");
	    txtSerie2.setText("123456");
	    txtSerie3.setText("123456");
	    txtSerie4.setText("123456");
	    
	    txtRazaoSocial.setText("Empresa Teste Ltda.");
	    txtNomeFant.setText("Empresa Teste");
	    txtCnpj.setText("00.000/00001-00");
	    txtCidade.setText("Manaus");
	    
	    txtVersao.setText("1.01.590");

	    //EVENTO DE CHANGE NO RADIO BUTTON TIPO DE BANCO
	    grpTipoBanco.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				// TODO Auto-generated method stub
				
				if(newValue.getUserData().toString().equals("1")){
					txtSid.setDisable(false);
				}else{
					txtSid.setDisable(true);
				}
			}

			
		});
	    
	    
	    chkAtivaMensal.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if(newValue){
					cboxDiaLibMen.setDisable(false);
				}else{
					cboxDiaLibMen.setDisable(true);
				}
			}
		});
	    
	    //EVENTO DE CHANGE NO RADIO BUTTON TIPO DE BANCO
	    grpVersaoSistema.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				// TODO Auto-generated method stub
				
//				if(newValue.getUserData().toString().equals("1")){
//					
//					System.out.println("ente");
//					paneUltimate.setStyle("-fx-border-color: #00796B;");
//				}else if(newValue.getUserData().toString().equals("2")){
//					System.out.println("profe");
//					
//				}else if(newValue.getUserData().toString().equals("3")){
//					System.out.println("ulti");
//			}

			}
		});
	    
	}

}
