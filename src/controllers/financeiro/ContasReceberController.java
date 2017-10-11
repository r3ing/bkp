package controllers.financeiro;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.PopOver.ArrowLocation;
import org.controlsfx.control.textfield.CustomTextField;
import org.hibernate.event.spi.ClearEvent;

import com.sun.javafx.robot.FXRobot;
import com.sun.javafx.robot.FXRobotFactory;
import com.sun.javafx.scene.control.skin.DatePickerSkin;
import com.sun.javafx.scene.control.skin.TableHeaderRow;

import application.DadosGlobais;
import controllers.compras.ItemController;
import controllers.configuracoes.UsuarioController;
import controllers.utils.ComboBoxData;
import controllers.utils.ConfigColumnController;
import controllers.utils.PrintExportController;
import controllers.utils.ProgressBarForm;
import controllers.vendas.ConvenioController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.StringConverter;
import jdk.internal.org.objectweb.asm.tree.IntInsnNode;
import models.configuracoes.Auditoria;
import models.configuracoes.NivelAcesso;
import models.configuracoes.OperacaoFinanceiro;
import models.configuracoes.OperacaoFinanceiroDAO;
import models.financeiro.CentroCusto;
import models.financeiro.CentroCustoDAO;
import models.financeiro.ContasReceber;
import models.financeiro.ContasReceberDAO;
import models.financeiro.PlanoConta;
import models.financeiro.PlanoContaDAO;
import models.financeiro.Portador;
import models.financeiro.PortadorDAO;
import models.financeiro.Secao;
import models.financeiro.SecaoDAO;
import models.recursosHumanos.Funcionario;
import models.recursosHumanos.FuncionarioDAO;
import models.vendas.Cliente;
import models.vendas.ClienteDAO;
import models.vendas.PlanoPagamento;
import models.vendas.PlanoPagamentoDAO;
import tools.controls.ColumsTableView;
import tools.controls.ComboBoxFilter;
import tools.enums.EnumDataContasReceber;
import tools.enums.EnumLogRetornoStatus;
import tools.enums.EnumNivelAcesso;
import tools.enums.EnumOrigemRegistro;
import tools.enums.EnumStatusContasReceber;
import tools.reports.TableConfPrint;
import tools.utils.LogRetorno;
import tools.utils.RelacaoItems;
import tools.utils.Util;
import tools.xml.UserDefinedColumn;
import tools.xml.XMLTableColumns;

public class ContasReceberController implements Initializable {

	@FXML
	private AnchorPane anchorPanePrincipal;

	@FXML
	private AnchorPane anchorPaneListagem;

	@FXML
	private ToolBar toolBar;

	@FXML
	private MenuButton btnInsertGrid;

	@FXML
	private MenuItem miAddContasReceber;

	@FXML
	private MenuItem miAddContasReceberLotes;

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
	private Button btnHelp;

	@FXML
	private SplitPane splitPaneFilter;

	@FXML
	private AnchorPane anchorPaneFilter;

	@FXML
	private TabPane tabPaneFiltros;

	@FXML
	private Tab tabContasReceber;

	@FXML
	private Label lblData_filt;

	@FXML
	private ComboBox<ComboBoxFilter> cboxData_filt;

	@FXML
	private Label lblPeriodo_filt;

	@FXML
	private ComboBox<?> cboxPeriodo_filt;

	@FXML
	private Label lblDataIni_filt;

	@FXML
	private DatePicker dateInicial_filt;

	@FXML
	private Label lblDataFin_filt;

	@FXML
	private DatePicker dateFinal_filt;

	@FXML
	private Label lblCodCliente_filt;

	@FXML
	private CustomTextField txtCodCliente_filt;

	@FXML
	private CustomTextField txtCliente_filt;

	@FXML
	private Label lblJuros_filt;

	@FXML
	private DatePicker dateJuros_filt;

	@FXML
	private Label lblPorcentagem_filt;

	@FXML
	private CustomTextField txtPorcentagem_filt;

	@FXML
	private Label lblFiliais_filt;

	@FXML
	private CheckComboBox<?> cbkFiliais_filt;

	@FXML
	private Label lblStatus_filt;

	@FXML
	private ComboBox<ComboBoxFilter> cboxStatus_filt;

	@FXML
	private Tab tabMaisFiltros;

	@FXML
	private Label lblCodPortador_filt;

	@FXML
	private CustomTextField txtCodPortador_filt;

	@FXML
	private CustomTextField txtPortador_filt;

	@FXML
	private Label lblCodSecao_filt;

	@FXML
	private CustomTextField txtCodSecao_filt;

	@FXML
	private CustomTextField txtSecao_filt;

	@FXML
	private Label lblCodVendedor_filt;

	@FXML
	private CustomTextField txtCodVendedor_filt;

	@FXML
	private CustomTextField txtVendedor_filt;

	@FXML
	private Label lblCodPlanoPagamento_filt;

	@FXML
	private CustomTextField txtCodPlanoPagamento_filt;

	@FXML
	private CustomTextField txtPlanoPagamento_filt;

	@FXML
	private Label lblCodPlanoContas_filt;

	@FXML
	private CustomTextField txtCodPlanoContas_filt;

	@FXML
	private CustomTextField txtPlanoContas_filt;

	@FXML
	private AnchorPane anchorPaneTableView;

	@FXML
	private TableView<ContasReceber> tbView;

	@FXML
	private TableColumn<ContasReceber, Boolean> tbColSelected;

	@FXML
	private TableColumn<ContasReceber, Integer> tbColCliente;

	@FXML
	private TableColumn<ContasReceber, String> tbColNome;

	@FXML
	private TableColumn<ContasReceber, Integer> tbColAtivoInat;

	@FXML
	private TableColumn<ContasReceber, Integer> tbColTitulo;

	@FXML
	private TableColumn<ContasReceber, Integer> tbColParcelas;

	@FXML
	private TableColumn<ContasReceber, LocalDateTime> tbColVencimento;

	@FXML
	private TableColumn<ContasReceber, BigDecimal> tbColValor;

	@FXML
	private TableColumn<ContasReceber, BigDecimal> tbColRecebido;

	@FXML
	private TableColumn<ContasReceber, Integer> tbColAtraso;

	@FXML
	private TableColumn<ContasReceber, BigDecimal> tbColJuro;

	@FXML
	private TableColumn<ContasReceber, BigDecimal> tbColSaldo;

	@FXML
	private TableColumn<ContasReceber, BigDecimal> tbColValorTotal;

	@FXML
	private TableColumn<ContasReceber, Integer> tbColStatus;

	@FXML
	private Label lblTotalLinhas;

	@FXML
	private Label lblNumLinhas;

	@FXML
	private ProgressBar pBar;

	@FXML
	private AnchorPane anchorPaneDetalhes_cr, anchorPaneContasReceberInt;

	@FXML
	private Button btnClose_cr;

	@FXML
	private Label lblTitleFormCad_cr;

	@FXML
	private Label lblCodOperacao_cr;

	@FXML
	private CustomTextField txtCodOperacao_cr;

	@FXML
	private CustomTextField txtOperacao_cr;

	@FXML
	private Label lblCodCliente_cr;

	@FXML
	private CustomTextField txtCodCliente_cr;

	@FXML
	private CustomTextField txtCliente_cr;

	@FXML
	private Label lblNoDocumento_cr;

	@FXML
	private TextField txtNoDocumento_cr;

	@FXML
	private CheckBox chkAutomatico_cr;

	@FXML
	private Label lblParcela_cr;

	@FXML
	private TextField txtParcela_cr;

	@FXML
	private Label lblDataEmissao_cr;

	@FXML
	private DatePicker dateDataEmissao_cr;

	@FXML
	private Label lblDataVencimento_cr;

	@FXML
	private DatePicker dateDataVencimento_cr;

	@FXML
	private Label lblDataPrevRecebimento_cr;

	@FXML
	private DatePicker dateDataPrevRecebimento_cr;

	@FXML
	private Label lblHistorico_cr;

	@FXML
	private TextField txtHistorico_cr;

	@FXML
	private Label lblValorBruto_cr;

	@FXML
	private TextField txtValorBruto_cr;

	@FXML
	private Label lblValorPorCiento_cr;

	@FXML
	private TextField txtValorPorCiento_cr;

	@FXML
	private Label lblValorDesconto_rc;

	@FXML
	private TextField txtValorDesconto_rc;

	@FXML
	private Label lblValorLiquido_cr;

	@FXML
	private TextField txtValorLiquido_cr;

	@FXML
	private Label lblCodMoeda_cr;

	@FXML
	private CustomTextField txtCodMoeda_cr;

	@FXML
	private CustomTextField txtMoeda_cr;

	@FXML
	private Label lblCodSecao_cr;

	@FXML
	private CustomTextField txtCodSecao_cr;

	@FXML
	private CustomTextField txtSecao_cr;

	@FXML
	private Label lblCodCentroCusto_cr;

	@FXML
	private CustomTextField txtCodCentroCusto_cr;

	@FXML
	private CustomTextField txtCentroCusto_cr;

	@FXML
	private Label lblCodPortador_cr;

	@FXML
	private CustomTextField txtCodPortador_cr;

	@FXML
	private CustomTextField txtPortador_cr;

	@FXML
	private Label lblCodPlanoContas_cr;

	@FXML
	private CustomTextField txtCodPlanoContas_cr;

	@FXML
	private CustomTextField txtPlanoContas_cr;

	@FXML
	private Label lblCodVendedor_cr;

	@FXML
	private CustomTextField txtCodVendedor_cr;

	@FXML
	private CustomTextField txtVendedor_cr;

	@FXML
	private Label lblCodTipoDoc_cr;

	@FXML
	private CustomTextField txtCodTipoDoc_cr;

	@FXML
	private CustomTextField txtTipoDoc_cr;

	@FXML
	private Button btnInsert_cr;

	@FXML
	private Button btnSave_cr;

	@FXML
	private Button btnCancel_cr;

	@FXML
	private AnchorPane anchorPaneDetalhes_crl;

	@FXML
	private Button btnClose_crl;

	@FXML
	private Label lblTitleFormCad_crl;

	@FXML
	private Label lblCodOperacao_crl;

	@FXML
	private CustomTextField txtCodOperacao_crl;

	@FXML
	private CustomTextField txtOperacao_crl;

	@FXML
	private Label lblCodCliente_crl;

	@FXML
	private CustomTextField txtCodCliente_crl;

	@FXML
	private CustomTextField txtCliente_crl;

	@FXML
	private Label lblNoDocumento_crl;

	@FXML
	private TextField txtNoDocumento_crl;

	@FXML
	private CheckBox chkAutomatico_crl;

	@FXML
	private Label lblCodSecao_crl;

	@FXML
	private CustomTextField txtCodSecao_crl;

	@FXML
	private CustomTextField txtSecao_crl;

	@FXML
	private Label lblCodCentroCusto_crl;

	@FXML
	private CustomTextField txtCodCentroCusto_crl;

	@FXML
	private CustomTextField txtCentroCusto_crl;

	@FXML
	private Label lblCodTipoDoc_crl;

	@FXML
	private CustomTextField txtCodTipoDoc_crl;

	@FXML
	private CustomTextField txtTipoDoc_crl;

	@FXML
	private Label lblCodPortador_crl;

	@FXML
	private CustomTextField txtCodPortador_crl;

	@FXML
	private CustomTextField txtPortador_crl;

	@FXML
	private Label lblCodPlanoContas_crl;

	@FXML
	private CustomTextField txtCodPlanoContas_crl;

	@FXML
	private CustomTextField txtPlanoContas_crl;

	@FXML
	private Label lblCodMoeda_crl;

	@FXML
	private CustomTextField txtCodMoeda_crl;

	@FXML
	private CustomTextField txtMoeda_crl;

	@FXML
	private Label lblCodDigitador_crl;

	@FXML
	private CustomTextField txtCodDigitador_crl;

	@FXML
	private CustomTextField txtDigitador_crl;

	@FXML
	private Label lblCodVendedor_crl;

	@FXML
	private CustomTextField txtCodVendedor_crl;

	@FXML
	private CustomTextField txtVendedor_crl;

	@FXML
	private Label lblDataEmissao_crl;

	@FXML
	private DatePicker dateDataEmissao_crl;

	@FXML
	private Label lblDataVencimento_crl;

	@FXML
	private DatePicker dateDataVencimento_crl;

	@FXML
	private Label lblValorBruto_crl;

	@FXML
	private TextField txtValorBruto_crl;

	@FXML
	private Label lblValorPorCiento_crl;

	@FXML
	private TextField txtValorPorCiento_crl;

	@FXML
	private Label lblValorDesconto_rcl;

	@FXML
	private TextField txtValorDesconto_rcl;

	@FXML
	private Label lblValorLiquido_crl;

	@FXML
	private TextField txtValorLiquido_crl;

	@FXML
	private CheckBox chkDividirParcelas_crl;

	@FXML
	private Label lblHistorico_crl;

	@FXML
	private TextField txtHistorico_crl;

	@FXML
	private Label lblNoParcelas_crl;

	@FXML
	private Spinner<Integer> spinNoParcelas_crl;

	@FXML
	private CheckBox chkIncluirParcela_crl;

	@FXML
	private Label lblParcelaInicial_crl, lblErrorMessage, lblErrorMessage1;

	@FXML
	private Spinner<Integer> spinParcelaInicial_crl;

	@FXML
	private TableView<ContasReceber> tbView_crl;

	@FXML
	private TableColumn<ContasReceber, Integer> tbColParcelas_crl;

	@FXML
	private TableColumn<ContasReceber, String> tbColHistorico_crl;

	@FXML
	private TableColumn<ContasReceber, LocalDateTime> tbColVencimento_crl;

	@FXML
	private TableColumn<ContasReceber, BigDecimal> tbColValor_crl;

	@FXML
	private Button btnGeraContasReceber;

	@FXML
	private Button btnInsert_crl;

	@FXML
	private Button btnSave_crl;

	@FXML
	private Button btnCancel_crl;

	@FXML
	private Label lblValorTotal_crl;

	@FXML
	private AnchorPane anchorPaneAlteraData;

	@FXML
	private BorderPane paneDatePicker;

	@FXML
	private Button btnCancel_ald;

	@FXML
	private Button btnSave_ald;

	@FXML
	private ContextMenu contextMenu = null;

	boolean flagPaneFilter = true;// ATRIBUTO UTILIZADO PARA CONTROLAR O STATUS
	// DO PAINEL DO FILTRO ABERTO OU FECHADO
	private List<Integer> paramFlagAtivo = Arrays.asList(1);// ATRIBUTO INICIAL
															// dO PARAMENTO DE
															// FLAGATIVO
															// UTILIZADO NAS
															// BUSCAS DE
															// REGISTROS
	private NivelAcesso nivAcessoPermissao;// ATRIBUTO COM OS NIVEIS DE ACESSO
											// DO USUARIO SOBRE OS REGISTROS
											// (EXCLUSAO,INSERCAO,ETC)
	private TabPane tabPrincipal;// TAB PANE PRINCIPAL PARA ABRIR NOVAS GUIAS A
									// PARITR DA TELA DE CADASTRO
	private String fileNameConfigColum = "Grid-Cad-ContasReceber";
	private ContasReceber entidadeBean = new ContasReceber();
	private ContasReceberDAO entidadeDao = new ContasReceberDAO();
	private ObservableList<ContasReceber> listaRegistros = FXCollections.observableArrayList();
	private ObservableList<ContasReceber> listContasReceberLotes = FXCollections.observableArrayList();
	private ObservableList<ContasReceber> listNewContasReceberLotes = FXCollections.observableArrayList();
	private BigDecimal total_crl = BigDecimal.ZERO;
	private BigDecimal tmp = BigDecimal.ZERO;
	private List<TableConfPrint> tableShowPrintList = new ArrayList<TableConfPrint>();
	private List<Object> listCROperations = new ArrayList<Object>();
	private PopOver popOver = new PopOver();
	private static Stage stg;
	// Date formatter
	private String pattern = "dd/MM/yyyy";
	private final LocalDate today = LocalDate.now();
	Util util = new Util();
	private boolean flagInsertUpdate = false;
	// CheckBox for TableCOlumn Head
	private CheckBox checkAll;
	// DatePicker for menu action change expiration date
	private DatePicker dateExpiration = new DatePicker();

	@FXML
	void actionBtnCancel_cr(ActionEvent event) {
		resetForm(true);
		txtCodOperacao_cr.requestFocus();
	}

	@FXML
	void actionBtnCancel_crl(ActionEvent event) {
		resetForm(false);
		flagInsertUpdate = false;
		lblValorTotal_crl.setText("VALOR TOTAL:");
		lblValorTotal_crl.setStyle("-fx-text-fill: black;");
		// entidadeBean = new ContasReceber();
	}

