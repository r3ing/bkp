package controllers.configuracoes;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.textfield.CustomTextField;

import com.jfoenix.controls.JFXToggleButton;
import com.sun.javafx.scene.control.skin.TableHeaderRow;
import application.DadosGlobais;
import controllers.compras.DepartamentoController;
import controllers.utils.ConfigColumnController;
import controllers.utils.PrintExportController;
import controllers.utils.ProgressBarForm;
import controllers.vendas.ConvenioController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javafx.scene.control.ToggleButton;

import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import models.configuracoes.Empresa;
import models.configuracoes.EmpresaDAO;
import models.configuracoes.NivelAcesso;
import models.configuracoes.NivelAcessoDAO;
import models.configuracoes.SequencialDAO;
import models.configuracoes.Usuario;
import models.configuracoes.UsuarioDAO;
import tools.application.ListMenu;
import tools.controls.ComboBoxFilter;
import tools.criptografia.EncryptMD5;
import tools.enums.EnumLogRetornoStatus;
import tools.reports.TableConfPrint;
import tools.utils.LogRetorno;
import tools.utils.Util;
import tools.xml.UserDefinedColumn;
import tools.xml.XMLTableColumns;

@SuppressWarnings("restriction")
public class UsuarioController implements Initializable {

	// INICIO <<------ SEÇAO COM A DECLARAÇÃO DOS ELEMENTOS DA INTERFACE GRAFICA
	// CRIADAS EM FXML ------->>
	@FXML
	private AnchorPane anchorPanePrincipal, anchorPaneNivelAcesso, anchorPaneListagem;

	@FXML
	private VBox hboxFormNivelAcesso;

	@FXML
	private ToolBar toolBarListagem;

	@FXML
	private Button btnInsertGrid, btnDelete, btnRefresh, btnFilter, btnPrint, btnConfig, btnCloseNivelAcesso,
			btnCancelNivelAcesso, btnSaveNivelAcesso, btnCloseCadUser;

	@FXML
	private ToggleButton toggleBtnHelp;

	@FXML
	private SplitPane splitPaneFilter;

	@FXML
	private AnchorPane anchorPaneFilter;

	@FXML
	private ComboBox<ComboBoxFilter> cboxFilterColumn;

	@FXML
	private TextField txtFilterColumn;

	@FXML
	private CustomTextField txtSearch;

	@FXML
	private ComboBox<ComboBoxFilter> cboxFlagAtivo, cboxIdioma;

	@FXML
	private Button btnQueryFilter;

	@FXML
	private TableView<Usuario> tbView;

	@FXML
	private TableColumn<Usuario, Integer> tbColCodigo, tbColCodemp, tbColAtivoInat;

	@FXML
	private TableColumn<Usuario, String> tbColDescricao, tbColIdioma;

	@FXML
	private TextField txtNomeGrid;

	@FXML
	private Button btnSaveGrid;

	@FXML
	private Label lblTotalLinhas, lblCboxFilterColumn, lblCboxFlagAtivo, lbTitleFormCadUsuario,
			lbTitleFormCadNivelAcesso, numLinhas;

	@FXML
	private AnchorPane anchorPaneDetalhes;

	@FXML
	private Label lblCodigo, lblErrorMessage;

	@FXML
	private TextField txtCodigo;

	@FXML
	private Label lblNome;

	@FXML
	private TextField txtNome;

	@FXML
	private Label lblPwd;

	@FXML
	private PasswordField txtPwd;

	@FXML
	private Label lblPwdConf, lblIdioma;

	@FXML
	private PasswordField txtPwdConf, txtPwdGrid, txtPwdConfGrid;

	@FXML
	private Button btnInsert, btnSave, btnCancel;

	@FXML
	private Pane searchPane;

	@FXML
	private ContextMenu contextMenu = null;

	@FXML
	private ProgressBar pBar;

	@FXML
	private CheckComboBox<ComboBoxFilter> cBoxEmpDispLogin;

	// COMPRAS
	@FXML
	private Tab tabCompras;

	@FXML
	private TabPane tabPaneCompras;

	// VENDAS
	@FXML
	private Tab tabVendas;

	@FXML
	private TabPane tabPaneVendas;

	@FXML
	private Label lblVdaAltObsRestritiva;

	@FXML
	private JFXToggleButton tgbVdaAltObsRestritiva;

	@FXML
	private Label lblVdaAltLimiteCredito;

	@FXML
	private JFXToggleButton tgbVdaAltLimiteCredito;

	@FXML
	private Label lblVdaMaxLimiteCredito;

	@FXML
	private TextField txtVdaMaxLimiteCredito;

	@FXML
	private Label lblVdaFaturamentoAltTipo;

	@FXML
	private JFXToggleButton tgbVdaFaturamentoAltTipo;

	@FXML
	private Label lblVdaAltVendedorPadrao;

	@FXML
	private JFXToggleButton tgbVdaAltVendedorPadrao;

	@FXML
	private Label lblVdaFaturamentoAltConvenio;

	@FXML
	private JFXToggleButton tgbVdaFaturamentoAltConvenio;

	@FXML
	private Label lblVdaFaturamentoAltTipoPreco;

	@FXML
	private JFXToggleButton tgbVdaFaturamentoAltTipoPreco;

	@FXML
	private Label lblVdaAltDadosProtesto;

	@FXML
	private JFXToggleButton tgbVdaAltDadosProtesto;

	@FXML
	private Label lblVdaAltCpfCnpj;

	@FXML
	private JFXToggleButton tgbVdaAltCpfCnpj;

	@FXML
	private Label lblVdaCliAltConsultProposta;

	@FXML
	private JFXToggleButton tgbVdaCliAltConsultProposta;

	@FXML
	private Label lblVdaVenAltConsultProposta;

	@FXML
	private JFXToggleButton tgbVdaVenAltConsultProposta;

	@FXML
	private Label lblVdaAutorizaCpfDuplicado;

	@FXML
	private JFXToggleButton tgbVdaAutorizaCpfDuplicado;

	@FXML
	private Label lblVdaAutorizaCpfZerado;

	@FXML
	private JFXToggleButton tgbVdaAutorizaCpfZerado;

	@FXML
	private Label lblVdaAutorizaClienteInadimplente;

	@FXML
	private JFXToggleButton tgbVdaAutorizaClienteInadimplente;

	@FXML
	private Label lblVdaAutorizaLimiteExedido;

	@FXML
	private JFXToggleButton tgbVdaAutorizaLimiteExedido;

	@FXML
	private Label lblVdaMaxLimiteExedido;

	@FXML
	private TextField txtVdaMaxLimiteExedido;

	@FXML
	private Label lblVdaAutorizaEstoqueNegativo;

	@FXML
	private JFXToggleButton tgbVdaAutorizaEstoqueNegativo;

	@FXML
	private Label lblVdaAutorizaPlanoPagto;

	@FXML
	private JFXToggleButton tgbVdaAutorizaPlanoPagto;

	@FXML
	private Label lblVdaAutorizaDescontoExcedido;

	@FXML
	private JFXToggleButton tgbVdaAutorizaDescontoExcedido;

	@FXML
	private Label lblVdaMaxDescontoExcedido;

	@FXML
	private TextField txtVdaMaxDescontoExcedido;

	@FXML
	private Label lblVdaAutorizaMarkupMinimo;

	@FXML
	private JFXToggleButton tgbVdaAutorizaMarkupMinimo;

	@FXML
	private Label lblVdaAutorizaDevolucao;

	@FXML
	private JFXToggleButton tgbVdaAutorizaDevolucao;

	@FXML
	private Label lblVdaAutorizaClienteObsRestritiva;

	@FXML
	private JFXToggleButton tgbVdaAutorizaClienteObsRestritiva;

	@FXML
	private Label lblVdaAutorizaClienteCadVencido;

	@FXML
	private JFXToggleButton tgbVdaAutorizaClienteCadVencido;

	@FXML
	private Label lblVdaAutorizaOperacaoSaida;

	@FXML
	private JFXToggleButton tgbVdaAutorizaOperacaoSaida;

	@FXML
	private Label lblVdaAutorizaPortadorDiferenteCliente;

	@FXML
	private JFXToggleButton tgbVdaAutorizaPortadorDiferenteCliente;

	@FXML
	private Label lblVdaAutorizaDescontoPromocao;

	@FXML
	private JFXToggleButton tgbVdaAutorizaDescontoPromocao;

	@FXML
	private Label lblVdaAutorizaClienteInativo;

	@FXML
	private JFXToggleButton tgbVdaAutorizaClienteInativo;

	@FXML
	private Label lblVdaAutorizaAcresimo;

	@FXML
	private JFXToggleButton tgbVdaAutorizaAcresimo;

	@FXML
	private Label lblVdaMaxAcresimo;

	@FXML
	private TextField txtVdaMaxAcresimo;

	@FXML
	private Label lblVdaAutorizaPortadorRestrito;

	@FXML
	private JFXToggleButton tgbVdaAutorizaPortadorRestrito;

	@FXML
	private Label lblVdaShowPrecoCusto;

	@FXML
	private JFXToggleButton tgbVdaShowPrecoCusto;

	@FXML
	private Label lblVdaShowPrecoAtacado;

	@FXML
	private JFXToggleButton tgbVdaShowPrecoAtacado;

	@FXML
	private Label lblVdaShowDebitoClientes;

	@FXML
	private JFXToggleButton tgbVdaShowDebitoClientes;

	@FXML
	private Label lblVdaShowMargemLucro;

	@FXML
	private JFXToggleButton tgbVdaShowMargemLucro;

	@FXML
	private Label lblVdaShowCreditoCliente;

	@FXML
	private JFXToggleButton tgbVdaShowCreditoCliente;

	@FXML
	private Label lblVdaLiberacaoRemota;

	@FXML
	private JFXToggleButton tgbVdaLiberacaoRemota;

	@FXML
	private Label lblVdaAcessaClientePrecoCusto;

	@FXML
	private JFXToggleButton tgbVdaAcessaClientePrecoCusto;

	@FXML
	private Tab tabFinanceiro;

	@FXML
	private Tab tabLivrosFiscales;

	@FXML
	private Tab tabRecursosH;

	@FXML
	private Tab tabServicos;

	@FXML
	private Tab tabProducao;

	@FXML
	private Tab tabContabilidade;

	@FXML
	private Tab tabConfiguracoes;

	// PARAMETROS GERAIS
	@FXML
	private Tab tabParamGerais;

	@FXML
	private TabPane tabPaneParamGerais;

	@FXML
	private Label lblCodFuncionario;

	@FXML
	private CustomTextField txtCodFuncionario;

	@FXML
	private TextField txtFuncionario;

	@FXML
	private Tab tabConfigTela1;

	@FXML
	private Tab tabConfigAcoes;

	@FXML
	private TabPane tabPaneConfig, tpCadastroUsr;

	@FXML
	private JFXToggleButton tgBtnAltDtMovtoSistema, tgBtnCopyUsu, tgBtnDefNiv, tgBtnCopyNivAcesso, tgBtnFinAltValorTitCtasRec, tgBtnFinAltDtVenctoTitCtasRec;

	@FXML
	private TextField txtLimDiasDtMovtoAvan, txtLimDiasDtMovtoRetro;

	// FINAL <<----------- SEÇAO COM A DECLARAÇÃO DOS ELEMENTOS DA INTERFACE
	// GRAFICA CRIADAS EM FXML ------>>

	// INICIO <<------------------ ATRIBUTOS UTILIZADOS NA CLASSE
	// --------------->>
	private ObservableList<Usuario> dataUsuario = null;
	private ObservableList<ComboBoxFilter> listComboBoxFilter = FXCollections.observableArrayList();
	private List<Integer> parameters = Arrays.asList(1);
	private List<TableConfPrint> tableShowPrintList = new ArrayList<TableConfPrint>();
	static Stage stg;
	private NivelAcesso nivAcessoPermCadUsu;
	private NivelAcesso nivAcessoPermAuditoria;
	private MenuBar menuPrincipal;
	private TabPane tabPrincipal;
	private List<String> listMenu;
	private List<NivelAcesso> listaNiveisAcesso;

