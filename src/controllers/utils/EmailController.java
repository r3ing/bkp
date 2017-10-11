package controllers.utils;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


import application.DadosGlobais;

import org.controlsfx.control.PopOver;
import org.controlsfx.control.PopOver.ArrowLocation;


import controllers.compras.DepartamentoController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import tools.utils.Util;

public class EmailController implements Initializable {

	@FXML
	private AnchorPane anchorpMail;
	@FXML
	private Button btnClose, btnSend;
    @FXML
    private ImageView  icon;
    @FXML
    private Text lblTitle;
	@FXML
	private Label lblTo, lblSubject, lblMensaje;
	@FXML
	private Text lblAdjunto;
	@FXML
	private TextField txtSendFor, txtSubject;
	@FXML
	private TextArea txtBody;
	@FXML
	private Pane titleBar;

	@SuppressWarnings("unused")
	private PopOver popOver = new PopOver();
	private File file;
	static Stage stg;
	Util util = new Util();

	public EmailController(File file) {
		super();
		// TODO Auto-generated constructor stub
		this.file = file;
	}

	@FXML
	void actionBtnClose(ActionEvent event) {

		Util.closeWindow(anchorpMail);

	}

	@FXML
	void actionBtnSend(ActionEvent event) throws MessagingException {

		Properties props = new Properties();
		props.put("mail.smtp.host", "mail.eptusdaamazonia.com.br");
		props.setProperty("mail.smtp.starttls.enable", "true");
		props.setProperty("mail.smtp.port", "587");
		props.setProperty("mail.smtp.user", "apollo@eptusdaamazonia.com.br");
		props.setProperty("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props, null);
		session.setDebug(true);

		BodyPart texto = new MimeBodyPart();
		texto.setText(txtBody.getText());

		BodyPart adjunto = new MimeBodyPart();
		adjunto.setDataHandler(new DataHandler(new FileDataSource(file)));
		adjunto.setFileName(file.getName());

		MimeMultipart multiParte = new MimeMultipart();

		multiParte.addBodyPart(texto);
		multiParte.addBodyPart(adjunto);

		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress("apollo@eptusdaamazonia.com.br"));
		Boolean flag = true;

		String to[] = txtSendFor.getText().split(";");
		InternetAddress[] address = new InternetAddress[to.length];
		for (int i = 0; i < to.length; i++) {
			if (Util.validateEmail(to[i])) {
				System.out.println(to[i]);
				address[i] = new InternetAddress(to[i]);
			} else {
				util.showAlert("Endereço de e-mail incorreto.","error");
				txtSendFor.requestFocus();
				flag = false;
				break;
			}
		}

		System.out.println(flag);

		if (flag) {
			message.addRecipients(Message.RecipientType.CC, address);
			message.setSubject(txtSubject.getText());
			message.setContent(multiParte);

			Transport t = session.getTransport("smtp");

			Task<String> tarefaSendEmail = new Task<String>() {
				@Override
				protected String call() throws Exception {
					t.connect("apollo@eptusdaamazonia.com.br", "3165241e");
					t.sendMessage(message, message.getAllRecipients());
					t.close();
					return "-";
				}

				@Override
				protected void succeeded() {
					stg.close();
					Util.closeWindow(anchorpMail);
				}

				@Override
				protected void failed() {
					stg.close();
					util.showAlert("Mail não pôde ser enviado.","error");

				}

				@Override
				protected void cancelled() {

					super.cancelled();
				}

			};

			Thread thread = new Thread(tarefaSendEmail);
			thread.setDaemon(true);
			thread.start();
			stg = ProgressBarForm.showProgressBar(DepartamentoController.class, tarefaSendEmail, DadosGlobais.resourceBundle.getString("msg"), false);

		}

	}
	
	public void setIdioma(){
		lblTitle.setText(DadosGlobais.resourceBundle.getString("lblTitle"));
		btnClose.setTooltip(new Tooltip(DadosGlobais.resourceBundle.getString("toolTipBtnCancelar")));
		lblTo.setText(DadosGlobais.resourceBundle.getString("lblTo"));
		lblSubject.setText(DadosGlobais.resourceBundle.getString("lblSubject"));
		lblMensaje.setText(DadosGlobais.resourceBundle.getString("lblMensaje"));
		btnSend.setText(DadosGlobais.resourceBundle.getString("btnSend"));
		btnSend.setTooltip(new Tooltip(DadosGlobais.resourceBundle.getString("lblTitle")));
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		lblAdjunto.setText("* " + file.getName());
		
		//Set Idioma
		setIdioma();
		
		

		// Styling Form and TitleBar
		titleBar.setId("title-bar-form");
		anchorpMail.getStyleClass().add("bordered-titled-border");
		if (DadosGlobais.sistema.equals("Ultimate")) {
			anchorpMail.getStylesheets()
					.add(getClass().getResource("/styles/css/themes Ultimate/style_Ultimate.css").toExternalForm());
		} else if (DadosGlobais.sistema.equals("Professional")) {
			anchorpMail.getStylesheets().add(
					getClass().getResource("/styles/css/themes Professional/style_Professional.css").toExternalForm());
		} else if (DadosGlobais.sistema.equals("Enterprise")) {
			anchorpMail.getStylesheets()
					.add(getClass().getResource("/styles/css/themes Enterprise/style_Enterprise.css").toExternalForm());
		}
		
		icon.setImage(new Image("/styles/img/favicon.ico"));

	}

}
