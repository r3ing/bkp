package controllers.compras;

import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javax.persistence.Column;

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXToggleButton;
import com.sun.javafx.collections.MappingChange.Map;
import com.sun.javafx.scene.control.skin.TableHeaderRow;
import application.DadosGlobais;
import controllers.configuracoes.UsuarioController;
import controllers.utils.ConfigColumnController;
import controllers.utils.PrintExportController;
import controllers.compras.ComposicaoPrecosController;
import controllers.utils.ProgressBarForm;
import controllers.utils.TrocaEmpresaController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.PopOver.ArrowLocation;
import org.controlsfx.control.textfield.CustomTextField;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.BigDecimalStringConverter;
import javafx.util.converter.IntegerStringConverter;
import models.compras.Departamento;
import models.compras.DepartamentoDAO;
import models.compras.Fabricante;
import models.compras.FabricanteDAO;
import models.compras.FamiliaPreco;
import models.compras.FamiliaPrecoDAO;
import models.compras.Fornecedor;
import models.compras.FornecedorDAO;
import models.compras.Grupo;
import models.compras.GrupoDAO;
import models.compras.Item;
import models.compras.ItemCodBar;
import models.compras.ItemCodBarDAO;
import models.compras.ItemDAO;
import models.compras.ItemEstoqueDeposito;
import models.compras.ItemFornecedor;
import models.compras.ItemValor;
import models.compras.OperacaoEntrada1;
import models.compras.OperacaoEntradaDAO1;
import models.compras.SubGrupo;
import models.compras.SubGrupoDAO;
import models.compras.TabelaLcServico;
import models.compras.TabelaLcServicoDAO;
import models.compras.TabelaNCM;
import models.compras.TabelaNCMDAO;
import models.compras.Tributacao;
import models.compras.TributacaoDAO;
import models.compras.Unidade;
import models.compras.UnidadeDAO;
import models.configuracoes.Empresa;
import models.configuracoes.EmpresaDAO;
import models.configuracoes.NivelAcesso;
import models.financeiro.Moeda;
import models.financeiro.MoedaDAO;
import models.financeiro.PlanoConta;
import tools.controls.ComboBoxFilter;
import tools.enums.EnumCboxCondicaoDoVeiculoItemDadosVeiculo;
import tools.enums.EnumCboxCondicaoVinItemDadosVeiculo;
import tools.enums.EnumCboxCorItemDadosVeiculo;
import tools.enums.EnumCboxEspVeiculoItemDadosVeiculo;
import tools.enums.EnumCboxProceProposVendaItemAdicionais;
import tools.enums.EnumCboxRestricaoItemDadosVeiculo;
import tools.enums.EnumCboxTipoCombustiveltemDadosVeiculo;
import tools.enums.EnumCboxTipoPinturaItemDadosVeiculo;
import tools.enums.EnumCboxTipoVeiculoItemDadosVeiculo;
import tools.enums.EnumCompartilhamento;
import tools.enums.EnumCriterioPrecoFilial;
import tools.enums.EnumLogRetornoStatus;
import tools.enums.EnumNivelAcesso;
import tools.enums.EnumTipoControleLote;
import tools.enums.EnumTipoFaturamento;
import tools.enums.EnumTipoItem;
import tools.reports.TableConfPrint;
import tools.utils.LogRetorno;
import tools.utils.Util;
import tools.xml.UserDefinedColumn;
import tools.xml.XMLTableColumns;

/**
 * CLASSE CONTROLADORA DO FORMULARIO viewItem.fxml
 * 
 * @author Eptus da Amazônia
 */
@SuppressWarnings("restriction")
public class ItemController implements Initializable {

	@FXML
	private AnchorPane anchorPanePrincipal, anchorPaneListagem, anchorPaneDetalhes, anchorPaneFilter, anchorPaneFormacaoPreco;

	@FXML
	private TabPane tabPaneItem;

	@FXML
	private Tab tabDadosAdicionais,
	tabDadosVeiculo;

	@FXML 
	private Pane paneDadosVeiculo, paneDadosBalanca;

	@FXML
	private ToolBar toolBarListagem, toolBarDetalhes;

	@FXML
	private Label lblNumLinhas, lblTotalLinhas;

	@FXML
	private Button btnInsertGrid, btnInsert, btnDelete, btnRefresh, btnFilter, btnPrint, btnExport, btnConfig,
	btnSave, btnCancel, btnClose, btnAddCodBar, btnAddCodFornecedor, btnClone;

	@FXML
	private SplitPane splitPaneFilter;

	@FXML
	private ComboBox<ComboBoxFilter> cboxFilterColumn;

	@FXML
	private ComboBox<ComboBoxFilter> cboxFlagAtivo;
	
	@FXML
	private CheckComboBox<ComboBoxFilter> cBoxTipoItem;
	
	@FXML
	public TableView<Item> tbView;

	@FXML
	private TableColumn<Item, Integer> tbColCodigo, tbColCodEmp, tbColAtivoInat ,tbColCodFabricante, tbColCodGrupo, tbColCodSubGrupo, tbColCodDepartamento
	,tbColCodCest,tbColCodFamiliaPreco,tbColCodMoeda,tbColCodUnidade,tbColCodUnidadeEmb,tbColCodTributacao,tbColEnviaBalanca;

	@FXML
	private TableColumn<Item, BigDecimal> tbColQtdPallet ,tbColMedidaM3,tbColPesoBruto, tbColPesoLiquido, tbColFatorConversao, tbColQtdLitros,
	tbColQtdEmbCompra, tbColQtdEmbVenda, tbColQtdEmbAtacado, tbColDescontoMax, tbColQtdEstoque;

	@FXML
	private TableColumn<Item, String> tbColDescricao, tbColCodBarra, tbColTributacao, tbColDadosAdicionais, tbColDescReduzida, tbColDescTecnica, 
	tbColNumFabricante, tbColNumOriginal , tbColFabricante,tbColGrupo, tbColSubGrupo, tbColDepartamento ,tbColLcServico, tbColNcm, tbColFamiliaPreco, tbColMoeda
	,tbColUnidade,tbColUnidadeEmb;

	@FXML
	private TextField txtCodigo, txtDescricao,  txtUnidadeEmb, txtLcServico, txtNcm, txtMoeda, txtQtdPallet, 
	txtDescReduzida, txtDescTecnica, txtNumFabricante, txtNumOriginal, txtCodCest, txtMedidaM3,
	txtPesoBruto, txtPesoLiquido, txtFatorConversao, txtQtdLitros, txtQtdEmbalagemCompra, txtQtdEmbalagemVenda, txtQtdEmbalagemAtacado,
	txtDataUltRep, txtNoDocUltRep, txtQtdUltRep, txtCodFornUltRep, txtFornUltRep, txtLocacao, txtQtdMinima, txtQtdMaxima, txtTributacao,
	txtCodBar, txtQtdEmbCodBar;

	@FXML
	private TextField txtInstrucoes,
	txtDiasValidade,
	txtObservacoesAdicionis,
	txtCodigoANP,
	txtPercentualGasNatural,
	txtQtdVolumes, txtSaldoAtual, txtQtdCcusto1, txtQtdCcusto2;


	@FXML
	private CustomTextField txtFilterColumn, txtCodFabricante, txtCodGrupo, txtCodSubGrupo, txtCodDepartamento, txtCodUnidade, txtCodUnidadeEmb,
	txtCodLcServico, txtCodNcm, txtCodFamiliaPreco, txtCodMoeda, txtGrupo, txtSubGrupo, txtFabricante, txtDepartamento, txtUnidade, txtFamiliaPreco,
	txtCodTributacao,

	txtReferenciaFornecedor,
	txtCodFornecedor,
	txtFornecedor,

	txtItemSimilar1,
	txtItemSimilar1Fk,

	txtItemSimilar2,
	txtItemSimilar2Fk,

	txtItemSimilar3,
	txtItemSimilar3Fk,

	txtItemSimilar4,
	txtItemSimilar4Fk,

	txtItemSimilar5,
	txtItemSimilar5Fk,

	txtItemSimilar6,
	txtItemSimilar6Fk;	

	@FXML
	private TextArea txtObservacao;

	@FXML
	private JFXToggleButton tgbFlagCodBarPrincipal,
	tgbtnFlagBalanca,
	tgbtnDisponivelInternet,
	tgbtnParametrosMobile,
	tgbtnFlagVeiculo,
	tgbtnProductoComposto;

	@FXML
	private RadioButton rbtnKg,
	rbtnUnitary;

	@FXML
	public TableView<ItemCodBar> tbViewCodBarras;

	@FXML
	private TableColumn<ItemCodBar, String> tbColCodBarras;

	@FXML
	private TableColumn<ItemCodBar, BigDecimal> tbColQtdEmbalagem;

	@FXML
	private TableColumn<ItemCodBar, Integer> tbColFlagPrincipal;

	@FXML
	public TableView<ItemValor> tbViewPosicaoEstoque;

	@FXML
	private TableColumn<ItemValor, Integer> tbColEmpEstoque, tbColDocReposicao;

	@FXML
	private TableColumn<ItemValor, BigDecimal> tbColQtdMin,tbColQtdReposicao, tbColQtdMax, tbColSaldoAtual, tbColCcusto1, tbColCcusto2, tbColPrecoCusto;

	@FXML
	private TableColumn<ItemValor, String> tbColLocacao;

	@FXML
	private TableColumn<ItemValor, LocalDateTime> tbColDtReposicao;

	@FXML
	public TableView<ItemValor> tbViewPrecosEmp;

	@FXML
	private TableColumn<ItemValor, String> tbColEmpPreco, tbColUsuarioPreco;

	@FXML
	private TableColumn<ItemValor, LocalDateTime> tbColDtUltPreco; 

	
	@FXML
	public TableView<ItemEstoqueDeposito> tbViewEstoqueDepositos;
	
	@FXML
	private TableColumn<ItemEstoqueDeposito, String> tbColDeposito; 
	
	@FXML
	private TableColumn<ItemEstoqueDeposito, BigDecimal> tbColDepQtdAtual, tbColDepQtdDisponivel, tbColDepQtdReservada, tbColDepQtdCcusto1, tbColDepQtdCcusto2; 
	
	@FXML
	private TableColumn<ItemEstoqueDeposito, Integer> tbColCodDeposito;
	
	@FXML
	private ProgressBar pBar;

	@FXML
	private ContextMenu contextMenu = null;

	@FXML
	private ToggleButton toggleHelp;

	//----------------------------- Begin Components of Cadastro Itens - Dados Veiculos ------------------------------------------------

	@FXML
	private TextField txtNoChassi,
	txtPotMotor,
	txtCm3,
	txtPesoBruto1,
	txtLiquido, 
	txtNoSerie,
	txtNoMotor,
	txtDistanciaEixos,
	txtRenavam,
	txtAnoFab,
	txtAnoMod,
	txtCmkg,
	txtCodMarcaModelo,
	txtLotacao;    

	@FXML
	private ComboBox<ComboBoxFilter> cbCor,
	cbTipoCombustivel,
	cbTipoPintura,
	cbTipoVeiculo,
	cbEspVeiculo,
	cbCondicaoVin,
	cbCondicaoDoVeiculo,
	cbRestricao,
	cboxProceProposVenda;

	@FXML
	private CheckBox sbEddvpModulo,
	sbEddvpNota;


	@FXML
	private TableColumn<Item, String> tbColNoChassi,
	tbColCodMarcaModelo,
	tbColNoSerie,
	tbColNoMotor,
	tbColRenavam;


	@FXML
	private TableColumn<Item, Integer> tbColAnoFab,
	tbColAnoMod,
	tbColCondicaoVin,
	tbColCondicaoDoVeiculo,
	tbColCor,
	tbColEspVeiculo,
	tbColLotacao,
	tbColTipoCombustivel,
	tbColTipoPintura,
	tbColTipoVeiculo,
	tbColRestricao; 	

	@FXML
	private TableColumn<Item, BigDecimal> tbColCm3,
	tbColCmkg,
	tbColDistanciaEixos,	   										   
	tbColPotMotor,
	tbColPesoBrutoVeiculo,
	tbColLiquido;	
	@FXML
	private TableColumn<Item, Boolean> tbColEddvpModulo,
	tbColEddvpNota;

	@FXML
	public TableView<ItemFornecedor> tbViewItemFornecedor;

	@FXML
	private TableColumn<ItemFornecedor, String> tbColCodItemFornecedor,tbColFornecedorItem;

	@FXML
	public TableView<ItemFornecedor> tbViewAlteracaoPreco;

	@FXML
	private TableColumn<ItemFornecedor, String> tbColAltPrecoData,tbColAltPrecoUsuario,tbColAltPrecoCusto, tbColAltPrecoVenda;


	@FXML
	private Label lblCboxFilterColumn, lblcboxFlagAtivo, lblDetalhes, lblDescriao, lblDetCodigo, lblDetDesc, lbTitleFormCad,
	lbligo, lblEmp, lblColAtivoInat ,lblFabricante, lblGrupo, lblSubGrupo, lblDepartamento
	,lblCest,lblFamiliaPreco,lblMoeda,lblUnidade,lblUnidadeEmb,lblTributacao, lblColEnviaBalanca, lblCodFornecedor;


	//-------------------------- End of Components of Cadastro Itens - Dados Veiculos -------------------------------------

	// ------------------- ELEMENTOS CONTIDOS NO FORMULARIO FXML ------------------------

	// ATRIBUTOS DA CLASSE
	private ObservableList<Item> listaRegistros = null;
	//private ObservableList<ItemCodBar> listCodBars = FXCollections.<ItemCodBar>observableArrayList();
	private List<ItemCodBar> listCodBars= new ArrayList<ItemCodBar>();
	private ObservableList<ItemValor> listValors = FXCollections.<ItemValor>observableArrayList();
	private ObservableList<ComboBoxFilter> listComboBoxFilter = FXCollections.observableArrayList();
	private List<TableConfPrint> tableShowPrintList = new ArrayList<TableConfPrint>();
	private boolean flagPaneFilter = true;// ATRIBUTO UTILIZADO PARA CONTROLAR O STATUS DO PAINEL DO FILTRO ABERTO OU FECHADO
	private List<Integer> paramFlagAtivo = Arrays.asList(1);// ATRIBUTO INICIAL DO PARAMENTO DE FLAGATIVO UTILIZADO NAS BUSCAS DE REGISTROS
	private NivelAcesso nivAcessoPermissao;// ATRIBUTO COM OS NIVEIS DE ACESSO DO USUARIO SOBRE OS REGISTROS (EXCLUSAO,INSERCAO,ETC)
	private TabPane tabPrincipal;// TAB PANE PRINCIPAL PARA ABRIR NOVAS GUIAS A PARITR DA TELA DE CADASTRO
	private String fieldFiltro;
	private String filtro;
	private boolean flagSearch = true;
	private boolean flagOpenBuscador = true;

	// OBJETOS INSTACIADOS NA CLASSE
	private Item entidadeBean = new Item();
	private ItemDAO entidadeDao = new ItemDAO();
	private Util util = new Util();
	private PopOver popOver = new PopOver();
	private static Stage stg;
	private String fileNameConfigColum = "Grid-Cad-Item";
	private ComposicaoPrecosController cpc;
	private Fornecedor fornedorItem;

	public ObservableList<ComboBoxFilter> listCboxCor = FXCollections.observableArrayList(),
			listCboxTipoCombustivel = FXCollections.observableArrayList(),
			listCboxTipoPintura = FXCollections.observableArrayList(),
			listCboxTipoVeiculo = FXCollections.observableArrayList(),
			listCboxEspVeiculo = FXCollections.observableArrayList(),
			listCboxCondicaoVin = FXCollections.observableArrayList(),
			listCboxCondicaoDoVeiculo = FXCollections.observableArrayList(),
			listCboxRestricao = FXCollections.observableArrayList(),
			listCboxProceProposVenda = FXCollections.observableArrayList();
	private ObservableList<ComboBoxFilter> listTipoItem = FXCollections.observableArrayList();

	// CONSTRUTOR PADRAO DA CLASSE
	public ItemController() {

	}

	// CONSTRUTOR GENERICO DA CLASSE ONDE É PASSADO OS NIVEIS DE ACESSO DO
	// FORMULARIO
	public ItemController(NivelAcesso nivAcesso, TabPane o_tabPrincipal){
		this.nivAcessoPermissao = nivAcesso;
		this.tabPrincipal = o_tabPrincipal;	

	}

	// CONSTRUTOR GENERICO DA CLASSE ONDE É PASSADO OS NIVEIS DE ACESSO DO
	// FORMULARIO
	public ItemController(NivelAcesso nivAcesso, String fieldFiltro, String filtro) {
		this.nivAcessoPermissao = nivAcesso;
		this.filtro=filtro;
		this.fieldFiltro=fieldFiltro;
	}

	@FXML
	/**
	 * AÇÃO DO BOTAO INSERIR PRESENTE NA ABA LISTAGEM (btnInsertGrid) - ATALHO
	 * F5
	 */
	void actionBtnInsertGrid(ActionEvent event) {
		if (nivAcessoPermissao.getFlagInsert().equals(1)) {

			entidadeBean = new Item();

			//INICIALIZA O OBJETO COM VALORES DEFALUT NOT NULL
			util.initializeAttribClass(entidadeBean);

			resetFormValues();
			setRowsItemValores();
			btnInsert.setDisable(true);
			btnSave.setDisable(false);
			btnCancel.setDisable(false);
			btnClone.setDisable(true);
			txtCodigo.setDisable(true);
			txtCodigo.setText("+1");
			//txtDescricao.clear();			
			Util.openFormCadastro(anchorPaneListagem, anchorPaneDetalhes, Util.FULL_SIZE);
			txtDescricao.requestFocus();
		} else {
			util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
		}

	}

	@FXML
	/**
	 * AÇÃO DO BOTAO INSERIR PRESENTE NA ABA DETALHES (btnInsert) - ATALHO F5
	 */
	void actionBtnInsert(ActionEvent event) {
		if (nivAcessoPermissao.getFlagInsert().equals(1)) {
			
			resetFormValues();
			entidadeBean = new Item();
			//INICIALIZA O OBJETO COM VALORES DEFALUT NOT NULL
			util.initializeAttribClass(entidadeBean);

			setRowsItemValores();

			btnInsert.setDisable(true);
			btnClone.setDisable(true);
			btnSave.setDisable(false);
			btnCancel.setDisable(false);
			txtCodigo.setDisable(true);
			txtCodigo.setText("+1");
			txtDescricao.requestFocus();
		} else {
			util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
		}

	}

	@FXML
	/**
	 * AÇÃO DO BOTAO INSERIR PRESENTE NA ABA DETALHES (btnInsert) - ATALHO F5
	 */
	void actionBtnClose(ActionEvent event) {
		// animaFadeOutPane();
		Util.fechaTelaCadastro(anchorPaneListagem, anchorPaneDetalhes);
		resetFormValues();
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
		Util.setDefaultStyle(txtDescricao);
	}

	
	@FXML
	/**
	 * AÇÃO DO BOTAO IMPRIMIR PRESENTE NA ABA DETALHES (btnPrint) - ATALHO
	 * CRTL+P
	 */
	void actionBtnPrint(ActionEvent event) throws NoSuchFieldException, SecurityException {
		if (nivAcessoPermissao.getFlagPrint().equals(1))
			printExportShow();
		else
			util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
	}

	@FXML
	/**
	 * AÇÃO DO BOTAO DE BUSCAR UTILIZADO NAS PESQUISAS COM FILTRO
	 * (BtnQueryFilter)
	 */
	void actionTxtFilterColumn(ActionEvent event) throws NoSuchFieldException, SecurityException {

		if (txtFilterColumn.getText().isEmpty()) {
			if (util.showAlert(DadosGlobais.resourceBundle.getString("alertConfirShowAllData"), "confirmation")) {
				taskQuery("filter", true);
			}
		} else {
			taskQuery("filter", true);
		}
	}

	/*
	 * 
        Common method that will be use in the 'actionBtnSave' 
        to be called in each tab after doing the corresponding 
        validation of its tab 

	 * */

