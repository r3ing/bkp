package controllers.application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.xml.bind.JAXBException;

import org.controlsfx.control.Notifications;

import application.DadosGlobais;
import connect.ConnectionHib;
import controllers.configuracoes.UsuarioController;
import controllers.utils.ProgressBarForm;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import tools.animations.FadeOutUpTransition;
import tools.utils.Util;
import tools.xml.Server;
import tools.xml.XMLConfig;

/**
 * Class controladora da tela Conexões
 * 
 * @author Julio Cesar
 * @version 1.0
 **/

public class TelaConexoesController {

	static int idConexao = 0;// se o valor é 0 inserir nova conexion, se é maior
								// q 0 atualizar o idServer.
	Util ut = new Util();

	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;
	@FXML
	private AnchorPane anchorPaneForm, anchorPaneConexoes;
	@FXML
	private VBox panelDereito;
	@FXML
	private Button btnSair, btnNovoConexao, btnExcluirConexao, btnAtualizarConexao;
	@FXML
	private TableView<Server> tbServers;
	@FXML
	private TableColumn<Server, StringProperty> colEnderco;
	@FXML
	private TableColumn<Server, IntegerProperty> colPort;
	@FXML
	private TableColumn<Server, StringProperty> colBD;
	@FXML
	private TableColumn<Server, StringProperty> colStatus;
	@FXML
	private TableColumn<Server, StringProperty> colSGBD;
	@FXML
	private ContextMenu contextMenuTabla;
	@FXML
	private MenuItem contMniAtualizar, contMniExcluir;
	@FXML
	private MenuItem contMniTestar;

	private LoginController loginPae;
	private static Stage stg;
	private Util util = new Util();

	public TelaConexoesController(LoginController login) {
		this.loginPae = login;
	}

	@FXML
	/**
	 * Ação do botão Atualizar
	 * 
	 * @param event
	 */
	void actionBtnAtualizarConexao(ActionEvent event) throws JAXBException, Exception {
		listConexoes();
	}

