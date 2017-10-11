package controllers.compras;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXToggleButton;
import com.sun.javafx.scene.control.skin.TableHeaderRow;
import application.DadosGlobais;
import controllers.configuracoes.UsuarioController;
import controllers.utils.ComboBoxIndicadorIE;
import controllers.utils.ConfigColumnController;
import controllers.utils.PrintExportController;
import controllers.utils.ProgressBarForm;
import controllers.utils.SearchController;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.PopOver.ArrowLocation;
import org.controlsfx.control.textfield.CustomTextField;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Element;

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
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import models.compras.Fornecedor;
import models.compras.FornecedorDAO;
import models.configuracoes.NivelAcesso;
import models.vendas.Cidade;
import models.vendas.CidadeDAO;
import tools.controls.ComboBoxFilter;
import tools.enums.EnumLogRetornoStatus;
import tools.enums.EnumNivelAcesso;
import tools.reports.TableConfPrint;
import tools.utils.LogRetorno;
import tools.utils.Util;
import tools.utils.Validacoes;
import tools.xml.UserDefinedColumn;
import tools.xml.XMLTableColumns;

/**
 * CLASSE CONTROLADORA DO FORMULARIO viewFornecedor.fxml
 * 
 * @author Eptus da Amazônia
 */
@SuppressWarnings("restriction")
public class FornecedorController implements Initializable {

	@FXML
	private AnchorPane anchorPanePrincipal, anchorPaneListagem, anchorPaneDetalhes, anchorPaneFilter, anpWeb;

	@FXML
	private SplitPane splitPaneFilter;

	@FXML
	private ToolBar toolBarListagem, toolBarDetalhes;

	@FXML
	public TableView<Fornecedor> tbView;

	@FXML
	public Tab tabInfGerais, tabDadosBanc, tabConsultaReceita;

	@FXML
	private Label lblCodigo, lblRazao, lblFantasia, lblCpfCnpj, lblIncEstad, lblTipoPessoa, lblIndIncEstad,
			lblCodCidade, lblCidade, lblEndereco, lblNumero, lblComplemento, lblBairro, lblCep, lblUf, lblFone,
			lblCelular, lblFone2, lblEmail, lblInternet, lblInscProd, lblDiasEntrega, lblContatoNome, lblContatoCargo,
			lblContato2Nome, lblContato2Cargo, lblCboxFilterColumn, lblcboxFlagAtivo, lblDetalhes, lblDescriao,
			lblDetCodigo, lblDetDesc, lblBanco2CpfCnpj, lblBanco2Destinatario, lblBanco2Conta, lblBanco2Agencia,
			lblBanco2, lblBancoCpfCnpj, lblBancoDestinatario, lblBancoConta, lblBancoAgencia, lblForncedorContatos,
			lblBanco, lblNumLinhas, lblTotalLinhas, lbTitleFormCad, lblErrorMessage;

	@FXML
	private Button btnInsertGrid, btnInsert, btnDelete, btnRefresh, btnFilter, btnPrint, btnExport, btnConfig, btnSave,
			btnCancel, btnClose;

	@FXML
	private TableColumn<Fornecedor, Integer> tbColCodigo, tbColCodeEmp, tbColAtivoInat, tbColDiasEntrega;

	@FXML
	private TableColumn<Fornecedor, String> tbColRazao, tbColFantasia, tbColCpfCnpj, tbColIeRg, tbColEndereco,
			tbColBairro, tbColCidade, tbColEndNumero, tbColComplemento, tbColUf, tbColFone, tbColCelular, tbColFone2,
			tbColEmail, tbColContato1Nome, tbColContato1Cargo, tbColContato2Nome, tbColContato2Cargo,
			tbColBanco1Descricao, tbColBanco1Agencia, tbColBanco1Conta, tbColBanco1Destinatario, tbColBanco1CpfCnpj,
			tbColBanco2Descricao, tbColBanco2Agencia, tbColBanco2Conta, tbColBanco2Destinatario, tbColBanco2CpfCnpj;

	@FXML
	private ComboBox<ComboBoxFilter> cboxTipoPessoa, cboxIndIncEstad, cboxFilterColumn, cboxFlagAtivo;

	@FXML
	private TextField txtRazao, txtCnpj, txtFantasia, txtIncEstad, txtCidade, txtEndereco, txtEndNumero, txtComplemento,
			txtBairro, txtCep, txtFone, txtCelular, txtFone2, txtEmail, txtInternetSite, txtInscProdRural,
			txtDiasEntrega, txtContatoNome, txtContatoCargo, txtContato2Nome, txtContato2Cargo, txtBanco1Descricao,
			txtBanco1Agencia, txtBanco1Conta, txtBanco1Destinatario, txtBanco1CpfCnpj, txtBanco2Descricao,
			txtBanco2Agencia, txtBanco2Conta, txtBanco2Destinatario, txtBanco2CpfCnpj, txtCodigo, txtUF;
	
