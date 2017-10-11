package controllers.application;
 
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXSpinner;
import com.sun.javafx.robot.FXRobot;
import com.sun.javafx.robot.FXRobotFactory;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javafx.util.StringConverter;
import application.DadosGlobais;
import application.Main;
import connect.ConnectionHib;
import models.configuracoes.Empresa;
import models.configuracoes.EmpresaDAO;
import models.configuracoes.UsuarioDAO;
import tools.animations.FadeInLeftTransition;
import tools.animations.FadeInRightTransition;
import tools.animations.FadeOutUpTransition;
import tools.criptografia.EncryptMD5;
import tools.enums.EnumLogRetornoStatus;
import tools.utils.LogRetorno;
import tools.utils.Util;
import tools.xml.XMLConfig;

/**
 * Class controladora da tela Login
 * 
 * @author Julio Cesar
 * @version 1.0
 **/

public class LoginController implements Initializable, EventHandler<KeyEvent> {

	@FXML
	private AnchorPane panePrincipal, paneLogin, panePreloader;
	@FXML
	private VBox vBoxLogin;
	@FXML
	private StackPane stackPane;
	@FXML
	private Label lblSerie, lblCNPJ, lblVersao, lblBemvindo, lblEptusAM, lblSistema;
	@FXML
	private ComboBox<Empresa> cmbEmpresa;
	@FXML
	private PasswordField txtSenha;
	@FXML
	private Button btnAcessar, btnConnDisp, btnCloseWindows, btnMinWindows;
	@FXML
	public Tooltip btnConnDispTooltip;
	@FXML
	private ImageView preloaderGIF, imgEptusPreloader;
	@FXML
	private JFXSpinner spinLoading;
	@FXML
	private Hyperlink linkWebEptus;

	private UsuarioDAO usHibDAO = new UsuarioDAO();
	private Util util = new Util();
	private String senha;
	private static boolean succes;

	@FXML
	/**
	 * Metodo actionBtnAcessar, ação do botão Acessar da Tela Login, primeiro
	 * criptografar a senha usando o algoritmo MD5 e procuramos a coincidência
	 * no banco de dados
	 * 
	 * @param event
	 *            Evento do botão Accesar
	 */
	void actionBtnAcessar(ActionEvent event) throws InstantiationException {

		senha = txtSenha.getText();
		LogRetorno log = null;
		if (!senha.equals("")) {

			String senhaMD5 = EncryptMD5.getMD5(senha);
			try {
				// System.out.println(11111);
				log = usHibDAO.loginUser(cmbEmpresa.getValue(), senhaMD5);
				// System.out.println(333);
				if (log.getStatus().equals(EnumLogRetornoStatus.SUCESSO)) {
					try {

						new Main().start(new Stage());
						Stage stage = (Stage) panePrincipal.getScene().getWindow();
						stage.close();

					} catch (Exception e) {
						e.printStackTrace();
						// System.out.println(log.getMsg());
					}

				} else {
					Util.alertError(log.getMsg());
					// util.showAlert(log.getMsg(), "error");
				}
			} catch (Exception e) {
				e.printStackTrace();
				Util.alertError(log.getMsg());
				// util.showAlert(log.getMsg(), "error");
			}

		} else {
			Util.alertWarn("O campo Senha é ogrigatorio!");
			// util.showAlert("O campo Senha é ogrigatorio!", "warning");
			txtSenha.requestFocus();
		}
	}

	@FXML
	/**
	 * Metodo actionBtnCloseWindow, ação do botão fechar janela
	 * 
	 * @param event
	 *            Evento do botão Accesar
	 */
	void actionBtnCloseWindow(ActionEvent event) {
		ConnectionHib.closeConnection();
		System.exit(0);
	}

	@FXML
	/**
	 * Metodo actionBtnMinWindow, ação do botão minimizar janela
	 * 
	 * @param event
	 *            Evento botão minimizar janela
	 */
	void actionBtnMinWindow(ActionEvent event) {
		((Stage) panePrincipal.getScene().getWindow()).setIconified(true);
	}

