package controllers.utils;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

import application.DadosGlobais;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.LocalDateStringConverter;
import javafx.util.converter.LocalDateTimeStringConverter;
import models.configuracoes.EmpresaDAO;
import tools.utils.Util;

public class TrocaDataSistemaController  implements Initializable {


	 	@FXML
	    private AnchorPane aPanePrincipal;
		
	 	@FXML
	    private TextField txtRazaoSocial;

	    @FXML
	    private TextField txtNomeFantasia;

	    @FXML
	    private Button btnAcessar;

	    @FXML
	    private DatePicker dateDataMovimento;

	    @FXML
	    private Button btnClose;

	    @FXML
	    private Label lbDataLimite;
	    
	    
	    
	    private String textoAba;
	    private String caminhoFxml;
	    private Object controladora;
	    private TabPane tabPane;
	    private Util ut = new Util();
	    private Stage stage;
	    Label data;

		public TrocaDataSistemaController(Label data){
			this.data = data;
		}


		public TrocaDataSistemaController(TabPane tabPanePrincipal,String tituloAba, String rotaFxml, Object controller){
			this.tabPane = tabPanePrincipal;
			this.textoAba = tituloAba;
			this.caminhoFxml = rotaFxml;
			this.controladora = controller;
		}
		
	    
	    
	    @FXML
	    public void actionBtnAcessar(ActionEvent event) {
	    	    	
	    	DadosGlobais.dataMovtoSistema = LocalDateTime.of(dateDataMovimento.getValue(), LocalTime.now()) ;
			data.setText(ut.formataDataString(DadosGlobais.dataMovtoSistema));
	    	stage = (Stage) aPanePrincipal.getScene().getWindow();
			stage.close();
			
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
		// TODO Auto-generated method stub
		
		LocalDateTime hoje = LocalDateTime.now();
		
		LocalDateTime dataLimiteRetro = hoje.minusDays(DadosGlobais.usuarioLogado.getConfDiasRetroDtMovto());
		
		LocalDateTime dataLimiteAvan = hoje.plusDays(DadosGlobais.usuarioLogado.getConfDiasAvanDtMovto());
		
		txtRazaoSocial.setText(DadosGlobais.empresaLogada.getRazaoSocial());
		txtNomeFantasia.setText(DadosGlobais.empresaLogada.getNomeFantasia());
		//String data[] = Util.formataData(DadosGlobais.dataMovtoSistema, "dd/MM/yyyy").split("/");
		
		
		// Color of the selected date range in DatePicker
				Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
					@Override
					public DateCell call(final DatePicker datePicker) {
						return new DateCell() {
							@Override
							public void updateItem(LocalDate item, boolean empty) {
								super.updateItem(item, empty);
								if (item.isAfter(dataLimiteRetro.minusDays(1).toLocalDate()) && item.isBefore(dataLimiteAvan.plusDays(1).toLocalDate()))
									setStyle("-fx-background-color: #29B6F6;");
								else
									setDisable(true);
							}
						};
					}
				};

		
				dateDataMovimento.setDayCellFactory(dayCellFactory);
			

		
		dateDataMovimento.setValue(hoje.toLocalDate());
		
		
		
		
		
		lbDataLimite.setText("Data Limite: "+ Util.formataDataString(dataLimiteRetro) + " à " + Util.formataDataString(dataLimiteAvan));
		
		// SETA O FOCO INICIAL NO TEXTFIELD DE BUSCA
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						dateDataMovimento.requestFocus();
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