    @FXML
    private JFXToggleButton tgbTransportadora;

	@FXML
	private TabPane tabPane;

	@FXML
	private WebView webCnpj = new WebView();

	@FXML
	private ProgressBar pBarCnpj = new ProgressBar(0);

	@FXML
	private Button btnEnviaCnpj, btnCapturaDados;

	@FXML
	private TextField txtUrlCnpj;

	@FXML
	private CustomTextField txtCodCidade, txtCodigos;

	@FXML
	private CustomTextField txtFilterColumn;

	@FXML
	private ProgressBar pBar;

	@FXML
	private ContextMenu contextMenu = null;

	@FXML
	private ToggleButton toggleHelp;

	@FXML
	private ImageView imgLoading;

	// ------------------- ELEMENTOS CONTIDOS NO FORMULARIO FXML
	// ------------------------

	// ATRIBUTOS DA CLASSE
	private ObservableList<Fornecedor> listaRegistros = null;
	private ObservableList<ComboBoxFilter> listComboBoxFilter = FXCollections.observableArrayList();
	private List<TableConfPrint> tableShowPrintList = new ArrayList<TableConfPrint>();
	boolean flagPaneFilter = true;// ATRIBUTO UTILIZADO PARA CONTROLAR O STATUS
									// DO PAINEL DO FILTRO ABERTO OU FECHADO
	boolean flagSearchCidade = true;// ATRIBUTO UTILIZADO PARA CONTROLAR O
									// STATUS DO PAINEL DO FILTRO ABERTO OU
									// FECHADO
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
	private Fornecedor entidadeBean = new Fornecedor();
	private FornecedorDAO entidadeDao = new FornecedorDAO();
	CidadeDAO cidadeDAO = new CidadeDAO();
	private Util util = new Util();
	private PopOver popOver = new PopOver();
	private static Stage stg;
	private String fileNameConfigColum = "Grid-Cad-Fornecedor";

	/**
	 * CONSTRUTOR PADRAO DA CLASSE
	 */
	public FornecedorController() {

	}

