package controllers.application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.CustomTextField;
import application.DadosGlobais;
import application.Main;
import connect.ConnectionHib;
import controllers.compras.AplicacaoController;
import controllers.compras.BoxLocalEstoqueController;
import controllers.compras.CorEstiloController;
import controllers.compras.DepartamentoController;
import controllers.compras.FabricanteController;
import controllers.compras.FamiliaPrecoController;
import controllers.compras.FornecedorController;
import controllers.compras.GradeController;
import controllers.compras.GrupoController;
import controllers.compras.ItemController;
import controllers.compras.NotaFiscalEntradaController;
import controllers.compras.TabelaNCMController;
import controllers.compras.OperacaoEntradaController;
import controllers.compras.SegmentoController;
import controllers.compras.SubGrupoController;
import controllers.compras.TabelaLcServicoController;
import controllers.compras.TributacaoController;
import controllers.compras.UnidadeController;
import controllers.configuracoes.AjustesSistemaController;
import controllers.configuracoes.AuditoriaController;
import controllers.configuracoes.CompartilharEmpresasController;
import controllers.configuracoes.LayoutsArquivoController;
import controllers.configuracoes.DepositoEstoqueController;
import controllers.configuracoes.EmpresaController;
import controllers.configuracoes.ImportacaoDadosController;
import controllers.configuracoes.ParametrosController;
import controllers.configuracoes.TabelaAuxiliarCfopController;
import controllers.configuracoes.TabelaAuxiliarCstCofinsController;
import controllers.configuracoes.TabelaAuxiliarCstIcmsController;
import controllers.configuracoes.TabelaAuxiliarCstIpiController;
import controllers.configuracoes.TabelaAuxiliarCstPiController;
import controllers.configuracoes.NivelesAcessoController;
import controllers.configuracoes.OperacaoFinanceiroController;
import controllers.configuracoes.OperacaoSaidaController;
import controllers.configuracoes.UsuarioController;
import controllers.financeiro.CaixaBancoController;
import controllers.financeiro.CentroCustoController;
import controllers.financeiro.ContasReceberController;
import controllers.financeiro.MoedaController;
import controllers.financeiro.PlanoContaController;
import controllers.financeiro.PortadorController;
import controllers.financeiro.SecaoController;
import controllers.recursosHumanos.FuncionarioController;
import controllers.utils.ControleSegurancaController;
import controllers.utils.ProgressBarForm;
import controllers.utils.TrocaDataSistemaController;
import controllers.utils.TrocaEmpresaController;
import controllers.utils.TrocaUsuarioController;
import controllers.vendas.CidadeController;
import controllers.vendas.ClienteController;
import controllers.vendas.ConvenioController;
import controllers.vendas.MensagemController;
import controllers.vendas.UfController;
import controllers.vendas.PaisController;
import controllers.vendas.PlanoPagamentoController;
import controllers.vendas.RamosActvClientesController;
import controllers.vendas.RegiaoController;
import controllers.vendas.RotaController;
import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.Duration;
import models.compras.OperacaoEntrada1;
import models.configuracoes.Compartilhamento;
import models.configuracoes.NivelAcesso;
import models.financeiro.CaixaBanco;
import models.financeiro.CentroCusto;
import models.financeiro.PlanoConta;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import tools.criptografia.EncryptMD5;
import tools.enums.EnumNivelAcesso;
import tools.utils.Util;

/**
 * Classe de controle do Form TelaPrincipalEptus. Com todos os controles no
 * formulário e ações.
 * 
 * @author User
 */
public class TelaPrincipalEptusController implements Initializable, EventHandler<ActionEvent> {

	// Controles do Form TelaPrincipalEptus-----------------------------

	@FXML
	private AnchorPane panePrincipal, panelEscondido;
	@FXML
	private StackPane stackPane;
	@FXML
	private ScrollPane scrollPaneEscond;

	@FXML
	private ListView<String> listViewMenu;

	@FXML
	private Button btnPrueba, btnClosedPaneEscond;

	@FXML
	private TabPane tabPanePrincipal;

	@FXML
	private Label server, C100;

	@FXML
	private Tab tab;

	@FXML
	private AnchorPane anchorPaneTab;

	@FXML
	private ToolBar toolbarPrincipal;

	@FXML
	private Button btnTolBarIconCand;

	@FXML
	private Button btnTolBarIconDatBase;

	@FXML
	private Button btnTolBarIconCalc;

	@FXML
	private Button btnTolBarIconPrinted;

	@FXML
	private Button btnTolBarIconPhone;

	@FXML
	private Button btnTolBarIconOut;

	@FXML
	private MenuBar mnBar;

	@FXML
	private Button btnClose;

	@FXML
	private Button btnMinimize;

	// ------------- Menu Compras ------------------------
	@FXML
	private Menu mnHeaCompras;

	@FXML
	private Menu mnRotCadastros;

	@FXML
	private MenuItem miCadUnidade, miCadTipFornecedor, miCadFornecedor, miCadFabricante, miCadSubGrupo, miCadCorEstilo,
			miCadGrupo, miCadTributacao, miCadTributacaoCFOP, miCadAplicacao, miCadFamiliaPreco, miCadBoxLocalEstoque,
			miCadTabelaLcServico, miCadTabelaNCM, miCadOperacaoEntrada, miCadOperacaoSaida, miCadDepartamento,
			miCadGrade, miCadItem, miCadAction, miCadLayArqFabricante, miCadTipGarantia, miCadCstIcms;

	@FXML
	private Menu mnRotConsultas;

	@FXML
	private Menu mnRelRelatorios;

	@FXML
	private Menu mnOpePromocaoProd;

	@FXML
	private Menu mnRepProdComp;

	@FXML
	private Menu mnCancRepComp;

	@FXML
	private Menu mnPlanConProdComp;

	@FXML
	private Menu mnDesagProdComp;

	@FXML
	private MenuItem mniSugCompComp;

	@FXML
	private MenuItem mniCalEstRegComp;

	@FXML
	private MenuItem mniCotPreComp;

	@FXML
	private Menu mnPedCompComp;

	@FXML
	private MenuItem miOpeNotaFiscais;

	@FXML
	private Menu mnGesDocFisEleComp;

	@FXML
	private Menu mnGesComABCComp;

	// ------------- Menu Vendas ------------------------
	@FXML
	private Menu mnHeaVendas;

	@FXML
	private Menu mnRot0111Cadastros;

	@FXML
	private MenuItem miCadPais;

	@FXML
	private MenuItem miCadUF;

	@FXML
	private MenuItem miCadCidade;

	@FXML
	private MenuItem miCadRotas;

	@FXML
	private MenuItem miCadRamoAtividade;

	@FXML
	private MenuItem miCadRegioesClientes;

	@FXML
	private MenuItem miCadConvenio;

	@FXML
	private MenuItem miCadCliente;

	@FXML
	private MenuItem miCadPlanoPagamento;

	@FXML
	private MenuItem miCadMensagem;

	@FXML
	private Label V1001;

	@FXML
	private Label V1011;

	@FXML
	private Label V1021;

	@FXML
	private Label V1031;

	// ------------- Menu Financeiro ------------------------
	@FXML
	private Menu mnHeaFinanceiro;

	@FXML
	private Menu mnRotFinancieroCadastros;

	@FXML
	private MenuItem miCadSecao;

	@FXML
	private MenuItem miCadMoeda;

	@FXML
	private MenuItem miCadPortador;

	@FXML
	private MenuItem miCadCentroCusto;

	@FXML
	private MenuItem miCadClienteFin;

	@FXML
	private Menu mnRotCaixaBanco;

	@FXML
	private MenuItem miCadCadastro;

	@FXML
	private Menu mnRotConsultasFin;
	
	@FXML
	private Menu mnRotOperacoesFin;	
	
	@FXML
	private MenuItem miOpeContasReceberFin;

	@FXML
	private Menu mnRelFin;

	@FXML
	private Menu mnConPagFin;

	@FXML
	private Menu mnConRecFin;

	@FXML
	private Menu mnCaixTesFin;

	@FXML
	private Menu mnCheqPreDatFin;

	@FXML
	private Menu mnContCaixMovFin;

	@FXML
	private Menu mnComBanAdmCartFin;

	@FXML
	private Menu mnAnaVenFin;

	@FXML
	private Menu mnResGerFin;

	@FXML
	private MenuItem mniConEstFinFin;

	@FXML
	private Menu mnConvDiarMoeFin;

	@FXML
	private Menu mnConPresSerFin;

