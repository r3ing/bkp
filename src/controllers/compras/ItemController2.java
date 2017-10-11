package controllers.compras;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.management.relation.Relation;

import com.jfoenix.controls.JFXToggleButton;
import com.sun.javafx.scene.control.skin.TableHeaderRow;
import application.DadosGlobais;
import controllers.configuracoes.UsuarioController;
import controllers.utils.ConfigColumnController;
import controllers.utils.PrintExportController;
import controllers.utils.ProgressBarForm;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import org.controlsfx.control.PopOver;
import org.controlsfx.control.PopOver.ArrowLocation;
import org.controlsfx.control.textfield.CustomTextField;

import javafx.beans.binding.IntegerExpression;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.StringConverter;
import models.compras.Item;
import models.compras.ItemDAO;
import models.compras.ItemValor;
import models.compras.ItemValorDAO;
import models.configuracoes.Compartilhamento;
import models.configuracoes.NivelAcesso;
import models.configuracoes.Usuario;
import models.configuracoes.UsuarioDAO;
import tools.controls.ComboBoxFilter;
import tools.enums.EnumLogRetornoStatus;
import tools.reports.TableConfPrint;
import tools.utils.LogRetorno;
import tools.utils.Util;
import tools.xml.UserDefinedColumn;
import tools.xml.XMLTableColumns;

/**
 * CLASSE CONTROLADORA DO FORMULARIO viewItem.fxml
 * 
 * @author Eptus da Amazônia
 * @param <P>
 */
@SuppressWarnings("restriction")
public class ItemController2<P> implements Initializable {

	@FXML
	private AnchorPane anchorPanePrincipal, anchorPaneListagem, anchorPaneDetalhes, anchorPaneFilter;

	@FXML
	private ToolBar toolBarListagem, toolBarDetalhes;

	@FXML
	private Label lblNumLinhas, lblTotalLinhas;

	@FXML
	private Button btnInsertGrid, btnInsert, btnDelete, btnRefresh, btnFilter, btnPrint, btnExport, btnConfig, btnSave,
			btnCancel, btnClose;

	@FXML
	private SplitPane splitPaneFilterDept;

	@FXML
	private ComboBox<ComboBoxFilter> cboxFilterColumn;

	@FXML
	private ComboBox<ComboBoxFilter> cboxFlagAtivo;

	@FXML
	public TableView<Item> tbView;

	@FXML
	private CustomTextField txtFilterColumn;

	@FXML
	private ProgressBar pBar;

	@FXML
	private ContextMenu contextMenu = null;

	@FXML
	private ToggleButton toggleHelp;

	@FXML
	private Label lblCboxFilterColumn, lblcboxFlagAtivo, lblDetalhes, lblDescriao, lblDetCodigo, lblDetDesc;

	@FXML
	private ToolBar toolBar;

	@FXML
	private TableColumn<Item, Integer> tbcolCodigo;

	@FXML
	private TableColumn<Item, String> tbcolDescricao;

	@FXML
	private TableColumn<Item, Integer> tbcolAtivoInat;

	@FXML
	private TableColumn<?, ?> tbcolDescReduz;

	@FXML
	private TableColumn<?, ?> tbcolDescTecn;

	@FXML
	private TableColumn<?, ?> tbcolNoFab;

	@FXML
	private TableColumn<?, ?> tbcolNoOriginal;

	@FXML
	private TableColumn<?, ?> tbcolCodFab;

	@FXML
	private TableColumn<?, ?> tbcolFab;

	@FXML
	private TableColumn<?, ?> tbcolCodGrup;

	@FXML
	private TableColumn<?, ?> tbcolGrup;

	@FXML
	private TableColumn<?, ?> tbcolCodSubGrup;

	@FXML
	private TableColumn<?, ?> tbcolSubGrup;

	@FXML
	private TableColumn<?, ?> tbcolCodDep;

	@FXML
	private TableColumn<?, ?> tbcolDep;

	@FXML
	private TableColumn<?, ?> tbcolCodServLc;

	@FXML
	private TableColumn<?, ?> tbcolServLc;

	@FXML
	private TableColumn<?, ?> tbcolCodNcm;

	@FXML
	private TableColumn<?, ?> tbcolNcm;

	@FXML
	private TableColumn<?, ?> tbcolCodCest;

	@FXML
	private TableColumn<?, ?> tbcolCodFam;

	@FXML
	private TableColumn<?, ?> tbcolFam;

	@FXML
	private TableColumn<?, ?> tbcolCodMoeda;

	@FXML
	private TableColumn<?, ?> tbcolMoeda;

	@FXML
	private TableColumn<?, ?> tbcolCodUnid;

	@FXML
	private TableColumn<?, ?> tbcolUnid;

	@FXML
	private TableColumn<?, ?> tbcolCodUnidEmb;

	@FXML
	private TableColumn<?, ?> tbcolUnidEmb;

	@FXML
	private TableColumn<?, ?> tbcolQtdPallet;

	@FXML
	private TableColumn<?, ?> tbcolMedida;

	@FXML
	private TableColumn<?, ?> tbcolPesoBrutoLiq;

	@FXML
	private TableColumn<?, ?> tbcolFatorConv;

	@FXML
	private TableColumn<?, ?> tbcolQtdLitros;

	@FXML
	private TableColumn<?, ?> tbcolCompra;

