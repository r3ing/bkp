package controllers.recursosHumanos;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import org.controlsfx.control.textfield.CustomTextField;

import com.sun.javafx.scene.control.skin.TableHeaderRow;

import application.DadosGlobais;
import controllers.compras.ItemController;
import controllers.utils.ConfigColumnController;
import controllers.utils.PrintExportController;
import controllers.utils.ProgressBarForm;

import org.controlsfx.control.PopOver;
import org.controlsfx.control.PopOver.ArrowLocation;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TabPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import models.configuracoes.NivelAcesso;
import models.recursosHumanos.Funcionario;
import models.recursosHumanos.FuncionarioDAO;
import tools.controls.ComboBoxFilter;
import tools.enums.EnumLogRetornoStatus;
import tools.enums.EnumNivelAcesso;
import tools.enums.EnumTipoFaturamento;
import tools.enums.EnumTipoFuncionario;
import tools.reports.TableConfPrint;
import tools.utils.LogRetorno;
import tools.utils.Util;
import tools.xml.UserDefinedColumn;
import tools.xml.XMLTableColumns;

public class FuncionarioController implements Initializable {

	@FXML
	private AnchorPane anchorPanePrincipal;

	@FXML
	private AnchorPane anchorPaneListagem;

	@FXML
	private ToolBar toolBar;

	@FXML
	private Button btnInsertGrid;

	@FXML
	private Button btnDelete;

	@FXML
	private Button btnRefresh;

	@FXML
	private Button btnFilter;

	@FXML
	private Button btnPrint;

	@FXML
	private Button btnConfig;

	@FXML
	private ToggleButton toggleHelp;

	@FXML
	private SplitPane splitPaneFilter;

	@FXML
	private AnchorPane anchorPaneFilter;

	@FXML
	private ComboBox<ComboBoxFilter> cboxFilterColumn;

	@FXML
	private ComboBox<ComboBoxFilter> cboxFlagAtivo;

	@FXML
	private Label lblCboxFilterColumn;

	@FXML
	private Label lblCboxFlagAtivo;

	@FXML
	private CustomTextField txtFilterColumn;

	@FXML
	private TableView<Funcionario> tbView;

	@FXML
	private TableColumn<Funcionario, Integer> tbColCodigo;

	@FXML
	private TableColumn<Funcionario, String> tbColDescricao;

	@FXML
	private TableColumn<Funcionario, Integer> tbColAtivoInat;

	@FXML
	private Label lblTotalLinhas;

	@FXML
	private Label lblNumLinhas;

	@FXML
	private ProgressBar pBar;

	@FXML
	private AnchorPane anchorPaneDetalhes;

	@FXML
	private Button btnClose;

	@FXML
	private Label lblTitleFormCad;

	@FXML
	private Label lblCodigo;

	@FXML
	private TextField txtCodigo;
	
	@FXML
	private Label lblTipoFuncionario;
	
	@FXML
	private ComboBox<ComboBoxFilter> cboxTipoFuncionario;
		
	@FXML
	private Label lblDescricao;

	@FXML
	private TextField txtDescricao;

	@FXML
	private Button btnInsert;

	@FXML
	private Button btnSave;

	@FXML
	private Button btnCancel;

	@FXML
	private ContextMenu contextMenu = null;

	// ATRIBUTOS DA CLASSE
	private ObservableList<Funcionario> listaRegistros = null;
	private ObservableList<ComboBoxFilter> listComboBoxFilter = FXCollections.observableArrayList();
	private List<TableConfPrint> tableShowPrintList = new ArrayList<TableConfPrint>();
	boolean flagPaneFilter = true;// ATRIBUTO UTILIZADO PARA CONTROLAR O STATUS
									// DO PAINEL DO FILTRO ABERTO OU FECHADO
	private List<Integer> paramFlagAtivo = Arrays.asList(1);// ATRIBUTO INICIAL
															// DO PARAMENTO DE
															// FLAGATIVO
															// UTILIZADO NAS
															// BUSCAS DE
															// REGISTROS
	private NivelAcesso nivAcessoPermissao;// ATRIBUTO COM OS NIVEIS DE ACESSO
											// DO USUARIO SOBRE OS REGISTROS
											// (EXCLUSAO,INSERCAO,ETC)
	private TabPane tabPrincipal;// TAB PANE PRINCIPAL PARA ABRIR NOVAS GUIAS A
									// PARITR DA TELA DE CADASTRO
	private String fileNameConfigColum = "Grid-Cad-Funcionario"; 

	// OBJETOS INSTACIADOS NA CLASSE
	private Funcionario entidadeBean = new Funcionario();
	private FuncionarioDAO entidadeDao = new FuncionarioDAO();
	private Util util = new Util();
	private PopOver popOver = new PopOver();
	private static Stage stg;

	@FXML
	void actionBtnCancel(ActionEvent event) {

		Util.setDefaultStyle(txtCodigo, txtDescricao);
		Util.limpar(txtCodigo, txtDescricao);
		loadByID(true, null, false);

	}

	@FXML
	void actionBtnClose(ActionEvent event) {

		Util.fechaTelaCadastro(anchorPaneListagem, anchorPaneDetalhes);
		if (tbView.getItems().size() > 0) {
			for (int i = 0; i < tbView.getItems().size(); i++) {

				if (tbView.getItems().get(i).getCodigo().toString().equals(txtCodigo.getText())) {
					tbView.getSelectionModel().select(i);
					tbView.scrollTo(i);

					break;
				}
			}
			tbView.requestFocus();
		} else {
			txtFilterColumn.requestFocus();
		}
		Util.setDefaultStyle(txtCodigo, txtDescricao);

	}

	@FXML
	void actionBtnConfig(ActionEvent event) {

		try {
			stg = new Stage();
			Scene scene = new Scene(util.openWindow("/views/utils/viewConfigColumns.fxml",
					new ConfigColumnController(FuncionarioController.class, tbView, fileNameConfigColum)));
			stg.setScene(scene);
			stg.initStyle(StageStyle.TRANSPARENT);
			stg.initModality(Modality.APPLICATION_MODAL);
			stg.show();
		} catch (IOException e) {
			util.tratamentoExcecao(e.getMessage().toString(), "[ FuncionarioController.actionBtnConfig() ]");
		}

	}