	/**
	 * CONSTRUTOR GENERICO DA CLASSE ONDE É PASSADO OS NIVEIS DE ACESSO DO
	 * FORMULARIO
	 * 
	 * @param nivAcesso
	 * @param o_tabPrincipal
	 */
	public FornecedorController(NivelAcesso nivAcesso, TabPane o_tabPrincipal) {

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
			entidadeBean = new Fornecedor();
			btnInsert.setDisable(true);
			btnSave.setDisable(false);
			btnCancel.setDisable(false);
			txtCodigo.setDisable(true);
			resetForm();
			txtCodigo.setText("+1");
			Util.openFormCadastro(anchorPaneListagem, anchorPaneDetalhes, Util.MEDIUM_SIZE);
			
			//--START EMPTY---
			lblErrorMessage.setText("");
			
			txtRazao.requestFocus();
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
			entidadeBean = new Fornecedor();
			btnInsert.setDisable(true);
			btnSave.setDisable(false);
			btnCancel.setDisable(false);
			txtCodigo.setDisable(true);
			resetForm();
			txtCodigo.setText("+1");
			txtRazao.requestFocus();
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

	@FXML
	/**
	 * AÇÃO DO BOTAO SALVAR PRESENTE NA ABA DETALHES (btnSave) - ATALHO F6
	 */
	void actionBtnSave(ActionEvent event) {

		if (nivAcessoPermissao.getFlagUpdate().equals(1)) 
		{

			if (!Util.noEmpty(lblErrorMessage, anchorPaneDetalhes, txtRazao, txtFantasia, txtCnpj) && !Util.noEmptyCboxFilter(cboxIndIncEstad)
					&& !Util.noEmpty(lblErrorMessage, anchorPaneDetalhes,txtCodCidade, txtCidade)) {
				if ((cboxTipoPessoa.getValue().getField().equals("1") && Validacoes.isCPF(txtCnpj.getText().toString()))
						|| (cboxTipoPessoa.getValue().getField().equals("2")
								&& Validacoes.isCNPJ(txtCnpj.getText().toString()))) {

					entidadeBean.setRazao(Util.textfieldNotNull("str", txtRazao));
					entidadeBean.setFantasia(Util.textfieldNotNull("str", txtFantasia));
					entidadeBean.setCpfCnpj(Util.textfieldNotNull("str", txtCnpj));
					entidadeBean.setIeRg(Util.textfieldNotNull("str", txtIncEstad));
					entidadeBean.setBairro(Util.textfieldNotNull("str", txtBairro));
					entidadeBean.setBanco1Agencia(Util.textfieldNotNull("str", txtBanco1Agencia));
					entidadeBean.setBanco1Conta(Util.textfieldNotNull("str", txtBanco1Conta));
					entidadeBean.setBanco1CpfCnpj(Util.textfieldNotNull("str", txtBanco1CpfCnpj));
					entidadeBean.setBanco1Descricao(Util.textfieldNotNull("str", txtBanco1Descricao));
					entidadeBean.setBanco1Destinatario(Util.textfieldNotNull("str", txtBanco1Destinatario));
					entidadeBean.setBanco2Agencia(Util.textfieldNotNull("str", txtBanco2Agencia));
					entidadeBean.setBanco2Conta(Util.textfieldNotNull("str", txtBanco2Conta));
					entidadeBean.setBanco2CpfCnpj(Util.textfieldNotNull("str", txtBanco2CpfCnpj));
					entidadeBean.setBanco2Descricao(Util.textfieldNotNull("str", txtBanco2Descricao));
					entidadeBean.setBanco2Destinatario(Util.textfieldNotNull("str", txtBanco2Destinatario));
					entidadeBean.setCelular(Util.textfieldNotNull("str", txtCelular));
					entidadeBean.setCep(Util.textfieldNotNull("str", txtCep));
					entidadeBean.setCidade(Util.textfieldNotNull("str", txtCidade));
					entidadeBean.setCodCidade(Integer.parseInt(Util.textfieldNotNull("int", txtCodCidade)));
					entidadeBean.setContato1Cargo(Util.textfieldNotNull("str", txtContatoCargo));
					entidadeBean.setContato1Nome(Util.textfieldNotNull("str", txtContatoNome));
					entidadeBean.setContato2Cargo(Util.textfieldNotNull("str", txtContato2Cargo));
					entidadeBean.setContato2Nome(Util.textfieldNotNull("str", txtContato2Nome));
					entidadeBean.setDiasEntrega(
							(txtDiasEntrega.getText().isEmpty() ? 1 : Integer.parseInt(txtDiasEntrega.getText())));
					entidadeBean.setEmail(Util.textfieldNotNull("str", txtEmail));
					entidadeBean.setEndereco(Util.textfieldNotNull("str", txtEndereco));
					entidadeBean.setEndNumero(Util.textfieldNotNull("str", txtEndNumero));
					entidadeBean.setComplemento(Util.textfieldNotNull("str", txtComplemento));
					entidadeBean.setFlagTipopessoa(Integer.parseInt(cboxTipoPessoa.getValue().getField()));
					entidadeBean.setFlagTransportadora(tgbTransportadora.isSelected() ? 1 : 0);
					entidadeBean.setFone(Util.textfieldNotNull("str", txtFone));
					entidadeBean.setFone2(Util.textfieldNotNull("str", txtFone2));
					entidadeBean.setInscProdrural(Util.textfieldNotNull("str", txtInscProdRural));
					entidadeBean.setInternetSite(Util.textfieldNotNull("str", txtInternetSite));
					entidadeBean.setUf(Util.textfieldNotNull("str", txtUF));
					entidadeBean.setFlagIndicadorInscEst(Integer.parseInt(cboxIndIncEstad.getValue().getField()));

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
										"[ FornecedorController.actionBtnSave() - INSERT ]");
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
										"[ FornecedorController.actionBtnSave() - UPDATE ]");
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
				} else {
					Util.setStyleError(true, txtCnpj);
				}
			}
			else{}	
			
		} else {
			util.showAlert(DadosGlobais.resourceBundle.getString("alertInfPermicoes"), "warning");
		}
	}

	@FXML
	/**
	 * AÇÃO DO BOTAO CANCELAR PRESENTE NA ABA DETALHES (btnCancel) - ATALHO F7
	 */
	void actionBtnCancel(ActionEvent event) {

		Util.setDefaultStyle(txtCodigo, txtRazao);
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
					new ConfigColumnController(FornecedorController.class, tbView, fileNameConfigColum)));
			stg.setScene(scene);
			stg.initStyle(StageStyle.TRANSPARENT);
			stg.initModality(Modality.APPLICATION_MODAL);
			stg.show();
		} catch (IOException e) {
			util.tratamentoExcecao(e.getMessage().toString(), "[ FornecedorController.actionBtnConfig() ]");
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
							+ " " + DadosGlobais.resourceBundle.getString("alertConfirmOprExcluirFornecedor"),
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
								"[ FornecedorController.actionBtnDelete() ]");
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
				stg = ProgressBarForm.showProgressBar(FornecedorController.class, tarefaCargaPg,
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
	/**
	 * EVENTO QUE ALTERA A MASCARA DO CAMPO CPF/CNPJ DE ACORDO COM O TIPO DE
	 * PESSOA SELECIONADA NO cboxTipoPessoa
	 */
	void actionCboxTipoPessoa(ActionEvent event) {
		if (cboxTipoPessoa.getValue().getField().equals("1")) {
			lblCpfCnpj.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblCpf"));
			Util.mascaraCPF(txtCnpj);
		} else {
			lblCpfCnpj.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblCnpj"));
			Util.mascaraCNPJ(txtCnpj);
		}
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

			if (font[i].getText().toString().contains("TELEFONE"))
				txtFone.setText(Util.removeBlankSpace(font[i + 1].getText().toString()));

		}

		LogRetorno log = cidadeDAO.getCidadeByName(municipio, uf);
		Cidade cidade = (Cidade) log.getObjeto();

		if (cidade != null) {
			txtCodCidade.setText(cidade.getCodigo().toString());
			txtCidade.setText(cidade.getDescricao());
			txtUF.setText(cidade.getUfSigla());

		} else {

			util.showAlert("Cidade não encotrada", "information");
			txtCodCidade.requestFocus();
		}

		tabPane.getSelectionModel().select(0);

		// }

	}

	@FXML
	void actionBtnEnviaCnpj(ActionEvent event) {

		String url = "http://www.receita.fazenda.gov.br/PessoaJuridica/CNPJ/cnpjreva/Cnpjreva_Solicitacao2.asp";

		if (webCnpj.getEngine().getLocation().equalsIgnoreCase(url)) {

			Element e = (Element) webCnpj.getEngine().executeScript("document.getElementById('cnpj')");
			e.setAttribute("value", Util.getStringConverterCNPJ(txtCnpj.getText()));

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

	@FXML
	/**
	 * EVENTO CLICK NO BOTAO QUE ABRE O BUSCADOR DE CIDADES
	 */
	void onActionTxtCodCidade(ActionEvent event) {
		if (flagOpenBuscador)
			showSearch();

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

					entidadeBean = (Fornecedor) logRet.getObjeto();

					if (entidadeBean != null) {

						if (logRet.getStatus().equals(EnumLogRetornoStatus.REGDUPLICADO))
							util.alertException(logRet.getMsg(), "", false);

						txtRazao.setText(entidadeBean.getRazao());
						txtFantasia.setText(entidadeBean.getFantasia());
						txtCnpj.setText(entidadeBean.getCpfCnpj());
						txtIncEstad.setText(entidadeBean.getIeRg());
						txtBairro.setText(entidadeBean.getBairro());
						txtBanco1Agencia.setText(entidadeBean.getBanco1Agencia());
						txtBanco1Conta.setText(entidadeBean.getBanco1Conta());
						txtBanco1CpfCnpj.setText(entidadeBean.getBanco1CpfCnpj());
						txtBanco1Descricao.setText(entidadeBean.getBanco1Descricao());
						txtBanco1Destinatario.setText(entidadeBean.getBanco1Destinatario());
						txtBanco2Agencia.setText(entidadeBean.getBanco2Agencia());
						txtBanco2Conta.setText(entidadeBean.getBanco2Conta());
						txtBanco2CpfCnpj.setText(entidadeBean.getBanco2CpfCnpj());
						txtBanco2Descricao.setText(entidadeBean.getBanco2Descricao());
						txtBanco2Destinatario.setText(entidadeBean.getBanco2Destinatario());
						txtCelular.setText(entidadeBean.getCelular());
						txtCep.setText(entidadeBean.getCep());
						txtCidade.setText(entidadeBean.getCidade());
						txtCodCidade.setText(entidadeBean.getCodCidade().toString());
						txtContatoCargo.setText(entidadeBean.getContato1Cargo());
						txtContatoNome.setText(entidadeBean.getContato1Nome());
						txtContato2Cargo.setText(entidadeBean.getContato2Cargo());
						txtContato2Nome.setText(entidadeBean.getContato2Nome());
						txtDiasEntrega.setText(entidadeBean.getDiasEntrega().toString());
						txtEmail.setText(entidadeBean.getEmail());
						txtEndereco.setText(entidadeBean.getEndereco());
						txtEndNumero.setText(entidadeBean.getEndNumero());
						txtComplemento.setText(entidadeBean.getComplemento());
						txtFone.setText(entidadeBean.getFone());
						txtFone2.setText(entidadeBean.getFone2());
						txtInscProdRural.setText(entidadeBean.getInscProdrural());
						txtInternetSite.setText(entidadeBean.getInternetSite());
						txtUF.setText(entidadeBean.getUf());
						tgbTransportadora.setSelected(entidadeBean.getFlagTransportadora() == 1 ? true : false);

						Util.setCboxFilterSelecionado(cboxTipoPessoa, entidadeBean.getFlagTipopessoa().toString());
						Util.setCboxFilterSelecionado(cboxIndIncEstad, entidadeBean.getFlagIndicadorInscEst().toString());

						txtRazao.selectEnd();

						if (flagDuplica) {
							entidadeBean = new Fornecedor();
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

					} else {

						//--START SHOW ERROR---
						lblErrorMessage.setText(DadosGlobais.resourceBundle.getString("alertErrorCodigoNull"));
						Util.setStyleError(true, txtCodigo);
						
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
							"[ FornecedorController.loadByID() ]");
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
			Util.setStyleError(true, txtCodigo);
			
			resetForm();
			txtCodigo.requestFocus();
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
						"[ FornecedorController.updateTbView() ]");
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

						listaRegistros = FXCollections.observableArrayList(entidadeDao.filterByColumn(cboxFilterColumn.getValue().getField(), txtFilterColumn.getText(),
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
						"[ FornecedorController.tafefaConsulta() ]");
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
				pBar.setProgress(1);
				if (logRet.getStatus().equals(EnumLogRetornoStatus.ERRO)) {
					Util.setStyleError(true, txtCodCidade);
					txtCodCidade.clear();
					txtCidade.clear();
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
						"[ FornecedorController.searchCidade() ]");
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
	 * RESETA OS CAMPOS CONTIDOS NO FORMULARIO
	 */
	public void resetForm() {
		Util.limpar(txtRazao, txtFantasia, txtCnpj, txtIncEstad, txtCodCidade, txtCidade, txtEndereco, txtEndNumero,
				txtComplemento, txtBairro, txtCep, txtFone, txtCelular, txtFone2, txtEmail, txtInternetSite,
				txtInscProdRural, txtDiasEntrega, txtContatoNome, txtContatoCargo, txtContato2Nome, txtContato2Cargo,
				txtBanco1Descricao, txtBanco1Agencia, txtBanco1Conta, txtBanco1Destinatario, txtBanco1CpfCnpj,
				txtBanco2Descricao, txtBanco2Agencia, txtBanco2Conta, txtBanco2Destinatario, txtBanco2CpfCnpj,
				txtCodigo);

		Util.setDefaultStyle(txtRazao, txtFantasia, txtCnpj, txtIncEstad, txtCodCidade, txtCidade, txtEndereco,
				txtEndNumero, txtComplemento, txtBairro, txtCep, txtFone, txtCelular, txtFone2, txtEmail,
				txtInternetSite, txtInscProdRural, txtDiasEntrega, txtContatoNome, txtContatoCargo, txtContato2Nome,
				txtContato2Cargo, txtBanco1Descricao, txtBanco1Agencia, txtBanco1Conta, txtBanco1Destinatario,
				txtBanco1CpfCnpj, txtBanco2Descricao, txtBanco2Agencia, txtBanco2Conta, txtBanco2Destinatario,
				txtBanco2CpfCnpj, txtCodigo);

		tgbTransportadora.setSelected(false);
		cboxIndIncEstad.getSelectionModel().clearSelection();
		cboxTipoPessoa.getSelectionModel().selectLast();
	}

	/**
	 * EXIBE O FORMULARIO DE BUSCA DE CIDADE
	 */
	public void showSearch() {

		ObservableList<ComboBoxFilter> list = FXCollections.observableArrayList();

		list.add(new ComboBoxFilter("Código", 1, "codigo"));
		list.add(new ComboBoxFilter("Descrição", 2, "descricao"));
		list.add(new ComboBoxFilter("UF", 3, "ufSigla"));

		util.showSearchView("Cidade", "descricao", CidadeDAO.class, list, txtCodCidade, txtCidade, txtUF, txtEndereco);

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

		lblEndereco.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblEndereco"));

		lbTitleFormCad.setText(DadosGlobais.resourceBundle.getString("lblTitleFormCad") + " "
				+ DadosGlobais.resourceBundle.getString("miCadFornecedor"));

		lblCodigo.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblCodigo"));
		lblTipoPessoa.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblTipoPessoa"));
		tgbTransportadora.setText(DadosGlobais.resourceBundle.getString("fornecedorController.tgbTransportadora"));
		lblRazao.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblRazao"));
		lblFantasia.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblFantasia"));
		lblCpfCnpj.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblCpfCnpj"));
		lblIncEstad.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblIncEstad"));
		lblIndIncEstad.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblIndIncEstad"));
		tabInfGerais.setText(DadosGlobais.resourceBundle.getString("fornecedorController.tabInfGerais"));
		lblCodCidade.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblCodCidade"));
		lblCidade.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblCidade"));
		lblUf.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblUf"));
		lblEndereco.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblEndereco"));
		lblNumero.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblNumero"));
		lblComplemento.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblComplemento"));
		lblBairro.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblBairro"));
		lblCep.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblCep"));
		lblFone.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblFone"));
		lblCelular.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblCelular"));
		lblFone2.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblFone2"));
		lblEmail.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblEmail"));
		lblInternet.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblInternet"));
		lblInscProd.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblInscProd"));
		lblDiasEntrega.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblDiasEntrega"));
		lblForncedorContatos
				.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblForncedorContatos"));
		lblContatoNome.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblContatoNome"));
		lblContatoCargo.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblContatoCargo"));
		lblContato2Nome.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblContatoNome"));
		lblContato2Cargo.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblContatoCargo"));
		tabDadosBanc.setText(DadosGlobais.resourceBundle.getString("fornecedorController.tabDadosBanc"));
		lblBanco.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblBanco"));
		lblBancoAgencia.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblBancoAgencia"));
		lblBancoConta.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblBancoConta"));
		lblBancoDestinatario
				.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblBancoDestinatario"));
		lblBancoCpfCnpj.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblBancoCpfCnpj"));
		lblBanco2.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblBanco"));
		lblBanco2Agencia.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblBancoAgencia"));
		lblBanco2Conta.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblBancoConta"));
		lblBanco2Destinatario
				.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblBancoDestinatario"));
		lblBanco2CpfCnpj.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblBancoCpfCnpj"));

		tabConsultaReceita.setText(DadosGlobais.resourceBundle.getString("fornecedorController.tabConsultaReceita"));
		btnCapturaDados.setText(DadosGlobais.resourceBundle.getString("fornecedorController.btnCapturaDados"));
		btnEnviaCnpj.setText(DadosGlobais.resourceBundle.getString("fornecedorController.btnEnviaCnpj"));

		tbColCodigo.setText(DadosGlobais.resourceBundle.getString("tbColCodigo"));
		tbColCodeEmp.setText(DadosGlobais.resourceBundle.getString("tbColCodeEmp"));
		tbColAtivoInat.setText(DadosGlobais.resourceBundle.getString("tbColAtivoInat"));
		tbColRazao.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblRazao"));
		tbColCpfCnpj.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblCpfCnpj"));
		tbColFantasia.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblFantasia"));
		tbColEndereco.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblEndereco"));
		tbColIeRg.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblIndIncEstad"));
		tbColBairro.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblBairro"));
		tbColCidade.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblCidade"));
		tbColEndNumero.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblNumero"));
		tbColComplemento.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblComplemento"));
		tbColUf.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblUf"));
		tbColFone.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblFone"));
		tbColCelular.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblCelular"));
		tbColFone2.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblFone2"));
		tbColEmail.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblEmail"));
		tbColDiasEntrega.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblDiasEntrega"));
		tbColContato1Nome.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblContatoNome"));
		tbColContato1Cargo.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblContatoCargo"));
		tbColContato2Nome.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblContatoNome"));
		tbColContato2Cargo.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblContatoCargo"));
		tbColBanco1Descricao.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblBanco"));
		tbColBanco1Agencia.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblBancoAgencia"));
		tbColBanco1Conta.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblBancoConta"));
		tbColBanco1Destinatario
				.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblBancoDestinatario"));
		tbColBanco1CpfCnpj.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblBancoCpfCnpj"));
		tbColBanco2Descricao.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblBanco2"));
		tbColBanco2Agencia.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblBancoAgencia2"));
		tbColBanco2Conta.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblBancoConta2"));
		tbColBanco2Destinatario
				.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblBancoDestinatario2"));
		tbColBanco2CpfCnpj.setText(DadosGlobais.resourceBundle.getString("fornecedorController.lblBancoCpfCnpj2"));

		tbView.setPlaceholder(new Label(DadosGlobais.resourceBundle.getString("tbView")));

	}

	/**
	 * METODO PARA POPULAR O COMBOBOX DE TIPOS DE FILTRO (cboxFilterColumn)
	 */
	public void populateCboxFilterColumn() {
		// 2 Tipo de Busca Contida, 1 Tipo de Busca Exata
		listComboBoxFilter.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("codigo"), 1, "codigo"));
		listComboBoxFilter.add(new ComboBoxFilter(
				DadosGlobais.resourceBundle.getString("fornecedorController.lblFantasia"), 2, "razao"));
		listComboBoxFilter.add(new ComboBoxFilter("Cpf/Cnpj", 2, "cpfCnpj"));
		listComboBoxFilter.add(new ComboBoxFilter("Inscrição Estadual", 2, "ieRg"));
		listComboBoxFilter.add(new ComboBoxFilter(
				DadosGlobais.resourceBundle.getString("fornecedorController.lblEndereco"), 2, "fantasia"));

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
		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("fornecedorController.cboxTipoPessoa1"), 1,
				"1"));
		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("fornecedorController.cboxTipoPessoa2"), 2,
				"2"));

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
	public void updateTableView(ObservableList visibleColumns, TableView<FornecedorDAO> table) {

		if (!visibleColumns.isEmpty()) {
			// hide all columns
			for (TableColumn<FornecedorDAO, ?> col : table.getColumns())
				col.setVisible(false);
			// show column and defines its position
			for (int i = 0; i < visibleColumns.size(); i++) {
				table.getColumns().remove(visibleColumns.get(i));
				table.getColumns().add(i, (TableColumn<FornecedorDAO, ?>) visibleColumns.get(i));
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
						TableColumn<Fornecedor, ?> tableColumn = tbView.getColumns().get(j);
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

			Object type = Fornecedor.class
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
					new PrintExportController(tbView, tableShowPrintList, "Fornecedor", pBar, stg)));

			stg.setScene(scene);
			stg.initStyle(StageStyle.TRANSPARENT);
			stg.initModality(Modality.APPLICATION_MODAL);
			stg.show();
		} catch (IOException e) {
			util.tratamentoExcecao(e.getMessage().toString(), "[ FornecedorController.printExportShow() ]");
		}

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

		Util.customSearchTextField("right", null, txtFilterColumn);
		Util.customSearchTextField("right", null, txtCodCidade);

		// CHAMADA A FUNÇÃO QUE CONFIGURA O TABLEVIEW DE ACORDO COM O ARQUIVO
		// XML GERADO PARA CADA USUARIO DENTRO DA PASTA C:/EptusAM/System.Grd
		configTableView();

		// CHAMADA A FUNÇÃO QUE FAZ A TRADUÇÃO DO FORMULARIO DE ACORDO COM AS
		// CONFIGURAÇÕES DO USUÁRIO
		translations();

		// FUNÇAO PARA MUDAR O FOCO PARA O PROXIMO CAMPO AO DAR ENTER
		Util.setNextFocus(txtRazao, txtFantasia, txtCnpj, txtIncEstad, txtEndereco, txtEndNumero, txtComplemento,
				txtBairro, txtCep, txtFone, txtCelular, txtFone2, txtEmail, txtInternetSite, txtInscProdRural,
				txtDiasEntrega, txtContatoNome, txtContatoCargo, txtContato2Nome, txtContato2Cargo);

		// CHAMADA A FUNÇÃO QUE SETA O ESTILO DEFAULT AOS CAMPOS TEXTFIELDS
		Util.setKeyPressDefaultStyles(txtRazao, txtFantasia, txtCnpj, txtIncEstad, txtCodCidade, txtEndereco,
				txtEndNumero, txtComplemento, txtBairro, txtCep, txtFone, txtCelular, txtFone2, txtEmail,
				txtInternetSite, txtInscProdRural, txtDiasEntrega, txtContatoNome, txtContatoCargo, txtContato2Nome,
				txtContato2Cargo, txtBanco1Descricao, txtBanco1Agencia, txtBanco1Conta, txtBanco1Destinatario,
				txtBanco1CpfCnpj, txtBanco2Descricao, txtBanco2Agencia, txtBanco2Conta, txtBanco2Destinatario,
				txtBanco2CpfCnpj, txtCodigo, txtFilterColumn);

		Util.setStyleOnFocus(txtFilterColumn, txtCodigo, txtRazao, txtFantasia, txtCnpj, txtIncEstad, txtCodCidade,
				txtEndereco, txtEndNumero, txtComplemento, txtBairro, txtCep, txtFone, txtCelular, txtFone2, txtEmail,
				txtInternetSite, txtInscProdRural, txtDiasEntrega, txtContatoNome, txtContatoCargo, txtContato2Nome,
				txtContato2Cargo, txtBanco1Descricao, txtBanco1Agencia, txtBanco1Conta, txtBanco1Destinatario,
				txtBanco1CpfCnpj, txtBanco2Descricao, txtBanco2Agencia, txtBanco2Conta, txtBanco2Destinatario,
				txtBanco2CpfCnpj);

		Util.setMouseClickDefaultStyles(cboxIndIncEstad);

		// CHAMADA A FUNÇÃO QUE SETA O ESTILO DEFAULT AOS CAMPOS TEXTFIELDS
		Util.onlyNumbers(txtCodigo, txtCodCidade, txtDiasEntrega);

		// CHAMADA A FUNÇÃO QUE SETA O ESTILO DEFAULT AOS CAMPOS TEXTFIELDS
		Util.onlyAlphanumeric(txtRazao, txtFilterColumn, txtRazao, txtFantasia, txtEndereco, txtBairro);

		// CHAMADA A FUNÇÃO QUE LIMITA O NUMERO DE CARACTERES QUE PODEM SER
		// DIGITADOS NOS CAMPOS TEXTFIELDS
		Util.maxCharacters(8, txtCodigo, txtCodCidade);
		Util.maxCharacters(45, txtRazao);
		Util.maxCharacters(45, txtFantasia);

		// CHAMADA A FUNÇÃO QUE DEFINE OS CAMPOS TEXTFIELDS QUE SERAO
		// CAPITALIZADOS(LETRAS MAÍUSCULAS)
		Util.whriteUppercase(txtRazao, txtFantasia, txtIncEstad, txtCidade, txtEndereco, txtEndNumero, txtComplemento,
				txtBairro, txtContatoNome, txtContatoCargo, txtContato2Nome, txtContato2Cargo, txtBanco1Descricao,
				txtBanco1Agencia, txtBanco1Conta, txtBanco1Destinatario, txtBanco1CpfCnpj, txtBanco2Descricao,
				txtBanco2Agencia, txtBanco2Conta, txtBanco2Destinatario, txtBanco2CpfCnpj, txtFilterColumn);

		// CHAMADA A FUNÇÃO PARA POPULAR OS COMBOBOX CONTINDOS NO FORMULARIO
		populateCboxFilterColumn();
		populateCboxFlagAtivo();
		populateCboxTipoPessoa();
