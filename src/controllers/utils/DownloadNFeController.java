package controllers.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import application.DadosGlobais;
import br.com.samuelweb.nfe.Certificado;
import br.com.samuelweb.nfe.ConfiguracoesIniciaisNfe;
import br.com.samuelweb.nfe.Nfe;
import br.com.samuelweb.nfe.exception.NfeException;
import br.com.samuelweb.nfe.util.CertificadoUtil;
import br.com.samuelweb.nfe.util.ConstantesUtil;
import br.com.samuelweb.nfe.util.Estados;
import br.com.samuelweb.nfe.util.ObjetoUtil;
import br.com.samuelweb.nfe.util.XmlUtil;
import br.inf.portalfiscal.nfe.schema.distdfeint.DistDFeInt;
import br.inf.portalfiscal.nfe.schema.distdfeint.DistDFeInt.ConsChNFe;
import br.inf.portalfiscal.nfe.schema.distdfeint.DistDFeInt.DistNSU;
import br.inf.portalfiscal.nfe.schema.retdistdfeint.RetDistDFeInt;
import br.inf.portalfiscal.nfe.schema.retdistdfeint.RetDistDFeInt.LoteDistDFeInt.DocZip;
import controllers.compras.NotaFiscalEntradaController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import tools.controls.ComboBoxFilter;
import tools.enums.EnumManifestcaoDestinatario;
import tools.utils.Util;

public class DownloadNFeController implements Initializable{
	
	@FXML
    private AnchorPane anpDownloadNFe;

    @FXML
    private Label lblCNPJ;

    @FXML
    private TextField txtCNPJ;

    @FXML
    private Label lblCertDigital;

    @FXML
    private TextField txtCertDigital;

    @FXML
    private Label lblChaveNFe, lblJustif;

    @FXML
    private TextField txtChaveNFe;
    
    @FXML
    private CheckBox ckBoxActivarManif;

    @FXML
    private Button btnDowlnload, btnManifNFe, btnClose;
    
    @FXML
    private ComboBox<ComboBoxFilter> cboxTipoEvnto;

    @FXML
    private TextField txtCodigoEvnto;

    @FXML
    private TextArea txtAreaJustificativa;

    //PARAMETROS DA CLASSE
    private String cnpj ;
    private String certDigital;
    Util util = new Util();
     NotaFiscalEntradaController notaFiscalEntradaController;
     TextField txtEnderco;
    
   
	/**
	 * CONSTRUCTOR DA CLASSE
	 * @param cnpj
	 * @param certDigital
	 */
	public DownloadNFeController(TextField txtEnderco, NotaFiscalEntradaController class1){
		this.notaFiscalEntradaController = class1;	
		this.txtEnderco = txtEnderco;
	}
	
	
	
	
	//-----------------------------------ACTION BUTTONS-----------------------------------
	
	@FXML
	void onActionBtnClose(ActionEvent event){
		util.closeWindow(anpDownloadNFe);		
	}
	
	
	@FXML
	void actionBtnDownload(ActionEvent event) {
		if(Util.noEmpty(txtChaveNFe)){
			txtChaveNFe.requestFocus();
//			util.showAlert("O Campo Chave NFe é obrigatorio!", "error");
		}else{
			downloadNFe();
			
			
		}
			
	}
	
	@FXML
	void actionBtnManifNFe(ActionEvent event){
		
		String dhEvnto = Util.formatterDateHoraZone(LocalDateTime.now());
		String tpEvnto = cboxTipoEvnto.getSelectionModel().getSelectedItem().getField();
		String chaveNFe = txtChaveNFe.getText();
		String descEvnto;
		String xJust = null;//SÓ VAI SER USADA QUANDO tpEvnto=210240
		
		
		
		if(util.alertConfirmation("Você está tentando manifestar uma NF-e. \n Click no botão Manisfestar para continuar!")){
			
			switch (tpEvnto) {
			case "210200":
				descEvnto = "Confirmacao da Operacao";
				break;
			case "210210":
				descEvnto = "Ciencia da Operacao";
				manifestoNFe(chaveNFe, tpEvnto, descEvnto, dhEvnto);
				break;

			case "210220":
				descEvnto = "Desconhecimento da Operacao";
				break;
				
			case "210240":
				descEvnto = "Operacao nao Realizada";
				break;

			default:
				break;
			}

		}else{
			ckBoxActivarManif.setSelected(false);
			btnManifNFe.setDisable(true);
			btnDowlnload.setDisable(false);
		}
			
	}
	