	private String msgCopiaUsuario;
	// <<--------- ESTADO INICIAL DO PAINEL DE FILTROS --------->>
	private boolean flagPaneFilter = true;
	private boolean flagBtnInsert = false;

	// <<------- OBJETOS UTILIZADOS NA CLASSE ------>>
	UsuarioDAO entidadeDao = new UsuarioDAO();
	SequencialDAO seqHibDAO = new SequencialDAO();
	Usuario entidadeBean = new Usuario();
	Util util = new Util();

	// <<----------- CONSTRUTOR COM PERMISSOES DA CLASSE --------->>
	// o_nivAcessoPermissao = PERMISSOES QUE VEM DA TABELA NIVEIS DE ACESSO
	// REFERENTE AO MENU CADASTRO DE USUARIO
	// o_nivAcessoPermissao2 = PERMISSOES QUE VEM DA TABELA NIVEIS DE ACESSO
	// REFERENTE AO MENU AUDITORIA
	public UsuarioController(NivelAcesso o_nivAcessoPermissao, MenuBar o_menuPrincipal, TabPane o_tabPrincipal) {

		this.nivAcessoPermCadUsu = o_nivAcessoPermissao;

		this.menuPrincipal = o_menuPrincipal;
		this.tabPrincipal = o_tabPrincipal;

	}

	// <--------- CONSTRUTOR PADRÃO DA CLASSE ---------->
	public UsuarioController() {

	}

	// INICIO <<---------- SEÇAO COM A DECLARAÇÃO DAS ACÕES DOS BOTÕES NO
	// FORMULARIO FXML ---------->>

	/**
	 * Ação do Botão Cancelamento de Operação a partir da aba Detalhes.
	 */
	@FXML
	void actionBtnCancel(ActionEvent event) {

		// RETIRA O EFEITO CASO OS CAMPOS FOSSEM PREENCHIDOS DE FORMA INVALIDA
		Util.setDefaultStyle(txtCodigo, txtNome, txtPwd, txtPwdConf);

		Util.limpar(txtCodigo, txtNome, txtPwd, txtPwdConf);

		carregaDadosDetalhe(true, null, false, false);

		btnInsert.setDisable(false);
		btnCancel.setDisable(true);
		btnSave.setDisable(true);
		txtCodigo.setDisable(false);
		txtCodigo.requestFocus();

	}

