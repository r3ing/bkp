package controllers.compras;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import models.compras.Fabricante;
import models.compras.FabricanteDAO;
import models.compras.Fornecedor;
import models.compras.FornecedorDAO;
import models.compras.Grade;
import models.compras.GradeDAO;
import models.compras.Grupo;
import models.compras.GrupoDAO;
import models.compras.Item;
import models.compras.ItemCodBar;
import models.compras.ItemDAO;
import models.compras.ItemFornecedor;
import models.compras.ItemFornecedorDAO;
import models.compras.ItemValor;
import models.compras.NfEntradaCab;
import models.compras.NfEntradaDAO;
import models.compras.OperacaoEntrada1;
import models.compras.OperacaoEntradaDAO1;
import models.compras.SubGrupo;
import models.compras.SubGrupoDAO;
import models.compras.TabelaNCM;
import models.compras.TabelaNCMDAO;
import models.compras.Tributacao;
import models.compras.TributacaoDAO;
import models.compras.Unidade;
import models.compras.UnidadeDAO;
import models.configuracoes.DepositoEstoque;
import models.configuracoes.DepositoEstoqueDAO;
import models.configuracoes.OperacaoEntrada;
import models.configuracoes.OperacaoEntradaDAO;
import models.financeiro.CentroCusto;
import models.financeiro.CentroCustoDAO;
import models.financeiro.Moeda;
import models.financeiro.MoedaDAO;
import models.financeiro.PlanoConta;
import models.financeiro.PlanoContaDAO;
import models.financeiro.Portador;
import models.financeiro.PortadorDAO;
import models.financeiro.Secao;
import models.financeiro.SecaoDAO;
import models.vendas.Cidade;
import models.vendas.CidadeDAO;
import tools.controls.ComboBoxFilter;
import tools.enums.EnumCompartilhamento;
import tools.enums.EnumStatusRelItens;
import tools.utils.LogRetorno;
import tools.utils.RelacaoItems;
import tools.utils.Util;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import org.controlsfx.control.textfield.CustomTextField;

import com.sun.javafx.scene.control.skin.BehaviorSkinBase;

import application.DadosGlobais;
import br.com.samuelweb.nfe.exception.NfeException;
import br.com.samuelweb.nfe.util.XmlUtil;
import br.inf.portalfiscal.nfe.schema.consrecinfe.TNFe.InfNFe.Det.Imposto.COFINSST;
import br.inf.portalfiscal.nfe.schema.consrecinfe.TNFe.InfNFe.Det.Imposto.ISSQN;
import br.inf.portalfiscal.nfe.schema.consrecinfe.TNFe.InfNFe.Det.Imposto.PISST;
import br.inf.portalfiscal.nfe.schema.nfe.TIpi;
import br.inf.portalfiscal.nfe.schema.nfe.TNFe;
import br.inf.portalfiscal.nfe.schema.nfe.TNFe.InfNFe.Cobr;
import br.inf.portalfiscal.nfe.schema.nfe.TNFe.InfNFe.Cobr.Dup;
import br.inf.portalfiscal.nfe.schema.nfe.TNFe.InfNFe.Det;
import controllers.utils.DownloadNFeController;
import controllers.utils.ProgressBarForm;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import br.inf.portalfiscal.nfe.schema.nfe.TNFe.InfNFe.Det.Imposto.COFINS;
import br.inf.portalfiscal.nfe.schema.nfe.TNFe.InfNFe.Det.Imposto.ICMS;
import br.inf.portalfiscal.nfe.schema.nfe.TNFe.InfNFe.Det.Imposto.II;
import br.inf.portalfiscal.nfe.schema.nfe.TNFe.InfNFe.Det.Imposto.PIS;
import br.inf.portalfiscal.nfe.schema.nfe.TNFe.InfNFe.Emit;
import br.inf.portalfiscal.nfe.schema.nfe.TNFe.InfNFe.Total;
import br.inf.portalfiscal.nfe.schema.nfe.TNfeProc;

public class NotaFiscalEntradaController implements Initializable {

	@FXML
	private AnchorPane anchorPanePrincipal, anchorPaneFilter, anchorPaneRepItens, anchorPaneFormacaoPreco;

	@FXML
	private SplitPane splitPaneFilterDept;

	@FXML
	private ComboBox<?> cboxFilterColumn;

	@FXML
	private ComboBox<?> cboxFlagAtivo;

	@FXML
	private Label lblCboxFilterColumn;

	@FXML
	private Label lblcboxFlagAtivo;

	@FXML
	private CustomTextField txtCodigoOperacao, // txtCodigoFuncionario,
	txtFileChooser, txtCodigoFornecedor, txtCodigoDepositoEstoque, txtCodigoSecao,
	// txtCodigoCentroCusto,
	txtCodigoPlanosContas, txtCodigoPortador;

	@FXML
	private TextField txtOperacao, txtCFOP, txtCFOP2, // txtFuncionario,
	txtFornecedor, txtNoCNPJ, txtNoNotaFiscal, txtDepositoEstoque, txtSecao, txtCentroCusto, txtPlanoContas, txtPortador
	// TOTALES
	, txtTotalItens, txtVTotalProducto, txtVTotalIPI, txtVTotalICMS, txtVTotalICMSDeson, txtVTotalICMSST,
	txtVTotalQtdComercialProd, txtVTotalFrete, txtVTotalSeguro, txtVTotalDesconto, txtVTotalDespAc,
	txtVTotalNotaFiscal;

	@FXML
	private Tab tabRelItens;

	@FXML
	private DatePicker dtxtDataEmissao, dtxtDataChegada;

	@FXML
	private Button btnFileChooser;

	@FXML
	private ToggleButton btnDownloadNFe;

	@FXML
	private TabPane tbPanePrincipal;

	@FXML
	private TableView<?> tbView;

	@FXML
	private TableView<TNFe> tbViewCabecalhoNotFisc;

	@FXML
	private TableColumn<TNFe, String> ideCUF;

	@FXML
	private TableColumn<TNFe, String> ideCNF;

	@FXML
	private TableColumn<TNFe, String> ideNatOp;

	@FXML
	private TableColumn<TNFe, String> ideIndPag;

	@FXML
	private TableColumn<TNFe, String> ideMod;

	@FXML
	private TableColumn<TNFe, String> ideSerie;

	@FXML
	private TableColumn<TNFe, String> ideNNF;

	@FXML
	private TableColumn<TNFe, String> ideDEmi;

	@FXML
	private TableColumn<TNFe, String> ideDSaiEnt;

	@FXML
	private TableColumn<TNFe, String> ideTpNF;

	@FXML
	private TableColumn<TNFe, String> ideCMunFG;

	@FXML
	private TableColumn<TNFe, String> ideTpImp;

	@FXML
	private TableColumn<TNFe, String> ideTpEmis;

	@FXML
	private TableColumn<TNFe, String> ideCDV;

	@FXML
	private TableColumn<TNFe, String> ideTpAmb;

	@FXML
	private TableColumn<TNFe, String> ideFinNFe;

	@FXML
	private TableColumn<TNFe, String> ideProEmi;

	@FXML
	private TableColumn<TNFe, String> ideVerProc;

	@FXML
	private TableColumn<TNFe, String> emitCNPJ;

	@FXML
	private TableColumn<TNFe, String> emitCPF;

	@FXML
	private TableColumn<TNFe, String> emitxNome;

	@FXML
	private TableColumn<TNFe, String> emitxFant;

	@FXML
	private TableColumn<TNFe, String> enderEmitxLgr;

	@FXML
	private TableColumn<TNFe, String> enderEmitNro;

	@FXML
	private TableColumn<TNFe, String> enderEmitxCpl;

	@FXML
	private TableColumn<TNFe, String> enderEmitxBairro;

	@FXML
	private TableColumn<TNFe, String> enderEmitcMun;

	@FXML
	private TableColumn<TNFe, String> enderEmitxMun;

	@FXML
	private TableColumn<TNFe, String> enderEmitUF;

	@FXML
	private TableColumn<TNFe, String> enderEmitCEP;

	@FXML
	private TableColumn<TNFe, String> enderEmitcPais;

	@FXML
	private TableColumn<TNFe, String> enderEmitxPais;

	@FXML
	private TableColumn<TNFe, String> enderEmitFone;

	@FXML
	private TableColumn<TNFe, String> emitIE;

	@FXML
	private TableColumn<TNFe, String> emitIEST;

	@FXML
	private TableColumn<TNFe, String> emitIM;

	@FXML
	private TableColumn<TNFe, String> emitCNAE;

	@FXML
	private TableColumn<TNFe, String> emitCRT;

	@FXML
	private TableColumn<TNFe, String> destCNPJ;

	@FXML
	private TableColumn<TNFe, String> destCPF;

	@FXML
	private TableColumn<TNFe, String> destxNome;

	@FXML
	private TableColumn<TNFe, String> destEmail;

	@FXML
	private TableColumn<TNFe, String> enderDestxLgr;

	@FXML
	private TableColumn<TNFe, String> enderDestNro;

	@FXML
	private TableColumn<TNFe, String> enderDestxCpl;

	@FXML
	private TableColumn<TNFe, String> enderDestxBairro;

	@FXML
	private TableColumn<TNFe, String> enderDestcMun;

	@FXML
	private TableColumn<TNFe, String> enderDestxMun;

	@FXML
	private TableColumn<TNFe, String> enderDestUF;

	@FXML
	private TableColumn<TNFe, String> enderDestCEP;

	@FXML
	private TableColumn<TNFe, String> enderDestcPais;

	@FXML
	private TableColumn<TNFe, String> enderDestxPais;

	@FXML
	private TableColumn<TNFe, String> enderDestFone;

	@FXML
	private TableColumn<TNFe, String> destIE;

	@FXML
	private TableColumn<TNFe, String> destISUF;

	@FXML
	private TableColumn<TNFe, String> entregaCNPJ;

	@FXML
	private TableColumn<TNFe, String> entregaxLgr;

	@FXML
	private TableColumn<TNFe, String> entregaNro;

	@FXML
	private TableColumn<TNFe, String> entregaxCpl;

	@FXML
	private TableColumn<TNFe, String> entregaxBairro;

	@FXML
	private TableColumn<TNFe, String> entregacMun;

	@FXML
	private TableColumn<TNFe, String> entregaxMun;

	@FXML
	private TableColumn<TNFe, String> entregaUF;

	@FXML
	private TableColumn<TNFe, String> totalICMSTotvBC;

	@FXML
	private TableColumn<TNFe, String> totalICMSTotvICMS;

	@FXML
	private TableColumn<TNFe, String> totalICMSTotvBCST;

	@FXML
	private TableColumn<TNFe, String> totalICMSTotvST;

	@FXML
	private TableColumn<TNFe, String> totalICMSTotvProd;

	@FXML
	private TableColumn<TNFe, String> totalICMSTotvFrete;

	@FXML
	private TableColumn<TNFe, String> totalICMSTotvSeg;

	@FXML
	private TableColumn<TNFe, String> totalICMSTotvDesc;

	@FXML
	private TableColumn<TNFe, String> totalICMSTotvII;

	@FXML
	private TableColumn<TNFe, String> totalICMSTotvIPI;

	@FXML
	private TableColumn<TNFe, String> totalICMSTotvPIS;

	@FXML
	private TableColumn<TNFe, String> totalICMSTotvCOFINS;

	@FXML
	private TableColumn<TNFe, String> totalICMSTotvOUTRO;

	@FXML
	private TableColumn<TNFe, String> totalISSTotvServ;

	@FXML
	private TableColumn<TNFe, String> totalISSTotvBC;

	@FXML
	private TableColumn<TNFe, String> totalISSTotvISS;

	@FXML
	private TableColumn<TNFe, String> totalISSTotvPIS;

	@FXML
	private TableColumn<TNFe, String> totalISSTotvCOFINS;

	@FXML
	private TableColumn<TNFe, String> totalRetTribvRetPIS;

	@FXML
	private TableColumn<TNFe, String> totalRetTribvRetCOFINS;

	@FXML
	private TableColumn<TNFe, String> totalRetTribvRetCSLL;

	@FXML
	private TableColumn<TNFe, String> totalRetTribvBCIRRF;

	@FXML
	private TableColumn<TNFe, String> totalRetTribvIRRF;

	@FXML
	private TableColumn<TNFe, String> totalRetTribvBCRetPrev;

	@FXML
	private TableColumn<TNFe, String> totalRetTribvRetPrev;

	@FXML
	private TableColumn<TNFe, String> transpModFrete;

	@FXML
	private TableColumn<TNFe, String> transportaCNPJ;

	@FXML
	private TableColumn<TNFe, String> transportaCPF;

	@FXML
	private TableColumn<TNFe, String> transportaANTT;

	@FXML
	private TableColumn<TNFe, String> transportaPlaca;

	@FXML
	private TableColumn<TNFe, String> transportaPlacaUF;

	@FXML
	private TableColumn<TNFe, String> transportaxNome;

	@FXML
	private TableColumn<TNFe, String> transportaxEnder;

	@FXML
	private TableColumn<TNFe, String> transportaxMun;

	@FXML
	private TableColumn<TNFe, String> transportaUF;

	@FXML
	private TableColumn<TNFe, String> transpRetTranspvServ;

	@FXML
	private TableColumn<TNFe, String> transpRetTranspvBCRet;

	@FXML
	private TableColumn<TNFe, String> transpRetTransppICMSRet;

	@FXML
	private TableColumn<TNFe, String> transpRetTranspvICMSRet;

	@FXML
	private TableColumn<TNFe, String> transpRetTranspCFOP;

	@FXML
	private TableColumn<TNFe, String> transpRetTranspcMunFG;

	@FXML
	private TableColumn<TNFe, String> transpVolqVol;

	@FXML
	private TableColumn<TNFe, String> transpVolEsp;

	@FXML
	private TableColumn<TNFe, String> transpVolMarca;

	@FXML
	private TableColumn<TNFe, String> transpVolPesoL;

	@FXML
	private TableColumn<TNFe, String> transpVolPesoB;

	@FXML
	private TableColumn<TNFe, String> transpVolnLacres;

	@FXML
	private TableColumn<TNFe, String> exportaUFEmbarq;

	@FXML
	private TableColumn<TNFe, String> exportaxLocEmbarq;

	// @FXML
	// private TableColumn<TNFe, String> infAdicInfCpl;
	//
	// @FXML
	// private TableColumn<TNFe, String> infAdicInfAdFisco;

	// TABLE DETALHES
	@FXML
	private TableView<Det> tbViewDetalheNotFisc;

	@FXML
	private TableColumn<Det, String> detNoItem;

	@FXML
	private TableColumn<Det, String> detProdcProd;

	@FXML
	private TableColumn<Det, String> detProdcEAN;

	@FXML
	private TableColumn<Det, String> detProdxProd;

	@FXML
	private TableColumn<Det, String> detProdNCM;

	@FXML
	private TableColumn<Det, String> detProdEXTIPI;

	@FXML
	private TableColumn<Det, String> detProdCFOP;

	@FXML
	private TableColumn<Det, String> detProduCom;

	@FXML
	private TableColumn<Det, String> detProdqCom;

	@FXML
	private TableColumn<Det, String> detProdqDev;

	@FXML
	private TableColumn<Det, String> detProdvUnCom;

	@FXML
	private TableColumn<Det, String> detProdvProd;

	@FXML
	private TableColumn<Det, String> detProdcEANTrib;

	@FXML
	private TableColumn<Det, String> detProduTrib;

	@FXML
	private TableColumn<Det, String> detProdqTrib;

	@FXML
	private TableColumn<Det, String> detProdvUnTrib;

	@FXML
	private TableColumn<Det, String> detProdvFrete;

	@FXML
	private TableColumn<Det, String> detProdvSeg;

	@FXML
	private TableColumn<Det, String> detProdvDesc;

	@FXML
	private TableColumn<Det, String> detProdIndTot;

	@FXML
	private TableColumn<Det, String> detProdVeicFlag;

	@FXML
	private TableColumn<Det, String> detProdVeictpOP;

	@FXML
	private TableColumn<Det, String> detProdVeicChassi;

	@FXML
	private TableColumn<Det, String> detProdVeiccCor;

	@FXML
	private TableColumn<Det, String> detProdVeicxCor;

	@FXML
	private TableColumn<Det, String> detProdVeicPot;

	@FXML
	private TableColumn<Det, String> detProdVeicCm3;

	@FXML
	private TableColumn<Det, String> detProdVeicPesoL;

	@FXML
	private TableColumn<Det, String> detProdVeicPesoB;

	@FXML
	private TableColumn<Det, String> detProdVeicnSerie;

	@FXML
	private TableColumn<Det, String> detProdVeicTpComb;

	@FXML
	private TableColumn<Det, String> detProdVeicnMotor;

	@FXML
	private TableColumn<Det, String> detProdVeicCmkg;

	@FXML
	private TableColumn<Det, String> detProdVeicDist;

	@FXML
	private TableColumn<Det, String> detProdVeicRenavam;

	@FXML
	private TableColumn<Det, String> detProdVeicAnoMod;

	@FXML
	private TableColumn<Det, String> detProdVeicAnoFab;

	@FXML
	private TableColumn<Det, String> detProdVeicTpPint;

	@FXML
	private TableColumn<Det, String> detProdVeicTpVeic;

	@FXML
	private TableColumn<Det, String> detProdVeicEspVeic;

	@FXML
	private TableColumn<Det, String> detProdVeicVin;

	@FXML
	private TableColumn<Det, String> detProdVeicCondVeic;

	@FXML
	private TableColumn<Det, String> detProdVeicCMod;

	@FXML
	private TableColumn<Det, String> detProdCombCProdANP;

	@FXML
	private TableColumn<Det, String> detProdCombCODIF;

	@FXML
	private TableColumn<Det, String> detProdCombQTemp;

	@FXML
	private TableColumn<Det, String> detProdCombUFCons;

	@FXML
	private TableColumn<Det, String> detProdCombCIDEQBCProd;

	@FXML
	private TableColumn<Det, String> detProdCombCIDEVAliqProd;

	@FXML
	private TableColumn<Det, String> detProdCombCIDEvCIDE;

	@FXML
	private TableColumn<Det, String> impCargaTribPrc;

	@FXML
	private TableColumn<Det, String> impCargaTribVlr;

	@FXML
	private TableColumn<Det, String> impIPIcIEnq;

	@FXML
	private TableColumn<Det, String> impIPICNPJProd;

	@FXML
	private TableColumn<Det, String> impIPIcSelo;

	@FXML
	private TableColumn<Det, String> impIPIqSelo;

	@FXML
	private TableColumn<Det, String> impIPIcEnq;

	@FXML
	private TableColumn<Det, String> impIPITribCST;

	@FXML
	private TableColumn<Det, String> impIPITribVBC;

	@FXML
	private TableColumn<Det, String> impIPITribQUnid;

	@FXML
	private TableColumn<Det, String> impIPITribVUnid;

	@FXML
	private TableColumn<Det, String> impIPITribPIPI;

	@FXML
	private TableColumn<Det, String> impIPITribVIPI;

	@FXML
	private TableColumn<Det, String> impIPINTCST;

	@FXML
	private TableColumn<Det, String> impICMS00Orig;

	@FXML
	private TableColumn<Det, String> impICMS00CST;

	@FXML
	private TableColumn<Det, String> impICMS00ModBC;

	@FXML
	private TableColumn<Det, String> impICMS00VBC;

	@FXML
	private TableColumn<Det, String> impICMS00PICMS;

	@FXML
	private TableColumn<Det, String> impICMS00VICMS;

	@FXML
	private TableColumn<Det, String> impICMS10Orig;

	@FXML
	private TableColumn<Det, String> impICMS10CST;

	@FXML
	private TableColumn<Det, String> impICMS10ModBC;

	@FXML
	private TableColumn<Det, String> impICMS10VBC;

	@FXML
	private TableColumn<Det, String> impICMS10PICMS;

	@FXML
	private TableColumn<Det, String> impICMS10VICMS;

	@FXML
	private TableColumn<Det, String> impICMS10ModBCST;

	@FXML
	private TableColumn<Det, String> impICMS10PMVAST;

	@FXML
	private TableColumn<Det, String> impICMS10PRedBCST;

	@FXML
	private TableColumn<Det, String> impICMS10VBCST;

	@FXML
	private TableColumn<Det, String> impICMS10PICMSST;

	@FXML
	private TableColumn<Det, String> impICMS10VICMSST;

	@FXML
	private TableColumn<Det, String> impICMS20Orig;

	@FXML
	private TableColumn<Det, String> impICMS20CST;

	@FXML
	private TableColumn<Det, String> impICMS20ModBC;

	@FXML
	private TableColumn<Det, String> impICMS20VBC;

	@FXML
	private TableColumn<Det, String> impICMS20PICMS;

	@FXML
	private TableColumn<Det, String> impICMS20VICMS, impICMS20VICMSDeson, impICMS20MotDesICMS;

	@FXML
	private TableColumn<Det, String> impICMS30Orig;

	@FXML
	private TableColumn<Det, String> impICMS30CST;

	@FXML
	private TableColumn<Det, String> impICMS30ModBCST;

	@FXML
	private TableColumn<Det, String> impICMS30PMVAST;

	@FXML
	private TableColumn<Det, String> impICMS30PRedBCST;

	@FXML
	private TableColumn<Det, String> impICMS30VBCST;

	@FXML
	private TableColumn<Det, String> impICMS30PICMSST;

	@FXML
	private TableColumn<Det, String> impICMS30VICMSST, impICMS30VICMSDeson, impICMS30MotDesICMS;

	@FXML
	private TableColumn<Det, String> impICMS40Orig;

	@FXML
	private TableColumn<Det, String> impICMS40CST, impICMS40VICMSDeson, impICMS40MotDesICMS;

	@FXML
	private TableColumn<Det, String> impICMS51Orig;

	@FXML
	private TableColumn<Det, String> impICMS51CST;

	@FXML
	private TableColumn<Det, String> impICMS51ModBC;

	@FXML
	private TableColumn<Det, String> impICMS51PRedBC;

	@FXML
	private TableColumn<Det, String> impICMS51VBC;

	@FXML
	private TableColumn<Det, String> impICMS51PICMS;

	@FXML
	private TableColumn<Det, String> impICMS51VICMS;

	@FXML
	private TableColumn<Det, String> impICMS60Orig;

	@FXML
	private TableColumn<Det, String> impICMS60CST;

	@FXML
	private TableColumn<Det, String> impICMS60VBCSTRet;

	@FXML
	private TableColumn<Det, String> impICMS60VICMSSTRet;

	@FXML
	private TableColumn<Det, String> impICMS70Orig;

	@FXML
	private TableColumn<Det, String> impICMS70CST;

	@FXML
	private TableColumn<Det, String> impICMS70ModBC;

	@FXML
	private TableColumn<Det, String> impICMS70PRedBC;

	@FXML
	private TableColumn<Det, String> impICMS70VBC;

	@FXML
	private TableColumn<Det, String> impICMS70PICMS;

	@FXML
	private TableColumn<Det, String> impICMS70VICMS;

	@FXML
	private TableColumn<Det, String> impICMS70ModBCST;

	@FXML
	private TableColumn<Det, String> impICMS70PMVAST;

	@FXML
	private TableColumn<Det, String> impICMS70PRedBCST;

	@FXML
	private TableColumn<Det, String> impICMS70VBCST;

	@FXML
	private TableColumn<Det, String> impICMS70PICMSST;

	@FXML
	private TableColumn<Det, String> impICMS70VICMSST, impICMS70VICMSDeson, impICMS70MotDesICMS;

	@FXML
	private TableColumn<Det, String> impICMS90Orig;

	@FXML
	private TableColumn<Det, String> impICMS90CST;

	@FXML
	private TableColumn<Det, String> impICMS90ModBC;

	@FXML
	private TableColumn<Det, String> impICMS90PRedBC;

	@FXML
	private TableColumn<Det, String> impICMS90VBC;

	@FXML
	private TableColumn<Det, String> impICMS90PICMS;

	@FXML
	private TableColumn<Det, String> impICMS90VICMS;

	@FXML
	private TableColumn<Det, String> impICMS90ModBCST;

	@FXML
	private TableColumn<Det, String> impICMS90PMVAST;

	@FXML
	private TableColumn<Det, String> impICMS90PRedBCST;

	@FXML
	private TableColumn<Det, String> impICMS90VBCST;

	@FXML
	private TableColumn<Det, String> impICMS90PICMSST;

	@FXML
	private TableColumn<Det, String> impICMS90VICMSST;

	@FXML
	private TableColumn<Det, String> impICMS90VICMSDeson;

	@FXML
	private TableColumn<Det, String> impICMS90MotDesICMS;

	@FXML
	private TableColumn<Det, String> impICMSPartOrig;

	@FXML
	private TableColumn<Det, String> impICMSPartCST;

	@FXML
	private TableColumn<Det, String> impICMSPartModBC;

	@FXML
	private TableColumn<Det, String> impICMSPartVBC;

	@FXML
	private TableColumn<Det, String> impICMSPartPRedBC;

	@FXML
	private TableColumn<Det, String> impICMSPartPICMS;

	@FXML
	private TableColumn<Det, String> impICMSPartVICMS;

	@FXML
	private TableColumn<Det, String> impICMSPartModBCST;

	@FXML
	private TableColumn<Det, String> impICMSPartPMVAST;

	@FXML
	private TableColumn<Det, String> impICMSPartPRedBCST;

	@FXML
	private TableColumn<Det, String> impICMSPartVBCST;

	@FXML
	private TableColumn<Det, String> impICMSPartPICMSST;

	@FXML
	private TableColumn<Det, String> impICMSPartVICMSST;

	@FXML
	private TableColumn<Det, String> impICMSPartPBCOp;

	@FXML
	private TableColumn<Det, String> impICMSPartUFST;

	@FXML
	private TableColumn<Det, String> impICMSSTOrig;

	@FXML
	private TableColumn<Det, String> impICMSSTCST;

	@FXML
	private TableColumn<Det, String> impICMSSTVBCSTRet;

	@FXML
	private TableColumn<Det, String> impICMSSTVICMSSTRet;

	@FXML
	private TableColumn<Det, String> impICMSSTVBCSTDest;

	@FXML
	private TableColumn<Det, String> impICMSSTVICMSSTDest;

	@FXML
	private TableColumn<Det, String> impCRTCMSSN101Orig;

	@FXML
	private TableColumn<Det, String> impCRTCMSSN101CSOSN;

	@FXML
	private TableColumn<Det, String> impCRTCMSSN101PCredSN;

	@FXML
	private TableColumn<Det, String> impCRTCMSSN101VCredICMSSN;

	@FXML
	private TableColumn<Det, String> impCRTCMSSN102Orig;

	@FXML
	private TableColumn<Det, String> impCRTCMSSN102CSOSN;

	@FXML
	private TableColumn<Det, String> impCRTCMSSN201Orig;

	@FXML
	private TableColumn<Det, String> impCRTCMSSN201CSOSN;

	@FXML
	private TableColumn<Det, String> impCRTCMSSN201ModBCST;

	@FXML
	private TableColumn<Det, String> impCRTCMSSN201PMVAST;

	@FXML
	private TableColumn<Det, String> impCRTCMSSN201PRedBCST;

	@FXML
	private TableColumn<Det, String> impCRTCMSSN201VBCST;

	@FXML
	private TableColumn<Det, String> impCRTCMSSN201PICMSST;

	@FXML
	private TableColumn<Det, String> impCRTCMSSN201VICMSST;

	@FXML
	private TableColumn<Det, String> impCRTCMSSN201PCredSN;

	@FXML
	private TableColumn<Det, String> impCRTCMSSN201VCredICMSSN;

	@FXML
	private TableColumn<Det, String> impCRTCMSSN202Orig;

	@FXML
	private TableColumn<Det, String> impCRTCMSSN202CSOSN;

	@FXML
	private TableColumn<Det, String> impCRTCMSSN202ModBCST;

	@FXML
	private TableColumn<Det, String> impCRTCMSSN202PMVAST;

	@FXML
	private TableColumn<Det, String> impCRTCMSSN202PRedBCST;

	@FXML
	private TableColumn<Det, String> impCRTCMSSN202VBCST;

	@FXML
	private TableColumn<Det, String> impCRTCMSSN202PICMSST;

	@FXML
	private TableColumn<Det, String> impCRTCMSSN202VICMSST;

	@FXML
	private TableColumn<Det, String> impCRTICMSSN500Orig;

	@FXML
	private TableColumn<Det, String> impCRTICMSSN500CSOSN;

	@FXML
	private TableColumn<Det, String> impCRTICMSSN500VBCSTRet;

	@FXML
	private TableColumn<Det, String> impCRTICMSSN500VICMSSTRet;

	@FXML
	private TableColumn<Det, String> impCRTICMSSN900Orig;

	@FXML
	private TableColumn<Det, String> impCRTICMSSN900CSOSN;

	@FXML
	private TableColumn<Det, String> impCRTICMSSN900ModBC;

	@FXML
	private TableColumn<Det, String> impCRTICMSSN900VBC;

	@FXML
	private TableColumn<Det, String> impCRTICMSSN900PRedBC;

	@FXML
	private TableColumn<Det, String> impCRTICMSSN900PICMS;

	@FXML
	private TableColumn<Det, String> impCRTICMSSN900ModBCST;