	@FXML
	void actionBtnDelete(ActionEvent event) {

		if ((entidadeBean.getFlagAtivo().equals(1) && nivAcessoPermissao.getFlagDisable().equals(1))
				|| (entidadeBean.getFlagAtivo().equals(0) && nivAcessoPermissao.getFlagEnable().equals(1))) {

			if (util.showAlert(
					DadosGlobais.resourceBundle.getString("alertConfirmOprExcluir") + " "
							+ (entidadeBean.getFlagAtivo().equals(1)
									? DadosGlobais.resourceBundle.getString("oprExcluir")
									: DadosGlobais.resourceBundle.getString("oprAtivar"))
							+ " " + DadosGlobais.resourceBundle.getString("funcionarioController.alertConfirmOprExcluir"),
					"confirmation")) {

				entidadeBean.setFlagAtivo(entidadeBean.getFlagAtivo().equals(1) ? 0 : 1);

				Task<String> tarefaCargaPg = new Task<String>() {
					@Override
					protected String call() throws Exception {

						entidadeDao.delete(entidadeBean);

						return "-";

					}

					@Override
					protected void succeeded() {
						stg.close();
						pBar.setProgress(1);
						updateTbView("DELETE");

					}

					@Override
					protected void failed() {
						stg.close();
						util.tratamentoExcecao(exceptionProperty().getValue().toString(),
								"[ FuncionarioController.actionBtnDelete() ]");
						pBar.setProgress(0);
					}

					@Override
					protected void cancelled() {
						tbView.getItems().clear();
						pBar.setProgress(0);
						super.cancelled();
					}

				};
				Thread t = new Thread(tarefaCargaPg);
				t.setDaemon(true);
				t.start();
				stg = ProgressBarForm.showProgressBar(FuncionarioController.class, tarefaCargaPg,
						tbView.getSelectionModel().getSelectedItem().getFlagAtivo().equals(1)
								? DadosGlobais.resourceBundle.getString("infoActiReg")
								: DadosGlobais.resourceBundle.getString("infoExcRegis"),
						false);
				pBar.setProgress(-1);

			}

		} else
			util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");

	}

	@FXML
	void actionBtnFilter(ActionEvent event) {

		if (!flagPaneFilter) {
			flagPaneFilter = true;
			splitPaneFilter.setDividerPositions(1);
			SplitPane.setResizableWithParent(anchorPaneFilter, Boolean.FALSE);
			txtFilterColumn.requestFocus();
		}

		else {
			splitPaneFilter.setDividerPositions(0);
			flagPaneFilter = false;
			SplitPane.setResizableWithParent(anchorPaneFilter, Boolean.FALSE);
		}

	}

	@FXML
	void actionBtnInsert(ActionEvent event) {

		if (nivAcessoPermissao.getFlagInsert().equals(1)) {
			entidadeBean = new Funcionario();
			btnInsert.setDisable(true);
			btnSave.setDisable(false);
			btnCancel.setDisable(false);
			txtCodigo.setDisable(true);
			txtCodigo.setText("+1");
			Util.limpar(txtDescricao);
			cboxTipoFuncionario.getSelectionModel().selectFirst();
			txtDescricao.requestFocus();
		} else {
			util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
		}

	}

	@FXML
	void actionBtnInsertGrid(ActionEvent event) {

		if (nivAcessoPermissao.getFlagInsert().equals(1)) {
			entidadeBean = new Funcionario();
			btnInsert.setDisable(true);
			btnSave.setDisable(false);
			btnCancel.setDisable(false);
			txtCodigo.setDisable(true);
			txtCodigo.setText("+1");
			Util.limpar(txtDescricao);
			cboxTipoFuncionario.getSelectionModel().selectFirst();
			Util.openFormCadastro(anchorPaneListagem, anchorPaneDetalhes, 0);
			txtDescricao.requestFocus();
		} else {
			util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
		}

	}

	@FXML
	void actionBtnPrint(ActionEvent event) throws NoSuchFieldException, SecurityException {

		if (nivAcessoPermissao.getFlagPrint().equals(1))
			printExportShow();
		else
			util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");

	}

	@FXML
	void actionBtnRefresh(ActionEvent event) {

		if (util.showAlert(DadosGlobais.resourceBundle.getString("alertConfirShowAllData"), "confirmation")) {
			paramFlagAtivo = Arrays.asList(1);
			taskQuery("all", true);
			cboxFlagAtivo.getSelectionModel().selectFirst();

		}

	}

	@FXML
	void actionBtnSave(ActionEvent event) {

		if (nivAcessoPermissao.getFlagUpdate().equals(1)) {

			if (!Util.noEmpty(txtDescricao)) {

				entidadeBean.setDescricao(txtDescricao.getText());
				entidadeBean.setFlagTipoFuncionario(cboxTipoFuncionario.getSelectionModel().getSelectedItem().getAction());

				if (txtCodigo.isDisable() && txtCodigo.getText().equals("+1")) {

					Task<String> TarefaRefresh = new Task<String>() {

						LogRetorno logRet = new LogRetorno();

						@Override
						protected String call() throws Exception {

							logRet = entidadeDao.insert(entidadeBean);

							return "-";
						}

						@Override
						protected void succeeded() {
							stg.close();
							pBar.setProgress(1);
							if (logRet.getStatus().equals(EnumLogRetornoStatus.SUCESSO)) {
								Util.showNotification(DadosGlobais.resourceBundle.getString("notificationSucess"));
								txtCodigo.setText(String.valueOf(logRet.getLastRecord()));
								btnSave.setDisable(true);
								btnCancel.setDisable(true);
								btnInsert.setDisable(false);
								txtCodigo.setDisable(false);
								updateTbView("INSERT");
								txtCodigo.requestFocus();

							} else {
								util.showAlert(logRet.getMsg(), "error");
							}
						}

						@Override
						protected void failed() {
							stg.close();
							util.tratamentoExcecao(exceptionProperty().getValue().toString(),
									"[ FuncionarioController.actionBtnSave() - INSERT ]");
							pBar.setProgress(0);
						}

						@Override
						protected void cancelled() {

							pBar.setProgress(0);
							super.cancelled();
						}

					};
					Thread t = new Thread(TarefaRefresh);
					t.setDaemon(true);
					t.start();
					stg = ProgressBarForm.showProgressBar(FuncionarioController.class, TarefaRefresh,
							DadosGlobais.resourceBundle.getString("infInsertRegistro"), false);
					pBar.setProgress(-1);

				} else

				if (entidadeBean.getCodigo().toString().equals(txtCodigo.getText())) {

					Task<String> TarefaRefresh = new Task<String>() {

						LogRetorno logRet = new LogRetorno();

						@Override
						protected String call() throws Exception {

							logRet = entidadeDao.update(entidadeBean);

							return "-";
						}

						@Override
						protected void succeeded() {
							stg.close();
							pBar.setProgress(1);

							if (logRet.getStatus().equals(EnumLogRetornoStatus.SUCESSO)) {
								Util.showNotification(DadosGlobais.resourceBundle.getString("notificationSucess"));
								btnSave.setDisable(true);
								btnCancel.setDisable(true);
								btnInsert.setDisable(false);
								txtCodigo.setDisable(false);
								updateTbView("UPDATE");
								txtCodigo.requestFocus();

							} else {
								util.showAlert(logRet.getMsg(), "error");
							}

						}

						@Override
						protected void failed() {
							stg.close();
							util.tratamentoExcecao(exceptionProperty().getValue().toString(),
									"[ FuncionarioController.actionBtnSave() - UPDATE ]");
							pBar.setProgress(0);
						}

						@Override
						protected void cancelled() {

							pBar.setProgress(0);
							super.cancelled();

						}

					};
					Thread t = new Thread(TarefaRefresh);
					t.setDaemon(true);
					t.start();
					stg = ProgressBarForm.showProgressBar(FuncionarioController.class, TarefaRefresh,
							DadosGlobais.resourceBundle.getString("infSaveChange"), false);
					pBar.setProgress(-1);

				}

				else {
					loadByID(false, Integer.valueOf(txtCodigo.getText()), false);
				}
			}
		} else {
			util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
		}

	}

