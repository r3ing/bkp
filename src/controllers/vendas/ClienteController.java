package controllers.vendas;

//import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import static java.nio.file.StandardCopyOption.*;
//import java.io.StringReader;
//import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.nio.charset.CodingErrorAction;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
//import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
//import java.util.Scanner;

import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXToggleButton;
import com.sun.javafx.scene.control.skin.TableHeaderRow;
import application.DadosGlobais;
import controllers.compras.FabricanteController;
import controllers.compras.ItemController;
import controllers.compras.SegmentoController;
import controllers.configuracoes.OperacaoSaidaController;
import controllers.configuracoes.UsuarioController;
import controllers.financeiro.PortadorController;
import controllers.financeiro.SecaoController;
import controllers.recursosHumanos.FuncionarioController;
import controllers.utils.ComboBoxIndicadorIE;
import controllers.utils.ConfigColumnController;
import controllers.utils.PrintExportController;
import controllers.utils.ProgressBarForm;
import controllers.utils.SearchController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import org.apache.axis2.databinding.types.soapencoding.Decimal;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.PopOver.ArrowLocation;
import org.controlsfx.control.textfield.CustomTextField;
import org.hibernate.sql.Update;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.PrettyXmlSerializer;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.html.HTMLDocument;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.Worker.State;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import models.compras.FabricanteDAO;
import models.compras.Fornecedor;
import models.compras.FornecedorDAO;
import models.compras.ItemCodBar;
import models.compras.Segmento;
import models.compras.SegmentoDAO;
import models.configuracoes.NivelAcesso;
import models.configuracoes.OperacaoSaida;
import models.configuracoes.OperacaoSaidaDAO;
import models.financeiro.Portador;
import models.financeiro.PortadorDAO;
import models.financeiro.Secao;
import models.financeiro.SecaoDAO;
import models.recursosHumanos.Funcionario;
import models.recursosHumanos.FuncionarioDAO;
import models.vendas.Cidade;
import models.vendas.CidadeDAO;
import models.vendas.Cliente;
import models.vendas.ClienteDAO;
import models.vendas.ClienteEndEntrega;
import models.vendas.ClienteEndEntregaDAO;
import models.vendas.Convenio;
import models.vendas.ConvenioDAO;
import models.vendas.Pais;
import models.vendas.PaisDAO;
import models.vendas.PlanoPagamento;
import models.vendas.PlanoPagamentoDAO;
import models.vendas.RamoAtividade;
import models.vendas.RamoAtividadeDAO;
import models.vendas.Regiao;
import models.vendas.RegiaoDAO;
import models.vendas.Rota;
import models.vendas.RotaDAO;
import tools.controls.ComboBoxFilter;
import tools.enums.EnumConsumidorFinal;
import tools.enums.EnumLogRetornoStatus;
import tools.enums.EnumNivelAcesso;
import tools.enums.EnumTipoFaturamento;
import tools.enums.EnumTipoPrazo;
import tools.reports.TableConfPrint;
import tools.utils.LogRetorno;
import tools.utils.Util;
import tools.utils.Validacoes;
import tools.xml.UserDefinedColumn;
import tools.xml.XMLTableColumns;

/**
 * CLASSE CONTROLADORA DO FORMULARIO cliente.fxml
 * 
 * @author Eptus da Amazônia
 */
@SuppressWarnings("restriction")
public class ClienteController implements Initializable {

	@FXML
	private AnchorPane anchorPanePrincipal, anchorPaneListagem, anchorPaneDetalhes, anchorPaneFilter, anpWeb;

	@FXML
	private SplitPane splitPaneFilter;

	@FXML
	private ToolBar toolBarListagem, toolBarDetalhes;

	@FXML
	private Label lblCodigo, lblCboxFilterColumn, lblcboxFlagAtivo, lblDescriao, lblDetCodigo, lblDetDesc, lblNumLinhas,
			lblTotalLinhas, lbTitleFormCad, lblErrorMessage;

	@FXML
	private Button btnInsertGrid, btnInsert, btnDelete, btnRefresh, btnFilter, btnPrint, btnExport, btnConfig, btnSave,
			btnCancel, btnClose;

	@FXML
	public TableView<Cliente> tbView;

	@FXML
	private TableColumn<Cliente, Integer> tbColCodigo;

	@FXML
	private TableColumn<Cliente, String> tbColRazao;

	@FXML
	private TableColumn<Cliente, String> tbColFantasia;

	@FXML
	private TableColumn<Cliente, String> tbColCpfCnpj;

	@FXML
	private TableColumn<Cliente, String> tbColIeRg;

	@FXML
	private TableColumn<Cliente, Integer> tbColIndicadorInscEst;

	@FXML
	private TableColumn<Cliente, Integer> tbColCodeEmp;

	@FXML
	private TableColumn<Cliente, Integer> tbColAtivoInat;

	@FXML
	private TableColumn<Cliente, Integer> tbColCodCidade;

	@FXML
	private TableColumn<Cliente, String> tbColCidade;

	@FXML
	private TableColumn<Cliente, String> tbColUf;

	@FXML
	private TableColumn<Cliente, String> tbColEndereco;

	@FXML
	private TableColumn<Cliente, String> tbColEndNumero;

	@FXML
	private TableColumn<Cliente, String> tbColComplemento;

	@FXML
	private TableColumn<Cliente, String> tbColBairro;

	@FXML
	private TableColumn<Cliente, String> tbColCep;

	@FXML
	private TableColumn<Cliente, String> tbColFone;

	@FXML
	private TableColumn<Cliente, String> tbColCelular;

	@FXML
	private TableColumn<Cliente, String> tbColFone2;

	@FXML
	private TableColumn<Cliente, String> tbColEmail;

	@FXML
	private TableColumn<Cliente, String> tbColInternet;

	@FXML
	private TableColumn<Cliente, String> tbColInscProd;

	@FXML
	private TableColumn<Cliente, BigDecimal> tbColLimiteCredito;

	@FXML
	private TableColumn<Cliente, Integer> tbColLocalLimiteCredito;

	@FXML
	private TableColumn<Cliente, String> tbColNoSuframa;

	@FXML
	private TableColumn<Cliente, Integer> tbColConsumidorFinal;

	@FXML
	private TableColumn<Cliente, String> tbColIdentEstrangeiro;

	@FXML
	private TableColumn<Cliente, String> tbColContatoPosVenda;

	@FXML
	private ComboBox<ComboBoxFilter> cboxTipoPessoa, cboxIndIncEstad, cboxFilterColumn, cboxFlagAtivo;

	// @FXML
	// private CustomTextField txtCodigos;

	@FXML
	private CustomTextField txtFilterColumn;

	@FXML
	private ProgressBar pBar;

	@FXML
	private ContextMenu contextMenu = null;

	@FXML
	private ToggleButton toggleHelp;

	@FXML
	private ToolBar toolBar;

	@FXML
	private TabPane tabPanePrincipal;

	// Tab Informações Gerais
	@FXML
	private Tab tabInfGerais;

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
	private TextField txtUF;

	@FXML
	private Label lblEndereco;

	@FXML
	private TextField txtEndereco;

	@FXML
	private Label lblNumero;

	@FXML
	private TextField txtEndNumero;

	@FXML
	private Label lblComplemento;

	@FXML
	private TextField txtComplemento;

	@FXML
	private Label lblBairro;

	@FXML
	private TextField txtBairro;

	@FXML
	private Label lblCep;

	@FXML
	private TextField txtCep;

	@FXML
	private Label lblFone;

	@FXML
	private TextField txtFone;

	@FXML
	private Label lblCelular;

	@FXML
	private TextField txtCelular;

	@FXML
	private Label lblFone2;

	@FXML
	private TextField txtFone2;

	@FXML
	private Label lblEmail;

	@FXML
	private TextField txtEmail;

	@FXML
	private Label lblInternet;

	@FXML
	private TextField txtInternetSite;

	@FXML
	private Label lblInscProd;

	@FXML
	private TextField txtInscProdRural;

	@FXML
	private TextField txtCodigo;

	@FXML
	private Label lblTipoPessoa;

	@FXML
	private Label lblRazao;

	@FXML
	private TextField txtRazao;

	@FXML
	private Label lblFantasia;

	@FXML
	private TextField txtFantasia;

	@FXML
	private Label lblCpfCnpj;

	@FXML
	private TextField txtCnpj;

	@FXML
	private Label lblIncEstad;

	@FXML
	private TextField txtIncEstad;

	@FXML
	private Label lblIndIncEstad;

	@FXML
	private Label lblLimiteCredito;

	@FXML
	private TextField txtLimiteCredito;

	@FXML
	private Label lblLocalLimiteCredito;

	@FXML
	private RadioButton rdbLocalLimiteCredito0;

	@FXML
	private ToggleGroup grpLocalLimiteCredito;

	@FXML
	private RadioButton rdbLocalLimiteCredito1;

	@FXML
	private Label lblNoSuframa;

	@FXML
	private TextField txtNoSuframa;

	@FXML
	private Label lblConsumidorFinal;

	@FXML
	private ComboBox<ComboBoxFilter> cboxConsumidorFinal;

	@FXML
	private Label lblIdentEstrangeiro;

	@FXML
	private Label lblContatoPosVenda;

	@FXML
	private TextField txtContatoPosVenda;

	@FXML
	private TextField txtIdentEstrangeiro;

	@FXML
	private ImageView imgImgCli;

	@FXML
	private Button btnImgCli;

	@FXML
	private Button btnRemoveImgCli;

	// Tab Referências
	@FXML
	private Tab tabReferencias;

	@FXML
	private Label lblCodRegiao;

	@FXML
	private FontAwesomeIconView iconNewFab;

	@FXML
	private CustomTextField txtCodRegiao;

	@FXML
	private CustomTextField txtRegiao;

	@FXML
	private Label lblCodRamoAtividade;

	@FXML
	private FontAwesomeIconView iconNewFab1;

	@FXML
	private CustomTextField txtCodRamoAtividade;

	@FXML
	private CustomTextField txtRamoAtividade;

	@FXML
	private Label lblCodConvenio;

	@FXML
	private FontAwesomeIconView iconNewFab2;

	@FXML
	private CustomTextField txtCodConvenio;

	@FXML
	private CustomTextField txtConvenio;

	@FXML
	private Label lblCodRota;

	@FXML
	private FontAwesomeIconView iconNewFab11;

	@FXML
	private CustomTextField txtCodRota;

	@FXML
	private CustomTextField txtRota;

	@FXML
	private Label lblCodVendedor;

	@FXML
	private FontAwesomeIconView iconNewFab3;

	@FXML
	private CustomTextField txtCodVendedor;

	@FXML
	private TextField txtVendedor;

	@FXML
	private Label lblCodSegmento;

	@FXML
	private FontAwesomeIconView iconNewFab12;

	@FXML
	private CustomTextField txtCodSegmento;

	@FXML
	private CustomTextField txtSegmento;

	@FXML
	private Label lblCodFuncionario;

	@FXML
	private FontAwesomeIconView iconNewFab21;

	@FXML
	private CustomTextField txtCodFuncionario;

	@FXML
	private CustomTextField txtFuncionario;

	@FXML
	private Label lblDataCadastro;

	@FXML
	private DatePicker dateDataCadastro;

	@FXML
	private Label lblDataValidade;

	@FXML
	private DatePicker dateDataValidade;

	@FXML
	private Label lblDataAtualizacao;

	@FXML
	private DatePicker dateDataAtualizacao;

	@FXML
	private Label lblDataUltimaCompra;

	@FXML
	private DatePicker dateDataUltimaCompra;

	@FXML
	private Label lblVendedor;

	@FXML
	private Label lblDataUltimaContato;

	@FXML
	private DatePicker dateDataUltimaContato;

	@FXML
	private Label lblObservacoes;

	@FXML
	private TextArea txtObservacoes;

	@FXML
	private Label lblObsRestritiva;

	@FXML
	private TextArea txtObsRestritiva;

	// Tab faturamento

	@FXML
	private Tab tabFaturamento;

	@FXML
	private Label lblTipoFaturamento;

	@FXML
	private ComboBox<ComboBoxFilter> cboxTipoFaturamento;

	@FXML
	private Label lblDescontoMax;

	@FXML
	private TextField txtDescontoMax;

	@FXML
	private Label lblTipoPrecoVenda;

	@FXML
	private RadioButton rdbTipoPrecoVenda0;

	@FXML
	private ToggleGroup grpTipoPrecoVenda;

	@FXML
	private RadioButton rdbTipoPrecoVenda1;

	@FXML
	private RadioButton rdbTipoPrecoVenda2;

	@FXML
	private Label lblPermiteUsoTabelaAtacado;

	@FXML
	private JFXToggleButton tgbPermiteUsoTabelaAtacado;

	@FXML
	private Label lblFreteConta;

	@FXML
	private RadioButton rdbFreteConta0;

	@FXML
	private ToggleGroup grpFreteConta;

	@FXML
	private RadioButton rdbFreteConta1;

	@FXML
	private Label lblCodPortador;

	@FXML
	private CustomTextField txtCodPortador;

	@FXML
	private CustomTextField txtPortador;

	@FXML
	private Label lblCodSecao;

	@FXML
	private CustomTextField txtCodSecao;

	@FXML
	private CustomTextField txtSecao;

	@FXML
	private Label lblCodPlanoPagamento;

	@FXML
	private CustomTextField txtCodPlanoPagamento;

	@FXML
	private CustomTextField txtPlanoPagamento;

	@FXML
	private Label lblCodTabelaPreco;

	@FXML
	private FontAwesomeIconView iconNewFab31;

	@FXML
	private CustomTextField txtCodTabelaPreco;

	@FXML
	private CustomTextField txtTabelaPreco;

	@FXML
	private Label lblCodOperacaoSaida;

	@FXML
	private FontAwesomeIconView iconNewFab121;

	@FXML
	private CustomTextField txtCodOperacaoSaida;

	@FXML
	private CustomTextField txtOperacaoSaida;

	@FXML
	private Label lblCodTransportadora;

	@FXML
	private FontAwesomeIconView iconNewFab211;

	@FXML
	private CustomTextField txtTransportadora;

	@FXML
	private CustomTextField txtCodTransportadora;

	// Tab Dados p/ Entrega
	@FXML
	private Tab tabDadosEntrega;

	@FXML
	private Label lblEnderecoEntrega;

	@FXML
	private TextField txtEnderecoEntrega;

	@FXML
	private Label lblNumeroEntrega;

	@FXML
	private TextField txtNumeroEntrega;

	@FXML
	private Label lblComplementoEntrega;

	@FXML
	private TextField txtComplementoEntrega;

	@FXML
	private Label lblUfEntrega;

	@FXML
	private TextField txtUfEntrega;

	@FXML
	private Label lblCepEntrega;

	@FXML
	private TextField txtCepEntrega;

	@FXML
	private Label lblTelefoneEntrega;

	@FXML
	private TextField txtTelefoneEntrega;

	@FXML
	private Label lblBairroEntrega;

	@FXML
	private TextField txtBairroEntrega;

	@FXML
	private Label lblCodCidadeEntrega;

	@FXML
	private FontAwesomeIconView iconNewFab4;

	@FXML
	private CustomTextField txtCodCidadeEntrega;

	@FXML
	private CustomTextField txtCidadeEntrega;

	@FXML
	private Label lblTeleFaxEntrega;

	@FXML
	private TextField txtTeleFaxEntrega;

	@FXML
	private Label lblReferenciaEntrega;

	@FXML
	private TextField txtReferenciaEntrega;

	@FXML
	private Button btnAddEndereco, btnCancelEndereco;

	@FXML
	private TableView<ClienteEndEntrega> tbViewDadosEntrega;

	@FXML
	private TableColumn<ClienteEndEntrega, String> tbColEnderecoEntrega;

	@FXML
	private TableColumn<ClienteEndEntrega, String> tbColEndNumeroEntrega;

	@FXML
	private TableColumn<ClienteEndEntrega, String> tbColComplementoEntrega;

	@FXML
	private TableColumn<ClienteEndEntrega, String> tbColBairroEntrega;

	@FXML
	private TableColumn<ClienteEndEntrega, Integer> tbColCodCidadeEntrega;

	@FXML
	private TableColumn<ClienteEndEntrega, String> tbColCidadeEntrega;

	@FXML
	private TableColumn<ClienteEndEntrega, String> tbColUfEntrega;

	@FXML
	private TableColumn<ClienteEndEntrega, String> tbColCepEntrega;

	@FXML
	private TableColumn<ClienteEndEntrega, String> tbColFoneEntrega;

	@FXML
	private TableColumn<ClienteEndEntrega, String> tbColReferenciaEntrega;

	// Tab Dados Bancarios
	@FXML
	private Tab tabDadosBanc;

	@FXML
	private Label lblBanco;

	@FXML
	private TextField txtBanco;

	@FXML
	private Label lblAgencia;

	@FXML
	private TextField txtAgencia;

	@FXML
	private Label lblConta;

	@FXML
	private TextField txtConta;

	@FXML
	private Label lblDestinatario;

	@FXML
	private TextField txtDestinatario;

	@FXML
	private Label lblNoCpfCnpj;

	@FXML
	private TextField txtNoCpfCnpj;

	@FXML
	private Label lblEnviaDadosCartorio;

	@FXML
	private JFXToggleButton tgbEnviaDadosCartorio;

	@FXML
	private Label lblDiasEnviaDadosCartorio;

	@FXML
	private Spinner<Integer> spnDiasEnviaDadosCartorio;

	// Tab Consulta Receita
	@FXML
	private Tab tabConsultaReceita;

	@FXML
	private WebView webCnpj = new WebView();

	@FXML
	private ProgressBar pBarCnpj = new ProgressBar(0);

	@FXML
	private Button btnEnviaCnpj;

	@FXML
	private Button btnCapturaDados;

	@FXML
	private TextField txtUrlCnpj;

	@FXML
	private ImageView imgLoading;

	// ------------------- ELEMENTOS CONTIDOS NO FORMULARIO FXML ------------------------

	// ATRIBUTOS DA CLASSE
	private ObservableList<Cliente> listaRegistros = FXCollections.observableArrayList();
	private ObservableList<ClienteEndEntrega> listClienteEndEntrega = FXCollections.observableArrayList();
	private ObservableList<ComboBoxFilter> listComboBoxFilter = FXCollections.observableArrayList();
	private ObservableList<ComboBoxFilter> listCboxConsumidorFinal = FXCollections.observableArrayList();
	private ObservableList<ComboBoxFilter> listCboxTipoFaturamento = FXCollections.observableArrayList();
	private List<TableConfPrint> tableShowPrintList = new ArrayList<TableConfPrint>();
	private boolean flagInsertUpdate = true; // FLAG FOR OPERATION INSERT OUR
												// UPDATE
	boolean flagPaneFilter = true;// ATRIBUTO UTILIZADO PARA CONTROLAR O STATUS
									// DO PAINEL DO FILTRO ABERTO OU FECHADO
	boolean flagSearchCidade = true;// ATRIBUTO UTILIZADO PARA CONTROLAR O
									// STATUS DO PAINEL DO FILTRO ABERTO OU
									// FECHADO
	boolean flagSearch = true;
	boolean flagOpenBuscador = true;
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
	private Cliente entidadeBean = new Cliente();
	private ClienteDAO entidadeDao = new ClienteDAO();
	CidadeDAO cidadeDAO = new CidadeDAO();
	private Util util = new Util();
	private PopOver popOver = new PopOver();
	private static Stage stg;
	private String fileNameConfigColum = "Grid-Cad-Cliente";
	private Object object;
	// Date formatter
	private String pattern = "dd/MM/yyyy";
	private final LocalDate today = LocalDate.now();

	/**
	 * CONSTRUTOR PADRAO DA CLASSE
	 */
	public ClienteController() {

	}

	/**
	 * CONSTRUTOR GENERICO DA CLASSE ONDE É PASSADO OS NIVEIS DE ACESSO DO
	 * FORMULARIO
	 * 
	 * @param nivAcesso
	 * @param o_tabPrincipal
	 */
	public ClienteController(NivelAcesso nivAcesso, TabPane o_tabPrincipal) {

		this.nivAcessoPermissao = nivAcesso;
		this.tabPrincipal = o_tabPrincipal;

	}