	@FXML
	void actionBtnClose(ActionEvent event) {

		if (anchorPaneDetalhes_cr.isVisible()) {

			Util.fechaTelaCadastro(anchorPaneListagem, anchorPaneDetalhes_cr);
			resetForm(true);
			flagInsertUpdate = false;
			cboxData_filt.requestFocus();

		} else if (anchorPaneDetalhes_crl.isVisible()) {

			Util.fechaTelaCadastro(anchorPaneListagem, anchorPaneDetalhes_crl);
			resetForm(false);
			flagInsertUpdate = false;
			cboxData_filt.requestFocus();

		} else {
			Util.fechaTelaCadastro(anchorPaneListagem, anchorPaneAlteraData);

		}

	}

	@FXML
	void actionBtnConfig(ActionEvent event) {

		try {
			stg = new Stage();
			Scene scene = new Scene(util.openWindow("/views/utils/viewConfigColumns.fxml",
					new ConfigColumnController(ContasReceberController.class, tbView, fileNameConfigColum)));
			stg.setScene(scene);
			stg.initStyle(StageStyle.TRANSPARENT);
			stg.initModality(Modality.APPLICATION_MODAL);
			stg.show();
		} catch (IOException e) {
			util.tratamentoExcecao(e.getMessage().toString(), "[ ContasReceberController.actionBtnConfig() ]");
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
							+ DadosGlobais.resourceBundle.getString("contasReceberController.alertConfirmOprExcluir"),
					"confirmation")) {

				entidadeBean.setFlagAtivo(entidadeBean.getFlagAtivo().equals(1) ? 0 : 1);

				Task<String> taskDelete = new Task<String>() {
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
								"[ ContasReceberController.actionBtnDelete() ]");
						pBar.setProgress(0);
					}