	/**
	 * Ação do Botão Configuração do Grid, para ajustar as colunas do Grid.
	 */
	@FXML
	void actionBtnConfig(ActionEvent event) {

		try {
			stg = new Stage();
			Scene scene = new Scene(util.openWindow("/views/utils/viewConfigColumns.fxml",
					new ConfigColumnController(UsuarioController.class, tbView, "Grid-Cad-Usuario")));
			stg.setScene(scene);
			stg.initStyle(StageStyle.TRANSPARENT);
			stg.initModality(Modality.APPLICATION_MODAL);
			stg.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Ação do Botão de Exclusão e Reatição de um Registro.
	 */
	@FXML
	void actionBtnDelete(ActionEvent event) {
		if ((tbView.getSelectionModel().getSelectedItem().getFlagAtivo() == 1
				&& nivAcessoPermCadUsu.getFlagDisable() == 1)
				|| (tbView.getSelectionModel().getSelectedItem().getFlagAtivo() == 0
						&& nivAcessoPermCadUsu.getFlagEnable() == 1)) {
			// if (util.showAlert("Tem certeza de que deseja "
			// + (tbView.getSelectionModel().getSelectedItem().getFlagAtivo() ==
			// 1 ? "EXCLUIR" : "ATIVAR")
			// + " o departamento.", "confirmation")) {
			if (util.showAlert(
					DadosGlobais.resourceBundle.getString("alertConfirmOprExcluir") + " "
							+ (entidadeBean.getFlagAtivo().equals(1)
									? DadosGlobais.resourceBundle.getString("oprExcluir")
									: DadosGlobais.resourceBundle.getString("oprAtivar"))
							+ " " + DadosGlobais.resourceBundle.getString("usuarioController.alertConfirmOprExcluir"),
					"confirmation")) {

				Task<String> tarefaCargaPg = new Task<String>() {

					@Override
					protected String call() throws Exception {
						parameters = Arrays.asList(1);
						entidadeDao.excluir(tbView.getSelectionModel().getSelectedItem().getCodigo(),
								DadosGlobais.codEmpDefault, tbView.getSelectionModel().getSelectedItem().getRecordNo(),
								DadosGlobais.idUsuario,
								tbView.getSelectionModel().getSelectedItem().getFlagAtivo() == 1 ? 0 : 1);

						return "-";

					}

					@Override
					protected void succeeded() {
						stg.close();
						pBar.setProgress(1);
						tarefaConsulta("filter", true);
					}

					@Override
					protected void failed() {
						stg.close();
						util.tratamentoExcecao(exceptionProperty().getValue().toString(),
								"[ UsuarioController.actionBtnDelete() ]");
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
				// stg =
				// ProgressBarForm.showProgressBar(DepartamentoController.class,
				// tarefaCargaPg,
				// (tbView.getSelectionModel().getSelectedItem().getFlagAtivo()
				// == 1 ? "Excluindo" : "Ativando")
				// + " Registro",
				// false);
				stg = ProgressBarForm.showProgressBar(UsuarioController.class, tarefaCargaPg,
						tbView.getSelectionModel().getSelectedItem().getFlagAtivo().equals(1)
								? DadosGlobais.resourceBundle.getString("infoActiReg")
								: DadosGlobais.resourceBundle.getString("infoExcRegis"),
						false);
				pBar.setProgress(-1);

			}
			// DESABILITA O BOTAO DE DELETE
			btnDelete.setDisable(true);
		} else
			util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
	}

	/**
	 * Ação do Botão de Inserção de um novo Registro a Partir da Tela de
	 * Detalhes.
	 */
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

	/**
	 * Ação do Botão de Inserção de um novo Registro a Partir da Tela de
	 * Detalhes.
	 */
	@FXML
	void actionBtnInsert(ActionEvent event) {

		actionBtnInsertGrid(null);

	}

	/**
	 * Ação do Botão de Inserção de um novo Registro a Partir da Tela de
	 * Listagem.
	 */
	@FXML
	void actionBtnInsertGrid(ActionEvent event) {
		if (nivAcessoPermCadUsu.getFlagInsert() == 1) {

			flagBtnInsert = true;
			btnInsert.setDisable(true);
			btnSave.setDisable(false);
			btnCancel.setDisable(false);
			txtCodigo.setDisable(true);
			txtCodigo.setText("+1");
			txtNome.clear();
			txtPwd.clear();
			txtPwdConf.clear();
			txtNome.requestFocus();
			resetaOpções();

			anchorPaneDetalhes.setVisible(true);
			
			
			//--START EMPTY---
			lblErrorMessage.setText("");
			
		} else {
			util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
		}
	}

	/**
	 * Ação do Botão de Impressão dos dados do Grid.
	 */
	@FXML
	void actionBtnPrint(ActionEvent event) throws NoSuchFieldException, SecurityException {
		if (nivAcessoPermCadUsu.getFlagPrint() == 1) {
			printExportShow();
		} else {
			util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
		}

	}

	/**
	 * Ação do Botão de Pesquisa por Filtros, que traz a lista dos dados
	 * filtrados para o Grid.
	 */
	@FXML
	void actionBtnQueryFilter(ActionEvent event) {

		if (txtFilterColumn.getText().isEmpty()) {
			if (util.showAlert(DadosGlobais.resourceBundle.getString("alertConfirShowAllData"), "confirmation")) {
				tarefaConsulta("filter", true);
			}
		} else {
			tarefaConsulta("filter", true);
		}
	}

	/**
	 * Ação do Botão Salvar da Aba Detalhes
	 */
	@FXML
	void actionBtnSave(ActionEvent event) {

		if (nivAcessoPermCadUsu.getFlagUpdate() == 1) {

			if (!Util.noEmpty( lblErrorMessage, anchorPaneDetalhes, txtNome)) {

				entidadeBean.setNome(txtNome.getText());

				entidadeBean.setIdioma(cboxIdioma.getValue().getField());

				List<String> listEmpDisp = new ArrayList<String>();

				for (ComboBoxFilter cbox : cBoxEmpDispLogin.getCheckModel().getCheckedItems()) {
					listEmpDisp.add(cbox.getField());
				}

				// VENDAS
				entidadeBean.setVdaAltObsRestritiva(tgbVdaAltObsRestritiva.isSelected() ? 1 : 0);
				entidadeBean.setVdaAltLimiteCredito(tgbVdaAltLimiteCredito.isSelected() ? 1 : 0);
				entidadeBean.setVdaMaxLimiteCredito(Util.decimalBRtoBigDecimal(4, txtVdaMaxLimiteCredito.getText()));
				entidadeBean.setVdaFaturamentoAltTipo(tgbVdaFaturamentoAltTipo.isSelected() ? 1 : 0);
				entidadeBean.setVdaAltVendedorPadrao(tgbVdaAltVendedorPadrao.isSelected() ? 1 : 0);
				entidadeBean.setVdaFaturamentoAltConvenio(tgbVdaFaturamentoAltConvenio.isSelected() ? 1 : 0);
				entidadeBean.setVdaFaturamentoAltTipoPreco(tgbVdaFaturamentoAltTipoPreco.isSelected() ? 1 : 0);
				entidadeBean.setVdaAltDadosProtesto(tgbVdaAltDadosProtesto.isSelected() ? 1 : 0);
				entidadeBean.setVdaAltCpfCnpj(tgbVdaAltCpfCnpj.isSelected() ? 1 : 0);
				entidadeBean.setVdaCliAltConsultProposta(tgbVdaCliAltConsultProposta.isSelected() ? 1 : 0);
				entidadeBean.setVdaVenAltConsultProposta(tgbVdaVenAltConsultProposta.isSelected() ? 1 : 0);
				entidadeBean.setVdaAutorizaCpfDuplicado(tgbVdaAutorizaCpfDuplicado.isSelected() ? 1 : 0);
				entidadeBean.setVdaAutorizaCpfZerado(tgbVdaAutorizaCpfZerado.isSelected() ? 1 : 0);
				entidadeBean.setVdaAutorizaClienteInadimplente(tgbVdaAutorizaClienteInadimplente.isSelected() ? 1 : 0);
				entidadeBean.setVdaAutorizaLimiteExedido(tgbVdaAutorizaLimiteExedido.isSelected() ? 1 : 0);
				entidadeBean.setVdaMaxLimiteExedido(Util.decimalBRtoBigDecimal(4, txtVdaMaxLimiteExedido.getText()));
				entidadeBean.setVdaAutorizaEstoqueNegativo(tgbVdaAutorizaEstoqueNegativo.isSelected() ? 1 : 0);
				entidadeBean.setVdaAutorizaPlanoPagto(tgbVdaAutorizaPlanoPagto.isSelected() ? 1 : 0);
				entidadeBean.setVdaAutorizaDescontoExcedido(tgbVdaAutorizaDescontoExcedido.isSelected() ? 1 : 0);
				entidadeBean
						.setVdaMaxDescontoExcedido(Util.decimalBRtoBigDecimal(4, txtVdaMaxDescontoExcedido.getText()));
				entidadeBean.setVdaAutorizaMarkupMinimo(tgbVdaAutorizaMarkupMinimo.isSelected() ? 1 : 0);
				entidadeBean.setVdaAutorizaDevolucao(tgbVdaAutorizaDevolucao.isSelected() ? 1 : 0);
				entidadeBean
						.setVdaAutorizaClienteObsRestritiva(tgbVdaAutorizaClienteObsRestritiva.isSelected() ? 1 : 0);
				entidadeBean.setVdaAutorizaClienteCadVencido(tgbVdaAutorizaClienteCadVencido.isSelected() ? 1 : 0);
				entidadeBean.setVdaAutorizaOperacaoSaida(tgbVdaAutorizaOperacaoSaida.isSelected() ? 1 : 0);
				entidadeBean.setVdaAutorizaPortadorDiferenteCliente(
						tgbVdaAutorizaPortadorDiferenteCliente.isSelected() ? 1 : 0);
				entidadeBean.setVdaAutorizaDescontoPromocao(tgbVdaAutorizaDescontoPromocao.isSelected() ? 1 : 0);
				entidadeBean.setVdaAutorizaClienteInativo(tgbVdaAutorizaClienteInativo.isSelected() ? 1 : 0);
				entidadeBean.setVdaAutorizaAcresimo(tgbVdaAutorizaAcresimo.isSelected() ? 1 : 0);
				entidadeBean.setVdaMaxAcresimo(Util.decimalBRtoBigDecimal(4, txtVdaMaxAcresimo.getText()));
				entidadeBean.setVdaAutorizaPortadorRestrito(tgbVdaAutorizaPortadorRestrito.isSelected() ? 1 : 0);
				entidadeBean.setVdaShowPrecoCusto(tgbVdaShowPrecoCusto.isSelected() ? 1 : 0);
				entidadeBean.setVdaShowPrecoAtacado(tgbVdaShowPrecoAtacado.isSelected() ? 1 : 0);
				entidadeBean.setVdaShowDebitoClientes(tgbVdaShowDebitoClientes.isSelected() ? 1 : 0);
				entidadeBean.setVdaShowMargemLucro(tgbVdaShowMargemLucro.isSelected() ? 1 : 0);
				entidadeBean.setVdaShowCreditoCliente(tgbVdaShowCreditoCliente.isSelected() ? 1 : 0);
				entidadeBean.setVdaLiberacaoRemota(tgbVdaLiberacaoRemota.isSelected() ? 1 : 0);
				entidadeBean.setVdaAcessaClientePrecoCusto(tgbVdaAcessaClientePrecoCusto.isSelected() ? 1 : 0);

				// RADIOBUTTONS DAS AÇÕES DA ABA DE CONFIGURAÇÕES
				// TELA 1

				// PERGUNTA 1
				// GRAVA O ARRAY DA EMPRESAS LIBERADAS PARA ACESSO, RETIRA OS
				// CARACTERES [ ] DA STRING PARA GRAVAR NO FORMATO 1,2,8...
				entidadeBean
						.setCodEmpDispLogin(listEmpDisp.toString().replace("[", "").replace("]", "").replace(" ", ""));

				// PERGUNTA 2 E 3
				// entidadeBean.setConfFlagAltDtMovto(Integer.valueOf(grpAltDtMovtoSistema.getSelectedToggle().getUserData().toString()));
				entidadeBean.setConfFlagAltDtMovto((tgBtnAltDtMovtoSistema.isSelected() ? 1 : 0));
				entidadeBean.setConfDiasRetroDtMovto(Integer.valueOf(txtLimDiasDtMovtoRetro.getText()));
				entidadeBean.setConfDiasAvanDtMovto(Integer.valueOf(txtLimDiasDtMovtoAvan.getText()));

				// TELA AÇÕES
				entidadeBean.setActionDefNivAcesso(tgBtnDefNiv.isSelected() ? 1 : 0);
				entidadeBean.setActionCopyNivAcesso(tgBtnCopyNivAcesso.isSelected() ? 1 : 0);
				entidadeBean.setActionCopyUsuario(tgBtnCopyUsu.isSelected() ? 1 : 0);

				//------------------------------- FINANCEIRO -----------------------------//
				//============= TELA 1 ==================//
				entidadeBean.setFinAltValorCtasReceber(tgBtnFinAltValorTitCtasRec.isSelected() ? 1 : 0);
				entidadeBean.setFinAltDtVenctoCtasReceber(tgBtnFinAltDtVenctoTitCtasRec.isSelected() ? 1 : 0);
				
				//============= FIM TELA 1 =============//
				//------------------------------ FIM FINANCEIRO -------------------------//
				
				
				
				// PARAMETROS GERAIS
				// entidadeBean.setCodFuncionario(Integer.valueOf(txtCodFuncionario.getText()));
				// entidadeBean.setCodFuncionarioFk(txtCodFuncionarioFk.getText());

				if (txtCodigo.isDisable() && txtCodigo.getText().equals("+1")) {

					if (!Util.noEmpty(txtPwd) && !Util.noEmpty(txtPwdConf)) {

						if (txtPwd.getText().equals(txtPwdConf.getText())) {

							entidadeBean.setPwd(EncryptMD5.getMD5(txtPwd.getText()));

							listMenu = new ArrayList<String>();

							Task<String> TarefaRefresh = new Task<String>() {

								LogRetorno logRet = new LogRetorno();

								@Override
								protected String call() throws Exception {

									listaMenu(menuPrincipal.getMenus());

									logRet = entidadeDao.inserir(entidadeBean, listMenu);

									return "-";
								}

								@Override
								protected void succeeded() {
									if (logRet.getStatus().equals(1)) {

										txtCodigo.setText(String.valueOf(logRet.getLastRecord()));
										txtPwd.clear();
										txtPwdConf.clear();
										btnSave.setDisable(true);
										btnCancel.setDisable(true);
										btnInsert.setDisable(false);
										txtCodigo.setDisable(false);
										txtCodigo.requestFocus();

									} else {

										util.showAlert(logRet.getMsg(), "error");

									}
									stg.close();
									pBar.setProgress(1);

								}

								@Override
								protected void failed() {
									stg.close();
									
									util.tratamentoExcecao(exceptionProperty().getValue().toString(),
											"[ UsuarioController.actionBtnSave() - INSERT ]");
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
							
							stg = ProgressBarForm.showProgressBar(UsuarioController.class, TarefaRefresh,
									DadosGlobais.resourceBundle.getString("infInsertRegistro"), false);
							pBar.setProgress(-1);

						} else {
							util.showAlert(DadosGlobais.resourceBundle.getString("usuarioController.alertValidaSenha"),
									"warning");
							Util.limpar(txtPwd, txtPwdConf);
							txtPwd.requestFocus();
						}
					}
				} else if (entidadeBean.getCodigo() == Integer.valueOf(txtCodigo.getText())) {

					if (vericaUserRoot(entidadeBean.getNome())) {

						entidadeBean.setCodigo(entidadeBean.getCodigo());
						entidadeBean.setCodemp(entidadeBean.getCodemp());
						entidadeBean.setRecordNo(entidadeBean.getRecordNo());

						if (!txtPwd.getText().isEmpty() && !txtPwd.getText().isEmpty()) {
							if (txtPwd.getText().equals(txtPwdConf.getText())) {
								entidadeBean.setPwd(EncryptMD5.getMD5(txtPwd.getText()));
							}
						}

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
									Util.showNotification(DadosGlobais.resourceBundle.getString("notificationSucess") +"\n"+ DadosGlobais.resourceBundle.getString("notificationSignOff") );
									btnSave.setDisable(true);
									btnCancel.setDisable(true);
									btnInsert.setDisable(false);
									txtCodigo.setDisable(false);
									txtCodigo.requestFocus();
									txtCodigo.requestFocus();
								} else {
									util.showAlert(logRet.getMsg(), "error");
								}
							}

							@Override
							protected void failed() {
								stg.close();
								
								util.tratamentoExcecao(exceptionProperty().getValue().toString(),
										"[ UsuarioController.actionBtnSave() - UPDATE ]");
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
						// stg =
						// ProgressBarForm.showProgressBar(UsuarioController.class,
						// TarefaRefresh,
						// "Gravando Usuário", false);
						stg = ProgressBarForm.showProgressBar(UsuarioController.class, TarefaRefresh,
								DadosGlobais.resourceBundle.getString("infSaveChange"), false);
						pBar.setProgress(-1);

					} else {
						util.showAlert("Este usuário não pode ser editado", "warning");
					}
				}

				else {
					carregaDadosDetalhe(false, Integer.valueOf(txtCodigo.getText()), true, false);
				}
			}
		} else {
			util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
		}
	}

	/**
	 * Ação do Botão Salvar da Aba Listagem
	 */
	@FXML
	void actionBtnSaveGrid(ActionEvent event) {

		if (nivAcessoPermCadUsu.getFlagUpdate() == 1) {

			if (!Util.noEmpty(txtNomeGrid)) {

				if (vericaUserRoot(entidadeBean.getNome())) {

					entidadeBean.setCodigo(entidadeBean.getCodigo());
					entidadeBean.setCodemp(entidadeBean.getCodemp());
					entidadeBean.setRecordNo(entidadeBean.getRecordNo());

					if (!txtPwdGrid.getText().isEmpty() && !txtPwdGrid.getText().isEmpty()) {
						if (txtPwdGrid.getText().equals(txtPwdConfGrid.getText())) {
							entidadeBean.setPwd(txtPwdGrid.getText());
						}
					}

					entidadeBean.setNome(txtNomeGrid.getText());

					Task<String> tarefaCargaPg = new Task<String>() {

						@Override
						protected String call() throws Exception {
							entidadeDao.update(entidadeBean);
							return "-";

						}

						@Override
						protected void succeeded() {
							stg.close();
							pBar.setProgress(1);
							tarefaConsulta("filter", true);
						}

						@Override
						protected void failed() {
							stg.close();
							// Util.alertError("Erro ao Conectar ao Banco de
							// Dados, verifique a conexão de rede!");
							util.tratamentoExcecao(exceptionProperty().getValue().toString(),
									"[ UsuarioController.actionBtnSave() - INSERT ]");
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
					// stg =
					// ProgressBarForm.showProgressBar(DepartamentoController.class,
					// tarefaCargaPg,
					// "Gravando Alterações", false);
					stg = ProgressBarForm.showProgressBar(UsuarioController.class, tarefaCargaPg,
							DadosGlobais.resourceBundle.getString("infInsertRegistro"), false);
					pBar.setProgress(-1);

					btnSaveGrid.setDisable(true);
					txtPwdGrid.clear();
					txtPwdConfGrid.clear();
					txtPwdGrid.setDisable(true);
					txtPwdConfGrid.setDisable(true);
				} else {
					Util.alertWarn("Este usuário não pode ser editado");
				}
			}
		} else {
			util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
		}
	}

	/**
	 * Ação do Botão Refresh, que traz a lista de todos os dados para o Grid.
	 */
	@FXML
	void actionBtnRefresh(ActionEvent event) {

		if (util.showAlert(DadosGlobais.resourceBundle.getString("alertConfirShowAllData"), "confirmation")) {

			parameters = Arrays.asList(1);
			tarefaConsulta("all", true);
			cboxFlagAtivo.getSelectionModel().selectFirst();

		}

	}

	@FXML
	void actionBtnCloseNivelAcesso(ActionEvent event) {
		Util.fechaTelaCadastro(anchorPaneListagem, anchorPaneNivelAcesso);
		f = null;
	}

	@FXML
	void actionBtnCloseCadUser(ActionEvent event) {
		Util.fechaTelaCadastro(anchorPaneListagem, anchorPaneDetalhes);
		ObservableList<Usuario> obsListUsr = tbView.getItems();
		if (obsListUsr.size() > 0) {
			for (int i = 0; i < obsListUsr.size(); i++) {
				Integer usrCod = obsListUsr.get(i).getCodigo();

				if (usrCod != null && txtCodigo.getText() != null) {
					if (usrCod.toString().equals(txtCodigo.getText())) {
						tbView.getSelectionModel().select(i);
						tbView.scrollTo(i);
						break;
					} else {
					}
				} else {
				}

			}

			tbView.requestFocus();

		} else {
			txtFilterColumn.requestFocus();
		}

		Util.setDefaultStyle(txtCodigo);
	}

	private FXMLLoader f;

	@FXML
	void actionBtnSaveNivelAcesso(ActionEvent event) {
		try {
			((NivelesAcessoController) f.getController()).actionSavePermissions(event);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void actionBtnCancelNivelAcesso(ActionEvent event) {
		((NivelesAcessoController) f.getController()).cancelActionSave();
	}

	// FINAL <<------------ SEÇAO COM A DECLARAÇÃO DAS ACÕES DOS BOTÕES NO
	// FORMULARIO FXML --------->>

	// INICIO <<------ SEÇAO COM A DECLARAÇÃO DAS EVENTOS DOS CAMPOS TEXTFIELS
	// EXISTENTES NO FORMULARIO FXML ------->>
	/**
	 * Evento do TextField txtCodigo que realiza a consulta do registro de
	 * acordo com o codigo digitado ao pressionar ENTER ou TAB.
	 */
	@FXML
	void keyPressedTxtCodigo(KeyEvent event) {
		
		lblErrorMessage.setText(""); 
		
		if (event.getCode().equals(KeyCode.ENTER)) {
			txtNome.requestFocus();
		}
	}

	/**
	 * Evento do TextField txtNome seta o estilo default ao campo e configura o
	 * formulario para salvar caso seja pressionado ENTER ou TAB.
	 */
	@FXML
	void keyPressedTxtNome(KeyEvent event) {

		lblErrorMessage.setText(""); 
		
		Util.setDefaultStyle(txtNome);

		if (event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.TAB)) {
			btnSave.setDisable(false);
			btnCancel.setDisable(false);
			btnInsert.setDisable(true);
			txtPwd.requestFocus();
		}
	}

	/**
	 * Evento do TextField txtPwd seta o estilo default ao campo
	 */
	@FXML
	void keyPressedTxtPwd(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ENTER)) {
			txtPwdConf.requestFocus();
		}
		Util.setDefaultStyle(txtPwd);
	}

	/**
	 * Evento do TextField txtPwdConf seta o estilo default ao campo
	 */
	@FXML
	void keyPressedTxtPwdConf(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ENTER)) {
			cboxIdioma.requestFocus();
		}
		Util.setDefaultStyle(txtPwdConf);
	}

	/**
	 * Evento do TextField txtNomeGrid seta o estilo default ao campo e
	 * configura o formulario para salvar caso seja pressionado ENTER ou TAB.
	 */
	@FXML
	void keyPressedTxtNomeGrid(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.TAB)) {
			btnSaveGrid.setDisable(false);
			txtPwdGrid.setDisable(false);
			txtPwdConfGrid.setDisable(false);
			txtPwdGrid.requestFocus();
		}
	}

	/**
	 * Evento do Texfield txtCodigo na tela detalhes, garante que somente
	 * caracteres Numéricos sejam inseridos pelo usário
	 */
	@FXML
	void keyTypedTxtCodigo(KeyEvent event) {
		Util.isLetter(event);
	}

	/**
	 * Evento do Texfield txtNome na tela detalhes, garante que somente
	 * caracteres Alfa-Numéricos sejam inseridos pelo usário
	 */
	@FXML
	void keyTypedTxtNome(KeyEvent event) {
		util.onlyAlphanumeric(event);
	}

	/**
	 * Evento do Texfield txtNomeGrid na tela listagem, garante que somente
	 * caracteres Alfa-Numéricos sejam inseridos pelo usário
	 */
	@FXML
	void keyTypedTxtNomeGrid(KeyEvent event) {
		util.onlyAlphanumeric(event);
	}

	@FXML
	void mouseClickedToggleHelp(MouseEvent event) {

	}

	/**
	 * Evento do TextField txtFilterColumn que efetua a busca pressionado ENTER
	 * ou TAB.
	 */
	@FXML
	void keyPressedTxtFilterColumn(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ENTER)) {
			actionBtnQueryFilter(null);
		}
	}