	@FXML
	private Menu mnGesFretFin;

	@FXML
	private Menu mnGestAnaCredFin;

	@FXML
	private Menu mnConCorClienFin;

	@FXML
	private Menu mnContReciCarnFin;

	@FXML
	private Menu mnProgFidClieFin;

	// -------------- Menu Produção -----------------------------
	@FXML
	private Menu mnHea0111Producao;

	// -------------- Menu Serviços -----------------------------
	@FXML
	private Menu mnHea0111Servicos;

	// ------------- Menu Livro Fiscais ------------------------
	@FXML
	private Menu mnHea0101LivFiscais;

	@FXML
	private Menu mnEscLivFisc;

	@FXML
	private Menu mnRelLivFisc;

	@FXML
	private Menu mnEmiCupFiscLivFisc;

	@FXML
	private Menu mnSintLivFisc;

	@FXML
	private Menu mnSPEDFisContLivFisc;

	// -------------- Menu Contabilidade ------------------------
	@FXML
	private Menu mnHea0111Contabilidade;

	// ------------- Menu Recursos Humanos ------------------------
	@FXML
	private Menu mnHeaRecHumanos;

	@FXML
	private Menu mnRotRHCadastros;

	@FXML
	private MenuItem miCadFuncionario;

	@FXML
	private Menu mnConRecHum;

	@FXML
	private Menu mnRelRecHum;

	@FXML
	private Menu mnLanCalRecHum;

	@FXML
	private MenuItem mniQuiFolPagRecHum;

	@FXML
	private Menu mnDefLayDocRecHum;

	@FXML
	private MenuItem mniConfAmbRecHum;

	// ------------- Menu AutomAT ------------------------
	// @FXML
	// private Menu mnAutomAT;

	@FXML
	private Menu mnCadAutomAT;

	@FXML
	private Menu mnConAutomAT;

	@FXML
	private Menu mnRelAutomAT;

	@FXML
	private Menu mnOrdSerAutomAT;

	@FXML
	private MenuItem mnConfAmbAutomAT;

	// ------------- Menu Utilitarios ------------------------
	@FXML
	private Menu mnHeaConfiguracao;

	@FXML
	private Menu mnRotCadConfig;
	
	@FXML
	private MenuItem miCadOperacaoFinanceiro;

	@FXML
	private MenuItem miOpeEmpresaConfig;

	@FXML
	private MenuItem miCadUsuarios;

	@FXML
	private Menu mnRot0111DefNivUser;

	@FXML
	private MenuItem miCad0111ManualDefNiv;

	@FXML
	private MenuItem mniAutDefNiv;

	@FXML
	private MenuItem mniSecundDefNiv;

	@FXML
	private MenuItem mniImpressCadUtil;

	@FXML
	private MenuItem miCadDepositoEstoque;

	@FXML
	private Menu mnRelUtil;

	@FXML
	private MenuItem mniEmiMalDirUtil;

	@FXML
	private Menu mnIntSistContUtil;

	@FXML
	private Menu mnIntEptBacUpCentUtil;

	@FXML
	private Menu mnIntEptPayUtil;

	@FXML
	private Menu mnIntPoSPacECoPAFUtil;

	@FXML
	private Menu mnIntECoDCUtil;

	@FXML
	private MenuItem miOpeAuditoria;

	@FXML
	private Label U100;

	@FXML
	private MenuItem miOpeControlCompartilhamento;

	@FXML
	private MenuItem miOpeAjusteSistema;

	@FXML
	private MenuItem miOpeManutencaoParam;

	// ------------- Menu Ajuda ------------------------
	@FXML
	private Menu mnHea0111Ajuda;

	@FXML
	private Menu mnRot0111Lenguagem;

	@FXML
	private CheckMenuItem ckmniEspanhol;

	@FXML
	private CheckMenuItem ckmniPortugues;

	@FXML
	private CheckMenuItem ckmniEnglish;

	@FXML
	private Menu mnRot0111Temas;

	@FXML
	private CheckMenuItem ckmiTemStarter;

	@FXML
	private CheckMenuItem ckmniProfTemAju1;

	@FXML
	private CheckMenuItem ckmniEntTemAju2;

	@FXML
	private CheckMenuItem ckmniUltTemAju3;

	@FXML
	private Pane paneFooter;

	private CustomTextField txtBuscarMenu;

	@FXML
	private MenuItem miCad0111CadPadrao;

	@FXML
	private MenuItem miCad0111GerarMenu;

	@FXML
	private MenuItem miCad0111Empresa;

	@FXML
	private Label A100;

	TranslateTransition cerrar = null;

	// --------INFORMACION DEL SISTEMA---------------------
	@FXML
	private Label usuarioText;

	@FXML
	private Label lblTitle;

	@FXML
	private HBox ImgHBox;

	@FXML
	private ImageView idiomaImg;

	@FXML
	private Label empresaText;

	@FXML
	private Label serverText;

	@FXML
	private Label dataLocalText;

	private static List<String> mList = new ArrayList<String>();

	@FXML
	private ContextMenu contMenuBuscar;

	private List<NivelAcesso> permissao = null;

	Util util = new Util();

	private PopOver popOver = new PopOver();

	public MenuBar mnBar(MenuBar m) {

		return m;
	}

	// -------Actions Fechar e Minimizar-------------------------------//

	@FXML
	void actionBtnClose(ActionEvent event) {
		// ConnectionHib.emf.close();
		System.exit(0);
	}

	@FXML
	void actionBtnMinimize(ActionEvent event) {
		((Stage) panePrincipal.getScene().getWindow()).setIconified(true);
	}

	// ---------------------------------- Actions Menu Commpras
	// Cadastros-------------------------------//
	private static Stage stg;

	@FXML
	/**
	 * Ação do menu Compras Cadastros Item. Exibe janelas com Form Unidade.
	 */
	private void actionMniItem(ActionEvent evt) throws IOException {
		crearTab(DadosGlobais.resourceBundle.getString("miCadItem"), "/views/compras/viewItem.fxml",
				new ItemController(Util.getNivelAcessoEntidade(EnumNivelAcesso.ITENS), tabPanePrincipal));
	}

	@FXML
	/**
	 * Ação do menu Compras Cadastros Unidadde. Exibe janelas com Form Unidade.
	 */
	private void actionMniUnidade(ActionEvent evt) throws IOException {
		crearTab(DadosGlobais.resourceBundle.getString("miCadUnidade"), "/views/compras/viewUnidade.fxml",
				new UnidadeController(Util.getNivelAcessoEntidade(EnumNivelAcesso.UNIDADES), tabPanePrincipal));

	}

	@FXML
	/**
	 * Ação do menu item Compras Cadastros Grupos. Exibe janelas com Form
	 * Grupos.
	 */
	private void actionMniGrupo(ActionEvent evt) throws IOException {
		crearTab(DadosGlobais.resourceBundle.getString("miCadGrupo"), "/views/compras/viewGrupo.fxml",
				new GrupoController(Util.getNivelAcessoEntidade(EnumNivelAcesso.GRUPOS), tabPanePrincipal));
	}

	@FXML
	/**
	 * Ação do menu item Compras Cadastros SubGrupos. Exibe janelas com Form
	 * SubGrupos.
	 */
	private void actionMniSubGrupo(ActionEvent evt) throws IOException {

		crearTab(DadosGlobais.resourceBundle.getString("miCadSubGrupo"), "/views/compras/viewSubGrupo.fxml",
				new SubGrupoController(Util.getNivelAcessoEntidade(EnumNivelAcesso.SUBGRUPOS), tabPanePrincipal));
	}

	@FXML
	/**
	 * Ação do menu item Compras Cadastros Frabricantes. Exibe janelas com Form
	 * Fabricantes.
	 */
	private void actionMniFabricante(ActionEvent evt) throws IOException {

		crearTab(DadosGlobais.resourceBundle.getString("miCadFabricante"), "/views/compras/viewFabricante.fxml",
				new FabricanteController(Util.getNivelAcessoEntidade(EnumNivelAcesso.FABRICANTES), tabPanePrincipal));

	}

	@FXML
	/**
	 * Ação do menu item Compras Cadastros Departamentos. Exibe janelas com Form
	 * Grupos.
	 */
	private void actionMniDepartamento(ActionEvent evt) throws IOException {

		crearTab(DadosGlobais.resourceBundle.getString("miCadDepartamento"), "/views/compras/viewDepartamento.fxml",
				new DepartamentoController(Util.getNivelAcessoEntidade(EnumNivelAcesso.DEPARTAMENTOS),
						tabPanePrincipal));

	}

