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
import models.compras.Departamento;
import models.compras.Item;
import models.vendas.Cidade;
import tools.controls.CheckTableView;
import tools.controls.ComboBoxFilter;
import tools.utils.Util;

public class SearchControllerMultiselection implements Initializable {

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
	private String selectedItem = null;
	private List<Integer> paramFlagAtivo = Arrays.asList(1);
	private Boolean selection;

	@SuppressWarnings("rawtypes")
	private Class classDAO;

	private TextField txtCodes;
	private ComboBox<ComboBoxFilter> cboxDescriptions;
	private static Stage stg;
	private Util util = new Util();
	private List list;
	private ObservableList data = FXCollections.observableArrayList();

	private ObservableList<ListSelectedItem> listSelectedItem = FXCollections.observableArrayList();
	private ObservableSet<Integer> selectedItems = FXCollections.observableSet(new HashSet<>());

	@SuppressWarnings({ "unused", "rawtypes" })
	private ObservableList tempItems = FXCollections.observableArrayList();

	@FXML
	void actionTxtSearch(ActionEvent event) {

		Task<String> TarefaRefresh = new Task<String>() {
			@SuppressWarnings("unchecked")
			@Override
			protected String call() throws Exception {

				Method method = classDAO.getMethod("filterByColumn", String.class, String.class, int.class, List.class);
				list = (List<?>) method.invoke(classDAO.newInstance(), cboxSearch.getValue().getField(),
						txtSearch.getText(), cboxSearch.getValue().getAction(), paramFlagAtivo);

				return "-";
			}

			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			protected void succeeded() {
				stg.close();
				pBar.setProgress(1);

				data = FXCollections.observableArrayList(list);
				tbView.setItems(data);

//				if (!listSelectedItem.isEmpty()) {
//
//					boolean tmp = false;
//
//					for (int i = 0; i < listSelectedItem.size(); i++) {
//						
//						for (int j = 0; j < tbView.getItems().size(); j++) {
//
//							if (listSelectedItem.get(i).check
//									.equals(((TableColumn) tbView.getColumns().get(0)).getCellData(j))
//									&& listSelectedItem.get(i).codigo
//											.equals(((TableColumn) tbView.getColumns().get(1)).getCellData(j))) {
//								tmp = true;
//								break;
//							}
//						}
//						if (!tmp) {
//							tbView.getItems().add(listSelectedItem.get(i));
//
//						}
//					}
//
//				}
				
				
				if (!listSelectedItem.isEmpty()) {

					boolean tmp = false;

					for (int i = 0; i < listSelectedItem.size(); i++) {
						for (int j = 0; j < tbView.getItems().size(); j++) {
							if (listSelectedItem.get(i).check
									.equals(((TableColumn) tbView.getColumns().get(0)).getCellData(j))
									&& listSelectedItem.get(i).codigo
											.equals(((TableColumn) tbView.getColumns().get(1)).getCellData(j))){
								tmp = true;
							}
						}
						if (!tmp)
							tbView.getItems().add(listSelectedItem.get(i));
					}
				}
				

				Util.setFocus(tbView);

			}

			@Override
			protected void failed() {
				stg.close();
				util.tratamentoExcecao(exceptionProperty().getValue().toString(),
						"[ SearhControllerMultiselection.actionTxtSearch() ]");
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

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@FXML
	void keyReleasedTxtSearch(KeyEvent event) {

		 if (data != null) {
//		 if (1 != 1) {

			if (txtSearch.textProperty().get().isEmpty()) {
				tbView.setItems(data);

				return;
			}

			ObservableList tableItems = FXCollections.observableArrayList();

			for (int i = 0; i < data.size(); i++) {
				for (int j = 1; j < tbView.getVisibleLeafColumns().size(); j++) {

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

			if (!listSelectedItem.isEmpty()) {

				boolean tmp = false;

				for (int i = 0; i < listSelectedItem.size(); i++) {
					for (int j = 0; j < tbView.getItems().size(); j++) {
						if (listSelectedItem.get(i).check
								.equals(((TableColumn) tbView.getColumns().get(0)).getCellData(j))
								&& listSelectedItem.get(i).codigo
										.equals(((TableColumn) tbView.getColumns().get(1)).getCellData(j))){
							tmp = true;
						}
					}
					if (!tmp)
						tbView.getItems().add(listSelectedItem.get(i));
				}
			}
		}

		// if (!listSelectedItem.isEmpty()) {
		//
		// for (int j = 0; j < listSelectedItem.size(); j++)
		// tbView.getItems().add(listSelectedItem.get(j));
		//
		//
		// }
	}

	@FXML
	void actionBtnClose(ActionEvent event) {

		Util.closeWindow(anpSearch);
	}

	@FXML
	void actionBtnOk(ActionEvent event) {

		if (!listSelectedItem.isEmpty()) {

			ObservableList<ComboBoxFilter> list = FXCollections.observableArrayList();

			for (int i = 0; i < listSelectedItem.size(); i++)
				list.add(new ComboBoxFilter(listSelectedItem.get(i).check.toString(),
						(Integer) listSelectedItem.get(i).codigo, (String) listSelectedItem.get(i).descricao));

			cboxDescriptions.getItems().clear();
			cboxDescriptions.getItems().add(new ComboBoxFilter("", 0, "MULTISELECTION"));
			cboxDescriptions.getItems().addAll(list);
			cboxDescriptions.getSelectionModel().selectFirst();

			txtCodes.setText("9999999");
		}

		actionBtnClose(null);

	}

	public void populaCboxSearch() {

		cboxSearch.getItems().addAll(listCboxFilter);

		Util.setCboxFilterSelecionado(cboxSearch, selectedItem);

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

		TableColumn colCheck = new TableColumn();
		colCheck.setPrefWidth(50);
		colCheck.setMinWidth(50);
		CheckBox chk = new CheckBox();
		chk.alignmentProperty().set(Pos.CENTER);
		colCheck.setGraphic(chk);

		colCheck.setCellValueFactory(new PropertyValueFactory("codigo"));

		colCheck.setCellFactory(col -> {

			// boolean property represents if the check box in the cell is
			// selected:
			BooleanProperty selected = new SimpleBooleanProperty();

			// create check box cell and bind its state to property:
			CheckBoxTableCell<Integer, Integer> cell = new CheckBoxTableCell<>(index -> selected);

			// update set of selected indices if check box state changes:
			selected.addListener((obs, wasSelected, isNowSelected) -> {
				if (isNowSelected) {
					selectedItems.add(cell.getItem());

					boolean tmp = false;

					if (!listSelectedItem.isEmpty())
						for (int i = 0; i < listSelectedItem.size(); i++)
							if (listSelectedItem.get(i).check.equals(cell.getItem()))
								tmp = true;

					if (!tmp)
						listSelectedItem.add(new ListSelectedItem(
								((TableColumn) tbView.getColumns().get(0)).getCellData(cell.getIndex()),
								((TableColumn) tbView.getColumns().get(1)).getCellData(cell.getIndex()),
								((TableColumn) tbView.getColumns().get(2)).getCellData(cell.getIndex())));

				} else {
					selectedItems.remove(cell.getItem());

					if (!listSelectedItem.isEmpty())
						for (int i = 0; i < listSelectedItem.size(); i++)
							if (listSelectedItem.get(i).check.equals(cell.getItem()))
								listSelectedItem.remove(i);
				}
			});

			// update check box state if set of selected indices changes:
			selectedItems.addListener((Change<? extends Integer> change) -> {
				selected.set(cell.getItem() != null && selectedItems.contains(cell.getItem()));

			});

			// update check box when cell is reused for a different index:
			cell.itemProperty().addListener((obs, oldItem, newItem) -> {
				selected.set(newItem != null && selectedItems.contains(newItem));
			});

			chk.selectedProperty().addListener(new ChangeListener<Boolean>() {

				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					// TODO Auto-generated method stub
					if (newValue) {
						listSelectedItem.clear();
						for (int i = 0; i < tbView.getItems().size(); i++) {

							selectedItems.add((Integer) ((TableColumn) tbView.getColumns().get(0)).getCellData(i));

							listSelectedItem
									.add(new ListSelectedItem(((TableColumn) tbView.getColumns().get(0)).getCellData(i),
											((TableColumn) tbView.getColumns().get(1)).getCellData(i),
											((TableColumn) tbView.getColumns().get(2)).getCellData(i)));

						}
					} else {
						listSelectedItem.clear();
						for (int i = 0; i < tbView.getItems().size(); i++) {

							selectedItems.remove((Integer) ((TableColumn) tbView.getColumns().get(0)).getCellData(i));
						}
					}
				}
			});

			return cell;
		});

		tbView.getColumns().add(colCheck);

		for (int i = 0; i < listCboxFilter.size(); i++) {

			TableColumn col = new TableColumn();
			col.setText(listCboxFilter.get(i).getValue());
			col.setCellValueFactory(new PropertyValueFactory(listCboxFilter.get(i).getField()));

			if (i == 0) {
				col.setPrefWidth(100);
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

	@SuppressWarnings("rawtypes")
	public SearchControllerMultiselection(String title, ObservableList<ComboBoxFilter> listCbox, String selectedItem,
			Class classDAO, TextField txtCodes, ComboBox<ComboBoxFilter> cboxDescriptions) {
		super();
		this.listCboxFilter = listCbox;
		this.title = title;
		this.selectedItem = selectedItem;
		this.classDAO = classDAO;
		this.txtCodes = txtCodes;
		this.cboxDescriptions = cboxDescriptions;
	}

	@SuppressWarnings({ "incomplete-switch", "unchecked", "rawtypes" })
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		Util.customSearchTextField("right", null, txtSearch);

		lblTitle.setText(lblTitle.getText() + " [" + title + "]");

		populaCboxSearch();

		configTableView();

		Util.setFocus(txtSearch);

		// CHAMADA A FUNÇÃO QUE DEFINE OS CAMPOS TEXTFIELDS QUE SERAO
		// CAPITALIZADOS(LETRAS MAÍUSCULAS)
		Util.whriteUppercase(txtSearch);

		tbView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		// tbView.getSelectionModel().selectedItemProperty().addListener(new
		// ChangeListener() {
		// @Override
		// public void changed(ObservableValue observable, Object oldValue,
		// Object newValue) {
		// // TODO Auto-generated method stub
		// if (newValue != null)
		// selectedItems.add(newValue);
		// else
		// selectedItems.clear();
		// }
		// });

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

					// listSelectedItem.clear();
					//
					// if (!selectedItems.isEmpty() &&
					// !tbView.getItems().isEmpty()) {
					//
					// for (int i = 0; i < selectedItems.size(); i++) {
					// for (int j = 0; j < tbView.getItems().size(); j++)
					// if (selectedItems.contains(((TableColumn)
					// tbView.getColumns().get(1)).getCellData(j)))
					// listSelectedItem.add(new ListSelectedItem(
					// ((TableColumn)
					// tbView.getColumns().get(0)).getCellData(j),
					// ((TableColumn)
					// tbView.getColumns().get(1)).getCellData(j),
					// ((TableColumn)
					// tbView.getColumns().get(2)).getCellData(j)));
					// }
					// }

					// System.out.println(listSelectedItem.size() + "
					// dfgdfgdfgdf");

				}
			}
		});

		tbView.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode().equals(KeyCode.ENTER)) {

				// txtCodigo.setText(((TableColumn) tbView.getColumns().get(0))
				// .getCellData(tbView.getSelectionModel().getSelectedIndex()).toString());
				// txtDescricao.setText(((TableColumn)
				// tbView.getColumns().get(1))
				// .getCellData(tbView.getSelectionModel().getSelectedIndex()).toString());
				//
				// actionBtnClose(null);
			}
		});