	@FXML
	private TableColumn<?, ?> tbcolVenda;

	@FXML
	private TableColumn<?, ?> tbcolAtacado;

	@FXML
	private TableColumn<?, ?> tbcolDescMax;

	@FXML
	private TableColumn<?, ?> tbcolCodBarra;

	@FXML
	private TableColumn<?, ?> tbcolQtdEstoque;

	@FXML
	private TableColumn<Item, String> tbcolCodTributacao;

	@FXML
	private TableColumn<Item, String> tbcolTributacao;

	@FXML
	private TableColumn<?, ?> tbcolEnviaBalanca;

	@FXML
	private TableColumn<?, ?> tbcolObservAdicionais;
	
	@FXML
	private TabPane tabPaneItems;

	@FXML
	private Tab tabInfGerais;

	@FXML
	private Label lblCodigo;

	@FXML
	private TextField txtCodigo;

	@FXML
	private Label lblDescricao;

	@FXML
	private TextField txtDescricao;

	@FXML
	private Label lblDescRedz;

	@FXML
	private TextField txtDescRedz;

	@FXML
	private Label lblDescTec;

	@FXML
	private TextField txtDescTec;

	@FXML
	private Label lblNoFab;

	@FXML
	private TextField txtNoFab;

	@FXML
	private Label lblNoOrig;

	@FXML
	private TextField txtNoOrig;

	@FXML
	private Label lblCodFab;

	@FXML
	private FontAwesomeIconView iconNewFab;

	@FXML
	private TextField txtCodFab;

	@FXML
	private TextField txtFab;

	@FXML
	private Label lblCodGrup;

	@FXML
	private FontAwesomeIconView iconNewGrup;

	@FXML
	private TextField txtCodGrup;

	@FXML
	private TextField txtGrup;

	@FXML
	private Label lblCodSubGrup;

	@FXML
	private FontAwesomeIconView iconNewSubGrup;

	@FXML
	private TextField txtCodSubGrup;

	@FXML
	private TextField txtSubGrup;

	@FXML
	private Label lblCodDept;

	@FXML
	private FontAwesomeIconView iconNewDept;

	@FXML
	private TextField txtCodDept;

	@FXML
	private TextField txtDept;

	@FXML
	private Label lblCodServLc;

	@FXML
	private FontAwesomeIconView iconNewServLc;

	@FXML
	private TextField txtCodServLc;

	@FXML
	private TextField lblServLc;

	@FXML
	private Label lblCodNcm;

	@FXML
	private FontAwesomeIconView iconNewNcm;

	@FXML
	private TextField txtCodNcm;

	@FXML
	private TextField txtNcm;

	@FXML
	private Label lblCodCest;

	@FXML
	private TextField txtCodCest;

	@FXML
	private Label lblCodFam;

	@FXML
	private FontAwesomeIconView iconNewFam;

	@FXML
	private TextField txtCodFam;

	@FXML
	private TextField txtFam;

	@FXML
	private Label lblCodMoeda;

	@FXML
	private FontAwesomeIconView iconNewMoeda;

	@FXML
	private TextField txtCodMoeda;

	@FXML
	private TextField txtMoeda;

	@FXML
	private Label lblCodUnid;

	@FXML
	private FontAwesomeIconView iconNewUnid;

	@FXML
	private TextField txtCodUnid;

	@FXML
	private TextField txtUnid;

	@FXML
	private Label lblCodUnidEmb;

	@FXML
	private FontAwesomeIconView iconNewUnidEmb;

	@FXML
	private TextField txtCodUnidEmb;

	@FXML
	private TextField txtUnidEmb;

	@FXML
	private Label lblQtdPallet;

	@FXML
	private TextField txtQtdPallet;

	@FXML
	private Label lblMedida;

	@FXML
	private TextField txtMedida;

	@FXML
	private Label lblPreso;

	@FXML
	private TextField txtPresoBruto;

	@FXML
	private TextField txtPresoLiquido;

	@FXML
	private Label lblFatorConv;

	@FXML
	private TextField txtFatorConv;

	@FXML
	private Label lblQtdLitros;

	@FXML
	private TextField txtQtdLitros;

	@FXML
	private Label lblCompra;

	@FXML
	private TextField txtCompra;

	@FXML
	private Label lblVenda;

	@FXML
	private TextField txtVenda;

	@FXML
	private Label lblAtacado;

	@FXML
	private TextField txtAtacado;

	@FXML
	private Label lblDesconMaximo;

	@FXML
	private TextField txtDesconMaximo;

	@FXML
	private Tab tabPosicEstoque;

	@FXML
	private TextField txtUn;

	@FXML
	private Label lblUn;

	@FXML
	private Label lblCusto1;

	@FXML
	private TextField txtCusto1;

	@FXML
	private Label lblCusto2;

	@FXML
	private TextField txtCusto2;

	@FXML
	private Label lblSaldoActual;

	@FXML
	private Label lblDadosUltRep;

	@FXML
	private Label lblDataUltRep;

	@FXML
	private TextField txtDataUltRep;

	@FXML
	private Label lblNoDocUltRep;

	@FXML
	private TextField txtNoDocUltRep;

	@FXML
	private Label lblQtdUltRep;

	@FXML
	private TextField txtQtdUltRep;