	@FXML
	/**
	 * Metodo actionCmbEmp, ação do combobox Empresa depois de se selecionar uma
	 * empresa, carrega os dados da empresa e preenche os dados no canto
	 * superior esquerdo da janel
	 * 
	 * @param event
	 */
	void actionCmbEmp(ActionEvent event) {

		Empresa result = null;
		try {

			LogRetorno lg = new LogRetorno();
			lg = EmpresaDAO.class.newInstance().getById(cmbEmpresa.getValue().getCodemp());
			result = (Empresa) lg.getObjeto();

		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (result != null) {
			lblVersao.setText(result.getVersao());
			lblSerie.setText(result.getNoSerie());
			lblCNPJ.setText(result.getCnpj());
		}
	}

	/**
	 * Metodo actionbtnConnDisp, ação do botão conexoes disponiveis, abrir
	 * janela com as conexoes gravadas no arquivo de configuração.
	 **/
	@FXML
	/**
	 * Metodo actionbtnConnDisp, ação do botão conexoes disponiveis, abrir
	 * janela com as conexoes gravadas no arquivo de configuração XML.
	 * 
	 * @param event
	 *            Evento botão ConnDisp
	 */
	void actionbtnConnDisp(ActionEvent event) {
		Stage janelaLogin = new Stage();
		try {

			Scene scene = null;
			try {
				scene = new Scene(Util.class.newInstance().openWindow("/views/application/telaConexoes.fxml",
						new TelaConexoesController(this)), 600, 400);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			scene.getStylesheets().add(getClass().getResource("/styles/css/conexoes.css").toExternalForm());
			janelaLogin.setScene(scene);
			janelaLogin.initStyle(StageStyle.TRANSPARENT);
			janelaLogin.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	 /**
	 * Verificar arquivo Configuração do Sistema
	 */
	 public void verificarConfig(){
	
	 Task<String> tarefaCargaPg = new Task<String>() {
	 boolean exist = false;
	 @Override
	 protected String call() throws Exception {
	 System.out.println("sddsds "+ConnectionHib.HOST +" "+ConnectionHib.SERVER);;
	// animaCarregado();
	 XMLConfig.arquivoConfExist();
	 return "-";
	 }
	
	 @Override
	 protected void succeeded() { // System.exit(0);
	
	// if(exist){
	 carregarConfig();
	// }else
	// Util.alertError("Erro ao carregar arquivo de configuração.
//	 "+exceptionProperty().getValue().toString());
	//
	 }
	//
	 @Override
	 protected void failed() {
	 new FadeOutUpTransition(preloaderGIF).play();
	 new FadeOutUpTransition(imgEptusPreloader).play();
	 new FadeOutUpTransition(lblBemvindo).play();
	 new FadeOutUpTransition(lblEptusAM).play();
	 new FadeOutUpTransition(lblSistema).play();
	 paneLogin.setVisible(true);
	 animaFadeInPane();
	// Util.alertWarn("Arquivo de configuração não encontrado ou dados de
//	 Conexão inválidos!");
	 Util.alertError("Erro ao carregar arquivo de configuração."+exceptionProperty().getValue().toString());
	
	 }
	
	 @Override
	 protected void cancelled() {
	
	 super.cancelled();
	 }
	 };
	 Thread t = new Thread(tarefaCargaPg);
	 t.setDaemon(true);
	 t.start();
	 }

	public void carregarConfig() {
		boolean exist = false;
		Task<String> tarefaCargaPg = new Task<String>() {
			@Override
			protected String call() throws Exception {
				// System.out.println("sddsds "+ConnectionHib.HOST +"
				// "+ConnectionHib.SERVER);;
				// animaCarregado();
				succes = ConnectionHib.cargarDadosXML();
				return "-";
			}

			@Override
			protected void succeeded() { // System.exit(0);
				openConnection();
				//
			}

			@Override
			protected void failed() {
				new FadeOutUpTransition(spinLoading).play();
				new FadeOutUpTransition(imgEptusPreloader).play();
				new FadeOutUpTransition(lblBemvindo).play();
				new FadeOutUpTransition(lblEptusAM).play();
				new FadeOutUpTransition(lblSistema).play();
				paneLogin.setVisible(true);
				cmbEmpresa.setDisable(true);
				txtSenha.setDisable(true);
				btnAcessar.setDisable(true);
				animaFadeInPane();
				// Util.alertWarn("Arquivo de configuração não encontrado ou
				// dados de Conexão inválidos!");
				// Util.alertError("Erro ao carregar dados da configuração. " +
				// exceptionProperty().getValue().toString());
				util.showAlert("Erro ao carregar dados da configuração. " + exceptionProperty().getValue().toString(),
						"error");

			}

			@Override
			protected void cancelled() {

				super.cancelled();
			}
		};
		Thread t = new Thread(tarefaCargaPg);
		t.setDaemon(true);
		t.start();
	}

	/**
	 * Metodo atualizarTela, carregar dados que vai ser mostrados na tela
	 **/
	public void atualizarTela() throws SQLException {

		if (ConnectionHib.emf != null) {

			EmpresaDAO empHibDAO = new EmpresaDAO();

			List<Empresa> empDados = empHibDAO.getListEmpresa();

			cmbEmpresa.setConverter(new StringConverter<Empresa>() {

				private Map<String, Empresa> mapEmpresa = new HashMap<String, Empresa>();

				@Override
				public Empresa fromString(String codEmp) {
					// TODO Auto-generated method stub
					return mapEmpresa.get(codEmp);
				}

				@Override
				public String toString(Empresa empresa) {
					// TODO Auto-generated method stub
					mapEmpresa.put(String.valueOf(empresa.getCodemp()), empresa);
					return empresa.getNomeFantasia();
				}
			});
			if (cmbEmpresa.getItems().size() != 0) {
				cmbEmpresa.getItems().clear();
			}
			cmbEmpresa.getItems().addAll(empDados);

			for (int i = 0; i < empDados.toArray().length; i++) {

				if (DadosGlobais.codEmpDefault == empDados.get(i).getCodemp()) {
					cmbEmpresa.getSelectionModel().select(i);
					break;
				}
			}
			LogRetorno lg = new LogRetorno();
			lg = empHibDAO.getById(cmbEmpresa.getValue().getCodemp());
			Empresa emp = (Empresa) lg.getObjeto();
			if (emp != null) {
				lblVersao.setText(emp.getVersao());
				lblSerie.setText(emp.getNoSerie());
				lblCNPJ.setText(emp.getCnpj());

				DadosGlobais.codEmpDefault = cmbEmpresa.getValue().getCodemp();
				DadosGlobais.empresa = cmbEmpresa.getValue().getNomeFantasia();
			} else {
				// Util.alertError("Erro na pesquisa dos dados da Empresa!");
				util.showAlert("Erro na pesquisa dos dados da Empresa!", "error");
			}

			// result.close();
			btnConnDispTooltip.setText(ConnectionHib.SERVER + "\n" + ConnectionHib.HOST);

		} else {
			// Util.alertError("Error al conectar la BBDD");
			util.showAlert("Error al conectar la BBDD", "error");
			System.exit(0);
		}

	}

	public void openConnection() {
		Task<String> tarefaCargaPg = new Task<String>() {
			@Override
			protected String call() throws Exception {
				// System.out.println("sddsds "+ConnectionHib.HOST +"
				// "+ConnectionHib.SERVER);;
				// animaCarregado();
				if (succes){
//					System.out.println(ConnectionHib.HOST);
					ConnectionHib.createConnection();
					
				}else{
					
					new FadeOutUpTransition(spinLoading).play();
					new FadeOutUpTransition(imgEptusPreloader).play();
					new FadeOutUpTransition(lblBemvindo).play();
					new FadeOutUpTransition(lblEptusAM).play();
					new FadeOutUpTransition(lblSistema).play();
					paneLogin.setVisible(true);
					cmbEmpresa.setDisable(true);
					txtSenha.setDisable(true);
					btnAcessar.setDisable(true);
					animaFadeInPane();
				}

				return "-";
			}

			@Override
			protected void succeeded() { // System.exit(0);

				// new FadeOutUpTransition(preloaderGIF).play();
				new FadeOutUpTransition(spinLoading).play();
				new FadeOutUpTransition(imgEptusPreloader).play();
				new FadeOutUpTransition(lblBemvindo).play();
				new FadeOutUpTransition(lblEptusAM).play();
				new FadeOutUpTransition(lblSistema).play();
				paneLogin.setVisible(true);
				// lblSerie.setFont(Font.font(DadosGlobais.fontSistema.getFamily(),
				// 18));
				animaFadeInPane();
				// System.out.println(ConnectionHib.STATUS);

				if (ConnectionHib.STATUS) {

					try {

						atualizarTela();

						txtSenha.requestFocus();
						FXRobot robot = FXRobotFactory.createRobot(panePrincipal.getScene());
						robot.keyPress(javafx.scene.input.KeyCode.ENTER);
					} catch (SQLException e) {
						e.printStackTrace();
					}

				} else {
					Util.alertWarn("Arquivo de configuração não encontrado ou dados de Conexão inválidos!");
					// util.showAlert("Arquivo de configuração não encontrado ou
					// dados de Conexão inválidos!", "warning");
					// cmbEmpresa.setDisable(true);
					// txtSenha.setDisable(true);
					// btnAcessar.setDisable(true);
				}
			}

			@Override
			protected void failed() {
				new FadeOutUpTransition(spinLoading).play();
				new FadeOutUpTransition(imgEptusPreloader).play();
				new FadeOutUpTransition(lblBemvindo).play();
				new FadeOutUpTransition(lblEptusAM).play();
				new FadeOutUpTransition(lblSistema).play();
				paneLogin.setVisible(true);
				cmbEmpresa.setDisable(true);
				txtSenha.setDisable(true);
				btnAcessar.setDisable(true);
				animaFadeInPane();
				// Util.alertWarn("Arquivo de configuração não encontrado ou
				// dados de Conexão inválidos!");
				if (exceptionProperty().getValue().toString().equals("java.lang.NullPointerException"))
					// Util.alertError("ERRO, Arquivo de configuraçõa foi
					// corrompido.");
					util.showAlert("ERRO, Arquivo de configuraçõa foi corrompido.", "error");
				else
					// Util.alertError("Error no sistema
					// "+exceptionProperty().getValue().toString());
					util.showAlert("Error no sistema " + exceptionProperty().getValue().toString(), "error");

			}

			@Override
			protected void cancelled() {

				super.cancelled();
			}
		};
		Thread t = new Thread(tarefaCargaPg);
		t.setDaemon(true);
		t.start();

	}

	public void animaFadeInPane() {
		FadeTransition fadeTransit = new FadeTransition(Duration.seconds(1), paneLogin);
		fadeTransit.setFromValue(0);
		fadeTransit.setToValue(1);
		fadeTransit.play();
	}

	public void animaFadeOutPane() {
		paneLogin.setVisible(false);
		txtSenha.setDisable(false);
		cmbEmpresa.setDisable(false);
		btnAcessar.setDisable(false);
	}

	public void animaCarregado() {
		new FadeInRightTransition(lblEptusAM).play();
		new FadeInRightTransition(lblSistema).play();
		new FadeInLeftTransition(lblBemvindo).play();
		new FadeInLeftTransition(spinLoading).play();
	}

	private final static String ENDERECO_XML_CONFIG = "c:/EptusAM/";

	@Override
	/**
	 * Cuando se inicia por primera vez Conexion.conn siempre va ser null, por
	 * eso creamos la conexion, cuando es cargado por la ventana Conexiones ya
	 * tiene una conexion establecida
	 **/
	public void initialize(URL arg0, ResourceBundle arg1) {
		boolean comprovarEndereco = false;
		// SETA O FOCO INICIAL NO TEXTFIELD DE SENHA
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
//				System.out.println("11111111");
				animaCarregado();
//				System.out.println("222222222222");
				txtSenha.setText("123");
//				txtSenha.requestFocus();
//				System.out.println("3333333333");
////				FXRobot robot = FXRobotFactory.createRobot(panePrincipal.getScene());
////				robot.keyPress(javafx.scene.input.KeyCode.ENTER);
//				System.out.println("44444444");
				
				
			}
		});

		lblSerie.setText("");
		lblCNPJ.setText("");
		lblVersao.setText("");

		// DadosGlobais.fontSistema = Util.FontSystem("Roboto"); //CARREGAR
		// FONTS DO SISTEMA

		// COMPROVAR EXISTE PASTAS DA CONFIGURAÇÃO DO SISTEMA
		File file = new File(ENDERECO_XML_CONFIG);
		if (file.exists())
			comprovarEndereco = true;

		else {
			comprovarEndereco = false;
			Util.alertError("Não foi possível achar o endereço de configuração. Solicite assistencia técnica. ");
			// util.showAlert("Não foi possível achar o endereço de
			// configuração. Solicite assistencia técnica.", "error");
			actionBtnCloseWindow(null);
		}

		if (comprovarEndereco) {
			if (ConnectionHib.emf == null) {
				// openConnection();
				carregarConfig();
			}
		}

		// txtSenha.setText("123");

		txtSenha.setOnKeyPressed(this);

		// if (ConnectionHib.emf == null) {
		// ConnectionHib.createConnection();
		//
		// if (ConnectionHib.STATUS == true) {
		//
		// try {
		// atualizarTela();
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		//
		// } else {
		// Util alert = new Util();
		// Util.alertWarn("Arquivo de configuração não encontrado ou dados de
		// Conexão inválidos!");
		// cmbEmpresa.setDisable(true);
		// txtSenha.setDisable(true);
		// btnAcessar.setDisable(true);
		// }
		//
		// } else {
		// try {
		// atualizarTela();
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		// }

		cmbEmpresa.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				Empresa result = null;
				LogRetorno lg = new LogRetorno();
				// System.out.println(cmbEmpresa.getValue().getCodemp());
				try {
					if (!cmbEmpresa.getSelectionModel().isEmpty())

						lg = EmpresaDAO.class.newInstance().getById(cmbEmpresa.getValue().getCodemp());
					result = (Empresa) lg.getObjeto();

				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (result != null) {
					lblVersao.setText(result.getVersao());
					lblSerie.setText(result.getNoSerie());
					lblCNPJ.setText(result.getCnpj());
				}
			}
		});

		panePrincipal.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			switch (event.getCode()) {
			case ESCAPE:
				actionBtnCloseWindow(null);
				break;

			}
		});		

	}

	@Override
	/**
	 * Capturar eventos de teclado
	 */
	public void handle(KeyEvent arg0) {
		if (arg0.getCode().equals(KeyCode.ENTER)) {
			try {
				actionBtnAcessar(null);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				System.out.println("Erro Login");
			}
		}
	}

}