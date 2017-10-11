package controllers.configuracoes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.mail.Folder;

import org.apache.axis2.databinding.types.soapencoding.DateTime;
import org.controlsfx.control.textfield.CustomTextField;

import com.jfoenix.controls.JFXToggleButton;
import com.sun.javafx.scene.control.skin.TabPaneSkin;

import application.DadosGlobais;
import br.com.samuelweb.nfe.Certificado;
import controllers.compras.DepartamentoController;
import controllers.utils.ConfigCertificadoController;
import controllers.utils.ConfigColumnController;
import controllers.utils.ProgressBarForm;
import controllers.utils.SearchController;
import controllers.utils.SpinnerController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import models.configuracoes.Config;
import models.configuracoes.ConfigDAO;
import models.configuracoes.Empresa;
import models.configuracoes.EmpresaDAO;
import tools.controls.ComboBoxFilter;
import tools.enums.EnumCriterioPrecoFilial;
import tools.enums.EnumTipoConsultaProdutosDefaultBuscaItens;
import tools.enums.EnumTipoControleLote;
import tools.enums.EnumTipoFreteModuloVendas;
import tools.enums.EnumTipocCodigoTelaVendas;
import tools.utils.LogRetorno;
import tools.utils.Util;

public class AjustesSistemaController implements Initializable {

	@FXML
	private AnchorPane anchorPanePrincipal;
	
	@FXML
	private Pane internalPane;

	@FXML
	private ToolBar toolBarDetalhes;

	@FXML
	private Button btnSave, btnCancel, btnFileChooser;

	@FXML
	private CustomTextField txtBuscarContenido;

	@FXML
	private TextField txtCodigo, txtNome,txtDiretorioXml;

	@FXML
	private TabPane tabPane, tabPaneParg, tabPaneComp, tabPaneVend, tabPaneFinc, tabPaneImg, tabPaneSeg;

	@FXML
	private Tab id, tabParg, tabComp, tabVend, tabFinc, tabLivf, tabRhum, tabServ, tabProd, tabCont, tabConf, tabImg,
			tabSeg;

	@FXML
	private Label lblCodigo;

	@FXML
	private Label lblNome;

	// Tab Parametros Gerais
	@FXML
	private Label lblGerQtdCaracterbusca;

	@FXML
	private Spinner<Integer> spnGerQtdCaracterbusca;

	@FXML
	private Label lblGerFlagModulofidelidade;

	@FXML
	private JFXToggleButton tgbGerFlagModulofidelidade;

	@FXML
	private Label lblGerPerccargatribgeral;

	@FXML
	private TextField txtGerPerccargatribgeral;

	@FXML
	private Label lblGerFlagEmailcopyoculta;

	@FXML
	private JFXToggleButton tgbGerFlagEmailcopyoculta;

	@FXML
	private Label lblGerEmailoculta;

	@FXML
	private TextField txtGerEmailoculta;

	@FXML
	private Label lblGerFlagNotificacao;

	@FXML
	private JFXToggleButton tgbGerFlagNotificacao;

	@FXML
	private Label lblGerNotificacaoestilo;

	@FXML
	private RadioButton rdbGerNotificacaoestilo0;

	@FXML
	private ToggleGroup grpGerNotificacaoestilo;

	@FXML
	private RadioButton rdbGerNotificacaoestilo1;

	@FXML
	private Label lblGerNotificacaoposicao, lblGerCertDigital, lblGerCertificado, lblGerDataVencto, lblGerDiasRest;

	@FXML
	private RadioButton rdbGerNotificacaoposicao0;

	@FXML
	private ToggleGroup grpGerNotificacaoposicao;

	@FXML
	private RadioButton rdbGerNotificacaoposicao1;

	@FXML
	private RadioButton rdbGerNotificacaoposicao2;

	@FXML
	private RadioButton rdbGerNotificacaoposicao3;

	@FXML
	private RadioButton rdbGerNotificacaoposicao4;

	@FXML
	private CustomTextField txtCertificado;

	@FXML
	private TextField txtDataVencto, txtDiasRestant;

	// Tab Compras
	@FXML
	private Label lblCpaTiposequenciaprod;

	@FXML
	private RadioButton rdbCpaTiposequenciaprod0;

	@FXML
	private ToggleGroup grpCpaTiposequenciaprod;

	@FXML
	private RadioButton rdbCpaTiposequenciaprod1;

	@FXML
	private RadioButton rdbCpaTiposequenciaprod2;

	@FXML
	private Label lblCpaQtdcasasdecimaisestoque;

	@FXML
	private RadioButton rdbCpaQtdcasasdecimaisestoque2;

	@FXML
	private ToggleGroup grpCpaQtdcasasdecimaisestoque;

	@FXML
	private RadioButton rdbCpaQtdcasasdecimaisestoque3;

	@FXML
	private RadioButton rdbCpaQtdcasasdecimaisestoque4;

	@FXML
	private Label lblCpaQtdcasasdecimaisvalor;

	@FXML
	private RadioButton rdbCpaQtdcasasdecimaisvalor2;

	@FXML
	private ToggleGroup grpCpaQtdcasasdecimaisvalor;

	@FXML
	private RadioButton rdbCpaQtdcasasdecimaisvalor3;

	@FXML
	private RadioButton rdbCpaQtdcasasdecimaisvalor4;

	@FXML
	private Label lblCpaTipocodprodutocompras;

	@FXML
	private RadioButton rdbCpaTipocodprodutocompras0;

	@FXML
	private ToggleGroup grpCpaTipocodprodutocompras;

	@FXML
	private RadioButton rdbCpaTipocodprodutocompras1;

	@FXML
	private RadioButton rdbCpaTipocodprodutocompras2;

	@FXML
	private Label lblCpaFlagDadosultimoproduto;

	@FXML
	private JFXToggleButton tgbCpaFlagDadosultimoproduto;

	@FXML
	private Label lblCpaFlagControlelote;

	@FXML
	private JFXToggleButton tgbCpaFlagControlelote;

	@FXML
	private Label lblCpaTipolotedefault;

	@FXML
	private ComboBox<ComboBoxFilter> cboxCpaTipolotedefault;

	@FXML
	private Label lblCpaFlagDadosveiculo;

	@FXML
	private JFXToggleButton tgbCpaFlagDadosveiculo;

	@FXML
	private Text lblCpaFlagTabprogressivadescprod;

	@FXML
	private JFXToggleButton tgbCpaFlagTabprogressivadescprod;

	@FXML
	private Label lblCpaTipocalculopreco;

	@FXML
	private RadioButton rdbCpaTipocalculopreco0;

	@FXML
	private ToggleGroup grpCpaTipocalculopreco;

	@FXML
	private RadioButton rdbCpaTipocalculopreco1;

	@FXML
	private Label lblCpaCriterioprecosfiliais;

	@FXML
	private Label lblCpaFlagTribautomaticafiliais;

	@FXML
	private JFXToggleButton tgbCpaFlagTribautomaticafiliais;

	@FXML
	private Text lblCpaFlagEnviaobsitensvenda;

	@FXML
	private JFXToggleButton tgbCpaFlagEnviaobsitensvenda;

	@FXML
	private Text lblCpaQtddiaschegadanfe;

	@FXML
	private Spinner<Integer> spnCpaQtddiaschegadanfe;

	// Tab Vendas
	@FXML
	private Label lblVdaTiposequenciacli;

	@FXML
	private RadioButton rdbVdaTiposequenciacli0;

	@FXML
	private ToggleGroup grpVdaTiposequenciacli;

	@FXML
	private RadioButton rdbVdaTiposequenciacli1;

	@FXML
	private RadioButton rdbVdaTiposequenciacli2;

	@FXML
	private Label lblVdaFlagJustificaliberacao;

	@FXML
	private JFXToggleButton tgbVdaFlagJustificaliberacao;

	@FXML
	private Label lblVdaFlagBloqvdacadvencido;

	@FXML
	private JFXToggleButton tgbVdaFlagBloqvdacadvencido;

	@FXML
	private Label lblVdaDiasBloqvdacadvencido;

	@FXML
	private Spinner<Integer> spnVdaDiasBloqvdacadvencido;

	@FXML
	private Label lblVdaFlagBloqvdainadimplente;

	@FXML
	private JFXToggleButton tgbVdaFlagBloqvdainadimplente;

	@FXML
	private Label lblVdaDiasBloqvdainadimplente;

	@FXML
	private Spinner<Integer> spnVdaDiasBloqvdainadimplente;

	@FXML
	private JFXToggleButton tgbVdaFlagBloqvdadesatualizado;

	@FXML
	private Label lblVdaFlagBloqvdadesatualizado;

	@FXML
	private Label lblVdaDiasBloqvdadesatualizado;

	@FXML
	private Spinner<Integer> spnVdaDiasBloqvdadesatualizado;

	@FXML
	private JFXToggleButton tgbVdaFlagBloqvdasemmovimento;

	@FXML
	private Label lblVdaFlagBloqvdasemmovimento;

