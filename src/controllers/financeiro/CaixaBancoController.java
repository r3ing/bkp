package controllers.financeiro;

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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import models.configuracoes.NivelAcesso;
import models.financeiro.CaixaBanco;
import models.financeiro.CaixaBanco;
import models.financeiro.CaixaBancoDAO;
import models.vendas.CidadeDAO;
import tools.controls.ComboBoxFilter;
import tools.enums.EnumBancos;
import tools.enums.EnumLogRetornoStatus;
import tools.enums.EnumNivelAcesso;
import tools.enums.EnumTipocCodigoTelaVendas;
import tools.reports.TableConfPrint;
import tools.utils.LogRetorno;
import tools.utils.Util;
import tools.xml.UserDefinedColumn;
import tools.xml.XMLTableColumns;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;
import org.controlsfx.control.PopOver.ArrowLocation;
import org.controlsfx.control.textfield.CustomTextField;

import com.jfoenix.controls.JFXToggleButton;
import com.sun.javafx.scene.control.skin.TableHeaderRow;

import application.DadosGlobais;
import controllers.compras.ItemController;
import controllers.utils.ConfigColumnController;
import controllers.utils.PrintExportController;
import controllers.utils.ProgressBarForm;

public class CaixaBancoController implements Initializable {

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
	public TableView<CaixaBanco> tbView;

	@FXML
	private TableColumn<CaixaBanco, Integer> tbColCodigo;

	@FXML
	private TableColumn<CaixaBanco, String> tbColDescricao;

	@FXML
	private TableColumn<CaixaBanco, Integer> tbColAtivoInat;
	
	@FXML
	private TableColumn<CaixaBanco, Integer> tbColTipoConta;

	@FXML
	private TableColumn<CaixaBanco, String> tbColAbreviatura;

	@FXML
	private TableColumn<CaixaBanco, String> tbColAgencia;

	@FXML
	private TableColumn<CaixaBanco, String> tbColAgenciaDigito;

	@FXML
	private TableColumn<CaixaBanco, String> tbColConta;

	@FXML
	private TableColumn<CaixaBanco, String> tbColContaDigito;

	@FXML
	private TableColumn<CaixaBanco, Integer> tbColApuracaoResultado;

	@FXML
	private TableColumn<CaixaBanco, Integer> tbColBanco;

	@FXML
	private TableColumn<CaixaBanco, Integer> tbColCarteira;

	@FXML
	private TableColumn<CaixaBanco, Integer> tbColTipoPessoa;

	@FXML
	private TableColumn<CaixaBanco, String> tbColRazaoSocial;

	@FXML
	private TableColumn<CaixaBanco, String> tbColCodCedente;

	@FXML
	private TableColumn<CaixaBanco, String> tbColEndereco;

	@FXML
	private TableColumn<CaixaBanco, String> tbColNumero;

	@FXML
	private TableColumn<CaixaBanco, String> tbColComplemento;

	@FXML
	private TableColumn<CaixaBanco, String> tbColBairro;

	@FXML
	private TableColumn<CaixaBanco, String> tbColCep;

	@FXML
	private TableColumn<CaixaBanco, Integer> tbColCodCidade;

	@FXML
	private TableColumn<CaixaBanco, String> tbColCidade;

	@FXML
	private TableColumn<CaixaBanco, String> tbColUf;

	@FXML
	private TableColumn<CaixaBanco, String> tbColCpfCnpj;

	@FXML
	private TableColumn<CaixaBanco, String> tbColFone;

	@FXML
	private TableColumn<CaixaBanco, String> tbColEmail;

	@FXML
	private TableColumn<CaixaBanco, String> tbColInstrucao1;

	@FXML
	private TableColumn<CaixaBanco, String> tbColInstrucao2;

	@FXML
	private TableColumn<CaixaBanco, String> tbColInstrucao3;

	@FXML
	private TableColumn<CaixaBanco, String> tbColInstrucao4;

	@FXML
	private Label lblTotalLinhas;

	@FXML
	private Label lblNumLinhas, lblErrorMessage;

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
	private Label lblDescricao;

	@FXML
	private TextField txtDescricao;

	@FXML
	private Label lblTipoConta;

	@FXML
	private RadioButton rdbTipoConta0;

	@FXML
	private ToggleGroup grpTipoConta;

	@FXML
	private RadioButton rdbTipoConta1;

	@FXML
	private Tab tabDadosCadastrais;

	@FXML
	private Label lblAbreviatura;

	@FXML
	private TextField txtAbreviatura;

	@FXML
	private Label lblAgencia;

	@FXML
	private TextField txtAgencia;

	@FXML
	private TextField txtAgenciaDigito;

	@FXML
	private Label lblConta;

	@FXML
	private TextField txtConta;

	@FXML
	private TextField txtContaDigito;

	@FXML
	private Label lblApuracaoResultado;

	@FXML
	private JFXToggleButton tgbApuracaoResultado;

	@FXML
	private Tab tabBoletos;

	@FXML
	private Label lblImprimeBoleto;

	@FXML
	private JFXToggleButton tgbImprimeBoleto;

	@FXML
	private Label lblBancos;

	@FXML
	private ComboBox<ComboBoxFilter> cboxBancos;

	@FXML
	private Label lblCarteira;

	@FXML
	private ComboBox<ComboBoxFilter> cboxCarteira;

	@FXML
	private Label lblTipoPessoa;

	@FXML
	private RadioButton rdbTipoPessoa0;

