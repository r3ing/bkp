package tools.controls;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Skin;
import javafx.scene.control.TableView;

public class CheckTableView<T> extends TableView<T> {

    private ObservableList<T> selected;

    public CheckTableView() {
       this(FXCollections.observableArrayList());
    }

    public CheckTableView(ObservableList<T> items) {
        setItems(items);
        setEditable(true);
        getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        skinProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                selected = ((CheckTableViewSkin) getSkin()).getSelectedRows();
                skinProperty().removeListener(this);
            }
        });
    }

    public void CheckTableViewU(ObservableList<T> items) {
        setItems(items);
        setEditable(true);
        getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        skinProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                selected = ((CheckTableViewSkin) getSkin()).getSelectedRows();
                skinProperty().removeListener(this);
            }
        });
    }
    
    @Override
    protected Skin<?> createDefaultSkin() {
        return new CheckTableViewSkin<>(this);
    }

    public ObservableList<T> getSelectedRows() {
        return selected;
    } 
}