	@FXML
	/**
	 * Ação do menu item Compras Cadastros Grades. Exibe janelas com Form
	 * Grupos.
	 */
	private void actionMniGrade(ActionEvent evt) throws IOException {

		crearTab(DadosGlobais.resourceBundle.getString("miCadGrade"), "/views/compras/viewGrade.fxml",
				new GradeController(Util.getNivelAcessoEntidade(EnumNivelAcesso.GRADE_DE_PRODUTOS), tabPanePrincipal));

	}

	@FXML
	/**
	 * Ação do menu item Compras Cadastros CorEstilo. Exibe janelas com Form
	 * Grupos.
	 */
	private void actionMniCorEstilo(ActionEvent evt) throws IOException {

		crearTab(DadosGlobais.resourceBundle.getString("miCadCorEstilo"), "/views/compras/viewCorEstilo.fxml",
				new CorEstiloController(Util.getNivelAcessoEntidade(EnumNivelAcesso.COR_ESTILOS), tabPanePrincipal));
	}

	@FXML
	/**
	 * Ação do menu item Compras Cadastros Fornecedores. Exibe janelas com Form
	 * Fornecedores.
	 */
	private void actionMniFornecedor(ActionEvent evt) throws IOException {

		crearTab(DadosGlobais.resourceBundle.getString("miCadFornecedor"), "/views/compras/viewFornecedor.fxml",
				new FornecedorController(Util.getNivelAcessoEntidade(EnumNivelAcesso.FORNECEDORES), tabPanePrincipal));

	}

	@FXML
	/**
	 * Ação do menu item Compras Cadastros Tributações. Exibe janelas com Form
	 * Grupos.
	 */
	private void actionMniTributacao(ActionEvent evt) throws IOException {

		crearTab(DadosGlobais.resourceBundle.getString("miCadTributacao"), "/views/compras/viewTributacao.fxml",
				new TributacaoController(Util.getNivelAcessoEntidade(EnumNivelAcesso.TRIBUTACAO), tabPanePrincipal));

	}

	@FXML
	/**
	 * Ação do menu item Compras Cadastros Aplicacoes. Exibe janelas com Form
	 * Grupos.
	 */
	private void actionMniAplicacao(ActionEvent evt) throws IOException {

		crearTab(DadosGlobais.resourceBundle.getString("miCadAplicacao"), "/views/compras/viewAplicacao.fxml",
				new AplicacaoController(Util.getNivelAcessoEntidade(EnumNivelAcesso.APLICACOES), tabPanePrincipal));

	}

	@FXML
	/**
	 * Ação do menu item Compras Cadastros Familia de Precificação. Exibe
	 * janelas com Form Grupos.
	 */
	private void actionMniFamiliaPreco(ActionEvent evt) throws IOException {

		crearTab(DadosGlobais.resourceBundle.getString("miCadFamiliaPreco"), "/views/compras/viewFamiliaPreco.fxml",
				new FamiliaPrecoController(Util.getNivelAcessoEntidade(EnumNivelAcesso.FAMILIAS_DE_PRECIFICACAO),
						tabPanePrincipal));

	}

	@FXML
	/**
	 * Ação do menu item Compras Cadastros Box local de Estoque. Exibe janelas
	 * com Form Grupos.
	 */
	private void actionMniBoxLocalEstoque(ActionEvent evt) throws IOException {

		crearTab(DadosGlobais.resourceBundle.getString("miCadBoxLocalEstoque"),
				"/views/compras/viewBoxLocalEstoque.fxml", new BoxLocalEstoqueController(
						Util.getNivelAcessoEntidade(EnumNivelAcesso.BOX_LOCAL_ESTOQUE), tabPanePrincipal));

	}

	@FXML
	/**
	 * Ação do menu item Compras Cadastros Tabela NCM. Exibe janelas com Form
	 * Grupos.
	 */
	private void actionMniTabelaLcServico(ActionEvent evt) throws IOException {

		crearTab(DadosGlobais.resourceBundle.getString("miCadTabelaLcServico"),
				"/views/compras/viewTabelaLcServico.fxml", new TabelaLcServicoController(
						Util.getNivelAcessoEntidade(EnumNivelAcesso.TABELA_LC_SERVICOS), tabPanePrincipal));

	}

	@FXML
	/**
	 * Ação do menu item Compras Cadastros Tabela NCM. Exibe janelas com Form
	 * Grupos.
	 */
	private void actionMniTabelaNCM(ActionEvent evt) throws IOException {

		crearTab(DadosGlobais.resourceBundle.getString("miCadTabelaNCM"), "/views/compras/viewTabelaNCM.fxml",
				new TabelaNCMController(Util.getNivelAcessoEntidade(EnumNivelAcesso.TABELA_NCM), tabPanePrincipal));

	}

	@FXML
	/**
	 * Ação do menu Compras Notas Fiscais de Entrada. Exibe janelas com Form
	 * Reposição de Estoque.
	 */
	private void actionMniNotaFiscEntrada(ActionEvent evt) throws IOException {

		crearTab(DadosGlobais.resourceBundle.getString("miOpeNotaFiscais"), "/views/compras/viewNotaFiscalEntrada.fxml",
				new NotaFiscalEntradaController());

	}

	// ---------------------------------- FIM - Actions Menu Commpras
	// Cadastros-------------------------------//

	// --------------------------- Menu Vendas
	// ----------------------------------
	// --------------------------- Cadastros
	// ------------------------------------
	@FXML
	void actionMiCliente(ActionEvent evt) throws IOException {

		crearTab(DadosGlobais.resourceBundle.getString("miCadCliente"), "/views/vendas/viewCliente.fxml",
				new ClienteController(Util.getNivelAcessoEntidade(EnumNivelAcesso.CLIENTE), tabPanePrincipal));

	}

	@FXML
	void actionMiCadPais(ActionEvent evt) throws IOException {

		crearTab(DadosGlobais.resourceBundle.getString("miCadPais"), "/views/vendas/viewPais.fxml",
				new PaisController(Util.getNivelAcessoEntidade(EnumNivelAcesso.PAIS), tabPanePrincipal));

	}

	@FXML
	void actionMiCadUF(ActionEvent evt) throws IOException {

		crearTab(DadosGlobais.resourceBundle.getString("miCadUF"), "/views/vendas/viewUf.fxml",
				new UfController(Util.getNivelAcessoEntidade(EnumNivelAcesso.UF), tabPanePrincipal));

	}

	@FXML
	void actionMiCadCidade(ActionEvent evt) throws IOException {

		crearTab(DadosGlobais.resourceBundle.getString("miCadCidade"), "/views/vendas/viewCidade.fxml",
				new CidadeController(Util.getNivelAcessoEntidade(EnumNivelAcesso.CIDADES), tabPanePrincipal));

	}

	@FXML
	void actionMiCadRotas(ActionEvent evt) throws IOException {

		crearTab(DadosGlobais.resourceBundle.getString("miCadRotas"), "/views/vendas/viewRotas.fxml",
				new RotaController(Util.getNivelAcessoEntidade(EnumNivelAcesso.ROTAS), tabPanePrincipal));

	}

	@FXML
	void actionMiCadRamoAtividade(ActionEvent evt) throws IOException {

		crearTab(DadosGlobais.resourceBundle.getString("miCadRamoAtividade"),
				"/views/vendas/viewRamosActvClientes.fxml", new RamosActvClientesController(
						Util.getNivelAcessoEntidade(EnumNivelAcesso.RAMO_ATIVIDADE), tabPanePrincipal));

	}

	@FXML
	void actionMiCadRegioesClientes(ActionEvent evt) throws IOException {

		crearTab(DadosGlobais.resourceBundle.getString("miCadRegioesClientes"), "/views/vendas/viewRegiao.fxml",
				new RegiaoController(Util.getNivelAcessoEntidade(EnumNivelAcesso.REGIOES_CLIENTES), tabPanePrincipal));

	}

	@FXML
	void actionMiCadConvenio(ActionEvent evt) throws IOException {

		crearTab(DadosGlobais.resourceBundle.getString("miCadConvenio"), "/views/vendas/viewConvenio.fxml",
				new ConvenioController(Util.getNivelAcessoEntidade(EnumNivelAcesso.CONVENIO), tabPanePrincipal));

	}