	@FXML
	private TextField txtCodFornUltRep;

	@FXML
	private TextField txtCodFornUltRep2;

	@FXML
	private Label lblCodFornUltRep;

	@FXML
	private TextField txtQtdBalan;

	@FXML
	private Label lblDatasUltBal;

	@FXML
	private TextField txtDatasUltBal;

	@FXML
	private Label lblQtdBalan;

	@FXML
	private Label lblDadosUltBal;

	@FXML
	private Label lblDadosUltRep1;

	@FXML
	private Label lblLocacao;

	@FXML
	private TextField txtLocacao;

	@FXML
	private Label lblQtdMinima;

	@FXML
	private TextField txtQtdMinima;

	@FXML
	private Label lblQtdMaxima;

	@FXML
	private TextField txtQtdMaxima;

	@FXML
	private Label lblCodAssistencia1;

	@FXML
	private FontAwesomeIconView iconCompra;

	@FXML
	private TextField txtCompra1;

	@FXML
	private Label lblDevVenda;

	@FXML
	private FontAwesomeIconView iconDevVenda;

	@FXML
	private TextField txtDevVenda;

	@FXML
	private TextField txtDevVenda2;

	@FXML
	private Label lblDevComp;

	@FXML
	private FontAwesomeIconView iconDevComp;

	@FXML
	private TextField txtDevComp;

	@FXML
	private TextField txtDevComp2;

	@FXML
	private Label lblRemesaEnt;

	@FXML
	private FontAwesomeIconView iconRemesaEnt;

	@FXML
	private TextField txtRemesaEnt;

	@FXML
	private TextField txtRemesaEnt2;

	@FXML
	private FontAwesomeIconView iconVenda;

	@FXML
	private TextField txtVenda2;

	@FXML
	private Label lblRemesaSai;

	@FXML
	private FontAwesomeIconView iconRemesaSai;

	@FXML
	private TextField txtRemesaSai;

	@FXML
	private TextField txtRemesaSai2;

	@FXML
	private Tab tabDadosAdicionais;

	@FXML
	private JFXToggleButton tgbtn14tabVen;

	@FXML
	private Label lblCodAssistencia11;

	@FXML
	private Label lblCompra1;

	@FXML
	private TextField txtCompra11;

	@FXML
	private Label lblDevVenda1;

	@FXML
	private TextField txtDevVenda1;

	@FXML
	private TextField txtDevVenda21;

	@FXML
	private Label lblDevComp1;

	@FXML
	private TextField txtDevComp1;

	@FXML
	private TextField txtDevComp21;

	@FXML
	private Label lblRemesaEnt1;

	@FXML
	private TextField txtRemesaEnt1;

	@FXML
	private TextField txtRemesaEnt21;

	@FXML
	private Label lblVenda1;

	@FXML
	private TextField txtVenda1;

	@FXML
	private TextField txtVenda21;

	@FXML
	private Label lblRemesaEnt11;

	@FXML
	private TextField txtRemesaEnt11;

	@FXML
	private TextField txtRemesaEnt211;

	@FXML
	private Tab tabDadosAdicionais2;

	@FXML
	private Tab tabDadosVeiculo;

	// ------------------- ELEMENTOS CONTIDOS NO FORMULARIO FXML
	// ------------------------

	// ATRIBUTOS DA CLASSE
	private ObservableList<Item> listaRegistros = null;
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

	// OBJETOS INSTACIADOS NA CLASSE
	private Item entidadeModel = new Item();
	private ItemDAO entidadeDao = new ItemDAO();
	private Locale locale = new Locale(DadosGlobais.idioma.toLowerCase());
	private ResourceBundle resourceBundle = ResourceBundle.getBundle("translate/eptus", locale);
	private Util util = new Util();
	private PopOver popOver = new PopOver();
	private static Stage stg;

	// CONSTRUTOR PADRAO DA CLASSE
	public ItemController2() {

	}

	// CONSTRUTOR GENERICO DA CLASSE ONDE É PASSADO OS NIVEIS DE ACESSO DO
	// FORMULARIO E AS EMPRESAS COMPARTILHADAS PARA AQUELE REGISTRO
	public ItemController2(NivelAcesso nivAcesso) {
		this.nivAcessoPermissao = nivAcesso;
	}
	String fieldFiltro = null;
	String filtro = null;
	
	public ItemController2(NivelAcesso nivAcesso,String fieldFiltro, String filtro) {
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
			entidadeModel = new Item();
			btnInsert.setDisable(true);
			btnSave.setDisable(false);
			btnCancel.setDisable(false);
			txtCodigo.setDisable(true);
			txtCodigo.setText("+1");
			txtDescricao.clear();

			// animaFadeInPane();
			Util.openFormCadastro(anchorPaneListagem, anchorPaneDetalhes, 0);
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
			entidadeModel = new Item();
			btnInsert.setDisable(true);
			btnSave.setDisable(false);
			btnCancel.setDisable(false);
			txtCodigo.setDisable(true);
			txtCodigo.setText("+1");
			txtDescricao.clear();
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
		if (tbView.getItems().size() > 0) {
			for (int i = 0; i < tbView.getItems().size(); i++) {

//				if (tbView.getItems().get(i).getCodigo().toString().equals(txtCodigo.getText())) {
//					tbView.getSelectionModel().select(i);
//					tbView.scrollTo(i);
//
//					break;
//				}
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
				tarefaConsulta("filter", true);
			}
		} else {
			tarefaConsulta("filter", true);
		}
	}

