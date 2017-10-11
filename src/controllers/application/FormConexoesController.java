package controllers.application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javax.xml.bind.JAXBException;

import org.controlsfx.control.Notifications;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import connect.ConnectionHib;
import controllers.configuracoes.UsuarioController;
import controllers.utils.ProgressBarForm;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import tools.utils.Util;
import tools.xml.Server;
import tools.xml.XMLConfig;

/**
 * Clase Controladora do Formulario Conexão
 * 
 * @author Julio Cesar
 * @version 1.0
 *
 */
public class FormConexoesController implements Initializable {
	@FXML
	private TelaConexoesController telaPai;
	@FXML
	private AnchorPane anchorPane;
	@FXML
	private Button btnFechar, btnTestConn, btnGravarConn;
	@FXML
	private CheckBox chkDefault;
	@FXML
	private TextField txtCodEmp, txtIdServer, txtNomConn, txtHost, txtPort, txtNomeBD, txtSIG, txtUser;
	@FXML
	private PasswordField txtPassword;
	@FXML
	private ToggleGroup SgbdGroup;
	@FXML
	private RadioButton rdBtnMysql, rdBtnOracle;

	Util util = new Util();
	private Stage stg;

	public FormConexoesController(TelaConexoesController telaFilho) {
		super();
		this.telaPai = telaFilho;
	}

	@FXML
	/**
	 * Ação botão fechar janela
	 * 
	 * @param event
	 */
	void actionBtnFechar(ActionEvent event) {
		Util.closeWindow(anchorPane);
	}

	@FXML
	/**
	 * Ação botão gravar
	 * 
	 * @param event
	 */
	void actionBtnGravarConn(ActionEvent event) throws JAXBException, Exception {
		
		boolean vacio = util.noEmpty(txtIdServer, txtNomConn, txtHost, txtPort, txtNomeBD, txtUser, txtPassword,
				txtCodEmp);
		
			
		if (!vacio) {
			Task<String> tarefaGravarConn = new Task<String>() {				
				boolean teste = false;					
				
				@Override
				protected String call() throws Exception {
					// System.out.println("sddsds "+ConnectionHib.HOST +"
					// "+ConnectionHib.SERVER);;
					// animaCarregado();
					
					XMLConfig.createNewServers(Integer.parseInt(txtIdServer.getText()), txtNomConn.getText(), (rdBtnMysql.isSelected()) ? "MySQL" : "Oracle",
							txtHost.getText(), Integer.parseInt(txtPort.getText()), txtNomeBD.getText(), txtSIG.getText(),
							txtUser.getText(), txtPassword.getText(), chkDefault.isSelected(),
							Integer.parseInt(txtCodEmp.getText()));
					return "-";
				}

				@Override
				protected void succeeded() { 
					stg.close();
					//Util.alertInf("Novo registro no arquivo de configuração!!");
//					util.showAlert("Novo registro no arquivo de configuração!!", "information");			
							
					Util.closeWindow(anchorPane);
					Util.showNotification("O registro foi gravado com sucesso!");					
					telaPai.carregaTableView();
									
					
				}

				@Override
				protected void failed() {					
					stg.close();
					util.tratamentoExcecao(exceptionProperty().getValue().toString(), "[ FormConexoesController.actionBtnGravarConn() - SALVAR ]");
					
				}

				@Override
				protected void cancelled() {
					super.cancelled();
				}
			};
			Thread t = new Thread(tarefaGravarConn);
			t.setDaemon(true);
			t.start();
			stg = ProgressBarForm.showProgressBar(FormConexoesController.class, tarefaGravarConn, "Salvando nova conexão...", false);    			
			
		}
	}