					@Override
					protected void cancelled() {
						tbView.getItems().clear();
						pBar.setProgress(0);
						super.cancelled();
					}

				};
				Thread t = new Thread(taskDelete);
				t.setDaemon(true);
				t.start();
				stg = ProgressBarForm.showProgressBar(ContasReceberController.class, taskDelete,
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
			cboxData_filt.requestFocus();
		}

		else {
			splitPaneFilter.setDividerPositions(0);
			flagPaneFilter = false;
			SplitPane.setResizableWithParent(anchorPaneFilter, Boolean.FALSE);
		}

	}

	@FXML
	void actionBtnInsert_cr(ActionEvent event) {
		if (nivAcessoPermissao.getFlagInsert().equals(1)) {
			flagInsertUpdate = true;
			entidadeBean = (ContasReceber) util.initializeAttribClass(new ContasReceber());

			btnInsert_cr.setDisable(true);
			btnSave_cr.setDisable(false);
			btnCancel_cr.setDisable(false);
			resetForm(true);
			Util.openFormCadastro(anchorPaneListagem, anchorPaneDetalhes_cr, 0);
			txtCodOperacao_cr.requestFocus();
		} else {
			util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
		}
	}

	@FXML
	void actionBtnInsert_crl(ActionEvent event) {

	}

	@FXML
	void actionBtnGeraContasReceber(ActionEvent event) {

		long date1 = dateDataVencimento_crl.getValue().toEpochDay();
		long date2 = dateDataEmissao_crl.getValue().toEpochDay();
		int days = (int) Math.abs(date2 - date1);

		if (!Util.noEmpty(lblErrorMessage, anchorPaneDetalhes_crl, txtCodOperacao_crl, txtCodCliente_crl,
				txtNoDocumento_crl)) {

			listContasReceberLotes.clear();
			tbView_crl.getItems().clear();

			if (!Util.noEmpty(txtCodOperacao_crl, txtCodCliente_crl, txtNoDocumento_crl, txtHistorico_crl)) {

				if (Util.decimalBRtoBigDecimal(2, txtValorBruto_crl.getText()).compareTo(BigDecimal.ZERO) == 0) {
					Util.setStyleError(true, txtValorBruto_crl);
					return;
				}

				txtCodOperacao_crl.setEditable(false);
				txtOperacao_crl.setEditable(false);
				txtValorBruto_crl.setEditable(false);
				txtValorPorCiento_crl.setEditable(false);
				txtValorDesconto_rcl.setEditable(false);
				txtValorLiquido_crl.setEditable(false);
				chkDividirParcelas_crl.setDisable(true);

				for (int i = 0; i < spinNoParcelas_crl.getValue(); i++) {

					ContasReceber cr = (ContasReceber) util.initializeAttribClass(new ContasReceber());

					cr.setCliente(entidadeBean.getCliente());
					cr.setNomeCliente(txtCliente_crl.getText());
					cr.setCodOperacaoFin(entidadeBean.getCodOperacaoFin());
					cr.setCodOperacaoFinFk(entidadeBean.getCodOperacaoFinFk());
					cr.setCodPortador(entidadeBean.getCodPortador());
					cr.setCodPortadorFk(entidadeBean.getCodPortadorFk());
					cr.setCodSecao(entidadeBean.getCodSecao());
					cr.setCodSecaoFk(entidadeBean.getCodSecaoFk());
					cr.setCodVendedor(entidadeBean.getCodVendedor());
					cr.setCodVendedorFk(entidadeBean.getCodVendedorFk());
					cr.setCodPlanoPagto(entidadeBean.getCodPlanoPagto());
					cr.setCodPlanoPagtoFk(entidadeBean.getCodPlanoPagtoFk());
					cr.setCodPlanoContas(entidadeBean.getCodPlanoContas());
					cr.setCodPlanoContasFk(entidadeBean.getCodPlanoContasFk());
					cr.setCodUsuarioLancamento(DadosGlobais.usuarioLogado.getCodigo());
					cr.setCodUsuarioLancamentoFk(DadosGlobais.usuarioLogado.getCheckDelete());

					cr.setNoDocto(Integer.parseInt(txtNoDocumento_crl.getText()));

					cr.setNoParcela(spinParcelaInicial_crl.getValue() + i);

					if (chkIncluirParcela_crl.isSelected())
						cr.setHistorico(txtHistorico_crl.getText() + " - " + (spinParcelaInicial_crl.getValue() + i)
								+ "/" + spinNoParcelas_crl.getValue());
					else
						cr.setHistorico(txtHistorico_crl.getText());

					cr.setValorBrutoDocto(Util.decimalBRtoBigDecimal(2, txtValorBruto_crl.getText()));
					cr.setValorDesconto(Util.decimalBRtoBigDecimal(2, txtValorDesconto_rcl.getText()));

					if (chkDividirParcelas_crl.isSelected()) {
						cr.setValorLiquido(Util.decimalBRtoBigDecimal(2, txtValorLiquido_crl.getText())
								.divide(new BigDecimal(spinNoParcelas_crl.getValue()), 2, RoundingMode.HALF_EVEN));

					} else {
						cr.setValorLiquido(Util.decimalBRtoBigDecimal(2, txtValorLiquido_crl.getText()));
					}

					cr.setDataEmissao(LocalDateTime.of(dateDataEmissao_crl.getValue(), LocalTime.now()));

					if (i == 0) {
						cr.setDataVencimento(LocalDateTime.of(dateDataVencimento_crl.getValue(), LocalTime.now()));
					} else {
						cr.setDataVencimento(LocalDateTime.of(dateDataVencimento_crl.getValue().plusDays(i * days),
								LocalTime.now()));
					}

					cr.setFlagTipoBaixa(EnumStatusContasReceber.STATUS_ABERTO.index);
					cr.setOrigemRegistro(EnumOrigemRegistro.INCLUCAO_MANUAL.index);

					listContasReceberLotes.add(cr);
				}

				if (chkDividirParcelas_crl.isSelected()) {
					tmp = new BigDecimal(0);
					for (int i = 0; i < listContasReceberLotes.size() - 1; i++)
						tmp = tmp.add(
								listContasReceberLotes.get(i).getValorLiquido().setScale(2, RoundingMode.HALF_EVEN));

					listContasReceberLotes.get(listContasReceberLotes.size() - 1).setValorLiquido(
							Util.decimalBRtoBigDecimal(2, txtValorLiquido_crl.getText()).subtract(tmp));

					tbView_crl.setItems(listContasReceberLotes);

					total_crl = BigDecimal.ZERO;
					for (ContasReceber c : tbView_crl.getItems()) {
						total_crl = total_crl.add(c.getValorLiquido()).setScale(2, RoundingMode.HALF_EVEN);
					}

					if (total_crl.compareTo(Util.decimalBRtoBigDecimal(2, txtValorLiquido_crl.getText())) == 1) {
						lblValorTotal_crl.setStyle("-fx-text-fill: red;");
						btnSave_crl.setDisable(true);
					} else {
						lblValorTotal_crl.setStyle("-fx-text-fill: black;");
						btnSave_crl.setDisable(false);
					}

					lblValorTotal_crl.setText("VALOR TOTAL: " + total_crl);
					lblValorTotal_crl.setVisible(true);
				} else {
					tbView_crl.setItems(listContasReceberLotes);
					btnSave_crl.setDisable(false);
				}

			}

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
		}

	}

	@FXML
	void actionBtnSave_cr(ActionEvent event) {

		if (nivAcessoPermissao.getFlagUpdate().equals(1)) {

			if (!Util.noEmpty(lblErrorMessage1, anchorPaneContasReceberInt, txtCodOperacao_cr, txtCodCliente_cr,
					txtNoDocumento_cr, txtParcela_cr, txtValorBruto_cr, txtHistorico_cr)) {

				if (Util.decimalBRtoBigDecimal(2, txtValorBruto_cr.getText()).compareTo(BigDecimal.ZERO) == 0) {
					Util.setStyleError(true, txtValorBruto_cr);
					return;
				}

				entidadeBean.setNoDocto(Integer.parseInt(txtNoDocumento_cr.getText()));
				entidadeBean.setNoParcela(Integer.parseInt(txtParcela_cr.getText()));
				// SERIE_DOCTO

				entidadeBean.setNomeCliente(txtCliente_cr.getText());
				entidadeBean.setHistorico(Util.textfieldNotNull("str", txtHistorico_cr));

				entidadeBean.setValorBrutoDocto(Util.decimalBRtoBigDecimal(2, txtValorBruto_cr.getText()));
				entidadeBean.setValorDesconto(Util.decimalBRtoBigDecimal(2, txtValorDesconto_rc.getText()));
				// VALOR_ACRESCIMO
				entidadeBean.setValorLiquido(Util.decimalBRtoBigDecimal(2, txtValorLiquido_cr.getText()));
				// VALOR_JUROS
				// VALOR_PAGTO
				// VALOR_SALDO
				// VALOR_JUROS_PAGTO
				// entidadeBean.setDataEmissao(Util.dateToLocalDateTime(Date.valueOf(dateDataEmissao_cr.getValue())));
				entidadeBean.setDataEmissao(LocalDateTime.of(dateDataEmissao_cr.getValue(), LocalTime.now()));

				entidadeBean.setDataPrevisaoPagto(
						Util.dateToLocalDateTime(Date.valueOf(dateDataPrevRecebimento_cr.getValue())));
				entidadeBean
						.setDataVencimento(Util.dateToLocalDateTime(Date.valueOf(dateDataVencimento_cr.getValue())));
				// DATA_PAGAMENTO
				entidadeBean.setCodUsuarioLancamento(DadosGlobais.usuarioLogado.getCodigo());
				entidadeBean.setCodUsuarioLancamentoFk(DadosGlobais.usuarioLogado.getCheckDelete());
				// COD_USUARIO_QUITACAO
				// FLAG_TIPO_BAIXA
				entidadeBean.setFlagTipoBaixa(EnumStatusContasReceber.STATUS_ABERTO.index);
				entidadeBean.setOrigemRegistro(EnumOrigemRegistro.INCLUCAO_MANUAL.index);

				// Faltan los campos Historico y ... Moeda,
				// Centro Custo, Tipo Documento

				if (flagInsertUpdate) {

					if (!chkAutomatico_cr.isSelected()) {

						LogRetorno lr = entidadeDao.getByDocumentParcel(Integer.valueOf(txtNoDocumento_cr.getText()),
								Integer.valueOf(txtParcela_cr.getText()), entidadeBean.getCliente());

						if (lr.getObjeto() != null) {
							util.showAlert(DadosGlobais.resourceBundle
									.getString("contasReceberController.alertDocumentParcelExists"), "warning");
							txtParcela_cr.requestFocus();
							return;
						}

						if (lr.getStatus().equals(EnumLogRetornoStatus.ERRO)) {

							util.showAlert(lr.getMsg(), "error");
							return;
						}
					}

					Task<String> TaskInsert = new Task<String>() {

						LogRetorno logRet = new LogRetorno();

						@Override
						protected String call() throws Exception {

							logRet = entidadeDao.insert(entidadeBean, chkAutomatico_cr.isSelected());

							return "-";
						}

						@Override
						protected void succeeded() {
							stg.close();
							// pBar.setProgress(1);
							if (logRet.getStatus().equals(EnumLogRetornoStatus.SUCESSO)) {
								Util.showNotification(DadosGlobais.resourceBundle.getString("notificationSucess"));
								updateTbView("INSERT");
								resetForm(true);
								txtCodOperacao_cr.requestFocus();

							} else {
								util.showAlert(logRet.getMsg(), "error");
							}
						}

						@Override
						protected void failed() {
							stg.close();
							util.tratamentoExcecao(exceptionProperty().getValue().toString(),
									"[ ContasReceberController.actionBtnSave() - INSERT ]");
							// pBar.setProgress(0);
						}

						@Override
						protected void cancelled() {
							// pBar.setProgress(0);
							super.cancelled();
						}

					};
					Thread t = new Thread(TaskInsert);
					t.setDaemon(true);
					t.start();
					stg = ProgressBarForm.showProgressBar(ContasReceber.class, TaskInsert,
							DadosGlobais.resourceBundle.getString("infInsertRegistro"), false);
					// pBar.setProgress(-1);

				} else {

					Task<String> TaskUpdate = new Task<String>() {

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

								updateTbView("UPDATE");

							} else {
								util.showAlert(logRet.getMsg(), "error");
							}

						}

						@Override
						protected void failed() {
							stg.close();
							util.tratamentoExcecao(exceptionProperty().getValue().toString(),
									"[ ContasReceberController.actionBtnSave() - UPDATE ]");
							pBar.setProgress(0);
						}

						@Override
						protected void cancelled() {

							pBar.setProgress(0);
							super.cancelled();

						}

					};

					Thread t = new Thread(TaskUpdate);
					t.setDaemon(true);
					t.start();
					stg = ProgressBarForm.showProgressBar(ContasReceberController.class, TaskUpdate,
							DadosGlobais.resourceBundle.getString("infSaveChange"), false);
					pBar.setProgress(-1);

				}

				// else {
				// loadByID(false, Integer.valueOf(txtCodigo.getText()), false);
				// }
			}
		} else {
			util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
		}

	}

	@SuppressWarnings("unchecked")
	@FXML
	void actionBtnSave_crl(ActionEvent event) {

		if (nivAcessoPermissao.getFlagUpdate().equals(1)) {

			if (!chkAutomatico_crl.isSelected()) {

				LogRetorno lr = entidadeDao.getByDocument(Integer.valueOf(txtNoDocumento_crl.getText()),
						entidadeBean.getCliente());

				if (lr.getStatus().equals(EnumLogRetornoStatus.SUCESSO)) {

					ArrayList<Integer> noParcels = new ArrayList<Integer>();

					for (Object o : lr.getListaObjetos()) {
						for (ContasReceber cr : tbView_crl.getItems()) {
							if (cr.getNoParcela().equals(((ContasReceber) o).getNoParcela())) {
								noParcels.add(cr.getNoParcela());
							}
						}
					}

					if (!noParcels.isEmpty()) {
						String parcel = "";
						for (Integer i : noParcels) {
							if (parcel.isEmpty())
								parcel = i.toString();
							else
								parcel += ", " + i;
						}

						util.showAlert(
								DadosGlobais.resourceBundle
										.getString("contasReceberController.alertDocumentParcelExists1") + " " + parcel,
								"warning");

						return;
					}
				}

				if (lr.getStatus().equals(EnumLogRetornoStatus.ERRO)) {
					util.showAlert(lr.getMsg(), "error");
					return;
				}
			}

			Task<String> TaskInsert = new Task<String>() {

				LogRetorno logRet = new LogRetorno();

				@Override
				protected String call() throws Exception {

					logRet = entidadeDao.insertLot(tbView_crl.getItems(), chkAutomatico_crl.isSelected());

					return "-";
				}

				@Override
				protected void succeeded() {
					stg.close();

					if (logRet.getStatus().equals(EnumLogRetornoStatus.SUCESSO)) {
						Util.showNotification(DadosGlobais.resourceBundle.getString("notificationSucess"));
						listNewContasReceberLotes.clear();
						listNewContasReceberLotes.addAll((ObservableList<ContasReceber>) logRet.getListaObjetos());
						updateTbView("INSERT");
						resetForm(false);
						txtCodOperacao_crl.requestFocus();
					} else {
						util.showAlert(logRet.getMsg(), "error");
					}
				}

				@Override
				protected void failed() {
					stg.close();
					util.tratamentoExcecao(exceptionProperty().getValue().toString(),
							"[ ContasReceberController.actionBtnSave() - INSERT ]");
				}

				@Override
				protected void cancelled() {
					super.cancelled();
				}

			};
			Thread t = new Thread(TaskInsert);
			t.setDaemon(true);
			t.start();
			stg = ProgressBarForm.showProgressBar(ContasReceber.class, TaskInsert,
					DadosGlobais.resourceBundle.getString("infInsertRegistro"), false);
		} else {
			util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
		}

	}

	@FXML
	void actionMiInsetContaReceber(ActionEvent event) {

		if (nivAcessoPermissao.getFlagInsert().equals(1)) {
			// entidadeBean = new ContasReceber();
			flagInsertUpdate = true;
			entidadeBean = (ContasReceber) util.initializeAttribClass(new ContasReceber());
			btnSave_cr.setDisable(false);
			btnCancel_cr.setDisable(false);
			resetForm(true);
			Util.openFormCadastro(anchorPaneListagem, anchorPaneDetalhes_cr, 0);

			// --START EMPTY---
			lblErrorMessage1.setText("");

			txtCodOperacao_cr.requestFocus();
		} else {
			util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
		}
	}

	@FXML
	void actionMiInsetContaReceberLotes(ActionEvent event) {

		if (nivAcessoPermissao.getFlagInsert().equals(1)) {
			entidadeBean = (ContasReceber) util.initializeAttribClass(new ContasReceber());
			btnSave_crl.setDisable(false);
			btnCancel_cr.setDisable(false);
			resetForm(false);
			Util.openFormCadastro(anchorPaneListagem, anchorPaneDetalhes_crl, 0);

			// --START EMPTY---
			lblErrorMessage.setText("");

			txtCodOperacao_crl.requestFocus();
			btnGeraContasReceber.setDisable(false);
		} else {
			util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
		}

	}

	@FXML
	void actionBtnHelp(ActionEvent event) {

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

		Bounds boundsScene = btnHelp.localToScene(btnHelp.getBoundsInLocal());

		if (!popOver.isShowing())
			popOver.show(btnHelp, boundsScene.getMaxX() - 15, boundsScene.getMaxY());
		else
			popOver.hide();

	}

	@FXML
	void keyPressedTxtParcela(KeyEvent event) {

		if (event.getCode().equals(KeyCode.ENTER))
			dateDataEmissao_cr.requestFocus();

	}

	@FXML
	void actionSave_ald(ActionEvent event) {

		for (Object cr : listCROperations)
			((ContasReceber) cr).setDataVencimento(Util.dateToLocalDateTime(Date.valueOf(dateExpiration.getValue())));

		Util.fechaTelaCadastro(anchorPaneListagem, anchorPaneAlteraData);

		actionOperatios(listCROperations);

	}

	@FXML
	void actionTxtCod(ActionEvent event) {
		// Filtros

		// Contas receber
		if (event.getSource().equals(txtCodOperacao_cr) || txtCodOperacao_cr.getRight().isPressed()
				|| event.getSource().equals(txtCodOperacao_crl) || txtCodOperacao_crl.getRight().isPressed())
			showSearch("OPERACAO");

		else if (event.getSource().equals(txtCodCliente_cr) || txtCodCliente_cr.getRight().isPressed()
				|| event.getSource().equals(txtCodCliente_crl) || txtCodCliente_crl.getRight().isPressed())
			showSearch("CLIENTE");

		else if (event.getSource().equals(txtCodSecao_cr) || txtCodSecao_cr.getRight().isPressed()
				|| event.getSource().equals(txtCodSecao_crl) || txtCodSecao_crl.getRight().isPressed())
			showSearch("SECAO");

		else if (event.getSource().equals(txtCodPortador_cr) || txtCodPortador_cr.getRight().isPressed()
				|| event.getSource().equals(txtCodPortador_crl) || txtCodPortador_crl.getRight().isPressed())
			showSearch("PORTADOR");

		else if (event.getSource().equals(txtCodPlanoContas_cr) || txtCodPlanoContas_cr.getRight().isPressed()
				|| event.getSource().equals(txtCodPlanoContas_crl) || txtCodPlanoContas_crl.getRight().isPressed())
			showSearch("PLANOS_CONTAS");

		else if (event.getSource().equals(txtCodVendedor_cr) || txtCodVendedor_cr.getRight().isPressed()
				|| event.getSource().equals(txtCodVendedor_crl) || txtCodVendedor_crl.getRight().isPressed())
			showSearch("VENDEDOR");
	}

	@FXML
	void keyPressedTxtCod(KeyEvent event) {
		// ---------------------------------------------

		switch (event.getCode()) {
		case F2:
			// Filtros
			if (event.getSource().equals(txtCodCliente_filt))
				showSearch("CLIENTE_FILTER");

			// Contas Receber
			else if (event.getSource().equals(txtCodOperacao_cr) || event.getSource().equals(txtCodOperacao_crl))
				showSearch("OPERACAO");

			else if (event.getSource().equals(txtCodCliente_cr) || event.getSource().equals(txtCodCliente_crl))
				showSearch("CLIENTE");

			else if (event.getSource().equals(txtCodSecao_cr) || event.getSource().equals(txtCodSecao_crl))
				showSearch("SECAO");

			else if (event.getSource().equals(txtCodPortador_cr) || event.getSource().equals(txtCodPortador_crl))
				showSearch("PORTADOR");

			else if (event.getSource().equals(txtCodPlanoContas_cr) || event.getSource().equals(txtCodPlanoContas_crl))
				showSearch("PLANOS_CONTAS");

			else if (event.getSource().equals(txtCodVendedor_cr) || event.getSource().equals(txtCodVendedor_crl))
				showSearch("VENDEDOR");

			break;

		case ENTER:

			// Contas Receber
			if (event.getSource().equals(txtCodOperacao_cr) && !Util.noEmpty(txtCodOperacao_cr))
				searchOperacao(Integer.parseInt(txtCodOperacao_cr.getText()));

			else if (event.getSource().equals(txtCodCliente_cr))
				searchCliente(Integer.parseInt(txtCodCliente_cr.getText()));

			else if (event.getSource().equals(txtCodSecao_cr) && !Util.noEmpty(txtCodSecao_cr))
				searchSecao(Integer.parseInt(txtCodSecao_cr.getText()));

			else if (event.getSource().equals(txtCodPortador_cr) && !Util.noEmpty(txtCodPortador_cr))
				searchPortador(Integer.parseInt(txtCodPortador_cr.getText()));

			else if (event.getSource().equals(txtCodPlanoContas_cr) && !Util.noEmpty(txtCodPlanoContas_cr))
				searchPlanoContas(Integer.parseInt(txtCodPlanoContas_cr.getText()));

			else if (event.getSource().equals(txtCodVendedor_cr) && !Util.noEmpty(txtCodPlanoContas_cr))
				searchVendedor(Integer.parseInt(txtCodVendedor_cr.getText()));
			// Cotas Receber Lotes
			if (event.getSource().equals(txtCodOperacao_crl) && !Util.noEmpty(txtCodOperacao_crl))
				searchOperacao(Integer.parseInt(txtCodOperacao_crl.getText()));

			else if (event.getSource().equals(txtCodCliente_crl))
				searchCliente(Integer.parseInt(txtCodCliente_crl.getText()));

			else if (event.getSource().equals(txtCodSecao_crl) && !Util.noEmpty(txtCodSecao_crl))
				searchSecao(Integer.parseInt(txtCodSecao_crl.getText()));

			else if (event.getSource().equals(txtCodPortador_crl) && !Util.noEmpty(txtCodPortador_crl))
				searchPortador(Integer.parseInt(txtCodPortador_crl.getText()));

			else if (event.getSource().equals(txtCodPlanoContas_crl) && !Util.noEmpty(txtCodPlanoContas_crl))
				searchPlanoContas(Integer.parseInt(txtCodPlanoContas_crl.getText()));

			else if (event.getSource().equals(txtCodVendedor_crl) && !Util.noEmpty(txtCodPlanoContas_crl))
				searchVendedor(Integer.parseInt(txtCodVendedor_crl.getText()));

			event.consume();

			break;

		case TAB:

			if (!event.isShiftDown()) {

				// Conta Receber
				if (event.getSource().equals(txtCodOperacao_cr))
					// txtCodCliente_cr.requestFocus();
					Util.setFocus(txtCodCliente_cr);

				else if (event.getSource().equals(txtCodCliente_cr))
					// txtNoDocumento_cr.requestFocus();
					Util.setFocus(txtNoDocumento_cr);

				else if (event.getSource().equals(txtCodMoeda_cr))
					Util.setFocus(txtCodSecao_cr);

				else if (event.getSource().equals(txtCodSecao_cr))
					// txtCodCentroCusto_cr.requestFocus();
					Util.setFocus(txtCodCentroCusto_cr);

				else if (event.getSource().equals(txtCodCentroCusto_cr))
					// txtCodPortador_cr.requestFocus();
					Util.setFocus(txtCodPortador_cr);

				else if (event.getSource().equals(txtCodPortador_cr))
					// txtCodPlanoContas_cr.requestFocus();
					Util.setFocus(txtCodPlanoContas_cr);

				else if (event.getSource().equals(txtCodPlanoContas_cr))
					// txtCodVendedor_cr.requestFocus();
					Util.setFocus(txtCodVendedor_cr);

				else if (event.getSource().equals(txtCodVendedor_cr))
					// txtCodTipoDoc_cr.requestFocus();
					Util.setFocus(txtCodVendedor_cr);
				// Contas Receber Lotes
				else if (event.getSource().equals(txtCodOperacao_crl))
					Util.setFocus(txtCodCliente_crl);

				else if (event.getSource().equals(txtCodCliente_crl))
					Util.setFocus(txtNoDocumento_crl);

				else if (event.getSource().equals(txtCodSecao_crl))
					Util.setFocus(txtCodCentroCusto_crl);

				else if (event.getSource().equals(txtCodCentroCusto_crl))
					Util.setFocus(txtCodTipoDoc_crl);

				else if (event.getSource().equals(txtCodTipoDoc_crl))
					Util.setFocus(txtCodPortador_crl);

				else if (event.getSource().equals(txtCodPortador_crl))
					Util.setFocus(txtCodPlanoContas_crl);

				else if (event.getSource().equals(txtCodPlanoContas_crl))
					Util.setFocus(txtCodMoeda_crl);

				else if (event.getSource().equals(txtCodMoeda_crl))
					Util.setFocus(txtCodDigitador_crl);

				else if (event.getSource().equals(txtCodDigitador_crl))
					Util.setFocus(txtCodVendedor_crl);

				else if (event.getSource().equals(txtCodVendedor_crl))
					Util.setFocus(dateDataEmissao_crl);

				// event.consume();
			}

			break;

		case BACK_SPACE:

			break;

		case DELETE:

			break;

		case RIGHT:

			break;

		case LEFT:

			break;

		default:

			break;
		}

	}

	public void aaa(CustomTextField c) {

		c.requestFocus();
	}

	@FXML
	void onClickLblCod(MouseEvent event) {

	}

	/**
	 * Show search form
	 */
	public void showSearch(String actionFrom) {

		ObservableList<ComboBoxFilter> list = FXCollections.observableArrayList();
		Object obj = null;

		switch (actionFrom) {
		case "CLIENTE_FILTER":
			list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("codigo"), 1, "codigo"));
			list.add(new ComboBoxFilter("Nome Fantasia", 2, "fantasia"));
			list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("codemp"), 3, "codemp"));

			util.showSearchMultiSelectionView("Clientes", "fantasia", ClienteDAO.class, list, txtCodCliente_filt,
					cboxStatus_filt);
			break;

		case "OPERACAO":
			list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("codigo"), 1, "codigo"));
			list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("descricao"), 2, "descricao"));
			list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("codemp"), 3, "codemp"));

			Util.flagContasReceber = true;
			obj = util.showSearchGetParameters("Operaçôes Financeiro", "descricao", OperacaoFinanceiroDAO.class, list);
			if (obj != null) {
				OperacaoFinanceiro opeFin = (OperacaoFinanceiro) obj;
				if (anchorPaneDetalhes_cr.isVisible()) {
					txtCodOperacao_cr.setText(String.valueOf(opeFin.getCodigo()));
					txtOperacao_cr.setText(opeFin.getDescricao());

					entidadeBean.setCodOperacaoFin(opeFin.getCodigo());
					entidadeBean.setCodOperacaoFinFk(opeFin.getCheckDelete());
					txtCodCliente_cr.requestFocus();
				} else if (anchorPaneDetalhes_crl.isVisible()) {
					txtCodOperacao_crl.setText(String.valueOf(opeFin.getCodigo()));
					txtOperacao_crl.setText(opeFin.getDescricao());

					entidadeBean.setCodOperacaoFin(opeFin.getCodigo());
					entidadeBean.setCodOperacaoFinFk(opeFin.getCheckDelete());
					txtCodCliente_crl.requestFocus();
				}

			}

			break;

		case "CLIENTE":
			list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("codigo"), 1, "codigo"));
			list.add(new ComboBoxFilter("Nome Fantasia", 2, "fantasia"));
			list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("codemp"), 3, "codemp"));

			obj = util.showSearchGetParameters("Clientes", "fantasia", ClienteDAO.class, list);

			if (obj != null) {
				Cliente cliente = (Cliente) obj;
				if (anchorPaneDetalhes_cr.isVisible()) {
					txtCodCliente_cr.setText(String.valueOf(cliente.getCodigo()));
					txtCliente_cr.setText(cliente.getFantasia());
					entidadeBean.setCliente(cliente);
					txtNoDocumento_cr.requestFocus();
				} else if (anchorPaneDetalhes_crl.isVisible()) {
					txtCodCliente_crl.setText(String.valueOf(cliente.getCodigo()));
					txtCliente_crl.setText(cliente.getFantasia());
					entidadeBean.setCliente(cliente);
					txtNoDocumento_crl.requestFocus();
				}

			}

			break;

		case "SECAO":
			list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("codigo"), 1, "codigo"));
			list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("descricao"), 2, "descricao"));
			list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("codemp"), 3, "codemp"));

			obj = util.showSearchGetParameters("Seção", "descricao", SecaoDAO.class, list);
			if (obj != null) {
				Secao secao = (Secao) obj;
				if (anchorPaneDetalhes_cr.isVisible()) {
					txtCodSecao_cr.setText(String.valueOf(secao.getCodigo()));
					txtSecao_cr.setText(secao.getDescricao());

					entidadeBean.setCodSecao(secao.getCodigo());
					entidadeBean.setCodSecaoFk(secao.getCheckDelete());
					txtCodCentroCusto_cr.requestFocus();
				} else if (anchorPaneDetalhes_crl.isVisible()) {
					txtCodSecao_crl.setText(String.valueOf(secao.getCodigo()));
					txtSecao_crl.setText(secao.getDescricao());

					entidadeBean.setCodSecao(secao.getCodigo());
					entidadeBean.setCodSecaoFk(secao.getCheckDelete());
					txtCodCentroCusto_crl.requestFocus();
				}
			}

			break;

		case "PORTADOR":
			list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("codigo"), 1, "codigo"));
			list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("descricao"), 2, "descricao"));
			list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("codemp"), 3, "codemp"));

			obj = util.showSearchGetParameters("Portador", "descricao", PortadorDAO.class, list);
			if (obj != null) {
				Portador portador = (Portador) obj;
				if (anchorPaneDetalhes_cr.isVisible()) {
					txtCodPortador_cr.setText(String.valueOf(portador.getCodigo()));
					txtPortador_cr.setText(portador.getDescricao());

					entidadeBean.setCodPortador(portador.getCodigo());
					entidadeBean.setCodPortadorFk(portador.getCheckDelete());
					txtCodPlanoContas_cr.requestFocus();
				} else if (anchorPaneDetalhes_crl.isVisible()) {
					txtCodPortador_crl.setText(String.valueOf(portador.getCodigo()));
					txtPortador_crl.setText(portador.getDescricao());

					entidadeBean.setCodPortador(portador.getCodigo());
					entidadeBean.setCodPortadorFk(portador.getCheckDelete());
					txtCodPlanoContas_crl.requestFocus();
				}

			}

			break;

		case "PLANOS_CONTAS":
			list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("codigo"), 1, "codigo"));
			list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("descricao"), 2, "descricao"));
			list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("codemp"), 3, "codemp"));

			obj = util.showSearchGetParameters("Planos de Contas", "descricao", PlanoContaDAO.class, list);
			if (obj != null) {
				PlanoConta planoConta = (PlanoConta) obj;
				if (anchorPaneDetalhes_cr.isVisible()) {
					txtCodPlanoContas_cr.setText(String.valueOf(planoConta.getCodigo()));
					txtPlanoContas_cr.setText(planoConta.getDescricao());

					entidadeBean.setCodPlanoContas(planoConta.getCodigo());
					entidadeBean.setCodPlanoContasFk(planoConta.getCheckDelete());
					txtCodVendedor_cr.requestFocus();
				} else if (anchorPaneDetalhes_crl.isVisible()) {
					txtCodPlanoContas_crl.setText(String.valueOf(planoConta.getCodigo()));
					txtPlanoContas_crl.setText(planoConta.getDescricao());

					entidadeBean.setCodPlanoContas(planoConta.getCodigo());
					entidadeBean.setCodPlanoContasFk(planoConta.getCheckDelete());
					txtCodVendedor_crl.requestFocus();
				}

			}

			break;

		case "VENDEDOR":
			list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("codigo"), 1, "codigo"));
			list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("descricao"), 2, "descricao"));
			list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("codemp"), 3, "codemp"));

			Util.flagVendedor = true;
			obj = util.showSearchGetParameters("Funcioanrio", "descricao", FuncionarioDAO.class, list);

			if (obj != null) {
				Funcionario vendedor = (Funcionario) obj;
				if (anchorPaneDetalhes_cr.isVisible()) {
					txtCodVendedor_cr.setText(String.valueOf(vendedor.getCodigo()));
					txtVendedor_cr.setText(vendedor.getDescricao());

					entidadeBean.setCodVendedor(vendedor.getCodigo());
					entidadeBean.setCodVendedorFk(vendedor.getCheckDelete());
					txtCodTipoDoc_cr.requestFocus();
				} else if (anchorPaneDetalhes_crl.isVisible()) {
					txtCodVendedor_crl.setText(String.valueOf(vendedor.getCodigo()));
					txtVendedor_crl.setText(vendedor.getDescricao());

					entidadeBean.setCodVendedor(vendedor.getCodigo());
					entidadeBean.setCodVendedorFk(vendedor.getCheckDelete());
					dateDataEmissao_crl.requestFocus();
				}

			}

			break;

		default:
			break;
		}

	}

	/**
	 * Method to search Cliente by Id
	 * 
	 * @param valorBusca
	 *            Id Cliente
	 */
	public void searchCliente(int valorBusca) {

		ClienteDAO clienteDAO = new ClienteDAO();

		Task<String> TarefaSearch = new Task<String>() {

			LogRetorno logRet = new LogRetorno();

			@Override
			protected String call() throws Exception {

				logRet = clienteDAO.getById(valorBusca);
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				// pBar.setProgress(1);
				if (logRet.getStatus().equals(EnumLogRetornoStatus.ERRO)) {
					if (anchorPaneDetalhes_cr.isVisible()) {
						Util.setStyleError(true, txtCodCliente_cr);
						txtCodCliente_cr.clear();
						txtCliente_cr.clear();
					} else if (anchorPaneDetalhes_crl.isVisible()) {
						Util.setStyleError(true, txtCodCliente_crl);
						txtCodCliente_crl.clear();
						txtCliente_crl.clear();
					}

				} else {
					if (logRet.getStatus().equals(EnumLogRetornoStatus.REGDUPLICADO))
						util.alertException(logRet.getMsg(), "", false);
					if (logRet.getStatus().equals(EnumLogRetornoStatus.SUCESSO)) {
						if (anchorPaneDetalhes_cr.isVisible()) {
							entidadeBean.setCliente((Cliente) logRet.getObjeto());
							txtCliente_cr.setText(((Cliente) logRet.getObjeto()).getFantasia());
							txtNoDocumento_cr.requestFocus();
						} else if (anchorPaneDetalhes_crl.isVisible()) {
							entidadeBean.setCliente((Cliente) logRet.getObjeto());
							txtCliente_crl.setText(((Cliente) logRet.getObjeto()).getFantasia());
							txtNoDocumento_crl.requestFocus();
						}

					}
				}
			}

			@Override
			protected void failed() {
				stg.close();
				util.tratamentoExcecao(exceptionProperty().getValue().toString(),
						"[ ContasReceberController.searchSecao() ]");
				// pBar.setProgress(0);
			}

			@Override
			protected void cancelled() {
				// tbView.getItems().clear();
				// pBar.setProgress(0);
				super.cancelled();
			}

		};
		Thread t = new Thread(TarefaSearch);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(ContasReceberController.class, TarefaSearch,
				DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
		// pBar.setProgress(-1);

	}

	/**
	 * Method to search Operacao Financeiro Contas Receber by Id
	 * 
	 * @param valorBusca
	 *            Id Operacao
	 */
	public void searchOperacao(int valorBusca) {

		OperacaoFinanceiroDAO opeFinDAO = new OperacaoFinanceiroDAO();

		Task<String> TarefaSearch = new Task<String>() {

			LogRetorno logRet = new LogRetorno();

			@Override
			protected String call() throws Exception {

				logRet = opeFinDAO.getOperacoesById(valorBusca);
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				// pBar.setProgress(1);
				if (logRet.getStatus().equals(EnumLogRetornoStatus.ERRO)) {
					if (anchorPaneDetalhes_cr.isVisible()) {
						Util.setStyleError(true, txtCodOperacao_cr);
						txtCodOperacao_cr.clear();
						txtOperacao_cr.clear();
					} else if (anchorPaneDetalhes_crl.isVisible()) {
						Util.setStyleError(true, txtCodOperacao_crl);
						txtCodOperacao_crl.clear();
						txtOperacao_crl.clear();
					}
				} else {
					if (logRet.getStatus().equals(EnumLogRetornoStatus.REGDUPLICADO))
						util.alertException(logRet.getMsg(), "", false);
					if (logRet.getStatus().equals(EnumLogRetornoStatus.SUCESSO)) {
						if (anchorPaneDetalhes_cr.isVisible()) {
							txtOperacao_cr.setText(((OperacaoFinanceiro) logRet.getObjeto()).getDescricao());
							entidadeBean.setCodOperacaoFin(((OperacaoFinanceiro) logRet.getObjeto()).getCodigo());
							entidadeBean
									.setCodOperacaoFinFk(((OperacaoFinanceiro) logRet.getObjeto()).getCheckDelete());
							txtCodCliente_cr.requestFocus();
						} else if (anchorPaneDetalhes_crl.isVisible()) {
							txtOperacao_crl.setText(((OperacaoFinanceiro) logRet.getObjeto()).getDescricao());
							entidadeBean.setCodOperacaoFin(((OperacaoFinanceiro) logRet.getObjeto()).getCodigo());
							entidadeBean
									.setCodOperacaoFinFk(((OperacaoFinanceiro) logRet.getObjeto()).getCheckDelete());
							txtCodCliente_crl.requestFocus();
						}
					}
				}
			}

			@Override
			protected void failed() {
				stg.close();
				util.tratamentoExcecao(exceptionProperty().getValue().toString(),
						"[ ContasReceberController.searchOperacao() ]");
				// pBar.setProgress(0);
			}

			@Override
			protected void cancelled() {
				// tbView.getItems().clear();
				// pBar.setProgress(0);
				super.cancelled();
			}

		};
		Thread t = new Thread(TarefaSearch);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(ContasReceberController.class, TarefaSearch,
				DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
		// pBar.setProgress(-1);

	}

	/**
	 * Method to search Secao by Id
	 * 
	 * @param valorBusca
	 *            Id Secao
	 */
	public void searchSecao(int valorBusca) {

		SecaoDAO secaoDAO = new SecaoDAO();

		Task<String> TarefaSearch = new Task<String>() {

			LogRetorno logRet = new LogRetorno();

			@Override
			protected String call() throws Exception {

				logRet = secaoDAO.getById(valorBusca);
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				// pBar.setProgress(1);
				if (logRet.getStatus().equals(EnumLogRetornoStatus.ERRO)) {
					if (anchorPaneDetalhes_cr.isVisible()) {
						Util.setStyleError(true, txtCodSecao_cr);
						txtCodSecao_cr.clear();
						txtSecao_cr.clear();
					} else if (anchorPaneDetalhes_crl.isVisible()) {
						Util.setStyleError(true, txtCodSecao_crl);
						txtCodSecao_crl.clear();
						txtSecao_crl.clear();
					}

				} else {
					if (logRet.getStatus().equals(EnumLogRetornoStatus.REGDUPLICADO))
						util.alertException(logRet.getMsg(), "", false);
					if (logRet.getStatus().equals(EnumLogRetornoStatus.SUCESSO)) {
						if (anchorPaneDetalhes_cr.isVisible()) {
							txtSecao_cr.setText(((Secao) logRet.getObjeto()).getDescricao());
							entidadeBean.setCodSecao(((Secao) logRet.getObjeto()).getCodigo());
							entidadeBean.setCodSecaoFk(((Secao) logRet.getObjeto()).getCheckDelete());
							txtCodCentroCusto_cr.requestFocus();
						} else if (anchorPaneDetalhes_crl.isVisible()) {
							txtSecao_crl.setText(((Secao) logRet.getObjeto()).getDescricao());
							entidadeBean.setCodSecao(((Secao) logRet.getObjeto()).getCodigo());
							entidadeBean.setCodSecaoFk(((Secao) logRet.getObjeto()).getCheckDelete());
							txtCodCentroCusto_crl.requestFocus();
						}
					}
				}
			}

			@Override
			protected void failed() {
				stg.close();
				util.tratamentoExcecao(exceptionProperty().getValue().toString(),
						"[ ContasReceberController.searchSecao() ]");
				// pBar.setProgress(0);
			}

			@Override
			protected void cancelled() {
				// tbView.getItems().clear();
				// pBar.setProgress(0);
				super.cancelled();
			}

		};
		Thread t = new Thread(TarefaSearch);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(ContasReceberController.class, TarefaSearch,
				DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
		// pBar.setProgress(-1);

	}

	/**
	 * Method to search Portador by Id
	 * 
	 * @param valorBusca
	 *            Id Portador
	 */
	public void searchPortador(int valorBusca) {

		PortadorDAO portadorDAO = new PortadorDAO();

		Task<String> TarefaSearch = new Task<String>() {

			LogRetorno logRet = new LogRetorno();

			@Override
			protected String call() throws Exception {

				logRet = portadorDAO.getById(valorBusca);
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				// pBar.setProgress(1);
				if (logRet.getStatus().equals(EnumLogRetornoStatus.ERRO)) {
					if (anchorPaneDetalhes_cr.isVisible()) {
						Util.setStyleError(true, txtCodPortador_cr);
						txtCodPortador_cr.clear();
						txtPortador_cr.clear();
					} else if (anchorPaneDetalhes_crl.isVisible()) {
						Util.setStyleError(true, txtCodPortador_crl);
						txtCodPortador_crl.clear();
						txtPortador_crl.clear();
					}
				} else {
					if (logRet.getStatus().equals(EnumLogRetornoStatus.REGDUPLICADO))
						util.alertException(logRet.getMsg(), "", false);
					if (logRet.getStatus().equals(EnumLogRetornoStatus.SUCESSO)) {
						if (anchorPaneDetalhes_cr.isVisible()) {
							txtPortador_cr.setText(((Portador) logRet.getObjeto()).getDescricao());
							entidadeBean.setCodPortador(((Portador) logRet.getObjeto()).getCodigo());
							entidadeBean.setCodPortadorFk(((Portador) logRet.getObjeto()).getCheckDelete());
							txtCodPlanoContas_cr.requestFocus();
						} else if (anchorPaneDetalhes_crl.isVisible()) {
							txtPortador_crl.setText(((Portador) logRet.getObjeto()).getDescricao());
							entidadeBean.setCodPortador(((Portador) logRet.getObjeto()).getCodigo());
							entidadeBean.setCodPortadorFk(((Portador) logRet.getObjeto()).getCheckDelete());
							txtCodPlanoContas_crl.requestFocus();
						}
					}
				}
			}

			@Override
			protected void failed() {
				stg.close();
				util.tratamentoExcecao(exceptionProperty().getValue().toString(),
						"[ ContasReceberController.searchPortador() ]");
				// pBar.setProgress(0);
			}

			@Override
			protected void cancelled() {
				// tbView.getItems().clear();
				// pBar.setProgress(0);
				super.cancelled();
			}

		};
		Thread t = new Thread(TarefaSearch);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(ContasReceberController.class, TarefaSearch,
				DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
		// pBar.setProgress(-1);

	}

	/**
	 * Method to search Plano Contas by Id
	 * 
	 * @param valorBusca
	 *            Id Plano Contas
	 */
	public void searchPlanoContas(int valorBusca) {

		PlanoContaDAO planoContaDAO = new PlanoContaDAO();

		Task<String> TarefaSearch = new Task<String>() {

			LogRetorno logRet = new LogRetorno();

			@Override
			protected String call() throws Exception {

				logRet = planoContaDAO.getById(valorBusca);
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				// pBar.setProgress(1);
				if (logRet.getStatus().equals(EnumLogRetornoStatus.ERRO)) {
					if (anchorPaneDetalhes_cr.isVisible()) {
						Util.setStyleError(true, txtCodPlanoContas_cr);
						txtCodPlanoContas_cr.clear();
						txtPlanoContas_cr.clear();
					} else if (anchorPaneDetalhes_crl.isVisible()) {
						Util.setStyleError(true, txtCodPlanoContas_crl);
						txtCodPlanoContas_crl.clear();
						txtPlanoContas_crl.clear();
					}
				} else {
					if (logRet.getStatus().equals(EnumLogRetornoStatus.REGDUPLICADO))
						util.alertException(logRet.getMsg(), "", false);
					if (logRet.getStatus().equals(EnumLogRetornoStatus.SUCESSO)) {
						if (anchorPaneDetalhes_cr.isVisible()) {
							txtPlanoContas_cr.setText(((PlanoConta) logRet.getObjeto()).getDescricao());
							entidadeBean.setCodPlanoContas(((PlanoConta) logRet.getObjeto()).getCodigo());
							entidadeBean.setCodPlanoContasFk(((PlanoConta) logRet.getObjeto()).getCheckDelete());
							txtCodVendedor_cr.requestFocus();
						} else if (anchorPaneDetalhes_crl.isVisible()) {
							txtPlanoContas_crl.setText(((PlanoConta) logRet.getObjeto()).getDescricao());
							entidadeBean.setCodPlanoContas(((PlanoConta) logRet.getObjeto()).getCodigo());
							entidadeBean.setCodPlanoContasFk(((PlanoConta) logRet.getObjeto()).getCheckDelete());
							txtCodVendedor_crl.requestFocus();
						}
					}
				}
			}

			@Override
			protected void failed() {
				stg.close();
				util.tratamentoExcecao(exceptionProperty().getValue().toString(),
						"[ ContasReceberController.searchPlanoContas() ]");
				// pBar.setProgress(0);
			}

			@Override
			protected void cancelled() {
				// tbView.getItems().clear();
				// pBar.setProgress(0);
				super.cancelled();
			}

		};
		Thread t = new Thread(TarefaSearch);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(ContasReceberController.class, TarefaSearch,
				DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
		// pBar.setProgress(-1);

	}

	/**
	 * Method to search Vendedor by Id
	 * 
	 * @param valorBusca
	 *            Id Vendedor
	 */
	public void searchVendedor(int valorBusca) {

		FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

		Task<String> TarefaSearch = new Task<String>() {

			LogRetorno logRet = new LogRetorno();

			@Override
			protected String call() throws Exception {

				logRet = funcionarioDAO.getVencedorById(valorBusca);
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				// pBar.setProgress(1);
				if (logRet.getStatus().equals(EnumLogRetornoStatus.ERRO)) {
					if (anchorPaneDetalhes_cr.isVisible()) {
						Util.setStyleError(true, txtCodVendedor_cr);
						txtCodVendedor_cr.clear();
						txtVendedor_cr.clear();
					} else if (anchorPaneDetalhes_crl.isVisible()) {
						Util.setStyleError(true, txtCodVendedor_crl);
						txtCodVendedor_crl.clear();
						txtVendedor_crl.clear();
					}
				} else {
					if (logRet.getStatus().equals(EnumLogRetornoStatus.REGDUPLICADO))
						util.alertException(logRet.getMsg(), "", false);
					if (logRet.getStatus().equals(EnumLogRetornoStatus.SUCESSO)) {
						if (anchorPaneDetalhes_cr.isVisible()) {
							txtVendedor_cr.setText(((Funcionario) logRet.getObjeto()).getDescricao());
							entidadeBean.setCodVendedor(((Funcionario) logRet.getObjeto()).getCodigo());
							entidadeBean.setCodVendedorFk(((Funcionario) logRet.getObjeto()).getCheckDelete());
						} else if (anchorPaneDetalhes_crl.isVisible()) {
							txtVendedor_crl.setText(((Funcionario) logRet.getObjeto()).getDescricao());
							entidadeBean.setCodVendedor(((Funcionario) logRet.getObjeto()).getCodigo());
							entidadeBean.setCodVendedorFk(((Funcionario) logRet.getObjeto()).getCheckDelete());
							dateDataEmissao_crl.requestFocus();
						}
					}
				}
			}

			@Override
			protected void failed() {
				stg.close();
				util.tratamentoExcecao(exceptionProperty().getValue().toString(),
						"[ ContasReceberController.searchVendedor() ]");
				// pBar.setProgress(0);
			}

			@Override
			protected void cancelled() {
				// tbView.getItems().clear();
				// pBar.setProgress(0);
				super.cancelled();
			}

		};
		Thread t = new Thread(TarefaSearch);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(ContasReceberController.class, TarefaSearch,
				DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
		// pBar.setProgress(-1);

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
					listaRegistros = FXCollections.observableArrayList(entidadeDao.getList(paramFlagAtivo));
				} else if (tipoConsulta.equals("filter")) {
					// CONSULTA COM FILTROS ATIVOS
					// switch (cboxFlagAtivo.getValue().getField()) {
					// case "0":
					// paramFlagAtivo = Arrays.asList(0);
					// break;
					// case "1":
					// paramFlagAtivo = Arrays.asList(1);
					// break;
					// case "2":
					// paramFlagAtivo = Arrays.asList(0, 1);
					// break;
					// default:
					// break;
					// }
					// String parametroBusca = null;
					//
					// if (txtFilterColumn.getText().isEmpty()) {
					//
					// listaRegistros =
					// FXCollections.observableArrayList(entidadeDao.filterByColumn(
					// cboxFilterColumn.getValue().getField(),
					// txtFilterColumn.getText(),
					// cboxFilterColumn.getValue().getAction(),
					// paramFlagAtivo));
					//
					// } else {
					//
					// if (cboxFilterColumn.getValue().getAction().equals(1)) {
					//
					// boolean temp = true;
					// for (int i = 0; i < txtFilterColumn.getText().length();
					// i++)
					// if
					// (!Character.toString(txtFilterColumn.getText().charAt(i)).matches("[0-9]"))
					// {
					// temp = false;
					// break;
					// }
					//
					// if (temp && txtFilterColumn.getText().length() <= 8)
					// parametroBusca = txtFilterColumn.getText();
					// else
					// parametroBusca = "0";
					//
					// } else if
					// (cboxFilterColumn.getValue().getAction().equals(2)) {
					// parametroBusca = txtFilterColumn.getText();
					// }
					//
					// listaRegistros = FXCollections
					// .observableArrayList(entidadeDao.filterByColumn(cboxFilterColumn.getValue().getField(),
					// parametroBusca, cboxFilterColumn.getValue().getAction(),
					// paramFlagAtivo));
					// }
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
				}
				// else txtFilterColumn.requestFocus();
			}

			@Override
			protected void failed() {
				stg.close();
				util.tratamentoExcecao(exceptionProperty().getValue().toString(),
						"[ ConstasreceberController.tafefaConsulta() ]");
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
		stg = ProgressBarForm.showProgressBar(ContasReceberController.class, TarefaRefresh,
				DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
		pBar.setProgress(-1);

	}

	public void loadByID(Integer noDocumento, Integer noParcela, Cliente cli) {

		// if ((!flagLastRegistro && codigo > 0) || (flagLastRegistro)) {

		Task<String> TarefaRefresh = new Task<String>() {
			LogRetorno logRet = new LogRetorno();

			@Override
			protected String call() throws Exception {

				logRet = entidadeDao.getByDocumentParcel(noDocumento, noParcela, cli);

				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				// pBar.setProgress(1);
				entidadeBean = (ContasReceber) logRet.getObjeto();

				if (entidadeBean != null) {

					if (logRet.getStatus().equals(EnumLogRetornoStatus.REGDUPLICADO))
						util.alertException(logRet.getMsg(), "", false);

					// Disable components
					lblCodOperacao_cr.setDisable(true);
					txtCodOperacao_cr.setDisable(true);
					txtOperacao_cr.setDisable(true);
					lblCodCliente_cr.setDisable(true);
					txtCodCliente_cr.setDisable(true);
					txtCliente_cr.setDisable(true);
					lblNoDocumento_cr.setDisable(true);
					txtNoDocumento_cr.setDisable(true);
					lblParcela_cr.setDisable(true);
					txtParcela_cr.setDisable(true);
					lblDataEmissao_cr.setDisable(true);
					dateDataEmissao_cr.setDisable(true);

					txtCodOperacao_cr.setText(entidadeBean.getOperacaoFinanceiro().getCodigo().toString());
					txtOperacao_cr.setText(entidadeBean.getOperacaoFinanceiro().getDescricao());
					txtCodCliente_cr.setText(entidadeBean.getCliente().getCodigo().toString());
					txtCliente_cr.setText(entidadeBean.getCliente().getFantasia());
					txtNoDocumento_cr.setText(entidadeBean.getNoDocto().toString());
					txtParcela_cr.setText(entidadeBean.getNoParcela().toString());

					dateDataPrevRecebimento_cr.setValue(LocalDate.of(entidadeBean.getDataPrevisaoPagto().getYear(),
							entidadeBean.getDataPrevisaoPagto().getMonth(),
							entidadeBean.getDataPrevisaoPagto().getDayOfMonth()));

					dateDataEmissao_cr.setValue(LocalDate.of(entidadeBean.getDataEmissao().getYear(),
							entidadeBean.getDataEmissao().getMonth(), entidadeBean.getDataEmissao().getDayOfMonth()));

					dateDataVencimento_cr.setValue(LocalDate.of(entidadeBean.getDataVencimento().getYear(),
							entidadeBean.getDataVencimento().getMonth(),
							entidadeBean.getDataVencimento().getDayOfMonth()));

					txtValorBruto_cr.setText(entidadeBean.getValorBrutoDocto().toString());
					txtValorDesconto_rc.setText(entidadeBean.getValorDesconto().toString());
					txtValorLiquido_cr.setText(entidadeBean.getValorLiquido().toString());

					txtHistorico_cr.setText(entidadeBean.getHistorico());

					txtCodSecao_cr.setText(
							entidadeBean.getSecao() != null ? entidadeBean.getSecao().getCodigo().toString() : "");
					txtSecao_cr.setText(entidadeBean.getSecaoDescricao());
					txtCodPortador_cr.setText(entidadeBean.getPortador() != null
							? entidadeBean.getPortador().getCodigo().toString() : "");
					txtPortador_cr.setText(entidadeBean.getPortadorDescricao());
					txtCodPlanoContas_cr.setText(entidadeBean.getPlanoConta() != null
							? entidadeBean.getPlanoConta().getCodigo().toString() : "");
					txtPlanoContas_cr.setText(entidadeBean.getPlanoContaDescricao());
					txtCodVendedor_cr.setText(entidadeBean.getVendedor() != null
							? entidadeBean.getVendedor().getCodigo().toString() : "");
					txtVendedor_cr.setText(entidadeBean.getVendedorDescricao());

					btnInsert_cr.setDisable(true);
					btnCancel_cr.setDisable(false);
					btnSave_cr.setDisable(false);

				} else {
					util.showAlert(DadosGlobais.resourceBundle.getString("alertErrorCodigoNull"), "error");
					btnSave_cr.setDisable(true);
					btnCancel_cr.setDisable(false);
				}

			}

			@Override
			protected void failed() {
				stg.close();
				util.tratamentoExcecao(exceptionProperty().getValue().toString(),
						"[ ContasReceberController.loadByID() ]");
				// pBar.setProgress(0);
			}

			@Override
			protected void cancelled() {
				tbView.getItems().clear();
				// pBar.setProgress(0);
				super.cancelled();
			}
		};
		Thread t = new Thread(TarefaRefresh);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(UsuarioController.class, TarefaRefresh,
				DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
		// pBar.setProgress(-1);

		// } else {
		// util.showAlert(DadosGlobais.resourceBundle.getString("alertErrorCodigoNull"),
		// "error");
		// Util.limpar(txtDescricao);
		// txtCodigo.requestFocus();
		// }
	}

	/**
	 * Method to modify the status of the account to receive.
	 * 
	 * @param contasReceber
	 *            List of accounts to receive to be modified.
	 */
	public void actionOperatios(List<Object> contasReceber) {

		Task<String> taskBaixarTitulo = new Task<String>() {

			LogRetorno logRet = new LogRetorno();

			@Override
			protected String call() throws Exception {

				logRet = entidadeDao.operations(contasReceber);

				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				pBar.setProgress(1);
				if (logRet.getStatus().equals(EnumLogRetornoStatus.SUCESSO)) {
					Util.showNotification(DadosGlobais.resourceBundle.getString("notificationSucess"));
					// updateTbView("OPERATIONS");--

					for (Object obj : logRet.getListaObjetos()) {

						for (int i = 0; i < tbView.getItems().size(); i++) {
							if (tbView.getItems().get(i).getCheckDelete()
									.compareTo((((ContasReceber) obj).getCheckDelete())) == 0) {

								tbView.getItems().set(i, (ContasReceber) obj);
								tbView.getItems().get(i).setSelected(false);
								tbView.getSelectionModel().select(i);

							}
						}
					}

					tbView.refresh();

				} else {
					util.showAlert(logRet.getMsg(), "error");
				}

			}

			@Override
			protected void failed() {
				stg.close();
				util.tratamentoExcecao(exceptionProperty().getValue().toString(),
						"[ ConvenioController.actionBaixarTitulo() ]");
				pBar.setProgress(0);
			}

			@Override
			protected void cancelled() {
				pBar.setProgress(0);
				super.cancelled();
			}

		};
		Thread t = new Thread(taskBaixarTitulo);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(ContasReceberController.class, taskBaixarTitulo, "Mensaje", false);
		pBar.setProgress(-1);

	}

	/**
	 * Reset the Fields contained in the Form
	 */
	public void resetForm(boolean frmContaReceber) {

		if (frmContaReceber) {

			Util.limpar(txtCodOperacao_cr, txtOperacao_cr, txtCodCliente_cr, txtCliente_cr, txtNoDocumento_cr,
					txtParcela_cr, txtValorBruto_cr, txtValorDesconto_rc, txtValorPorCiento_cr, txtValorLiquido_cr,
					txtHistorico_cr, txtCodMoeda_cr, txtMoeda_cr, txtCodSecao_cr, txtSecao_cr, txtCodCentroCusto_cr,
					txtCentroCusto_cr, txtCodPortador_cr, txtPortador_cr, txtCodPlanoContas_cr, txtPlanoContas_cr,
					txtCodVendedor_cr, txtVendedor_cr, txtCodTipoDoc_cr, txtTipoDoc_cr);
			Util.setDefaultStyle(txtCodOperacao_cr, txtOperacao_cr, txtCodCliente_cr, txtCliente_cr, txtNoDocumento_cr,
					txtParcela_cr, txtHistorico_cr, txtCodMoeda_cr, txtMoeda_cr, txtCodSecao_cr, txtSecao_cr,
					txtCodCentroCusto_cr, txtCentroCusto_cr, txtCodPortador_cr, txtPortador_cr, txtCodPlanoContas_cr,
					txtPlanoContas_cr, txtCodVendedor_cr, txtVendedor_cr, txtCodTipoDoc_cr, txtTipoDoc_cr,
					txtValorBruto_cr, txtValorDesconto_rc, txtValorLiquido_cr);

			chkAutomatico_cr.setSelected(false);

			dateDataEmissao_cr.setValue(today);
			dateDataVencimento_cr.setValue(today);
			dateDataPrevRecebimento_cr.setValue(today);

			lblCodOperacao_cr.setDisable(false);
			txtCodOperacao_cr.setDisable(false);
			txtOperacao_cr.setDisable(false);
			lblCodCliente_cr.setDisable(false);
			txtCodCliente_cr.setDisable(false);
			txtCliente_cr.setDisable(false);
			lblNoDocumento_cr.setDisable(false);
			txtNoDocumento_cr.setDisable(false);
			lblParcela_cr.setDisable(false);
			txtParcela_cr.setDisable(false);
			lblDataEmissao_cr.setDisable(false);
			dateDataEmissao_cr.setDisable(false);

			entidadeBean = (ContasReceber) util.initializeAttribClass(new ContasReceber());

		} else {

			Util.limpar(txtCodOperacao_crl, txtOperacao_crl, txtCodCliente_crl, txtCliente_crl, txtNoDocumento_crl,
					txtCodSecao_crl, txtSecao_crl, txtCodCentroCusto_crl, txtCentroCusto_crl, txtCodTipoDoc_crl,
					txtTipoDoc_crl, txtCodPortador_crl, txtPortador_crl, txtCodPlanoContas_crl, txtPlanoContas_crl,
					txtCodMoeda_crl, txtMoeda_crl, txtCodDigitador_crl, txtDigitador_crl, txtCodVendedor_crl,
					txtVendedor_crl, txtHistorico_crl, txtValorBruto_crl, txtValorPorCiento_crl, txtValorDesconto_rcl,
					txtValorLiquido_crl);

			Util.setDefaultStyle(txtCodOperacao_crl, txtOperacao_crl, txtCodCliente_crl, txtCliente_crl,
					txtNoDocumento_crl, txtCodSecao_crl, txtSecao_crl, txtCodCentroCusto_crl, txtCentroCusto_crl,
					txtCodTipoDoc_crl, txtTipoDoc_crl, txtCodPortador_crl, txtPortador_crl, txtCodPlanoContas_crl,
					txtPlanoContas_crl, txtCodMoeda_crl, txtMoeda_crl, txtCodDigitador_crl, txtDigitador_crl,
					txtCodVendedor_crl, txtVendedor_crl, txtHistorico_crl, txtValorBruto_crl, txtValorPorCiento_crl,
					txtValorDesconto_rcl, txtValorLiquido_crl);

			chkAutomatico_crl.setSelected(false);
			chkDividirParcelas_crl.setSelected(false);
			chkIncluirParcela_crl.setSelected(false);
			dateDataEmissao_crl.setValue(today);
			dateDataVencimento_crl.setValue(today);
			chkDividirParcelas_crl.setSelected(false);
			spinNoParcelas_crl.getValueFactory().setValue(1);
			spinParcelaInicial_crl.getValueFactory().setValue(1);

			txtCodOperacao_crl.setEditable(true);
			txtOperacao_crl.setEditable(true);
			txtValorBruto_crl.setEditable(true);
			txtValorPorCiento_crl.setEditable(true);
			txtValorDesconto_rcl.setEditable(true);
			txtValorLiquido_crl.setEditable(true);
			chkDividirParcelas_crl.setDisable(false);

			lblValorTotal_crl.setVisible(false);
			lblValorTotal_crl.setText("VALOR TOTAL:");
			lblValorTotal_crl.setStyle("-fx-text-fill: black;");

			entidadeBean = (ContasReceber) util.initializeAttribClass(new ContasReceber());
			txtCodOperacao_crl.requestFocus();
			tbView_crl.getItems().clear();
			listContasReceberLotes.clear();

			btnSave_crl.setDisable(true);
		}

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

			Object type = ContasReceber.class
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
									DadosGlobais.resourceBundle.getString("contasReceberController.lblTitle"), pBar,
									stg)));

			stg.setScene(scene);
			stg.initStyle(StageStyle.TRANSPARENT);
			stg.initModality(Modality.APPLICATION_MODAL);
			stg.show();
		} catch (IOException e) {
			util.tratamentoExcecao(e.getMessage().toString(), "[ ContasReceberController.printExportShow() ]");
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
					if (listaRegistros != null) {
						if (anchorPaneDetalhes_cr.isVisible())
							listaRegistros.add(entidadeBean);
						if (anchorPaneDetalhes_crl.isVisible()) {
							if (!listNewContasReceberLotes.isEmpty())
								listaRegistros.addAll(listNewContasReceberLotes);
						}

					} else {

						listaRegistros = FXCollections.observableArrayList();
						if (anchorPaneDetalhes_cr.isVisible())
							listaRegistros.add(entidadeBean);
						if (anchorPaneDetalhes_crl.isVisible()) {
							if (!listContasReceberLotes.isEmpty())
								listaRegistros.addAll(listNewContasReceberLotes);
						}

					}
				} else if (flagOperacao.equals("UPDATE")) {

					if (tbView.getItems().size() == 0) {
						listaRegistros = FXCollections.observableArrayList();
						listaRegistros.add(entidadeBean);
					} else {
						for (int i = 0; i < tbView.getItems().size(); i++) {
							if (tbView.getItems().get(i).getNoDocto().equals(entidadeBean.getNoDocto())
									&& tbView.getItems().get(i).getNoDocto().equals(entidadeBean.getNoParcela())) {
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
					// tbView.setItems(listaRegistros);
					tbView.getItems().setAll(listaRegistros);

				lblNumLinhas.setText(String.valueOf(tbView.getItems().size()));
			}

			@Override
			protected void failed() {
				stg.close();
				util.tratamentoExcecao(exceptionProperty().getValue().toString(),
						"[ ContasReceberController.updateTbView() ]");
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
		stg = ProgressBarForm.showProgressBar(ContasReceberController.class, TarefaAtualiza,
				DadosGlobais.resourceBundle.getString("infAtualizaTbView"), false);
		pBar.setProgress(-1);

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
	public void updateTableView(ObservableList visibleColumns, TableView<ContasReceber> table) {

		if (!visibleColumns.isEmpty()) {
			// hide all columns
			for (TableColumn<ContasReceber, ?> col : table.getColumns())
				col.setVisible(false);
			// show column and defines its position
			for (int i = 0; i < visibleColumns.size(); i++) {
				table.getColumns().remove(visibleColumns.get(i));
				table.getColumns().add(i, (TableColumn<ContasReceber, ?>) visibleColumns.get(i));
				table.getColumns().get(i).setVisible(true);
			}
		}
	}

	/**
	 * Fill comboBox cboxData_filt and property definitions
	 */
	public void fillCboxData_filt() {

		ObservableList<ComboBoxFilter> listCboxData_filt = FXCollections.observableArrayList();

		for (EnumDataContasReceber item : EnumDataContasReceber.values())
			listCboxData_filt.add(new ComboBoxFilter(item.text, 0, item));

		cboxData_filt.getItems().addAll(listCboxData_filt);
		cboxData_filt.getSelectionModel().selectFirst();

		cboxData_filt.setConverter(new StringConverter<ComboBoxFilter>() {
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
	 * Fill comboBox cboxStatus_filt and property definitions
	 */
	public void fillCboxStatus_filt() {

		ObservableList<ComboBoxFilter> listCboxStatus_filt = FXCollections.observableArrayList();

		for (EnumStatusContasReceber item : EnumStatusContasReceber.values())
			listCboxStatus_filt.add(new ComboBoxFilter(item.text, 0, item));

		cboxStatus_filt.getItems().addAll(listCboxStatus_filt);
		cboxStatus_filt.getSelectionModel().selectFirst();

		cboxStatus_filt.setConverter(new StringConverter<ComboBoxFilter>() {
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
						TableColumn<ContasReceber, ?> tableColumn = tbView.getColumns().get(j);
						tbView.getColumns().remove(j);
						tbView.getColumns().add(userDefinedColumns.get(i).getPosition(), tableColumn);
						tableColumn.setPrefWidth(userDefinedColumns.get(i).getWidth());
						tableColumn.setVisible(true);
					}
				}
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

	/**
	 * Financial operation between the gross, discount and liquid values
	 * 
	 * @param valorBruto
	 *            TextField containing the gross value
	 * @param valorDesconto
	 *            TextField containing the discount value
	 * @param valorLiquido
	 *            TextField containing the liquid value
	 */
	public void financialOperation(TextField txtValorBruto, TextField txtValorPorCiento, TextField txtValorDesconto,
			TextField txtValorLiquido) {

		txtValorPorCiento.textProperty().addListener((observable, oldValue, newValue) -> {

			if (newValue.isEmpty()) {
				txtValorDesconto.clear();
				return;
			}

			if (Util.decimalBRtoBigDecimal(2, txtValorPorCiento.getText()).compareTo(new BigDecimal(100)) >= 0
					|| Util.decimalBRtoBigDecimal(2, txtValorBruto.getText()).compareTo(BigDecimal.ZERO) == 0) {
				Util.setStyleError(true, txtValorPorCiento);
				txtValorPorCiento.setText(oldValue);
			}

			if (Util.decimalBRtoBigDecimal(2, txtValorPorCiento.getText()).compareTo(BigDecimal.ZERO) != 0) {
				BigDecimal percent = BigDecimal.ZERO;
				percent = Util.decimalBRtoBigDecimal(2, txtValorBruto.getText())
						.multiply(Util.decimalBRtoBigDecimal(2, txtValorPorCiento.getText()));
				if (!txtValorDesconto.isFocused())
					txtValorDesconto.setText(percent.divide(new BigDecimal(100)).toString());
			}

		});

		txtValorDesconto.textProperty().addListener((observable, oldValue, newValue) -> {

			if (newValue.isEmpty()) {
				txtValorLiquido.setText(txtValorBruto.getText());
				txtValorPorCiento.clear();
				return;
			}

			if (Util.decimalBRtoBigDecimal(2, txtValorDesconto.getText())
					.compareTo(Util.decimalBRtoBigDecimal(2, txtValorBruto.getText())) >= 0) {
				Util.setStyleError(true, txtValorDesconto);
				txtValorDesconto.setText(oldValue);
			} else {
				txtValorLiquido.setText(Util.decimalBRtoBigDecimal(2, txtValorBruto.getText())
						.subtract(Util.decimalBRtoBigDecimal(2, txtValorDesconto.getText())).toString());
				BigDecimal percent = BigDecimal.ZERO;
				percent = Util.decimalBRtoBigDecimal(2, txtValorDesconto.getText()).multiply(new BigDecimal(100));
				if (!txtValorPorCiento.isFocused())
					txtValorPorCiento.setText(percent.divide(Util.decimalBRtoBigDecimal(2, txtValorBruto.getText()), 2,
							BigDecimal.ROUND_HALF_EVEN).toString());
			}
		});

		txtValorBruto.textProperty().addListener((ov, oldVal, newVal) -> {
			if (!newVal.isEmpty()) {

				if (Util.decimalBRtoBigDecimal(2, newVal).compareTo(new BigDecimal("9999999999999")) == 1) {
					txtValorBruto.setText(oldVal);
					Util.setStyleError(true, txtValorBruto);
				}

				if (Util.decimalBRtoBigDecimal(2, txtValorBruto.getText())
						.compareTo(Util.decimalBRtoBigDecimal(2, txtValorDesconto.getText())) >= 0
						&& Util.decimalBRtoBigDecimal(2, txtValorBruto.getText()).compareTo(BigDecimal.ZERO) == 1)
					txtValorLiquido.setText(Util.decimalBRtoBigDecimal(2, txtValorBruto.getText())
							.subtract(Util.decimalBRtoBigDecimal(2, txtValorDesconto.getText())).toString());
				else {
					Util.setStyleError(true, txtValorBruto);
					txtValorLiquido.clear();
				}
			} else {
				txtValorDesconto.clear();
				txtValorLiquido.clear();
			}
		});

		txtValorBruto.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
				if (txtValorBruto.getText().isEmpty()) {
					txtValorDesconto.clear();
					txtValorPorCiento.clear();
					txtValorLiquido.clear();
				} else if (Util.decimalBRtoBigDecimal(2, txtValorBruto.getText())
						.compareTo(Util.decimalBRtoBigDecimal(2, txtValorDesconto.getText())) < 0) {
					txtValorLiquido.clear();
					Util.setStyleError(true, txtValorBruto);
				}
			}
		});

		txtValorBruto.setOnKeyPressed((KeyEvent key) -> {
			if (key.getCode().equals(KeyCode.ENTER))
				txtValorPorCiento.requestFocus();
		});

		txtValorPorCiento.setOnKeyPressed((KeyEvent key) -> {
			if (key.getCode().equals(KeyCode.ENTER))
				txtValorDesconto.requestFocus();
		});

		txtValorDesconto.setOnKeyPressed((KeyEvent key) -> {
			if (key.getCode().equals(KeyCode.ENTER))
				txtHistorico_cr.requestFocus();
		});

	}

	/**
	 * Operations with dates
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void datesOperations() {

		// Set values to DatePickers
		dateInicial_filt.setValue(today);
		dateFinal_filt.setValue(today);
		dateJuros_filt.setValue(today);
		dateDataEmissao_cr.setValue(today);
		dateDataVencimento_cr.setValue(today);
		dateDataEmissao_crl.setValue(today);
		dateDataVencimento_crl.setValue(today);
		dateExpiration.setValue(today);

		new ComboBoxData(cboxPeriodo_filt, dateInicial_filt, dateFinal_filt);

		// Date format for DatePicker
		StringConverter converter = new StringConverter<LocalDate>() {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

			@Override
			public String toString(LocalDate date) {
				if (date != null) {
					return dateFormatter.format(date);
				} else {
					return "";
				}
			}

			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, dateFormatter);
				} else {
					return today;
				}
			}
		};
		dateJuros_filt.setConverter(converter);
		dateDataEmissao_cr.setConverter(converter);
		dateDataVencimento_cr.setConverter(converter);
		dateDataPrevRecebimento_cr.setConverter(converter);
		// dateExpiration.setConverter(converter);
		dateDataEmissao_crl.setConverter(converter);
		dateDataVencimento_crl.setConverter(converter);

		Callback<DatePicker, DateCell> dateExpitationCellFactory = new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);

						if (item.isBefore(today))
							setDisable(true);
					}
				};
			}
		};
		dateExpiration.setDayCellFactory(dateExpitationCellFactory);

		DatePickerSkin dateSkin = new DatePickerSkin(dateExpiration);
		Node popupContent = dateSkin.getPopupContent();
		popupContent.setStyle("-fx-effect: null");
		paneDatePicker.setCenter(popupContent);

		Callback<DatePicker, DateCell> dayCFContaReceber = new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);

						if (item.isBefore(dateDataEmissao_cr.getValue()))
							setDisable(true);
					}
				};
			}
		};
		dateDataVencimento_cr.setDayCellFactory(dayCFContaReceber);
		dateDataPrevRecebimento_cr.setDayCellFactory(dayCFContaReceber);

		Callback<DatePicker, DateCell> dayCFContaReceberLotes = new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);

						if (item.isBefore(dateDataEmissao_crl.getValue()))
							setDisable(true);
					}
				};
			}
		};
		dateDataVencimento_crl.setDayCellFactory(dayCFContaReceberLotes);

		dateDataEmissao_cr.valueProperty().addListener(new ChangeListener<LocalDate>() {
			@Override
			public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue,
					LocalDate newValue) {

				if (newValue.isAfter(dateDataVencimento_cr.getValue()))
					dateDataVencimento_cr.setValue(newValue);

				if (newValue.isAfter(dateDataPrevRecebimento_cr.getValue()))
					dateDataPrevRecebimento_cr.setValue(newValue);
			}
		});

		dateDataVencimento_cr.valueProperty().addListener(new ChangeListener<LocalDate>() {
			@Override
			public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue,
					LocalDate newValue) {
				dateDataPrevRecebimento_cr.setValue(newValue);

			}
		});

		dateDataEmissao_crl.valueProperty().addListener(new ChangeListener<LocalDate>() {
			@Override
			public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue,
					LocalDate newValue) {
				if (newValue.isAfter(dateDataVencimento_crl.getValue()))
					dateDataVencimento_crl.setValue(newValue);

			}
		});

	}

	/**
	 * Method to format date in column
	 * 
	 * @param tbCol
	 *            Column to be formatted
	 */
	public void formatterColumnDate(TableColumn<ContasReceber, LocalDateTime> tbCol) {
		tbCol.setCellFactory((TableColumn<ContasReceber, LocalDateTime> column) -> {
			return new TableCell<ContasReceber, LocalDateTime>() {
				@Override
				protected void updateItem(LocalDateTime item, boolean empty) {
					super.updateItem(item, empty);
					if (item == null || empty) {
						setText(null);
					} else {
						setText(Util.formataDataString(item));
					}
				}
			};
		});
	}

	@SuppressWarnings("unchecked")
	public void formatterColumnsBigDecimal(TableColumn<ContasReceber, BigDecimal>... tbCols) {

		for (TableColumn<ContasReceber, BigDecimal> column : tbCols) {
			column.setCellFactory((TableColumn<ContasReceber, BigDecimal> col) -> {
				return new TableCell<ContasReceber, BigDecimal>() {
					@Override
					protected void updateItem(BigDecimal item, boolean empty) {
						super.updateItem(item, empty);
						if (item == null || empty) {
							setText(null);
						} else {
							setText(item.setScale(2, RoundingMode.HALF_EVEN).toString().replace(".", ","));
						}
					}
				};
			});
		}
	}

	public ContasReceberController() {
		// TODO Auto-generated constructor stub
	}

	public ContasReceberController(NivelAcesso nivAcesso, TabPane o_tabPrincipal) {

		this.nivAcessoPermissao = nivAcesso;
		this.tabPrincipal = o_tabPrincipal;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		anchorPaneListagem.setVisible(true);
		anchorPaneDetalhes_cr.setVisible(false);
		anchorPaneDetalhes_crl.setVisible(false);
		anchorPaneAlteraData.setVisible(false);

		// CHAMADA A FUNÇÃO PARA SETAR O FOCO INICIAL DO FORMULÁRIO
		Util.setFocus(cboxData_filt);

		// Operations with dates
		datesOperations();

		fillCboxData_filt();
		fillCboxStatus_filt();

		Util.maxCharacters(2, txtParcela_cr);
		Util.maxCharacters(255, txtHistorico_cr);

		Util.decimalBR(2, txtValorBruto_cr, txtValorPorCiento_cr, txtValorDesconto_rc, txtValorLiquido_cr,
				txtValorBruto_crl, txtValorPorCiento_crl, txtValorDesconto_rcl, txtValorLiquido_crl);

		// Financial Operations
		financialOperation(txtValorBruto_cr, txtValorPorCiento_cr, txtValorDesconto_rc, txtValorLiquido_cr);
		financialOperation(txtValorBruto_crl, txtValorPorCiento_crl, txtValorDesconto_rcl, txtValorLiquido_crl);

		// CHAMADA A FUNÇÃO QUE CONFIGURA O TABLEVIEW DE ACORDO COM O ARQUIVO
		// XML GERADO PARA CADA USUARIO DENTRO DA PASTA C:/EptusAM/System.Grd
		configTableView();

		Util.customSearchTextField("right", null, txtCodOperacao_cr, txtCodCliente_filt, txtCodPortador_filt,
				txtCodSecao_filt, txtCodVendedor_filt, txtCodPlanoPagamento_filt, txtCodPlanoContas_filt);
		Util.customSearchTextField("right", null, txtCodCliente_cr, txtCodMoeda_cr, txtCodSecao_cr,
				txtCodCentroCusto_cr, txtCodPortador_cr, txtCodPlanoContas_cr, txtCodVendedor_cr, txtCodTipoDoc_cr);
		Util.customSearchTextField("right", null, txtCodOperacao_crl, txtCodCliente_crl, txtCodSecao_crl,
				txtCodCentroCusto_crl, txtCodTipoDoc_crl, txtCodPortador_crl, txtCodPlanoContas_crl, txtCodMoeda_crl,
				txtCodDigitador_crl, txtCodVendedor_crl);

		tabPaneFiltros.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {

			if (tabPaneFiltros.getSelectionModel().getSelectedItem().equals(tabMaisFiltros)) {
				anchorPaneFilter.setPrefHeight(135);
			}

			if (tabPaneFiltros.getSelectionModel().getSelectedItem().equals(tabContasReceber)) {
				anchorPaneFilter.setPrefHeight(175);
				splitPaneFilter.setDividerPositions(1);
			}
		});

		txtPorcentagem_filt.setText(DadosGlobais.empresaLogada.getConfig().getFinTaxajuros().toString());

		// CHAMADA A FUNÇÃO QUE SETA O ESTILO DEFAULT AOS CAMPOS TEXTFIELDS
		// FILTERS
		Util.setKeyPressDefaultStyles(txtCodCliente_filt, txtPorcentagem_filt, txtCodPortador_filt, txtCodSecao_filt,
				txtCodVendedor_filt, txtCodPlanoPagamento_filt, txtCodPlanoContas_filt);
		Util.setStyleOnFocus(txtCodCliente_filt, txtPorcentagem_filt, txtCodPortador_filt, txtCodSecao_filt,
				txtCodVendedor_filt, txtCodPlanoPagamento_filt, txtCodPlanoContas_filt);
		Util.onlyNumbers(txtCodCliente_filt, txtCodPortador_filt, txtCodSecao_filt, txtCodVendedor_filt,
				txtCodPlanoPagamento_filt, txtCodPlanoContas_filt);
		Util.maxCharacters(8, txtCodCliente_filt, txtCodPortador_filt, txtCodSecao_filt, txtCodVendedor_filt,
				txtCodPlanoPagamento_filt, txtCodPlanoContas_filt);
		// CONTAS A RECEBER
		Util.setKeyPressDefaultStyles(txtCodOperacao_cr, txtCodCliente_cr, txtNoDocumento_cr, txtCodMoeda_cr,
				txtCodSecao_cr, txtCodCentroCusto_cr, txtCodPortador_cr, txtCodPlanoContas_cr, txtCodVendedor_cr,
				txtCodTipoDoc_cr, txtValorBruto_cr, txtValorPorCiento_cr, txtValorDesconto_rc, txtValorLiquido_cr);
		Util.setStyleOnFocus(txtCodOperacao_cr, txtCodCliente_cr, txtNoDocumento_cr, txtCodMoeda_cr, txtCodSecao_cr,
				txtCodCentroCusto_cr, txtCodPortador_cr, txtCodPlanoContas_cr, txtCodVendedor_cr, txtCodTipoDoc_cr,
				txtParcela_cr, txtHistorico_cr, txtValorBruto_cr, txtValorPorCiento_cr, txtValorDesconto_rc,
				txtValorLiquido_cr);
		Util.onlyNumbers(txtCodOperacao_cr, txtCodCliente_cr, txtNoDocumento_cr, txtParcela_cr, txtCodMoeda_cr,
				txtCodSecao_cr, txtCodCentroCusto_cr, txtCodPortador_cr, txtCodPlanoContas_cr, txtCodVendedor_cr,
				txtCodTipoDoc_cr);
		// Util.onlyAlphanumeric();
		Util.maxCharacters(8, txtCodOperacao_cr, txtCodCliente_cr, txtNoDocumento_cr, txtCodMoeda_cr, txtCodSecao_cr,
				txtCodCentroCusto_cr, txtCodPortador_cr, txtCodPlanoContas_cr, txtCodVendedor_cr, txtCodTipoDoc_cr);
		Util.whriteUppercase(txtHistorico_cr);
		// CONTAS RECEBER LOTES
		Util.setKeyPressDefaultStyles(txtCodOperacao_crl, txtCodCliente_crl, txtNoDocumento_crl, txtCodSecao_crl,
				txtCodCentroCusto_crl, txtCodTipoDoc_crl, txtCodPortador_crl, txtCodPlanoContas_crl, txtCodMoeda_crl,
				txtCodDigitador_crl, txtCodVendedor_crl, txtValorBruto_crl, txtValorPorCiento_crl, txtValorDesconto_rcl,
				txtValorLiquido_crl, txtHistorico_crl);
		Util.setStyleOnFocus(txtCodOperacao_crl, txtCodCliente_crl, txtNoDocumento_crl, txtCodSecao_crl,
				txtCodCentroCusto_crl, txtCodTipoDoc_crl, txtCodPortador_crl, txtCodPlanoContas_crl, txtCodMoeda_crl,
				txtCodDigitador_crl, txtCodVendedor_crl, txtHistorico_crl, txtValorBruto_crl, txtValorPorCiento_crl,
				txtValorDesconto_rcl, txtValorLiquido_crl);
		Util.onlyNumbers(txtCodOperacao_crl, txtCodCliente_crl, txtNoDocumento_crl, txtCodSecao_crl,
				txtCodCentroCusto_crl, txtCodTipoDoc_crl, txtCodPortador_crl, txtCodPlanoContas_crl, txtCodMoeda_crl,
				txtCodDigitador_crl, txtCodVendedor_crl);
		// Util.onlyAlphanumeric();
		Util.maxCharacters(8, txtCodOperacao_crl, txtCodCliente_crl, txtNoDocumento_crl, txtCodSecao_crl,
				txtCodCentroCusto_crl, txtCodTipoDoc_crl, txtCodPortador_crl, txtCodPlanoContas_crl, txtCodMoeda_crl,
				txtCodDigitador_crl, txtCodVendedor_crl);
		Util.whriteUppercase(txtHistorico_crl);

		txtParcela_cr.focusedProperty().addListener((ov, olvVal, newVal) -> {

			if (!newVal && !txtParcela_cr.getText().isEmpty() && !txtCliente_cr.getText().isEmpty()
					&& !txtNoDocumento_cr.getText().isEmpty() && txtHistorico_cr.getText().isEmpty()
					&& !chkAutomatico_cr.isSelected())

				txtHistorico_cr.setText("DOC. NR " + txtNoDocumento_cr.getText() + "/" + txtParcela_cr.getText() + " - "
						+ txtCliente_cr.getText());
		});

		chkAutomatico_cr.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if (newValue) {
					txtNoDocumento_cr.setDisable(true);
					txtNoDocumento_cr.setText("000");
				} else {
					txtNoDocumento_cr.setDisable(false);
					txtNoDocumento_cr.clear();
				}
			}
		});

		// CHAMADA A FUNÇÃO PARA POPULAR OS COMBOBOX CONTINDOS NO FORMULARIO
		// -------------------

		// Spinner declaration
		spinNoParcelas_crl.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 30, 1));
		spinParcelaInicial_crl.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 30, 1));

		// CONFIGURA AS COLUNAS EXISTENTES NO TABLEVIEW, ASSOCIA A COLUNA DO
		// FXML AO ATRIBUTO EXISTENTE NA CLASSE DO MODEL
		tbColCliente.setCellValueFactory(new PropertyValueFactory<ContasReceber, Integer>("clienteId"));
		tbColNome.setCellValueFactory(new PropertyValueFactory<ContasReceber, String>("clienteNome"));
		tbColAtivoInat.setCellValueFactory(new PropertyValueFactory<ContasReceber, Integer>("flagAtivo"));
		tbColTitulo.setCellValueFactory(new PropertyValueFactory<ContasReceber, Integer>("noDocto"));
		tbColParcelas.setCellValueFactory(new PropertyValueFactory<ContasReceber, Integer>("noParcela"));
		tbColVencimento.setCellValueFactory(new PropertyValueFactory<ContasReceber, LocalDateTime>("dataVencimento"));
		tbColValor.setCellValueFactory(new PropertyValueFactory<ContasReceber, BigDecimal>("valorBrutoDocto"));
		tbColRecebido.setCellValueFactory(new PropertyValueFactory<ContasReceber, BigDecimal>("valorPagto"));
		tbColStatus.setCellValueFactory(new PropertyValueFactory<ContasReceber, Integer>("flagTipoBaixa"));
		tbColAtraso.setCellValueFactory(new PropertyValueFactory<ContasReceber, Integer>("atraso"));
		tbColJuro.setCellValueFactory(new PropertyValueFactory<ContasReceber, BigDecimal>("juros"));
		tbColSaldo.setCellValueFactory(new PropertyValueFactory<ContasReceber, BigDecimal>("saldo"));
		tbColValorTotal.setCellValueFactory(new PropertyValueFactory<ContasReceber, BigDecimal>("valorTotal"));

		// valor, recibido, juro, saldo, valor total
		formatterColumnsBigDecimal(tbColValor, tbColRecebido, tbColJuro, tbColSaldo, tbColValorTotal);

		tbColSelected.setCellValueFactory(new PropertyValueFactory<ContasReceber, Boolean>("selected"));
		tbColSelected
				.setCellFactory(new Callback<TableColumn<ContasReceber, Boolean>, TableCell<ContasReceber, Boolean>>() {
					@Override
					public TableCell<ContasReceber, Boolean> call(TableColumn<ContasReceber, Boolean> param) {
						// TODO Auto-generated method stub
						CheckBoxTableColumn c = new CheckBoxTableColumn();
						return c;
					}
				});

		checkAll = new CheckBox();
		checkAll.setStyle("-fx-alignment: center");
		checkAll.setOnAction(e -> {
			if (checkAll.isSelected()) {
				if (!tbView.getItems().isEmpty()) {
					for (ContasReceber c : tbView.getItems())
						c.setSelected(true);
					tbView.refresh();
				}
			} else {
				if (!tbView.getItems().isEmpty()) {
					for (ContasReceber c : tbView.getItems())
						c.setSelected(false);
					tbView.refresh();
				}
			}
		});

		tbColSelected.setGraphic(checkAll);
		tbColSelected.setStyle("-fx-alignment: center");

		checkAll.disableProperty().bind(Bindings.isEmpty(tbView.getItems()));
		tbView.setEditable(true);

		// Formatter column date
		formatterColumnDate(tbColVencimento);
		formatterColumnDate(tbColVencimento_crl);

		tbColStatus.setCellFactory(col -> new TableCell<ContasReceber, Integer>() {
			@Override
			public void updateItem(Integer flag, boolean empty) {
				super.updateItem(flag, empty);
				if (empty) {
					setText("");
				} else {
					if (flag == null)
						setText(null);
					else {
						for (EnumStatusContasReceber e : EnumStatusContasReceber.values()) {
							if (flag.equals(e.index))
								setText(e.text);
						}
					}
				}
			}
		});

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

			switch (event.getCode()) {
			case ENTER:

				// &&
				// tbView.getSelectionModel().getSelectedItem().getFlagAtivo().equals(1))
				// {
				// tbView.getSelectionModel().getSelectedItem().setSelected(false);
				// tbView.refresh();

				// loadByID(false,
				// tbView.getSelectionModel().getSelectedItem().getCodigo(),
				// false);
				// Util.openFormCadastro(anchorPaneListagem,
				// anchorPaneDetalhes_cr, 0);
				break;

			case ESCAPE:
				// txtFilterColumn.requestFocus();
				break;

			case SPACE:

				if (tbView.getSelectionModel().getSelectedIndex() >= 0) {
					tbView.getSelectionModel().getSelectedItem()
							.setSelected(!tbView.getSelectionModel().getSelectedItem().isSelected());
					tbView.refresh();

				}
				break;

			case UP:
			case DOWN:
				tbView.requestFocus();
				break;

			default:
				break;

			}

		});

		// PROPIEDADES DAS LINAS DO TABLEVIEW, ADICIONA EVENTOS AO MANUSEAR AS
		// LINHASD DO TABLEVIEW
		tbView.setRowFactory(tb -> {
			// EXIBE O EFEITO DE OPACIDADE NAS LINHAS CASO O ITEM DO TABLEVIEW
			// TENHA O FLAGATIVO = 0
			TableRow<ContasReceber> row = new TableRow<ContasReceber>() {
				@Override
				public void updateItem(ContasReceber objeto, boolean empty) {
					super.updateItem(objeto, empty);
					if (objeto == null) {
						setStyle("");
					} else {
						
						setStyle("-fx-text-background-color: BLACK;");

						// if (objeto.getFlagAtivo().equals(0)) {
						// setStyle("-fx-text-background-color: #BDBDBD;");
						// }

						// BAIXADO - AZUL:#1565C0
						// RENEGOCIADO - AMARILLO:#FF8F00
						// CHECKBOX SELECTED AMARILLO - #FFF8E1

						if (objeto.isSelected()) {

							setStyle("-fx-background-color: -fx-table-cell-border-color, #FFF8E1;"
									+ "-fx-text-background-color: BLACK;");

							if (objeto.getFlagTipoBaixa().equals(EnumStatusContasReceber.BAIXADO.index))
								setStyle("-fx-background-color: -fx-table-cell-border-color, #FFF8E1; "
										+ "-fx-text-background-color: #1565C0; ");

							if (objeto.getFlagTipoBaixa().equals(EnumStatusContasReceber.RENEGOCIADO.index))
								setStyle("-fx-background-color: -fx-table-cell-border-color, #FFF8E1; "
										+ "-fx-text-background-color: #FF8F00; ");

							if (objeto.getAtraso() != null && objeto.getAtraso() > 0
									&& (objeto.getFlagTipoBaixa().equals(EnumStatusContasReceber.STATUS_ABERTO.index)
											|| objeto.getFlagTipoBaixa()
													.equals(EnumStatusContasReceber.BAIXADO_PARCIAL.index)))
								setStyle("-fx-background-color: -fx-table-cell-border-color, #FFF8E1; "
										+ "-fx-text-background-color: red; ");

						} else {

							if (objeto.getFlagTipoBaixa().equals(EnumStatusContasReceber.BAIXADO.index))
								setStyle("-fx-text-background-color: #1565C0; ");

							if (objeto.getFlagTipoBaixa().equals(EnumStatusContasReceber.RENEGOCIADO.index))
								setStyle("-fx-text-background-color: #FF8F00; ");

							if (objeto.getAtraso() != null && objeto.getAtraso() > 0
									&& (objeto.getFlagTipoBaixa().equals(EnumStatusContasReceber.STATUS_ABERTO.index)
											|| objeto.getFlagTipoBaixa()
													.equals(EnumStatusContasReceber.BAIXADO_PARCIAL.index)))
								setStyle("-fx-text-background-color: red; ");
						}
					}
				}
			};

			// EVENTOS ON CLICK NAS LINHAS DO TABLEVIEW
			row.setOnMouseClicked(tr -> {
				// CASO SEJA DADO UM DUPLO CLIQUE NA LINHA DO TABLE VIEW
				if (tr.getClickCount() >= 2 && (!row.isEmpty()) && tr.getButton().equals(MouseButton.PRIMARY)) {
					tbView.getItems().get(row.getIndex())
							.setSelected(!tbView.getItems().get(row.getIndex()).isSelected());
					tbView.refresh();
				}

				// CASO SEJA DADO UM CLIQUE COM O BOTAO SECUNDARIO DO MOUSE O
				// BOTAO DIREITO EXIBE UM MENU DE AÇOES
				if (tr.getButton().equals(MouseButton.SECONDARY) && !row.isEmpty()) {

					// ESTANCIA UM NOVO CONTEXTMENU
					contextMenu = new ContextMenu();

					// MENUITEM - RASTRAR NOTA FISCAL
					MenuItem itemRastrearNotaFiscal = new MenuItem(
							DadosGlobais.resourceBundle.getString("actionRastrearNotaFiscal"));
					itemRastrearNotaFiscal.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {

						}
					});

					// MENUITEM - RASTREAR RENEGOCIACAO
					MenuItem itemRastrearRenegociacao = new MenuItem(
							DadosGlobais.resourceBundle.getString("actionRastrearRenegociacao"));
					itemRastrearRenegociacao.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent e) {

						}
					});

					// MENUITEM - RASTREAR RECEBIMENTOS
					MenuItem itemRastrearRecebimento = new MenuItem(
							DadosGlobais.resourceBundle.getString("actionRastrearRecebimentos"));
					itemRastrearRecebimento.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent e) {

						}
					});

					// MENU - OPERACOES
					Menu operacoes = new Menu(DadosGlobais.resourceBundle.getString("actionOperacoes"));

					// MENUITEM - EDITAR
					MenuItem itemEditar = new MenuItem(DadosGlobais.resourceBundle.getString("actionAtualizar"));
					itemEditar.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent e) {
							if (nivAcessoPermissao.getFlagUpdate().equals(1)) {

								for (ContasReceber cr : tbView.getItems()) {

									if (cr.isSelected()) {

										if (cr.getFlagTipoBaixa().equals(EnumStatusContasReceber.STATUS_ABERTO.index)) {

											loadByID(cr.getNoDocto(), cr.getNoParcela(), cr.getCliente());
											Util.openFormCadastro(anchorPaneListagem, anchorPaneDetalhes_cr, 0);
											flagInsertUpdate = false;
											break;
										} else {
											util.showAlert("O titulo no puede ser modificado, verifique la selecion",
													"warning");
											break;
										}
									}
								}

								// loadByID(tbView.getSelectionModel().getSelectedItem().getNoDocto(),
								// tbView.getSelectionModel().getSelectedItem().getNoParcela(),
								// tbView.getSelectionModel().getSelectedItem().getCliente());
								// Util.openFormCadastro(anchorPaneListagem,
								// anchorPaneDetalhes_cr, 0);
								// flagInsertUpdate = false;

							} else {
								util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
							}
						}
					});

					// MENUITEM - EXCLUIR
					MenuItem itemExcluir = new MenuItem(DadosGlobais.resourceBundle.getString("actionExcluir"));
					itemExcluir.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent e) {

						}
					});

					// MENUITEM - BAIXAR TITULO
					MenuItem itemBaixarTitulo = new MenuItem(
							DadosGlobais.resourceBundle.getString("actionBaixarTitulo"));
					itemBaixarTitulo.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent e) {
							listCROperations.clear();
							boolean flag = true;
							for (int i = 0; i < tbView.getItems().size(); i++) {
								if (tbView.getItems().get(i).isSelected()) {
									if (!tbView.getItems().get(i).getFlagTipoBaixa()
											.equals(EnumStatusContasReceber.STATUS_ABERTO.index)
											&& !tbView.getItems().get(i).getFlagTipoBaixa()
													.equals(EnumStatusContasReceber.BAIXADO_PARCIAL.index)) {
										flag = false;
										break;
									} else {
										listCROperations.add(tbView.getItems().get(i).clone());
									}
								}
							}

							if (!flag)
								util.showAlert("Existen titulos que no pueden ser baixados, verifique la selecion",
										"warning");
							else {

								for (Object cr : listCROperations)
									((ContasReceber) cr).setFlagTipoBaixa(EnumStatusContasReceber.BAIXADO.index);

								actionOperatios(listCROperations);
							}
						}
					});

					// MENUITEM - RENEGOCIAR TITULO
					MenuItem itemRenegociarTitulo = new MenuItem(
							DadosGlobais.resourceBundle.getString("actionRenegociarTitulo"));
					itemRenegociarTitulo.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent e) {
							listCROperations.clear();
							boolean flag = true;
							for (int i = 0; i < tbView.getItems().size(); i++) {
								if (tbView.getItems().get(i).isSelected()) {
									if (!tbView.getItems().get(i).getFlagTipoBaixa()
											.equals(EnumStatusContasReceber.STATUS_ABERTO.index)
											&& !tbView.getItems().get(i).getFlagTipoBaixa()
													.equals(EnumStatusContasReceber.BAIXADO_PARCIAL.index)) {
										flag = false;
										break;
									} else {
										listCROperations.add(tbView.getItems().get(i).clone());
									}
								}
							}

							if (!flag)
								util.showAlert("Existen titulos que no pueden ser Renegociados, verifique la selecion",
										"warning");
							else {

								for (Object cr : listCROperations)
									((ContasReceber) cr).setFlagTipoBaixa(EnumStatusContasReceber.RENEGOCIADO.index);

								actionOperatios(listCROperations);
							}

						}
					});

					// MENUITEM - CANCELAR BAIXA
					MenuItem itemCancelarBaixa = new MenuItem(
							DadosGlobais.resourceBundle.getString("actionCancelarBaixa"));
					itemCancelarBaixa.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent e) {

							listCROperations.clear();
							boolean flag = true;
							for (int i = 0; i < tbView.getItems().size(); i++) {
								if (tbView.getItems().get(i).isSelected()) {
									if (!tbView.getItems().get(i).getFlagTipoBaixa()
											.equals(EnumStatusContasReceber.BAIXADO.index)
											&& !tbView.getItems().get(i).getFlagTipoBaixa()
													.equals(EnumStatusContasReceber.BAIXADO_PARCIAL.index)) {
										flag = false;
										break;
									} else {
										listCROperations.add(tbView.getItems().get(i).clone());
									}
								}
							}

							if (!flag)
								util.showAlert("Existen titulos que no pueden ser Cancelados, verifique la selecion",
										"warning");
							else {

								for (Object cr : listCROperations)
									((ContasReceber) cr).setFlagTipoBaixa(EnumStatusContasReceber.STATUS_ABERTO.index);

								actionOperatios(listCROperations);
							}

						}
					});

					// MENUITEM - ALTERAR DATA VENCIMENTO
					MenuItem itemAlterarDataVencimeto = new MenuItem(
							DadosGlobais.resourceBundle.getString("actionAlterarDataVencimeto"));
					itemAlterarDataVencimeto.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent e) {

							listCROperations.clear();
							boolean flag = true;
							for (int i = 0; i < tbView.getItems().size(); i++) {
								if (tbView.getItems().get(i).isSelected()) {
									if (!tbView.getItems().get(i).getFlagTipoBaixa()
											.equals(EnumStatusContasReceber.STATUS_ABERTO.index)) {
										flag = false;
										break;
									} else {
										listCROperations.add(tbView.getItems().get(i).clone());
									}
								}
							}

							if (!flag)
								util.showAlert(
										"Existen titulos que no pueden ser alterado la fecha de Vencimiento, verifique la selecion",
										"warning");
							else {
								dateExpiration.setValue(today);
								Util.openFormCadastro(anchorPaneListagem, anchorPaneAlteraData, 0);
							}

						}
					});

					operacoes.getItems().addAll(itemEditar, itemExcluir, itemBaixarTitulo, itemRenegociarTitulo,
							itemCancelarBaixa, itemAlterarDataVencimeto);

					contextMenu.getItems().addAll(itemRastrearNotaFiscal, itemRastrearRenegociacao,
							itemRastrearRecebimento, operacoes);

					boolean selectedRow = false;
					int countSelectedRow = 0;
					for (ContasReceber c : tbView.getItems()) {
						if (c.isSelected()) {
							selectedRow = true;
							countSelectedRow++;

							if (countSelectedRow > 1)
								break;
						}
					}
					if (!selectedRow)
						row.addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, Event::consume);
					else {

						if (countSelectedRow > 1) {
							itemEditar.setDisable(true);
							itemExcluir.setDisable(true);
						}

						row.setContextMenu(contextMenu);
					}

				}
			});

			return row;
		});

		tbView_crl.setEditable(true);
		tbColVencimento_crl.setEditable(true);
		tbColValor_crl.setEditable(true);

		tbColParcelas_crl.setCellValueFactory(new PropertyValueFactory<ContasReceber, Integer>("noParcela"));
		tbColHistorico_crl.setCellValueFactory(new PropertyValueFactory<ContasReceber, String>("historico"));
		tbColVencimento_crl
				.setCellValueFactory(new PropertyValueFactory<ContasReceber, LocalDateTime>("dataVencimento"));
		Callback<TableColumn<ContasReceber, LocalDateTime>, TableCell<ContasReceber, LocalDateTime>> dateCellFactory = (
				TableColumn<ContasReceber, LocalDateTime> param) -> new DateEditingCell();
		tbColVencimento_crl.setCellFactory(dateCellFactory);
		tbColVencimento_crl.setOnEditCommit((TableColumn.CellEditEvent<ContasReceber, LocalDateTime> t) -> {
			((ContasReceber) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setDataVencimento(t.getNewValue());
		});

		tbColValor_crl.setCellValueFactory(new PropertyValueFactory<ContasReceber, BigDecimal>("valorLiquido"));
		Callback<TableColumn<ContasReceber, BigDecimal>, TableCell<ContasReceber, BigDecimal>> cellFactory = (
				TableColumn<ContasReceber, BigDecimal> param) -> new EditingCell();
		tbColValor_crl.setCellFactory(cellFactory);
		tbColValor_crl.setOnEditCommit(new EventHandler<CellEditEvent<ContasReceber, BigDecimal>>() {
			@Override
			public void handle(CellEditEvent<ContasReceber, BigDecimal> t) {

				if (!chkDividirParcelas_crl.isSelected()) {
					t.getTableView().getItems().get(t.getTablePosition().getRow()).setValorLiquido(t.getNewValue());

				} else {

					t.getTableView().getItems().get(t.getTablePosition().getRow()).setValorLiquido(t.getNewValue());

					tmp = new BigDecimal(0);
					for (int i = 0; i <= t.getTablePosition().getRow(); i++)
						tmp = tmp.add(
								tbView_crl.getItems().get(i).getValorLiquido().setScale(2, BigDecimal.ROUND_HALF_EVEN));

					if (tmp.compareTo(Util.decimalBRtoBigDecimal(2, txtValorLiquido_crl.getText())) >= 0) {
						util.showAlert("Valor das parcelas supera o valor total", "warning");

						for (int i = t.getTablePosition().getRow() + 1; i < tbView_crl.getItems().size(); i++) {
							tbView_crl.getItems().get(i).setValorLiquido(BigDecimal.ZERO);
						}

						tbView_crl.refresh();

					} else {

						BigDecimal cal = Util.decimalBRtoBigDecimal(2, txtValorLiquido_crl.getText()).subtract(tmp);

						int forChanging = tbView_crl.getItems().size() - (t.getTablePosition().getRow() + 1);

						for (int i = t.getTablePosition().getRow() + 1; i < tbView_crl.getItems().size() - 1; i++) {
							tbView_crl.getItems().get(i).setValorLiquido(
									cal.divide(new BigDecimal(forChanging), 2, BigDecimal.ROUND_HALF_EVEN));
						}

						tmp = new BigDecimal(0);
						for (int i = 0; i < tbView_crl.getItems().size() - 1; i++)
							tmp = tmp.add(tbView_crl.getItems().get(i).getValorLiquido().setScale(2,
									BigDecimal.ROUND_HALF_EVEN));

						tbView_crl.getItems().get(tbView_crl.getItems().size() - 1).setValorLiquido(
								Util.decimalBRtoBigDecimal(2, txtValorLiquido_crl.getText()).subtract(tmp));

						tbView_crl.refresh();

					}

					total_crl = BigDecimal.ZERO;
					for (ContasReceber c : tbView_crl.getItems()) {
						total_crl = total_crl.add(c.getValorLiquido()).setScale(2, BigDecimal.ROUND_HALF_EVEN);
					}

					if (total_crl.compareTo(Util.decimalBRtoBigDecimal(2, txtValorLiquido_crl.getText())) == 1) {
						lblValorTotal_crl.setStyle("-fx-text-fill: red;");
						btnSave_crl.setDisable(true);
					} else {
						lblValorTotal_crl.setStyle("-fx-text-fill: black;");
						btnSave_crl.setDisable(false);
					}

					lblValorTotal_crl.setText("VALOR TOTAL: " + total_crl);
				}
			}
		});

		chkAutomatico_crl.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if (newValue) {
					txtNoDocumento_crl.setDisable(true);
					txtNoDocumento_crl.setText("000");
				} else {
					txtNoDocumento_crl.setDisable(false);
					txtNoDocumento_crl.clear();
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
				// if (!txtCodOperacao_cr.isFocused() &&
				// !txtCodCliente_cr.isFocused() &&
				// !txtCodMoeda_cr.isFocused()
				// && !txtCodCentroCusto_cr.isFocused() &&
				// !txtCodSecao_cr.isFocused()
				// && !txtCodPortador_cr.isFocused() &&
				// !txtCodPlanoContas_cr.isFocused()
				// && !txtCodVendedor_cr.isFocused() &&
				// !txtCodTipoDoc_cr.isFocused()
				// && !txtCodOperacao_crl.isFocused() &&
				// !txtCodCliente_crl.isFocused()
				// && !txtCodMoeda_crl.isFocused() &&
				// !txtCodCentroCusto_crl.isFocused()
				// && !txtCodSecao_crl.isFocused() &&
				// !txtCodPortador_crl.isFocused()
				// && !txtCodPlanoContas_crl.isFocused() &&
				// !txtCodVendedor_crl.isFocused()
				// && !txtCodTipoDoc_crl.isFocused()) {
				// if (anchorPaneDetalhes_crl.isVisible()) {
				// // txtCodigo.setDisable(true);
				// actionBtnClose(null);

				// } else if (anchorPaneDetalhes_crl.isVisible()) {
				// // txtCodigo.setDisable(true);
				// actionBtnClose(null);
				// }

				// flagPaneFilter = false;
				// actionBtnFilter(null);
				// }
				break;

			case F4:
				if (!anchorPaneDetalhes_cr.isVisible() && !anchorPaneDetalhes_crl.isVisible())
					actionBtnRefresh(null);
				break;

			case F5:
				if (!anchorPaneDetalhes_cr.isVisible() && !anchorPaneDetalhes_crl.isVisible()) {
					if (btnInsertGrid.isShowing()) {
						btnInsertGrid.hide();
					} else {
						btnInsertGrid.show();
						btnInsertGrid.requestFocus();
						FXRobot robot = FXRobotFactory.createRobot(anchorPanePrincipal.getScene());
						robot.keyPress(javafx.scene.input.KeyCode.DOWN);
					}

				}
				// if (!btnInsertGrid.isDisable() &&
				// !anchorPaneDetalhes.isVisible())
				// actionBtnInsertGrid(null);
				// else if (anchorPaneDetalhes.isVisible())
				// actionBtnInsert(null);

				break;

			case F6:
				if (anchorPaneDetalhes_cr.isVisible() && !btnSave_cr.isDisable())
					actionBtnSave_cr(null);
				else if (anchorPaneDetalhes_crl.isVisible() && !btnSave_crl.isDisable())
					actionBtnSave_crl(null);

				break;

			case F7:
				if (anchorPaneDetalhes_cr.isVisible() && !btnCancel_cr.isDisable())
					actionBtnCancel_cr(null);
				else if (anchorPaneDetalhes_crl.isVisible() && !btnCancel_crl.isDisable())
					actionBtnCancel_crl(null);
				break;

			case DELETE:
				if (!anchorPaneDetalhes_cr.isVisible() && !anchorPaneDetalhes_crl.isVisible()) {
					if (!btnDelete.isDisable())
						actionBtnDelete(null);
				}
				break;

			case P:
				if (!anchorPaneDetalhes_cr.isVisible() && !anchorPaneDetalhes_cr.isVisible()) {
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

				// if (anchorPaneDetalhes.isVisible() &&
				// !btnCancel.isDisable())
				// {
				// if (txtCodigo.getText().equals("+1"))
				// txtCodigo.clear();
				//
				// txtCodigo.setDisable(false);
				// btnCancel.setDisable(true);
				// btnSave.setDisable(true);
				// btnInsert.setDisable(false);
				// txtCodigo.requestFocus();
				// }

				break;
			default:
				break;
			}
		});

	}

	class CheckBoxTableColumn extends CheckBoxTableCell<ContasReceber, Boolean> {

		final CheckBox checkBox = new CheckBox();

		public CheckBoxTableColumn() {

			checkBox.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {

					tbView.getItems().get(getIndex()).setSelected(checkBox.isSelected());
					tbView.refresh();

					if (!checkBox.isSelected())
						checkAll.setSelected(false);
					else {
						boolean allSelected = true;

						for (ContasReceber cr : tbView.getItems()) {
							if (!cr.isSelected()) {
								allSelected = false;
								break;
							}
						}

						if (allSelected)
							checkAll.setSelected(true);

					}
				}
			});
		}

		@Override
		public void updateItem(Boolean item, boolean empty) {
			super.updateItem(item, empty);
			if (!empty) {

				checkBox.setSelected(tbView.getItems().get(getIndex()).isSelected());

				if (!checkBox.isSelected())
					checkAll.setSelected(false);
				else {
					boolean allSelected = true;

					for (ContasReceber cr : tbView.getItems()) {
						if (!cr.isSelected()) {
							allSelected = false;
							break;
						}
					}

					if (allSelected)
						checkAll.setSelected(true);

				}

				setGraphic(checkBox);

			} else {
				setGraphic(null);
			}
		}
	}

	// Editing Cell DatePicker
	class DateEditingCell extends TableCell<ContasReceber, LocalDateTime> {

		private DatePicker datePicker;

		private DateEditingCell() {
		}

		@Override
		public void startEdit() {
			if (!isEmpty()) {
				super.startEdit();
				createDatePicker();
				setText(null);
				setGraphic(datePicker);
			}
		}

		@Override
		public void cancelEdit() {
			super.cancelEdit();
			setText(getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
			setGraphic(null);
		}

		@Override
		public void updateItem(LocalDateTime item, boolean empty) {
			super.updateItem(item, empty);

			if (empty) {
				setText(null);
				setGraphic(null);
			} else {
				if (isEditing()) {
					if (datePicker != null) {
						datePicker.setValue(getDate());
					}
					setText(null);
					setGraphic(datePicker);
				} else {
					setText(getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
					setGraphic(null);
				}
			}
		}

		private void createDatePicker() {
			datePicker = new DatePicker(getDate());
			datePicker.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);

			Callback<DatePicker, DateCell> dateCellFactory = new Callback<DatePicker, DateCell>() {
				@Override
				public DateCell call(final DatePicker datePicker) {
					return new DateCell() {
						@Override
						public void updateItem(LocalDate item, boolean empty) {
							super.updateItem(item, empty);

							if (item.isBefore(dateDataEmissao_crl.getValue()))
								setDisable(true);
						}
					};
				}
			};
			datePicker.setDayCellFactory(dateCellFactory);

			// Date format for DatePicker
			StringConverter converter = new StringConverter<LocalDate>() {
				DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

				@Override
				public String toString(LocalDate date) {
					if (date != null) {
						return dateFormatter.format(date);
					} else {
						return "";
					}
				}

				@Override
				public LocalDate fromString(String string) {
					if (string != null && !string.isEmpty()) {
						return LocalDate.parse(string, dateFormatter);
					} else {
						return today;
					}
				}
			};
			datePicker.setConverter(converter);

			datePicker.setOnAction((e) -> {
				// commitEdit(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
				// commitEdit(LocalDateTime.from(getDate()));
				commitEdit(LocalDateTime.of(datePicker.getValue(), LocalTime.now()));
			});
		}

		private LocalDate getDate() {
			return getItem() == null ? LocalDate.now() : getItem().toLocalDate(); // toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		}
	}

	// Editing Cell TextField
	class EditingCell extends TableCell<ContasReceber, BigDecimal> {

		private TextField textField;

		private EditingCell() {
		}

		@Override
		public void startEdit() {
			if (!isEmpty()) {
				super.startEdit();
				createTextField();
				// setText(null);
				textField.selectAll();
				setGraphic(textField);
				textField.selectAll();
			}
		}

		@Override
		public void cancelEdit() {
			super.cancelEdit();
			setText(getItem().toString());
			setGraphic(null);
		}

		@Override
		public void updateItem(BigDecimal item, boolean empty) {
			super.updateItem(item, empty);

			if (empty) {
				// setText(item.toString());
				setText(null);
				setGraphic(null);
			} else {
				if (isEditing()) {
					if (textField != null) {
						textField.setText(getString());
					}
					setText(null);
					setGraphic(textField);
				} else {
					setText(getString());
					setGraphic(null);
				}
			}
		}

		private void createTextField() {

			textField = new TextField(getString());
			textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
			Util.decimalBR(2, textField);

			textField.textProperty().addListener((observable, oldValue, newValue) -> {

				if (chkDividirParcelas_crl.isSelected())
					if (Util.decimalBRtoBigDecimal(2, textField.getText())
							.compareTo(Util.decimalBRtoBigDecimal(2, txtValorLiquido_crl.getText())) >= 0)
						textField.setText(oldValue);
			});

			textField.setOnAction((e) -> {
				if (!textField.getText().isEmpty()
						&& Util.decimalBRtoBigDecimal(2, textField.getText()).compareTo(BigDecimal.ZERO) == 1) {
					commitEdit(Util.decimalBRtoBigDecimal(2, textField.getText()));
				} else
					cancelEdit();
			});

		}

		private String getString() {
			return getItem() == null ? "" : getItem().toString();
		}
	}

}
