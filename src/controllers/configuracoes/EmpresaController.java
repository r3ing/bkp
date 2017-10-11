package controllers.configuracoes;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.controlsfx.control.textfield.CustomTextField;

import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXToggleButton;
import com.sun.javafx.scene.control.skin.TableHeaderRow;

import application.DadosGlobais;
import controllers.compras.DepartamentoController;
import controllers.utils.ComboBoxData.CboxData;
import controllers.utils.ComboBoxUF;
import controllers.utils.ConfigColumnController;
import controllers.utils.PrintExportController;
import controllers.utils.ProgressBarForm;
import javafx.fxml.Initializable;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import models.compras.Departamento;
import models.compras.DepartamentoDAO;
import models.configuracoes.Auditoria;
import models.configuracoes.AuditoriaDAO;
import models.configuracoes.Empresa;
import models.configuracoes.EmpresaDAO;
import tools.controls.ComboBoxFilter;
import tools.reports.TableConfPrint;
import tools.utils.Log;
import tools.utils.Util;
import tools.xml.UserDefinedColumn;
import tools.xml.XMLTableColumns;

public class EmpresaController implements Initializable {

	@FXML
	private AnchorPane anchorPanePrincipal, anchorPaneListagem, anchorPaneDetalhes, anchorPaneFilter;

	@FXML
	private TabPane tabPane;

	@FXML
	private ToolBar toolBarListagem, toolBarDetalhes;

	@FXML
	private Button btnRefresh, btnFilter, btnPrint, btnConfig, btnSave, btnCancel;

	@FXML
	private ToggleButton toggleBtnHelp;

	@FXML
	private SplitPane splitPaneFilter;

	@FXML
	private ComboBox<ComboBoxFilter> cboxFilterColumn;

	@FXML
	private CustomTextField txtFilterColumn;

	@FXML
	private TableView<Empresa> tbView;

	@FXML
	private TableColumn<Empresa, Integer> tbcolCodigo;

	@FXML
	private TableColumn<Empresa, String> tbcolRazaoSocial;

	@FXML
	private TableColumn<Empresa, String> tbcolNomeFant;

	@FXML
	private TableColumn<Empresa, String> tbcolCnpj;

	@FXML
	private TableColumn<Empresa, String> tbcolAbreviat;

	@FXML
	private TableColumn<Empresa, String> tbcolEndereco;

	@FXML
	private TableColumn<Empresa, String> tbcolNumero;

	@FXML
	private TableColumn<Empresa, String> tbcolBairro;

	@FXML
	private TableColumn<Empresa, String> tbcolCidade;

	@FXML
	private TableColumn<Empresa, String> tbcolUF;

	@FXML
	private TableColumn<Empresa, String> tbcolCep;

	@FXML
	private TableColumn<Empresa, String> tbcolFone;

	@FXML
	private TableColumn<Empresa, String> tbcolCelular;

	@FXML
	private TableColumn<Empresa, String> tbcolInscricEstad;

	@FXML
	private TableColumn<Empresa, String> tbcolInscricMunic;

	@FXML
	private TableColumn<Empresa, String> tbcolSuframa;

	@FXML
	private TableColumn<Empresa, String> tbcolWeb;

	@FXML
	private TableColumn<Empresa, String> tbcolCnaeFiscal;

	@FXML
	private TableColumn<Empresa, String> tbcolLinhaAdicional;

	@FXML
	private TableColumn<Empresa, String> tbcolNumSerie;

	@FXML
	private Label lblTotalLinhas, lblNumLinhas;

	@FXML
	private Label lblCodigo, lblVersaoSys, lblRazaoSocial, lblNumero, lblBairro, lblNomeFant, lblAbreviat, lblEndereco,
			lblCep, lblFone, lblCidade, lblCelular, lblInscricEstad, lblInscricMunic, lblCnpj, lblSuframa,
			lblCnaeFiscal, lblLinhaAdicional, lblPagIntern;

	@FXML
	private TextField txtCodigo, txtVersaoSys, txtRazaoSocial, txtNumero, txtBairro, txtNomeFant, txtAbreviat,
			txtEndereco, txtCep, txtFone, txtCidade, txtCelular, txtInscricEstad, txtInscricMunic, txtCnpj, txtSuframa,
			txtCnaeFiscal, txtLinhaAdicional, txtPagIntern;

	@FXML
	private ComboBox<String> cboxUF;

	@FXML
	private TextField txtEmail, txtNomeExib, txtServEntrada, txtServSaida, txtPortaS, txtLogin, txtSerie1, txtSerie2,
			txtSerie3, txtSerie4;

	@FXML
	private PasswordField txtSenha;

	@FXML
	private Label lblNomeExib, lblServEntrada, lblServSaida, lblPortaS, lblPortaE, lblLogin, lblSenha, lblNumSerie;

	@FXML
	private JFXToggleButton tgbServidorAut;

	@FXML
	private Tab tabInformacTrib;

	@FXML
	private Label lblCodRegime, lblCodNatureza, lblEnquadramento, lblIndicTipoAtv, lblCorEmpresa;

	@FXML
	private ComboBox<ComboBoxFilter> cboxCodRegime;

	@FXML
	private ComboBox<ComboBoxFilter> cboxCodNatureza;

	@FXML
	private ComboBox<ComboBoxFilter> cboxEnquadramento;

	@FXML
	private ComboBox<ComboBoxFilter> cboxIndicTipoAtv;

	@FXML
	private JFXColorPicker colorPicker;

	@FXML
	private ProgressBar pBar;

	@FXML
	private ContextMenu contextMenu = null;

