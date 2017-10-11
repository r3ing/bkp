package tools.controls;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import com.sun.javafx.scene.control.skin.TableViewSkin;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.CheckBoxTableCell;

public class CheckTableViewSkin<T> extends TableViewSkin<T> {

    private final TableColumn<T, Boolean> checkColumn;
    private final CheckBox headerCheckBox = new CheckBox();
    private final List<BooleanProperty> colSelected = new ArrayList<>();

    private final ChangeListener<Number> listener = (obs, ov, nv) -> {
        if (nv.intValue() != -1) {
            Platform.runLater(() -> {
                colSelected.get(nv.intValue()).set(!colSelected.get(nv.intValue()).get());
                refreshSelection();
            });
        }
    };
    private final ChangeListener<Boolean> headerListener = (obs, ov, nv) -> {
        Platform.runLater(() -> {
            IntStream.range(0, colSelected.size()).forEach(i -> colSelected.get(i).set(nv));
            refreshSelection();
        });
    };

    public CheckTableViewSkin(CheckTableView<T> control) {
        super(control);

        checkColumn = new TableColumn<>();

        headerCheckBox.selectedProperty().addListener(headerListener);
        checkColumn.setGraphic(headerCheckBox);

        // install listeners in checkboxes
        IntStream.range(0, control.getItems().size()).forEach(i -> {
            final SimpleBooleanProperty simple = new SimpleBooleanProperty();
            simple.addListener((obs, ov, nv) -> refreshSelection());
            colSelected.add(simple);
        });
        checkColumn.setCellFactory(CheckBoxTableCell.forTableColumn(colSelected::get));

        checkColumn.setPrefWidth(60);
        checkColumn.setEditable(true);
        checkColumn.setResizable(false);
        getColumns().add(0, checkColumn);

        getSelectionModel().selectedIndexProperty().addListener(listener);
    }

    private void refreshSelection() {
        // refresh list of selected rows
        getSelectionModel().selectedIndexProperty().removeListener(listener);
        getSelectionModel().clearSelection();
        AtomicInteger count = new AtomicInteger();
        IntStream.range(0, colSelected.size()).forEach(i -> {
            if (colSelected.get(i).get()) {
                getSelectionModel().select(i);
                count.getAndIncrement();
            }
        });
        headerCheckBox.selectedProperty().removeListener(headerListener);
        headerCheckBox.setSelected(count.get() == colSelected.size());
        headerCheckBox.selectedProperty().addListener(headerListener);
        // it may flick, but required to show all selected rows focused 
        getSkinnable().requestFocus();
        getSelectionModel().selectedIndexProperty().addListener(listener);
    }

    public ObservableList<T> getSelectedRows() {
        return getSelectionModel().getSelectedItems();
    }
}