	@FXML
	private TableColumn<Det, String> impCRTICMSSN900PMVAST;

	@FXML
	private TableColumn<Det, String> impCRTICMSSN900PRedBCST;

	@FXML
	private TableColumn<Det, String> impCRTICMSSN900VBCST;

	@FXML
	private TableColumn<Det, String> impCRTICMSSN900PICMSST;

	@FXML
	private TableColumn<Det, String> impCRTICMSSN900VICMSST;

	@FXML
	private TableColumn<Det, String> impCRTICMSSN900PCredSN;

	@FXML
	private TableColumn<Det, String> impCRTICMSSN900VCredICMSSN;

	@FXML
	private TableColumn<Det, String> impIIVBC;

	@FXML
	private TableColumn<Det, String> impIIVDespAdu;

	@FXML
	private TableColumn<Det, String> impIIVII;

	@FXML
	private TableColumn<Det, String> impIIVIOF;

	@FXML
	private TableColumn<Det, String> impPISPISAliqCST;

	@FXML
	private TableColumn<Det, String> impPISPISAliqVBC;

	@FXML
	private TableColumn<Det, String> impPISPISAliqPPIS;

	@FXML
	private TableColumn<Det, String> impPISPISAliqVPIS;

	@FXML
	private TableColumn<Det, String> impPISPISQtdeCST;

	@FXML
	private TableColumn<Det, String> impPISPISQtdeQBCProd;

	@FXML
	private TableColumn<Det, String> impPISPISQtdeVAliqProd;

	@FXML
	private TableColumn<Det, String> impPISPISQtdeVPIS;

	@FXML
	private TableColumn<Det, String> impPISPISNTCST;

	@FXML
	private TableColumn<Det, String> impPISPISOutrCST;

	@FXML
	private TableColumn<Det, String> impPISPISOutrVBC;

	@FXML
	private TableColumn<Det, String> impPISPISOutrPPIS;

	@FXML
	private TableColumn<Det, String> impPISPISOutrQBCProd;

	@FXML
	private TableColumn<Det, String> impPISPISOutrVAliqProd;

	@FXML
	private TableColumn<Det, String> impPISPISOutrVPIS;

	@FXML
	private TableColumn<Det, String> impPISSTVBC;

	@FXML
	private TableColumn<Det, String> impPISSTPPIS;

	@FXML
	private TableColumn<Det, String> impPISSTQBCProd;

	@FXML
	private TableColumn<Det, String> impPISSTVAliqProd;

	@FXML
	private TableColumn<Det, String> impPISSTVPIS;

	@FXML
	private TableColumn<Det, String> impCOFINSCOFINSAliqCST;

	@FXML
	private TableColumn<Det, String> impCOFINSCOFINSAliqVBC;

	@FXML
	private TableColumn<Det, String> impCOFINSCOFINSAliqPCOFINS;

	@FXML
	private TableColumn<Det, String> impCOFINSCOFINSAliqVCOFINS;

	@FXML
	private TableColumn<Det, String> impCOFINSCOFINSQtdeCST;

	@FXML
	private TableColumn<Det, String> impCOFINSCOFINSQtdeQBCProd;

	@FXML
	private TableColumn<Det, String> impCOFINSCOFINSQtdeVAliqProd;

	@FXML
	private TableColumn<Det, String> impCOFINSCOFINSQtdeVCOFINS;

	@FXML
	private TableColumn<Det, String> impCOFINSCOFINSNTCST;

	@FXML
	private TableColumn<Det, String> impCOFINSCOFINSOutrCST;

	@FXML
	private TableColumn<Det, String> impCOFINSCOFINSOutrVBC;

	@FXML
	private TableColumn<Det, String> impCOFINSCOFINSOutrPCOFINS;

	@FXML
	private TableColumn<Det, String> impCOFINSCOFINSOutrQBCProd;

	@FXML
	private TableColumn<Det, String> impCOFINSCOFINSOutrVAliqProd;

	@FXML
	private TableColumn<Det, String> impCOFINSCOFINSOutrVCOFINS;

	@FXML
	private TableColumn<Det, String> impCOFINSSTVBC;

	@FXML
	private TableColumn<Det, String> impCOFINSSTPCOFINS;

	@FXML
	private TableColumn<Det, String> impCOFINSSTQBCProd;

	@FXML
	private TableColumn<Det, String> impCOFINSSTVAliqProd;

	@FXML
	private TableColumn<Det, String> impCOFINSSTVCOFINS;

	@FXML
	private TableColumn<Det, String> impISSQNVBC;

	@FXML
	private TableColumn<Det, String> impISSQNVAliq;

	@FXML
	private TableColumn<Det, String> impISSQNVISSQN;

	@FXML
	private TableColumn<Det, String> impISSQNCMunFG;

	@FXML
	private TableColumn<Det, String> impISSQNCListServ;

	@FXML
	private TableColumn<Det, String> impISSQNVDeducao;

	@FXML
	private TableColumn<Det, String> impISSQNVOutro;

	@FXML
	private TableColumn<Det, String> impISSQNVDescIncond;

	@FXML
	private TableColumn<Det, String> impISSQNVDescCond;

	@FXML
	private TableColumn<Det, String> impISSQNVISSRet;

	@FXML
	private TableColumn<Det, String> impISSQNIndISS;

	@FXML
	private TableColumn<Det, String> impISSQNCServico;

	@FXML
	private TableColumn<Det, String> impISSQNCMun;

	@FXML
	private TableColumn<Det, String> impISSQNCPais;

	@FXML
	private TableColumn<Det, String> impISSQNNProcesso;

	@FXML
	private TableColumn<Det, String> impISSQNIndIncentivo;

	@FXML
	private TableColumn<Det, String> detProdInfAdProd;

	// TABLE FATURAMENTO
	@FXML
	private TableView<Dup> tbViewFaturamento;

	@FXML
	private TableColumn<Dup, String> dupnDup;

	@FXML
	private TableColumn<Dup, String> dupdVenc;

	@FXML
	private TableColumn<Dup, String> dupvDup;

	// TABLE PROTOCOLO
	@FXML
	private Tab tabProtocolo;

	@FXML
	private TableView<TNfeProc> tbViewProtocolo;

	@FXML
	private TableColumn<TNfeProc, String> ideChaveNFe;

	@FXML
	private TableColumn<TNfeProc, String> ideProtocSEFAZ;

	@FXML
	private TableColumn<TNfeProc, String> ideDtProtocSEFAZ;

	@FXML
	private TableColumn<TNfeProc, String> ideNLote;

	// TABLE RELAÇÃO DE ITEM
	@FXML
	private TableView<RelacaoItems> tbViewRelacaoProd;

	@FXML
	private TableColumn<RelacaoItems, Integer> tbColStatus;

	@FXML
	private TableColumn<RelacaoItems, String> tbColCodRelProd;

	@FXML
	private TableColumn<RelacaoItems, String> tbColDescripcaoNFeRelProd;

	@FXML
	private TableColumn<RelacaoItems, String> tbColDescripcaoEptusRelProd;

	@FXML
	private TableColumn<RelacaoItems, String> tbColNoFornecedorRelProd;

	@FXML
	private TableColumn<RelacaoItems, String> tbColCodNCMNFeRelProd;

	@FXML
	private TableColumn<RelacaoItems, String> tbColCodNCMEptusRelProd;

	@FXML
	private TableColumn<RelacaoItems, String> tbColCodBarRelProd, tbColCodBarEptusRelProd;

	@FXML
	private TableColumn<RelacaoItems, String> tbColCFOPRelProd;

	@FXML
	private TableColumn<RelacaoItems, String> tbColCSTICMSRelProd;

	@FXML
	private TableColumn<RelacaoItems, String> tbColQtdNFeRelProd;

	@FXML
	private TableColumn<RelacaoItems, String> tbColUndRelProd;

	@FXML
	private TableColumn<RelacaoItems, String> tbColEmbComprasRelProd;

	@FXML
	private TableColumn<RelacaoItems, String> tbColFatorConversaoRelProd;

	@FXML
	private TableColumn<RelacaoItems, String> tbColQtdReposicaoRelProd;

	@FXML
	private TableColumn<RelacaoItems, String> tbColVlrUndBrutoRelProd;

	@FXML
	private TableColumn<RelacaoItems, String> tbColVlrDescontoRelProd;

	@FXML
	private TableColumn<RelacaoItems, String> tbColVlrDespAcessRelProd;

	@FXML
	private TableColumn<RelacaoItems, String> tbColVlrUndLiqRelProd, tbColVlrUnitBrutoNFeRelProd;

	@FXML
	private TableColumn<RelacaoItems, String> tbColAliqICMSRelProd;

	@FXML
	private TableColumn<RelacaoItems, String> tbColQtdConfRelProd;

	@FXML
	private TableColumn<RelacaoItems, String> tbColVlrFreteRelProd;

	@FXML
	private TableColumn<RelacaoItems, String> tbColVlrSegRelProd;

	@FXML
	private TableColumn<RelacaoItems, String> tbColAliqIPIRelProd;

	@FXML
	private TableColumn<RelacaoItems, String> tbColImpIPITribVBCRelProd;

	@FXML
	private TableColumn<RelacaoItems, String> tbColImpIPITribVIPIRelProd;

	@FXML
	private TableColumn<RelacaoItems, String> tbColVlrICMSDesonRelProd;

	@FXML
	private TableColumn<RelacaoItems, String> tbColReducBCICMSRelProd;

	@FXML
	private TableColumn<RelacaoItems, String> tbColBaseCalcICMSRelProd;

	@FXML
	private TableColumn<RelacaoItems, String> tbColVlrICMSSTRelProd;

	@FXML
	private TableColumn<RelacaoItems, String> tbColModBCICMSSubRelProd;

	@FXML
	private TableColumn<RelacaoItems, String> tbColPautaMVARelProd;

	@FXML
	private TableColumn<RelacaoItems, String> tbColBaseCalcICMSSubRelProd;

	@FXML
	private TableColumn<RelacaoItems, String> tbColOperationMathRelItem;

	@FXML
	private TableColumn<RelacaoItems, String> tbColVlrICMSSubRelProd;

	@FXML
	private TableColumn<RelacaoItems, String> tbColSubEstoqueDestRelProd;

	@FXML
	private Label lblTotalLinhas;

	@FXML
	private Label lblNumLinhas;

	@FXML
	private ProgressBar pBar;

	@FXML
	private Tab tabCabecalho;

	@FXML
	private Tab tabDetalhe;

	@FXML
	private Tab tabFaturamento;

	@FXML
	private TextField fatNFat;

	@FXML
	private TextField fatVOrig;

	@FXML
	private TextField fatVDesc;

	@FXML
	private TextField fatVLiq;

	// -----------FORM RELAÇÃO ITENS ------------------
	@FXML
	private CustomTextField txtCodItemRelItens, txtCodMoedaRelItens, txtCodUnidEmbRelItens, txtCodTribVendaRelItens;

	@FXML
	private TextField txtDescripcaoRelItens, txtCFOPRelItens, txtCSTRelItens, txtEmbCompRelItens, txtEmbVendaRelItens,
	txtCodBarraRelItens, txtUnidEmbRelItens, txtMoedaRelItens, txtTribVendaRelItens;

	@FXML
	private TextArea txtAreaObservacao;

	@FXML
	private CustomTextField txtCodUniRelItens;

	@FXML
	private TextField txtUnidadeRelItens;

	@FXML
	private CustomTextField txtCodFabRelItens;

	@FXML
	private TextField txtFabricanteRelItens;

	@FXML
	private CustomTextField txtCodGrupRelItens;

	@FXML
	private TextField txtGrupoRelItens;

	@FXML
	private CustomTextField txtCodSubGrupRelItens;

	@FXML
	private TextField txtSubGrupRelItens;

	@FXML
	private CustomTextField txtCodNCMRelItens;

	@FXML
	private TextField txtNCMRelItens;

	@FXML
	private CustomTextField txtCodGradEstoqRelItens;

	@FXML
	private TextField txtGradeRelItens, txtVlrUnitario, txtVlrDesconto, txtVlrICMS, txtVlrICMSDeson, txtSubstituicao,
	txtVlrIPI, txtDespAcessoria, txtVlrSeguro, txtVlrPIS, txtVlrCOFINS;

	@FXML
	private Button btnConfirmRepItens, btnSave;

	// ----------- ITEM VALOR ----------------------
	@FXML
	public TableView<ItemValor> tbViewPrecosEmp;

	@FXML
	private TableColumn<ItemValor, String> tbColEmpPreco, tbColUsuarioPreco;

	@FXML
	private TableColumn<ItemValor, LocalDateTime> tbColDtUltPreco;

	@FXML
	private TableColumn<ItemValor, BigDecimal> tbColValorCusto;

	// ---------- INFORMAÇÕES ADICIONAIS ----------
	@FXML
	private Tab tabInfAdic;

	@FXML
	private TextArea txtAreaInfAdic;

	static Stage stg;
	static String enderecoXML;
	private AnchorPane anchorPaneGuia;

	private ComposicaoPrecosController cpc;

	private String xmlNFe;

	Fornecedor fornecedorRe;

	public NotaFiscalEntradaController() {
		// TODO Auto-generated constructor stub
	}

	@FXML
	void actionBtnProcessarXML(ActionEvent event) {

		if (enderecoXML != null && !txtFileChooser.equals(""))
			importXMLTNFeProc(enderecoXML);
	}	


