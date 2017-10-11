package controllers.utils;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.CustomTextField;

import application.DadosGlobais;
import br.com.samuelweb.nfe.Certificado;
import br.com.samuelweb.nfe.exception.NfeException;
import br.com.samuelweb.nfe.util.CertificadoUtil;
import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import tools.utils.Util;

public class ConfigCertificadoController implements Initializable{

	@FXML
	private AnchorPane anpConfigColumn;

	@FXML
	private Pane titleBar;

	@FXML
	private Button btnClose;

	@FXML
	private Button btnSave;

	@FXML
	private Label lblTitleBarConfigCert, lblCertDisponiveis, lblDadosCert, lblNomeCert, lblTipoCert, lblDataVencto, lblDiasRest, 
			lblNomeCertSelect, lblTipoCertSelect, lblDataVencSelect, lblDiasRestSelect;

	@FXML
	private ComboBox<Certificado> cmbCertificados;

	Stage stg;
	private CustomTextField  txtNomeCert;    
	private TextField txtDataVencto, txtDiasRestant;
	

	//CONSTRUCTOR DA CLASSE
	public ConfigCertificadoController(CustomTextField txtNomeCert, TextField txtDataVencto, TextField txtDiasRestant){
		this.txtNomeCert = txtNomeCert;
		this.txtDataVencto = txtDataVencto;
		this.txtDiasRestant = txtDiasRestant;
	}

	@FXML
	void actionBtnSave(ActionEvent event) {
		Certificado certificado = cmbCertificados.getSelectionModel().getSelectedItem();
		String cnpj = certificado.getNome().substring(certificado.getNome().length()-14, certificado.getNome().length());
		if(cnpj.equals(Util.getStringConverterCNPJ(DadosGlobais.empresaLogada.getCnpj()))){
			txtNomeCert.setText(certificado.getNome());
			txtDataVencto.setText(Util.dateFormatInToOut(certificado.getVencimento().toString(), "yyyy-MM-dd", "dd/MM/yyyy"));
			txtDiasRestant.setText(String.valueOf(certificado.getDiasRestantes()));
			Util.closeWindow(anpConfigColumn);
		}else{
			Util.alertError("O CNPJ do certificado selecionado, não corresponde ao CNPJ da empresa logada no sistema!");
		}	

	}

	@FXML
	void onActionBtnClose(ActionEvent event) {
		Util.closeWindow(anpConfigColumn);
	}

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
				
		//CARREGAR OS STYLE DO SISTEMA
		anpConfigColumn.getStylesheets().add(getClass().getResource("/styles/css/themes "+DadosGlobais.empresaLogada.getSistema()+ "/style_"+DadosGlobais.empresaLogada.getSistema()+".css").toExternalForm());
		
		lblTitleBarConfigCert.setText(DadosGlobais.resourceBundle.getString("configCertificado.lblTitleBarConfigCert"));
		lblCertDisponiveis.setText(DadosGlobais.resourceBundle.getString("configCertificado.lblCertDisponiveis"));
		lblDadosCert.setText(DadosGlobais.resourceBundle.getString("configCertificado.lblDadosCert"));
		lblNomeCert.setText(DadosGlobais.resourceBundle.getString("configCertificado.lblNomeCert"));
		lblTipoCert.setText(DadosGlobais.resourceBundle.getString("configCertificado.lblTipoCert"));
		lblDataVencto.setText(DadosGlobais.resourceBundle.getString("configCertificado.lblDataVencto"));
		lblDiasRest.setText(DadosGlobais.resourceBundle.getString("configCertificado.lblDiasRest"));
		
		
		
		cmbCertificados.setItems(FXCollections.observableArrayList(Util.getAllCertificate()));

		cmbCertificados.setCellFactory(new Callback<ListView<Certificado>, ListCell<Certificado>>() {			
			@Override
			public ListCell<Certificado> call(ListView<Certificado> arg0) {
				// TODO Auto-generated method stub
				return new ListCell<Certificado>(){
					@Override
					protected void updateItem(Certificado item, boolean empty) {
						super.updateItem(item, empty);
						if (item == null || empty) {
							setGraphic(null);
						} else {
							setText(item.getNome()+" ["+item.getVencimento()+"]");
						}
					}
				} ;
			}
		});

		cmbCertificados.setConverter(new StringConverter<Certificado>() {
			public String toString(Certificado user) {
				if (user == null){
					return null;
				} else {
					return user.getNome();
				}
			}

			public Certificado fromString(String userId) {
				return null;
			}
		});

		cmbCertificados.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				Certificado certificado = cmbCertificados.getSelectionModel().getSelectedItem();
				lblNomeCertSelect.setText(certificado.getNome());
				lblTipoCertSelect.setText(certificado.getTipo());
				lblDataVencSelect.setText(Util.dateFormatInToOut(certificado.getVencimento().toString(), "yyyy-MM-dd", "dd/MM/yyyy"));
				lblDiasRestSelect.setText(String.valueOf(certificado.getDiasRestantes()));
			}
		});	

		if(!txtNomeCert.equals("null")){
			for(int i=0; i<cmbCertificados.getItems().size(); i++){
				if(txtNomeCert.getText().equals(cmbCertificados.getItems().get(i).getNome())){
					cmbCertificados.getSelectionModel().select(i);
					lblNomeCertSelect.setText(cmbCertificados.getSelectionModel().getSelectedItem().getNome());
					lblTipoCertSelect.setText(cmbCertificados.getSelectionModel().getSelectedItem().getTipo());
					lblDataVencSelect.setText(Util.dateFormatInToOut(cmbCertificados.getSelectionModel().getSelectedItem().getVencimento().toString(), "yyyy-MM-dd", "dd/MM/yyyy"));
					lblDiasRestSelect.setText(cmbCertificados.getSelectionModel().getSelectedItem().getDiasRestantes().toString());
					break;
				}
			}
		}
	}

}