	@FXML
	private ToggleGroup grpTipoPessoa;

	@FXML
	private RadioButton rdbTipoPessoa1;

	@FXML
	private Label lblRazaoSocial;

	@FXML
	private TextField txtRazaoSocial;

	@FXML
	private Label lblCodCedente;

	@FXML
	private TextField txtCodCedente;

	@FXML
	private Label lblEndereco;

	@FXML
	private TextField txtEndereco;

	@FXML
	private Label lblNumero;

	@FXML
	private TextField txtNumero;

	@FXML
	private Label lblComplemento;

	@FXML
	private TextField txtComplemento;

	@FXML
	private Label lblBairro;

	@FXML
	private Label lblCep;

	@FXML
	private TextField txtCep;

	@FXML
	private TextField txtBairro;

	@FXML
	private Label lblCodCidade;

	@FXML
	private CustomTextField txtCodCidade;

	@FXML
	private Label lblCidade;

	@FXML
	private TextField txtCidade;

	@FXML
	private Label lblUf;

	@FXML
	private TextField txtUf;

	@FXML
	private Label lblCpfCnpj;

	@FXML
	private TextField txtCpfCnpj;

	@FXML
	private Label lblFone;

	@FXML
	private TextField txtFone;

	@FXML
	private Label lblEmail;

	@FXML
	private TextField txtEmail;

	@FXML
	private Label lblInstrucao1;

	@FXML
	private TextField txtInstrucao1;

	@FXML
	private Label lblInstrucao2;

	@FXML
	private TextField txtInstrucao2;

	@FXML
	private Label lblInstrucao3;

	@FXML
	private TextField txtInstrucao3;

	@FXML
	private Label lblInstrucao4;

	@FXML
	private TextField txtInstrucao4;

	@FXML
	private Tab tabCobranca;

	@FXML
	private Tab tabObservacoes;

	@FXML
	private Button btnInsert;

	@FXML
	private Button btnSave;

	@FXML
	private Button btnCancel;

	@FXML
	private ContextMenu contextMenu = null;

	// ATRIBUTOS DA CLASSE
	private ObservableList<CaixaBanco> listaRegistros = null;
	private ObservableList<ComboBoxFilter> listComboBoxFilter = FXCollections.observableArrayList();

	public ObservableList<ComboBoxFilter> listCboxBancos = FXCollections.observableArrayList();
	public ObservableList<ComboBoxFilter> listCboxCarteiras = FXCollections.observableArrayList();

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

	// OBJETOS INSTACIADOS NA CLASSE
	private CaixaBanco entidadeBean = new CaixaBanco();
	private CaixaBancoDAO entidadeDao = new CaixaBancoDAO();
	private Util util = new Util();
	private PopOver popOver = new PopOver();
	private static Stage stg;
	private String fileNameConfigColum = "Grid-Cad-CaixaBanco";
	boolean flagOpenBuscador = true;
	boolean flagSearchCidade = true;

	@FXML
	void actionBtnCancel(ActionEvent event) {

		Util.setDefaultStyle(txtCodigo, txtDescricao, txtAbreviatura, txtAgencia, txtAgenciaDigito, txtConta,
				txtContaDigito, txtRazaoSocial, txtCodCedente, txtEndereco, txtNumero, txtComplemento, txtBairro, txtCep,
				txtCodCidade, txtCidade, txtUf, txtCpfCnpj, txtFone, txtEmail, txtInstrucao1, txtInstrucao2,
				txtInstrucao3, txtInstrucao4);
		Util.limpar(txtCodigo, txtDescricao, txtAbreviatura, txtAgencia, txtAgenciaDigito, txtConta, txtContaDigito,
				txtRazaoSocial, txtCodCedente, txtEndereco, txtNumero, txtComplemento, txtBairro, txtCep, txtCodCidade,
				txtCidade, txtUf, txtCpfCnpj, txtFone, txtEmail, txtInstrucao1, txtInstrucao2, txtInstrucao3,
				txtInstrucao4);
		cboxBancos.getSelectionModel().select(0);
		Util.setSelectRadioButton(1, grpTipoConta);
		tgbApuracaoResultado.setSelected(false);
		tgbImprimeBoleto.setSelected(true);
		Util.setSelectRadioButton(1, grpTipoPessoa);

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

	}