	void doActionSave()
	{			       
		if (txtCodigo.isDisable() && txtCodigo.getText().equals("+1")) 
		{

			Task<String> TarefaRefresh = new Task<String>() 
			{

				LogRetorno logRet = new LogRetorno();

				@Override
				protected String call() throws Exception 
				{			
					logRet = entidadeDao.insert(entidadeBean, tbViewCodBarras.getItems(), tbViewPosicaoEstoque.getItems(),tbViewItemFornecedor.getItems());			
					return "-";
				}

				@Override
				protected void succeeded() 
				{
					stg.close();
					pBar.setProgress(1);

					if ( logRet.getStatus().equals(EnumLogRetornoStatus.SUCESSO)) 
					{
						Util.showNotification(DadosGlobais.resourceBundle.getString("notificationSucess"));
						txtCodigo.setText(String.valueOf(logRet.getLastRecord()));
						btnSave.setDisable(true);
						btnCancel.setDisable(true);
						btnClone.setDisable(true);
						btnInsert.setDisable(false);
						txtCodigo.setDisable(false);
						updateTbView("INSERT");
						tabPaneItem.getSelectionModel().select(0);
						txtCodigo.requestFocus();

					} 
					else 
					{			
						util.showAlert(logRet.getMsg(), "error");			
					}
				}

				@Override
				protected void failed() 
				{
					stg.close();
					util.tratamentoExcecao(exceptionProperty().getValue().toString(),"[ ItemController.actionBtnSave() - INSERT ]");
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
		else

			if (entidadeBean.getCodigo().toString().equals(txtCodigo.getText())) 
			{

				Task<String> TarefaRefresh = new Task<String>() 
				{

					LogRetorno logRet = new LogRetorno();

					@Override
					protected String call() throws Exception 
					{

						logRet = entidadeDao.update(entidadeBean, tbViewCodBarras.getItems(), listCodBars, tbViewItemFornecedor.getItems());

						return "-";
					}

					@Override
					protected void succeeded() 
					{
						stg.close();
						pBar.setProgress(1);

						if (logRet.getStatus().equals(EnumLogRetornoStatus.SUCESSO)) 
						{

							Util.showNotification(DadosGlobais.resourceBundle.getString("notificationSucess"));
							btnSave.setDisable(true);
							btnCancel.setDisable(true);
							btnClone.setDisable(true);
							btnInsert.setDisable(false);
							txtCodigo.setDisable(false);
							updateTbView("UPDATE");
							tabPaneItem.getSelectionModel().select(0);
							txtCodigo.requestFocus();

						} 
						else 
						{
							util.showAlert(logRet.getMsg(), "error");
						}

					}

					@Override
					protected void failed() 
					{
						stg.close();
						util.tratamentoExcecao(exceptionProperty().getValue().toString(),
								"[ ItemController.actionBtnSave() - UPDATE ]");
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
				stg = ProgressBarForm.showProgressBar(UsuarioController.class, TarefaRefresh,
						DadosGlobais.resourceBundle.getString("infSaveChange"), false);
				pBar.setProgress(-1);

			}			
			else 
			{

				loadByID(false, Integer.valueOf(txtCodigo.getText()), false);
			}		

	}


	@FXML
	/**
	 * AÇÃO DO BOTAO SALVAR PRESENTE NA ABA DETALHES (btnSave) - ATALHO F6
	 */
	void actionBtnSave(ActionEvent event) 
	{


		if (nivAcessoPermissao.getFlagUpdate().equals(1)) 
		{

			boolean flagServico = false;
			
			for (int i = 0; i < cBoxTipoItem.getCheckModel().getCheckedItems().size(); i++) {
				
				if(((EnumTipoItem) cBoxTipoItem.getCheckModel().getCheckedItems().get(i).getEnumOpcao()).equals(EnumTipoItem.SERVICOS)){
					flagServico = true;
				}
				
			}

			if ( (!Util.noEmpty(txtDescricao, txtDescReduzida, txtCodFabricante, txtCodGrupo, txtCodSubGrupo, txtCodUnidade, txtCodUnidadeEmb)) &&
					
					!Util.noEmpty(cBoxTipoItem)
					
					&&
					((flagServico == true && !Util.noEmpty(txtCodLcServico) && cBoxTipoItem.getCheckModel().getCheckedItems().size() == 1) 
							|| (flagServico == false && !Util.noEmpty(txtCodNcm)))
					
					
					&&
					((tgbtnFlagVeiculo.isSelected() &&	
							(!Util.noEmpty(txtNoChassi,txtPotMotor,txtCm3,
									txtPesoBruto1,
									txtLiquido, 
									txtNoSerie,
									txtNoMotor,
									txtDistanciaEixos,
									txtRenavam,
									txtAnoFab,
									txtAnoMod,
									txtCmkg,
									txtCodMarcaModelo,
									txtLotacao)  &&   
									!Util.noEmpty(
											cbCor,
											cbTipoCombustivel,
											cbTipoPintura,
											cbTipoVeiculo,
											cbEspVeiculo,
											cbCondicaoVin,
											cbCondicaoDoVeiculo,
											cbRestricao,
											cboxProceProposVenda)
									)
							) || !tgbtnFlagVeiculo.isSelected())
					){


				//FLAG PARA CHECAR SE FOI SETADO A TRIBUTACAO PARA TODAS AS EMPRESAS
				boolean flagChecaTributacoes = false;
				String empsTributacaoNull = "" ;

				for (int i = 0; i < tbViewPosicaoEstoque.getItems().size(); i++) {
					if(tbViewPosicaoEstoque.getItems().get(i).getTributacao() == null){
						flagChecaTributacoes = true;
						empsTributacaoNull += tbViewPosicaoEstoque.getItems().get(i).getCodemp()+" | " ;

					}				
				}	

				if(!flagChecaTributacoes){

					//FLAG PARA VERIFICAR SE FOI SETADO UM CODIGO DE BARRAS PRINCIPAL
					boolean flagCodBarPrincipal=false;

					if(tbViewCodBarras.getItems().size() > 0)
					{

						for( int i=0 ; i < tbViewCodBarras.getItems().size() ; i++ )
						{					        	
							if( tbViewCodBarras.getItems().get(i).getFlagCodbarPrincipal().equals(1) )
								flagCodBarPrincipal = true;
						}
					}
					else
					{
						flagCodBarPrincipal = true;
					}

					if(flagCodBarPrincipal)
					{



						// ABA INFORMAÇÕES GERAIS
						// CAMPOS OBRIGATORIOS
						
						
						entidadeBean.setDescricao(txtDescricao.getText());
						entidadeBean.setDescReduzida(txtDescReduzida.getText());
						entidadeBean.setCodCest(txtCodCest.getText());
						entidadeBean.setQtdEmbCompra(Util.decimalBRtoBigDecimal(4, txtQtdEmbalagemCompra.getText()));
						entidadeBean.setQtdEmbVenda(Util.decimalBRtoBigDecimal(4, txtQtdEmbalagemVenda.getText()));
						entidadeBean.setQtdEmbAtacado(Util.decimalBRtoBigDecimal(4, txtQtdEmbalagemAtacado.getText()));
						entidadeBean.setQtdVolumes(Util.decimalBRtoBigDecimal(4, txtQtdVolumes.getText()));			        
						entidadeBean.setFatorConversao(Util.decimalBRtoBigDecimal(4, txtFatorConversao.getText()));

						List<String> listTipoItem = new ArrayList<String>();
						
						for (ComboBoxFilter cbox : cBoxTipoItem.getCheckModel().getCheckedItems()) {
							
							listTipoItem.add(((EnumTipoItem) cbox.getEnumOpcao()).codTipoItem);
							
						}
						
						entidadeBean.setTipoItem(listTipoItem.toString().replace("[", "").replace("]", "").replace(" ", ""));				

						//CAMPOS OPCIONAIS
						entidadeBean.setDescTecnica(txtDescTecnica.getText());
						entidadeBean.setNumFrabricate(txtNumFabricante.getText());
						entidadeBean.setNumOriginal(txtNumOriginal.getText());
						entidadeBean.setQtdPallet(Util.decimalBRtoBigDecimal(4, txtQtdPallet.getText()));
						entidadeBean.setMedidaM3(Util.decimalBRtoBigDecimal(4, txtMedidaM3.getText()));
						entidadeBean.setQtdLitros(Util.decimalBRtoBigDecimal(4, txtQtdLitros.getText()));
						entidadeBean.setPesoBruto(Util.decimalBRtoBigDecimal(4, txtPesoBruto.getText()));
						entidadeBean.setPesoLiquido(Util.decimalBRtoBigDecimal(4, txtPesoLiquido.getText()));
						entidadeBean.setObservacao(txtObservacao.getText());	


						///--------------------CORRESPONDING VALIDATION AND SAVE OF TAB DADOS ADICIONAIS 1----------------------------------			

						String balancaInstrucoes = txtInstrucoes.getText();

						int flagBalanca = tgbtnFlagBalanca.isSelected() ? 1 : 0,
								balancaTipoPreco = 0,
								balancaDiasValidade = Integer.parseInt(txtDiasValidade.getText());

						if( rbtnKg.isSelected() )
							balancaTipoPreco = 0;
						else if(rbtnUnitary.isSelected())
							balancaTipoPreco = 1;
						else{}

						entidadeBean.setFlagBalanca(flagBalanca);							
						entidadeBean.setBalancaTipoPreco(balancaTipoPreco);				    
						entidadeBean.setBalancaInstrucoes(balancaInstrucoes);										
						entidadeBean.setBalancaDiasValidade(balancaDiasValidade);	

						int flagDisponivelNet = tgbtnDisponivelInternet.isSelected() ? 1 : 0,
								flagMobile = tgbtnParametrosMobile.isSelected() ? 1 : 0,	
										flagItemComposto = tgbtnProductoComposto.isSelected() ? 1 : 0;	

						entidadeBean.setFlagDisponivelNet(flagDisponivelNet);
						entidadeBean.setFlagMobile(flagMobile);

						entidadeBean.setFlagItemComposto(flagItemComposto);		


						int procedimentoEstoqueNegativo = Integer.parseInt(cboxProceProposVenda.getSelectionModel().getSelectedItem().getField());
						entidadeBean.setProcedimentoEstoqueNegativo(procedimentoEstoqueNegativo);

						entidadeBean.setDadosAdicionais(txtObservacoesAdicionis.getText());
						entidadeBean.setCodigoAnp(txtCodigoANP.getText());
						entidadeBean.setPercentualGasAnp(Util.decimalBRtoBigDecimal(4, txtPercentualGasNatural.getText()));


						///--------------------CORRESPONDING VALIDATION AND SAVE OF TAB DADOS D VEICULO---------------------------------- 
						int flagVeiculo = tgbtnFlagVeiculo.isSelected() ? 1 : 0;

						entidadeBean.setVeiculoFlag(flagVeiculo);	

						String veiculoChassi = txtNoChassi.getText(),
								veiculoNoSerie = txtNoSerie.getText(),
								veiculoNoMotor = txtNoMotor.getText(),
								veiculoRenavam = txtRenavam.getText(),
								veiculoCodMarcaModelo = txtCodMarcaModelo.getText();

						int  veiculoCor = Integer.parseInt(cbCor.getSelectionModel().getSelectedItem().getField()),
								veiculoTipoCombustivel = Integer.parseInt(cbTipoCombustivel.getSelectionModel().getSelectedItem().getField()),
								veiculoAnoFabricacao = Integer.parseInt(Util.textfieldNotNull("int" , txtAnoFab)),
								veiculoAnoModelo = Integer.parseInt(Util.textfieldNotNull("int", txtAnoMod));				        
						char veiculoTipoPintura = cbTipoPintura.getSelectionModel().getSelectedItem().getField().charAt(0);				    
						int	veiculoTipo = Integer.parseInt(cbTipoVeiculo.getSelectionModel().getSelectedItem().getField()),				    			
								veiculoEspecie = Integer.parseInt(cbEspVeiculo.getSelectionModel().getSelectedItem().getField());
						char veiculoCondicaoVin = cbCondicaoVin.getSelectionModel().getSelectedItem().getField().charAt(0);
						int 	veiculoCondicao = Integer.parseInt(cbCondicaoDoVeiculo.getSelectionModel().getSelectedItem().getField()),				    	
								veiculoLotacao = Integer.parseInt(Util.textfieldNotNull("int",txtLotacao)),				    	
								veiculoRestricao = Integer.parseInt(cbRestricao.getSelectionModel().getSelectedItem().getField()),
								veiculoFlagEnviaVenda = sbEddvpModulo.isSelected() ? 1 : 0;					    

						BigDecimal veiculoPotencia =Util.decimalBRtoBigDecimal(4, Util.textfieldNotNull("dec",txtPotMotor)),
								veiculoCm3 = Util.decimalBRtoBigDecimal(4, Util.textfieldNotNull("dec",txtCm3)),
								veiculoPesoBruto = Util.decimalBRtoBigDecimal(4, Util.textfieldNotNull("dec",txtPesoBruto1)),
								veiculoPesoLiquido = Util.decimalBRtoBigDecimal(4, Util.textfieldNotNull("dec",txtLiquido)),
								veiculoDistanciaEixos = Util.decimalBRtoBigDecimal(4, Util.textfieldNotNull("dec",txtDistanciaEixos)),	
								veiculoCmkg = Util.decimalBRtoBigDecimal(4, Util.textfieldNotNull("dec",txtCmkg));

						entidadeBean.setVeiculoChassi(veiculoChassi);					
						entidadeBean.setVeiculoCor(veiculoCor);					
						entidadeBean.setVeiculoPotencia(veiculoPotencia);					
						entidadeBean.setVeiculoCm3(veiculoCm3);					
						entidadeBean.setVeiculoPesoBruto(veiculoPesoBruto);					
						entidadeBean.setVeiculoPesoLiquido(veiculoPesoLiquido);					
						entidadeBean.setVeiculoNoSerie(veiculoNoSerie);					
						entidadeBean.setVeiculoTipoCombustivel(veiculoTipoCombustivel);
						entidadeBean.setVeiculoNoMotor(veiculoNoMotor);					
						entidadeBean.setVeiculoDistanciaEixos(veiculoDistanciaEixos);
						entidadeBean.setVeiculoRenavam(veiculoRenavam);					
						entidadeBean.setVeiculoAnoFabricacao(veiculoAnoFabricacao);
						entidadeBean.setVeiculoAnoModelo(veiculoAnoModelo);
						entidadeBean.setVeiculoTipoPintura(veiculoTipoPintura);
						entidadeBean.setVeiculoCmkg(veiculoCmkg);					
						entidadeBean.setVeiculoTipo(veiculoTipo);					
						entidadeBean.setVeiculoEspecie(veiculoEspecie);
						entidadeBean.setVeiculoCondicaoVin(veiculoCondicaoVin);
						entidadeBean.setVeiculoCondicao(veiculoCondicao);					
						entidadeBean.setVeiculoCodMarcaModelo(veiculoCodMarcaModelo);					
						entidadeBean.setVeiculoLotacao(veiculoLotacao);
						entidadeBean.setVeiculoRestricao(veiculoRestricao);					
						entidadeBean.setVeiculoFlagEnviaVenda(veiculoFlagEnviaVenda);			


						doActionSave();//method that realy save into DB


					}else
					{
						util.showAlert("Defina um Código de Barras como principal!", "warning");
					}
				}else{
					util.showAlert("Faltra tributacao da empresa " + empsTributacaoNull, "error");
				}

			}


		} 
		else 
		{
			util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
		}

	}

	@FXML
	/**
	 * AÇÃO DO BOTAO CANCELAR PRESENTE NA ABA DETALHES (btnCancel) - ATALHO F7
	 */
	void actionBtnCancel(ActionEvent event) 
	{

		//Util.setDefaultStyle(txtCodigo, txtDescricao);
		resetFormValues();
		loadByID(true, null, false);

	}
	
	@FXML
	/**
	 * AÇÃO DO BOTAO DUPLICAR PRESENTE NA ABA DETALHES (btnClone) - ATALHO CTRL+D
	 */
	void actionBtnClone(ActionEvent event) 
	{
		loadByID(false, Integer.parseInt(txtCodigo.getText()), true);
	}

	
	
	@FXML
	/**
	 * AÇÃO DO BOTAO CONFIGURAÇÃO DO GRID PRESENTE NA ABA LISTAGEM (btnConfig)
	 */
	void actionBtnConfig(ActionEvent event) {

		try {
			stg = new Stage();
			Scene scene = new Scene(util.openWindow("/views/utils/viewConfigColumns.fxml",
					new ConfigColumnController(ItemController.class, tbView, fileNameConfigColum)));
			stg.setScene(scene);
			stg.initStyle(StageStyle.TRANSPARENT);
			stg.initModality(Modality.APPLICATION_MODAL);
			stg.show();
		} catch (IOException e) {
			util.tratamentoExcecao(e.getMessage().toString(), "[ ItemController.actionBtnConfig() ]");
		}

	}

	@FXML
	/**
	 * AÇÃO DO BOTAO EXCLUIR PRESENTE NA ABA LISTAGEM (btnDelete) ATALHO - delete
	 */
	void actionBtnDelete(ActionEvent event) {

		if ((entidadeBean.getFlagAtivo().equals(1) && nivAcessoPermissao.getFlagDisable().equals(1))
				|| (entidadeBean.getFlagAtivo().equals(0) && nivAcessoPermissao.getFlagEnable().equals(1))) {

			if (util.showAlert(DadosGlobais.resourceBundle.getString("alertConfirmOprExcluir") + " "
					+ (entidadeBean.getFlagAtivo().equals(1) ? DadosGlobais.resourceBundle.getString("oprExcluir")
							: DadosGlobais.resourceBundle.getString("oprAtivar"))
					+ " " + DadosGlobais.resourceBundle.getString("alertConfirmOprExcluirItem"), "confirmation")) {

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
								"[ ItemController.actionBtnDelete() ]");
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
				stg = ProgressBarForm.showProgressBar(ItemController.class, tarefaCargaPg,
						tbView.getSelectionModel().getSelectedItem().getFlagAtivo().equals(1)
						? DadosGlobais.resourceBundle.getString("infoActiReg") : DadosGlobais.resourceBundle.getString("infoExcRegis"),
								false);
				pBar.setProgress(-1);

			}

		} else
			util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");

	}

	@FXML
	/**
	 * AÇÃO DO BOTAO FILTRO PRESENTE NA ABA LISTAGEM (btnFilter) TEM COMO FUNÇÃO
	 * EXIBIR E OCULTAR O PAINEL DE FILTROS DO FORMULÁRIO
	 */
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
	/**
	 * AÇÃO DO BOTAO RECARREGAR(REFRESH) PRESENTE NA ABA LISTAGEM (btnRefresh) -
	 * ATALHO F4
	 */
	void actionBtnRefresh(ActionEvent event) {

		if (util.showAlert(DadosGlobais.resourceBundle.getString("alertConfirShowAllData"),"confirmation")) {
			paramFlagAtivo = Arrays.asList(1);
			taskQuery("all", true);
			cboxFlagAtivo.getSelectionModel().selectFirst();

		}
	}

	@FXML
	/**
	 * AÇÃO DO BOTAO INCLUIR CODIGO DE BARRAS
	 */
	void actionBtnAddCodBar(ActionEvent event) {

		boolean flagDuplicado=false;

		if (!Util.noEmpty(txtCodBar,txtQtdEmbCodBar)){

			if((Util.decimalBRtoBigDecimal(4, txtQtdEmbCodBar.getText()).compareTo(BigDecimal.valueOf(0.0000))) == 1){

				LogRetorno logRetorno = new LogRetorno();

				//CONSULTA CODIGO DE BARRAS SE JÁ EXISTE EM ALGUM ITEM
				logRetorno = entidadeDao.getCodBarById(txtCodBar.getText());

				//CASO RETORNE UM LOG DIFERENTE DE SUCESSO MOSTRA QUE O CODBAR JA EXISTE E QUAL O ITEM
				if(!logRetorno.getStatus().equals(EnumLogRetornoStatus.SUCESSO)){

					Util.alertInf(logRetorno.getMsg());
					txtCodBar.requestFocus();

					//CASO A CONSULTA RETORNE SUCESSO 
				}else{
					//PERCORRE NO TABLEVIEW PARA VER SE O CODBAR JA EXISTE NO TABLEVIEW 
					for (int i = 0; i < tbViewCodBarras.getItems().size(); i++) {
						if(tbViewCodBarras.getItems().get(i).getCodBarras().equals(txtCodBar.getText())){
							flagDuplicado = true;
							txtCodBar.requestFocus();
						}
					}

					//CASO O CODIGO NAO EXISTA NO TABLEVIEW, CRIA UM ITEMCODBAR E ADD AO TABLEVIEW
					if(!flagDuplicado){

						ItemCodBar cdb = new ItemCodBar();
						cdb.setCodBarras(txtCodBar.getText());
						cdb.setQtdEmbalagem(Util.decimalBRtoBigDecimal(4, txtQtdEmbCodBar.getText()));
						cdb.setFlagAtivo(1);
						cdb.setFlagCodbarPrincipal(tgbFlagCodBarPrincipal.isSelected() ? 1 : 0);
						cdb.setItem(entidadeBean);
						tbViewCodBarras.getItems().add(cdb);
						txtCodBar.clear();
						txtQtdEmbCodBar.setText("1,0000");
						tgbFlagCodBarPrincipal.selectedProperty().set(false);
						txtCodBar.requestFocus();
					}
				}

			}else{
				txtQtdEmbCodBar.requestFocus();
			}
		}
	}

	@FXML
	/**
	 * AÇÃO DO BOTAO ADD COD FORNECEDOR
	 * 
	 */
	void actionBtnAddCodFornecedor(ActionEvent event) throws NoSuchFieldException, SecurityException {
		try{

			boolean refereciaExists = false;

			for (int i = 0; i < tbViewItemFornecedor.getItems().size(); i++) {

				if(tbViewItemFornecedor.getItems().get(i).getCodItemFornecedor().equals(Integer.parseInt(txtReferenciaFornecedor.getText()))
						&& tbViewItemFornecedor.getItems().get(i).getFornecedor().equals(fornedorItem)
						){

					refereciaExists = true;

				}

			}
			if(!refereciaExists){
				ItemFornecedor ifo = new ItemFornecedor();
				ifo.setItem(entidadeBean);
				ifo.setFornecedor(fornedorItem);
				ifo.setCodItemFornecedor(Integer.parseInt(txtReferenciaFornecedor.getText()));
				ifo.setFlagAtivo(1);
				tbViewItemFornecedor.getItems().add(ifo);
				tbViewItemFornecedor.refresh();
				txtReferenciaFornecedor.clear();
				txtCodFornecedor.clear();
				txtFornecedor.clear();
				txtReferenciaFornecedor.requestFocus();
			}else{
				util.showAlert("Referência já cadastrada", "warning");
				txtReferenciaFornecedor.requestFocus();
			}
		}
		catch(Exception e){

		}
	}

	@FXML
	/**
	 * EVENTO KEYPRESSED NO CAMPO CODIGO (txtCodigo) AO SER PRESSIONADO ENTER
	 * MUDA O FOCO PARA O ELEMENTO DESCRIÇÃO NA TELA DE DETALHES
	 */
	void keyPressedTxtReferenciaFornecedor(KeyEvent event) {

		if (event.getCode().equals(KeyCode.ENTER)) {
			txtCodFornecedor.requestFocus();
		}

	}

	@FXML
	/**
	 * EVENTO KEYPRESSED NO CAMPO CODIGO (txtCodigo) AO SER PRESSIONADO ENTER
	 * MUDA O FOCO PARA O ELEMENTO DESCRIÇÃO NA TELA DE DETALHES
	 */
	void keyPressedBtnAddCodFornecedor(KeyEvent event) throws NoSuchFieldException, SecurityException {

		if (event.getCode().equals(KeyCode.ENTER)) {
			actionBtnAddCodFornecedor(null);
		}

	}


	@FXML
	/**
	 * EVENTO KEYPRESSED NO CAMPO CODIGO (txtCodigo) AO SER PRESSIONADO ENTER
	 * MUDA O FOCO PARA O ELEMENTO DESCRIÇÃO NA TELA DE DETALHES
	 */
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
	/**
	 * EVENTO CLICK NO BOTAO QUE ABRE O BUSCADOR DE FABRICANTES
	 */
	void onActionTgbtnFlagVeiculo(ActionEvent event) {
		if(tgbtnFlagVeiculo.isSelected())
			paneDadosVeiculo.setDisable(false);
		else
			paneDadosVeiculo.setDisable(true);
	}	


	@FXML
	/**
	 * EVENTO CLICK NO BOTAO QUE ABRE O BUSCADOR DE FABRICANTES
	 */
	void onActionTgbtnFlagBalanca(ActionEvent event) {
		if(tgbtnFlagBalanca.isSelected())
			paneDadosBalanca.setDisable(false);
		else
			paneDadosBalanca.setDisable(true);
	}	

	@FXML
	/**
	 * EVENTO KEYPRESSED NO CAMPO CODIGO (txtCodigo) AO SER PRESSIONADO ENTER
	 * MUDA O FOCO PARA O ELEMENTO DESCRIÇÃO NA TELA DE DETALHES
	 */
	void onKeyPressedTxtCodBar(KeyEvent event) {

		//		if (event.getCode().equals(KeyCode.ENTER)) {
		//			txtQtdEmbCodBar.requestFocus();
		//		}

	}
	/**
	 * EVENTO KEYPRESSED NO CAMPO DE BUSCA DE REGISTROS (TxtFilterColumn) AO
	 * PRESSIONAR O ENTER EFETUA A BUSCA NO BANCO DE DADOS
	 */
	@FXML
	void keyPressedTxtFilterColumn(KeyEvent event) throws NoSuchFieldException, SecurityException {

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
	/**
	 * EVENTO KEYRELEASED DO CAMPO DE BUSCA DE REGISTRO (txtFilterColumn)
	 * REALIZA O FILTRO DE INFORMAÇOES NO TABLEVIEW ENQUANTO É DIGITADO DADOS NO
	 * CAMPO DE BUSCA SEM CARREGAR REGISTROS DA BASE DE DADOS
	 */
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
					if(Util.removeSpecialCharacters(cellValue.toLowerCase()).contains(txtFilterColumn.textProperty().get().toLowerCase())){
						tableItems.add(listaRegistros.get(i));
						break;
					}
				}
			}

			tbView.setItems(tableItems);
			lblNumLinhas.setText(String.valueOf(tbView.getItems().size()));
			;

		}
	}


	@FXML
	void onClickLblFabricante(MouseEvent event) {		
		try {

			if(Util.getNivelAcessoEntidade(EnumNivelAcesso.FABRICANTES).getFlagView().equals(1)){
				util.crearTab(tabPrincipal, DadosGlobais.resourceBundle.getString("miCadFabricante") , "/views/compras/viewFabricante.fxml", 
						new FabricanteController(Util.getNivelAcessoEntidade(EnumNivelAcesso.FABRICANTES), tabPrincipal), false);
			}else{
				util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@FXML
	/**
	 * EVENTO keyPressed do Campo Código do Fabricante
	 * Caso seja Pressionado F2 abre o buscador
	 * Caso seja pressionado ENTER tenta buscar o registro e troca o foco para o próximo campo
	 */
	void onKeyPressedTxtCodFabricante(KeyEvent event) {
		switch (event.getCode()) {
		case F2:
			flagSearch = false;
			showSearch("FABRICANTE");
			flagSearch = true;
			break;

		case ENTER:
		case TAB:
			flagOpenBuscador=false;
			txtCodGrupo.requestFocus();
			break;
		case N:
			if (event.isControlDown()){
				if(Util.getNivelAcessoEntidade(EnumNivelAcesso.FABRICANTES).getFlagInsert().equals(1)){
					txtCodFabricante.clear();
					txtFabricante.clear();
					txtCodFabricante.setDisable(true);
					Util.customSaveTextField("right",null, txtFabricante);
					txtFabricante.setEditable(true);
					txtFabricante.setDisable(false);
					txtFabricante.requestFocus();
				}else{
					util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
				}
			}
			break;

		default:
			break;

		}
	}


	@FXML
	/**
	 * EVENTO CLICK NO BOTAO QUE ABRE O BUSCADOR DE FABRICANTES
	 */
	void onActionTxtCodFabricante(ActionEvent event) {
		if(flagOpenBuscador)
			showSearch("FABRICANTE");

		flagOpenBuscador=true;
	}	

	@FXML
	/**
	 * EVENTO CLICK NO BOTAO QUE ABRE O BUSCADOR DE GRUPOS
	 */
	void onActionTxtFabricante(ActionEvent event) {
		if(!Util.noEmpty(txtFabricante)){

			FabricanteDAO fabricanteDao = new FabricanteDAO();
			Fabricante fabricante = new Fabricante();
			fabricante.setDescricao(txtFabricante.getText());

			Task<String> TarefaSaveGrupo = new Task<String>() {

				LogRetorno log = new LogRetorno();

				@Override
				protected String call() throws Exception {

					log = fabricanteDao.insert(fabricante);
					return "-";
				}

				@Override
				protected void succeeded() {
					stg.close();
					pBar.setProgress(1);
					if(log.getStatus().equals(EnumLogRetornoStatus.SUCESSO)){
						txtCodFabricante.setText(log.getLastRecord().toString());
						txtFabricante.setEditable(false);
						txtFabricante.setDisable(true);
						txtCodFabricante.setDisable(false);
						Util.customEditedTextField("right",null, txtFabricante);
						txtCodGrupo.requestFocus();
					}

				}

				@Override
				protected void failed() {
					stg.close();
					util.tratamentoExcecao(exceptionProperty().getValue().toString(),
							"[ ItemController.onActionTxtFabricante() ]");
					pBar.setProgress(0);

				}

				@Override
				protected void cancelled() {
					tbView.getItems().clear();
					pBar.setProgress(0);
					super.cancelled();
				}

			};
			Thread t = new Thread(TarefaSaveGrupo);
			t.setDaemon(true);
			t.start();
			stg = ProgressBarForm.showProgressBar(UsuarioController.class, TarefaSaveGrupo, DadosGlobais.resourceBundle.getString("infSaveChange"), false);
			pBar.setProgress(-1);
		}
	}

	@FXML
	void onClickLblGrupo(MouseEvent event) {		
		try {

			if(Util.getNivelAcessoEntidade(EnumNivelAcesso.GRUPOS).getFlagView().equals(1)){
				util.crearTab(tabPrincipal, DadosGlobais.resourceBundle.getString("miCadGrupo") , "/views/compras/viewGrupo.fxml", 
						new GrupoController(Util.getNivelAcessoEntidade(EnumNivelAcesso.GRUPOS), tabPrincipal), false);
			}else{
				util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void onKeyReleasedTxtQtdMin(KeyEvent event) {
		setChangeItemValor("QTDMIN");
	}
	@FXML
	void onKeyReleasedTxtQtdMax(KeyEvent event) {
		setChangeItemValor("QTDMAX");
	}
	@FXML
	void onKeyReleasedTxtLocacao(KeyEvent event) {
		setChangeItemValor("LOCACAO");
	}

	@FXML
	/**
	 * EVENTO keyPressed do Campo Código do Grupo
	 * Caso seja Pressionado F2 abre o buscador
	 * Caso seja pressionado ENTER tenta buscar o registro e troca o foco para o próximo campo
	 */
	void onKeyPressedTxtCodGrupo(KeyEvent event) {
		switch (event.getCode()) {
		case F2:
			flagSearch = false;
			showSearch("GRUPO");
			flagSearch = true;
			break;

		case ENTER:
		case TAB:
			flagOpenBuscador=false;
			txtCodSubGrupo.requestFocus();
			break;
		case N:
			if (event.isControlDown()){
				if(Util.getNivelAcessoEntidade(EnumNivelAcesso.GRUPOS).getFlagInsert().equals(1)){
					txtCodGrupo.clear();
					txtGrupo.clear();
					txtCodGrupo.setDisable(true);
					Util.customSaveTextField("right",null, txtGrupo);
					txtGrupo.setEditable(true);
					txtGrupo.setDisable(false);
					txtGrupo.requestFocus();
				}else{
					util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
				}
			}
			break;

		default:
			break;

		}
	}

	@FXML
	/**
	 * EVENTO CLICK NO BOTAO QUE ABRE O BUSCADOR DE GRUPOS
	 */
	void onActionTxtCodGrupo(ActionEvent event) {
		if(flagOpenBuscador)
			showSearch("GRUPO");

		flagOpenBuscador=true;
	}	

	@FXML
	/**
	 * EVENTO CLICK NO BOTAO QUE ABRE O BUSCADOR DE GRUPOS
	 */
	void onActionTxtGrupo(ActionEvent event) {

		GrupoDAO grupoDao = new GrupoDAO();
		Grupo grupo = new Grupo();
		grupo.setDescricao(txtGrupo.getText());

		Task<String> TarefaSaveGrupo = new Task<String>() {

			LogRetorno log = new LogRetorno();

			@Override
			protected String call() throws Exception {

				log = grupoDao.insert(grupo);
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				pBar.setProgress(1);
				if(log.getStatus().equals(EnumLogRetornoStatus.SUCESSO)){
					txtGrupo.setEditable(false);
					txtGrupo.setDisable(true);
					txtCodGrupo.setDisable(false);
					txtCodGrupo.setText(log.getLastRecord().toString());
					Util.customEditedTextField("right",null, txtGrupo);
					txtCodSubGrupo.requestFocus();
				}

			}

			@Override
			protected void failed() {
				stg.close();
				util.tratamentoExcecao(exceptionProperty().getValue().toString(),
						"[ ItemController.actionTxtGrupo() ]");
				pBar.setProgress(0);
			}

			@Override
			protected void cancelled() {
				tbView.getItems().clear();
				pBar.setProgress(0);
				super.cancelled();
			}

		};
		Thread t = new Thread(TarefaSaveGrupo);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(UsuarioController.class, TarefaSaveGrupo, DadosGlobais.resourceBundle.getString("infSaveChange"), false);
		pBar.setProgress(-1);

	}	

	@FXML
	void onClickLblSubGrupo(MouseEvent event) {		
		try {

			if(Util.getNivelAcessoEntidade(EnumNivelAcesso.SUBGRUPOS).getFlagView().equals(1)){
				util.crearTab(tabPrincipal, DadosGlobais.resourceBundle.getString("miCadSubGrupo") , "/views/compras/viewSubGrupo.fxml", 
						new SubGrupoController(Util.getNivelAcessoEntidade(EnumNivelAcesso.SUBGRUPOS), tabPrincipal), false);
			}else{
				util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	/**
	 * EVENTO keyPressed do Campo Código do Grupo
	 * Caso seja Pressionado F2 abre o buscador
	 * Caso seja pressionado ENTER tenta buscar o registro e troca o foco para o próximo campo
	 */
	void onKeyPressedTxtCodSubGrupo(KeyEvent event) {
		switch (event.getCode()) {
		case F2:
			flagSearch = false;
			showSearch("SUBGRUPO");
			flagSearch = true;
			break;

		case ENTER:
		case TAB:
			flagOpenBuscador=false;
			txtCodDepartamento.requestFocus();
			break;
		case N:
			if (event.isControlDown()){
				if(Util.getNivelAcessoEntidade(EnumNivelAcesso.SUBGRUPOS).getFlagInsert().equals(1)){
					txtCodSubGrupo.clear();
					txtSubGrupo.clear();
					txtCodSubGrupo.setDisable(true);
					Util.customSaveTextField("right",null, txtSubGrupo);
					txtSubGrupo.setEditable(true);
					txtSubGrupo.setDisable(false);
					txtSubGrupo.requestFocus();
				}else{
					util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
				}
			}
			break;

		default:
			break;

		}
	}

	@FXML
	/**
	 * EVENTO CLICK NO BOTAO QUE ABRE O BUSCADOR DE GRUPOS
	 */
	void onActionTxtCodSubGrupo(ActionEvent event) {
		if(flagOpenBuscador)
			showSearch("SUBGRUPO");

		flagOpenBuscador=true;
	}	

	@FXML
	/**
	 * EVENTO CLICK NO BOTAO QUE ABRE O BUSCADOR DE GRUPOS
	 */
	void onActionTxtSubGrupo(ActionEvent event) {
		if(!Util.noEmpty(txtSubGrupo)){

			SubGrupoDAO subGrupoDao = new SubGrupoDAO();
			SubGrupo subGrupo = new SubGrupo();
			subGrupo.setDescricao(txtSubGrupo.getText());

			Task<String> TarefaSaveGrupo = new Task<String>() {

				LogRetorno log = new LogRetorno();

				@Override
				protected String call() throws Exception {

					log = subGrupoDao.insert(subGrupo);
					return "-";
				}

				@Override
				protected void succeeded() {
					stg.close();
					pBar.setProgress(1);
					if(log.getStatus().equals(EnumLogRetornoStatus.SUCESSO)){
						txtCodSubGrupo.setText(log.getLastRecord().toString());
						txtSubGrupo.setEditable(false);
						txtSubGrupo.setDisable(true);
						txtCodSubGrupo.setDisable(false);
						Util.customEditedTextField("right",null, txtSubGrupo);
						txtCodDepartamento.requestFocus();
					}

				}

				@Override
				protected void failed() {
					stg.close();
					util.tratamentoExcecao(exceptionProperty().getValue().toString(),
							"[ ItemController.actionTxtSubGrupo() ]");
					pBar.setProgress(0);

				}

				@Override
				protected void cancelled() {
					tbView.getItems().clear();
					pBar.setProgress(0);
					super.cancelled();
				}

			};
			Thread t = new Thread(TarefaSaveGrupo);
			t.setDaemon(true);
			t.start();
			stg = ProgressBarForm.showProgressBar(UsuarioController.class, TarefaSaveGrupo, DadosGlobais.resourceBundle.getString("infSaveChange"), false);
			pBar.setProgress(-1);
		}
	}


	@FXML
	void onClickLblDepartamento(MouseEvent event) {		
		try {

			if(Util.getNivelAcessoEntidade(EnumNivelAcesso.DEPARTAMENTOS).getFlagView().equals(1)){
				util.crearTab(tabPrincipal, DadosGlobais.resourceBundle.getString("miCadDepartamento") , "/views/compras/viewDepartamento.fxml", 
						new DepartamentoController(Util.getNivelAcessoEntidade(EnumNivelAcesso.DEPARTAMENTOS), tabPrincipal), false);
			}else{
				util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	/**
	 * EVENTO keyPressed do Campo Código do Grupo
	 * Caso seja Pressionado F2 abre o buscador
	 * Caso seja pressionado ENTER tenta buscar o registro e troca o foco para o próximo campo
	 */
	void onKeyPressedTxtCodDepartamento(KeyEvent event) {
		switch (event.getCode()) {
		case F2:
			flagSearch = false;
			showSearch("DEPARTAMENTO");
			flagSearch = true;
			break;

		case ENTER:
		case TAB:
			flagOpenBuscador=false;
			txtCodLcServico.requestFocus();
			break;
		case N:
			if (event.isControlDown()){
				if(Util.getNivelAcessoEntidade(EnumNivelAcesso.DEPARTAMENTOS).getFlagInsert().equals(1)){
					txtCodDepartamento.clear();
					txtDepartamento.clear();
					txtCodDepartamento.setDisable(true);
					Util.customSaveTextField("right",null, txtDepartamento);
					txtDepartamento.setEditable(true);
					txtDepartamento.setDisable(false);
					txtDepartamento.requestFocus();
				}else{
					util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
				}
			}
			break;

		default:
			break;

		}
	}

	@FXML
	/**
	 * EVENTO CLICK NO BOTAO QUE ABRE O BUSCADOR DE GRUPOS
	 */
	void onActionTxtCodDepartamento(ActionEvent event) {
		if(flagOpenBuscador){
			showSearch("DEPARTAMENTO");
		}
		flagOpenBuscador=true;
	}	

	@FXML
	/**
	 * EVENTO CLICK NO BOTAO QUE ABRE O BUSCADOR DE GRUPOS
	 */
	void onActionTxtDepartamento(ActionEvent event) {
		if(!util.noEmpty(txtDepartamento)){

			DepartamentoDAO departamentoDao = new DepartamentoDAO();
			Departamento departamento = new Departamento();
			departamento.setDescricao(txtDepartamento.getText());

			Task<String> TarefaSaveGrupo = new Task<String>() {

				LogRetorno log = new LogRetorno();

				@Override
				protected String call() throws Exception {

					log = departamentoDao.insert(departamento);
					return "-";
				}

				@Override
				protected void succeeded() {
					stg.close();
					pBar.setProgress(1);
					if(log.getStatus().equals(EnumLogRetornoStatus.SUCESSO)){
						txtCodDepartamento.setText(log.getLastRecord().toString());
						txtDepartamento.setEditable(false);
						txtDepartamento.setDisable(true);
						txtCodDepartamento.setDisable(false);
						Util.customEditedTextField("right",null, txtDepartamento);
						txtCodDepartamento.requestFocus();
					}

				}

				@Override
				protected void failed() {
					stg.close();
					util.tratamentoExcecao(exceptionProperty().getValue().toString(),
							"[ ItemController.actionTxtDepartamento() ]");
					pBar.setProgress(0);

				}

				@Override
				protected void cancelled() {
					tbView.getItems().clear();
					pBar.setProgress(0);
					super.cancelled();
				}

			};
			Thread t = new Thread(TarefaSaveGrupo);
			t.setDaemon(true);
			t.start();
			stg = ProgressBarForm.showProgressBar(UsuarioController.class, TarefaSaveGrupo, DadosGlobais.resourceBundle.getString("infSaveChange"), false);
			pBar.setProgress(-1);
		}
	}

	@FXML
	void onClickLblFamiliaPreco(MouseEvent event) {		
		try {

			if(Util.getNivelAcessoEntidade(EnumNivelAcesso.FAMILIAS_DE_PRECIFICACAO).getFlagView().equals(1)){
				util.crearTab(tabPrincipal, DadosGlobais.resourceBundle.getString("miCadFamiliaPreco") , "/views/compras/viewFamiliaPreco.fxml", 
						new FamiliaPrecoController(Util.getNivelAcessoEntidade(EnumNivelAcesso.FAMILIAS_DE_PRECIFICACAO), tabPrincipal), false);
			}else{
				util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@FXML
	/**
	 * EVENTO keyPressed do Campo Código do FamiliaPreco
	 * Caso seja Pressionado F2 abre o buscador
	 * Caso seja pressionado ENTER tenta buscar o registro e troca o foco para o próximo campo
	 */
	void onKeyPressedTxtCodFamiliaPreco(KeyEvent event) {
		switch (event.getCode()) {
		case F2:
			flagSearch = false;
			showSearch("FAMILIAPRECO");
			flagSearch = true;
			break;

		case ENTER:
		case TAB:
			flagOpenBuscador=false;
			txtCodMoeda.requestFocus();
			break;
		case N:
			if (event.isControlDown()){
				if(Util.getNivelAcessoEntidade(EnumNivelAcesso.FAMILIAS_DE_PRECIFICACAO).getFlagInsert().equals(1)){
					txtCodFamiliaPreco.clear();
					txtFamiliaPreco.clear();
					txtCodFamiliaPreco.setDisable(true);
					Util.customSaveTextField("right",null, txtFamiliaPreco);
					txtFamiliaPreco.setEditable(true);
					txtFamiliaPreco.setDisable(false);
					txtFamiliaPreco.requestFocus();
				}else{
					util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
				}
			}
			break;

		default:
			break;

		}
	}


	@FXML
	/**
	 * EVENTO CLICK NO BOTAO QUE ABRE O BUSCADOR DE FAMILIA_PRECOS
	 */
	void onActionTxtCodFamiliaPreco(ActionEvent event) {
		if(flagOpenBuscador)
			showSearch("FAMILIAPRECO");

		flagOpenBuscador=true;
	}	

	@FXML
	/**
	 * EVENTO CLICK NO BOTAO QUE ABRE O BUSCADOR DE GRUPOS
	 */
	void onActionTxtFamiliaPreco(ActionEvent event) {
		if(!util.noEmpty(txtFamiliaPreco)){

			FamiliaPrecoDAO familiaPrecoDao = new FamiliaPrecoDAO();
			FamiliaPreco familiaPreco = new FamiliaPreco();
			familiaPreco.setDescricao(txtFamiliaPreco.getText());

			Task<String> TarefaSaveGrupo = new Task<String>() {

				LogRetorno log = new LogRetorno();

				@Override
				protected String call() throws Exception {

					log = familiaPrecoDao.insert(familiaPreco);
					return "-";
				}

				@Override
				protected void succeeded() {
					stg.close();
					pBar.setProgress(1);
					if(log.getStatus().equals(EnumLogRetornoStatus.SUCESSO)){
						System.out.println(99);
						txtCodFamiliaPreco.setText(log.getLastRecord().toString());
						txtFamiliaPreco.setEditable(false);
						txtFamiliaPreco.setDisable(true);
						txtCodFamiliaPreco.setDisable(false);
						Util.customEditedTextField("right",null, txtFamiliaPreco);
						txtCodGrupo.requestFocus();
					}

				}

				@Override
				protected void failed() {
					stg.close();
					util.tratamentoExcecao(exceptionProperty().getValue().toString(),
							"[ ItemController.onActionTxtFamiliaPreco() ]");
					pBar.setProgress(0);

				}

				@Override
				protected void cancelled() {
					tbView.getItems().clear();
					pBar.setProgress(0);
					super.cancelled();
				}

			};
			Thread t = new Thread(TarefaSaveGrupo);
			t.setDaemon(true);
			t.start();
			stg = ProgressBarForm.showProgressBar(UsuarioController.class, TarefaSaveGrupo, DadosGlobais.resourceBundle.getString("infSaveChange"), false);
			pBar.setProgress(-1);
		}
	}


	@FXML
	void onClickLblUnidade(MouseEvent event) {		
		try {

			if(Util.getNivelAcessoEntidade(EnumNivelAcesso.UNIDADES).getFlagView().equals(1)){
				util.crearTab(tabPrincipal, DadosGlobais.resourceBundle.getString("miCadUnidade") , "/views/compras/viewUnidade.fxml", 
						new UnidadeController(Util.getNivelAcessoEntidade(EnumNivelAcesso.UNIDADES), tabPrincipal), false);
			}else{
				util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@FXML
	/**
	 * EVENTO keyPressed do Campo Código do Unidade
	 * Caso seja Pressionado F2 abre o buscador
	 * Caso seja pressionado ENTER tenta buscar o registro e troca o foco para o próximo campo
	 */
	void onKeyPressedTxtCodUnidade(KeyEvent event) {
		switch (event.getCode()) {
		case F2:
			flagSearch = false;
			showSearch("UNIDADE");
			flagSearch = true;
			break;

		case ENTER:
		case TAB:
			flagOpenBuscador=false;
			txtCodUnidadeEmb.requestFocus();
			break;
		case N:
			if (event.isControlDown()){
				if(Util.getNivelAcessoEntidade(EnumNivelAcesso.UNIDADES).getFlagInsert().equals(1)){
					txtCodUnidade.clear();
					txtUnidade.clear();
					txtCodUnidade.setDisable(true);
					Util.customSaveTextField("right",null, txtUnidade);
					txtUnidade.setEditable(true);
					txtUnidade.setDisable(false);
					txtUnidade.requestFocus();
				}else{
					util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
				}
			}
			break;

		default:
			break;

		}
	}

	@FXML
	/**
	 * EVENTO keyPressed do Campo Código do Unidade
	 * Caso seja Pressionado F2 abre o buscador
	 * Caso seja pressionado ENTER tenta buscar o registro e troca o foco para o próximo campo
	 */
	void onKeyPressedTxtCodUnidadeEmb(KeyEvent event) {
		switch (event.getCode()) {
		case F2:
			flagSearch = false;
			showSearch("UNIDADEEMB");
			flagSearch = true;
			break;

		case ENTER:
			flagOpenBuscador=false;
			txtQtdPallet.requestFocus();
			break;

		default:
			break;

		}
	}
	@FXML
	/**
	 * EVENTO CLICK NO BOTAO QUE ABRE O BUSCADOR DE UNIDADES
	 */
	void onActionTxtCodUnidadeEmb(ActionEvent event) {
		if(flagOpenBuscador)
			showSearch("UNIDADEEMB");

		flagOpenBuscador=true;
	}	
	@FXML
	/**
	 * EVENTO CLICK NO BOTAO QUE ABRE O BUSCADOR DE UNIDADES
	 */
	void onActionTxtCodUnidade(ActionEvent event) {
		if(flagOpenBuscador)
			showSearch("UNIDADE");

		flagOpenBuscador=true;
	}	

	@FXML
	/**
	 * EVENTO CLICK NO BOTAO QUE ABRE O BUSCADOR DE GRUPOS
	 */
	void onActionTxtUnidade(ActionEvent event) {
		if(!util.noEmpty(txtUnidade)){

			UnidadeDAO unidadeDao = new UnidadeDAO();
			Unidade unidade = new Unidade();
			unidade.setDescricao(txtUnidade.getText());

			Task<String> TarefaSaveGrupo = new Task<String>() {

				LogRetorno log = new LogRetorno();

				@Override
				protected String call() throws Exception {

					log = unidadeDao.insert(unidade);
					return "-";
				}

				@Override
				protected void succeeded() {
					stg.close();
					pBar.setProgress(1);
					if(log.getStatus().equals(EnumLogRetornoStatus.SUCESSO)){
						System.out.println(99);
						txtCodUnidade.setText(log.getLastRecord().toString());
						txtUnidade.setEditable(false);
						txtUnidade.setDisable(true);
						txtCodUnidade.setDisable(false);
						Util.customEditedTextField("right",null, txtUnidade);
						txtCodGrupo.requestFocus();
					}

				}

				@Override
				protected void failed() {
					stg.close();
					util.tratamentoExcecao(exceptionProperty().getValue().toString(),
							"[ ItemController.onActionTxtUnidade() ]");
					pBar.setProgress(0);

				}

				@Override
				protected void cancelled() {
					tbView.getItems().clear();
					pBar.setProgress(0);
					super.cancelled();
				}

			};
			Thread t = new Thread(TarefaSaveGrupo);
			t.setDaemon(true);
			t.start();
			stg = ProgressBarForm.showProgressBar(UsuarioController.class, TarefaSaveGrupo, DadosGlobais.resourceBundle.getString("infSaveChange"), false);
			pBar.setProgress(-1);
		}
	}


	@FXML
	void onClickLblLcServico(MouseEvent event) {		
		try {

			if(Util.getNivelAcessoEntidade(EnumNivelAcesso.TABELA_LC_SERVICOS).getFlagView().equals(1)){
				util.crearTab(tabPrincipal, DadosGlobais.resourceBundle.getString("miCadTabelaLcServico") , "/views/compras/viewTabelaLcServico.fxml", 
						new TabelaLcServicoController(Util.getNivelAcessoEntidade(EnumNivelAcesso.TABELA_LC_SERVICOS), tabPrincipal), false);
			}else{
				util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	/**
	 * EVENTO keyPressed do Campo Código do Grupo
	 * Caso seja Pressionado F2 abre o buscador
	 * Caso seja pressionado ENTER tenta buscar o registro e troca o foco para o próximo campo
	 */
	void onKeyPressedTxtCodLcServico(KeyEvent event) {
		switch (event.getCode()) {
		case F2:
			flagSearch = false;
			showSearch("LCSERVICO");
			flagSearch = true;
			break;

		case ENTER:
		case TAB:
			flagOpenBuscador=false;
			txtCodNcm.requestFocus();
			break;

		default:
			break;

		}
	}

	@FXML
	/**
	 * EVENTO CLICK NO BOTAO QUE ABRE O BUSCADOR DE GRUPOS
	 */
	void onActionTxtCodLcServico(ActionEvent event) {
		if(flagOpenBuscador)
			showSearch("LCSERVICO");

		flagOpenBuscador=true;
	}


	@FXML
	void onClickLblNcm(MouseEvent event) {		
		try {

			if(Util.getNivelAcessoEntidade(EnumNivelAcesso.TABELA_NCM).getFlagView().equals(1)){
				util.crearTab(tabPrincipal, DadosGlobais.resourceBundle.getString("miCadTabelaNcm") , "/views/compras/viewTabelaNcm.fxml", 
						new TabelaNCMController(Util.getNivelAcessoEntidade(EnumNivelAcesso.TABELA_NCM), tabPrincipal), false);
			}else{
				util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	/**
	 * EVENTO keyPressed do Campo Código do Grupo
	 * Caso seja Pressionado F2 abre o buscador
	 * Caso seja pressionado ENTER tenta buscar o registro e troca o foco para o próximo campo
	 */
	void onKeyPressedTxtCodNcm(KeyEvent event) {
		switch (event.getCode()) {
		case F2:
			flagSearch = false;
			showSearch("NCM");
			flagSearch = true;
			break;

		case ENTER:
			flagOpenBuscador=false;
			txtCodFamiliaPreco.requestFocus();
			break;

		default:
			break;

		}
	}

	@FXML
	/**
	 * EVENTO CLICK NO BOTAO QUE ABRE O BUSCADOR DE GRUPOS
	 */
	void onActionTxtCodNcm(ActionEvent event) {
		if(flagOpenBuscador)
			showSearch("NCM");

		flagOpenBuscador=true;
	}

	@FXML
	/**
	 * EVENTO keyPressed do Campo Código do Grupo
	 * Caso seja Pressionado F2 abre o buscador
	 * Caso seja pressionado ENTER tenta buscar o registro e troca o foco para o próximo campo
	 */
	void onKeyPressedTxtCodMoeda(KeyEvent event) {
		switch (event.getCode()) {
		case F2:
			flagSearch = false;
			showSearch("MOEDA");
			flagSearch = true;
			break;

		case ENTER:
			flagOpenBuscador=false;
			txtCodUnidade.requestFocus();
			break;

		default:
			break;

		}
	}



	@FXML
	/**
	 * EVENTO keyPressed do Campo Código do Grupo
	 * Caso seja Pressionado F2 abre o buscador
	 * Caso seja pressionado ENTER tenta buscar o registro e troca o foco para o próximo campo
	 */
	void onKeyPressedTxtCodTributacao(KeyEvent event) {
		switch (event.getCode()) {
		case F2:
			flagSearch = false;
			showSearch("TRIBUTACAO");
			flagSearch = true;
			break;

		case ENTER:
			flagOpenBuscador=false;
			txtTributacao.requestFocus();
			break;

		default:
			break;

		}
	}

	@FXML
	/**
	 * EVENTO CLICK NO BOTAO QUE ABRE O BUSCADOR DE GRUPOS
	 */
	void onActionTxtCodTributacao(ActionEvent event) {
		if(flagOpenBuscador)
			showSearch("TRIBUTACAO");

		flagOpenBuscador=true;
	}	


	@FXML
	void onClickLblFornecedor(MouseEvent event) {		
		try {

			if(Util.getNivelAcessoEntidade(EnumNivelAcesso.FORNECEDORES).getFlagView().equals(1)){
				util.crearTab(tabPrincipal, DadosGlobais.resourceBundle.getString("miCadFornecedor") , "/views/compras/viewFornecedor.fxml", 
						new FornecedorController(Util.getNivelAcessoEntidade(EnumNivelAcesso.FORNECEDORES), tabPrincipal), false);
			}else{
				util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@FXML
	/**
	 * EVENTO keyPressed do Campo Código do Fabricante
	 * Caso seja Pressionado F2 abre o buscador
	 * Caso seja pressionado ENTER tenta buscar o registro e troca o foco para o próximo campo
	 */
	void onKeyPressedTxtCodFornecedor(KeyEvent event) {
		switch (event.getCode()) {
		case F2:
			flagSearch = false;
			showSearch("FORNECEDOR");
			flagSearch = true;
			break;

		case ENTER:
		case TAB:
			flagOpenBuscador=false;
			btnAddCodFornecedor.requestFocus();
			break;


		default:
			break;

		}
	}


	@FXML
	/**
	 * EVENTO CLICK NO BOTAO QUE ABRE O BUSCADOR DE FABRICANTES
	 */
	void onActionTxtCodFornecedor(ActionEvent event) {
		if(flagOpenBuscador)
			showSearch("FORNECEDOR");

		flagOpenBuscador=true;
	}	



	@FXML
	/**
	 * EVENTO keyPressed do Campo Fator Conversão
	 */
	void onKeyPressedTxtQtdVolume(KeyEvent event) {
		switch (event.getCode()) {

		case ENTER:
			Util.setFocus(txtObservacao);
			break;

		default:
			break;

		}
	}

	void searchItemSimilarWithWnd(Object evtSource) 
	{
		String ItemSimilarOption = "";

		if( evtSource == txtItemSimilar1 )
			ItemSimilarOption = "ITEMSIMILAR1";
		else if( evtSource == txtItemSimilar2 )
			ItemSimilarOption = "ITEMSIMILAR2";		    	
		else if( evtSource == txtItemSimilar3 )	
			ItemSimilarOption = "ITEMSIMILAR3";		    	
		else if( evtSource == txtItemSimilar4 )	
			ItemSimilarOption = "ITEMSIMILAR4";		    	
		else if( evtSource == txtItemSimilar5 )	
			ItemSimilarOption = "ITEMSIMILAR5";		    	
		else if( evtSource == txtItemSimilar6 )	
			ItemSimilarOption = "ITEMSIMILAR6";		    	
		else{}	

		flagSearch = false;
		showSearch(ItemSimilarOption);	
		flagSearch = true;			
	}


	@FXML
	/**
	 * EVENTO CLICK NO BOTAO QUE ABRE O BUSCADOR
	 */
	void onActionDadosAdicionais (ActionEvent event) 
	{	    
		Object evtSource = event.getSource();
		searchItemSimilarWithWnd(evtSource);		
	}	



	@FXML
	/**
	 * EVENTO keyPressed do Campo ItemSimilares
	 * Caso seja Pressionado F2 abre o buscador
	 * Caso seja pressionado ENTER tenta buscar o registro e troca o foco para o próximo campo
	 */
	void onKeyPressedDadosAdicionais(KeyEvent event)
	{				    
		Object evtSource = event.getSource();	    

		switch (event.getCode()) 
		{
		case F2:	
			searchItemSimilarWithWnd(evtSource);						
			break;	
		
		case ENTER:					
		case TAB:														
			flagSearch = false;

			if( evtSource == txtItemSimilar1 )

				if ( !txtItemSimilar1.getText().isEmpty() )
					searchItemSimilar(Integer.parseInt(txtItemSimilar1.getText()), txtItemSimilar1, txtItemSimilar1Fk);		
				else{}	

			else if( evtSource == txtItemSimilar2 )

				if (!txtItemSimilar2.getText().isEmpty())
					searchItemSimilar(Integer.parseInt(txtItemSimilar2.getText()), txtItemSimilar2, txtItemSimilar2Fk);		
				else{}	

			else if( evtSource == txtItemSimilar3 )	

				if (!txtItemSimilar3.getText().isEmpty())
					searchItemSimilar(Integer.parseInt(txtItemSimilar3.getText()), txtItemSimilar3, txtItemSimilar3Fk);		
				else{}	

			else if( evtSource == txtItemSimilar4 )	

				if (!txtItemSimilar4.getText().isEmpty())
					searchItemSimilar(Integer.parseInt(txtItemSimilar4.getText()), txtItemSimilar4, txtItemSimilar4Fk);		
				else{}	

			else if( evtSource == txtItemSimilar5 )	

				if (!txtItemSimilar5.getText().isEmpty())
					searchItemSimilar(Integer.parseInt(txtItemSimilar5.getText()), txtItemSimilar5, txtItemSimilar5Fk);		
				else{}	

			else if( evtSource == txtItemSimilar6 )	

				if (!txtItemSimilar6.getText().isEmpty())
					searchItemSimilar(Integer.parseInt(txtItemSimilar6.getText()), txtItemSimilar6, txtItemSimilar6Fk);		
				else{}	

			else{}		
			event.consume();
			break;			
		
		case SHIFT:

			break;	

		case DELETE:
			break;

		case BACK_SPACE:
			break;

		case LEFT:
			break;

		case RIGHT:
			break;
		default:
			break;

		}

		
	}	






	@SuppressWarnings("unchecked")
	@FXML
	/**
	 * EVENTO CLICK DO TOOGLE BOTAO LEGENDA (toggleLegenda) EXIBE O POUPOP COM
	 * AS LEGENDAS DE DADOS EXISTENTES NO TABLEVIEW
	 */
	void mouseClickedToggleHelp(MouseEvent event) {

		VBox vbox = new VBox();
		vbox.setPadding(new Insets(10));
		vbox.setSpacing(8);
		vbox.setAlignment(Pos.CENTER);

		TreeItem<String> itemCol = new TreeItem<String>(DadosGlobais.resourceBundle.getString("help.active.inactive"));
		itemCol.setExpanded(true);
		TreeItem<String> itemCol0 = new TreeItem<String>("1 - " + DadosGlobais.resourceBundle.getString("help.active"));
		TreeItem<String> itemCol1 = new TreeItem<String>("0 - " + DadosGlobais.resourceBundle.getString("help.inactive"));
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
	 * Método que preenche o formulario de edição dos dados do consultados através
	 * do método getById e getLast da camada DAO
	 * 
	 * @param flagLastRegistro
	 * 			</BR>
	 *            TRUE -> INDICA QUE O REGISTRO A SER CARREGADO É O ULTIMO CÓDIGO EXISTENTE
	 * @param codigo
	 * 			</BR>
	 *            CODIGO USADO NO FILTRO DA BUSCA DO REGISTRO
	 * @param flagDuplica
	 * 			</BR>
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
											
					entidadeBean = (Item) logRet.getObjeto();

					if (entidadeBean != null) {

						try{
							
						if (logRet.getStatus().equals(EnumLogRetornoStatus.REGDUPLICADO))
							util.alertException(logRet.getMsg(), "", false);

						resetFormValues();

						List<String> listTipoItem = new ArrayList<String>(Arrays.asList(entidadeBean.getTipoItem().split(",")));
												
						for (int i = 0; i < cBoxTipoItem.getItems().size(); i++) {
							
							for(String item : listTipoItem) {  
								
								if(((EnumTipoItem) cBoxTipoItem.getItems().get(i).getEnumOpcao()).codTipoItem.toString().equals(item.toString()))
									cBoxTipoItem.getCheckModel().check(cBoxTipoItem.getItems().get(i));
								
							} 
						}
						
												
						txtDescricao.setText(entidadeBean.getDescricao());
						txtCodFabricante.setText(entidadeBean.getFabricante().getCodigo().toString());
						txtFabricante.setText(entidadeBean.getFabricanteDescricao());
						txtCodGrupo.setText(entidadeBean.getGrupo().getCodigo().toString());
						txtGrupo.setText(entidadeBean.getGrupoDescricao());
						txtCodSubGrupo.setText(entidadeBean.getSubGrupo().getCodigo().toString());
						txtSubGrupo.setText(entidadeBean.getSubGrupoDescricao());
						txtCodDepartamento.setText(entidadeBean.getDepartamento() != null ? entidadeBean.getDepartamento().getCodigo().toString() : "");
						txtDepartamento.setText(entidadeBean.getDepartamentoDescricao());
						txtCodLcServico.setText(entidadeBean.getLcServicoCodigo());
						txtLcServico.setText(entidadeBean.getLcServicoDescricao());
						txtCodNcm.setText(entidadeBean.getNcmCodigo());
						txtNcm.setText(entidadeBean.getNcmDescricao());
						txtCodMoeda.setText(entidadeBean.getMoeda().getCodigo().toString());
						txtMoeda.setText(entidadeBean.getMoeda().getDescInteiroSingular());
						txtCodUnidade.setText(entidadeBean.getUnidade().getCodigo().toString());
						txtUnidade.setText(entidadeBean.getUnidadeDescricao());	
						txtCodUnidadeEmb.setText(entidadeBean.getUnidadeEmb().getCodigo().toString());
						txtUnidadeEmb.setText(entidadeBean.getUnidadeEmbDescricao());	
						txtCodFamiliaPreco.setText(entidadeBean.getFamiliaPreco() != null ? entidadeBean.getFamiliaPreco().getCodigo().toString() : "");
						txtFamiliaPreco.setText(entidadeBean.getFamiliaPrecoDescricao());

						txtDescReduzida.setText(entidadeBean.getDescReduzida());
						txtDescTecnica.setText(entidadeBean.getDescTecnica());
						txtCodCest.setText(entidadeBean.getCodCest());
						txtNumFabricante.setText(entidadeBean.getNumFrabricate());
						txtNumOriginal.setText(entidadeBean.getNumOriginal());
						txtQtdEmbalagemAtacado.setText(entidadeBean.getQtdEmbAtacado().toString());
						txtQtdEmbalagemCompra.setText(entidadeBean.getQtdEmbCompra().toString());
						txtQtdEmbalagemVenda.setText(entidadeBean.getQtdEmbVenda().toString());
						txtQtdLitros.setText(entidadeBean.getQtdLitros().toString());
						txtQtdPallet.setText(entidadeBean.getQtdPallet().toString());
						txtPesoBruto.setText(entidadeBean.getPesoBruto().toString());
						txtPesoLiquido.setText(entidadeBean.getPesoLiquido().toString());
						txtMedidaM3.setText(entidadeBean.getMedidaM3().toString());
						txtFatorConversao.setText(entidadeBean.getFatorConversao().toString());
						txtObservacao.setText(entidadeBean.getObservacao());
						txtQtdVolumes.setText(String.valueOf(entidadeBean.getQtdVolumes()));


						///--------------------Put entity data into its CORRESPONDING fields OF TAB DADOS ADICIONAIS---------------------------------- 

						//------ >>> Dados sobre Balança

						int FlagBalanca = entidadeBean.getFlagBalanca();
						
						if( FlagBalanca == 0 ){
							tgbtnFlagBalanca.setSelected(false);
							paneDadosBalanca.setDisable(true);	
						}
						else if( FlagBalanca == 1 ){
							tgbtnFlagBalanca.setSelected(true); 
							paneDadosBalanca.setDisable(false);
						}

						rbtnKg.setSelected(false);
						rbtnUnitary.setSelected(false);
						int BalacaTipoPreco = entidadeBean.getBalancaTipoPreco();

						if( BalacaTipoPreco == 0 )
							rbtnKg.setSelected(true);
						else if( BalacaTipoPreco == 1 )
							rbtnUnitary.setSelected(true);
						else{}

						txtInstrucoes.setText(entidadeBean.getBalancaInstrucoes()); 
						txtDiasValidade.setText(String.valueOf(entidadeBean.getBalancaDiasValidade()));	
						
						// Fim Dados Sobre Balança <<< ---------------
						
						int FlagDisponivelNet = entidadeBean.getFlagDisponivelNet(),
								FlagMobile = entidadeBean.getFlagMobile(),
								VeiculoFlag = entidadeBean.getVeiculoFlag(),
								FlagItemCompost = entidadeBean.getFlagItemComposto();

						if( FlagDisponivelNet == 0 )
							tgbtnDisponivelInternet.setSelected(false);
						else if( FlagDisponivelNet == 1 )
							tgbtnDisponivelInternet.setSelected(true); 
						else{}			

						if( FlagMobile == 0 )
							tgbtnParametrosMobile.setSelected(false);
						else if( FlagMobile == 1 )
							tgbtnParametrosMobile.setSelected(true); 
						else{}			

						if( VeiculoFlag == 1 )						    	
							tgbtnFlagVeiculo.setSelected(true);
						else
							tgbtnFlagVeiculo.setSelected(false);

						paneDadosVeiculo.setDisable(!tgbtnFlagVeiculo.isSelected());

						if( FlagItemCompost == 0 )
							tgbtnProductoComposto.setSelected(false);
						else if( FlagItemCompost == 1 )
							tgbtnProductoComposto.setSelected(true); 
						else{}		

						selectCboxFieldIndex(cboxProceProposVenda, entidadeBean.getProcedimentoEstoqueNegativo());	

						txtObservacoesAdicionis.setText(entidadeBean.getDadosAdicionais());
						txtCodigoANP.setText(entidadeBean.getCodigoAnp());
						txtPercentualGasNatural.setText(String.valueOf(entidadeBean.getPercentualGasAnp()));


						txtItemSimilar1.setText(entidadeBean.getItemSimilar1() != null ? entidadeBean.getItemSimilar1().getCodigo().toString() : "");
						txtItemSimilar1Fk.setText(entidadeBean.getItemSimilar1Descricao());

						txtItemSimilar2.setText(entidadeBean.getItemSimilar2() != null ? entidadeBean.getItemSimilar2().getCodigo().toString() : "");
						txtItemSimilar2Fk.setText(entidadeBean.getItemSimilar2Descricao());

						txtItemSimilar3.setText(entidadeBean.getItemSimilar3() != null ? entidadeBean.getItemSimilar3().getCodigo().toString() : "");
						txtItemSimilar3Fk.setText(entidadeBean.getItemSimilar3Descricao());

						txtItemSimilar4.setText(entidadeBean.getItemSimilar4() != null ? entidadeBean.getItemSimilar4().getCodigo().toString() : "");
						txtItemSimilar4Fk.setText(entidadeBean.getItemSimilar4Descricao());

						txtItemSimilar5.setText(entidadeBean.getItemSimilar5() != null ? entidadeBean.getItemSimilar5().getCodigo().toString() : "");
						txtItemSimilar5Fk.setText(entidadeBean.getItemSimilar5Descricao());

						txtItemSimilar6.setText(entidadeBean.getItemSimilar6() != null ? entidadeBean.getItemSimilar6().getCodigo().toString() : "");
						txtItemSimilar6Fk.setText(entidadeBean.getItemSimilar6Descricao());							

						///--------------------Put entity data into its CORRESPONDING fields OF TAB DADOS D VEICULO---------------------------------- 					

						txtNoChassi.setText(entidadeBean.getVeiculoChassi());				     		
						txtNoSerie.setText(entidadeBean.getVeiculoNoSerie());
						txtNoMotor.setText(entidadeBean.getVeiculoNoMotor());

						txtRenavam.setText(entidadeBean.getVeiculoRenavam());
						txtCodMarcaModelo.setText(entidadeBean.getVeiculoCodMarcaModelo());

						txtAnoFab.setText(String.valueOf(entidadeBean.getVeiculoAnoFabricacao()));
						txtAnoMod.setText(String.valueOf(entidadeBean.getVeiculoAnoModelo()));

						selectCboxFieldIndex(cbCor, entidadeBean.getVeiculoCor());						    
						selectCboxFieldIndex(cbTipoCombustivel, entidadeBean.getVeiculoTipoCombustivel());
						selectCboxFieldIndex(cbTipoPintura, entidadeBean.getVeiculoTipoPintura());
						selectCboxFieldIndex(cbTipoVeiculo, entidadeBean.getVeiculoTipo());						    
						selectCboxFieldIndex(cbEspVeiculo, entidadeBean.getVeiculoEspecie());
						selectCboxFieldIndex(cbCondicaoVin, entidadeBean.getVeiculoCondicaoVin());
						selectCboxFieldIndex(cbCondicaoDoVeiculo, entidadeBean.getVeiculoCondicao());
						selectCboxFieldIndex(cbRestricao, entidadeBean.getVeiculoRestricao());							
						txtLotacao.setText(String.valueOf(entidadeBean.getVeiculoLotacao())); 
						txtPotMotor.setText(entidadeBean.getVeiculoPotencia().toString());						    
						txtCm3.setText(entidadeBean.getVeiculoCm3().toString());
						txtPesoBruto1.setText(entidadeBean.getVeiculoPesoBruto().toString());
						txtLiquido.setText(entidadeBean.getVeiculoPesoLiquido().toString());
						txtDistanciaEixos.setText(entidadeBean.getVeiculoDistanciaEixos().toString());	
						txtCmkg.setText(entidadeBean.getVeiculoCmkg().toString());

						if( entidadeBean.getVeiculoFlagEnviaVenda() == 1 )
							sbEddvpModulo.setSelected(true);
						else
							sbEddvpModulo.setSelected(false);

						//----------------------------------------------						

						tbViewCodBarras.getItems().addAll(FXCollections.observableArrayList(entidadeBean.getItemCodBars()));

						tbViewPosicaoEstoque.getItems().addAll(FXCollections.observableArrayList(entidadeBean.getItemValors()));

						tbViewPrecosEmp.getItems().addAll(FXCollections.observableArrayList(entidadeBean.getItemValors()));

						tbViewItemFornecedor.getItems().addAll(FXCollections.observableArrayList(entidadeBean.getItemFornecedor()));
						
						//tbViewEstoqueDepositos.getItems().addAll(FXCollections.observableArrayList(entidadeBean.getItemEstoqueDeposito()));

						ItemCodBar e;
						listCodBars.clear();

						for (int i = 0; i < tbViewCodBarras.getItems().size(); i++) {
							e = new ItemCodBar();
							e = tbViewCodBarras.getItems().get(i).clone();
							listCodBars.add(e);
						}

						txtDescricao.selectEnd();

						tbViewPosicaoEstoque.getSelectionModel().select(0);
						tbViewPrecosEmp.getSelectionModel().select(0);

						if (flagDuplica) {
							try{
							
							Grupo g = entidadeBean.getGrupo();
							Fabricante f = entidadeBean.getFabricante();
							SubGrupo sg = entidadeBean.getSubGrupo();
							Moeda m = entidadeBean.getMoeda();
							Unidade uni = entidadeBean.getUnidade();
							Unidade uniEmb = entidadeBean.getUnidadeEmb();
							Departamento depto = entidadeBean.getDepartamento();
							TabelaNCM ncm = entidadeBean.getNcm();
							TabelaLcServico lc = entidadeBean.getLcServico();
							FamiliaPreco fm = entidadeBean.getFamiliaPreco();
							Item is1 = entidadeBean.getItemSimilar1();
							Item is2 = entidadeBean.getItemSimilar2();
							Item is3 = entidadeBean.getItemSimilar3();
							Item is4 = entidadeBean.getItemSimilar4();
							Item is5 = entidadeBean.getItemSimilar5();
							Item is6 = entidadeBean.getItemSimilar6();
							
							entidadeBean = new Item();
							txtCodigo.setDisable(true);
							txtCodigo.setText("+1");

							
							btnInsert.setDisable(true);
							txtCodigo.setDisable(true);
							btnCancel.setDisable(false);
							btnSave.setDisable(false);
							txtDescricao.requestFocus();
							
							tbViewCodBarras.getItems().clear();
							tbViewEstoqueDepositos.getItems().clear();
							tbViewPrecosEmp.getItems().clear();
							tbViewPosicaoEstoque.getItems().clear();
							tbViewAlteracaoPreco.getItems().clear();
							
							if(g != null)
								entidadeBean.setGrupo(g);
							if(sg != null)
								entidadeBean.setSubGrupo(sg);
							if(f != null)
								entidadeBean.setFabricante(f);
							if(m != null)
								entidadeBean.setMoeda(m);
							if(uni != null)
								entidadeBean.setUnidade(uni);
							if(uniEmb != null)
								entidadeBean.setUnidadeEmb(uniEmb);
							
							if(depto != null){
								entidadeBean.setDepartamento(depto);
								entidadeBean.setCodDepartamento(depto.getCodigo());
								entidadeBean.setCodDepartamentoFk(depto.getCheckDelete());
							}
							
							if(fm != null){
								entidadeBean.setFamiliaPreco(fm);
								entidadeBean.setCodFamiliaPreco(fm.getCodigo());
								entidadeBean.setCodFamiliaPrecoFk(fm.getCheckDelete());
							}
							
							if(ncm != null){
								entidadeBean.setNcm(ncm);
								entidadeBean.setCodNcm(ncm.getCodigo());
								entidadeBean.setCodNcmFk(ncm.getCheckDelete());
								entidadeBean.setNcmCodigo(ncm.getCodNCM());
							}
							
							if(lc != null){
								entidadeBean.setLcServico(lc);
								entidadeBean.setCodLcServico(lc.getCodigo());
								entidadeBean.setCodLcServicoFk(lc.getCheckDelete());
								entidadeBean.setLcServicoCodigo(lc.getCodLcnfse());
							}
							
							if(is1 != null){
								entidadeBean.setItemSimilar1(is1);
								entidadeBean.setCodItemSimilar1(is1.getCodigo());
								entidadeBean.setCodItemSimilar1Fk(is1.getCheckDelete());
							}
							
							if(is2 != null){
								entidadeBean.setItemSimilar2(is2);
								entidadeBean.setCodItemSimilar2(is2.getCodigo());
								entidadeBean.setCodItemSimilar2Fk(is2.getCheckDelete());
							}
							
							if(is3 != null){
								entidadeBean.setItemSimilar3(is3);
								entidadeBean.setCodItemSimilar3(is3.getCodigo());
								entidadeBean.setCodItemSimilar3Fk(is3.getCheckDelete());
							}
							
							if(is4 != null){
								entidadeBean.setItemSimilar4(is4);
								entidadeBean.setCodItemSimilar4(is4.getCodigo());
								entidadeBean.setCodItemSimilar4Fk(is4.getCheckDelete());
							}
							
							if(is5 != null){
								entidadeBean.setItemSimilar5(is5);
								entidadeBean.setCodItemSimilar5(is5.getCodigo());
								entidadeBean.setCodItemSimilar5Fk(is5.getCheckDelete());
							}
							
							if(is6 != null){
								entidadeBean.setItemSimilar6(is6);
								entidadeBean.setCodItemSimilar6(is6.getCodigo());
								entidadeBean.setCodItemSimilar6Fk(is6.getCheckDelete());
							}
							
							setRowsItemValores();
							
							}catch(Exception r){
								
							}
							
						} else {

							
							txtCodigo.setText(String.valueOf(entidadeBean.getCodigo()));

							if (!flagLastRegistro && !flagDuplica) {

								btnInsert.setDisable(true);
								txtCodigo.setDisable(true);
								btnCancel.setDisable(false);
								btnSave.setDisable(false);
								btnClone.setDisable(false);
								txtDescricao.requestFocus();

							}

							if (flagLastRegistro && !flagDuplica) {

								btnInsert.setDisable(false);
								btnCancel.setDisable(true);
								btnSave.setDisable(true);
								btnClone.setDisable(true);
								txtCodigo.setDisable(false);
								txtCodigo.requestFocus();
							}
							
						}
						}
						catch (Exception e2) {
							
							util.showAlert("Erro ao Carregar Informações!","error");
							resetFormValues();
							txtCodigo.requestFocus();
						}
					} else {

						util.showAlert(DadosGlobais.resourceBundle.getString("alertErrorCodigoNull"),"error");
						btnSave.setDisable(true);
						btnClone.setDisable(true);
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
							"[ ItemController.loadByID() ]");
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
			stg = ProgressBarForm.showProgressBar(UsuarioController.class, TarefaRefresh,DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
			pBar.setProgress(-1);

		} else {
			util.showAlert(DadosGlobais.resourceBundle.getString("alertErrorCodigoNull"),"error");
			Util.limpar(txtDescricao);
			txtCodigo.requestFocus();
		}
	}

	/**
	 * METODO QUE CRIA UMA TAREFA PARA ATUALIZAR AS LINHAS DO TABLEVIEW QUE
	 * SOFRERAM ALTERAÇÃO
	 * 
	 * @param flagOperacao
	 * 			<BR>
	 *            INSERT -> EM CASO DE INSERCAO DE REGISTRO NOVO CRIA O REGISTRO NA LISTA 
	 *          <BR>
	 *            UPDATE -> EM CASO DE UMA ALTERACAO DE UM REGISTRO QUE
	 *            JÁ EXISTE, ALTERA OS VALROES DA LINHA NO TABLEVIEW 
	 *          <BR>
	 *            DELETE -> EM CASO DE EXCLUSAO DE UM REGISTRO, ALTERA OS VALROES DA LINHA
	 *            NO TABLEVIEW
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
						"[ ItemController.updateTbView() ]");
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
		stg = ProgressBarForm.showProgressBar(UsuarioController.class, TarefaAtualiza,
				DadosGlobais.resourceBundle.getString("infAtualizaTbView"), false);
		pBar.setProgress(-1);

	}

	/**
	 * METODO QUE CRIA UMA TAREFA PARA A BUSCA DE DADOS
	 * 
	 * @param tipoConsulta
	 * 			</BR>
	 *          FILTER -> FAZ A CONSULTA DOS REGISTROS FILTRANDO DADOS
	 *          FILTRANDO OS REGISTROS USANDO AS INFORMAÇOES DO PAINEL DE FILTRO;</BR>
	 *          FILTRO ALL -> FAZ A CONSULTA DE TODOS OS REGISTROS ATIVOS</BR>
	 * @param flagRefresh
	 * 			</BR>
	 *          TRUE -> RECARREGA O TABLEVIEW APÓS EFETUAR A CONSULTA DOS REGISTROS </BR>
	 *          FALSE -> NÃO RECARREGA O TABLEVIEW APÓS A CONSULTA DOS REGISTROS
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

					if (txtFilterColumn.getText().isEmpty())
					{

						listaRegistros = FXCollections.observableArrayList(entidadeDao.filterByColumn(
								cboxFilterColumn.getValue().getField(), txtFilterColumn.getText(),
								cboxFilterColumn.getValue().getAction(), paramFlagAtivo));

					}
					else 
					{

						if (cboxFilterColumn.getValue().getAction().equals(1)) 
						{

							boolean temp = true;
							for (int i = 0; i < txtFilterColumn.getText().length(); i++)
								if (!Character.toString(txtFilterColumn.getText().charAt(i)).matches("[0-9]")) 
								{
									temp = false;
									break;
								}

							if (temp && txtFilterColumn.getText().length() <= 8)
								parametroBusca = txtFilterColumn.getText();
							else
								parametroBusca = "0";

						} 
						else if (cboxFilterColumn.getValue().getAction().equals(2)) 
						{
							parametroBusca = txtFilterColumn.getText();
						}

						listaRegistros = FXCollections.observableArrayList(
								entidadeDao.filterByColumn(cboxFilterColumn.getValue().getField(), parametroBusca,
										cboxFilterColumn.getValue().getAction(), paramFlagAtivo));
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
						"[ ItemController.tafefaConsulta() ]");
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
		stg = ProgressBarForm.showProgressBar(UsuarioController.class, TarefaRefresh,DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
		pBar.setProgress(-1);

	}

	/**
	 * METODO QUE CRIA UMA TAREFA PARA A BUSCA DE DADOS
	 * 
	 * @param tipoConsulta
	 *            FILTER -> FAZ A CONSULTA DOS REGISTROS FILTRANDO DADOS
	 *            FILTRANDO OS REGISTROS USANDO AS INFORMAÇOES DO PAINEL DE
	 *            FILTRO ALL -> FAZ A CONSULTA DE TODOS OS REGISTROS ATIVOS
	 * @param flagRefresh
	 *            TRUE -> RECARREGA O TABLEVIEW COM APÓS EFETUAR A CONSULTA DOS
	 *            REGISTROS FALSE -> NÃO RECARREGA O TABLEVIEW APÓS A CONSULTA
	 *            DOS REGISTROS
	 */
	public void searchFabricante(int valorBusca) {

		Task<String> TarefaRefresh = new Task<String>() {

			LogRetorno logRet = new LogRetorno();
			FabricanteDAO fabricanteDAO = new FabricanteDAO();

			@Override
			protected String call() throws Exception {

				logRet = fabricanteDAO.getById(valorBusca);
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				pBar.setProgress(1);
				if (logRet.getStatus().equals(EnumLogRetornoStatus.ERRO)) {
					Util.setStyleError(true, txtCodFabricante);
					txtCodFabricante.clear();
					txtFabricante.clear();
				} else {
					if (logRet.getStatus().equals(EnumLogRetornoStatus.REGDUPLICADO))
						util.alertException(logRet.getMsg(), "", false);
					if (logRet.getStatus().equals(EnumLogRetornoStatus.SUCESSO)) {
						txtFabricante.setText(((Fabricante) logRet.getObjeto()).getDescricao());
						entidadeBean.setFabricante((Fabricante) logRet.getObjeto());
					}
				}
			}

			@Override
			protected void failed() {
				stg.close();
				util.tratamentoExcecao(exceptionProperty().getValue().toString(),
						"[ ItemController.searchFabricante() ]");
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
		stg = ProgressBarForm.showProgressBar(UsuarioController.class, TarefaRefresh,DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
		pBar.setProgress(-1);

	}

	/**
	 * METODO QUE CRIA UMA TAREFA PARA A BUSCA DE DADOS
	 * 
	 * @param tipoConsulta
	 *            FILTER -> FAZ A CONSULTA DOS REGISTROS FILTRANDO DADOS
	 *            FILTRANDO OS REGISTROS USANDO AS INFORMAÇOES DO PAINEL DE
	 *            FILTRO ALL -> FAZ A CONSULTA DE TODOS OS REGISTROS ATIVOS
	 * @param flagRefresh
	 *            TRUE -> RECARREGA O TABLEVIEW COM APÓS EFETUAR A CONSULTA DOS
	 *            REGISTROS FALSE -> NÃO RECARREGA O TABLEVIEW APÓS A CONSULTA
	 *            DOS REGISTROS
	 */
	public void searchGrupo(int valorBusca) {

		Task<String> TarefaRefresh = new Task<String>() {

			LogRetorno logRet = new LogRetorno();
			GrupoDAO grupoDAO = new GrupoDAO();

			@Override
			protected String call() throws Exception {

				logRet = grupoDAO.getById(valorBusca);
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				pBar.setProgress(1);
				if (logRet.getStatus().equals(EnumLogRetornoStatus.ERRO)) {
					Util.setStyleError(true, txtCodGrupo);
					txtCodGrupo.clear();
					txtGrupo.clear();
				} else {
					if (logRet.getStatus().equals(EnumLogRetornoStatus.REGDUPLICADO))
						util.alertException(logRet.getMsg(), "", false);
					if (logRet.getStatus().equals(EnumLogRetornoStatus.SUCESSO)) {
						txtGrupo.setText(((Grupo) logRet.getObjeto()).getDescricao());
						entidadeBean.setGrupo((Grupo) logRet.getObjeto());
					}
				}
			}

			@Override
			protected void failed() {
				stg.close();
				util.tratamentoExcecao(exceptionProperty().getValue().toString(),
						"[ ItemController.searchFabricante() ]");
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
		stg = ProgressBarForm.showProgressBar(UsuarioController.class, TarefaRefresh,DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
		pBar.setProgress(-1);

	}

	/**
	 * METODO QUE CRIA UMA TAREFA PARA A BUSCA DE DADOS
	 * 
	 * @param tipoConsulta
	 *            FILTER -> FAZ A CONSULTA DOS REGISTROS FILTRANDO DADOS
	 *            FILTRANDO OS REGISTROS USANDO AS INFORMAÇOES DO PAINEL DE
	 *            FILTRO ALL -> FAZ A CONSULTA DE TODOS OS REGISTROS ATIVOS
	 * @param flagRefresh
	 *            TRUE -> RECARREGA O TABLEVIEW COM APÓS EFETUAR A CONSULTA DOS
	 *            REGISTROS FALSE -> NÃO RECARREGA O TABLEVIEW APÓS A CONSULTA
	 *            DOS REGISTROS
	 */
	public void searchSubGrupo(int valorBusca) {

		Task<String> TarefaRefresh = new Task<String>() {

			LogRetorno logRet = new LogRetorno();
			SubGrupoDAO subGrupoDAO = new SubGrupoDAO();

			@Override
			protected String call() throws Exception {

				logRet = subGrupoDAO.getById(valorBusca);
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				pBar.setProgress(1);
				if (logRet.getStatus().equals(EnumLogRetornoStatus.ERRO)) {
					Util.setStyleError(true, txtCodSubGrupo);
					txtCodSubGrupo.clear();
					txtSubGrupo.clear();
				} else {
					if (logRet.getStatus().equals(EnumLogRetornoStatus.REGDUPLICADO))
						util.alertException(logRet.getMsg(), "", false);
					if (logRet.getStatus().equals(EnumLogRetornoStatus.SUCESSO)) {
						txtSubGrupo.setText(((SubGrupo) logRet.getObjeto()).getDescricao());
						entidadeBean.setSubGrupo((SubGrupo) logRet.getObjeto()); 
					}
				}
			}

			@Override
			protected void failed() {
				stg.close();
				util.tratamentoExcecao(exceptionProperty().getValue().toString(),
						"[ ItemController.searchSubGrupo() ]");
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
		stg = ProgressBarForm.showProgressBar(UsuarioController.class, TarefaRefresh,DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
		pBar.setProgress(-1);

	}


	/**
	 * METODO QUE CRIA UMA TAREFA PARA A BUSCA DE DADOS
	 * 
	 * @param tipoConsulta
	 *            FILTER -> FAZ A CONSULTA DOS REGISTROS FILTRANDO DADOS
	 *            FILTRANDO OS REGISTROS USANDO AS INFORMAÇOES DO PAINEL DE
	 *            FILTRO ALL -> FAZ A CONSULTA DE TODOS OS REGISTROS ATIVOS
	 * @param flagRefresh
	 *            TRUE -> RECARREGA O TABLEVIEW COM APÓS EFETUAR A CONSULTA DOS
	 *            REGISTROS FALSE -> NÃO RECARREGA O TABLEVIEW APÓS A CONSULTA
	 *            DOS REGISTROS
	 */
	public void searchDepartamento(int valorBusca) {

		Task<String> TarefaRefresh = new Task<String>() {

			LogRetorno logRet = new LogRetorno();
			DepartamentoDAO departamentoDAO = new DepartamentoDAO();

			@Override
			protected String call() throws Exception {

				logRet = departamentoDAO.getById(valorBusca);
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				pBar.setProgress(1);
				if (logRet.getStatus().equals(EnumLogRetornoStatus.ERRO)) {
					Util.setStyleError(true, txtCodDepartamento);
					txtCodDepartamento.clear();
					txtDepartamento.clear();
				} else {
					if (logRet.getStatus().equals(EnumLogRetornoStatus.REGDUPLICADO))
						util.alertException(logRet.getMsg(), "", false);
					if (logRet.getStatus().equals(EnumLogRetornoStatus.SUCESSO)) {
						txtDepartamento.setText(((Departamento) logRet.getObjeto()).getDescricao());
						//entidadeBean.setDepartamento((Departamento) logRet.getObjeto());
						entidadeBean.setCodDepartamento(((Departamento) logRet.getObjeto()).getCodigo());
						entidadeBean.setCodDepartamentoFk(((Departamento) logRet.getObjeto()).getCheckDelete());
					}
				}
			}

			@Override
			protected void failed() {
				stg.close();
				util.tratamentoExcecao(exceptionProperty().getValue().toString(),
						"[ ItemController.searchDepartamento() ]");
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
		stg = ProgressBarForm.showProgressBar(UsuarioController.class, TarefaRefresh,DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
		pBar.setProgress(-1);

	}


	/**
	 * METODO QUE CRIA UMA TAREFA PARA A BUSCA DE DADOS
	 * 
	 * @param tipoConsulta
	 *            FILTER -> FAZ A CONSULTA DOS REGISTROS FILTRANDO DADOS
	 *            FILTRANDO OS REGISTROS USANDO AS INFORMAÇOES DO PAINEL DE
	 *            FILTRO ALL -> FAZ A CONSULTA DE TODOS OS REGISTROS ATIVOS
	 * @param flagRefresh
	 *            TRUE -> RECARREGA O TABLEVIEW COM APÓS EFETUAR A CONSULTA DOS
	 *            REGISTROS FALSE -> NÃO RECARREGA O TABLEVIEW APÓS A CONSULTA
	 *            DOS REGISTROS
	 */
	public void searchFamiliaPreco(int valorBusca) {

		Task<String> TarefaRefresh = new Task<String>() {

			LogRetorno logRet = new LogRetorno();
			FamiliaPrecoDAO familiaPrecoDAO = new FamiliaPrecoDAO();

			@Override
			protected String call() throws Exception {

				logRet = familiaPrecoDAO.getById(valorBusca);
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				pBar.setProgress(1);
				if (logRet.getStatus().equals(EnumLogRetornoStatus.ERRO)) {
					Util.setStyleError(true, txtCodFamiliaPreco);
					txtCodFamiliaPreco.clear();
					txtFamiliaPreco.clear();
				} else {
					if (logRet.getStatus().equals(EnumLogRetornoStatus.REGDUPLICADO))
						util.alertException(logRet.getMsg(), "", false);
					if (logRet.getStatus().equals(EnumLogRetornoStatus.SUCESSO)) {
						txtFamiliaPreco.setText(((FamiliaPreco) logRet.getObjeto()).getDescricao());
						entidadeBean.setCodFamiliaPreco(((FamiliaPreco)  logRet.getObjeto()).getCodigo());
						entidadeBean.setCodFamiliaPrecoFk(((FamiliaPreco)  logRet.getObjeto()).getCheckDelete());
					}
				}
			}

			@Override
			protected void failed() {
				stg.close();
				util.tratamentoExcecao(exceptionProperty().getValue().toString(),
						"[ ItemController.searchFamiliaPreco() ]");
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
		stg = ProgressBarForm.showProgressBar(UsuarioController.class, TarefaRefresh,DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
		pBar.setProgress(-1);

	}

	public void searchMoeda(int valorBusca) {

		Task<String> TarefaRefresh = new Task<String>() {

			LogRetorno logRet = new LogRetorno();
			MoedaDAO moedaDAO = new MoedaDAO();

			@Override
			protected String call() throws Exception {

				logRet = moedaDAO.getById(valorBusca);
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				pBar.setProgress(1);
				if (logRet.getStatus().equals(EnumLogRetornoStatus.ERRO)) {
					Util.setStyleError(true, txtCodMoeda);
					txtCodMoeda.clear();
					txtMoeda.clear();
				} else {
					if (logRet.getStatus().equals(EnumLogRetornoStatus.REGDUPLICADO))
						util.alertException(logRet.getMsg(), "", false);
					if (logRet.getStatus().equals(EnumLogRetornoStatus.SUCESSO)) {
						txtMoeda.setText(((Moeda) logRet.getObjeto()).getSimbolo());
						entidadeBean.setMoeda((Moeda) logRet.getObjeto());
					}
				}
			}

			@Override
			protected void failed() {
				stg.close();
				util.tratamentoExcecao(exceptionProperty().getValue().toString(),
						"[ ItemController.searchMoeda() ]");
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
		stg = ProgressBarForm.showProgressBar(UsuarioController.class, TarefaRefresh,DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
		pBar.setProgress(-1);

	}


	/**
	 * METODO QUE CRIA UMA TAREFA PARA A BUSCA DE DADOS
	 * 
	 * @param valorBusca - valor procurado no banco de dados
	 * @param tipoUnidade
	 *            principal -> unidade principal
	 *            embalagem -> unidade embalagem
	 */
	public void searchUnidade(int valorBusca, TextField txtUnidade, TextField txtCodUnidade, String tipoUnidade) {

		Task<String> TarefaRefresh = new Task<String>() {

			LogRetorno logRet = new LogRetorno();
			UnidadeDAO unidadeDAO = new UnidadeDAO();

			@Override
			protected String call() throws Exception {

				logRet = unidadeDAO.getById(valorBusca);
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				pBar.setProgress(1);
				if (logRet.getStatus().equals(EnumLogRetornoStatus.ERRO)) {
					Util.setStyleError(true, txtCodUnidade);
					txtCodUnidade.clear();
					txtUnidade.clear();
				} else {
					if (logRet.getStatus().equals(EnumLogRetornoStatus.REGDUPLICADO))
						util.alertException(logRet.getMsg(), "", false);
					if (logRet.getStatus().equals(EnumLogRetornoStatus.SUCESSO)) {
						txtUnidade.setText(((Unidade) logRet.getObjeto()).getDescricao());

						if(tipoUnidade.equals("principal"))
							entidadeBean.setUnidade((Unidade) logRet.getObjeto());
						if(tipoUnidade.equals("embalagem"))
							entidadeBean.setUnidadeEmb((Unidade) logRet.getObjeto());
					}
				}
			}

			@Override
			protected void failed() {
				stg.close();
				util.tratamentoExcecao(exceptionProperty().getValue().toString(),
						"[ ItemController.searchUnidade() ]");
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
		stg = ProgressBarForm.showProgressBar(UsuarioController.class, TarefaRefresh,DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
		pBar.setProgress(-1);

	}


	/**
	 * METODO QUE CRIA UMA TAREFA PARA A BUSCA DE DADOS
	 * 
	 * @param tipoConsulta
	 *            FILTER -> FAZ A CONSULTA DOS REGISTROS FILTRANDO DADOS
	 *            FILTRANDO OS REGISTROS USANDO AS INFORMAÇOES DO PAINEL DE
	 *            FILTRO ALL -> FAZ A CONSULTA DE TODOS OS REGISTROS ATIVOS
	 * @param flagRefresh
	 *            TRUE -> RECARREGA O TABLEVIEW COM APÓS EFETUAR A CONSULTA DOS
	 *            REGISTROS FALSE -> NÃO RECARREGA O TABLEVIEW APÓS A CONSULTA
	 *            DOS REGISTROS
	 */
	public void searchLcServico(int valorBusca) {

		Task<String> TarefaRefresh = new Task<String>() {

			LogRetorno logRet = new LogRetorno();
			TabelaLcServicoDAO tabelaLcServicoDAO = new TabelaLcServicoDAO();

			@Override
			protected String call() throws Exception {

				logRet = tabelaLcServicoDAO.getById(valorBusca);
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				pBar.setProgress(1);
				if (logRet.getStatus().equals(EnumLogRetornoStatus.ERRO)) {
					Util.setStyleError(true, txtCodLcServico);
					txtCodLcServico.clear();
					txtLcServico.clear();
				} else {
					if (logRet.getStatus().equals(EnumLogRetornoStatus.REGDUPLICADO))
						util.alertException(logRet.getMsg(), "", false);
					if (logRet.getStatus().equals(EnumLogRetornoStatus.SUCESSO)) {
						txtLcServico.setText(((TabelaLcServico) logRet.getObjeto()).getDescricao());
						entidadeBean.setCodLcServico(((TabelaLcServico) logRet.getObjeto()).getCodigo());
						entidadeBean.setLcServicoCodigo(((TabelaLcServico) logRet.getObjeto()).getCodLcnfse());
						entidadeBean.setCodLcServicoFk(((TabelaLcServico) logRet.getObjeto()).getCheckDelete());
					}
				}
			}

			@Override
			protected void failed() {
				stg.close();
				util.tratamentoExcecao(exceptionProperty().getValue().toString(),
						"[ ItemController.searchLcServico() ]");
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
		stg = ProgressBarForm.showProgressBar(UsuarioController.class, TarefaRefresh,DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
		pBar.setProgress(-1);

	}


	/**
	 * METODO QUE CRIA UMA TAREFA PARA A BUSCA DE DADOS
	 * 
	 * @param tipoConsulta
	 *            FILTER -> FAZ A CONSULTA DOS REGISTROS FILTRANDO DADOS
	 *            FILTRANDO OS REGISTROS USANDO AS INFORMAÇOES DO PAINEL DE
	 *            FILTRO ALL -> FAZ A CONSULTA DE TODOS OS REGISTROS ATIVOS
	 * @param flagRefresh
	 *            TRUE -> RECARREGA O TABLEVIEW COM APÓS EFETUAR A CONSULTA DOS
	 *            REGISTROS FALSE -> NÃO RECARREGA O TABLEVIEW APÓS A CONSULTA
	 *            DOS REGISTROS
	 */
	public void searchNcm(String valorBusca) {

		Task<String> TarefaRefresh = new Task<String>() {

			LogRetorno logRet = new LogRetorno();
			TabelaNCMDAO tabelaNCMDAO = new TabelaNCMDAO();

			@Override
			protected String call() throws Exception {

				logRet = tabelaNCMDAO.getById(valorBusca);
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				pBar.setProgress(1);
				if (logRet.getStatus().equals(EnumLogRetornoStatus.ERRO)) {
					Util.setStyleError(true, txtCodNcm);
					txtCodNcm.clear();
					txtNcm.clear();
				} else {
					if (logRet.getStatus().equals(EnumLogRetornoStatus.REGDUPLICADO))
						util.alertException(logRet.getMsg(), "", false);
					if (logRet.getStatus().equals(EnumLogRetornoStatus.SUCESSO)) {
						txtNcm.setText(((TabelaNCM) logRet.getObjeto()).getDescricao());
						entidadeBean.setCodNcm((((TabelaNCM) logRet.getObjeto()).getCodigo()));
						entidadeBean.setCodNcmFk((((TabelaNCM) logRet.getObjeto()).getCheckDelete()));
						entidadeBean.setNcmCodigo((((TabelaNCM) logRet.getObjeto()).getCodNCM()));
					}
				}
			}

			@Override
			protected void failed() {
				stg.close();
				util.tratamentoExcecao(exceptionProperty().getValue().toString(),
						"[ ItemController.searchNcm() ]");
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
		stg = ProgressBarForm.showProgressBar(UsuarioController.class, TarefaRefresh,DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
		pBar.setProgress(-1);

	}


	/**
	 * METODO QUE CRIA UMA TAREFA PARA A BUSCA DE DADOS
	 * 
	 * @param tipoConsulta
	 *            FILTER -> FAZ A CONSULTA DOS REGISTROS FILTRANDO DADOS
	 *            FILTRANDO OS REGISTROS USANDO AS INFORMAÇOES DO PAINEL DE
	 *            FILTRO ALL -> FAZ A CONSULTA DE TODOS OS REGISTROS ATIVOS
	 * @param flagRefresh
	 *            TRUE -> RECARREGA O TABLEVIEW COM APÓS EFETUAR A CONSULTA DOS
	 *            REGISTROS FALSE -> NÃO RECARREGA O TABLEVIEW APÓS A CONSULTA
	 *            DOS REGISTROS
	 */
	public void searchTributacao(int valorBusca) {

		Task<String> TarefaRefresh = new Task<String>() {

			LogRetorno logRet = new LogRetorno();
			TributacaoDAO tributacaoDAO = new TributacaoDAO();

			@Override
			protected String call() throws Exception {

				logRet = tributacaoDAO.getById(valorBusca);
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				pBar.setProgress(1);
				if (logRet.getStatus().equals(EnumLogRetornoStatus.ERRO)) {
					Util.setStyleError(true, txtCodTributacao);
					txtCodTributacao.clear();
					txtTributacao.clear();
				} else {
					if (logRet.getStatus().equals(EnumLogRetornoStatus.REGDUPLICADO))
						util.alertException(logRet.getMsg(), "", false);
					if (logRet.getStatus().equals(EnumLogRetornoStatus.SUCESSO)) {

						txtTributacao.setText(((Tributacao) logRet.getObjeto()).getDescricao());
						//entidadeBean.getItemValors().get(i).setTributacao((Tributacao) logRet.getObjeto());
						tbViewPosicaoEstoque.getSelectionModel().getSelectedItem().setTributacao((Tributacao) logRet.getObjeto());
						tbViewPosicaoEstoque.getSelectionModel().getSelectedItem().setFlagChangeTributacaoItem(true);
						if(DadosGlobais.empresaLogada.getConfig().getCpaFlagTribautomaticafiliais().equals(1)){
							for (int i = 0; i < tbViewPosicaoEstoque.getItems().size(); i++) {
								tbViewPosicaoEstoque.getItems().get(i).setTributacao(((Tributacao) logRet.getObjeto()));
							}
						}
					}
				}
			}

			@Override
			protected void failed() {
				stg.close();
				util.tratamentoExcecao(exceptionProperty().getValue().toString(),
						"[ ItemController.searchTributacao() ]");
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
		stg = ProgressBarForm.showProgressBar(UsuarioController.class, TarefaRefresh,DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
		pBar.setProgress(-1);

	}

	public void searchFornecedor(int valorBusca) {

		Task<String> TarefaRefresh = new Task<String>() {

			LogRetorno logRet = new LogRetorno();
			FornecedorDAO fonecedorDAO = new FornecedorDAO();

			@Override
			protected String call() throws Exception {

				logRet = fonecedorDAO.getById(valorBusca);
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				pBar.setProgress(1);
				if (logRet.getStatus().equals(EnumLogRetornoStatus.ERRO)) {
					Util.setStyleError(true, txtCodFornecedor);
					txtCodFornecedor.clear();
					txtFornecedor.clear();
				} else {
					if (logRet.getStatus().equals(EnumLogRetornoStatus.REGDUPLICADO))
						util.alertException(logRet.getMsg(), "", false);
					if (logRet.getStatus().equals(EnumLogRetornoStatus.SUCESSO)) {
						txtFornecedor.setText(((Fornecedor) logRet.getObjeto()).getRazao());
						fornedorItem = ((Fornecedor) logRet.getObjeto());
					}
				}
			}

			@Override
			protected void failed() {
				stg.close();
				util.tratamentoExcecao(exceptionProperty().getValue().toString(),
						"[ ItemController.searchFornecedor() ]");
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
		stg = ProgressBarForm.showProgressBar(UsuarioController.class, TarefaRefresh,DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
		pBar.setProgress(-1);

	}


	//----------------------------------------------SEARCH ITEMS SIMILARES-----------------------------------------

	/**
	 * METODO QUE CRIA UMA TAREFA PARA A BUSCA DE DADOS
	 * 
	 * @param tipoConsulta
	 *            FILTER -> FAZ A CONSULTA DOS REGISTROS FILTRANDO DADOS
	 *            FILTRANDO OS REGISTROS USANDO AS INFORMAÇOES DO PAINEL DE
	 *            FILTRO ALL -> FAZ A CONSULTA DE TODOS OS REGISTROS ATIVOS
	 * @param flagRefresh
	 *            TRUE -> RECARREGA O TABLEVIEW COM APÓS EFETUAR A CONSULTA DOS
	 *            REGISTROS FALSE -> NÃO RECARREGA O TABLEVIEW APÓS A CONSULTA
	 *            DOS REGISTROS
	 */
	@SuppressWarnings("rawtypes")
	public void searchItemSimilar(int valorBusca, CustomTextField txtCod, CustomTextField txtDesc ) 
	{

		Task<String> TarefaRefresh = new Task<String>() 
		{
			LogRetorno logRet = new LogRetorno();
			ItemDAO itemDAO = new ItemDAO();

			@Override
			protected String call() throws Exception 
			{

				logRet = itemDAO.getById(valorBusca);
				return "-";
			}

			@Override
			protected void succeeded() 
			{
				stg.close();
				pBar.setProgress(1);
				if (logRet.getStatus().equals(EnumLogRetornoStatus.ERRO) ) 
				{
					Util.setStyleError(true, txtCod);
					txtCod.clear();
					txtDesc.clear();
				} 
				else 
				{				

					if (logRet.getStatus().equals(EnumLogRetornoStatus.REGDUPLICADO))
						util.alertException(logRet.getMsg(), "", false);
					if (logRet.getStatus().equals(EnumLogRetornoStatus.SUCESSO)) 
					{

						Item itemObj =  (Item) logRet.getObjeto();	
						int itemCodsimilar = itemObj.getCodigo();
						BigInteger itemCodsimilarFK = itemObj.getCheckDelete();

						if( itemCodsimilar != entidadeBean.getCodigo() )
						{	

							txtDesc.setText(itemObj.getDescricao());		

							if( txtCod == txtItemSimilar1 && Util.differentText(itemCodsimilar, txtItemSimilar2,txtItemSimilar3,txtItemSimilar4,txtItemSimilar5,txtItemSimilar6) )
							{
								entidadeBean.setCodItemSimilar1(itemCodsimilar);
								entidadeBean.setCodItemSimilar1Fk(itemCodsimilarFK);
								txtItemSimilar2.requestFocus();
							}
							else if( txtCod == txtItemSimilar2 && Util.differentText(itemCodsimilar, txtItemSimilar1,txtItemSimilar3,txtItemSimilar4,txtItemSimilar5,txtItemSimilar6) )
							{
								entidadeBean.setCodItemSimilar2(itemCodsimilar);
								entidadeBean.setCodItemSimilar2Fk(itemCodsimilarFK);
								txtItemSimilar3.requestFocus();
							}
							else if( txtCod == txtItemSimilar3 && Util.differentText(itemCodsimilar, txtItemSimilar1,txtItemSimilar2,txtItemSimilar4,txtItemSimilar5,txtItemSimilar6) )
							{
								entidadeBean.setCodItemSimilar3(itemCodsimilar);
								entidadeBean.setCodItemSimilar3Fk(itemCodsimilarFK);
								txtItemSimilar4.requestFocus();
							}
							else if( txtCod == txtItemSimilar4 && Util.differentText(itemCodsimilar, txtItemSimilar1,txtItemSimilar2,txtItemSimilar3,txtItemSimilar5,txtItemSimilar6) )
							{
								entidadeBean.setCodItemSimilar4(itemCodsimilar);
								entidadeBean.setCodItemSimilar4Fk(itemCodsimilarFK);
								txtItemSimilar5.requestFocus();
							}
							else if( txtCod == txtItemSimilar5 && Util.differentText(itemCodsimilar, txtItemSimilar1,txtItemSimilar2,txtItemSimilar3,txtItemSimilar4,txtItemSimilar6) )
							{
								entidadeBean.setCodItemSimilar5(itemCodsimilar);
								entidadeBean.setCodItemSimilar5Fk(itemCodsimilarFK);
								txtItemSimilar6.requestFocus();
							}
							else if( txtCod == txtItemSimilar6 && Util.differentText(itemCodsimilar, txtItemSimilar1,txtItemSimilar2,txtItemSimilar3,txtItemSimilar4,txtItemSimilar5)  )
							{
								entidadeBean.setCodItemSimilar6(itemCodsimilar);
								entidadeBean.setCodItemSimilar6Fk(itemCodsimilarFK);
								txtItemSimilar1.requestFocus();
							}
							else
							{

								/*Util.setStyleError(true, txtCod);
										txtCod.clear();
										txtDesc.clear();*/	
							}									
						}
						else
						{
							/*Util.setStyleError(true, txtCod);
									txtCod.clear();
									txtDesc.clear();*/								
						}
					}
					else{}
				}
			}

			@Override
			protected void failed() 
			{
				stg.close();
				util.tratamentoExcecao(exceptionProperty().getValue().toString(),"[ ItemController.searchItemSimilar() ]");
				pBar.setProgress(0);
			}

			@Override
			protected void cancelled() 
			{
				tbView.getItems().clear();
				pBar.setProgress(0);
				super.cancelled();
			}

		};

		Thread t = new Thread(TarefaRefresh);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(UsuarioController.class, TarefaRefresh,DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
		pBar.setProgress(-1);

	}





	//------------------------------------END SEARCH ITEMS SIMILARES-----------------------------------------	


	public void showSearch(String actionFrom)
	{

		ObservableList<ComboBoxFilter> list = FXCollections.observableArrayList();

		list.add(new ComboBoxFilter("Código", 1, "codigo"));
		list.add(new ComboBoxFilter("Descrição", 2, "descricao"));
		list.add(new ComboBoxFilter("Cód Empresa", 3, "codemp"));

		switch (actionFrom) 
		{
		case "FABRICANTE":

			Object obj = util.showSearchGetParameters("Fabricantes", "descricao", FabricanteDAO.class,list);

			if (obj != null) 
			{
				entidadeBean.setFabricante((Fabricante) obj);
				txtCodFabricante.setText(String.valueOf(((Fabricante) obj).getCodigo()));
				txtFabricante.setText(((Fabricante) obj).getDescricao());
				txtCodGrupo.requestFocus();
			}
			break;

		case "GRUPO":

			Object obj2 = util.showSearchGetParameters("Grupos", "descricao", GrupoDAO.class,list);

			if (obj2 != null) 
			{
				entidadeBean.setGrupo((Grupo) obj2);
				txtCodGrupo.setText(String.valueOf(((Grupo) obj2).getCodigo()));
				txtGrupo.setText(((Grupo) obj2).getDescricao());
				txtCodSubGrupo.requestFocus();
			}
			break;

		case "SUBGRUPO":

			Object obj3 = util.showSearchGetParameters("Sub-Grupos", "descricao", SubGrupoDAO.class,list);

			if (obj3 != null) 
			{
				entidadeBean.setSubGrupo((SubGrupo) obj3);
				txtCodSubGrupo.setText(String.valueOf(((SubGrupo) obj3).getCodigo()));
				txtSubGrupo.setText(((SubGrupo) obj3).getDescricao());
				txtCodDepartamento.requestFocus();
			}
			break;

		case "DEPARTAMENTO":

			Object obj4 = util.showSearchGetParameters("Departamentos", "descricao", DepartamentoDAO.class,list);

			if (obj4 != null) 
			{
				//entidadeBean.setDepartamento((Departamento) obj4);
				entidadeBean.setCodDepartamento(((Departamento) obj4).getCodigo());
				entidadeBean.setCodDepartamentoFk(((Departamento) obj4).getCheckDelete());
				txtCodDepartamento.setText(String.valueOf(((Departamento) obj4).getCodigo()));
				txtDepartamento.setText(((Departamento) obj4).getDescricao());
				txtCodLcServico.requestFocus();
			}
			break;

		case "LCSERVICO":

			list.clear();
			list.add(new ComboBoxFilter("Código", 1, "codigo"));
			list.add(new ComboBoxFilter("Descrição", 2, "descricao"));
			list.add(new ComboBoxFilter("Cód LC Serviço", 3, "codLcnfse"));


			Object obj5 = util.showSearchGetParameters("Tabela LC-Serviços", "descricao", TabelaLcServicoDAO.class,list);

			if (obj5 != null) 
			{
				//entidadeBean.setLcServico((TabelaLcServico) obj5);
				entidadeBean.setCodLcServico(((TabelaLcServico) obj5).getCodigo());
				entidadeBean.setCodLcServicoFk(((TabelaLcServico) obj5).getCheckDelete());
				entidadeBean.setLcServicoCodigo(((TabelaLcServico) obj5).getCodLcnfse());
				txtCodLcServico.setText(String.valueOf(((TabelaLcServico) obj5).getCodigo()));
				txtLcServico.setText(((TabelaLcServico) obj5).getDescricao());
				txtCodNcm.requestFocus();
			}
			break;

		case "NCM":

			list.clear();
			list.add(new ComboBoxFilter("Código", 1, "codigo"));
			list.add(new ComboBoxFilter("Descrição", 2, "descricao"));
			list.add(new ComboBoxFilter("Cód NCM", 3, "codNCM"));

			Object obj6 = util.showSearchGetParameters("Tabela de NCM", "descricao", TabelaNCMDAO.class,list);

			if (obj6 != null) {
				//entidadeBean.setNcm((TabelaNCM) obj6);
				entidadeBean.setCodNcm((((TabelaNCM) obj6).getCodigo()));
				entidadeBean.setCodNcmFk((((TabelaNCM) obj6).getCheckDelete()));
				entidadeBean.setNcmCodigo((((TabelaNCM) obj6).getCodNCM()));
				txtCodNcm.setText(((TabelaNCM) obj6).getCodNCM());
				txtNcm.setText(((TabelaNCM) obj6).getDescricao());
				txtCodFamiliaPreco.requestFocus();
			}
			break;

		case "FAMILIAPRECO":

			Object obj7 = util.showSearchGetParameters("Famílias de Preços", "descricao", FamiliaPrecoDAO.class,list);

			if (obj7 != null) 
			{
				entidadeBean.setCodFamiliaPreco(((FamiliaPreco) obj7).getCodigo());
				entidadeBean.setCodFamiliaPrecoFk(((FamiliaPreco) obj7).getCheckDelete());
				txtCodFamiliaPreco.setText(String.valueOf(((FamiliaPreco) obj7).getCodigo()));
				txtFamiliaPreco.setText(((FamiliaPreco) obj7).getDescricao());
				txtCodMoeda.requestFocus();
			}
			break;


		case "MOEDA":

			list.clear();
			list.add(new ComboBoxFilter("Código", 1, "codigo"));
			list.add(new ComboBoxFilter("Descrição", 2, "descInteiroSingular"));
			list.add(new ComboBoxFilter("Símbolo", 3, "simbolo"));
			list.add(new ComboBoxFilter("Cód Empresa", 3, "codemp"));

			Object obj8 = util.showSearchGetParameters("Moedas", "descInteiroSingular", MoedaDAO.class,list);

			if (obj8 != null) 
			{
				entidadeBean.setMoeda((Moeda) obj8);
				txtCodMoeda.setText(String.valueOf(((Moeda) obj8).getCodigo()));
				txtMoeda.setText(((Moeda) obj8).getSimbolo());
				txtCodUnidade.requestFocus();
			}
			break;

		case "UNIDADE":

			Object obj9 = util.showSearchGetParameters("Unidades", "descricao", UnidadeDAO.class,list);

			if (obj9 != null)
			{
				entidadeBean.setUnidade((Unidade) obj9);
				txtCodUnidade.setText(String.valueOf(((Unidade) obj9).getCodigo()));
				txtUnidade.setText(((Unidade) obj9).getDescricao());
				txtCodUnidadeEmb.requestFocus();
			}
			break;


		case "UNIDADEEMB":

			Object obj10 = util.showSearchGetParameters("Unidades", "descricao", UnidadeDAO.class,list);

			if (obj10 != null)
			{
				entidadeBean.setUnidadeEmb((Unidade) obj10);
				txtCodUnidadeEmb.setText(String.valueOf(((Unidade) obj10).getCodigo()));
				txtUnidadeEmb.setText(((Unidade) obj10).getDescricao());
				txtQtdPallet.requestFocus();
			}
			break;


		case "FORNECEDOR":

			list.clear();
			list.add(new ComboBoxFilter("Código", 1, "codigo"));
			list.add(new ComboBoxFilter("Razão Social", 2, "razao"));
			list.add(new ComboBoxFilter("Nome Fantasia", 3, "fantasia"));
			list.add(new ComboBoxFilter("Cód Empresa", 4, "codemp"));

			Object objForn = util.showSearchGetParameters("Fornecedores", "razao", FornecedorDAO.class,list);

			if (objForn != null) 
			{

				txtCodFornecedor.setText(String.valueOf(((Fornecedor) objForn).getCodigo()));
				txtFornecedor.setText(((Fornecedor) objForn).getRazao());
				fornedorItem = (Fornecedor) objForn;
				btnAddCodFornecedor.requestFocus();
			}
			break;

		case "ITEMSIMILAR1":

			Object obj11 = util.showSearchGetParameters("Items", "descricao", ItemDAO.class, list);

			if (obj11 != null) 
			{
				Item object = (Item) obj11;		

				int itemCod = object.getCodigo();

				if( itemCod != entidadeBean.getCodigo() && Util.differentText(itemCod, txtItemSimilar2,txtItemSimilar3,txtItemSimilar4,txtItemSimilar5,txtItemSimilar6))
				{						
					entidadeBean.setCodItemSimilar1(itemCod);
					entidadeBean.setCodItemSimilar1Fk(object.getCheckDelete());

					txtItemSimilar1.setText(String.valueOf(itemCod));
					txtItemSimilar1Fk.setText(object.getDescricao());
				}
				else{}

				txtItemSimilar2.requestFocus();
			}

			break;			

		case "ITEMSIMILAR2":

			Object obj12= util.showSearchGetParameters("Items", "descricao", ItemDAO.class, list);

			if (obj12 != null) 
			{
				Item object = (Item) obj12;	

				int itemCod = object.getCodigo();


				if( itemCod != entidadeBean.getCodigo() && Util.differentText(itemCod, txtItemSimilar1,txtItemSimilar3,txtItemSimilar4,txtItemSimilar5,txtItemSimilar6) )
				{	
					entidadeBean.setCodItemSimilar2(itemCod);
					entidadeBean.setCodItemSimilar2Fk(object.getCheckDelete());

					txtItemSimilar2.setText(String.valueOf(itemCod));
					txtItemSimilar2Fk.setText(object.getDescricao());

				}
				else{}	

				txtItemSimilar3.requestFocus();
			}

			break;		

		case "ITEMSIMILAR3":

			Object obj13= util.showSearchGetParameters("Items", "descricao", ItemDAO.class, list);

			if (obj13 != null) 
			{
				Item object = (Item) obj13;	

				int itemCod = object.getCodigo();

				if( itemCod != entidadeBean.getCodigo() && Util.differentText(itemCod, txtItemSimilar1,txtItemSimilar2,txtItemSimilar4,txtItemSimilar5,txtItemSimilar6) )
				{	

					entidadeBean.setCodItemSimilar3(itemCod);
					entidadeBean.setCodItemSimilar3Fk(object.getCheckDelete());

					txtItemSimilar3.setText(String.valueOf(itemCod));
					txtItemSimilar3Fk.setText(object.getDescricao());

				}
				else{}					

				txtItemSimilar4.requestFocus();
			}

			break;		

		case "ITEMSIMILAR4":

			Object obj14= util.showSearchGetParameters("Items", "descricao", ItemDAO.class, list);

			if (obj14 != null) 
			{
				Item object = (Item) obj14;	

				int itemCod = object.getCodigo();


				if( itemCod != entidadeBean.getCodigo() && Util.differentText(itemCod, txtItemSimilar1,txtItemSimilar2,txtItemSimilar3,txtItemSimilar5,txtItemSimilar6))
				{	

					entidadeBean.setCodItemSimilar4(itemCod);
					entidadeBean.setCodItemSimilar4Fk(object.getCheckDelete());

					txtItemSimilar4.setText(String.valueOf(itemCod));
					txtItemSimilar4Fk.setText(object.getDescricao());

				}
				else{}					


				txtItemSimilar5.requestFocus();
			}

			break;		

		case "ITEMSIMILAR5":

			Object obj15= util.showSearchGetParameters("Items", "descricao", ItemDAO.class, list);

			if (obj15 != null) 
			{
				Item object = (Item) obj15;	

				int itemCod = object.getCodigo();

				if( itemCod != entidadeBean.getCodigo() && Util.differentText(itemCod, txtItemSimilar1,txtItemSimilar2,txtItemSimilar3,txtItemSimilar4,txtItemSimilar6))
				{	

					entidadeBean.setCodItemSimilar5(itemCod);
					entidadeBean.setCodItemSimilar5Fk(object.getCheckDelete());

					txtItemSimilar5.setText(String.valueOf(itemCod));
					txtItemSimilar5Fk.setText(object.getDescricao());

				}
				else{}		

				txtItemSimilar6.requestFocus();
			}

			break;		

		case "ITEMSIMILAR6":

			Object obj16= util.showSearchGetParameters("Items", "descricao", ItemDAO.class, list);

			if (obj16 != null) 
			{
				Item object = (Item) obj16;			

				int itemCod = object.getCodigo();

				if( itemCod != entidadeBean.getCodigo() && Util.differentText(itemCod, txtItemSimilar1,txtItemSimilar2,txtItemSimilar3,txtItemSimilar4,txtItemSimilar5))
				{	
					entidadeBean.setCodItemSimilar6(itemCod);
					entidadeBean.setCodItemSimilar6Fk(object.getCheckDelete());

					txtItemSimilar6.setText(String.valueOf(itemCod));
					txtItemSimilar6Fk.setText(object.getDescricao());

				}
				else{}					

				txtItemSimilar1.requestFocus();
			}

			break;					


		case "TRIBUTACAO":

			Object obj17 = util.showSearchGetParameters("Tributação", "descricao", TributacaoDAO.class, list);

			if (obj17 != null) 
			{
				Tributacao trib = (Tributacao) obj17;			

				txtCodTributacao.setText(trib.getCodigo().toString());
				txtTributacao.setText(trib.getDescricao());
				tbViewPosicaoEstoque.getSelectionModel().getSelectedItem().setTributacao(trib);
				tbViewPosicaoEstoque.getSelectionModel().getSelectedItem().setFlagChangeTributacaoItem(true);
				
				if(DadosGlobais.empresaLogada.getConfig().getCpaFlagTribautomaticafiliais().equals(1)){
					for (int i = 0; i < tbViewPosicaoEstoque.getItems().size(); i++) {
						tbViewPosicaoEstoque.getItems().get(i).setTributacao(trib);
					}
				}

			}

			break;					


		default:
			break;

		}

	}


	public void setChangeItemValor(String origem){

		switch (origem) 
		{
		case "QTDMIN":
			tbViewPosicaoEstoque.getSelectionModel().getSelectedItem().setQtdMin(Util.decimalBRtoBigDecimal(2, txtQtdMinima.getText()));	
			break;

		case "QTDMAX":
			tbViewPosicaoEstoque.getSelectionModel().getSelectedItem().setQtdMax(Util.decimalBRtoBigDecimal(2, txtQtdMaxima.getText()));	
			break;

		case "LOCACAO":
			tbViewPosicaoEstoque.getSelectionModel().getSelectedItem().setLocacao(txtLocacao.getText());	
			break;

		default:
			break;

		}

		tbViewPosicaoEstoque.refresh();

		tbViewPosicaoEstoque.getSelectionModel().getSelectedItem().setFlagChangeItemValor(true);

	}


	public void setRowsItemValores(){
		ObservableList<ItemValor> valores = FXCollections.<ItemValor>observableArrayList();

		ItemValor iValor = null;

		for (int i = 0; i < Util.getCompartilhamentoEntidade(EnumCompartilhamento.ITENS).size(); i++) {
			iValor = new ItemValor();
			util.initializeAttribClass(iValor);				
			iValor.setCodemp(Util.getCompartilhamentoEntidade(EnumCompartilhamento.ITENS).get(i));
			valores.add(iValor);
		}

		tbViewPrecosEmp.getItems().addAll(valores);
		tbViewPosicaoEstoque.getItems().addAll(valores);
		tbViewPrecosEmp.getSelectionModel().selectFirst();
		tbViewPosicaoEstoque.getSelectionModel().selectFirst();

	}

	public void resetFormValues(){

		//LIMPAR CAMPOS TEXTFIELDS DO CADASTRO
		
		
			Util.limpar(txtCodigo, txtDescricao,  txtUnidadeEmb, txtLcServico, txtNcm, txtMoeda, txtQtdPallet, 
				txtDescReduzida, txtDescTecnica, txtNumFabricante, txtNumOriginal, txtCodCest, txtMedidaM3,
				txtPesoBruto, txtPesoLiquido, txtFatorConversao, txtQtdLitros, txtQtdEmbalagemCompra, txtQtdEmbalagemVenda, txtQtdEmbalagemAtacado,
				txtCodFabricante, txtCodGrupo, txtCodSubGrupo, txtCodDepartamento, txtCodUnidade, txtCodUnidadeEmb,
				txtCodLcServico, txtCodNcm, txtCodFamiliaPreco, txtCodMoeda, txtGrupo, txtSubGrupo, txtFabricante,
				txtDepartamento, txtUnidade, txtFamiliaPreco, txtCodTributacao, txtTributacao, txtQtdMaxima, txtQtdMinima,txtCodBar,
				txtReferenciaFornecedor, txtCodFornecedor, txtFornecedor,
				txtItemSimilar1,txtItemSimilar1Fk,txtItemSimilar2,txtItemSimilar2Fk,txtItemSimilar3,txtItemSimilar3Fk,
				txtItemSimilar4,txtItemSimilar4Fk,txtItemSimilar5,txtItemSimilar5Fk,txtItemSimilar6,txtItemSimilar6Fk
				);
			
				cBoxTipoItem.getCheckModel().clearChecks();
		
		
		txtObservacao.clear();
		
		Util.setDefaultStyle(txtDescricao, txtCodigo, txtFilterColumn, txtCodFabricante, txtCodNcm,	txtCodGrupo, txtCodSubGrupo, 
				txtGrupo, txtSubGrupo, txtFabricante, txtDepartamento, txtCodFamiliaPreco, txtCodUnidade, txtCodUnidadeEmb, 
				txtFamiliaPreco, txtUnidade, txtUnidadeEmb, txtCodTributacao,txtCodBar, txtQtdEmbCodBar,
				txtItemSimilar1,txtItemSimilar1Fk,txtItemSimilar2,txtItemSimilar2Fk,txtItemSimilar3,txtItemSimilar3Fk,
				txtItemSimilar4,txtItemSimilar4Fk,txtItemSimilar5,txtItemSimilar5Fk,txtItemSimilar6,txtItemSimilar6Fk
				);

	
		tbViewCodBarras.getItems().clear();
		tbViewPrecosEmp.getItems().clear();
		tbViewPosicaoEstoque.getItems().clear();
		tbViewItemFornecedor.getItems().clear();
		tbViewEstoqueDepositos.getItems().clear();

		txtQtdEmbalagemAtacado.setText("1,0000");
		txtQtdEmbalagemCompra.setText("1,0000");
		txtQtdEmbalagemVenda.setText("1,0000");
		txtFatorConversao.setText("1,0000");
		txtPesoLiquido.setText("0,0000");
		txtMedidaM3.setText("0,0000");
		txtPesoBruto.setText("0,0000");
		txtQtdPallet.setText("0,0000");
		txtMedidaM3.setText("0,0000");
		txtQtdLitros.setText("1,0000");
		txtQtdEmbCodBar.setText("1,0000");

	}

	/**
	 * METODO QUE FAZ A TRADUÇAO DOS ELEMENTOS CONTINDOS NO FORMULARIO
	 */
	public void setIdioma() {

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
		lblcboxFlagAtivo.setText(DadosGlobais.resourceBundle.getString("lblcboxFlagAtivo"));
		lblTotalLinhas.setText(DadosGlobais.resourceBundle.getString("lblTotalLinhas"));
		//lblDetCodigo.setText(DadosGlobais.resourceBundle.getString("lblDetCodigo"));
		//lblDetDesc.setText(DadosGlobais.resourceBundle.getString("lblDetDesc"));
		lbTitleFormCad.setText(DadosGlobais.resourceBundle.getString("lblTitleFormCad")+" "+DadosGlobais.resourceBundle.getString("miCadItem"));
		//tbColCodigo.setText(DadosGlobais.resourceBundle.getString("tbColCodigo"));
		//tbColDescricao.setText(DadosGlobais.resourceBundle.getString("tbColDescricao"));
		//tbColCodeEmp.setText(DadosGlobais.resourceBundle.getString("tbColCodeEmp"));
		//tbColAtivoInat.setText(DadosGlobais.resourceBundle.getString("tbColAtivoInat"));

		tbView.setPlaceholder(new Label(DadosGlobais.resourceBundle.getString("tbView")));

	}

	/**
	 * METODO PARA POPULAR O COMBOBOX DE TIPOS DE FILTRO (cboxFilterColumn)
	 */
	public void populateCboxFilterColumn() {
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
	public void populateCboxFlagAtivo() {
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
	 * ComboBox Tipo Item.
	 */
	public void populateCboxTipoItem() {
		for (EnumTipoItem item : EnumTipoItem.values())
			listTipoItem.add(new ComboBoxFilter(item.descTipoItem, 1 ,item));

		cBoxTipoItem.getItems().addAll(listTipoItem);
		


		cBoxTipoItem.setConverter(new StringConverter<ComboBoxFilter>() {
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

	// ----MÉTODOS RESPONSAVEIS POR CONFIGURAR AS COLUNAS DO TABLEVIEW	E GRAVAR AS CONFIGURACOES NO ARQUIVO XML PARA CADA USUARIO

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
	public void updateTableView(ObservableList visibleColumns, TableView<ItemDAO> table) {

		if (!visibleColumns.isEmpty()) {
			// hide all columns
			for (TableColumn<ItemDAO, ?> col : table.getColumns())
				col.setVisible(false);
			// show column and defines its position
			for (int i = 0; i < visibleColumns.size(); i++) {
				table.getColumns().remove(visibleColumns.get(i));
				table.getColumns().add(i, (TableColumn<ItemDAO, ?>) visibleColumns.get(i));
				table.getColumns().get(i).setVisible(true);
			}

		}

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
						TableColumn<Item, ?> tableColumn = tbView.getColumns().get(j);
						tbView.getColumns().remove(j);
						tbView.getColumns().add(userDefinedColumns.get(i).getPosition(), tableColumn);
						tableColumn.setPrefWidth(userDefinedColumns.get(i).getWidth());
						tableColumn.setVisible(true);
					}
				}
			}
		}
	}
	// ----MÉTODOS RESPONSAVEIS POR CONFIGURAR AS COLUNAS DO TABLEVIEW
	// -----------
	// -----E GRAVAR AS CONFIGURACOES NO ARQUIVO XML PARA CADA USUARIO
	// ------------
	// ------------------------ FIM --------------------------------------------

	// MÉTODO QUE ABRE A CAIXA DE OPCOES DE IMPRESSÃO E EXPORTAÇÃO DE DADOS
	@SuppressWarnings({ "rawtypes"})
	/**
	 * Show the form to configure the actions of the report, print and export to
	 * different formats.
	 * 
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	public void printExportShow() throws NoSuchFieldException, SecurityException {

		try {
			for (TableColumn column : tbView.getVisibleLeafColumns()) {

				try{

					Object type = Item.class.getDeclaredField(((PropertyValueFactory) column.getCellValueFactory()).getProperty()).getType().getSimpleName();

					if (type.equals("Timestamp"))
						type = "Date";

					tableShowPrintList.add(new TableConfPrint(column.getText(), column.getId(),((PropertyValueFactory) column.getCellValueFactory()).getProperty(), type.toString(),
							column.getWidth()));

				}catch(Exception e){
					tableShowPrintList.clear();
				}
			}

			stg = new Stage();
			Scene scene = new Scene(util.openWindow("/views/utils/viewPrintExport.fxml",
					new PrintExportController(tbView, tableShowPrintList, "Itens", pBar, stg)));

			stg.setScene(scene);
			stg.initStyle(StageStyle.TRANSPARENT);
			stg.initModality(Modality.APPLICATION_MODAL);
			stg.show();
		} catch (Exception e) {
			util.tratamentoExcecao(e.getMessage().toString(), "[ TabelaNCMController.printExportShow() ]");
		}

	}


	/**
	 * Fill comboBox fillCboxCor and property definitions
	 */
	public void fillCboxCor() 
	{

		for (EnumCboxCorItemDadosVeiculo item : EnumCboxCorItemDadosVeiculo.values())
			listCboxCor.add(new ComboBoxFilter(item.text, 0, item.index.toString()));

		cbCor.setItems(listCboxCor);
		cbCor.getSelectionModel().select(0);

		cbCor.setConverter(new StringConverter<ComboBoxFilter>() 
		{

			@Override
			public ComboBoxFilter fromString(String string) 
			{
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String toString(ComboBoxFilter object) 
			{
				// TODO Auto-generated method stub
				if (object != null) 
				{
					return object.getValue();
				} 
				else 
				{
					return "";
				}
			}

		});

	}	

	/**
	 * Fill comboBox fillCboxTipoCombustivel and property definitions
	 */
	public void fillCboxTipoCombustivel() 
	{

		for (EnumCboxTipoCombustiveltemDadosVeiculo item : EnumCboxTipoCombustiveltemDadosVeiculo.values())
			listCboxTipoCombustivel.add(new ComboBoxFilter(item.text, 0, item.index.toString()));

		cbTipoCombustivel.setItems(listCboxTipoCombustivel);
		cbTipoCombustivel.getSelectionModel().select(0);

		cbTipoCombustivel.setConverter(new StringConverter<ComboBoxFilter>() 
		{

			@Override
			public ComboBoxFilter fromString(String string) 
			{
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String toString(ComboBoxFilter object) 
			{
				// TODO Auto-generated method stub
				if (object != null) 
				{
					return object.getValue();
				} 
				else 
				{
					return "";
				}
			}

		});

	}	


	/**
	 * Fill comboBox fillCboxTipoPintura and property definitions
	 */
	public void fillCboxTipoPintura() 
	{
		for (EnumCboxTipoPinturaItemDadosVeiculo item : EnumCboxTipoPinturaItemDadosVeiculo.values())
			listCboxTipoPintura.add(new ComboBoxFilter(item.text, 0, String.valueOf(item.index)));

		cbTipoPintura.setItems(listCboxTipoPintura);
		cbTipoPintura.getSelectionModel().clearAndSelect(0);
		cbTipoPintura.setValue(cbTipoPintura.getSelectionModel().getSelectedItem());

		cbTipoPintura.setConverter(new StringConverter<ComboBoxFilter>() 
		{

			@Override
			public ComboBoxFilter fromString(String string) 
			{
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String toString(ComboBoxFilter object) 
			{
				// TODO Auto-generated method stub
				if (object != null) 
				{
					return object.getValue();
				} 
				else 
				{
					return "";
				}
			}

		});

	}	

	/**
	 * Fill comboBox fillCboxTipoVeiculo and property definitions
	 */
	public void fillCboxTipoVeiculo() 
	{

		for (EnumCboxTipoVeiculoItemDadosVeiculo item : EnumCboxTipoVeiculoItemDadosVeiculo.values())
			listCboxTipoVeiculo.add(new ComboBoxFilter(item.text, 0, item.index.toString()));

		cbTipoVeiculo.setItems(listCboxTipoVeiculo);
		cbTipoVeiculo.getSelectionModel().clearAndSelect(0);
		cbTipoVeiculo.setValue(cbTipoVeiculo.getSelectionModel().getSelectedItem());

		cbTipoVeiculo.setConverter(new StringConverter<ComboBoxFilter>() 
		{

			@Override
			public ComboBoxFilter fromString(String string) 
			{
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String toString(ComboBoxFilter object) 
			{
				// TODO Auto-generated method stub
				if (object != null) 
				{
					return object.getValue();
				} 
				else 
				{
					return "";
				}
			}

		});

	}	


	public void selectCboxFieldIndex(ComboBox<ComboBoxFilter> cbFilter, int dbValue) 
	{		   
		ObservableList<ComboBoxFilter> obsjList = cbFilter.getItems();		   
		int index = 0;

		for ( int  i = 0 ; i < cbFilter.getItems().size() ; i++ )
		{	   
			ComboBoxFilter tmp = obsjList.get(i);
			String strField = tmp.getField();
			int fieldValue = 0;

			try
			{
				fieldValue = Integer.parseInt(strField);
			}		 
			catch(Exception e)
			{
				char temp = strField.charAt(0);
				fieldValue = temp;			    	 
			}

			if(  fieldValue == dbValue )
			{ 	  
				index = i;
				break;
			}
			else{}
		}  

		cbFilter.getSelectionModel().select(index);		
	}

	/**
	 * Fill comboBox fillCboxEspVeiculo and property definitions
	 */
	public void fillCboxEspVeiculo() 
	{

		for (EnumCboxEspVeiculoItemDadosVeiculo item : EnumCboxEspVeiculoItemDadosVeiculo.values())
			listCboxEspVeiculo.add(new ComboBoxFilter(item.text, 0, item.index.toString()));

		cbEspVeiculo.setItems(listCboxEspVeiculo);
		cbEspVeiculo.getSelectionModel().clearAndSelect(0);
		cbEspVeiculo.setValue(cbEspVeiculo.getSelectionModel().getSelectedItem());

		cbEspVeiculo.setConverter(new StringConverter<ComboBoxFilter>() 
		{

			@Override
			public ComboBoxFilter fromString(String string) 
			{
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String toString(ComboBoxFilter object) 
			{
				// TODO Auto-generated method stub
				if (object != null) 
				{
					return object.getValue();
				} 
				else 
				{
					return "";
				}
			}

		});

	}	


	/**
	 * Fill comboBox fillCboxCondicaoVin and property definitions
	 */
	public void fillCboxCondicaoVin() 
	{

		for (EnumCboxCondicaoVinItemDadosVeiculo item : EnumCboxCondicaoVinItemDadosVeiculo.values())
			listCboxCondicaoVin.add(new ComboBoxFilter(item.text, 0, String.valueOf(item.index)));

		cbCondicaoVin.setItems(listCboxCondicaoVin);
		cbCondicaoVin.getSelectionModel().clearAndSelect(0);
		cbCondicaoVin.setValue(cbCondicaoVin.getSelectionModel().getSelectedItem());

		cbCondicaoVin.setConverter(new StringConverter<ComboBoxFilter>() 
		{

			@Override
			public ComboBoxFilter fromString(String string) 
			{
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String toString(ComboBoxFilter object) 
			{
				// TODO Auto-generated method stub
				if (object != null) 
				{
					return object.getValue();
				} 
				else 
				{
					return "";
				}
			}

		});

	}	

	/**
	 * Fill comboBox fillCboxCondicaoDoVeiculo and property definitions
	 */
	public void fillCboxCondicaoDoVeiculo() 
	{

		for (EnumCboxCondicaoDoVeiculoItemDadosVeiculo item : EnumCboxCondicaoDoVeiculoItemDadosVeiculo.values())
			listCboxCondicaoDoVeiculo.add(new ComboBoxFilter(item.text, 0, item.index.toString()));

		cbCondicaoDoVeiculo.setItems(listCboxCondicaoDoVeiculo);
		cbCondicaoDoVeiculo.getSelectionModel().clearAndSelect(0);
		cbCondicaoDoVeiculo.setValue(cbCondicaoDoVeiculo.getSelectionModel().getSelectedItem());

		cbCondicaoDoVeiculo.setConverter(new StringConverter<ComboBoxFilter>() 
		{

			@Override
			public ComboBoxFilter fromString(String string) 
			{
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String toString(ComboBoxFilter object) 
			{
				// TODO Auto-generated method stub
				if (object != null) 
				{
					return object.getValue();
				} 
				else 
				{
					return "";
				}
			}

		});

	}	

	/**
	 * Fill comboBox fillCboxRestricao and property definitions
	 */
	public void fillCboxRestricao() 
	{

		for (EnumCboxRestricaoItemDadosVeiculo item : EnumCboxRestricaoItemDadosVeiculo.values())
			listCboxRestricao.add(new ComboBoxFilter(item.text, 0, item.index.toString()));

		cbRestricao.setItems(listCboxRestricao);
		cbRestricao.getSelectionModel().clearAndSelect(0);
		cbRestricao.setValue(cbRestricao.getSelectionModel().getSelectedItem());

		cbRestricao.setConverter(new StringConverter<ComboBoxFilter>() 
		{

			@Override
			public ComboBoxFilter fromString(String string) 
			{
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String toString(ComboBoxFilter object) 
			{
				// TODO Auto-generated method stub
				if (object != null) 
				{
					return object.getValue();
				} 
				else 
				{
					return "";
				}
			}

		});

	}		

	/**
	 * Fill comboBox fillCboxProceProposVenda and property definitions
	 */
	public void fillCboxProceProposVenda() 
	{

		for (EnumCboxProceProposVendaItemAdicionais item : EnumCboxProceProposVendaItemAdicionais.values())
			listCboxProceProposVenda.add(new ComboBoxFilter(item.text, 0, item.index.toString()));

		cboxProceProposVenda.setItems(listCboxProceProposVenda);
		cboxProceProposVenda.getSelectionModel().clearAndSelect(0);
		cboxProceProposVenda.setValue(cboxProceProposVenda.getSelectionModel().getSelectedItem());

		cboxProceProposVenda.setConverter(new StringConverter<ComboBoxFilter>() 
		{

			@Override
			public ComboBoxFilter fromString(String string) 
			{
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String toString(ComboBoxFilter object) 
			{
				// TODO Auto-generated method stub
				if (object != null) 
				{
					return object.getValue();
				} 
				else 
				{
					return "";
				}
			}

		});

	}			


	public void closedComposicaoPreco(){
		Util.fechaTelaCadastro(anchorPaneDetalhes, anchorPaneFormacaoPreco);
		tbViewPrecosEmp.refresh();
	}

	public void setNewComposicaoPreco(ItemValor iv){
		
		List<Integer> empresasAfetadas = new ArrayList();
		
		boolean flagChangeCustoOnly = false;
		
		for (int i = 0; i < tbViewPrecosEmp.getItems().size(); i++) {

			if(DadosGlobais.empresaLogada.getConfig().getCpaCriterioprecosfiliais().equals(EnumCriterioPrecoFilial.CADA_EMPRESA_TEM_SEU_PRECO.criterioPreco)
					&& tbViewPrecosEmp.getItems().get(i).getCodemp().equals(tbViewPrecosEmp.getSelectionModel().getSelectedItem().getCodemp())){
				
				empresasAfetadas.add(tbViewPrecosEmp.getSelectionModel().getSelectedItem().getCodemp());
				
			}else 
				if(DadosGlobais.empresaLogada.getConfig().getCpaCriterioprecosfiliais().equals(EnumCriterioPrecoFilial.PRECO_CUSTO_E_VENDA_E_PROMOCAO_IGUAIS.criterioPreco)
						||
						DadosGlobais.empresaLogada.getConfig().getCpaCriterioprecosfiliais().equals(EnumCriterioPrecoFilial.PRECO_CUSTO_E_VENDA_IGUAIS.criterioPreco)){
					empresasAfetadas.add(tbViewPrecosEmp.getItems().get(i).getCodemp());
					
				}else
					if(DadosGlobais.empresaLogada.getConfig().getCpaCriterioprecosfiliais().equals(EnumCriterioPrecoFilial.PRECO_CUSTO_IGUAIS.criterioPreco)){
						empresasAfetadas.add(tbViewPrecosEmp.getItems().get(i).getCodemp());
						flagChangeCustoOnly = true;
						
					}
		}

	
		for (int j = 0; j < tbViewPrecosEmp.getItems().size(); j++) {
		
			for (int i = 0; i < empresasAfetadas.size(); i++) {
			
			//---- INCIDENCIA DE COMPRA -----------------
			if(tbViewPrecosEmp.getItems().get(j).getCodemp().equals(empresasAfetadas.get(i))){
				
			tbViewPrecosEmp.getItems().get(j).setVlrCusto(iv.getVlrCusto());

			tbViewPrecosEmp.getItems().get(j).setFpCustoRealCompra(iv.getFpCustoRealCompra());

			tbViewPrecosEmp.getItems().get(j).setFpVlrEmbalagem(iv.getFpVlrEmbalagem());

			tbViewPrecosEmp.getItems().get(j).setFpDescontoCompra(iv.getFpDescontoCompra());

			tbViewPrecosEmp.getItems().get(j).setFpIpiCompra(iv.getFpIpiCompra());

			tbViewPrecosEmp.getItems().get(j).setFpFreteCompra(iv.getFpFreteCompra());

			tbViewPrecosEmp.getItems().get(j).setFpCreditoIcms(iv.getFpCreditoIcms());

			tbViewPrecosEmp.getItems().get(j).setFpIcmsDesonerado(iv.getFpIcmsDesonerado());

			tbViewPrecosEmp.getItems().get(j).setFpSubstituicaoTrib(iv.getFpSubstituicaoTrib());

			tbViewPrecosEmp.getItems().get(j).setFpCreditoPis(iv.getFpCreditoPis());

			tbViewPrecosEmp.getItems().get(j).setFpCreditoCofins(iv.getFpCreditoCofins());

			tbViewPrecosEmp.getItems().get(j).setFpDespAcessoria(iv.getFpDespAcessoria());

			tbViewPrecosEmp.getItems().get(j).setFpSeguro(iv.getFpSeguro());

			tbViewPrecosEmp.getItems().get(j).setFpOutrosCreditos(iv.getFpOutrosCreditos());

			tbViewPrecosEmp.getItems().get(j).setFpOutrosCustos(iv.getFpOutrosCustos());

			// -----------------INCIDENCIA VENDA VAREJO ------------------------------------
			
			if((tbViewPrecosEmp.getSelectionModel().getSelectedItem().getCodemp().equals(empresasAfetadas.get(j)) && flagChangeCustoOnly == true) || flagChangeCustoOnly == false){
				
			tbViewPrecosEmp.getItems().get(j).setFpMargemLiquidaVarejo(iv.getFpMargemLiquidaVarejo());
			
			tbViewPrecosEmp.getItems().get(j).setMargemLucroBrutoVarejo(iv.getMargemLucroBrutoVarejo());
			
			tbViewPrecosEmp.getItems().get(j).setFpCustoOperacionalVarejo(iv.getFpCustoOperacionalVarejo());

			tbViewPrecosEmp.getItems().get(j).setFpPisVendaVarejo(iv.getFpPisVendaVarejo());

			tbViewPrecosEmp.getItems().get(j).setFpCofinsVendaVarejo(iv.getFpCofinsVendaVarejo());

			tbViewPrecosEmp.getItems().get(j).setFpIcmsIssVendaVarejo(iv.getFpIcmsIssVendaVarejo());

			tbViewPrecosEmp.getItems().get(j).setFpComissaoVendaVarejo(iv.getFpComissaoVendaVarejo());		    

			tbViewPrecosEmp.getItems().get(j).setFpImpostoRendaVarejo(iv.getFpImpostoRendaVarejo());		    

			tbViewPrecosEmp.getItems().get(j).setFpContribuicaoSocialVarejo(iv.getFpContribuicaoSocialVarejo());		    

			tbViewPrecosEmp.getItems().get(j).setFpFreteVendaVarejo(iv.getFpFreteVendaVarejo());		    

			tbViewPrecosEmp.getItems().get(j).setFpSimplesNacionalVarejo(iv.getFpSimplesNacionalVarejo());		    

			tbViewPrecosEmp.getItems().get(j).setFpOutrasDespesasVarejo(iv.getFpOutrasDespesasVarejo());		    

			tbViewPrecosEmp.getItems().get(j).setVlrVendaVarejo(iv.getVlrVendaVarejo());


			// -----------------INCIDENCIA VENDA ATACADO ------------------------------------

			tbViewPrecosEmp.getItems().get(j).setFpMargemLiquidaAtacado(iv.getFpMargemLiquidaAtacado());
			
			tbViewPrecosEmp.getItems().get(j).setMargemLucroBrutoAtacado(iv.getMargemLucroBrutoAtacado());

			tbViewPrecosEmp.getItems().get(j).setFpCustoOperacionalAtacado(iv.getFpCustoOperacionalAtacado());

			tbViewPrecosEmp.getItems().get(j).setFpPisVendaAtacado(iv.getFpPisVendaAtacado());

			tbViewPrecosEmp.getItems().get(j).setFpCofinsVendaAtacado(iv.getFpCofinsVendaAtacado());

			tbViewPrecosEmp.getItems().get(j).setFpIcmsIssVendaAtacado(iv.getFpIcmsIssVendaAtacado());

			tbViewPrecosEmp.getItems().get(j).setFpComissaoVendaAtacado(iv.getFpComissaoVendaAtacado());		    

			tbViewPrecosEmp.getItems().get(j).setFpImpostoRendaAtacado(iv.getFpImpostoRendaAtacado());		    

			tbViewPrecosEmp.getItems().get(j).setFpContribuicaoSocialAtacado(iv.getFpContribuicaoSocialAtacado());		    

			tbViewPrecosEmp.getItems().get(j).setFpFreteVendaAtacado(iv.getFpFreteVendaAtacado());		    

			tbViewPrecosEmp.getItems().get(j).setFpSimplesNacionalAtacado(iv.getFpSimplesNacionalAtacado());		    

			tbViewPrecosEmp.getItems().get(j).setFpOutrasDespesasAtacado(iv.getFpOutrasDespesasAtacado());		    

			tbViewPrecosEmp.getItems().get(j).setVlrVendaAtacado(iv.getVlrVendaAtacado());
			
			}
			
			}	
			
//		tbViewPrecosEmp.getSelectionModel().getSelectedItem().setVlrCusto(iv.getVlrCusto());
//
//		tbViewPrecosEmp.getSelectionModel().getSelectedItem().setFpCustoRealCompra(iv.getFpCustoRealCompra());
//
//		tbViewPrecosEmp.getSelectionModel().getSelectedItem().setFpVlrEmbalagem(iv.getFpVlrEmbalagem());
//
//		tbViewPrecosEmp.getSelectionModel().getSelectedItem().setFpDescontoCompra(iv.getFpDescontoCompra());
//
//		tbViewPrecosEmp.getSelectionModel().getSelectedItem().setFpIpiCompra(iv.getFpIpiCompra());
//
//		tbViewPrecosEmp.getSelectionModel().getSelectedItem().setFpFreteCompra(iv.getFpFreteCompra());
//
//		tbViewPrecosEmp.getSelectionModel().getSelectedItem().setFpCreditoIcms(iv.getFpCreditoIcms());
//
//		tbViewPrecosEmp.getSelectionModel().getSelectedItem().setFpIcmsDesonerado(iv.getFpIcmsDesonerado());
//
//		tbViewPrecosEmp.getSelectionModel().getSelectedItem().setFpSubstituicaoTrib(iv.getFpSubstituicaoTrib());
//
//		tbViewPrecosEmp.getSelectionModel().getSelectedItem().setFpCreditoPis(iv.getFpCreditoPis());
//
//		tbViewPrecosEmp.getSelectionModel().getSelectedItem().setFpCreditoCofins(iv.getFpCreditoCofins());
//
//		tbViewPrecosEmp.getSelectionModel().getSelectedItem().setFpDespAcessoria(iv.getFpDespAcessoria());
//
//		tbViewPrecosEmp.getSelectionModel().getSelectedItem().setFpSeguro(iv.getFpSeguro());
//
//		tbViewPrecosEmp.getSelectionModel().getSelectedItem().setFpOutrosCreditos(iv.getFpOutrosCreditos());
//
//		tbViewPrecosEmp.getSelectionModel().getSelectedItem().setFpOutrosCustos(iv.getFpOutrosCustos());
//
//
//		// -----------------INCIDENCIA VENDA VAREJO ------------------------------------
//
//		tbViewPrecosEmp.getSelectionModel().getSelectedItem().setFpMargemLiquidaVarejo(iv.getFpMargemLiquidaVarejo());
//		
//		tbViewPrecosEmp.getSelectionModel().getSelectedItem().setMargemLucroBrutoVarejo(iv.getMargemLucroBrutoVarejo());
//		
//		tbViewPrecosEmp.getSelectionModel().getSelectedItem().setFpCustoOperacionalVarejo(iv.getFpCustoOperacionalVarejo());
//
//		tbViewPrecosEmp.getSelectionModel().getSelectedItem().setFpPisVendaVarejo(iv.getFpPisVendaVarejo());
//
//		tbViewPrecosEmp.getSelectionModel().getSelectedItem().setFpCofinsVendaVarejo(iv.getFpCofinsVendaVarejo());
//
//		tbViewPrecosEmp.getSelectionModel().getSelectedItem().setFpIcmsIssVendaVarejo(iv.getFpIcmsIssVendaVarejo());
//
//		tbViewPrecosEmp.getSelectionModel().getSelectedItem().setFpComissaoVendaVarejo(iv.getFpComissaoVendaVarejo());		    
//
//		tbViewPrecosEmp.getSelectionModel().getSelectedItem().setFpImpostoRendaVarejo(iv.getFpImpostoRendaVarejo());		    
//
//		tbViewPrecosEmp.getSelectionModel().getSelectedItem().setFpContribuicaoSocialVarejo(iv.getFpContribuicaoSocialVarejo());		    
//
//		tbViewPrecosEmp.getSelectionModel().getSelectedItem().setFpFreteVendaVarejo(iv.getFpFreteVendaVarejo());		    
//
//		tbViewPrecosEmp.getSelectionModel().getSelectedItem().setFpSimplesNacionalVarejo(iv.getFpSimplesNacionalVarejo());		    
//
//		tbViewPrecosEmp.getSelectionModel().getSelectedItem().setFpOutrasDespesasVarejo(iv.getFpOutrasDespesasVarejo());		    
//
//		tbViewPrecosEmp.getSelectionModel().getSelectedItem().setVlrVendaVarejo(iv.getVlrVendaVarejo());
//
//
//		// -----------------INCIDENCIA VENDA ATACADO ------------------------------------
//
//		tbViewPrecosEmp.getSelectionModel().getSelectedItem().setFpMargemLiquidaAtacado(iv.getFpMargemLiquidaAtacado());
//		
//		tbViewPrecosEmp.getSelectionModel().getSelectedItem().setMargemLucroBrutoAtacado(iv.getMargemLucroBrutoAtacado());
//
//		tbViewPrecosEmp.getSelectionModel().getSelectedItem().setFpCustoOperacionalAtacado(iv.getFpCustoOperacionalAtacado());
//
//		tbViewPrecosEmp.getSelectionModel().getSelectedItem().setFpPisVendaAtacado(iv.getFpPisVendaAtacado());
//
//		tbViewPrecosEmp.getSelectionModel().getSelectedItem().setFpCofinsVendaAtacado(iv.getFpCofinsVendaAtacado());
//
//		tbViewPrecosEmp.getSelectionModel().getSelectedItem().setFpIcmsIssVendaAtacado(iv.getFpIcmsIssVendaAtacado());
//
//		tbViewPrecosEmp.getSelectionModel().getSelectedItem().setFpComissaoVendaAtacado(iv.getFpComissaoVendaAtacado());		    
//
//		tbViewPrecosEmp.getSelectionModel().getSelectedItem().setFpImpostoRendaAtacado(iv.getFpImpostoRendaAtacado());		    
//
//		tbViewPrecosEmp.getSelectionModel().getSelectedItem().setFpContribuicaoSocialAtacado(iv.getFpContribuicaoSocialAtacado());		    
//
//		tbViewPrecosEmp.getSelectionModel().getSelectedItem().setFpFreteVendaAtacado(iv.getFpFreteVendaAtacado());		    
//
//		tbViewPrecosEmp.getSelectionModel().getSelectedItem().setFpSimplesNacionalAtacado(iv.getFpSimplesNacionalAtacado());		    
//
//		tbViewPrecosEmp.getSelectionModel().getSelectedItem().setFpOutrasDespesasAtacado(iv.getFpOutrasDespesasAtacado());		    
//
//		tbViewPrecosEmp.getSelectionModel().getSelectedItem().setVlrVendaAtacado(iv.getVlrVendaAtacado());

		}}
		
		tbViewPrecosEmp.getSelectionModel().getSelectedItem().setFlagChangePrecoEmp(true);
		
		

		tbViewPrecosEmp.refresh();
	}

	// MÉTODO DE INICILIAZAÇÃO DA CLASSE
	@SuppressWarnings("static-access")
	@Override
	/**
	 * Initialize
	 * 
	 * @param location
	 * @param resources
	 */
	public void initialize(URL location, ResourceBundle resources) {		

		
		// CONFIGURA PAINEIS INICIAIS
		anchorPaneListagem.setVisible(true);
		anchorPaneDetalhes.setVisible(false);

		fillCboxProceProposVenda();
		populateCboxTipoItem();
		fillCboxCor();
		fillCboxTipoCombustivel();
		fillCboxTipoPintura();
		fillCboxTipoVeiculo();
		fillCboxEspVeiculo();
		fillCboxCondicaoVin();
		fillCboxCondicaoDoVeiculo();
		fillCboxRestricao();
		


		try 
		{
			cpc = new ComposicaoPrecosController(this);		
			anchorPaneFormacaoPreco.getChildren().add(util.openWindow("/views/compras/viewComposicaoDePrecos.fxml", cpc));
			
		} 
		catch (Exception e1) 
		{
						
		} 

		tbViewCodBarras.setEditable(true);

		//		tgbtnHabilitaAba.setOnAction(new EventHandler<ActionEvent>() 
		//		{		
		//			@Override
		//			public void handle(ActionEvent event) 
		//			{	
		//				boolean selected = tgbtnHabilitaAba.isSelected();
		//				tabDadosVeiculo.setDisable(!selected);
		//				
		//				if ( selected ) 
		//				     entidadeBean.setVeiculoFlag(1);
		//				else
		//					 entidadeBean.setVeiculoFlag(0);
		//				
		//				//doActionSave();
		//				entidadeDao.update(entidadeBean, tbViewCodBarras.getItems(), listCodBars);
		//			}
		//			
		//		});

		Util.customSearchTextField("right",null, txtFilterColumn);
		Util.customSearchTextField("right",null, txtCodFabricante);
		Util.customSearchTextField("right",null,  txtCodGrupo);
		Util.customSearchTextField("right",null,  txtCodSubGrupo);
		Util.customSearchTextField("right",null,  txtCodDepartamento);
		Util.customSearchTextField("right",null,  txtCodFamiliaPreco);
		Util.customSearchTextField("right",null,  txtCodLcServico);
		Util.customSearchTextField("right",null,  txtCodMoeda);
		Util.customSearchTextField("right",null,  txtCodNcm);
		Util.customSearchTextField("right",null,  txtCodUnidade);
		Util.customSearchTextField("right",null,  txtCodUnidadeEmb);
		Util.customEditedTextField("right", null, txtDepartamento);
		Util.customEditedTextField("right", null, txtFabricante);
		Util.customEditedTextField("right", null, txtGrupo);
		Util.customEditedTextField("right", null, txtSubGrupo);
		Util.customEditedTextField("right", null, txtUnidade);
		Util.customEditedTextField("right", null, txtFamiliaPreco);
		Util.customSearchTextField("right", null, txtCodTributacao);
		Util.customSearchTextField("right", null, txtCodFornecedor);

		//-----------------Dados adicionais------------------------

		Util.customSearchTextField("right", null, txtItemSimilar1);					
		Util.customSearchTextField("right", null, txtItemSimilar2);				
		Util.customSearchTextField("right", null, txtItemSimilar3);					
		Util.customSearchTextField("right", null, txtItemSimilar4);	
		Util.customSearchTextField("right", null, txtItemSimilar5);					
		Util.customSearchTextField("right", null, txtItemSimilar6);	

		txtItemSimilar1Fk.setEditable(false);	
		txtItemSimilar2Fk.setEditable(false);
		txtItemSimilar3Fk.setEditable(false);
		txtItemSimilar4Fk.setEditable(false);
		txtItemSimilar5Fk.setEditable(false);
		txtItemSimilar6Fk.setEditable(false);

		// CHAMADA A FUNÇÃO QUE CONFIGURA O TABLEVIEW DE ACORDO COM O ARQUIVO
		// XML GERADO PARA CADA USUARIO DENTRO DA PASTA C:/EptusAM/System.Grd
		configTableView();

		// CHAMADA A FUNÇÃO QUE FAZ A TRADUÇÃO DO FORMULARIO DE ACORDO COM AS
		// CONFIGURAÇÕES DO USUÁRIO
		setIdioma();

		// CHAMADA A FUNÇÃO QUE SETA O ESTILO DEFAULT AOS CAMPOS TEXTFIELDS
		util.setKeyPressDefaultStyles(txtDescricao, txtCodigo, txtFilterColumn, txtCodFabricante, txtCodNcm, txtCodDepartamento,
				txtCodGrupo, txtCodSubGrupo, txtGrupo, txtSubGrupo, txtFabricante, txtDepartamento, txtCodMoeda, txtDescReduzida, txtDescTecnica,
				txtQtdEmbalagemAtacado, txtQtdEmbalagemCompra, txtQtdEmbalagemVenda,txtQtdEmbCodBar,txtQtdLitros, txtQtdMaxima,txtQtdMinima,
				txtQtdPallet,txtCodLcServico,txtPesoBruto,txtPesoLiquido,txtFatorConversao,txtMedidaM3,
				txtCodCest,txtNumFabricante,txtNumOriginal,txtLocacao, txtCodBar,
				txtCodFamiliaPreco, txtCodUnidade, txtCodUnidadeEmb,  txtFamiliaPreco, txtUnidade, txtUnidadeEmb, txtCodTributacao, 
				txtItemSimilar1, txtItemSimilar1Fk, txtItemSimilar2, txtItemSimilar2Fk, txtItemSimilar3, txtItemSimilar3Fk,					
				txtItemSimilar4, txtItemSimilar4Fk, txtItemSimilar5, txtItemSimilar5Fk, txtItemSimilar6, txtItemSimilar6Fk, txtCodFornecedor, txtReferenciaFornecedor, txtFornecedor);

		Util.setStyleOnFocus(txtFilterColumn,txtCodigo, txtDescricao,  txtCodFabricante, txtCodNcm, txtCodDepartamento,
				txtCodMoeda, txtDescReduzida, txtDescTecnica,
				txtQtdEmbalagemAtacado, txtQtdEmbalagemCompra, txtQtdEmbalagemVenda,txtQtdEmbCodBar,txtQtdLitros, txtQtdMaxima,txtQtdMinima,
				txtQtdPallet,txtCodLcServico,txtPesoBruto,txtPesoLiquido,txtFatorConversao,txtMedidaM3,
				txtCodCest,txtNumFabricante,txtNumOriginal,txtLocacao, txtCodBar,
				txtCodGrupo, txtCodSubGrupo, txtGrupo, txtSubGrupo, txtFabricante, txtDepartamento,   
				txtCodFamiliaPreco, txtCodUnidade, txtCodUnidadeEmb,  txtFamiliaPreco, txtUnidade, txtUnidadeEmb, txtCodTributacao,
				txtItemSimilar1, txtItemSimilar1Fk, txtItemSimilar2, txtItemSimilar2Fk, txtItemSimilar3, txtItemSimilar3Fk,					
				txtItemSimilar4, txtItemSimilar4Fk, txtItemSimilar5, txtItemSimilar5Fk, txtItemSimilar6, txtItemSimilar6Fk, txtReferenciaFornecedor, txtCodFornecedor);

		// CHAMADA A FUNÇÃO QUE SETA O ESTILO DEFAULT AOS CAMPOS TEXTFIELDS
		util.onlyNumbers(txtCodigo, txtCodFabricante, txtCodGrupo, txtCodSubGrupo, txtCodDepartamento, txtCodFamiliaPreco, txtCodUnidade, txtCodUnidadeEmb,
				txtCodLcServico, txtCodNcm, txtCodMoeda, txtCodCest,
				txtItemSimilar1, txtItemSimilar2, txtItemSimilar3, txtItemSimilar4, txtItemSimilar5, txtItemSimilar6, txtCodFornecedor);

		// CHAMADA A FUNÇÃO QUE SETA O ESTILO DEFAULT AOS CAMPOS TEXTFIELDS
		util.onlyAlphanumeric(txtDescricao, txtFilterColumn, txtReferenciaFornecedor, txtFornecedor);

		// CHAMADA A FUNÇÃO QUE LIMITA O NUMERO DE CARACTERES QUE PODEM SER
		// DIGITADOS NOS CAMPOS TEXTFIELDS
		util.maxCharacters(8, txtCodigo);
		util.maxCharacters(40, txtDescricao);
		util.maxCharacters(25, txtDescReduzida);
		util.maxCharacters(60, txtDescTecnica);
		util.maxCharacters(35, txtGrupo);
		util.maxCharacters(35, txtSubGrupo);
		util.maxCharacters(35, txtFabricante);
		util.maxCharacters(35, txtDepartamento);
		util.maxCharacters(35, txtFamiliaPreco);
		util.maxCharacters(3, txtUnidade);
		util.maxCharacters(9, txtQtdPallet);
		util.maxCharacters(9, txtQtdLitros);
		util.maxCharacters(9, txtQtdEmbalagemVenda);
		util.maxCharacters(9, txtQtdEmbalagemCompra);
		util.maxCharacters(9, txtQtdEmbalagemAtacado);
		util.maxCharacters(9, txtFatorConversao);
		util.maxCharacters(9, txtPesoBruto);
		util.maxCharacters(9, txtPesoLiquido);

		// CHAMADA A FUNÇÃO QUE DEFINE OS CAMPOS TEXTFIELDS QUE SERAO
		// CAPITALIZADOS(LETRAS MAÍUSCULAS)
		util.whriteUppercase(txtDescricao, txtFilterColumn, txtGrupo, txtSubGrupo, 
				txtFabricante, txtDepartamento, txtUnidade, txtFamiliaPreco, txtNumFabricante,
				txtNumOriginal, txtDescReduzida, txtDescTecnica,txtLocacao
				);

		// FORMATA CAMPOS DECIMAIS
		util.decimalBR(4, txtQtdPallet, txtQtdLitros, txtMedidaM3, txtQtdEmbalagemVenda, txtQtdEmbalagemCompra, txtQtdEmbalagemAtacado, 
				txtFatorConversao, txtPesoBruto, txtPesoLiquido, txtQtdEmbCodBar);

		util.decimalBR(2,txtQtdMaxima, txtQtdMinima);

		//INICIALIZA CAMPOS COM VALORES DEFAULT
		txtQtdEmbalagemAtacado.setText("1,0000");
		txtQtdEmbalagemCompra.setText("1,0000");
		txtQtdEmbalagemVenda.setText("1,0000");
		txtFatorConversao.setText("1,0000");
		txtPesoLiquido.setText("0,0000");
		txtMedidaM3.setText("0,0000");
		txtPesoBruto.setText("0,0000");
		txtQtdVolumes.setText("1");
		txtQtdPallet.setText("0,0000");
		txtMedidaM3.setText("0,0000");
		txtQtdLitros.setText("0,0000");
		txtQtdEmbCodBar.setText("1,0000");

		// CHAMADA A FUNÇÃO PARA POPULAR OS COMBOBOX CONTINDOS NO FORMULARIO
		populateCboxFilterColumn();
		populateCboxFlagAtivo();

		// CHAMADA A FUNÇÃO PARA SETAR O FOCO INICIAL DO FORMULÁRIO
		util.setFocus(txtFilterColumn);


		Util.setNextFocus(txtDescricao, txtDescReduzida, txtDescTecnica, txtNumFabricante, txtNumOriginal, txtCodCest, txtQtdPallet, txtMedidaM3,
				txtPesoBruto, txtPesoLiquido, txtFatorConversao, txtQtdLitros, txtQtdEmbalagemCompra, txtQtdEmbalagemVenda, 
				txtQtdEmbalagemAtacado,txtQtdVolumes, txtCodBar, txtQtdEmbCodBar,
				txtLocacao,txtQtdMinima,txtQtdMaxima,txtCodTributacao);

		// CONFIGURA AS COLUNAS EXISTENTES NO TABLEVIEW, ASSOCIA A COLUNA DO
		// FXML AO ATRIBUTO EXISTENTE NA CLASSE DO MODEL
		tbColCodigo.setCellValueFactory(new PropertyValueFactory<Item, Integer>("codigo"));
		tbColDescricao.setCellValueFactory(new PropertyValueFactory<Item, String>("descricao"));
		tbColCodEmp.setCellValueFactory(new PropertyValueFactory<Item, Integer>("codemp"));
		tbColAtivoInat.setCellValueFactory(new PropertyValueFactory<Item, Integer>("flagAtivo"));
		tbColCodFabricante.setCellValueFactory(new PropertyValueFactory<Item, Integer>("codFabricante"));
		tbColCodGrupo.setCellValueFactory(new PropertyValueFactory<Item, Integer>("codGrupo"));
		tbColCodSubGrupo.setCellValueFactory(new PropertyValueFactory<Item, Integer>("codSubGrupo"));
		tbColCodDepartamento.setCellValueFactory(new PropertyValueFactory<Item, Integer>("codDepartamento"));
		tbColCodCest.setCellValueFactory(new PropertyValueFactory<Item, Integer>("codCest"));
		tbColCodFamiliaPreco.setCellValueFactory(new PropertyValueFactory<Item, Integer>("CodFamiliaPreco"));
		tbColCodMoeda.setCellValueFactory(new PropertyValueFactory<Item, Integer>("codMoeda"));
		tbColCodUnidade.setCellValueFactory(new PropertyValueFactory<Item, Integer>("codUnidade"));
		tbColCodUnidadeEmb.setCellValueFactory(new PropertyValueFactory<Item, Integer>("codUnidadeEmb"));
		//tbColCodTributacao.setCellValueFactory(new PropertyValueFactory<Item, Integer>("tributacao"));
		tbColEnviaBalanca.setCellValueFactory(new PropertyValueFactory<Item, Integer>("flagEnviaBalanca"));


		tbColQtdPallet.setCellValueFactory(new PropertyValueFactory<Item, BigDecimal>("qtdPallet"));
		tbColMedidaM3.setCellValueFactory(new PropertyValueFactory<Item, BigDecimal>("medidaM3"));
		tbColPesoBruto.setCellValueFactory(new PropertyValueFactory<Item, BigDecimal>("pesoBruto"));
		tbColPesoLiquido.setCellValueFactory(new PropertyValueFactory<Item, BigDecimal>("pesoLiquido"));
		tbColFatorConversao.setCellValueFactory(new PropertyValueFactory<Item, BigDecimal>("fatorConversao"));
		tbColQtdLitros.setCellValueFactory(new PropertyValueFactory<Item, BigDecimal>("qtdLitros"));
		tbColQtdEmbCompra.setCellValueFactory(new PropertyValueFactory<Item, BigDecimal>("qtdEmbCompra"));
		tbColQtdEmbVenda.setCellValueFactory(new PropertyValueFactory<Item, BigDecimal>("qtdEmbVenda"));
		tbColQtdEmbAtacado.setCellValueFactory(new PropertyValueFactory<Item, BigDecimal>("qtdEmbAtacado"));
		tbColDescontoMax.setCellValueFactory(new PropertyValueFactory<Item, BigDecimal>("descontoMax"));
		tbColQtdEstoque.setCellValueFactory(new PropertyValueFactory<Item, BigDecimal>("qtdEstoque"));

		//tbColCodBarra.setCellValueFactory(new PropertyValueFactory<Item, String>("codBarras"));
		tbColTributacao.setCellValueFactory(new PropertyValueFactory<Item, String>("tributacao"));
		tbColDadosAdicionais.setCellValueFactory(new PropertyValueFactory<Item, String>("dadosAdicionais"));
		tbColDescReduzida.setCellValueFactory(new PropertyValueFactory<Item, String>("descReduzida"));
		tbColDescTecnica.setCellValueFactory(new PropertyValueFactory<Item, String>("descTecnica")); 
		tbColNumFabricante.setCellValueFactory(new PropertyValueFactory<Item, String>("numFabricante"));
		tbColNumOriginal.setCellValueFactory(new PropertyValueFactory<Item, String>("numOriginal"));
		//tbColFabricante.setCellValueFactory(new PropertyValueFactory<Item, String>("fabricanteDescricao"));
		tbColGrupo.setCellValueFactory(new PropertyValueFactory<Item, String>("grupoDescricao"));
		//tbColSubGrupo.setCellValueFactory(new PropertyValueFactory<Item, String>("subGrupoDescricao"));
		//tbColDepartamento.setCellValueFactory(new PropertyValueFactory<Item, String>("departamentoDescricao"));
		//tbColLcServico.setCellValueFactory(new PropertyValueFactory<Item, String>("lcServicoDescricao"));
		//tbColNcm.setCellValueFactory(new PropertyValueFactory<Item, String>("codNcm"));
		//tbColFamiliaPreco.setCellValueFactory(new PropertyValueFactory<Item, String>("familiaPrecoDescricao"));
		//tbColMoeda.setCellValueFactory(new PropertyValueFactory<Item, String>("moeda"));
		//tbColUnidade.setCellValueFactory(new PropertyValueFactory<Item, String>("unidadeDescricao"));
		//tbColUnidadeEmb.setCellValueFactory(new PropertyValueFactory<Item, String>("unidadeEmbDescricao"));


		// MÉTODO QUE CONVERTE OS VALORES DA COLUNA FLAGATIVO EM TEXTO CASO FLAGATIVO = 0 MOSTRA INATIVO E FLAGATIVO = 1 MOSTRA ATIVO
		tbColAtivoInat.setCellFactory(col -> new TableCell<Item, Integer>() {
			@Override
			public void updateItem(Integer flagAtivo, boolean empty) {
				super.updateItem(flagAtivo, empty);
				if (empty) {
					setText(" ");
				} else {
					if (flagAtivo == null)
						setText(null);
					else if (flagAtivo.equals(0)) {
						setText(String.format(DadosGlobais.resourceBundle.getString("inativo"), flagAtivo));
					} else if (flagAtivo.equals(1))
						setText(String.format(DadosGlobais.resourceBundle.getString("ativo"), flagAtivo));
				}
			}
		});
		// MÉTODO QUE CONVERTE OS VALORES DA COLUNA FLAGATIVO EM TEXTO CASO FLAGATIVO = 0 MOSTRA INATIVO E FLAGATIVO = 1 MOSTRA ATIVO
		//		tbColFlagPrincipal.setCellFactory(col -> new TableCell<ItemCodBar, Integer>() {
		//			@Override
		//			public void updateItem(Integer flagPrincipal, boolean empty) {
		//				super.updateItem(flagPrincipal, empty);
		//				if (empty) {
		//					setText(" ");
		//				} else {
		//					if (flagPrincipal == null)
		//						setText(null);
		//					else if (flagPrincipal.equals(0)) {
		//						setText("");
		//					} else if (flagPrincipal.equals(1)){
		//						setText("");
		//						FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CHECK);
		//						icon.setFill(Color.web("#212121"));
		//						icon.setSize("14px");
		//						setGraphic(icon);
		//					}
		//				}
		//			}
		//		});
		//		tbColCodTributacao.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Item, Integer>, ObservableValue<Integer>>() {
		//
		//	            @Override
		//	            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Item, Integer> p) {
		//	              //SOLUÇÃO É ESTA LINHA                
		//	              return new ReadOnlyObjectWrapper<>(p.getValue().getItemValors().get(0).getCodTributacao());
		//	            }
		//	        });
		//		 
		//		 tbColTributacao.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Item, String>, ObservableValue<String>>() {
		//
		//	            @Override
		//	            public ObservableValue<String> call(TableColumn.CellDataFeatures<Item, String> p) {
		//	              //SOLUÇÃO É ESTA LINHA                
		//	              return new ReadOnlyObjectWrapper<>(p.getValue().getItemCodbars().size() > 0 ? p.getValue().getItemCodbars().iterator().next().getCodBarras() : null);
		//	            }
		//	        });

		//		 tbColFabricante.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Item, String>, ObservableValue<String>>() {
		//
		//	            @Override
		//	            public ObservableValue<String> call(TableColumn.CellDataFeatures<Item, String> p) {
		//	              //SOLUÇÃO É ESTA LINHA                
		//	              return new ReadOnlyObjectWrapper<>(!p.getValue().getFabricante().getCodigo().equals(0) ? p.getValue().getFabricante().getDescricao() : "b");
		//	            }
		//	        });
		//		



		//TABLE VIEW DE CODIGO DE BARRAS
		tbColCodBarras.setCellValueFactory(new PropertyValueFactory<ItemCodBar, String>("codBarras"));
		tbColQtdEmbalagem.setCellValueFactory(new PropertyValueFactory<ItemCodBar, BigDecimal>("qtdEmbalagem"));
		tbColFlagPrincipal.setEditable(true);
		tbColFlagPrincipal.setCellValueFactory(new PropertyValueFactory<ItemCodBar, Integer>("flagCodbarPrincipal"));

		tbColFlagPrincipal.setCellFactory(col -> {

			// boolean property represents if the check box in the cell is
			// selected:
			BooleanProperty selected = new SimpleBooleanProperty();

			// create check box cell and bind its state to property:
			CheckBoxTableCell<ItemCodBar, Integer> cell = new CheckBoxTableCell<>(index -> selected);

			// update set of selected indices if check box state changes:
			selected.addListener((obs, wasSelected, isNowSelected) -> {

				if (isNowSelected) {

					for(int i=0; i < tbViewCodBarras.getItems().size(); i++){
						if(cell.getIndex() == i)
							tbViewCodBarras.getItems().get(i).setFlagCodbarPrincipal(1);
						else
							tbViewCodBarras.getItems().get(i).setFlagCodbarPrincipal(0);
					}

					tbViewCodBarras.refresh();

				}

			});

			//update check box when cell is reused for a different index:
			cell.itemProperty().addListener((obs, oldItem, newItem) -> {
				if(cell.getItem() != null && cell.itemProperty().get().equals(1)){

					selected.set(cell.getItem() != null && cell.itemProperty().get().equals(1));

				}

			});

			return cell;
		});


		//TABLEVIEW POSICAO DE ESTOQUE
		tbColEmpEstoque.setCellValueFactory(new PropertyValueFactory<ItemValor, Integer>("codemp"));
		tbColQtdMin.setCellValueFactory(new PropertyValueFactory<ItemValor, BigDecimal>("qtdMin"));
		tbColQtdMax.setCellValueFactory(new PropertyValueFactory<ItemValor, BigDecimal>("qtdMax"));
		tbColSaldoAtual.setCellValueFactory(new PropertyValueFactory<ItemValor, BigDecimal>("qtdAtual"));
		tbColCcusto1.setCellValueFactory(new PropertyValueFactory<ItemValor, BigDecimal>("qtdCcusto1"));
		tbColCcusto2.setCellValueFactory(new PropertyValueFactory<ItemValor, BigDecimal>("qtdCcusto2"));
		tbColLocacao.setCellValueFactory(new PropertyValueFactory<ItemValor, String>("locacao"));
		tbColDtReposicao.setCellValueFactory(new PropertyValueFactory<ItemValor, LocalDateTime>("repData"));
		tbColQtdReposicao.setCellValueFactory(new PropertyValueFactory<ItemValor, BigDecimal>("repQuantidade"));
		tbColDocReposicao.setCellValueFactory(new PropertyValueFactory<ItemValor, Integer>("repDocumento"));		

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
								util.alternaBotaoDelete(btnDelete,
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
				util.alternaBotaoDelete(btnDelete, tbView.getSelectionModel().getSelectedItem().getFlagAtivo());
			}
		});

		//		txtLocacao.focusedProperty().addListener(new ChangeListener<Boolean>() {
		//			@Override
		//			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
		//					Boolean newPropertyValue) {
		//				
		//				if (!newPropertyValue) {
		//					System.out.println("saidalocao");
		//					if (!btnInsert.isPressed() && !btnClose.isPressed() && !btnCancel.isPressed()){
		//						tbViewPosicaoEstoque.getSelectionModel().getSelectedItem().setLocacao(txtLocacao.getText());
		//						tbViewPosicaoEstoque.refresh();	
		//						
		//					}
		//				}
		//			}
		//		});


		//		tableAuthors.getSelectionModel()
		//        .selectedItemProperty()
		//        .addListener((observableValue, authorProps, authorProps2) -> {
		//            //This works:
		//            txtName.textProperty().bindBidirectional(authorProps2.authorsNameProperty());
		//            //This doesn't work:
		//            txtId.textProperty().bindBidirectional(authorProps2.authorsIdProperty());
		//        });

		//txtId.textProperty().bindBidirectional(authorProps2.authorsIdProperty(), new NumberStringConverter());

		//tbViewPosicaoEstoque.selectionModelProperty().bind(txtLocacao.textProperty());


		tbViewPosicaoEstoque.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {




			if (newSelection != null) {

				txtQtdMaxima.setText(tbViewPosicaoEstoque.getSelectionModel().getSelectedItem().getQtdMax().toString());
				txtQtdMinima.setText(tbViewPosicaoEstoque.getSelectionModel().getSelectedItem().getQtdMin().toString());
				txtLocacao.setText(tbViewPosicaoEstoque.getSelectionModel().getSelectedItem().getLocacao());

				// CARREGA OS DADOS DA LINHA SELECIONADA OBJETO INSTANCIADO
				txtDataUltRep.setText(util.dateFormatInToOut(tbViewPosicaoEstoque.getSelectionModel().getSelectedItem().getRepData().toString(),"yyyy-mm-dd", "dd/mm/yyyy"));
				txtNoDocUltRep.setText(tbViewPosicaoEstoque.getSelectionModel().getSelectedItem().getRepDocumento().toString());
				txtQtdUltRep.setText(tbViewPosicaoEstoque.getSelectionModel().getSelectedItem().getRepQuantidade().toString());
				txtCodFornUltRep.setText(tbViewPosicaoEstoque.getSelectionModel().getSelectedItem().getRepCodFornecedor().toString());
				txtFornUltRep.setText(tbViewPosicaoEstoque.getSelectionModel().getSelectedItem().getRepFornecedor());
				txtCodTributacao.setText(tbViewPosicaoEstoque.getSelectionModel().getSelectedItem().getTributacao() != null ? tbViewPosicaoEstoque.getSelectionModel().getSelectedItem().getTributacao().getCodigo().toString() : "");
				txtTributacao.setText(tbViewPosicaoEstoque.getSelectionModel().getSelectedItem().getTributacao() != null ? tbViewPosicaoEstoque.getSelectionModel().getSelectedItem().getTributacao().getDescricao() : "" );
				
				//ATUALIZA A TABELA DE ESTOQUE POR DEPOSITOS DE ACORDO COM A EMPRESA SELECIONADA
				tbViewEstoqueDepositos.getItems().clear();
				
				for (ItemEstoqueDeposito item : entidadeBean.getItemEstoqueDeposito()) {
				    if(item.getCodemp().equals(tbViewPosicaoEstoque.getSelectionModel().getSelectedItem().getCodemp()))
				    	tbViewEstoqueDepositos.getItems().add(item);
				}
				
				tbViewEstoqueDepositos.refresh();
			
			}

		});


		//TABLEVIEW PRECOS DE EMPRESA
		tbColEmpPreco.setCellValueFactory(new PropertyValueFactory<ItemValor, String>("codemp"));
		tbColDtUltPreco.setCellValueFactory(new PropertyValueFactory<ItemValor, LocalDateTime>("altPrecoData"));
		tbColUsuarioPreco.setCellValueFactory(new PropertyValueFactory<ItemValor, String>("altPrecoUsuario"));
		tbColPrecoCusto.setCellValueFactory(new PropertyValueFactory<ItemValor, BigDecimal>("vlrCusto"));

		tbViewPrecosEmp.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {

			if ( newSelection != null ) {

				//					ItemValor iv = tbViewPrecosEmp.getSelectionModel().getSelectedItem();
				//					cpc.UpdateForm(iv);
				//					
				// CARREGA OS DADOS DA LINHA SELECIONADA OBJETO INSTANCIADO


			}
		});


		tbViewPrecosEmp.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode().equals(KeyCode.ENTER)) {


				cpc.UpdateForm(tbViewPrecosEmp.getSelectionModel().getSelectedItem());
				Util.openFormCadastro(anchorPaneDetalhes,anchorPaneFormacaoPreco, 0);


			}

		});

		// EVENTO ON FOCUS DO BOTAO DE AJUDA
		toggleHelp.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue) {
					if (!popOver.isFocused()) {
						popOver.hide();
						toggleHelp.setSelected(false);
					} else {
						popOver.hide();
						toggleHelp.setSelected(false);
					}

				}
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
			TableRow<Item> row = new TableRow<Item>() {
				@Override
				public void updateItem(Item objeto, boolean empty) {
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
					itemExcluir.setOnAction(new EventHandler<ActionEvent>() 
					{						
						@Override
						public void handle(ActionEvent e) {
							actionBtnDelete(null);
						}
					});

					// ITEM 3 DO MENU DE AÇOES - RASTREAR PRODUTOS ASSOCIADOS A ESTE DEPARTAMENTO
					MenuItem itemRastreiaProd = new MenuItem(DadosGlobais.resourceBundle.getString("actionRastrearProduto"));
					if(Util.getNivelAcessoEntidade(EnumNivelAcesso.ITENS).getFlagView().equals(0)){
						itemRastreiaProd.setDisable(true);
					}
					itemRastreiaProd.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent e) {	
							//							try {
							//							//	util.crearTab(tabPrincipal, DadosGlobais.resourceBundle.getString("miCadItem") , "/views/compras/viewItens.fxml", 
							//								//		new ItemController(Util.getNivelAcessoEntidade(EnumNivelAcesso.ITENS), "Cod_Item", tbView.getSelectionModel().getSelectedItem().getCodigo().toString() ), false);
							//							} catch (IOException e1) {
							//								util.tratamentoExcecao(e1.getMessage().toString(),"[ ItemController.acaoRastrearItens() ]");
							//							}
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


		tbViewCodBarras.setEditable(true);
		tbColQtdEmbalagem.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalStringConverter()));
		tbColQtdEmbalagem.setOnEditCommit(new EventHandler<CellEditEvent<ItemCodBar, BigDecimal>>() {

			@Override
			public void handle(CellEditEvent<ItemCodBar, BigDecimal> arg0) {
				// TODO Auto-generated method stub
				try{

					if(arg0.getNewValue().compareTo(BigDecimal.valueOf(0.0000)) == 1)
						arg0.getRowValue().setQtdEmbalagem(arg0.getNewValue());
				}
				catch(Exception e){

				}


			}

		});


		tbViewCodBarras.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode().equals(KeyCode.DELETE) && tbViewCodBarras.getSelectionModel().getSelectedItem().getFlagAtivo().equals(1)) {
				if(!tbViewCodBarras.getSelectionModel().getSelectedItem().getItem().getCodigo().toString().equals(tbViewCodBarras.getSelectionModel().getSelectedItem().getCodBarras())){
					if(tbViewCodBarras.getSelectionModel().getSelectedItem().getFlagCodbarPrincipal().equals(0)){
						if (util.showAlert(DadosGlobais.resourceBundle.getString("alertConfirmOprExcluir"),"confirmation")) {
							tbViewCodBarras.getSelectionModel().getSelectedItem().setFlagAtivo(0);
							tbViewCodBarras.refresh();	
						}
					}else{
						Util.alertWarn("Selecione um outro código de barras como principal antes de excluir!");
					}
				}else{
					Util.alertWarn("Este Código de Barra não pode ser excluido");
				}
			}
		});

		tbViewCodBarras.setRowFactory(tb -> {
			// EXIBE O EFEITO DE OPACIDADE NAS LINHAS CASO O ITEM DO TABLEVIEW
			// TENHA O FLAGATIVO = 0
			//TableRow<ItemCodBar> row2 = new TableRow<ItemCodBar>();

			TableRow<ItemCodBar> row2 = new TableRow<ItemCodBar>() {
				@Override
				public void updateItem(ItemCodBar objeto, boolean empty) {
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

			return row2;
		});


		//TABLEVIEW CODIGOS DE FORNECEDOR

		tbColCodItemFornecedor.setCellValueFactory(new PropertyValueFactory<ItemFornecedor, String>("codItemFornecedor"));
		tbColFornecedorItem.setCellValueFactory(new PropertyValueFactory<ItemFornecedor, String>("fornecedorDescricao"));

		tbViewItemFornecedor.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode().equals(KeyCode.DELETE) && tbViewItemFornecedor.getSelectionModel().getSelectedItem().getFlagAtivo().equals(1)) {


				if (util.showAlert(DadosGlobais.resourceBundle.getString("alertConfirmOprExcluir"),"confirmation")) {
					tbViewItemFornecedor.getSelectionModel().getSelectedItem().setFlagAtivo(0);
					tbViewItemFornecedor.refresh();	
				}

			}
		});

		tbViewItemFornecedor.setRowFactory(tb -> {
			// EXIBE O EFEITO DE OPACIDADE NAS LINHAS CASO O ITEM DO TABLEVIEW
			// TENHA O FLAGATIVO = 0
			//TableRow<ItemCodBar> row2 = new TableRow<ItemCodBar>();

			TableRow<ItemFornecedor> row3 = new TableRow<ItemFornecedor>() {
				@Override
				public void updateItem(ItemFornecedor objeto, boolean empty) {
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

			return row3;
		});
		
		

		//TABLEVIEW ESTOQUE POR DEPOSITO
		
		tbColCodDeposito.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ItemEstoqueDeposito,Integer>, ObservableValue<Integer>>() {
			@Override
			public ObservableValue<Integer> call(CellDataFeatures<ItemEstoqueDeposito, Integer> param) {
				
				return new ReadOnlyObjectWrapper<Integer>(param.getValue().getDepositoEstoque().getCodigo());
			}
		});
				
		tbColDeposito.setCellValueFactory(new PropertyValueFactory<ItemEstoqueDeposito, String>("depositoDescricao"));
		tbColDepQtdAtual.setCellValueFactory(new PropertyValueFactory<ItemEstoqueDeposito, BigDecimal>("qtdAtual"));
		tbColDepQtdDisponivel.setCellValueFactory(new PropertyValueFactory<ItemEstoqueDeposito, BigDecimal>("qtdDiponivel"));
		tbColDepQtdReservada.setCellValueFactory(new PropertyValueFactory<ItemEstoqueDeposito, BigDecimal>("qtdReservada"));
		tbColDepQtdCcusto1.setCellValueFactory(new PropertyValueFactory<ItemEstoqueDeposito, BigDecimal>("qtdCcusto1"));
		tbColDepQtdCcusto2.setCellValueFactory(new PropertyValueFactory<ItemEstoqueDeposito, BigDecimal>("qtdCcusto2"));
		
		
		
		// EVENTO DE CARREGA OS DADOS DETALHES AO SAIR DO CAMPO CODIGO
		txtCodigo.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (!newPropertyValue) {
					if (!txtCodigo.getText().isEmpty() && !txtCodigo.isDisable()) {
						if (!btnInsert.isPressed() && !btnClose.isPressed() && !btnCancel.isPressed()){
							loadByID(false, Integer.valueOf(txtCodigo.getText()), false);
						}

					}else{
						actionBtnInsert(null);
					}
				}
			}

		});
		
		
		// EVENTO DE CARREGA OS DADOS DETALHES AO SAIR DO CAMPO DESCRICAO
		txtDescricao.focusedProperty().addListener(new ChangeListener<Boolean>() {
					@Override
					public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
							Boolean newPropertyValue) {
						if (!newPropertyValue) {
							if (!txtDescricao.getText().isEmpty()) {
								if (!btnInsert.isPressed() && !btnClose.isPressed() && !btnCancel.isPressed()){
									txtDescReduzida.setText(txtDescricao.getText());
								}

							}
						}
					}

				});

		
		//EVENTO PARA SELECIONAR O TIPO DO ITEM
		cBoxTipoItem.getCheckModel().getCheckedItems().addListener(new ListChangeListener<ComboBoxFilter>() {
			public void onChanged(ListChangeListener.Change<? extends ComboBoxFilter> c) {
					
				while(c.next()){
					for (ComboBoxFilter tipoItem : c.getAddedSubList()) {
                       //CASO SELECIONE SERVICO , DESMARCA AS OUTRAS OPCOES
						if(((EnumTipoItem) tipoItem.getEnumOpcao()).equals(EnumTipoItem.SERVICOS)){
							for (int j = 0; j <  cBoxTipoItem.getItems().size(); j++) {
								if(cBoxTipoItem.getCheckModel().isChecked(j)){
									if(!((EnumTipoItem) cBoxTipoItem.getItems().get(j).getEnumOpcao()).equals(EnumTipoItem.SERVICOS)){
										cBoxTipoItem.getCheckModel().clearCheck(j);
									}
								}
							}
							//CASO SEJA MARCADO QUALQUER TIPO QUE NAO SEJA SERVICO , DESMARCA SERVICO
						}else{
							for (int j = 0; j <  cBoxTipoItem.getItems().size(); j++) {
								if(cBoxTipoItem.getCheckModel().isChecked(j)){
									if(((EnumTipoItem) cBoxTipoItem.getItems().get(j).getEnumOpcao()).equals(EnumTipoItem.SERVICOS)){
										cBoxTipoItem.getCheckModel().clearCheck(j);
									}
								}
							}
						}
                    }
				}
			}
		});

		
		txtCodFabricante.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (!newPropertyValue) {
					if (!btnInsert.isPressed() && !btnClose.isPressed() && !btnCancel.isPressed() && flagSearch && !txtCodFabricante.getRight().isPressed()){
						if (!txtCodFabricante.getText().isEmpty()){
							searchFabricante(Integer.parseInt(txtCodFabricante.getText()));		
						}
					}
				}
			}
		});


		txtFabricante.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,Boolean newPropertyValue) {
				if (!newPropertyValue) {
					txtCodFabricante.setDisable(false);
					txtFabricante.setEditable(false);
					Util.customEditedTextField("right", null, txtFabricante);	
				}
			}
		});

		txtCodGrupo.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
				if (!newPropertyValue) {
					if (!btnInsert.isPressed() && !btnClose.isPressed() && !btnCancel.isPressed() && flagSearch && !txtCodGrupo.getRight().isPressed()){
						if (!txtCodGrupo.getText().isEmpty()){
							searchGrupo(Integer.parseInt(txtCodGrupo.getText()));

						}
					}

				}

			}
		});

		txtGrupo.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,Boolean newPropertyValue) {
				if (!newPropertyValue) {
					if(!txtGrupo.getRight().isPressed()){
						txtCodGrupo.setDisable(false);
						txtGrupo.setEditable(false);
						Util.customEditedTextField("right", null, txtGrupo);
					}
				}
			}
		});

		txtCodSubGrupo.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (!newPropertyValue) {
					if (!btnInsert.isPressed() && !btnClose.isPressed() && !btnCancel.isPressed() && flagSearch && !txtCodSubGrupo.getRight().isPressed()){
						if (!txtCodSubGrupo.getText().isEmpty()){
							searchSubGrupo(Integer.parseInt(txtCodSubGrupo.getText()));	
						}
					}
				}
			}
		});

		txtSubGrupo.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,Boolean newPropertyValue) {
				if (!newPropertyValue) {	
					if(!txtSubGrupo.getRight().isPressed()){
						txtCodSubGrupo.setDisable(false);
						txtSubGrupo.setEditable(false);
						Util.customEditedTextField("right", null, txtSubGrupo);	
					}
				}
			}
		});

		txtCodDepartamento.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
				if (!newPropertyValue) {
					if (!btnInsert.isPressed() && !btnClose.isPressed() && !btnCancel.isPressed() && flagSearch && !txtCodDepartamento.getRight().isPressed() ){
						if (!txtCodDepartamento.getText().isEmpty()){
							searchDepartamento(Integer.parseInt(txtCodDepartamento.getText()));	
						}
					}
				}
			}
		});

		txtDepartamento.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,Boolean newPropertyValue) {
				if (!newPropertyValue) {
					if(!txtDepartamento.getRight().isPressed()){
						txtCodDepartamento.setDisable(false);
						txtDepartamento.setEditable(false);
						Util.customEditedTextField("right", null, txtDepartamento);
					}
				}
			}
		});


		txtCodFamiliaPreco.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (!newPropertyValue) {
					if (!btnInsert.isPressed() && !btnClose.isPressed() && !btnCancel.isPressed() && flagSearch && !txtCodFamiliaPreco.getRight().isPressed() ){
						if (!txtCodFamiliaPreco.getText().isEmpty()){
							searchFamiliaPreco(Integer.parseInt(txtCodFamiliaPreco.getText()));	
						}
					}
				}
			}
		});

		txtFamiliaPreco.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,Boolean newPropertyValue) {
				if (!newPropertyValue) {
					if(!txtFamiliaPreco.getRight().isPressed()){
						txtCodFamiliaPreco.setDisable(false);
						txtFamiliaPreco.setEditable(false);
						Util.customEditedTextField("right", null, txtFamiliaPreco);
					}
				}
			}
		});


		txtCodMoeda.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (!newPropertyValue) {
					if (!btnInsert.isPressed() && !btnClose.isPressed() && !btnCancel.isPressed() && flagSearch && !txtCodMoeda.getRight().isPressed() ){
						if (!txtCodMoeda.getText().isEmpty()){
							searchMoeda(Integer.parseInt(txtCodMoeda.getText()));	
						}
					}
				}
			}
		});

		txtCodUnidade.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (!newPropertyValue) {
					if (!btnInsert.isPressed() && !btnClose.isPressed() && !btnCancel.isPressed() && flagSearch && !txtCodUnidade.getRight().isPressed() ){
						if (!txtCodUnidade.getText().isEmpty()){
							searchUnidade(Integer.parseInt(txtCodUnidade.getText()), txtUnidade, txtCodUnidade, "principal");	
						}
					}
				}
			}
		});

		txtUnidade.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,Boolean newPropertyValue) {
				if (!newPropertyValue) {
					if(!txtUnidade.getRight().isPressed()){
						txtCodUnidade.setDisable(false);
						txtUnidade.setEditable(false);
						Util.customEditedTextField("right", null, txtUnidade);
					}
				}
			}
		});


		txtCodUnidadeEmb.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (!newPropertyValue) {
					if (!btnInsert.isPressed() && !btnClose.isPressed() && !btnCancel.isPressed() && flagSearch && !txtCodUnidadeEmb.getRight().isPressed()){
						if (!txtCodUnidadeEmb.getText().isEmpty()){
							searchUnidade(Integer.parseInt(txtCodUnidadeEmb.getText()), txtUnidadeEmb, txtCodUnidadeEmb, "embalagem");	
						}
					}
				}
			}
		});

		txtCodLcServico.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (!newPropertyValue) {
					if (!btnInsert.isPressed() && !btnClose.isPressed() && !btnCancel.isPressed() && flagSearch && !txtCodLcServico.getRight().isPressed()){
						if (!txtCodLcServico.getText().isEmpty()){
							searchLcServico(Integer.parseInt(txtCodLcServico.getText()));	
						}
					}
				}
			}
		});

		txtCodNcm.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (!newPropertyValue) {
					if (!btnInsert.isPressed() && !btnClose.isPressed() && !btnCancel.isPressed() && flagSearch && !txtCodNcm.getRight().isPressed()){
						if (!txtCodNcm.getText().isEmpty()){
							searchNcm(txtCodNcm.getText());	
						}
					}
				}
			}
		});


		//------------------- ABA POSICAO DE ESTOQUE --------------------------------
		//		
		//		txtLocacao.textProperty().addListener(new ChangeListener<String>() {
		//
		//			@Override
		//			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		//				// TODO Auto-generated method stub
		////				tbViewPosicaoEstoque.getSelectionModel().getSelectedItem().setLocacao(txtLocacao.getText());
		////				tbViewPosicaoEstoque.refresh();
		//				setChangeItemValor("LOCACAO");
		//			}
		//		});
		//		//txtId.textProperty().bindBidirectional(authorProps2.authorsIdProperty(), new NumberStringConverter());
		//		
		//		
		//		
		//		txtQtdMinima.textProperty().addListener(new ChangeListener<String>() {
		//
		//			@Override
		//			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		//				// TODO Auto-generated method stub
		//				//if (txtQtdMinima.getText().isEmpty())
		//					//txtQtdMinima.setText("0.00");
		//				
		//				//tbViewPosicaoEstoque.getSelectionModel().getSelectedItem().setQtdMin(Util.decimalBRtoBigDecimal(2, txtQtdMinima.getText()));
		//				//tbViewPosicaoEstoque.refresh();
		//				
		//				setChangeItemValor("QTDMIN");
		//				
		//			}
		//		});
		//		
		////		
		//		txtQtdMaxima.textProperty().addListener(new ChangeListener<String>() {
		//
		//			@Override
		//			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		//				// TODO Auto-generated method stub
		//				//if (txtQtdMaxima.getText().isEmpty())
		//					//txtQtdMaxima.setText("0.00");
		//				
		////				tbViewPosicaoEstoque.getSelectionModel().getSelectedItem().setQtdMax(Util.decimalBRtoBigDecimal(2, txtQtdMaxima.getText()));
		////				tbViewPosicaoEstoque.refresh();
		//				setChangeItemValor("QTDMAX");
		//			}
		//		});
		////		
		//		txtQtdMinima.focusedProperty().addListener(new ChangeListener<Boolean>() {
		//			@Override
		//			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
		//					Boolean newPropertyValue) {
		//				if (!newPropertyValue) {
		//					if (!btnInsert.isPressed() && !btnClose.isPressed() && !btnCancel.isPressed()){
		//						if (txtQtdMinima.getText().isEmpty())
		//							txtQtdMinima.setText("0.00");
		//							
		//						tbViewPosicaoEstoque.getSelectionModel().getSelectedItem().setQtdMin(Util.decimalBRtoBigDecimal(2, txtQtdMinima.getText()));
		//						tbViewPosicaoEstoque.refresh();	
		//					}
		//				}
		//			}
		//		});

		//		txtQtdMaxima.focusedProperty().addListener(new ChangeListener<Boolean>() {
		//			@Override
		//			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
		//					Boolean newPropertyValue) {
		//				if (!newPropertyValue) {
		//					if (!btnInsert.isPressed() && !btnClose.isPressed() && !btnCancel.isPressed()){
		//					
		//						if (txtQtdMaxima.getText().isEmpty())
		//							txtQtdMaxima.setText("0.00");
		//				
		//						tbViewPosicaoEstoque.getSelectionModel().getSelectedItem().setQtdMax(Util.decimalBRtoBigDecimal(2, txtQtdMaxima.getText()));
		//						tbViewPosicaoEstoque.refresh();	
		//					
		//					}
		//				}
		//			}
		//		});




		txtCodTributacao.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (!newPropertyValue) {
					if (!btnInsert.isPressed() && !btnClose.isPressed() && !btnCancel.isPressed() && flagSearch && !txtCodTributacao.getRight().isPressed()){
						if (!txtCodTributacao.getText().isEmpty()){
							searchTributacao(Integer.parseInt(txtCodTributacao.getText()));
						}
					}
				}
			}
		});

		txtCodFornecedor.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (!newPropertyValue) {
					if (!btnInsert.isPressed() && !btnClose.isPressed() && !btnCancel.isPressed() && flagSearch && !txtCodFornecedor.getRight().isPressed()){
						if (!txtCodFornecedor.getText().isEmpty()){
							searchFornecedor(Integer.parseInt(txtCodFornecedor.getText()));
						}
					}
				}
			}
		});

		//		txtCodBar.focusedProperty().addListener(new ChangeListener<Boolean>() {
		//			@Override
		//			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
		//					Boolean newPropertyValue) {
		//				if (!newPropertyValue) {
		//					if (!btnInsert.isPressed() && !btnClose.isPressed() && !btnCancel.isPressed() && flagSearch){
		//						if (!txtCodBar.getText().isEmpty()){
		//							LogRetorno logRetorno = new LogRetorno();
		//							logRetorno = entidadeDao.getCodBarById(txtCodBar.getText());
		//							if(!logRetorno.getStatus().equals(EnumLogRetornoStatus.SUCESSO)){
		//								Util.alertInf(logRetorno.getMsg());
		//								btnAddCodBar.setDisable(true);
		//								txtCodBar.requestFocus();
		//							}else{
		//								btnAddCodBar.setDisable(false);
		//							}
		//								
		//						}
		//					}
		//				}
		//			}
		//		});


		//--------------------------------------------------ITEMS SIMILARES FOCUSED PROPERTYS-------------------------------

		txtItemSimilar1.focusedProperty().addListener(new ChangeListener<Boolean>() 
		{
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) 
			{
				if (!newPropertyValue) 
				{
					if (!btnInsert.isPressed() && !btnClose.isPressed() && !btnCancel.isPressed() && flagSearch && !txtItemSimilar1.getRight().isPressed())
					{
						if (!txtItemSimilar1.getText().isEmpty())
						{
							searchItemSimilar(Integer.parseInt(txtItemSimilar1.getText()), txtItemSimilar1, txtItemSimilar1Fk);		
						}
					}
				}
			}
		});

		txtItemSimilar2.focusedProperty().addListener(new ChangeListener<Boolean>() 
		{
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) 
			{
				if (!newPropertyValue) 
				{
					if (!btnInsert.isPressed() && !btnClose.isPressed() && !btnCancel.isPressed() && flagSearch && !txtItemSimilar2.getRight().isPressed())
					{
						if (!txtItemSimilar2.getText().isEmpty())
						{
							searchItemSimilar(Integer.parseInt(txtItemSimilar2.getText()), txtItemSimilar2, txtItemSimilar2Fk);		
						}
					}
				}
			}
		});	

		txtItemSimilar3.focusedProperty().addListener(new ChangeListener<Boolean>() 
		{
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) 
			{
				if (!newPropertyValue) 
				{
					if (!btnInsert.isPressed() && !btnClose.isPressed() && !btnCancel.isPressed() && flagSearch && !txtItemSimilar3.getRight().isPressed())
					{
						if (!txtItemSimilar3.getText().isEmpty())
						{
							searchItemSimilar(Integer.parseInt(txtItemSimilar3.getText()), txtItemSimilar3, txtItemSimilar3Fk);		
						}
					}
				}
			}
		});	

		txtItemSimilar4.focusedProperty().addListener(new ChangeListener<Boolean>() 
		{
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) 
			{
				if (!newPropertyValue) 
				{
					if (!btnInsert.isPressed() && !btnClose.isPressed() && !btnCancel.isPressed() && flagSearch && !txtItemSimilar4.getRight().isPressed())
					{
						if (!txtItemSimilar4.getText().isEmpty())
						{
							searchItemSimilar(Integer.parseInt(txtItemSimilar4.getText()), txtItemSimilar4, txtItemSimilar4Fk);		
						}
					}
				}
			}
		});	