	@FXML
	void actionTxtFilterColumn(ActionEvent event) {

		if (txtFilterColumn.getText().isEmpty()) {
			if (util.showAlert(DadosGlobais.resourceBundle.getString("alertConfirShowAllData"), "confirmation")) {
				taskQuery("filter", true);
			}
		} else {
			taskQuery("filter", true);
		}

	}

	@FXML
	void keyPressedTxtCodigo(KeyEvent event) {

		if (event.getCode().equals(KeyCode.ENTER)) {
			txtDescricao.requestFocus();
		}

		if (event.getCode().equals(KeyCode.ESCAPE)) {
			if (anchorPaneDetalhes.isVisible() && btnCancel.isDisable()) {
				txtCodigo.setDisable(true);
				actionBtnClose(null);
			}
		}

	}

	@FXML
	void keyPressedTxtFilterColumn(KeyEvent event) {

		if (event.getCode().equals(KeyCode.TAB) && tbView.getItems().size() > 0
				&& tbView.getItems().get(0).getCodigo() != null) {
			tbView.getSelectionModel().select(0);
			Util.setFocus(tbView);

		} else {
			Util.setFocus(txtFilterColumn);
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@FXML
	void keyReleasedTxtFilterColumn(KeyEvent event) {

		if (listaRegistros != null) {

			if (txtFilterColumn.textProperty().get().isEmpty()) {
				tbView.setItems(listaRegistros);
				lblNumLinhas.setText(String.valueOf(tbView.getItems().size()));
				return;
			}

			ObservableList tableItems = FXCollections.observableArrayList();
			ObservableList cols = tbView.getVisibleLeafColumns();

			for (int i = 0; i < listaRegistros.size(); i++) {

				for (int j = 0; j < cols.size(); j++) {

					TableColumn col = (TableColumn) cols.get(j);
					String cellValue = col.getCellData(listaRegistros.get(i)).toString();
					if (Util.removeSpecialCharacters(cellValue.toLowerCase())
							.contains(txtFilterColumn.textProperty().get().toLowerCase())) {
						tableItems.add(listaRegistros.get(i));
						break;
					}
				}
			}

			tbView.setItems(tableItems);
			lblNumLinhas.setText(String.valueOf(tbView.getItems().size()));
		}

	}

	@SuppressWarnings("unchecked")
	@FXML
	void mouseClickedToggleHelp(MouseEvent event) {

		VBox vbox = new VBox();
		vbox.setPadding(new Insets(10));
		vbox.setSpacing(8);
		vbox.setAlignment(Pos.CENTER);

		TreeItem<String> itemCol = new TreeItem<String>(DadosGlobais.resourceBundle.getString("help.active.inactive"));
		itemCol.setExpanded(true);
		TreeItem<String> itemCol0 = new TreeItem<String>("1 - " + DadosGlobais.resourceBundle.getString("help.active"));
		TreeItem<String> itemCol1 = new TreeItem<String>(
				"0 - " + DadosGlobais.resourceBundle.getString("help.inactive"));
		itemCol.getChildren().addAll(itemCol0, itemCol1);

		TreeView<String> tree = new TreeView<String>(itemCol);
		tree.setPrefWidth(180);
		tree.setPrefHeight(100);
		tree.autosize();

		Text title = new Text(DadosGlobais.resourceBundle.getString("help"));
		title.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		vbox.getChildren().add(title);
		vbox.getChildren().add(tree);

		popOver.setContentNode(vbox);
		popOver.setArrowLocation(ArrowLocation.TOP_LEFT);

		Bounds boundsScene = toggleHelp.localToScene(toggleHelp.getBoundsInLocal());

		if (toggleHelp.isSelected())
			popOver.show(toggleHelp, boundsScene.getMaxX() - 15, boundsScene.getMaxY());
		else
			popOver.hide();

	}

	/**
	 * METODO QUE CRIA UMA TAREFA PARA A BUSCA DE DADOS
	 * 
	 * @param tipoConsulta
	 *            </BR>
	 *            FILTER -> FAZ A CONSULTA DOS REGISTROS FILTRANDO DADOS
	 *            FILTRANDO OS REGISTROS USANDO AS INFORMAÇOES DO PAINEL DE
	 *            FILTRO;</BR>
	 *            FILTRO ALL -> FAZ A CONSULTA DE TODOS OS REGISTROS ATIVOS</BR>
	 * @param flagRefresh
	 *            </BR>
	 *            TRUE -> RECARREGA O TABLEVIEW APÓS EFETUAR A CONSULTA DOS
	 *            REGISTROS </BR>
	 *            FALSE -> NÃO RECARREGA O TABLEVIEW APÓS A CONSULTA DOS
	 *            REGISTROS
	 */
	public void taskQuery(String tipoConsulta, boolean flagRefresh) {

		Task<String> TarefaRefresh = new Task<String>() {
			@Override
			protected String call() throws Exception {
				// TIPOCONSULTA:
				// ALL(all) TRAS TODOS OS DADOS
				// FILTER(filter) FITRA A CONSULTA DE ACORDO COM OS VALORES
				// PREENCHIDOS NO FORMULARIO
				if (tipoConsulta.equals("all")) {
					// CONSULTA TODOS OS USUARIOS ATIVOS
					listaRegistros = FXCollections.observableArrayList(entidadeDao.getList(paramFlagAtivo));
				} else if (tipoConsulta.equals("filter")) {
					// CONSULTA COM FILTROS ATIVOS
					switch (cboxFlagAtivo.getValue().getField()) {
					case "0":
						paramFlagAtivo = Arrays.asList(0);
						break;
					case "1":
						paramFlagAtivo = Arrays.asList(1);
						break;
					case "2":
						paramFlagAtivo = Arrays.asList(0, 1);
						break;
					default:
						break;
					}
					String parametroBusca = null;

					if (txtFilterColumn.getText().isEmpty()) {

						listaRegistros = FXCollections.observableArrayList(entidadeDao.filterByColumn(
								cboxFilterColumn.getValue().getField(), txtFilterColumn.getText(),
								cboxFilterColumn.getValue().getAction(), paramFlagAtivo));

					} else {

						if (cboxFilterColumn.getValue().getAction().equals(1)) {

							boolean temp = true;
							for (int i = 0; i < txtFilterColumn.getText().length(); i++)
								if (!Character.toString(txtFilterColumn.getText().charAt(i)).matches("[0-9]")) {
									temp = false;
									break;
								}

							if (temp && txtFilterColumn.getText().length() <= 8)
								parametroBusca = txtFilterColumn.getText();
							else
								parametroBusca = "0";

						} else if (cboxFilterColumn.getValue().getAction().equals(2)) {
							parametroBusca = txtFilterColumn.getText();
						}

						listaRegistros = FXCollections
								.observableArrayList(entidadeDao.filterByColumn(cboxFilterColumn.getValue().getField(),
										parametroBusca, cboxFilterColumn.getValue().getAction(), paramFlagAtivo));
					}
				}
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				pBar.setProgress(1);
				// CASO A CONSULTA RETORNE ALGUM REGISTRO HABILITA O BOTAO DE
				// IMPRESSÃO
				if (listaRegistros.size() > 0)
					btnPrint.setDisable(false);
				else
					btnPrint.setDisable(true);

				// CASO O FLAG REFRESH SEJA TRUE RECARREGA O TABLEVIEW COM OS
				// DADOS DA CONSULTA
				if (flagRefresh == true) {
					tbView.getItems().clear();
					tbView.getItems().setAll(listaRegistros);
				}
				lblNumLinhas.setText(String.valueOf(tbView.getItems().size()));
				if (tbView.getItems().size() > 0) {
					tbView.getSelectionModel().select(0);
					tbView.requestFocus();
				} else
					txtFilterColumn.requestFocus();
			}

			@Override
			protected void failed() {
				stg.close();
				util.tratamentoExcecao(exceptionProperty().getValue().toString(),
						"[ FuncionarioController.tafefaConsulta() ]");
				pBar.setProgress(0);
			}

			@Override
			protected void cancelled() {
				tbView.getItems().clear();
				pBar.setProgress(0);
				super.cancelled();
			}

		};
		Thread t = new Thread(TarefaRefresh);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(FuncionarioController.class, TarefaRefresh,
				DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
		pBar.setProgress(-1);

	}

	@SuppressWarnings({ "rawtypes" })
	/**
	 * Show the form to configure the actions of the report, print and export to
	 * different formats.
	 * 
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	public void printExportShow() throws NoSuchFieldException, SecurityException {

		for (TableColumn column : tbView.getVisibleLeafColumns()) {

			Object type = Funcionario.class
					.getDeclaredField(((PropertyValueFactory) column.getCellValueFactory()).getProperty()).getType()
					.getSimpleName();

			if (type.equals("Timestamp"))
				type = "Date";

			tableShowPrintList.add(new TableConfPrint(column.getText(), column.getId(),
					((PropertyValueFactory) column.getCellValueFactory()).getProperty(), type.toString(),
					column.getWidth()));

		}
		try {
			stg = new Stage();
			Scene scene = new Scene(util.openWindow("/views/utils/viewPrintExport.fxml",
					new PrintExportController(tbView, tableShowPrintList,
							DadosGlobais.resourceBundle.getString("funcionarioController.lblTitle"), pBar, stg)));

			stg.setScene(scene);
			stg.initStyle(StageStyle.TRANSPARENT);
			stg.initModality(Modality.APPLICATION_MODAL);
			stg.show();
		} catch (IOException e) {
			util.tratamentoExcecao(e.getMessage().toString(), "[ FuncionarioController.printExportShow() ]");
		}

	}

	/**
	 * METODO QUE CRIA UMA TAREFA PARA ATUALIZAR AS LINHAS DO TABLEVIEW QUE
	 * SOFRERAM ALTERAÇÃO
	 * 
	 * @param flagOperacao
	 *            <BR>
	 *            INSERT -> EM CASO DE INSERCAO DE REGISTRO NOVO CRIA O REGISTRO
	 *            NA LISTA <BR>
	 *            UPDATE -> EM CASO DE UMA ALTERACAO DE UM REGISTRO QUE JÁ
	 *            EXISTE, ALTERA OS VALROES DA LINHA NO TABLEVIEW <BR>
	 *            DELETE -> EM CASO DE EXCLUSAO DE UM REGISTRO, ALTERA OS
	 *            VALROES DA LINHA NO TABLEVIEW
	 */
	public void updateTbView(String flagOperacao) {

		Task<String> TarefaAtualiza = new Task<String>() {
			@Override
			protected String call() throws Exception {

				if (flagOperacao.equals("INSERT")) {
					if (listaRegistros != null)
						listaRegistros.add(entidadeBean);
					else {
						listaRegistros = FXCollections.observableArrayList();
						listaRegistros.add(entidadeBean);
					}
				} else if (flagOperacao.equals("UPDATE")) {

					if (tbView.getItems().size() == 0) {
						listaRegistros = FXCollections.observableArrayList();
						listaRegistros.add(entidadeBean);
					} else {
						for (int i = 0; i < tbView.getItems().size(); i++) {
							if (tbView.getItems().get(i).getCodigo().equals(entidadeBean.getCodigo())) {
								listaRegistros.set(i, entidadeBean);
								break;
							}
						}
					}
				} else if (flagOperacao.equals("DELETE")) {

					tbView.refresh();

				}

				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				pBar.setProgress(1);
				if (!flagOperacao.equals("DELETE"))
					tbView.setItems(listaRegistros);

				lblNumLinhas.setText(String.valueOf(tbView.getItems().size()));

			}

			@Override
			protected void failed() {
				stg.close();
				util.tratamentoExcecao(exceptionProperty().getValue().toString(),
						"[ FuncionarioController.updateTbView() ]");
				pBar.setProgress(0);
			}

			@Override
			protected void cancelled() {
				tbView.getItems().clear();
				pBar.setProgress(0);
				super.cancelled();
			}

		};
		Thread t = new Thread(TarefaAtualiza);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(FuncionarioController.class, TarefaAtualiza,
				DadosGlobais.resourceBundle.getString("infAtualizaTbView"), false);
		pBar.setProgress(-1);

	}

	/**
	 * Método que preenche o formulario de edição dos dados do consultados
	 * através do método getById e getLast da camada DAO
	 * 
	 * @param flagLastRegistro
	 *            </BR>
	 *            TRUE -> INDICA QUE O REGISTRO A SER CARREGADO É O ULTIMO
	 *            CÓDIGO EXISTENTE
	 * @param codigo
	 *            </BR>
	 *            CODIGO USADO NO FILTRO DA BUSCA DO REGISTRO
	 * @param flagDuplica
	 *            </BR>
	 *            TRUE -> INDICA QUE O REGISTRO VAI SER DUPLICADO
	 */
	public void loadByID(boolean flagLastRegistro, Integer codigo, boolean flagDuplica) {

		if ((!flagLastRegistro && codigo > 0) || (flagLastRegistro)) {

			Task<String> TarefaRefresh = new Task<String>() {
				LogRetorno logRet = new LogRetorno();

				@Override
				protected String call() throws Exception {

					if (!flagLastRegistro) {

						logRet = entidadeDao.getById(codigo.intValue());

					} else {

						logRet = entidadeDao.getLast();
					}
					return "-";
				}

				@Override
				protected void succeeded() {
					stg.close();
					pBar.setProgress(1);

					entidadeBean = (Funcionario) logRet.getObjeto();

					if (entidadeBean != null) {

						if (logRet.getStatus().equals(EnumLogRetornoStatus.REGDUPLICADO))
							util.alertException(logRet.getMsg(), "", false);

						txtDescricao.setText(entidadeBean.getDescricao());
						cboxTipoFuncionario.getSelectionModel().select(entidadeBean.getFlagTipoFuncionario());
						txtDescricao.selectEnd();
						cboxTipoFuncionario.getSelectionModel().select(entidadeBean.getFlagTipoFuncionario());

						if (flagDuplica) {
							entidadeBean = new Funcionario();
							txtCodigo.setText("+1");
							btnInsert.setDisable(true);
							txtCodigo.setDisable(true);
							btnCancel.setDisable(false);
							btnSave.setDisable(false);
							txtDescricao.requestFocus();
						} else {

							txtCodigo.setText(String.valueOf(entidadeBean.getCodigo()));

							if (!flagLastRegistro && !flagDuplica) {

								btnInsert.setDisable(true);
								txtCodigo.setDisable(true);
								btnCancel.setDisable(false);
								btnSave.setDisable(false);
								txtDescricao.requestFocus();

							}

							if (flagLastRegistro && !flagDuplica) {

								btnInsert.setDisable(false);
								btnCancel.setDisable(true);
								btnSave.setDisable(true);
								txtCodigo.setDisable(false);
								txtCodigo.requestFocus();
							}
						}

					} else {

						util.showAlert(DadosGlobais.resourceBundle.getString("alertErrorCodigoNull"), "error");
						btnSave.setDisable(true);
						btnCancel.setDisable(false);
						btnInsert.setDisable(false);
						txtCodigo.requestFocus();
						Util.limpar(txtDescricao, txtCodigo);
						cboxTipoFuncionario.getSelectionModel().selectFirst();
					}

				}

				@Override
				protected void failed() {
					stg.close();
					util.tratamentoExcecao(exceptionProperty().getValue().toString(),
							"[ FuncionarioController.loadByID() ]");
					pBar.setProgress(0);
				}

				@Override
				protected void cancelled() {
					tbView.getItems().clear();
					pBar.setProgress(0);
					super.cancelled();
				}
			};
			Thread t = new Thread(TarefaRefresh);
			t.setDaemon(true);
			t.start();
			stg = ProgressBarForm.showProgressBar(FuncionarioController.class, TarefaRefresh,
					DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
			pBar.setProgress(-1);

		} else {
			util.showAlert(DadosGlobais.resourceBundle.getString("alertErrorCodigoNull"), "error");
			Util.limpar(txtDescricao);
			cboxTipoFuncionario.getSelectionModel().selectFirst();
			txtCodigo.requestFocus();
		}
	}

	/**
	 * METODO PARA POPULAR O COMBOBOX DE TIPOS DE FILTRO (cboxFilterColumn)
	 */
	public void fillCboxFilterColumn() {
		// 2 Tipo de Busca Contida, 1 Tipo de Busca Exata
		listComboBoxFilter.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("codigo"), 1, "codigo"));
		listComboBoxFilter.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("descricao"), 2, "descricao"));

		cboxFilterColumn.getItems().addAll(listComboBoxFilter);
		cboxFilterColumn.getSelectionModel().selectLast();

		cboxFilterColumn.setConverter(new StringConverter<ComboBoxFilter>() {
			@Override
			public ComboBoxFilter fromString(String string) {
				return null;
			}

			@Override
			public String toString(ComboBoxFilter object) {
				if (object != null) {
					return object.getValue();
				} else {
					return "";
				}
			}
		});

	}

