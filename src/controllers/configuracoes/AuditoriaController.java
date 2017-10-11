package controllers.configuracoes;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.textfield.CustomTextField;

import com.sun.javafx.scene.control.skin.TableHeaderRow;

import application.DadosGlobais;
import controllers.compras.DepartamentoController;
import controllers.utils.ComboBoxData;
import controllers.utils.ConfigColumnController;
import controllers.utils.PrintExportController;
import controllers.utils.ProgressBarForm;
import javafx.util.Callback;
import javafx.util.StringConverter;
import models.compras.Departamento;
import models.compras.DepartamentoDAO;
import models.configuracoes.Auditoria;
import models.configuracoes.AuditoriaDAO;
import models.configuracoes.Usuario;
import tools.controls.ComboBoxFilter;
import tools.reports.TableConfPrint;
import tools.utils.Util;
import tools.xml.UserDefinedColumn;
import tools.xml.XMLTableColumns;

public class AuditoriaController implements Initializable {

	// SECTION WITH THE DECLARATION OF THE GRAPHIC INTERFACE ELEMENTS CREATED IN
	// FXML
	@FXML
	private AnchorPane anchorPanePrincipal;
	@FXML
	private ToolBar toolBar;
	@FXML
	private Button btnRefresh, btnFilter, btnPrint, btnConfig;
	@FXML
	private ToggleButton toggleBtnHelp;
	@FXML
	private SplitPane splitPaneFilter;
	@FXML
	private CheckComboBox<ComboBoxFilter> cboxTipoOperacion;
	@FXML
	private ComboBox<ComboBoxFilter> cboxFiltros;
	@FXML
	private ComboBox<String> cboxPeriodo;
	@FXML
	private CustomTextField txtFilterColumn;
	@FXML
	private DatePicker dateInicial, dateFinal;
	@FXML
	private ProgressBar pBar;
	@FXML
	private TableView<Auditoria> tbView;
	@FXML
	private TableColumn<Auditoria, Integer> tbColCodemp;
	@FXML
	private TableColumn<Auditoria, Integer> tbColCodUsuario;
	@FXML
	private TableColumn<Auditoria, String> tbColUsuario;
	@FXML
	private TableColumn<Auditoria, String> tbColSistema;
	@FXML
	private TableColumn<Auditoria, String> tbColTipoOperacion;
	@FXML
	private TableColumn<Auditoria, String> tbColTipoRegistro;
	@FXML
	private TableColumn<Auditoria, LocalDateTime> tbColDataMovt;
	@FXML
	private TableColumn<Auditoria, String> tbColValAnterior;
	@FXML
	private TableColumn<Auditoria, String> tbColValNovo;
	@FXML
	private TableColumn<Auditoria, String> tbColHistorico;
	@FXML
	private TableColumn<Auditoria, String> tbColDocCodigo;
	@FXML
	private Label lblTotalLinhas, lblNumLinhas;
	// -----------------------------------------------------------------------------------------------
	@FXML
	Label lblTipoOperacao, lblPeriodo, lblDataIni, lblDataFin, lblBuscarPor;

	// Attributes used in the class
	private ObservableList<Auditoria> dataAuditoria = null;
	Util util = new Util();
	AuditoriaDAO auDao = new AuditoriaDAO();
	static Stage stg;
	private final LocalDate today = LocalDate.now();
	public List<TableConfPrint> tableShowPrintList = new ArrayList<TableConfPrint>();

	boolean flagRastreioUsuario = false;
	Integer codUsuarioRastreio;

	// Date formatter
	private String pattern = "dd/MM/yyyy";
	// Initial status of filter panel
	boolean flagPaneFilter = true;

	public AuditoriaController() {

		// TODO Auto-generated constructor stub
	}

	public AuditoriaController(Object arg, Integer initCodUsuario) {

		if (arg instanceof Usuario && initCodUsuario > 0) {

			flagRastreioUsuario = true;
			this.codUsuarioRastreio = initCodUsuario;

		}

	}