		tbView.setRowFactory(tb -> {
			TableRow row = new TableRow() {
			};

			row.setOnMouseClicked(tr -> {
				if (tr.getClickCount() >= 2 && (!row.isEmpty()) && tr.getButton().equals(MouseButton.PRIMARY)) {
					// txtCodigo.setText(((TableColumn)
					// tbView.getColumns().get(0))
					// .getCellData(tbView.getSelectionModel().getSelectedIndex()).toString());
					// txtDescricao.setText(((TableColumn)
					// tbView.getColumns().get(1))
					// .getCellData(tbView.getSelectionModel().getSelectedIndex()).toString());
					// actionBtnClose(null);

				}

				if (tr.getClickCount() == 1 && (!row.isEmpty()) && tr.getButton().equals(MouseButton.PRIMARY)) {
					// txtCodigo.setText(((TableColumn)
					// tbView.getColumns().get(0))
					// .getCellData(tbView.getSelectionModel().getSelectedIndex()).toString());
					// txtDescricao.setText(((TableColumn)
					// tbView.getColumns().get(1))
					// .getCellData(tbView.getSelectionModel().getSelectedIndex()).toString());
					// actionBtnClose(null);

					// ObservableList<String> t =
					// tbView.getSelectionModel().getSelectedItems();
					//
					// tbView.getSelectionModel().clearSelection(row.getIndex());
					//
					//
					// for (int i = 0; i < t.size(); i++)
					// tbView.getSelectionModel().select(t.get(i));

				}

			});

			return row;
		});
		// tableView.getItems().clear();
	}

	public class ListSelectedItem {

		Object check;
		Object codigo;
		Object descricao;

		public ListSelectedItem(Object check, Object codigo, Object descricao) {
			super();
			this.check = check;
			this.codigo = codigo;
			this.descricao = descricao;
		}

		public Object getCheck() {
			return check;
		}

		public Object getCodigo() {
			return codigo;
		}

		public Object getDescricao() {
			return descricao;
		}

	}

}