	/**
	 * METODO PARA POPULAR O COMBOBOX DE OPCAO DE ATIVO E INATIVO
	 * (cboxFlagAtivo)
	 */
	public void fillCboxFlagAtivo() {
		ObservableList<ComboBoxFilter> list = FXCollections.observableArrayList();
		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("ativo"), 1, "1"));
		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("inativo"), 1, "0"));
		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("inativo"), 1, "2"));
		cboxFlagAtivo.getItems().addAll(list);
		cboxFlagAtivo.getSelectionModel().selectFirst();
		cboxFlagAtivo.setConverter(new StringConverter<ComboBoxFilter>() {
			@Override
			public ComboBoxFilter fromString(String string) {

				return null;
			}

			@Override
			public String toString(ComboBoxFilter object) {

				if (object != null) {
					return object.getValue();
				} else {
					return "";
				}
			}
		});
	}
	
	/**
	 * METODO PARA POPULAR O COMBOBOX TIPO FUNCIONARIO
	 * (cboxTipoFuncionario)
	 */
	public void fillCboxTipoFuncionario() {
		ObservableList<ComboBoxFilter> list = FXCollections.observableArrayList();
		
		for (EnumTipoFuncionario item : EnumTipoFuncionario.values())
			list.add(new ComboBoxFilter(item.text, item.index, item.index.toString()));

		cboxTipoFuncionario.getItems().addAll(list);
		cboxTipoFuncionario.getSelectionModel().selectFirst();
		
		cboxTipoFuncionario.setConverter(new StringConverter<ComboBoxFilter>() {
			@Override
			public ComboBoxFilter fromString(String string) {

				return null;
			}

			@Override
			public String toString(ComboBoxFilter object) {

				if (object != null) {
					return object.getValue();
				} else {
					return "";
				}
			}
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	/**
	 * Método que configura as colunas do tableview de acordo configuracões do
	 * usuário definiu na tela de configuração das colunas.
	 * 
	 * @param visibleColumns
	 *            List of columns defined by user.
	 * @param table
	 *            Instance of the entidadeBean tableView.
	 */
	public void updateTableView(ObservableList visibleColumns, TableView<Funcionario> table) {

		if (!visibleColumns.isEmpty()) {
			// hide all columns
			for (TableColumn<Funcionario, ?> col : table.getColumns())
				col.setVisible(false);
			// show column and defines its position
			for (int i = 0; i < visibleColumns.size(); i++) {
				table.getColumns().remove(visibleColumns.get(i));
				table.getColumns().add(i, (TableColumn<Funcionario, ?>) visibleColumns.get(i));
				table.getColumns().get(i).setVisible(true);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	/**
	 * Método utilizado para gravar o arquivo com as configurações do Tableview
	 * por usuário
	 */
	public void saveColumnSettings() {

		XMLTableColumns xml = new XMLTableColumns();
		ArrayList<UserDefinedColumn> userDefinedColumnList = new ArrayList<UserDefinedColumn>();

		int i = 0;
		for (TableColumn col : tbView.getVisibleLeafColumns()) {
			userDefinedColumnList.add(new UserDefinedColumn(col.getId(), col.getText(), i, col.getWidth()));
			i++;

		}

		xml.WriteXMLColumns(userDefinedColumnList, fileNameConfigColum);

	}

	@SuppressWarnings({ "rawtypes" })
	/**
	 * Método que configura as colunas do tableview de acordo com o arquivo de
	 * configuracões do usuário.
	 */
	public void configTableView() {
		XMLTableColumns xmlTableColumns = new XMLTableColumns();

		ArrayList<UserDefinedColumn> userDefinedColumns = xmlTableColumns.ReadXMLColumns(fileNameConfigColum);

		if (userDefinedColumns != null) {

			for (TableColumn col : tbView.getColumns())
				col.setVisible(false);

			for (int i = 0; i < userDefinedColumns.size(); i++) {
				for (int j = 0; j < tbView.getColumns().size(); j++) {
					if (tbView.getColumns().get(j).getId().equals(userDefinedColumns.get(i).getName())) {
						TableColumn<Funcionario, ?> tableColumn = tbView.getColumns().get(j);
						tbView.getColumns().remove(j);
						tbView.getColumns().add(userDefinedColumns.get(i).getPosition(), tableColumn);
						tableColumn.setPrefWidth(userDefinedColumns.get(i).getWidth());
						tableColumn.setVisible(true);
					}
				}
			}
		}
	}

	/**
	 * METODO QUE FAZ A TRADUÇAO DOS ELEMENTOS CONTINDOS NO FORMULARIO
	 */
	public void translations() {

		btnInsertGrid.setTooltip(new Tooltip(DadosGlobais.resourceBundle.getString("toolTipInsert")));
		btnRefresh.setTooltip(new Tooltip(DadosGlobais.resourceBundle.getString("toolTipRefresh")));
		btnDelete.setTooltip(new Tooltip(DadosGlobais.resourceBundle.getString("toolTipExcluir")));
		btnFilter.setTooltip(new Tooltip(DadosGlobais.resourceBundle.getString("toolTipFilter")));
		btnPrint.setTooltip(new Tooltip(DadosGlobais.resourceBundle.getString("toolTipPrint")));
		btnConfig.setTooltip(new Tooltip(DadosGlobais.resourceBundle.getString("toolTipConfig")));
		toggleHelp.setTooltip(new Tooltip(DadosGlobais.resourceBundle.getString("toolTipHelp")));
		btnInsert.setTooltip(new Tooltip(DadosGlobais.resourceBundle.getString("toolTipInsert")));
		btnSave.setTooltip(new Tooltip(DadosGlobais.resourceBundle.getString("toolTipSave")));
		btnCancel.setTooltip(new Tooltip(DadosGlobais.resourceBundle.getString("toolTipCancel")));

		lblCboxFilterColumn.setText(DadosGlobais.resourceBundle.getString("lblCboxFilterColumn"));
		lblCboxFlagAtivo.setText(DadosGlobais.resourceBundle.getString("lblcboxFlagAtivo"));
		lblTotalLinhas.setText(DadosGlobais.resourceBundle.getString("lblTotalLinhas"));

		lblTitleFormCad.setText(DadosGlobais.resourceBundle.getString("lblTitleFormCad") +" "+ DadosGlobais.resourceBundle.getString("miCadFuncionario"));

		lblCodigo.setText(DadosGlobais.resourceBundle.getString("lblDetCodigo"));
		lblDescricao.setText(DadosGlobais.resourceBundle.getString("lblDetDesc"));
		lblTipoFuncionario.setText(DadosGlobais.resourceBundle.getString("funcionarioController.lblTipoFuncionario"));

		tbColCodigo.setText(DadosGlobais.resourceBundle.getString("tbColCodigo"));
		tbColDescricao.setText(DadosGlobais.resourceBundle.getString("tbColDescricao"));
		tbColAtivoInat.setText(DadosGlobais.resourceBundle.getString("tbColAtivoInat"));

		tbView.setPlaceholder(new Label(DadosGlobais.resourceBundle.getString("tbView")));

	}

	public FuncionarioController() {
		// TODO Auto-generated constructor stub
	}

	public FuncionarioController(NivelAcesso nivAcesso, TabPane o_tabPrincipal) {
		this.nivAcessoPermissao = nivAcesso;
		this.tabPrincipal = o_tabPrincipal;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		// CONFIGURA PAINEIS INICIAIS
		anchorPaneListagem.setVisible(true);
		anchorPaneDetalhes.setVisible(false);

		Util.customSearchTextField("right", null, txtFilterColumn);

		// CHAMADA A FUNÇÃO QUE CONFIGURA O TABLEVIEW DE ACORDO COM O ARQUIVO
		// XML GERADO PARA CADA USUARIO DENTRO DA PASTA C:/EptusAM/System.Grd
		configTableView();

		// CHAMADA A FUNÇÃO QUE FAZ A TRADUÇÃO DO FORMULARIO DE ACORDO COM AS
		// CONFIGURAÇÕES DO USUÁRIO
		translations();

		// CHAMADA A FUNÇÃO QUE SETA O ESTILO DEFAULT AOS CAMPOS TEXTFIELDS
		Util.setKeyPressDefaultStyles(txtCodigo, txtDescricao);
		Util.setStyleOnFocus(txtFilterColumn,txtCodigo, txtDescricao);

		Util.onlyNumbers(txtCodigo);

		Util.onlyAlphanumeric(txtDescricao, txtFilterColumn);

		// CHAMADA A FUNÇÃO QUE LIMITA O NUMERO DE CARACTERES QUE PODEM SER
		// DIGITADOS NOS CAMPOS TEXTFIELDS
		Util.maxCharacters(8, txtCodigo);

		// CHAMADA A FUNÇÃO QUE DEFINE OS CAMPOS TEXTFIELDS QUE SERAO
		// CAPITALIZADOS(LETRAS MAÍUSCULAS)
		Util.whriteUppercase(txtFilterColumn, txtDescricao);

		// CHAMADA A FUNÇÃO PARA POPULAR OS COMBOBOX CONTINDOS NO FORMULARIO
		fillCboxFilterColumn();
		fillCboxFlagAtivo();
		fillCboxTipoFuncionario();

		// CHAMADA A FUNÇÃO PARA SETAR O FOCO INICIAL DO FORMULÁRIO
		Util.setFocus(txtFilterColumn);

		// CONFIGURA AS COLUNAS EXISTENTES NO TABLEVIEW, ASSOCIA A COLUNA DO
		// FXML AO ATRIBUTO EXISTENTE NA CLASSE DO MODEL
		tbColCodigo.setCellValueFactory(new PropertyValueFactory<Funcionario, Integer>("codigo"));
		tbColDescricao.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("descricao"));
		tbColAtivoInat.setCellValueFactory(new PropertyValueFactory<Funcionario, Integer>("flagAtivo"));

		// EVENTO QUE CONTROLA O FOCO DO TABLEVIEW
		tbView.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (tbView.getItems().size() > 0) {
					// CASO O FOCO VOLTE AO TABLEVIEW BOTAO DELETE É HABILITADO
					if (newPropertyValue) {
						if (tbView.getItems().size() > 0) {
							if (tbView.getSelectionModel().getSelectedItem() != null) {
								btnDelete.setDisable(false);
								btnPrint.setDisable(false);
								Util.alternaBotaoDelete(btnDelete,
										tbView.getSelectionModel().getSelectedItem().getFlagAtivo());
							} else {
								tbView.getSelectionModel().selectLast();
							}
						}
					}
				}

				if (!newPropertyValue) {
					// CASO O FOCO NAO SEJA RETIRADO PARA PRESSIONAR O BOTAO
					// DELETE O BOTAO DELETE É DESABILITADO
					if (!btnDelete.isFocused())
						btnDelete.setDisable(true);
				}
			}
		});

		// EVENTO ONSELECT DO TABLEVIEW, CONTROLA A SELEÇÃO DOS ITENS DO
		// TABLEVIEW ATRAVÉS DO CLICK OU DO TECLADO
		tbView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				// CARREGA OS DADOS DA LINHA SELECIONADA OBJETO INSTANCIADO
				entidadeBean = tbView.getSelectionModel().getSelectedItem();
				Util.alternaBotaoDelete(btnDelete, tbView.getSelectionModel().getSelectedItem().getFlagAtivo());
			}
		});

		// EVENTO DE CLICAR E ARRASTAR AS COLUNAS DO TABLEVIEW, AO ARRASTAR OU
		// REDIMENSIONAR OS VALORES SAO GRAVADOS NO ARQUIVO XML
		tbView.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
			if (event.getTarget() instanceof TableHeaderRow || event.getTarget() instanceof Rectangle)
				saveColumnSettings();
		});

		tbView.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode().equals(KeyCode.ENTER)
					&& tbView.getSelectionModel().getSelectedItem().getFlagAtivo().equals(1)) {
				loadByID(false, tbView.getSelectionModel().getSelectedItem().getCodigo(), false);
				Util.openFormCadastro(anchorPaneListagem, anchorPaneDetalhes, 0);
			}
			if (event.getCode().equals(KeyCode.ESCAPE)) {
				txtFilterColumn.requestFocus();
			}
		});

		// PROPIEDADES DAS LINAS DO TABLEVIEW, ADICIONA EVENTOS AO MANUSEAR AS
		// LINHASD DO TABLEVIEW
		tbView.setRowFactory(tb -> {
			// EXIBE O EFEITO DE OPACIDADE NAS LINHAS CASO O ITEM DO TABLEVIEW
			// TENHA O FLAGATIVO = 0
			TableRow<Funcionario> row = new TableRow<Funcionario>() {
				@Override
				public void updateItem(Funcionario objeto, boolean empty) {
					super.updateItem(objeto, empty);
					if (objeto == null) {
						setStyle("");
					} else if (objeto.getFlagAtivo().equals(0)) {
						setStyle("-fx-text-background-color: #bdbdbd;");
					} else {
						setStyle("-fx-text-background-color: BLACK;");
					}
				}
			};

			// EVENTOS ON CLICK NAS LINHAS DO TABLEVIEW
			row.setOnMouseClicked(tr -> {
				// CASO SEJA DADO UM DUPLO CLIQUE NA LINHA DO TABLE VIEW
				if (tr.getClickCount() >= 2 && (!row.isEmpty()) && tr.getButton().equals(MouseButton.PRIMARY)) {
					if (tbView.getSelectionModel().getSelectedItem().getFlagAtivo().equals(1)) {
						loadByID(false, tbView.getSelectionModel().getSelectedItem().getCodigo(), false);
						Util.openFormCadastro(anchorPaneListagem, anchorPaneDetalhes, 0);
					}
				}

				// CASO SEJA DADO UM CLIQUE COM O BOTAO SECUNDARIO DO MOUSE O
				// BOTAO DIREITO EXIBE UM MENU DE AÇOES
				if (tr.getButton().equals(MouseButton.SECONDARY)) {

					// ESTANCIA UM NOVO CONTEXTMENU
					contextMenu = new ContextMenu();

					// ITEM 1 DO MENU DE AÇOES - ALTERAR UM REGISTRO
					MenuItem itemAtualizar = new MenuItem(DadosGlobais.resourceBundle.getString("actionAtualizar"));
					itemAtualizar.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							txtCodigo.setText(
									Integer.toString(tbView.getSelectionModel().getSelectedItem().getCodigo()));
							loadByID(false, tbView.getSelectionModel().getSelectedItem().getCodigo(), false);
							Util.openFormCadastro(anchorPaneListagem, anchorPaneDetalhes, 0);
						}
					});

					// ITEM 2 DO MENU DE AÇOES - EXCLUIR UM REGISTRO
					MenuItem itemExcluir = new MenuItem(DadosGlobais.resourceBundle.getString("actionExcluir"));
					itemExcluir.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent e) {
							actionBtnDelete(null);
						}
					});

					// ITEM 3 DO MENU DE AÇOES - RASTREAR PRODUTOS ASSOCIADOS A
					// ESTE DEPARTAMENTO
					MenuItem itemRastreiaProd = new MenuItem(
							DadosGlobais.resourceBundle.getString("actionRastrearCliente"));
					if (Util.getNivelAcessoEntidade(EnumNivelAcesso.ITENS).getFlagView().equals(0)) {
						itemRastreiaProd.setDisable(true);
					}
					itemRastreiaProd.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent e) {
							try {
								util.crearTab(tabPrincipal, DadosGlobais.resourceBundle.getString("miCadItem"),
										"/views/compras/viewItem.fxml",
										new ItemController(Util.getNivelAcessoEntidade(EnumNivelAcesso.ITENS),
												"Cod_Funcionario",
												tbView.getSelectionModel().getSelectedItem().getCodigo().toString()),
										false);
							} catch (IOException e1) {
								util.tratamentoExcecao(e1.getMessage().toString(),
										"[ FuncionarioController.acaoRastrearItens() ]");
							}
						}
					});

					// ITEM 4 DO MENU DE AÇOES - DUPLICAR CADASTRO
					MenuItem itemDuplica = new MenuItem(DadosGlobais.resourceBundle.getString("actionDuplicar"));
					itemDuplica.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent e) {
							loadByID(false, tbView.getSelectionModel().getSelectedItem().getCodigo(), true);
							Util.openFormCadastro(anchorPaneListagem, anchorPaneDetalhes, 0);
						}
					});

					// CASO A LINHA SELECIONADA DO TABLEVIEW TENHA O FLAGATIVO =
					// 0 DESABILITA A OPCAO DE ATUALIZAR
					// E ALTERA O TEXTO DA OPCAO EXCLUIR PARA REATIVAR
					if (tbView.getSelectionModel().getSelectedItem().getFlagAtivo().equals(0)) {
						itemAtualizar.setDisable(true);
						itemExcluir.setText(DadosGlobais.resourceBundle.getString("actionAtivar"));
						itemDuplica.setDisable(true);
					}

					contextMenu.getItems().addAll(itemAtualizar, itemExcluir, itemRastreiaProd, itemDuplica);
					tbView.setContextMenu(contextMenu);
				}
			});

			return row;
		});

		// EVENTO DE CARREGA OS DADOS DETALHES AO SAIR DO CAMPO CODIGO
		txtCodigo.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (!newPropertyValue) {
					if (!txtCodigo.getText().isEmpty() && !txtCodigo.isDisable()) {
						if (!btnInsert.isPressed() && !btnClose.isPressed() && !btnCancel.isPressed()) {
							loadByID(false, Integer.valueOf(txtCodigo.getText()), false);
						}

					}
				}
			}

		});

		// TECLAS DE ATALHOS PARA O FORMULARIO
		// F2 - CONSULTAR | F4 - CARREGAR TODOSO OS DADOS REFRESH| F5 - INSERIR
		// NOVO REGISTRO | F6 - GRAVAR | DEL - EXCLUIR |F7 - CANCELAR || CTRL+P
		// IMPRIMIR
		anchorPanePrincipal.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			switch (event.getCode()) {
			case F2:
				if (anchorPaneDetalhes.isVisible()) {
					txtCodigo.setDisable(true);
					actionBtnClose(null);
				}
				flagPaneFilter = false;
				actionBtnFilter(null);
				break;

			case F4:
				if (!anchorPaneDetalhes.isVisible()) {
					actionBtnRefresh(null);
				}
				break;

			case F5:
				if (!btnInsertGrid.isDisable() && !anchorPaneDetalhes.isVisible())
					actionBtnInsertGrid(null);
				else if (anchorPaneDetalhes.isVisible())
					actionBtnInsert(null);

				break;

			case F6:
				if (anchorPaneDetalhes.isVisible() && !btnSave.isDisable())
					actionBtnSave(null);

				break;

			case F7:
				if (anchorPaneDetalhes.isVisible() && !btnCancel.isDisable())
					actionBtnCancel(null);
				break;

			case DELETE:
				if (!anchorPaneDetalhes.isVisible()) {
					if (!btnDelete.isDisable())
						actionBtnDelete(null);
				}
				break;

			case P:
				if (!anchorPaneDetalhes.isVisible()) {
					if (event.isControlDown())
						try {
							if (!btnPrint.isDisable())
								actionBtnPrint(null);
						} catch (Exception e) {

							e.printStackTrace();
						}
				}
				break;

			case ESCAPE:

				if (anchorPaneDetalhes.isVisible() && !btnCancel.isDisable()) {
					if (txtCodigo.getText().equals("+1"))
						txtCodigo.clear();

					txtCodigo.setDisable(false);
					btnCancel.setDisable(true);
					btnSave.setDisable(true);
					btnInsert.setDisable(false);
					txtCodigo.requestFocus();
				}

				break;
			default:
				break;
			}
		});

	}

}
