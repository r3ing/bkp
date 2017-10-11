package controllers.utils;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.DadosGlobais;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tools.utils.Util;

public class ControleSegurancaController  implements Initializable {


	 	@FXML
	    private AnchorPane aPanePrincipal;
		
	 	@FXML
	    private TextField txtRazaoSocial;

	    @FXML
	    private TextField txtNomeFantasia;

	    @FXML
	    private Button btnAcessar;

	    @FXML
	    private PasswordField txtChaveSeguranca;

	    @FXML
	    private Button btnClose;

	    private String textoAba;
	    private String caminhoFxml;
	    private Object controladora;
	    private TabPane tabPane;
	    private Util ut = new Util();
	    private Stage stage;
	    

		public ControleSegurancaController(){
			
		}


		public ControleSegurancaController(TabPane tabPanePrincipal,String tituloAba, String rotaFxml, Object controller){
			this.tabPane = tabPanePrincipal;
			this.textoAba = tituloAba;
			this.caminhoFxml = rotaFxml;
			this.controladora = controller;
		}
		
	    
	    
	    @FXML
	    public void actionBtnAcessar(ActionEvent event) {
	    	try {
	    		
	    		stage = (Stage) aPanePrincipal.getScene().getWindow();
				stage.close();
				ut.crearTab(tabPane, textoAba, caminhoFxml, controladora, true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }

	    @FXML
	    public void actionBtnClose(ActionEvent event) {
	    	stage = (Stage) aPanePrincipal.getScene().getWindow();
			stage.close();
	    }
	    
	    @FXML
		void keyPressedTxtChaveSeguranca(KeyEvent event) {
			if (event.getCode().equals(KeyCode.ENTER)) {
				actionBtnAcessar(null);
			}
		}
	
	
	@SuppressWarnings("incomplete-switch")
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		
		
		txtRazaoSocial.setText(DadosGlobais.empresaLogada.getRazaoSocial());
		txtNomeFantasia.setText(DadosGlobais.empresaLogada.getNomeFantasia());
		
		
		// SETA O FOCO INICIAL NO TEXTFIELD DE BUSCA
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						txtChaveSeguranca.requestFocus();
					}
				});

		
		aPanePrincipal.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			switch (event.getCode()) {
			case ESCAPE:
				actionBtnClose(null);
				break;

			}
		});

	}

}