	@FXML
	/**
	 * AÇÃO DO BOTAO INSERIR PRESENTE NA ABA LISTAGEM (btnInsertGrid) - ATALHO
	 * F5
	 */
	void actionBtnInsertGrid(ActionEvent event) {
		if (nivAcessoPermissao.getFlagInsert().equals(1)) {
			entidadeBean = new Cliente();
			btnInsert.setDisable(true);
			btnSave.setDisable(false);
			btnCancel.setDisable(false);
			txtCodigo.setDisable(true);
			resetForm();
			txtCodigo.setText("+1");
			Util.openFormCadastro(anchorPaneListagem, anchorPaneDetalhes, 0);
			
			
			//--START EMPTY---
			lblErrorMessage.setText("");
			
			txtRazao.requestFocus();

			lblCpfCnpj.setDisable(false);
			txtCnpj.setDisable(false);
			lblCodVendedor.setDisable(false);
			txtCodVendedor.setDisable(false);
			txtVendedor.setDisable(false);
			txtCodConvenio.setDisable(false);
			lblCodConvenio.setDisable(false);
			txtConvenio.setDisable(false);
			lblTipoPrecoVenda.setDisable(false);
			for (Toggle t : grpTipoPrecoVenda.getToggles())
				((RadioButton) t).setDisable(false);

			rdbTipoPrecoVenda0.setSelected(true);
			lblTipoFaturamento.setDisable(false);
			cboxTipoFaturamento.setDisable(false);
			lblLimiteCredito.setDisable(false);
			if (DadosGlobais.usuarioLogado.getVdaAltLimiteCredito().equals(1))
				txtLimiteCredito.setDisable(false);
			else
				txtLimiteCredito.setDisable(true);

			lblObsRestritiva.setDisable(false);
			txtObsRestritiva.setDisable(false);
			lblEnviaDadosCartorio.setDisable(false);
			tgbEnviaDadosCartorio.setDisable(false);
			tgbEnviaDadosCartorio.setSelected(false);
			lblDiasEnviaDadosCartorio.setDisable(true);
			spnDiasEnviaDadosCartorio.setDisable(true);
			spnDiasEnviaDadosCartorio.getValueFactory().setValue(1);

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
			entidadeBean = new Cliente();
			btnInsert.setDisable(true);
			btnSave.setDisable(false);
			btnCancel.setDisable(false);
			txtCodigo.setDisable(true);
			resetForm();
			txtCodigo.setText("+1");
			txtRazao.requestFocus();
			imgImgCli.setImage(new Image("/styles/img/user.png"));

			txtCnpj.setDisable(false);
			txtCodVendedor.setDisable(false);
			txtVendedor.setDisable(false);
			txtCodConvenio.setDisable(false);
			txtConvenio.setDisable(false);
			for (Toggle t : grpTipoPrecoVenda.getToggles())
				((RadioButton) t).setDisable(false);

			rdbTipoPrecoVenda0.setSelected(true);
			cboxTipoFaturamento.setDisable(false);
			txtLimiteCredito.setDisable(false);
			if (DadosGlobais.usuarioLogado.getVdaAltLimiteCredito().equals(1))
				txtLimiteCredito.setDisable(false);
			else
				txtLimiteCredito.setDisable(true);

			lblObsRestritiva.setDisable(false);
			txtObsRestritiva.setDisable(false);
			lblEnviaDadosCartorio.setDisable(false);
			tgbEnviaDadosCartorio.setDisable(false);
			tgbEnviaDadosCartorio.setSelected(false);
			lblDiasEnviaDadosCartorio.setDisable(true);
			spnDiasEnviaDadosCartorio.setDisable(true);
			spnDiasEnviaDadosCartorio.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 360, 1));

		} else {
			util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
		}

	}

	@FXML
	/**
	 * AÇÃO DO BOTAO INSERIR PRESENTE NA ABA DETALHES (btnInsert) - ATALHO F5
	 */
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
		resetForm();
		Util.deleteFile(DadosGlobais.folderImagens + "tmp.png");

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

	@SuppressWarnings("unchecked")
	@FXML
	/**
	 * AÇÃO DO BOTAO SALVAR PRESENTE NA ABA DETALHES (btnSave) - ATALHO F6
	 */
	void actionBtnSave(ActionEvent event) {

		if (nivAcessoPermissao.getFlagUpdate().equals(1)) {

			if (!Util.noEmpty(lblErrorMessage, anchorPaneDetalhes, txtRazao, txtFantasia, txtCodCidade, txtCidade, txtUF, txtBairro, txtCep)
					&& !Util.noEmptyCboxFilter(cboxIndIncEstad) && Validacoes.isCep(txtCep)
					&& Validacoes.isTelefone(txtFone) && Validacoes.isTelefone(txtFone2)) {

				/*
				 * validate client by user if cnpj is duplicadted, if is null,
				 * credit limit, txtCnpj,
				 */
				// Validate Client whit CPF/CNPJ empty
				if (DadosGlobais.usuarioLogado.getVdaAutorizaCpfZerado().equals(0) && txtCnpj.getText().isEmpty()) {
					txtCnpj.setStyle("-fx-border-color: #ff7575;");
					txtCnpj.requestFocus();
					return;
				} else if (!txtCnpj.getText().isEmpty()) {
					if (cboxTipoPessoa.getValue().getAction().equals(1)
							&& !Validacoes.isCPF(txtCnpj.getText().toString())) {
						txtCnpj.setStyle("-fx-border-color: #ff7575;");
						txtCnpj.requestFocus();
						return;
					} else if (cboxTipoPessoa.getValue().getAction().equals(2)
							&& !Validacoes.isCNPJ(txtCnpj.getText().toString())) {
						txtCnpj.setStyle("-fx-border-color: #ff7575;");
						txtCnpj.requestFocus();
						return;
					}
				}

				// Validate Client whit CPF/CNPJ duplicate
				List<Cliente> cli = entidadeDao.getClienteByCNPJ(txtCnpj.getText());
				if (!cli.isEmpty()) {
					for (Cliente c : cli) {
						if (!c.getCodigo().equals(Integer.valueOf(txtCodigo.getText()))) {

							if (!util.showAlert(
									DadosGlobais.resourceBundle.getString("clienteController.alertCpfCnpjDuplicated")
											+ " " + c.getCodigo() + ". \n"
											+ DadosGlobais.resourceBundle
													.getString("clienteController.alertCpfCnpjDuplicated1"),
									"confirmation")) {
								txtCnpj.requestFocus();
								return;
							}
						}
					}
				}

				// Tab Informacoes Gerais
				entidadeBean.setFlagTipopessoa(cboxTipoPessoa.getSelectionModel().getSelectedItem().getAction());
				entidadeBean.setRazao(txtRazao.getText());
				entidadeBean.setFantasia(txtFantasia.getText());
				entidadeBean.setCpfCnpj(Util.textfieldNotNull("str", txtCnpj));
				entidadeBean.setIeRg(Util.textfieldNotNull("str", txtIncEstad));
				entidadeBean.setFlagIndicadorinscest(cboxIndIncEstad.getSelectionModel().getSelectedIndex());
				entidadeBean.setCodCidade(Integer.valueOf(txtCodCidade.getText()));
				entidadeBean.setCidade(txtCidade.getText());
				entidadeBean.setUf(txtUF.getText());
				entidadeBean.setEndereco(Util.textfieldNotNull("str", txtEndereco));
				entidadeBean.setEndNumero(Util.textfieldNotNull("int", txtEndNumero));
				entidadeBean.setComplemento(Util.textfieldNotNull("str", txtComplemento));
				entidadeBean.setBairro(Util.textfieldNotNull("str", txtBairro));
				entidadeBean.setCep(Util.textfieldNotNull("str", txtCep));
				entidadeBean.setFone(Util.textfieldNotNull("str", txtFone));
				entidadeBean.setCelular(Util.textfieldNotNull("str", txtCelular));
				entidadeBean.setFone2(Util.textfieldNotNull("str", txtFone2));
				entidadeBean.setEmail(Util.textfieldNotNull("str", txtEmail));
				entidadeBean.setInternetSite(Util.textfieldNotNull("str", txtInternetSite));
				entidadeBean.setInscProdrural(Util.textfieldNotNull("str", txtInscProdRural));
				entidadeBean.setLimiteCredito(Util.decimalBRtoBigDecimal(4, txtLimiteCredito.getText()));
				entidadeBean.setOrigemLimiteCredito(Integer
						.valueOf(grpLocalLimiteCredito.selectedToggleProperty().getValue().getUserData().toString()));
				entidadeBean.setSuframa(Util.textfieldNotNull("str", txtNoSuframa));
				entidadeBean.setFlagConsumidorFinal(cboxConsumidorFinal.getSelectionModel().getSelectedIndex());
				entidadeBean.setIdentificacaoEstrangeiro(Util.textfieldNotNull("str", txtIdentEstrangeiro));
				entidadeBean.setContatoNome(Util.textfieldNotNull("str", txtContatoPosVenda));

				File file = new File(DadosGlobais.folderImagens + "tmp.png");

				if (!file.exists()) {
					Util.saveImageToFile(imgImgCli.getImage(), DadosGlobais.folderImagens + "tmp.png");
					file = new File(DadosGlobais.folderImagens + "tmp.png");
				}

				if (file.canRead()) {

					byte[] bFile = new byte[(int) file.length()];

					try {
						FileInputStream fileInputStream = new FileInputStream(file);
						fileInputStream.read(bFile);
						fileInputStream.close();
					} catch (Exception e) {
						e.printStackTrace();
					}

					entidadeBean.setFoto(bFile);
				}

				// Tab Referencias
				if (entidadeBean.getCodRegiao() == null) {
					entidadeBean.setCodRegiao(0);
					entidadeBean.setCodRegiaoFk(BigInteger.ZERO);
				}

				if (entidadeBean.getCodRamoAtividade() == null) {
					entidadeBean.setCodRamoAtividade(0);
					entidadeBean.setCodRamoAtividadeFk(BigInteger.ZERO);
				}

				if (entidadeBean.getCodConvenio() == null) {
					entidadeBean.setCodConvenio(0);
					entidadeBean.setCodConvenioFk(BigInteger.ZERO);
				}

				if (entidadeBean.getCodRota() == null) {
					entidadeBean.setCodRota(0);
					entidadeBean.setCodRotaFk(BigInteger.ZERO);
				}

				if (entidadeBean.getCodVendedor() == null) {
					entidadeBean.setCodVendedor(0);
					entidadeBean.setCodVendedorFk(BigInteger.ZERO);
				}

				if (entidadeBean.getCodSegmento() == null) {
					entidadeBean.setCodSegmento(0);
					entidadeBean.setCodSegmentoFk(BigInteger.ZERO);
				}

				if (entidadeBean.getCodFuncionario() == null) {
					entidadeBean.setCodFuncionario(0);
					entidadeBean.setCodFuncionarioFk(BigInteger.ZERO);
				}

				entidadeBean.setDataCadastro(Util.dateToLocalDateTime(Date.valueOf(dateDataCadastro.getValue())));
				entidadeBean.setDataAtualizacao(Util.dateToLocalDateTime(Date.valueOf(dateDataAtualizacao.getValue())));
				entidadeBean.setObservacao(txtObservacoes.getText().isEmpty() ? " " : txtObservacoes.getText());
				entidadeBean.setObsRestritiva(txtObsRestritiva.getText().isEmpty() ? " " : txtObsRestritiva.getText());

				// Tab Faturamento
				entidadeBean.setTipoFaturamento(cboxTipoFaturamento.getSelectionModel().getSelectedIndex());
				entidadeBean.setDescontoMaximo(Util.decimalBRtoBigDecimal(4, txtDescontoMax.getText()));
				entidadeBean.setTipoPrecoVenda(Integer
						.valueOf(grpTipoPrecoVenda.selectedToggleProperty().getValue().getUserData().toString()));
				entidadeBean.setFlagVendaAtacado(tgbPermiteUsoTabelaAtacado.isSelected() ? 1 : 0);
				entidadeBean.setTipoFrete(
						Integer.valueOf(grpFreteConta.selectedToggleProperty().getValue().getUserData().toString()));

				if (entidadeBean.getCodPortador() == null) {
					entidadeBean.setCodPortador(0);
					entidadeBean.setCodPortadorFk(BigInteger.ZERO);
				}

				if (entidadeBean.getCodSecao() == null) {
					entidadeBean.setCodSecao(0);
					entidadeBean.setCodSecaoFk(BigInteger.ZERO);
				}

				if (entidadeBean.getCodOperacaoSaida() == null) {
					entidadeBean.setCodOperacaoSaida(0);
					entidadeBean.setCodOperacaoSaidaFk(BigInteger.ZERO);
				}

				if (entidadeBean.getCodPlanoPagamento() == null) {
					entidadeBean.setCodPlanoPagamento(0);
					entidadeBean.setCodPlanoPagamentoFk(BigInteger.ZERO);
				}

				if (entidadeBean.getCodTransportadora() == null) {
					entidadeBean.setCodTransportadora(0);
					entidadeBean.setCodTransportadoraFk(BigInteger.ZERO);
				}

				// Tab Dados Bancarios
				entidadeBean.setBanco1Descricao(Util.textfieldNotNull("str", txtBanco));
				entidadeBean.setBancoAgencia(Util.textfieldNotNull("str", txtAgencia));
				entidadeBean.setBancoConta(Util.textfieldNotNull("str", txtConta));
				entidadeBean.setBancoDestinatario(Util.textfieldNotNull("str", txtDestinatario));
				entidadeBean.setBancoCpfCnpj(Util.textfieldNotNull("str", txtNoCpfCnpj));
				entidadeBean.setFlagCobrancaCartorio(tgbEnviaDadosCartorio.isSelected() ? 1 : 0);
				entidadeBean.setCobrancaCartorioDias(spnDiasEnviaDadosCartorio.getValue());

				if (txtCodigo.isDisable() && txtCodigo.getText().equals("+1")) {

					Task<String> TarefaRefresh = new Task<String>() {

						LogRetorno logRet = new LogRetorno();

						@Override
						protected String call() throws Exception {

							logRet = entidadeDao.insert(entidadeBean, tbViewDadosEntrega.getItems());
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

								actionBtnCancelEndereco(null);
								tabPanePrincipal.getSelectionModel().selectFirst();
								txtCodigo.requestFocus();

								Util.deleteFile(DadosGlobais.folderImagens + "tmp.png");

							} else {

								util.showAlert(logRet.getMsg(), "error");

							}
						}

						@Override
						protected void failed() {
							stg.close();
							util.tratamentoExcecao(exceptionProperty().getValue().toString(),
									"[ ClienteController.actionBtnSave() - INSERT ]");
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

				} else

				if (entidadeBean.getCodigo().toString().equals(txtCodigo.getText())) {

					Task<String> TarefaRefresh = new Task<String>() {

						LogRetorno logRet = new LogRetorno();

						@Override
						protected String call() throws Exception {

							logRet = entidadeDao.update(entidadeBean, listClienteEndEntrega,
									tbViewDadosEntrega.getItems());

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

								actionBtnCancelEndereco(null);
								tabPanePrincipal.getSelectionModel().selectFirst();
								txtCodigo.requestFocus();

								Util.deleteFile(DadosGlobais.folderImagens + "tmp.png");

							} else {
								util.showAlert(logRet.getMsg(), "error");
							}

						}

						@Override
						protected void failed() {
							stg.close();
							util.tratamentoExcecao(exceptionProperty().getValue().toString(),
									"[ ClienteController.actionBtnSave() - UPDATE ]");
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
							DadosGlobais.resourceBundle.getString("infSaveChange"), false);
					pBar.setProgress(-1);

				}

				else {
					loadByID(false, Integer.valueOf(txtCodigo.getText()), false);
				}
				// }
				// else {
				// //Util.setStyleError(true, txtCnpj);
				// }
			}
		} else {
			util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
		}
	}

	@FXML
	/**
	 * AÇÃO DO BOTAO CANCELAR PRESENTE NA ABA DETALHES (btnCancel) - ATALHO F7
	 */
	void actionBtnCancel(ActionEvent event) {

		resetForm();
		loadByID(true, null, false);

	}

	@FXML
	/**
	 * AÇÃO DO BOTAO CONFIGURAÇÃO DO GRID PRESENTE NA ABA LISTAGEM (btnConfig)
	 */
	void actionBtnConfig(ActionEvent event) {

		try {
			stg = new Stage();
			Scene scene = new Scene(util.openWindow("/views/utils/viewConfigColumns.fxml",
					new ConfigColumnController(ClienteController.class, tbView, fileNameConfigColum)));
			stg.setScene(scene);
			stg.initStyle(StageStyle.TRANSPARENT);
			stg.initModality(Modality.APPLICATION_MODAL);
			stg.show();
		} catch (IOException e) {
			util.tratamentoExcecao(e.getMessage().toString(), "[ ClienteController.actionBtnConfig() ]");
		}

	}

	@FXML
	/**
	 * AÇÃO DO BOTAO EXCLUIR PRESENTE NA ABA LISTAGEM (btnDelete) ATALHO -
	 * delete
	 */
	void actionBtnDelete(ActionEvent event) {

		if ((entidadeBean.getFlagAtivo().equals(1) && nivAcessoPermissao.getFlagDisable().equals(1))
				|| (entidadeBean.getFlagAtivo().equals(0) && nivAcessoPermissao.getFlagEnable().equals(1))) {

			if (util.showAlert(
					DadosGlobais.resourceBundle.getString("alertConfirmOprExcluir") + " "
							+ (entidadeBean.getFlagAtivo().equals(1)
									? DadosGlobais.resourceBundle.getString("oprExcluir")
									: DadosGlobais.resourceBundle.getString("oprAtivar"))
							+ " " + DadosGlobais.resourceBundle.getString("clienteController.alertConfirmOprExcluir"),
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
								"[ ClienteController.actionBtnDelete() ]");
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
				stg = ProgressBarForm.showProgressBar(ClienteController.class, tarefaCargaPg,
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
		if (util.showAlert(DadosGlobais.resourceBundle.getString("alertConfirShowAllData"), "confirmation")) {
			paramFlagAtivo = Arrays.asList(1);
			taskQuery("all", true);
			cboxFlagAtivo.getSelectionModel().selectFirst();

		}

	}

	@FXML
	void actionBtnImgCli(ActionEvent event) throws IOException {

		FileChooser fileChooser = new FileChooser();

		fileChooser.setTitle("Select the image");
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("BMP", "*.bmp"));

		File file = fileChooser.showOpenDialog(null);
		if (file != null) {
			String url = DadosGlobais.folderImagens + "tmp.png";
			imgImgCli.setImage(Util.resizeImages(file, url));
		}

	}

	@FXML
	void actionRemoveImgCli(ActionEvent event) {

		imgImgCli.setImage(new Image("/styles/img/user.png"));
		btnImgCli.requestFocus();

		Util.deleteFile(DadosGlobais.folderImagens + "tmp.png");

	}

	@FXML
	void actionBtnAddEndereco(ActionEvent event) {

		if (!Util.noEmpty(txtEnderecoEntrega, txtBairroEntrega, txtCodCidadeEntrega, txtCidadeEntrega, txtUfEntrega,
				txtCepEntrega) && Validacoes.isCep(txtCepEntrega) && Validacoes.isTelefone(txtTelefoneEntrega)) {

			if (flagInsertUpdate) {
				ClienteEndEntrega ce = new ClienteEndEntrega();
				ce.setEndereco(Util.textfieldNotNull("str", txtEnderecoEntrega));
				ce.setEndNumero(Util.textfieldNotNull("int", txtNumeroEntrega));
				ce.setComplemento(Util.textfieldNotNull("str", txtComplementoEntrega));
				ce.setBairro(Util.textfieldNotNull("str", txtBairroEntrega));
				ce.setCodCidade(Integer.valueOf(txtCodCidadeEntrega.getText()));
				ce.setCidade(txtCidadeEntrega.getText());
				ce.setUf(txtUfEntrega.getText());
				ce.setCep(Util.textfieldNotNull("str", txtCepEntrega));
				ce.setFone(Util.textfieldNotNull("str", txtTelefoneEntrega));
				ce.setPontoReferencia(Util.textfieldNotNull("str", txtReferenciaEntrega));
				ce.setCheckDelete(Util.checkDeleteCreate());
				ce.setFlagAtivo(1);
				ce.setCliente(entidadeBean);
				tbViewDadosEntrega.getItems().add(ce);

				Util.limpar(txtEnderecoEntrega, txtNumeroEntrega, txtComplementoEntrega, txtBairroEntrega,
						txtCodCidadeEntrega, txtCidadeEntrega, txtUfEntrega, txtCepEntrega, txtTelefoneEntrega,
						txtReferenciaEntrega);

				// btnAddEndereco.setText(DadosGlobais.resourceBundle.getString("clienteController.btnAddEndereco"));
				txtEnderecoEntrega.requestFocus();
				flagInsertUpdate = true;

			} else if (!flagInsertUpdate && tbViewDadosEntrega.getSelectionModel().getSelectedItem() != null) {
				boolean flagUdate = false;

				for (ClienteEndEntrega ce : entidadeBean.getClienteEndEntregas()) {
					if (ce.getRecordNo()
							.equals(tbViewDadosEntrega.getSelectionModel().getSelectedItem().getRecordNo())) {
						flagUdate = true;
						// Update EntidadeBean
						ce.setEndereco(txtEnderecoEntrega.getText());
						ce.setEndNumero(Util.textfieldNotNull("int", txtNumeroEntrega));
						ce.setComplemento(Util.textfieldNotNull("str", txtComplementoEntrega));
						ce.setBairro(Util.textfieldNotNull("str", txtBairroEntrega));
						ce.setCodCidade(Integer.valueOf(txtCodCidadeEntrega.getText()));
						ce.setCidade(txtCidadeEntrega.getText());
						ce.setUf(txtUfEntrega.getText());
						ce.setCep(Util.textfieldNotNull("str", txtCepEntrega));
						ce.setFone(Util.textfieldNotNull("str", txtTelefoneEntrega));
						ce.setPontoReferencia(Util.textfieldNotNull("str", txtReferenciaEntrega));
					}
				}

				if (!flagUdate) {
					// Update TableView and change Button
					tbViewDadosEntrega.getSelectionModel().getSelectedItem().setEndereco(txtEnderecoEntrega.getText());
					tbViewDadosEntrega.getSelectionModel().getSelectedItem()
							.setEndNumero(Util.textfieldNotNull("int", txtNumeroEntrega));
					tbViewDadosEntrega.getSelectionModel().getSelectedItem()
							.setComplemento(Util.textfieldNotNull("str", txtComplementoEntrega));
					tbViewDadosEntrega.getSelectionModel().getSelectedItem()
							.setBairro(Util.textfieldNotNull("str", txtBairroEntrega));
					tbViewDadosEntrega.getSelectionModel().getSelectedItem()
							.setCodCidade(Integer.valueOf(txtCodCidadeEntrega.getText()));
					tbViewDadosEntrega.getSelectionModel().getSelectedItem().setCidade(txtCidadeEntrega.getText());
					tbViewDadosEntrega.getSelectionModel().getSelectedItem().setUf(txtUfEntrega.getText());
					tbViewDadosEntrega.getSelectionModel().getSelectedItem()
							.setCep(Util.textfieldNotNull("str", txtCepEntrega));
					tbViewDadosEntrega.getSelectionModel().getSelectedItem()
							.setFone(Util.textfieldNotNull("str", txtTelefoneEntrega));
					tbViewDadosEntrega.getSelectionModel().getSelectedItem()
							.setPontoReferencia(Util.textfieldNotNull("str", txtReferenciaEntrega));
				}

				Util.limpar(txtEnderecoEntrega, txtNumeroEntrega, txtComplementoEntrega, txtBairroEntrega,
						txtCodCidadeEntrega, txtCidadeEntrega, txtUfEntrega, txtCepEntrega, txtTelefoneEntrega,
						txtReferenciaEntrega);

				alterBtnAddEndereco(true);
				flagInsertUpdate = true;

				tbViewDadosEntrega.refresh();

			}

			btnCancelEndereco.setDisable(true);

		}

	}

	@FXML
	void actionBtnCancelEndereco(ActionEvent event) {

		Util.limpar(txtEnderecoEntrega, txtNumeroEntrega, txtComplementoEntrega, txtBairroEntrega, txtCodCidadeEntrega,
				txtCidadeEntrega, txtUfEntrega, txtCepEntrega, txtTelefoneEntrega, txtReferenciaEntrega);

		alterBtnAddEndereco(true);
		flagInsertUpdate = true;

		btnCancelEndereco.setDisable(true);

	}

	@FXML
	/**
	 * EVENTO QUE ALTERA A MASCARA DO CAMPO CPF/CNPJ DE ACORDO COM O TIPO DE
	 * PESSOA SELECIONADA NO cboxTipoPessoa
	 */
	void actionCboxTipoPessoa(ActionEvent event) {
		if (cboxTipoPessoa.getValue().getField().equals("1")) {
			lblCpfCnpj.setText(DadosGlobais.resourceBundle.getString("clienteController.lblCpf"));
			Util.mascaraCPF(txtCnpj);
		} else {
			lblCpfCnpj.setText(DadosGlobais.resourceBundle.getString("clienteController.lblCnpj"));
			Util.mascaraCNPJ(txtCnpj);

		}
	}

	@FXML
	/**
	 * EVENTO KEYPRESSED NO CAMPO CODIGO (txtCodigo) AO SER PRESSIONADO ENTER
	 * MUDA O FOCO PARA O ELEMENTO DESCRIÇÃO NA TELA DE DETALHES
	 */
	void keyPressedTxtCodigo(KeyEvent event) {
		
		lblErrorMessage.setText(""); 

		if (event.getCode().equals(KeyCode.ENTER)) {
			txtRazao.requestFocus();
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
	 * EVENTO KEYPRESSED NO CAMPO DESCRICAO (txtDescricao) AO SER PRESSIONADO
	 * ENTER ATIVA O BOTAO DE SALVAR (btnSave) AO DIGITAR QUALQUER TECLA SETA O
	 * ESTILO DEFAULT TIRANDO O ESTILO DE VALIDAÇÃO
	 */
	void keyPressedTxtDescricao(KeyEvent event) {
		
		lblErrorMessage.setText(""); 		

		if (event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.TAB)) {

			Util.setFocus(txtRazao);

		}

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
					if (Util.removeSpecialCharacters(cellValue.toLowerCase())
							.contains(txtFilterColumn.textProperty().get().toLowerCase())) {
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
	/**
	 * EVENTO keyPressed do Campo Código da Cidade Caso seja Pressionado F2 abre
	 * o buscador de cidade Caso seja pressionado ENTER tenta buscar a cidade e
	 * troca o foco para o próximo campo
	 */
	void onKeyPressedTxtCodCidade(KeyEvent event) {
		switch (event.getCode()) {
		case F2:
			flagSearchCidade = false;
			showSearchCidade();
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

	@FXML
	/**
	 * EVENTO CLICK NO BOTAO QUE ABRE O BUSCADOR DE CIDADES
	 */
	void onActionTxtCodCidade(ActionEvent event) {
		if (flagOpenBuscador)
			showSearchCidade();

		flagOpenBuscador = true;
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
	void actionBtnCapturaDados(ActionEvent event) throws IOException {

		HtmlCleaner cleaner = new HtmlCleaner();
		@SuppressWarnings("deprecation")
		TagNode node = cleaner.clean(new URL(webCnpj.getEngine().getLocation()), "ISO-8859-1");

		TagNode[] font = node.getElementsByName("font", true);

		String municipio = null, uf = null;

		for (int i = 0; i < font.length; i++) {

			Element e = (Element) webCnpj.getEngine().executeScript("document.getElementById('ni')");
			txtCnpj.setText(Util.getStringConverterCNPJ(e.getAttribute("value")));

			if (font[i].getText().toString().contains("NOME EMPRESARIAL"))
				txtRazao.setText(Util.removeBlankSpace(font[i + 1].getText().toString()));

			if (font[i].getText().toString().contains("TÍTULO DO ESTABELECIMENTO (NOME DE FANTASIA)"))
				txtFantasia.setText(Util.removeBlankSpace(font[i + 1].getText().toString()));

			if (font[i].getText().toString().contains("LOGRADOURO"))
				txtEndereco.setText(Util.removeBlankSpace(font[i + 1].getText().toString()));

			if (font[i].getText().toString().contains("NÚMERO"))
				txtEndNumero.setText(Util.removeBlankSpace(font[i + 1].getText().toString()));

			if (font[i].getText().toString().contains("COMPLEMENTO"))
				txtComplemento.setText(Util.removeBlankSpace(font[i + 1].getText().toString()));

			if (font[i].getText().toString().contains("CEP"))
				txtCep.setText(Util.removeBlankSpace(font[i + 1].getText().toString()));

			if (font[i].getText().toString().contains("BAIRRO/DISTRITO"))
				txtBairro.setText(Util.removeBlankSpace(font[i + 1].getText().toString()));

			if (font[i].getText().toString().contains("MUNICÍPIO"))
				municipio = Util.removeBlankSpace(font[i + 1].getText().toString());

			if (font[i].getText().toString().contains("UF"))
				uf = Util.removeBlankSpace(font[i + 1].getText().toString());

			if (font[i].getText().toString().contains("ENDEREÇO ELETRÔNICO"))
				txtEmail.setText(Util.removeBlankSpace(font[i + 1].getText().toString()));

			if (font[i].getText().toString().contains("TELEFONE")){				
				
				String []t = Util.removeBlankSpace(font[i + 1].getText().toString()).split("/");
				
				if(t.length==1)
					txtFone.setText(t[0]);
				else{
					txtFone.setText(t[0].substring(0, t[0].length() - 1));
					txtFone2.setText(t[1].substring(1));
				}
				
			}
				

		}

		LogRetorno log = cidadeDAO.getCidadeByName(municipio, uf);
		Cidade cidade = (Cidade) log.getObjeto();

		if (cidade != null) {

			txtCodCidade.setText(cidade.getCodigo().toString());
			txtCidade.setText(cidade.getDescricao());
			txtUF.setText(cidade.getUfSigla());

		}

		else {
			util.showAlert("Cidade não encotrada", "information");
			txtCodCidade.requestFocus();
		}

		tabPanePrincipal.getSelectionModel().select(0);

	}

	@FXML
	void actionBtnEnviaCnpj(ActionEvent event) {

		Element e = (Element) webCnpj.getEngine().executeScript("document.getElementById('cnpj')");
		e.setAttribute("value", Util.getStringConverterCNPJ(txtCnpj.getText()));

	}

	@FXML
	void onActionTxtCidadeEntrega(ActionEvent event) {

	}

	@FXML
	void onActionTxtCodCidadeEntrega(ActionEvent event) {

		if (flagOpenBuscador)
			showSearchCidadeEndereco();

		flagOpenBuscador = true;

	}

	@FXML
	void onActionTxtCodConvenio(ActionEvent event) {

		if (flagOpenBuscador)
			showSearchConvenio();

		flagOpenBuscador = true;

	}

	@FXML
	void onActionTxtCodFuncionario(ActionEvent event) {

		if (flagOpenBuscador)
			showSearchFuncionario();

		flagOpenBuscador = true;

	}

	@FXML
	void onActionTxtCodRamoAtividade(ActionEvent event) {

		if (flagOpenBuscador)
			showSearchRamoAtividade();

		flagOpenBuscador = true;

	}

	@FXML
	void onActionTxtCodRegiao(ActionEvent event) {

		if (flagOpenBuscador)
			showSearchRegiao();

		flagOpenBuscador = true;

	}

	@FXML
	void onActionTxtCodRota(ActionEvent event) {

		if (flagOpenBuscador)
			showSearchRota();

		flagOpenBuscador = true;

	}

	@FXML
	void onActionTxtCodSegmento(ActionEvent event) {

		if (flagOpenBuscador)
			showSearchSegmento();

		flagOpenBuscador = true;

	}

	@FXML
	void onActionTxtCodVendedor(ActionEvent event) {

		if (flagOpenBuscador)
			showSearchVendedor();

		flagOpenBuscador = true;

	}

	@FXML
	void onActionTxtConvenio(ActionEvent event) {

	}

	@FXML
	void onActionTxtFuncionario(ActionEvent event) {

	}

	@FXML
	void onActionTxtRamoAtividade(ActionEvent event) {

	}

	@FXML
	void onActionTxtRegiao(ActionEvent event) {

	}

	@FXML
	void onActionTxtRota(ActionEvent event) {

	}

	@FXML
	void onActionTxtSegmento(ActionEvent event) {

	}

	@FXML
	void onActionTxtOperacaoSaida(ActionEvent event) {

	}

	@FXML
	void onActionTxtCodOperacaoSaida(ActionEvent event) {

		if (flagOpenBuscador)
			showSearchOperacaoSaida();

		flagOpenBuscador = true;

	}

	@FXML
	void onActionTxtCodPlanoPagamento(ActionEvent event) {

		if (flagOpenBuscador)
			showSearchPlanoPagamento();

		flagOpenBuscador = true;

	}

	@FXML
	void onActionTxtCodPortador(ActionEvent event) {

		if (flagOpenBuscador)
			showSearchPortador();

		flagOpenBuscador = true;

	}

	@FXML
	void onActionTxtCodSecao(ActionEvent event) {

		if (flagOpenBuscador)
			showSearchSecao();

		flagOpenBuscador = true;

	}

	@FXML
	void onActionTxtCodTabelaPreco(ActionEvent event) {

	}

	@FXML
	void onActionTxtCodTransportadora(ActionEvent event) {

		if (flagOpenBuscador)
			showSearchTransportadora();

		flagOpenBuscador = true;

	}

	@FXML
	void onActionTxtPlanoPagamento(ActionEvent event) {

	}

	@FXML
	void onActionTxtPortador(ActionEvent event) {

	}

	@FXML
	void onActionTxtSecao(ActionEvent event) {

	}

	@FXML
	void onActionTxtTabelaPreco(ActionEvent event) {

	}

	@FXML
	void onActionTxtTransportadora(ActionEvent event) {

	}

	@FXML
	void onActionTxtVendedor(ActionEvent event) {

	}

	@FXML
	void onClickLblCodCidade(MouseEvent event) {

		try {
			if (Util.getNivelAcessoEntidade(EnumNivelAcesso.CIDADES).getFlagView().equals(1)) {
				util.crearTab(tabPrincipal, DadosGlobais.resourceBundle.getString("miCadCidade"),
						"/views/vendas/viewCidade.fxml",
						new CidadeController(Util.getNivelAcessoEntidade(EnumNivelAcesso.CIDADES), tabPrincipal),
						false);
			} else {
				util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	void onClickLblCodCidadeEntrega(MouseEvent event) {

	}

	@FXML
	void onClickLblCodConvenio(MouseEvent event) {

		try {
			if (Util.getNivelAcessoEntidade(EnumNivelAcesso.CONVENIO).getFlagView().equals(1)) {
				util.crearTab(tabPrincipal, DadosGlobais.resourceBundle.getString("miCadConvenio"),
						"/views/vendas/viewConvenio.fxml",
						new ConvenioController(Util.getNivelAcessoEntidade(EnumNivelAcesso.CONVENIO), tabPrincipal),
						false);
			} else {
				util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	void onClickLblCodFuncionario(MouseEvent event) {

		try {
			if (Util.getNivelAcessoEntidade(EnumNivelAcesso.FUNCIONARIO).getFlagView().equals(1)) {
				util.crearTab(tabPrincipal, DadosGlobais.resourceBundle.getString("miCadFuncionario"),
						"/views/recursosHumanos/viewFuncionario.fxml", new FuncionarioController(
								Util.getNivelAcessoEntidade(EnumNivelAcesso.FUNCIONARIO), tabPrincipal),
						false);
			} else {
				util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	void onClickLblCodRamoAtividade(MouseEvent event) {

		try {
			if (Util.getNivelAcessoEntidade(EnumNivelAcesso.RAMO_ATIVIDADE).getFlagView().equals(1)) {
				util.crearTab(tabPrincipal, DadosGlobais.resourceBundle.getString("miCadRamoAtividade"),
						"/views/vendas/viewRamosActvClientes.fxml", new RamosActvClientesController(
								Util.getNivelAcessoEntidade(EnumNivelAcesso.RAMO_ATIVIDADE), tabPrincipal),
						false);
			} else {
				util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	void onClickLblCodRegiao(MouseEvent event) {

		try {
			if (Util.getNivelAcessoEntidade(EnumNivelAcesso.REGIOES_CLIENTES).getFlagView().equals(1)) {
				util.crearTab(tabPrincipal, DadosGlobais.resourceBundle.getString("miCadRegioesClientes"),
						"/views/vendas/viewRegiao.fxml", new RegiaoController(
								Util.getNivelAcessoEntidade(EnumNivelAcesso.REGIOES_CLIENTES), tabPrincipal),
						false);
			} else {
				util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	void onClickLblCodRota(MouseEvent event) {

		try {
			if (Util.getNivelAcessoEntidade(EnumNivelAcesso.ROTAS).getFlagView().equals(1)) {
				util.crearTab(tabPrincipal, DadosGlobais.resourceBundle.getString("miCadRotas"),
						"/views/vendas/viewRotas.fxml",
						new RotaController(Util.getNivelAcessoEntidade(EnumNivelAcesso.ROTAS), tabPrincipal), false);
			} else {
				util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	void onClickLblCodSegmento(MouseEvent event) {

		try {
			if (Util.getNivelAcessoEntidade(EnumNivelAcesso.SEGMENTOS).getFlagView().equals(1)) {
				util.crearTab(tabPrincipal, DadosGlobais.resourceBundle.getString("miCadSegmento"),
						"/views/compras/viewSegmento.fxml",
						new SegmentoController(Util.getNivelAcessoEntidade(EnumNivelAcesso.SEGMENTOS), tabPrincipal),
						false);
			} else {
				util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	void onClickLblCodVendedor(MouseEvent event) {

	}

	@FXML
	void onClickLblCodOperacaoSaida(MouseEvent event) {

		try {
			if (Util.getNivelAcessoEntidade(EnumNivelAcesso.OPERACOES_DE_SAIDA).getFlagView().equals(1)) {
				util.crearTab(tabPrincipal, DadosGlobais.resourceBundle.getString("miCadOperacaoSaida"),
						"/views/configuracoes/viewOperacaoSaida.fxml", new OperacaoSaidaController(
								Util.getNivelAcessoEntidade(EnumNivelAcesso.OPERACOES_DE_SAIDA), tabPrincipal),
						false);
			} else {
				util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	void onClickLblCodPlanoPagamento(MouseEvent event) {

		try {
			if (Util.getNivelAcessoEntidade(EnumNivelAcesso.PLANO_DE_PAGAMENTO).getFlagView().equals(1)) {
				util.crearTab(tabPrincipal, DadosGlobais.resourceBundle.getString("miCadPlanoPagamento"),
						"/views/vendas/viewPlanoPagamento.fxml", new PortadorController(
								Util.getNivelAcessoEntidade(EnumNivelAcesso.PLANO_DE_PAGAMENTO), tabPrincipal),
						false);
			} else {
				util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	void onClickLblCodPortador(MouseEvent event) {

		try {
			if (Util.getNivelAcessoEntidade(EnumNivelAcesso.PORTADOR).getFlagView().equals(1)) {
				util.crearTab(tabPrincipal, DadosGlobais.resourceBundle.getString("miCadPortador"),
						"/views/financeiro/viewPortador.fxml",
						new PortadorController(Util.getNivelAcessoEntidade(EnumNivelAcesso.PORTADOR), tabPrincipal),
						false);
			} else {
				util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	void onClickLblCodSecao(MouseEvent event) {

		try {
			if (Util.getNivelAcessoEntidade(EnumNivelAcesso.SECAO).getFlagView().equals(1)) {
				util.crearTab(tabPrincipal, DadosGlobais.resourceBundle.getString("miCadSecao"),
						"/views/financeiro/viewSecao.fxml",
						new SecaoController(Util.getNivelAcessoEntidade(EnumNivelAcesso.SECAO), tabPrincipal), false);
			} else {
				util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	void onClickLblCodTabelaPreco(MouseEvent event) {

	}

	@FXML
	void onClickLblCodTransportadora(MouseEvent event) {

	}

	@FXML
	void onKeyPressedTxtCodCidadeEntrega(KeyEvent event) {

		switch (event.getCode()) {
		case F2:
			flagSearch = false;
			showSearchCidadeEndereco();
			flagSearch = true;
			break;

		case ENTER:
		case TAB:
			flagOpenBuscador = false;
			txtCepEntrega.requestFocus();
			break;

		default:
			break;

		}

	}

	@FXML
	void onKeyPressedTxtCodConvenio(KeyEvent event) {

		switch (event.getCode()) {
		case F2:
			flagSearch = false;
			showSearchConvenio();
			flagSearch = true;
			break;

		case ENTER:
		case TAB:
			flagOpenBuscador = false;
			txtCodRota.requestFocus();
			break;

		default:
			break;

		}

	}

	@FXML
	void onKeyPressedTxtCodFuncionario(KeyEvent event) {

		switch (event.getCode()) {
		case F2:
			flagSearch = false;
			showSearchFuncionario();
			;
			flagSearch = true;
			break;

		case ENTER:
		case TAB:
			flagOpenBuscador = false;
			cboxTipoFaturamento.requestFocus();
			break;

		default:
			break;

		}

	}

	@FXML
	void onKeyPressedTxtCodRamoAtividade(KeyEvent event) {

		switch (event.getCode()) {
		case F2:
			flagSearch = false;
			showSearchRamoAtividade();
			flagSearch = true;
			break;

		case ENTER:
		case TAB:
			flagOpenBuscador = false;
			txtCodConvenio.requestFocus();
			break;

		default:
			break;

		}

	}

	@FXML
	void onKeyPressedTxtCodRegiao(KeyEvent event) {

		switch (event.getCode()) {
		case F2:
			flagSearch = false;
			showSearchRegiao();
			flagSearch = true;
			break;

		case ENTER:
		case TAB:
			flagOpenBuscador = false;
			txtCodRamoAtividade.requestFocus();
			break;

		default:
			break;

		}

	}

	@FXML
	void onKeyPressedTxtCodRota(KeyEvent event) {

		switch (event.getCode()) {
		case F2:
			flagSearch = false;
			showSearchRota();
			flagSearch = true;
			break;

		case ENTER:
		case TAB:
			flagOpenBuscador = false;
			txtCodVendedor.requestFocus();
			break;

		default:
			break;

		}

	}

	@FXML
	void onKeyPressedTxtCodSegmento(KeyEvent event) {

		switch (event.getCode()) {
		case F2:
			flagSearch = false;
			showSearchSegmento();
			flagSearch = true;
			break;

		case ENTER:
		case TAB:
			flagOpenBuscador = false;
			txtCodVendedor.requestFocus();
			break;

		default:
			break;

		}

	}

	@FXML
	void onKeyPressedTxtCodVendedor(KeyEvent event) {

		switch (event.getCode()) {
		case F2:
			flagSearch = false;
			showSearchVendedor();
			flagSearch = true;
			break;

		case ENTER:
		case TAB:
			flagOpenBuscador = false;
			txtCodVendedor.requestFocus();
			break;

		default:
			break;

		}

	}

	@FXML
	void onKeyPressedTxtCodOperacaoSaida(KeyEvent event) {

		switch (event.getCode()) {
		case F2:
			flagSearch = false;
			showSearchOperacaoSaida();
			flagSearch = true;
			break;

		case ENTER:
		case TAB:
			flagOpenBuscador = false;
			txtCodVendedor.requestFocus();
			break;

		default:
			break;

		}

	}

	@FXML
	void onKeyPressedTxtCodPlanoPagamento(KeyEvent event) {

		switch (event.getCode()) {
		case F2:
			flagSearch = false;
			showSearchPlanoPagamento();
			flagSearch = true;
			break;

		case ENTER:
		case TAB:
			flagOpenBuscador = false;
			txtCodVendedor.requestFocus();
			break;

		default:
			break;

		}

	}

	@FXML
	void onKeyPressedTxtCodPortador(KeyEvent event) {

		switch (event.getCode()) {
		case F2:
			flagSearch = false;
			showSearchPortador();
			flagSearch = true;
			break;

		case ENTER:
		case TAB:
			flagOpenBuscador = false;
			txtCodVendedor.requestFocus();
			break;

		default:
			break;

		}

	}

	@FXML
	void onKeyPressedTxtCodSecao(KeyEvent event) {

		switch (event.getCode()) {
		case F2:
			flagSearch = false;
			showSearchSecao();
			flagSearch = true;
			break;

		case ENTER:
		case TAB:
			flagOpenBuscador = false;
			txtCodVendedor.requestFocus();
			break;

		default:
			break;

		}

	}

	@FXML
	void onKeyPressedTxtCodTabelaPreco(KeyEvent event) {

	}

	@FXML
	void onKeyPressedTxtCodTransportadora(KeyEvent event) {

		switch (event.getCode()) {
		case F2:
			flagSearch = false;
			showSearchTransportadora();
			flagSearch = true;
			break;

		case ENTER:
		case TAB:
			flagOpenBuscador = false;
			cboxTipoFaturamento.requestFocus();
			break;

		default:
			break;

		}

	}

	/**
	 * EXIBE O FORMULARIO DE BUSCA REGIAO
	 */
	public void showSearchRegiao() {

		ObservableList<ComboBoxFilter> list = FXCollections.observableArrayList();

		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("codigo"), 1, "codigo"));
		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("descricao"), 2, "descricao"));
		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("codemp"), 3, "codemp"));

		// util.showSearchView("Regiao", "descricao", RegiaoDAO.class, list,
		// txtCodRegiao, txtRegiao, null,
		// txtCodRamoAtividade);
		object = util.showSearchGetParameters("Regiao", "descricao", RegiaoDAO.class, list);

		if (object != null) {
			Regiao obj = (Regiao) object;
			txtCodRegiao.setText(String.valueOf(obj.getCodigo()));
			txtRegiao.setText(obj.getDescricao());
			// entidadeBean.setRegiao(obj);
			entidadeBean.setCodRegiao(obj.getCodigo());
			entidadeBean.setCodRegiaoFk(obj.getCheckDelete());
			txtCodRamoAtividade.requestFocus();
		}

	}

	/**
	 * EXIBE O FORMULARIO DE BUSCA RAMO DE ATIVIDADE
	 */
	public void showSearchRamoAtividade() {

		ObservableList<ComboBoxFilter> list = FXCollections.observableArrayList();

		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("codigo"), 1, "codigo"));
		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("descricao"), 2, "descricao"));
		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("codemp"), 3, "codemp"));

		// util.showSearchView("Ramo Atividade", "descricao",
		// RamoAtividadeDAO.class, list, txtCodRamoAtividade,
		// txtRamoAtividade, null, txtCodConvenio);
		object = util.showSearchGetParameters("Ramo Atividade", "descricao", RamoAtividadeDAO.class, list);

		if (object != null) {
			RamoAtividade obj = (RamoAtividade) object;
			txtCodRamoAtividade.setText(String.valueOf(obj.getCodigo()));
			txtRamoAtividade.setText(obj.getDescricao());
			// entidadeBean.setRamoAtividade(obj);
			entidadeBean.setCodRamoAtividade(obj.getCodigo());
			entidadeBean.setCodRamoAtividadeFk(obj.getCheckDelete());
			txtCodConvenio.requestFocus();
		}

	}

	/**
	 * EXIBE O FORMULARIO DE BUSCA CONVENIO
	 */
	public void showSearchConvenio() {

		ObservableList<ComboBoxFilter> list = FXCollections.observableArrayList();

		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("codigo"), 1, "codigo"));
		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("descricao"), 2, "descricao"));
		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("codemp"), 3, "codemp"));

		// util.showSearchView("Cônvenio", "descricao", ConvenioDAO.class, list,
		// txtCodConvenio, txtConvenio, null,
		// txtCodRota);

		object = util.showSearchGetParameters("Cônvenio", "descricao", ConvenioDAO.class, list);

		if (object != null) {
			Convenio obj = (Convenio) object;
			txtCodConvenio.setText(String.valueOf(obj.getCodigo()));
			txtConvenio.setText(obj.getDescricao());
			// entidadeBean.setConvenio(obj);
			entidadeBean.setCodConvenio(obj.getCodigo());
			entidadeBean.setCodConvenioFk(obj.getCheckDelete());
			txtCodRota.requestFocus();
		}

	}

	/**
	 * EXIBE O FORMULARIO DE BUSCA ROTA
	 */
	public void showSearchRota() {

		ObservableList<ComboBoxFilter> list = FXCollections.observableArrayList();

		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("codigo"), 1, "codigo"));
		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("descricao"), 2, "descricao"));
		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("codemp"), 3, "codemp"));

		// util.showSearchView("Rota", "descricao", RotaDAO.class, list,
		// txtCodRota, txtRota, null, txtCodVendedor);

		object = util.showSearchGetParameters("Rota", "descricao", RotaDAO.class, list);

		if (object != null) {
			Rota obj = (Rota) object;
			txtCodRota.setText(String.valueOf(obj.getCodigo()));
			txtRota.setText(obj.getDescricao());
			// entidadeBean.setRota(obj);
			entidadeBean.setCodRota(obj.getCodigo());
			entidadeBean.setCodRotaFk(obj.getCheckDelete());
			txtCodVendedor.requestFocus();
		}

	}

	/**
	 * EXIBE O FORMULARIO DE BUSCA VENDEDOR
	 */
	public void showSearchVendedor() {

		ObservableList<ComboBoxFilter> list = FXCollections.observableArrayList();

		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("codigo"), 1, "codigo"));
		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("descricao"), 2, "descricao"));
		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("codemp"), 3, "codemp"));

		Util.flagVendedor = true;
		object = util.showSearchGetParameters("Funcioanrio", "descricao", FuncionarioDAO.class, list);

		if (object != null) {
			Funcionario obj = (Funcionario) object;
			txtCodVendedor.setText(String.valueOf(obj.getCodigo()));
			txtVendedor.setText(obj.getDescricao());
			// entidadeBean.setVendedor(obj);
			entidadeBean.setCodVendedor(obj.getCodigo());
			entidadeBean.setCodVendedorFk(obj.getCheckDelete());
			txtCodSegmento.requestFocus();
		}

	}

	/**
	 * EXIBE O FORMULARIO DE BUSCA SEGMENTO
	 */
	public void showSearchSegmento() {

		ObservableList<ComboBoxFilter> list = FXCollections.observableArrayList();

		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("codigo"), 1, "codigo"));
		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("descricao"), 2, "descricao"));
		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("codemp"), 3, "codemp"));

		// util.showSearchView("Segmento", "descricao", SegmentoDAO.class, list,
		// txtCodSegmento, txtCodSegmento, null,
		// txtCodFuncionario);

		object = util.showSearchGetParameters("Segmento", "descricao", SegmentoDAO.class, list);

		if (object != null) {
			Segmento obj = (Segmento) object;
			txtCodSegmento.setText(String.valueOf(obj.getCodigo()));
			txtSegmento.setText(obj.getDescricao());
			// entidadeBean.setSegmento(obj);
			entidadeBean.setCodSegmento(obj.getCodigo());
			entidadeBean.setCodSegmentoFk(obj.getCheckDelete());
			txtCodFuncionario.requestFocus();
		}

	}

	/**
	 * EXIBE O FORMULARIO DE BUSCA FUNCIONARIO
	 */
	public void showSearchFuncionario() {

		ObservableList<ComboBoxFilter> list = FXCollections.observableArrayList();

		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("codigo"), 1, "codigo"));
		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("descricao"), 2, "descricao"));
		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("codemp"), 3, "codemp"));

		// util.showSearchView("Funcioanrio", "descricao", FuncionarioDAO.class,
		// list, txtCodRota, txtRota, null, null);

		object = util.showSearchGetParameters("Funcioanrio", "descricao", FuncionarioDAO.class, list);

		if (object != null) {
			Funcionario obj = (Funcionario) object;
			txtCodFuncionario.setText(String.valueOf(obj.getCodigo()));
			txtFuncionario.setText(obj.getDescricao());
			// entidadeBean.setFuncionario(obj);
			entidadeBean.setCodFuncionario(obj.getCodigo());
			entidadeBean.setCodFuncionarioFk(obj.getCheckDelete());
			dateDataCadastro.requestFocus();
		}

	}

	/**
	 * EXIBE O FORMULARIO DE BUSCA SECAO
	 */
	public void showSearchSecao() {

		ObservableList<ComboBoxFilter> list = FXCollections.observableArrayList();

		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("codigo"), 1, "codigo"));
		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("descricao"), 2, "descricao"));
		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("codemp"), 3, "codemp"));

		// util.showSearchView("Seção", "descricao", SecaoDAO.class, list,
		// txtCodSecao, txtSecao, null, txtCodTabelaPreco);

		object = util.showSearchGetParameters("Seção", "descricao", SecaoDAO.class, list);

		if (object != null) {
			Secao obj = (Secao) object;
			txtCodSecao.setText(String.valueOf(obj.getCodigo()));
			txtSecao.setText(obj.getDescricao());
			// entidadeBean.setSecao(obj);
			entidadeBean.setCodSecao(obj.getCodigo());
			entidadeBean.setCodSecaoFk(obj.getCheckDelete());
			txtCodTabelaPreco.requestFocus();
		}

	}

	/**
	 * EXIBE O FORMULARIO DE BUSCA TRANSPORTADORA
	 */
	public void showSearchTransportadora() {

		ObservableList<ComboBoxFilter> list = FXCollections.observableArrayList();

		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("codigo"), 1, "codigo"));
		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("clienteController.lblRazao"), 2, "razao"));
		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("codemp"), 3, "codemp"));

		Util.flagTrasnportadora = true;
		object = util.showSearchGetParameters("Trasnportadora", "razao", FornecedorDAO.class, list);

		if (object != null) {
			Fornecedor obj = (Fornecedor) object;
			txtCodTransportadora.setText(String.valueOf(obj.getCodigo()));
			txtTransportadora.setText(obj.getRazao());
			// entidadeBean.setTransportadora(obj);
			entidadeBean.setCodTransportadora(obj.getCodigo());
			entidadeBean.setCodTransportadoraFk(obj.getCheckDelete());
			cboxTipoFaturamento.requestFocus();
		}

	}

	/**
	 * EXIBE O FORMULARIO DE BUSCA PORTADOR
	 */
	public void showSearchPortador() {

		ObservableList<ComboBoxFilter> list = FXCollections.observableArrayList();

		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("codigo"), 1, "codigo"));
		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("descricao"), 2, "descricao"));
		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("codemp"), 3, "codemp"));

		// util.showSearchView("Portador", "descricao", PortadorDAO.class, list,
		// txtCodPortador, txtPortador, null,
		// txtCodSecao);

		object = util.showSearchGetParameters("Portador", "descricao", PortadorDAO.class, list);

		if (object != null) {
			Portador obj = (Portador) object;
			txtCodPortador.setText(String.valueOf(obj.getCodigo()));
			txtPortador.setText(obj.getDescricao());
			// entidadeBean.setPortador(obj);
			entidadeBean.setCodPortador(obj.getCodigo());
			entidadeBean.setCodPortadorFk(obj.getCheckDelete());
			txtCodSecao.requestFocus();
		}
	}

	/**
	 * EXIBE O FORMULARIO DE BUSCA OPERACAO SAIDA
	 */
	public void showSearchOperacaoSaida() {

		ObservableList<ComboBoxFilter> list = FXCollections.observableArrayList();

		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("codigo"), 1, "codigo"));
		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("descricao"), 2, "descricao"));
		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("codemp"), 3, "codemp"));

		// util.showSearchView("Operção Saida", "descricao",
		// OperacaoSaidaDAO.class, list, txtCodOperacaoSaida,
		// txtOperacaoSaida, null, txtCodPlanoPagamento);

		object = util.showSearchGetParameters("Operção Saida", "descricao", OperacaoSaidaDAO.class, list);

		if (object != null) {
			OperacaoSaida obj = (OperacaoSaida) object;
			txtCodOperacaoSaida.setText(String.valueOf(obj.getCodigo()));
			txtOperacaoSaida.setText(obj.getDescricao());
			// entidadeBean.setOperacaoSaida(obj);
			entidadeBean.setCodOperacaoSaida(obj.getCodigo());
			entidadeBean.setCodOperacaoSaidaFk(obj.getCheckDelete());
			txtCodPlanoPagamento.requestFocus();
		}

	}

	/**
	 * EXIBE O FORMULARIO DE BUSCA PLANO PAGAMENTO
	 */
	public void showSearchPlanoPagamento() {

		ObservableList<ComboBoxFilter> list = FXCollections.observableArrayList();

		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("codigo"), 1, "codigo"));
		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("descricao"), 2, "descricao"));
		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("codemp"), 3, "codemp"));

		// util.showSearchView("Plano Pagamento", "descricao",
		// PlanoPagamentoDAO.class, list, txtCodPlanoPagamento,
		// txtPlanoPagamento, null, txtCodTransportadora);

		object = util.showSearchGetParameters("Plano Pagamento", "descricao", PlanoPagamentoDAO.class, list);

		if (object != null) {
			PlanoPagamento obj = (PlanoPagamento) object;
			txtCodPlanoPagamento.setText(String.valueOf(obj.getCodigo()));
			txtPlanoPagamento.setText(obj.getDescricao());
			// entidadeBean.setPlanoPagamento(obj);
			entidadeBean.setCodPlanoPagamento(obj.getCodigo());
			entidadeBean.setCodPlanoPagamentoFk(obj.getCheckDelete());
			txtCodTransportadora.requestFocus();
		}

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
					
					lblErrorMessage.setText("");
					
					stg.close();
					pBar.setProgress(1);

					entidadeBean = (Cliente) logRet.getObjeto();

					if (entidadeBean != null) {

						if (logRet.getStatus().equals(EnumLogRetornoStatus.REGDUPLICADO))
							util.alertException(logRet.getMsg(), "", false);

						// Validate user - client
						if (DadosGlobais.usuarioLogado.getVdaAltCpfCnpj().equals(1)) {
							lblCpfCnpj.setDisable(false);
							txtCnpj.setDisable(false);
						} else {
							lblCpfCnpj.setDisable(true);
							txtCnpj.setDisable(true);
						}

						if (DadosGlobais.usuarioLogado.getVdaAltVendedorPadrao().equals(1)) {
							lblCodVendedor.setDisable(false);
							txtCodVendedor.setDisable(false);
							txtVendedor.setDisable(false);
						} else {
							lblCodVendedor.setDisable(true);
							txtCodVendedor.setDisable(true);
							txtVendedor.setDisable(true);
						}

						if (DadosGlobais.usuarioLogado.getVdaFaturamentoAltConvenio().equals(1)) {
							lblCodConvenio.setDisable(false);
							txtCodConvenio.setDisable(false);
							txtConvenio.setDisable(false);
						} else {
							lblCodConvenio.setDisable(true);
							txtCodConvenio.setDisable(true);
							txtConvenio.setDisable(true);
						}

						if (DadosGlobais.usuarioLogado.getVdaFaturamentoAltTipoPreco().equals(1)) {
							lblTipoPrecoVenda.setDisable(false);
							for (Toggle t : grpTipoPrecoVenda.getToggles())
								((RadioButton) t).setDisable(false);
						} else {
							lblTipoPrecoVenda.setDisable(true);
							for (Toggle t : grpTipoPrecoVenda.getToggles())
								((RadioButton) t).setDisable(true);
						}

						if (DadosGlobais.usuarioLogado.getVdaFaturamentoAltTipo().equals(1)) {
							lblTipoFaturamento.setDisable(false);
							cboxTipoFaturamento.setDisable(false);
						} else {
							lblTipoFaturamento.setDisable(true);
							cboxTipoFaturamento.setDisable(true);
						}

						if (DadosGlobais.usuarioLogado.getVdaAltLimiteCredito().equals(1)) {
							lblLimiteCredito.setDisable(false);
							txtLimiteCredito.setDisable(false);
						} else {
							lblLimiteCredito.setDisable(true);
							txtLimiteCredito.setDisable(true);
						}

						if (DadosGlobais.usuarioLogado.getVdaAltObsRestritiva().equals(1)) {
							lblObsRestritiva.setDisable(false);
							txtObsRestritiva.setDisable(false);
						} else {
							lblObsRestritiva.setDisable(true);
							txtObsRestritiva.setDisable(true);
						}

						if (DadosGlobais.usuarioLogado.getVdaAltDadosProtesto().equals(1)) {
							lblEnviaDadosCartorio.setDisable(false);
							tgbEnviaDadosCartorio.setDisable(false);
							lblDiasEnviaDadosCartorio.setDisable(false);
							spnDiasEnviaDadosCartorio.setDisable(false);
						} else {
							lblEnviaDadosCartorio.setDisable(true);
							tgbEnviaDadosCartorio.setDisable(true);
							lblDiasEnviaDadosCartorio.setDisable(true);
							spnDiasEnviaDadosCartorio.setDisable(true);
						}

						// Tab Informacoes Gerais
						txtCodigo.setText(entidadeBean.getCodigo().toString());
						Util.setCboxFilterSelecionado(cboxTipoPessoa, entidadeBean.getFlagTipopessoa().toString());
						txtRazao.setText(entidadeBean.getRazao());
						txtFantasia.setText(entidadeBean.getFantasia());
						txtCnpj.setText(entidadeBean.getCpfCnpj());
						txtIncEstad.setText(entidadeBean.getIeRg());
						Util.setCboxFilterSelecionado(cboxIndIncEstad,
								entidadeBean.getFlagIndicadorinscest().toString());
						txtCidade.setText(entidadeBean.getCidade());
						txtCodCidade.setText(entidadeBean.getCodCidade().toString());
						txtUF.setText(entidadeBean.getUf());
						txtEndereco.setText(entidadeBean.getEndereco());
						txtEndNumero.setText(entidadeBean.getEndNumero());
						txtComplemento.setText(entidadeBean.getComplemento());
						txtBairro.setText(entidadeBean.getBairro());
						txtCep.setText(entidadeBean.getCep());
						txtFone.setText(entidadeBean.getFone());
						txtCelular.setText(entidadeBean.getCelular());
						txtFone2.setText(entidadeBean.getFone2());
						txtEmail.setText(entidadeBean.getEmail());
						txtInternetSite.setText(entidadeBean.getInternetSite());
						txtInscProdRural.setText(entidadeBean.getInscProdrural());
						txtLimiteCredito.setText(entidadeBean.getLimiteCredito().toString());						
						Util.setSelectRadioButton(entidadeBean.getOrigemLimiteCredito(), grpLocalLimiteCredito);
						txtNoSuframa.setText(entidadeBean.getSuframa());
						Util.setCboxFilterSelecionado(cboxConsumidorFinal,
								entidadeBean.getFlagConsumidorFinal().toString());
						txtIdentEstrangeiro.setText(entidadeBean.getIdentificacaoEstrangeiro());
						txtContatoPosVenda.setText(entidadeBean.getContatoNome());

						imgImgCli.setImage(null);

						// Image
						if (entidadeBean.getFoto().length > 0) {
							try {
								FileOutputStream f = new FileOutputStream(
										DadosGlobais.folderImagens + entidadeBean.getCheckDelete() + ".png");
								f.write(entidadeBean.getFoto());
								f.close();
							} catch (Exception e) {
								e.printStackTrace();
							}
							File file = new File(DadosGlobais.folderImagens + entidadeBean.getCheckDelete() + ".png");
							imgImgCli.setImage(new Image(file.toURI().toString()));
						} else
							imgImgCli.setImage(new Image("/styles/img/user.png"));

						Util.deleteFile(DadosGlobais.folderImagens + entidadeBean.getCheckDelete() + ".png");

						// Tab Referencias
						txtCodRegiao.setText(entidadeBean.getRegiao() != null
								? entidadeBean.getRegiao().getCodigo().toString() : "");
						txtRegiao.setText(entidadeBean.getRegiaoDescricao());
						txtCodRamoAtividade.setText(entidadeBean.getRamoAtividade() != null
								? entidadeBean.getRamoAtividade().getCodigo().toString() : "");
						txtRamoAtividade.setText(entidadeBean.getRamoAtividadeDescricao());
						txtCodConvenio.setText(entidadeBean.getConvenio() != null
								? entidadeBean.getConvenio().getCodigo().toString() : "");
						txtConvenio.setText(entidadeBean.getConvenioDescricao());
						txtCodRota.setText(
								entidadeBean.getRota() != null ? entidadeBean.getRota().getCodigo().toString() : "");
						txtRota.setText(entidadeBean.getRotaDescricao());
						txtCodVendedor.setText(entidadeBean.getVendedor() != null
								? entidadeBean.getVendedor().getCodigo().toString() : "");
						txtVendedor.setText(entidadeBean.getVendedorDescricao());
						txtCodSegmento.setText(
								entidadeBean.getSecao() != null ? entidadeBean.getSecao().getCodigo().toString() : "");
						txtSegmento.setText(entidadeBean.getSecaoDescricao());
						txtCodFuncionario.setText(entidadeBean.getFuncionario() != null
								? entidadeBean.getFuncionario().getCodigo().toString() : "");
						txtFuncionario.setText(entidadeBean.getFuncionarioDescricao());
						dateDataCadastro.setValue(LocalDate.of(entidadeBean.getDataCadastro().getYear(),
								entidadeBean.getDataCadastro().getMonth(),
								entidadeBean.getDataCadastro().getDayOfMonth()));

						dateDataValidade.setValue(dateDataCadastro.getValue()
								.plusDays(DadosGlobais.empresaLogada.getConfig().getVdaDiasBloqvdacadvencido()));

						// dateDataValidade.setValue(LocalDate.of(entidadeBean.getDataValidade().getYear(),
						// entidadeBean.getDataValidade().getMonth(),
						// entidadeBean.getDataValidade().getDayOfMonth()));
						dateDataAtualizacao.setValue(LocalDate.of(entidadeBean.getDataAtualizacao().getYear(),
								entidadeBean.getDataAtualizacao().getMonth(),
								entidadeBean.getDataAtualizacao().getDayOfMonth()));

						txtObservacoes.setText(entidadeBean.getObservacao());
						txtObsRestritiva.setText(entidadeBean.getObsRestritiva());

						// Tab Faturamento
						cboxTipoFaturamento.getSelectionModel().select(entidadeBean.getTipoFaturamento());
						txtDescontoMax.setText(entidadeBean.getDescontoMaximo().toString());
						Util.setSelectRadioButton(entidadeBean.getTipoPrecoVenda(), grpTipoPrecoVenda);
						tgbPermiteUsoTabelaAtacado.setSelected(entidadeBean.getFlagVendaAtacado() == 1 ? true : false);
						Util.setSelectRadioButton(entidadeBean.getTipoFrete(), grpFreteConta);
						txtCodPortador.setText(entidadeBean.getPortador() != null
								? entidadeBean.getPortador().getCodigo().toString() : "");
						txtPortador.setText(entidadeBean.getPortadorDescricao());
						txtCodSecao.setText(
								entidadeBean.getSecao() != null ? entidadeBean.getSecao().getCodigo().toString() : "");
						txtSecao.setText(entidadeBean.getSecaoDescricao());

						// Tabela de precos
						txtCodOperacaoSaida.setText(entidadeBean.getOperacaoSaida() != null
								? entidadeBean.getOperacaoSaida().getCodigo().toString() : "");
						txtOperacaoSaida.setText(entidadeBean.getOperacaoSaidaDescricao());
						txtCodPlanoPagamento.setText(entidadeBean.getPlanoPagamento() != null
								? entidadeBean.getPlanoPagamento().getCodigo().toString() : "");
						txtPlanoPagamento.setText(entidadeBean.getPlanoPagamentoDescricao());
						txtCodTransportadora.setText(entidadeBean.getTransportadora() != null
								? entidadeBean.getTransportadora().getCodigo().toString() : "");
						txtTransportadora.setText(entidadeBean.getTransportadoraDescricao());

						// Tab Dados Entrega
						listClienteEndEntrega.clear();
						tbViewDadosEntrega.getItems().clear();

						if (!entidadeBean.getClienteEndEntregas().isEmpty()) {

							for (ClienteEndEntrega ce : entidadeBean.getClienteEndEntregas())
								if (ce.getFlagAtivo().equals(1))
									tbViewDadosEntrega.getItems().add(ce);

							if (!tbViewDadosEntrega.getItems().isEmpty())
								for (int i = 0; i < tbViewDadosEntrega.getItems().size(); i++)
									listClienteEndEntrega.add(tbViewDadosEntrega.getItems().get(i).clone());

						}

						// Tab Dados Bancarios
						txtBanco.setText(entidadeBean.getBanco1Descricao());
						txtAgencia.setText(entidadeBean.getBancoAgencia());
						txtConta.setText(entidadeBean.getBancoConta());
						txtDestinatario.setText(entidadeBean.getBancoDestinatario());
						txtNoCpfCnpj.setText(entidadeBean.getBancoCpfCnpj());
						tgbEnviaDadosCartorio
								.setSelected(entidadeBean.getFlagCobrancaCartorio().equals(1) ? true : false);
						spnDiasEnviaDadosCartorio.getValueFactory().setValue(entidadeBean.getCobrancaCartorioDias());
						if (!tgbEnviaDadosCartorio.isSelected()){
							lblDiasEnviaDadosCartorio.setDisable(true);
							spnDiasEnviaDadosCartorio.setDisable(true);							
						}

						txtRazao.selectEnd();

						if (flagDuplica) {
							entidadeBean = new Cliente();
							txtCodigo.setText("+1");
							btnInsert.setDisable(true);
							txtCodigo.setDisable(true);
							btnCancel.setDisable(false);
							btnSave.setDisable(false);
							txtRazao.requestFocus();
						} else {

							txtCodigo.setText(String.valueOf(entidadeBean.getCodigo()));

							if (!flagLastRegistro && !flagDuplica) {

								btnInsert.setDisable(true);
								txtCodigo.setDisable(true);
								btnCancel.setDisable(false);
								btnSave.setDisable(false);
								txtRazao.requestFocus();

							}

							if (flagLastRegistro && !flagDuplica) {

								btnInsert.setDisable(false);
								btnCancel.setDisable(true);
								btnSave.setDisable(true);
								txtCodigo.setDisable(false);
								txtCodigo.requestFocus();
							}
						}

					} 
					else 
					{

						//--START SHOW ERROR---
						lblErrorMessage.setText(DadosGlobais.resourceBundle.getString("alertErrorCodigoNull"));
						//util.showAlert(DadosGlobais.resourceBundle.getString("alertErrorCodigoNull"), "error");
						btnSave.setDisable(true);
						btnCancel.setDisable(false);
						btnInsert.setDisable(false);
						txtCodigo.requestFocus();
						resetForm();
					}

				}

				@Override
				protected void failed() {
					stg.close();
					util.tratamentoExcecao(exceptionProperty().getValue().toString(),
							"[ ClienteController.loadByID() ]");
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
			stg = ProgressBarForm.showProgressBar(UsuarioController.class, TarefaRefresh,
					DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
			pBar.setProgress(-1);

		} 
		else 
		{
			lblErrorMessage.setText(DadosGlobais.resourceBundle.getString("alertErrorCodigoNull"));
			resetForm();
			txtCodigo.requestFocus();
		}
	}

	// public void loadEnderecoByID() {
	//
	// // if ((!flagLastRegistro && codigo > 0) || (flagLastRegistro)) {
	//
	// Task<String> TarefaLoad = new Task<String>() {
	// LogRetorno logRet = new LogRetorno();
	// ClienteEndEntrega cliEntrega = new ClienteEndEntrega();
	// ClienteEndEntregaDAO cliEntregaDAO = new ClienteEndEntregaDAO();
	//
	// @Override
	// protected String call() throws Exception {
	//
	// logRet =
	// cliEntregaDAO.getById(tbViewDadosEntrega.getSelectionModel().getSelectedItem().getCliente(),
	// tbViewDadosEntrega.getSelectionModel().getSelectedItem().getCheckDelete());
	//
	// return "-";
	// }
	//
	// @Override
	// protected void succeeded() {
	// stg.close();
	// pBar.setProgress(1);
	//
	// cliEntrega = (ClienteEndEntrega) logRet.getObjeto();
	//
	// if (cliEntrega != null) {
	//
	// if (logRet.getStatus().equals(EnumLogRetornoStatus.REGDUPLICADO))
	// util.alertException(logRet.getMsg(), "", false);
	//
	// txtEndereco.setText(cliEntrega.getEndereco());
	// txtNumeroEntrega.setText(cliEntrega.getEndNumero());
	// txtComplementoEntrega.setText(cliEntrega.getComplemento());
	// txtBairroEntrega.setText(cliEntrega.getBairro());
	// txtCodCidadeEntrega.setText(String.valueOf(cliEntrega.getCodCidade()));
	// txtCidadeEntrega.setText(cliEntrega.getCidade());
	// txtUfEntrega.setText(cliEntrega.getUf());
	// txtCepEntrega.setText(cliEntrega.getCep());
	// txtTelefoneEntrega.setText(cliEntrega.getFone());
	// txtReferenciaEntrega.setText(cliEntrega.getFone());
	// txtEnderecoEntrega.selectEnd();
	//
	// alteBtnAddEndereco(!flagInsertUpdate);
	// flagInsertUpdate = false;
	//
	//
	// } else {
	//
	// util.showAlert(DadosGlobais.resourceBundle.getString("alertErrorCodigoNull"),
	// "error");
	// txtEnderecoEntrega.requestFocus();
	// }
	//
	// }
	//
	// @Override
	// protected void failed() {
	// stg.close();
	// util.tratamentoExcecao(exceptionProperty().getValue().toString(),
	// "[ ClienteController.loadEnderecoByID() ]");
	// pBar.setProgress(0);
	// }
	//
	// @Override
	// protected void cancelled() {
	// tbView.getItems().clear();
	// pBar.setProgress(0);
	// super.cancelled();
	// }
	// };
	// Thread t = new Thread(TarefaLoad);
	// t.setDaemon(true);
	// t.start();
	// stg = ProgressBarForm.showProgressBar(ClienteController.class,
	// TarefaLoad,
	// DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
	// pBar.setProgress(-1);
	//
	// // } else {
	// //
	// util.showAlert(DadosGlobais.resourceBundle.getString("alertErrorCodigoNull"),
	// // "error");
	// // resetForm();
	// // txtCodigo.requestFocus();
	// // }
	// }

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
						"[ ClienteController.updateTbView() ]");
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
						"[ ClienteController.tarefaRefresh() ]");
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
		stg = ProgressBarForm.showProgressBar(UsuarioController.class, TarefaRefresh,
				DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
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
	public void searchCidade(int valorBusca) {

		Task<String> TarefaRefresh = new Task<String>() {

			LogRetorno logRet = new LogRetorno();
			CidadeDAO cidDao = new CidadeDAO();

			@Override
			protected String call() throws Exception {

				logRet = cidDao.getById(valorBusca);
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				//pBar.setProgress(1);
				if (logRet.getStatus().equals(EnumLogRetornoStatus.ERRO)) {
					Util.setStyleError(true, txtCodCidade);
					txtCodCidade.clear();
					// txtCidade.clear();
				} else {
					if (logRet.getStatus().equals(EnumLogRetornoStatus.REGDUPLICADO))
						util.alertException(logRet.getMsg(), "", false);
					if (logRet.getStatus().equals(EnumLogRetornoStatus.SUCESSO)) {
						txtCidade.setText(((Cidade) logRet.getObjeto()).getDescricao());
						txtUF.setText(((Cidade) logRet.getObjeto()).getUfSigla());
					}
				}
			}

			@Override
			protected void failed() {
				stg.close();
				util.tratamentoExcecao(exceptionProperty().getValue().toString(),
						"[ ClienteController.searchCidade() ]");
				//pBar.setProgress(0);
			}

			@Override
			protected void cancelled() {
				//tbView.getItems().clear();
				//pBar.setProgress(0);
				super.cancelled();
			}

		};
		Thread t = new Thread(TarefaRefresh);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(UsuarioController.class, TarefaRefresh,
				DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
		//pBar.setProgress(-1);

	}

	public void searchCidadeEntrega(int valorBusca) {

		Task<String> TarefaRefresh = new Task<String>() {

			LogRetorno logRet = new LogRetorno();
			CidadeDAO cidDao = new CidadeDAO();

			@Override
			protected String call() throws Exception {

				logRet = cidDao.getById(valorBusca);
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				//pBar.setProgress(1);
				if (logRet.getStatus().equals(EnumLogRetornoStatus.ERRO)) {
					Util.setStyleError(true, txtCodCidade);
					// txtCodCidade.clear();
					txtCidadeEntrega.clear();
					txtUfEntrega.clear();
				} else {
					if (logRet.getStatus().equals(EnumLogRetornoStatus.REGDUPLICADO))
						util.alertException(logRet.getMsg(), "", false);
					if (logRet.getStatus().equals(EnumLogRetornoStatus.SUCESSO)) {
						txtCidadeEntrega.setText(((Cidade) logRet.getObjeto()).getDescricao());
						txtUfEntrega.setText(((Cidade) logRet.getObjeto()).getUfSigla());
					}
				}
			}

			@Override
			protected void failed() {
				stg.close();
				util.tratamentoExcecao(exceptionProperty().getValue().toString(),
						"[ ClienteController.searchCidade() ]");
				//pBar.setProgress(0);
			}

			@Override
			protected void cancelled() {
				//tbView.getItems().clear();
				//pBar.setProgress(0);
				super.cancelled();
			}

		};
		Thread t = new Thread(TarefaRefresh);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(UsuarioController.class, TarefaRefresh,
				DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
		//pBar.setProgress(-1);

	}

	/**
	 * Method to search Regiao by Id
	 * 
	 * @param valorBusca
	 *            Id Regiao
	 */
	public void searchRegiao(int valorBusca) {

		RegiaoDAO regiaoDAO = new RegiaoDAO();

		Task<String> TarefaSearch = new Task<String>() {

			LogRetorno logRet = new LogRetorno();

			@Override
			protected String call() throws Exception {

				logRet = regiaoDAO.getById(valorBusca);
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				//pBar.setProgress(1);
				if (logRet.getStatus().equals(EnumLogRetornoStatus.ERRO)) {
					Util.setStyleError(true, txtCodRegiao);
					txtCodRegiao.clear();
					txtRegiao.clear();
				} else {
					if (logRet.getStatus().equals(EnumLogRetornoStatus.REGDUPLICADO))
						util.alertException(logRet.getMsg(), "", false);
					if (logRet.getStatus().equals(EnumLogRetornoStatus.SUCESSO)) {
						txtRegiao.setText(((Regiao) logRet.getObjeto()).getDescricao());
						// entidadeBean.setRegiao((Regiao) logRet.getObjeto());
						entidadeBean.setCodRegiao(((Regiao) logRet.getObjeto()).getCodigo());
						entidadeBean.setCodRegiaoFk(((Regiao) logRet.getObjeto()).getCheckDelete());

					}
				}
			}

			@Override
			protected void failed() {
				stg.close();
				util.tratamentoExcecao(exceptionProperty().getValue().toString(),
						"[ ClienteController.searchRegiao() ]");
				//pBar.setProgress(0);
			}

			@Override
			protected void cancelled() {
				//tbView.getItems().clear();
				//pBar.setProgress(0);
				super.cancelled();
			}

		};
		Thread t = new Thread(TarefaSearch);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(ClienteController.class, TarefaSearch,
				DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
		//pBar.setProgress(-1);

	}

	/**
	 * Method to search Ramo Atividade by Id
	 * 
	 * @param valorBusca
	 *            Id RamoAtividade
	 */
	public void searchRamoAtividade(int valorBusca) {

		RamoAtividadeDAO ramoAtividadeDAO = new RamoAtividadeDAO();

		Task<String> TarefaSearch = new Task<String>() {

			LogRetorno logRet = new LogRetorno();

			@Override
			protected String call() throws Exception {

				logRet = ramoAtividadeDAO.getById(valorBusca);
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				//pBar.setProgress(1);
				if (logRet.getStatus().equals(EnumLogRetornoStatus.ERRO)) {
					Util.setStyleError(true, txtCodRamoAtividade);
					txtCodRamoAtividade.clear();
					txtRamoAtividade.clear();
				} else {
					if (logRet.getStatus().equals(EnumLogRetornoStatus.REGDUPLICADO))
						util.alertException(logRet.getMsg(), "", false);
					if (logRet.getStatus().equals(EnumLogRetornoStatus.SUCESSO)) {
						txtRamoAtividade.setText(((RamoAtividade) logRet.getObjeto()).getDescricao());
						// entidadeBean.setRamoAtividade((RamoAtividade)
						// logRet.getObjeto());
						entidadeBean.setCodRamoAtividade(((RamoAtividade) logRet.getObjeto()).getCodigo());
						entidadeBean.setCodRamoAtividadeFk(((RamoAtividade) logRet.getObjeto()).getCheckDelete());

					}
				}
			}

			@Override
			protected void failed() {
				stg.close();
				util.tratamentoExcecao(exceptionProperty().getValue().toString(),
						"[ ClienteController.searchRamoAtividade() ]");
				//pBar.setProgress(0);
			}

			@Override
			protected void cancelled() {
				//tbView.getItems().clear();
				//pBar.setProgress(0);
				super.cancelled();
			}

		};
		Thread t = new Thread(TarefaSearch);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(ClienteController.class, TarefaSearch,
				DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
		//pBar.setProgress(-1);

	}

	/**
	 * Method to search Convenio by Id
	 * 
	 * @param valorBusca
	 *            Id Convenio
	 */
	public void searchConvenio(int valorBusca) {

		ConvenioDAO convenioDAO = new ConvenioDAO();

		Task<String> TarefaSearch = new Task<String>() {

			LogRetorno logRet = new LogRetorno();

			@Override
			protected String call() throws Exception {

				logRet = convenioDAO.getById(valorBusca);
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				//pBar.setProgress(1);
				if (logRet.getStatus().equals(EnumLogRetornoStatus.ERRO)) {
					Util.setStyleError(true, txtCodConvenio);
					txtCodConvenio.clear();
					txtConvenio.clear();
				} else {
					if (logRet.getStatus().equals(EnumLogRetornoStatus.REGDUPLICADO))
						util.alertException(logRet.getMsg(), "", false);
					if (logRet.getStatus().equals(EnumLogRetornoStatus.SUCESSO)) {
						txtConvenio.setText(((Convenio) logRet.getObjeto()).getDescricao());
						
						entidadeBean.setCodConvenio(((Convenio) logRet.getObjeto()).getCodigo());
						entidadeBean.setCodConvenioFk(((Convenio) logRet.getObjeto()).getCheckDelete());

					}
				}
			}

			@Override
			protected void failed() {
				stg.close();
				util.tratamentoExcecao(exceptionProperty().getValue().toString(),
						"[ ClienteController.searchConvenio() ]");
				//pBar.setProgress(0);
			}

			@Override
			protected void cancelled() {
				//tbView.getItems().clear();
				//pBar.setProgress(0);
				super.cancelled();
			}

		};
		Thread t = new Thread(TarefaSearch);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(ClienteController.class, TarefaSearch,
				DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
		//pBar.setProgress(-1);

	}

	/**
	 * Method to search Rota by Id
	 * 
	 * @param valorBusca
	 *            Id Rota
	 */
	public void searchRota(int valorBusca) {

		RotaDAO rotaDAO = new RotaDAO();

		Task<String> TarefaSearch = new Task<String>() {

			LogRetorno logRet = new LogRetorno();

			@Override
			protected String call() throws Exception {

				logRet = rotaDAO.getById(valorBusca);
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				//pBar.setProgress(1);
				if (logRet.getStatus().equals(EnumLogRetornoStatus.ERRO)) {
					Util.setStyleError(true, txtCodRota);
					txtCodRota.clear();
					txtRota.clear();
				} else {
					if (logRet.getStatus().equals(EnumLogRetornoStatus.REGDUPLICADO))
						util.alertException(logRet.getMsg(), "", false);
					if (logRet.getStatus().equals(EnumLogRetornoStatus.SUCESSO)) {
						txtRota.setText(((Rota) logRet.getObjeto()).getDescricao());
						
						entidadeBean.setCodRota(((Rota) logRet.getObjeto()).getCodigo());
						entidadeBean.setCodRotaFk(((Rota) logRet.getObjeto()).getCheckDelete());

					}
				}
			}

			@Override
			protected void failed() {
				stg.close();
				util.tratamentoExcecao(exceptionProperty().getValue().toString(), "[ ClienteController.searchRota() ]");
				//pBar.setProgress(0);
			}

			@Override
			protected void cancelled() {
				//tbView.getItems().clear();
				//pBar.setProgress(0);
				super.cancelled();
			}

		};
		Thread t = new Thread(TarefaSearch);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(ClienteController.class, TarefaSearch,
				DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
		//pBar.setProgress(-1);

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
				//pBar.setProgress(1);
				if (logRet.getStatus().equals(EnumLogRetornoStatus.ERRO)) {
					Util.setStyleError(true, txtCodVendedor);
					txtCodVendedor.clear();
					txtVendedor.clear();
				} else {
					if (logRet.getStatus().equals(EnumLogRetornoStatus.REGDUPLICADO))
						util.alertException(logRet.getMsg(), "", false);
					if (logRet.getStatus().equals(EnumLogRetornoStatus.SUCESSO)) {
						txtVendedor.setText(((Funcionario) logRet.getObjeto()).getDescricao());
						
						entidadeBean.setCodVendedor(((Funcionario) logRet.getObjeto()).getCodigo());
						entidadeBean.setCodVendedorFk(((Funcionario) logRet.getObjeto()).getCheckDelete());

					}
				}
			}

			@Override
			protected void failed() {
				stg.close();
				util.tratamentoExcecao(exceptionProperty().getValue().toString(), "[ ClienteController.searchVendedor() ]");
				//pBar.setProgress(0);
			}

			@Override
			protected void cancelled() {
				//tbView.getItems().clear();
				//pBar.setProgress(0);
				super.cancelled();
			}

		};
		Thread t = new Thread(TarefaSearch);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(ClienteController.class, TarefaSearch,
				DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
		//pBar.setProgress(-1);

	}

	/**
	 * Method to search Segmento by Id
	 * 
	 * @param valorBusca
	 *            Id Segmento
	 */
	public void searchSegmento(int valorBusca) {

		SegmentoDAO segmentoDAO = new SegmentoDAO();

		Task<String> TarefaSearch = new Task<String>() {

			LogRetorno logRet = new LogRetorno();

			@Override
			protected String call() throws Exception {

				logRet = segmentoDAO.getById(valorBusca);
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				//pBar.setProgress(1);
				if (logRet.getStatus().equals(EnumLogRetornoStatus.ERRO)) {
					Util.setStyleError(true, txtCodSegmento);
					txtCodSegmento.clear();
					txtSegmento.clear();
				} else {
					if (logRet.getStatus().equals(EnumLogRetornoStatus.REGDUPLICADO))
						util.alertException(logRet.getMsg(), "", false);
					if (logRet.getStatus().equals(EnumLogRetornoStatus.SUCESSO)) {
						txtSegmento.setText(((Segmento) logRet.getObjeto()).getDescricao());
						// entidadeBean.setSegmento((Segmento)
						// logRet.getObjeto());
						entidadeBean.setCodSegmento(((Segmento) logRet.getObjeto()).getCodigo());
						entidadeBean.setCodSegmentoFk(((Segmento) logRet.getObjeto()).getCheckDelete());

					}
				}
			}

			@Override
			protected void failed() {
				stg.close();
				util.tratamentoExcecao(exceptionProperty().getValue().toString(),
						"[ ClienteController.searchSegmento() ]");
				//pBar.setProgress(0);
			}

			@Override
			protected void cancelled() {
				//tbView.getItems().clear();
				//pBar.setProgress(0);
				super.cancelled();
			}

		};
		Thread t = new Thread(TarefaSearch);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(ClienteController.class, TarefaSearch,
				DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
		//pBar.setProgress(-1);

	}

	/**
	 * Method to search Funcionario by Id
	 * 
	 * @param valorBusca
	 *            Id Funcionario
	 */
	public void searchFuncionario(int valorBusca) {

		FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

		Task<String> TarefaSearch = new Task<String>() {

			LogRetorno logRet = new LogRetorno();

			@Override
			protected String call() throws Exception {

				logRet = funcionarioDAO.getById(valorBusca);
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				//pBar.setProgress(1);
				if (logRet.getStatus().equals(EnumLogRetornoStatus.ERRO)) {
					Util.setStyleError(true, txtCodFuncionario);
					txtCodFuncionario.clear();
					txtFuncionario.clear();
				} else {
					if (logRet.getStatus().equals(EnumLogRetornoStatus.REGDUPLICADO))
						util.alertException(logRet.getMsg(), "", false);
					if (logRet.getStatus().equals(EnumLogRetornoStatus.SUCESSO)) {
						txtFuncionario.setText(((Funcionario) logRet.getObjeto()).getDescricao());
						// entidadeBean.setFuncionario((Funcionario)
						// logRet.getObjeto());
						entidadeBean.setCodFuncionario(((Funcionario) logRet.getObjeto()).getCodigo());
						entidadeBean.setCodFuncionarioFk(((Funcionario) logRet.getObjeto()).getCheckDelete());

					}
				}
			}

			@Override
			protected void failed() {
				stg.close();
				util.tratamentoExcecao(exceptionProperty().getValue().toString(),
						"[ ClienteController.searchFuncionario() ]");
				//pBar.setProgress(0);
			}

			@Override
			protected void cancelled() {
				//tbView.getItems().clear();
				//pBar.setProgress(0);
				super.cancelled();
			}

		};
		Thread t = new Thread(TarefaSearch);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(ClienteController.class, TarefaSearch,
				DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
		//pBar.setProgress(-1);

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
				//pBar.setProgress(1);
				if (logRet.getStatus().equals(EnumLogRetornoStatus.ERRO)) {
					Util.setStyleError(true, txtCodPortador);
					txtCodPortador.clear();
					txtPortador.clear();
				} else {
					if (logRet.getStatus().equals(EnumLogRetornoStatus.REGDUPLICADO))
						util.alertException(logRet.getMsg(), "", false);
					if (logRet.getStatus().equals(EnumLogRetornoStatus.SUCESSO)) {
						txtPortador.setText(((Portador) logRet.getObjeto()).getDescricao());
						// entidadeBean.setPortador((Portador)
						// logRet.getObjeto());
						entidadeBean.setCodPortador(((Portador) logRet.getObjeto()).getCodigo());
						entidadeBean.setCodPortadorFk(((Portador) logRet.getObjeto()).getCheckDelete());

					}
				}
			}

			@Override
			protected void failed() {
				stg.close();
				util.tratamentoExcecao(exceptionProperty().getValue().toString(),
						"[ ClienteController.searchPortador() ]");
				//pBar.setProgress(0);
			}

			@Override
			protected void cancelled() {
				//tbView.getItems().clear();
				//pBar.setProgress(0);
				super.cancelled();
			}

		};
		Thread t = new Thread(TarefaSearch);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(ClienteController.class, TarefaSearch,
				DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
		pBar.setProgress(-1);

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
				//pBar.setProgress(1);
				if (logRet.getStatus().equals(EnumLogRetornoStatus.ERRO)) {
					Util.setStyleError(true, txtCodSecao);
					txtCodSecao.clear();
					txtSecao.clear();
				} else {
					if (logRet.getStatus().equals(EnumLogRetornoStatus.REGDUPLICADO))
						util.alertException(logRet.getMsg(), "", false);
					if (logRet.getStatus().equals(EnumLogRetornoStatus.SUCESSO)) {
						txtSecao.setText(((Secao) logRet.getObjeto()).getDescricao());
						// entidadeBean.setSecao((Secao) logRet.getObjeto());
						entidadeBean.setCodSecao(((Secao) logRet.getObjeto()).getCodigo());
						entidadeBean.setCodSecaoFk(((Secao) logRet.getObjeto()).getCheckDelete());

					}
				}
			}

			@Override
			protected void failed() {
				stg.close();
				util.tratamentoExcecao(exceptionProperty().getValue().toString(),
						"[ ClienteController.searchSecao() ]");
				//pBar.setProgress(0);
			}

			@Override
			protected void cancelled() {
				//tbView.getItems().clear();
				//pBar.setProgress(0);
				super.cancelled();
			}

		};
		Thread t = new Thread(TarefaSearch);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(ClienteController.class, TarefaSearch,
				DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
		//pBar.setProgress(-1);

	}

	/**
	 * Method to search Operacao Saida by Id
	 * 
	 * @param valorBusca
	 *            Id Operacao Saida
	 */
	public void searchOperacaoSaida(int valorBusca) {

		OperacaoSaidaDAO operacaoSaidaDAO = new OperacaoSaidaDAO();

		Task<String> TarefaSearch = new Task<String>() {

			LogRetorno logRet = new LogRetorno();

			@Override
			protected String call() throws Exception {

				logRet = operacaoSaidaDAO.getById(valorBusca);
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				//pBar.setProgress(1);
				if (logRet.getStatus().equals(EnumLogRetornoStatus.ERRO)) {
					Util.setStyleError(true, txtCodOperacaoSaida);
					txtCodOperacaoSaida.clear();
					txtOperacaoSaida.clear();
				} else {
					if (logRet.getStatus().equals(EnumLogRetornoStatus.REGDUPLICADO))
						util.alertException(logRet.getMsg(), "", false);
					if (logRet.getStatus().equals(EnumLogRetornoStatus.SUCESSO)) {
						txtOperacaoSaida.setText(((OperacaoSaida) logRet.getObjeto()).getDescricao());
						// entidadeBean.setOperacaoSaida((OperacaoSaida)
						// logRet.getObjeto());
						entidadeBean.setCodOperacaoSaida(((OperacaoSaida) logRet.getObjeto()).getCodigo());
						entidadeBean.setCodOperacaoSaidaFk(((OperacaoSaida) logRet.getObjeto()).getCheckDelete());

					}
				}
			}

			@Override
			protected void failed() {
				stg.close();
				util.tratamentoExcecao(exceptionProperty().getValue().toString(),
						"[ ClienteController.searchOperacaoSaida() ]");
				//pBar.setProgress(0);
			}

			@Override
			protected void cancelled() {
				//tbView.getItems().clear();
				//pBar.setProgress(0);
				super.cancelled();
			}

		};
		Thread t = new Thread(TarefaSearch);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(ClienteController.class, TarefaSearch,
				DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
		//pBar.setProgress(-1);

	}

	/**
	 * Method to search Plano Pagamento by Id
	 * 
	 * @param valorBusca
	 *            Id Plano Pagamento
	 */
	public void searchPlanoPagamento(int valorBusca) {

		PlanoPagamentoDAO planoPagamentoDAO = new PlanoPagamentoDAO();

		Task<String> TarefaSearch = new Task<String>() {

			LogRetorno logRet = new LogRetorno();

			@Override
			protected String call() throws Exception {

				logRet = planoPagamentoDAO.getById(valorBusca);
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				//pBar.setProgress(1);
				if (logRet.getStatus().equals(EnumLogRetornoStatus.ERRO)) {
					Util.setStyleError(true, txtCodPlanoPagamento);
					txtCodPlanoPagamento.clear();
					txtPlanoPagamento.clear();
				} else {
					if (logRet.getStatus().equals(EnumLogRetornoStatus.REGDUPLICADO))
						util.alertException(logRet.getMsg(), "", false);
					if (logRet.getStatus().equals(EnumLogRetornoStatus.SUCESSO)) {
						txtPlanoPagamento.setText(((PlanoPagamento) logRet.getObjeto()).getDescricao());
						// entidadeBean.setPlanoPagamento((PlanoPagamento)
						// logRet.getObjeto());
						entidadeBean.setCodPlanoPagamento(((PlanoPagamento) logRet.getObjeto()).getCodigo());
						entidadeBean.setCodPlanoPagamentoFk(((PlanoPagamento) logRet.getObjeto()).getCheckDelete());

					}
				}
			}

			@Override
			protected void failed() {
				stg.close();
				util.tratamentoExcecao(exceptionProperty().getValue().toString(),
						"[ ClienteController.searchPlanoPagamento() ]");
				//pBar.setProgress(0);
			}

			@Override
			protected void cancelled() {
				//tbView.getItems().clear();
				//pBar.setProgress(0);
				super.cancelled();
			}

		};
		Thread t = new Thread(TarefaSearch);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(ClienteController.class, TarefaSearch,
				DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
		//pBar.setProgress(-1);

	}

	/**
	 * Method to search Transportadora by Id
	 * 
	 * @param valorBusca
	 *            Id Transportadora
	 */
	public void searchTransportadora(int valorBusca) {

		FornecedorDAO fornecedorDAO = new FornecedorDAO();

		Task<String> TarefaSearch = new Task<String>() {

			LogRetorno logRet = new LogRetorno();

			@Override
			protected String call() throws Exception {

				logRet = fornecedorDAO.getTransportadoraById(valorBusca);
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				//pBar.setProgress(1);
				if (logRet.getStatus().equals(EnumLogRetornoStatus.ERRO)) {
					Util.setStyleError(true, txtCodTransportadora);
					txtCodTransportadora.clear();
					txtTransportadora.clear();
				} else {
					if (logRet.getStatus().equals(EnumLogRetornoStatus.REGDUPLICADO))
						util.alertException(logRet.getMsg(), "", false);
					if (logRet.getStatus().equals(EnumLogRetornoStatus.SUCESSO)) {
						txtTransportadora.setText(((Fornecedor) logRet.getObjeto()).getRazao());
						// entidadeBean.setPlanoPagamento((PlanoPagamento)
						// logRet.getObjeto());
						entidadeBean.setCodTransportadora(((Fornecedor) logRet.getObjeto()).getCodigo());
						entidadeBean.setCodTransportadoraFk(((Fornecedor) logRet.getObjeto()).getCheckDelete());

					}
				}
			}

			@Override
			protected void failed() {
				stg.close();
				util.tratamentoExcecao(exceptionProperty().getValue().toString(),
						"[ ClienteController.searchPlanoPagamento() ]");
				//pBar.setProgress(0);
			}

			@Override
			protected void cancelled() {
				//tbView.getItems().clear();
				//pBar.setProgress(0);
				super.cancelled();
			}

		};
		Thread t = new Thread(TarefaSearch);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(ClienteController.class, TarefaSearch,
				DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
		//pBar.setProgress(-1);

	}

	/**
	 * RESETA OS CAMPOS CONTIDOS NO FORMULARIO
	 */
	public void resetForm() {

		// Tab Informacoes Gerais
		cboxTipoPessoa.getSelectionModel().selectLast();
		cboxIndIncEstad.getSelectionModel().selectFirst();
		cboxConsumidorFinal.getSelectionModel().selectFirst();
		rdbLocalLimiteCredito0.setSelected(true);
		imgImgCli.setImage(new Image("/styles/img/user.png"));

		Util.limpar(txtCodigo, txtRazao, txtFantasia, txtCnpj, txtIncEstad, txtCodCidade, txtCidade, txtUF, txtEndereco,
				txtEndNumero, txtComplemento, txtBairro, txtCep, txtFone, txtCelular, txtFone2, txtEmail,
				txtInternetSite, txtInscProdRural, txtLimiteCredito, txtNoSuframa, txtIdentEstrangeiro,
				txtContatoPosVenda);

		Util.setDefaultStyle(txtCodigo, txtRazao, txtFantasia, txtCnpj, txtIncEstad, txtCodCidade, txtCidade, txtUF,
				txtEndereco, txtEndNumero, txtComplemento, txtBairro, txtCep, txtFone, txtCelular, txtFone2, txtEmail,
				txtInternetSite, txtInscProdRural, txtLimiteCredito, txtNoSuframa, txtIdentEstrangeiro,
				txtContatoPosVenda);

		// Tab Referencias
		Util.limpar(txtCodRegiao, txtRegiao, txtCodRamoAtividade, txtRamoAtividade, txtCodConvenio, txtConvenio,
				txtCodRota, txtRota, txtCodVendedor, txtVendedor, txtCodSegmento, txtSegmento, txtCodFuncionario,
				txtFuncionario);

		Util.setDefaultStyle(txtCodRegiao, txtRegiao, txtCodRamoAtividade, txtRamoAtividade, txtCodConvenio,
				txtConvenio, txtCodRota, txtRota, txtCodVendedor, txtVendedor, txtCodSegmento, txtSegmento,
				txtCodFuncionario, txtFuncionario);

		// cboxTipoFaturamento.getSelectionModel().selectFirst();
		dateDataCadastro.setValue(today);
		dateDataValidade.setValue(today);
		dateDataAtualizacao.setValue(today);

		// Tab Faturamento
		cboxTipoFaturamento.getSelectionModel().selectFirst();

		Util.limpar(txtDescontoMax, txtCodPortador, txtPortador, txtCodSecao, txtSecao, txtCodTabelaPreco,
				txtTabelaPreco, txtCodOperacaoSaida, txtOperacaoSaida, txtCodPlanoPagamento, txtPlanoPagamento,
				txtCodTransportadora, txtTransportadora);

		rdbTipoPrecoVenda0.setSelected(true);
		rdbFreteConta0.setSelected(true);

		tgbPermiteUsoTabelaAtacado.setSelected(false);

		tgbEnviaDadosCartorio.setSelected(false);

		tgbEnviaDadosCartorio.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if (newValue) {
					lblDiasEnviaDadosCartorio.setDisable(false);
					spnDiasEnviaDadosCartorio.setDisable(false);
				} else {
					lblDiasEnviaDadosCartorio.setDisable(true);
					spnDiasEnviaDadosCartorio.setDisable(true);
				}

			}
		});

		// Tab Dados Entrega
		Util.limpar(txtEnderecoEntrega, txtNumeroEntrega, txtComplementoEntrega, txtBairroEntrega, txtCodCidadeEntrega,
				txtCidadeEntrega, txtUfEntrega, txtCepEntrega, txtTelefoneEntrega, txtReferenciaEntrega);

		Util.setDefaultStyle(txtEnderecoEntrega, txtNumeroEntrega, txtComplementoEntrega, txtBairroEntrega,
				txtCodCidadeEntrega, txtCidadeEntrega, txtUfEntrega, txtCepEntrega, txtTelefoneEntrega,
				txtReferenciaEntrega);

		tbViewDadosEntrega.getItems().clear();

		// Tab Dados Banacarios
		Util.limpar(txtBanco, txtAgencia, txtConta, txtDestinatario, txtNoCpfCnpj);

		Util.setDefaultStyle(txtBanco, txtAgencia, txtConta, txtDestinatario, txtNoCpfCnpj);

		tabPanePrincipal.getSelectionModel().selectFirst();

		webCnpj.getEngine().load("");

	}

	/**
	 * EXIBE O FORMULARIO DE BUSCA DE CIDADE
	 */
	public void showSearchCidade() {

		ObservableList<ComboBoxFilter> list = FXCollections.observableArrayList();

		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("codigo"), 1, "codigo"));
		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("descricao"), 2, "descricao"));
		list.add(new ComboBoxFilter("UF", 3, "ufSigla"));

		util.showSearchView("Cidade", "descricao", CidadeDAO.class, list, txtCodCidade, txtCidade, txtUF, txtEndereco);

	}

	/**
	 * EXIBE O FORMULARIO DE BUSCA DE CIDADE
	 */
	public void showSearchCidadeEndereco() {

		ObservableList<ComboBoxFilter> list = FXCollections.observableArrayList();

		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("codigo"), 1, "codigo"));
		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("descricao"), 2, "descricao"));
		list.add(new ComboBoxFilter("UF", 3, "ufSigla"));

		util.showSearchView("Cidade", "descricao", CidadeDAO.class, list, txtCodCidadeEntrega, txtCidadeEntrega,
				txtUfEntrega, txtCepEntrega);

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
		lblcboxFlagAtivo.setText(DadosGlobais.resourceBundle.getString("lblcboxFlagAtivo"));
		lblTotalLinhas.setText(DadosGlobais.resourceBundle.getString("lblTotalLinhas"));

		lbTitleFormCad.setText(DadosGlobais.resourceBundle.getString("lblTitleFormCad") + " "
				+ DadosGlobais.resourceBundle.getString("miCadCliente"));

		// Tab Informaçôes Gerais
		tabInfGerais.setText(DadosGlobais.resourceBundle.getString("clienteController.tabInfGerais"));
		lblCodigo.setText(DadosGlobais.resourceBundle.getString("clienteController.lblCodigo"));
		lblTipoPessoa.setText(DadosGlobais.resourceBundle.getString("clienteController.lblTipoPessoa"));
		lblRazao.setText(DadosGlobais.resourceBundle.getString("clienteController.lblRazao"));
		lblFantasia.setText(DadosGlobais.resourceBundle.getString("clienteController.lblFantasia"));
		lblCpfCnpj.setText(DadosGlobais.resourceBundle.getString("clienteController.lblCnpj"));
		lblIncEstad.setText(DadosGlobais.resourceBundle.getString("clienteController.lblIncEstad"));
		lblIndIncEstad.setText(DadosGlobais.resourceBundle.getString("clienteController.lblIndIncEstad"));
		lblCodCidade.setText(DadosGlobais.resourceBundle.getString("clienteController.lblCodCidade"));
		lblCidade.setText(DadosGlobais.resourceBundle.getString("clienteController.lblCidade"));
		lblUf.setText(DadosGlobais.resourceBundle.getString("clienteController.lblUf"));
		lblEndereco.setText(DadosGlobais.resourceBundle.getString("clienteController.lblEndereco"));
		lblNumero.setText(DadosGlobais.resourceBundle.getString("clienteController.lblNumero"));
		lblComplemento.setText(DadosGlobais.resourceBundle.getString("clienteController.lblComplemento"));
		lblBairro.setText(DadosGlobais.resourceBundle.getString("clienteController.lblBairro"));
		lblCep.setText(DadosGlobais.resourceBundle.getString("clienteController.lblCep"));
		lblFone.setText(DadosGlobais.resourceBundle.getString("clienteController.lblFone"));
		lblCelular.setText(DadosGlobais.resourceBundle.getString("clienteController.lblCelular"));
		lblFone2.setText(DadosGlobais.resourceBundle.getString("clienteController.lblFone2"));
		lblEmail.setText(DadosGlobais.resourceBundle.getString("clienteController.lblEmail"));
		lblInternet.setText(DadosGlobais.resourceBundle.getString("clienteController.lblInternet"));
		lblInscProd.setText(DadosGlobais.resourceBundle.getString("clienteController.lblInscProd"));
		lblLimiteCredito.setText(DadosGlobais.resourceBundle.getString("clienteController.lblLimiteCredito"));
		lblLocalLimiteCredito.setText(DadosGlobais.resourceBundle.getString("clienteController.lblLocalLimiteCredito"));
		rdbLocalLimiteCredito0
				.setText(DadosGlobais.resourceBundle.getString("clienteController.rdbLocalLimiteCredito0"));
		rdbLocalLimiteCredito1
				.setText(DadosGlobais.resourceBundle.getString("clienteController.rdbLocalLimiteCredito1"));
		lblNoSuframa.setText(DadosGlobais.resourceBundle.getString("clienteController.lblNoSuframa"));
		lblConsumidorFinal.setText(DadosGlobais.resourceBundle.getString("clienteController.lblConsumidorFinal"));
		lblIdentEstrangeiro.setText(DadosGlobais.resourceBundle.getString("clienteController.lblIdentEstrangeiro"));
		lblContatoPosVenda.setText(DadosGlobais.resourceBundle.getString("clienteController.lblContatoPosVenda"));
		btnImgCli.setText(DadosGlobais.resourceBundle.getString("clienteController.btnImgCli"));
		btnRemoveImgCli.setText(DadosGlobais.resourceBundle.getString("clienteController.btnRemoveImgCli"));

		// Tab Referências
		tabReferencias.setText(DadosGlobais.resourceBundle.getString("clienteController.tabReferencias"));
		lblCodRegiao.setText(DadosGlobais.resourceBundle.getString("clienteController.lblCodRegiao"));
		lblCodRamoAtividade.setText(DadosGlobais.resourceBundle.getString("clienteController.lblCodRamoAtividade"));
		lblCodConvenio.setText(DadosGlobais.resourceBundle.getString("clienteController.lblCodConvenio"));
		lblCodRota.setText(DadosGlobais.resourceBundle.getString("clienteController.lblCodRota"));
		lblCodVendedor.setText(DadosGlobais.resourceBundle.getString("clienteController.lblCodVendedor"));
		lblCodSegmento.setText(DadosGlobais.resourceBundle.getString("clienteController.lblCodSegmento"));
		lblCodFuncionario.setText(DadosGlobais.resourceBundle.getString("clienteController.lblCodFuncionario"));
		lblTipoFaturamento.setText(DadosGlobais.resourceBundle.getString("clienteController.lblTipoFaturamento"));
		lblDataCadastro.setText(DadosGlobais.resourceBundle.getString("clienteController.lblDataCadastro"));
		lblDataValidade.setText(DadosGlobais.resourceBundle.getString("clienteController.lblDataValidade"));
		lblDataAtualizacao.setText(DadosGlobais.resourceBundle.getString("clienteController.lblDataAtualizacao"));
		lblObservacoes.setText(DadosGlobais.resourceBundle.getString("clienteController.lblObservacoes"));
		lblObsRestritiva.setText(DadosGlobais.resourceBundle.getString("clienteController.lblObsRestritiva"));

		// Tab Faturamento
		tabFaturamento.setText(DadosGlobais.resourceBundle.getString("clienteController.tabFaturamento"));
		lblTipoFaturamento.setText(DadosGlobais.resourceBundle.getString("clienteController.lblTipoFaturamento"));
		lblDescontoMax.setText(DadosGlobais.resourceBundle.getString("clienteController.lblDescontoMax"));
		rdbTipoPrecoVenda0.setText(DadosGlobais.resourceBundle.getString("clienteController.rdbTipoPrecoVenda0"));
		lblPermiteUsoTabelaAtacado
				.setText(DadosGlobais.resourceBundle.getString("clienteController.lblPermiteUsoTabelaAtacado"));
		lblFreteConta.setText(DadosGlobais.resourceBundle.getString("clienteController.lblFreteConta"));
		rdbFreteConta0.setText(DadosGlobais.resourceBundle.getString("clienteController.rdbFreteConta0"));
		rdbFreteConta1.setText(DadosGlobais.resourceBundle.getString("clienteController.rdbFreteConta1"));
		lblCodPortador.setText(DadosGlobais.resourceBundle.getString("clienteController.lblCodPortador"));
		lblCodSecao.setText(DadosGlobais.resourceBundle.getString("clienteController.lblCodSecao"));
		lblCodTabelaPreco.setText(DadosGlobais.resourceBundle.getString("clienteController.lblCodTabelaPreco"));
		lblCodOperacaoSaida.setText(DadosGlobais.resourceBundle.getString("clienteController.lblCodOperacaoSaida"));
		lblCodPlanoPagamento.setText(DadosGlobais.resourceBundle.getString("clienteController.lblCodPlanoPagamento"));
		lblCodTransportadora.setText(DadosGlobais.resourceBundle.getString("clienteController.lblCodTransportadora"));

		// Tab Dados Entrega
		tabDadosEntrega.setText(DadosGlobais.resourceBundle.getString("clienteController.tabDadosEntrega"));
		lblEnderecoEntrega.setText(DadosGlobais.resourceBundle.getString("clienteController.lblEndereco"));
		lblNumeroEntrega.setText(DadosGlobais.resourceBundle.getString("clienteController.lblNumero"));
		lblComplementoEntrega.setText(DadosGlobais.resourceBundle.getString("clienteController.lblComplemento"));
		lblBairroEntrega.setText(DadosGlobais.resourceBundle.getString("clienteController.lblBairro"));
		lblCodCidadeEntrega.setText(DadosGlobais.resourceBundle.getString("clienteController.lblCodCidade"));
		lblUfEntrega.setText(DadosGlobais.resourceBundle.getString("clienteController.lblUf"));
		lblCepEntrega.setText(DadosGlobais.resourceBundle.getString("clienteController.lblCep"));
		lblTelefoneEntrega.setText(DadosGlobais.resourceBundle.getString("clienteController.lblFone"));
		lblReferenciaEntrega.setText(DadosGlobais.resourceBundle.getString("clienteController.lblReferenciaEntrega"));
		btnAddEndereco.setText(DadosGlobais.resourceBundle.getString("clienteController.btnAddEndereco"));
		btnCancelEndereco.setText(DadosGlobais.resourceBundle.getString("toolTipBtnCancelar"));

		// Tab Dados Bancarios
		tabDadosBanc.setText(DadosGlobais.resourceBundle.getString("clienteController.tabDadosBanc"));
		lblBanco.setText(DadosGlobais.resourceBundle.getString("clienteController.lblBanco"));
		lblAgencia.setText(DadosGlobais.resourceBundle.getString("clienteController.lblAgencia"));
		lblConta.setText(DadosGlobais.resourceBundle.getString("clienteController.lblConta"));
		lblDestinatario.setText(DadosGlobais.resourceBundle.getString("clienteController.lblDestinatario"));
		lblNoCpfCnpj.setText(DadosGlobais.resourceBundle.getString("clienteController.lblCpfCnpj"));
		lblEnviaDadosCartorio.setText(DadosGlobais.resourceBundle.getString("clienteController.lblEnviaDadosCartorio"));
		lblDiasEnviaDadosCartorio.setText(DadosGlobais.resourceBundle.getString("clienteController.lblDiasEnviaDadosCartorio"));
		
		
		//Tab Consulta Receita Federal
		tabConsultaReceita.setText(DadosGlobais.resourceBundle.getString("clienteController.tabConsultaReceita"));
		btnEnviaCnpj.setText(DadosGlobais.resourceBundle.getString("clienteController.btnEnviaCnpj"));
		btnCapturaDados.setText(DadosGlobais.resourceBundle.getString("clienteController.btnCapturaDados"));

		// TableView
		tbColCodigo.setText(DadosGlobais.resourceBundle.getString("tbColCodigo"));
		tbColCodeEmp.setText(DadosGlobais.resourceBundle.getString("tbColCodeEmp"));
		tbColAtivoInat.setText(DadosGlobais.resourceBundle.getString("tbColAtivoInat"));
		tbColRazao.setText(DadosGlobais.resourceBundle.getString("clienteController.lblRazao"));
		tbColFantasia.setText(DadosGlobais.resourceBundle.getString("clienteController.lblFantasia"));
		tbColCpfCnpj.setText(DadosGlobais.resourceBundle.getString("clienteController.lblCpfCnpj"));
		tbColIeRg.setText(DadosGlobais.resourceBundle.getString("clienteController.lblIncEstad"));
		tbColCodCidade.setText(DadosGlobais.resourceBundle.getString("clienteController.lblCodCidade"));
		tbColCidade.setText(DadosGlobais.resourceBundle.getString("clienteController.lblCidade"));
		tbColUf.setText(DadosGlobais.resourceBundle.getString("clienteController.lblUf"));
		tbColEndereco.setText(DadosGlobais.resourceBundle.getString("clienteController.lblEndereco"));
		tbColEndNumero.setText(DadosGlobais.resourceBundle.getString("clienteController.lblNumero"));
		tbColComplemento.setText(DadosGlobais.resourceBundle.getString("clienteController.lblComplemento"));
		tbColBairro.setText(DadosGlobais.resourceBundle.getString("clienteController.lblBairro"));
		tbColCep.setText(DadosGlobais.resourceBundle.getString("clienteController.lblCep"));
		tbColFone.setText(DadosGlobais.resourceBundle.getString("clienteController.lblFone"));
		tbColCelular.setText(DadosGlobais.resourceBundle.getString("clienteController.lblCelular"));
		tbColFone2.setText(DadosGlobais.resourceBundle.getString("clienteController.lblFone2"));
		tbColEmail.setText(DadosGlobais.resourceBundle.getString("clienteController.lblEmail"));
		tbColInternet.setText(DadosGlobais.resourceBundle.getString("clienteController.lblInternet"));
		tbColInscProd.setText(DadosGlobais.resourceBundle.getString("clienteController.lblInscProd"));
		tbColLimiteCredito.setText(DadosGlobais.resourceBundle.getString("clienteController.lblLimiteCredito"));
		tbColLocalLimiteCredito
				.setText(DadosGlobais.resourceBundle.getString("clienteController.lblLocalLimiteCredito"));
		tbColNoSuframa.setText(DadosGlobais.resourceBundle.getString("clienteController.lblNoSuframa"));
		tbColConsumidorFinal.setText(DadosGlobais.resourceBundle.getString("clienteController.lblConsumidorFinal"));
		tbColIdentEstrangeiro.setText(DadosGlobais.resourceBundle.getString("clienteController.lblIdentEstrangeiro"));
		tbColContatoPosVenda.setText(DadosGlobais.resourceBundle.getString("clienteController.lblContatoPosVenda"));

		// TableView Cliente Endeco Entrega
		tbColEnderecoEntrega.setText(DadosGlobais.resourceBundle.getString("clienteController.lblEndereco"));
		tbColEndNumeroEntrega.setText(DadosGlobais.resourceBundle.getString("clienteController.lblNumero"));
		tbColComplementoEntrega.setText(DadosGlobais.resourceBundle.getString("clienteController.lblComplemento"));
		tbColBairroEntrega.setText(DadosGlobais.resourceBundle.getString("clienteController.lblBairro"));
		tbColCodCidadeEntrega.setText(DadosGlobais.resourceBundle.getString("clienteController.lblCodCidade"));
		tbColCidadeEntrega.setText(DadosGlobais.resourceBundle.getString("clienteController.lblCidade"));
		tbColUfEntrega.setText(DadosGlobais.resourceBundle.getString("clienteController.lblUf"));
		tbColCepEntrega.setText(DadosGlobais.resourceBundle.getString("clienteController.lblCep"));
		tbColFoneEntrega.setText(DadosGlobais.resourceBundle.getString("clienteController.lblFone"));
		tbColReferenciaEntrega.setText(DadosGlobais.resourceBundle.getString("clienteController.lblReferenciaEntrega"));

		tbViewDadosEntrega.setPlaceholder(new Label(DadosGlobais.resourceBundle.getString("tbView")));
		tbView.setPlaceholder(new Label(DadosGlobais.resourceBundle.getString("tbView")));
	}

	/**
	 * METODO PARA POPULAR O COMBOBOX DE TIPOS DE FILTRO (cboxFilterColumn)
	 */
	public void populateCboxFilterColumn() {
		// 2 Tipo de Busca Contida, 1 Tipo de Busca Exata
		listComboBoxFilter.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("codigo"), 1, "codigo"));
		listComboBoxFilter.add(
				new ComboBoxFilter(DadosGlobais.resourceBundle.getString("clienteController.lblRazao"), 0, "razao"));
		listComboBoxFilter.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("clienteController.lblCpfCnpj"),
				1, "cpfCnpj"));
		listComboBoxFilter.add(new ComboBoxFilter(
				DadosGlobais.resourceBundle.getString("clienteController.cboxFilterColumn2"), 2, "ieRg"));
		listComboBoxFilter.add(new ComboBoxFilter(
				DadosGlobais.resourceBundle.getString("clienteController.lblFantasia"), 3, "fantasia"));

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
	 * METODO PARA POPULAR O COMBOBOX DE TIPO DE PESSOA
	 */
	public void populateCboxTipoPessoa() {
		ObservableList<ComboBoxFilter> list = FXCollections.observableArrayList();
		list.add(
				new ComboBoxFilter(DadosGlobais.resourceBundle.getString("clienteController.cboxTipoPessoa1"), 1, "1"));
		list.add(
				new ComboBoxFilter(DadosGlobais.resourceBundle.getString("clienteController.cboxTipoPessoa2"), 2, "2"));

		cboxTipoPessoa.getItems().addAll(list);
		cboxTipoPessoa.getSelectionModel().selectLast();
		cboxTipoPessoa.setConverter(new StringConverter<ComboBoxFilter>() {
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
	 * Fill comboBox CboxConsumidorFinal and property definitions
	 */
	public void fillCboxConsumidorFinal() {

		for (EnumConsumidorFinal item : EnumConsumidorFinal.values())
			listCboxConsumidorFinal.add(new ComboBoxFilter(item.text, 0, item.index.toString()));

		cboxConsumidorFinal.getItems().addAll(listCboxConsumidorFinal);
		cboxConsumidorFinal.getSelectionModel().select(0);

		cboxConsumidorFinal.setConverter(new StringConverter<ComboBoxFilter>() {
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
	 * Fill comboBox CboxTipoFaturamento and property definitions
	 */
	public void fillCboxTipoFaturamento() {

		for (EnumTipoFaturamento item : EnumTipoFaturamento.values())
			listCboxTipoFaturamento.add(new ComboBoxFilter(item.text, item.index, item.index.toString()));

		cboxTipoFaturamento.getItems().addAll(listCboxTipoFaturamento);
		cboxTipoFaturamento.getSelectionModel().selectFirst();
		// cboxTipoFaturamento.getSelectionModel().select(0);

		cboxTipoFaturamento.setConverter(new StringConverter<ComboBoxFilter>() {
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

	// ----MÉTODOS RESPONSAVEIS POR CONFIGURAR AS COLUNAS DO TABLEVIEW E GRAVAR
	// AS CONFIGURACOES NO ARQUIVO XML PARA CADA USUARIO

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
	public void updateTableView(ObservableList visibleColumns, TableView<ClienteDAO> table) {

		if (!visibleColumns.isEmpty()) {
			// hide all columns
			for (TableColumn<ClienteDAO, ?> col : table.getColumns())
				col.setVisible(false);
			// show column and defines its position
			for (int i = 0; i < visibleColumns.size(); i++) {
				table.getColumns().remove(visibleColumns.get(i));
				table.getColumns().add(i, (TableColumn<ClienteDAO, ?>) visibleColumns.get(i));
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
						TableColumn<Cliente, ?> tableColumn = tbView.getColumns().get(j);
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

			Object type = Cliente.class
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
					new PrintExportController(tbView, tableShowPrintList, "Cliente", pBar, stg)));

			stg.setScene(scene);
			stg.initStyle(StageStyle.TRANSPARENT);
			stg.initModality(Modality.APPLICATION_MODAL);
			stg.show();
		} catch (IOException e) {
			util.tratamentoExcecao(e.getMessage().toString(), "[ troller.printExportShow() ]");
		}

	}

	/**
	 * Method to change the appearance of the button Insert or Update
	 * ClienteEndEntrega
	 * 
	 * @param flagInsertUpdate
	 *            True to insert and false to update
	 */
	public void alterBtnAddEndereco(boolean flagInsertUpdate) {

		FontAwesomeIconView icon = new FontAwesomeIconView(
				flagInsertUpdate ? FontAwesomeIcon.PLUS : FontAwesomeIcon.SAVE);

		icon.setId("icons");
		btnAddEndereco.setText(
				DadosGlobais.resourceBundle.getString(flagInsertUpdate ? "toolTipBtnAdd" : "toolTipBtnGuardar"));

		btnAddEndereco.setGraphic(icon);
	}

	// MÉTODO DE INICILIAZAÇÃO DA CLASSE
	@SuppressWarnings({ "static-access", "unchecked" })
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
		
		Util.customSearchTextField("right", null, txtFilterColumn, txtCodCidade, txtCodRegiao, txtCodRamoAtividade,
				txtCodConvenio, txtCodRota, txtCodVendedor, txtCodSegmento, txtCodFuncionario, txtCodPortador,
				txtCodSecao, txtCodTabelaPreco, txtCodOperacaoSaida, txtCodPlanoPagamento, txtCodTransportadora,
				txtCodCidadeEntrega);

		// CHAMADA A FUNÇÃO QUE CONFIGURA O TABLEVIEW DE ACORDO COM O ARQUIVO
		// XML GERADO PARA CADA USUARIO DENTRO DA PASTA C:/EptusAM/System.Grd
		configTableView();

		// CHAMADA A FUNÇÃO QUE FAZ A TRADUÇÃO DO FORMULARIO DE ACORDO COM AS
		// CONFIGURAÇÕES DO USUÁRIO
		translations();

		// FUNÇAO PARA MUDAR O FOCO PARA O PROXIMO CAMPO AO DAR ENTER
		Util.setNextFocus(txtRazao, txtFantasia, txtCnpj, txtIncEstad, txtEndereco, txtEndNumero, txtBairro, txtCep,
				txtFone, txtCelular, txtFone2, txtEmail, txtInternetSite, txtInscProdRural, txtContatoPosVenda);

		// CHAMADA A FUNÇÃO QUE SETA O ESTILO DEFAULT AOS CAMPOS TEXTFIELDS
		// Tab Informações Gerais
		Util.setKeyPressDefaultStyles(txtFilterColumn, txtCodigo, txtRazao, txtFantasia, txtCnpj, txtIncEstad,
				txtCodCidade, txtUF, txtEndereco, txtEndNumero, txtComplemento, txtBairro, txtCep, txtFone, txtCelular,
				txtFone2, txtEmail, txtInternetSite, txtInscProdRural, txtLimiteCredito, txtNoSuframa,
				txtIdentEstrangeiro, txtContatoPosVenda);
		// Tab Referências
		Util.setKeyPressDefaultStyles(txtCodRegiao, txtRegiao, txtCodRamoAtividade, txtRamoAtividade, txtCodConvenio,
				txtConvenio, txtCodRota, txtRota, txtCodVendedor, txtVendedor, txtCodSegmento, txtSegmento,
				txtCodFuncionario, txtFuncionario, txtVendedor);
		// Tab Fatiramento
		Util.setKeyPressDefaultStyles(txtDescontoMax, txtCodPortador, txtPortador, txtCodSecao, txtSecao,
				txtCodTabelaPreco, txtTabelaPreco, txtCodOperacaoSaida, txtOperacaoSaida, txtCodPlanoPagamento,
				txtPlanoPagamento, txtCodTransportadora, txtTransportadora);

		// Tab Dados p/ Entrega
		Util.setKeyPressDefaultStyles(txtEnderecoEntrega, txtNumeroEntrega, txtComplementoEntrega, txtBairroEntrega,
				txtCodCidadeEntrega, txtCidadeEntrega, txtUfEntrega, txtCepEntrega, txtTelefoneEntrega,
				txtReferenciaEntrega);
		// Tab Dados Bancarios
		Util.setKeyPressDefaultStyles(txtBanco, txtAgencia, txtConta, txtDestinatario, txtNoCpfCnpj);

		Util.setStyleOnFocus(txtFilterColumn, txtCodigo, txtRazao, txtFantasia, txtCnpj, txtIncEstad, txtCodCidade,
				txtUF, txtEndereco, txtEndNumero, txtComplemento, txtBairro, txtCep, txtFone, txtCelular, txtFone2,
				txtEmail, txtInternetSite, txtInscProdRural, txtLimiteCredito, txtNoSuframa, txtIdentEstrangeiro,
				txtContatoPosVenda);
		// Tab Referências
		Util.setStyleOnFocus(txtCodRegiao, txtRegiao, txtCodRamoAtividade, txtRamoAtividade, txtCodConvenio,
				txtConvenio, txtCodRota, txtRota, txtCodVendedor, txtVendedor, txtCodSegmento, txtSegmento,
				txtCodFuncionario, txtFuncionario, txtVendedor);
		// Tab Fatiramento
		Util.setStyleOnFocus(txtDescontoMax, txtCodPortador, txtPortador, txtCodSecao, txtSecao, txtCodTabelaPreco,
				txtTabelaPreco, txtCodOperacaoSaida, txtOperacaoSaida, txtCodPlanoPagamento, txtPlanoPagamento,
				txtCodTransportadora, txtTransportadora);
		// Tab Dados p/ Entrega
		Util.setStyleOnFocus(txtEnderecoEntrega, txtNumeroEntrega, txtComplementoEntrega, txtBairroEntrega,
				txtCodCidadeEntrega, txtCidadeEntrega, txtUfEntrega, txtCepEntrega, txtTelefoneEntrega,
				txtReferenciaEntrega);
		// Tab Dados Bancarios
		Util.setStyleOnFocus(txtBanco, txtAgencia, txtConta, txtDestinatario, txtNoCpfCnpj);

		Util.setMouseClickDefaultStyles(cboxIndIncEstad);

		Util.onlyNumbers(txtCodigo, txtIncEstad, txtInscProdRural, txtNoSuframa, txtEndNumero, txtNumeroEntrega,
				txtCodCidade, txtCodCidadeEntrega, txtCodRegiao, txtCodRamoAtividade, txtCodConvenio, txtCodRota,
				txtCodVendedor, txtCodSegmento, txtCodFuncionario, txtCodPortador, txtCodSecao, txtCodTabelaPreco,
				txtCodOperacaoSaida, txtCodPlanoPagamento, txtCodTransportadora);

		Util.decimalBR(4, txtLimiteCredito, txtDescontoMax);

		txtLimiteCredito.textProperty()
				.addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {

					if (!txtLimiteCredito.getText().isEmpty()) {
						BigDecimal l = new BigDecimal(txtLimiteCredito.getText().replace(",", "."));

						if (l.compareTo(DadosGlobais.usuarioLogado.getVdaMaxLimiteCredito()) == 1)
							if(!oldValue.isEmpty())
								txtLimiteCredito.setText(oldValue);
									//txtLimiteCredito.getText().substring(0, txtLimiteCredito.getText().length() - 1));

					}

				});

		spnDiasEnviaDadosCartorio.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 360, 1));

		// CHAMADA A FUNÇÃO QUE SETA O ESTILO DEFAULT AOS CAMPOS TEXTFIELDS
		Util.onlyAlphanumeric(txtRazao, txtFilterColumn, txtRazao, txtFantasia, txtEndereco, txtBairro);

		// CHAMADA A FUNÇÃO QUE LIMITA O NUMERO DE CARACTERES QUE PODEM SER
		// DIGITADOS NOS CAMPOS TEXTFIELDS
		Util.maxCharacters(8, txtCodigo, txtCodCidade);
		Util.maxCharacters(45, txtRazao, txtFantasia);
		// Util.maxCharacters(45, txtFantasia);
		Util.maxCharacters(14, txtFone); 

		// CAPITALIZADOS(LETRAS MAÍUSCULAS)
		Util.whriteUppercase(txtFilterColumn, txtCodigo, txtRazao, txtFantasia, txtCnpj, txtIncEstad, txtCodCidade,
				txtUF, txtEndereco, txtEndNumero, txtComplemento, txtBairro, txtCep, txtFone, txtCelular, txtFone2,
				txtInternetSite, txtInscProdRural, txtLimiteCredito, txtNoSuframa, txtIdentEstrangeiro,
				txtContatoPosVenda);
		// Tab Referências
		Util.whriteUppercase(txtCodRegiao, txtRegiao, txtCodRamoAtividade, txtRamoAtividade, txtCodConvenio,
				txtConvenio, txtCodRota, txtRota, txtCodVendedor, txtVendedor, txtCodSegmento, txtSegmento,
				txtCodFuncionario, txtFuncionario, txtVendedor);
		// Tab Fatiramento
		Util.whriteUppercase(txtDescontoMax, txtPortador, txtSecao, txtTabelaPreco, txtOperacaoSaida, txtPlanoPagamento,
				txtTransportadora);
		// Tab Dados p/ Entrega
		Util.whriteUppercase(txtEnderecoEntrega, txtNumeroEntrega, txtComplementoEntrega, txtBairroEntrega,
				txtCodCidadeEntrega, txtCidadeEntrega, txtUfEntrega, txtCepEntrega, txtTelefoneEntrega,
				txtReferenciaEntrega);
		// Tab Dados Bancarios
		Util.whriteUppercase(txtBanco, txtAgencia, txtConta, txtDestinatario, txtNoCpfCnpj);

		Util.whriteLowercase(txtEmail);

		// CHAMADA A FUNÇÃO PARA POPULAR OS COMBOBOX CONTINDOS NO FORMULARIO
		populateCboxFilterColumn();
		populateCboxFlagAtivo();
		populateCboxTipoPessoa();
		fillCboxConsumidorFinal();
		fillCboxTipoFaturamento();
		// ComboBoxIndicadorIE cboxIndIE = new
		// ComboBoxIndicadorIE(cboxIndIncEstad);
		ComboBoxIndicadorIE.ComboBoxIndicadorIE(cboxIndIncEstad);
		// MASCARA DE CAMPOS ESPECIFICOS
		Util.mascaraCEP(txtCep);
		Util.mascaraCNPJ(txtCnpj);
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
		
		Util.maskPhone(txtFone2);
		
		txtFone2.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if(!newValue){
					if(!Util.validatePhone(txtFone2)){			
						txtFone2.requestFocus();
						txtFone2.positionCaret(txtFone2.getText().length());
					}
				}
			}
		});

		Util.mascaraCEP(txtCepEntrega);
		Util.mascaraCNPJ(txtNoCpfCnpj);
		
		Util.maskTelFixo(txtTelefoneEntrega);
		
		

		// Initializes the values of the RadioButton
		Util.defineRadioButton(grpLocalLimiteCredito, grpTipoPrecoVenda, grpFreteConta);

		// CHAMADA A FUNÇÃO PARA SETAR O FOCO INICIAL DO FORMULÁRIO
		util.setFocus(txtFilterColumn);

		Util.disableDatePicker(dateDataValidade);

		btnCancelEndereco.setDisable(true);

		btnImgCli.disableProperty().bind(btnSave.disableProperty());
		btnAddEndereco.disableProperty().bind(btnSave.disableProperty());

		// CONFIGURA AS COLUNAS EXISTENTES NO TABLEVIEW, ASSOCIA A COLUNA DO
		// FXML AO ATRIBUTO EXISTENTE NA CLASSE DO MODEL
		tbColCodigo.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("codigo"));
		tbColRazao.setCellValueFactory(new PropertyValueFactory<Cliente, String>("razao"));
		tbColFantasia.setCellValueFactory(new PropertyValueFactory<Cliente, String>("fantasia"));
		tbColCpfCnpj.setCellValueFactory(new PropertyValueFactory<Cliente, String>("cpfCnpj"));
		tbColIeRg.setCellValueFactory(new PropertyValueFactory<Cliente, String>("ieRg"));
		tbColIndicadorInscEst.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("flagIndicadorinscest"));
		tbColCodeEmp.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("codemp"));
		tbColAtivoInat.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("flagAtivo"));
		tbColCodCidade.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("codCidade"));
		tbColCidade.setCellValueFactory(new PropertyValueFactory<Cliente, String>("cidade"));
		tbColUf.setCellValueFactory(new PropertyValueFactory<Cliente, String>("uf"));
		tbColEndereco.setCellValueFactory(new PropertyValueFactory<Cliente, String>("endereco"));
		tbColEndNumero.setCellValueFactory(new PropertyValueFactory<Cliente, String>("endNumero"));
		tbColComplemento.setCellValueFactory(new PropertyValueFactory<Cliente, String>("complemento"));
		tbColBairro.setCellValueFactory(new PropertyValueFactory<Cliente, String>("bairro"));
		tbColCep.setCellValueFactory(new PropertyValueFactory<Cliente, String>("cep"));
		tbColFone.setCellValueFactory(new PropertyValueFactory<Cliente, String>("fone"));
		tbColCelular.setCellValueFactory(new PropertyValueFactory<Cliente, String>("celular"));
		tbColFone2.setCellValueFactory(new PropertyValueFactory<Cliente, String>("fone2"));
		tbColEmail.setCellValueFactory(new PropertyValueFactory<Cliente, String>("email"));
		tbColInternet.setCellValueFactory(new PropertyValueFactory<Cliente, String>("internetSite"));
		tbColInscProd.setCellValueFactory(new PropertyValueFactory<Cliente, String>("inscProdrural"));
		tbColLimiteCredito.setCellValueFactory(new PropertyValueFactory<Cliente, BigDecimal>("limiteCredito"));
		tbColLocalLimiteCredito.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("origemLimiteCredito"));
		tbColNoSuframa.setCellValueFactory(new PropertyValueFactory<Cliente, String>("suframa"));
		tbColConsumidorFinal.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("flagConsumidorFinal"));
		tbColIdentEstrangeiro
				.setCellValueFactory(new PropertyValueFactory<Cliente, String>("identificacaoEstrangeiro"));
		tbColContatoPosVenda.setCellValueFactory(new PropertyValueFactory<Cliente, String>("contatoNome"));

		// TableView Cliente Dados Entrega
		tbColEnderecoEntrega.setCellValueFactory(new PropertyValueFactory<ClienteEndEntrega, String>("endereco"));
		tbColEndNumeroEntrega.setCellValueFactory(new PropertyValueFactory<ClienteEndEntrega, String>("endNumero"));
		tbColComplementoEntrega.setCellValueFactory(new PropertyValueFactory<ClienteEndEntrega, String>("complemento"));
		tbColBairroEntrega.setCellValueFactory(new PropertyValueFactory<ClienteEndEntrega, String>("bairro"));
		tbColCodCidadeEntrega.setCellValueFactory(new PropertyValueFactory<ClienteEndEntrega, Integer>("codCidade"));
		tbColCidadeEntrega.setCellValueFactory(new PropertyValueFactory<ClienteEndEntrega, String>("cidade"));
		tbColUfEntrega.setCellValueFactory(new PropertyValueFactory<ClienteEndEntrega, String>("uf"));
		tbColCepEntrega.setCellValueFactory(new PropertyValueFactory<ClienteEndEntrega, String>("cep"));
		tbColFoneEntrega.setCellValueFactory(new PropertyValueFactory<ClienteEndEntrega, String>("fone"));
		tbColReferenciaEntrega
				.setCellValueFactory(new PropertyValueFactory<ClienteEndEntrega, String>("pontoReferencia"));

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

		tbViewDadosEntrega.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode().equals(KeyCode.ENTER)
					&& tbViewDadosEntrega.getSelectionModel().getSelectedItem().getFlagAtivo().equals(1)) {
				// loadEnderecoByID();
				txtEnderecoEntrega.setText(tbViewDadosEntrega.getSelectionModel().getSelectedItem().getEndereco());
				txtNumeroEntrega.setText(tbViewDadosEntrega.getSelectionModel().getSelectedItem().getEndNumero());
				txtComplementoEntrega
						.setText(tbViewDadosEntrega.getSelectionModel().getSelectedItem().getComplemento());
				txtBairroEntrega.setText(tbViewDadosEntrega.getSelectionModel().getSelectedItem().getBairro());
				txtCodCidadeEntrega.setText(
						String.valueOf(tbViewDadosEntrega.getSelectionModel().getSelectedItem().getCodCidade()));
				txtCidadeEntrega.setText(tbViewDadosEntrega.getSelectionModel().getSelectedItem().getCidade());
				txtUfEntrega.setText(tbViewDadosEntrega.getSelectionModel().getSelectedItem().getUf());
				txtCepEntrega.setText(tbViewDadosEntrega.getSelectionModel().getSelectedItem().getCep());
				txtTelefoneEntrega.setText(tbViewDadosEntrega.getSelectionModel().getSelectedItem().getFone());
				txtReferenciaEntrega
						.setText(tbViewDadosEntrega.getSelectionModel().getSelectedItem().getPontoReferencia());
				txtEnderecoEntrega.selectEnd();

				alterBtnAddEndereco(false);
				flagInsertUpdate = false;
				btnCancelEndereco.setDisable(false);

			}
			if (event.getCode().equals(KeyCode.DELETE)) {
				if (tbViewDadosEntrega.getSelectionModel().getSelectedItem().getFlagAtivo().equals(1)
						&& nivAcessoPermissao.getFlagDisable().equals(1)
						&& util.showAlert(
								DadosGlobais.resourceBundle.getString("alertConfirmOprExcluir") + " "
										+ DadosGlobais.resourceBundle.getString("oprExcluir") + " "
										+ DadosGlobais.resourceBundle
												.getString("clienteController.alertConfirmOprExcluirEndereco"),
								"confirmation")) {

					boolean flagUdate = false;

					for (ClienteEndEntrega ce : entidadeBean.getClienteEndEntregas()) {
						if (ce.getRecordNo()
								.equals(tbViewDadosEntrega.getSelectionModel().getSelectedItem().getRecordNo())) {
							flagUdate = true;
							ce.setFlagAtivo(0);
						}
					}

					if (!flagUdate)
						tbViewDadosEntrega.getSelectionModel().getSelectedItem().setFlagAtivo(0);

					tbViewDadosEntrega.refresh();

				} else if (tbViewDadosEntrega.getSelectionModel().getSelectedItem().getFlagAtivo().equals(0)
						&& nivAcessoPermissao.getFlagEnable().equals(1)
						&& util.showAlert(
								DadosGlobais.resourceBundle.getString("alertConfirmOprExcluir") + " "
										+ DadosGlobais.resourceBundle.getString("oprAtivar") + " "
										+ DadosGlobais.resourceBundle
												.getString("clienteController.alertConfirmOprExcluirEndereco"),
								"confirmation")) {

					boolean flagUdate = false;

					for (ClienteEndEntrega ce : entidadeBean.getClienteEndEntregas()) {
						if (ce.getRecordNo()
								.equals(tbViewDadosEntrega.getSelectionModel().getSelectedItem().getRecordNo())) {
							flagUdate = true;
							ce.setFlagAtivo(1);
						}
					}

					if (!flagUdate)
						tbViewDadosEntrega.getSelectionModel().getSelectedItem().setFlagAtivo(1);

					tbViewDadosEntrega.refresh();
				}
			}
		});

		tbViewDadosEntrega.setRowFactory(tb -> {
			// EXIBE O EFEITO DE OPACIDADE NAS LINHAS CASO O ITEM DO TABLEVIEW
			// TENHA O FLAGATIVO = 0
			TableRow<ClienteEndEntrega> row = new TableRow<ClienteEndEntrega>() {
				@Override
				public void updateItem(ClienteEndEntrega objeto, boolean empty) {
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
					if (tbViewDadosEntrega.getSelectionModel().getSelectedItem().getFlagAtivo().equals(1)) {
						// loadEnderecoByID();
						// Change Button and enable buton cancel
						txtEnderecoEntrega
								.setText(tbViewDadosEntrega.getSelectionModel().getSelectedItem().getEndereco());
						txtNumeroEntrega
								.setText(tbViewDadosEntrega.getSelectionModel().getSelectedItem().getEndNumero());
						txtComplementoEntrega
								.setText(tbViewDadosEntrega.getSelectionModel().getSelectedItem().getComplemento());
						txtBairroEntrega.setText(tbViewDadosEntrega.getSelectionModel().getSelectedItem().getBairro());
						txtCodCidadeEntrega.setText(String
								.valueOf(tbViewDadosEntrega.getSelectionModel().getSelectedItem().getCodCidade()));
						txtCidadeEntrega.setText(tbViewDadosEntrega.getSelectionModel().getSelectedItem().getCidade());
						txtUfEntrega.setText(tbViewDadosEntrega.getSelectionModel().getSelectedItem().getUf());
						txtCepEntrega.setText(tbViewDadosEntrega.getSelectionModel().getSelectedItem().getCep());
						txtTelefoneEntrega.setText(tbViewDadosEntrega.getSelectionModel().getSelectedItem().getFone());
						txtReferenciaEntrega
								.setText(tbViewDadosEntrega.getSelectionModel().getSelectedItem().getPontoReferencia());
						txtEnderecoEntrega.selectEnd();

						alterBtnAddEndereco(false);
						flagInsertUpdate = false;
						btnCancelEndereco.setDisable(false);

					}
				}
			});

			return row;
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
			TableRow<Cliente> row = new TableRow<Cliente>() {
				@Override
				public void updateItem(Cliente objeto, boolean empty) {
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

					// ITEM DO MENU DE AÇOES - ALTERAR UM REGISTRO
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

					// ITEM DO MENU DE AÇOES - EXCLUIR UM REGISTRO
					MenuItem itemExcluir = new MenuItem(DadosGlobais.resourceBundle.getString("actionExcluir"));
					itemExcluir.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent e) {
							actionBtnDelete(null);
						}
					});

					// ITEM DO MENU DE AÇOES - DUPLICAR CADASTRO
					MenuItem itemDuplica = new MenuItem(DadosGlobais.resourceBundle.getString("actionDuplicar"));
					itemDuplica.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent e) {
							loadByID(false, tbView.getSelectionModel().getSelectedItem().getCodigo(), true);
							Util.openFormCadastro(anchorPaneListagem, anchorPaneDetalhes, 0);
						}
					});

					// ITEM DO MENU DE AÇOES - RASTREAR PRODUTOS ASSOCIADOS A
					// ESTE DEPARTAMENTO
					MenuItem itemRastreiaCriaCliente = new MenuItem("Criar Cliente");
					itemRastreiaCriaCliente.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent e) {

						}
					});

					// ITEM DO MENU DE AÇOES - RASTREAR PRODUTOS ASSOCIADOS A
					// ESTE DEPARTAMENTO
					MenuItem itemRastreiaProd = new MenuItem(
							DadosGlobais.resourceBundle.getString("actionRastrearProduto"));
					if (Util.getNivelAcessoEntidade(EnumNivelAcesso.ITENS).getFlagView().equals(0)) {
						itemRastreiaProd.setDisable(true);
					}
					itemRastreiaProd.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent e) {
							try {
								util.crearTab(tabPrincipal, DadosGlobais.resourceBundle.getString("miCadItem"),
										"/views/compras/viewItens.fxml",
										new ItemController(Util.getNivelAcessoEntidade(EnumNivelAcesso.ITENS),
												"Cod_Unidade",
												tbView.getSelectionModel().getSelectedItem().getCodigo().toString()),
										false);
							} catch (IOException e1) {
								util.tratamentoExcecao(e1.getMessage().toString(),
										"[ UnidadeController.acaoRastrearItens() ]");
							}
						}
					});
					// ITEM DO MENU DE AÇOES - RASTREAR PRODUTOS ASSOCIADOS A
					// ESTE DEPARTAMENTO
					MenuItem itemRastreiaProdReposto = new MenuItem("Rastreia Produtos Repostos");
					itemRastreiaProdReposto.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent e) {

						}
					});

					// ITEM DO MENU DE AÇOES - RASTREAR PRODUTOS ASSOCIADOS A
					// ESTE DEPARTAMENTO
					MenuItem itemRastreiaNfeEnt = new MenuItem("Rastreia Notas Entrada");
					itemRastreiaNfeEnt.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent e) {

						}
					});

					// ITEM DO MENU DE AÇOES - RASTREAR PRODUTOS ASSOCIADOS A
					// ESTE DEPARTAMENTO
					MenuItem itemRastreiaCtasPagar = new MenuItem("Rastreia Contas a Pagar");
					itemRastreiaCtasPagar.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent e) {

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

					contextMenu.getItems().addAll(itemAtualizar, itemExcluir, itemDuplica, itemRastreiaCriaCliente,
							itemRastreiaProd, itemRastreiaProdReposto, itemRastreiaNfeEnt, itemRastreiaCtasPagar);

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

		// EVENTO DE VERIFICA SE O VALOR É VALIDO AO SAIR DO CAMPO CPF/CNPJ
		txtCnpj.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				// if (!newPropertyValue) {
				if (!txtCnpj.getText().isEmpty() && !txtCnpj.isDisable()) {

					if (cboxTipoPessoa.getValue().getField().equals("1")) {
						if (!Validacoes.isCPF(txtCnpj.getText().toString())) {
							Util.setStyleError(true, txtCnpj);
						}
					} else if (!Validacoes.isCNPJ(txtCnpj.getText().toString())) {
						Util.setStyleError(true, txtCnpj);
					}
				}
			}
			// }

		});

		// EVENTO DE CARREGA O NOME DA CIDADE AO SAIR DO CAMPO CODIGO CIDADE
		txtCodCidade.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (!newPropertyValue) {
					if (!btnInsert.isPressed() && !btnClose.isPressed() && !btnCancel.isPressed() && flagSearchCidade) {
						if (!txtCodCidade.getText().isEmpty()) {
							searchCidade(Integer.parseInt(txtCodCidade.getText()));
						}
					}
				}
			}
		});

		// EVENTO DE CARREGA O NOME DA CIDADE AO SAIR DO CAMPO CODIGO CIDADE
		txtCodCidadeEntrega.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (!newPropertyValue) {
					if (!btnInsert.isPressed() && !btnClose.isPressed() && !btnCancel.isPressed() && flagSearchCidade) {
						if (!txtCodCidadeEntrega.getText().isEmpty()) {
							searchCidadeEntrega(Integer.parseInt(txtCodCidadeEntrega.getText()));

						}
					}

				}

			}
		});

		txtCodRegiao.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (!newPropertyValue) {
					if (!btnInsert.isPressed() && !btnClose.isPressed() && !btnCancel.isPressed() && flagSearch) {
						if (!txtCodRegiao.getText().isEmpty()) {
							searchRegiao(Integer.parseInt(txtCodRegiao.getText()));
						}
					}
				}
			}
		});

		txtCodRamoAtividade.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (!newPropertyValue) {
					if (!btnInsert.isPressed() && !btnClose.isPressed() && !btnCancel.isPressed() && flagSearch) {
						if (!txtCodRamoAtividade.getText().isEmpty()) {
							searchRamoAtividade(Integer.parseInt(txtCodRamoAtividade.getText()));
						}
					}
				}
			}
		});

		txtCodConvenio.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (!newPropertyValue) {
					if (!btnInsert.isPressed() && !btnClose.isPressed() && !btnCancel.isPressed() && flagSearch) {
						if (!txtCodConvenio.getText().isEmpty()) {
							searchConvenio(Integer.parseInt(txtCodConvenio.getText()));
						}
					}
				}
			}
		});

		txtCodRota.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (!newPropertyValue) {
					if (!btnInsert.isPressed() && !btnClose.isPressed() && !btnCancel.isPressed() && flagSearch) {
						if (!txtCodRota.getText().isEmpty()) {
							searchRota(Integer.parseInt(txtCodRota.getText()));
						}
					}
				}
			}
		});

		txtCodVendedor.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (!newPropertyValue) {
					if (!btnInsert.isPressed() && !btnClose.isPressed() && !btnCancel.isPressed() && flagSearch) {
						if (!txtCodVendedor.getText().isEmpty()) {
							searchVendedor(Integer.parseInt(txtCodVendedor.getText()));
						}
					}
				}
			}
		});

		txtCodSegmento.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (!newPropertyValue) {
					if (!btnInsert.isPressed() && !btnClose.isPressed() && !btnCancel.isPressed() && flagSearch) {
						if (!txtCodSegmento.getText().isEmpty()) {
							searchSegmento(Integer.parseInt(txtCodSegmento.getText()));
						}
					}
				}
			}
		});

		txtCodFuncionario.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (!newPropertyValue) {
					if (!btnInsert.isPressed() && !btnClose.isPressed() && !btnCancel.isPressed() && flagSearch) {
						if (!txtCodFuncionario.getText().isEmpty()) {
							searchFuncionario(Integer.parseInt(txtCodFuncionario.getText()));
						}
					}
				}
			}
		});

		txtCodPortador.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (!newPropertyValue) {
					if (!btnInsert.isPressed() && !btnClose.isPressed() && !btnCancel.isPressed() && flagSearch) {
						if (!txtCodPortador.getText().isEmpty()) {
							searchPortador(Integer.parseInt(txtCodPortador.getText()));
						}
					}
				}
			}
		});

		txtCodSecao.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (!newPropertyValue) {
					if (!btnInsert.isPressed() && !btnClose.isPressed() && !btnCancel.isPressed() && flagSearch) {
						if (!txtCodSecao.getText().isEmpty()) {
							searchSecao(Integer.parseInt(txtCodSecao.getText()));
						}
					}
				}
			}
		});

		txtCodOperacaoSaida.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (!newPropertyValue) {
					if (!btnInsert.isPressed() && !btnClose.isPressed() && !btnCancel.isPressed() && flagSearch) {
						if (!txtCodOperacaoSaida.getText().isEmpty()) {
							searchOperacaoSaida(Integer.parseInt(txtCodOperacaoSaida.getText()));
						}
					}
				}
			}
		});

		txtCodPlanoPagamento.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (!newPropertyValue) {
					if (!btnInsert.isPressed() && !btnClose.isPressed() && !btnCancel.isPressed() && flagSearch) {
						if (!txtCodPlanoPagamento.getText().isEmpty()) {
							searchPlanoPagamento(Integer.parseInt(txtCodPlanoPagamento.getText()));
						}
					}
				}
			}
		});

		txtCodTransportadora.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (!newPropertyValue) {
					if (!btnInsert.isPressed() && !btnClose.isPressed() && !btnCancel.isPressed() && flagSearch) {
						if (!txtCodTransportadora.getText().isEmpty()) {
							searchTransportadora(Integer.parseInt(txtCodTransportadora.getText()));
						}
					}
				}
			}
		});

		// MÉTODO QUE CONVERTE OS VALORES DA COLUNA FLAGATIVO EM TEXTO CASO
		// FLAGATIVO = 0 MOSTRA INATIVO E FLAGATIVO = 1 MOSTRA ATIVO
		// tbColAtivoInat.setCellFactory(col -> new TableCell<Cliente,
		// Integer>() {
		// @Override
		// public void updateItem(Integer flagAtivo, boolean empty) {
		// super.updateItem(flagAtivo, empty);
		// if (empty) {
		// setText(" ");
		// } else {
		// if (flagAtivo == null)
		// setText(null);
		// else if (flagAtivo.equals(0)) {
		// setText(String.format(DadosGlobais.resourceBundle.getString("inativo"),
		// flagAtivo));
		// } else if (flagAtivo.equals(1))
		// setText(String.format(DadosGlobais.resourceBundle.getString("ativo"),
		// flagAtivo));
		// }
		// }
		// });

		pBarCnpj.progressProperty().bind(webCnpj.getEngine().getLoadWorker().progressProperty());

		tabPanePrincipal.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
			if (tabPanePrincipal.getSelectionModel().getSelectedItem().equals(tabConsultaReceita))
				webCnpj.getEngine().load(DadosGlobais.URL_CONSULT_CNPJ);
			// "http://www.receita.fazenda.gov.br/PessoaJuridica/CNPJ/cnpjreva/Cnpjreva_Solicitacao2.asp");
			else
				webCnpj.getEngine().load("about:blank");

		});

		// http://www.receita.fazenda.gov.br/PessoaJuridica/CNPJ/cnpjreva/valida.asp
		// http://www.receita.fazenda.gov.br/PessoaJuridica/CNPJ/cnpjreva/Cnpjreva_Comprovante.asp

		btnEnviaCnpj.setDisable(true);
		btnCapturaDados.setDisable(true);

		imgLoading.setVisible(false);

		webCnpj.getEngine().getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {

			@SuppressWarnings("rawtypes")
			public void changed(ObservableValue ov, State oldState, State newState) {

				if (newState == State.SCHEDULED) {
					txtUrlCnpj.setText(webCnpj.getEngine().getLocation());
					btnEnviaCnpj.setDisable(true);
					btnCapturaDados.setDisable(true);

					anpWeb.setRightAnchor(txtUrlCnpj, (double) 45);

					imgLoading.setImage(new Image("/styles/img/gears.gif"));
					imgLoading.setVisible(true);

				}

				if (newState == State.SUCCEEDED) {

					imgLoading.setVisible(false);
					anpWeb.setRightAnchor(txtUrlCnpj, (double) 10);

					Element e = (Element) webCnpj.getEngine().executeScript("document.getElementById('cnpj')");
					if (e != null) {
						if (!txtCnpj.getText().isEmpty() && Validacoes.isCNPJ(txtCnpj.getText()))
							btnEnviaCnpj.setDisable(false);

					} else {
						e = (Element) webCnpj.getEngine().executeScript("document.getElementById('ni')");
						if (e != null) {
							btnCapturaDados.setDisable(false);
						}
					}
				}

				if (newState == State.FAILED) {
					webCnpj.getEngine().reload();
				}

			}
		});

		dateDataCadastro.setValue(today);
		dateDataValidade.setValue(today);
		dateDataAtualizacao.setValue(today);

		// Date format for DatePicker
		@SuppressWarnings("rawtypes")
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

		dateDataCadastro.setConverter(converter);
		// dateDataValidade.setConverter(converter);
		dateDataAtualizacao.setConverter(converter);

		// TECLAS DE ATALHOS PARA O FORMULARIO
		// F2 - CONSULTAR | F4 - CARREGAR TODOSO OS DADOS REFRESH| F5 - INSERIR
		// NOVO REGISTRO | F6 - GRAVAR | DEL - EXCLUIR |F7 - CANCELAR || CTRL+P
		// IMPRIMIR
		anchorPanePrincipal.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			switch (event.getCode()) {
			case F2:
				if (!txtCodCidade.isFocused() && !txtCodRegiao.isFocused() && !txtCodRamoAtividade.isFocused()
						&& !txtCodConvenio.isFocused() && !txtCodRota.isFocused() && !txtCodVendedor.isFocused()
						&& !txtCodSegmento.isFocused() && !txtCodFuncionario.isFocused() && !txtCodPortador.isFocused()
						&& !txtCodSecao.isFocused() && !txtCodTabelaPreco.isFocused()
						&& !txtCodOperacaoSaida.isFocused() && !txtCodPlanoPagamento.isFocused()
						&& !txtCodTransportadora.isFocused() && !txtCodCidadeEntrega.isFocused()) {
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