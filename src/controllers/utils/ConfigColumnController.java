package controllers.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.DadosGlobais;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import tools.controls.ColumsTableView;
import tools.controls.EditingCell;
import tools.utils.Util;
import tools.xml.UserDefinedColumn;
import tools.xml.XMLTableColumns;

/**
 * Class to define the columns that will be visible and their size.
 * 
 * @author User
 *
 */
public class ConfigColumnController implements Initializable {
	@FXML
	private AnchorPane anpConfigColumn;
	@FXML
	private ListView<ColumsTableView> lvAllColumn;
    @FXML
    private Label lblColumnDisp, lblColumnVis, lblInfo;
	@FXML
	private Button btnCancel, btnSave, btnSendRight, btnSendLeft, btnSendAllRight, btnSendAllLeft, btnMoveUp,
			btnMoveDown, btnMoveFirst, btnMoveLast;
	@FXML
	private TableView<ColumsTableView> tableColums;
	@FXML
	private Pane titleBar;

	// private static final DataFormat SERIALIZED_MIME_TYPE = new
	// DataFormat("application/x-java-serialized-object");

	private ObservableList<TableColumn<?, ?>> visibleColumns = FXCollections.observableArrayList();

	private TableView<?> tableView;
	private TableColumn nameCol;
	private TableColumn widthCol;

	private InnerShadow innerShadow = new InnerShadow();

	@SuppressWarnings("rawtypes")
	private Class clase;

	private String fileName;

	@SuppressWarnings("rawtypes")
	public ConfigColumnController(Object clase, TableView<?> tableView, String fileName) {
		// TODO Auto-generated constructor stub
		this.clase = (Class) clase;
		this.tableView = tableView;
		this.fileName = fileName;

	}

	@FXML
	void actionBtnMoveUp(ActionEvent event) {

		if (tableColums.getSelectionModel().getSelectedItem() != null) {
			if (tableColums.getSelectionModel().getSelectedIndex() > 0) {
				tableColums.getItems().add(tableColums.getSelectionModel().getSelectedIndex() - 1,
						tableColums.getSelectionModel().getSelectedItem());
				tableColums.getItems().remove(tableColums.getSelectionModel().getSelectedIndex());
				tableColums.getSelectionModel().clearAndSelect(tableColums.getSelectionModel().getSelectedIndex() - 1);
				tableColums.requestFocus();
			}
		}
	}

	@FXML
	void actionBtnMoveDown(ActionEvent event) {

		if (tableColums.getSelectionModel().getSelectedItem() != null) {

			if (!tableColums.getSelectionModel().isSelected(tableColums.getItems().size() - 1)) {
				if (tableColums.getSelectionModel().isSelected(0)) {
					tableColums.getItems().add(2, tableColums.getSelectionModel().getSelectedItem());
					tableColums.getItems().remove(tableColums.getSelectionModel().getSelectedIndex());
					tableColums.getSelectionModel().clearAndSelect(1);
					tableColums.requestFocus();
				} else {
					tableColums.getItems().add(tableColums.getSelectionModel().getSelectedIndex() + 2,
							tableColums.getSelectionModel().getSelectedItem());
					tableColums.getItems().remove(tableColums.getSelectionModel().getSelectedIndex());
					tableColums.getSelectionModel()
							.clearAndSelect(tableColums.getSelectionModel().getSelectedIndex() + 2);
					tableColums.requestFocus();
				}
			}

		}

	}

	@FXML
	void actionBtnMoveFirst(ActionEvent event) {

		if (tableColums.getSelectionModel().getSelectedItem() != null) {
			if (tableColums.getSelectionModel().getSelectedIndex() > 0) {
				tableColums.getItems().add(0, tableColums.getSelectionModel().getSelectedItem());
				tableColums.getItems().remove(tableColums.getSelectionModel().getSelectedIndex());
				tableColums.getSelectionModel().clearAndSelect(0);
				tableColums.requestFocus();
			}
		}

	}