	/**
	 * Evento do textField txtFilterColumn para filtrar dados do Tableview ao
	 * digitar
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@FXML
	void keyReleasedTxtFilterColumn(KeyEvent event) {
		if (dataUsuario != null) {

			if (txtFilterColumn.textProperty().get().isEmpty()) {
				tbView.setItems(dataUsuario);
				numLinhas.setText(String.valueOf(tbView.getItems().size()));
				if (tbView.getItems().size() > 0)
					btnPrint.setDisable(false);
				else
					btnPrint.setDisable(true);

				return;
			}

			ObservableList tableItems = FXCollections.observableArrayList();
			ObservableList cols = tbView.getColumns();

			for (int i = 0; i < dataUsuario.size(); i++) {

				for (int j = 0; j < cols.size(); j++) {

					TableColumn col = (TableColumn) cols.get(j);

					String cellValue = col.getCellData(dataUsuario.get(i)).toString();
					if (cellValue.toUpperCase().contains(txtFilterColumn.textProperty().get().toUpperCase())) {
						tableItems.add(dataUsuario.get(i));
						break;
					}

				}
			}

			tbView.setItems(tableItems);
			numLinhas.setText(String.valueOf(tbView.getItems().size()));

			if (tbView.getItems().size() > 0)
				btnPrint.setDisable(false);
			else
				btnPrint.setDisable(true);

		}
	}

	@FXML
	void actionTgBtnAltDtMovtoSistema(ActionEvent event) {

		txtLimDiasDtMovtoAvan.setDisable((tgBtnAltDtMovtoSistema.isSelected() ? false : true));
		txtLimDiasDtMovtoRetro.setDisable((tgBtnAltDtMovtoSistema.isSelected() ? false : true));

	}

	public void actionToggleButtons() {

		tgbVdaAltLimiteCredito.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if (newValue) {
					lblVdaMaxLimiteCredito.setDisable(false);
					txtVdaMaxLimiteCredito.setDisable(false);

				} else {
					lblVdaMaxLimiteCredito.setDisable(true);
					txtVdaMaxLimiteCredito.setDisable(true);

				}
			}
		});

		tgbVdaAutorizaLimiteExedido.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if (newValue) {
					lblVdaMaxLimiteExedido.setDisable(false);
					txtVdaMaxLimiteExedido.setDisable(false);

				} else {
					lblVdaMaxLimiteExedido.setDisable(true);
					txtVdaMaxLimiteExedido.setDisable(true);

				}
			}
		});

		tgbVdaAutorizaDescontoExcedido.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if (newValue) {
					lblVdaMaxDescontoExcedido.setDisable(false);
					txtVdaMaxDescontoExcedido.setDisable(false);

				} else {
					lblVdaMaxDescontoExcedido.setDisable(true);
					txtVdaMaxDescontoExcedido.setDisable(true);

				}
			}
		});

		tgbVdaAutorizaAcresimo.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if (newValue) {
					lblVdaMaxAcresimo.setDisable(false);
					txtVdaMaxAcresimo.setDisable(false);

				} else {
					lblVdaMaxAcresimo.setDisable(true);
					txtVdaMaxAcresimo.setDisable(true);

				}
			}
		});
		
		
		
	}

	// FINAL <<------ SEÇAO COM A DECLARAÇÃO DAS EVENTOS DOS CAMPOS TEXTFIELS
	// EXISTENTES NO FORMULARIO FXML ------->>

	// INICIO <<-------- SEÇAO COM MÉTODOS DA CLASSE ------------------------>>
	/**
	 * Método que preenche o formulario de edição dos dados do Usuário
	 */
	public void carregaDadosDetalhe(boolean lastRegistro, Integer codigo, boolean flagTabListagem,
			boolean flagCloneUsuario) {

		if ((!lastRegistro && !txtCodigo.getText().isEmpty() && codigo > 0) || (lastRegistro)) {
			if (!lastRegistro) {
				entidadeBean = entidadeDao.getUserById(codigo, DadosGlobais.codEmpDefault);
			} else {
				
				entidadeBean = entidadeDao.getLastUsuario();
			}
			if (entidadeBean != null) {

				if (!flagCloneUsuario) {
					txtCodigo.setText(String.valueOf(entidadeBean.getCodigo()));
					btnInsert.setDisable(true);
				} else {
					txtCodigo.setText("+1");
					btnInsert.setDisable(false);
				}
				
				tpCadastroUsr.getSelectionModel().selectFirst();
				tabPaneCompras.getSelectionModel().selectFirst();
				tabPaneVendas.getSelectionModel().selectFirst();
				tabPaneConfig.getSelectionModel().selectFirst();
				tabPaneParamGerais.getSelectionModel().selectFirst();

				txtNome.setText(entidadeBean.getNome());
				btnSave.setDisable(false);
				btnCancel.setDisable(false);
				txtNome.requestFocus();

				// SELECIONA O IDIOMA DE ACORDO COM QUE ESTA PREENCHIDO NO
				// USUARIO
				for (int i = 0; i < cboxIdioma.getItems().size(); i++) {
					if (cboxIdioma.getItems().get(i).getField().equals((entidadeBean.getIdioma()))) {
						cboxIdioma.getSelectionModel().select(i);
						break;
					}
				}

				// VENDAS
				tgbVdaAltObsRestritiva.setSelected(entidadeBean.getVdaAltObsRestritiva() == 1 ? true : false);
				tgbVdaAltLimiteCredito.setSelected(entidadeBean.getVdaAltLimiteCredito() == 1 ? true : false);
				txtVdaMaxLimiteCredito.setText(entidadeBean.getVdaMaxLimiteCredito().toString());
				tgbVdaFaturamentoAltTipo.setSelected(entidadeBean.getVdaFaturamentoAltTipo() == 1 ? true : false);
				tgbVdaAltVendedorPadrao.setSelected(entidadeBean.getVdaAltVendedorPadrao() == 1 ? true : false);
				tgbVdaFaturamentoAltConvenio
						.setSelected(entidadeBean.getVdaFaturamentoAltConvenio() == 1 ? true : false);
				tgbVdaFaturamentoAltTipoPreco
						.setSelected(entidadeBean.getVdaFaturamentoAltTipoPreco() == 1 ? true : false);
				tgbVdaAltDadosProtesto.setSelected(entidadeBean.getVdaAltDadosProtesto() == 1 ? true : false);
				tgbVdaAltCpfCnpj.setSelected(entidadeBean.getVdaAltCpfCnpj() == 1 ? true : false);
				tgbVdaCliAltConsultProposta.setSelected(entidadeBean.getVdaCliAltConsultProposta() == 1 ? true : false);
				tgbVdaVenAltConsultProposta.setSelected(entidadeBean.getVdaVenAltConsultProposta() == 1 ? true : false);
				tgbVdaAutorizaCpfDuplicado.setSelected(entidadeBean.getVdaAutorizaCpfDuplicado() == 1 ? true : false);
				tgbVdaAutorizaCpfZerado.setSelected(entidadeBean.getVdaAutorizaCpfZerado() == 1 ? true : false);
				tgbVdaAutorizaClienteInadimplente
						.setSelected(entidadeBean.getVdaAutorizaClienteInadimplente() == 1 ? true : false);
				tgbVdaAutorizaLimiteExedido.setSelected(entidadeBean.getVdaAutorizaLimiteExedido() == 1 ? true : false);
				txtVdaMaxLimiteExedido.setText(entidadeBean.getVdaMaxLimiteExedido().toString());
				tgbVdaAutorizaEstoqueNegativo
						.setSelected(entidadeBean.getVdaAutorizaEstoqueNegativo() == 1 ? true : false);
				tgbVdaAutorizaPlanoPagto.setSelected(entidadeBean.getVdaAutorizaPlanoPagto() == 1 ? true : false);
				tgbVdaAutorizaDescontoExcedido
						.setSelected(entidadeBean.getVdaAutorizaDescontoExcedido() == 1 ? true : false);
				txtVdaMaxDescontoExcedido.setText(entidadeBean.getVdaMaxDescontoExcedido().toString());
				tgbVdaAutorizaMarkupMinimo.setSelected(entidadeBean.getVdaAutorizaMarkupMinimo() == 1 ? true : false);
				tgbVdaAutorizaDevolucao.setSelected(entidadeBean.getVdaAutorizaDevolucao() == 1 ? true : false);
				tgbVdaAutorizaClienteObsRestritiva
						.setSelected(entidadeBean.getVdaAutorizaClienteObsRestritiva() == 1 ? true : false);
				tgbVdaAutorizaClienteCadVencido
						.setSelected(entidadeBean.getVdaAutorizaClienteCadVencido() == 1 ? true : false);
				tgbVdaAutorizaOperacaoSaida.setSelected(entidadeBean.getVdaAutorizaOperacaoSaida() == 1 ? true : false);
				tgbVdaAutorizaPortadorDiferenteCliente
						.setSelected(entidadeBean.getVdaAutorizaPortadorDiferenteCliente() == 1 ? true : false);
				tgbVdaAutorizaDescontoPromocao
						.setSelected(entidadeBean.getVdaAutorizaDescontoPromocao() == 1 ? true : false);
				tgbVdaAutorizaClienteInativo
						.setSelected(entidadeBean.getVdaAutorizaClienteInativo() == 1 ? true : false);
				tgbVdaAutorizaAcresimo.setSelected(entidadeBean.getVdaAutorizaAcresimo() == 1 ? true : false);
				txtVdaMaxAcresimo.setText(entidadeBean.getVdaMaxAcresimo().toString());
				tgbVdaAutorizaPortadorRestrito
						.setSelected(entidadeBean.getVdaAutorizaPortadorRestrito() == 1 ? true : false);
				tgbVdaShowPrecoCusto.setSelected(entidadeBean.getVdaShowPrecoCusto() == 1 ? true : false);
				tgbVdaShowPrecoCusto.setSelected(entidadeBean.getVdaShowPrecoCusto() == 1 ? true : false);
				tgbVdaShowPrecoAtacado.setSelected(entidadeBean.getVdaShowPrecoAtacado() == 1 ? true : false);
				tgbVdaShowDebitoClientes.setSelected(entidadeBean.getVdaShowDebitoClientes() == 1 ? true : false);
				tgbVdaShowMargemLucro.setSelected(entidadeBean.getVdaShowMargemLucro() == 1 ? true : false);
				tgbVdaShowCreditoCliente.setSelected(entidadeBean.getVdaShowCreditoCliente() == 1 ? true : false);
				tgbVdaLiberacaoRemota.setSelected(entidadeBean.getVdaLiberacaoRemota() == 1 ? true : false);
				tgbVdaAcessaClientePrecoCusto
						.setSelected(entidadeBean.getVdaAcessaClientePrecoCusto() == 1 ? true : false);

				
				// ------------------------------- ABA FINANCEIRO -------------------------------------//
				// =============== TELA 1 ====================//
					tgBtnFinAltValorTitCtasRec.setSelected(entidadeBean.getFinAltValorCtasReceber() == 1 ? true : false);
					tgBtnFinAltDtVenctoTitCtasRec.setSelected(entidadeBean.getFinAltDtVenctoCtasReceber() == 1 ? true : false);
				// =============== FINAL TELA 1 ====================//
				// ------------------------------- FINAL ABA FINANCEIRO -------------------------------------//
				
				// SELECIONA AS EMPRESAS DISPONIVEIS PARA O USUARIO FAZER LOGIN
				String a[] = entidadeBean.getCodEmpDispLogin().split(",");
				cBoxEmpDispLogin.getCheckModel().clearChecks();
				for (int i = 0; i < cBoxEmpDispLogin.getItems().size(); i++) {

					for (int j = 0; j < a.length; j++) {
						if (cBoxEmpDispLogin.getItems().get(i).getField().equals(((a[j])))) {
							cBoxEmpDispLogin.getCheckModel().check(i);
						}
					}

				}

				tgBtnDefNiv.setSelected(entidadeBean.getActionDefNivAcesso() == 1 ? true : false);
				tgBtnCopyNivAcesso.setSelected(entidadeBean.getActionCopyNivAcesso() == 1 ? true : false);
				tgBtnCopyUsu.setSelected(entidadeBean.getActionCopyUsuario() == 1 ? true : false);

				tgBtnAltDtMovtoSistema.setSelected(entidadeBean.getConfFlagAltDtMovto() == 1 ? true : false);
				actionTgBtnAltDtMovtoSistema(null);
				txtLimDiasDtMovtoRetro.setText(entidadeBean.getConfDiasRetroDtMovto().toString());
				txtLimDiasDtMovtoAvan.setText(entidadeBean.getConfDiasAvanDtMovto().toString());

			} else {

				// util.showAlert("O código não foi encontrado.", "error");
				util.showAlert(DadosGlobais.resourceBundle.getString("alertErrorCodigoNull"), "error");
				btnSave.setDisable(true);
				btnCancel.setDisable(false);
				btnInsert.setDisable(false);
				txtCodigo.requestFocus();
				Util.limpar(txtNome, txtCodigo);
			}

		} else {
			util.showAlert("Código incorreto.", "error");
			Util.limpar(txtNome);
			txtCodigo.requestFocus();
		}

	}