	@FXML
    void actionCkBoxActivarManif(ActionEvent event) {
		if(Util.noEmpty(txtChaveNFe)){
			txtChaveNFe.requestFocus();
//			util.showAlert("O Campo Chave NFe é obrigatorio!", "error");
			ckBoxActivarManif.setSelected(false);
		}else{
			if(ckBoxActivarManif.isSelected()){
				btnManifNFe.setDisable(false);
			}else
				btnManifNFe.setDisable(true);	
		}
		
    }
	
	
	public boolean verificarCertDigital(){		
//		Certificado certificado = CertificadoUtil.listaCertificadosWindows();
		boolean exist = false;
		try {
			for(int i=0; i < CertificadoUtil.listaCertificadosWindows().size(); i++){
				if(Util.getStringConverterCNPJ(txtCNPJ.getText()).contentEquals(CertificadoUtil.listaCertificadosWindows().get(i).getNome())){
					Util.showNotification("certificado encontrado con exito");
					exist = true;
					break;
				}
			}
		} catch (NfeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return exist ? true : false;
	}
	
	public Certificado getCertificadoWindows(){		
		Certificado certificado = null;
		try {
			for(int i=0; i < CertificadoUtil.listaCertificadosWindows().size(); i++){				
				if(Util.getStringConverterCNPJ(txtCNPJ.getText()).contentEquals(CertificadoUtil.listaCertificadosWindows().get(i).getNome())){
					certificado = CertificadoUtil.listaCertificadosWindows().get(i);				
					break;
				}
			}
		} catch (NfeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return certificado;
	}
	
	
	
	public void downloadNFe(){
		try {
			// Inicia As Configurações			
//			try {
				ConfiguracoesIniciaisNfe config = iniciaConfigurações();
//			} catch (NfeException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			DistDFeInt distDFeInt = new DistDFeInt();
			distDFeInt.setVersao("1.01");
			distDFeInt.setTpAmb(config.getAmbiente());
			distDFeInt.setCUFAutor(config.getEstado().getCodigoIbge());
			// Substitua os X pelo CNPJ
			distDFeInt.setCNPJ(Util.getStringConverterCNPJ(DadosGlobais.empresaLogada.getCnpj()));
			// 09.094.516/0001-17
			// 54.630.397/0001-24

			DistNSU distNSU = new DistNSU();
			ConsChNFe chNFe = new ConsChNFe();

			chNFe.setChNFe(txtChaveNFe.getText());
			distDFeInt.setConsChNFe(chNFe);

			// Informe false para não fazer a validação do X ML - Ganho De
			// tempo.
			RetDistDFeInt retorno = Nfe.distribuicaoDfe(distDFeInt, false);

//			System.out.println("Status:" + retorno.getCStat());
//			System.out.println("Motivo:" + retorno.getXMotivo());
			
			if (retorno.getCStat().equals("138")) {
				List<DocZip> listaDoc = retorno.getLoteDistDFeInt().getDocZip();				
				for (DocZip docZip : listaDoc) {
//					System.out.println(docZip.getSchema());
					if(docZip.getSchema().equals("resNFe_v1.01.xsd")){
						boolean confirm = util.alertConfirmar("A NF-e não foi manifestada. Deseja manifestar agora?");
						if(confirm){
							ckBoxActivarManif.setSelected(true);
							btnManifNFe.setDisable(false);
							btnDowlnload.setDisable(true);
						}else{
							break;
						}
					}else if(docZip.getSchema().equals("procNFe_v3.10.xsd")){
						Writer wr = new FileWriter("C:\\EptusAM\\System.xml\\"+chNFe.getChNFe()+".xml");
						wr.write(XmlUtil.gZipToXml(docZip.getValue()));
						wr.close();		
						txtEnderco.setText(chNFe.getChNFe()+".xml");
						notaFiscalEntradaController.changeData();
						Util.closeWindow(anpDownloadNFe);
					}
				}

			} else {
				util.showAlert("Status " + retorno.getCStat() + "\n" + retorno.getXMotivo(), "error");
			}

		} catch (NfeException | IOException e) {
			System.out.println("Erro:" + e.getMessage());
		}
	}

	public ConfiguracoesIniciaisNfe iniciaConfigurações() throws NfeException {
		// Certificado Windows
		Certificado certificado = null;
		List<Certificado> listCertificados = Util.getAllCertificate();
		for(int i=0; i <listCertificados.size(); i++){
			//System.out.println( listCertificados.get(i).getVencimento().toString().equals(DadosGlobais.empresaLogada.getConfig().get(0).getGerCertificadoDtVencto()) + "  ----  data cert: "+listCertificados.get(i).getVencimento()+" ----- data bD: "+DadosGlobais.empresaLogada.getConfig().get(0).getGerCertificadoDtVencto());
			if(listCertificados.get(i).getNome().equals(DadosGlobais.empresaLogada.getConfig().getGerCertificadoDigital()) && listCertificados.get(i).getVencimento().toString().equals(DadosGlobais.empresaLogada.getConfig().getGerCertificadoDtVencto())){
				certificado = listCertificados.get(i);
				break;
			}
		}
		return ConfiguracoesIniciaisNfe.iniciaConfiguracoes(Estados.AM, ConstantesUtil.AMBIENTE.PRODUCAO, certificado,
				"C:\\NFe\\3423", ConstantesUtil.VERSAO.V3_10);
	}
	
	
	public void manifestoNFe(String chaveNFe, String tpEvnto, String descEvnto, String dhEvnto){			
		 try{			 
				 //Inicia As Configurações
				 //Para Mais informações:
//				 https://github.com/Samuel-Oliveira/Java_NFe/wiki/Configura%C3%A7%C3%B5es-Nfe
				 ConfiguracoesIniciaisNfe config = iniciaConfigurações();
				 
				
				 //Substituua os X Pela Chave da Nota que deseja Manifestar
				 
				 //Identificador da TAG a ser assinada, a regra de formação do Id é: “ID” + tpEvento + chave da NF-e + nSeqEvento
				 String id = "ID"+tpEvnto+chaveNFe+"01";
				
				 br.inf.portalfiscal.nfe.schema.envConfRecebto.TEnvEvento envEvento = new br.inf.portalfiscal.nfe.schema.envConfRecebto.TEnvEvento();
				 envEvento.setVersao("1.00");
				 envEvento.setIdLote("1");
				
				 br.inf.portalfiscal.nfe.schema.envConfRecebto.TEvento evento = new br.inf.portalfiscal.nfe.schema.envConfRecebto.TEvento();
				 evento.setVersao("1.00");
				
				 br.inf.portalfiscal.nfe.schema.envConfRecebto.TEvento.InfEvento
				 infEvento = new
				 br.inf.portalfiscal.nfe.schema.envConfRecebto.TEvento.InfEvento();
				 infEvento.setId(id);
				 infEvento.setCOrgao("91");
				 infEvento.setTpAmb(config.getAmbiente());
				
				 //Substituua os X Pelo CNPJ
				 infEvento.setCNPJ(Util.getStringConverterCNPJ(DadosGlobais.empresaLogada.getCnpj()));
				 infEvento.setChNFe(chaveNFe);
				
				 //Altere a Data
				 infEvento.setDhEvento(dhEvnto);
				 infEvento.setTpEvento(cboxTipoEvnto.getSelectionModel().getSelectedItem().getField());
				 infEvento.setNSeqEvento("1");
				 infEvento.setVerEvento("1.00");
				
				 br.inf.portalfiscal.nfe.schema.envConfRecebto.TEvento.InfEvento.DetEvento
				 detEvento = new
				 br.inf.portalfiscal.nfe.schema.envConfRecebto.TEvento.InfEvento.DetEvento();
				 detEvento.setVersao("1.00");
				 detEvento.setDescEvento(descEvnto);
				 infEvento.setDetEvento(detEvento);
				 evento.setInfEvento(infEvento);
				 envEvento.getEvento().add(evento);
				
				 //Informe false para não fazer a validação do XML - Ganho De tempo.
				 br.inf.portalfiscal.nfe.schema.retEnvConfRecebto.TRetEnvEvento
				 retorno = Nfe.manifestacao(envEvento,false);
				 System.out.println("Status:" +
				 retorno.getRetEvento().get(0).getInfEvento().getCStat());
				 System.out.println("Motivo:" +
				 retorno.getRetEvento().get(0).getInfEvento().getXMotivo());
				 System.out.println("Data:" +
				 retorno.getRetEvento().get(0).getInfEvento().getDhRegEvento());
				
				 } catch (NfeException e) {
				 System.out.println("Erro:" + e.getMessage());
				 }
				
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		Platform.runLater(new Runnable() {			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				txtChaveNFe.requestFocus();
			}
		});
		
		//CARREGAR OS STYLE DO SISTEMA
		anpDownloadNFe.getStylesheets().add(getClass().getResource("/styles/css/themes "+DadosGlobais.empresaLogada.getSistema()+ "/style_"+DadosGlobais.empresaLogada.getSistema()+".css").toExternalForm());
		Util.onlyNumbers(txtChaveNFe);
		Util.maxCharacters(44, txtChaveNFe);
//		txtChaveNFe.setOnKeyPressed(new EventHandler<Event>() {
//
//			@Override
//			public void handle(Event event) {
//				// TODO Auto-generated method stub
//				Util.onlyNumbers(txtChaveNFe);				
//			}
//		});
		
		Util.setKeyPressDefaultStyles(txtChaveNFe);
		
		txtCNPJ.setText(DadosGlobais.empresaLogada.getCnpj());
		txtCertDigital.setText(DadosGlobais.empresaLogada.getConfig().getGerCertificadoDigital());
		ObservableList<ComboBoxFilter> listCboxTipoEvnto = FXCollections.observableArrayList();
		for(int i=0; i<EnumManifestcaoDestinatario.values().length; i++){
			listCboxTipoEvnto.add(new ComboBoxFilter(EnumManifestcaoDestinatario.values()[i].manifesto, EnumManifestcaoDestinatario.values()[i].id, EnumManifestcaoDestinatario.values()[i].codigoEvento.toString()));
		}
		
		cboxTipoEvnto.setItems(listCboxTipoEvnto);
		cboxTipoEvnto.getSelectionModel().select(1);
		txtCodigoEvnto.setText(cboxTipoEvnto.getSelectionModel().getSelectedItem().getField());
		cboxTipoEvnto.setConverter(new StringConverter<ComboBoxFilter>() {
			
			@Override
			public String toString(ComboBoxFilter object) {
				// TODO Auto-generated method stub
				if(object != null)
					return object.getValue();
				else
				return "";
			}
			
			@Override
			public ComboBoxFilter fromString(String string) {
				// TODO Auto-generated method stub
				return null;
			}
		});
		
		cboxTipoEvnto.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				txtCodigoEvnto.setText(cboxTipoEvnto.getSelectionModel().getSelectedItem().getField());
				if(cboxTipoEvnto.getSelectionModel().getSelectedItem().getAction() == 4){
					txtAreaJustificativa.setDisable(false);
					lblJustif.setDisable(false);
				}else{
					txtAreaJustificativa.setDisable(true);
					lblJustif.setDisable(true);
				}
			}
		});
		
		
		
	}
	
	

}