	@FXML
	void actionMiCadSegmento(ActionEvent evt) throws IOException {

		crearTab(DadosGlobais.resourceBundle.getString("miCadSegmento"), "/views/compras/viewSegmento.fxml",
				new SegmentoController(Util.getNivelAcessoEntidade(EnumNivelAcesso.SEGMENTOS), tabPanePrincipal));

	}

	@FXML
	void actionMiCadPlanoPagamento(ActionEvent evt) throws IOException {

		crearTab(DadosGlobais.resourceBundle.getString("miCadPlanoPagamento"), "/views/vendas/viewPlanoPagamento.fxml",
				new PlanoPagamentoController(Util.getNivelAcessoEntidade(EnumNivelAcesso.PLANO_DE_PAGAMENTO),
						tabPanePrincipal));

	}

	@FXML
	void actionMiCadMensagem(ActionEvent evt) throws IOException {

		crearTab(DadosGlobais.resourceBundle.getString("miCadMensagem"), "/views/vendas/viewMensagem.fxml",
				new MensagemController(Util.getNivelAcessoEntidade(EnumNivelAcesso.MENSAGEM), tabPanePrincipal));

	}

	// -------------------------- Fim Menu vendas
	// -------------------------------

	// --------------------------- Menu Financiero
	// ----------------------------------
	// --------------------------- Cadastros
	// ------------------------------------

	@FXML
	void actionMiCadSecao(ActionEvent evt) throws IOException {

		crearTab(DadosGlobais.resourceBundle.getString("miCadSecao"), "/views/financeiro/viewSecao.fxml",
				new SecaoController(Util.getNivelAcessoEntidade(EnumNivelAcesso.SECAO), tabPanePrincipal));

	}

	@FXML
	void actionMiCadPortador(ActionEvent evt) throws IOException {

		crearTab(DadosGlobais.resourceBundle.getString("miCadPortador"), "/views/financeiro/viewPortador.fxml",
				new PortadorController(Util.getNivelAcessoEntidade(EnumNivelAcesso.PORTADOR), tabPanePrincipal));

	}

	@FXML
	void actionMiCentroCusto(ActionEvent evt) throws IOException {

		crearTab(DadosGlobais.resourceBundle.getString("miCadCentroCusto"), "/views/financeiro/viewCentroCusto.fxml",
				new CentroCustoController(Util.getNivelAcessoEntidade(EnumNivelAcesso.CENTROS_DE_CUSTO),
						tabPanePrincipal));

	}

	@FXML
	void actionMiPlanoContas(ActionEvent evt) throws IOException {

		crearTab(DadosGlobais.resourceBundle.getString("miCadPlanoContas"), "/views/financeiro/viewPlanoContas.fxml",
				new PlanoContaController(Util.getNivelAcessoEntidade(EnumNivelAcesso.PLANO_DE_CONTAS),
						tabPanePrincipal));

	}

	@FXML
	void actionMiCadCaixaBanco(ActionEvent evt) throws IOException {

		crearTab(DadosGlobais.resourceBundle.getString("miCadCaixaBanco"), "/views/financeiro/viewCaixaBanco.fxml",
				new CaixaBancoController(Util.getNivelAcessoEntidade(EnumNivelAcesso.CAIXA_BANCO), tabPanePrincipal));

	}

	@FXML
	void actionMiCadMoeda(ActionEvent evt) throws IOException {

		crearTab(DadosGlobais.resourceBundle.getString("miCadMoeda"), "/views/financeiro/viewMoeda.fxml",
				new MoedaController(Util.getNivelAcessoEntidade(EnumNivelAcesso.MOEDA), tabPanePrincipal));

	}

	@FXML
	void actionMiOpeContasReceberFin(ActionEvent evt) throws IOException {

		crearTab(DadosGlobais.resourceBundle.getString("miOpeContasReceberFin"),
				"/views/financeiro/viewContasReceber.fxml", new ContasReceberController(
						Util.getNivelAcessoEntidade(EnumNivelAcesso.CONTAS_A_RECEBER), tabPanePrincipal));

	}

	// -------------------------- Fim Menu Financiero
	// -------------------------------

	// --------------------------- Menu Recursos Humanos
	// ----------------------------------
	// --------------------------- Cadastros
	// ------------------------------------

	@FXML
	void actionMiCadFuncionario(ActionEvent evt) throws IOException {

		crearTab(DadosGlobais.resourceBundle.getString("miCadFuncionario"),
				"/views/recursosHumanos/viewFuncionario.fxml",
				new FuncionarioController(Util.getNivelAcessoEntidade(EnumNivelAcesso.FUNCIONARIO), tabPanePrincipal));

	}

	// -------------------------- Fim Menu Recursos Humanos
	// -------------------------------

	@FXML
	/**
	 * Ação do menu item Utilitarios Cadastros Padrao. Exibe janelas com Form
	 * Grupos.
	 */
	void actionMniAjuCadPadrao(ActionEvent event) throws IOException {
		crearTab(miCad0111CadPadrao.getText(), "/views/formCadastroPadrao.fxml", null);
	}

	// --------------Menu Utilitarios---------------------------
	@FXML
	void actionMniManualDefNiv(ActionEvent event) throws IOException {
		boolean existe = false;
		int position = 0;

		for (int i = 0; i < tabPanePrincipal.getTabs().size(); i++) {
			if (miCad0111ManualDefNiv.getText().equals(tabPanePrincipal.getTabs().get(i).getText())) {
				existe = true;
				position = i;
				break;
			}
		}
		if (existe) {
			tabPanePrincipal.getSelectionModel().select(position);
		} else {
			tab = new Tab(miCad0111ManualDefNiv.getText());
			tab.setClosable(true);
			FXMLLoader p = new FXMLLoader(getClass().getResource("/views/configuracoes/NivelAcesso.fxml"));
			p.setController(new NivelesAcessoController(mnBar));
			tab.setContent(p.load());
			tabPanePrincipal.getTabs().add(tab);
			tabPanePrincipal.getSelectionModel().select(tab);

		}

		// crearTab(mniManualDefNiv.getText(), "/views/NivelesAcesso.fxml");
	}

	@FXML
	void actionMiOpeEmpresaConifg(ActionEvent event) throws IOException {

		Stage stg;

		stg = new Stage();

		FXMLLoader fxmlS = new FXMLLoader(getClass().getResource("/views/utils/viewControleSeguranca.fxml"));

		fxmlS.setController(new ControleSegurancaController(tabPanePrincipal, miOpeEmpresaConfig.getText(),
				"/views/configuracoes/viewEmpresa.fxml", new EmpresaController()));

		Scene scenes = new Scene(fxmlS.load(), 470, 280);

		stg.setScene(scenes);

		stg.initStyle(StageStyle.TRANSPARENT);
		stg.initModality(Modality.APPLICATION_MODAL);
		stg.show();

	}

	@FXML
	void actionMniUtilCadUsuarios(ActionEvent event) throws IOException {
		crearTab(miCadUsuarios.getText(), "/views/configuracoes/viewUsuario.fxml",
				new UsuarioController(Util.getNivelAcessoEntidade(EnumNivelAcesso.USUARIOS), mnBar, tabPanePrincipal));

	}

	@FXML
	void actionMniAuditar(ActionEvent event) throws IOException {
		crearTab(DadosGlobais.resourceBundle.getString("miOpeAuditoria"), "/views/configuracoes/viewAuditoria.fxml",
				new AuditoriaController());
	}

	@FXML
	void actionMniCompartihamento(ActionEvent event) throws IOException {
		// crearTab(miCad0111UsuariosUtil.getText(),
		// "/views/configuracoes/viewUsuario.fxml", new
		// UsuarioController(geraPermissaoCad(miCad0111UsuariosUtil.getId()),geraPermissaoCad(miOpe0111Auditoria.getId()),geraPermissaoCad(miCad0111ManualDefNiv.getId()),
		// mnBar, tabPanePrincipal));
		crearTab(miOpeControlCompartilhamento.getText(), "/views/configuracoes/viewCompartilharEmpresas.fxml",
				new CompartilharEmpresasController(mnBar));
	}

	@FXML
	void actionMniOpe01111AjusteSistema(ActionEvent event) throws IOException {

		crearTab(miOpeAjusteSistema.getText(), "/views/configuracoes/viewAjustesSistema.fxml",
				new AjustesSistemaController(tabPanePrincipal));// Was necesary
																// add a
																// parameter
																// because in
																// the
																// 'AjustesSistemaController'
																// class it will
																// be used

	}

	@FXML
	void actionMiOpe01111ManutencaoParam(ActionEvent event) throws IOException {

		crearTab(miOpeManutencaoParam.getText(), "/views/configuracoes/viewParametros.fxml",
				new ParametrosController());
	}