	@FXML
	/**
	 * Ação botão Testar conexão
	 * 
	 * @param event
	 */
	void actionBtnTestConn(ActionEvent event) {	
		
		Task<String> tarefaTestarConn = new Task<String>() {
			boolean teste = false;
			@Override
			protected String call() throws Exception {
				
				teste = ConnectionHib.testarConnection((rdBtnMysql.isSelected()) ? "MySQL" : "Oracle",txtHost.getText(), Integer.parseInt(txtPort.getText()),
						txtNomeBD.getText(), txtUser.getText(), txtPassword.getText());
				
				return "-";
			}

			@Override
			protected void succeeded() { 
				stg.close();
				if (teste)
					//Util.alertInf("Conexão foi sucesso!");
					Util.showNotification("A conexão foi testada com sucesso!");
				else
//					Util.alertError("Erro tentando fazer ligação com o Banco de Dados!");
					util.showAlert("Erro tentando fazer ligação com o Banco de Dados!", "error");
					
					
				
				
			}

			@Override
			protected void failed() {					
				stg.close();
				util.tratamentoExcecao(exceptionProperty().getValue().toString(), "[ FormConexoesController.actionBtnTestConn() - TEST ]");				
			}

			@Override
			protected void cancelled() {
				super.cancelled();
			}
		};
		Thread t = new Thread(tarefaTestarConn);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(FormConexoesController.class, tarefaTestarConn, "Testando conexão...", false);
	}

	@FXML
	/**
	 * metodo para capturar eventos de teclado
	 * 
	 * @param event
	 */
	void actionKeys(KeyEvent event) throws JAXBException, Exception {
		switch (event.getCode()) {
		case ESCAPE:// Sair
			actionBtnFechar(null);
			break;
		case F3:// Testar Conexão
			actionBtnTestConn(null);
			break;
		case F6:// Gravar Registro
			actionBtnGravarConn(null);
			break;
		default:
			break;
		}
	}

	@Override
	/**
	 * Metodo initialize, dependedo das variables idConexao e xmConf vai
	 * atualiçar ou insertar
	 */
	public void initialize(URL location, ResourceBundle resources) {
		anchorPane.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			try {
				actionKeys(event);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		txtPort.setText("3306");
		
		ArrayList<Server> xmlConf;
		try {
			xmlConf = XMLConfig.getListServers();
			if (TelaConexoesController.idConexao != 0 && xmlConf != null) {
//				chkDefault.setDisable(true);
				System.out.println("fkdjfkjlkfjdjjtweruu0943t349439");
				chkDefault.setSelected(false);
				for (int i = 0; i < xmlConf.toArray().length; i++) {
					if (xmlConf.get(i).getIdServer() == TelaConexoesController.idConexao) {
						txtIdServer.setText(String.valueOf(xmlConf.get(i).getIdServer()));
						txtNomConn.setText(xmlConf.get(i).getNome());
						txtHost.setText(xmlConf.get(i).getHost());
						txtPort.setText(String.valueOf(xmlConf.get(i).getPort()));
						txtNomeBD.setText(xmlConf.get(i).getBd());
						txtUser.setText(xmlConf.get(i).getUser());
						txtPassword.setText(xmlConf.get(i).getPsw());
						txtCodEmp.setText(String.valueOf(xmlConf.get(i).getEmpresaDefault()));
						chkDefault.setSelected(xmlConf.get(i).isServerDefault());
						if(xmlConf.get(i).getSgbd().equals("MySQL")){
							rdBtnMysql.setSelected(true);
						}else{
							rdBtnOracle.setSelected(true);
						}
					}
				}

			} else if (TelaConexoesController.idConexao == 0 && xmlConf != null) {
				chkDefault.setDisable(true);
				chkDefault.setSelected(false);
				novaConexao();
			} else {
				chkDefault.setSelected(true);
				chkDefault.setDisable(true);
				novaConexao();
			}
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		rdBtnMysql.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(rdBtnMysql.isSelected())
					txtPort.setText("3306");
			}
		});
		
		rdBtnOracle.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(rdBtnOracle.isSelected())
					txtPort.setText("1521");
			}
		});
	}

	/**
	 * Metodo Nova Conexão
	 */
	public void novaConexao() {
		txtNomConn.setEditable(true);
		if (XMLConfig.highID() != null) {
			txtIdServer.setText(String.valueOf(XMLConfig.highID().getHighID() + 1));
		} else
			txtIdServer.setText(String.valueOf(1));
		util.limpar(txtNomConn, txtHost, txtPort, txtNomeBD, txtUser, txtPassword, txtCodEmp, txtSIG);
	}
}