	@FXML
	void actionBtnConfig(ActionEvent event) {

		try {
			stg = new Stage();
			Scene scene = new Scene(util.openWindow("/views/utils/viewConfigColumns.fxml",
					new ConfigColumnController(CaixaBancoController.class, tbView, fileNameConfigColum)));
			stg.setScene(scene);
			stg.initStyle(StageStyle.TRANSPARENT);
			stg.initModality(Modality.APPLICATION_MODAL);
			stg.show();
		} catch (IOException e) {
			util.tratamentoExcecao(e.getMessage().toString(), "[ CaixaBancoController.actionBtnConfig() ]");
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
							+ " "
							+ DadosGlobais.resourceBundle.getString("caixaBancoController.alertConfirmOprExcluir"),
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
								"[ CaixaBancoController.actionBtnDelete() ]");
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
				stg = ProgressBarForm.showProgressBar(CaixaBancoController.class, tarefaCargaPg,
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
			entidadeBean = new CaixaBanco();
			btnInsert.setDisable(true);
			btnSave.setDisable(false);
			btnCancel.setDisable(false);
			txtCodigo.setDisable(true);

			Util.setDefaultStyle(txtCodigo, txtDescricao, txtAbreviatura, txtAgencia, txtAgenciaDigito, txtConta,
					txtContaDigito, txtRazaoSocial, txtCodCedente, txtEndereco, txtNumero, txtComplemento, txtBairro,
					txtCep, txtCodCidade, txtCidade, txtUf, txtCpfCnpj, txtFone, txtEmail, txtInstrucao1, txtInstrucao2,
					txtInstrucao3, txtInstrucao4);
			Util.limpar(txtCodigo, txtDescricao, txtAbreviatura, txtAgencia, txtAgenciaDigito, txtConta,
					txtContaDigito, txtRazaoSocial, txtCodCedente, txtEndereco, txtNumero, txtComplemento, txtBairro,
					txtCep, txtCodCidade, txtCidade, txtUf, txtCpfCnpj, txtFone, txtEmail, txtInstrucao1, txtInstrucao2,
					txtInstrucao3, txtInstrucao4);
			txtCodigo.setText("+1");
			cboxBancos.getSelectionModel().select(0);
			Util.setSelectRadioButton(1, grpTipoConta);
			tgbApuracaoResultado.setSelected(false);
			tgbImprimeBoleto.setSelected(true);
			Util.setSelectRadioButton(1, grpTipoPessoa);

			txtDescricao.requestFocus();
		} else {
			util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
		}

	}