		txtItemSimilar5.focusedProperty().addListener(new ChangeListener<Boolean>() 
		{
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) 
			{
				if (!newPropertyValue) 
				{
					if (!btnInsert.isPressed() && !btnClose.isPressed() && !btnCancel.isPressed() && flagSearch && !txtItemSimilar5.getRight().isPressed())
					{
						if (!txtItemSimilar5.getText().isEmpty())
						{
							searchItemSimilar(Integer.parseInt(txtItemSimilar5.getText()), txtItemSimilar5, txtItemSimilar5Fk);		
						}
					}
				}
			}
		});	

		txtItemSimilar6.focusedProperty().addListener(new ChangeListener<Boolean>() 
		{
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) 
			{
				if (!newPropertyValue) 
				{
					if (!btnInsert.isPressed() && !btnClose.isPressed() && !btnCancel.isPressed() && flagSearch && !txtItemSimilar6.getRight().isPressed())
					{
						if (!txtItemSimilar6.getText().isEmpty())
						{
							searchItemSimilar(Integer.parseInt(txtItemSimilar6.getText()), txtItemSimilar6, txtItemSimilar6Fk);		
						}
					}
				}
			}
		});			
		//--------------------------------------END ITEMS SIMILARES FOCUSED PROPERTYS-------------------------------		


		// TECLAS DE ATALHOS PARA O FORMULARIO
		// F2 - CONSULTAR | F4 - CARREGAR TODOSO OS DADOS REFRESH| F5 - INSERIR NOVO REGISTRO | F6 - GRAVAR | DEL - EXCLUIR |F7 - CANCELAR || CTRL+P IMPRIMIR
		anchorPanePrincipal.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			switch (event.getCode()) {
			case F2:
				if (!txtCodFabricante.isFocused() && !txtCodGrupo.isFocused() && !txtCodSubGrupo.isFocused() && !txtCodMoeda.isFocused() && 
						!txtCodDepartamento.isFocused() && !txtCodFamiliaPreco.isFocused() && !txtCodUnidade.isFocused() && 
						!txtCodUnidadeEmb.isFocused() && !txtCodNcm.isFocused() && !txtCodLcServico.isFocused() && !txtItemSimilar1.isFocused() && 
						!txtItemSimilar2.isFocused() && !txtItemSimilar3.isFocused() && !txtItemSimilar4.isFocused() && !txtItemSimilar5.isFocused() &&
						!txtItemSimilar6.isFocused() && !txtCodTributacao.isFocused() && !txtCodFornecedor.isFocused()) {
					if (anchorPaneDetalhes.isVisible()) {
						txtCodigo.setDisable(true);
						actionBtnClose(null);
					}
					flagPaneFilter = false;
					actionBtnFilter(null);
				}
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
					btnClone.setDisable(true);
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