//		ComboBoxIndicadorIE cboxIndIE = new ComboBoxIndicadorIE(cboxIndIncEstad);
		ComboBoxIndicadorIE.ComboBoxIndicadorIE(cboxIndIncEstad);

		// MASCARA DE CAMPOS ESPECIFICOS
		Util.mascaraCEP(txtCep);
		Util.mascaraCNPJ(txtCnpj);
		Util.mascaraEmail(txtEmail);
		Util.maskPhone(txtFone);
		Util.mascaraTelefone(txtFone2);

		// CHAMADA A FUNÇÃO PARA SETAR O FOCO INICIAL DO FORMULÁRIO
		util.setFocus(txtFilterColumn);

		// CONFIGURA AS COLUNAS EXISTENTES NO TABLEVIEW, ASSOCIA A COLUNA DO
		// FXML AO ATRIBUTO EXISTENTE NA CLASSE DO MODEL
		tbColCodigo.setCellValueFactory(new PropertyValueFactory<Fornecedor, Integer>("codigo"));
		tbColRazao.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("razao"));
		tbColCodeEmp.setCellValueFactory(new PropertyValueFactory<Fornecedor, Integer>("codemp"));
		tbColAtivoInat.setCellValueFactory(new PropertyValueFactory<Fornecedor, Integer>("flagAtivo"));
		tbColDiasEntrega.setCellValueFactory(new PropertyValueFactory<Fornecedor, Integer>("diasEntrega"));
		tbColFantasia.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("fantasia"));
		tbColEndereco.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("endereco"));
		tbColBairro.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("bairro"));
		tbColCpfCnpj.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("cpfCnpj"));
		tbColIeRg.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("ieRg"));
		tbColCidade.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("cidade"));
		tbColEndNumero.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("complemento"));
		tbColComplemento.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("endNumero"));
		tbColUf.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("uf"));
		tbColFone.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("fone"));
		tbColCelular.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("celular"));
		tbColFone2.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("fone2"));
		tbColEmail.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("email"));
		tbColContato1Nome.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("contato1Nome"));
		tbColContato1Cargo.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("contato1Cargo"));
		tbColContato2Nome.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("contato2Nome"));
		tbColContato2Cargo.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("contato2Cargo"));
		tbColBanco1Descricao.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("banco1Descricao"));
		tbColBanco1Agencia.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("banco1Agencia"));
		tbColBanco1Conta.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("banco1Conta"));
		tbColBanco1Destinatario.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("banco1Destinatario"));
		tbColBanco1CpfCnpj.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("banco1CpfCnpj"));

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
			TableRow<Fornecedor> row = new TableRow<Fornecedor>() {
				@Override
				public void updateItem(Fornecedor objeto, boolean empty) {
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
					}

					else if (!Validacoes.isCNPJ(txtCnpj.getText().toString())) {
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

		// MÉTODO QUE CONVERTE OS VALORES DA COLUNA FLAGATIVO EM TEXTO CASO
		// FLAGATIVO = 0 MOSTRA INATIVO E FLAGATIVO = 1 MOSTRA ATIVO
		tbColAtivoInat.setCellFactory(col -> new TableCell<Fornecedor, Integer>() {
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

		pBarCnpj.progressProperty().bind(webCnpj.getEngine().getLoadWorker().progressProperty());

		tabPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
			if (tabPane.getSelectionModel().getSelectedIndex() == 2) {
				webCnpj.getEngine().load(
						"http://www.receita.fazenda.gov.br/PessoaJuridica/CNPJ/cnpjreva/Cnpjreva_Solicitacao2.asp");
			}
			// else
			// webCnpj.getEngine().load("about:blank");
		});

		btnEnviaCnpj.setDisable(true);
		btnCapturaDados.setDisable(true);

		imgLoading.setVisible(false);

		webCnpj.getEngine().getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
			public void changed(ObservableValue ov, State oldState, State newState) {

				if (newState == State.SCHEDULED) {
					txtUrlCnpj.setText(webCnpj.getEngine().getLocation());
					btnEnviaCnpj.setDisable(true);
					btnCapturaDados.setDisable(true);
					//
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
			}
		});

		// TECLAS DE ATALHOS PARA O FORMULARIO
		// F2 - CONSULTAR | F4 - CARREGAR TODOSO OS DADOS REFRESH| F5 - INSERIR
		// NOVO REGISTRO | F6 - GRAVAR | DEL - EXCLUIR |F7 - CANCELAR || CTRL+P
		// IMPRIMIR
		anchorPanePrincipal.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			switch (event.getCode()) {
			case F2:
				if (!txtCodCidade.isFocused()) {
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