	@FXML
	void actionBtnConfig(ActionEvent event) {
		try {
			stg = new Stage();
			Scene scene = new Scene(util.openWindow("/views/utils/viewConfigColumns.fxml",
					new ConfigColumnController(AuditoriaController.class, tbView, "Grid-Auditoria")));
			scene.setFill(Color.TRANSPARENT);
			stg.initStyle(StageStyle.TRANSPARENT);
			stg.setScene(scene);
			stg.initModality(Modality.APPLICATION_MODAL);
			stg.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void actionTxtFilterColumn(ActionEvent event) {

		List<String> tipoOperacion = new ArrayList<String>();
		// tipoOperacion.addAll(cboxTipoOperacion.getCheckModel().getCheckedItems().);
		for (ComboBoxFilter cbox : cboxTipoOperacion.getCheckModel().getCheckedItems()) {
			tipoOperacion.add(cbox.getField());
		}

		List<LocalDate> data = new ArrayList<LocalDate>();
		data.add(dateInicial.getValue());
		data.add(dateFinal.getValue());

		Task<String> tarefaCargaPg = new Task<String>() {
			@Override
			protected String call() throws Exception {
				dataAuditoria = FXCollections.observableArrayList(auDao.filterAuditoria(
						cboxFiltros.getValue().getField(), txtFilterColumn.getText(), tipoOperacion, data));
				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();
				pBar.setProgress(1);
				tbView.setItems(dataAuditoria);
				lblNumLinhas.setText(String.valueOf(tbView.getItems().size()));
				if (tbView.getItems().size() > 0)
					btnPrint.setDisable(false);
				else {
					util.showAlert(DadosGlobais.resourceBundle.getString("alertInfRegisterNull"),"error");
					btnPrint.setDisable(true);
				}
			}

			@Override
			protected void failed() {
				stg.close();
				util.showAlert(DadosGlobais.resourceBundle.getString("alertErrorCnxDb") + exceptionProperty().getValue(),"error");
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
		stg = ProgressBarForm.showProgressBar(AuditoriaController.class, tarefaCargaPg,
				DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
		pBar.setProgress(-1);
	}

	@FXML
	void actionBtnFilter(ActionEvent event) {

		if (flagPaneFilter) {
			flagPaneFilter = false;
			splitPaneFilter.setDividerPositions(0);
		} else {
			splitPaneFilter.setDividerPositions(1);
			flagPaneFilter = true;
		}
	}

	@FXML
	void actionBtnPrint(ActionEvent event) throws NoSuchFieldException, SecurityException {
		printExportShow();
	}

	@FXML
	void actionBtnRefresh(ActionEvent event) {

		if (util.showAlert(DadosGlobais.resourceBundle.getString("alertConfirShowAllData"),"confirmation")) {
			Task<String> tarefaCargaPg = new Task<String>() {
				@Override
				protected String call() throws Exception {
					dataAuditoria = FXCollections.observableArrayList(auDao.getListAuditoria());
					return "-";
				}

				@Override
				protected void succeeded() {
					stg.close();
					pBar.setProgress(1);
					tbView.setItems(dataAuditoria);
					lblNumLinhas.setText(String.valueOf(tbView.getItems().size()));
					if (tbView.getItems().size() > 0)
						btnPrint.setDisable(false);
					else
						btnPrint.setDisable(true);
				}

				@Override
				protected void failed() {
					stg.close();
					util.showAlert(DadosGlobais.resourceBundle.getString("alertErrorCnxDb"),"error");
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
			stg = ProgressBarForm.showProgressBar(AuditoriaController.class, tarefaCargaPg,
					DadosGlobais.resourceBundle.getString("infConsulRegist"), false);
			pBar.setProgress(-1);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@FXML
	void keyReleasedTxtFilterColumn(KeyEvent event) {

		if (dataAuditoria != null) {
			if (txtFilterColumn.textProperty().get().isEmpty()) {
				tbView.setItems(dataAuditoria);
				lblNumLinhas.setText(String.valueOf(tbView.getItems().size()));
				if (tbView.getItems().size() > 0)
					btnPrint.setDisable(false);
				else
					btnPrint.setDisable(true);
				return;
			}
			ObservableList tableItems = FXCollections.observableArrayList();
			ObservableList cols = tbView.getVisibleLeafColumns();

			for (int i = 0; i < dataAuditoria.size(); i++) {
				for (int j = 0; j < cols.size(); j++) {
					TableColumn col = (TableColumn) cols.get(j);
					String cellValue = col.getCellData(dataAuditoria.get(i)).toString();
					if (cellValue.toLowerCase().contains(txtFilterColumn.textProperty().get().toLowerCase())) {
						tableItems.add(dataAuditoria.get(i));
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

	/**
	 * ComboBox Filer.
	 */
	public void fillCbkFiltros() {

		ObservableList<ComboBoxFilter> filtros = FXCollections.observableArrayList();
		filtros.add(new ComboBoxFilter(
				DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.valAnterior"), 0,
				"valorAnterior"));
		filtros.add(new ComboBoxFilter(
				DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.novoVal"), 0,
				"valorNovo"));
		filtros.add(new ComboBoxFilter(
				DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.tipoRegist"), 0,
				"tipoRegistro"));
		filtros.add(new ComboBoxFilter(
				DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.historico"), 0,
				"historico"));
		filtros.add(new ComboBoxFilter(
				DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.empresa"), 1,
				"codemp"));
		filtros.add(new ComboBoxFilter(
				DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.docCode"), 0,
				"docCodigo"));
		filtros.add(new ComboBoxFilter(
				DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.codigoUser"), 1,
				"codUsuario"));
		filtros.add(new ComboBoxFilter(
				DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.nomeUser"), 1,
				"nomeUsuario"));

		cboxFiltros.getItems().addAll(filtros);

		cboxFiltros.setConverter(new StringConverter<ComboBoxFilter>() {
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

	/**
	 * ComboBox Tipo Operacion.
	 */
	public void fillTipoOperacion() {

		// ArrayList to fill comboBox cbkTipoOperacion
		ObservableList<ComboBoxFilter> operacion = FXCollections.observableArrayList();
		operacion.add(new ComboBoxFilter(
				DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.cancelamento"), 0,
				DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.cancelamento")));
		operacion.add(new ComboBoxFilter(
				DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.alteracao"), 1,
				DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.alteracao")));
		operacion.add(new ComboBoxFilter(
				DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.exclusao"), 2,
				DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.exclusao")));
		operacion.add(new ComboBoxFilter(
				DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.reativacao"), 3,
				DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.reativacao")));

		cboxTipoOperacion.getItems().addAll(operacion);

		cboxTipoOperacion.setConverter(new StringConverter<ComboBoxFilter>() {
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

			Object type = Auditoria.class
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
			Scene scene = new Scene(util.openWindow("/views/utils/viewPrintExport.fxml",
					new PrintExportController(tbView, tableShowPrintList, "Auditoria", pBar, stg)));
			stg.setScene(scene);
			stg.initStyle(StageStyle.TRANSPARENT);
			stg.initModality(Modality.APPLICATION_MODAL);
			stg.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@SuppressWarnings({ "rawtypes" })
	/**
	 * Configure the columns and define columns properties, those columns are
	 * stored in a file.
	 */
	public void configTableView() {
		XMLTableColumns xmlTableColumns = new XMLTableColumns();

		ArrayList<UserDefinedColumn> userDefinedColumns = xmlTableColumns.ReadXMLColumns("Grid-Auditoria");

		if (userDefinedColumns != null) {

			for (TableColumn col : tbView.getColumns())
				col.setVisible(false);

			for (int i = 0; i < userDefinedColumns.size(); i++) {
				for (int j = 0; j < tbView.getColumns().size(); j++) {
					if (tbView.getColumns().get(j).getId().equals(userDefinedColumns.get(i).getName())) {
						TableColumn<Auditoria, ?> tableColumn = tbView.getColumns().get(j);
						tbView.getColumns().remove(j);
						tbView.getColumns().add(userDefinedColumns.get(i).getPosition(), tableColumn);
						tableColumn.setPrefWidth(userDefinedColumns.get(i).getWidth());
						tableColumn.setVisible(true);
					}
				}
			}
		}
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
	public void updateTableView(ObservableList visibleColumns, TableView<Auditoria> table) {

		if (!visibleColumns.isEmpty()) {
			// hide all columns
			for (TableColumn<Auditoria, ?> col : table.getColumns())
				col.setVisible(false);
			// show column and defines its position
			for (int i = 0; i < visibleColumns.size(); i++) {
				table.getColumns().remove(visibleColumns.get(i));
				table.getColumns().add(i, (TableColumn<Auditoria, ?>) visibleColumns.get(i));
				table.getColumns().get(i).setVisible(true);
			}

		}

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
		xml.WriteXMLColumns(userDefinedColumnList, "Grid-Auditoria");
	}

	public void translations() {

		btnRefresh.setTooltip(new Tooltip(DadosGlobais.resourceBundle.getString("toolTipRefresh")));
		btnFilter.setTooltip(new Tooltip(DadosGlobais.resourceBundle.getString("toolTipFilter")));
		btnPrint.setTooltip(new Tooltip(DadosGlobais.resourceBundle.getString("toolTipPrint")));
		btnConfig.setTooltip(new Tooltip(DadosGlobais.resourceBundle.getString("toolTipConfig")));
		toggleBtnHelp.setTooltip(new Tooltip(DadosGlobais.resourceBundle.getString("toolTipHelp")));

		lblTipoOperacao.setText(
				DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.lblTipoOperacao"));
		lblPeriodo.setText(
				DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.lblPeriodo"));
		lblDataIni.setText(
				DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.lblDataIni"));
		lblDataFin.setText(
				DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.lblDataFin"));
		lblBuscarPor.setText(
				DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.lblBuscarPor"));
		lblTotalLinhas.setText(DadosGlobais.resourceBundle.getString("lblTotalLinhas"));

		tbColCodemp.setText(DadosGlobais.resourceBundle.getString("tbColCodeEmp"));
		tbColCodUsuario.setText(
				DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.tbColCodUsuario"));
		tbColUsuario.setText(
				DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.tbColUsuario"));
		tbColSistema.setText(
				DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.tbColSistema"));
		tbColTipoOperacion.setText(DadosGlobais.resourceBundle
				.getString("controllers.configuracoes.AuditoriaController.tbColTipoOperacion"));
		tbColTipoRegistro.setText(DadosGlobais.resourceBundle
				.getString("controllers.configuracoes.AuditoriaController.tbColTipoRegistro"));
		tbColDataMovt.setText(
				DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.tbColDataMovt"));
		tbColValAnterior.setText(DadosGlobais.resourceBundle
				.getString("controllers.configuracoes.AuditoriaController.tbColValAnterior"));
		tbColValNovo.setText(
				DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.tbColValNovo"));
		tbColHistorico.setText(
				DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.tbColHistorico"));
		tbColDocCodigo.setText(
				DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.tbColDocCodigo"));

		tbView.setPlaceholder(new Label(DadosGlobais.resourceBundle.getString("tbView")));
	}

	@SuppressWarnings({ "unchecked", "restriction" })
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Set values to DatePickers
		dateInicial.setValue(today);
		dateFinal.setValue(today);

		// Set idioma
		translations();

		// Fill comboBoxs
		// cboxTipoOperacion.getItems().addAll(listTipoOperacion);
		fillTipoOperacion();
		fillCbkFiltros();
		cboxFiltros.getSelectionModel().select(3);
		
		Util.customSearchTextField("right",null, txtFilterColumn);

		@SuppressWarnings("unused")
		ComboBoxData cboxData = new ComboBoxData(cboxPeriodo, dateInicial, dateFinal);

		Util.whriteUppercase(txtFilterColumn);

		// Definition of columns
		tbColCodemp.setCellValueFactory(new PropertyValueFactory<Auditoria, Integer>("codemp"));
		tbColCodUsuario.setCellValueFactory(new PropertyValueFactory<Auditoria, Integer>("codUsuario"));
		tbColUsuario.setCellValueFactory(new PropertyValueFactory<Auditoria, String>("nomeUsuario"));
		tbColSistema.setCellValueFactory(new PropertyValueFactory<Auditoria, String>("sistema"));
		tbColTipoOperacion.setCellValueFactory(new PropertyValueFactory<Auditoria, String>("tipoOperacao"));
		tbColTipoRegistro.setCellValueFactory(new PropertyValueFactory<Auditoria, String>("tipoRegistro"));
		tbColDataMovt.setCellValueFactory(new PropertyValueFactory<Auditoria, LocalDateTime>("dtMovto"));
		tbColValAnterior.setCellValueFactory(new PropertyValueFactory<Auditoria, String>("valorAnterior"));
		tbColValNovo.setCellValueFactory(new PropertyValueFactory<Auditoria, String>("valorNovo"));
		tbColHistorico.setCellValueFactory(new PropertyValueFactory<Auditoria, String>("historico"));
		tbColDocCodigo.setCellValueFactory(new PropertyValueFactory<Auditoria, String>("docCodigo"));

		// Configure columns tableView
		configTableView();

		// SETA O FOCO INICIAL NO TEXTFIELD DA ABA DE FILTROS
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				txtFilterColumn.requestFocus();
			}
		});

		// PROPIEDADES DAS LINAS DO TABLEVIEW, ADICIONA EVENTOS AO MANUSEAR AS
		// LINHASD DO TABLEVIEW
		tbView.setRowFactory(tb -> {
			// EXIBE O EFEITO DE OPACIDADE NAS LINHAS CASO O ITEM DO TABLEVIEW
			// TENHA O FLAGATIVO = 0
			TableRow<Auditoria> row = new TableRow<Auditoria>() {
				@Override
				public void updateItem(Auditoria objeto, boolean empty) {
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
			return row;
		});

		/*
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

		dateInicial.setConverter(converter);
		dateFinal.setConverter(converter);

		// Color of the selected date range in DatePicker
		Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);
						if (item.isAfter(dateInicial.getValue()) && item.isBefore(dateFinal.getValue())
								|| item.isEqual(dateInicial.getValue()) || item.isEqual(dateFinal.getValue()))
							setStyle("-fx-background-color: #29B6F6;");
					}
				};
			}
		};

		dateInicial.setDayCellFactory(dayCellFactory);
		dateFinal.setDayCellFactory(dayCellFactory);		
		*/

		// Validation for when a tableColum resizes or changes index
		tbView.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
			if (event.getTarget() instanceof TableHeaderRow || event.getTarget() instanceof Rectangle)
				saveColumnSettings();

		});

		// Formatter column date
		tbColDataMovt.setCellFactory((TableColumn<Auditoria, LocalDateTime> column) -> {
			return new TableCell<Auditoria, LocalDateTime>() {
				@Override
				protected void updateItem(LocalDateTime item, boolean empty) {
					super.updateItem(item, empty);
					if (item == null || empty) {
						setText(null);
					} else {
						setText(Util.formataDataHoraString(item));
					}
				}
			};
		});

		// TECLAS DE ATALHOS PARA TOOLBAR
		anchorPanePrincipal.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			switch (event.getCode()) {
			case F4:
				actionBtnRefresh(null);
				break;

			case F2:
				flagPaneFilter = false;
				actionBtnFilter(null);

				break;

			default:
				break;
			}
		});

		if (flagRastreioUsuario) {

			txtFilterColumn.setText(codUsuarioRastreio.toString());

			// SELECIONA O IDIOMA DE ACORDO COM QUE ESTA PREENCHIDO NO
			// USUARIO
			for (int i = 0; i < cboxFiltros.getItems().size(); i++) {
				if (cboxFiltros.getItems().get(i).getField().equals("codUsuario")) {
					cboxFiltros.getSelectionModel().select(i);
					break;
				}
			}

			// SELECIONA INTERVALO DE DATA COMO TODOS PARA CONSULTA DE LOG DO
			// USUARIO ATRAVES DO RASTREIO
			cboxPeriodo.getSelectionModel().selectLast();

			actionTxtFilterColumn(null);

		}

	}

	// public class Combobox {
	// String value;
	// int action;
	// String field;
	//
	// public Combobox(String value, int action, String field) {
	// super();
	// this.value = value;
	// this.action = action;
	// this.field = field;
	// }
	//
	// public String getValue() {
	// return value;
	// }
	//
	// public int getAction() {
	// return action;
	// }
	//
	// public String getField() {
	// return field;
	// }
	// }

}