	/**
	 * Método reponsavel por buscar dados na classe modelo
	 * 
	 * @param tipoConsulta
	 *            se traz todos os dados da tabela ou se aplica filtros a
	 *            consulta
	 * @param flagRefresh
	 *            caso seja True carrega os dados da consulta no Tableview da
	 *            View
	 * @return void
	 */
	public void listarUsuarios(String tipoConsulta, boolean flagRefresh) {

		// TIPOCONSULTA:
		// ALL(all) TRAS TODOS OS DADOS
		// FILTER(filter) FITRA A CONSULTA DE ACORDO COM OS VALORES PREENCHIDOS
		// NO FORMULARIO

		if (tipoConsulta.equals("all")) {

			// CONSULTA TODOS OS USUARIOS ATIVOS
			dataUsuario = FXCollections.observableArrayList(entidadeDao.getListUsuario(parameters));

		}

		else if (tipoConsulta.equals("filter"))

		{

			// CONSULTA COM FILTROS ATIVOS
			switch (cboxFlagAtivo.getValue().getAction()) {
			case 0:
				parameters = Arrays.asList(1);
				break;
			case 1:
				parameters = Arrays.asList(0);
				break;
			case 2:
				parameters = Arrays.asList(0, 1);
				break;
			default:
				break;
			}

			String parametroBusca = null;

			if (txtFilterColumn.getText().isEmpty()) {

				dataUsuario = FXCollections
						.observableArrayList(entidadeDao.filterByColumn(cboxFilterColumn.getValue().getField(),
								txtFilterColumn.getText(), cboxFilterColumn.getValue().getAction(), parameters));

			} else {

				if (cboxFilterColumn.getValue().getAction() == 1) {

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

				} else if (cboxFilterColumn.getValue().getAction() == 2) {
					parametroBusca = txtFilterColumn.getText();
				}

				dataUsuario = FXCollections
						.observableArrayList(entidadeDao.filterByColumn(cboxFilterColumn.getValue().getField(),
								parametroBusca, cboxFilterColumn.getValue().getAction(), parameters));
			}

		}

		// CASO A CONSULTA RETORNE ALGUM REGISTRO HABILITA O BOTAO DE
		// IMPRESSÃO
		if (dataUsuario.size() > 0)
			btnPrint.setDisable(false);
		else
			btnPrint.setDisable(true);

		// CASO O FLAG REFRESH SEJA TRUE RECARREGA O TABLEVIEW COM OS DADOS
		// DA
		// CONSULTA
		if (flagRefresh == true) {
			tbView.getItems().clear();
			tbView.getItems().setAll(dataUsuario);
		}

	}

