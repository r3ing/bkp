package controllers.utils;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import application.DadosGlobais;
import application.Main;
import controllers.application.TelaPrincipalEptusController;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.configuracoes.CompartilhamentoDAO;
import models.configuracoes.EmpresaDAO;
import models.configuracoes.NivelAcesso;
import models.configuracoes.NivelAcessoDAO;
import models.configuracoes.Usuario;
import models.configuracoes.UsuarioDAO;
import tools.criptografia.EncryptMD5;
import tools.enums.EnumLogRetornoStatus;
import tools.utils.LogRetorno;
import tools.utils.Util;

public class TrocaUsuarioController  implements Initializable {


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
	    private TabPane tabPanePrincipal;
	    private Util ut = new Util();
	    private Stage stage;
	    private Label lbNomeUsuario;
	    private ImageView idiomaImg;
	    
	    private TelaPrincipalEptusController m;
	    MenuBar mn;
	    Util util = new Util();

		public TrocaUsuarioController(){
			
		}


		//public TrocaUsuarioController(AnchorPane panePrincipal, TelaPrincipalEptusController telaPrincipalEptusController, MenuBar mn, Label lb){
			public TrocaUsuarioController(MenuBar mn, TabPane tabPanePrincipal ,Label lbUsuario, ImageView idiomaImg){
			this.lbNomeUsuario = lbUsuario;
			this.mn=mn;
			this.tabPanePrincipal = tabPanePrincipal;
			this.idiomaImg = idiomaImg;
		}
		
	    
	    @FXML
	    public void actionBtnAcessar(ActionEvent event) {
	      		
	    	UsuarioDAO usHibDAO = new UsuarioDAO();
			
	    	String senha = txtChaveSeguranca.getText();

			if (!senha.equals("")) {
				
				String senhaMD5 = EncryptMD5.getMD5(senha);
				
				LogRetorno log = usHibDAO.loginUser(DadosGlobais.empresaLogada, senhaMD5);

				if (log.getStatus().equals(EnumLogRetornoStatus.SUCESSO)) {
					
					File fTmp = new File(DadosGlobais.folderGridLog + "Forms[U"+ DadosGlobais.usuarioLogado.getCodigo() + "E"+DadosGlobais.usuarioLogado.getCodemp()+"]" );
					if (!fTmp.exists())
						fTmp.mkdir();

					stage = (Stage) aPanePrincipal.getScene().getWindow();
					stage.close();
					
					try {
						ut.permissoesMenuPrincipal(mn.getMenus(), DadosGlobais.usuarioLogado.getNiveisAcesso());
						lbNomeUsuario.setText(DadosGlobais.usuarioLogado.getNome());
						Image img = new Image("/styles/img/"+DadosGlobais.idioma+".png");
						idiomaImg.setImage(img);
					
					
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				} else {
					util.showAlert(log.getMsg(),"error");
				}
			} else {
				util.showAlert("O campo Senha é ogrigatorio!","warning");
				txtChaveSeguranca.requestFocus();
			}
				
	    }

	    @FXML
	    public void actionBtnClose(ActionEvent event) {
	    	System.exit(0);
	    	
			
	    }
	    
	    @FXML
		void keyPressedTxtChaveSeguranca(KeyEvent event) {
			if (event.getCode().equals(KeyCode.ENTER)) {
				actionBtnAcessar(null);
			}
		}
	
	
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
