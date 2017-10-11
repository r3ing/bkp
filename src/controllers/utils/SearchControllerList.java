package controllers.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import javax.swing.text.StyledEditorKit.BoldAction;

import org.controlsfx.control.textfield.CustomTextField;

import application.DadosGlobais;
import controllers.configuracoes.CompartilharEmpresasController;
import controllers.configuracoes.UsuarioController;

import controllers.utils.ComboBoxData.CboxData;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener.Change;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import models.compras.OperacaoEntrada1;
import models.configuracoes.Empresa;
import net.sf.jasperreports.components.barbecue.BarcodeProviders.NW7Provider;
import tools.controls.CheckTableView;
import tools.controls.ComboBoxFilter;
import tools.utils.Util;

public class SearchControllerList implements Initializable {

	@FXML
	private AnchorPane anpSearch, anpForm;

	@FXML
	private Button btnClose, btnOk;

	@FXML
	private Label lblTitle;

	@FXML
	private Label lblSearch;

	@FXML
	private CustomTextField txtSearch;

	@FXML
	private ComboBox<ComboBoxFilter> cboxSearch;

	@SuppressWarnings("rawtypes")
	@FXML
	private TableView tbView;

	@FXML
	private ProgressBar pBar;

	@FXML
	private HBox hBox;

	// ------------------- ELEMENTOS CONTIDOS NO FORMULARIO FXML
	// ------------------------

	// ATRIBUTOS DA CLASSE
	private ObservableList<ComboBoxFilter> listCboxFilter = FXCollections.observableArrayList();
	private String title = null;
	private String selectedItemBox = null;
	private List<Integer> paramFlagAtivo = Arrays.asList(1);

	@SuppressWarnings("rawtypes")
	private Class classDAO;

	private static Stage stg;
	private Util util = new Util();
	private List list;
	public static Object obj;
	private ObservableList data = FXCollections.observableArrayList();
	private static boolean methodType = false;

	private ObservableList<SelectedItem> selectedItem = FXCollections.observableArrayList();

	@SuppressWarnings({ "unused", "rawtypes" })
	private ObservableList tempItems = FXCollections.observableArrayList();