	@FXML
	private Label lblVdaDiasBloqvdasemmovimento;

	@FXML
	private Spinner<Integer> spnVdaDiasBloqvdasemmovimento;

	@FXML
	private Label lblVdaLimiteCreditoDefault;

	@FXML
	private TextField txtVdaLimiteCreditoDefault;

	@FXML
	private Label lblVdaTipolimcreditoClientes;

	@FXML
	private RadioButton rdbVdaTipolimcreditoClientes0;

	@FXML
	private ToggleGroup grpVdaTipolimcreditoClientes;

	@FXML
	private RadioButton rdbVdaTipolimcreditoClientes1;

	@FXML
	private Label lblVdaFlagVinculaVendedorcli;

	@FXML
	private RadioButton rdbVdaFlagVinculaVendedorcli1;

	@FXML
	private ToggleGroup grpVdaFlagVinculaVendedorcli;

	@FXML
	private RadioButton rdbVdaFlagVinculaVendedorcli0;

	@FXML
	private RadioButton rdbVdaFlagVinculaVendedorcli2;

	@FXML
	private JFXToggleButton tgbVdaFlagUtilizaCliassociados;

	@FXML
	private Label lblVdaFlagUtilizaCliassociados;

	@FXML
	private Label lblVdaTipolimcreditoCliassociados;

	@FXML
	private RadioButton rdbVdaTipolimcreditoCliassociados0;

	@FXML
	private ToggleGroup grpVdaTipolimcreditoCliassociados;

	@FXML
	private RadioButton rdbVdaTipolimcreditoCliassociados1;

	@FXML
	private Label lblVdaFlagEnviaclicartorio;

	@FXML
	private JFXToggleButton tgbVdaFlagEnviaclicartorio;

	@FXML
	private Label lblVdaDiasEnviocartorio;

	@FXML
	private Spinner<Integer> spnVdaDiasEnviocartorio;

	@FXML
	private Label lblVdaFlagExibedadosfiliaisVenda;

	@FXML
	private JFXToggleButton tgbVdaFlagExibedadosfiliaisVenda;

	@FXML
	private Label lblVdaTipoExibedadosfiliasVenda;

	@FXML
	private RadioButton rdbVdaTipoExibedadosfiliasVenda0;

	@FXML
	private ToggleGroup grpVdaTipoExibedadosfiliasVenda;

	@FXML
	private RadioButton rdbVdaTipoExibedadosfiliasVenda1;

	@FXML
	private Label lblVdaFlagDescontoprodsPromocao;

	@FXML
	private JFXToggleButton tgbVdaFlagDescontoprodsPromocao;

	@FXML
	private Label lblVdaFlagExibeprodsSimilares;

	@FXML
	private JFXToggleButton tgbVdaFlagExibeprodsSimilares;

	@FXML
	private Label lblVdaComissaoLocalorigem;

	@FXML
	private Button btnVdaComissaoLocalorigem;

	@FXML
	private RadioButton rdbVdaComissaoLocalorigem0;

	@FXML
	private ToggleGroup grpVdaComissaoLocalorigem;

	@FXML
	private RadioButton rdbVdaComissaoLocalorigem1;

	@FXML
	private RadioButton rdbVdaComissaoLocalorigem2;

	@FXML
	private RadioButton rdbVdaComissaoLocalorigem3;

	@FXML
	private RadioButton rdbVdaComissaoLocalorigem4;

	@FXML
	private Label lblVdaComissaoTipopagamento;

	@FXML
	private RadioButton rdbVdaComissaoTipopagamento0;

	@FXML
	private ToggleGroup grpVdaComissaoTipopagamento;

	@FXML
	private RadioButton rdbVdaComissaoTipopagamento1;

	@FXML
	private Label lblVdaFiltrodefaultBuscaitens;

	@FXML
	private ComboBox<ComboBoxFilter> cboxVdaFiltrodefaultBuscaitens;

	@FXML
	private Label lblVdaDiasvalidadeProposta;

	@FXML
	private Spinner<Integer> spnVdaDiasvalidadeProposta;

	@FXML
	private Label lblVdaFreteTipodefault;

	@FXML
	private ComboBox<ComboBoxFilter> cboxVdaFreteTipodefault;

	@FXML
	private Label lblVdaFlagUtilizatabelaPrecocli;

	@FXML
	private JFXToggleButton tgbVdaFlagUtilizatabelaPrecocli;

	@FXML
	private Label lblVdaTipoidentificacaoVendedor;

	@FXML
	private RadioButton rdbVdaTipoidentificacaoVendedor0;

	@FXML
	private ToggleGroup grpVdaTipoidentificacaoVendedor;

	@FXML
	private RadioButton rdbVdaTipoidentificacaoVendedor1;

	@FXML
	private Label lblVdaTipocodigoTeladevendas;

	@FXML
	private ComboBox<ComboBoxFilter> cboxVdaTipocodigoTeladevendas, cBoxCpaCriterioPrecoFilial;

	@FXML
	private Text lblVdaFlagCodigobalanca;

	@FXML
	private JFXToggleButton tgbVdaFlagCodigobalanca;

	@FXML
	private Text lblVdaCodigobalancaQtddigitos;

	@FXML
	private RadioButton rdbVdaCodigobalancaQtddigitos4;

	@FXML
	private ToggleGroup grpVdaCodigobalancaQtddigitos;

	@FXML
	private RadioButton rdbVdaCodigobalancaQtddigitos5;

	@FXML
	private RadioButton rdbVdaCodigobalancaQtddigitos6;

	// Tab Financiero
	@FXML
	private Label lblFinDiascarenciaGerarjuros;

	@FXML
	private TextField txtFinDiascarenciaGerarjuros;

	@FXML
	private Label lblFinMultactasrec;

	@FXML
	private TextField txtFinMultactasrec;

	@FXML
	private Label lblFinTaxajuros;

	@FXML
	private TextField txtFinTaxajuros;

	@FXML
	private Label lblFinTipointevaloTaxajuros;

	@FXML
	private RadioButton rdbFinTipointevaloTaxajuros0;

	@FXML
	private ToggleGroup grpFinTipointevaloTaxajuros;

	@FXML
	private RadioButton rdbFinTipointevaloTaxajuros1;

	@FXML
	private Label lblFinTaxajurosMinima;

	@FXML
	private TextField txtFinTaxajurosMinima;

	// Tab Livros Fiscais

	// Tab Recursos Humanos

	// Tab Automat

	// Tab Produção

	// Tab Contabilidade

	// Tab Configurações

	// Tab Contabilidade

	// Tab Images
	@FXML
	private Label lblImgLogomarca;
	@FXML
	private Button btnImgLogomarca;
	@FXML
	private ImageView imgImgLogomarca;

	// TabPane Validacao de Seguranca
	@FXML
	private Spinner<Integer> spnSegPrazoAlterasenha, spnSegQtdcaracter;

	@FXML
	private Label lblSegFlagAltsenhaperio, lblSegPrazoAlterasenha, lblSegPrazoAlterasenhadias, lblSegQtdcaracter,
			lblSegSeqdigitos, lblSegFlagContemletra, lblSegFlagReutilizapwd;

	@FXML
	private JFXToggleButton tgbSegFlagAltsenhaperio, tgbSegSeqdigitos, tgbSegFlagContemletra, tgbSegFlagReutilizapwd;

	// Attributes used in the class
	public Empresa empresa = new Empresa();
	public Config config = new Config();
	public ConfigDAO configDAO = new ConfigDAO();
	public EmpresaDAO empDAO = new EmpresaDAO();
	static Stage stg;
	TabPane tpPrincipal;
	public Util util = new Util();

	public ObservableList<ComboBoxFilter> listCboxCpaTipolotedefault = FXCollections.observableArrayList();
	public ObservableList<ComboBoxFilter> listCboxCpaCriterioPrecoFilial = FXCollections.observableArrayList();
	public ObservableList<ComboBoxFilter> listCboxVdaFiltrodefaultBuscaitens = FXCollections.observableArrayList();
	public ObservableList<ComboBoxFilter> listCboxVdaFreteTipodefault = FXCollections.observableArrayList();
	public ObservableList<ComboBoxFilter> listCboxVdaTipocodigoTeladevendas = FXCollections.observableArrayList();

	