	@FXML
	/**
	 * Ação do menu item Configurações Cadastros Operações de Entrada. Exibe
	 * janelas com Form Grupos.
	 */
	private void actionMiCadOperacaoEntrada(ActionEvent evt) throws IOException {

		crearTab(DadosGlobais.resourceBundle.getString("miCadOperacaoEntrada"),
				"/views/configuracoes/viewOperacaoEntrada.fxml", new OperacaoEntradaController(
						Util.getNivelAcessoEntidade(EnumNivelAcesso.OPERACOES_DE_ENTRADA), tabPanePrincipal));

	}

	@FXML
	/**
	 * Ação do menu item Configurações Cadastros Operações de Saida. Exibe
	 * janelas com Form Grupos.
	 */
	private void actionMiCadOperacaoSaida(ActionEvent evt) throws IOException {

		crearTab(DadosGlobais.resourceBundle.getString("miCadOperacaoSaida"),
				"/views/configuracoes/viewOperacaoSaida.fxml", new OperacaoSaidaController(
						Util.getNivelAcessoEntidade(EnumNivelAcesso.OPERACOES_DE_SAIDA), tabPanePrincipal));

	}

	@FXML
	/**
	 * Ação do menu item Configurações Cadastros Operações Financeiro
	 */
	private void actionMiCadOperacaoFinanceiro(ActionEvent evt) throws IOException {

		crearTab(DadosGlobais.resourceBundle.getString("miCadOperacaoFinanceiro"),
				"/views/configuracoes/viewOperacaoFinanceiro.fxml", new OperacaoFinanceiroController(
						Util.getNivelAcessoEntidade(EnumNivelAcesso.OPERACOES_FINANCEIRO), tabPanePrincipal));

	}

	@FXML
	private void actionMiCadDepositoEstoque(ActionEvent evt) throws IOException {

		crearTab(DadosGlobais.resourceBundle.getString("miCadDepositoEstoque"),
				"/views/configuracoes/viewDepositoEstoque.fxml", new DepositoEstoqueController(
						Util.getNivelAcessoEntidade(EnumNivelAcesso.DEPOSITO_ESTOQUES), tabPanePrincipal));
	}

	/// ----------------------------------------------NEW CADASTRO TABELAS
	/// AUXILIARES----------------------

	@FXML
	/**
	 * Ação do menu item Configurações Cadastros TabelasAuxiliares. Exibe
	 * janelas com Form Grupos.
	 */
	private void actionMiCadCfop(ActionEvent evt) throws IOException 
	{
		crearTab("Cfop", "/views/configuracoes/viewTabelaAuxiliarCfop.fxml",
				new TabelaAuxiliarCfopController(Util.getNivelAcessoEntidade(EnumNivelAcesso.TABELA_AUXILIAR_CFOP),
						tabPanePrincipal));

	}
	
	@FXML
	private void actionMiCadCstCofins(ActionEvent evt) throws IOException 
	{
		crearTab("CstCofins", "/views/configuracoes/viewTabelaAuxiliarCstCofins.fxml",
				new TabelaAuxiliarCstCofinsController(Util.getNivelAcessoEntidade(EnumNivelAcesso.TABELA_AUXILIAR_CST_COFINS),
						tabPanePrincipal));

	}
	
	@FXML
	private void actionMiCadCstIcms(ActionEvent evt) throws IOException
	{
		crearTab("CstIcms", "/views/configuracoes/viewTabelaAuxiliarCstIcms.fxml",
				new TabelaAuxiliarCstIcmsController(Util.getNivelAcessoEntidade(EnumNivelAcesso.TABELA_AUXILIAR_CST_ICM),
						tabPanePrincipal));
	}
	
	@FXML
	private void actionMiCadCstIpi(ActionEvent evt) throws IOException 
	{
		crearTab("CstIpi", "/views/configuracoes/viewTabelaAuxiliarCstIpi.fxml",
				new TabelaAuxiliarCstIpiController(Util.getNivelAcessoEntidade(EnumNivelAcesso.TABELA_AUXILIAR_CST_IPI),
						tabPanePrincipal));
	}
	
	@FXML
	private void actionMiCadCstPis(ActionEvent evt) throws IOException 
	{
		crearTab("CstPis", "/views/configuracoes/viewTabelaAuxiliarCstPis.fxml",
				new TabelaAuxiliarCstPiController(Util.getNivelAcessoEntidade(EnumNivelAcesso.TABELA_AUXILIAR_CST_PIS),
						tabPanePrincipal));
	}	



	@FXML
	/**
	 * Ação do menu item Configurações Cadastros DLayaoutsArquivo. Exibe janelas
	 * com Form Grupos.
	 */

	private void actionMiCadLayoutsArquivo(ActionEvent evt) throws IOException 
	{		
		//util.showDLayoutsArquivo();
		
		 crearTab( "LayoutArquivo", "/views/configuracoes/viewLayoutsArquivo.fxml",
				  new LayoutsArquivoController( Util.getNivelAcessoEntidade(EnumNivelAcesso.LAYOUT_ARQUIVO), tabPanePrincipal));		
			
		//util.showLayoutArquivo(tabPanePrincipal);
		
		//crearTab(DadosGlobais.resourceBundle.getString("miCadDepositoEstoque"), "/views/configuracoes/viewDLayaoutsArquivo.fxml",
				//new DLayaoutsArquivoController(Util.getNivelAcessoEntidade(EnumNivelAcesso.DEPOSITO_ESTOQUES), tabPanePrincipal));
	}	
	
	
	

	@FXML
	/**
	 * Ação do menu item Configurações Cadastros TabelasAuxiliares. Exibe
	 * janelas com Form Grupos.
	 */
	private void actionImportacaoDados(ActionEvent evt) throws IOException {
		/*
		 * crearTab( "Tabelas Auxiliares",
		 * "/views/configuracoes/viewTabelasAuxiliares.fxml", new
		 * TabelaAuxiliarCstIcmController(
		 * Util.getNivelAcessoEntidade(EnumNivelAcesso.TABELA_AUXILIAR_CST_ICM),
		 * tabPanePrincipal));
		 */
		
		 crearTab( "Importacao Dados", "/views/configuracoes/viewImportacaoDados.fxml",
				  new ImportacaoDadosController( Util.getNivelAcessoEntidade(EnumNivelAcesso.LAYOUT_ARQUIVO), tabPanePrincipal));		
					
		//util.showImportacaoDados(tabPanePrincipal);
	}

	/// ----------------------------------------------END CADASTRO TABELAS
	/// AUXILIARES----------------------

	// --------------------------------------//
	@FXML
	void actionCkmniEntTemAju(ActionEvent event) {
		if (ckmniEntTemAju2.isSelected()) {

			ckmniProfTemAju1.setSelected(false);
			ckmniUltTemAju3.setSelected(false);
			ckmniEntTemAju2.setSelected(true);//
			panePrincipal.getScene().getStylesheets().clear();
			panePrincipal.getScene().getStylesheets()
					.add(getClass().getResource("/styles/css/themes Enterprise/style_Enterprise.css").toExternalForm());

		}
	}

	@FXML
	void actionCkmniProfTemAju(ActionEvent event) {

		if (ckmniProfTemAju1.isSelected()) {
			ckmniEntTemAju2.setSelected(false);
			ckmniUltTemAju3.setSelected(false);
			ckmniProfTemAju1.setSelected(true);
			panePrincipal.getScene().getStylesheets().clear();
			panePrincipal.getScene().getStylesheets().add(
					getClass().getResource("/styles/css/themes Professional/style_Professional.css").toExternalForm());

		}
	}

	@FXML
	void actionCkmniUltTemAju(ActionEvent event) {

		if (ckmniUltTemAju3.isSelected()) {

			ckmniEntTemAju2.setSelected(false);
			ckmniProfTemAju1.setSelected(false);
			ckmniUltTemAju3.setSelected(true);
			panePrincipal.getScene().getStylesheets().clear();
			panePrincipal.getScene().getStylesheets()
					.add(getClass().getResource("/styles/css/themes Ultimate/style_Ultimate.css").toExternalForm());

		}
	}

	@FXML
	void actionMiGerarMenu(ActionEvent event) {

		// NivelesAcessoController n = new NivelesAcessoController(mnBar);
		// n.masMenu();

	}

	// ----------------------Btn Toolbar-----------------------------------//
	@FXML
	void actionBtnBarIconOut(ActionEvent event) {
		System.exit(0);
	}

