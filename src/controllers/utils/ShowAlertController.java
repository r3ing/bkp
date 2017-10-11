package controllers.utils;

import java.net.URL;
import java.util.ResourceBundle;

import application.DadosGlobais;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import tools.utils.Util;

public class ShowAlertController implements Initializable {

	@FXML
	private AnchorPane aPanePrincipal;

	@FXML
	private Button btnClose;

	@FXML
	private Label lblTitle;

	@FXML
	private FontAwesomeIconView iconTile;

	@FXML
	private FontAwesomeIconView icon;

	@FXML
	private Text lblInfo;

	@FXML
	private Button btnSim;

	@FXML
	private Button btnNao;

	@FXML
	private Button btnOk;

	private String message, alertType;
	public static boolean action;

	@FXML
	void actionBtnClose(ActionEvent event) {
		Util.closeWindow(aPanePrincipal);
		action = false;
	}

	@FXML
	void actionBtnNao(ActionEvent event) {
		Util.closeWindow(aPanePrincipal);
		action = false;
	}

	@FXML
	void actionBtnOk(ActionEvent event) {
		Util.closeWindow(aPanePrincipal);
		action = false;
	}

	@FXML
	void actionBtnSim(ActionEvent event) {
		action = true;
		Util.closeWindow(aPanePrincipal);
	}

	public ShowAlertController() {

	}

	public ShowAlertController(String message, String alertType) {
		super();
		// TODO Auto-generated constructor stub
		this.message = message;
		this.alertType = alertType;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		// confirmation,information,warning,error
		btnSim.setText(DadosGlobais.resourceBundle.getString("btnAlertYes"));
		btnNao.setText(DadosGlobais.resourceBundle.getString("btnAlertNo"));
		btnClose.setTooltip(new Tooltip(DadosGlobais.resourceBundle.getString("btnAlertClose"))); 
		

		switch (alertType) {
		case "confirmation":
			icon.setGlyphName("QUESTION_CIRCLE");
			icon.setFill(Color.web("#3d6ec5"));//1E88E5 
			lblTitle.setText(DadosGlobais.resourceBundle.getString("confirmation"));
			btnOk.setVisible(false);
			Util.setFocus(btnNao);

			break;

		case "information":
			icon.setGlyphName("INFO_CIRCLE");
			icon.setFill(Color.web("#3d6ec5"));//1E88E5 
			lblTitle.setText(DadosGlobais.resourceBundle.getString("information"));
			btnSim.setVisible(false);
			btnNao.setVisible(false);
			Util.setFocus(btnOk);

			break;

		case "warning":

			icon.setGlyphName("WARNING");
			icon.setFill(Color.web("#ce962b"));//FF6F00
			lblTitle.setText(DadosGlobais.resourceBundle.getString("warning"));
			btnSim.setVisible(false);
			btnNao.setVisible(false);
			Util.setFocus(btnOk);

			break;

		case "error":

			icon.setGlyphName("WARNING");
			icon.setFill(Color.web("#c05049"));//b71c1c
			lblTitle.setText(DadosGlobais.resourceBundle.getString("error"));
			btnSim.setVisible(false);
			btnNao.setVisible(false);
			Util.setFocus(btnOk);

			break;
			
		case "warningConfirm":

			icon.setGlyphName("WARNING");
			icon.setFill(Color.web("#ce962b"));//FF6F00
			lblTitle.setText(DadosGlobais.resourceBundle.getString("warning"));
			btnOk.setVisible(false);
			Util.setFocus(btnNao);

			break;			

		default:
			break;
		}

		lblInfo.setText(message);

		aPanePrincipal.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			switch (event.getCode()) {

			case ENTER:
				if (btnSim.isFocused())
					actionBtnSim(null);
				else if (btnNao.isFocused() || btnOk.isFocused())
					actionBtnClose(null);
				break;

			case ESCAPE:
				actionBtnClose(null);
				break;
			default:
				break;
			}
		});

	}

}