	// Initial status of filter panel
	boolean flagPaneFilter = true;

	// Attributes used in the class
	private ObservableList<Empresa> dataEmpresa = null;
	private Empresa empresa = new Empresa();
	EmpresaDAO empDao = new EmpresaDAO();
	Util util = new Util();
	public List<TableConfPrint> tableShowPrintList = new ArrayList<TableConfPrint>();
	public ObservableList<ComboBoxFilter> listComboBoxFilter = FXCollections.observableArrayList();
	static Stage stg;
	private List<Integer> paramFlagAtivo = Arrays.asList(1);

	public EmpresaController() {
		super();
		// TODO Auto-generated constructor stub
	}

	@FXML
	void actionBtnCancel(ActionEvent event) {

		Util.setDefaultStyle(txtCodigo, txtAbreviat, txtEndereco, txtBairro, txtCep);

		cargarDatosEmpresa("only", false, DadosGlobais.empresaLogada.getCodigo());

		// tabListagem.setDisable(false);

	}

	@FXML
	void actionBtnConfig(ActionEvent event) {

		try {
			stg = new Stage();
			Scene scene = new Scene(util.openWindow("/views/utils/viewConfigColumns.fxml",
					new ConfigColumnController(EmpresaController.class, tbView, "Grid-Empresa")));
			scene.getStylesheets()
					.add(getClass().getResource("/styles/css/themes " + DadosGlobais.empresaLogada.getSistema()
							+ "/style_" + DadosGlobais.empresaLogada.getSistema() + ".css").toExternalForm());
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
	void actionBtnFilter(ActionEvent event) {
		if (flagPaneFilter) {
			flagPaneFilter = false;
			splitPaneFilter.setDividerPositions(0);
		} else {
			splitPaneFilter.setDividerPositions(1);
			flagPaneFilter = true;
			txtFilterColumn.requestFocus();
		}

	}

	@FXML
	void actionBtnPrint(ActionEvent event) throws NoSuchFieldException, SecurityException {
		printExportShow();
	}

	@FXML
	void actionTxtFilterColumn(ActionEvent event) {

		// Inica a Tarefa de Busca de Dados
		Task<String> tarefaCargaPg = new Task<String>() {
			@Override
			protected String call() throws Exception {

				cargarDatosEmpresa("filter", true, 0);
				return "-";

			}

			@Override
			protected void succeeded() {
				tbView.getItems().clear();
				stg.close();
				pBar.setProgress(1);

				tbView.getItems().addAll(dataEmpresa);
				tbView.getSelectionModel().select(0);
				tbView.requestFocus();

				lblNumLinhas.setText(String.valueOf(tbView.getItems().size()));
				if (tbView.getItems().size() > 0)
					btnPrint.setDisable(false);
				else {
					util.showAlert(DadosGlobais.resourceBundle.getString("alertInfRegisterNull"), "error");
					btnPrint.setDisable(true);
				}

			}

			@Override
			protected void failed() {
				stg.close();
				util.showAlert(DadosGlobais.resourceBundle.getString("alertErrorQuery"), "error");
				pBar.setProgress(0);
			}

			@Override
			protected void cancelled() {
				// tbDepart.getItems().clear();
				pBar.setProgress(0);
				super.cancelled();
			}

		};

		Thread t = new Thread(tarefaCargaPg);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(DepartamentoController.class, tarefaCargaPg,
				DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
		pBar.setProgress(-1);

	}

	@FXML
	void actionBtnSave(ActionEvent event) {

		empresa.setCodigo(Integer.valueOf(txtCodigo.getText()));
		// empresa.setRazaoSocial(txtRazaoSocial.getText());
		// empresa.setNomeFantasia(txtNomeFant.getText());
		empresa.setDescReduzida(txtAbreviat.getText());
		empresa.setCorEmpresa(Integer.toHexString(colorPicker.getValue().hashCode()));
		empresa.setEndereco(txtEndereco.getText());
		empresa.setEndNumero(txtNumero.getText());
		empresa.setBairro(txtBairro.getText());
		empresa.setCep(txtCep.getText());
		empresa.setFone(txtFone.getText());
		empresa.setCelular(txtCelular.getText());
		// empresa.setCnpj(txtCnpj.getText());
		empresa.setIe(txtInscricEstad.getText());
		empresa.setIncMunicipal(txtInscricMunic.getText());
		empresa.setSuframa(txtSuframa.getText());
		empresa.setWebHomepage(txtPagIntern.getText());
		empresa.setCnaeFiscal(txtCnaeFiscal.getText());
		empresa.setLinhaAdicional(txtLinhaAdicional.getText());
		empresa.setEmailNome(txtNomeExib.getText());
		empresa.setEmailEmail(txtEmail.getText());
		empresa.setEmailHostsai(txtServSaida.getText());
		empresa.setEmailPortsai(txtPortaS.getText());
		empresa.setEmailLogin(txtLogin.getText());
		empresa.setEmailPassword(txtSenha.getText());
		empresa.setEmailFlagAutenticacao((tgbServidorAut.isSelected() ? 1 : 0));
		empresa.setRegimeTrib(Integer.valueOf(cboxCodRegime.getValue().getField()));
		empresa.setCodNatjuridica(cboxCodNatureza.getValue().getField());
		empresa.setCodEnquadraporte(Integer.valueOf(cboxEnquadramento.getValue().getField()));
		empresa.setIndTipoativ(Integer.valueOf(cboxIndicTipoAtv.getValue().getField()));

		if (!Util.noEmpty(txtCodigo, txtAbreviat, txtEndereco, txtBairro, txtCep) && Util.validateCep(txtCep)
				&& Util.validateCel(txtCelular)) {

			Task<String> tarefaCargaPg = new Task<String>() {
				@Override
				protected String call() throws Exception {
					empDao.update(empresa);
					return "-";
				}

				@Override
				protected void succeeded() {
					stg.close();
					txtCodigo.requestFocus();
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
	void actionBtnRefresh(ActionEvent event) {

		if (util.showAlert(DadosGlobais.resourceBundle.getString("alertConfirShowAllData"), "confirmation")) {
			Task<String> tarefaCargaPg = new Task<String>() {
				@Override
				protected String call() throws Exception {

					cargarDatosEmpresa("all", false, 0);
					return "-";
				}

				@Override
				protected void succeeded() {
					stg.close();
					pBar.setProgress(1);
					tbView.setItems(dataEmpresa);
					lblNumLinhas.setText(String.valueOf(tbView.getItems().size()));
					if (tbView.getItems().size() > 0)
						btnPrint.setDisable(false);
					else
						btnPrint.setDisable(true);
				}

				@Override
				protected void failed() {
					stg.close();
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
			Thread t = new Thread(tarefaCargaPg);
			t.setDaemon(true);
			t.start();
			stg = ProgressBarForm.showProgressBar(EmpresaController.class, tarefaCargaPg,
					DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
			pBar.setProgress(-1);
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
		// Util.setDefaultStyle(txtDescricao);
	}

	@FXML
	void keyPressedTxtCodigo(KeyEvent event) {
		Util.setDefaultStyle(txtCodigo);

		if (event.getCode().equals(KeyCode.ENTER)) {
			cargarDatosEmpresa("only", false, Integer.valueOf(txtCodigo.getText()));
		}

		if (event.getCode().equals(KeyCode.ESCAPE)) {
			if (anchorPaneDetalhes.isVisible()) {
				actionBtnClose(null);
			}
		}

	}

	@FXML
	void keyReleasedTxtCep(KeyEvent event) {
		Util.setFormatCep(event, txtCep);

	}

	@FXML
	void keyReleasedTxtFone(KeyEvent event) {
		// Util.setFormatPhone(event, txtFone);
		// ------

	}

	@FXML
	void keyReleasedTxtCelular(KeyEvent event) {
		Util.setFormatCel(event, txtCelular);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@FXML
	void keyReleasedTxtFilterColumn(KeyEvent event) {

		if (dataEmpresa != null) {

			if (txtFilterColumn.textProperty().get().isEmpty()) {
				tbView.setItems(dataEmpresa);
				lblNumLinhas.setText(String.valueOf(tbView.getItems().size()));
				if (tbView.getItems().size() > 0)
					btnPrint.setDisable(false);
				else
					btnPrint.setDisable(true);

				return;
			}

			ObservableList tableItems = FXCollections.observableArrayList();
			ObservableList cols = tbView.getVisibleLeafColumns();

			for (int i = 0; i < dataEmpresa.size(); i++) {

				for (int j = 0; j < cols.size(); j++) {

					TableColumn col = (TableColumn) cols.get(j);
					String cellValue = col.getCellData(dataEmpresa.get(i)).toString();
					if (cellValue.toLowerCase().contains(txtFilterColumn.textProperty().get().toLowerCase())) {
						tableItems.add(dataEmpresa.get(i));
						break;
					}
				}
			}

			tbView.setItems(tableItems);
			lblNumLinhas.setText(String.valueOf(tbView.getItems().size()));
			if (tbView.getItems().size() > 0)
				btnPrint.setDisable(false);
			else
				btnPrint.setDisable(true);

		}

	}

	@FXML
	void mouseClickedToggleHelp(MouseEvent event) {

	}

	/**
	 * Validation event onChange TabItem.
	 * 
	 * @param pos
	 *            TabItem index.
	 * @param getLast
	 *            Change value.
	 */
	public void onChangeAba(int pos, boolean getLast, boolean action) {
		if (getLast && action) {
			tabPane.getSelectionModel().select(pos);
			actionBtnCancel(null);
			// tabListagem.setDisable(true);

		} else {
			tabPane.getSelectionModel().select(pos);
			// tabListagem.setDisable(true);
		}

	}

	/**
	 * Show data in tableView
	 *
	 */
	public void cargarDatosEmpresa(String tipoConsulta, boolean flagRefresh, int codigo) {

		// all - traz todos os dados
		// filter - consulta com filtros
		// only - traz so um

		if (tipoConsulta.equals("all")) {
			dataEmpresa = FXCollections.observableArrayList(empDao.getListEmpresa());
			if (flagRefresh = true)
				tbView.getItems().setAll(dataEmpresa);
		}

		else if (tipoConsulta.equals("filter")) {
			if (cboxFilterColumn.getValue().getAction() == 0) {
				String filter = null;

				boolean temp = true;
				for (int i = 0; i < txtFilterColumn.getText().length(); i++)
					if (!Character.toString(txtFilterColumn.getText().charAt(i)).matches("[0-9]")) {
						temp = false;
						break;
					}

				if (temp && txtFilterColumn.getText().length() <= 8)
					filter = txtFilterColumn.getText();

				else
					filter = "0";

				dataEmpresa = FXCollections.observableArrayList(empDao.filterByColumn(
						cboxFilterColumn.getValue().getField(), filter, cboxFilterColumn.getValue().getAction(), paramFlagAtivo));
			}

			else
				dataEmpresa = FXCollections
						.observableArrayList(empDao.filterByColumn(cboxFilterColumn.getValue().getField(),
								txtFilterColumn.getText(), cboxFilterColumn.getValue().getAction(), paramFlagAtivo));

		}

		else if (tipoConsulta.equals("only")) {

			empresa = (Empresa)empDao.getById(codigo).getObjeto();

			if (empresa != null) {

				txtCodigo.setText(String.valueOf(empresa.getCodigo()));
				txtRazaoSocial.setText(empresa.getRazaoSocial());
				txtNomeFant.setText(empresa.getNomeFantasia());
				txtAbreviat.setText(empresa.getDescReduzida());
				colorPicker.setValue(Color.web("#" + empresa.getCorEmpresa()));
				txtEndereco.setText(empresa.getEndereco());
				txtNumero.setText(empresa.getEndNumero());
				txtBairro.setText(empresa.getBairro());
				txtCidade.setText(empresa.getCodCidade() + " - " + empresa.getCidade());
				cboxUF.getSelectionModel().select(empresa.getUf());
				txtCep.setText(empresa.getCep());
				txtFone.setText(empresa.getFone());
				txtCelular.setText(empresa.getCelular());
				txtCnpj.setText(empresa.getCnpj());
				txtInscricEstad.setText(empresa.getIe());
				txtInscricMunic.setText(empresa.getIncMunicipal());
				txtSuframa.setText(empresa.getSuframa());
				txtPagIntern.setText(empresa.getWebHomepage());
				txtCnaeFiscal.setText(empresa.getCnaeFiscal());
				txtLinhaAdicional.setText(empresa.getLinhaAdicional());

				txtNomeExib.setText(empresa.getEmailNome());
				txtEmail.setText(empresa.getEmailEmail());
				txtServSaida.setText(empresa.getEmailHostsai());
				txtPortaS.setText(empresa.getEmailPortsai());
				txtLogin.setText(empresa.getEmailLogin());
				txtSenha.setText(empresa.getEmailPassword());
				tgbServidorAut.setSelected(empresa.getEmailFlagAutenticacao() == 1 ? true : false);

				String serie[] = empresa.getNoSerie().split("-");
				txtSerie1.setText(serie[0]);
				txtSerie2.setText(serie[1]);
				txtSerie3.setText(serie[2]);
				txtSerie4.setText(serie[3]);

				for (int i = 0; i < cboxCodRegime.getItems().size(); i++) {
					if (cboxCodRegime.getItems().get(i).getField().equals(String.valueOf(empresa.getRegimeTrib())))
						cboxCodRegime.getSelectionModel().select(i);

				}

				for (int i = 0; i < cboxCodNatureza.getItems().size(); i++) {
					if (cboxCodNatureza.getItems().get(i).getField().equals(empresa.getCodNatjuridica()))
						cboxCodNatureza.getSelectionModel().select(i);
				}

				for (int i = 0; i < cboxEnquadramento.getItems().size(); i++) {
					if (cboxEnquadramento.getItems().get(i).getField()
							.equals(String.valueOf(empresa.getCodEnquadraporte())))
						cboxEnquadramento.getSelectionModel().select(i);

				}

				for (int i = 0; i < cboxIndicTipoAtv.getItems().size(); i++) {
					if (cboxIndicTipoAtv.getItems().get(i).getField().equals(String.valueOf(empresa.getIndTipoativ())))
						cboxIndicTipoAtv.getSelectionModel().select(i);
				}
				btnSave.setDisable(false);
			} else {
				util.showAlert(DadosGlobais.resourceBundle.getString("alertErrorCodigoNull"), "error");
				btnSave.setDisable(true);
				Util.limpar(txtCodigo);
			}
		}

	}

	/**
	 * Fill comboBox cboxCodRegime and property definitions
	 */
	public void fillCboxCodRegime() {

		ObservableList<ComboBoxFilter> list = FXCollections.observableArrayList();
		list.add(new ComboBoxFilter("SIMPLES NACIONAL", 1, "1"));
		list.add(new ComboBoxFilter("SIMPLES NACIONAL – EXCESSO DE SUBLIMITE DE RECEITA BRUTA", 2, "2"));
		list.add(new ComboBoxFilter("REGIME NORMAL", 3, "3"));

		cboxCodRegime.getItems().addAll(list);

		cboxCodRegime.setConverter(new StringConverter<ComboBoxFilter>() {
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
	 * Fill comboBox cboxEnquadramento and property definitions
	 */
	public void fillCboxEnquadramento() {

		ObservableList<ComboBoxFilter> list = FXCollections.observableArrayList();
		list.add(new ComboBoxFilter("Grupo I - Grande", 1, "1"));
		list.add(new ComboBoxFilter("Grupo II - Grande", 2, "2"));
		list.add(new ComboBoxFilter("Grupo III - Média", 3, "3"));
		list.add(new ComboBoxFilter("Grupo IV - Média", 4, "4"));
		list.add(new ComboBoxFilter("Pequena", 5, "5"));
		list.add(new ComboBoxFilter("Microempresa", 6, "6"));

		cboxEnquadramento.getItems().addAll(list);

		cboxEnquadramento.setConverter(new StringConverter<ComboBoxFilter>() {
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
	 * Fill comboBox cboxCodNatureza and property definitions
	 */
	public void fillCboxCodNatureza() {

		ObservableList<ComboBoxFilter> list = FXCollections.observableArrayList();
		list.add(new ComboBoxFilter("ADMINISTRAÇÃO PÚBLICA", 0, "0"));
		list.add(new ComboBoxFilter("Órgão Público do Poder Executivo Federal", 0, "101-5"));
		list.add(new ComboBoxFilter("Órgão Público do Poder Executivo Estadual ou do Distrito Federal", 0, "102-3"));
		list.add(new ComboBoxFilter("Órgão Público do Poder Executivo Municipal", 0, "103-1"));
		list.add(new ComboBoxFilter("Órgão Público do Poder Legislativo Federal", 0, "104-0"));
		list.add(new ComboBoxFilter("Órgão Público do Poder Legislativo Estadual ou do Distrito Federal", 0, "105-8"));
		list.add(new ComboBoxFilter("Órgão Público do Poder Legislativo Municipal", 0, "106-6"));
		list.add(new ComboBoxFilter("Órgão Público do Poder Judiciário Federal", 0, "107-4"));
		list.add(new ComboBoxFilter("Órgão Público do Poder Judiciário Estadual", 0, "108-2"));
		list.add(new ComboBoxFilter("Autarquia Federal", 0, "110-4"));
		list.add(new ComboBoxFilter("Autarquia Estadual ou do Distrito Federal", 0, "111-2"));
		list.add(new ComboBoxFilter("Autarquia Municipal", 0, "112-0"));
		list.add(new ComboBoxFilter("Fundação Federal", 0, "113-9"));
		list.add(new ComboBoxFilter("Fundação Estadual ou do Distrito Federal", 0, "114-7"));
		list.add(new ComboBoxFilter("Fundação Municipal", 0, "115-5"));
		list.add(new ComboBoxFilter("Órgão Público Autônomo Federal", 0, "116-3"));
		list.add(new ComboBoxFilter("Órgão Público Autônomo Estadual ou do Distrito Federal", 0, "117-1"));
		list.add(new ComboBoxFilter("Órgão Público Autônomo Municipal", 0, "118-0"));
		list.add(new ComboBoxFilter("Comissão Polinacional", 0, "119-8"));
		list.add(new ComboBoxFilter("Fundo Público", 0, "120-1"));
		list.add(new ComboBoxFilter("Associação Pública", 0, "121-0"));
		list.add(new ComboBoxFilter("ENTIDADES EMPRESARIAIS", 0, "0"));
		list.add(new ComboBoxFilter("Empresa Pública", 0, "201-1"));
		list.add(new ComboBoxFilter("Sociedade de Economia Mista", 0, "203-8"));
		list.add(new ComboBoxFilter("Sociedade Anônima Aberta", 0, "204-6"));
		list.add(new ComboBoxFilter("Sociedade Anônima Fechada", 0, "205-4"));
		list.add(new ComboBoxFilter("Sociedade Empresária Limitada", 0, "206-2"));
		list.add(new ComboBoxFilter("Sociedade Empresária em Nome Coletivo", 0, "207-0"));
		list.add(new ComboBoxFilter("Sociedade Empresária em Comandita Simples", 0, "208-9"));
		list.add(new ComboBoxFilter("Sociedade Empresária em Comandita por Ações", 0, "209-7"));
		list.add(new ComboBoxFilter("Sociedade em Conta de Participação", 0, "212-7"));
		list.add(new ComboBoxFilter("Empresário (Individual)", 0, "213-5"));
		list.add(new ComboBoxFilter("Cooperativa", 0, "214-3"));
		list.add(new ComboBoxFilter("Consórcio de Sociedades", 0, "215-1"));
		list.add(new ComboBoxFilter("Grupo de Sociedades", 0, "216-0"));
		list.add(new ComboBoxFilter("Estabelecimento, no Brasil, de Sociedade Estrangeira", 0, "217-8"));
		list.add(new ComboBoxFilter("Estabelecimento, no Brasil, de Empresa Binacional Argentino-Brasileira", 0,
				"219-4"));
		list.add(new ComboBoxFilter("Empresa Domiciliada no Exterior", 0, "221-6"));
		list.add(new ComboBoxFilter("Clube/Fundo de Investimento", 0, "222-4"));
		list.add(new ComboBoxFilter("Sociedade Simples Pura", 0, "223-2"));
		list.add(new ComboBoxFilter("Sociedade Simples Limitada", 0, "224-0"));
		list.add(new ComboBoxFilter("Sociedade Simples em Nome Coletivo", 0, "225-9"));
		list.add(new ComboBoxFilter("Sociedade Simples em Comandita Simples", 0, "226-7"));
		list.add(new ComboBoxFilter("Empresa Binacional", 0, "227-5"));
		list.add(new ComboBoxFilter("Consórcio de Empregadores", 0, "228-3"));
		list.add(new ComboBoxFilter("Consórcio Simples", 0, "229-1"));
		list.add(new ComboBoxFilter("Empresa Individual de Responsabilidade Limitada (de Natureza Empresária)", 0,
				"230-5"));
		list.add(new ComboBoxFilter("Empresa Individual de Responsabilidade Limitada (de Natureza Simples)", 0,
				"231-3"));
		list.add(new ComboBoxFilter("ENTIDADES SEM FINS LUCRATIVOS", 0, "0"));
		list.add(new ComboBoxFilter("Serviço Notarial e Registral (Cartório)", 0, "303-4"));
		list.add(new ComboBoxFilter("Fundação Privada", 0, "306-9"));
		list.add(new ComboBoxFilter("Serviço Social Autônomo", 0, "307-7"));
		list.add(new ComboBoxFilter("Condomínio Edilício", 0, "308-5"));
		list.add(new ComboBoxFilter("Comissão de Conciliação Prévia", 0, "310-7"));
		list.add(new ComboBoxFilter("Entidade de Mediação e Arbitragem", 0, "311-5"));
		list.add(new ComboBoxFilter("Partido Político", 0, "312-3"));
		list.add(new ComboBoxFilter("Entidade Sindical", 0, "313-1"));
		list.add(new ComboBoxFilter("Estabelecimento, no Brasil, de Fundação ou Associação Estrangeiras", 0, "320-4"));
		list.add(new ComboBoxFilter("Fundação ou Associação domiciliada no exterior", 0, "321-2"));
		list.add(new ComboBoxFilter("Organização Religiosa", 0, "322-0"));
		list.add(new ComboBoxFilter("Comunidade Indígena", 0, "323-9"));
		list.add(new ComboBoxFilter("Fundo Privado", 0, "324-7"));
		list.add(new ComboBoxFilter("Associação Privada", 0, "399-9"));
		list.add(new ComboBoxFilter("PESSOAS FÍSICAS", 0, "0"));
		list.add(new ComboBoxFilter("Empresa Individual Imobiliária", 0, "401-4"));
		list.add(new ComboBoxFilter("Contribuinte Individual", 0, "408-1"));
		list.add(new ComboBoxFilter("Candidato a Cargo Político Eletivo", 0, "409-0"));
		list.add(new ComboBoxFilter("INSTITUIÇÕES EXTRATERRITORIAIS", 0, "0"));
		list.add(new ComboBoxFilter("Organização Internacional", 0, "501-0"));
		list.add(new ComboBoxFilter("Representação Diplomática Estrangeira", 0, "502-9"));
		list.add(new ComboBoxFilter("Outras Instituições Extraterritoriais", 0, "503-7"));

		cboxCodNatureza.getItems().addAll(list);

		cboxCodNatureza.setConverter(new StringConverter<ComboBoxFilter>() {
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
	 * Fill comboBox cboxIndicTipoAtv and property definitions
	 */
	public void fillCboxIndicTipoAtv() {

		ObservableList<ComboBoxFilter> list = FXCollections.observableArrayList();
		list.add(new ComboBoxFilter("Industrial ou Equiparado a Industrial", 0, "0"));
		list.add(new ComboBoxFilter("Outros", 0, "1"));

		cboxIndicTipoAtv.getItems().addAll(list);

		cboxIndicTipoAtv.setConverter(new StringConverter<ComboBoxFilter>() {
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
	 * Fill comboBox cboxFilterColumn and property definitions
	 */
	public void cokFillFilterColumn() {

		listComboBoxFilter.add(new ComboBoxFilter("Código", 0, "codigo"));
		listComboBoxFilter.add(new ComboBoxFilter("Razão Social", 1, "razaoSocial"));
		listComboBoxFilter.add(new ComboBoxFilter("Nome de Fantasia", 2, "nomeFantasia"));
		listComboBoxFilter.add(new ComboBoxFilter("CNPJ", 3, "cnpj"));

		cboxFilterColumn.getItems().addAll(listComboBoxFilter);
		cboxFilterColumn.getSelectionModel().selectFirst();

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

		xml.WriteXMLColumns(userDefinedColumnList, "Grid-Empresa");

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
	public void updateTableView(ObservableList visibleColumns, TableView<DepartamentoDAO> table) {

		if (!visibleColumns.isEmpty()) {
			// hide all columns
			for (TableColumn<DepartamentoDAO, ?> col : table.getColumns())
				col.setVisible(false);
			// show column and defines its position
			for (int i = 0; i < visibleColumns.size(); i++) {
				table.getColumns().remove(visibleColumns.get(i));
				table.getColumns().add(i, (TableColumn<DepartamentoDAO, ?>) visibleColumns.get(i));
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

		ArrayList<UserDefinedColumn> userDefinedColumns = xmlTableColumns.ReadXMLColumns("Grid-Empresa");

		if (userDefinedColumns != null) {

			for (TableColumn col : tbView.getColumns())
				col.setVisible(false);

			for (int i = 0; i < userDefinedColumns.size(); i++) {
				for (int j = 0; j < tbView.getColumns().size(); j++) {
					if (tbView.getColumns().get(j).getId().equals(userDefinedColumns.get(i).getName())) {
						TableColumn<Empresa, ?> tableColumn = tbView.getColumns().get(j);
						tbView.getColumns().remove(j);
						tbView.getColumns().add(userDefinedColumns.get(i).getPosition(), tableColumn);
						tableColumn.setPrefWidth(userDefinedColumns.get(i).getWidth());
						tableColumn.setVisible(true);
					}
				}
			}
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

			Object type = Empresa.class
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
					new PrintExportController(tbView, tableShowPrintList, "Empresa", pBar, stg)));
			stg.setScene(scene);
			stg.initStyle(StageStyle.TRANSPARENT);
			stg.initModality(Modality.APPLICATION_MODAL);
			stg.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		// System.out.println(UUID.randomUUID());
		// Definition of columns
		tbcolCodigo.setCellValueFactory(new PropertyValueFactory<Empresa, Integer>("codigo"));
		tbcolRazaoSocial.setCellValueFactory(new PropertyValueFactory<Empresa, String>("razaoSocial"));
		tbcolNomeFant.setCellValueFactory(new PropertyValueFactory<Empresa, String>("nomeFantasia"));
		tbcolCnpj.setCellValueFactory(new PropertyValueFactory<Empresa, String>("cnpj"));
		tbcolAbreviat.setCellValueFactory(new PropertyValueFactory<Empresa, String>("descReduzida"));
		tbcolEndereco.setCellValueFactory(new PropertyValueFactory<Empresa, String>("endereco"));
		tbcolNumero.setCellValueFactory(new PropertyValueFactory<Empresa, String>("endNumero"));
		tbcolBairro.setCellValueFactory(new PropertyValueFactory<Empresa, String>("bairro"));
		tbcolCidade.setCellValueFactory(new PropertyValueFactory<Empresa, String>("cidade"));
		tbcolUF.setCellValueFactory(new PropertyValueFactory<Empresa, String>("uf"));
		tbcolCep.setCellValueFactory(new PropertyValueFactory<Empresa, String>("cep"));
		tbcolFone.setCellValueFactory(new PropertyValueFactory<Empresa, String>("fone"));
		tbcolCelular.setCellValueFactory(new PropertyValueFactory<Empresa, String>("celular"));
		tbcolInscricEstad.setCellValueFactory(new PropertyValueFactory<Empresa, String>("ie"));
		tbcolInscricMunic.setCellValueFactory(new PropertyValueFactory<Empresa, String>("incMunicipal"));
		tbcolSuframa.setCellValueFactory(new PropertyValueFactory<Empresa, String>("suframa"));
		tbcolWeb.setCellValueFactory(new PropertyValueFactory<Empresa, String>("webHomepage"));
		tbcolCnaeFiscal.setCellValueFactory(new PropertyValueFactory<Empresa, String>("cnaeFiscal"));
		tbcolLinhaAdicional.setCellValueFactory(new PropertyValueFactory<Empresa, String>("linhaAdicional"));
		tbcolNumSerie.setCellValueFactory(new PropertyValueFactory<Empresa, String>("noSerie"));

		// Configure columns tableView
		configTableView();
		// Fill comboBox cboxFilterColumn
		cokFillFilterColumn();
		// Fill comboBox CboxCodRegime
		fillCboxCodRegime();
		// Fill comboBox cboxEnquadramento
		fillCboxEnquadramento();
		// Fill comboBox codNatureza
		fillCboxCodNatureza();
		// Fill comboBox cboxIndicTipoAtv
		fillCboxIndicTipoAtv();
		// Fill comboBox cboxUF
		ComboBoxUF cboxUf = new ComboBoxUF(cboxUF);
		// Set default style of Node on in event onClick
		Util.defaultStyle(txtCodigo, txtAbreviat, txtEndereco, txtBairro, txtCep);
		// CHAMADA A FUNÇÃO QUE SETA O ESTILO DEFAULT AOS CAMPOS TEXTFIELDS
		Util.setKeyPressDefaultStyles(txtAbreviat, txtEndereco, txtBairro, txtCep, txtFone, txtCelular);
		Util.setStyleOnFocus(txtFilterColumn,txtCodigo, txtRazaoSocial, txtNomeFant, txtAbreviat, txtEndereco, txtNumero, txtBairro, txtCidade, txtCep, txtFone,
				txtCelular, txtCnpj, txtInscricEstad, txtInscricMunic, txtSuframa, txtPagIntern, txtCnaeFiscal, txtLinhaAdicional, txtNomeExib, txtEmail, txtServSaida,
				txtPortaS, txtLogin, txtSenha);
		
		// CHAMADA A FUNÇÃO QUE SETA O ESTILO DEFAULT AOS CAMPOS TEXTFIELDS
		Util.onlyNumbers(txtCodigo, txtNumero, txtCep, txtFone, txtCelular, txtCnaeFiscal, txtPortaS);

		// Always write in capital letters
		Util.whriteUppercase(txtFilterColumn, txtCodigo, txtAbreviat, txtEndereco, txtBairro, txtCep, txtFone,
				txtCelular, txtInscricEstad, txtInscricMunic, txtSuframa, txtCnaeFiscal, txtLinhaAdicional);
		// Always write in lowerCase
		Util.whriteLowercase(txtPagIntern, txtEmail);
		// Limit the number of characters
		Util.maxCharacters(8, txtCodigo);
		Util.maxCharacters(4, txtPortaS);
		Util.maxCharacters(10, txtCep);
		Util.maxCharacters(14, txtFone);
		Util.maxCharacters(16, txtCelular);

		Util.mascaraTelefone(txtFone);

		Util.customSearchTextField("right",null, txtFilterColumn);

		Util.setNextFocus(txtEndereco, txtNumero, txtBairro, txtCep, txtFone, txtCelular, txtInscricEstad,
				txtInscricMunic, txtSuframa, txtPagIntern, txtCnaeFiscal, txtLinhaAdicional, txtNomeExib, txtEmail,
				txtServSaida, txtPortaS, txtLogin, txtSenha);

		// Validation for when a tableColum resizes or changes index
		tbView.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
			if (event.getTarget() instanceof TableHeaderRow || event.getTarget() instanceof Rectangle)
				saveColumnSettings();
		});

		// // Event handle onSelected TabPane tabDetalhes
		// tabPane.getSelectionModel().selectedIndexProperty().addListener(new
		// ChangeListener<Number>() {
		// @Override
		// public void changed(ObservableValue<? extends Number> ov, Number
		// oldValue, Number newValue) {
		// if (newValue.equals(1)) {
		// onChangeAba(1, true, true);
		// btnCancel.setDisable(false);
		// }
		// }
		// });

		tbView.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode().equals(KeyCode.ENTER)
					&& tbView.getSelectionModel().getSelectedItem().getFlagAtivo().equals(1)) {
				anchorPaneListagem.setDisable(true);
				anchorPaneDetalhes.setVisible(true);
				txtCodigo.requestFocus();
				cargarDatosEmpresa("only", false, tbView.getSelectionModel().getSelectedItem().getCodigo());

			} else if (event.getCode().equals(KeyCode.ESCAPE)) {
				txtFilterColumn.requestFocus();
			} else if (event.getCode().equals(KeyCode.CONTEXT_MENU)) {

				// // ESTANCIA UM NOVO CONTEXTMENU
				// contextMenu = new ContextMenu();
				// // ITEM 1 DO MENU DE AÇOES - ALTERAR UM REGISTRO
				// MenuItem itemAtualizar = new
				// MenuItem(DadosGlobais.resourceBundle.getString("actionAtualizar"));
				// itemAtualizar.setOnAction(new EventHandler<ActionEvent>() {
				// @Override
				// public void handle(ActionEvent event) {
				// Util.abreTelaCadastro(anchorPaneListagem,
				// anchorPaneDetalhes);
				// txtCodigo.requestFocus();
				// cargarDatosEmpresa("only", false,
				// tbView.getSelectionModel().getSelectedItem().getCodigo());
				// }
				// });
				//
				// contextMenu.getItems().addAll(itemAtualizar);
				// tbView.setContextMenu(contextMenu);
				//
				// contextMenu.show(null);

				System.out.println(123);

			}
		});

		tbView.setRowFactory(tb -> {
			// PROPIEDADES DAS LINAS DO TABLEVIEW, ADICIONA EVENTOS AO MANUSEAR
			// AS LINHASD DO TABLEVIEW

			// EXIBE O EFEITO DE OPACIDADE NAS LINHAS CASO O ITEM DO TABLEVIEW
			// TENHA O FLAGATIVO = 0
			TableRow<Empresa> row = new TableRow<Empresa>() {
				@Override
				public void updateItem(Empresa objeto, boolean empty) {
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

			// // ESTANCIA UM NOVO CONTEXTMENU
			// contextMenu = new ContextMenu();
			// // ITEM 1 DO MENU DE AÇOES - ALTERAR UM REGISTRO
			// MenuItem mAtualizar = new
			// MenuItem(DadosGlobais.resourceBundle.getString("actionAtualizar"));
			// mAtualizar.setOnAction(new EventHandler<ActionEvent>() {
			// @Override
			// public void handle(ActionEvent event) {
			// Util.abreTelaCadastro(anchorPaneListagem, anchorPaneDetalhes);
			// txtCodigo.requestFocus();
			// cargarDatosEmpresa("only", false,
			// tbView.getSelectionModel().getSelectedItem().getCodigo());
			// }
			// });
			// contextMenu.getItems().addAll(mAtualizar);
			// tbView.setContextMenu(contextMenu);

			// row.contextMenuProperty().bind(
			// Bindings.when(row.emptyProperty())
			// .then((ContextMenu)null)
			// .otherwise(contextMenu)
			// );

			// row.contextMenuProperty().bind(
			// Bindings.when(Bindings.isNotNull(row.itemProperty()))
			// .then(contextMenu)
			// .otherwise((ContextMenu)null));

			row.setOnMouseClicked(tr -> {

				if (tr.getClickCount() >= 2 && (!row.isEmpty()) && tr.getButton().equals(MouseButton.PRIMARY)
						&& tbView.getItems().get(0).getCodigo() != null) {
					// onChangeAba(1, false, false);
					anchorPaneDetalhes.setVisible(true);
					txtCodigo.requestFocus();
					cargarDatosEmpresa("only", false, tbView.getSelectionModel().getSelectedItem().getCodigo());
				}

				if (tr.getButton().equals(MouseButton.SECONDARY)) {

					// ESTANCIA UM NOVO CONTEXTMENU
					contextMenu = new ContextMenu();
					// ITEM 1 DO MENU DE AÇOES - ALTERAR UM REGISTRO
					MenuItem itemAtualizar = new MenuItem(DadosGlobais.resourceBundle.getString("actionAtualizar"));
					itemAtualizar.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							Util.openFormCadastro(anchorPaneListagem, anchorPaneDetalhes, 0);
							txtCodigo.requestFocus();
							cargarDatosEmpresa("only", false, tbView.getSelectionModel().getSelectedItem().getCodigo());
						}
					});

					contextMenu.getItems().addAll(itemAtualizar);
					tbView.setContextMenu(contextMenu);
				}
			});

			return row;
		});

		// Event onFocus txtDescricao TextField
		txtCodigo.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if (!newValue)
					if (!Util.noEmpty(txtCodigo))
						cargarDatosEmpresa("only", false, Integer.valueOf(txtCodigo.getText()));
			}
		});

		// Set focus to txtCodigo
		Util.setFocus(txtFilterColumn);

		// TECLAS DE ATALHOS PARA TOOLBAR
		anchorPanePrincipal.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			switch (event.getCode()) {
			case F2:
				if (txtCodigo.isFocused() && anchorPaneDetalhes.isVisible()) {
					actionBtnClose(null);
					flagPaneFilter = false;
					actionBtnFilter(null);
				} else {
					flagPaneFilter = false;
					actionBtnFilter(null);
				}
				break;

			case F4:
				actionBtnRefresh(null);
				break;

			case F6:
				if (anchorPaneDetalhes.isVisible() && !btnSave.isDisable())
					if (!btnSave.isDisable())
						actionBtnSave(null);
				break;

			case F7:
				if (anchorPaneDetalhes.isVisible() && !btnCancel.isDisable())
					actionBtnCancel(null);
				break;

			case P:
				if (event.isControlDown())
					try {
						if (!btnPrint.isDisable())
							actionBtnPrint(null);
					} catch (Exception e) {
						e.printStackTrace();
					}
				break;

			case ESCAPE:

				if (anchorPaneDetalhes.isVisible() && !btnCancel.isDisable()) {
					btnSave.setDisable(true);
					txtCodigo.requestFocus();
				}

			default:
				break;
			}
		});

	}

}