	@FXML
	void actionTxtSearch(ActionEvent event) {

		Task<String> TarefaRefresh = new Task<String>() {
			@SuppressWarnings("unchecked")
			@Override
			protected String call() throws Exception {
				Method method;

				if (Util.flagTrasnportadora) {
					method = classDAO.getMethod("filterTrasnportadoraByColumn", String.class, String.class, int.class,
							List.class);
					list = (List<?>) method.invoke(classDAO.newInstance(), cboxSearch.getValue().getField(),
							txtSearch.getText(), cboxSearch.getValue().getAction(), paramFlagAtivo);

				} else if (Util.flagVendedor) {
					method = classDAO.getMethod("filterVendedorByColumn", String.class, String.class, int.class,
							List.class);
					list = (List<?>) method.invoke(classDAO.newInstance(), cboxSearch.getValue().getField(),
							txtSearch.getText(), cboxSearch.getValue().getAction(), paramFlagAtivo);

				} else if (Util.flagContasReceber) {
					method = classDAO.getMethod("filterContasReceberByColumn", String.class, String.class, int.class,
							List.class);
					list = (List<?>) method.invoke(classDAO.newInstance(), cboxSearch.getValue().getField(),
							txtSearch.getText(), cboxSearch.getValue().getAction(), paramFlagAtivo);
				}

				else {
					method = classDAO.getMethod("filterByColumn", String.class, String.class, int.class, List.class);
					list = (List<?>) method.invoke(classDAO.newInstance(), cboxSearch.getValue().getField(),
							txtSearch.getText(), cboxSearch.getValue().getAction(), paramFlagAtivo);
				}

				return "-";
			}

			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			protected void succeeded() {
				stg.close();
				pBar.setProgress(1);

				data = FXCollections.observableArrayList(list);

				tbView.setItems(data);

				if (!selectedItem.isEmpty()) {

					for (int j = 0; j < tbView.getItems().size(); j++) {
						if (selectedItem.get(0).getCodigo()
								.equals(((TableColumn) tbView.getColumns().get(0)).getCellData(j))
								&& selectedItem.get(0).getDescricao()
										.equals(((TableColumn) tbView.getColumns().get(1)).getCellData(j))) {

							tbView.getSelectionModel().select(tbView.getItems().get(j));
							selectedItem.clear();
						}
					}

					if (!selectedItem.isEmpty()) {
						tbView.getItems().add(selectedItem.get(0));
						tbView.getSelectionModel().select(selectedItem.get(0));
					}
				}

				if (tbView.getItems().size() > 0) {
					tbView.getSelectionModel().select(0);
					tbView.requestFocus();
				}
				// tbView.requestFocus();
				// Util.setFocus(tbView);
			}

			@Override
			protected void failed() {
				stg.close();
				util.tratamentoExcecao(exceptionProperty().getValue().toString(),
						"[ SearhController.actionTxtSearch() ]");
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@FXML
	void keyReleasedTxtSearch(KeyEvent event) {

		if (data != null) {

			if (txtSearch.textProperty().get().isEmpty()) {
				tbView.setItems(data);

				if (!selectedItem.isEmpty()) {

					for (int j = 0; j < tbView.getItems().size(); j++)

						if (selectedItem.get(0).getCodigo()
								.equals(((TableColumn) tbView.getColumns().get(0)).getCellData(j))
								&& selectedItem.get(0).getDescricao()
										.equals(((TableColumn) tbView.getColumns().get(1)).getCellData(j)))
							tbView.getSelectionModel().select(tbView.getItems().get(j));

				}

				return;
			}

			ObservableList tableItems = FXCollections.observableArrayList();

			for (int i = 0; i < data.size(); i++) {
				for (int j = 0; j < tbView.getVisibleLeafColumns().size(); j++) {

					TableColumn col = (TableColumn) tbView.getVisibleLeafColumns().get(j);
					String cellValue = col.getCellData(data.get(i)).toString();
					if (Util.removeSpecialCharacters(cellValue.toLowerCase())
							.contains(txtSearch.textProperty().get().toLowerCase())) {
						tableItems.add(data.get(i));
						break;
					}
				}
				tbView.setItems(tableItems);
			}
		}

		if (!selectedItem.isEmpty()) {
			tbView.getItems().add(selectedItem.get(0));
			tbView.getSelectionModel().select(selectedItem.get(0));
		}
	}

	@FXML
	void actionBtnClose(ActionEvent event) {

		Util.closeWindow(anpSearch);
		Util.flagTrasnportadora = false;
		Util.flagVendedor = false;
		Util.flagContasReceber = false;
	}

	@FXML
	void actionBtnOk(ActionEvent event) {

	}

	public void populaCboxSearch() {

		cboxSearch.getItems().addAll(listCboxFilter);

		Util.setCboxFilterSelecionado(cboxSearch, selectedItemBox);

		cboxSearch.setConverter(new StringConverter<ComboBoxFilter>() {
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void configTableView() {

		for (int i = 0; i < listCboxFilter.size(); i++) {

			TableColumn col = new TableColumn();
			col.setText(listCboxFilter.get(i).getValue());
			col.setCellValueFactory(new PropertyValueFactory(listCboxFilter.get(i).getField()));

			if (i == 0) {
				col.setPrefWidth(80);
				col.setMinWidth(80);
			}
			if (i > 0) {
				col.setPrefWidth(250);
				col.setMinWidth(250);
			}

			col.setStyle("-fx-alignment: center-left;");
			tbView.getColumns().add(col);
		}

	}

	public SearchControllerList(String title, ObservableList<ComboBoxFilter> listCbox, String seletedItemBox,
			Class classDAO) {
		// TODO Auto-generated constructor stub
		super();
		this.title = title;
		this.listCboxFilter = listCbox;
		this.selectedItemBox = seletedItemBox;
		this.classDAO = classDAO;
	}

	@SuppressWarnings({ "incomplete-switch", "unchecked", "rawtypes" })
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		Util.customSearchTextField("right", null, txtSearch);

		lblTitle.setText(lblTitle.getText() + " [" + title + "]");

		btnOk.setVisible(false);

		populaCboxSearch();

		configTableView();

		Util.setFocus(txtSearch);

		// CHAMADA A FUNÇÃO QUE DEFINE OS CAMPOS TEXTFIELDS QUE SERAO
		// CAPITALIZADOS(LETRAS MAÍUSCULAS)
		Util.whriteUppercase(txtSearch);

		anpSearch.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			switch (event.getCode()) {
			case ESCAPE:
				if (tbView.isFocused())
					txtSearch.requestFocus();
				else
					actionBtnClose(null);
				break;

			}
		});

		tbView.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if (!newValue) {
					selectedItem.clear();

					selectedItem.add(new SelectedItem(
							((TableColumn) tbView.getColumns().get(0))
									.getCellData(tbView.getSelectionModel().getSelectedItem()),
							((TableColumn) tbView.getColumns().get(1))
									.getCellData(tbView.getSelectionModel().getSelectedItem()),
							((TableColumn) tbView.getColumns().get(2))
									.getCellData(tbView.getSelectionModel().getSelectedItem())));
				}
			}
		});

		tbView.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode().equals(KeyCode.ENTER)) {

				obj = tbView.getSelectionModel().getSelectedItem();
				// OperacaoEntrada op = (OperacaoEntrada) obj;
				// System.out.println(op.getDescricao());
				actionBtnClose(null);

			}
		});

		tbView.setRowFactory(tb -> {
			TableRow row = new TableRow() {
			};

			row.setOnMouseClicked(tr -> {
				if (tr.getClickCount() >= 2 && (!row.isEmpty()) && tr.getButton().equals(MouseButton.PRIMARY)) {

					obj = tbView.getSelectionModel().getSelectedItem();
					actionBtnClose(null);

				}
			});
			return row;
		});

	}

	public class SelectedItem {

		Object codigo;
		Object descricao;
		Object extra;

		public SelectedItem(Object codigo, Object descricao, Object extra) {
			super();
			this.codigo = codigo;
			this.descricao = descricao;
			this.extra = extra;
		}

		public Object getCodigo() {
			return codigo;
		}

		public Object getDescricao() {
			return descricao;
		}

		public Object getExtra() {
			return extra;
		}
	}

}
