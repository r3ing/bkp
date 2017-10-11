package controllers.configuracoes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class CadastroPadrao {

    @FXML
    private AnchorPane anchorPanePrincipal;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab tabListagem;

    @FXML
    private AnchorPane anchorPaneListagem;

    @FXML
    private ToolBar toolBarListagem;

    @FXML
    private Button btnInsertGrid;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnRefresh;

    @FXML
    private Button btnFilter;

    @FXML
    private Button btnPrint;

    @FXML
    private Button btnConfig;

    @FXML
    private ToggleButton toggleBtnHelp;

    @FXML
    private SplitPane splitPaneFilter;

    @FXML
    private AnchorPane anchorPaneFilter;

    @FXML
    private ComboBox<?> cbkFilterColumn;

    @FXML
    private TextField txtFilterColumn;

    @FXML
    private Button btnQueryFilter;

    @FXML
    private ComboBox<?> cmbLstarTudo;

    @FXML
    private TableView<?> tbView;

    @FXML
    private TableColumn<?, ?> tbColCodigo;

    @FXML
    private TableColumn<?, ?> tbColDescricao;

    @FXML
    private TableColumn<?, ?> tbColCodemp;

    @FXML
    private TableColumn<?, ?> tbColAtivoInat;

    @FXML
    private TextField txtDescricaoGrid;

    @FXML
    private Button btnSaveGrid;

    @FXML
    private Label lblTotalLinhas;

    @FXML
    private Label numLinhas;

    @FXML
    private Tab tabDetalhes;

    @FXML
    private AnchorPane anchorPaneDetalhes;

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtDescricao;

    @FXML
    private ToolBar toolBarDetalhes;

    @FXML
    private Button btnInsert;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnCancel;

    @FXML
    void actionBtnCancel(ActionEvent event) {

    }

    @FXML
    void actionBtnConfig(ActionEvent event) {

    }

    @FXML
    void actionBtnDelete(ActionEvent event) {

    }

    @FXML
    void actionBtnFilter(ActionEvent event) {

    }

    @FXML
    void actionBtnInsert(ActionEvent event) {

    }

    @FXML
    void actionBtnInsertGrid(ActionEvent event) {

    }

    @FXML
    void actionBtnPrint(ActionEvent event) {

    }

    @FXML
    void actionBtnQueryFilter(ActionEvent event) {

    }

    @FXML
    void actionBtnSave(ActionEvent event) {

    }

    @FXML
    void actionBtnSaveGrid(ActionEvent event) {

    }

    @FXML
    void btnRefresh(ActionEvent event) {

    }

    @FXML
    void keyPressedTxtCodigo(KeyEvent event) {

    }

    @FXML
    void keyPressedTxtDescricao(KeyEvent event) {

    }

    @FXML
    void keyPressedTxtDescricaoGrid(KeyEvent event) {

    }

    @FXML
    void keyReleasedTxtDescricaoGrid(KeyEvent event) {

    }

    @FXML
    void keyReleasedTxtFilterColumn(KeyEvent event) {

    }

    @FXML
    void keyTypedTxtCodigo(KeyEvent event) {

    }

    @FXML
    void keyTypedTxtDescricao(KeyEvent event) {

    }

    @FXML
    void keyTypedTxtDescricaoGrid(KeyEvent event) {

    }

    @FXML
    void mouseClickedToggleHelp(MouseEvent event) {

    }

}