	@FXML
	private void actionBtnCalc(ActionEvent evt) {

		try {
			// Runtime.getRuntime().exec("calc");
			Runtime.getRuntime().getClass();
			Runtime.getRuntime().exec("C:\\Windows\\System32\\calc.exe");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	// ----------------------Final Btn
	// Toolbar-----------------------------------//

	/**
	 * Método para evitar a repetição de aba.
	 * 
	 * @param nomeObjeto
	 *            nome aba
	 * @throws IOException
	 */
	public void crearTab(String nomeObjeto, String dir, Object arg) throws IOException {

		boolean existe = false;
		int position = 0;

		for (int i = 0; i < tabPanePrincipal.getTabs().size(); i++) {
			if (nomeObjeto.equals(tabPanePrincipal.getTabs().get(i).getText())) {
				existe = true;
				position = i;
				break;
			}
		}
		if (existe) {
			tabPanePrincipal.getSelectionModel().select(position);
		} else {
			tab = new Tab(nomeObjeto);
			tab.setClosable(true);
			FXMLLoader vista = new FXMLLoader(getClass().getResource(dir));
			vista.setController(arg);
			tab.setContent(vista.load());
			tabPanePrincipal.getTabs().add(tab);
			tabPanePrincipal.getSelectionModel().select(tab);
		}
	}

	@FXML
	private void actionCkmniES(ActionEvent evt) {
		ckmniEnglish.setSelected(false);
		ckmniPortugues.setSelected(false);
		ckmniEspanhol.setSelected(true);
		Locale l = new Locale("es");
		ResourceBundle bundler = ResourceBundle.getBundle("translate/eptus", l);

		for (int i = 0; i < mnBar.getMenus().size(); i++) {
			mnBar.getMenus().get(i).setText(bundler.getString(mnBar.getMenus().get(i).getId()));
			recorrerMenu(mnBar.getMenus().get(i).getItems(), l);

		}

		// MODIFICAR FILE EPTUS.CONF
		Util ut = new Util();
		ut.writeFileConf("idioma=" + "es");
	}

	@FXML
	private void actionCkmniPT(ActionEvent evt) {
		ckmniEspanhol.setSelected(false);
		ckmniPortugues.setSelected(true);
		ckmniEnglish.setSelected(false);

		Locale l = new Locale("pt");
		ResourceBundle bundler = ResourceBundle.getBundle("translate/eptus", l);

		for (int i = 0; i < mnBar.getMenus().size(); i++) {
			// System.out.println(mnBar.getMenus().get(i).getText());
			mnBar.getMenus().get(i).setText(bundler.getString(mnBar.getMenus().get(i).getId()));
			recorrerMenu(mnBar.getMenus().get(i).getItems(), l);

		}

		// MODIFICAR FILE EPTUS.CONF
		Util ut = new Util();
		ut.writeFileConf("idioma=" + "pt");
	}

	@FXML
	private void actionCkmniEN(ActionEvent evt) {
		ckmniEspanhol.setSelected(false);
		ckmniPortugues.setSelected(false);
		ckmniEnglish.setSelected(true);
		Locale l = new Locale("en");
		ResourceBundle bundler = ResourceBundle.getBundle("translate/eptus", l);
		for (int i = 0; i < mnBar.getMenus().size(); i++) {
			// System.out.println(mnBar.getMenus().get(i).getText());
			mnBar.getMenus().get(i).setText(bundler.getString(mnBar.getMenus().get(i).getId()));
			recorrerMenu(mnBar.getMenus().get(i).getItems(), l);

		}

		// MODIFICAR FILE EPTUS.CONF
		Util ut = new Util();
		ut.writeFileConf("idioma=" + "es");
	}

	/**
	 * Método para percorrer os menu principais
	 * 
	 * @param observableList
	 *            lista dos menues
	 * @param idioma
	 *            idioma
	 */
	public void recorrerMenu(ObservableList<MenuItem> observableList, Locale idioma) {

		ResourceBundle bundler = ResourceBundle.getBundle("translate/eptus", idioma);

		for (int i = 0; i < observableList.size(); i++) {

			observableList.get(i).setText(bundler.getString(observableList.get(i).getId()));

			System.out.println(observableList.get(i).getId() + observableList.get(i).toString());

			if (observableList.get(i) instanceof Menu) {
				recorrerMenu(((Menu) observableList.get(i)).getItems(), idioma);
			}

		}

	}

	@FXML
	public void actionFecharEscondida(ActionEvent evt) {
		cerrar.setToX(-(panelEscondido.getWidth()));
		cerrar.play();
	}

	public NivelAcesso geraPermissaoCad(String idMenuItem) {
		NivelAcesso result = null;
		// for (NivelAcesso niv : DadosGlobais.listNivelAcesso) {
		// if (niv.getMenu().equals(idMenuItem)) {
		// result = niv;
		// break;
		// }
		// }
		return result;

	}

	public Compartilhamento geraCompartilhamentoCad(String idMenuItem) {
		Compartilhamento result = null;
		for (Compartilhamento comp : DadosGlobais.listCodempCompartilha) {
			if (comp.getId().getModFuncao().equals(idMenuItem)) {
				result = comp;
				break;
			}
		}
		return result;
	}

	/**
	 * Create folder System.Grid, System.anx, System.tmp.empX if not exist
	 */
	public void createFolderSystem() {

		File fGrid = new File(DadosGlobais.folderGridLog);
		if (!fGrid.exists())
			fGrid.mkdir();

		File fAnx = new File(DadosGlobais.folderAnexoEmail);
		if (!fAnx.exists())
			fAnx.mkdir();

		File fTmp = new File(DadosGlobais.folderTemp + "emp" + DadosGlobais.empresaLogada.getCodemp());
		if (!fTmp.exists())
			fTmp.mkdir();

		File fImg = new File(DadosGlobais.folderImagens);
		if (!fImg.exists())
			fImg.mkdir();

	}

	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub

		// System.out.println(arg0.getSource().toString());
		// if(arg0.getSource()==btnTolBarIconCand){
		// synchronized (OBSERVABLE) {
		// OBSERVABLE.setChanged();
		// OBSERVABLE.notifyObservers(arg0);
		// }
		// }
	}

	/**
	 * metodo para buscar menu cuando KEY_PRESSED = ENTER
	 * 
	 * @param observableList
	 * @param text
	 */
	public void buscarMenu(ObservableList<MenuItem> observableList, String text) {

		for (MenuItem mni : observableList) {
			if (mni instanceof Menu) {
				buscarMenu(((Menu) mni).getItems(), text);
			} else {
				if (mni.getGraphic() instanceof Label) {
					if (((Label) mni.getGraphic()).getText().toLowerCase().equals(text.toLowerCase())) {
						mni.getOnAction().handle(null);
						break;
					}

				}
				if (mni.getText().toLowerCase().equals(text.toLowerCase())) {
					mni.getOnAction().handle(null);
					break;
				}
			}
		}
	}

	public void buscarMenuContextMenu(ObservableList<?> observableList, String text) {
		for (int i = 0; i < observableList.size(); i++) {
			if (observableList.get(i) instanceof Menu) {
				buscarMenuContextMenu(((Menu) observableList.get(i)).getItems(), text);
			} else {
				if (Util.removeSpecialCharacters(((MenuItem) observableList.get(i)).getText().toLowerCase())
						.contains(text.toLowerCase()))
					// if (((MenuItem)
					// observableList.get(i)).getText().toLowerCase().contains(text.toLowerCase()))
					mList.add(((MenuItem) observableList.get(i)).getText());
			}
		}

	}

	public void showPopOverSearch() {

		txtBuscarMenu.clear();
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(10));
		vbox.setSpacing(8);
		vbox.setAlignment(Pos.CENTER);

		// Button btnSearch = new Button();
		// btnSearch.setStyle("-fx-background-color : transparent; -fx-padding :
		// 0;");
		// FontAwesomeIconView icon = new
		// FontAwesomeIconView(FontAwesomeIcon.SEARCH);
		// icon.setFill(Color.web("#757575"));
		// icon.setSize("16px");
		// btnSearch.setGraphic(icon);
		// txtBuscarMenu.setLeft(btnSearch);
		txtBuscarMenu.setOnKeyPressed(key -> {
			if (key.getCode().equals(KeyCode.ESCAPE))
				popOver.hide();

		});

		Util.customSearchTextField("left", null, txtBuscarMenu);
		vbox.getChildren().add(txtBuscarMenu);

		popOver.setContentNode(vbox);
		popOver.setArrowLocation(null);
		popOver.setOpacity(0.9);
		popOver.show(panePrincipal.getScene().getWindow());
	}