	@FXML
	/**
	 * AÇÃO DO BOTAO SALVAR PRESENTE NA ABA DETALHES (btnSave) - ATALHO F6
	 */
	void actionBtnSave(ActionEvent event) {

		if (nivAcessoPermissao.getFlagUpdate().equals(1)) {

			if (!Util.noEmpty(txtDescricao)) {

				entidadeModel.setDescricao(txtDescricao.getText());

				if (txtCodigo.isDisable() && txtCodigo.getText().equals("+1")) {

					Task<String> TarefaRefresh = new Task<String>() {

						LogRetorno logRet = new LogRetorno();

						@Override
						protected String call() throws Exception {
							
							logRet = entidadeDao.inserir(entidadeModel);
							
							return "-";
						}

						@Override
						protected void succeeded() {
							stg.close();
							pBar.setProgress(1);
							if (logRet.getStatus().equals(EnumLogRetornoStatus.SUCESSO)) {								
								txtCodigo.setText(String.valueOf(logRet.getLastRecord()));								
								btnSave.setDisable(true);
								btnCancel.setDisable(true);
								btnInsert.setDisable(false);
								txtCodigo.setDisable(false);								
								atualizaTbView("INSERT");								
								txtCodigo.requestFocus();
							} else {
								util.showAlert(logRet.getMsg(), "error");

							}
						}

						@Override
						protected void failed() {
							stg.close();
							util.tratamentoExcecao(exceptionProperty().getValue().toString(),
									"[ ItemController.actionBtnSave() - INSERT ]");
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
							resourceBundle.getString("infInsertRegistro"), false);
					pBar.setProgress(-1);

				} else

//				if (entidadeModel.getId().getCodigo().toString().equals(txtCodigo.getText())) {
					if (entidadeModel.getCodigo().toString().equals(txtCodigo.getText())) {
					Task<String> TarefaRefresh = new Task<String>() {

						LogRetorno logRet = new LogRetorno();

						@Override
						protected String call() throws Exception {

							logRet = entidadeDao.update(entidadeModel);

							return "-";
						}

						@Override
						protected void succeeded() {
							stg.close();
							pBar.setProgress(1);

							if (logRet.getStatus().equals(EnumLogRetornoStatus.SUCESSO)) {			
								btnSave.setDisable(true);
								btnCancel.setDisable(true);
								btnInsert.setDisable(false);
								txtCodigo.setDisable(false);
								atualizaTbView("UPDATE");
								txtCodigo.requestFocus();
							} else {
								util.showAlert(logRet.getMsg(), "error");
							}

						}

						@Override
						protected void failed() {
							stg.close();
							util.tratamentoExcecao(exceptionProperty().getValue().toString(),
									"[ ItemController.actionBtnSave() - UPDATE ]");
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
							resourceBundle.getString("infSaveChange"), false);
					pBar.setProgress(-1);

				}

				else {
					carregaDadosDetalhe(false, Integer.valueOf(txtCodigo.getText()), false);
				}
			}
		} else {
			util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
		}

	}

	@FXML
	/**
	 * AÇÃO DO BOTAO CANCELAR PRESENTE NA ABA DETALHES (btnCancel) - ATALHO F8
	 */
	void actionBtnCancel(ActionEvent event) {

		Util.setDefaultStyle(txtCodigo, txtDescricao);
		Util.limpar(txtCodigo, txtDescricao);
		carregaDadosDetalhe(true, null, false);

	}

	@FXML
	/**
	 * AÇÃO DO BOTAO CONFIGURAÇÃO DO GRID PRESENTE NA ABA LISTAGEM (btnConfig)
	 */
	void actionBtnConfig(ActionEvent event) {

		try {
			stg = new Stage();
			Scene scene = new Scene(util.openWindow("/views/utils/viewConfigColumns.fxml",
					new ConfigColumnController(ItemController2.class, tbView, "Grid-Cad-Items")));
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
	 * AÇÃO DO BOTAO EXCLUIR PRESENTE NA ABA LISTAGEM (btnDelete) ATALHO - F7
	 */
	void actionBtnDelete(ActionEvent event) {

		if ((entidadeModel.getFlagAtivo().equals(1) && nivAcessoPermissao.getFlagDisable().equals(1))
				|| (entidadeModel.getFlagAtivo().equals(0) && nivAcessoPermissao.getFlagEnable().equals(1))) {

			if (util.showAlert(DadosGlobais.resourceBundle.getString("alertConfirmOprExcluir") + " "
					+ (entidadeModel.getFlagAtivo().equals(1) ? DadosGlobais.resourceBundle.getString("oprExcluir")
							: DadosGlobais.resourceBundle.getString("oprAtivar"))
					+ " " + DadosGlobais.resourceBundle.getString("alertConfirmOprExcluirEntidad"), "confirmation")) {

				entidadeModel.setFlagAtivo(entidadeModel.getFlagAtivo().equals(1) ? 0 : 1);

				Task<String> tarefaCargaPg = new Task<String>() {
					@Override
					protected String call() throws Exception {

						entidadeDao.excluir(entidadeModel);

						return "-";

					}

					@Override
					protected void succeeded() {
						stg.close();
						pBar.setProgress(1);
						atualizaTbView("DELETE");

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
				stg = ProgressBarForm.showProgressBar(ItemController2.class, tarefaCargaPg,
						tbView.getSelectionModel().getSelectedItem().getFlagAtivo().equals(1)
								? resourceBundle.getString("infoActiReg") : resourceBundle.getString("infoExcRegis"),
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
			splitPaneFilterDept.setDividerPositions(1);
			SplitPane.setResizableWithParent(anchorPaneFilter, Boolean.FALSE);
			txtFilterColumn.requestFocus();
		}

		else {
			splitPaneFilterDept.setDividerPositions(0);
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
			tarefaConsulta("all", true);
			cboxFlagAtivo.getSelectionModel().selectFirst();

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

	/**
	 * EVENTO KEYPRESSED NO CAMPO DE BUSCA DE REGISTROS (TxtFilterColumn) AO
	 * PRESSIONAR O ENTER EFETUA A BUSCA NO BANCO DE DADOS
	 */
	@FXML
	void keyPressedTxtFilterColumn(KeyEvent event) throws NoSuchFieldException, SecurityException {

		if (event.getCode().equals(KeyCode.TAB) && tbView.getItems().size() > 0
				&& tbView.getItems().get(0).getCodigo() != null) {
//				&& tbView.getItems().get(0).getId().getCodigo() != null) {
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
//					if (cellValue.toLowerCase().contains(txtFilterColumn.textProperty().get().toLowerCase())) {
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

		TreeItem<String> itemCol = new TreeItem<String>(resourceBundle.getString("help.active.inactive"));
		itemCol.setExpanded(true);
		TreeItem<String> itemCol0 = new TreeItem<String>("1 - " + resourceBundle.getString("help.active"));
		TreeItem<String> itemCol1 = new TreeItem<String>("0 - " + resourceBundle.getString("help.inactive"));
		itemCol.getChildren().addAll(itemCol0, itemCol1);

		TreeView<String> tree = new TreeView<String>(itemCol);
		tree.setPrefWidth(180);
		tree.setPrefHeight(100);
		tree.autosize();

		Text title = new Text(resourceBundle.getString("help"));
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
	 * Método que preenche o formulario de edição dos dados do Usuário
	 * 
	 * @param lastRegistro
	 *            TRUE -> INDICA QUE O REGISTRO A SER CARREGADO É O ULTIMO
	 *            CÓDIGO EXISTENTE
	 * @param codigo
	 *            CODIGO USADO NO FILTRO DA BUSCA DO REGISTRO
	 * @param flagTabListagem
	 *            TRUE - > DESABILITA A TABLISTAGEM FALSE -> HABILITA A
	 *            TABLISTAGEM
	 * @param FlagClone
	 *            TRUE -> INDICA QUE O REGISTRO VAI SER DUPLICADO
	 */
	public void carregaDadosDetalhe(boolean flagLastRegistro, Integer codigo, boolean flagDuplica) {

		if ((!flagLastRegistro && codigo > 0) || (flagLastRegistro)) {

			Task<String> TarefaRefresh = new Task<String>() {
				LogRetorno logRet = new LogRetorno();

				@Override
				protected String call() throws Exception {

					if (!flagLastRegistro) {

						logRet = entidadeDao.getById(codigo.intValue());

					} else {

						entidadeModel = entidadeDao.getLast();
					}
					return "-";
				}

				@Override
				protected void succeeded() {
					stg.close();
					pBar.setProgress(1);

					entidadeModel = (Item) logRet.getObjeto();

					if (entidadeModel != null) {

						if (logRet.getStatus().equals(2))
							util.alertException(logRet.getMsg(), "", false);

						txtDescricao.setText(entidadeModel.getDescricao());
						txtDescricao.selectEnd();

						if (flagDuplica) {
							txtCodigo.setText("+1");
							btnInsert.setDisable(true);
							txtCodigo.setDisable(true);
							btnCancel.setDisable(false);
							btnSave.setDisable(false);
							txtDescricao.requestFocus();
						} else {

//							txtCodigo.setText(String.valueOf(entidadeModel.getId().getCodigo()));
							txtCodigo.setText(String.valueOf(entidadeModel.getCodigo()));

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
						util.limpar(txtDescricao, txtCodigo);
					}

				}

				@Override
				protected void failed() {
					stg.close();
					util.tratamentoExcecao(exceptionProperty().getValue().toString(),
							"[ ItemController.carregaDadosDetalhe() ]");
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
					DadosGlobais.resourceBundle.getString("alertErrorCodigoNull"), false);
			pBar.setProgress(-1);

		} else {
			util.showAlert(DadosGlobais.resourceBundle.getString("alertErrorCodigoNull"), "error");
			util.limpar(txtDescricao);
			txtCodigo.requestFocus();
		}
	}

	/**
	 * METODO QUE CRIA UMA TAREFA PARA ATUALIZAR AS LINHAS DO TABLEVIEW QUE
	 * SOFRERAM ALTERAÇÃO
	 * 
	 * @param tipoConsulta
	 *            INSERT -> EM CASO DE INSERCAO DE REGISTRO NOVO CRIA O REGISTRO
	 *            NA LISTA UPDATE -> EM CASO DE UMA ALTERACAO DE UM REGISTRO QUE
	 *            JÁ EXISTE, ALTERA OS VALROES DA LINHA NO TABLEVIEW DELETE ->
	 *            EM CASO DE EXCLUSAO DE UM REGISTRO, ALTERA OS VALROES DA LINHA
	 *            NO TABLEVIEW
	 */
	public void atualizaTbView(String flagOperacao) {

		Task<String> TarefaAtualiza = new Task<String>() {
			@Override
			protected String call() throws Exception {

				if (flagOperacao.equals("INSERT")) {
					if (listaRegistros != null)
						listaRegistros.add(entidadeModel);
					else {
						listaRegistros = FXCollections.observableArrayList();
						listaRegistros.add(entidadeModel);
					}
				} else if (flagOperacao.equals("UPDATE")) {

					if (tbView.getItems().size() == 0) {
						listaRegistros = FXCollections.observableArrayList();
						listaRegistros.add(entidadeModel);
					} else {
						for (int i = 0; i < tbView.getItems().size(); i++) {
							if (tbView.getItems().get(i).getCodigo().equals(entidadeModel.getCodigo())) {
//							if (tbView.getItems().get(i).getId().getCodigo().equals(entidadeModel.getId().getCodigo())) {
								listaRegistros.set(i, entidadeModel);
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
						"[ ItemController.atualizaTbView() ]");
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
				resourceBundle.getString("infAtualizaTbView"), false);
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
	public void tarefaConsulta(String tipoConsulta, boolean flagRefresh) {

		Task<String> TarefaRefresh = new Task<String>() {
			@Override
			protected String call() throws Exception {
				// TIPOCONSULTA:
				// ALL(all) TRAS TODOS OS DADOS
				// FILTER(filter) FITRA A CONSULTA DE ACORDO COM OS VALORES
				// PREENCHIDOS NO FORMULARIO
				if (tipoConsulta.equals("all")) {

					// CONSULTA TODOS OS USUARIOS ATIVOS

					listaRegistros = FXCollections
					
							.observableArrayList(entidadeDao.getList(paramFlagAtivo));
					
//					for (int j = 0; j < listaRegistros.size(); j++) {
//						
//					
//					for (int i = 0; i < listaRegistros.get(j).getItemValors().size(); i++) {
//						
//						//	System.out.println(listaRegistros.get(j).getItemValors().get(i).getCodTributacao());	
//					
//					}}
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
//				if (tbView.getItems().size() > 0) {
//					tbView.getSelectionModel().select(0);
//					tbView.requestFocus();
//				} else
//					txtFilterColumn.requestFocus();
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
		stg = ProgressBarForm.showProgressBar(UsuarioController.class, TarefaRefresh,
				resourceBundle.getString("infConsulRegist"), false);
		pBar.setProgress(-1);

	}

	/**
	 * METODO QUE FAZ A TRADUÇAO DOS ELEMENTOS CONTINDOS NO FORMULARIO
	 */
	public void setIdioma() {

		btnInsertGrid.setTooltip(new Tooltip(resourceBundle.getString("toolTipInsert")));
		btnRefresh.setTooltip(new Tooltip(resourceBundle.getString("toolTipRefresh")));
		btnDelete.setTooltip(new Tooltip(resourceBundle.getString("toolTipExcluir")));
		btnFilter.setTooltip(new Tooltip(resourceBundle.getString("toolTipFilter")));
		btnPrint.setTooltip(new Tooltip(resourceBundle.getString("toolTipPrint")));
		btnConfig.setTooltip(new Tooltip(resourceBundle.getString("toolTipConfig")));
		toggleHelp.setTooltip(new Tooltip(resourceBundle.getString("toolTipHelp")));

		btnInsert.setTooltip(new Tooltip(resourceBundle.getString("toolTipInsert")));
		btnSave.setTooltip(new Tooltip(resourceBundle.getString("toolTipSave")));
		btnCancel.setTooltip(new Tooltip(resourceBundle.getString("toolTipCancel")));

		lblCboxFilterColumn.setText(resourceBundle.getString("lblCboxFilterColumn"));
		lblcboxFlagAtivo.setText(resourceBundle.getString("lblcboxFlagAtivo"));
		lblTotalLinhas.setText(resourceBundle.getString("lblTotalLinhas"));
		// lblDetCodigo.setText(resourceBundle.getString("lblDetCodigo"));
		// lblDetDesc.setText(resourceBundle.getString("lblDetDesc"));

		tbcolCodigo.setText(resourceBundle.getString("tbColCodigo"));
		tbcolDescricao.setText(resourceBundle.getString("tbColDescricao"));
		tbcolAtivoInat.setText(resourceBundle.getString("tbColAtivoInat"));

		tbView.setPlaceholder(new Label(resourceBundle.getString("tbView")));

	}

	/**
	 * METODO PARA POPULAR O COMBOBOX DE TIPOS DE FILTRO (cboxFilterColumn)
	 */
	public void populaCboxFilterColumn() {
		// 2 Tipo de Busca Contida, 1 Tipo de Busca Exata
		listComboBoxFilter.add(new ComboBoxFilter(resourceBundle.getString("codigo"), 1, "codigo"));
		listComboBoxFilter.add(new ComboBoxFilter(resourceBundle.getString("descricao"), 2, "descricao"));

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
	public void populaCboxFlagAtivo() {
		ObservableList<ComboBoxFilter> list = FXCollections.observableArrayList();
		list.add(new ComboBoxFilter(resourceBundle.getString("ativo"), 1, "1"));
		list.add(new ComboBoxFilter(resourceBundle.getString("inativo"), 1, "0"));
		list.add(new ComboBoxFilter(resourceBundle.getString("ativo.inativo"), 1, "2"));
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

	// ----MÉTODOS RESPONSAVEIS POR CONFIGURAR AS COLUNAS DO TABLEVIEW
	// -----------
	// -----E GRAVAR AS CONFIGURACOES NO ARQUIVO XML PARA CADA USUARIO
	// ------------

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

		xml.WriteXMLColumns(userDefinedColumnList, "Grid-Cad-Items");

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	/**
	 * Método que configura as colunas do tableview de acordo configuracões do
	 * usuário definiu na tela de configuração das colunas.
	 * 
	 * @param visibleColumns
	 *            List of columns defined by user.
	 * @param table
	 *            Instance of the entidadeModel tableView.
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

		ArrayList<UserDefinedColumn> userDefinedColumns = xmlTableColumns.ReadXMLColumns("Grid-Cad-Items");

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
	@SuppressWarnings({ "rawtypes" })
	/**
	 * Show the form to configure the actions of the report, print and export to
	 * different formats.
	 * 
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	public void printExportShow() throws NoSuchFieldException, SecurityException {

		// validar qur atabla contenga dados y k tenaga al menos una columna

		for (TableColumn column : tbView.getVisibleLeafColumns()) {

			Object type = Item.class
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
					new PrintExportController(tbView, tableShowPrintList, "Item", pBar, stg)));

			stg.setScene(scene);
			stg.initStyle(StageStyle.TRANSPARENT);
			stg.initModality(Modality.APPLICATION_MODAL);
			stg.show();
		} catch (IOException e) {
			util.tratamentoExcecao(e.getMessage().toString(), "[ ItemController.printExportShow() ]");
		}

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

		anchorPaneDetalhes.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
			if (event.getCode() == KeyCode.ENTER) {

				KeyEvent newEvent = new KeyEvent(null, null, KeyEvent.KEY_PRESSED, "", "\t", KeyCode.TAB,
						event.isShiftDown(), event.isControlDown(), event.isAltDown(), event.isMetaDown());

				Event.fireEvent(event.getTarget(), newEvent);
				event.consume();

			}
		});

		// CONFIGURA PAINEIS INICIAIS
		anchorPaneListagem.setVisible(true);
		anchorPaneDetalhes.setVisible(false);

		Util.customSearchTextField("right",null, txtFilterColumn);

		// CHAMADA A FUNÇÃO QUE CONFIGURA O TABLEVIEW DE ACORDO COM O ARQUIVO
		// XML GERADO PARA CADA USUARIO DENTRO DA PASTA C:/EptusAM/System.Grd
		configTableView();

		// CHAMADA A FUNÇÃO QUE FAZ A TRADUÇÃO DO FORMULARIO DE ACORDO COM AS
		// CONFIGURAÇÕES DO USUÁRIO
		setIdioma();

		// CHAMADA A FUNÇÃO QUE SETA O ESTILO DEFAULT AOS CAMPOS TEXTFIELDS
		util.setKeyPressDefaultStyles(txtDescricao, txtCodigo, txtFilterColumn);

		// CHAMADA A FUNÇÃO QUE SETA O ESTILO DEFAULT AOS CAMPOS TEXTFIELDS
		util.onlyNumbers(txtCodigo);

		// CHAMADA A FUNÇÃO QUE SETA O ESTILO DEFAULT AOS CAMPOS TEXTFIELDS
		util.onlyAlphanumeric(txtFilterColumn, txtDescricao, txtDescRedz, txtDescTec, txtNoFab, txtNoOrig, txtCodCest);

		// CHAMADA A FUNÇÃO QUE LIMITA O NUMERO DE CARACTERES QUE PODEM SER
		// DIGITADOS NOS CAMPOS TEXTFIELDS
		util.maxCharacters(8, txtCodigo);
		util.maxCharacters(45, txtDescricao);

		// CHAMADA A FUNÇÃO QUE DEFINE OS CAMPOS TEXTFIELDS QUE SERAO
		// CAPITALIZADOS(LETRAS MAÍUSCULAS)
		util.whriteUppercase(txtFilterColumn, txtDescricao, txtDescRedz, txtDescTec, txtNoFab, txtNoOrig, txtCodCest);

		Util.mascaraNumero(txtQtdPallet);

		// CHAMADA A FUNÇÃO PARA POPULAR OS COMBOBOX CONTINDOS NO FORMULARIO
		populaCboxFilterColumn();
		populaCboxFlagAtivo();

		// CHAMADA A FUNÇÃO PARA SETAR O FOCO INICIAL DO FORMULÁRIO
		util.setFocus(txtFilterColumn);

		Util.setNextFocus(txtDescricao, txtDescRedz, txtDescTec, txtNoFab, txtNoOrig, txtCodCest, txtQtdPallet,
				txtMedida, txtPresoBruto, txtPresoLiquido, txtFatorConv, txtQtdLitros, txtCompra, txtVenda, txtAtacado,
				txtDesconMaximo);

		// CONFIGURA AS COLUNAS EXISTENTES NO TABLEVIEW, ASSOCIA A COLUNA DO
		// FXML AO ATRIBUTO EXISTENTE NA CLASSE DO MODEL
		 tbcolCodigo.setCellValueFactory(new PropertyValueFactory<Item, Integer>("codigo"));
		 tbcolDescricao.setCellValueFactory(new PropertyValueFactory<Item, String>("descricao"));
     	 tbcolAtivoInat.setCellValueFactory(new PropertyValueFactory<Item, Integer>("flagAtivo"));
		
		 tbcolTributacao.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Item, String>, ObservableValue<String>>() {

	            @Override
	            public ObservableValue<String> call(TableColumn.CellDataFeatures<Item, String> p) {
	              //SOLUÇÃO É ESTA LINHA                
	              return new ReadOnlyObjectWrapper<>(p.getValue().getItemValors().get(0).getCodTributacao().toString());
	            }
	        });
		 
		 tbcolCodTributacao.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Item, String>, ObservableValue<String>>() {

	            @Override
	            public ObservableValue<String> call(TableColumn.CellDataFeatures<Item, String> p) {
	              //SOLUÇÃO É ESTA LINHA                
	              return new ReadOnlyObjectWrapper<>(p.getValue().getItemCodbars().size() > 0 ? p.getValue().getItemCodbars().iterator().next().getCodBarras() : null);
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
				entidadeModel = tbView.getSelectionModel().getSelectedItem();
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

		// EVENTO DE CLICAR E ARRASTAR AS COLUNAS DO TABLEVIEW, AO ARRASTAR OU
		// REDIMENSIONAR OS VALORES SAO GRAVADOS NO ARQUIVO XML
		tbView.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
			if (event.getTarget() instanceof TableHeaderRow || event.getTarget() instanceof Rectangle)
				saveColumnSettings();
		});

		tbView.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode().equals(KeyCode.ENTER)
					&& tbView.getSelectionModel().getSelectedItem().getFlagAtivo().equals(1)) {
				carregaDadosDetalhe(false, tbView.getSelectionModel().getSelectedItem().getCodigo(), false);
//				carregaDadosDetalhe(false, tbView.getSelectionModel().getSelectedItem().getId().getCodigo(), false);
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
				carregaDadosDetalhe(false, tbView.getSelectionModel().getSelectedItem().getCodigo(), false);
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
							carregaDadosDetalhe(false, tbView.getSelectionModel().getSelectedItem().getCodigo(), false);
//									Integer.toString(tbView.getSelectionModel().getSelectedItem().getId().getCodigo()));
//							carregaDadosDetalhe(false, tbView.getSelectionModel().getSelectedItem().getId().getCodigo(), false);
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
							DadosGlobais.resourceBundle.getString("actionRastrearProduto"));
					itemRastreiaProd.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent e) {

						}
					});

					// ITEM 4 DO MENU DE AÇOES - DUPLICAR CADASTRO
					MenuItem itemDuplica = new MenuItem(DadosGlobais.resourceBundle.getString("actionDuplicar"));
					itemDuplica.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent e) {
							carregaDadosDetalhe(false, tbView.getSelectionModel().getSelectedItem().getCodigo(), true);
//							carregaDadosDetalhe(false, tbView.getSelectionModel().getSelectedItem().getId().getCodigo(), true);
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
//						if (!btnInsert.isPressed() && !btnClose.isPressed())
//							carregaDadosDetalhe(false, Integer.valueOf(txtCodigo.getText()), false);

					}
				}
			}

		});

		// MÉTODO QUE CONVERTE OS VALORES DA COLUNA FLAGATIVO EM TEXTO
		// CASO FLAGATIVO = 0 MOSTRA INATIVO E FLAGATIVO = 1 MOSTRA ATIVO
		// tbColAtivoInat.setCellFactory(col -> new TableCell<Item, Integer>() {
		// @Override
		// public void updateItem(Integer flagAtivo, boolean empty) {
		// super.updateItem(flagAtivo, empty);
		// if (empty) {
		// setText(" ");
		// } else {
		// if (flagAtivo == null)
		// setText(null);
		// else if (flagAtivo.equals(0)){
		// setText(String.format(DadosGlobais.resourceBundle.getString("inativo"),
		// flagAtivo));
		// }
		// else if (flagAtivo.equals(1))
		// setText(String.format(DadosGlobais.resourceBundle.getString("ativo"),
		// flagAtivo));
		// }
		// }
		// });

		// TECLAS DE ATALHOS PARA O FORMULARIO
		// F2 - CONSULTAR | F4 - CARREGAR TODOSO OS DADOS REFRESH| F5 - INSERIR
		// NOVO REGISTRO | F6 - GRAVAR | DEL - EXCLUIR |F7 - CANCELAR
		// CTRL+P IMPRIMIR
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
				if (!btnSave.isDisable())
					actionBtnSave(null);

				break;

			case F7:
				if (!btnCancel.isDisable())
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
					tabPaneItems.getSelectionModel().select(0);
					txtCodigo.requestFocus();
				}

				break;
			default:
				break;
			}
		});
		
		if(fieldFiltro != null && filtro != null){
			System.out.println("busca produtos filtra "+fieldFiltro+" = "+filtro);
		}
	}
}