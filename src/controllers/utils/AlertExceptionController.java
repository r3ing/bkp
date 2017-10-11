package controllers.utils;
import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.PopOver.ArrowLocation;
import application.DadosGlobais;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tools.application.Email;
import tools.utils.Util;

public class AlertExceptionController  implements Initializable {


	 	@FXML
	    private AnchorPane aPanePrincipal;
		
	 	@FXML
	    private TextField txtRazaoSocial;

	    @FXML
	    private TextField txtNomeFantasia;

	    @FXML
	    private Button btnAcessar, btnReportar;

	    @FXML
	    private DatePicker dateDataMovimento;

	    @FXML
	    private Button btnClose;

	    @FXML
	    private TextArea txtErro;
	    @FXML
	    private FontAwesomeIconView iconAlert;

	    
	    
	    private String textoAba;
	    private String caminhoFxml;
	    private Object controladora;
	    private TabPane tabPane;
	    private Util util = new Util();
	    private Stage stage,stg;
	    private String logErro;
	    private String logMsg;
		private PopOver popOver = new PopOver();
		private boolean flagBtnReport;
		public AlertExceptionController(){
			
		}


		public AlertExceptionController(String msg, String erro, boolean flagBtnReportar){
			this.logErro=erro;
			this.logMsg = msg;
			this.flagBtnReport = flagBtnReportar;
		}
		
	    
	    
	    @FXML
	    public void actionBtnAcessar(ActionEvent event) {
	    	    	
	    	stage = (Stage) aPanePrincipal.getScene().getWindow();
			stage.close();
			
	    }

	    @FXML
	    public void actionBtnClose(ActionEvent event) {
	    	stage = (Stage) aPanePrincipal.getScene().getWindow();
			stage.close();
	    }
	    
	   
	    @FXML
	    public void actionBtnReportar(ActionEvent event) throws AWTException, IOException {
	    	
	   	String nomeImg = DadosGlobais.usuarioLogado.getNome()+LocalDateTime.now().hashCode()+"_ImgErro.jpg";
     	File printScreenErro = util.printScreen(DadosGlobais.folderPrintScreen, nomeImg, "jpg");
        Email email = new Email("mail.eptusdaamazonia.com.br", "true", "587", "apollo@eptusdaamazonia.com.br", "true", "3165241e", printScreenErro);
			
    	String msgEmail = 
    			"Usuário: ["+DadosGlobais.usuarioLogado.getCodigo()+"] "+DadosGlobais.usuarioLogado.getNome()+"\n"
    			+"Data: "+Util.formataDataHoraString(util.geraDataMovto())+"\n"+
    			logMsg+"\n"+logErro;
    	
	    	Task<String> tarefaSendEmail = new Task<String>() {
				@Override
				protected String call() throws Exception {
					email.enviaEmail("bruno.carvalho@msn.com", msgEmail, "Exception Log ["+DadosGlobais.empresaLogada.getCodigo()+"] "+DadosGlobais.empresaLogada.getNomeFantasia());
					return "-";
				}

				@Override
				protected void succeeded() {
					stg.close();
					Util.closeWindow(aPanePrincipal);
				}

				@Override
				protected void failed() {
					stg.close();
					Util.alertError("Mail não pôde ser enviado.");

				}

				@Override
				protected void cancelled() {

					super.cancelled();
				}

			};

			Thread thread = new Thread(tarefaSendEmail);
			thread.setDaemon(true);
			thread.start();
			stg = ProgressBarForm.showProgressBar(AlertExceptionController.class, tarefaSendEmail, DadosGlobais.resourceBundle.getString("msg"),false);
	
	    
	    }
	    
	    public void showPopOver(){
	    	stage = (Stage) aPanePrincipal.getScene().getWindow();
	    	VBox vbox = new VBox();
			vbox.setPadding(new Insets(10));
			vbox.setSpacing(8);
			vbox.setAlignment(Pos.CENTER);
			vbox.setPrefWidth(stage.getWidth());
			
			
			Text title = new Text("Erro Retornado");
			title.setFont(Font.font("Arial", FontWeight.BOLD, 12));
			
			Label lb = new Label(logErro);
			lb.setWrapText(true);
		
			
			vbox.getChildren().add(title);
			vbox.getChildren().add(lb);

			popOver.setContentNode(vbox);
			popOver.setArrowLocation(ArrowLocation.TOP_LEFT);
			
			popOver.show(iconAlert);
			
			
	    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		txtErro.setEditable(false);
		txtErro.setText(logMsg);
		
		if(!flagBtnReport)
			btnReportar.setDisable(true);
		
		aPanePrincipal.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			switch (event.getCode()) {
			case ESCAPE:
				actionBtnClose(null);
				break;
			case ENTER:
				actionBtnClose(null);
				break;
			case H:
				if (event.isControlDown())
					
					showPopOver();
					
				break;
			default:
				break;
			}
		});

	}

}