	public TelaPrincipalEptusController() {

	}

	@FXML
	void onClickLbUsuario(MouseEvent event) {

		if (event.getClickCount() == 2) {

			boolean flagAbasAberta = util.verificaTabsAbertas(tabPanePrincipal);
			boolean flagFechaAba = false;

			if (!flagAbasAberta) {
				// flagAbasAberta = util.alertWarningConfirma(
				// "Existem Janelas Abertas \nDados que não foram salvos serão
				// perdidos! \nDeseja Prosseguir com a Troca de Usuário?");
				flagAbasAberta = util.showAlert(
						"Existem Janelas Abertas. Dados que não foram salvos serão perdidos!. Deseja Prosseguir com a Troca de Usuário?",
						"warningConfirm");
				if (flagAbasAberta)
					util.fecharTabsAbertas(tabPanePrincipal);
			}

			if (flagAbasAberta) {

				Stage stg;

				stg = new Stage();

				FXMLLoader fxmlS = new FXMLLoader(getClass().getResource("/views/utils/viewTrocaUsuario.fxml"));

				fxmlS.setController(new TrocaUsuarioController(mnBar, tabPanePrincipal, usuarioText, idiomaImg));

				Scene scenes;
				try {
					scenes = new Scene(fxmlS.load(), 470, 280);
					stg.setScene(scenes);
					stg.initStyle(StageStyle.TRANSPARENT);
					stg.initModality(Modality.APPLICATION_MODAL);
					stg.show();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}

	@FXML
	void onClickLbEmpresa(MouseEvent event) {

		if (event.getClickCount() == 2) {

			boolean flagAbasAberta = util.verificaTabsAbertas(tabPanePrincipal);

			if (!flagAbasAberta) {
				// flagAbasAberta = util.alertWarningConfirma(
				// "Existem Janelas Abertas \nDados que não foram salvos serão
				// perdidos! \nDeseja Prosseguir com a Troca da Empresa?");
				flagAbasAberta = util.showAlert(
						"Existem Janelas Abertas. Dados que não foram salvos serão perdidos!. Deseja Prosseguir com a Troca de Usuário?",
						"warningConfirm");
				if (flagAbasAberta)
					util.fecharTabsAbertas(tabPanePrincipal);
			}

			if (flagAbasAberta) {

				Stage stg;

				stg = new Stage();

				FXMLLoader fxmlS = new FXMLLoader(getClass().getResource("/views/utils/viewTrocaEmpresa.fxml"));

				fxmlS.setController(new TrocaEmpresaController(empresaText));

				Scene scenes;
				try {
					scenes = new Scene(fxmlS.load(), 470, 280);
					stg.setScene(scenes);
					stg.initStyle(StageStyle.TRANSPARENT);
					stg.initModality(Modality.APPLICATION_MODAL);
					stg.show();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}

	@FXML
	void onClickLbDataSistema(MouseEvent event) {

		if (event.getClickCount() == 2) {

			if (DadosGlobais.usuarioLogado.getConfFlagAltDtMovto() == 1) {

				boolean flagAbasAberta = util.verificaTabsAbertas(tabPanePrincipal);

				if (!flagAbasAberta) {
					// flagAbasAberta = util.alertWarningConfirma(
					// "Existem Janelas Abertas \nDados que não foram salvos
					// serão perdidos! \nDeseja Prosseguir com a Troca de
					// Usuário?");
					flagAbasAberta = util.showAlert(
							"Existem Janelas Abertas. Dados que não foram salvos serão perdidos!. Deseja Prosseguir com a Troca de Usuário?",
							"warningConfirm");
					if (flagAbasAberta)
						util.fecharTabsAbertas(tabPanePrincipal);
				}

				if (flagAbasAberta) {

					Stage stg;

					stg = new Stage();

					FXMLLoader fxmlS = new FXMLLoader(getClass().getResource("/views/utils/viewTrocaDataSistema.fxml"));

					fxmlS.setController(new TrocaDataSistemaController(dataLocalText));

					Scene scenes;
					try {
						scenes = new Scene(fxmlS.load(), 470, 280);
						stg.setScene(scenes);
						stg.initStyle(StageStyle.TRANSPARENT);
						stg.initModality(Modality.APPLICATION_MODAL);
						stg.show();

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			} else {
				// util.alertWarn("Usuário não possui permissão para alterar
				// data de movimento!");
				util.showAlert("Usuário não possui permissão para alterar data de movimento!", "warning");
			}
		}
	}

	ObservableList<String> l = FXCollections.observableArrayList();

	public void prepararAnima() {
		TranslateTransition abrir = new TranslateTransition(new Duration(150), panelEscondido);
		cerrar = new TranslateTransition(new Duration(200), panelEscondido);

		listViewMenu.setOnMouseClicked(click -> {
			if (click.getButton().equals(MouseButton.PRIMARY)) {
				for (int i = 0; i < mnBar.getMenus().size(); i++) {
					buscarMenu(mnBar.getMenus().get(i).getItems(), listViewMenu.getSelectionModel().getSelectedItem());
					listViewMenu.getItems().clear();
					txtBuscarMenu.clear();
					cerrar.setToX(-(panelEscondido.getWidth()));
					cerrar.play();

				}
			}

		});

		txtBuscarMenu.addEventFilter(KeyEvent.KEY_RELEASED, keypress -> {

			listViewMenu.getItems().clear();

			if (!keypress.getCode().equals(KeyCode.ENTER) && !txtBuscarMenu.getText().isEmpty()) {

				// if (panelEscondido.getTranslateX() <= 0) {
				// abrir.setToX(205);
				// abrir.play();
				// }

				if (!mList.isEmpty()) {
					mList.clear();
				}

				buscarMenuContextMenu(mnBar.getMenus(), txtBuscarMenu.getText());

			} else if (keypress.getCode().equals(KeyCode.ENTER)) {
				for (int i = 0; i < mnBar.getMenus().size(); i++) {
					if (!txtBuscarMenu.getText().isEmpty()) {
						buscarMenu(mnBar.getMenus().get(i).getItems(), txtBuscarMenu.getText());
					}

				}
				// listViewMenu.getItems().clear();
				// txtBuscarMenu.clear();
				// cerrar.setToX(-(panelEscondido.getWidth()));
				// cerrar.play();

				popOver.hide();

			}
		});
	}

	// boolean existe = false;
	//
	// public void permisoVisibleMenu(ObservableList<?> observableList)
	// throws InstantiationException, IllegalAccessException {
	//
	// // System.out.println(observableList.size());
	// for (int i = 0; i < observableList.size(); i++) {
	// if (observableList.get(i) instanceof Menu) {
	//
	// // ULTIMATE
	// if (DadosGlobais.sistema.equals("Ultimate") && ((Menu)
	// observableList.get(i)).getId().charAt(7) == '1') {
	// lblTitle.setText("Eptus@M - Ultimate");
	// for (NivelAcesso nivAc : permissao) {//
	// if (((Menu) observableList.get(i)).getId().equals(nivAc.getMenu())) {
	// if (((Menu) observableList.get(i)).getId().subSequence(2,
	// 5).equals("Hea")) {
	// ((Menu) observableList.get(i)).setDisable((nivAc.getFlagView() == 1) ?
	// false : true);
	// } else {
	// ((Menu) observableList.get(i)).setVisible((nivAc.getFlagView() == 1) ?
	// true : false);
	// }
	// existe = true;
	// break;
	// } else
	// existe = false;
	// }
	//
	// // No caso do menu seja novo e nao fique no banco de dados
	// // vai ficar desativado
	// if (!existe) {
	// ((Menu) observableList.get(i)).setDisable(true);
	// existe = false;
	//
	// }
	// }
	//
	// // PORFESSIONAL
	// else if (DadosGlobais.sistema.equals("Professional")
	// && ((Menu) observableList.get(i)).getId().charAt(6) == '1') {
	// lblTitle.setText("Eptus@M - Professional");
	// for (NivelAcesso nivAc : permissao) {
	// if (((Menu) observableList.get(i)).getId().equals(nivAc.getMenu())) {
	// if (((Menu) observableList.get(i)).getId().subSequence(2,
	// 5).equals("Hea")) {
	//
	// ((Menu) observableList.get(i)).setDisable((nivAc.getFlagView() == 1) ?
	// false : true);
	// } else {
	// ((Menu) observableList.get(i)).setVisible((nivAc.getFlagView() == 1) ?
	// true : false);
	// }
	// existe = true;
	// break;
	// } else
	// existe = false;
	// }
	// if (!existe) {
	// ((Menu) observableList.get(i)).setDisable(true);
	// existe = false;
	// }
	// }
	//
	// // ENTERPRISE
	// else if (DadosGlobais.sistema.equals("Enterprise")
	// && ((Menu) observableList.get(i)).getId().charAt(8) == '1') {
	// lblTitle.setText("Eptus@M - Enterprise");
	// for (NivelAcesso nivAc : permissao) {
	// if (((Menu) observableList.get(i)).getId().equals(nivAc.getMenu())) {
	// if (((Menu) observableList.get(i)).getId().subSequence(2,
	// 5).equals("Hea")) {
	//
	// ((Menu) observableList.get(i)).setDisable((nivAc.getFlagView() == 1) ?
	// false : true);
	// } else {
	// ((Menu) observableList.get(i)).setVisible((nivAc.getFlagView() == 1) ?
	// true : false);
	// }
	// existe = true;
	// break;
	// } else
	// existe = false;
	// }
	// if (!existe) {
	// ((Menu) observableList.get(i)).setDisable(true);
	// existe = false;
	// }
	// } else
	// ((Menu) observableList.get(i)).setVisible(false);
	//
	// permisoVisibleMenu(((Menu) observableList.get(i)).getItems());
	//
	// } else {
	//
	// for (NivelAcesso nivAc : permissao) {
	// if (((MenuItem) observableList.get(i)).getId().equals(nivAc.getMenu())) {
	// ((MenuItem) observableList.get(i)).setVisible((nivAc.getFlagAtivo() == 1)
	// ? true : false);
	// break;
	// }
	//
	// }
	//
	// if (DadosGlobais.sistema.equals("Professional")
	// && ((MenuItem) observableList.get(i)).getId().charAt(6) == '1') {
	// for (NivelAcesso nivAc : permissao) {
	// if (((MenuItem) observableList.get(i)).getId().equals(nivAc.getMenu())) {
	// ((MenuItem) observableList.get(i)).setVisible((nivAc.getFlagView() == 1)
	// ? true : false);
	// existe = true;
	// break;
	// } else
	// existe = false;
	// }
	//
	// if (!existe) {
	// ((MenuItem) observableList.get(i)).setDisable(true);
	// existe = false;
	// }
	// } else if (DadosGlobais.sistema.equals("Ultimate")
	// && ((MenuItem) observableList.get(i)).getId().charAt(7) == '1') {
	// for (NivelAcesso nivAc : permissao) {
	// if (((MenuItem) observableList.get(i)).getId().equals(nivAc.getMenu())) {
	// ((MenuItem) observableList.get(i)).setVisible((nivAc.getFlagView() == 1)
	// ? true : false);
	// existe = true;
	// break;
	// } else
	// existe = false;
	// }
	//
	// if (!existe) {
	// ((MenuItem) observableList.get(i)).setDisable(true);
	// existe = false;
	//
	// }
	// } else if (DadosGlobais.sistema.equals("Enterprise")
	// && ((MenuItem) observableList.get(i)).getId().charAt(8) == '1') {
	// for (NivelAcesso nivAc : permissao) {
	// if (((MenuItem) observableList.get(i)).getId().equals(nivAc.getMenu())) {
	// ((MenuItem) observableList.get(i)).setVisible((nivAc.getFlagView() == 1)
	// ? true : false);
	// existe = true;
	// break;
	// } else
	// existe = false;
	// }
	//
	// if (!existe) {
	// ((MenuItem) observableList.get(i)).setDisable(true);
	// existe = false;
	// }
	// } else
	// ((MenuItem) observableList.get(i)).setVisible(false);
	//
	// }
	// }
	// }

	/**
	 * <p>
	 * Método Initializable da Classe. Ler configurações de idioma no arquivo
	 * conf.
	 * </p>
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		txtBuscarMenu = new CustomTextField();
		txtBuscarMenu.setVisible(true);
		txtBuscarMenu.setPrefWidth(400);
		txtBuscarMenu.setPrefHeight(30);

		// AUTOCOMPLETAMIENTO DO TEXFIELD DE PESQUISA
		new AutoCompletionTextFieldBinding(txtBuscarMenu,
				new Callback<AutoCompletionBinding.ISuggestionRequest, Collection>() {
					@Override
					public Collection call(AutoCompletionBinding.ISuggestionRequest param) {
						return mList;
					}
				});

		// SETA O FOCO INICIAL NO TEXTFIELD DE BUSCA
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				// txtBuscarMenu.requestFocus();
			}
		});

		ckmniEntTemAju2.setSelected(false);
		ckmniProfTemAju1.setSelected(false);
		ckmniUltTemAju3.setSelected(true);
		// System.out.println(TelaPrincipalEptusController.class.getProtectionDomain().getCodeSource().getLocation());
		// Conexion.getConnection();

		serverText.setText(String.valueOf(ConnectionHib.SERVER));
		usuarioText.setText(DadosGlobais.usuario);
		empresaText.setText(DadosGlobais.empresa);

		prepararAnima();

		// Create folder of System
		createFolderSystem();

		Image img = new Image("/styles/img/" + DadosGlobais.idioma + ".png");

		idiomaImg.setImage(img);

		dataLocalText.setText("Manaus, " + util.formataDataString(DadosGlobais.dataMovtoSistema));
		// txtBuscarMenu.setVisible(false);

		try {
			//
			// Cargar permissao visible do menu do usuario
			DadosGlobais.traducao();
			util.permissoesMenuPrincipal(mnBar.getMenus(), DadosGlobais.usuarioLogado.getNiveisAcesso());

			// if(permissao.size() == qtdMenus){
			// System.out.println("Menu OK");
			// }
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// try {
		// permisoVisibleMenu(mnBar.getMenus());
		// } catch (InstantiationException e) {
		// // TODO Auto-generated catch block

		// e.printStackTrace();
		// } catch (IllegalAccessException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// System.out.println(Dados.codigoEmpresa+"-sdsadasd");

		// Util ut = new Util();
		// String idioma = ut.readFileConf();
		// System.out.println(idioma);
		// Locale l = new Locale(idioma);
		// if (idioma.equals("en")) {
		// ckmniEN.setSelected(true);
		// } else if (idioma.equals("es")) {
		// ckmniES.setSelected(true);
		// } else {
		// if (idioma.equals("pt")) {
		// ckmniPT.setSelected(true);
		// }
		//
		// }
		//
		// ResourceBundle bundler = ResourceBundle.getBundle("translate/eptus",
		// l);
		// for (int i = 0; i < mnBar.getMenus().size(); i++) {
		//
		// System.out.println(mnBar.getMenus().get(i).getText() + " Txt");
		// System.out.println(mnBar.getMenus().get(i).getId() + " id");
		// System.out.println(mnBar.getMenus().get(i).toString() + " str");
		// System.out.println(mnBar.getMenus().get(i).getItems().get(0));
		// //
		// mnBar.getMenus().get(i).setText(bundler.getString(mnBar.getMenus().get(i).getId()));
		// // recorrerMenu(mnBar.getMenus().get(i).getItems(), l);
		//
		// }
		//
		// btnTolBarIconCand.setOnAction(this);
		// btnTolBarIconDatBase.setOnAction(this);
		// mniDepCadComp.setOnAction(this);

		// Idioma
		// Locale locale = new Locale(DadosGlobais.idioma.toLowerCase());
		// DadosGlobais.resourceBundle =
		// ResourceBundle.getBundle("translate/eptus", locale);

		// EVENTO ON FOCUS DO BOTAO DE AJUDA
		// popOver.focusedProperty().addListener(new ChangeListener<Boolean>() {
		// @Override
		// public void changed(ObservableValue<? extends Boolean> observable,
		// Boolean oldValue, Boolean newValue) {
		//// if (!newValue) {
		//// popOver.hide();
		//// }
		//
		// System.out.println(oldValue + " - " +newValue);
		// }
		// });

		panePrincipal.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			switch (event.getCode()) {
			case F1:
				if (!popOver.isShowing())
					showPopOverSearch();
				break;

			case ESCAPE:
				popOver.hide();
				break;

			default:
				break;
			}
		});
		// if (event.getCode().equals(KeyCode.ESCAPE))
		// Main.stage.close();

		Main.stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				// TODO Auto-generated method stub
				Platform.exit();
				System.exit(0);
			}
		});

	}

}