	@FXML
	/**
	 * Ação o botão Excluir
	 * 
	 * @param event
	 */
	void actionBtnExcluirConexao(ActionEvent event) {
		if (!tbServers.getSelectionModel().isEmpty()) {
			boolean confirm = ut.alertConfirmar("Confirmar para Excluir");
//			boolean confirm  = util.showAlert("Confirmar para Excluir", "confirmation");
			if (confirm) {
				try {
					excluirConexao();
				} catch (JAXBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} else {
				Util.alertInf("Você deve selecionar um item para excluir!!");
//				util.showAlert("Você deve selecionar um item para excluir!!", "information");
			}
		}
	}

	@FXML
	/**
	 * Ação do MenuItem do ContextMenu Excluir
	 * 
	 * @param event
	 */
	void actionContMniExcluirDados(ActionEvent event) {
		if (!tbServers.getSelectionModel().isEmpty()) {
			actionBtnExcluirConexao(null);
		}
	}

	@FXML
	/**
	 * Ação do MenuItem do ContextMenu Testar Conexão
	 * 
	 * @param event
	 */
	void actionMniTestarConexao(ActionEvent event) {
		if (!tbServers.getSelectionModel().isEmpty()) {

			Task<String> tarefaTestarConn = new Task<String>() {
				boolean teste = false;

				@Override
				protected String call() throws Exception {
					teste = ConnectionHib.testarConnection(
							(tbServers.getSelectionModel().getSelectedItem().getSgbd().equals("MySQL")) ? "MySQL"
									: "Oracle",
							tbServers.getSelectionModel().getSelectedItem().getHost(),
							tbServers.getSelectionModel().getSelectedItem().getPort(),
							tbServers.getSelectionModel().getSelectedItem().getBd(),
							tbServers.getSelectionModel().getSelectedItem().getUser(),
							tbServers.getSelectionModel().getSelectedItem().getPsw());
					return "-";
				}

				@Override
				protected void succeeded() {
					stg.close();
					if (teste)
//						Util.alertInf("Conexão foi sucesso!");
						Util.showNotification("A conexão foi testada com sucesso!");
//						util.showAlert("Conexão foi sucesso!", "information");
					else
//						Util.alertError("Erro tentando fazer ligação com Bases de Dados!");
						util.showAlert("Erro tentando fazer ligação com Bases de Dados!", "error");
				}

				@Override
				protected void failed() {
					stg.close();
					// if(ConnectionHib.logRet.getStatus().equals(0)){
					// System.out.println("22232");
					// util.tratamentoExcecao(ConnectionHib.logRet.getMsg(), "[
					// TelaConexoesController.actionMniTestarConexao() - TEST
					// ]");
					// }
					util.tratamentoExcecao(exceptionProperty().getValue().toString(),
							"[ TelaConexoesController.actionMniTestarConexao() - TEST ]");

				}

				@Override
				protected void cancelled() {

					super.cancelled();
				}
			};
			Thread t = new Thread(tarefaTestarConn);
			t.setDaemon(true);
			t.start();
			stg = ProgressBarForm.showProgressBar(TelaConexoesController.class, tarefaTestarConn, "Testando conexão...",
					false);

		}

	}

	@FXML
	void actionContMniAtualizarDados(ActionEvent event) {
		idConexao = tbServers.getSelectionModel().getSelectedItem().getIdServer();
		Stage ventDialg = new Stage();
		try {
			Scene scene = new Scene(
					ut.openWindow("/views/application/formConexoes.fxml", new FormConexoesController(this)), 281, 379);
			scene.getStylesheets().add(getClass().getResource("/styles/css/conexoes.css").toExternalForm());
			ventDialg.setScene(scene);
			ventDialg.initStyle(StageStyle.TRANSPARENT);
			ventDialg.initModality(Modality.APPLICATION_MODAL);
			ventDialg.getIcons().add(new Image(getClass().getResource("/styles/img/favicon.ico").toExternalForm()));
			ventDialg.centerOnScreen();
			ventDialg.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	/**
	 * Ação botão Nova Conexão
	 * 
	 * @param event
	 */
	void actionBtnNovoConexao(ActionEvent event) {
		idConexao = 0;
		Stage ventDialg = new Stage();
		try {
			Scene scene = new Scene(
					ut.openWindow("/views/application/formConexoes.fxml", new FormConexoesController(this)), 281, 379);
			scene.getStylesheets().add(getClass().getResource("/styles/css/conexoes.css").toExternalForm());
			ventDialg.setScene(scene);
			ventDialg.initStyle(StageStyle.TRANSPARENT);
			ventDialg.initModality(Modality.APPLICATION_MODAL);
			ventDialg.centerOnScreen();
			ventDialg.show();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@FXML
	/**
	 * Ação botão Sair fechar janela
	 * 
	 * @param event
	 */
	void actionBtnSair(ActionEvent event) {
		Util.closeWindow(anchorPaneConexoes);
	}

	/**
	 * Metodo que carrega o TableView com os dados do XML de configuracao
	 * 
	 * @throws Exception
	 * @throws JAXBException
	 */
	public boolean carregaTableView() {
		ArrayList<Server> listServer;
		boolean succes = false;
		try {
			
			listServer = XMLConfig.getListServers();
			ObservableList<Server> data = null;
			if (listServer != null){
				data = FXCollections.observableArrayList(listServer);
				succes = true;
			}
				

			colEnderco.setCellValueFactory(new PropertyValueFactory<Server, StringProperty>("host"));
			colPort.setCellValueFactory(new PropertyValueFactory<Server, IntegerProperty>("port"));
			colBD.setCellValueFactory(new PropertyValueFactory<Server, StringProperty>("bd"));
			colStatus.setCellValueFactory(new PropertyValueFactory<Server, StringProperty>("nome"));
			colSGBD.setCellValueFactory(new PropertyValueFactory<Server, StringProperty>("sgbd"));
			tbServers.setItems(data);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return succes;
	}

	/**
	 * Metodo que exibe as conexoes no tableView
	 * 
	 * @throws Exception
	 * @throws JAXBException
	 */
	public void listConexoes() throws JAXBException, Exception {
		// ArrayList<Server> listServer = XMLConfig.getListServers();
		// if (listServer != null) {

		if(carregaTableView()){
		tbServers.setRowFactory(row -> {
			TableRow<Server> rows = new TableRow<Server>();
			rows.setOnMouseClicked(evtMouse -> {
				if (evtMouse.getClickCount() >= 2 && (!rows.isEmpty())
						&& evtMouse.getButton().equals(MouseButton.PRIMARY)) {
					try {
						ConnectionHib.closeConnection();
					} catch (Exception e) {

						e.printStackTrace();
					}
					ConnectionHib.HOST = rows.getItem().getHost();
					ConnectionHib.PORT = rows.getItem().getPort();
					ConnectionHib.BD = rows.getItem().getBd();
					ConnectionHib.SGBD = rows.getItem().getSgbd();
					ConnectionHib.USER = rows.getItem().getUser();
					ConnectionHib.PASSWORD = rows.getItem().getPsw();
					ConnectionHib.SERVER = rows.getItem().getNome();
					DadosGlobais.codEmpDefault = rows.getItem().getEmpresaDefault();

					loginPae.animaFadeOutPane();

					Util.closeWindow(anchorPaneConexoes);
					try {
						loginPae.animaCarregado();
						loginPae.openConnection();

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			return rows;
		});
		}
	}

	/**
	 * Metodo pra excluir conexão selecionada
	 */
	public void excluirConexao() throws JAXBException, Exception {
		Task<String> tarefaExcluir = new Task<String>() {
			@Override
			protected String call() throws Exception {
				XMLConfig.excluirServer(tbServers.getSelectionModel().getSelectedItem().getIdServer());
				return "-";
			}
//
			@Override
			protected void succeeded() {
				stg.close();	
				Util.showNotification("O registro foi excluido com sucesso!");
								
				carregaTableView();
			}

			@Override
			protected void failed() {
				 stg.close();
				util.tratamentoExcecao(exceptionProperty().getValue().toString(),
						"[ TelaConexoesController.excluirConexao() - EXCLUIR ]");
			}

			@Override
			protected void cancelled() {
				super.cancelled();
			}
		};
		Thread t = new Thread(tarefaExcluir);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(TelaConexoesController.class, tarefaExcluir, "Excluindo conexão...",
				false);	
//		
	}

	@FXML
	void initialize() throws JAXBException, Exception {
		assert btnSair != null : "fx:id=\"btnSair\" was not injected: check your FXML file 'formServer.fxml'.";
		assert btnNovoConexao != null : "fx:id=\"btnNovoConexao\" was not injected: check your FXML file 'formServer.fxml'.";
		assert btnExcluirConexao != null : "fx:id=\"btnExcluirConexao\" was not injected: check your FXML file 'formServer.fxml'.";
		assert btnAtualizarConexao != null : "fx:id=\"btnAtualizarConexao\" was not injected: check your FXML file 'formServer.fxml'.";
		assert tbServers != null : "fx:id=\"tbServers\" was not injected: check your FXML file 'formServer.fxml'.";
		listConexoes();
	}

}
