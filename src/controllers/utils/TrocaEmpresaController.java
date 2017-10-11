package controllers.utils;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import application.DadosGlobais;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import models.configuracoes.CompartilhamentoDAO;
import models.configuracoes.Empresa;
import models.configuracoes.EmpresaDAO;
import tools.utils.Util;

public class TrocaEmpresaController  implements Initializable {


	 	@FXML
	    private AnchorPane aPanePrincipal;
		
	 	@FXML
	    private TextField txtRazaoSocial;

	    @FXML
	    private TextField txtNomeFantasia;

	    @FXML
	    private Button btnAcessar;

	    @FXML
	    private ComboBox<Empresa> cboxEmpresa;

	    @FXML
	    private Button btnClose;

	    private String textoAba;
	    private String caminhoFxml;
	    private Object controladora;
	    private TabPane tabPane;
	    private Util ut = new Util();
	    private Stage stage;
	    Label lbEmpresa;

		public TrocaEmpresaController(Label lbEmp){
			this.lbEmpresa = lbEmp;
		}


		public TrocaEmpresaController(TabPane tabPanePrincipal,String tituloAba, String rotaFxml, Object controller){
			this.tabPane = tabPanePrincipal;
			this.textoAba = tituloAba;
			this.caminhoFxml = rotaFxml;
			this.controladora = controller;
		}
		
	    
	    
	    @FXML
	    public void actionBtnAcessar(ActionEvent event) {
	    	
	    	
	    	
	    	try {
	    		DadosGlobais.empresaLogada = cboxEmpresa.getValue();
		    	lbEmpresa.setText(DadosGlobais.empresaLogada.getNomeFantasia());
				DadosGlobais.listCodempCompartilha = CompartilhamentoDAO.class.newInstance().getCompartilhamentosById(DadosGlobais.empresaLogada.getCodigo(), DadosGlobais.empresaLogada.getCheckDelete());
				
				File fTmp = new File(DadosGlobais.folderTemp + "emp" + DadosGlobais.empresaLogada.getCodemp());
				if (!fTmp.exists())
					fTmp.mkdir();
				
				stage = (Stage) aPanePrincipal.getScene().getWindow();
				stage.close();
				
				
	    	} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
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
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		txtRazaoSocial.setText(DadosGlobais.empresaLogada.getRazaoSocial());
		txtNomeFantasia.setText(DadosGlobais.empresaLogada.getNomeFantasia());
		
		
		
		EmpresaDAO empHibDAO = new EmpresaDAO();
		
		List<Empresa> empDados = empHibDAO.getListEmpresa();

		cboxEmpresa.setConverter(new StringConverter<Empresa>() {

			private Map<String, Empresa> mapEmpresa = new HashMap<String, Empresa>();

			@Override
			public Empresa fromString(String codEmp) {
				
				return mapEmpresa.get(codEmp);
			}

			@Override
			public String toString(Empresa empresa) {
				
				mapEmpresa.put(String.valueOf(empresa.getCodemp()), empresa);
				
				return empresa.getNomeFantasia();
			
			}
		});
	
		for (int i = 0; i < empDados.size(); i++) {
			
			if(DadosGlobais.usuarioLogado.getCodEmpDispLogin().contains(empDados.get(i).getCodigo().toString())){
				
				cboxEmpresa.getItems().add(empDados.get(i));
					
			}
			
			
		}
		
		for (int i = 0; i < cboxEmpresa.getItems().size(); i++) {

			if (DadosGlobais.empresaLogada.getCodigo().equals(cboxEmpresa.getItems().get(i).getCodigo())) {

				cboxEmpresa.getSelectionModel().select(i);

			}

		}


		
		// SETA O FOCO INICIAL NO TEXTFIELD DE BUSCA
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						cboxEmpresa.requestFocus();
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


	public static void main(String[] args) {
		
	}

}