	@FXML
	void actionBtnMoveLast(ActionEvent event) {

		if (tableColums.getSelectionModel().getSelectedItem() != null) {
			if (!tableColums.getSelectionModel().isSelected(tableColums.getItems().size() - 1)) {
				tableColums.getItems().add(tableColums.getItems().size(),
						tableColums.getSelectionModel().getSelectedItem());
				tableColums.getItems().remove(tableColums.getSelectionModel().getSelectedIndex());
				tableColums.getSelectionModel().clearAndSelect(tableColums.getItems().size() - 1);
				tableColums.requestFocus();
			}
		}

	}

	@FXML
	void onActionBtnSendLeft(ActionEvent event) {

		if (!tableColums.getSelectionModel().getSelectedItems().isEmpty()) {
			lvAllColumn.getItems().addAll(tableColums.getSelectionModel().getSelectedItems());
			tableColums.getItems().removeAll(tableColums.getSelectionModel().getSelectedItems());
			tableColums.getSelectionModel().clearSelection();
			lvAllColumn.requestFocus();
			lvAllColumn.getSelectionModel().clearAndSelect(lvAllColumn.getItems().size() - 1);
		}

	}

	@FXML
	void onActionBtnSendRight(ActionEvent event) {

		if (!lvAllColumn.getSelectionModel().getSelectedItems().isEmpty()) {
			tableColums.getItems().addAll(lvAllColumn.getSelectionModel().getSelectedItems());
			lvAllColumn.getItems().removeAll(lvAllColumn.getSelectionModel().getSelectedItems());
			lvAllColumn.getSelectionModel().clearSelection();
		}
	}

	@FXML
	void onActioBtntnSendAllRight(ActionEvent event) {

		if (!lvAllColumn.getItems().isEmpty()) {
			tableColums.getItems().addAll(lvAllColumn.getItems());
			lvAllColumn.getItems().removeAll(lvAllColumn.getItems());
		}
	}