	public void tarefaConsulta(String tipoConsulta, boolean flagRefresh) {
		Task<String> TarefaRefresh = new Task<String>() {

			@Override
			protected String call() throws Exception {

				listarUsuarios(tipoConsulta, flagRefresh);

				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				pBar.setProgress(1);
				numLinhas.setText(String.valueOf(tbView.getItems().size()));
				txtFilterColumn.requestFocus();
			}

			@Override
			protected void failed() {
				stg.close();
				// util.showAlert("Erro ao Conectar ao Banco de Dados, verifique
				// a conexão de rede!", "error");
				util.showAlert(DadosGlobais.resourceBundle.getString("alertErrorCnxDb"), "error");
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
		stg = ProgressBarForm.showProgressBar(UsuarioController.class, TarefaRefresh, "Consultando Registros", false);
		pBar.setProgress(-1);

	}

	public void listaMenu(ObservableList<?> m) {
		for (int i = 0; i < m.size(); i++) {
			if (m.get(i) instanceof Menu) {

				listMenu.add(((Menu) m.get(i)).getId());
				listaMenu(((Menu) m.get(i)).getItems());

			} else {

				listMenu.add(((MenuItem) m.get(i)).getId());

			}

		}
	}

	public boolean vericaUserRoot(String usuarioNome) {
		boolean retorno = false;

		if (usuarioNome.equals("SUPORTE EPTUS") && DadosGlobais.usuario.equals("SUPORTE EPTUS"))
			retorno = true;
		else if (!usuarioNome.equals("SUPORTE EPTUS"))
			retorno = true;

		return retorno;
	}

	public void resetaOpções() {
		cboxIdioma.getSelectionModel().selectFirst();
		cBoxEmpDispLogin.getCheckModel().clearChecks();

		tgBtnDefNiv.setSelected(false);
		tgBtnCopyNivAcesso.setSelected(false);
		tgBtnCopyUsu.setSelected(false);

		// ABA CONFIGURACAO
		// TELA 1 - PERGUNTAS 2 - 3
		tgBtnAltDtMovtoSistema.setSelected(false);
		txtLimDiasDtMovtoAvan.setText("0");
		txtLimDiasDtMovtoRetro.setText("0");
	}

	public List<NivelAcesso> copiaNiveisAcesso(Usuario usuario) {

		Task<String> tarefaCargaPg = new Task<String>() {

			NivelAcessoDAO nivDao = new NivelAcessoDAO();

			@Override
			protected String call() throws Exception {
				listaNiveisAcesso = nivDao.getListPermisao(usuario);
				return "-";

			}

			@Override
			protected void succeeded() {
				msgCopiaUsuario = "[" + usuario.getCodigo() + "] " + usuario.getNome();
				stg.close();
				pBar.setProgress(1);

			}

			@Override
			protected void failed() {
				stg.close();
				// util.showAlert("Erro ao Conectar ao Banco de Dados, verifique
				// a conexão de rede!", "error");
				util.showAlert(DadosGlobais.resourceBundle.getString("alertErrorCnxDb"), "error");
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
				"Copiando Acessos do Usuário", false);
		pBar.setProgress(-1);

		return listaNiveisAcesso;
	}

	public void colarNiveisAcesso(Usuario usuario) {

		Task<String> tarefaCargaPg = new Task<String>() {

			NivelAcessoDAO nivDao = new NivelAcessoDAO();
			List<NivelAcesso> listaNiveisAcesso2 = null;

			@Override
			protected String call() throws Exception {
				listaNiveisAcesso2 = nivDao.getListPermisao(usuario);

				nivDao.duplicaNivelAcesso(listaNiveisAcesso, listaNiveisAcesso2);
				return "-";

			}

			@Override
			protected void succeeded() {
				stg.close();
				pBar.setProgress(1);
				listaNiveisAcesso = null;
				listaNiveisAcesso2 = null;

			}

			@Override
			protected void failed() {
				stg.close();
				// util.showAlert("Erro ao Conectar ao Banco de Dados, verifique
				// a conexão de rede!", "error");
				util.showAlert(DadosGlobais.resourceBundle.getString("alertErrorCnxDb"), "error");
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
		stg = ProgressBarForm.showProgressBar(DepartamentoController.class, tarefaCargaPg, "Colando Acessos do Usuário",
				false);
		pBar.setProgress(-1);

	}

	public void copiaUsuario(int usuarioID, int codempUsuario) {

		Usuario usuarioClone = entidadeDao.getUserById(usuarioID, codempUsuario);

		// onChangeAba(1, false, true);
		btnInsert.setDisable(true);
		btnSave.setDisable(false);
		btnCancel.setDisable(false);
		txtCodigo.setDisable(true);
		txtCodigo.setText("+1");
		txtNome.clear();
		txtPwd.clear();
		txtPwdConf.clear();
		txtNome.requestFocus();

		carregaDadosDetalhe(false, usuarioID, false, true);
	}

	// FINAL <<--------- SEÇAO COM MÉTODOS DA CLASSE ---------->>

	// INICIO <<------- SEÇAO COM MÉTODOS RESPONSÁVEIS PELA CONFIGURAÇÃO E
	// PERSONALIZAÇÃO DO TABLEVIEW ----->>
	@SuppressWarnings("rawtypes")
	/**
	 * Save columns setting defined by user in the file corresponding to this
	 * user.
	 */
	public void saveColumnSettings() {

		XMLTableColumns xml = new XMLTableColumns();
		ArrayList<UserDefinedColumn> userDefinedColumnList = new ArrayList<UserDefinedColumn>();

		int i = 0;
		for (TableColumn col : tbView.getVisibleLeafColumns()) {
			userDefinedColumnList.add(new UserDefinedColumn(col.getId(), col.getText(), i, col.getWidth()));
			i++;

		}

		xml.WriteXMLColumns(userDefinedColumnList, "Grid-Cad-Usuario");

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	/**
	 * Method to configure the columns and define their position defined by
	 * user.
	 * 
	 * @param visibleColumns
	 *            List of columns defined by user.
	 * @param table
	 *            Instance of the department tableView.
	 */
	public void updateTableView(ObservableList visibleColumns, TableView<Usuario> table) {

		if (!visibleColumns.isEmpty()) {
			// hide all columns
			for (TableColumn<Usuario, ?> col : table.getColumns())
				col.setVisible(false);
			// show column and defines its position
			for (int i = 0; i < visibleColumns.size(); i++) {
				table.getColumns().remove(visibleColumns.get(i));
				table.getColumns().add(i, (TableColumn<Usuario, ?>) visibleColumns.get(i));
				table.getColumns().get(i).setVisible(true);
			}

		}

	}

	@SuppressWarnings({ "rawtypes" })
	/**
	 * Configure the columns and define columns properties, those columns are
	 * stored in a file.
	 */
	public void configTableView() {
		XMLTableColumns xmlTableColumns = new XMLTableColumns();

		ArrayList<UserDefinedColumn> userDefinedColumns = xmlTableColumns.ReadXMLColumns("Grid-Cad-Usuario");

		if (userDefinedColumns != null) {

			for (TableColumn col : tbView.getColumns())
				col.setVisible(false);

			for (int i = 0; i < userDefinedColumns.size(); i++) {
				for (int j = 0; j < tbView.getColumns().size(); j++) {
					if (tbView.getColumns().get(j).getId().equals(userDefinedColumns.get(i).getName())) {
						TableColumn<Usuario, ?> tableColumn = tbView.getColumns().get(j);
						tbView.getColumns().remove(j);
						tbView.getColumns().add(userDefinedColumns.get(i).getPosition(), tableColumn);
						tableColumn.setPrefWidth(userDefinedColumns.get(i).getWidth());
						tableColumn.setVisible(true);
					}
				}
			}
		}
	}
	// FINAL <<----- SEÇAO COM MÉTODOS RESPONSÁVEIS PELA CONFIGURAÇÃO E
	// PERSONALIZAÇÃO DO TABLEVIEW -------->>

	// INICIO <<------ SEÇAO COM MÉTODO RESPONSÁVEL PELA IMPRESSÃO DA LISTAGEM
	// DO TABLEVIEW ----->>
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

			Object type = Usuario.class
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
					new PrintExportController(tbView, tableShowPrintList, "Usuários", pBar, stg)));
			stg.setScene(scene);
			stg.initStyle(StageStyle.TRANSPARENT);
			stg.initModality(Modality.APPLICATION_MODAL);
			stg.show();
		} catch (IOException e) {
			//
			e.printStackTrace();
		}

	}
	// FINAL <<---- SEÇAO COM MÉTODO RESPONSÁVEL PELA IMPRESSÃO DA LISTAGEM DO
	// TABLEVIEW ---->>

	// INICIO <<---- SEÇAO COM MÉTODO RESPONSÁVEL POR POPULAR OS COMBOBOX
	// EXISTENTES NO FORMULÁRIO FXML ----->>
	/**
	 * Fill comboBox cbkFilterColumn and property definitions
	 */
	public void populaCboxFilterColumn() {
		// 2 contido, 1 exato
		ObservableList<ComboBoxFilter> listColunas = FXCollections.observableArrayList();
		listColunas.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("codigo"), 1, "codigo"));
		listColunas.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("descricao"), 2, "nome"));

		cboxFilterColumn.getItems().addAll(listColunas);
		cboxFilterColumn.getSelectionModel().selectLast();

		cboxFilterColumn.setConverter(new StringConverter<ComboBoxFilter>() {
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
	 * Fill comboBox cboxIdioma and property definitions
	 */
	public void populaCboxIdioma() {

		ObservableList<ComboBoxFilter> listIdiomas = FXCollections.observableArrayList();

		listIdiomas.add(new ComboBoxFilter("Português", 1, "PT"));
		listIdiomas.add(new ComboBoxFilter("Espanhol", 1, "ES"));
		listIdiomas.add(new ComboBoxFilter("Inglês", 1, "EN"));

		cboxIdioma.getItems().addAll(listIdiomas);
		cboxIdioma.getSelectionModel().selectFirst();

		cboxIdioma.setConverter(new StringConverter<ComboBoxFilter>() {
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

	public void populaCboxFlagAtivo() {

		ObservableList<ComboBoxFilter> listFlagAtivo = FXCollections.observableArrayList();

		listFlagAtivo.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("ativo"), 0, ""));
		listFlagAtivo.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("inativo"), 1, ""));
		listFlagAtivo.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("inativo"), 2, ""));
		cboxFlagAtivo.getItems().addAll(listFlagAtivo);
		cboxFlagAtivo.getSelectionModel().selectFirst();
		cboxFlagAtivo.setConverter(new StringConverter<ComboBoxFilter>() {
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
	 * ComboBox Tipo Operacion.
	 */
	public void populaCboxEmpDispLogin() {

		// ArrayList to fill comboBox cbkTipoOperacion
		ObservableList<ComboBoxFilter> empresas = FXCollections.observableArrayList();

		EmpresaDAO o_empDao = new EmpresaDAO();

		List<Empresa> listEmpresa = o_empDao.getListEmpresa();

		for (int i = 0; i < listEmpresa.size(); i++) {
			empresas.add(new ComboBoxFilter((listEmpresa.get(i).getNomeFantasia()), 1,
					(listEmpresa.get(i).getCodigo().toString())));
		}

		cBoxEmpDispLogin.getItems().addAll(empresas);

		cBoxEmpDispLogin.setConverter(new StringConverter<ComboBoxFilter>() {
			@Override
			public String toString(ComboBoxFilter object) {
				// TODO Auto-generated method stub
				if (object != null) {
					return object.getValue();
				} else {
					return "";
				}
			}

			@Override
			public ComboBoxFilter fromString(String string) {
				// TODO Auto-generated method stub
				return null;
			}
		});
	}
	// INICIO <<---- SEÇAO COM MÉTODO RESPONSÁVEL POR POPULAR OS COMBOBOX
	// EXISTENTES NO FORMULÁRIO FXML ---->>

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
		toggleBtnHelp.setTooltip(new Tooltip(DadosGlobais.resourceBundle.getString("toolTipHelp")));
		btnInsert.setTooltip(new Tooltip(DadosGlobais.resourceBundle.getString("toolTipInsert")));
		btnSave.setTooltip(new Tooltip(DadosGlobais.resourceBundle.getString("toolTipSave")));
		btnCancel.setTooltip(new Tooltip(DadosGlobais.resourceBundle.getString("toolTipCancel")));

		lblCboxFilterColumn.setText(DadosGlobais.resourceBundle.getString("lblCboxFilterColumn"));
		lblCboxFlagAtivo.setText(DadosGlobais.resourceBundle.getString("lblcboxFlagAtivo"));
		lblTotalLinhas.setText(DadosGlobais.resourceBundle.getString("lblTotalLinhas"));

		lbTitleFormCadUsuario.setText(DadosGlobais.resourceBundle.getString("lblTitleFormCad") + " "
				+ DadosGlobais.resourceBundle.getString("usuarioController.lblTitleFormCad"));

		lblCodigo.setText(DadosGlobais.resourceBundle.getString("lblDetCodigo"));
		lblNome.setText(DadosGlobais.resourceBundle.getString("usuarioController.lblNome"));
		lblPwd.setText(DadosGlobais.resourceBundle.getString("usuarioController.lblPwd"));
		lblPwdConf.setText(DadosGlobais.resourceBundle.getString("usuarioController.lblPwdConf"));
		lblIdioma.setText(DadosGlobais.resourceBundle.getString("usuarioController.lblIdioma"));

		tbColCodigo.setText(DadosGlobais.resourceBundle.getString("tbColCodigo"));
		tbColDescricao.setText(DadosGlobais.resourceBundle.getString("usuarioController.lblNome"));
		tbColCodemp.setText(DadosGlobais.resourceBundle.getString("usuarioController.tbColCodemp"));
		tbColIdioma.setText(DadosGlobais.resourceBundle.getString("usuarioController.lblIdioma"));
		tbColAtivoInat.setText(DadosGlobais.resourceBundle.getString("tbColAtivoInat"));

		tbView.setPlaceholder(new Label(DadosGlobais.resourceBundle.getString("tbView")));

		//TAB COMPRAS
		tabCompras.setText(DadosGlobais.resourceBundle.getString("usuarioController.tabCompras"));
		
		//TAB VENDAS
		tabVendas.setText(DadosGlobais.resourceBundle.getString("usuarioController.tabVendas"));
		lblVdaAltObsRestritiva.setText(DadosGlobais.resourceBundle.getString("usuarioController.lblVdaAltObsRestritiva"));
		lblVdaAltLimiteCredito.setText(DadosGlobais.resourceBundle.getString("usuarioController.lblVdaAltLimiteCredito"));
		lblVdaMaxLimiteCredito.setText(DadosGlobais.resourceBundle.getString("usuarioController.lblVdaMaxLimiteCredito"));
		lblVdaFaturamentoAltTipo.setText(DadosGlobais.resourceBundle.getString("usuarioController.lblVdaFaturamentoAltTipo"));
		lblVdaAltVendedorPadrao.setText(DadosGlobais.resourceBundle.getString("usuarioController.lblVdaAltVendedorPadrao"));
		lblVdaFaturamentoAltConvenio.setText(DadosGlobais.resourceBundle.getString("usuarioController.lblVdaFaturamentoAltConvenio"));
		lblVdaFaturamentoAltTipoPreco.setText(DadosGlobais.resourceBundle.getString("usuarioController.lblVdaFaturamentoAltTipoPreco"));
		lblVdaAltDadosProtesto.setText(DadosGlobais.resourceBundle.getString("usuarioController.lblVdaAltDadosProtesto"));
		lblVdaAltCpfCnpj.setText(DadosGlobais.resourceBundle.getString("usuarioController.lblVdaAltCpfCnpj"));
		lblVdaCliAltConsultProposta.setText(DadosGlobais.resourceBundle.getString("usuarioController.lblVdaCliAltConsultProposta"));
		lblVdaVenAltConsultProposta.setText(DadosGlobais.resourceBundle.getString("usuarioController.lblVdaVenAltConsultProposta"));
		lblVdaAutorizaCpfDuplicado.setText(DadosGlobais.resourceBundle.getString("usuarioController.lblVdaAutorizaCpfDuplicado"));
		lblVdaAutorizaCpfZerado.setText(DadosGlobais.resourceBundle.getString("usuarioController.lblVdaAutorizaCpfZerado"));
		lblVdaAutorizaClienteInadimplente.setText(DadosGlobais.resourceBundle.getString("usuarioController.lblVdaAutorizaClienteInadimplente"));
		lblVdaAutorizaLimiteExedido.setText(DadosGlobais.resourceBundle.getString("usuarioController.lblVdaAutorizaLimiteExedido"));
		lblVdaMaxLimiteExedido.setText(DadosGlobais.resourceBundle.getString("usuarioController.lblVdaMaxLimiteExedido"));
		lblVdaAutorizaEstoqueNegativo.setText(DadosGlobais.resourceBundle.getString("usuarioController.lblVdaAutorizaEstoqueNegativo"));
		lblVdaAutorizaPlanoPagto.setText(DadosGlobais.resourceBundle.getString("usuarioController.lblVdaAutorizaPlanoPagto"));
		lblVdaAutorizaDescontoExcedido.setText(DadosGlobais.resourceBundle.getString("usuarioController.lblVdaAutorizaDescontoExcedido"));
		lblVdaMaxDescontoExcedido.setText(DadosGlobais.resourceBundle.getString("usuarioController.lblVdaMaxDescontoExcedido"));
		lblVdaAutorizaMarkupMinimo.setText(DadosGlobais.resourceBundle.getString("usuarioController.lblVdaAutorizaMarkupMinimo"));
		lblVdaAutorizaDevolucao.setText(DadosGlobais.resourceBundle.getString("usuarioController.lblVdaAutorizaDevolucao"));
		lblVdaAutorizaClienteObsRestritiva.setText(DadosGlobais.resourceBundle.getString("usuarioController.lblVdaAutorizaClienteObsRestritiva"));
		lblVdaAutorizaClienteCadVencido.setText(DadosGlobais.resourceBundle.getString("usuarioController.lblVdaAutorizaClienteCadVencido"));
		lblVdaAutorizaOperacaoSaida.setText(DadosGlobais.resourceBundle.getString("usuarioController.lblVdaAutorizaOperacaoSaida"));
		lblVdaAutorizaPortadorDiferenteCliente.setText(DadosGlobais.resourceBundle.getString("usuarioController.lblVdaAutorizaPortadorDiferenteCliente"));
		lblVdaAutorizaDescontoPromocao.setText(DadosGlobais.resourceBundle.getString("usuarioController.lblVdaAutorizaDescontoPromocao"));
		lblVdaAutorizaClienteInativo.setText(DadosGlobais.resourceBundle.getString("usuarioController.lblVdaAutorizaClienteInativo"));
		lblVdaAutorizaAcresimo.setText(DadosGlobais.resourceBundle.getString("usuarioController.lblVdaAutorizaAcresimo"));
		lblVdaMaxAcresimo.setText(DadosGlobais.resourceBundle.getString("usuarioController.lblVdaMaxAcresimo"));
		lblVdaAutorizaPortadorRestrito.setText(DadosGlobais.resourceBundle.getString("usuarioController.lblVdaAutorizaPortadorRestrito"));
		lblVdaShowPrecoCusto.setText(DadosGlobais.resourceBundle.getString("usuarioController.lblVdaShowPrecoCusto"));
		lblVdaShowPrecoAtacado.setText(DadosGlobais.resourceBundle.getString("usuarioController.lblVdaShowPrecoAtacado"));
		lblVdaShowDebitoClientes.setText(DadosGlobais.resourceBundle.getString("usuarioController.lblVdaShowDebitoClientes"));
		lblVdaShowMargemLucro.setText(DadosGlobais.resourceBundle.getString("usuarioController.lblVdaShowMargemLucro"));
		lblVdaShowCreditoCliente.setText(DadosGlobais.resourceBundle.getString("usuarioController.lblVdaShowCreditoCliente"));
		lblVdaLiberacaoRemota.setText(DadosGlobais.resourceBundle.getString("usuarioController.lblVdaLiberacaoRemota"));
		lblVdaAcessaClientePrecoCusto.setText(DadosGlobais.resourceBundle.getString("usuarioController.lblVdaAcessaClientePrecoCusto"));	
		
		//TAB FINANCEIRO
		tabFinanceiro.setText(DadosGlobais.resourceBundle.getString("usuarioController.tabFinanceiro"));
		
		//TAB LIVROS FISCALES
		tabLivrosFiscales.setText(DadosGlobais.resourceBundle.getString("usuarioController.tabLivrosFiscales"));
		
		//TAB RECURSOS HUMANOS
		tabRecursosH.setText(DadosGlobais.resourceBundle.getString("usuarioController.tabRecursosH"));
		
		//TAB SERVICOS 
		tabServicos.setText(DadosGlobais.resourceBundle.getString("usuarioController.tabServicos"));
		
		//TAB PRODUCAO
		tabProducao.setText(DadosGlobais.resourceBundle.getString("usuarioController.tabProducao"));
		
		//TAB CONTABILIDADE
		tabContabilidade.setText(DadosGlobais.resourceBundle.getString("usuarioController.tabContabilidade"));
		
		//TAB CONFIGURACOES
		tabConfiguracoes.setText(DadosGlobais.resourceBundle.getString("usuarioController.tabConfiguracoes"));
		
		//TAB PARAMETROS GERAIS
		tabParamGerais.setText(DadosGlobais.resourceBundle.getString("usuarioController.tabParamGerais"));
		lblCodFuncionario.setText(DadosGlobais.resourceBundle.getString("usuarioController.lblCodFuncionario"));
		
		
		
		
		
	}

	/// INICIAL
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		anchorPaneListagem.setVisible(true);
		anchorPaneDetalhes.setVisible(false);
		anchorPaneNivelAcesso.setVisible(false);

		// AnchorPane.bottomAnchor="50.0" AnchorPane.leftAnchor="20.0"
		// AnchorPane.rightAnchor="20.0" AnchorPane.topAnchor="100.0">

		// Label l = new Label("Tela 1");
		// l.setRotate(90);
		// StackPane stp = new StackPane(new Group(l));
		// stp.setRotate(90);
		// tabComprasTela1.setGraphic(stp);
		//
		// Label l2 = new Label("Tela 2");
		// l2.setRotate(90);
		// StackPane stp2 = new StackPane(new Group(l2));
		// stp2.setRotate(90);
		// tabComprasTela2.setGraphic(stp2);
		//
		// tabPaneCompras.setTabMinHeight(60);
		// tabPaneCompras.setTabMaxHeight(60);
		//
		// Label l12 = new Label("Tela 1");
		// l.setRotate(90);
		// StackPane stp12 = new StackPane(new Group(l));
		// stp12.setRotate(90);
		// tabConfigTela1.setGraphic(stp12);
		//
		// Label l22 = new Label("Ações");
		// l22.setRotate(90);
		// StackPane stp22 = new StackPane(new Group(l22));
		// stp22.setRotate(90);
		// tabConfigAcoes.setGraphic(stp22);
		//
		// tabPaneConfig.setTabMinHeight(60);
		// tabPaneConfig.setTabMaxHeight(60);

		actionToggleButtons();

		// Iniciar o TableView com um usuario Nulo para corrigir problema de
		// itens inativos no grid

		Usuario userNull = new Usuario();
		tbView.getItems().addAll(userNull);

		// Associar a coluna do TableView ao Atributo da Classe Modelo Usuario
		tbColCodigo.setCellValueFactory(new PropertyValueFactory<Usuario, Integer>("codigo"));
		tbColDescricao.setCellValueFactory(new PropertyValueFactory<Usuario, String>("nome"));
		tbColCodemp.setCellValueFactory(new PropertyValueFactory<Usuario, Integer>("codemp"));
		tbColAtivoInat.setCellValueFactory(new PropertyValueFactory<Usuario, Integer>("flagAtivo"));
		tbColIdioma.setCellValueFactory(new PropertyValueFactory<Usuario, String>("idioma"));

		Util.defaultStyle(txtNome, txtCodigo, txtPwd, txtPwdConf, txtNomeGrid, txtFilterColumn);

		// DEFINE OS TEXTFIELDS QUE SERAO MAIUSCULO
		Util.whriteUppercase(txtNome, txtNomeGrid, txtFilterColumn);

		// carrega dados nos combobox da aba de filtros
		populaCboxFilterColumn();
		populaCboxFlagAtivo();
		populaCboxIdioma();
		populaCboxEmpDispLogin();

		// Chamada a funçao configTableView que configura as colunas do Grid
		configTableView();

		translations();

		// Set Style Tabs
		Util.setStyleTab(tabPaneCompras, tabPaneVendas, tabPaneConfig);
		
		Util.decimalBR(4, txtVdaMaxLimiteCredito, txtVdaMaxDescontoExcedido, txtVdaMaxAcresimo);

		// SETA O FOCO INICIAL NO TEXTFIELD DA ABA DE FILTROS
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				txtFilterColumn.requestFocus();
			}
		});

		// EVENTOS NO TABLEVIEW -->>>> INICIO

		// Grava as alterações feitas nas colunas do grid ao clicar e arrastar
		tbView.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
			if (event.getTarget() instanceof TableHeaderRow || event.getTarget() instanceof Rectangle)
				saveColumnSettings();
		});

		// EVENTO PARA CONTROLAR A LINHA SELECIONA NO TABLEVIEW
		tbView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				if (tbView.getItems().get(0).getCodigo() != null) {
					if (tbView.getSelectionModel().getSelectedItem().getFlagAtivo() == 1) {

						txtNomeGrid.setText(tbView.getSelectionModel().getSelectedItem().getNome());

						btnDelete.setDisable(false);
						txtNomeGrid.setDisable(false);

						btnSaveGrid.setDisable(true);
						txtPwdGrid.setDisable(true);
						txtPwdConfGrid.setDisable(true);

						Util.limpar(txtPwdGrid, txtPwdConfGrid);

						FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
						icon.setSize("16");
						icon.setId("icons");
						btnDelete.setGraphic(icon);
						btnDelete.setTooltip(new Tooltip(DadosGlobais.resourceBundle.getString("toolTipExcluir")));
						entidadeBean = tbView.getSelectionModel().getSelectedItem();
						// usuario.setCodemp(tbView.getSelectionModel().getSelectedItem().getCodemp());
						// usuario.setRecordNo(tbView.getSelectionModel().getSelectedItem().getRecordNo());
						// usuario.setCodigo(tbView.getSelectionModel().getSelectedItem().getCodigo());
						// usuario.setPwd(tbView.getSelectionModel().getSelectedItem().getPwd());
						// usuario.setNome(tbView.getSelectionModel().getSelectedItem().getNome());
						// usuario.setIdioma(tbView.getSelectionModel().getSelectedItem().getIdioma());
						// usuario.setCheckDelete(tbView.getSelectionModel().getSelectedItem().getCheckDelete());

					} else if (tbView.getItems().get(0).getCodigo() != null
							&& tbView.getSelectionModel().getSelectedItem().getFlagAtivo() == 0) {

						btnDelete.setDisable(false);
						txtNomeGrid.setDisable(true);

						btnSaveGrid.setDisable(true);
						txtPwdGrid.setDisable(true);
						txtPwdConfGrid.setDisable(true);

						Util.limpar(txtNomeGrid, txtPwdGrid, txtPwdConfGrid);

						FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CHECK_SQUARE_ALT);
						icon.setSize("16");
						icon.setId("icons");
						btnDelete.setGraphic(icon);
						btnDelete.setTooltip(new Tooltip(DadosGlobais.resourceBundle.getString("tooltipAtivar")));
					}

				}

			}
		});

		// CONTROLA O EVENTO DE ONFOCUS DO TABLEVIEW
		tbView.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (!newPropertyValue) {
					if (!txtNomeGrid.isFocused() && !txtPwdGrid.isFocused() && !txtPwdConfGrid.isFocused()) {
						txtNomeGrid.clear();
						txtPwdGrid.clear();
						txtPwdConfGrid.clear();
						txtNomeGrid.setDisable(true);
					}

					if (!btnDelete.isFocused())
						btnDelete.setDisable(true);
				}
			}
		});

		tbView.setRowFactory(tb -> {
			TableRow<Usuario> row = new TableRow<Usuario>() {
				@Override
				public void updateItem(Usuario usuario, boolean empty) {
					super.updateItem(usuario, empty);
					if (usuario == null) {
						setStyle("");
					} else {
						for (int i = 0; i < getChildren().size(); i++) {
							if (usuario.getFlagAtivo() != null) {
								if (usuario.getFlagAtivo() == 0)
									((Labeled) getChildren().get(i)).setTextFill(Color.web("#bdbdbd"));
								else
									((Labeled) getChildren().get(i)).setTextFill(Color.BLACK);
							}

						}
					}
				}
			};

			// EVENTO CLICK DO MOUSE EM UMA LINHA DO GRID
			row.setOnMouseClicked(tr -> {

				// EVENTO DOIS CLIQUES NA LINHA DO TABLEVIEW, SELECIONA
				// O REGISTRO PARA SER EDITADO NA ABA DETALHES
				if (tr.getClickCount() >= 2 && (!row.isEmpty()) && tr.getButton().equals(MouseButton.PRIMARY)
						&& tbView.getItems().get(0).getCodigo() != null) {
					if (tbView.getSelectionModel().getSelectedItem().getFlagAtivo() == 1) {
						flagBtnInsert = true;

						btnInsert.setDisable(true);
						btnSave.setDisable(false);
						btnCancel.setDisable(false);
						txtCodigo.setDisable(true);
						txtCodigo.setText("+1");
						txtNome.clear();
						txtPwd.clear();
						txtPwdConf.clear();
						txtNome.requestFocus();

						txtCodigo.setText(Integer.toString(tbView.getSelectionModel().getSelectedItem().getCodigo()));
						carregaDadosDetalhe(false, tbView.getSelectionModel().getSelectedItem().getCodigo(), true,
								false);

						anchorPaneDetalhes.setVisible(true);
					}

				}

				if (tr.getButton().equals(MouseButton.SECONDARY)) {

					contextMenu = new ContextMenu();
					// SELECIONA O REGISTRO PARA SER EDITADO NA ABA DETALHES
					MenuItem itemAtualizar = new MenuItem(DadosGlobais.resourceBundle.getString("actionAtualizar"));
					itemAtualizar.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							flagBtnInsert = true;
							// onChangeAba(1, false, flagBtnInsert);
							btnInsert.setDisable(true);
							btnSave.setDisable(false);
							btnCancel.setDisable(false);
							txtCodigo.setDisable(true);
							txtCodigo.setText("+1");
							txtNome.clear();
							txtPwd.clear();
							txtPwdConf.clear();
							txtNome.requestFocus();

							txtCodigo.setText(
									Integer.toString(tbView.getSelectionModel().getSelectedItem().getCodigo()));
							carregaDadosDetalhe(false, tbView.getSelectionModel().getSelectedItem().getCodigo(), true,
									false);

							anchorPaneDetalhes.setVisible(true);
						}
					});

					// EXCLUI O REGISTRO SELECIONADO
					MenuItem itemExcluir = new MenuItem(DadosGlobais.resourceBundle.getString("actionExcluir"));
					itemExcluir.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent e) {
							actionBtnDelete(null);
						}
					});

					// ABRE A JANELA DE AUDITORIA PARA RASTREAR OS LOGS DO
					// USUARIO SELECIONADO
					MenuItem itemRastreaLog = new MenuItem(DadosGlobais.resourceBundle.getString("actionRastrearLogs"));
					itemRastreaLog.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent e) {
							try {
								if (nivAcessoPermAuditoria.getFlagView() == 1) {
									util.crearTab(tabPrincipal, DadosGlobais.resourceBundle.getString("tabAuditoria"),
											"/views/configuracoes/viewAuditoria.fxml",
											new AuditoriaController(entidadeBean,
													tbView.getSelectionModel().getSelectedItem().getCodigo()),
											false);
								} else {
									util.showAlert("Nao", "warning");
								}
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					});

					// ABRE A JANELA DE AUDITORIA PARA RASTREAR OS LOGS DO
					// USUARIO SELECIONADO
					MenuItem itemDefNivAcesso = new MenuItem(
							DadosGlobais.resourceBundle.getString("actionDefinirNivelAcesso"));
					itemDefNivAcesso.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent e) {

							if (DadosGlobais.usuarioLogado.getActionDefNivAcesso() == 1) {

								Util.openFormCadastro(anchorPaneListagem, anchorPaneNivelAcesso, 0);
								try {
									f = new FXMLLoader(getClass().getResource("/views/configuracoes/NivelAcesso.fxml"));
									f.setController(new NivelesAcessoController(menuPrincipal, entidadeBean));
									if (hboxFormNivelAcesso.getChildren().size() > 0) {
										hboxFormNivelAcesso.getChildren().remove(0,
												hboxFormNivelAcesso.getChildren().size());
									}
									hboxFormNivelAcesso.getChildren().add(f.load());
									btnSaveNivelAcesso.setDisable(false);
									// tabPane.getTabs().get(2).setContent(f.load());
									// tabPane.getTabs().get(2).setDisable(false);
									// tabPane.getSelectionModel().select(2);
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							} else {
								util.showAlert("Nao", "warning");
							}
						}
					});

					MenuItem itemCopyNivAcesso = new MenuItem(
							DadosGlobais.resourceBundle.getString("actionCopiarNivelAcesso"));
					itemCopyNivAcesso.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent e) {
							try {

								copiaNiveisAcesso(entidadeBean);

							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					});

					MenuItem itemColaNivAcesso = new MenuItem(
							DadosGlobais.resourceBundle.getString("actionColarNivelAcesso"));
					itemColaNivAcesso.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent e) {
							try {

								if (util.showAlert(
										DadosGlobais.resourceBundle.getString("actionCopiarNivelAcesso") + ": \n"
												+ msgCopiaUsuario + " --> ["
												+ tbView.getSelectionModel().getSelectedItem().getCodigo() + "] "
												+ tbView.getSelectionModel().getSelectedItem().getNome(),
										"confirmation"))
									colarNiveisAcesso(entidadeBean);
								else
									listaNiveisAcesso = null;

							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					});

					MenuItem itemCopyUsuario = new MenuItem(
							DadosGlobais.resourceBundle.getString("actionCopiarUsuario"));
					itemCopyUsuario.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent e) {
							try {

								copiaUsuario(tbView.getSelectionModel().getSelectedItem().getCodigo(),
										tbView.getSelectionModel().getSelectedItem().getCodemp());

							} catch (Exception e1) {

								e1.printStackTrace();
							}
						}
					});

					// DESABILITAR O MENU DE ALTERACAO CASO O REGISTRO ESTEJA
					// FLAG INATIVO = 0
					if (tbView.getSelectionModel().getSelectedItem().getFlagAtivo() == 0) {
						itemAtualizar.setDisable(true);
						itemExcluir.setText(DadosGlobais.resourceBundle.getString("tooltipAtivar"));
					}

					if (listaNiveisAcesso != null) {
						itemCopyNivAcesso.setDisable(true);
						itemColaNivAcesso.setDisable(false);
					} else {
						itemCopyNivAcesso.setDisable(false);
						itemColaNivAcesso.setDisable(true);
					}

					// DESABILITA AS ACOES CASO USUARIO NAO TENHA PERMISSAO PARA
					// AS ACOES
					itemDefNivAcesso.setDisable(DadosGlobais.usuarioLogado.getActionDefNivAcesso() == 0 ? true : false);
					itemCopyNivAcesso
							.setDisable(DadosGlobais.usuarioLogado.getActionCopyNivAcesso() == 0 ? true : false);
					itemColaNivAcesso
							.setDisable(DadosGlobais.usuarioLogado.getActionCopyNivAcesso() == 0 ? true : false);
					itemCopyUsuario.setDisable(DadosGlobais.usuarioLogado.getActionCopyUsuario() == 0 ? true : false);

					contextMenu.getItems().addAll(itemAtualizar, itemExcluir, itemRastreaLog, itemDefNivAcesso,
							itemCopyNivAcesso, itemColaNivAcesso, itemCopyUsuario);
					tbView.setContextMenu(contextMenu);
				}
			});

			return row;
		});

		// FINAL <<----------- EVENTOS NO TABLEVIEW --------->> FINAL

		// INICIALIZA A ABA DO TABPANE SELECIONADA COMO 1
		// tabPane.getSelectionModel().selectedIndexProperty().addListener(new
		// ChangeListener<Number>() {
		// @Override
		// public void changed(ObservableValue<? extends Number> ov, Number
		// oldValue, Number newValue) {
		// boolean t=true;
		// if((oldValue.toString().equals("2")))
		// t = util.showAlert("Deseja Sair?","confirmation");
		//
		// if(t){
		//
		// onChangeAba((Integer) newValue, true, flagBtnInsert);
		// flagBtnInsert = false;
		// }else{
		// tabPane.getSelectionModel().select(2);
		// }
		// }
		// });

		// EVENTO DE VALIDACAO DAS SENHAS DIGITADAS AO PERDER O FOCO DO ELEMENTO
		// TXTFIELD TXTPWDCONF
		txtPwdConf.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (!newPropertyValue) {
					if (!txtPwd.getText().equals(txtPwdConf.getText())) {
						Util.limpar(txtPwd, txtPwdConf);
						// util.showAlert("Senhas digitadas não conferem!",
						// "warning");
						util.showAlert(DadosGlobais.resourceBundle.getString("usuarioController.alertValidaSenha"),
								"warning");
						txtPwd.requestFocus();
					}
				}
			}

		});

		// EVENTO DE VALIDACAO DAS SENHAS DIGITADAS AO PERDER O FOCO DO ELEMENTO
		// TXTFIELD TXTPWDCONF
		txtPwdConfGrid.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (!newPropertyValue) {
					if (!txtPwdGrid.getText().equals(txtPwdConfGrid.getText())) {
						Util.limpar(txtPwdGrid, txtPwdConfGrid);
						// util.showAlert("Senhas digitadas não conferem!",
						// "warning");
						util.showAlert(DadosGlobais.resourceBundle.getString("usuarioController.alertValidaSenha"),
								"warning");
						txtPwdGrid.requestFocus();
					}
				}
			}

		});

		// EVENTO DE VALIDACAO DAS SENHAS DIGITADAS AO PERDER O FOCO DO ELEMENTO
		// TXTFIELD TXTPWDCONFGRID
		txtPwdConfGrid.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (!newPropertyValue) {
					if (!txtPwdGrid.getText().equals(txtPwdConfGrid.getText())) {
						Util.limpar(txtPwdGrid, txtPwdConfGrid);
						// util.showAlert("Senhas digitadas não conferem!",
						// "warning");
						util.showAlert(DadosGlobais.resourceBundle.getString("usuarioController.alertValidaSenha"),
								"warning");
						txtPwdGrid.requestFocus();
					}
				}
			}

		});

		// EVENTO DE CARREGA O CADASTRO DO USARIO AO SAIR DO CAMPO CODIGO
		txtCodigo.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {

				if (!newPropertyValue) {

					if (!txtCodigo.getText().isEmpty() && !txtCodigo.isDisable()) {

						// if (tabPane.getSelectionModel().getSelectedIndex() ==
						// 1) {

						if (!btnInsert.isPressed()) {
							carregaDadosDetalhe(false, Integer.valueOf(txtCodigo.getText()), true, false);
						}
						// }
					}
				}
			}

		});

		// EVENTOS DOS RADIOBUTTONS DAS OPCOES DO USUARIO
		// ABA CONFIGURACOES

		// TECLAS DE ATALHOS PARA TOOLBAR ====> INICIO
		anchorPanePrincipal.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			switch (event.getCode()) {
			case F2:
				if (anchorPaneDetalhes.isVisible()) {
					flagPaneFilter = false;
					txtCodigo.setDisable(true);
					actionBtnCancel(null);
					// actionBtnFilter(null);
				} else {
					flagPaneFilter = false;
					actionBtnFilter(null);
				}
				break;
			case F4:
				if (!anchorPaneDetalhes.isVisible() && !anchorPaneNivelAcesso.isVisible()) {
					actionBtnRefresh(null);
				}
				break;
			case F5:
				if (anchorPaneDetalhes.isVisible())
					actionBtnInsert(null);
				else if (!anchorPaneNivelAcesso.isVisible() && !btnInsertGrid.isDisabled())
					actionBtnInsertGrid(null);
				break;

			case F6:
				if (!btnSave.isDisable() && anchorPaneDetalhes.isVisible())
					actionBtnSave(null);
				break;

			case F8:
				if (!btnCancel.isDisable() && anchorPaneDetalhes.isVisible())
					actionBtnCancel(null);
				break;

			case F7:
				if (!btnDelete.isDisable() && !anchorPaneListagem.isVisible() && !anchorPaneNivelAcesso.isVisible())
					actionBtnDelete(null);
				break;

			case P:
				if (event.isControlDown())
					try {
						if (!btnPrint.isDisable() && !anchorPaneListagem.isVisible()
								&& !anchorPaneNivelAcesso.isVisible())
							actionBtnPrint(null);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				break;

			default:
				break;
			}
		});
		// TECLAS DE ATALHOS PARA TOOLBAR ====> FINAL

		// ----------------------------

		double sbWidth = ((this.tabPrincipal.getWidth() - 220) - txtSearch.getWidth());
		txtSearch.setPromptText(DadosGlobais.resourceBundle.getString("searchLabelController.lblSearchedWord"));
		txtSearch.setLayoutX(sbWidth);
		txtSearch.setLayoutY(20);

		Button btn = Util.customSearchTextField("right", null, txtSearch);
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				util.actionSearchBox(txtSearch, tpCadastroUsr);

			}
		});

		txtSearch.setOnKeyPressed(key -> {
			if (key.getCode().equals(KeyCode.ENTER)) {
				util.actionSearchBox(txtSearch, tpCadastroUsr);
			} else {
			}
		});

		// -----------------------------

	}

}