	@FXML
	void actionBtnInsertGrid(ActionEvent event) {

		if (nivAcessoPermissao.getFlagInsert().equals(1)) {
			entidadeBean = new CaixaBanco();
			btnInsert.setDisable(true);
			btnSave.setDisable(false);
			btnCancel.setDisable(false);
			txtCodigo.setDisable(true);

			Util.setDefaultStyle(txtCodigo, txtDescricao, txtAbreviatura, txtAgencia, txtAgenciaDigito, txtConta,
					txtContaDigito, txtRazaoSocial, txtCodCedente, txtEndereco, txtNumero, txtComplemento, txtBairro,
					txtCep, txtCodCidade, txtCidade, txtUf, txtCpfCnpj, txtFone, txtEmail, txtInstrucao1, txtInstrucao2,
					txtInstrucao3, txtInstrucao4);
			Util.limpar(txtCodigo, txtDescricao, txtAbreviatura, txtAgencia, txtAgenciaDigito, txtConta,
					txtContaDigito, txtRazaoSocial, txtCodCedente, txtEndereco, txtNumero, txtComplemento, txtBairro,
					txtCep, txtCodCidade, txtCidade, txtUf, txtCpfCnpj, txtFone, txtEmail, txtInstrucao1, txtInstrucao2,
					txtInstrucao3, txtInstrucao4);
			txtCodigo.setText("+1");
			cboxBancos.getSelectionModel().select(0);
			Util.setSelectRadioButton(1, grpTipoConta);
			tgbApuracaoResultado.setSelected(false);
			tgbImprimeBoleto.setSelected(true);
			Util.setSelectRadioButton(1, grpTipoPessoa);

			Util.openFormCadastro(anchorPaneListagem, anchorPaneDetalhes, 0);
			
			
			//--START EMPTY---
			lblErrorMessage.setText("");
			
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

			if (!Util.noEmpty( lblErrorMessage, anchorPaneDetalhes, txtDescricao)) {

				entidadeBean.setDescricao(Util.textfieldNotNull("srt", txtDescricao));
				entidadeBean.setTipoConta(
						Integer.valueOf(grpTipoConta.selectedToggleProperty().getValue().getUserData().toString()));
				entidadeBean.setAbreviatura(Util.textfieldNotNull("str", txtAbreviatura));
				entidadeBean.setAgenciaNumero(Util.textfieldNotNull("srt", txtAgencia));
				entidadeBean.setAgenciaDigito(Util.textfieldNotNull("srt", txtAgenciaDigito));
				entidadeBean.setContaNumero(Util.textfieldNotNull("srt", txtConta));
				entidadeBean.setContaDigito(Util.textfieldNotNull("srt", txtContaDigito));
				entidadeBean.setFlagApuracaoResultado(tgbApuracaoResultado.isSelected() ? 1 : 0);
				entidadeBean.setFlagBoleto(tgbImprimeBoleto.isSelected() ? 1 : 0);
				entidadeBean.setTipoBanco(cboxBancos.getSelectionModel().getSelectedItem().getAction());
				entidadeBean.setCarteira(cboxCarteira.getSelectionModel().getSelectedItem().getAction());
				entidadeBean.setTipoPessoa(
						Integer.valueOf(grpTipoPessoa.selectedToggleProperty().getValue().getUserData().toString()));
				entidadeBean.setRazaoSocial(Util.textfieldNotNull("srt", txtRazaoSocial));
				entidadeBean.setCedenteNumero(Util.textfieldNotNull("srt", txtCodCedente));
				entidadeBean.setEndereco(Util.textfieldNotNull("srt", txtEndereco));
				entidadeBean.setEndNumero(Util.textfieldNotNull("srt", txtNumero));
				entidadeBean.setComplemento(Util.textfieldNotNull("srt", txtComplemento));
				entidadeBean.setBairro(Util.textfieldNotNull("srt", txtBairro));
				entidadeBean.setCep(Util.textfieldNotNull("srt", txtCep));
				entidadeBean.setCodCidade(Integer.valueOf(Util.textfieldNotNull("srt", txtCodCidade)));
				entidadeBean.setCidade(Util.textfieldNotNull("srt", txtCidade));
				entidadeBean.setUf(Util.textfieldNotNull("srt", txtUf));
				entidadeBean.setCpfCnpj(Util.textfieldNotNull("srt", txtCpfCnpj));
				entidadeBean.setFone(Util.textfieldNotNull("srt", txtFone));
				entidadeBean.setEmail(Util.textfieldNotNull("srt", txtEmail));
				entidadeBean.setInstrucao01(Util.textfieldNotNull("srt", txtInstrucao1));
				entidadeBean.setInstrucao02(Util.textfieldNotNull("srt", txtInstrucao2));
				entidadeBean.setInstrucao03(Util.textfieldNotNull("srt", txtInstrucao3));
				entidadeBean.setInstrucao04(Util.textfieldNotNull("srt", txtInstrucao4));

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
									"[ CaixaBancoController.actionBtnSave() - INSERT ]");
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
					stg = ProgressBarForm.showProgressBar(CaixaBancoController.class, TarefaRefresh,
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
									"[ CaixaBancoController.actionBtnSave() - UPDATE ]");
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
					stg = ProgressBarForm.showProgressBar(CaixaBancoController.class, TarefaRefresh,
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
		
		lblErrorMessage.setText(""); 		

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

	@FXML
	void onActionTxtCodCidade(ActionEvent event) {

		if (flagOpenBuscador)
			showSearch();

		flagOpenBuscador = true;

	}

	@FXML
	/**
	 * EVENTO keyPressed do Campo Código da Cidade Caso seja Pressionado F2 abre
	 * o buscador de cidade Caso seja pressionado ENTER tenta buscar a cidade e
	 * troca o foco para o próximo campo
	 */
	void onKeyPressedTxtCodCidade(KeyEvent event) {
		switch (event.getCode()) {
		case F2:
			flagSearchCidade = false;
			showSearch();
			flagSearchCidade = true;
			break;

		case ENTER:
			flagOpenBuscador = false;
			txtEndereco.requestFocus();
			break;

		default:
			break;

		}
	}

	/**
	 * EXIBE O FORMULARIO DE BUSCA DE CIDADE
	 */
	public void showSearch() {

		ObservableList<ComboBoxFilter> list = FXCollections.observableArrayList();

		list.add(new ComboBoxFilter("Código", 1, "codigo"));
		list.add(new ComboBoxFilter("Descrição", 2, "descricao"));
		list.add(new ComboBoxFilter("UF", 3, "ufSigla"));

		util.showSearchView("Cidade", "descricao", CidadeDAO.class, list, txtCodCidade, txtCidade, txtUf, txtEndereco);

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

			Object type = CaixaBanco.class
					.getDeclaredField(((PropertyValueFactory) column.getCellValueFactory()).getProperty()).getType()
					.getSimpleName();

			if (type.equals("LocalDateTime"))
				type = "Date";

			tableShowPrintList.add(new TableConfPrint(column.getText(), column.getId(),
					((PropertyValueFactory) column.getCellValueFactory()).getProperty(), type.toString(),
					column.getWidth()));

		}
		try {
			stg = new Stage();
			Scene scene = new Scene(
					util.openWindow("/views/utils/viewPrintExport.fxml",
							new PrintExportController(tbView, tableShowPrintList,
									DadosGlobais.resourceBundle.getString("caixaBancoController.lblTitle"), pBar,
									stg)));

			stg.setScene(scene);
			stg.initStyle(StageStyle.TRANSPARENT);
			stg.initModality(Modality.APPLICATION_MODAL);
			stg.show();
		} catch (IOException e) {
			util.tratamentoExcecao(e.getMessage().toString(), "[ CaixaBancoController.printExportShow() ]");
		}

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

				}

				else if (tipoConsulta.equals("filter"))

				{
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
						"[ CaixaBancoController.tafefaConsulta() ]");
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
		stg = ProgressBarForm.showProgressBar(CaixaBancoController.class, TarefaRefresh,
				DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
		pBar.setProgress(-1);

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
						"[ CaixaBancoController.updateTbView() ]");
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
		stg = ProgressBarForm.showProgressBar(CaixaBancoController.class, TarefaAtualiza,
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
				protected void succeeded() 
				{
					
					lblErrorMessage.setText("");
					
					stg.close();
					pBar.setProgress(1);

					entidadeBean = (CaixaBanco) logRet.getObjeto();

					if (entidadeBean != null) {

						if (logRet.getStatus().equals(EnumLogRetornoStatus.REGDUPLICADO))
							util.alertException(logRet.getMsg(), "", false);

						txtDescricao.setText(entidadeBean.getDescricao());
						txtDescricao.selectEnd();
						Util.setSelectRadioButton(entidadeBean.getTipoConta(), grpTipoConta);
						if (rdbTipoConta0.isSelected())
							tabCobranca.setDisable(true);
						txtAbreviatura.setText(entidadeBean.getAbreviatura());
						txtAgencia.setText(entidadeBean.getAgenciaNumero());
						txtAgenciaDigito.setText(entidadeBean.getAgenciaDigito());
						txtConta.setText(entidadeBean.getContaNumero());
						txtContaDigito.setText(entidadeBean.getContaDigito());
						tgbApuracaoResultado.setSelected(entidadeBean.getFlagApuracaoResultado() == 1 ? true : false);
						tgbImprimeBoleto.setSelected(entidadeBean.getFlagBoleto() == 1 ? true : false);
						cboxBancos.getSelectionModel().select(entidadeBean.getTipoBanco() - 1);
						cboxCarteira.getSelectionModel().select(entidadeBean.getCarteira());
						Util.setSelectRadioButton(entidadeBean.getTipoPessoa(), grpTipoPessoa);
						txtRazaoSocial.setText(entidadeBean.getRazaoSocial());
						txtCodCedente.setText(entidadeBean.getCedenteNumero());
						txtEndereco.setText(entidadeBean.getEndereco());
						txtNumero.setText(entidadeBean.getEndNumero());
						txtComplemento.setText(entidadeBean.getComplemento());
						txtBairro.setText(entidadeBean.getBairro());
						txtCep.setText(entidadeBean.getCep());
						txtCodCidade.setText(String.valueOf(entidadeBean.getCodCidade()));
						txtCidade.setText(entidadeBean.getCidade());
						txtUf.setText(entidadeBean.getUf());
						txtCpfCnpj.setText(entidadeBean.getCpfCnpj());
						txtFone.setText(entidadeBean.getFone());
						txtEmail.setText(entidadeBean.getEmail());
						txtInstrucao1.setText(entidadeBean.getInstrucao01());
						txtInstrucao2.setText(entidadeBean.getInstrucao02());
						txtInstrucao3.setText(entidadeBean.getInstrucao03());
						txtInstrucao4.setText(entidadeBean.getInstrucao04());

						if (flagDuplica) {
							entidadeBean = new CaixaBanco();
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

						//--START SHOW ERROR---
						lblErrorMessage.setText(DadosGlobais.resourceBundle.getString("alertErrorCodigoNull"));
						Util.setStyleError(true, txtCodigo);						
						
						btnSave.setDisable(true);
						btnCancel.setDisable(false);
						btnInsert.setDisable(false);
						txtCodigo.requestFocus();
						Util.limpar(txtDescricao, txtCodigo);
					}

				}

				@Override
				protected void failed() {
					stg.close();
					util.tratamentoExcecao(exceptionProperty().getValue().toString(),
							"[ CaixaBancoController.loadByID() ]");
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
			stg = ProgressBarForm.showProgressBar(CaixaBancoController.class, TarefaRefresh,
					DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
			pBar.setProgress(-1);

		} 
		else 
		{
			lblErrorMessage.setText(DadosGlobais.resourceBundle.getString("alertErrorCodigoNull"));
			Util.setStyleError(true, txtCodigo);
			
			Util.limpar(txtDescricao);
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
		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("ativo.inativo"), 1, "2"));
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
	 * METODO PARA POPULAR O COMBOBOX BANCOS (cboxBancos)
	 */
	public void fillCboxBancos() {

		for (int i = 0; i < EnumBancos.values().length; i++) {

			if (i == 0)
				listCboxBancos.add(new ComboBoxFilter(EnumBancos.values()[i].banco, EnumBancos.values()[i].id,
						EnumBancos.values()[i].banco));
			else {

				if (EnumBancos.values()[i].id != EnumBancos.values()[i - 1].id) {
					listCboxBancos.add(new ComboBoxFilter(EnumBancos.values()[i].banco, EnumBancos.values()[i].id,
							EnumBancos.values()[i].banco));
				}

			}
		}

		cboxBancos.getItems().addAll(listCboxBancos);
		cboxBancos.getSelectionModel().select(0);

		cboxBancos.setConverter(new StringConverter<ComboBoxFilter>() {
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

	/**
	 * METODO PARA POPULAR O COMBOBOX BANCOS (cboxCarteira)
	 */
	public void fillCboxCarteiras() {

		for (int i = 0; i < EnumBancos.values().length; i++) {

			if (EnumBancos.values()[i].id == 1) {
				listCboxCarteiras.add(new ComboBoxFilter(EnumBancos.values()[i].carteira, EnumBancos.values()[i].index,
						EnumBancos.values()[i].idcarteira));
			}

		}

		cboxCarteira.getItems().addAll(listCboxCarteiras);
		cboxCarteira.getSelectionModel().select(0);

		cboxCarteira.setConverter(new StringConverter<ComboBoxFilter>() {
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
	public void updateTableView(ObservableList visibleColumns, TableView<CaixaBanco> table) {

		if (!visibleColumns.isEmpty()) {
			// hide all columns
			for (TableColumn<CaixaBanco, ?> col : table.getColumns())
				col.setVisible(false);
			// show column and defines its position
			for (int i = 0; i < visibleColumns.size(); i++) {
				table.getColumns().remove(visibleColumns.get(i));
				table.getColumns().add(i, (TableColumn<CaixaBanco, ?>) visibleColumns.get(i));
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
						TableColumn<CaixaBanco, ?> tableColumn = tbView.getColumns().get(j);
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

		lblTitleFormCad.setText(DadosGlobais.resourceBundle.getString("lblTitleFormCad") + " "
				+ DadosGlobais.resourceBundle.getString("miCadCaixaBanco"));

		lblCodigo.setText(DadosGlobais.resourceBundle.getString("lblDetCodigo"));
		lblDescricao.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblDescricao"));
		lblTipoConta.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblTipoConta"));
		rdbTipoConta0.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.rdbTipoConta0"));
		rdbTipoConta1.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.rdbTipoConta1"));
		
		//TabDadosCadastrais
		tabDadosCadastrais.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.tabDadosCadastrais"));
		lblAbreviatura.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblAbreviatura"));
		lblAgencia.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblAgencia"));
		lblConta.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblConta"));
		lblApuracaoResultado.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblApuracaoResultado"));
		
		//TabBoleto
		tabBoletos.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.tabBoletos"));
		lblImprimeBoleto.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblImprimeBoleto"));
		lblBancos.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblBancos"));
		lblCarteira.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblCarteira"));
		lblTipoPessoa.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblTipoPessoa"));
		rdbTipoPessoa0.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.rdbTipoPessoa0"));
		rdbTipoPessoa1.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.rdbTipoPessoa1"));
		lblRazaoSocial.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblRazaoSocial"));
		lblCodCedente.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblCodCedente"));
		lblEndereco.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblEndereco"));
		lblNumero.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblNumero"));
		lblComplemento.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblComplemento"));
		lblBairro.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblBairro"));
		lblCep.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblCep"));
		lblCodCidade.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblCodCidade"));
		lblCidade.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblCidade"));
		lblUf.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblUf"));
		lblCpfCnpj.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblCpfCnpj"));
		lblFone.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblFone"));
		lblEmail.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblEmail"));
		lblInstrucao1.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblInstrucao1"));
		lblInstrucao2.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblInstrucao2"));
		lblInstrucao3.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblInstrucao3"));
		lblInstrucao4.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblInstrucao4"));
		
		//TabCobranca
		tabCobranca.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.tabCobranca"));
		
		//TabObservacoes
		tabObservacoes.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.tabObservacoes"));		

		tbColCodigo.setText(DadosGlobais.resourceBundle.getString("tbColCodigo"));
		tbColDescricao.setText(DadosGlobais.resourceBundle.getString("tbColDescricao"));
		tbColAtivoInat.setText(DadosGlobais.resourceBundle.getString("tbColAtivoInat"));
		tbColTipoConta.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblTipoConta"));
		tbColAbreviatura.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblAbreviatura"));
		tbColAgencia.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblAgencia"));
		tbColAgenciaDigito.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.tbColAgenciaDigito"));
		tbColConta.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblConta"));
		tbColContaDigito.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.tbColContaDigito"));
		tbColApuracaoResultado.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.tbColApuracaoResultado"));
		tbColBanco.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblBancos"));
		tbColCarteira.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblCarteira"));
		tbColTipoPessoa.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblTipoPessoa"));
		tbColRazaoSocial.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblRazaoSocial"));
		tbColCodCedente.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblCodCedente"));
		tbColEndereco.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblEndereco"));
		tbColNumero.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblNumero"));
		tbColComplemento.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblComplemento"));
		tbColBairro.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblBairro"));
		tbColCep.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblCep"));
		tbColCodCidade.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblCodCidade"));
		tbColCidade.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblCidade"));
		tbColUf.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblUf"));
		tbColCpfCnpj.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblCpfCnpj"));
		tbColFone.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblFone"));
		tbColEmail.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblEmail"));
		tbColInstrucao1.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblInstrucao1"));
		tbColInstrucao2.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblInstrucao2"));
		tbColInstrucao3.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblInstrucao3"));
		tbColInstrucao4.setText(DadosGlobais.resourceBundle.getString("caixaBancoController.lblInstrucao4"));
		
		tbView.setPlaceholder(new Label(DadosGlobais.resourceBundle.getString("tbView")));

	}

	public CaixaBancoController(NivelAcesso nivAcesso, TabPane o_tabPrincipal) {

		this.nivAcessoPermissao = nivAcesso;
		this.tabPrincipal = o_tabPrincipal;
	}

	public CaixaBancoController() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("restriction")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		// CONFIGURA PAINEIS INICIAIS
		anchorPaneListagem.setVisible(true);
		anchorPaneDetalhes.setVisible(false);

		Util.customSearchTextField("right", null, txtFilterColumn);
		Util.customSearchTextField("right", null, txtCodCidade);

		Util.mascaraCEP(txtCep);
		Util.mascaraCNPJ(txtCpfCnpj);
		Util.mascaraEmail(txtEmail);
		Util.maskPhone(txtFone); 
		
		txtFone.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if(!newValue){
					if(!Util.validatePhone(txtFone)){			
						txtFone.requestFocus();
						txtFone.positionCaret(txtFone.getText().length());
					}
				}
			}
		});

		// CHAMADA A FUNÇÃO QUE CONFIGURA O TABLEVIEW DE ACORDO COM O ARQUIVO
		// XML GERADO PARA CADA USUARIO DENTRO DA PASTA C:/EptusAM/System.Grd
		configTableView();

		// CHAMADA A FUNÇÃO QUE FAZ A TRADUÇÃO DO FORMULARIO DE ACORDO COM AS
		// CONFIGURAÇÕES DO USUÁRIO
		translations();

		// CHAMADA A FUNÇÃO QUE SETA O ESTILO DEFAULT AOS CAMPOS TEXTFIELDS
		Util.setKeyPressDefaultStyles(txtCodigo, txtDescricao, txtAbreviatura, txtAgencia, txtAgenciaDigito, txtConta,
				txtContaDigito, txtRazaoSocial, txtCodCedente, txtEndereco, txtNumero, txtComplemento, txtBairro, txtCep,
				txtCodCidade, txtCidade, txtUf, txtCpfCnpj, txtFone, txtEmail, txtInstrucao1, txtInstrucao2,
				txtInstrucao3, txtInstrucao4);

		Util.setStyleOnFocus(txtFilterColumn, txtCodigo, txtDescricao, txtAbreviatura, txtAgencia, txtAgenciaDigito,
				txtConta, txtContaDigito, txtRazaoSocial, txtCodCedente, txtEndereco, txtNumero, txtComplemento,
				txtBairro, txtCep, txtCodCidade, txtCidade, txtUf, txtCpfCnpj, txtFone, txtEmail, txtInstrucao1,
				txtInstrucao2, txtInstrucao3, txtInstrucao4);

		Util.onlyNumbers(txtCodigo, txtAgencia, txtAgenciaDigito, txtConta, txtContaDigito, txtNumero, txtCidade);

		Util.onlyAlphanumeric(txtDescricao, txtFilterColumn);

		// CHAMADA A FUNÇÃO QUE LIMITA O NUMERO DE CARACTERES QUE PODEM SER
		// DIGITADOS NOS CAMPOS TEXTFIELDS
		Util.maxCharacters(8, txtCodigo);

		// CHAMADA A FUNÇÃO QUE DEFINE OS CAMPOS TEXTFIELDS QUE SERAO
		// CAPITALIZADOS(LETRAS MAÍUSCULAS)
		Util.whriteUppercase(txtFilterColumn, txtCodigo, txtDescricao, txtAbreviatura, txtAgencia, txtAgenciaDigito,
				txtConta, txtContaDigito, txtRazaoSocial, txtCodCedente, txtEndereco, txtNumero, txtComplemento,
				txtBairro, txtCep, txtCodCidade, txtCidade, txtUf, txtCpfCnpj, txtFone, txtEmail, txtInstrucao1,
				txtInstrucao2, txtInstrucao3, txtInstrucao4);

		Util.whriteLowercase(txtEmail);

		// CHAMADA A FUNÇÃO PARA POPULAR OS COMBOBOX CONTINDOS NO FORMULARIO
		fillCboxFilterColumn();
		fillCboxFlagAtivo();
		fillCboxBancos();
		fillCboxCarteiras();

		// CHAMADA A FUNÇÃO PARA SETAR O FOCO INICIAL DO FORMULÁRIO
		Util.setFocus(txtFilterColumn);

		// Initializes the values of the RadioButton
		Util.defineRadioButton(grpTipoConta, grpTipoPessoa);

		// CONFIGURA AS COLUNAS EXISTENTES NO TABLEVIEW, ASSOCIA A COLUNA DO
		// FXML AO ATRIBUTO EXISTENTE NA CLASSE DO MODEL
		tbColCodigo.setCellValueFactory(new PropertyValueFactory<CaixaBanco, Integer>("codigo"));
		tbColDescricao.setCellValueFactory(new PropertyValueFactory<CaixaBanco, String>("descricao"));
		tbColAtivoInat.setCellValueFactory(new PropertyValueFactory<CaixaBanco, Integer>("flagAtivo"));
		tbColTipoConta.setCellValueFactory(new PropertyValueFactory<CaixaBanco, Integer>("tipoConta"));
		tbColAbreviatura.setCellValueFactory(new PropertyValueFactory<CaixaBanco, String>("abreviatura"));
		tbColAgencia.setCellValueFactory(new PropertyValueFactory<CaixaBanco, String>("agenciaNumero"));
		tbColAgenciaDigito.setCellValueFactory(new PropertyValueFactory<CaixaBanco, String>("agenciaDigito"));
		tbColConta.setCellValueFactory(new PropertyValueFactory<CaixaBanco, String>("contaNumero"));
		tbColContaDigito.setCellValueFactory(new PropertyValueFactory<CaixaBanco, String>("contaDigito"));
		tbColApuracaoResultado.setCellValueFactory(new PropertyValueFactory<CaixaBanco, Integer>("flagApuracaoResultado"));
		tbColBanco.setCellValueFactory(new PropertyValueFactory<CaixaBanco, Integer>("tipoBanco"));
		tbColCarteira.setCellValueFactory(new PropertyValueFactory<CaixaBanco, Integer>("carteira"));
		tbColTipoPessoa.setCellValueFactory(new PropertyValueFactory<CaixaBanco, Integer>("tipoPessoa"));
		tbColRazaoSocial.setCellValueFactory(new PropertyValueFactory<CaixaBanco, String>("razaoSocial"));
		tbColCodCedente.setCellValueFactory(new PropertyValueFactory<CaixaBanco, String>("cedenteNumero"));
		tbColEndereco.setCellValueFactory(new PropertyValueFactory<CaixaBanco, String>("endereco"));
		tbColNumero.setCellValueFactory(new PropertyValueFactory<CaixaBanco, String>("endNumero"));
		tbColComplemento.setCellValueFactory(new PropertyValueFactory<CaixaBanco, String>("complemento"));
		tbColBairro.setCellValueFactory(new PropertyValueFactory<CaixaBanco, String>("bairro"));
		tbColCep.setCellValueFactory(new PropertyValueFactory<CaixaBanco, String>("cep"));
		tbColCodCidade.setCellValueFactory(new PropertyValueFactory<CaixaBanco, Integer>("codCidade"));
		tbColCidade.setCellValueFactory(new PropertyValueFactory<CaixaBanco, String>("cidade"));
		tbColUf.setCellValueFactory(new PropertyValueFactory<CaixaBanco, String>("uf"));
		tbColCpfCnpj.setCellValueFactory(new PropertyValueFactory<CaixaBanco, String>("cpfCnpj"));
		tbColFone.setCellValueFactory(new PropertyValueFactory<CaixaBanco, String>("fone"));
		tbColEmail.setCellValueFactory(new PropertyValueFactory<CaixaBanco, String>("email"));
		tbColInstrucao1.setCellValueFactory(new PropertyValueFactory<CaixaBanco, String>("instrucao01"));
		tbColInstrucao2.setCellValueFactory(new PropertyValueFactory<CaixaBanco, String>("instrucao02"));
		tbColInstrucao3.setCellValueFactory(new PropertyValueFactory<CaixaBanco, String>("instrucao03"));
		tbColInstrucao4.setCellValueFactory(new PropertyValueFactory<CaixaBanco, String>("instrucao04"));
		
		
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
			TableRow<CaixaBanco> row = new TableRow<CaixaBanco>() {
				@Override
				public void updateItem(CaixaBanco objeto, boolean empty) {
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
												"Cod_CaixaBanco",
												tbView.getSelectionModel().getSelectedItem().getCodigo().toString()),
										false);
							} catch (IOException e1) {
								util.tratamentoExcecao(e1.getMessage().toString(),
										"[ CaixaBancoController.acaoRastrearItens() ]");
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

		grpTipoConta.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
				if (grpTipoConta.getSelectedToggle().getUserData().toString().equals("1"))
					tabCobranca.setDisable(false);
				else
					tabCobranca.setDisable(true);
			}
		});

		cboxBancos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ComboBoxFilter>() {
			@Override
			public void changed(ObservableValue<? extends ComboBoxFilter> observable, ComboBoxFilter oldValue,
					ComboBoxFilter newValue) {
				// TODO Auto-generated method stub
				listCboxCarteiras.clear();
				for (int i = 0; i < EnumBancos.values().length; i++)
					if (newValue.getAction() == EnumBancos.values()[i].id)
						listCboxCarteiras.add(new ComboBoxFilter(EnumBancos.values()[i].carteira,
								EnumBancos.values()[i].index, EnumBancos.values()[i].idcarteira));

				cboxCarteira.getItems().clear();
				cboxCarteira.getItems().addAll(listCboxCarteiras);
				cboxCarteira.getSelectionModel().select(0);

			}
		});

		tgbImprimeBoleto.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if (newValue) {
					lblBancos.setDisable(false);
					cboxBancos.setDisable(false);
					lblCarteira.setDisable(false);
					cboxCarteira.setDisable(false);
					lblTipoPessoa.setDisable(false);
					rdbTipoPessoa0.setDisable(false);
					rdbTipoPessoa1.setDisable(false);
					lblRazaoSocial.setDisable(false);
					txtRazaoSocial.setDisable(false);
					lblCodCedente.setDisable(false);
					txtCodCedente.setDisable(false);
					lblEndereco.setDisable(false);
					txtEndereco.setDisable(false);
					lblNumero.setDisable(false);
					txtNumero.setDisable(false);
					lblComplemento.setDisable(false);
					txtComplemento.setDisable(false);
					lblBairro.setDisable(false);
					txtBairro.setDisable(false);
					lblCep.setDisable(false);
					txtCep.setDisable(false);
					lblCodCidade.setDisable(false);
					txtCodCidade.setDisable(false);
					lblCidade.setDisable(false);
					txtCidade.setDisable(false);
					lblUf.setDisable(false);
					txtUf.setDisable(false);
					lblCpfCnpj.setDisable(false);
					txtCpfCnpj.setDisable(false);
					lblFone.setDisable(false);
					txtFone.setDisable(false);
					lblEmail.setDisable(false);
					txtEmail.setDisable(false);
					lblInstrucao1.setDisable(false);
					txtInstrucao1.setDisable(false);
					lblInstrucao2.setDisable(false);
					txtInstrucao2.setDisable(false);
					lblInstrucao3.setDisable(false);
					txtInstrucao3.setDisable(false);
					lblInstrucao4.setDisable(false);
					txtInstrucao4.setDisable(false);

				} else {

					lblBancos.setDisable(true);
					cboxBancos.setDisable(true);
					lblCarteira.setDisable(true);
					cboxCarteira.setDisable(true);
					lblTipoPessoa.setDisable(true);
					rdbTipoPessoa0.setDisable(true);
					rdbTipoPessoa1.setDisable(true);
					lblRazaoSocial.setDisable(true);
					txtRazaoSocial.setDisable(true);
					lblCodCedente.setDisable(true);
					txtCodCedente.setDisable(true);
					lblEndereco.setDisable(true);
					txtEndereco.setDisable(true);
					lblNumero.setDisable(true);
					txtNumero.setDisable(true);
					lblComplemento.setDisable(true);
					txtComplemento.setDisable(true);
					lblBairro.setDisable(true);
					txtBairro.setDisable(true);
					lblCep.setDisable(true);
					txtCep.setDisable(true);
					lblCodCidade.setDisable(true);
					txtCodCidade.setDisable(true);
					lblCidade.setDisable(true);
					txtCidade.setDisable(true);
					lblUf.setDisable(true);
					txtUf.setDisable(true);
					lblCpfCnpj.setDisable(true);
					txtCpfCnpj.setDisable(true);
					lblFone.setDisable(true);
					txtFone.setDisable(true);
					lblEmail.setDisable(true);
					txtEmail.setDisable(true);
					lblInstrucao1.setDisable(true);
					txtInstrucao1.setDisable(true);
					lblInstrucao2.setDisable(true);
					txtInstrucao2.setDisable(true);
					lblInstrucao3.setDisable(true);
					txtInstrucao3.setDisable(true);
					lblInstrucao4.setDisable(true);
					txtInstrucao4.setDisable(true);

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