	@FXML
	void actionBtnDownloadNFe(ActionEvent event) {

		try {
			stg = new Stage();
			Scene scene = new Scene(util.openWindow("/views/utils/viewDownloadNFe.fxml",
					new DownloadNFeController(txtFileChooser, this)));
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
	void actionBtnFileChooserXML(ActionEvent event) {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Importar arquivo XML");
		
		String urlDefault = (!DadosGlobais.empresaLogada.getConfig().getCpaDiretorioXml().equals("") ? DadosGlobais.empresaLogada.getConfig().getCpaDiretorioXml() : "");
//		System.out.println(DadosGlobais.empresaLogada.getConfig().getCpaDiretorioXml().equals(""));
		if(!urlDefault.equals("")){
			fileChooser.setInitialDirectory(new File(urlDefault));
		}	

		// Agregar filtros para facilitar la busqueda
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML", "*.xml"));

		// Obtener la imagen seleccionada
		File xmlFile = fileChooser.showOpenDialog(anchorPanePrincipal.getScene().getWindow());

		// Mostar la imagen
		if (xmlFile != null) {
			// System.out.println(xmlFile.getName());
			txtFileChooser.setText(xmlFile.getName());
			txtFileChooser.setTooltip(new Tooltip(xmlFile.getAbsolutePath()));
			enderecoXML = xmlFile.getAbsolutePath();
			// btnProcessarXML.setDisable(false);
			// btnFileChooser.requestFocus();
			actionBtnProcessarXML(null);
		}
	}

//	@FXML
//	void actionTxtFilterColumn(ActionEvent event) {
//
//	}
//
//	@FXML
//	void keyPressedTxtFilterColumn(KeyEvent event) {
//
//	}
//
//	@FXML
//	void actionKeyPressedOperacao(KeyEvent keyEvnt) {
//		if (keyEvnt.getCode().equals(KeyCode.F2)) {
//			showSearch("OPERACAO");
//		}
//	}
//
//	@FXML
//	void actionKeyPressedCentroCusto(KeyEvent keyEvnt) {
//		if (keyEvnt.getCode().equals(KeyCode.F2)) {
//			showSearch("CENTRO_CUSTO");
//		}
//	}
//
//	@FXML
//	void actionKeyPressedPlanosContas(KeyEvent keyEvnt) {
//		if (keyEvnt.getCode().equals(KeyCode.F2)) {
//			showSearch("PLANOS_CONTAS");
//		}
//	}
//
//	@FXML
//	void actionKeyPressedPortador(KeyEvent keyEvnt) {
//		if (keyEvnt.getCode().equals(KeyCode.F2)) {
//			showSearch("PORTADOR");
//		}
//	}
//
//	@FXML
//	void actionKeyPressedSecao(KeyEvent keyEvnt) {
//		if (keyEvnt.getCode().equals(KeyCode.F2)) {
//			showSearch("SECAO");
//		}
//	}
//
//	@FXML
//	void keyReleasedTxtFilterColumn(KeyEvent event) {
//
//	}

	public void designTabPane() {
		StackPane stackPaneTabCab = new StackPane();
		Label lblCabecalho = new Label("Cabeçalho");
		stackPaneTabCab.getChildren().add(lblCabecalho);
		tabCabecalho.setContent(stackPaneTabCab);
	}

	public void loadImportNFeXMLHead(TableView<TNFe> tablea, TNFe xmlImport) {

		ideCUF.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
				// TODO Auto-generated method stub
				return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getIde().getCUF());
			}
		});

		ideCNF.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
				// TODO Auto-generated method stub
				return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getIde().getCNF());
			}
		});

		ideNatOp.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getIde().getNatOp());
					}
				});

		ideIndPag.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getIde().getIndPag());
					}
				});

		ideMod.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
				// TODO Auto-generated method stub
				return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getIde().getMod());
			}
		});

		ideSerie.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getIde().getSerie());
					}
				});

		ideNNF.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
				// TODO Auto-generated method stub
				return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getIde().getNNF());
			}
		});

		ideDEmi.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getIde().getDhEmi());
					}
				});

		ideDSaiEnt.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getIde().getDhSaiEnt());
					}
				});

		ideTpNF.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getIde().getTpNF());
					}
				});

		ideCMunFG.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getIde().getCMunFG());
					}
				});

		ideTpImp.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getIde().getTpImp());
					}
				});

		ideTpEmis.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getIde().getTpEmis());
					}
				});

		ideCDV.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
				// TODO Auto-generated method stub
				return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getIde().getCDV());
			}
		});

		ideTpAmb.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getIde().getTpAmb());
					}
				});

		ideFinNFe.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getIde().getFinNFe());
					}
				});

		ideProEmi.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getIde().getProcEmi());
					}
				});

		ideVerProc.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getIde().getVerProc());
					}
				});

		emitCNPJ.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getEmit() != null
								? Util.getStringConverterCNPJ(param.getValue().getInfNFe().getEmit().getCNPJ()) : null);
					}
				});

		emitCPF.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getEmit().getCPF());
					}
				});

		emitxNome.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getEmit().getXNome());
					}
				});

		emitxFant.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getEmit().getXFant());
					}
				});

		enderEmitxLgr.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue().getInfNFe().getEmit().getEnderEmit().getXLgr());
					}
				});

		enderEmitNro.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue().getInfNFe().getEmit().getEnderEmit().getNro());
					}
				});

		enderEmitxCpl.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue().getInfNFe().getEmit().getEnderEmit().getXCpl());
					}
				});

		enderEmitxBairro.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue().getInfNFe().getEmit().getEnderEmit().getXBairro());
					}
				});

		enderEmitcMun.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue().getInfNFe().getEmit().getEnderEmit().getCMun());
					}
				});

		enderEmitxMun.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue().getInfNFe().getEmit().getEnderEmit().getXMun());
					}
				});

		enderEmitUF.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue().getInfNFe().getEmit().getEnderEmit().getUF().value());
					}
				});

		enderEmitCEP.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue().getInfNFe().getEmit().getEnderEmit().getCEP());
					}
				});

		enderEmitcPais.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue().getInfNFe().getEmit().getEnderEmit().getCPais());
					}
				});

		enderEmitxPais.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue().getInfNFe().getEmit().getEnderEmit().getXPais());
					}
				});

		enderEmitFone.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue().getInfNFe().getEmit().getEnderEmit().getFone());
					}
				});

		emitIE.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
				// TODO Auto-generated method stub
				return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getEmit().getIE());
			}
		});

		emitIEST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getEmit().getIEST());
					}
				});

		emitIM.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
				// TODO Auto-generated method stub
				return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getEmit().getIM());
			}
		});

		emitCNAE.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getEmit().getCNAE());
					}
				});

		emitCRT.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getEmit().getCRT());
					}
				});

		destCNPJ.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getDest().getCNPJ());
					}
				});

		destCPF.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getDest().getCNPJ());
					}
				});

		destxNome.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getDest().getXNome());
					}
				});

		destEmail.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getDest().getEmail());
					}
				});
		enderDestxLgr.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue().getInfNFe().getDest().getEnderDest().getXLgr());
					}
				});

		enderDestNro.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue().getInfNFe().getDest().getEnderDest().getXCpl());
					}
				});

		enderDestxBairro.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue().getInfNFe().getDest().getEnderDest().getXBairro());
					}
				});

		enderDestcMun.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue().getInfNFe().getDest().getEnderDest().getCMun());
					}
				});

		enderDestxMun.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue().getInfNFe().getDest().getEnderDest().getXMun());
					}
				});

		enderDestUF.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue().getInfNFe().getDest().getEnderDest().getUF().value());
					}
				});

		enderDestCEP.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue().getInfNFe().getDest().getEnderDest().getCEP());
					}
				});

		enderDestcPais.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue().getInfNFe().getDest().getEnderDest().getCPais());
					}
				});

		enderDestxPais.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue().getInfNFe().getDest().getEnderDest().getXPais());
					}
				});

		enderDestFone.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue().getInfNFe().getDest().getEnderDest().getFone());
					}
				});

		destIE.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
				// TODO Auto-generated method stub
				return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getDest().getIE());
			}
		});

		destISUF.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getDest().getISUF());
					}
				});

		entregaCNPJ.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getEntrega() != null
								? param.getValue().getInfNFe().getEntrega().getCNPJ() : null);
					}
				});

		entregaxLgr.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getEntrega() != null
								? param.getValue().getInfNFe().getEntrega().getXLgr() : null);
					}
				});

		entregaNro.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getEntrega() != null
								? param.getValue().getInfNFe().getEntrega().getNro().toString() : null);
					}
				});

		entregaxCpl.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getEntrega() != null
								? param.getValue().getInfNFe().getEntrega().getXCpl() : null);
					}
				});

		entregaxBairro.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getEntrega() != null
								? param.getValue().getInfNFe().getEntrega().getXBairro() : null);
					}
				});

		entregacMun.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getEntrega() != null
								? param.getValue().getInfNFe().getEntrega().getCMun() : null);
					}
				});

		entregaxMun.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getEntrega() != null
								? param.getValue().getInfNFe().getEntrega().getXMun() : null);
					}
				});

		entregaUF.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getEntrega() != null
								? param.getValue().getInfNFe().getEntrega().getUF().value() : null);
					}
				});

		totalICMSTotvBC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getTotal().getICMSTot().getVBC());
					}
				});

		totalICMSTotvICMS.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue().getInfNFe().getTotal().getICMSTot().getVICMS());
					}
				});

		totalICMSTotvBCST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue().getInfNFe().getTotal().getICMSTot().getVBCST());
					}
				});

		totalICMSTotvST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getTotal().getICMSTot().getVST());
					}
				});

		totalICMSTotvProd.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue().getInfNFe().getTotal().getICMSTot().getVProd());
					}
				});

		totalICMSTotvFrete.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue().getInfNFe().getTotal().getICMSTot().getVFrete());
					}
				});

		totalICMSTotvSeg.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue().getInfNFe().getTotal().getICMSTot().getVSeg());
					}
				});

		totalICMSTotvDesc.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue().getInfNFe().getTotal().getICMSTot().getVDesc());
					}
				});

		totalICMSTotvII.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getTotal().getICMSTot().getVII());
					}
				});

		totalICMSTotvIPI.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue().getInfNFe().getTotal().getICMSTot().getVIPI());
					}
				});

		totalICMSTotvPIS.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue().getInfNFe().getTotal().getICMSTot().getVPIS());
					}
				});

		totalICMSTotvCOFINS.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue().getInfNFe().getTotal().getICMSTot().getVCOFINS());
					}
				});

		totalICMSTotvOUTRO.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue().getInfNFe().getTotal().getICMSTot().getVOutro());
					}
				});

		totalISSTotvServ.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getTotal().getISSQNtot() != null
								? param.getValue().getInfNFe().getTotal().getISSQNtot().getVServ() : null);
					}
				});

		totalISSTotvBC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getTotal().getISSQNtot() != null
								? param.getValue().getInfNFe().getTotal().getISSQNtot().getVBC() : null);
					}
				});

		totalISSTotvISS.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getTotal().getISSQNtot() != null
								? param.getValue().getInfNFe().getTotal().getISSQNtot().getVISS() : null);
					}
				});

		totalISSTotvPIS.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getTotal().getISSQNtot() != null
								? param.getValue().getInfNFe().getTotal().getISSQNtot().getVPIS() : null);
					}
				});

		totalISSTotvCOFINS.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getTotal().getISSQNtot() != null
								? param.getValue().getInfNFe().getTotal().getISSQNtot().getVCOFINS() : null);
					}
				});

		totalRetTribvRetPIS.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getTotal().getRetTrib() != null
								? param.getValue().getInfNFe().getTotal().getRetTrib().getVRetPIS() : null);
					}
				});

		totalRetTribvRetCOFINS.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getTotal().getRetTrib() != null
								? param.getValue().getInfNFe().getTotal().getRetTrib().getVRetCOFINS() : null);
					}
				});

		totalRetTribvRetCSLL.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getTotal().getRetTrib() != null
								? param.getValue().getInfNFe().getTotal().getRetTrib().getVRetCSLL() : null);
					}
				});

		totalRetTribvIRRF.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getTotal().getRetTrib() != null
								? param.getValue().getInfNFe().getTotal().getRetTrib().getVBCIRRF() : null);
					}
				});

		totalRetTribvIRRF.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getTotal().getRetTrib() != null
								? param.getValue().getInfNFe().getTotal().getRetTrib().getVIRRF() : null);
					}
				});

		totalRetTribvBCRetPrev.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getTotal().getRetTrib() != null
								? param.getValue().getInfNFe().getTotal().getRetTrib().getVBCRetPrev() : null);
					}
				});

		totalRetTribvRetPrev.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getTotal().getRetTrib() != null
								? param.getValue().getInfNFe().getTotal().getRetTrib().getVRetPrev() : null);
					}
				});

		transpModFrete.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getTransp() != null
								? param.getValue().getInfNFe().getTransp().getModFrete() : null);
					}
				});

		transportaCNPJ.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getTransp() != null
								&& param.getValue().getInfNFe().getTransp().getTransporta() != null
								? param.getValue().getInfNFe().getTransp().getTransporta().getCNPJ() : null);
					}
				});

		transportaCPF.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getTransp() != null
								&& param.getValue().getInfNFe().getTransp().getTransporta() != null
								? param.getValue().getInfNFe().getTransp().getTransporta().getCPF() : null);
					}
				});

		transportaANTT.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getTransp() != null
								&& param.getValue().getInfNFe().getTransp().getVeicTransp() != null
								? param.getValue().getInfNFe().getTransp().getVeicTransp().getRNTC() : null);
					}
				});

		transportaPlaca.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue().getInfNFe().getTransp().getVeicTransp() != null
								? param.getValue().getInfNFe().getTransp().getVeicTransp().getPlaca() : null);
					}
				});

		transportaPlacaUF.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue().getInfNFe().getTransp().getVeicTransp() != null
								? param.getValue().getInfNFe().getTransp().getVeicTransp().getUF().value()
										: null);
					}
				});

		transportaxNome.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue().getInfNFe().getTransp().getTransporta() != null
								? param.getValue().getInfNFe().getTransp().getTransporta().getXNome() : null);
					}
				});

		transportaxEnder.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getTransp() != null
								&& param.getValue().getInfNFe().getTransp().getTransporta() != null
								? param.getValue().getInfNFe().getTransp().getTransporta().getXEnder() : null);
					}
				});

		transportaxMun.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getTransp() != null
								&& param.getValue().getInfNFe().getTransp().getTransporta() != null
								? param.getValue().getInfNFe().getTransp().getTransporta().getXMun() : null);
					}
				});

		transportaUF.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getTransp() != null
								&& param.getValue().getInfNFe().getTransp().getTransporta() != null
								&& param.getValue().getInfNFe().getTransp().getTransporta().getUF() != null
								? param.getValue().getInfNFe().getTransp().getTransporta().getUF().value()
										: null);
					}
				});

		transpRetTranspvServ.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getTransp() != null
								&& param.getValue().getInfNFe().getTransp().getRetTransp() != null
								? param.getValue().getInfNFe().getTransp().getRetTransp().getVServ() : null);
					}
				});

		transpRetTranspvBCRet.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getTransp().getRetTransp() != null
								? param.getValue().getInfNFe().getTransp().getRetTransp().getVBCRet() : null);
					}
				});

		transpRetTransppICMSRet.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getTransp().getRetTransp() != null
								? param.getValue().getInfNFe().getTransp().getRetTransp().getPICMSRet() : null);
					}
				});

		transpRetTranspvICMSRet.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getTransp().getRetTransp() != null
								? param.getValue().getInfNFe().getTransp().getRetTransp().getVICMSRet() : null);
					}
				});

		transpRetTranspCFOP.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getTransp().getRetTransp() != null
								? param.getValue().getInfNFe().getTransp().getRetTransp().getCFOP() : null);
					}
				});

		transpRetTranspcMunFG.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getTransp().getRetTransp() != null
								? param.getValue().getInfNFe().getTransp().getRetTransp().getCMunFG() : null);
					}
				});

		transpVolqVol.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getTransp() != null
								&& !param.getValue().getInfNFe().getTransp().getVol().isEmpty()
								? param.getValue().getInfNFe().getTransp().getVol().iterator().next().getQVol()
										: null);
					}
				});

		transpVolEsp.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getTransp() != null
								&& !param.getValue().getInfNFe().getTransp().getVol().isEmpty()
								? param.getValue().getInfNFe().getTransp().getVol().iterator().next().getEsp()
										: null);
					}
				});

		transpVolMarca.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getTransp() != null
								&& !param.getValue().getInfNFe().getTransp().getVol().isEmpty()
								? param.getValue().getInfNFe().getTransp().getVol().iterator().next().getMarca()
										: null);
					}
				});

		transpVolPesoL.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getTransp() != null
								&& !param.getValue().getInfNFe().getTransp().getVol().isEmpty()
								? param.getValue().getInfNFe().getTransp().getVol().iterator().next().getPesoL()
										: null);
					}
				});

		transpVolPesoB.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getTransp() != null
								&& !param.getValue().getInfNFe().getTransp().getVol().isEmpty()
								? param.getValue().getInfNFe().getTransp().getVol().iterator().next().getPesoB()
										: null);
					}
				});

		// transpVolnLacres.setCellValueFactory(new
		// Callback<TableColumn.CellDataFeatures<TNFe, String>,
		// ObservableValue<String>>() {
		// @Override
		// public ObservableValue<String> call(CellDataFeatures<TNFe, String>
		// param) {
		// // TODO Auto-generated method stub
		// return new
		// ReadOnlyStringWrapper(param.getValue().getInfNFe().getTransp() !=null
		// && param.getValue().getInfNFe().getTransp().getVol() !=null &&
		// param.getValue().getInfNFe().getTransp().getVol().iterator().next().getLacres()
		// !=null ?
		// param.getValue().getInfNFe().getTransp().getVol().iterator().next().getLacres().iterator().next().getNLacre()
		// : null);
		// }
		// });

		exportaUFEmbarq.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getExporta() != null
								? param.getValue().getInfNFe().getExporta().getUFSaidaPais().value() : null);
					}
				});

		exportaxLocEmbarq.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNFe, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNFe, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getInfNFe().getExporta() != null
								? param.getValue().getInfNFe().getExporta().getXLocDespacho() : null);
					}
				});

		/*
		 * infAdicInfAdFisco.setCellValueFactory(new
		 * Callback<TableColumn.CellDataFeatures<TNFe, String>,
		 * ObservableValue<String>>() {
		 * 
		 * @Override public ObservableValue<String> call(CellDataFeatures<TNFe,
		 * String> param) { // TODO Auto-generated method stub return new
		 * ReadOnlyStringWrapper(param.getValue().getInfNFe().getInfAdic() !=
		 * null ? param.getValue().getInfNFe().getInfAdic().getInfAdFisco() :
		 * null); } });
		 * 
		 * infAdicInfCpl.setCellValueFactory(new
		 * Callback<TableColumn.CellDataFeatures<TNFe, String>,
		 * ObservableValue<String>>() {
		 * 
		 * @Override public ObservableValue<String> call(CellDataFeatures<TNFe,
		 * String> param) { // TODO Auto-generated method stub return new
		 * ReadOnlyStringWrapper(param.getValue().getInfNFe().getInfAdic() !=
		 * null ? param.getValue().getInfNFe().getInfAdic().getInfCpl() : null);
		 * } });
		 */
		tablea.setItems(FXCollections.observableArrayList(xmlImport));
	}

	BigDecimal vProd;
	BigDecimal vDesc;

	/**
	 * TABLE DETALHES
	 * 
	 * @param tabela
	 * @param xmlImport
	 */
	public void loadImportNFeXMLDetail(TableView<Det> tabela, List<Det> xmlImport) {

		detNoItem.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue() != null ? param.getValue().getNItem() : null);
					}
				});

		detProdcProd.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue() != null ? param.getValue().getProd().getCProd() : null);
					}
				});

		detProdcEAN.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue() != null ? param.getValue().getProd().getCEAN() : null);
					}
				});

		detProdxProd.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue() != null ? param.getValue().getProd().getXProd() : null);
					}
				});

		detProdNCM.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue() != null ? param.getValue().getProd().getNCM() : null);
					}
				});

		detProdEXTIPI.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue() != null ? param.getValue().getProd().getEXTIPI() : null);
					}
				});

		detProdCFOP.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue() != null ? param.getValue().getProd().getCFOP() : null);
					}
				});

		detProduCom.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue() != null ? param.getValue().getProd().getUCom() : null);
					}
				});

		detProdqCom.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue() != null ? param.getValue().getProd().getQCom() : null);
					}
				});

		// detProdqDev.setCellValueFactory(new
		// Callback<TableColumn.CellDataFeatures<Det, String>,
		// ObservableValue<String>>() {
		// @Override
		// public ObservableValue<String> call(CellDataFeatures<Det, String>
		// param) {
		// // TODO Auto-generated method stub
		// return new ReadOnlyStringWrapper(param.getValue() != null ?
		// param.getValue().getProd().get : null);
		// }
		// });

		detProdvUnCom.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue() != null ? param.getValue().getProd().getVUnCom() : null);
					}
				});

		detProdvProd.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue() != null ? param.getValue().getProd().getVProd() : null);
					}
				});

		detProdcEANTrib.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue() != null ? param.getValue().getProd().getCEANTrib() : null);
					}
				});

		detProduTrib.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue() != null ? param.getValue().getProd().getUTrib() : null);
					}
				});

		detProdqTrib.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue() != null ? param.getValue().getProd().getQTrib() : null);
					}
				});

		detProdvUnTrib.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue() != null ? param.getValue().getProd().getVUnTrib() : null);
					}
				});

		detProdvFrete.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue() != null ? param.getValue().getProd().getVFrete() : null);
					}
				});

		detProdvSeg.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue() != null ? param.getValue().getProd().getVSeg() : null);
					}
				});

		detProdvDesc.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue() != null ? param.getValue().getProd().getVDesc() : null);
					}
				});

		detProdIndTot.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								param.getValue() != null ? param.getValue().getProd().getIndTot() : null);
					}
				});

		detProdVeictpOP.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getProd().getVeicProd() != null
								? param.getValue().getProd().getVeicProd().getTpOp() : null);
					}
				});

		detProdVeicChassi.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getProd().getVeicProd() != null
								? param.getValue().getProd().getVeicProd().getChassi() : null);
					}
				});

		detProdVeiccCor.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getProd().getVeicProd() != null
								? param.getValue().getProd().getVeicProd().getCCor() : null);
					}
				});

		detProdVeicxCor.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getProd().getVeicProd() != null
								? param.getValue().getProd().getVeicProd().getXCor() : null);
					}
				});

		detProdVeicPot.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getProd().getVeicProd() != null
								? param.getValue().getProd().getVeicProd().getPot() : null);
					}
				});

		// detProdVeicCm3.setCellValueFactory(new
		// Callback<TableColumn.CellDataFeatures<Det, String>,
		// ObservableValue<String>>() {
		// @Override
		// public ObservableValue<String> call(CellDataFeatures<Det, String>
		// param) {
		// // TODO Auto-generated method stub
		// return new ReadOnlyStringWrapper(param.getValue() != null ?
		// param.getValue().getProd().getVeicProd().get : null);
		// }
		// });

		detProdVeicPesoL.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getProd().getVeicProd() != null
								? param.getValue().getProd().getVeicProd().getPesoL() : null);
					}
				});

		detProdVeicPesoB.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getProd().getVeicProd() != null
								? param.getValue().getProd().getVeicProd().getPesoB() : null);
					}
				});

		detProdVeicnSerie.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getProd().getVeicProd() != null
								? param.getValue().getProd().getVeicProd().getNSerie() : null);
					}
				});

		detProdVeicTpComb.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getProd().getVeicProd() != null
								? param.getValue().getProd().getVeicProd().getTpComb() : null);
					}
				});

		detProdVeicnMotor.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getProd().getVeicProd() != null
								? param.getValue().getProd().getVeicProd().getNMotor() : null);
					}
				});

		// detProdVeicCmkg.setCellValueFactory(new
		// Callback<TableColumn.CellDataFeatures<Det, String>,
		// ObservableValue<String>>() {
		// @Override
		// public ObservableValue<String> call(CellDataFeatures<Det, String>
		// param) {
		// // TODO Auto-generated method stub
		// return new ReadOnlyStringWrapper(param.getValue() != null ?
		// param.getValue().getProd().getVeicProd().get : null);
		// }
		// });

		detProdVeicDist.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getProd().getVeicProd() != null
								? param.getValue().getProd().getVeicProd().getDist() : null);
					}
				});

		// detProdVeicRenavam.setCellValueFactory(new
		// Callback<TableColumn.CellDataFeatures<Det, String>,
		// ObservableValue<String>>() {
		// @Override
		// public ObservableValue<String> call(CellDataFeatures<Det, String>
		// param) {
		// // TODO Auto-generated method stub
		// return new ReadOnlyStringWrapper(param.getValue() != null ?
		// param.getValue().getProd().getVeicProd().getr : null);
		// }
		// });

		detProdVeicAnoMod.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getProd().getVeicProd() != null
								? param.getValue().getProd().getVeicProd().getAnoMod() : null);
					}
				});

		detProdVeicAnoFab.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getProd().getVeicProd() != null
								? param.getValue().getProd().getVeicProd().getAnoFab() : null);
					}
				});

		detProdVeicTpPint.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getProd().getVeicProd() != null
								? param.getValue().getProd().getVeicProd().getTpPint() : null);
					}
				});

		detProdVeicTpVeic.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getProd().getVeicProd() != null
								? param.getValue().getProd().getVeicProd().getTpVeic() : null);
					}
				});

		detProdVeicEspVeic.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getProd().getVeicProd() != null
								? param.getValue().getProd().getVeicProd().getEspVeic() : null);
					}
				});

		detProdVeicVin.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getProd().getVeicProd() != null
								? param.getValue().getProd().getVeicProd().getVIN() : null);
					}
				});

		detProdVeicCondVeic.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getProd().getVeicProd() != null
								? param.getValue().getProd().getVeicProd().getCondVeic() : null);
					}
				});

		detProdVeicCMod.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getProd().getVeicProd() != null
								? param.getValue().getProd().getVeicProd().getCMod() : null);
					}
				});

		detProdCombCProdANP.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getProd().getComb() != null
								? param.getValue().getProd().getComb().getCProdANP() : null);
					}
				});

		detProdCombCODIF.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getProd().getComb() != null
								? param.getValue().getProd().getComb().getCODIF() : null);
					}
				});

		detProdCombQTemp.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getProd().getComb() != null
								? param.getValue().getProd().getComb().getQTemp() : null);
					}
				});

		detProdCombUFCons.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getProd().getComb() != null
								? param.getValue().getProd().getComb().getUFCons().value() : null);
					}
				});

		detProdCombCIDEQBCProd.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getProd().getComb() != null
								? param.getValue().getProd().getComb().getCIDE().getQBCProd() : null);
					}
				});

		detProdCombCIDEvCIDE.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getProd().getComb() != null
								? param.getValue().getProd().getComb().getCIDE().getVCIDE() : null);
					}
				});

		detProdCombCIDEVAliqProd.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getProd().getComb() != null
								? param.getValue().getProd().getComb().getCIDE().getVAliqProd() : null);
					}
				});

		impIPIcEnq.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						TIpi ipi = getTIpi(param.getValue());
						return new ReadOnlyStringWrapper(ipi != null ? ipi.getCEnq() : null);
					}
				});

		impIPICNPJProd.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						TIpi ipi = getTIpi(param.getValue());
						return new ReadOnlyStringWrapper(ipi != null ? ipi.getCNPJProd() : null);
					}
				});

		impIPIcSelo.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						TIpi ipi = getTIpi(param.getValue());
						return new ReadOnlyStringWrapper(ipi != null ? ipi.getCSelo() : null);
					}
				});

		impIPIqSelo.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						TIpi ipi = getTIpi(param.getValue());
						return new ReadOnlyStringWrapper(ipi != null ? ipi.getQSelo() : null);
					}
				});

		impIPITribCST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						TIpi ipi = getTIpi(param.getValue());
						return new ReadOnlyStringWrapper(
								(ipi != null && ipi.getIPITrib() != null) ? ipi.getIPITrib().getCST() : null);
					}
				});

		impIPITribVBC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						TIpi ipi = getTIpi(param.getValue());
						return new ReadOnlyStringWrapper(
								(ipi != null && ipi.getIPITrib() != null) ? ipi.getIPITrib().getVBC() : null);
					}
				});

		impIPITribQUnid.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						TIpi ipi = getTIpi(param.getValue());
						return new ReadOnlyStringWrapper(
								(ipi != null && ipi.getIPITrib() != null) ? ipi.getIPITrib().getQUnid() : null);
					}
				});

		impIPITribVUnid.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						TIpi ipi = getTIpi(param.getValue());
						return new ReadOnlyStringWrapper(
								(ipi != null && ipi.getIPITrib() != null) ? ipi.getIPITrib().getVUnid() : null);
					}
				});

		impIPITribPIPI.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						TIpi ipi = getTIpi(param.getValue());
						return new ReadOnlyStringWrapper(
								(ipi != null && ipi.getIPITrib() != null) ? ipi.getIPITrib().getPIPI() : null);
					}
				});

		impIPITribVIPI.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						TIpi ipi = getTIpi(param.getValue());
						return new ReadOnlyStringWrapper(
								(ipi != null && ipi.getIPITrib() != null) ? ipi.getIPITrib().getVIPI() : null);
					}
				});

		impIPINTCST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						TIpi ipi = getTIpi(param.getValue());
						return new ReadOnlyStringWrapper(
								(ipi != null && ipi.getIPINT() != null) ? ipi.getIPINT().getCST() : null);
					}
				});

		impICMS00Orig.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						// ICMS icms = null;
						// if
						// (param.getValue().getImposto().getContent().iterator().next().getValue()
						// != null && param
						// .getValue().getImposto().getContent().iterator().next().getValue()
						// instanceof ICMS) {
						// icms = (ICMS)
						// param.getValue().getImposto().getContent().iterator().next().getValue();
						// }
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS00() != null) ? icms.getICMS00().getOrig() : null);
					}
				});

		impICMS00CST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						// ICMS icms = null;
						// if
						// (param.getValue().getImposto().getContent().iterator().next().getValue()
						// != null && param
						// .getValue().getImposto().getContent().iterator().next().getValue()
						// instanceof ICMS) {
						// icms = (ICMS)
						// param.getValue().getImposto().getContent().iterator().next().getValue();
						// }
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS00() != null) ? icms.getICMS00().getCST() : null);
					}
				});

		impICMS00ModBC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						// ICMS icms = null;
						// if
						// (param.getValue().getImposto().getContent().iterator().next().getValue()
						// != null && param
						// .getValue().getImposto().getContent().iterator().next().getValue()
						// instanceof ICMS) {
						// icms = (ICMS)
						// param.getValue().getImposto().getContent().iterator().next().getValue();
						// }
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS00() != null) ? icms.getICMS00().getModBC() : null);
					}
				});

		impICMS00VBC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						// ICMS icms = null;
						// if
						// (param.getValue().getImposto().getContent().iterator().next().getValue()
						// != null && param
						// .getValue().getImposto().getContent().iterator().next().getValue()
						// instanceof ICMS) {
						// icms = (ICMS)
						// param.getValue().getImposto().getContent().iterator().next().getValue();
						// }
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS00() != null) ? icms.getICMS00().getVBC() : null);
					}
				});

		impICMS00PICMS.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						// ICMS icms = null;
						// if
						// (param.getValue().getImposto().getContent().iterator().next().getValue()
						// != null && param
						// .getValue().getImposto().getContent().iterator().next().getValue()
						// instanceof ICMS) {
						// icms = (ICMS)
						// param.getValue().getImposto().getContent().iterator().next().getValue();
						// }
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS00() != null) ? icms.getICMS00().getPICMS() : null);
					}
				});

		impICMS00VICMS.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						// ICMS icms = null;
						// if
						// (param.getValue().getImposto().getContent().iterator().next().getValue()
						// != null && param
						// .getValue().getImposto().getContent().iterator().next().getValue()
						// instanceof ICMS) {
						// icms = (ICMS)
						// param.getValue().getImposto().getContent().iterator().next().getValue();
						// }
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS00() != null) ? icms.getICMS00().getVICMS() : null);
					}
				});

		impICMS10Orig.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						// ICMS icms = null;
						// if
						// (param.getValue().getImposto().getContent().iterator().next().getValue()
						// != null && param
						// .getValue().getImposto().getContent().iterator().next().getValue()
						// instanceof ICMS) {
						// icms = (ICMS)
						// param.getValue().getImposto().getContent().iterator().next().getValue();
						// }
						// return new ReadOnlyStringWrapper(
						// (icms != null && icms.getICMS10() != null) ?
						// icms.getICMS10().getOrig() : null);
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS10() != null) ? icms.getICMS10().getOrig() : null);
					}
				});

		impICMS10CST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						// ICMS icms = null;
						// if
						// (param.getValue().getImposto().getContent().iterator().next().getValue()
						// != null && param
						// .getValue().getImposto().getContent().iterator().next().getValue()
						// instanceof ICMS) {
						// icms = (ICMS)
						// param.getValue().getImposto().getContent().iterator().next().getValue();
						// }
						// return new ReadOnlyStringWrapper(
						// (icms != null && icms.getICMS10() != null) ?
						// icms.getICMS10().getCST() : null);
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS10() != null) ? icms.getICMS10().getCST() : null);
					}
				});

		impICMS10ModBC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						// ICMS icms = null;
						// if
						// (param.getValue().getImposto().getContent().iterator().next().getValue()
						// != null && param
						// .getValue().getImposto().getContent().iterator().next().getValue()
						// instanceof ICMS) {
						// icms = (ICMS)
						// param.getValue().getImposto().getContent().iterator().next().getValue();
						// }
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS10() != null) ? icms.getICMS10().getModBC() : null);
					}
				});

		impICMS10VBC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						// ICMS icms = null;
						// if
						// (param.getValue().getImposto().getContent().iterator().next().getValue()
						// != null && param
						// .getValue().getImposto().getContent().iterator().next().getValue()
						// instanceof ICMS) {
						// icms = (ICMS)
						// param.getValue().getImposto().getContent().iterator().next().getValue();
						// }
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS10() != null) ? icms.getICMS10().getVBC() : null);
					}
				});

		impICMS10PICMS.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						// ICMS icms = null;
						// if
						// (param.getValue().getImposto().getContent().iterator().next().getValue()
						// != null && param
						// .getValue().getImposto().getContent().iterator().next().getValue()
						// instanceof ICMS) {
						// icms = (ICMS)
						// param.getValue().getImposto().getContent().iterator().next().getValue();
						// }
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS10() != null) ? icms.getICMS10().getPICMS() : null);
					}
				});

		impICMS10VICMS.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						// ICMS icms = null;
						// if
						// (param.getValue().getImposto().getContent().iterator().next().getValue()
						// != null && param
						// .getValue().getImposto().getContent().iterator().next().getValue()
						// instanceof ICMS) {
						// icms = (ICMS)
						// param.getValue().getImposto().getContent().iterator().next().getValue();
						// }
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS10() != null) ? icms.getICMS10().getVICMS() : null);
					}
				});

		impICMS10ModBCST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						// ICMS icms = null;
						// if
						// (param.getValue().getImposto().getContent().iterator().next().getValue()
						// != null && param
						// .getValue().getImposto().getContent().iterator().next().getValue()
						// instanceof ICMS) {
						// icms = (ICMS)
						// param.getValue().getImposto().getContent().iterator().next().getValue();
						// }
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS10() != null) ? icms.getICMS10().getModBCST() : null);
					}
				});

		impICMS10PMVAST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						// ICMS icms = null;
						// if
						// (param.getValue().getImposto().getContent().iterator().next().getValue()
						// != null && param
						// .getValue().getImposto().getContent().iterator().next().getValue()
						// instanceof ICMS) {
						// icms = (ICMS)
						// param.getValue().getImposto().getContent().iterator().next().getValue();
						// }
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS10() != null) ? icms.getICMS10().getPMVAST() : null);
					}
				});

		impICMS10PRedBCST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						// ICMS icms = null;
						// if
						// (param.getValue().getImposto().getContent().iterator().next().getValue()
						// != null && param
						// .getValue().getImposto().getContent().iterator().next().getValue()
						// instanceof ICMS) {
						// icms = (ICMS)
						// param.getValue().getImposto().getContent().iterator().next().getValue();
						// }
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS10() != null) ? icms.getICMS10().getPRedBCST() : null);
					}
				});

		impICMS10VBCST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						// ICMS icms = null;
						// if
						// (param.getValue().getImposto().getContent().iterator().next().getValue()
						// != null && param
						// .getValue().getImposto().getContent().iterator().next().getValue()
						// instanceof ICMS) {
						// icms = (ICMS)
						// param.getValue().getImposto().getContent().iterator().next().getValue();
						// }
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS10() != null) ? icms.getICMS10().getVBCST() : null);
					}
				});

		impICMS10PICMSST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						// ICMS icms = null;
						// if
						// (param.getValue().getImposto().getContent().iterator().next().getValue()
						// != null && param
						// .getValue().getImposto().getContent().iterator().next().getValue()
						// instanceof ICMS) {
						// icms = (ICMS)
						// param.getValue().getImposto().getContent().iterator().next().getValue();
						// }
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS10() != null) ? icms.getICMS10().getPICMSST() : null);
					}
				});

		impICMS10VICMSST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						// ICMS icms = null;
						// if
						// (param.getValue().getImposto().getContent().iterator().next().getValue()
						// != null && param
						// .getValue().getImposto().getContent().iterator().next().getValue()
						// instanceof ICMS) {
						// icms = (ICMS)
						// param.getValue().getImposto().getContent().iterator().next().getValue();
						// }
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS10() != null) ? icms.getICMS10().getVICMSST() : null);
					}
				});

		//
		impICMS20Orig.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						// ICMS icms = null;
						// if
						// (param.getValue().getImposto().getContent().iterator().next().getValue()
						// != null && param
						// .getValue().getImposto().getContent().iterator().next().getValue()
						// instanceof ICMS) {
						// icms = (ICMS)
						// param.getValue().getImposto().getContent().iterator().next().getValue();
						// }
						// icms = (ICMS)
						// param.getValue().getImposto().getContent().iterator().next().getValue();
						// System.out.println("ICMS Orig 20"+
						// icms.getICMS20().getOrig());
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS20() != null) ? icms.getICMS20().getOrig() : null);
					}
				});

		impICMS20CST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						// ICMS icms = null;
						// if
						// (param.getValue().getImposto().getContent().iterator().next().getValue()
						// != null && param
						// .getValue().getImposto().getContent().iterator().next().getValue()
						// instanceof ICMS) {
						// icms = (ICMS)
						// param.getValue().getImposto().getContent().iterator().next().getValue();
						// }
						// icms = (ICMS)
						// param.getValue().getImposto().getContent().iterator().next().getValue();
						// System.out.println("ICMS CST 20"+
						// icms.getICMS20().getCST());
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS20() != null) ? icms.getICMS20().getCST() : null);
					}
				});

		impICMS20ModBC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						// ICMS icms = null;
						// if
						// (param.getValue().getImposto().getContent().iterator().next().getValue()
						// != null && param
						// .getValue().getImposto().getContent().iterator().next().getValue()
						// instanceof ICMS) {
						// icms = (ICMS)
						// param.getValue().getImposto().getContent().iterator().next().getValue();
						// }
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS20() != null) ? icms.getICMS20().getModBC() : null);
					}
				});

		impICMS20VBC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						// ICMS icms = null;
						// if
						// (param.getValue().getImposto().getContent().iterator().next().getValue()
						// != null && param
						// .getValue().getImposto().getContent().iterator().next().getValue()
						// instanceof ICMS) {
						// icms = (ICMS)
						// param.getValue().getImposto().getContent().iterator().next().getValue();
						// }
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS20() != null) ? icms.getICMS20().getVBC() : null);
					}
				});

		impICMS20PICMS.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						// ICMS icms = null;
						// if
						// (param.getValue().getImposto().getContent().iterator().next().getValue()
						// != null && param
						// .getValue().getImposto().getContent().iterator().next().getValue()
						// instanceof ICMS) {
						// icms = (ICMS)
						// param.getValue().getImposto().getContent().iterator().next().getValue();
						// }
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS20() != null) ? icms.getICMS20().getPICMS() : null);
					}
				});

		impICMS20VICMS.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						// ICMS icms = null;
						// if
						// (param.getValue().getImposto().getContent().iterator().next().getValue()
						// != null && param
						// .getValue().getImposto().getContent().iterator().next().getValue()
						// instanceof ICMS) {
						// icms = (ICMS)
						// param.getValue().getImposto().getContent().iterator().next().getValue();
						// }
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS20() != null) ? icms.getICMS20().getVICMS() : null);
					}
				});

		impICMS20VICMSDeson.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						// ICMS icms = null;
						// if
						// (param.getValue().getImposto().getContent().iterator().next().getValue()
						// != null && param
						// .getValue().getImposto().getContent().iterator().next().getValue()
						// instanceof ICMS) {
						// icms = (ICMS)
						// param.getValue().getImposto().getContent().iterator().next().getValue();
						// }
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS20() != null) ? icms.getICMS20().getVICMSDeson() : null);
					}
				});

		impICMS20MotDesICMS.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						// ICMS icms = null;
						// if
						// (param.getValue().getImposto().getContent().iterator().next().getValue()
						// != null && param
						// .getValue().getImposto().getContent().iterator().next().getValue()
						// instanceof ICMS) {
						// icms = (ICMS)
						// param.getValue().getImposto().getContent().iterator().next().getValue();
						// }
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS20() != null) ? icms.getICMS20().getMotDesICMS() : null);
					}
				});

		impICMS30Orig.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						// ICMS icms = null;
						// if
						// (param.getValue().getImposto().getContent().iterator().next().getValue()
						// != null && param
						// .getValue().getImposto().getContent().iterator().next().getValue()
						// instanceof ICMS) {
						// icms = (ICMS)
						// param.getValue().getImposto().getContent().iterator().next().getValue();
						// }
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS30() != null) ? icms.getICMS30().getOrig() : null);
					}
				});

		impICMS30CST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						// ICMS icms = null;
						// if
						// (param.getValue().getImposto().getContent().iterator().next().getValue()
						// != null && param
						// .getValue().getImposto().getContent().iterator().next().getValue()
						// instanceof ICMS) {
						// icms = (ICMS)
						// param.getValue().getImposto().getContent().iterator().next().getValue();
						// }
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS30() != null) ? icms.getICMS30().getCST() : null);
					}
				});

		impICMS30ModBCST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						// ICMS icms = null;
						// if
						// (param.getValue().getImposto().getContent().iterator().next().getValue()
						// != null && param
						// .getValue().getImposto().getContent().iterator().next().getValue()
						// instanceof ICMS) {
						// icms = (ICMS)
						// param.getValue().getImposto().getContent().iterator().next().getValue();
						// }
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS30() != null) ? icms.getICMS30().getModBCST() : null);
					}
				});

		impICMS30PMVAST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						// ICMS icms = null;
						// if
						// (param.getValue().getImposto().getContent().iterator().next().getValue()
						// != null && param
						// .getValue().getImposto().getContent().iterator().next().getValue()
						// instanceof ICMS) {
						// icms = (ICMS)
						// param.getValue().getImposto().getContent().iterator().next().getValue();
						// }
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS30() != null) ? icms.getICMS30().getPMVAST() : null);
					}
				});

		impICMS30PRedBCST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						// ICMS icms = null;
						// if
						// (param.getValue().getImposto().getContent().iterator().next().getValue()
						// != null && param
						// .getValue().getImposto().getContent().iterator().next().getValue()
						// instanceof ICMS) {
						// icms = (ICMS)
						// param.getValue().getImposto().getContent().iterator().next().getValue();
						// }
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS30() != null) ? icms.getICMS30().getPRedBCST() : null);
					}
				});

		impICMS30VBCST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						// ICMS icms = null;
						// if
						// (param.getValue().getImposto().getContent().iterator().next().getValue()
						// != null && param
						// .getValue().getImposto().getContent().iterator().next().getValue()
						// instanceof ICMS) {
						// icms = (ICMS)
						// param.getValue().getImposto().getContent().iterator().next().getValue();
						// }
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS30() != null) ? icms.getICMS30().getVBCST() : null);
					}
				});

		impICMS30PICMSST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						// ICMS icms = null;
						// if
						// (param.getValue().getImposto().getContent().iterator().next().getValue()
						// != null && param
						// .getValue().getImposto().getContent().iterator().next().getValue()
						// instanceof ICMS) {
						// icms = (ICMS)
						// param.getValue().getImposto().getContent().iterator().next().getValue();
						// }
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS30() != null) ? icms.getICMS30().getPICMSST() : null);
					}
				});

		impICMS30VICMSST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						// ICMS icms = null;
						// if
						// (param.getValue().getImposto().getContent().iterator().next().getValue()
						// != null && param
						// .getValue().getImposto().getContent().iterator().next().getValue()
						// instanceof ICMS) {
						// icms = (ICMS)
						// param.getValue().getImposto().getContent().iterator().next().getValue();
						// }
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS30() != null) ? icms.getICMS30().getVICMSST() : null);
					}
				});

		impICMS30VICMSDeson.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						// ICMS icms = null;
						// if
						// (param.getValue().getImposto().getContent().iterator().next().getValue()
						// != null && param
						// .getValue().getImposto().getContent().iterator().next().getValue()
						// instanceof ICMS) {
						// icms = (ICMS)
						// param.getValue().getImposto().getContent().iterator().next().getValue();
						// }
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS30() != null) ? icms.getICMS30().getVICMSDeson() : null);
					}
				});

		impICMS30MotDesICMS.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						// ICMS icms = null;
						// if
						// (param.getValue().getImposto().getContent().iterator().next().getValue()
						// != null && param
						// .getValue().getImposto().getContent().iterator().next().getValue()
						// instanceof ICMS) {
						// icms = (ICMS)
						// param.getValue().getImposto().getContent().iterator().next().getValue();
						// }
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS30() != null) ? icms.getICMS30().getMotDesICMS() : null);
					}
				});

		impICMS40Orig.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS40() != null) ? icms.getICMS40().getOrig() : null);
					}
				});

		impICMS40CST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS40() != null) ? icms.getICMS40().getCST() : null);
					}
				});

		impICMS40VICMSDeson.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS40() != null) ? icms.getICMS40().getVICMSDeson() : null);
					}
				});

		impICMS40MotDesICMS.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS40() != null) ? icms.getICMS40().getMotDesICMS() : null);
					}
				});

		impICMS51Orig.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS51() != null) ? icms.getICMS51().getOrig() : null);
					}
				});

		impICMS51CST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS51() != null) ? icms.getICMS51().getCST() : null);
					}
				});

		impICMS51ModBC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS51() != null) ? icms.getICMS51().getModBC() : null);
					}
				});

		impICMS51VBC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS51() != null) ? icms.getICMS51().getVBC() : null);
					}
				});

		impICMS51PICMS.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS51() != null) ? icms.getICMS51().getPICMS() : null);
					}
				});

		impICMS51VICMS.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS51() != null) ? icms.getICMS51().getVICMS() : null);
					}
				});

		impICMS51ModBC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS51() != null) ? icms.getICMS51().getModBC() : null);
					}
				});

		impICMS51PRedBC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS51() != null) ? icms.getICMS51().getPRedBC() : null);
					}
				});

		impICMS60Orig.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS60() != null) ? icms.getICMS60().getOrig() : null);
					}
				});

		impICMS60CST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS60() != null) ? icms.getICMS60().getCST() : null);
					}
				});

		impICMS60VBCSTRet.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS60() != null) ? icms.getICMS60().getVBCSTRet() : null);
					}
				});

		impICMS60VICMSSTRet.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS60() != null) ? icms.getICMS60().getVICMSSTRet() : null);
					}
				});

		impICMS70Orig.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS70() != null) ? icms.getICMS70().getOrig() : null);
					}
				});

		impICMS70CST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS70() != null) ? icms.getICMS70().getCST() : null);
					}
				});

		impICMS70ModBC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS70() != null) ? icms.getICMS70().getModBC() : null);
					}
				});

		impICMS70PRedBC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS70() != null) ? icms.getICMS70().getPRedBC() : null);
					}
				});

		impICMS70VBC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS70() != null) ? icms.getICMS70().getVBC() : null);
					}
				});

		impICMS70PICMS.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS70() != null) ? icms.getICMS70().getPICMS() : null);
					}
				});

		impICMS70VICMS.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS70() != null) ? icms.getICMS70().getVICMS() : null);
					}
				});

		impICMS70ModBCST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS70() != null) ? icms.getICMS70().getModBCST() : null);
					}
				});

		impICMS70PMVAST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS70() != null) ? icms.getICMS70().getPMVAST() : null);
					}
				});

		impICMS70PRedBCST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS70() != null) ? icms.getICMS70().getPRedBCST() : null);
					}
				});

		impICMS70VBCST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS70() != null) ? icms.getICMS70().getVBCST() : null);
					}
				});

		impICMS70PICMSST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS70() != null) ? icms.getICMS70().getPICMSST() : null);
					}
				});

		impICMS70VICMSST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS70() != null) ? icms.getICMS70().getVICMSST() : null);
					}
				});

		impICMS70VICMSDeson.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS70() != null) ? icms.getICMS70().getVICMSDeson() : null);
					}
				});

		impICMS70MotDesICMS.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS70() != null) ? icms.getICMS70().getMotDesICMS() : null);
					}
				});

		impICMS90Orig.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS90() != null) ? icms.getICMS90().getOrig() : null);
					}
				});

		impICMS90CST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS90() != null) ? icms.getICMS90().getCST() : null);
					}
				});

		impICMS90ModBC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS90() != null) ? icms.getICMS90().getModBC() : null);
					}
				});

		impICMS90PRedBC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS90() != null) ? icms.getICMS90().getPRedBC() : null);
					}
				});

		impICMS90VBC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS90() != null) ? icms.getICMS90().getVBC() : null);
					}
				});

		impICMS90PICMS.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS90() != null) ? icms.getICMS90().getPICMS() : null);
					}
				});

		impICMS90VICMS.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS90() != null) ? icms.getICMS90().getVICMS() : null);
					}
				});

		impICMS90ModBCST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS90() != null) ? icms.getICMS90().getModBCST() : null);
					}
				});

		impICMS90PMVAST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS90() != null) ? icms.getICMS90().getPMVAST() : null);
					}
				});

		impICMS90PRedBCST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS90() != null) ? icms.getICMS90().getPRedBCST() : null);
					}
				});

		impICMS90VBCST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS90() != null) ? icms.getICMS90().getVBCST() : null);
					}
				});

		impICMS90PICMSST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS90() != null) ? icms.getICMS90().getPICMSST() : null);
					}
				});

		impICMS90VICMSST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS90() != null) ? icms.getICMS90().getVICMSST() : null);
					}
				});

		impICMS90VICMSDeson.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS90() != null) ? icms.getICMS90().getVICMSDeson() : null);
					}
				});

		impICMS90MotDesICMS.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMS90() != null) ? icms.getICMS90().getMotDesICMS() : null);
					}
				});

		impICMSPartOrig.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMSPart() != null) ? icms.getICMSPart().getOrig() : null);
					}
				});

		impICMSPartCST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMSPart() != null) ? icms.getICMSPart().getCST() : null);
					}
				});

		impICMSPartModBC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMSPart() != null) ? icms.getICMSPart().getModBC() : null);
					}
				});
		//
		impICMSPartVBC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMSPart() != null) ? icms.getICMSPart().getVBC() : null);
					}
				});

		impICMSPartPRedBC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMSPart() != null) ? icms.getICMSPart().getPRedBC() : null);
					}
				});

		impICMSPartPICMS.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMSPart() != null) ? icms.getICMSPart().getPICMS() : null);
					}
				});

		impICMSPartVICMS.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMSPart() != null) ? icms.getICMSPart().getVICMS() : null);
					}
				});

		impICMSPartModBCST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMSPart() != null) ? icms.getICMSPart().getModBCST() : null);
					}
				});

		impICMSPartPMVAST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMSPart() != null) ? icms.getICMSPart().getPMVAST() : null);
					}
				});

		impICMSPartPRedBCST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMSPart() != null) ? icms.getICMSPart().getPRedBCST() : null);
					}
				});

		impICMSPartVBCST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMSPart() != null) ? icms.getICMSPart().getModBCST() : null);
					}
				});

		impICMSPartPICMSST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMSPart() != null) ? icms.getICMSPart().getPICMSST() : null);
					}
				});

		impICMSPartVICMSST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMSPart() != null) ? icms.getICMSPart().getVICMSST() : null);
					}
				});

		impICMSPartPBCOp.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMSPart() != null) ? icms.getICMSPart().getPBCOp() : null);
					}
				});

		impICMSPartUFST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper((icms != null && icms.getICMSPart() != null)
								? icms.getICMSPart().getUFST().value() : null);
					}
				});

		impICMSSTOrig.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMSST() != null) ? icms.getICMSST().getOrig() : null);
					}
				});

		impICMSSTCST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMSST() != null) ? icms.getICMSST().getCST() : null);
					}
				});

		impICMSSTVBCSTRet.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMSST() != null) ? icms.getICMSST().getVBCSTRet() : null);
					}
				});

		impICMSSTVICMSSTRet.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMSST() != null) ? icms.getICMSST().getVICMSSTRet() : null);
					}
				});

		impICMSSTVBCSTDest.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMSST() != null) ? icms.getICMSST().getVBCSTDest() : null);
					}
				});

		impICMSSTVICMSSTDest.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMSST() != null) ? icms.getICMSST().getVICMSSTDest() : null);
					}
				});

		impCRTCMSSN101Orig.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMSSN101() != null) ? icms.getICMSSN101().getOrig() : null);
					}
				});

		impCRTCMSSN101CSOSN.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMSSN101() != null) ? icms.getICMSSN101().getCSOSN() : null);
					}
				});

		impCRTCMSSN101PCredSN.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper((icms != null && icms.getICMSSN101() != null)
								? icms.getICMSSN101().getPCredSN() : null);
					}
				});

		impCRTCMSSN101VCredICMSSN.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper((icms != null && icms.getICMSSN101() != null)
								? icms.getICMSSN101().getVCredICMSSN() : null);
					}
				});

		impCRTCMSSN102Orig.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMSSN102() != null) ? icms.getICMSSN102().getOrig() : null);
					}
				});

		impCRTCMSSN102CSOSN.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMSSN102() != null) ? icms.getICMSSN102().getCSOSN() : null);
					}
				});

		impCRTCMSSN201Orig.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMSSN201() != null) ? icms.getICMSSN201().getOrig() : null);
					}
				});

		impCRTCMSSN201CSOSN.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMSSN201() != null) ? icms.getICMSSN201().getCSOSN() : null);
					}
				});

		impCRTCMSSN201ModBCST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper((icms != null && icms.getICMSSN201() != null)
								? icms.getICMSSN201().getModBCST() : null);
					}
				});

		impCRTCMSSN201PMVAST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMSSN201() != null) ? icms.getICMSSN201().getPMVAST() : null);
					}
				});

		impCRTCMSSN201PRedBCST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper((icms != null && icms.getICMSSN201() != null)
								? icms.getICMSSN201().getPRedBCST() : null);
					}
				});

		impCRTCMSSN201VBCST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMSSN201() != null) ? icms.getICMSSN201().getVBCST() : null);
					}
				});

		impCRTCMSSN201PICMSST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper((icms != null && icms.getICMSSN201() != null)
								? icms.getICMSSN201().getPICMSST() : null);
					}
				});

		impCRTCMSSN201VICMSST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper((icms != null && icms.getICMSSN201() != null)
								? icms.getICMSSN201().getVICMSST() : null);
					}
				});

		impCRTCMSSN201PCredSN.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper((icms != null && icms.getICMSSN201() != null)
								? icms.getICMSSN201().getPCredSN() : null);
					}
				});

		impCRTCMSSN201VCredICMSSN.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper((icms != null && icms.getICMSSN201() != null)
								? icms.getICMSSN201().getVCredICMSSN() : null);
					}
				});

		impCRTCMSSN202Orig.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMSSN202() != null) ? icms.getICMSSN202().getOrig() : null);
					}
				});

		impCRTCMSSN202CSOSN.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMSSN202() != null) ? icms.getICMSSN202().getCSOSN() : null);
					}
				});

		impCRTCMSSN202ModBCST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper((icms != null && icms.getICMSSN202() != null)
								? icms.getICMSSN202().getModBCST() : null);
					}
				});

		impCRTCMSSN202PMVAST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMSSN202() != null) ? icms.getICMSSN202().getPMVAST() : null);
					}
				});

		impCRTCMSSN202PRedBCST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper((icms != null && icms.getICMSSN202() != null)
								? icms.getICMSSN202().getPRedBCST() : null);
					}
				});

		impCRTCMSSN202VBCST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMSSN202() != null) ? icms.getICMSSN202().getVBCST() : null);
					}
				});

		impCRTCMSSN202PICMSST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper((icms != null && icms.getICMSSN202() != null)
								? icms.getICMSSN202().getPICMSST() : null);
					}
				});

		impCRTCMSSN202VICMSST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper((icms != null && icms.getICMSSN202() != null)
								? icms.getICMSSN202().getVICMSST() : null);
					}
				});

		impCRTICMSSN500Orig.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMSSN500() != null) ? icms.getICMSSN500().getOrig() : null);
					}
				});

		impCRTICMSSN500CSOSN.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMSSN500() != null) ? icms.getICMSSN500().getCSOSN() : null);
					}
				});

		impCRTICMSSN500VBCSTRet.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper((icms != null && icms.getICMSSN500() != null)
								? icms.getICMSSN500().getVBCSTRet() : null);
					}
				});

		impCRTICMSSN500VICMSSTRet.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper((icms != null && icms.getICMSSN500() != null)
								? icms.getICMSSN500().getVICMSSTRet() : null);
					}
				});

		impCRTICMSSN900Orig.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMSSN900() != null) ? icms.getICMSSN900().getOrig() : null);
					}
				});

		impCRTICMSSN900CSOSN.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMSSN900() != null) ? icms.getICMSSN900().getCSOSN() : null);
					}
				});

		impCRTICMSSN900ModBC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMSSN900() != null) ? icms.getICMSSN900().getModBC() : null);
					}
				});

		impCRTICMSSN900VBC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMSSN900() != null) ? icms.getICMSSN900().getVBC() : null);
					}
				});

		impCRTICMSSN900PRedBC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMSSN900() != null) ? icms.getICMSSN900().getPRedBC() : null);
					}
				});

		impCRTICMSSN900PICMS.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMSSN900() != null) ? icms.getICMSSN900().getPICMS() : null);
					}
				});

		impCRTICMSSN900ModBCST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper((icms != null && icms.getICMSSN900() != null)
								? icms.getICMSSN900().getModBCST() : null);
					}
				});

		impCRTICMSSN900PMVAST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMSSN900() != null) ? icms.getICMSSN900().getPMVAST() : null);
					}
				});

		impCRTICMSSN900PRedBCST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper((icms != null && icms.getICMSSN900() != null)
								? icms.getICMSSN900().getPRedBCST() : null);
					}
				});

		impCRTICMSSN900VBCST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper(
								(icms != null && icms.getICMSSN900() != null) ? icms.getICMSSN900().getVBCST() : null);
					}
				});

		impCRTICMSSN900PICMSST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper((icms != null && icms.getICMSSN900() != null)
								? icms.getICMSSN900().getPICMSST() : null);
					}
				});

		impCRTICMSSN900VICMSST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper((icms != null && icms.getICMSSN900() != null)
								? icms.getICMSSN900().getVICMSST() : null);
					}
				});

		impCRTICMSSN900PCredSN.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper((icms != null && icms.getICMSSN900() != null)
								? icms.getICMSSN900().getPCredSN() : null);
					}
				});

		impCRTICMSSN900VCredICMSSN.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ICMS icms = getIcms(param.getValue());
						return new ReadOnlyStringWrapper((icms != null && icms.getICMSSN900() != null)
								? icms.getICMSSN900().getVCredICMSSN() : null);
					}
				});

		impIIVBC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						II ii = getII(param.getValue());
						return new ReadOnlyStringWrapper(ii != null ? ii.getVBC() : null);
					}
				});

		impIIVDespAdu.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						II ii = getII(param.getValue());
						return new ReadOnlyStringWrapper(ii != null ? ii.getVDespAdu() : null);
					}
				});

		impIIVII.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						II ii = getII(param.getValue());
						return new ReadOnlyStringWrapper(ii != null ? ii.getVII() : null);
					}
				});

		impIIVIOF.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						II ii = getII(param.getValue());
						return new ReadOnlyStringWrapper(ii != null ? ii.getVIOF() : null);
					}
				});

		impPISPISAliqCST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						PIS pis = getPIS(param.getValue());
						return new ReadOnlyStringWrapper(
								(pis != null && pis.getPISAliq() != null) ? pis.getPISAliq().getCST() : null);
					}
				});

		impPISPISAliqVBC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						PIS pis = getPIS(param.getValue());
						return new ReadOnlyStringWrapper(
								(pis != null && pis.getPISAliq() != null) ? pis.getPISAliq().getVBC() : null);
					}
				});

		impPISPISAliqPPIS.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						PIS pis = getPIS(param.getValue());
						return new ReadOnlyStringWrapper(
								(pis != null && pis.getPISAliq() != null) ? pis.getPISAliq().getPPIS() : null);
					}
				});

		impPISPISAliqVPIS.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						PIS pis = getPIS(param.getValue());
						return new ReadOnlyStringWrapper(
								(pis != null && pis.getPISAliq() != null) ? pis.getPISAliq().getVPIS() : null);
					}
				});

		impPISPISQtdeCST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						PIS pis = getPIS(param.getValue());
						return new ReadOnlyStringWrapper(
								(pis != null && pis.getPISQtde() != null) ? pis.getPISQtde().getCST() : null);
					}
				});

		impPISPISQtdeQBCProd.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						PIS pis = getPIS(param.getValue());
						return new ReadOnlyStringWrapper(
								(pis != null && pis.getPISQtde() != null) ? pis.getPISQtde().getQBCProd() : null);
					}
				});

		impPISPISQtdeVAliqProd.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						PIS pis = getPIS(param.getValue());
						return new ReadOnlyStringWrapper(
								(pis != null && pis.getPISQtde() != null) ? pis.getPISQtde().getVAliqProd() : null);
					}
				});

		impPISPISQtdeVPIS.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						PIS pis = getPIS(param.getValue());
						return new ReadOnlyStringWrapper(
								(pis != null && pis.getPISQtde() != null) ? pis.getPISQtde().getVPIS() : null);
					}
				});

		impPISPISNTCST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						PIS pis = getPIS(param.getValue());
						return new ReadOnlyStringWrapper(
								(pis != null && pis.getPISNT() != null) ? pis.getPISNT().getCST() : null);
					}
				});

		impPISPISOutrCST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						PIS pis = getPIS(param.getValue());
						return new ReadOnlyStringWrapper(
								(pis != null && pis.getPISOutr() != null) ? pis.getPISOutr().getCST() : null);
					}
				});

		impPISPISOutrVBC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						PIS pis = getPIS(param.getValue());
						return new ReadOnlyStringWrapper(
								(pis != null && pis.getPISOutr() != null) ? pis.getPISOutr().getVBC() : null);
					}
				});

		impPISPISOutrPPIS.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						PIS pis = getPIS(param.getValue());
						return new ReadOnlyStringWrapper(
								(pis != null && pis.getPISOutr() != null) ? pis.getPISOutr().getPPIS() : null);
					}
				});

		impPISPISOutrQBCProd.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						PIS pis = getPIS(param.getValue());
						return new ReadOnlyStringWrapper(
								(pis != null && pis.getPISOutr() != null) ? pis.getPISOutr().getQBCProd() : null);
					}
				});

		impPISPISOutrVAliqProd.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						PIS pis = getPIS(param.getValue());
						return new ReadOnlyStringWrapper(
								(pis != null && pis.getPISOutr() != null) ? pis.getPISOutr().getVAliqProd() : null);
					}
				});

		impPISPISOutrVPIS.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						PIS pis = getPIS(param.getValue());
						return new ReadOnlyStringWrapper(
								(pis != null && pis.getPISOutr() != null) ? pis.getPISOutr().getVPIS() : null);
					}
				});

		impPISSTVBC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						PISST pisst = getPISST(param.getValue());
						return new ReadOnlyStringWrapper(pisst != null ? pisst.getVBC() : null);
					}
				});

		impPISSTPPIS.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						PISST pisst = getPISST(param.getValue());
						return new ReadOnlyStringWrapper(pisst != null ? pisst.getPPIS() : null);
					}
				});

		impPISSTQBCProd.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						PISST pisst = getPISST(param.getValue());
						return new ReadOnlyStringWrapper(pisst != null ? pisst.getQBCProd() : null);
					}
				});

		impPISSTVAliqProd.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						PISST pisst = getPISST(param.getValue());
						return new ReadOnlyStringWrapper(pisst != null ? pisst.getVAliqProd() : null);
					}
				});

		impPISSTVPIS.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						PISST pisst = getPISST(param.getValue());
						return new ReadOnlyStringWrapper(pisst != null ? pisst.getVPIS() : null);
					}
				});

		impCOFINSCOFINSAliqCST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						COFINS cofins = getCOFINS(param.getValue());
						return new ReadOnlyStringWrapper((cofins != null && cofins.getCOFINSAliq() != null)
								? cofins.getCOFINSAliq().getCST() : null);
					}
				});

		impCOFINSCOFINSAliqVBC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						COFINS cofins = getCOFINS(param.getValue());
						return new ReadOnlyStringWrapper((cofins != null && cofins.getCOFINSAliq() != null)
								? cofins.getCOFINSAliq().getVBC() : null);
					}
				});

		impCOFINSCOFINSAliqPCOFINS.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						COFINS cofins = getCOFINS(param.getValue());
						return new ReadOnlyStringWrapper((cofins != null && cofins.getCOFINSAliq() != null)
								? cofins.getCOFINSAliq().getPCOFINS() : null);
					}
				});

		impCOFINSCOFINSAliqVCOFINS.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						COFINS cofins = getCOFINS(param.getValue());
						return new ReadOnlyStringWrapper((cofins != null && cofins.getCOFINSAliq() != null)
								? cofins.getCOFINSAliq().getVCOFINS() : null);
					}
				});

		impCOFINSCOFINSQtdeCST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						COFINS cofins = getCOFINS(param.getValue());
						return new ReadOnlyStringWrapper((cofins != null && cofins.getCOFINSQtde() != null)
								? cofins.getCOFINSQtde().getCST() : null);
					}
				});

		impCOFINSCOFINSQtdeQBCProd.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						COFINS cofins = getCOFINS(param.getValue());
						return new ReadOnlyStringWrapper((cofins != null && cofins.getCOFINSQtde() != null)
								? cofins.getCOFINSQtde().getQBCProd() : null);
					}
				});

		impCOFINSCOFINSQtdeVAliqProd.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						COFINS cofins = getCOFINS(param.getValue());
						return new ReadOnlyStringWrapper((cofins != null && cofins.getCOFINSQtde() != null)
								? cofins.getCOFINSQtde().getVAliqProd() : null);
					}
				});

		impCOFINSCOFINSQtdeVCOFINS.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						COFINS cofins = getCOFINS(param.getValue());
						return new ReadOnlyStringWrapper((cofins != null && cofins.getCOFINSQtde() != null)
								? cofins.getCOFINSQtde().getVCOFINS() : null);
					}
				});

		impCOFINSCOFINSNTCST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						COFINS cofins = getCOFINS(param.getValue());
						return new ReadOnlyStringWrapper((cofins != null && cofins.getCOFINSNT() != null)
								? cofins.getCOFINSNT().getCST() : null);
					}
				});

		impCOFINSCOFINSOutrCST.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						COFINS cofins = getCOFINS(param.getValue());
						return new ReadOnlyStringWrapper((cofins != null && cofins.getCOFINSOutr() != null)
								? cofins.getCOFINSOutr().getCST() : null);
					}
				});

		impCOFINSCOFINSOutrVBC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						COFINS cofins = getCOFINS(param.getValue());
						return new ReadOnlyStringWrapper((cofins != null && cofins.getCOFINSOutr() != null)
								? cofins.getCOFINSOutr().getVBC() : null);
					}
				});

		impCOFINSCOFINSOutrPCOFINS.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						COFINS cofins = getCOFINS(param.getValue());
						return new ReadOnlyStringWrapper((cofins != null && cofins.getCOFINSOutr() != null)
								? cofins.getCOFINSOutr().getPCOFINS() : null);
					}
				});

		impCOFINSCOFINSOutrQBCProd.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						COFINS cofins = getCOFINS(param.getValue());
						return new ReadOnlyStringWrapper((cofins != null && cofins.getCOFINSOutr() != null)
								? cofins.getCOFINSOutr().getPCOFINS() : null);
					}
				});

		impCOFINSCOFINSOutrVAliqProd.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						COFINS cofins = getCOFINS(param.getValue());
						return new ReadOnlyStringWrapper((cofins != null && cofins.getCOFINSOutr() != null)
								? cofins.getCOFINSOutr().getVAliqProd() : null);
					}
				});

		impCOFINSCOFINSOutrVCOFINS.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						COFINS cofins = getCOFINS(param.getValue());
						return new ReadOnlyStringWrapper((cofins != null && cofins.getCOFINSOutr() != null)
								? cofins.getCOFINSOutr().getVCOFINS() : null);
					}
				});

		impCOFINSSTVBC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						COFINSST cofinsst = getCOFINSST(param.getValue());
						return new ReadOnlyStringWrapper(cofinsst != null ? cofinsst.getVBC() : null);
					}
				});

		impCOFINSSTPCOFINS.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						COFINSST cofinsst = getCOFINSST(param.getValue());
						return new ReadOnlyStringWrapper(cofinsst != null ? cofinsst.getPCOFINS() : null);
					}
				});

		impCOFINSSTQBCProd.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						COFINSST cofinsst = getCOFINSST(param.getValue());
						return new ReadOnlyStringWrapper(cofinsst != null ? cofinsst.getQBCProd() : null);
					}
				});

		impCOFINSSTVAliqProd.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						COFINSST cofinsst = getCOFINSST(param.getValue());
						return new ReadOnlyStringWrapper(cofinsst != null ? cofinsst.getVAliqProd() : null);
					}
				});

		impCOFINSSTVCOFINS.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						COFINSST cofinsst = getCOFINSST(param.getValue());
						return new ReadOnlyStringWrapper(cofinsst != null ? cofinsst.getVCOFINS() : null);
					}
				});

		impISSQNVBC.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ISSQN issqn = getISSQN(param.getValue());
						return new ReadOnlyStringWrapper(issqn != null ? issqn.getVBC() : null);
					}
				});

		impISSQNVAliq.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ISSQN issqn = getISSQN(param.getValue());
						return new ReadOnlyStringWrapper(issqn != null ? issqn.getVAliq() : null);
					}
				});

		impISSQNVISSQN.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ISSQN issqn = getISSQN(param.getValue());
						return new ReadOnlyStringWrapper(issqn != null ? issqn.getVISSQN() : null);
					}
				});

		impISSQNCMunFG.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ISSQN issqn = getISSQN(param.getValue());
						return new ReadOnlyStringWrapper(issqn != null ? issqn.getCMunFG() : null);
					}
				});

		impISSQNCListServ.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ISSQN issqn = getISSQN(param.getValue());
						return new ReadOnlyStringWrapper(issqn != null ? issqn.getCListServ() : null);
					}
				});

		impISSQNVDeducao.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ISSQN issqn = getISSQN(param.getValue());
						return new ReadOnlyStringWrapper(issqn != null ? issqn.getVDeducao() : null);
					}
				});

		impISSQNVOutro.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ISSQN issqn = getISSQN(param.getValue());
						return new ReadOnlyStringWrapper(issqn != null ? issqn.getVOutro() : null);
					}
				});

		impISSQNVDescIncond.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ISSQN issqn = getISSQN(param.getValue());
						return new ReadOnlyStringWrapper(issqn != null ? issqn.getVDescIncond() : null);
					}
				});

		impISSQNVDescCond.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ISSQN issqn = getISSQN(param.getValue());
						return new ReadOnlyStringWrapper(issqn != null ? issqn.getVDescCond() : null);
					}
				});

		impISSQNVISSRet.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ISSQN issqn = getISSQN(param.getValue());
						return new ReadOnlyStringWrapper(issqn != null ? issqn.getVISSRet() : null);
					}
				});

		impISSQNIndISS.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ISSQN issqn = getISSQN(param.getValue());
						return new ReadOnlyStringWrapper(issqn != null ? issqn.getIndISS() : null);
					}
				});

		impISSQNCServico.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ISSQN issqn = getISSQN(param.getValue());
						return new ReadOnlyStringWrapper(issqn != null ? issqn.getCServico() : null);
					}
				});

		impISSQNCMun.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ISSQN issqn = getISSQN(param.getValue());
						return new ReadOnlyStringWrapper(issqn != null ? issqn.getCMun() : null);
					}
				});

		impISSQNCPais.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ISSQN issqn = getISSQN(param.getValue());
						return new ReadOnlyStringWrapper(issqn != null ? issqn.getCPais() : null);
					}
				});

		impISSQNNProcesso.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ISSQN issqn = getISSQN(param.getValue());
						return new ReadOnlyStringWrapper(issqn != null ? issqn.getNProcesso() : null);
					}
				});

		impISSQNIndIncentivo.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub
						ISSQN issqn = getISSQN(param.getValue());
						return new ReadOnlyStringWrapper(issqn != null ? issqn.getIndIncentivo() : null);
					}
				});

		detProdInfAdProd.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Det, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Det, String> param) {
						// TODO Auto-generated method stub

						return new ReadOnlyStringWrapper(param.getValue().getInfAdProd());
					}
				});

		tabela.setItems(FXCollections.observableArrayList(xmlImport));
	}

	public void loadImportNFeXMLFat(TableView<Dup> tabFaturamento, Cobr fat) {

		ObservableList<Dup> dados = FXCollections
				.observableArrayList(fat != null && fat.getDup() != null ? fat.getDup() : null);
		// System.out.println("Entre metodo loadImportNFeXMLFat ");
		//
		// System.out.println("tmanho da lista DUp "+ dados.size());
		if (fat.getFat() != null) {
			fatNFat.setText(fat.getFat().getNFat());
			fatVOrig.setText(fat.getFat().getVOrig());
			fatVDesc.setText(fat.getFat().getVDesc());
			fatVLiq.setText(fat.getFat().getVLiq());
		} else
			Util.limpar(fatNFat, fatVDesc, fatVLiq, fatVOrig);// limpar campos

		dupnDup.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Dup, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Dup, String> param) {
				// TODO Auto-generated method stub
				// System.out.println("tamanho "+param.getValue().getDup());
				// return new
				// ReadOnlyObjectWrapper<>(param.getValue().getInfNFe().getCobr().getDup().size()
				// > 0 ?
				// param.getValue().getInfNFe().getCobr().getDup().iterator().next().getNDup()
				// : null);
				return new ReadOnlyObjectWrapper<>(param.getValue().getNDup());
			}
		});

		dupdVenc.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Dup, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Dup, String> param) {
						// TODO Auto-generated method stub
						// System.out.println("tamanho
						// "+param.getValue().getDup().size());
						// return new
						// ReadOnlyObjectWrapper<>(param.getValue().getInfNFe().getCobr().getDup().size()
						// > 0 ?
						// param.getValue().getInfNFe().getCobr().getDup().iterator().next().getNDup()
						// : null);

						return new ReadOnlyObjectWrapper<>(param.getValue().getDVenc());
					}
				});

		dupvDup.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Dup, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Dup, String> param) {
				// TODO Auto-generated method stub
				// System.out.println("tamanho
				// "+param.getValue().getDup().size());
				// return new
				// ReadOnlyObjectWrapper<>(param.getValue().getInfNFe().getCobr().getDup().size()
				// > 0 ?
				// param.getValue().getInfNFe().getCobr().getDup().iterator().next().getNDup()
				// : null);
				return new ReadOnlyObjectWrapper<>(param.getValue().getVDup());
			}
		});
		tabFaturamento.setItems(FXCollections.observableArrayList(fat.getDup()));

		// tabFaturamento.refresh();
	}

	public void loadImportNFeXMLProtocolo(TableView<TNfeProc> tabelaProtocolo, TNfeProc protoc) {
		ideChaveNFe.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNfeProc, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNfeProc, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(param.getValue().getProtNFe().getInfProt().getChNFe());
					}
				});

		ideProtocSEFAZ.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNfeProc, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNfeProc, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								(param.getValue() != null && param.getValue().getProtNFe() != null)
								? param.getValue().getProtNFe().getInfProt().getNProt() : null);
					}
				});

		ideDtProtocSEFAZ.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<TNfeProc, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<TNfeProc, String> param) {
						// TODO Auto-generated method stub
						return new ReadOnlyStringWrapper(
								(param.getValue() != null && param.getValue().getProtNFe() != null)
								? param.getValue().getProtNFe().getInfProt().getDhRecbto() : null);
					}
				});

		tabelaProtocolo.setItems(FXCollections.observableArrayList(protoc));
	}

	public void loadTotal(Total total, List<Det> detProductos) {
		txtTotalItens.setText(String.valueOf(detProductos.size()));

		boolean erroTotal = false;

		BigDecimal vTotalProd = BigDecimal.valueOf(Double.parseDouble("0.00"));
		BigDecimal vTotalQtdComercial = BigDecimal.valueOf(Double.parseDouble("0.00"));
		BigDecimal vTotalIPI = BigDecimal.valueOf(Double.parseDouble("0.00"));
		BigDecimal vTotalII = BigDecimal.valueOf(Double.parseDouble("0.00"));
		BigDecimal vTotalICMS = BigDecimal.valueOf(Double.parseDouble("0.00"));
		BigDecimal vTotalICMSDeson = BigDecimal.valueOf(Double.parseDouble("0.00"));
		BigDecimal vTotalICMSST = BigDecimal.valueOf(Double.parseDouble("0.00"));
		BigDecimal vTotalFrete = BigDecimal.valueOf(Double.parseDouble("0.00"));
		BigDecimal vTotalSeguro = BigDecimal.valueOf(Double.parseDouble("0.00"));
		BigDecimal vTotalServico = BigDecimal.valueOf(Double.parseDouble("0.00"));
		BigDecimal vTotalDesconto = BigDecimal.valueOf(Double.parseDouble("0.00"));
		BigDecimal vTotalDespAp = BigDecimal.valueOf(Double.parseDouble("0.00"));
		BigDecimal vTotalNotaFiscal = BigDecimal.valueOf(Double.parseDouble("0.00"));

		// System.out.println(detProductos.size());
		for (int i = 0; i < detProductos.size(); i++) {
			vTotalProd = vTotalProd
					.add(BigDecimal.valueOf(Double.parseDouble(detProductos.get(i).getProd().getVProd())));
			vTotalQtdComercial = vTotalQtdComercial
					.add(BigDecimal.valueOf(Double.parseDouble(detProductos.get(i).getProd().getQCom())));
			TIpi ipi = getTIpi(detProductos.get(i));
			vTotalIPI = vTotalIPI.add(BigDecimal.valueOf(
					Double.parseDouble(ipi != null && ipi.getIPITrib() != null ? ipi.getIPITrib().getVIPI() : "0.00")));
			ICMS icms = getIcms(detProductos.get(i));
			// vTotalICMSDeson =
			// vTotalICMSDeson.add(BigDecimal.valueOf(Double.parseDouble(icms
			// !=null && icms.geticms)))
			II ii = getII(detProductos.get(i));
			vTotalII = vTotalII.add(BigDecimal.valueOf(Double.parseDouble(ii != null ? ii.getVII() : "0.00")));
			vTotalFrete = vTotalFrete
					.add(BigDecimal.valueOf(Double.parseDouble(detProductos.get(i).getProd().getVFrete() != null
					? detProductos.get(i).getProd().getVFrete() : "0.00")));
			vTotalDesconto = vTotalDesconto
					.add(BigDecimal.valueOf(Double.parseDouble(detProductos.get(i).getProd().getVDesc() != null
					? detProductos.get(i).getProd().getVDesc() : "0.00")));
			vTotalSeguro = vTotalSeguro
					.add(BigDecimal.valueOf(Double.parseDouble(detProductos.get(i).getProd().getVSeg() != null
					? detProductos.get(i).getProd().getVSeg() : "0.00")));
			vTotalDespAp = vTotalDespAp
					.add(BigDecimal.valueOf(Double.parseDouble(detProductos.get(i).getProd().getVOutro() != null
					? detProductos.get(i).getProd().getVOutro() : "0.00")));

			// VALOR TOTAL ICMS NORMAL, SVALOR TOTAL ICMS DESONERADO E VALOR
			// TOTAL SITUAÇÃO TIBUTARIA
			if (icms.getICMS00() != null) {
				vTotalICMS = vTotalICMS.add(BigDecimal
						.valueOf(Double.parseDouble(icms.getICMS00() != null ? icms.getICMS00().getVICMS() : "0.00")));
			}

			if (icms.getICMS10() != null) {
				vTotalICMSST = vTotalICMSST.add(BigDecimal.valueOf(Double.parseDouble(icms.getICMS10().getVICMSST())));
				vTotalICMS = vTotalICMS.add(BigDecimal.valueOf(Double
						.parseDouble(icms.getICMS10().getVICMS() != null ? icms.getICMS10().getVICMS() : "0.00")));
			}

			if (icms.getICMS20() != null) {
				vTotalICMSDeson = vTotalICMSDeson.add(BigDecimal.valueOf(Double.parseDouble(
						icms.getICMS20().getVICMSDeson() != null ? icms.getICMS20().getVICMSDeson() : "0.00")));
				vTotalICMS = vTotalICMS.add(BigDecimal.valueOf(Double
						.parseDouble(icms.getICMS20().getVICMS() != null ? icms.getICMS20().getVICMS() : "0.00")));
			}
			if (icms.getICMS30() != null) {
				vTotalICMSDeson = vTotalICMSDeson.add(BigDecimal.valueOf(Double.parseDouble(
						icms.getICMS30().getVICMSDeson() != null ? icms.getICMS30().getVICMSDeson() : "0.00")));
				vTotalICMSST = vTotalICMSST.add(BigDecimal.valueOf(Double.parseDouble(icms.getICMS30().getVICMSST())));

			}
			if (icms.getICMS40() != null && icms.getICMS40().getVICMSDeson() != null) {
				vTotalICMSDeson = vTotalICMSDeson
						.add(BigDecimal.valueOf(Double.parseDouble(icms.getICMS40().getVICMSDeson())));
			}

			if (icms.getICMS51() != null) {
				vTotalICMS = vTotalICMS.add(BigDecimal.valueOf(Double
						.parseDouble(icms.getICMS51().getVICMS() != null ? icms.getICMS51().getVICMS() : "0.00")));
			}

			if (icms.getICMS70() != null) {
				vTotalICMSDeson = vTotalICMSDeson.add(BigDecimal.valueOf(Double.parseDouble(
						icms.getICMS70().getVICMSDeson() != null ? icms.getICMS70().getVICMSDeson() : "0.00")));
				vTotalICMSST = vTotalICMSST.add(BigDecimal.valueOf(Double.parseDouble(icms.getICMS70().getVICMSST())));
				vTotalICMS = vTotalICMS.add(BigDecimal.valueOf(Double
						.parseDouble(icms.getICMS70().getVICMS() != null ? icms.getICMS70().getVICMS() : "0.00")));
			}
			if (icms.getICMS90() != null && icms.getICMS90().getVICMSDeson() != null) {
				vTotalICMSDeson = vTotalICMSDeson.add(BigDecimal.valueOf(Double.parseDouble(
						icms.getICMS90().getVICMSDeson() != null ? icms.getICMS90().getVICMSDeson() : "0.00")));
				vTotalICMSST = vTotalICMSST.add(BigDecimal.valueOf(Double.parseDouble(icms.getICMS90().getVICMSST())));
				vTotalICMS = vTotalICMS.add(BigDecimal.valueOf(Double
						.parseDouble(icms.getICMS90().getVICMS() != null ? icms.getICMS90().getVICMS() : "0.00")));
			}
			// VALOR TOTAL ICMSSN
			if (icms.getICMSSN201() != null) {
				vTotalICMSST = vTotalICMSST
						.add(BigDecimal.valueOf(Double.parseDouble(icms.getICMSSN201().getVICMSST())));
			}
			if (icms.getICMSSN202() != null) {
				vTotalICMSST = vTotalICMSST
						.add(BigDecimal.valueOf(Double.parseDouble(icms.getICMSSN202().getVICMSST())));
			}
			if (icms.getICMSSN900() != null) {
				vTotalICMSST = vTotalICMSST
						.add(BigDecimal.valueOf(Double.parseDouble(icms.getICMSSN900().getVICMSST())));
			}

		}

		if (vTotalProd.compareTo(BigDecimal.valueOf(Double.parseDouble(total.getICMSTot().getVProd()))) == 0) {
			txtVTotalProducto
			.setText(String.valueOf(BigDecimal.valueOf(Double.parseDouble(total.getICMSTot().getVProd()))));
			txtVTotalProducto.setStyle("");
		} else {
			txtVTotalProducto
			.setText(String.valueOf(BigDecimal.valueOf(Double.parseDouble(total.getICMSTot().getVProd()))));
			txtVTotalProducto.setStyle("-fx-background-color : #ef5350");
			erroTotal = true;
		}

		txtVTotalQtdComercialProd.setText(String.valueOf(vTotalQtdComercial));

		if (vTotalIPI.compareTo(BigDecimal.valueOf(Double.parseDouble(total.getICMSTot().getVIPI()))) == 0) {
			txtVTotalIPI.setText(String.valueOf(BigDecimal.valueOf(Double.parseDouble(total.getICMSTot().getVIPI()))));
			txtVTotalIPI.setStyle("");
		} else {
			txtVTotalIPI.setText(String.valueOf(BigDecimal.valueOf(Double.parseDouble(total.getICMSTot().getVIPI()))));
			txtVTotalIPI.setStyle("-fx-background-color : #ef5350");
			erroTotal = true;
		}

		if (vTotalICMS.compareTo(BigDecimal.valueOf(Double.parseDouble(total.getICMSTot().getVICMS()))) == 0) {
			txtVTotalICMS
			.setText(String.valueOf(BigDecimal.valueOf(Double.parseDouble(total.getICMSTot().getVICMS()))));
			txtVTotalICMS.setStyle("");
		} else {
			txtVTotalICMS
			.setText(String.valueOf(BigDecimal.valueOf(Double.parseDouble(total.getICMSTot().getVICMS()))));
			txtVTotalICMS.setStyle("-fx-background-color : #ef5350");
			txtVTotalICMS.setTooltip(new Tooltip(String.valueOf(vTotalICMS)));
			erroTotal = true;
		}

		if (vTotalICMSDeson
				.compareTo(BigDecimal.valueOf(Double.parseDouble(total.getICMSTot().getVICMSDeson()))) == 0) {
			txtVTotalICMSDeson.setText(
					String.valueOf(BigDecimal.valueOf(Double.parseDouble(total.getICMSTot().getVICMSDeson()))));
			txtVTotalICMSDeson.setStyle("");
		} else {
			txtVTotalICMSDeson.setText(
					String.valueOf(BigDecimal.valueOf(Double.parseDouble(total.getICMSTot().getVICMSDeson()))));
			txtVTotalICMSDeson.setStyle("-fx-background-color : #ef5350");
			txtVTotalICMSDeson.setTooltip(new Tooltip(String.valueOf(vTotalICMSDeson)));
			erroTotal = true;
		}

		if (vTotalICMSST.compareTo(BigDecimal.valueOf(Double.parseDouble(total.getICMSTot().getVST()))) == 0) {
			txtVTotalICMSST
			.setText(String.valueOf(BigDecimal.valueOf(Double.parseDouble(total.getICMSTot().getVST()))));
			txtVTotalICMSST.setStyle("");
		} else {
			txtVTotalICMSST
			.setText(String.valueOf(BigDecimal.valueOf(Double.parseDouble(total.getICMSTot().getVST()))));
			txtVTotalICMSST.setStyle("-fx-background-color : #ef5350");
			erroTotal = true;
		}

		if (vTotalDesconto.compareTo(BigDecimal.valueOf(Double.parseDouble(total.getICMSTot().getVDesc()))) == 0) {
			txtVTotalDesconto
			.setText(String.valueOf(BigDecimal.valueOf(Double.parseDouble(total.getICMSTot().getVDesc()))));
			txtVTotalDesconto.setStyle("");
		} else {
			txtVTotalDesconto
			.setText(String.valueOf(BigDecimal.valueOf(Double.parseDouble(total.getICMSTot().getVDesc()))));
			txtVTotalDesconto.setStyle("-fx-background-color : #ef5350");
			erroTotal = true;
		}

		if (vTotalFrete.compareTo(BigDecimal.valueOf(Double.parseDouble(total.getICMSTot().getVFrete()))) == 0) {
			txtVTotalFrete
			.setText(String.valueOf(BigDecimal.valueOf(Double.parseDouble(total.getICMSTot().getVFrete()))));
			txtVTotalFrete.setStyle("");
		} else {
			txtVTotalFrete
			.setText(String.valueOf(BigDecimal.valueOf(Double.parseDouble(total.getICMSTot().getVFrete()))));
			txtVTotalFrete.setStyle("-fx-background-color : #ef5350");
			erroTotal = true;
		}

		if (vTotalSeguro.compareTo(BigDecimal.valueOf(Double.parseDouble(total.getICMSTot().getVSeg()))) == 0) {
			txtVTotalSeguro
			.setText(String.valueOf(BigDecimal.valueOf(Double.parseDouble(total.getICMSTot().getVSeg()))));
			txtVTotalSeguro.setStyle("");
		} else {
			txtVTotalSeguro
			.setText(String.valueOf(BigDecimal.valueOf(Double.parseDouble(total.getICMSTot().getVSeg()))));
			txtVTotalSeguro.setStyle("-fx-background-color : #ef5350");
			erroTotal = true;
		}

		txtVTotalDespAc.setText(String.valueOf(BigDecimal.valueOf(Double.parseDouble(total.getICMSTot().getVOutro()))));

		vTotalNotaFiscal = vTotalProd.subtract(vTotalDesconto).subtract(vTotalICMSDeson).add(vTotalICMSST)
				.add(vTotalFrete).add(vTotalSeguro).add(vTotalDespAp).add(vTotalII).add(vTotalIPI);

		// System.out.println("valor prod:= "+vTotalProd);
		// System.out.println("valor desconto:= "+vTotalDesconto);
		// System.out.println("valor ICMSDeson:= "+vTotalICMSDeson);
		// System.out.println(vTotalProd.subtract(vTotalDesconto).subtract(vTotalICMSDeson));

		if (vTotalNotaFiscal.compareTo(BigDecimal.valueOf(Double.parseDouble(total.getICMSTot().getVNF()))) == 0
				|| !erroTotal) {
			txtVTotalNotaFiscal
			.setText(String.valueOf(BigDecimal.valueOf(Double.parseDouble(total.getICMSTot().getVNF()))));
			txtVTotalNotaFiscal.setStyle("");
		} else {
			txtVTotalNotaFiscal
			.setText(String.valueOf(BigDecimal.valueOf(Double.parseDouble(total.getICMSTot().getVNF()))));
			txtVTotalNotaFiscal.setStyle("-fx-background-color : #ef5350");
		}

		erroTotal = false;

	}

	/**
	 * METODO RETORNA DADOS TIPI
	 * 
	 * @param det
	 * @return
	 */
	public TIpi getTIpi(Det det) {
		List<JAXBElement<?>> list = det.getImposto().getContent();

		for (int i = 0; i < list.size(); i++) {
			Object obj = list.get(i).getValue();
			if (obj != null && obj instanceof TIpi)
				return (TIpi) obj;
		}
		return null;
	}

	/**
	 * METODO RETORNA DADOS ICMS
	 * 
	 * @param det
	 * @return
	 */
	public ICMS getIcms(Det det) {
		List<JAXBElement<?>> list = det.getImposto().getContent();

		for (int i = 0; i < list.size(); i++) {
			Object obj = list.get(i).getValue();
			if (obj != null && obj instanceof ICMS)
				return (ICMS) obj;
		}
		return null;
	}

	/**
	 * METODO RETORNA DADOS II
	 * 
	 * @param det
	 * @return
	 */
	public II getII(Det det) {
		List<JAXBElement<?>> list = det.getImposto().getContent();

		for (int i = 0; i < list.size(); i++) {
			Object obj = list.get(i).getValue();
			if (obj != null && obj instanceof II)
				return (II) obj;
		}
		return null;
	}

	/**
	 * METODO RETORNA DADOS PIS
	 * 
	 * @param det
	 * @return
	 */
	public PIS getPIS(Det det) {
		List<JAXBElement<?>> list = det.getImposto().getContent();

		for (int i = 0; i < list.size(); i++) {
			Object obj = list.get(i).getValue();
			if (obj != null && obj instanceof PIS)
				return (PIS) obj;
		}
		return null;
	}

	/**
	 * METODO RETORNA DADOS PISST
	 * 
	 * @param det
	 * @return
	 */
	public PISST getPISST(Det det) {
		List<JAXBElement<?>> list = det.getImposto().getContent();

		for (int i = 0; i < list.size(); i++) {
			Object obj = list.get(i).getValue();
			if (obj != null && obj instanceof PISST)
				return (PISST) obj;
		}
		return null;
	}

	/**
	 * METODO RETORNA DADOS CONFINS
	 * 
	 * @param det
	 * @return
	 */
	public COFINS getCOFINS(Det det) {
		List<JAXBElement<?>> list = det.getImposto().getContent();

		for (int i = 0; i < list.size(); i++) {
			Object obj = list.get(i).getValue();
			if (obj != null && obj instanceof COFINS)
				return (COFINS) obj;
		}
		return null;
	}

	/**
	 * METODO RETORNA DADOS COFINSST
	 * 
	 * @param det
	 * @return
	 */
	public COFINSST getCOFINSST(Det det) {
		List<JAXBElement<?>> list = det.getImposto().getContent();

		for (int i = 0; i < list.size(); i++) {
			Object obj = list.get(i).getValue();
			if (obj != null && obj instanceof COFINSST)
				return (COFINSST) obj;
		}
		return null;
	}

	/**
	 * METODO RETORNA DADOS ISSQN
	 * 
	 * @param det
	 * @return
	 */
	public ISSQN getISSQN(Det det) {
		List<JAXBElement<?>> list = det.getImposto().getContent();

		for (int i = 0; i < list.size(); i++) {
			Object obj = list.get(i).getValue();
			if (obj != null && obj instanceof ISSQN)
				return (ISSQN) obj;
		}
		return null;
	}

	/**
	 * METODO IMPORTAR XML TNFeProc
	 * 
	 * @param url
	 *            ENDEREÇO DO ARQUIVO
	 */
	public void importXMLTNFeProc(String url) {

		Task<String> tarefaCargaXMLPg = new Task<String>() {
			TNfeProc importXmlNFeProc;

			@Override
			protected String call() throws Exception {
				try {
					xmlNFe = XmlUtil.leXml(url);
					importXmlNFeProc = XmlUtil.xmlToObject(xmlNFe, TNfeProc.class);
					// System.out.println("-*-*-*-*-*-*-*-*"+importXmlNFeProc);
				} catch (JAXBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NfeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				if (importXmlNFeProc.getProtNFe() == null) {
					Util.alertInf("Não foram localizadas informações sobre o protocolo de autorização desta NFe!");
					Util util = new Util();
					boolean confirmation = util.alertConfirmation(
							"Confirmar importação do XML sem os dados de autorização fornecidos pela SEFAZ?");
					// xmlNFe =
					// XmlUtil.xmlToObject(XmlUtil.leXml("C:/Eptus/System.xml/EnvNFe_Av310_12885.xml"),
					// TNFe.class);
					if (confirmation) {
						importXMLTNFe(url);
					}
				} else {

					loadDataFornecedor(importXmlNFeProc.getNFe());
					loadImportNFeXMLHead(tbViewCabecalhoNotFisc, importXmlNFeProc.getNFe());
					loadImportNFeXMLDetail(tbViewDetalheNotFisc, importXmlNFeProc.getNFe().getInfNFe().getDet());
					if (importXmlNFeProc.getNFe().getInfNFe().getCobr() != null) {
						loadImportNFeXMLFat(tbViewFaturamento, importXmlNFeProc.getNFe().getInfNFe().getCobr());
					} else {
						Util.limpar(fatNFat, fatVDesc, fatVLiq, fatVOrig);
						tbViewFaturamento.getItems().clear();
					}

					loadImportNFeXMLProtocolo(tbViewProtocolo, importXmlNFeProc);
					loadTotal(importXmlNFeProc.getNFe().getInfNFe().getTotal(),
							importXmlNFeProc.getNFe().getInfNFe().getDet());
					loadDataRelationItem(importXmlNFeProc.getNFe());
					txtAreaInfAdic.setText(importXmlNFeProc.getNFe().getInfNFe().getInfAdic().getInfCpl());
				}

				// btnProcessarXML.setDisable(true);
			}

			@Override
			protected void failed() {
				stg.close();

			}

			@Override
			protected void cancelled() {
				super.cancelled();
			}

		};
		Thread t = new Thread(tarefaCargaXMLPg);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(NotaFiscalEntradaController.class, tarefaCargaXMLPg,
				"Importando arquivo XML...", false);
	}

	/**
	 * METODO IMPORTAR XML TNFe
	 * 
	 * @param url
	 *            ENDEREÇO DO ARQUIVO
	 */
	public void importXMLTNFe(String url) {

		Task<String> tarefaCargaXMLPg = new Task<String>() {
			TNFe importXmlNFe;

			@Override
			protected String call() throws Exception {
				try {
					importXmlNFe = XmlUtil.xmlToObject(XmlUtil.leXml(url), TNFe.class);
				} catch (JAXBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NfeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				loadDataFornecedor(importXmlNFe);
				// ppppppppppppppppppppppppppppppppppppppppppppppppppppppp
				try {
					NfEntradaDAO.class.newInstance().getNFe(fornecedorRe, importXmlNFe.getInfNFe().getIde().getCNF());
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// *****************************************************
				loadImportNFeXMLHead(tbViewCabecalhoNotFisc, importXmlNFe);
				loadImportNFeXMLDetail(tbViewDetalheNotFisc, importXmlNFe.getInfNFe().getDet());
				loadImportNFeXMLFat(tbViewFaturamento, importXmlNFe.getInfNFe().getCobr());
				loadTotal(importXmlNFe.getInfNFe().getTotal(), importXmlNFe.getInfNFe().getDet());
				loadDataRelationItem(importXmlNFe);
				tabProtocolo.setDisable(true);
				txtAreaInfAdic.setText(importXmlNFe.getInfNFe().getInfAdic().getInfCpl());
			}

			@Override
			protected void failed() {
				stg.close();
			}

			@Override
			protected void cancelled() {
				super.cancelled();
			}
		};
		Thread t = new Thread(tarefaCargaXMLPg);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(NotaFiscalEntradaController.class, tarefaCargaXMLPg,
				"Importando arquivo XML sem os dados de autorização" + "\n" + "fornecidos pela SEFAZ...", false);

	}

	/**
	 * 
	 * @param tnfe
	 */
	public void loadDataFornecedor(TNFe tnfe) {

		String cnpj = Util.getStringConverterCNPJ(tnfe.getInfNFe().getEmit().getCNPJ());
		try {
			fornecedorRe = FornecedorDAO.class.newInstance().getFornecedorByCNPJ(cnpj);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String dataEmicap = tnfe.getInfNFe().getIde().getDhEmi();
		int month = Integer.parseInt(dataEmicap.substring(5, 7));
		int day = Integer.parseInt(dataEmicap.substring(8, 10));
		int year = Integer.parseInt(dataEmicap.substring(0, 4));
		LocalDate ld = LocalDate.of(year, month, day);

		txtNoNotaFiscal.setText(tnfe.getInfNFe().getIde().getNNF());
		dtxtDataEmissao.setValue(ld);
		if (fornecedorRe != null) {
			txtCodigoFornecedor.setText(String.valueOf(fornecedorRe.getCodigo()));
			txtFornecedor.setText(tnfe.getInfNFe().getEmit().getXNome());
			txtNoCNPJ.setText(cnpj);

			try {
				LogRetorno logRetNFc = NfEntradaDAO.class.newInstance().getNFe(fornecedorRe,
						tnfe.getInfNFe().getIde().getNNF());
				// System.out.println(logRetNFc.getObjeto() instanceof
				// NfEntradaCab && logRetNFc.getObjeto() != null ?
				// ((NfEntradaCab)logRetNFc.getObjeto()).getNfEntradaXML().getXml()
				// : "Nao Existe");
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// fornecedorRe = forn;
		} else {
			boolean confirm = false;

			txtCodigoFornecedor.setText("+1");
			txtCodigoFornecedor.setDisable(true);
			txtFornecedor.setText(tnfe.getInfNFe().getEmit().getXNome());
			txtNoCNPJ.setText(cnpj);

			try {
				confirm = Util.class.newInstance()
						.alertConfirmation("O fornecedor não consta no banco de dados. \n Deseja salvar agora?");
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (confirm) {
				saveFornecedor();
			}
		}

		txtCodigoSecao.requestFocus();
	}

	List<RelacaoItems> listRelacaoItems = null;
	ObservableList<RelacaoItems> dadosRelacaoItem;

	@SuppressWarnings("unused")
	public void loadDataRelationItem(TNFe nFe) {
		// TODO Auto-generated method stub

		RelacaoItems relacaoItems = null;
		double vlrUniLiqDesconto;
		double vlrUniLiqDespAcc;
		double vlrUniLiqICMSDeson;
		double qtdNFe;
		double embComp;
		List<ItemFornecedor> listItemFornecedor = null;

		if (nFe != null) {
			if (listRelacaoItems == null)
				listRelacaoItems = new ArrayList<RelacaoItems>();
			else {
				tbViewRelacaoProd.getItems().clear();
				tbViewRelacaoProd.refresh();
				listRelacaoItems.clear();

			}

			int i = 0;
			ICMS icms = null;
			TIpi ipi = null;
			String cSTICMS = null;
			String origICMS = null;
			String aliqICMS = null;
			String vlrICMS = null;
			String vlrICMSST = null;
			String vlrICMSDeson = null;
			String vlrPMVAST = null;
			String vlrBC = null;
			String vlrBCST = null;

			for (Det detProd : nFe.getInfNFe().getDet()) {
				icms = getIcms(detProd) != null ? getIcms(detProd) : null;
				ipi = getTIpi(detProd) != null ? getTIpi(detProd) : null;
				// ICMS 00
				if (icms != null && icms.getICMS00() != null) {
					cSTICMS = icms.getICMS00().getCST();
					origICMS = icms.getICMS00().getOrig();
					aliqICMS = icms.getICMS00().getPICMS();
					vlrICMS = icms.getICMS00().getVICMS();
					vlrBC = icms.getICMS00().getVBC();
				}
				// ICMS 10
				else if (icms != null && icms.getICMS10() != null) {

					cSTICMS = icms.getICMS10().getCST();
					origICMS = icms.getICMS10().getOrig();
					aliqICMS = icms.getICMS10().getPICMS();
					vlrICMS = icms.getICMS10().getVICMS();
					vlrICMSST = icms.getICMS10().getVICMSST();
					vlrBC = icms.getICMS10().getVBC();
					vlrBCST = icms.getICMS10().getVBCST();
					vlrPMVAST = icms.getICMS10().getPMVAST();
				}
				// ICMS 20
				else if (icms != null && icms.getICMS20() != null) {
					cSTICMS = icms.getICMS20().getCST();
					origICMS = icms.getICMS20().getOrig();
					aliqICMS = icms.getICMS20().getPICMS();
					vlrICMS = icms.getICMS20().getVICMS();
					vlrBC = icms.getICMS20().getVBC();
					vlrICMSDeson = icms.getICMS20().getVICMSDeson();
				}
				// ICMS 30
				else if (icms != null && icms.getICMS30() != null) {
					cSTICMS = icms.getICMS30().getCST();
					origICMS = icms.getICMS30().getOrig();
					vlrBCST = icms.getICMS30().getVBCST();
					vlrICMSST = icms.getICMS30().getVICMSST();
					vlrICMSDeson = icms.getICMS30().getVICMSDeson();
				}
				// ICMS 40
				else if (icms != null && icms.getICMS40() != null) {
					cSTICMS = icms.getICMS40().getCST();
					origICMS = icms.getICMS40().getOrig();
					vlrICMSDeson = icms.getICMS40().getVICMSDeson();
				}
				// ICMS 60
				else if (icms != null && icms.getICMS60() != null) {
					cSTICMS = icms.getICMS60().getCST();
					origICMS = icms.getICMS60().getOrig();
				}

				relacaoItems = new RelacaoItems();

				// COMPROVAR SE O ITEM JÁ FOI RELACIONADO
				if (fornecedorRe != null) {
					try {
						listItemFornecedor = ItemFornecedorDAO.class.newInstance().getAllItemFornecedor(fornecedorRe);
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					for (ItemFornecedor itemForn : listItemFornecedor) {
						// System.out.println("dentro del for*/*//*/**/**/*/*");
						if (fornecedorRe.getCheckDelete().compareTo(itemForn.getFornecedor().getCheckDelete()) == 0
								&& Integer.parseInt(detProd.getProd().getCProd()) == itemForn.getCodItemFornecedor()) {
							// System.out.println("entre en el EXISTE
							// --*-*-*-*-");
							relacaoItems.setStatus(EnumStatusRelItens.EXISTE.status);
							relacaoItems.setItem(itemForn.getItem());
							relacaoItems.setItemFornecedor(itemForn);
							relacaoItems.setCodRelProd(itemForn.getItem().getCodigo().toString());
							relacaoItems.setDescripcaoEptusRelProd(itemForn.getItem().getDescricao());
							relacaoItems.setCodNCMEptusRelProd(itemForn.getItem().getNcmCodigo());
							relacaoItems.setCodBarEptusRelProd(
									itemForn.getItem().getItemCodBars().iterator().next().getCodBarras());

							// relacaoItems.setCodBarRelProd(itemForn.getItem().getItemCodBars().iterator().next().getCodBarras());
							// System.out.println("-*--**--*-*-**-**-
							// "+itemForn.getItem().getFabricante().getCodigo()+"
							// -*--**--*-*-**-**-");
						}
					}
				}

				relacaoItems.setCodBarRelProd(detProd.getProd().getCEAN() != null ? detProd.getProd().getCEAN() : "");
				relacaoItems.setCodItemFornecedor(detProd.getProd().getCProd());
				relacaoItems.setDescripcaoNFeRelProd(detProd.getProd().getXProd());
				relacaoItems.setNoFornecedorRelProd(detProd.getProd().getCProd());
				relacaoItems.setCodNCMNFeRelProd(detProd.getProd().getNCM());

				relacaoItems.setCFOPRelProd(operacao.getCfop().toString());
				relacaoItems.setCSTICMSRelProd(cSTICMS != null ? origICMS + cSTICMS : "--");

				relacaoItems.setQtdNFeRelProd(detProd.getProd().getQCom());
				relacaoItems.setUndRelProd(detProd.getProd().getUCom());
				relacaoItems.setTipoOperacaoMat("*");
				relacaoItems.setEmbComprasRelProd(relacaoItems.getStatus() == EnumStatusRelItens.NAO_EXISTE.status
						? "1.0000" : String.valueOf(relacaoItems.getItem().getQtdEmbCompra()));

				qtdNFe = Double.parseDouble(relacaoItems.getQtdNFeRelProd());
				embComp = Double.parseDouble(relacaoItems.getEmbComprasRelProd());
				BigDecimal mult = new BigDecimal(qtdNFe * embComp);
				BigDecimal roundMult = mult.setScale(4, BigDecimal.ROUND_HALF_EVEN);

				relacaoItems.setQtdReposicaoRelProd(String.valueOf(roundMult));
				relacaoItems.setVlrUnitBrutoNFe(detProd.getProd().getVUnCom());
				relacaoItems.setVlrUndBrutoRelProd(detProd.getProd().getVUnCom());
				relacaoItems.setVlrDescontoRelProd(
						detProd.getProd().getVDesc() != null ? detProd.getProd().getVDesc() : "0.00");
				relacaoItems.setVlrDespAcessRelProd(
						detProd.getProd().getVOutro() != null ? detProd.getProd().getVOutro() : "0.00");

				// CALCULO VALOR UNITARIO LIQUIDO COM VALOR DE DESCONTO
				vlrUniLiqDesconto = detProd.getProd().getVDesc() != null
						? (Double.parseDouble(detProd.getProd().getVDesc())
								/ Double.parseDouble(detProd.getProd().getQCom()))
								: 0.00;
						// CALCULO VALOR UNITARIO LIQUIDO COM VALOR DESP. ACESSORIA
						vlrUniLiqDespAcc = detProd.getProd().getVOutro() != null
								? (Double.parseDouble(detProd.getProd().getVOutro()))
										/ Double.parseDouble(detProd.getProd().getQCom())
										: 0.00;
										// CALCULO VALOR UNITARIO LIQUIDO COM VALOR ICMS DESONERADO
										vlrUniLiqICMSDeson = vlrICMSDeson != null
												? (Double.parseDouble(vlrICMSDeson)) / Double.parseDouble(detProd.getProd().getQCom()) : 0.00;

												relacaoItems.setVlrUndLiqRelProd(String.valueOf(Double.parseDouble(detProd.getProd().getVUnCom())
														- vlrUniLiqDesconto + vlrUniLiqDespAcc - vlrUniLiqICMSDeson));
												relacaoItems.setAliqICMSRelProd(aliqICMS != null ? aliqICMS : "0.00");
												relacaoItems.setQtdConfRelProd(detProd.getProd().getQCom());
												relacaoItems.setVlrFreteRelProd(detProd.getProd().getVFrete());
												relacaoItems.setVlrSegRelProd(detProd.getProd().getVSeg());
												relacaoItems.setAliqIPIRelProd(
														ipi != null && ipi.getIPITrib() != null ? ipi.getIPITrib().getPIPI() : "0.00");
												relacaoItems.setVlrImpIPITribVBCRelProd(
														ipi != null && ipi.getIPITrib() != null ? ipi.getIPITrib().getVBC() : "0.00");
												relacaoItems.setVlrImpIPITribVIPIRelProd(
														ipi != null && ipi.getIPITrib() != null ? ipi.getIPITrib().getVIPI() : "0.00");
												relacaoItems.setVlrICMSDesonRelProd(vlrICMSDeson != null ? vlrICMSDeson : "0.00");
												relacaoItems.setVlrICMSSTRelProd(vlrICMS != null ? vlrICMS : "0.00");

												relacaoItems.setBaseCalcICMSRelProd(vlrBC != null ? vlrBC : "0.00");
												relacaoItems.setPautaMVARelProd(vlrPMVAST != null ? vlrPMVAST : "0.00");
												relacaoItems.setBaseCalcICMSSubRelProd(vlrBCST != null ? vlrBCST : "0.00");
												relacaoItems.setVlrICMSSubRelProd(vlrICMSST != null ? vlrICMSST : "0.00");
												relacaoItems.setSubEstoqueDestRelProd(depositoEstoque);

												listRelacaoItems.add(relacaoItems);
												i++;

												// RETORNO AO VALOR INICIAL DAS VARIABLES
												i = 0;
												icms = null;
												ipi = null;
												cSTICMS = null;
												origICMS = null;
												aliqICMS = null;
												vlrICMS = null;
												vlrICMSST = null;
												vlrICMSDeson = null;
												vlrPMVAST = null;
												vlrBC = null;
												vlrBCST = null;
			}
			ObservableList<RelacaoItems> listOutRelItens = FXCollections.observableArrayList(listRelacaoItems);

			tbViewRelacaoProd.getItems().addAll(listOutRelItens);

			tbColStatus.setCellValueFactory(new PropertyValueFactory<RelacaoItems, Integer>("status"));
			tbColStatus.setCellFactory(col -> new TableCell<RelacaoItems, Integer>() {
				public void updateItem(Integer status, boolean empty) {
					super.updateItem(status, empty);
					if (empty) {
						setText("");
					} else {
						if (status == EnumStatusRelItens.NAO_EXISTE.status) {
							FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CHAIN_BROKEN);
							icon.setSize("16");
							icon.setFill(Color.web("#c62828"));
							setGraphic(icon);
							// System.out.println(col.getTableView().getItems().get(col.getTableView().getSelectionModel().getSelectedIndex()-1).getCodRelProd());
						} else if (status == EnumStatusRelItens.EXISTE.status) {
							FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CHAIN);
							icon.setSize("16");
							icon.setFill(Color.web("#43A047"));
							setGraphic(icon);
						} else if (status == EnumStatusRelItens.PENDENTE.status) {
							FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CHAIN);
							icon.setSize("16");
							icon.setFill(Color.web("#0288D1"));
							setGraphic(icon);
						} else if (status == EnumStatusRelItens.NOVO.status) {
							FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.PLUS_CIRCLE);
							icon.setSize("16");
							icon.setFill(Color.web("#0288D1"));
							setGraphic(icon);
						}
					}
				}
			});

			// TableColumn<RelacaoItems, CustomTextField> col = new
			// TableColumn<RelacaoItems, CustomTextField>("wqrweqw");
			// col.setCellValueFactory(new
			// PropertyValueFactory<>("tipoOperacaoMat"));

			tbColCodRelProd.setCellValueFactory(new PropertyValueFactory<RelacaoItems, String>("codRelProd"));
			tbColOperationMathRelItem
			.setCellFactory(new Callback<TableColumn<RelacaoItems, String>, TableCell<RelacaoItems, String>>() {

				@Override
				public TableCell<RelacaoItems, String> call(TableColumn<RelacaoItems, String> param) {
					// TODO Auto-generated method stub
					CmbBox cmbBox = new CmbBox();
					// cmbBox.setAlignment(Pos.CENTER);
					return cmbBox;
				}
			});
			tbColOperationMathRelItem.setCellValueFactory(cellData -> cellData.getValue().getTipoOperacaoMatProperty());
			tbColDescripcaoNFeRelProd
			.setCellValueFactory(new PropertyValueFactory<RelacaoItems, String>("descripcaoNFeRelProd"));
			tbColDescripcaoEptusRelProd
			.setCellValueFactory(new PropertyValueFactory<RelacaoItems, String>("descripcaoEptusRelProd"));
			tbColNoFornecedorRelProd
			.setCellValueFactory(new PropertyValueFactory<RelacaoItems, String>("noFornecedorRelProd"));
			tbColCodNCMNFeRelProd
			.setCellValueFactory(new PropertyValueFactory<RelacaoItems, String>("codNCMNFeRelProd"));
			tbColCodNCMEptusRelProd
			.setCellValueFactory(new PropertyValueFactory<RelacaoItems, String>("codNCMEptusRelProd"));
			tbColCodBarRelProd.setCellValueFactory(cellData -> cellData.getValue().getCodBarRelProdProperty());
			tbColCodBarEptusRelProd
			.setCellValueFactory(cellData -> cellData.getValue().getCodBarEptusRelProdProperty());
			tbColCFOPRelProd.setCellValueFactory(new PropertyValueFactory<RelacaoItems, String>("cFOPRelProd"));
			tbColQtdNFeRelProd.setCellValueFactory(new PropertyValueFactory<RelacaoItems, String>("qtdNFeRelProd"));
			tbColUndRelProd.setCellValueFactory(new PropertyValueFactory<RelacaoItems, String>("undRelProd"));
			tbColEmbComprasRelProd
			.setCellValueFactory(new PropertyValueFactory<RelacaoItems, String>("embComprasRelProd"));
			tbColFatorConversaoRelProd
			.setCellValueFactory(cellFatorConversao -> cellFatorConversao.getValue().fatorConversaoProperty());
			tbColQtdReposicaoRelProd.setCellValueFactory(
					cellQtdReposicao -> cellQtdReposicao.getValue().getQtdReposicaoRelProdProperty());

			tbColVlrUndBrutoRelProd
			.setCellValueFactory(new PropertyValueFactory<RelacaoItems, String>("vlrUndBrutoRelProd"));
			tbColVlrUnitBrutoNFeRelProd.setCellValueFactory(
					cellVlrUnitBrutoNFe -> cellVlrUnitBrutoNFe.getValue().getVlrUnitBrutoNFeProperty());
			tbColVlrDescontoRelProd
			.setCellValueFactory(new PropertyValueFactory<RelacaoItems, String>("vlrDescontoRelProd"));
			tbColVlrDespAcessRelProd
			.setCellValueFactory(new PropertyValueFactory<RelacaoItems, String>("vlrDespAcessRelProd"));
			tbColCSTICMSRelProd.setCellValueFactory(new PropertyValueFactory<RelacaoItems, String>("cSTICMSRelProd"));
			tbColVlrUndLiqRelProd
			.setCellValueFactory(new PropertyValueFactory<RelacaoItems, String>("vlrUndLiqRelProd"));
			tbColAliqIPIRelProd.setCellValueFactory(new PropertyValueFactory<RelacaoItems, String>("aliqIPIRelProd"));
			tbColImpIPITribVBCRelProd
			.setCellValueFactory(new PropertyValueFactory<RelacaoItems, String>("vlrImpIPITribVBCRelProd"));
			tbColImpIPITribVIPIRelProd
			.setCellValueFactory(new PropertyValueFactory<RelacaoItems, String>("vlrImpIPITribVIPIRelProd"));
			tbColAliqICMSRelProd.setCellValueFactory(new PropertyValueFactory<RelacaoItems, String>("aliqICMSRelProd"));
			tbColQtdConfRelProd.setCellValueFactory(new PropertyValueFactory<RelacaoItems, String>("qtdConfRelProd"));
			tbColVlrFreteRelProd.setCellValueFactory(new PropertyValueFactory<RelacaoItems, String>("vlrFreteRelProd"));
			tbColVlrSegRelProd.setCellValueFactory(new PropertyValueFactory<RelacaoItems, String>("vlrSegRelProd"));
			tbColVlrICMSDesonRelProd
			.setCellValueFactory(new PropertyValueFactory<RelacaoItems, String>("vlrICMSDesonRelProd"));
			tbColReducBCICMSRelProd
			.setCellValueFactory(new PropertyValueFactory<RelacaoItems, String>("reducBCICMSRelProd"));
			tbColBaseCalcICMSRelProd
			.setCellValueFactory(new PropertyValueFactory<RelacaoItems, String>("baseCalcICMSRelProd"));
			tbColVlrICMSSTRelProd
			.setCellValueFactory(new PropertyValueFactory<RelacaoItems, String>("vlrICMSSTRelProd"));
			tbColModBCICMSSubRelProd
			.setCellValueFactory(new PropertyValueFactory<RelacaoItems, String>("modBCICMSSubRelProd"));
			tbColPautaMVARelProd.setCellValueFactory(new PropertyValueFactory<RelacaoItems, String>("pautaMVARelProd"));
			tbColBaseCalcICMSSubRelProd
			.setCellValueFactory(new PropertyValueFactory<RelacaoItems, String>("baseCalcICMSSubRelProd"));
			tbColVlrICMSSubRelProd
			.setCellValueFactory(new PropertyValueFactory<RelacaoItems, String>("vlrICMSSubRelProd"));
			tbColSubEstoqueDestRelProd
			.setCellValueFactory(new PropertyValueFactory<RelacaoItems, String>("subEstoqueDestRelProd"));
			// tbViewRelacaoProd.getColumns().add(col);
			// tbViewRelacaoProd.getSelectionModel().getSelectedItem().getCodBarEptusRelProdProperty().bind(txtFileChooser.textProperty());
			tbViewRelacaoProd.refresh();
		}

		btnSave.setDisable(false);
	}

	Util util = new Util();
	OperacaoEntrada operacao;
	DepositoEstoque depositoEstoque;
	Secao secao;
	CentroCusto centCusto;
	PlanoConta planoConta;
	Portador portador;
	Fornecedor fornecedor;
	Item item;
	Unidade unidade;
	Unidade undEmbalagem;
	Fabricante fabricante;
	Grupo grupo;
	SubGrupo subGrupo;
	Grade grade;
	TabelaNCM ncm;
	Moeda moeda;
	Tributacao tributacao;

	/**
	 * EXIBE O FORMULARIO DE BUSCA DE departamento
	 */
	public Object showSearch(String actionFrom) {

		ObservableList<ComboBoxFilter> list = FXCollections.observableArrayList();
		Object obj = null;

		switch (actionFrom) {
		
		case "OPERACAO":
			list.add(new ComboBoxFilter("Código", 1, "codigo"));
			list.add(new ComboBoxFilter("Descrição", 2, "descricao"));
			list.add(new ComboBoxFilter("Ativo", 3, "flagAtivo"));

			obj = util.showSearchGetParameters("Operações de Entreda", "descricao", OperacaoEntradaDAO.class, list);

			if (obj != null) {
				operacao = (OperacaoEntrada) obj;
				txtCodigoOperacao.setText(String.valueOf(operacao.getCodigo()));
				txtOperacao.setText(operacao.getDescricao());
				txtCFOP.setText(String.valueOf(operacao.getCfop()));
				txtCFOP2.setText(operacao.getCfop2().toString());
				txtFileChooser.requestFocus();

				telaConfig(operacao);
			}
			break;
			
		case "FORNECEDOR":
			list.add(new ComboBoxFilter("Código", 1, "codigo"));
			list.add(new ComboBoxFilter("Razão Social", 2, "razao"));
			list.add(new ComboBoxFilter("Nome Fantasia", 3, "fantasia"));
			list.add(new ComboBoxFilter("Código Empresa", 4, "codemp"));
			list.add(new ComboBoxFilter("CPF / CNPJ", 5, "cpfCnpj"));

			obj = util.showSearchGetParameters("Fornecedor", "razao", FornecedorDAO.class, list);

			if (obj != null) {
				fornecedor = (Fornecedor) obj;
				txtCodigoFornecedor.setText(String.valueOf(fornecedor.getCodigo()));
				txtFornecedor.setText(fornecedor.getRazao());
				txtNoCNPJ.setText(fornecedor.getCpfCnpj());
				dtxtDataEmissao.requestFocus();
			}
			break;

		case "DEPOSITO_ESTOQUE":
			list.add(new ComboBoxFilter("Código", 1, "codigo"));
			list.add(new ComboBoxFilter("Descrição", 2, "descricao"));
			list.add(new ComboBoxFilter("Ativo", 3, "flagAtivo"));

			obj = util.showSearchGetParameters("Deposito de Estoque", "descricao", DepositoEstoqueDAO.class, list);
			if (obj != null) {
				depositoEstoque = (DepositoEstoque) obj;
				txtCodigoDepositoEstoque.setText(String.valueOf(depositoEstoque.getCodigo()));
				txtDepositoEstoque.setText(depositoEstoque.getDescricao());
				txtCodigoSecao.requestFocus();
			}

			break;

		case "CENTRO_CUSTO":
			list.add(new ComboBoxFilter("Código", 1, "codigo"));
			list.add(new ComboBoxFilter("Descrição", 2, "descricao"));
			list.add(new ComboBoxFilter("Ativo", 3, "flagAtivo"));

			obj = util.showSearchGetParameters("Centro de Custo", "descricao", CentroCustoDAO.class, list);
			if (obj != null) {
				centCusto = (CentroCusto) obj;
				// txtCodigoCentroCusto.setText(String.valueOf(centCusto.getCodigo()));
				txtCentroCusto.setText(centCusto.getDescricao());
				txtCodigoPlanosContas.requestFocus();
			}

			break;

		case "PLANOS_CONTAS":
			list.add(new ComboBoxFilter("Código", 1, "codigo"));
			list.add(new ComboBoxFilter("Descrição", 2, "descricao"));
			list.add(new ComboBoxFilter("Ativo", 3, "flagAtivo"));

			obj = util.showSearchGetParameters("Planos de Contas", "descricao", PlanoContaDAO.class, list);
			if (obj != null) {
				planoConta = (PlanoConta) obj;
				txtCodigoPlanosContas.setText(String.valueOf(planoConta.getCodigo()));
				txtPlanoContas.setText(planoConta.getDescricao());
				txtCodigoPortador.requestFocus();
			}

			break;

		case "SECAO":
			list.add(new ComboBoxFilter("Código", 1, "codigo"));
			list.add(new ComboBoxFilter("Descrição", 2, "descricao"));
			list.add(new ComboBoxFilter("Ativo", 3, "flagAtivo"));

			obj = util.showSearchGetParameters("Seção", "descricao", SecaoDAO.class, list);
			if (obj != null) {
				secao = (Secao) obj;
				txtCodigoSecao.setText(String.valueOf(secao.getCodigo()));
				txtSecao.setText(secao.getDescricao());
				txtCodigoPlanosContas.requestFocus();
			}

			break;

		case "PORTADOR":
			list.add(new ComboBoxFilter("Código", 1, "codigo"));
			list.add(new ComboBoxFilter("Descrição", 2, "descricao"));
			list.add(new ComboBoxFilter("Ativo", 3, "flagAtivo"));

			obj = util.showSearchGetParameters("Portador", "descricao", PortadorDAO.class, list);
			if (obj != null) {
				portador = (Portador) obj;
				txtCodigoPortador.setText(String.valueOf(portador.getCodigo()));
				txtPortador.setText(portador.getDescricao());
			}

			break;

		case "ITEM":
			list.add(new ComboBoxFilter("Código", 1, "codigo"));
			list.add(new ComboBoxFilter("Descrição", 2, "descricao"));
			list.add(new ComboBoxFilter("Ativo", 3, "flagAtivo"));

			obj = util.showSearchGetParameters("Itens", "descricao", ItemDAO.class, list);
			if (obj != null) {
				item = (Item) obj;
				txtCodItemRelItens.setText(String.valueOf(item.getCodigo()));
				txtDescripcaoRelItens.setText(item.getDescricao());
				txtCodFabRelItens.setText(item.getFabricante().getCodigo().toString());
				txtFabricanteRelItens.setText(item.getFabricante().getDescricao());
				txtCodGrupRelItens.setText(item.getGrupo().getCodigo().toString());
				txtGrupoRelItens.setText(item.getGrupo().getDescricao());
				txtCodSubGrupRelItens.setText(item.getSubGrupo().getCodigo().toString());
				txtSubGrupRelItens.setText(item.getSubGrupo().getDescricao());
				txtCodMoedaRelItens.setText(item.getMoeda().getCodigo().toString());
				txtMoedaRelItens.setText(item.getMoeda().getSimbolo());
				txtCodUniRelItens.setText(item.getUnidade().getCodigo().toString());
				txtUnidadeRelItens.setText(item.getUnidade().getDescricao());
				txtCodUnidEmbRelItens.setText(item.getUnidadeEmb().getCodigo().toString());
				txtUnidEmbRelItens.setText(item.getUnidadeEmb().getDescricao());
				txtCodNCMRelItens.setText(item.getNcm().getCodNCM());
				txtNCMRelItens.setText(item.getNcm().getDescricao());
				// FALTA GRADE DE ESTOQUE
				// txtCodTribVendaRelItens.setText(item.getItemValors().iterator().next().getTributacao().getCodigo().toString());
				// txtTribVendaRelItens.setText(item.getItemValors().iterator().next().getTributacao().getDescricao());
				txtEmbCompRelItens.requestFocus();
			}
			break;

		case "UNIDADE":
			list.add(new ComboBoxFilter("Código", 1, "codigo"));
			list.add(new ComboBoxFilter("Descrição", 2, "descricao"));
			list.add(new ComboBoxFilter("Ativo", 3, "flagAtivo"));

			obj = util.showSearchGetParameters("Unidades", "descricao", UnidadeDAO.class, list);
			if (obj != null) {
				unidade = (Unidade) obj;
				txtCodUniRelItens.setText(String.valueOf(unidade.getCodigo()));
				txtUnidadeRelItens.setText(unidade.getDescricao());
				txtCodUnidEmbRelItens.requestFocus();
			}
			break;

		case "UNIDADE_EMBALAGEM":
			list.add(new ComboBoxFilter("Código", 1, "codigo"));
			list.add(new ComboBoxFilter("Descrição", 2, "descricao"));
			list.add(new ComboBoxFilter("Ativo", 3, "flagAtivo"));

			obj = util.showSearchGetParameters("Unidades de Embalagem", "descricao", UnidadeDAO.class, list);
			if (obj != null) {
				undEmbalagem = (Unidade) obj;
				txtCodUnidEmbRelItens.setText(String.valueOf(undEmbalagem.getCodigo()));
				txtUnidEmbRelItens.setText(undEmbalagem.getDescricao());
				txtCodNCMRelItens.requestFocus();
			}
			break;

		case "GRUPO":
			list.add(new ComboBoxFilter("Código", 1, "codigo"));
			list.add(new ComboBoxFilter("Descrição", 2, "descricao"));
			list.add(new ComboBoxFilter("Ativo", 3, "flagAtivo"));

			obj = util.showSearchGetParameters("Grupos", "descricao", GrupoDAO.class, list);
			if (obj != null) {
				grupo = (Grupo) obj;
				txtCodGrupRelItens.setText(String.valueOf(grupo.getCodigo()));
				txtGrupoRelItens.setText(grupo.getDescricao());
				txtCodSubGrupRelItens.requestFocus();
			}
			break;

		case "SUBGRUPO":
			list.add(new ComboBoxFilter("Código", 1, "codigo"));
			list.add(new ComboBoxFilter("Descrição", 2, "descricao"));
			list.add(new ComboBoxFilter("Ativo", 3, "flagAtivo"));

			obj = util.showSearchGetParameters("Sub Grupos", "descricao", SubGrupoDAO.class, list);
			if (obj != null) {
				subGrupo = (SubGrupo) obj;
				txtCodSubGrupRelItens.setText(String.valueOf(subGrupo.getCodigo()));
				txtSubGrupRelItens.setText(subGrupo.getDescricao());
				txtCodMoedaRelItens.requestFocus();
			}
			break;

		case "FABRICANTE":
			list.add(new ComboBoxFilter("Código", 1, "codigo"));
			list.add(new ComboBoxFilter("Descrição", 2, "descricao"));
			list.add(new ComboBoxFilter("Ativo", 3, "flagAtivo"));

			obj = util.showSearchGetParameters("Fabricantes", "descricao", FabricanteDAO.class, list);
			if (obj != null) {
				fabricante = (Fabricante) obj;
				txtCodFabRelItens.setText(String.valueOf(fabricante.getCodigo()));
				txtFabricanteRelItens.setText(fabricante.getDescricao());
				txtCodGrupRelItens.requestFocus();
			}
			break;

		case "NCM":
			list.add(new ComboBoxFilter("Código", 1, "codigo"));
			list.add(new ComboBoxFilter("Descrição", 2, "descricao"));
			list.add(new ComboBoxFilter("Ativo", 3, "flagAtivo"));

			obj = util.showSearchGetParameters("NCM", "descricao", TabelaNCMDAO.class, list);
			if (obj != null) {
				ncm = (TabelaNCM) obj;
				txtCodNCMRelItens.setText(String.valueOf(ncm.getCodigo()));
				txtNCMRelItens.setText(ncm.getDescricao());
				txtCodGradEstoqRelItens.requestFocus();
			}
			break;

		case "GRADE_ESTOQUE":
			list.add(new ComboBoxFilter("Código", 1, "codigo"));
			list.add(new ComboBoxFilter("Descrição", 2, "descricao"));
			list.add(new ComboBoxFilter("Ativo", 3, "flagAtivo"));

			obj = util.showSearchGetParameters("Grade de Estoque", "descricao", GradeDAO.class, list);
			if (obj != null) {
				grade = (Grade) obj;
				txtCodGradEstoqRelItens.setText(String.valueOf(grade.getCodigo()));
				txtGradeRelItens.setText(grade.getDescricao());
				txtCodTribVendaRelItens.requestFocus();
			}
			break;

		case "MOEDA":
			list.add(new ComboBoxFilter("Código", 1, "codigo"));
			list.add(new ComboBoxFilter("Simbolo", 2, "simbolo"));
			list.add(new ComboBoxFilter("Descripção Decimal Sing", 3, "descDecimalSingular"));
			list.add(new ComboBoxFilter("Descripção Decimal Plural", 4, "descDecimalPlural"));
			list.add(new ComboBoxFilter("Ativo", 5, "flagAtivo"));
			
			obj = util.showSearchGetParameters("Moedas", "simbolo", MoedaDAO.class, list);
			if (obj != null) {
				moeda = (Moeda) obj;
				txtCodMoedaRelItens.setText(String.valueOf(moeda.getCodigo()));
				txtMoedaRelItens.setText(moeda.getSimbolo());
				txtCodUniRelItens.requestFocus();
			}
			break;

		case "TRIBUTACAO_VENDAS":
			list.add(new ComboBoxFilter("Código", 1, "codigo"));
			list.add(new ComboBoxFilter("Descrição", 2, "descricao"));
			list.add(new ComboBoxFilter("Ativo", 3, "flagAtivo"));

			obj = util.showSearchGetParameters("Tributação de Vendas", "descricao", TributacaoDAO.class, list);
			if (obj != null) {
				tributacao = (Tributacao) obj;
				txtCodTribVendaRelItens.setText(String.valueOf(tributacao.getCodigo()));
				txtTribVendaRelItens.setText(tributacao.getDescricao());
				txtCodBarraRelItens.requestFocus();
			}
			break;

		default:
			break;
		}

		return obj;
	}

	public void saveFornecedor() {
		// FORNECEDOR NAO EXISTE NO BANCO DE DADOS

		Task<String> tarefaSaveFornecedor = new Task<String>() {

			Fornecedor forn = null;

			@Override
			protected String call() throws Exception {
				Emit emitenteNFe = tbViewCabecalhoNotFisc.getItems().get(0).getInfNFe().getEmit();
				Fornecedor fornecedor = null;
				// INICIALIZAR TUDOS OS ATRIBUTOS DA CLASSE NOT NULL
				fornecedor = (Fornecedor) Util.class.newInstance().initializeAttribClass(new Fornecedor());

				fornecedor.setFantasia(emitenteNFe.getXFant());
				fornecedor.setRazao(emitenteNFe.getXNome());
				fornecedor.setCpfCnpj(emitenteNFe.getCNPJ() != null ? Util.getStringConverterCNPJ(emitenteNFe.getCNPJ())
						: emitenteNFe.getCPF());
				fornecedor.setEndereco(emitenteNFe.getEnderEmit().getXLgr());
				fornecedor.setEndNumero(emitenteNFe.getEnderEmit().getNro());
				fornecedor.setComplemento(
						emitenteNFe.getEnderEmit().getXCpl() != null ? emitenteNFe.getEnderEmit().getXCpl() : "");
				fornecedor.setBairro(emitenteNFe.getEnderEmit().getXBairro());
				fornecedor.setCep(emitenteNFe.getEnderEmit().getCEP());
				fornecedor.setUf(emitenteNFe.getEnderEmit().getUF().value());

				// BUSCAR CODIGO DE CIDADE PELO CODIGO IBGE
				if (emitenteNFe.getEnderEmit().getCMun() != null) {
					try {
						Cidade cidade = (Cidade) CidadeDAO.class.newInstance()
								.getByIBGE(emitenteNFe.getEnderEmit().getCMun()).getObjeto();
						fornecedor.setCodCidade(cidade.getCodigo());
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				fornecedor.setCidade(emitenteNFe.getEnderEmit().getXMun());
				fornecedor.setFone(emitenteNFe.getEnderEmit().getFone());
				fornecedor.setIeRg(emitenteNFe.getIE());

				try {

					forn = (Fornecedor) FornecedorDAO.class.newInstance().insert(fornecedor).getObjeto();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// }
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				// System.out.println("ID---"+forn.getCodigo()+" :/:
				// CHECKDELETE---- "+forn.getCheckDelete());
				fornecedorRe = forn;
				txtCodigoFornecedor.setText(String.valueOf(forn.getCodigo()));
			}

			@Override
			protected void failed() {
				stg.close();
			}

			@Override
			protected void cancelled() {
				super.cancelled();
			}
		};
		Thread t = new Thread(tarefaSaveFornecedor);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(NotaFiscalEntradaController.class, tarefaSaveFornecedor,
				"Gravando informação do fornecedor no banco de dados...", false);

	}

	/**
	 * METODO PARA GRAVAR NFe NO BANCO DE DADOS
	 */
	public void saveNFe() {

		NfEntradaCab retrnNFentradaCab = null;

		Task<String> tarefaSaveNFe = new Task<String>() {
			@Override
			protected String call() throws Exception {
				try {
					NfEntradaDAO.class.newInstance().insert(tbViewCabecalhoNotFisc.getItems().get(0), fornecedorRe,
							tbViewRelacaoProd.getItems(), xmlNFe);
					// System.out.println("CheckDelete
					// "+retrnNFentradaCab.getCheckDelete());
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				// System.out.println("ID---"+forn.getCodigo()+" :/:
				// CHECKDELETE---- "+forn.getCheckDelete());

			}

			@Override
			protected void failed() {
				stg.close();
			}

			@Override
			protected void cancelled() {
				super.cancelled();
			}
		};
		Thread t = new Thread(tarefaSaveNFe);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(NotaFiscalEntradaController.class, tarefaSaveNFe,
				"Gravando informação da NFe no banco de dados...", false);

	}

	/**
	 * 
	 */
	public void changeData() {
		enderecoXML = "C:\\EptusAM\\System.xml\\" + txtFileChooser.getText();
		actionBtnProcessarXML(null);
	}

	@FXML
	void actionBtnSave(ActionEvent evt) {
		boolean valida = false;
		if (tbViewRelacaoProd.getItems() != null) {
			List<RelacaoItems> listRelItens = tbViewRelacaoProd.getItems();
			for (int i = 0; i < listRelItens.size(); i++) {
				if (listRelItens.get(i).getStatus() == 0) {
					tbViewRelacaoProd.getSelectionModel().select(i);
					tbViewRelacaoProd.scrollTo(i);
					valida = false;
					break;
				} else {
					valida = true;
				}
			}
		}
		tbViewRelacaoProd.requestFocus();
		evt.consume();
	}

	// ----------------- ACTIONS FORMULARIO REPOSIÇÃO DE ITENS
	// -----------------------------------------
	@FXML
	void actionBtnCloseRepItens(ActionEvent evt) {
		Util.fechaTelaCadastro((AnchorPane) tabRelItens.getContent(), anchorPaneRepItens);
		if (tbViewRelacaoProd.getItems().size() > 0) {
			for (int i = 0; i < tbViewRelacaoProd.getItems().size(); i++) {

				if (tbViewRelacaoProd.getItems().get(i).getCodBarRelProd().toString()
						.equals(txtCodBarraRelItens.getText())) {
					tbViewRelacaoProd.getSelectionModel().select(i);
					tbViewRelacaoProd.scrollTo(i);

					break;
				}
			}
			tbViewRelacaoProd.requestFocus();

		}

	}

	@FXML
	void actionBtnConfirmRepItens(ActionEvent evt) {
		List<ItemValor> listItemValor = null;
		if (!txtCodItemRelItens.getText().equals("") && item != null) {
			relItem.setStatus(EnumStatusRelItens.PENDENTE.status);
			relItem.setItem(item);
			relItem.setCodRelProd(item.getCodigo().toString());
			relItem.setDescripcaoEptusRelProd(item.getDescricao());
			// relItem.setNoFabricanteRelProd(item.getFabricante().getCodigo().toString());
			relItem.setCodNCMEptusRelProd(item.getCodNcm().toString());
			relItem.setCodBarEptusRelProd(item.getItemCodBars().iterator().next().getCodBarras());
			// tbViewRelacaoProd.refresh();
			BigDecimal qtdEmbCompras = new BigDecimal(txtEmbCompRelItens.getText());
			relItem.setEmbComprasRelProd(String.valueOf(qtdEmbCompras.setScale(4, RoundingMode.HALF_EVEN)));
			BigDecimal qtdNFe = BigDecimal.valueOf(Double.parseDouble(relItem.getQtdNFeRelProd()));

			relItem.setQtdReposicaoRelProd(
					qtdNFe.multiply(qtdEmbCompras).setScale(4, RoundingMode.HALF_EVEN).toString());
			relItem.setFatorConversao(String.valueOf(qtdEmbCompras.setScale(4, RoundingMode.HALF_EVEN)));
		} else {
			if (!txtCodUniRelItens.getText().equals("") && !txtCodGrupRelItens.getText().equals("")
					&& !txtCodSubGrupRelItens.getText().equals("") && !txtCodFabRelItens.getText().equals("")
					&& !txtCodNCMRelItens.getText().equals("") && !txtCodGradEstoqRelItens.getText().equals("")) {
				Item itemNew = null;
				ItemCodBar itemCodBar = null;

				try {
					itemNew = (Item) Util.class.newInstance().initializeAttribClass(new Item());
					itemCodBar = (ItemCodBar) Util.class.newInstance().initializeAttribClass(new ItemCodBar());

				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				itemNew.setCodemp(DadosGlobais.empresaLogada.getCodigo());
				itemNew.setDescricao(relItem.getDescripcaoNFeRelProd());
				itemNew.setFabricante(fabricante);
				itemNew.setGrupo(grupo);
				itemNew.setSubGrupo(subGrupo);
				itemNew.setNcm(ncm);
				itemNew.setCodNcm(ncm.getCodigo());
				itemNew.setCodNcmFk(ncm.getCheckDelete());
				itemNew.setUnidade(unidade);
				itemNew.setUnidadeEmb(undEmbalagem);
				itemNew.setQtdEmbCompra(BigDecimal.valueOf(Double.parseDouble(txtEmbCompRelItens.getText())));
				itemNew.setQtdEmbVenda(BigDecimal.valueOf(Double.parseDouble(txtEmbVendaRelItens.getText())));
				itemNew.setObservacao(txtAreaObservacao.getText());
				itemCodBar.setCodBarras(txtCodBarraRelItens.getText());
				itemCodBar.setFlagCodbarPrincipal(1);
				itemCodBar.setCodemp(itemNew.getCodemp());
				Set<ItemCodBar> itensCodBar = new HashSet<ItemCodBar>();
				itensCodBar.add(itemCodBar);
				List<ItemCodBar> listCodBar = new ArrayList<ItemCodBar>();
				listCodBar.add(itemCodBar);
				itemNew.setItemCodBars(itensCodBar);

				listItemValor = new ArrayList<ItemValor>();

				for (ItemValor iValor : tbViewPrecosEmp.getItems()) {
					iValor.setItem(itemNew);
					iValor.setTributacao(tributacao);
					listItemValor.add(iValor);
				}
				itemNew.setItemValors(listItemValor);

				itemNew.setFabricante(fabricante);
				itemNew.setMoeda(moeda);
				itemNew.setUnidade(unidade);
				itemNew.setUnidadeEmb(undEmbalagem);

				relItem.setCodRelProd("+1");
				relItem.setStatus(EnumStatusRelItens.NOVO.status);
				relItem.setItem(itemNew);
				relItem.setDescripcaoEptusRelProd(relItem.getDescripcaoNFeRelProd());
				relItem.setCodNCMEptusRelProd(itemNew.getNcmCodigo().toString());
				relItem.setCodBarRelProd(itemCodBar.getCodBarras());
				BigDecimal qtdEmbCompras = new BigDecimal(txtEmbCompRelItens.getText());
				relItem.setEmbComprasRelProd(String.valueOf(qtdEmbCompras.setScale(4, RoundingMode.HALF_EVEN)));
				BigDecimal qtdNFe = BigDecimal.valueOf(Double.parseDouble(relItem.getQtdNFeRelProd()));

				relItem.setQtdReposicaoRelProd(
						qtdNFe.multiply(qtdEmbCompras).setScale(4, RoundingMode.HALF_EVEN).toString());
				relItem.setFatorConversao(String.valueOf(qtdEmbCompras.setScale(4, RoundingMode.HALF_EVEN)));

			}
		}
		tbViewRelacaoProd.refresh();
		Util.fechaTelaCadastro((AnchorPane) tabRelItens.getContent(), anchorPaneRepItens);
		if (tbViewRelacaoProd.getItems().size() > 0) {
			for (int i = 0; i < tbViewRelacaoProd.getItems().size(); i++) {
				if (tbViewRelacaoProd.getItems().get(i).getDescripcaoNFeRelProd()
						.equals(txtCodBarraRelItens.getText())) {
					tbViewRelacaoProd.getSelectionModel().select(i + 1);
					tbViewRelacaoProd.scrollTo(i);
					break;
				}
			}
			tbViewRelacaoProd.requestFocus();
		}
		txtCodItemRelItens.setText("");
		txtCodUniRelItens.setText("");
		txtUnidadeRelItens.setText("");
		txtCodGrupRelItens.setText("");
		txtGrupoRelItens.setText("");
		txtCodSubGrupRelItens.setText("");
		txtSubGrupRelItens.setText("");
		txtCodFabRelItens.setText("");
		txtFabricanteRelItens.setText("");
		txtCodNCMRelItens.setText("");
		txtNCMRelItens.setText("");
		txtCodGradEstoqRelItens.setText("");
		txtGradeRelItens.setText("");
		txtCodUnidEmbRelItens.setText("");
		txtUnidEmbRelItens.setText("");
		txtCodMoedaRelItens.setText("");
		txtMoedaRelItens.setText("");
		txtCodTribVendaRelItens.clear();
		txtTribVendaRelItens.clear();

//		item = null;
//		unidade = null;
//		fabricante = null;
////		fornecedor = null;
//		grupo = null;
//		subGrupo = null;
//		ncm = null;
//		grade = null;
//		moeda = null;
//		undEmbalagem = null;
//		tributacao = null;
//		listItemValor = null;

	}

	@FXML
	void actionSearch(ActionEvent event) {

		if (event.getSource().equals(txtCodigoOperacao) || txtCodigoOperacao.getRight().isPressed()) {
			showSearch("OPERACAO");
		}
		
		if (event.getSource().equals(txtCodigoFornecedor) || txtCodigoOperacao.getRight().isPressed())
			showSearch("FORNECEDOR");
		
		if (event.getSource().equals(txtCodigoSecao) || txtCodigoSecao.getRight().isPressed())
			showSearch("SECAO");

		if (event.getSource().equals(txtCodigoPlanosContas) || txtCodigoPlanosContas.getRight().isPressed())
			showSearch("PLANOS_CONTAS");

		if (event.getSource().equals(txtCodigoPortador) || txtCodigoPortador.getRight().isPressed())
			showSearch("PORTADOR");

		// FORMULARIO RELAÇÃO DE ITENS
		if (event.getSource().equals(txtCodItemRelItens) || txtCodItemRelItens.getRight().isPressed()) {
			showSearch("ITEM");
		}

		if (event.getSource().equals(txtCodUniRelItens) || txtCodUniRelItens.getRight().isPressed())
			showSearch("UNIDADE");

		if (event.getSource().equals(txtCodGrupRelItens) || txtCodGrupRelItens.getRight().isPressed())
			showSearch("GRUPO");

		if (event.getSource().equals(txtCodSubGrupRelItens) || txtCodSubGrupRelItens.getRight().isPressed())
			showSearch("SUBGRUPO");

		if (event.getSource().equals(txtCodFabRelItens) || txtCodFabRelItens.getRight().isPressed())
			showSearch("FABRICANTE");

		if (event.getSource().equals(txtCodNCMRelItens) || txtCodNCMRelItens.getRight().isPressed())
			showSearch("NCM");

		if (event.getSource().equals(txtCodGradEstoqRelItens) || txtCodGradEstoqRelItens.getRight().isPressed())
			showSearch("GRADE_ESTOQUE");

		if (event.getSource().equals(txtCodUnidEmbRelItens) || txtCodUnidEmbRelItens.getRight().isPressed())
			showSearch("UNIDADE_EMBALAGEM");

		if (event.getSource().equals(txtCodMoedaRelItens) || txtCodMoedaRelItens.getRight().isPressed())
			showSearch("MOEDA");

		if (event.getSource().equals(txtCodTribVendaRelItens) || txtCodTribVendaRelItens.getRight().isPressed())
			showSearch("TRIBUTACAO_VENDAS");

		event.consume();

	}

	@FXML
	void onKeyPressed(KeyEvent keyEvt) {

		switch (keyEvt.getCode()) {
		case F2:
			if (keyEvt.getSource().equals(txtCodigoOperacao))
				showSearch("OPERACAO");
			
			if (keyEvt.getSource().equals(txtCodigoFornecedor))
				showSearch("FORNECEDOR");

			if (keyEvt.getSource().equals(txtCodigoDepositoEstoque))
				showSearch("DEPOSITO_ESTOQUE");

			if (keyEvt.getSource().equals(txtCodigoSecao))
				showSearch("SECAO");

			if (keyEvt.getSource().equals(txtCodigoPlanosContas))
				showSearch("PLANOS_CONTAS");

			if (keyEvt.getSource().equals(txtCodigoPortador))
				showSearch("PORTADOR");

			if (keyEvt.getSource().equals(txtCodItemRelItens))
				showSearch("ITEM");

			if (keyEvt.getSource().equals(txtCodUniRelItens))
				showSearch("UNIDADE");

			if (keyEvt.getSource().equals(txtCodGrupRelItens))
				showSearch("GRUPO");

			if (keyEvt.getSource().equals(txtCodSubGrupRelItens))
				showSearch("SUBGRUPO");

			if (keyEvt.getSource().equals(txtCodFabRelItens))
				showSearch("FABRICANTE");

			if (keyEvt.getSource().equals(txtCodNCMRelItens))
				showSearch("NCM");

			if (keyEvt.getSource().equals(txtCodGradEstoqRelItens))
				showSearch("GRADE_ESTOQUE");

			if (keyEvt.getSource().equals(txtCodUnidEmbRelItens))
				showSearch("UNIDADE_EMBALAGEM");

			if (keyEvt.getSource().equals(txtCodMoedaRelItens))
				showSearch("MOEDA");

			if (keyEvt.getSource().equals(txtCodTribVendaRelItens))
				showSearch("TRIBUTACAO_VENDAS");

			break;

		case ENTER:
//		case TAB:
			if (!keyEvt.isShiftDown()) {
				if(keyEvt.getSource().equals(txtEmbCompRelItens)){
					txtCodFabRelItens.requestFocus();
				}
				
				if (keyEvt.getSource().equals(txtCodigoOperacao))
					searchOperacao(Integer.parseInt(txtCodigoOperacao.getText()));
				
				if (keyEvt.getSource().equals(txtCodigoFornecedor))
					searchFornecedor(Integer.parseInt(txtCodigoFornecedor.getText()));
				
				if (keyEvt.getSource().equals(txtCodigoDepositoEstoque))
					searchDepositoEstoque(Integer.parseInt(txtCodigoDepositoEstoque.getText()));
				
				if (keyEvt.getSource().equals(txtCodItemRelItens) && !Util.noEmpty(txtCodItemRelItens))
					searchItem(Integer.parseInt(txtCodItemRelItens.getText()));

				if (keyEvt.getSource().equals(txtCodUniRelItens) && !Util.noEmpty(txtCodUniRelItens))
					searchUnidade(Integer.parseInt(txtCodUniRelItens.getText()), txtCodUniRelItens);

				if (keyEvt.getSource().equals(txtCodUnidEmbRelItens) && !Util.noEmpty(txtCodUnidEmbRelItens))
					searchUnidade(Integer.parseInt(txtCodUnidEmbRelItens.getText()), txtCodUnidEmbRelItens);

				if (keyEvt.getSource().equals(txtCodGrupRelItens) && !Util.noEmpty(txtCodGrupRelItens))
					searchGrupo(Integer.parseInt(txtCodGrupRelItens.getText()));

				if (keyEvt.getSource().equals(txtCodSubGrupRelItens) && !Util.noEmpty(txtCodSubGrupRelItens))
					searchSubGrupo(Integer.parseInt(txtCodSubGrupRelItens.getText()));

				if (keyEvt.getSource().equals(txtCodFabRelItens) && !Util.noEmpty(txtCodFabRelItens))
					searchFabricante(Integer.parseInt(txtCodFabRelItens.getText()));

				if (keyEvt.getSource().equals(txtCodNCMRelItens) && !Util.noEmpty(txtCodNCMRelItens))
					searchNCM(Integer.parseInt(txtCodNCMRelItens.getText()));

				if (keyEvt.getSource().equals(txtCodGradEstoqRelItens))
					searchGrade(Integer.parseInt(txtCodGradEstoqRelItens.getText()));

				if (keyEvt.getSource().equals(txtCodMoedaRelItens))
					searchMoeda(Integer.parseInt(txtCodMoedaRelItens.getText()));

				if (keyEvt.getSource().equals(txtCodTribVendaRelItens)){
					searchTributacao(Integer.parseInt(txtCodTribVendaRelItens.getText()));
				}

				keyEvt.consume();
			}
			break;
			//
		case TAB:
			if (keyEvt.getSource().equals(txtCodigoOperacao) && !util.noEmpty(txtCodigoOperacao))			
				txtCodigoOperacao.requestFocus();
			else
				setNext(keyEvt.getSource());
			
			
			
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
	
	public void setNext(Object txtObj){
		if(txtObj instanceof TextField){
			if(((TextField)txtObj).getSkin() instanceof BehaviorSkinBase){
				((BehaviorSkinBase) ((TextField)txtObj).getSkin()).getBehavior().traverseNext();
			}
		}
		if(txtObj instanceof CustomTextField){
//			System.out.println("entre al Custom");
			if(((CustomTextField)txtObj).getSkin() instanceof BehaviorSkinBase){
//				System.out.println("entre al behavior");
				((BehaviorSkinBase) ((CustomTextField)txtObj).getSkin()).getBehavior().traverseNext();
//				System.out.println(((BehaviorSkinBase) ((CustomTextField)txtObj).getSkin()).getBehavior());
			}
		}
		
		
	}
	
	private void searchDepositoEstoque(Integer codDeposito) {
		Task<String> tarefaCargarDepEstoque = new Task<String>() {
			@Override
			protected String call() throws Exception {
				logRetorno = DepositoEstoqueDAO.class.newInstance().getById(codDeposito);
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				depositoEstoque = (DepositoEstoque) logRetorno.getObjeto();
				if (depositoEstoque == null) {
					util.showAlert("Deposito de Estoque não encontrado no banco de dados!", "error");
				} else {
					txtCodigoDepositoEstoque.setText(String.valueOf(depositoEstoque.getCodigo()));
					txtDepositoEstoque.setText(depositoEstoque.getDescricao());
					txtCodigoSecao.requestFocus();
				}
			}

			@Override
			protected void failed() {
				stg.close();
				util.showAlert("Error de conexion", "error");
			}

			@Override
			protected void cancelled() {
				super.cancelled();
			}

		};
		Thread t = new Thread(tarefaCargarDepEstoque);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(NotaFiscalEntradaController.class, tarefaCargarDepEstoque,
				"Procurando informação do Deposito de Estoque", false);
	}
	

	private void searchTributacao(Integer codTributacao) {
		Task<String> tarefaCargarTributacaoPg = new Task<String>() {
			@Override
			protected String call() throws Exception {
				logRetorno = TributacaoDAO.class.newInstance().getById(codTributacao);
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				tributacao = (Tributacao) logRetorno.getObjeto();
				if (tributacao == null) {
					util.showAlert("Tributacao não encontrada no banco de dados!", "error");
				} else {
					txtCodTribVendaRelItens.setText(String.valueOf(tributacao.getCodigo()));
					txtTribVendaRelItens.setText(tributacao.getDescricao());
					txtCodBarraRelItens.requestFocus();
				}
			}

			@Override
			protected void failed() {
				stg.close();
				util.showAlert("Error de conexion", "error");
			}

			@Override
			protected void cancelled() {
				super.cancelled();
			}

		};
		Thread t = new Thread(tarefaCargarTributacaoPg);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(NotaFiscalEntradaController.class, tarefaCargarTributacaoPg,
				"Procurando informação da Tributação", false);
	}

	private void searchMoeda(Integer codMoeda) {
		Task<String> tarefaCargarMoedaPg = new Task<String>() {
			@Override
			protected String call() throws Exception {
				logRetorno = MoedaDAO.class.newInstance().getById(codMoeda);
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				moeda = (Moeda) logRetorno.getObjeto();
				if (moeda == null) {
					util.showAlert("Moeda não encontrada no banco de dados!", "error");
				} else {
					txtCodMoedaRelItens.setText(String.valueOf(moeda.getCodigo()));
					txtMoedaRelItens.setText(moeda.getSimbolo());
					txtCodUniRelItens.requestFocus();
				}
			}

			@Override
			protected void failed() {
				stg.close();
				util.showAlert("Error de conexion", "error");
			}

			@Override
			protected void cancelled() {
				super.cancelled();
			}

		};
		Thread t = new Thread(tarefaCargarMoedaPg);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(NotaFiscalEntradaController.class, tarefaCargarMoedaPg,
				"Procurando informação da Moeda", false);
	}

	private void searchGrade(Integer Grade) {
		// TODO Auto-generated method stub
		Task<String> tarefaCargarGradePg = new Task<String>() {
			@Override
			protected String call() throws Exception {
				logRetorno = GradeDAO.class.newInstance().getById(Integer.parseInt(txtCodGradEstoqRelItens.getText()));
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				grade = (Grade) logRetorno.getObjeto();
				if (grade == null) {
					util.showAlert("Grade não encontrado no banco de dados!", "error");
				} else {
					txtCodGradEstoqRelItens.setText(String.valueOf(grade.getCodigo()));
					txtGradeRelItens.setText(grade.getDescricao());
					txtCodTribVendaRelItens.requestFocus();
				}
			}

			@Override
			protected void failed() {
				stg.close();
				util.showAlert("Error de conexion", "error");
			}

			@Override
			protected void cancelled() {
				super.cancelled();
			}

		};
		Thread t = new Thread(tarefaCargarGradePg);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(NotaFiscalEntradaController.class, tarefaCargarGradePg,
				"Procurando informação da Grade de Estoque", false);
	}

	private void searchNCM(Integer codNCM) {
		// TODO Auto-generated method stub
		Task<String> tarefaCargarNCMPg = new Task<String>() {
			@Override
			protected String call() throws Exception {
				logRetorno = TabelaNCMDAO.class.newInstance().getById(Integer.parseInt(txtCodNCMRelItens.getText()));
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				ncm = (TabelaNCM) logRetorno.getObjeto();
				if (ncm == null) {
					util.showAlert("NCM não encontrada no banco de dados!", "error");
				} else {
					txtCodNCMRelItens.setText(ncm.getCodNCM());
					txtNCMRelItens.setText(ncm.getDescricao());
					txtCodGradEstoqRelItens.requestFocus();
				}
			}

			@Override
			protected void failed() {
				stg.close();
				util.showAlert("Error de conexion", "error");
			}

			@Override
			protected void cancelled() {
				super.cancelled();
			}

		};
		Thread t = new Thread(tarefaCargarNCMPg);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(NotaFiscalEntradaController.class, tarefaCargarNCMPg,
				"Procurando informação da NCM", false);
	}

	private void searchFabricante(Integer codFabricante) {
		// TODO Auto-generated method stub
		Task<String> tarefaCargarFabricantePg = new Task<String>() {
			@Override
			protected String call() throws Exception {
				logRetorno = FabricanteDAO.class.newInstance().getById(Integer.parseInt(txtCodFabRelItens.getText()));
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				fabricante = (Fabricante) logRetorno.getObjeto();
				if (fabricante == null) {
					util.showAlert("Fabricante não encontrado no banco de dados!", "error");
				} else {
					txtCodFabRelItens.setText(String.valueOf(fabricante.getCodigo()));
					txtFabricanteRelItens.setText(fabricante.getDescricao());
					txtCodGrupRelItens.requestFocus();
				}
			}

			@Override
			protected void failed() {
				stg.close();
				util.showAlert("Error de conexion", "error");
			}

			@Override
			protected void cancelled() {
				super.cancelled();
			}

		};
		Thread t = new Thread(tarefaCargarFabricantePg);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(NotaFiscalEntradaController.class, tarefaCargarFabricantePg,
				"Procurando informação do Fabricante", false);
	}

	private void searchSubGrupo(Integer codSubGrupo) {
		// TODO Auto-generated method stub
		Task<String> tarefaCargarSubGrupPg = new Task<String>() {
			@Override
			protected String call() throws Exception {
				logRetorno = SubGrupoDAO.class.newInstance().getById(Integer.parseInt(txtCodSubGrupRelItens.getText()));
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				subGrupo = (SubGrupo) logRetorno.getObjeto();
				if (subGrupo == null) {
					util.showAlert("Sub-Grupo não encontrado no banco de dados!", "error");
				} else {
					txtCodSubGrupRelItens.setText(String.valueOf(subGrupo.getCodigo()));
					txtSubGrupRelItens.setText(subGrupo.getDescricao());
					txtCodMoedaRelItens.requestFocus();
				}
			}

			@Override
			protected void failed() {
				stg.close();
				util.showAlert("Error de conexion", "error");
			}

			@Override
			protected void cancelled() {
				super.cancelled();
			}

		};
		Thread t = new Thread(tarefaCargarSubGrupPg);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(NotaFiscalEntradaController.class, tarefaCargarSubGrupPg,
				"Procurando informação do Sub-Grupo", false);
	}

	private void searchGrupo(Integer codGrupo) {
		// TODO Auto-generated method stub
		Task<String> tarefaCargarGrupPg = new Task<String>() {
			@Override
			protected String call() throws Exception {
				logRetorno = GrupoDAO.class.newInstance().getById(Integer.parseInt(txtCodGrupRelItens.getText()));
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				grupo = (Grupo) logRetorno.getObjeto();
				if (grupo == null) {
					util.showAlert("Grupo não encontrado no banco de dados!", "error");
				} else {
					txtCodGrupRelItens.setText(String.valueOf(grupo.getCodigo()));
					txtGrupoRelItens.setText(grupo.getDescricao());
					txtCodSubGrupRelItens.requestFocus();
				}
			}

			@Override
			protected void failed() {
				stg.close();
				util.showAlert("Error de conexion", "error");
			}

			@Override
			protected void cancelled() {
				super.cancelled();
			}

		};
		Thread t = new Thread(tarefaCargarGrupPg);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(NotaFiscalEntradaController.class, tarefaCargarGrupPg,
				"Procurando informação do Grupo", false);
	}

	private void searchUnidade(Integer codUnidade, TextField textfield) {
		// TODO Auto-generated method stub
		Task<String> tarefaCargarUnidadePg = new Task<String>() {
			@Override
			protected String call() throws Exception {
				logRetorno = UnidadeDAO.class.newInstance().getById(codUnidade);
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				
				if (textfield.equals(txtCodUniRelItens)) {
					unidade = (Unidade) logRetorno.getObjeto();
					if (unidade == null) {
						util.showAlert("Unidade não encontrada no banco de dados!", "error");
					}else{
						txtCodUniRelItens.setText(String.valueOf(unidade.getCodigo()));
						txtUnidadeRelItens.setText(unidade.getDescricao());
						txtCodUnidEmbRelItens.requestFocus();
					}
				} else {// vem do Unidade de Embalagem
					undEmbalagem = (Unidade) logRetorno.getObjeto();
					
					if (undEmbalagem == null) {
						util.showAlert("Unidade de Embalagem não encontrada no banco de dados!", "error");
					}else{
						txtCodUnidEmbRelItens.setText(String.valueOf(undEmbalagem.getCodigo()));
						txtUnidEmbRelItens.setText(undEmbalagem.getDescricao());
						txtCodNCMRelItens.requestFocus();
					}
				}
				
//				unidade = (Unidade) logRetorno.getObjeto();
//				if (unidade == null) {
//					util.showAlert("Unidade não encontrada no banco de dados!", "error");
//				} else {
//					if (textfield.equals(txtCodUniRelItens)) {
//						txtCodUniRelItens.setText(String.valueOf(unidade.getCodigo()));
//						txtUnidadeRelItens.setText(unidade.getDescricao());
//						txtCodUnidEmbRelItens.requestFocus();
//					} else {
//						undEmbalagem = unidade;
//						txtCodUnidEmbRelItens.setText(String.valueOf(unidade.getCodigo()));
//						txtUnidEmbRelItens.setText(unidade.getDescricao());
//						txtCodNCMRelItens.requestFocus();
//					}
//
//				}
			}

			@Override
			protected void failed() {
				stg.close();
				util.showAlert("Error de conexion", "error");
			}

			@Override
			protected void cancelled() {
				super.cancelled();
			}

		};
		Thread t = new Thread(tarefaCargarUnidadePg);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(NotaFiscalEntradaController.class, tarefaCargarUnidadePg,
				"Procurando informação da Unidade", false);
	}
	
	private void searchOperacao(Integer codOperacao) {
		// TODO Auto-generated method stub

		Task<String> tarefaCargarOperacao = new Task<String>() {
			@Override
			protected String call() throws Exception {
				logRetorno = OperacaoEntradaDAO.class.newInstance().getById(Integer.parseInt(txtCodigoOperacao.getText()));
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				operacao = (OperacaoEntrada) logRetorno.getObjeto();
				if (operacao == null) {
					util.showAlert("Operação de Entrada não encontrada no banco de dados!", "error");
				} else {
//					txtCodigoOperacao.setText(String.valueOf(operacao.getCodigo()));
//					txtOperacao.setText(operacao.getDescricao());
//					txtCFOP.setText(operacao.getCfop().toString());
//					txtCFOP2.setText(operacao.getCfop2().toString());
					telaConfig(operacao);
//					btnFileChooser.requestFocus();
				}
			}

			@Override
			protected void failed() {
				stg.close();
				util.showAlert("Error de conexion", "error");
			}

			@Override
			protected void cancelled() {
				super.cancelled();
			}

		};
		Thread t = new Thread(tarefaCargarOperacao);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(NotaFiscalEntradaController.class, tarefaCargarOperacao,
				"Procurando informação da Operação de Entrada", false);

	}
	
	private void searchFornecedor(Integer codFornecedor) {
		// TODO Auto-generated method stub

		Task<String> tarefaCargarFornecedor = new Task<String>() {
			@Override
			protected String call() throws Exception {
				logRetorno = FornecedorDAO.class.newInstance().getById(Integer.parseInt(txtCodigoFornecedor.getText()));
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				fornecedor = (Fornecedor) logRetorno.getObjeto();
				if (fornecedor == null) {
					util.showAlert("Fornecedor não encontrado no banco de dados!", "error");
				} else {
					txtCodigoFornecedor.setText(String.valueOf(fornecedor.getCodigo()));
					txtFornecedor.setText(fornecedor.getRazao());
					txtNoCNPJ.setText(fornecedor.getCpfCnpj());
					dtxtDataEmissao.requestFocus();
				}
			}

			@Override
			protected void failed() {
				stg.close();
				util.showAlert("Error de conexion", "error");
			}

			@Override
			protected void cancelled() {
				super.cancelled();
			}

		};
		Thread t = new Thread(tarefaCargarFornecedor);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(NotaFiscalEntradaController.class, tarefaCargarFornecedor,
				"Procurando informação do Fornecedor", false);

	}

	private void searchItem(Integer codItem) {
		// TODO Auto-generated method stub

		Task<String> tarefaCargarItemPg = new Task<String>() {
			@Override
			protected String call() throws Exception {
				logRetorno = ItemDAO.class.newInstance().getById(Integer.parseInt(txtCodItemRelItens.getText()));
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				item = (Item) logRetorno.getObjeto();
				if (item == null) {
					util.showAlert("Item não encontrado no banco de dados!", "error");
				} else {
					txtCodItemRelItens.setText(String.valueOf(item.getCodigo()));
					txtDescripcaoRelItens.setText(item.getDescricao());
					// txtDescripcaoRelItens.setText(item.getItemCodBars().iterator().next().getCodBarras());
					txtEmbCompRelItens.requestFocus();
				}
			}

			@Override
			protected void failed() {
				stg.close();
				util.showAlert("Error de conexion", "error");
			}

			@Override
			protected void cancelled() {
				super.cancelled();
			}

		};
		Thread t = new Thread(tarefaCargarItemPg);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(NotaFiscalEntradaController.class, tarefaCargarItemPg,
				"Procurando informação do item", false);

	}

	public void setRowsItemValores(BigDecimal precoDefault) {
		// ObservableList<ItemValor> valores =
		// FXCollections.<ItemValor>observableArrayList();

		ItemValor iValor = null;
		List<ItemValor> listValores = new ArrayList<ItemValor>();

		for (int i = 0; i < Util.getCompartilhamentoEntidade(EnumCompartilhamento.ITENS).size(); i++) {
			iValor = new ItemValor();

			try {
				iValor = (ItemValor) Util.class.newInstance().initializeAttribClass(new ItemValor());
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			iValor.setCodemp(Util.getCompartilhamentoEntidade(EnumCompartilhamento.ITENS).get(i));
			iValor.setVlrCusto(Util.decimalBRtoBigDecimal(4, "0,0000"));
			iValor.setVlrVendaVarejo(Util.decimalBRtoBigDecimal(4, "0,0000"));
			// iValor.setMargemLucroVarejo(Util.decimalBRtoBigDecimal(4,
			// "0,0000"));
			iValor.setAltPrecoData(LocalDateTime.of(LocalDate.of(2001, 01, 01), LocalTime.of(00, 01)));
			iValor.setAltPrecoUsuario("");
			iValor.setLocacao("");
			// iValor.setCodTributacao(0);
			iValor.setRepCodFornecedor(0);
			iValor.setRepFornecedor("");
			// iValor.setRepData(LocalDateTime.of(LocalDate.of(2001, 01, 01),
			// LocalTime.of(00, 01)));
			// iValor.setRepDcoumento(0);
			iValor.setRepQuantidade(Util.decimalBRtoBigDecimal(4, "0,0000"));
			iValor.setVlrCusto(precoDefault);
			iValor.setQtdAtual(Util.decimalBRtoBigDecimal(4, "0,0000"));
			iValor.setQtdCcusto1(Util.decimalBRtoBigDecimal(4, "0,0000"));
			iValor.setQtdCcusto2(Util.decimalBRtoBigDecimal(4, "0,0000"));
			iValor.setQtdMax(Util.decimalBRtoBigDecimal(4, "0,0000"));
			iValor.setQtdMin(Util.decimalBRtoBigDecimal(4, "0,0000"));
			listValores.add(iValor);

		}

		tbViewPrecosEmp.getItems().addAll(FXCollections.observableArrayList(listValores));
		tbViewPrecosEmp.refresh();

		// tbViewPosicaoEstoque.getItems().addAll(valores);
		// tbViewPrecosEmp.getSelectionModel().selectFirst();
		// tbViewPosicaoEstoque.getSelectionModel().selectFirst();

	}

	/**
	 * FECHAR JANELA COMPOSIÇÃO DE PREÇO
	 */
	public void closedComposicaoPreco() {
		Util.fechaTelaCadastro(anchorPaneRepItens, anchorPaneFormacaoPreco);
		tbViewPrecosEmp.refresh();
	}

	/**
	 *CONFIGURAÇÃO DA TELA AO CARREGAR A OPERAÇÃO
	 * */
	private void telaConfig(OperacaoEntrada operacao){

		if(operacao.getNfFlagXml() == 1){
		
			btnFileChooser.setDisable(false);
			btnDownloadNFe.setDisable(false);
			txtCodigoFornecedor.setEditable(false);
			txtNoNotaFiscal.setEditable(false);
			txtNoCNPJ.setEditable(false);
			dtxtDataEmissao.setEditable(false);			
			
		}else{
			btnFileChooser.setDisable(true);
			btnDownloadNFe.setDisable(true);
			txtCodigoFornecedor.setEditable(true);
			txtNoNotaFiscal.setEditable(true);
			txtNoCNPJ.setEditable(true);
			dtxtDataEmissao.setEditable(true);
			
		}	
		
		txtCodigoOperacao.setText(operacao.getCodigo().toString());
		txtOperacao.setText(operacao.getDescricao());		
		
		depositoEstoque = operacao.getDepositoEstoque() !=null ? operacao.getDepositoEstoque() : null;
		txtCodigoDepositoEstoque.setText(operacao.getDepositoEstoque() !=null ? operacao.getDepositoEstoque().getCodigo().toString() : "");
		txtCodigoDepositoEstoque.setEditable(true);
		txtDepositoEstoque.setText(operacao.getDepositoEstoque() !=null ? operacao.getDepositoEstoque().getDescricao() : "");
		
		planoConta = operacao.getPlanoConta() !=null ? operacao.getPlanoConta() : null;
		txtCodigoPlanosContas.setText(operacao.getPlanoConta() !=null ? operacao.getPlanoConta().getCodigo().toString() : "");
		txtCodigoPlanosContas.setEditable(true);
		txtPlanoContas.setText(operacao.getPlanoConta() !=null ? operacao.getPlanoConta().getDescricao() : "");
		
		portador = operacao.getPortador() !=null ? operacao.getPortador() : null;
		txtCodigoPortador.setText(operacao.getPortador() !=null ? operacao.getPortador().getCodigo().toString() : "");	
		txtCodigoPortador.setEditable(true);
		txtPortador.setText(operacao.getPortador() !=null ? operacao.getPortador().getDescricao() : "");
		
		secao = operacao.getSecao() !=null ? operacao.getSecao() : null;
		txtCodigoSecao.setText(operacao.getSecao() !=null ? operacao.getSecao().getCodigo().toString() : "");
		txtCodigoSecao.setEditable(true);
		txtSecao.setText(operacao.getSecao() !=null ? operacao.getSecao().getDescricao().toString() : "");	
		
		txtCFOP.setText(operacao !=null ? operacao.getCfop().toString() : "");
		txtCFOP2.setText(operacao !=null ? operacao.getCfop2().toString() : "");
		
		btnFileChooser.requestFocus();
		dtxtDataChegada.setEditable(true);
		util.disableDatePicker(dtxtDataEmissao, dtxtDataChegada);
		util.disableButtonCustomTextField(txtCodigoFornecedor, txtCodigoDepositoEstoque, txtCodigoPlanosContas, txtCodigoPortador, txtCodigoSecao);
				
		tbPanePrincipal.setDisable(false);


	}

	RelacaoItems relItem;
	LogRetorno logRetorno;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				txtCodigoOperacao.requestFocus();
				anchorPanePrincipal.addEventFilter(KeyEvent.KEY_PRESSED, evt -> {
					switch (evt.getCode()) {
					case F6:
						saveNFe();
						// saveFornecedor();
						// NfEntradaDAO.teste();
						break;

					default:
						break;
					}
				});
			}
		});

		Util.decimalBR(2, txtVTotalQtdComercialProd, txtVTotalProducto, txtVTotalIPI, txtVTotalICMS, txtVTotalICMSDeson,
				txtVTotalICMSST, txtVTotalFrete, txtVTotalSeguro, txtVTotalDesconto, txtVTotalDespAc,
				txtVTotalNotaFiscal);
		
		Util.customSearchTextField("right", null, txtCodigoFornecedor, txtCodigoPortador, txtCodigoDepositoEstoque, txtCodigoPlanosContas, txtCodigoSecao,
				txtCodigoOperacao, txtCodItemRelItens, txtCodUniRelItens, txtCodGrupRelItens, txtCodSubGrupRelItens,
				txtCodFabRelItens, txtCodNCMRelItens, txtCodGradEstoqRelItens, txtCodMoedaRelItens,
				txtCodUnidEmbRelItens, txtCodTribVendaRelItens);

		txtVTotalQtdComercialProd.setText(String.valueOf(0.00));
		txtVTotalIPI.setText(String.valueOf(0.00));
		txtVTotalICMS.setText(String.valueOf(0.00));
		txtVTotalICMSDeson.setText(String.valueOf(0.00));
		txtVTotalICMSST.setText(String.valueOf(0.00));
		txtVTotalProducto.setText(String.valueOf(0.00));
		txtVTotalFrete.setText(String.valueOf(0.00));
		txtVTotalSeguro.setText(String.valueOf(0.00));
		txtVTotalDesconto.setText(String.valueOf(0.00));
		txtVTotalDespAc.setText(String.valueOf(0.00));
		txtVTotalNotaFiscal.setText(String.valueOf(0.00));

		dtxtDataEmissao.setEditable(false);
		dtxtDataChegada.setEditable(false);
		util.disableDatePicker(dtxtDataEmissao);
		util.disableDatePicker(dtxtDataChegada);
		
		txtCodigoFornecedor.setEditable(false);
		txtCodigoDepositoEstoque.setEditable(false);
		txtCodigoPlanosContas.setEditable(false);
		txtCodigoSecao.setEditable(false);
		txtCodigoPortador.setEditable(false);
		txtCodigoFornecedor.setDisable(true);
		Util.disableButtonCustomTextField(txtCodigoFornecedor,txtCodigoDepositoEstoque,txtCodigoPlanosContas,txtCodigoSecao,txtCodigoPortador);

		// tbViewRelacaoProd.getSelectionModel()
		// .selectedItemProperty()
		// .addListener((observableValue, authorProps, authorProps2) -> {
		// //This works:
		// txtCodigoOperacao.textProperty().bindBidirectional(authorProps2.getCodBarEptusRelProdProperty());
		// });

		// btnSave.disableProperty().bind(Bindings.isEmpty(tbViewRelacaoProd.getItems()));
		tbViewRelacaoProd.setEditable(true);
		tbViewRelacaoProd.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (!tbViewRelacaoProd.getItems().isEmpty() && event.getCode().equals(KeyCode.ENTER)) {
				Util.openFormCadastro((AnchorPane) tabRelItens.getContent(), anchorPaneRepItens, 0);
				relItem = tbViewRelacaoProd.getSelectionModel().getSelectedItem();
				if (!relItem.getCodNCMNFeRelProd().equals("")) {
					try {
						ncm = (TabelaNCM) TabelaNCMDAO.class.newInstance().getById(relItem.getCodNCMNFeRelProd())
								.getObjeto();
						// System.out.println("-*-*-**//*-***----////*-/-/-//-///
						// "+ncm.getCodNCM() +" "+ncm.getDescricao());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (relItem.getItem() != null) {
					txtCodItemRelItens.setText(relItem.getItem().getCodigo().toString());
					txtDescripcaoRelItens.setText(relItem.getItem().getDescricao());
					txtCodFabRelItens.setText(relItem.getItem().getFabricante().getCodigo().toString());
					txtFabricanteRelItens.setText(relItem.getItem().getFabricante().getDescricao());
					txtCodGrupRelItens.setText(relItem.getItem().getGrupo().getCodigo().toString());
					txtGrupoRelItens.setText(relItem.getItem().getGrupo().getDescricao());
					txtCodSubGrupRelItens.setText(relItem.getItem().getSubGrupo().getCodigo().toString());
					txtSubGrupRelItens.setText(relItem.getItem().getSubGrupo().getDescricao());
					txtCodUniRelItens.setText(relItem.getItem().getUnidade().getCodigo().toString());
					txtUnidadeRelItens.setText(relItem.getItem().getUnidade().getDescricao());
					txtCodUnidEmbRelItens.setText(relItem.getItem().getUnidadeEmb().getCodigo().toString());
					txtUnidEmbRelItens.setText(relItem.getItem().getUnidadeEmb().getDescricao());
					txtCodMoedaRelItens.setText(relItem.getItem().getMoeda().getCodigo().toString());
					txtMoedaRelItens.setText(relItem.getItem().getMoeda().getSimbolo());
					txtCodNCMRelItens.setText(ncm != null ? ncm.getCodNCM() : "");
					txtNCMRelItens.setText(ncm != null ? ncm.getDescricao() : "");
					txtCodTribVendaRelItens.setText(
							relItem.getItem().getItemValors().iterator().next().getTributacao().getCodigo().toString());
					txtTribVendaRelItens.setText(
							relItem.getItem().getItemValors().iterator().next().getTributacao().getDescricao());
					txtCodBarraRelItens.setText(relItem.getItem().getItemCodBars().iterator().next().getCodBarras());
					txtVlrUnitario.setText(relItem.getVlrUndBrutoRelProd());
					txtVlrDesconto.setText(relItem.getVlrDescontoRelProd());
					txtVlrIPI.setText(relItem.getVlrImpIPITribVIPIRelProd());
					txtVlrICMS.setText(relItem.getVlrICMSSTRelProd());
					txtVlrICMSDeson.setText(relItem.getVlrICMSDesonRelProd());
					txtSubstituicao.setText(relItem.getVlrICMSSubRelProd());
					txtDespAcessoria.setText(relItem.getVlrDespAcessRelProd());
					txtVlrSeguro.setText(relItem.getVlrSegRelProd());
					txtEmbCompRelItens.setText(relItem.getItem().getQtdEmbCompra().toString());
					txtEmbVendaRelItens.setText(relItem.getItem().getQtdEmbVenda().toString());
					// txtVlrPIS.setText(relItem.get);
					// txtVlrCOFINS.setText(relItem.get);

					if (relItem.getItem().getItemValors().size() == 0) {
						tbViewPrecosEmp.getItems().clear();
						setRowsItemValores(BigDecimal.valueOf(0.00));
					} else {
						tbViewPrecosEmp.getItems().clear();
						// setRowsItemValores(BigDecimal.valueOf(0.00));
						tbViewPrecosEmp.getItems()
						.addAll(FXCollections.observableArrayList(relItem.getItem().getItemValors()));
					}

				} else {
					txtCodItemRelItens.requestFocus();
					txtDescripcaoRelItens.setText(relItem.getDescripcaoNFeRelProd());
					txtCFOPRelItens.setText(relItem.getCFOPRelProd());
					txtCSTRelItens.setText(relItem.getCSTICMSRelProd());
					txtEmbCompRelItens.setText(relItem.getEmbComprasRelProd());
					txtEmbVendaRelItens.setText(relItem.getEmbComprasRelProd());
					txtCodBarraRelItens.setText(relItem.getCodBarRelProd() != null ? relItem.getCodBarRelProd() : "");
					txtCodNCMRelItens.setText(ncm != null ? ncm.getCodNCM() : "");
					txtNCMRelItens.setText(ncm != null ? ncm.getDescricao() : "");
					txtVlrUnitario.setText(relItem.getVlrUndBrutoRelProd());
					txtVlrDesconto.setText(relItem.getVlrDescontoRelProd());
					txtVlrIPI.setText(relItem.getVlrImpIPITribVIPIRelProd());
					txtVlrICMS.setText(relItem.getVlrICMSSTRelProd());
					txtVlrICMSDeson.setText(relItem.getVlrICMSDesonRelProd());
					txtSubstituicao.setText(relItem.getVlrICMSSubRelProd());
					txtDespAcessoria.setText(relItem.getVlrDespAcessRelProd());
					txtVlrSeguro.setText(relItem.getVlrSegRelProd());
					// txtVlrPIS.setText(relItem.get);
					// txtVlrCOFINS.setText(relItem.get);
					tbViewPrecosEmp.getItems().clear();
					setRowsItemValores(BigDecimal.valueOf(Double.parseDouble(relItem.getVlrUndBrutoRelProd())));
				}

			}
		});

		tabRelItens.setOnSelectionChanged(evt -> {
			if (!tbViewRelacaoProd.getItems().isEmpty() && tabRelItens.isSelected()) {
				tbViewRelacaoProd.getSelectionModel().select(0);
				Util.setFocus(tbViewRelacaoProd);
			}
		});

		ObservableList<String> listCmBox = FXCollections.observableArrayList();
		listCmBox.add("Multiplicação");
		listCmBox.add("Divição");

		// tbColOperationMathRelItem.setCellFactory(ComboBoxTableCell.forTableColumn(listCmBox));
		// tbColOperationMathRelItem.setOnEditCommit(new
		// EventHandler<TableColumn.CellEditEvent<RelacaoItems,String>>() {
		//
		// @Override
		// public void handle(CellEditEvent<RelacaoItems, String> arg) {
		// // TODO Auto-generated method stub
		//// if(arg.getNewValue().equals("Multiplicação"))
		////// arg.getRowValue().setQtdReposicaoRelProd();
		//// else
		//// System.out.println("Div");
		// }
		// });

		// TABLEVIEW PRECOS DE EMPRESA
		tbColEmpPreco.setCellValueFactory(new PropertyValueFactory<ItemValor, String>("codemp"));
		tbColDtUltPreco.setCellValueFactory(new PropertyValueFactory<ItemValor, LocalDateTime>("altPrecoData"));
		tbColUsuarioPreco.setCellValueFactory(new PropertyValueFactory<ItemValor, String>("altPrecoUsuario"));
		tbColValorCusto.setCellValueFactory(new PropertyValueFactory<ItemValor, BigDecimal>("vlrCusto"));

	

		Label lblCabecalho = new Label("Cabeçalho");
		lblCabecalho.setId("lblTab");
		// stackPaneTabCab.getChildren().add(lblCabecalho);
		StackPane stackPaneTabCab = new StackPane(new Group(lblCabecalho));
		stackPaneTabCab.setId("stpTab");
		tabCabecalho.setGraphic(stackPaneTabCab);

		Label lblDetalhes = new Label("Detalhes");
		lblDetalhes.setId("lblTab");
		// stackPaneTabCab.getChildren().add(lblCabecalho);
		StackPane stackPaneTabDet = new StackPane(new Group(lblDetalhes));
		stackPaneTabDet.setId("stpTab");
		tabDetalhe.setGraphic(stackPaneTabDet);

		Label lblFaturament = new Label("Faturamento");
		lblFaturament.setId("lblTab");
		// stackPaneTabCab.getChildren().add(lblCabecalho);
		StackPane stackPaneTabFat = new StackPane(new Group(lblFaturament));
		stackPaneTabFat.setId("stpTab");
		tabFaturamento.setGraphic(stackPaneTabFat);

		Label lblProtocolo = new Label("Protocolo");
		lblProtocolo.setId("lblTab");
		// stackPaneTabCab.getChildren().add(lblCabecalho);
		StackPane stackPaneTabProt = new StackPane(new Group(lblProtocolo));
		stackPaneTabProt.setId("stpTab");
		tabProtocolo.setGraphic(stackPaneTabProt);

		Label lblInfAdic = new Label("Inf. Adicionais");
		lblInfAdic.setId("lblTab");
		// stackPaneTabCab.getChildren().add(lblCabecalho);
		StackPane stackPaneTabInfAdic = new StackPane(new Group(lblInfAdic));
		stackPaneTabInfAdic.setId("stpTab");
		tabInfAdic.setGraphic(stackPaneTabInfAdic);

		// COMPOSIÇÃO DE PREÇOS
		try {
			cpc = new ComposicaoPrecosController(this);
			anchorPaneFormacaoPreco.getChildren()
			.add(util.openWindow("/views/compras/viewComposicaoDePrecos.fxml", cpc));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		tbViewPrecosEmp.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {

			if (newSelection != null) {

				ItemValor iv = tbViewPrecosEmp.getSelectionModel().getSelectedItem();
				cpc.UpdateForm(iv);
				// CARREGA OS DADOS DA LINHA SELECIONADA OBJETO INSTANCIADO

			}
		});

		tbViewPrecosEmp.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode().equals(KeyCode.ENTER)) {

				Util.openFormCadastro(anchorPaneRepItens, anchorPaneFormacaoPreco, 0);

			}

		});

	}

	// ------------------------------------------------------------------------------
	// CLASSE DO COMBOBOX DA TABELA RELAÇÃO DE ITENS
	// --------------------------------------------------------------------------
	class CmbBox extends ComboBoxTableCell<RelacaoItems, String> {

		final ComboBox<String> cmbBox = new ComboBox<String>();
		private ObservableList<String> listCmBox = FXCollections.observableArrayList();

		public CmbBox() {
			cmbBox.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					double qtdNFe = Double.parseDouble(tbViewRelacaoProd.getItems().get(getIndex()).getQtdNFeRelProd());
					double embComp = Double
							.parseDouble(tbViewRelacaoProd.getItems().get(getIndex()).getEmbComprasRelProd());
					double vlrUnitBruto = Double
							.parseDouble(tbViewRelacaoProd.getItems().get(getIndex()).getVlrUnitBrutoNFe());

					if (cmbBox.getSelectionModel().getSelectedItem().equals("*")) {
						BigDecimal multRep = new BigDecimal(qtdNFe * embComp).setScale(4, BigDecimal.ROUND_HALF_EVEN);
						tbViewRelacaoProd.getItems().get(getIndex()).setQtdReposicaoRelProd(String.valueOf(multRep));

						BigDecimal multVlrUnitBruto = new BigDecimal(vlrUnitBruto * embComp).setScale(4,
								BigDecimal.ROUND_HALF_EVEN);
						tbViewRelacaoProd.getItems().get(getIndex())
						.setVlrUndBrutoRelProd(String.valueOf(multVlrUnitBruto));

						tbViewRelacaoProd.getItems().get(getIndex()).setTipoOperacaoMat("*");
						System.out.println("RePOSICAO = "+multRep);
						
					}
					if (cmbBox.getSelectionModel().getSelectedItem().equals("/")) {
						BigDecimal div = new BigDecimal(qtdNFe / embComp).setScale(4, BigDecimal.ROUND_HALF_EVEN);
						tbViewRelacaoProd.getItems().get(getIndex()).setQtdReposicaoRelProd(String.valueOf(div));

						BigDecimal divVlrUnitBruto = new BigDecimal(vlrUnitBruto / embComp).setScale(4,
								BigDecimal.ROUND_HALF_EVEN);
						tbViewRelacaoProd.getItems().get(getIndex())
						.setVlrUndBrutoRelProd(String.valueOf(divVlrUnitBruto));

						BigDecimal fatorConversao = new BigDecimal(embComp / qtdNFe).setScale(4,
								BigDecimal.ROUND_HALF_EVEN);
						tbViewRelacaoProd.getItems().get(getIndex()).setFatorConversao(String.valueOf(fatorConversao));
						tbViewRelacaoProd.getItems().get(getIndex()).setTipoOperacaoMat("/");
				
					}

					tbViewRelacaoProd.refresh();

				}
			});
		}

		@Override
		public void updateItem(String item, boolean empty) {
			super.updateItem(item, empty);
			if (!empty) {
				listCmBox.add("*");
				listCmBox.add("/");
				cmbBox.setItems(listCmBox);
				cmbBox.getSelectionModel().select(item);				
				cmbBox.setDisable(tbViewRelacaoProd.getItems().get(getIndex()).getItem() != null ? false : true);
				setGraphic(cmbBox);
				setText("");
			} else {
				setText(null);
				setGraphic(null);
			}
		}

	}
}