	@FXML
	void onActionBtnSendAllLeft(ActionEvent event) {

		if (!tableColums.getItems().isEmpty()) {
			lvAllColumn.getItems().addAll(tableColums.getItems());
			tableColums.getItems().removeAll(tableColums.getItems());
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@FXML
	void actionBtnSave(ActionEvent event)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {

		if (!tableColums.getItems().isEmpty()) {

			for (int i = 0; i < tableColums.getItems().size(); i++) {
				for (TableColumn col : tableView.getColumns()) {
					if (col.getText().equals(tableColums.getItems().get(i).getTitle())) {
						col.setPrefWidth(Double.parseDouble(tableColums.getItems().get(i).getWidth()));
						visibleColumns.add(col);
					}
				}
			}

			Method method = clase.getMethod("updateTableView", ObservableList.class, TableView.class);
			method.invoke(clase.newInstance(), visibleColumns, tableView);

			Stage stage = (Stage) anpConfigColumn.getScene().getWindow();
			stage.close();

			XMLTableColumns xml = new XMLTableColumns();
			ArrayList<UserDefinedColumn> userDefinedColumnList = new ArrayList<UserDefinedColumn>();

			int i = 0;
			for (TableColumn col : visibleColumns) {
				userDefinedColumnList.add(new UserDefinedColumn(col.getId(), col.getText(), i, col.getWidth()));
				i++;
			}

			xml.WriteXMLColumns(userDefinedColumnList, fileName);

		}

		else {
			Stage stage = (Stage) anpConfigColumn.getScene().getWindow();
			stage.close();
		}

	}

	@FXML
	void onActionBtnCancel(ActionEvent event) {

		Stage stage = (Stage) anpConfigColumn.getScene().getWindow();
		stage.close();

	}
	
	public void translations()
	{
		
		btnCancel.setTooltip(new Tooltip(DadosGlobais.resourceBundle.getString("toolTipBtnCancelar")));
		lblColumnDisp.setText(DadosGlobais.resourceBundle.getString("lblColumnDisp"));
		lblColumnVis.setText(DadosGlobais.resourceBundle.getString("lblColumnVis"));
		lblInfo.setText(DadosGlobais.resourceBundle.getString("lblInfo"));
		btnSave.setTooltip(new Tooltip(DadosGlobais.resourceBundle.getString("toolTipBtnGuardar")));
		//nameCol.setText(Dados.resourceBundle.getString("nameCol"));
		//widthCol.setText(Dados.resourceBundle.getString("widthCol"));
		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		lvAllColumn.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		tableColums.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		//Set Idioma
		translations();
		
		//anpConfigColumn.getScene().getStylesheets().add(getClass().getResource("/styles/css/themes "+ DadosGlobais.empresaLogada.getSistema() +"/style_"+DadosGlobais.empresaLogada.getSistema()+".css").toExternalForm());
		

		// Styling Form and TitleBar
//		titleBar.setId("title-bar-form");

		// anchorPanePrintGridPane.getStyleClass().add("bordered-titled-title");
//		anpConfigColumn.getStyleClass().add("bordered-titled-border");
		// anchorPanePrintGridPane.getStyleClass().add("bordered-titled-content");
		anpConfigColumn.getStylesheets()
		.add(getClass().getResource("/styles/css/themes "+DadosGlobais.empresaLogada.getSistema()+ "/style_"+DadosGlobais.empresaLogada.getSistema()+".css").toExternalForm());
		
//		if (DadosGlobais.sistema.equals("Ultimate")) {
//			anpConfigColumn.getStylesheets()
//					.add(getClass().getResource("/styles/css/themes Ultimate/style_Ultimate.css").toExternalForm());
//		} else if (DadosGlobais.sistema.equals("Professional")) {
//			anpConfigColumn.getStylesheets().add(
//					getClass().getResource("/styles/css/themes Professional/style_Professional.css").toExternalForm());
//		} else if (DadosGlobais.sistema.equals("Enterprise")) {
//			anpConfigColumn.getStylesheets()
//					.add(getClass().getResource("/styles/css/themes Enterprise/style_Enterprise.css").toExternalForm());
//		}

		// fill listView
		for (TableColumn col : tableView.getColumns())
			lvAllColumn.getItems().add(new ColumsTableView(col.getText(),
					String.valueOf(col.getWidth()).substring(0, String.valueOf(col.getWidth()).length() - 2)));

		lvAllColumn.setCellFactory(new Callback<ListView<ColumsTableView>, ListCell<ColumsTableView>>() {
			@Override
			public ListCell<ColumsTableView> call(ListView<ColumsTableView> param) {
				ListCell<ColumsTableView> cells = new TextFieldListCell<>(new StringConverter<ColumsTableView>() {
					@Override
					public String toString(ColumsTableView object) {
						return object.getTitle();
					}

					@Override
					public ColumsTableView fromString(String string) {
						// how to modify the object that was in the cell???
						return null;
					}
				});
				return cells;
			}
		});

		Callback<TableColumn, TableCell> cellFactory = new Callback<TableColumn, TableCell>() {
			@Override
			public TableCell call(TableColumn p) {
				return new EditingCell();
			}
		};

		nameCol = new TableColumn(DadosGlobais.resourceBundle.getString("nameCol"));
		nameCol.setMinWidth(145);
		nameCol.setMaxWidth(145);
		nameCol.setCellValueFactory(new PropertyValueFactory<ColumsTableView, String>("title"));

		widthCol = new TableColumn(DadosGlobais.resourceBundle.getString("widthCol"));
		widthCol.setMinWidth(70);
		widthCol.setMaxWidth(70);
		widthCol.setCellValueFactory(new PropertyValueFactory<ColumsTableView, String>("width"));
		widthCol.setCellFactory(cellFactory);
		widthCol.setOnEditCommit(new EventHandler<CellEditEvent<ColumsTableView, String>>() {
			@Override
			public void handle(CellEditEvent<ColumsTableView, String> t) {
				t.getTableView().getItems().get(t.getTablePosition().getRow()).setWidth(t.getNewValue());
			}
		});

		tableColums.setPlaceholder(new Label(""));
		tableColums.getColumns().addAll(nameCol, widthCol);

		// fill TableView tableColums
		for (TableColumn col : tableView.getVisibleLeafColumns())
			tableColums.getItems().add(new ColumsTableView(col.getText(),
					String.valueOf(col.getWidth()).substring(0, String.valueOf(col.getWidth()).length() - 2)));

		compareList(lvAllColumn, tableColums);

		// validation when tableView and listsView are empty
		btnSave.disableProperty().bind(Bindings.isEmpty(tableColums.getItems()));
		btnSendAllLeft.disableProperty().bind(Bindings.isEmpty(tableColums.getItems()));
		btnSendAllRight.disableProperty().bind(Bindings.isEmpty(lvAllColumn.getItems()));
		btnSendLeft.disableProperty().bind(Bindings.isEmpty(tableColums.getItems()));
		btnSendRight.disableProperty().bind(Bindings.isEmpty(lvAllColumn.getItems()));
		btnMoveUp.disableProperty().bind(Bindings.isEmpty(tableColums.getItems()));
		btnMoveDown.disableProperty().bind(Bindings.isEmpty(tableColums.getItems()));
		btnMoveFirst.disableProperty().bind(Bindings.isEmpty(tableColums.getItems()));
		btnMoveLast.disableProperty().bind(Bindings.isEmpty(tableColums.getItems()));

		// tableColums.setRowFactory(tc -> {
		// TableRow<ColumsTableView> row = new TableRow<>();
		// row.setOnDragDetected(event -> {
		// if (!row.isEmpty()) {
		// Integer index = row.getIndex();
		// Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
		// db.setDragView(row.snapshot(null, null));
		// ClipboardContent cc = new ClipboardContent();
		// cc.put(SERIALIZED_MIME_TYPE, index);
		// db.setContent(cc);
		// event.consume();
		// }
		// });
		//
		// row.setOnDragOver(event -> {
		// Dragboard db = event.getDragboard();
		// if (db.hasContent(SERIALIZED_MIME_TYPE)) {
		// if (row.getIndex() != ((Integer)
		// db.getContent(SERIALIZED_MIME_TYPE)).intValue()) {
		// event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
		// event.consume();
		// }
		// }
		// });
		//
		// row.setOnDragDropped(event -> {
		// Dragboard db = event.getDragboard();
		// if (db.hasContent(SERIALIZED_MIME_TYPE)) {
		// int draggedIndex = (Integer) db.getContent(SERIALIZED_MIME_TYPE);
		// ColumsTableView draggedRow =
		// tableColums.getItems().remove(draggedIndex);
		// int dropIndex;
		// if (row.isEmpty()) {
		// dropIndex = tableColums.getItems().size();
		// } else {
		// dropIndex = row.getIndex();
		// }
		// tableColums.getItems().add(dropIndex, draggedRow);
		// event.setDropCompleted(true);
		// tableColums.getSelectionModel().clearSelection();
		// tableColums.getSelectionModel().select(dropIndex);
		// event.consume();
		// }
		// });
		// return row;
		// });

		// anpConfigColumn.addEventFilter(KeyEvent.KEY_PRESSED, event ->
		// {
		// if (event.getCode().equals(KeyCode.ESCAPE)) {
		// //if (!widthCol.onEditStartProperty().isBound()) {
		// //Stage stage = (Stage)anpConfigColumn.getScene().getWindow();
		// //stage.close();
		// //}
		// }
		// });

		// anpConfigColumn.setOnKeyPressed(new EventHandler<KeyEvent>() {
		// public void handle(KeyEvent ke) {
		// if(ke.getCode().equals(KeyCode.ESCAPE)){
		// Stage stage = (Stage)anpConfigColumn.getScene().getWindow();
		// stage.close();
		// }
		// }
		// });

		anpConfigColumn.setOnKeyPressed((KeyEvent ke) -> {
			switch (ke.getCode()) {
			case ESCAPE:
				Util.closeWindow(anpConfigColumn);
				break;
				
			case F6:
				try {
					actionBtnSave(null);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			default:
				break;
			}
		});

	}

	/**
	 * Method to compare the data between the listView and the tableView, and
	 * eliminate the repeated ones.
	 * 
	 * @param lvAllColumn
	 *            ListView which contains the available columns.
	 * @param tableColums
	 *            TableView which contains the visible columns.
	 */
	public void compareList(ListView<?> lvAllColumn, TableView<ColumsTableView> tableColums) {

		if (!tableColums.getItems().isEmpty()) {
			for (int i = 0; i < tableColums.getItems().size(); i++)
				for (int j = 0; j < lvAllColumn.getItems().size(); j++)
					if (tableColums.getItems().get(i).getTitle().equals(lvAllColumn.getItems().get(j).toString()))
						lvAllColumn.getItems().remove(j);

		}
	}

}