	@FXML
	void actionBtnFileChooserXML(ActionEvent event) {

		DirectoryChooser directory = new DirectoryChooser();
		
		directory.setTitle("Selecione o Diretório para Armazenar o XML");

		File f = directory.showDialog(anchorPanePrincipal.getScene().getWindow());
				
		txtDiretorioXml.setText(f.getAbsolutePath());
		

	}

	
	public void actionToggleButtons() {

		tgbGerFlagNotificacao.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if (newValue) {
					lblGerNotificacaoestilo.setDisable(false);
					rdbGerNotificacaoestilo0.setDisable(false);
					rdbGerNotificacaoestilo1.setDisable(false);
					lblGerNotificacaoposicao.setDisable(false);
					rdbGerNotificacaoposicao0.setDisable(false);
					rdbGerNotificacaoposicao1.setDisable(false);
					rdbGerNotificacaoposicao2.setDisable(false);
					rdbGerNotificacaoposicao3.setDisable(false);
					rdbGerNotificacaoposicao4.setDisable(false);
				} else {
					lblGerNotificacaoestilo.setDisable(true);
					rdbGerNotificacaoestilo0.setDisable(true);
					rdbGerNotificacaoestilo1.setDisable(true);
					lblGerNotificacaoposicao.setDisable(true);
					rdbGerNotificacaoposicao0.setDisable(true);
					rdbGerNotificacaoposicao1.setDisable(true);
					rdbGerNotificacaoposicao2.setDisable(true);
					rdbGerNotificacaoposicao3.setDisable(true);
					rdbGerNotificacaoposicao4.setDisable(true);

				}
			}
		});

		tgbSegFlagAltsenhaperio.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if (newValue) {
					lblSegPrazoAlterasenha.setDisable(false);
					lblSegPrazoAlterasenhadias.setDisable(false);
					spnSegPrazoAlterasenha.setDisable(false);
				} else {
					lblSegPrazoAlterasenha.setDisable(true);
					lblSegPrazoAlterasenhadias.setDisable(true);
					spnSegPrazoAlterasenha.setDisable(true);
				}
			}
		});

		tgbGerFlagEmailcopyoculta.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if (newValue) {
					lblGerEmailoculta.setDisable(false);
					txtGerEmailoculta.setDisable(false);
				} else {
					lblGerEmailoculta.setDisable(true);
					txtGerEmailoculta.setDisable(true);
				}
			}
		});

		tgbCpaFlagControlelote.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if (newValue) {
					lblCpaTipolotedefault.setDisable(false);
					cboxCpaTipolotedefault.setDisable(false);
				} else {
					lblCpaTipolotedefault.setDisable(true);
					cboxCpaTipolotedefault.setDisable(true);
				}
			}
		});

		tgbVdaFlagBloqvdacadvencido.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if (newValue) {
					lblVdaDiasBloqvdacadvencido.setDisable(false);
					spnVdaDiasBloqvdacadvencido.setDisable(false);
				} else {
					lblVdaDiasBloqvdacadvencido.setDisable(true);
					spnVdaDiasBloqvdacadvencido.setDisable(true);
				}
			}
		});

		tgbVdaFlagBloqvdainadimplente.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if (newValue) {
					lblVdaDiasBloqvdainadimplente.setDisable(false);
					spnVdaDiasBloqvdainadimplente.setDisable(false);
				} else {
					lblVdaDiasBloqvdainadimplente.setDisable(true);
					spnVdaDiasBloqvdainadimplente.setDisable(true);
				}
			}
		});

		tgbVdaFlagBloqvdadesatualizado.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if (newValue) {
					lblVdaDiasBloqvdadesatualizado.setDisable(false);
					spnVdaDiasBloqvdadesatualizado.setDisable(false);
				} else {
					lblVdaDiasBloqvdadesatualizado.setDisable(true);
					spnVdaDiasBloqvdadesatualizado.setDisable(true);
				}
			}
		});

		tgbVdaFlagBloqvdasemmovimento.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if (newValue) {
					lblVdaDiasBloqvdasemmovimento.setDisable(false);
					spnVdaDiasBloqvdasemmovimento.setDisable(false);
				} else {
					lblVdaDiasBloqvdasemmovimento.setDisable(true);
					spnVdaDiasBloqvdasemmovimento.setDisable(true);
				}
			}
		});

		tgbVdaFlagUtilizaCliassociados.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if (newValue) {
					lblVdaTipolimcreditoCliassociados.setDisable(false);
					rdbVdaTipolimcreditoCliassociados0.setDisable(false);
					rdbVdaTipolimcreditoCliassociados1.setDisable(false);
				} else {
					lblVdaTipolimcreditoCliassociados.setDisable(true);
					rdbVdaTipolimcreditoCliassociados0.setDisable(true);
					rdbVdaTipolimcreditoCliassociados1.setDisable(true);
				}
			}
		});

		tgbVdaFlagEnviaclicartorio.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if (newValue) {
					lblVdaDiasEnviocartorio.setDisable(false);
					spnVdaDiasEnviocartorio.setDisable(false);
				} else {
					lblVdaDiasEnviocartorio.setDisable(true);
					spnVdaDiasEnviocartorio.setDisable(true);
				}
			}
		});

		tgbVdaFlagExibedadosfiliaisVenda.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if (newValue) {
					lblVdaTipoExibedadosfiliasVenda.setDisable(false);
					rdbVdaTipoExibedadosfiliasVenda0.setDisable(false);
					rdbVdaTipoExibedadosfiliasVenda1.setDisable(false);
				} else {
					lblVdaTipoExibedadosfiliasVenda.setDisable(true);
					rdbVdaTipoExibedadosfiliasVenda0.setDisable(true);
					rdbVdaTipoExibedadosfiliasVenda1.setDisable(true);
				}
			}
		});

		tgbVdaFlagCodigobalanca.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if (newValue) {
					lblVdaCodigobalancaQtddigitos.setOpacity(1);
					rdbVdaCodigobalancaQtddigitos4.setDisable(false);
					rdbVdaCodigobalancaQtddigitos5.setDisable(false);
					rdbVdaCodigobalancaQtddigitos6.setDisable(false);
				} else {
					lblVdaCodigobalancaQtddigitos.setOpacity(0.3);
					rdbVdaCodigobalancaQtddigitos4.setDisable(true);
					rdbVdaCodigobalancaQtddigitos5.setDisable(true);
					rdbVdaCodigobalancaQtddigitos6.setDisable(true);
				}
			}
		});
	}

	@FXML
	void actionBtnSave(ActionEvent event) {

		int month = Integer.parseInt(txtDataVencto.getText().substring(3, 5));
		int day = Integer.parseInt(txtDataVencto.getText().substring(0, 2));
		int year = Integer.parseInt(txtDataVencto.getText().substring(6, 10));
		// System.out.println("day: "+day+" month: "+month+" year: "+year);
		LocalDate localDate = LocalDate.of(year, month, day);

		// Parametros Gerais
		empresa.getConfig().setGerQtdCaracterbusca(spnGerQtdCaracterbusca.getValue());
		empresa.getConfig().setGerFlagModulofidelidade(tgbGerFlagModulofidelidade.isSelected() ? 1 : 0);
		empresa.getConfig().setGerPerccargatribgeral(Util.decimalBRtoBigDecimal(2, txtGerPerccargatribgeral.getText()));
		empresa.getConfig().setGerFlagEmailcopyoculta(tgbGerFlagEmailcopyoculta.isSelected() ? 1 : 0);
		empresa.getConfig().setGerEmailoculta(txtGerEmailoculta.getText());
		empresa.getConfig().setGerFlagNotificacao(tgbGerFlagNotificacao.isSelected() ? 1 : 0);
		empresa.getConfig().setGerNotificacaoestilo(
				Integer.valueOf(grpGerNotificacaoestilo.selectedToggleProperty().getValue().getUserData().toString()));
		empresa.getConfig().setGerNotificacaoposicao(Integer.valueOf(grpGerNotificacaoposicao.selectedToggleProperty().getValue().getUserData().toString()));
		// System.out.println(txtCertificado.getText());
		empresa.getConfig().setGerCertificadoDigital(txtCertificado.getText());
		empresa.getConfig().setGerCertificadoDtVencto(LocalDateTime.of(localDate, LocalTime.now()).toString());

		// TabPane Compras
		empresa.getConfig().setCpaTiposequenciaprod(
				Integer.valueOf(grpCpaTiposequenciaprod.selectedToggleProperty().getValue().getUserData().toString()));
		empresa.getConfig().setCpaQtdcasasdecimaisestoque(Integer
				.valueOf(grpCpaQtdcasasdecimaisestoque.selectedToggleProperty().getValue().getUserData().toString()));
		empresa.getConfig().setCpaQtdcasasdecimaisvalor(Integer
				.valueOf(grpCpaQtdcasasdecimaisvalor.selectedToggleProperty().getValue().getUserData().toString()));
		empresa.getConfig().setCpaTipocodprodutocompras(Integer
				.valueOf(grpCpaTipocodprodutocompras.selectedToggleProperty().getValue().getUserData().toString()));
		empresa.getConfig().setCpaFlagDadosultimoproduto(tgbCpaFlagDadosultimoproduto.isSelected() ? 1 : 0);
		empresa.getConfig().setCpaFlagControlelote(tgbCpaFlagControlelote.isSelected() ? 1 : 0);
		empresa.getConfig().setCpaTipolotedefault(cboxCpaTipolotedefault.getSelectionModel().getSelectedIndex());
		empresa.getConfig().setCpaFlagDadosveiculo(tgbCpaFlagDadosveiculo.isSelected() ? 1 : 0);
		empresa.getConfig().setCpaFlagTabprogressivadescprod(tgbCpaFlagTabprogressivadescprod.isSelected() ? 1 : 0);
		empresa.getConfig().setCpaTipocalculopreco(
				Integer.valueOf(grpCpaTipocalculopreco.selectedToggleProperty().getValue().getUserData().toString()));
		empresa.getConfig().setCpaCriterioprecosfiliais(((EnumCriterioPrecoFilial)cBoxCpaCriterioPrecoFilial.getSelectionModel().getSelectedItem().getEnumOpcao()).criterioPreco);
		empresa.getConfig().setCpaFlagTribautomaticafiliais(tgbCpaFlagTribautomaticafiliais.isSelected() ? 1 : 0);
		empresa.getConfig().setCpaFlagEnviaobsitensvenda(tgbCpaFlagEnviaobsitensvenda.isSelected() ? 1 : 0);
		empresa.getConfig().setCpaQtddiaschegadanfe(spnCpaQtddiaschegadanfe.getValue());
		empresa.getConfig().setCpaDiretorioXml(txtDiretorioXml.getText());
		
		
		// TabPane Vendas
		empresa.getConfig().setVdaTiposequenciacli(
				Integer.valueOf(grpVdaTiposequenciacli.selectedToggleProperty().getValue().getUserData().toString()));
		empresa.getConfig().setVdaFlagJustificaliberacao(tgbVdaFlagJustificaliberacao.isSelected() ? 1 : 0);
		empresa.getConfig().setVdaFlagBloqvdacadvencido(tgbVdaFlagBloqvdacadvencido.isSelected() ? 1 : 0);
		empresa.getConfig().setVdaDiasBloqvdacadvencido(spnVdaDiasBloqvdacadvencido.getValue());
		empresa.getConfig().setVdaFlagBloqvdainadimplente(tgbVdaFlagBloqvdainadimplente.isSelected() ? 1 : 0);
		empresa.getConfig().setVdaDiasBloqvdainadimplente(spnVdaDiasBloqvdainadimplente.getValue());
		empresa.getConfig().setVdaFlagBloqvdadesatualizado(tgbVdaFlagBloqvdadesatualizado.isSelected() ? 1 : 0);
		empresa.getConfig().setVdaDiasBloqvdadesatualizado(spnVdaDiasBloqvdadesatualizado.getValue());
		empresa.getConfig().setVdaFlagBloqvdasemmovimento(tgbVdaFlagBloqvdasemmovimento.isSelected() ? 1 : 0);
		empresa.getConfig().setVdaDiasBloqvdasemmovimento(spnVdaDiasBloqvdasemmovimento.getValue());
		empresa.getConfig().setVdaLimiteCreditoDefault(Util.decimalBRtoBigDecimal(2, txtVdaLimiteCreditoDefault.getText()));
		empresa.getConfig().setVdaTipolimcreditoClientes(Integer
				.valueOf(grpVdaTipolimcreditoClientes.selectedToggleProperty().getValue().getUserData().toString()));
		empresa.getConfig().setVdaFlagVinculaVendedorcli(Integer
				.valueOf(grpVdaFlagVinculaVendedorcli.selectedToggleProperty().getValue().getUserData().toString()));
		empresa.getConfig().setVdaFlagUtilizaCliassociados(tgbVdaFlagUtilizaCliassociados.isSelected() ? 1 : 0);
		empresa.getConfig().setVdaTipolimcreditoCliassociados(Integer.valueOf(
				grpVdaTipolimcreditoCliassociados.selectedToggleProperty().getValue().getUserData().toString()));
		empresa.getConfig().setVdaFlagEnviaclicartorio(tgbVdaFlagEnviaclicartorio.isSelected() ? 1 : 0);
		empresa.getConfig().setVdaDiasEnviocartorio(spnVdaDiasEnviocartorio.getValue());
		empresa.getConfig().setVdaFlagExibedadosfiliaisVenda(tgbVdaFlagExibedadosfiliaisVenda.isSelected() ? 1 : 0);
		empresa.getConfig().setVdaTipoExibedadosfiliasVenda(Integer
				.valueOf(grpVdaTipoExibedadosfiliasVenda.selectedToggleProperty().getValue().getUserData().toString()));
		empresa.getConfig().setVdaFlagDescontoprodsPromocao(tgbVdaFlagDescontoprodsPromocao.isSelected() ? 1 : 0);
		empresa.getConfig().setVdaFlagExibeprodsSimilares(tgbVdaFlagExibeprodsSimilares.isSelected() ? 1 : 0);
		empresa.getConfig().setVdaComissaoLocalorigem(Integer
				.valueOf(grpVdaComissaoLocalorigem.selectedToggleProperty().getValue().getUserData().toString()));
		empresa.getConfig().setVdaComissaoTipopagamento(Integer
				.valueOf(grpVdaComissaoTipopagamento.selectedToggleProperty().getValue().getUserData().toString()));
		empresa.getConfig().setVdaFiltrodefaultBuscaitens(cboxVdaFiltrodefaultBuscaitens.getSelectionModel().getSelectedIndex());
		empresa.getConfig().setVdaDiasvalidadeProposta(spnVdaDiasvalidadeProposta.getValue());
		empresa.getConfig().setVdaFreteTipodefault(cboxVdaFreteTipodefault.getSelectionModel().getSelectedIndex());
		empresa.getConfig().setVdaFlagUtilizatabelaPrecocli(tgbVdaFlagUtilizatabelaPrecocli.isSelected() ? 1 : 0);
		empresa.getConfig().setVdaTipoidentificacaoVendedor(Integer
				.valueOf(grpVdaTipoidentificacaoVendedor.selectedToggleProperty().getValue().getUserData().toString()));
		empresa.getConfig().setVdaTipocodigoTeladevendas(cboxVdaTipocodigoTeladevendas.getSelectionModel().getSelectedIndex());
		empresa.getConfig().setVdaFlagCodigobalanca(tgbVdaFlagCodigobalanca.isSelected() ? 1 : 0);
		empresa.getConfig().setVdaCodigobalancaQtddigitos(Integer
				.valueOf(grpVdaCodigobalancaQtddigitos.selectedToggleProperty().getValue().getUserData().toString()));

		// TabPane Financiero
		empresa.getConfig().setFinDiascarenciaGerarjuros(Integer.valueOf(txtFinDiascarenciaGerarjuros.getText()));
		empresa.getConfig().setFinMultactasrec(Util.decimalBRtoBigDecimal(2, txtFinMultactasrec.getText()));
		empresa.getConfig().setFinTaxajuros(Util.decimalBRtoBigDecimal(2, txtFinTaxajuros.getText()));
		empresa.getConfig().setFinTipointevaloTaxajuros(Integer
				.valueOf(grpFinTipointevaloTaxajuros.selectedToggleProperty().getValue().getUserData().toString()));
		empresa.getConfig().setFinTaxajurosMinima(Util.decimalBRtoBigDecimal(2, txtFinTaxajurosMinima.getText()));

		// Validaçao de Segurança
		empresa.getConfig().setSegFlagAltsenhaperio(tgbSegFlagAltsenhaperio.isSelected() ? 1 : 0);
		empresa.getConfig().setSegPrazoAlterasenha(spnSegPrazoAlterasenha.getValue());
		empresa.getConfig().setSegQtdcaracter(spnSegQtdcaracter.getValue());
		empresa.getConfig().setSegSeqdigitos(tgbSegSeqdigitos.isSelected() ? 1 : 0);
		empresa.getConfig().setSegFlagContemletra(tgbSegFlagContemletra.isSelected() ? 1 : 0);
		empresa.getConfig().setSegFlagReutilizapwd(tgbSegFlagReutilizapwd.isSelected() ? 1 : 0);

		// Image
		File file = new File("C:\\EptusAM\\System.tmp\\emp" + txtCodigo.getText() + "\\logo.png");
		if (file.exists()) {
			byte[] bFile = new byte[(int) file.length()];

			try {
				FileInputStream fileInputStream = new FileInputStream(file);
				fileInputStream.read(bFile);
				fileInputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			empresa.getConfig().setImgLogomarca(bFile);
		}

		if (1 == 1) {

			Task<String> tarefaCargaPg = new Task<String>() {
				@Override
				protected String call() throws Exception {
					configDAO.update(empresa.getConfig());
					return "-";
				}

				@Override
				protected void succeeded() {
					stg.close();
				}

				@Override
				protected void failed() {
					stg.close();
					util.showAlert(DadosGlobais.resourceBundle.getString("alertErrorCnxDb"), "error");
				}

				@Override
				protected void cancelled() {
					super.cancelled();
				}
			};
			Thread t = new Thread(tarefaCargaPg);
			t.setDaemon(true);
			t.start();
			stg = ProgressBarForm.showProgressBar(EmpresaController.class, tarefaCargaPg,
					DadosGlobais.resourceBundle.getString("infConsulRegist"), false);

		}

	}

	@FXML
	void actionBtnConfigCertificado(ActionEvent event) {
		try {
			stg = new Stage();
			Scene scene = new Scene(util.openWindow("/views/utils/viewConfigCertificado.fxml",
					new ConfigCertificadoController(txtCertificado, txtDataVencto, txtDiasRestant)));
			stg.setScene(scene);
			stg.initStyle(StageStyle.TRANSPARENT);
			stg.initModality(Modality.APPLICATION_MODAL);
			stg.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void actionBtnCancel(ActionEvent event) {
		Util.setDefaultStyle(txtCodigo);
		tarefaConsulta("current");

	}

	@FXML
	void actionBtnImgLogomarca(ActionEvent event) throws InstantiationException, IllegalAccessException, IOException {
		FileChooser fileChooser = new FileChooser();

		fileChooser.setTitle("Select the image");
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("BMP", "*.bmp"));

		File file = fileChooser.showOpenDialog(null);

		if (file != null) {
			String url = "C:\\EptusAM\\System.tmp\\emp" + txtCodigo.getText() + "\\logo.png";
			imgImgLogomarca.setImage(Util.resizeImages(file, url));
		}

	}

	@FXML
	void keyPressedTxtCodigo(KeyEvent event) {

		Util.setDefaultStyle(txtCodigo);

		if ((event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.TAB)) && !Util.noEmpty(txtCodigo))
			tabPane.requestFocus();

		if (event.getCode().equals(KeyCode.F2)) {

			// try {
			// stg = new Stage();
			// Scene scene = new
			// Scene(util.openWindow("/views/utils/viewSearch.fxml",new
			// SearchController()));
			// scene.getStylesheets().add(getClass().getResource("/styles/css/themes
			// "+DadosGlobais.empresaLogada.getSistema()+"/style_"+DadosGlobais.empresaLogada.getSistema()+".css").toExternalForm());
			// stg.setScene(scene);
			// stg.initStyle(StageStyle.TRANSPARENT);
			// stg.initModality(Modality.APPLICATION_MODAL);
			// stg.show();
			// } catch (IOException e) {
			// util.tratamentoExcecao(e.getMessage().toString(), "[
			// AjustesSistemaController.keyPressedTxtCodigo() ]");
			// }
		}

	}

	@FXML
	void keyTypedTxtCodigo(KeyEvent event) {
		Util.isLetter(event);
	}

	public void setDatosConfig() {
		List<Certificado> listCertificate = Util.getAllCertificate();

		txtCodigo.setText(String.valueOf(empresa.getCodemp()));
		txtNome.setText(empresa.getNomeFantasia());
		if (empresa.getConfig().getSegSeqdigitos() != null
				&& empresa.getConfig().getSegFlagContemletra() != null
				&& empresa.getConfig().getSegFlagReutilizapwd() != null) {

			// Parametros Gerais
			spnGerQtdCaracterbusca.getValueFactory().setValue(empresa.getConfig().getGerQtdCaracterbusca());
			tgbGerFlagModulofidelidade
					.setSelected(empresa.getConfig().getGerFlagModulofidelidade() == 1 ? true : false);
			txtGerPerccargatribgeral.setText(empresa.getConfig().getGerPerccargatribgeral()
					.setScale(2, BigDecimal.ROUND_HALF_EVEN).toString());
			tgbGerFlagEmailcopyoculta
					.setSelected(empresa.getConfig().getGerFlagEmailcopyoculta() == 1 ? true : false);
			txtGerEmailoculta.setText(empresa.getConfig().getGerEmailoculta());
			tgbGerFlagNotificacao.setSelected(empresa.getConfig().getGerFlagNotificacao() == 1 ? true : false);
			Util.setSelectRadioButton(empresa.getConfig().getGerNotificacaoestilo(), grpGerNotificacaoestilo);
			Util.setSelectRadioButton(empresa.getConfig().getGerNotificacaoposicao(), grpGerNotificacaoposicao);
			txtCertificado.setText(empresa.getConfig().getGerCertificadoDigital());		
			txtDataVencto.setText(Util.dateFormatInToOut(empresa.getConfig().getGerCertificadoDtVencto(), "yyyy-MM-dd", "dd/MM/yyyy"));
			
			for (int i = 0; i < listCertificate.size(); i++) {
				if (txtCertificado.getText().equals(listCertificate.get(i).getNome())) {
					txtDiasRestant.setText(listCertificate.get(i).getDiasRestantes().toString());
					break;
				}
			}

			// TabPane Compras
			Util.setSelectRadioButton(empresa.getConfig().getCpaTiposequenciaprod(), grpCpaTiposequenciaprod);
			Util.setSelectRadioButton(empresa.getConfig().getCpaQtdcasasdecimaisestoque(),
					grpCpaQtdcasasdecimaisestoque);
			Util.setSelectRadioButton(empresa.getConfig().getCpaQtdcasasdecimaisvalor(),
					grpCpaQtdcasasdecimaisvalor);
			Util.setSelectRadioButton(empresa.getConfig().getCpaTipocodprodutocompras(),
					grpCpaTipocodprodutocompras);
			tgbCpaFlagDadosultimoproduto
					.setSelected(empresa.getConfig().getCpaFlagDadosultimoproduto() == 1 ? true : false);
			tgbCpaFlagControlelote.setSelected(empresa.getConfig().getCpaFlagControlelote() == 1 ? true : false);
			cboxCpaTipolotedefault.getSelectionModel().select(empresa.getConfig().getCpaTipolotedefault());
			tgbCpaFlagDadosveiculo.setSelected(empresa.getConfig().getCpaFlagDadosveiculo() == 1 ? true : false);
			tgbCpaFlagTabprogressivadescprod.setSelected(empresa.getConfig().getCpaFlagTabprogressivadescprod() == 1 ? true : false);
			Util.setSelectRadioButton(empresa.getConfig().getCpaTipocalculopreco(), grpCpaTipocalculopreco);			

			for (int i = 0; i < cBoxCpaCriterioPrecoFilial.getItems().size(); i++) {
				
				if(empresa.getConfig().getCpaCriterioprecosfiliais().equals(((EnumCriterioPrecoFilial) cBoxCpaCriterioPrecoFilial.getItems().get(i).getEnumOpcao()).criterioPreco)){
					cBoxCpaCriterioPrecoFilial.getSelectionModel().select(cBoxCpaCriterioPrecoFilial.getItems().get(i));
				}
				
			}
			
			tgbCpaFlagTribautomaticafiliais
					.setSelected(empresa.getConfig().getCpaFlagTribautomaticafiliais() == 1 ? true : false);
			tgbCpaFlagEnviaobsitensvenda
					.setSelected(empresa.getConfig().getCpaFlagEnviaobsitensvenda() == 1 ? true : false);
			spnCpaQtddiaschegadanfe.getValueFactory().setValue(empresa.getConfig().getCpaQtddiaschegadanfe());
			txtDiretorioXml.setText(empresa.getConfig().getCpaDiretorioXml());
			
			
			// TabPane Vendas
			Util.setSelectRadioButton(empresa.getConfig().getVdaTiposequenciacli(), grpVdaTiposequenciacli);
			tgbVdaFlagJustificaliberacao
					.setSelected(empresa.getConfig().getVdaFlagJustificaliberacao() == 1 ? true : false);
			tgbVdaFlagBloqvdacadvencido
					.setSelected(empresa.getConfig().getVdaFlagBloqvdacadvencido() == 1 ? true : false);
			spnVdaDiasBloqvdacadvencido.getValueFactory()
					.setValue(empresa.getConfig().getVdaDiasBloqvdacadvencido());
			tgbVdaFlagBloqvdainadimplente
					.setSelected(empresa.getConfig().getVdaFlagBloqvdainadimplente() == 1 ? true : false);
			spnVdaDiasBloqvdainadimplente.getValueFactory()
					.setValue(empresa.getConfig().getVdaDiasBloqvdainadimplente());
			tgbVdaFlagBloqvdadesatualizado
					.setSelected(empresa.getConfig().getVdaFlagBloqvdadesatualizado() == 1 ? true : false);
			spnVdaDiasBloqvdadesatualizado.getValueFactory()
					.setValue(empresa.getConfig().getVdaDiasBloqvdadesatualizado());
			tgbVdaFlagBloqvdasemmovimento
					.setSelected(empresa.getConfig().getVdaFlagBloqvdasemmovimento() == 1 ? true : false);
			spnVdaDiasBloqvdasemmovimento.getValueFactory()
					.setValue(empresa.getConfig().getVdaDiasBloqvdasemmovimento());
			txtVdaLimiteCreditoDefault.setText(empresa.getConfig().getVdaLimiteCreditoDefault()
					.setScale(2, BigDecimal.ROUND_HALF_EVEN).toString());
			Util.setSelectRadioButton(empresa.getConfig().getVdaTipolimcreditoClientes(),
					grpVdaTipolimcreditoClientes);
			Util.setSelectRadioButton(empresa.getConfig().getVdaFlagVinculaVendedorcli(),
					grpVdaFlagVinculaVendedorcli);
			tgbVdaFlagUtilizaCliassociados
					.setSelected(empresa.getConfig().getVdaFlagUtilizaCliassociados() == 1 ? true : false);
			Util.setSelectRadioButton(empresa.getConfig().getVdaTipolimcreditoCliassociados(),
					grpVdaTipolimcreditoCliassociados);
			tgbVdaFlagEnviaclicartorio
					.setSelected(empresa.getConfig().getVdaFlagEnviaclicartorio() == 1 ? true : false);
			spnVdaDiasEnviocartorio.getValueFactory().setValue(empresa.getConfig().getVdaDiasEnviocartorio());
			tgbVdaFlagExibedadosfiliaisVenda
					.setSelected(empresa.getConfig().getVdaFlagExibedadosfiliaisVenda() == 1 ? true : false);
			Util.setSelectRadioButton(empresa.getConfig().getVdaTipoExibedadosfiliasVenda(),
					grpVdaTipoExibedadosfiliasVenda);
			tgbVdaFlagDescontoprodsPromocao
					.setSelected(empresa.getConfig().getVdaFlagDescontoprodsPromocao() == 1 ? true : false);
			tgbVdaFlagExibeprodsSimilares
					.setSelected(empresa.getConfig().getVdaFlagExibeprodsSimilares() == 1 ? true : false);
			Util.setSelectRadioButton(empresa.getConfig().getVdaComissaoLocalorigem(),
					grpVdaComissaoLocalorigem);
			Util.setSelectRadioButton(empresa.getConfig().getVdaComissaoTipopagamento(),
					grpVdaComissaoTipopagamento);
			cboxVdaFiltrodefaultBuscaitens.getSelectionModel()
					.select(empresa.getConfig().getVdaFiltrodefaultBuscaitens());
			spnVdaDiasvalidadeProposta.getValueFactory()
					.setValue(empresa.getConfig().getVdaDiasvalidadeProposta());
			cboxVdaFreteTipodefault.getSelectionModel().select(empresa.getConfig().getVdaFreteTipodefault());
			tgbVdaFlagUtilizatabelaPrecocli
					.setSelected(empresa.getConfig().getVdaFlagUtilizatabelaPrecocli() == 1 ? true : false);
			Util.setSelectRadioButton(empresa.getConfig().getVdaTipoidentificacaoVendedor(),
					grpVdaTipoidentificacaoVendedor);
			cboxVdaTipocodigoTeladevendas.getSelectionModel()
					.select(empresa.getConfig().getVdaTipocodigoTeladevendas());
			tgbVdaFlagCodigobalanca
					.setSelected(empresa.getConfig().getVdaFlagCodigobalanca() == 1 ? true : false);
			Util.setSelectRadioButton(empresa.getConfig().getVdaCodigobalancaQtddigitos(),
					grpVdaCodigobalancaQtddigitos);

			if (cboxVdaTipocodigoTeladevendas.getSelectionModel().getSelectedIndex() != 2) {
				tgbVdaFlagCodigobalanca.setDisable(true);
				lblVdaFlagCodigobalanca.setOpacity(0.3);

				lblVdaCodigobalancaQtddigitos.setOpacity(0.3);
				rdbVdaCodigobalancaQtddigitos4.setDisable(true);
				rdbVdaCodigobalancaQtddigitos5.setDisable(true);
				rdbVdaCodigobalancaQtddigitos6.setDisable(true);

			}

			// TabPane Financiero
			txtFinDiascarenciaGerarjuros.setText(empresa.getConfig().getFinDiascarenciaGerarjuros().toString());
			txtFinMultactasrec.setText(
					empresa.getConfig().getFinMultactasrec().setScale(2, BigDecimal.ROUND_HALF_EVEN).toString());
			// txtFinMultactasrec.setText(empresa.getConfig().getFinMultactasrec().toString());
			txtFinTaxajuros.setText(
					empresa.getConfig().getFinTaxajuros().setScale(2, BigDecimal.ROUND_HALF_EVEN).toString());
			// txtFinTaxajuros.setText(empresa.getConfig().getFinTaxajuros().toString());
			Util.setSelectRadioButton(empresa.getConfig().getFinTipointevaloTaxajuros(),
					grpFinTipointevaloTaxajuros);
			txtFinTaxajurosMinima.setText(empresa.getConfig().getFinTaxajurosMinima()
					.setScale(2, BigDecimal.ROUND_HALF_EVEN).toString());
			// txtFinTaxajurosMinima.setText(empresa.getConfig().getFinTaxajurosMinima().toString());

			// Validaçao de Segurança
			tgbSegFlagAltsenhaperio
					.setSelected(empresa.getConfig().getSegFlagAltsenhaperio() == 1 ? true : false);
			spnSegPrazoAlterasenha.getValueFactory().setValue(empresa.getConfig().getSegPrazoAlterasenha());
			spnSegQtdcaracter.getValueFactory().setValue(empresa.getConfig().getSegQtdcaracter());
			tgbSegSeqdigitos.setSelected(empresa.getConfig().getSegSeqdigitos() == 1 ? true : false);
			tgbSegFlagContemletra.setSelected(empresa.getConfig().getSegFlagContemletra() == 1 ? true : false);
			tgbSegFlagReutilizapwd.setSelected(empresa.getConfig().getSegFlagReutilizapwd() == 1 ? true : false);

			// Images
			if (empresa.getConfig().getImgLogomarca().length > 0) {
				try {
					FileOutputStream f = new FileOutputStream(
							"C:/EptusAM/System.tmp/emp" + txtCodigo.getText() + "/logo.png");
					f.write(empresa.getConfig().getImgLogomarca());
					f.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				File file = new File("C:/EptusAM/System.tmp/emp" + txtCodigo.getText() + "/logo.png");

				imgImgLogomarca.setImage(new Image(file.toURI().toString()));
			} else {
				imgImgLogomarca.setImage(null);

			}
		}
	}

	public void tarefaConsulta(String tipoConsulta) {

		Task<String> TarefaRefresh = new Task<String>() {
			LogRetorno lr = new LogRetorno();

			@Override
			protected String call() throws Exception {

				if (tipoConsulta.equals("filter")) {
					lr = empDAO.getById(Integer.valueOf(txtCodigo.getText()));
				} else
					lr = empDAO.getById(DadosGlobais.empresaLogada.getCodemp());
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				empresa = (Empresa) lr.getObjeto();
				if (empresa != null && empresa.getConfig() != null) {
					btnSave.setDisable(false);
					setDatosConfig();
					if (tipoConsulta.equals("current"))
						btnSave.setDisable(true);

					btnImgLogomarca.setDisable(false);
				} else {
					util.showAlert(DadosGlobais.resourceBundle.getString("alertErrorCodigoNull"), "error");
					txtNome.clear();
					txtCodigo.requestFocus();
					btnImgLogomarca.setDisable(true);

				}

			}

			@Override
			protected void failed() {
				stg.close();
				util.showAlert("Erro ao Conectar ao Banco de Dados, verifique a conexão de rede!", "error");
			}

			@Override
			protected void cancelled() {
				super.cancelled();
			}
		};
		Thread t = new Thread(TarefaRefresh);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(AjustesSistemaController.class, TarefaRefresh, "Consultando Registros",
				false);
	}

	public AjustesSistemaController( TabPane tpPpal) {
		super();
		tpPrincipal = tpPpal; 
		// TODO Auto-generated constructor stub	
		
	}

	/**
	 * Fill comboBox cboxCpaTipolotedefault and property definitions
	 */
	public void fillCboxCpaTipolotedefault() {

		for (EnumTipoControleLote item : EnumTipoControleLote.values())
			listCboxCpaTipolotedefault.add(new ComboBoxFilter(item.text, 0, item.index.toString()));

		cboxCpaTipolotedefault.getItems().addAll(listCboxCpaTipolotedefault);
		cboxCpaTipolotedefault.getSelectionModel().select(0);

		cboxCpaTipolotedefault.setConverter(new StringConverter<ComboBoxFilter>() {
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
	 * Fill comboBox cboxCpaCriterioPrecoFilial and property definitions
	 */
	public void fillCboxCpaCriterioPrecoFilial() {

		for (EnumCriterioPrecoFilial item : EnumCriterioPrecoFilial.values())
			listCboxCpaCriterioPrecoFilial.add(new ComboBoxFilter(item.descCriterio, 0, item));

		cBoxCpaCriterioPrecoFilial.getItems().addAll(listCboxCpaCriterioPrecoFilial);
		cBoxCpaCriterioPrecoFilial.getSelectionModel().select(0);

		cBoxCpaCriterioPrecoFilial.setConverter(new StringConverter<ComboBoxFilter>() {
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
	 * Fill comboBox cboxVdaFiltrodefaultBuscaitens and property definitions
	 */
	public void fillCboxVdaFiltrodefaultBuscaitens() {

		for (EnumTipoConsultaProdutosDefaultBuscaItens item : EnumTipoConsultaProdutosDefaultBuscaItens.values())
			listCboxVdaFiltrodefaultBuscaitens.add(new ComboBoxFilter(item.text, 0, item.index.toString()));

		cboxVdaFiltrodefaultBuscaitens.getItems().addAll(listCboxVdaFiltrodefaultBuscaitens);
		cboxVdaFiltrodefaultBuscaitens.getSelectionModel().select(0);

		cboxVdaFiltrodefaultBuscaitens.setConverter(new StringConverter<ComboBoxFilter>() {
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
	 * Fill comboBox cboxVdaFreteTipodefault and property definitions
	 */
	public void fillCboxVdaFreteTipodefault() {

		for (EnumTipoFreteModuloVendas item : EnumTipoFreteModuloVendas.values())
			listCboxVdaFreteTipodefault.add(new ComboBoxFilter(item.text, 0, item.index.toString()));

		cboxVdaFreteTipodefault.getItems().addAll(listCboxVdaFreteTipodefault);
		cboxVdaFreteTipodefault.getSelectionModel().select(0);

		cboxVdaFreteTipodefault.setConverter(new StringConverter<ComboBoxFilter>() {
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
	 * Fill comboBox cboxVdaTipocodigoTeladevendas and property definitions
	 */
	public void fillCboxVdaTipocodigoTeladevendas() {

		for (EnumTipocCodigoTelaVendas item : EnumTipocCodigoTelaVendas.values())
			listCboxVdaTipocodigoTeladevendas.add(new ComboBoxFilter(item.text, 0, item.index.toString()));

		cboxVdaTipocodigoTeladevendas.getItems().addAll(listCboxVdaTipocodigoTeladevendas);
		cboxVdaTipocodigoTeladevendas.getSelectionModel().select(0);

		cboxVdaTipocodigoTeladevendas.setConverter(new StringConverter<ComboBoxFilter>() {
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

	public void translations() {

		// Label do Form
		lblCodigo.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblCodigo"));
		lblNome.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblNome"));
		// Text do TabPane
		tabParg.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.tabParg"));
		tabComp.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.tabComp"));
		tabVend.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.tabVend"));
		tabFinc.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.tabFinc"));
		tabLivf.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.tabLivf"));
		tabRhum.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.tabRhum"));
		tabServ.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.tabServ"));
		tabProd.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.tabProd"));
		tabCont.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.tabCont"));
		tabConf.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.tabConf"));
		tabImg.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.tabImg"));
		tabSeg.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.tabSeg"));
		// Tab Parametros Gerais
		lblGerQtdCaracterbusca
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblGerQtdCaracterbusca"));
		lblGerFlagModulofidelidade
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblGerFlagModulofidelidade"));
		lblGerPerccargatribgeral
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblGerPerccargatribgeral"));
		lblGerFlagEmailcopyoculta
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblGerFlagEmailcopyoculta"));
		lblGerEmailoculta.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblGerEmailoculta"));
		lblGerFlagNotificacao
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblGerFlagNotificacao"));
		lblGerNotificacaoestilo
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblGerNotificacaoestilo"));
		rdbGerNotificacaoestilo0
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.rdbGerNotificacaoestilo0"));
		rdbGerNotificacaoestilo1
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.rdbGerNotificacaoestilo1"));
		lblGerNotificacaoposicao
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblGerNotificacaoposicao"));
		rdbGerNotificacaoposicao0
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.rdbGerNotificacaoposicao0"));
		rdbGerNotificacaoposicao1
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.rdbGerNotificacaoposicao1"));
		rdbGerNotificacaoposicao2
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.rdbGerNotificacaoposicao2"));
		rdbGerNotificacaoposicao3
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.rdbGerNotificacaoposicao3"));
		rdbGerNotificacaoposicao4
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.rdbGerNotificacaoposicao4"));
		lblGerCertDigital.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblGerCertDigital"));
		lblGerCertificado.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblGerCertificado"));
		lblGerDataVencto.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblGerDataVencto"));
		lblGerDiasRest.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblGerDiasRest"));
		// Tab Compras
		lblCpaTiposequenciaprod
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblCpaTiposequenciaprod"));
		rdbCpaTiposequenciaprod0.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.manual"));
		rdbCpaTiposequenciaprod1.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.automatico"));
		rdbCpaTiposequenciaprod2.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.aDefinir"));
		lblCpaQtdcasasdecimaisestoque.setText(
				DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblCpaQtdcasasdecimaisestoque"));
		lblCpaQtdcasasdecimaisvalor
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblCpaQtdcasasdecimaisvalor"));
		lblCpaTipocodprodutocompras
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblCpaTipocodprodutocompras"));
		rdbCpaTipocodprodutocompras0.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.eptus"));
		rdbCpaTipocodprodutocompras1
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.noFabricante"));
		rdbCpaTipocodprodutocompras2
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.codBarras"));
		lblCpaFlagDadosultimoproduto.setText(
				DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblCpaFlagDadosultimoproduto"));
		lblCpaFlagControlelote
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblCpaFlagControlelote"));
		lblCpaTipolotedefault
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblCpaTipolotedefault"));
		lblCpaFlagDadosveiculo
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblCpaFlagDadosveiculo"));
		lblCpaFlagTabprogressivadescprod.setText(
				DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblCpaFlagTabprogressivadescprod"));
		lblCpaTipocalculopreco
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblCpaTipocalculopreco"));
		rdbCpaTipocalculopreco0.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.simples"));
		rdbCpaTipocalculopreco1.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.detalhada"));
		lblCpaCriterioprecosfiliais
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblCpaCriterioprecosfiliais"));
		lblCpaFlagTribautomaticafiliais.setText(
				DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblCpaFlagTribautomaticafiliais"));
		lblCpaFlagEnviaobsitensvenda.setText(
				DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblCpaFlagEnviaobsitensvenda"));
		lblCpaQtddiaschegadanfe
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblCpaQtddiaschegadanfe"));
		// Tab Vendas
		lblVdaTiposequenciacli
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblVdaTiposequenciacli"));
		rdbVdaTiposequenciacli0.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.manual"));
		rdbVdaTiposequenciacli1.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.automatico"));
		rdbVdaTiposequenciacli2.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.aDefinir"));
		lblVdaFlagJustificaliberacao.setText(
				DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblVdaFlagJustificaliberacao"));
		lblVdaFlagBloqvdacadvencido
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblVdaFlagBloqvdacadvencido"));
		lblVdaDiasBloqvdacadvencido
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblVdaDiasBloqvdacadvencido"));
		lblVdaFlagBloqvdainadimplente.setText(
				DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblVdaFlagBloqvdainadimplente"));
		lblVdaDiasBloqvdainadimplente.setText(
				DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblVdaDiasBloqvdainadimplente"));
		lblVdaFlagBloqvdadesatualizado.setText(
				DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblVdaFlagBloqvdadesatualizado"));
		lblVdaDiasBloqvdadesatualizado.setText(
				DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblVdaDiasBloqvdadesatualizado"));
		lblVdaFlagBloqvdasemmovimento.setText(
				DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblVdaFlagBloqvdasemmovimento"));
		lblVdaDiasBloqvdasemmovimento.setText(
				DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblVdaDiasBloqvdasemmovimento"));
		lblVdaLimiteCreditoDefault
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblVdaLimiteCreditoDefault"));
		lblVdaTipolimcreditoClientes.setText(
				DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblVdaTipolimcreditoClientes"));
		rdbVdaTipolimcreditoClientes0.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.total"));
		rdbVdaTipolimcreditoClientes1.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.mensal"));
		lblVdaFlagVinculaVendedorcli.setText(
				DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblVdaFlagVinculaVendedorcli"));
		rdbVdaFlagVinculaVendedorcli1.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.sim"));
		rdbVdaFlagVinculaVendedorcli0.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.nao"));
		rdbVdaFlagVinculaVendedorcli2
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.aleatorio"));
		lblVdaFlagUtilizaCliassociados.setText(
				DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblVdaFlagUtilizaCliassociados"));
		lblVdaTipolimcreditoCliassociados.setText(
				DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblVdaTipolimcreditoCliassociados"));
		rdbVdaTipolimcreditoCliassociados0
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.consolidado"));
		rdbVdaTipolimcreditoCliassociados1
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.individual"));
		lblVdaFlagEnviaclicartorio
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblVdaFlagEnviaclicartorio"));
		lblVdaDiasEnviocartorio
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblVdaDiasEnviocartorio"));
		lblVdaFlagExibedadosfiliaisVenda.setText(
				DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblVdaFlagExibedadosfiliaisVenda"));
		lblVdaTipoExibedadosfiliasVenda.setText(
				DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblVdaTipoExibedadosfiliasVenda"));
		rdbVdaTipoExibedadosfiliasVenda0
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.estoqueVirVenda"));
		rdbVdaTipoExibedadosfiliasVenda1
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.estoque"));
		lblVdaFlagDescontoprodsPromocao.setText(
				DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblVdaFlagDescontoprodsPromocao"));
		lblVdaFlagExibeprodsSimilares.setText(
				DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblVdaFlagExibeprodsSimilares"));
		lblVdaComissaoLocalorigem
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblVdaComissaoLocalorigem"));
		lblVdaComissaoTipopagamento
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblVdaComissaoTipopagamento"));
		rdbVdaComissaoTipopagamento0
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.emissaoVenda"));
		rdbVdaComissaoTipopagamento1
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.liquidacao"));
		lblVdaFiltrodefaultBuscaitens.setText(
				DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblVdaFiltrodefaultBuscaitens"));
		lblVdaDiasvalidadeProposta
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblVdaDiasvalidadeProposta"));
		lblVdaFreteTipodefault
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblVdaFreteTipodefault"));
		lblVdaFlagUtilizatabelaPrecocli.setText(
				DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblVdaFlagUtilizatabelaPrecocli"));
		lblVdaTipoidentificacaoVendedor.setText(
				DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblVdaTipoidentificacaoVendedor"));
		rdbVdaTipoidentificacaoVendedor0
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.codigo"));
		rdbVdaTipoidentificacaoVendedor1
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.senha"));
		lblVdaTipocodigoTeladevendas.setText(
				DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblVdaTipocodigoTeladevendas"));
		lblVdaFlagCodigobalanca
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblVdaFlagCodigobalanca"));
		lblVdaCodigobalancaQtddigitos.setText(
				DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblVdaCodigobalancaQtddigitos"));
		rdbVdaCodigobalancaQtddigitos4.setText(
				DadosGlobais.resourceBundle.getString("ajustesSistemaController.rdbVdaCodigobalancaQtddigitos4"));
		rdbVdaCodigobalancaQtddigitos5.setText(
				DadosGlobais.resourceBundle.getString("ajustesSistemaController.rdbVdaCodigobalancaQtddigitos5"));
		rdbVdaCodigobalancaQtddigitos6.setText(
				DadosGlobais.resourceBundle.getString("ajustesSistemaController.rdbVdaCodigobalancaQtddigitos6"));
		// Tab Images
		lblImgLogomarca.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblImgLogomarca"));
		btnImgLogomarca.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.btnImgLogomarca"));
		// TabPane Validacao de Seguranca
		lblSegFlagAltsenhaperio
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblSegFlagAltsenhaperio"));
		lblSegPrazoAlterasenha
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblSegPrazoAlterasenha"));
		lblSegPrazoAlterasenhadias
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblSegPrazoAlterasenhadias"));
		lblSegQtdcaracter.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblSegQtdcaracter"));
		lblSegSeqdigitos.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblSegSeqdigitos"));
		lblSegFlagContemletra
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblSegFlagContemletra"));
		lblSegFlagReutilizapwd
				.setText(DadosGlobais.resourceBundle.getString("ajustesSistemaController.lblSegFlagReutilizapwd"));
		btnSave.setTooltip(new Tooltip(DadosGlobais.resourceBundle.getString("toolTipSave")));
		btnCancel.setTooltip(new Tooltip(DadosGlobais.resourceBundle.getString("toolTipCancel")));

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		actionToggleButtons();

		translations();

		// Set Style Tabs
		Util.setStyleTab(tabPaneParg, tabPaneComp, tabPaneVend, tabPaneFinc, tabPaneImg, tabPaneSeg);

		// Set focus to txtCodigo
		Util.setFocus(txtCodigo);

		// Initializes the values of the RadioButton
		Util.defineRadioButton(grpGerNotificacaoestilo, grpGerNotificacaoposicao, grpCpaTiposequenciaprod,
				grpCpaQtdcasasdecimaisestoque, grpCpaQtdcasasdecimaisvalor, grpCpaTipocodprodutocompras,
				grpCpaTipocalculopreco, grpVdaTiposequenciacli,
				grpVdaTipolimcreditoClientes, grpVdaFlagVinculaVendedorcli, grpVdaTipolimcreditoCliassociados,
				grpVdaTipoExibedadosfiliasVenda, grpVdaComissaoLocalorigem, grpVdaComissaoTipopagamento,
				grpVdaTipoidentificacaoVendedor, grpVdaCodigobalancaQtddigitos, grpFinTipointevaloTaxajuros);

		// Set default style of Node on in event onClick
		Util.defaultStyle(txtCodigo);

		// Fill comboBoxs
		fillCboxCpaTipolotedefault();
		fillCboxCpaCriterioPrecoFilial();
		fillCboxVdaFiltrodefaultBuscaitens();
		fillCboxVdaFreteTipodefault();
		fillCboxVdaTipocodigoTeladevendas();

		// Spinner declaration
		spnGerQtdCaracterbusca.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 2));
		spnCpaQtddiaschegadanfe.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 360, 1));
		spnVdaDiasBloqvdacadvencido.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 720, 1));
		spnVdaDiasBloqvdainadimplente.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 720, 1));
		spnVdaDiasBloqvdadesatualizado.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 720, 1));
		spnVdaDiasBloqvdasemmovimento.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 720, 1));
		spnVdaDiasEnviocartorio.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 90, 1));
		spnVdaDiasvalidadeProposta.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 90, 1));
		spnSegPrazoAlterasenha.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(7, 90, 7));
		spnSegQtdcaracter.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));

		// CHAMADA A FUNÇÃO QUE SETA O ESTILO DEFAULT AOS CAMPOS TEXTFIELDS
		Util.onlyNumbers(txtCodigo);

		// CHAMADA A FUNÇÃO QUE SETA O ESTILO DEFAULT AOS CAMPOS TEXTFIELDS
		// Util.onlyAlphanumeric(txtDescricao, txtFilterColumn);

		// Limit number of characters
		Util.maxCharacters(8, txtCodigo);

		txtCodigo.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if (!newValue && !Util.noEmpty(txtCodigo))
					tarefaConsulta("filter");

			}
		});

		// ShortCuts Keys
		anchorPanePrincipal.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			switch (event.getCode()) {

			case F6:
				if (!btnSave.isDisable())
					if (!btnSave.isDisable())
						actionBtnSave(null);
				break;

			case F8:
				if (!btnCancel.isDisable())
					actionBtnCancel(null);
				break;

			default:
				break;
			}
		});


		// Set default data
		actionBtnCancel(null);

		cboxVdaTipocodigoTeladevendas.getSelectionModel().selectedIndexProperty()
				.addListener(new ChangeListener<Number>() {
					@Override
					public void changed(ObservableValue<? extends Number> observable, Number oldValue,
							Number newValue) {
						// TODO Auto-generated method stub
						if (newValue.intValue() == 2) {
							tgbVdaFlagCodigobalanca.setDisable(false);
							lblVdaFlagCodigobalanca.setOpacity(1);

							if (tgbVdaFlagCodigobalanca.isSelected()) {
								lblVdaCodigobalancaQtddigitos.setOpacity(1);
								rdbVdaCodigobalancaQtddigitos4.setDisable(false);
								rdbVdaCodigobalancaQtddigitos5.setDisable(false);
								rdbVdaCodigobalancaQtddigitos6.setDisable(false);
							}
						} else {
							tgbVdaFlagCodigobalanca.setDisable(true);
							lblVdaFlagCodigobalanca.setOpacity(0.3);

							lblVdaCodigobalancaQtddigitos.setOpacity(0.3);
							rdbVdaCodigobalancaQtddigitos4.setDisable(true);
							rdbVdaCodigobalancaQtddigitos5.setDisable(true);
							rdbVdaCodigobalancaQtddigitos6.setDisable(true);
						}
					}
				});

		Button btnSearchCertificate = new Button();
		btnSearchCertificate.setStyle("-fx-background-color : transparent; -fx-padding : 0; -fx-cursor: hand ;");
		FontAwesomeIconView iconSearchPortador = new FontAwesomeIconView(FontAwesomeIcon.SEARCH);
		iconSearchPortador.setFill(Color.web("#757575"));
		iconSearchPortador.setSize("16px");
		btnSearchCertificate.setGraphic(iconSearchPortador);
		btnSearchCertificate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				// showSearch("PORTADOR");
				actionBtnConfigCertificado(event);
			}
		});
		txtCertificado.setLeft(btnSearchCertificate);
				
		// ---------------------------------------------------------------------------
		
		double sbWidth = ((tpPrincipal.getWidth() - 220) - txtBuscarContenido.getWidth());
		txtBuscarContenido.setPromptText(DadosGlobais.resourceBundle.getString("searchLabelController.lblSearchedWord"));
		txtBuscarContenido.setLayoutX(sbWidth);
		txtBuscarContenido.setLayoutY(20);
	    
		Button btn = Util.customSearchTextField("right", null, txtBuscarContenido);
		btn.setOnAction(new EventHandler<ActionEvent>() 
		{			
			@Override
			public void handle(ActionEvent event) 
			{
				util.actionSearchBox( txtBuscarContenido, tabPane);
				
			}
		});
		
		txtBuscarContenido.setOnKeyPressed(key -> {
			if ( key.getCode().equals(KeyCode.ENTER) ) 
			{	
				 util.actionSearchBox( txtBuscarContenido, tabPane);
			}
			else{}
		});	
		
		// ---------------------------------------------------------------------------
	}

}
