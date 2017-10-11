package controllers.compras;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ResourceBundle;

import application.DadosGlobais;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
//import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.compras.ItemValor;
//import models.configuracoes.NivelAcesso;
import tools.utils.Util;


public class ComposicaoPrecosController implements Initializable {


	@FXML
	private AnchorPane aPanePrincipal;

	@FXML
	private Button btnClose,btnCancel;

	@FXML
	private Button btnRestauraPadrao;

	@FXML
	private Button btnReplicaFormacaoVarAta;

	@FXML
	private TextField txtPrecoCusto;

	@FXML
	private TextField txtVlrEmbalagem;

	@FXML
	private TextField txtPercDesconto;

	@FXML
	private TextField txtVlrDesconto;

	@FXML
	private TextField txtPercIPI;

	@FXML
	private TextField txtVlrIPI;

	@FXML
	private TextField txtPercFrete;

	@FXML
	private TextField txtVlrFrete;

	@FXML
	private TextField txtPercCredICMS;

	@FXML
	private TextField txtVlrCredICMS;

	@FXML
	private TextField txtPercICMSDesonerado;

	@FXML
	private TextField txtVlrICMSDesonerado;

	@FXML
	private TextField txtPercSubstituicao;

	@FXML
	private TextField txtVlrSubstituicao;

	@FXML
	private TextField txtPercCredPIS;

	@FXML
	private TextField txtVlrCredPIS;

	@FXML
	private TextField txtPercCredCOFINS;

	@FXML
	private TextField txtVlrCredCOFINS;

	@FXML
	private TextField txtPercDespAcessoria;

	@FXML
	private TextField txtVlrDespAcessoria;

	@FXML
	private TextField txtPercSeguro;

	@FXML
	private TextField txtVlrSeguro;

	@FXML
	private TextField txtPercOutrosCreditos;

	@FXML
	private TextField txtVlrOutrosCreditos;

	@FXML
	private TextField txtPercOutrosCustos;

	@FXML
	private TextField txtVlrOutrosCustos;

	@FXML
	private TextField txtPercMargenLiquidaVar, txtPercMargenBrutaVar;

	@FXML
	private TextField txtVlrMargenLiquidaVar;

	@FXML
	private TextField txtPercCustoOperacionalVar;

	@FXML
	private TextField txtVlrCustoOperacionalVar;

	@FXML
	private TextField txtPercPISVar;

	@FXML
	private TextField txtVlrPISVar;

	@FXML
	private TextField txtPercCOFINSVar;

	@FXML
	private TextField txtVlrCOFINSVar;

	@FXML
	private TextField txtPercICMSVar;

	@FXML
	private TextField txtVlrICMSVar;

	@FXML
	private TextField txtPercComissaoVar;

	@FXML
	private TextField txtVlrComissaoVar;

	@FXML
	private TextField txtPercImpostoRendaVar;

	@FXML
	private TextField txtVlrImpostoRendaVar;

	@FXML
	private TextField txtPercContSocialVar;

	@FXML
	private TextField txtVlrContSocialVar;

	@FXML
	private TextField txtPercFreteVendasVar;

	@FXML
	private TextField txtVlrFreteVendasVar;

	@FXML
	private TextField txtPercSimplesVar;

	@FXML
	private TextField txtVlrSimplesVar;

	@FXML
	private TextField txtPercOutrasDespVendaVar;

	@FXML
	private TextField txtVlrOutrasDespVendaVar;

	@FXML
	private TextField txtCustoRealCompra;

	@FXML
	private TextField txtValorVendaEmbVar;

	@FXML
	private TextField txtValorVendaVar;

	@FXML
	private TextField txtPercMargenLiquidaAta, txtPercMargenBrutaAta;

	@FXML
	private TextField txtVlrMargenLiquidaAta;

	@FXML
	private TextField txtPercCustoOperacionalAta;

	@FXML
	private TextField txtVlrCustoOperacionalAta;

	@FXML
	private TextField txtPercPISAta;

	@FXML
	private TextField txtVlrPISAta;

	@FXML
	private TextField txtPercCOFINSAta;

	@FXML
	private TextField txtVlrCOFINSAta;

	@FXML
	private TextField txtPercICMSAta;

	@FXML
	private TextField txtVlrICMSAta;

	@FXML
	private TextField txtPercComissaoAta;

	@FXML
	private TextField txtVlrComissaoAta;

	@FXML
	private TextField txtPercImpostoRendaAta;

	@FXML
	private TextField txtVlrImpostoRendaAta;

	@FXML
	private TextField txtPercContSocialAta;

	@FXML
	private TextField txtVlrContSocialAta;

	@FXML
	private TextField txtPercFreteVendasAta;

	@FXML
	private TextField txtVlrFreteVendasAta;

	@FXML
	private TextField txtPercSimplesAta;

	@FXML
	private TextField txtVlrSimplesAta;

	@FXML
	private TextField txtPercOutrasDespVendaAta;

	@FXML
	private TextField txtVlrOutrasDespVendaAta;

	@FXML
	private TextField txtValorVendaEmbAta;

	@FXML
	private TextField txtValorVendaAta;

	@FXML
	private Button btnConfirm;

	@FXML
	private Label lblRepData,lblRepQtd, lblRepValor, lblRepFornecedor, lblCustoCompra, lblCustoMedio, lblCustoAnterior;


	private Object controllerDad;
	private ItemValor itemValor;
	private Util util = new Util();
	public static final BigDecimal ONE_HUNDRED = new BigDecimal(100.0000);

	public ComposicaoPrecosController()
	{		

	}	

	public ComposicaoPrecosController(Object controllerDad)
	{	

		this.controllerDad = controllerDad;

	}	


	@FXML
	void actionBtnClose(ActionEvent event) {

		if(controllerDad instanceof ItemController){		
			((ItemController) controllerDad).closedComposicaoPreco();
		}
		if(controllerDad instanceof NotaFiscalEntradaController){

			((NotaFiscalEntradaController) controllerDad).closedComposicaoPreco();	
		}
	}


	@FXML
	void actionBtnConfirm(ActionEvent event) {

		if(controllerDad instanceof ItemController){		
			((ItemController) controllerDad).setNewComposicaoPreco(itemValor);
			((ItemController) controllerDad).closedComposicaoPreco();
		}
	}

	@FXML
	void actionBtnReplicaFormacaoVarAta(ActionEvent event) {

		txtPercCustoOperacionalAta.setText(txtPercCustoOperacionalVar.getText());
		txtPercPISAta.setText(txtPercPISVar.getText());
		txtPercCOFINSAta.setText(txtPercCOFINSVar.getText());
		txtPercICMSAta.setText(txtPercICMSVar.getText());
		txtPercComissaoAta.setText(txtPercComissaoVar.getText());
		txtPercImpostoRendaAta.setText(txtPercImpostoRendaVar.getText());
		txtPercContSocialAta.setText(txtPercContSocialVar.getText());
		txtPercFreteVendasAta.setText(txtPercFreteVendasVar.getText());
		txtPercSimplesAta.setText(txtPercSimplesVar.getText());
		txtPercOutrasDespVendaAta.setText(txtPercOutrasDespVendaVar.getText());

		setFormacaoPrecoDetalhada(BigDecimal.ZERO, BigDecimal.ZERO);
	}

	@FXML
	void onKeyPressedTxtOutrosCustos(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.TAB)) {
			util.setFocus(txtPercMargenLiquidaVar);
		}
	}

	@FXML
	void onKeyPressedTxtPrecoCusto(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.TAB)) {

			if(txtPercMargenLiquidaVar.isDisable()){

				util.setFocus(txtPercMargenBrutaVar);
				//txtPercMargenBrutaVar.requestFocus();

			}


		}
	}

	@FXML
	void onKeyPressedTxtVlrVendaAta(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.TAB) ) {
			util.setFocus(txtPrecoCusto);
		}
	}

	@FXML
	void onKeyPressedTxtVlrVendaVar(KeyEvent event) {

		if (event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.TAB) ) {
			if(txtPercMargenLiquidaAta.isDisable())
				util.setFocus(txtPercMargenBrutaAta);
			else
				util.setFocus(txtPercMargenLiquidaAta);
		}
	}		

	public void setFormacaoPrecoDetalhada(BigDecimal valorVendaVarejo, BigDecimal valorVendaAtacado){

		BigDecimal vlrVendaVarejo, vlrVendaAtacado, margemBrutaVarejo, margemBrutaAtacado, vlrCusto, subTotal, margemMarkup, markupDivisor;
		
		if(DadosGlobais.empresaLogada.getConfig().getCpaTipocalculopreco().equals(1)){
			

			vlrCusto = Util.decimalBRtoBigDecimal(4, txtPrecoCusto.getText());
			BigDecimal vlrEmbalagem = Util.decimalBRtoBigDecimal(4, txtVlrEmbalagem.getText());
			BigDecimal desconto = Util.decimalBRtoBigDecimal(4, txtPercDesconto.getText());
			BigDecimal ipi = Util.decimalBRtoBigDecimal(4, txtPercIPI.getText());
			BigDecimal frete = Util.decimalBRtoBigDecimal(4, txtPercFrete.getText());
			BigDecimal credIcms = Util.decimalBRtoBigDecimal(2, txtPercCredICMS.getText());
			BigDecimal icmsDesonerado = Util.decimalBRtoBigDecimal(2, txtPercICMSDesonerado.getText());
			BigDecimal icmsST = Util.decimalBRtoBigDecimal(2, txtPercSubstituicao.getText());
			BigDecimal credPis = Util.decimalBRtoBigDecimal(2, txtPercCredPIS.getText());
			BigDecimal credCofins = Util.decimalBRtoBigDecimal(2, txtPercCredCOFINS.getText());
			BigDecimal despesaAcessoria = Util.decimalBRtoBigDecimal(2, txtPercDespAcessoria.getText());
			BigDecimal seguro = Util.decimalBRtoBigDecimal(2, txtPercSeguro.getText());
			BigDecimal outrosCreditos = Util.decimalBRtoBigDecimal(2, txtPercOutrosCreditos.getText());
			BigDecimal outrosCustos = Util.decimalBRtoBigDecimal(2, txtPercOutrosCustos.getText());

			BigDecimal percMargemLiquidaVarejo = Util.decimalBRtoBigDecimal(4, txtPercMargenLiquidaVar.getText());
			BigDecimal percCustoOperacionalVarejo = Util.decimalBRtoBigDecimal(4,  txtPercCustoOperacionalVar.getText());
			BigDecimal percPISVarejo = Util.decimalBRtoBigDecimal(2,  txtPercPISVar.getText());
			BigDecimal percCOFINSVarejo = Util.decimalBRtoBigDecimal(2,  txtPercCOFINSVar.getText());
			BigDecimal percICMSVarejo = Util.decimalBRtoBigDecimal(2,  txtPercICMSVar.getText());
			BigDecimal percComissaoVarejo = Util.decimalBRtoBigDecimal(2,  txtPercComissaoVar.getText());
			BigDecimal percIRVarejo = Util.decimalBRtoBigDecimal(2,  txtPercImpostoRendaVar.getText());
			BigDecimal percContSocialVarejo = Util.decimalBRtoBigDecimal(2,  txtPercContSocialVar.getText());
			BigDecimal percFreteVarejo = Util.decimalBRtoBigDecimal(2,  txtPercFreteVendasVar.getText());
			BigDecimal percSimplesNacionalVarejo = Util.decimalBRtoBigDecimal(2,  txtPercSimplesVar.getText());
			BigDecimal percOutrasDespesasVarejo = Util.decimalBRtoBigDecimal(2,  txtPercOutrasDespVendaVar.getText());

			BigDecimal percMargemLiquidaAtacado = Util.decimalBRtoBigDecimal(4, txtPercMargenLiquidaAta.getText());
			BigDecimal percCustoOperacionalAtacado = Util.decimalBRtoBigDecimal(4,  txtPercCustoOperacionalAta.getText());
			BigDecimal percPISAtacado = Util.decimalBRtoBigDecimal(2,  txtPercPISAta.getText());
			BigDecimal percCOFINSAtacado = Util.decimalBRtoBigDecimal(2,  txtPercCOFINSAta.getText());
			BigDecimal percICMSAtacado = Util.decimalBRtoBigDecimal(2,  txtPercICMSAta.getText());
			BigDecimal percComissaoAtacado = Util.decimalBRtoBigDecimal(2,  txtPercComissaoAta.getText());
			BigDecimal percIRAtacado = Util.decimalBRtoBigDecimal(2,  txtPercImpostoRendaAta.getText());
			BigDecimal percContSocialAtacado = Util.decimalBRtoBigDecimal(2,  txtPercContSocialAta.getText());
			BigDecimal percFreteAtacado = Util.decimalBRtoBigDecimal(2,  txtPercFreteVendasAta.getText());
			BigDecimal percSimplesNacionalAtacado = Util.decimalBRtoBigDecimal(2,  txtPercSimplesAta.getText());
			BigDecimal percOutrasDespesasAtacado = Util.decimalBRtoBigDecimal(2,  txtPercOutrasDespVendaAta.getText());



			// ----------- CAMPO DESCONTO

			BigDecimal vlrDesconto = (vlrCusto.multiply(desconto)).divide(ONE_HUNDRED);

			txtVlrDesconto.setText(vlrDesconto.setScale(2,RoundingMode.HALF_EVEN).toString());

			subTotal = vlrCusto.add(vlrEmbalagem).subtract(vlrDesconto);

			// ------------ CAMPO IPI

			BigDecimal vlrIpi = ((subTotal).multiply(ipi)).divide(ONE_HUNDRED);

			txtVlrIPI.setText(vlrIpi.setScale(2,RoundingMode.HALF_EVEN).toString());

			// -------  CAMPO FRETE	

			BigDecimal vlrFrete = ((subTotal).multiply(frete)).divide(ONE_HUNDRED);

			txtVlrFrete.setText(vlrFrete.setScale(2, RoundingMode.HALF_EVEN).toString());

			// -------  CAMPO CREDITO ICMS	

			BigDecimal vlrCredIcms = ((subTotal).multiply(credIcms)).divide(ONE_HUNDRED);

			txtVlrCredICMS.setText(vlrCredIcms.setScale(2, RoundingMode.HALF_EVEN).toString());

			// -------  CAMPO ICMS DESONERADO

			BigDecimal vlrIcmsDesonerado = ((subTotal).multiply(icmsDesonerado)).divide(ONE_HUNDRED);

			txtVlrICMSDesonerado.setText(vlrIcmsDesonerado.setScale(2, RoundingMode.HALF_EVEN).toString());

			// -------  CAMPO ICMS ST

			BigDecimal vlrIcmsST = ((subTotal).multiply(icmsST)).divide(ONE_HUNDRED);

			txtVlrSubstituicao.setText(vlrIcmsST.setScale(2, RoundingMode.HALF_EVEN).toString());

			// -------  CAMPO CREDITO PIS	

			BigDecimal vlrCredPis = ((subTotal).multiply(credPis)).divide(ONE_HUNDRED);

			txtVlrCredPIS.setText(vlrCredPis.setScale(2, RoundingMode.HALF_EVEN).toString());

			// -------  CAMPO CREDITO COFINS	

			BigDecimal vlrCredCofins = ((subTotal).multiply(credCofins)).divide(ONE_HUNDRED);

			txtVlrCredCOFINS.setText(vlrCredCofins.setScale(2, RoundingMode.HALF_EVEN).toString());

			// -------  CAMPO DESPESA ACESSORIA

			BigDecimal vlrDespeAcessoria = ((subTotal).multiply(despesaAcessoria)).divide(ONE_HUNDRED);

			txtVlrDespAcessoria.setText(vlrDespeAcessoria.setScale(2, RoundingMode.HALF_EVEN).toString());

			// -------  CAMPO SEGURO

			BigDecimal vlrSeguro = ((subTotal).multiply(seguro)).divide(ONE_HUNDRED);

			txtVlrSeguro.setText(vlrSeguro.setScale(2, RoundingMode.HALF_EVEN).toString());

			// -------  CAMPO OUTROS CREDITOS

			BigDecimal vlrOutrosCreditos = ((subTotal).multiply(outrosCreditos)).divide(ONE_HUNDRED);

			txtVlrOutrosCreditos.setText(vlrOutrosCreditos.setScale(2, RoundingMode.HALF_EVEN).toString());

			// -------  CAMPO OUTROS CUSTOS

			BigDecimal vlrOutrosCustos = ((subTotal).multiply(outrosCustos)).divide(ONE_HUNDRED);

			txtVlrOutrosCustos.setText(vlrOutrosCustos.setScale(2, RoundingMode.HALF_EVEN).toString());


			// ----------- CUSTO REAL DE COMPRA

			BigDecimal custoReal = ((vlrCusto.add(vlrEmbalagem).subtract(vlrDesconto)).add(vlrIpi))
					.add(vlrFrete)
					.subtract(vlrCredIcms)
					.subtract(vlrIcmsDesonerado)
					.add(vlrIcmsST)
					.subtract(vlrCredPis)
					.subtract(vlrCredCofins)
					.add(vlrDespeAcessoria)
					.add(vlrSeguro)
					.subtract(vlrOutrosCreditos)
					.add(vlrOutrosCustos);

			txtCustoRealCompra.setText(custoReal.toString());


			// ----------------- ABA VENDA VAREJO -------------- //

			// ------------------- MARGEM LIQUIDA VAREJO --------//

			// -->> INDICE DE USADO PARA O MARKUP <---
			// ***  A FORMULA É MARKUP = 100% - ( A SOMA DAS INCIDENCIAS SOBRE A VENDA ))
			// *** MARKUP DIVISOR = MARKUP / 100
			// *** VALOR DE VENDA = CUSTO REAL / MARKUP DIVISOR

			margemMarkup = ONE_HUNDRED.subtract((percMargemLiquidaVarejo
					.add(percCustoOperacionalVarejo)
					.add(percPISVarejo)
					.add(percCOFINSVarejo)
					.add(percICMSVarejo)
					.add(percComissaoVarejo)
					.add(percIRVarejo)
					.add(percContSocialVarejo)
					.add(percFreteVarejo)
					.add(percSimplesNacionalVarejo)
					.add(percOutrasDespesasVarejo)
					));

			//inicia a margem de lucro e o valor de venda varejo em 0
			BigDecimal vlrMargemLucroVarejo = BigDecimal.valueOf(0.0000);

			vlrVendaVarejo = valorVendaVarejo;

			try {
				//Caso o valor de venda seja 0 o sistema calcula o valor de venda
				if(valorVendaVarejo.equals(BigDecimal.ZERO)){

					markupDivisor = margemMarkup.divide(ONE_HUNDRED, 4, RoundingMode.HALF_EVEN);

					if(markupDivisor.signum() > 0 )
						vlrVendaVarejo = custoReal.divide(markupDivisor, 4, RoundingMode.HALF_EVEN);

					else{

						vlrVendaVarejo = BigDecimal.ZERO.setScale(4, RoundingMode.HALF_EVEN);
					
					}

					vlrMargemLucroVarejo = (vlrVendaVarejo.multiply(percMargemLiquidaVarejo)).divide(ONE_HUNDRED , 4, RoundingMode.HALF_EVEN);

					//SENAO O SISTEMA GERA A MARGEM DE LUCRO LIQUIDA A PARTIR DO VALOR DE VENDA INSERIDO
				}else{

					margemMarkup = ONE_HUNDRED.subtract((percCustoOperacionalVarejo
							.add(percPISVarejo)
							.add(percCOFINSVarejo)
							.add(percICMSVarejo)
							.add(percComissaoVarejo)
							.add(percIRVarejo)
							.add(percContSocialVarejo)
							.add(percFreteVarejo)
							.add(percSimplesNacionalVarejo)
							.add(percOutrasDespesasVarejo)
							));


					BigDecimal percCusto = (custoReal.multiply(ONE_HUNDRED)).divide(valorVendaVarejo , 4, RoundingMode.HALF_EVEN);

					txtPercMargenLiquidaVar.setText(margemMarkup.subtract(percCusto).setScale(4, RoundingMode.HALF_EVEN).toString());

					vlrMargemLucroVarejo = (vlrVendaVarejo.multiply(margemMarkup.subtract(percCusto))).divide(ONE_HUNDRED , 4, RoundingMode.HALF_EVEN);
				}
				
				margemBrutaVarejo = ((vlrVendaVarejo.subtract(vlrCusto).divide((vlrCusto.divide(ONE_HUNDRED, 4, RoundingMode.HALF_EVEN)), 4, RoundingMode.HALF_EVEN)));
				
				txtPercMargenBrutaVar.setText(margemBrutaVarejo.setScale(4,RoundingMode.HALF_EVEN).toString());
					
			} catch (Exception e) {
				//util.showAlert("Erro ao Efetuar Calculo de Preco Detalhado!", "warning");
			}


			// ---------------------- CUSTO OPERACIONAL VAREJO ------------//

			BigDecimal vlrCustoOperacionalVarejo = (vlrVendaVarejo.multiply(percCustoOperacionalVarejo)).divide(ONE_HUNDRED);

			txtVlrCustoOperacionalVar.setText(vlrCustoOperacionalVarejo.setScale(2, RoundingMode.HALF_EVEN).toString());


			// ---------------------- CUSTO PIS VAREJO ------------//

			BigDecimal vlrPISVarejo = (vlrVendaVarejo.multiply(percPISVarejo)).divide(ONE_HUNDRED);

			txtVlrPISVar.setText(vlrPISVarejo.setScale(2, RoundingMode.HALF_EVEN).toString());

			// ---------------------- CUSTO COFINS VAREJO ------------//

			BigDecimal vlrCOFINSVarejo = (vlrVendaVarejo.multiply(percCOFINSVarejo)).divide(ONE_HUNDRED);

			txtVlrCOFINSVar.setText(vlrCOFINSVarejo.setScale(2, RoundingMode.HALF_EVEN).toString());

			// ---------------------- CUSTO ICMS VAREJO ------------//

			BigDecimal vlrICMSVarejo = (vlrVendaVarejo.multiply(percICMSVarejo)).divide(ONE_HUNDRED);

			txtVlrICMSVar.setText(vlrICMSVarejo.setScale(2, RoundingMode.HALF_EVEN).toString());

			// ---------------------- CUSTO COMISSAO VAREJO ------------//

			BigDecimal vlrComissaoVarejo = (vlrVendaVarejo.multiply(percComissaoVarejo)).divide(ONE_HUNDRED);

			txtVlrComissaoVar.setText(vlrComissaoVarejo.setScale(2, RoundingMode.HALF_EVEN).toString());

			// ---------------------- CUSTO IMPOSTO DE RENDA VAREJO ------------//

			BigDecimal vlrIRVarejo = (vlrVendaVarejo.multiply(percIRVarejo)).divide(ONE_HUNDRED);

			txtVlrImpostoRendaVar.setText(vlrIRVarejo.setScale(2, RoundingMode.HALF_EVEN).toString());

			// ---------------------- CUSTO CONTRIBUICAO SOCIAL VAREJO ------------//

			BigDecimal vlrContribSocialVarejo = (vlrVendaVarejo.multiply(percContSocialVarejo)).divide(ONE_HUNDRED);

			txtVlrContSocialVar.setText(vlrContribSocialVarejo.setScale(2, RoundingMode.HALF_EVEN).toString());

			// ---------------------- CUSTO FRETES VAREJO ------------//

			BigDecimal vlrFreteVarejo = (vlrVendaVarejo.multiply(percFreteVarejo)).divide(ONE_HUNDRED);

			txtVlrFreteVendasVar.setText(vlrFreteVarejo.setScale(2, RoundingMode.HALF_EVEN).toString());

			// ---------------------- CUSTO SIMPLES NACIONAL VAREJO ------------//

			BigDecimal vlrSimplesNacionalVarejo = (vlrVendaVarejo.multiply(percSimplesNacionalVarejo)).divide(ONE_HUNDRED);

			txtVlrSimplesVar.setText(vlrSimplesNacionalVarejo.setScale(2, RoundingMode.HALF_EVEN).toString());


			// ---------------------- CUSTO OUTRAS DESPESAS VAREJO ------------//

			BigDecimal vlrOutrasDespesasVarejo = (vlrVendaVarejo.multiply(percOutrasDespesasVarejo)).divide(ONE_HUNDRED);

			txtVlrOutrasDespVendaVar.setText(vlrOutrasDespesasVarejo.setScale(2, RoundingMode.HALF_EVEN).toString());


			// ---------- MARGEM VAREJO E VALOR DE VENDA VAREJO -------------- //


			txtVlrMargenLiquidaVar.setText(vlrMargemLucroVarejo.setScale(4, RoundingMode.HALF_EVEN).toString());
			
			txtValorVendaVar.setText(vlrVendaVarejo.setScale(4, RoundingMode.HALF_EVEN).toString());

			// ============== FIM ABA VENDA VAREJO =====================//


			// ----------------- ABA VENDA VAREJO -------------- //

			// ------------------- MARGEM LIQUIDA VAREJO --------//

			// -->> INDICE DE USADO PARA O MARKUP <---
			// ***  A FORMULA É MARKUP = 100% - ( A SOMA DAS INCIDENCIAS SOBRE A VENDA ))
			// *** MARKUP DIVISOR = MARKUP / 100
			// *** VALOR DE VENDA = CUSTO REAL / MARKUP DIVISOR

			margemMarkup = ONE_HUNDRED.subtract((percMargemLiquidaAtacado
					.add(percCustoOperacionalAtacado)
					.add(percPISAtacado)
					.add(percCOFINSAtacado)
					.add(percICMSAtacado)
					.add(percComissaoAtacado)
					.add(percIRAtacado)
					.add(percContSocialAtacado)
					.add(percFreteAtacado)
					.add(percSimplesNacionalAtacado)
					.add(percOutrasDespesasAtacado)
					));

			//inicia a margem de lucro e o valor de venda varejo em 0
			BigDecimal vlrMargemLucroAtacado = BigDecimal.valueOf(0.0000);

			vlrVendaAtacado = valorVendaAtacado;

			try {
				//Caso o valor de venda seja 0 o sistema calcula o valor de venda
				if(valorVendaAtacado.equals(BigDecimal.ZERO)){

					markupDivisor = margemMarkup.divide(ONE_HUNDRED, 4, RoundingMode.HALF_EVEN);

					if(markupDivisor.signum() > 0 )
						vlrVendaAtacado = custoReal.divide(markupDivisor, 4, RoundingMode.HALF_EVEN);

					else{

						vlrVendaAtacado = BigDecimal.ZERO.setScale(4, RoundingMode.HALF_EVEN);
						//util.showAlert("A soma das Incidências sobre o valor de VENDA ATACADO deve ser menor que 100", "warning");
					}

					vlrMargemLucroAtacado = (vlrVendaAtacado.multiply(percMargemLiquidaAtacado)).divide(ONE_HUNDRED , 4, RoundingMode.HALF_EVEN);

					//SENAO O SISTEMA GERA A MARGEM DE LUCRO LIQUIDA A PARTIR DO VALOR DE VENDA INSERIDO
				}else{

					margemMarkup = ONE_HUNDRED.subtract((percCustoOperacionalAtacado
							.add(percPISAtacado)
							.add(percCOFINSAtacado)
							.add(percICMSAtacado)
							.add(percComissaoAtacado)
							.add(percIRAtacado)
							.add(percContSocialAtacado)
							.add(percFreteAtacado)
							.add(percSimplesNacionalAtacado)
							.add(percOutrasDespesasAtacado)
							));


					BigDecimal percCusto = (custoReal.multiply(ONE_HUNDRED)).divide(valorVendaAtacado , 4, RoundingMode.HALF_EVEN);

					txtPercMargenLiquidaAta.setText(margemMarkup.subtract(percCusto).setScale(4, RoundingMode.HALF_EVEN).toString());

					vlrMargemLucroAtacado = (vlrVendaAtacado.multiply(margemMarkup.subtract(percCusto))).divide(ONE_HUNDRED , 4, RoundingMode.HALF_EVEN);
				}
				
				margemBrutaAtacado = ((vlrVendaAtacado.subtract(vlrCusto).divide((vlrCusto.divide(ONE_HUNDRED, 4, RoundingMode.HALF_EVEN)), 4, RoundingMode.HALF_EVEN)));
				
				txtPercMargenBrutaAta.setText(margemBrutaAtacado.setScale(4,RoundingMode.HALF_EVEN).toString());
				

			} catch (Exception e) {
				//util.showAlert("Erro ao Efetuar Calculo de Preco Detalhado!", "warning");
			}


			// ---------------------- CUSTO OPERACIONAL VAREJO ------------//

			BigDecimal vlrCustoOperacionalAtacado = (vlrVendaAtacado.multiply(percCustoOperacionalAtacado)).divide(ONE_HUNDRED);

			txtVlrCustoOperacionalAta.setText(vlrCustoOperacionalAtacado.setScale(2, RoundingMode.HALF_EVEN).toString());


			// ---------------------- CUSTO PIS VAREJO ------------//

			BigDecimal vlrPISAtacado = (vlrVendaAtacado.multiply(percPISAtacado)).divide(ONE_HUNDRED);

			txtVlrPISAta.setText(vlrPISAtacado.setScale(2, RoundingMode.HALF_EVEN).toString());

			// ---------------------- CUSTO COFINS VAREJO ------------//

			BigDecimal vlrCOFINSAtacado = (vlrVendaAtacado.multiply(percCOFINSAtacado)).divide(ONE_HUNDRED);

			txtVlrCOFINSAta.setText(vlrCOFINSAtacado.setScale(2, RoundingMode.HALF_EVEN).toString());

			// ---------------------- CUSTO ICMS VAREJO ------------//

			BigDecimal vlrICMSAtacado = (vlrVendaAtacado.multiply(percICMSAtacado)).divide(ONE_HUNDRED);

			txtVlrICMSAta.setText(vlrICMSAtacado.setScale(2, RoundingMode.HALF_EVEN).toString());

			// ---------------------- CUSTO COMISSAO VAREJO ------------//

			BigDecimal vlrComissaoAtacado = (vlrVendaAtacado.multiply(percComissaoAtacado)).divide(ONE_HUNDRED);

			txtVlrComissaoAta.setText(vlrComissaoAtacado.setScale(2, RoundingMode.HALF_EVEN).toString());

			// ---------------------- CUSTO IMPOSTO DE RENDA VAREJO ------------//

			BigDecimal vlrIRAtacado = (vlrVendaAtacado.multiply(percIRAtacado)).divide(ONE_HUNDRED);

			txtVlrImpostoRendaAta.setText(vlrIRAtacado.setScale(2, RoundingMode.HALF_EVEN).toString());

			// ---------------------- CUSTO CONTRIBUICAO SOCIAL VAREJO ------------//

			BigDecimal vlrContribSocialAtacado = (vlrVendaAtacado.multiply(percContSocialAtacado)).divide(ONE_HUNDRED);

			txtVlrContSocialAta.setText(vlrContribSocialAtacado.setScale(2, RoundingMode.HALF_EVEN).toString());

			// ---------------------- CUSTO FRETES VAREJO ------------//

			BigDecimal vlrFreteAtacado = (vlrVendaAtacado.multiply(percFreteAtacado)).divide(ONE_HUNDRED);

			txtVlrFreteVendasAta.setText(vlrFreteAtacado.setScale(2, RoundingMode.HALF_EVEN).toString());

			// ---------------------- CUSTO SIMPLES NACIONAL VAREJO ------------//

			BigDecimal vlrSimplesNacionalAtacado = (vlrVendaAtacado.multiply(percSimplesNacionalAtacado)).divide(ONE_HUNDRED);

			txtVlrSimplesAta.setText(vlrSimplesNacionalAtacado.setScale(2, RoundingMode.HALF_EVEN).toString());


			// ---------------------- CUSTO OUTRAS DESPESAS VAREJO ------------//

			BigDecimal vlrOutrasDespesasAtacado = (vlrVendaAtacado.multiply(percOutrasDespesasAtacado)).divide(ONE_HUNDRED);

			txtVlrOutrasDespVendaAta.setText(vlrOutrasDespesasAtacado.setScale(2, RoundingMode.HALF_EVEN).toString());


			// ---------- MARGEM VAREJO E VALOR DE VENDA VAREJO -------------- //


			txtVlrMargenLiquidaAta.setText(vlrMargemLucroAtacado.setScale(4, RoundingMode.HALF_EVEN).toString());

			txtValorVendaAta.setText(vlrVendaAtacado.setScale(4, RoundingMode.HALF_EVEN).toString());

			// ALTERA O OBJETO ITEM VALOR

			//---- INCIDENCIA DE COMPRA -----------------

//			itemValor = new ItemValor();
//
//			itemValor.setVlrCusto(Util.decimalBRtoBigDecimal(4, txtPrecoCusto.getText()));	
//
//			itemValor.setFpCustoRealCompra(Util.decimalBRtoBigDecimal(4, txtCustoRealCompra.getText()));	
//
//			itemValor.setFpVlrEmbalagem(Util.decimalBRtoBigDecimal(4,txtVlrEmbalagem.getText()));
//
//			itemValor.setFpDescontoCompra(Util.decimalBRtoBigDecimal(2,txtPercDesconto.getText()));
//
//			itemValor.setFpIpiCompra(Util.decimalBRtoBigDecimal(2,txtPercIPI.getText()));
//
//			itemValor.setFpFreteCompra(Util.decimalBRtoBigDecimal(2, txtPercFrete.getText()));
//
//			itemValor.setFpCreditoIcms(Util.decimalBRtoBigDecimal(2, txtPercCredICMS.getText()));
//
//			itemValor.setFpIcmsDesonerado(Util.decimalBRtoBigDecimal(2, txtPercICMSDesonerado.getText()));
//
//			itemValor.setFpSubstituicaoTrib(Util.decimalBRtoBigDecimal(2, txtPercSubstituicao.getText()));
//
//			itemValor.setFpCreditoPis(Util.decimalBRtoBigDecimal(2, txtPercCredPIS.getText()));
//
//			itemValor.setFpCreditoCofins(Util.decimalBRtoBigDecimal(2, txtPercCredCOFINS.getText()));
//
//			itemValor.setFpDespAcessoria(Util.decimalBRtoBigDecimal(2, txtPercDespAcessoria.getText()));
//
//			itemValor.setFpSeguro(Util.decimalBRtoBigDecimal(2, txtPercSeguro.getText()));
//
//			itemValor.setFpOutrosCreditos(Util.decimalBRtoBigDecimal(2, txtPercOutrosCreditos.getText()));
//
//			itemValor.setFpOutrosCustos(Util.decimalBRtoBigDecimal(2, txtPercOutrosCustos.getText()));
//
//
//			// -----------------INCIDENCIA VENDA VAREJO ------------------------------------
//
//			itemValor.setFpMargemLiquidaVarejo(Util.decimalBRtoBigDecimal(4, txtPercMargenLiquidaVar.getText()));
//
//			itemValor.setFpCustoOperacionalVarejo(Util.decimalBRtoBigDecimal(2, txtPercCustoOperacionalVar.getText()));
//
//			itemValor.setFpPisVendaVarejo(Util.decimalBRtoBigDecimal(2, txtPercPISVar.getText()));
//
//			itemValor.setFpCofinsVendaVarejo(Util.decimalBRtoBigDecimal(2, txtPercCOFINSVar.getText()));
//
//			itemValor.setFpIcmsIssVendaVarejo(Util.decimalBRtoBigDecimal(2, txtPercICMSVar.getText()));
//
//			itemValor.setFpComissaoVendaVarejo(Util.decimalBRtoBigDecimal(2, txtPercComissaoVar.getText()));		    
//
//			itemValor.setFpImpostoRendaVarejo(Util.decimalBRtoBigDecimal(2, txtPercImpostoRendaVar.getText()));		    
//
//			itemValor.setFpContribuicaoSocialVarejo(Util.decimalBRtoBigDecimal(2, txtPercContSocialVar.getText()));		    
//
//			itemValor.setFpFreteVendaVarejo(Util.decimalBRtoBigDecimal(2, txtPercFreteVendasVar.getText()));		    
//
//			itemValor.setFpSimplesNacionalVarejo(Util.decimalBRtoBigDecimal(2, txtPercSimplesVar.getText()));		    
//
//			itemValor.setFpOutrasDespesasVarejo(Util.decimalBRtoBigDecimal(2, txtPercOutrasDespVendaVar.getText()));		    
//
//			itemValor.setVlrVendaVarejo(Util.decimalBRtoBigDecimal(2, txtValorVendaVar.getText()));
//
//			// -----------------INCIDENCIA VENDA ATACADO ------------------------------------
//
//			itemValor.setFpMargemLiquidaAtacado(Util.decimalBRtoBigDecimal(4, txtPercMargenLiquidaAta.getText()));
//
//			itemValor.setFpCustoOperacionalAtacado(Util.decimalBRtoBigDecimal(2, txtPercCustoOperacionalAta.getText()));
//
//			itemValor.setFpPisVendaAtacado(Util.decimalBRtoBigDecimal(2, txtPercPISAta.getText()));
//
//			itemValor.setFpCofinsVendaAtacado(Util.decimalBRtoBigDecimal(2, txtPercCOFINSAta.getText()));
//
//			itemValor.setFpIcmsIssVendaAtacado(Util.decimalBRtoBigDecimal(2, txtPercICMSAta.getText()));
//
//			itemValor.setFpComissaoVendaAtacado(Util.decimalBRtoBigDecimal(2, txtPercComissaoAta.getText()));		    
//
//			itemValor.setFpImpostoRendaAtacado(Util.decimalBRtoBigDecimal(2, txtPercImpostoRendaAta.getText()));		    
//
//			itemValor.setFpContribuicaoSocialAtacado(Util.decimalBRtoBigDecimal(2, txtPercContSocialAta.getText()));		    
//
//			itemValor.setFpFreteVendaAtacado(Util.decimalBRtoBigDecimal(2, txtPercFreteVendasAta.getText()));		    
//
//			itemValor.setFpSimplesNacionalAtacado(Util.decimalBRtoBigDecimal(2, txtPercSimplesAta.getText()));		    
//
//			itemValor.setFpOutrasDespesasAtacado(Util.decimalBRtoBigDecimal(2, txtPercOutrasDespVendaAta.getText()));		    
//
//			itemValor.setVlrVendaAtacado(Util.decimalBRtoBigDecimal(2, txtValorVendaAta.getText()));

		//FORMACAO DE PRECO SIMPLES	
		}else
			{
	
			vlrCusto = Util.decimalBRtoBigDecimal(4, txtPrecoCusto.getText());

			if(valorVendaVarejo.equals(BigDecimal.ZERO)){

				margemBrutaVarejo = Util.decimalBRtoBigDecimal(4,txtPercMargenBrutaVar.getText());
				vlrVendaVarejo = vlrCusto.add((vlrCusto.multiply(margemBrutaVarejo.divide(ONE_HUNDRED , 2, RoundingMode.HALF_EVEN))));

				txtValorVendaVar.setText(vlrVendaVarejo.toString());
			
			}else{

				//MARGEM BRUTA (V - C)/(C/100)
				if(vlrCusto.signum() > 0)
					margemBrutaVarejo = ((valorVendaVarejo.subtract(vlrCusto).divide((vlrCusto.divide(ONE_HUNDRED, 4, RoundingMode.HALF_EVEN)), 4, RoundingMode.HALF_EVEN)));

				else
					margemBrutaVarejo = Util.decimalBRtoBigDecimal(2,"0.0000");

				txtPercMargenBrutaVar.setText(margemBrutaVarejo.toString());
			}
			
			if(valorVendaAtacado.equals(BigDecimal.ZERO)){
				
				margemBrutaAtacado = Util.decimalBRtoBigDecimal(4,txtPercMargenBrutaAta.getText());
				vlrVendaAtacado = vlrCusto.add((vlrCusto.multiply(margemBrutaAtacado.divide(ONE_HUNDRED , 2, RoundingMode.HALF_EVEN))));

				txtValorVendaAta.setText(vlrVendaAtacado.toString());

			}else{
				
				//MARGEM BRUTA (V - C)/(C/100)
				if(vlrCusto.signum() > 0)
					margemBrutaAtacado = ((valorVendaAtacado.subtract(vlrCusto).divide((vlrCusto.divide(ONE_HUNDRED, 4, RoundingMode.HALF_EVEN)), 4, RoundingMode.HALF_EVEN)));

				else
					margemBrutaAtacado = Util.decimalBRtoBigDecimal(2,"0.0000");

				txtPercMargenBrutaAta.setText(margemBrutaAtacado.toString());
				
			}
		}
		itemValor = new ItemValor();

		itemValor.setVlrCusto(Util.decimalBRtoBigDecimal(4, txtPrecoCusto.getText()));	

		itemValor.setFpCustoRealCompra(Util.decimalBRtoBigDecimal(4, txtCustoRealCompra.getText()));	

		itemValor.setFpVlrEmbalagem(Util.decimalBRtoBigDecimal(4,txtVlrEmbalagem.getText()));

		itemValor.setFpDescontoCompra(Util.decimalBRtoBigDecimal(2,txtPercDesconto.getText()));

		itemValor.setFpIpiCompra(Util.decimalBRtoBigDecimal(2,txtPercIPI.getText()));

		itemValor.setFpFreteCompra(Util.decimalBRtoBigDecimal(2, txtPercFrete.getText()));

		itemValor.setFpCreditoIcms(Util.decimalBRtoBigDecimal(2, txtPercCredICMS.getText()));

		itemValor.setFpIcmsDesonerado(Util.decimalBRtoBigDecimal(2, txtPercICMSDesonerado.getText()));

		itemValor.setFpSubstituicaoTrib(Util.decimalBRtoBigDecimal(2, txtPercSubstituicao.getText()));

		itemValor.setFpCreditoPis(Util.decimalBRtoBigDecimal(2, txtPercCredPIS.getText()));

		itemValor.setFpCreditoCofins(Util.decimalBRtoBigDecimal(2, txtPercCredCOFINS.getText()));

		itemValor.setFpDespAcessoria(Util.decimalBRtoBigDecimal(2, txtPercDespAcessoria.getText()));

		itemValor.setFpSeguro(Util.decimalBRtoBigDecimal(2, txtPercSeguro.getText()));

		itemValor.setFpOutrosCreditos(Util.decimalBRtoBigDecimal(2, txtPercOutrosCreditos.getText()));

		itemValor.setFpOutrosCustos(Util.decimalBRtoBigDecimal(2, txtPercOutrosCustos.getText()));


		// -----------------INCIDENCIA VENDA VAREJO ------------------------------------

		itemValor.setFpMargemLiquidaVarejo(Util.decimalBRtoBigDecimal(4, txtPercMargenLiquidaVar.getText()));
		
		itemValor.setMargemLucroBrutoVarejo(Util.decimalBRtoBigDecimal(4, txtPercMargenBrutaVar.getText()));

		itemValor.setFpCustoOperacionalVarejo(Util.decimalBRtoBigDecimal(2, txtPercCustoOperacionalVar.getText()));

		itemValor.setFpPisVendaVarejo(Util.decimalBRtoBigDecimal(2, txtPercPISVar.getText()));

		itemValor.setFpCofinsVendaVarejo(Util.decimalBRtoBigDecimal(2, txtPercCOFINSVar.getText()));

		itemValor.setFpIcmsIssVendaVarejo(Util.decimalBRtoBigDecimal(2, txtPercICMSVar.getText()));

		itemValor.setFpComissaoVendaVarejo(Util.decimalBRtoBigDecimal(2, txtPercComissaoVar.getText()));		    

		itemValor.setFpImpostoRendaVarejo(Util.decimalBRtoBigDecimal(2, txtPercImpostoRendaVar.getText()));		    

		itemValor.setFpContribuicaoSocialVarejo(Util.decimalBRtoBigDecimal(2, txtPercContSocialVar.getText()));		    

		itemValor.setFpFreteVendaVarejo(Util.decimalBRtoBigDecimal(2, txtPercFreteVendasVar.getText()));		    

		itemValor.setFpSimplesNacionalVarejo(Util.decimalBRtoBigDecimal(2, txtPercSimplesVar.getText()));		    

		itemValor.setFpOutrasDespesasVarejo(Util.decimalBRtoBigDecimal(2, txtPercOutrasDespVendaVar.getText()));		    

		itemValor.setVlrVendaVarejo(Util.decimalBRtoBigDecimal(2, txtValorVendaVar.getText()));

		// -----------------INCIDENCIA VENDA ATACADO ------------------------------------

		itemValor.setFpMargemLiquidaAtacado(Util.decimalBRtoBigDecimal(4, txtPercMargenLiquidaAta.getText()));

		itemValor.setMargemLucroBrutoAtacado(Util.decimalBRtoBigDecimal(4, txtPercMargenBrutaAta.getText()));
		
		itemValor.setFpCustoOperacionalAtacado(Util.decimalBRtoBigDecimal(2, txtPercCustoOperacionalAta.getText()));

		itemValor.setFpPisVendaAtacado(Util.decimalBRtoBigDecimal(2, txtPercPISAta.getText()));

		itemValor.setFpCofinsVendaAtacado(Util.decimalBRtoBigDecimal(2, txtPercCOFINSAta.getText()));

		itemValor.setFpIcmsIssVendaAtacado(Util.decimalBRtoBigDecimal(2, txtPercICMSAta.getText()));

		itemValor.setFpComissaoVendaAtacado(Util.decimalBRtoBigDecimal(2, txtPercComissaoAta.getText()));		    

		itemValor.setFpImpostoRendaAtacado(Util.decimalBRtoBigDecimal(2, txtPercImpostoRendaAta.getText()));		    

		itemValor.setFpContribuicaoSocialAtacado(Util.decimalBRtoBigDecimal(2, txtPercContSocialAta.getText()));		    

		itemValor.setFpFreteVendaAtacado(Util.decimalBRtoBigDecimal(2, txtPercFreteVendasAta.getText()));		    

		itemValor.setFpSimplesNacionalAtacado(Util.decimalBRtoBigDecimal(2, txtPercSimplesAta.getText()));		    

		itemValor.setFpOutrasDespesasAtacado(Util.decimalBRtoBigDecimal(2, txtPercOutrasDespVendaAta.getText()));		    

		itemValor.setVlrVendaAtacado(Util.decimalBRtoBigDecimal(2, txtValorVendaAta.getText()));

	}


	public void UpdateForm(ItemValor iv)
	{
		util.setFocus(txtPrecoCusto);

		//---- INCIDENCIA DE COMPRA -----------------

		txtPrecoCusto.setText(iv.getVlrCusto().toString());	

		txtVlrEmbalagem.setText(iv.getFpVlrEmbalagem().toString());

		txtPercDesconto.setText(iv.getFpDescontoCompra().toString());

		txtPercIPI.setText(iv.getFpIpiCompra().toString());

		txtPercFrete.setText(iv.getFpFreteCompra().toString());

		txtPercCredICMS.setText(iv.getFpCreditoIcms().toString());

		txtPercICMSDesonerado.setText(iv.getFpIcmsDesonerado().toString());

		txtPercSubstituicao.setText(iv.getFpSubstituicaoTrib().toString());

		txtPercCredPIS.setText(iv.getFpCreditoPis().toString());

		txtPercCredCOFINS.setText(iv.getFpCreditoCofins().toString());

		txtPercDespAcessoria.setText(iv.getFpDespAcessoria().toString());

		txtPercSeguro.setText(iv.getFpSeguro().toString());

		txtPercOutrosCreditos.setText(iv.getFpOutrosCreditos().toString());

		txtPercOutrosCustos.setText(iv.getFpOutrosCustos().toString());


		// -----------------INCIDENCIA VENDA VAREJO ------------------------------------

		txtPercMargenLiquidaVar.setText(iv.getFpMargemLiquidaVarejo().toString());
		
		txtPercMargenBrutaVar.setText(iv.getMargemLucroBrutoVarejo().toString());

		txtPercCustoOperacionalVar.setText(iv.getFpCustoOperacionalVarejo().toString());

		txtPercPISVar.setText(iv.getFpPisVendaVarejo().toString());

		txtPercCOFINSVar.setText(iv.getFpCofinsVendaVarejo().toString());		    

		txtPercICMSVar.setText(iv.getFpIcmsIssVendaVarejo().toString());

		txtPercComissaoVar.setText(iv.getFpComissaoVendaVarejo().toString());		    

		txtPercImpostoRendaVar.setText(iv.getFpImpostoRendaVarejo().toString());		    

		txtPercContSocialVar.setText(iv.getFpContribuicaoSocialVarejo().toString());		    

		txtPercFreteVendasVar.setText(iv.getFpFreteVendaVarejo().toString());		    

		txtPercSimplesVar.setText(iv.getFpSimplesNacionalVarejo().toString());		    

		txtPercOutrasDespVendaVar.setText(iv.getFpOutrasDespesasVarejo().toString());		    

		txtCustoRealCompra.setText(iv.getFpCustoRealCompra().toString());

		txtPercMargenBrutaVar.setText(iv.getMargemLucroBrutoVarejo().toString());

		txtValorVendaVar.setText(iv.getVlrVendaVarejo().toString());



		// -----------------INCIDENCIA VENDA ATACADO ------------------------------------  


		txtPercMargenLiquidaAta.setText(iv.getFpMargemLiquidaAtacado().toString());
		
		txtPercMargenBrutaAta.setText(iv.getMargemLucroBrutoAtacado().toString());

		txtPercCustoOperacionalAta.setText(iv.getFpCustoOperacionalAtacado().toString());

		txtPercPISAta.setText(iv.getFpPisVendaAtacado().toString());

		txtPercCOFINSAta.setText(iv.getFpCofinsVendaAtacado().toString());		    

		txtPercICMSAta.setText(iv.getFpIcmsIssVendaAtacado().toString());

		txtPercComissaoAta.setText(iv.getFpComissaoVendaAtacado().toString());		    

		txtPercImpostoRendaAta.setText(iv.getFpImpostoRendaAtacado().toString());		    

		txtPercContSocialAta.setText(iv.getFpContribuicaoSocialAtacado().toString());		    

		txtPercFreteVendasAta.setText(iv.getFpFreteVendaAtacado().toString());		    

		txtPercSimplesAta.setText(iv.getFpSimplesNacionalAtacado().toString());		    

		txtPercOutrasDespVendaAta.setText(iv.getFpOutrasDespesasAtacado().toString());		

		txtPercMargenBrutaAta.setText(iv.getMargemLucroBrutoAtacado().toString());

		txtValorVendaAta.setText(iv.getVlrVendaAtacado().toString());


		//DADOS REPOSICAO
		//txtDataUltRep.setText(util.dateFormatInToOut(tbViewPosicaoEstoque.getSelectionModel().getSelectedItem().getRepData().toString(),"yyyy-mm-dd", "dd/mm/yyyy"));
		lblRepData.setText(util.dateFormatInToOut(iv.getRepData().toString(),"yyyy-mm-dd", "dd/mm/yyyy"));
		lblRepFornecedor.setText(iv.getRepFornecedor());
		lblRepQtd.setText(iv.getRepQuantidade().toString());
		lblRepValor.setText(iv.getRepVlrCusto().toString());

		//DADOS CUSTOS HISTÓRICOS
		lblCustoMedio.setText(iv.getVlrCustoMedio().toString());
		lblCustoCompra.setText(iv.getVlrCustoCompra().toString());
		lblCustoAnterior.setText(iv.getVlrCustoAnterior().toString());

	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {

		//		Util.setFocus(txtPrecoCusto);
		//		txtPrecoCusto.requestFocus();
		if(DadosGlobais.empresaLogada.getConfig().getCpaTipocalculopreco().equals(0)){
			txtVlrEmbalagem.setDisable(true);
			txtPercDesconto.setDisable(true);
			txtPercIPI.setDisable(true);
			txtPercFrete.setDisable(true);
			txtPercCredICMS.setDisable(true);
			txtPercICMSDesonerado.setDisable(true);
			txtPercSubstituicao.setDisable(true);
			txtPercCredPIS.setDisable(true);
			txtPercCredCOFINS.setDisable(true);
			txtPercDespAcessoria.setDisable(true);
			txtPercSeguro.setDisable(true);
			txtPercOutrosCreditos.setDisable(true);
			txtPercOutrosCustos.setDisable(true);

			txtPercMargenLiquidaVar.setDisable(true);
			txtPercCustoOperacionalVar.setDisable(true);
			txtPercPISVar.setDisable(true);
			txtPercCOFINSVar.setDisable(true);
			txtPercICMSVar.setDisable(true);
			txtPercComissaoVar.setDisable(true);
			txtPercImpostoRendaVar.setDisable(true);
			txtPercContSocialVar.setDisable(true);
			txtPercFreteVendasVar.setDisable(true);
			txtPercSimplesVar.setDisable(true);
			txtPercOutrasDespVendaVar.setDisable(true);

			txtPercMargenLiquidaAta.setDisable(true);
			txtPercCustoOperacionalAta.setDisable(true);
			txtPercPISAta.setDisable(true);
			txtPercCOFINSAta.setDisable(true);
			txtPercICMSAta.setDisable(true);
			txtPercComissaoAta.setDisable(true);
			txtPercImpostoRendaAta.setDisable(true);
			txtPercContSocialAta.setDisable(true);
			txtPercFreteVendasAta.setDisable(true);
			txtPercSimplesAta.setDisable(true);
			txtPercOutrasDespVendaAta.setDisable(true);


			txtPercMargenBrutaVar.setDisable(false);
			txtPercMargenBrutaAta.setDisable(false);
		}

		Util.decimalBRNegativo(DadosGlobais.empresaLogada.getConfig().getCpaQtdcasasdecimaisvalor(), txtVlrMargenLiquidaVar, txtVlrMargenLiquidaAta, txtPercMargenBrutaVar,txtPercMargenBrutaAta);
		
		Util.decimalBRNegativo(4, txtPercMargenLiquidaVar, txtPercMargenLiquidaAta);

		Util.decimalBR(DadosGlobais.empresaLogada.getConfig().getCpaQtdcasasdecimaisvalor(), txtPrecoCusto, txtVlrEmbalagem, txtCustoRealCompra);

		Util.decimalBR(DadosGlobais.empresaLogada.getConfig().getCpaQtdcasasdecimaisvalor(), txtValorVendaVar, txtValorVendaAta);

		Util.decimalBR(2, txtPercDesconto, txtVlrDesconto, txtPercIPI, txtVlrIPI, txtPercFrete,
				txtVlrFrete, txtPercCredICMS, txtVlrCredICMS, txtPercICMSDesonerado, txtVlrICMSDesonerado, txtPercSubstituicao,	    
				txtVlrSubstituicao, txtPercCredPIS, txtVlrCredPIS, txtPercCredCOFINS, txtVlrCredCOFINS, txtPercDespAcessoria, txtVlrDespAcessoria,	    
				txtPercSeguro, txtVlrSeguro, txtPercOutrosCreditos, txtVlrOutrosCreditos, txtPercOutrosCustos, txtVlrOutrosCustos,	    
				txtPercCustoOperacionalVar, txtVlrCustoOperacionalVar, txtPercPISVar, txtVlrPISVar, txtPercCOFINSVar,   
				txtVlrCOFINSVar, txtPercICMSVar, txtVlrICMSVar, txtPercComissaoVar, txtVlrComissaoVar, txtPercImpostoRendaVar, txtVlrImpostoRendaVar,	    
				txtPercContSocialVar, txtVlrContSocialVar, txtPercFreteVendasVar, txtVlrFreteVendasVar, txtPercSimplesVar, txtVlrSimplesVar,	    
				txtPercOutrasDespVendaVar, txtVlrOutrasDespVendaVar, txtValorVendaEmbVar,	    
				txtPercCustoOperacionalAta, txtVlrCustoOperacionalAta, txtPercPISAta, txtVlrPISAta, txtPercCOFINSAta,	    
				txtVlrCOFINSAta, txtPercICMSAta, txtVlrICMSAta, txtPercComissaoAta, txtVlrComissaoAta, txtPercImpostoRendaAta, txtVlrImpostoRendaAta,	    
				txtPercContSocialAta,  txtVlrContSocialAta, txtPercFreteVendasAta, txtVlrFreteVendasAta, txtPercSimplesAta, txtVlrSimplesAta, txtPercOutrasDespVendaAta,
				txtVlrOutrasDespVendaAta);

		Util.setStyleOnFocus( txtPercMargenBrutaVar,txtPercMargenBrutaAta, txtPrecoCusto, txtVlrEmbalagem, txtPercDesconto, txtVlrDesconto, txtPercIPI, txtVlrIPI, txtPercFrete,
				txtVlrFrete, txtPercCredICMS, txtVlrCredICMS, txtPercICMSDesonerado, txtVlrICMSDesonerado, txtPercSubstituicao,	    
				txtVlrSubstituicao, txtPercCredPIS, txtVlrCredPIS, txtPercCredCOFINS, txtVlrCredCOFINS, txtPercDespAcessoria, txtVlrDespAcessoria,	    
				txtPercSeguro, txtVlrSeguro, txtPercOutrosCreditos, txtVlrOutrosCreditos, txtPercOutrosCustos, txtVlrOutrosCustos, txtPercMargenLiquidaVar,	    
				txtVlrMargenLiquidaVar, txtPercCustoOperacionalVar, txtVlrCustoOperacionalVar, txtPercPISVar, txtVlrPISVar, txtPercCOFINSVar,   
				txtVlrCOFINSVar, txtPercICMSVar, txtVlrICMSVar, txtPercComissaoVar, txtVlrComissaoVar, txtPercImpostoRendaVar, txtVlrImpostoRendaVar,	    
				txtPercContSocialVar, txtVlrContSocialVar, txtPercFreteVendasVar, txtVlrFreteVendasVar, txtPercSimplesVar, txtVlrSimplesVar,	    
				txtPercOutrasDespVendaVar, txtVlrOutrasDespVendaVar, txtCustoRealCompra, txtValorVendaEmbVar, txtValorVendaVar, txtPercMargenLiquidaAta,	    
				txtVlrMargenLiquidaAta,txtPercCustoOperacionalAta, txtVlrCustoOperacionalAta, txtPercPISAta, txtVlrPISAta, txtPercCOFINSAta,	    
				txtVlrCOFINSAta, txtPercICMSAta, txtVlrICMSAta, txtPercComissaoAta, txtVlrComissaoAta, txtPercImpostoRendaAta, txtVlrImpostoRendaAta,	    
				txtPercContSocialAta,  txtVlrContSocialAta, txtPercFreteVendasAta, txtVlrFreteVendasAta, txtPercSimplesAta, txtVlrSimplesAta, txtPercOutrasDespVendaAta,
				txtVlrOutrasDespVendaAta, txtValorVendaEmbAta, txtValorVendaAta);

		Util.setNextFocus( txtPrecoCusto,txtPercMargenBrutaVar,txtPercMargenBrutaAta, txtVlrEmbalagem, txtPercDesconto, txtPercIPI,  txtPercFrete,
				txtPercCredICMS, txtVlrCredICMS, txtPercICMSDesonerado, txtVlrICMSDesonerado, txtPercSubstituicao,	    
				txtVlrSubstituicao, txtPercCredPIS, txtVlrCredPIS, txtPercCredCOFINS, txtVlrCredCOFINS, txtPercDespAcessoria, txtVlrDespAcessoria,	    
				txtPercSeguro, txtVlrSeguro, txtPercOutrosCreditos, txtVlrOutrosCreditos, txtPercOutrosCustos, txtVlrOutrosCustos, txtPercMargenLiquidaVar,	    
				txtVlrMargenLiquidaVar, txtPercCustoOperacionalVar, txtVlrCustoOperacionalVar, txtPercPISVar, txtVlrPISVar, txtPercCOFINSVar,   
				txtVlrCOFINSVar, txtPercICMSVar, txtVlrICMSVar, txtPercComissaoVar, txtVlrComissaoVar, txtPercImpostoRendaVar, txtVlrImpostoRendaVar,	    
				txtPercContSocialVar, txtVlrContSocialVar, txtPercFreteVendasVar, txtVlrFreteVendasVar, txtPercSimplesVar, txtVlrSimplesVar,	    
				txtPercOutrasDespVendaVar, txtVlrOutrasDespVendaVar, txtCustoRealCompra, txtValorVendaEmbVar, txtValorVendaVar, txtPercMargenLiquidaAta,	    
				txtVlrMargenLiquidaAta,txtPercCustoOperacionalAta, txtVlrCustoOperacionalAta, txtPercPISAta, txtVlrPISAta, txtPercCOFINSAta,	    
				txtVlrCOFINSAta, txtPercICMSAta, txtVlrICMSAta, txtPercComissaoAta, txtVlrComissaoAta, txtPercImpostoRendaAta, txtVlrImpostoRendaAta,	    
				txtPercContSocialAta,  txtVlrContSocialAta, txtPercFreteVendasAta, txtVlrFreteVendasAta, txtPercSimplesAta, txtVlrSimplesAta, txtPercOutrasDespVendaAta,
				txtVlrOutrasDespVendaAta, txtValorVendaEmbAta, txtValorVendaAta);



		// ------------------ EVENTOS TEXTFIELDS AO PERDER O FOCO FAZER OS CALCULOS E ALTERAR O PRECO DE VENDA ------------------------ //

		//// =================== INCIDENCIA DE COMPRA ========================= ////

		txtPrecoCusto.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(!newValue){
					txtPrecoCusto.setText(Util.decimalBRtoBigDecimal(4, txtPrecoCusto.getText()).toString());
					setFormacaoPrecoDetalhada(BigDecimal.ZERO,BigDecimal.ZERO);

				}
			}
		});

		txtVlrEmbalagem.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(!newValue){
					txtVlrEmbalagem.setText(Util.decimalBRtoBigDecimal(4, txtVlrEmbalagem.getText()).toString());
					setFormacaoPrecoDetalhada(BigDecimal.ZERO,BigDecimal.ZERO);
				}
			}
		});

		txtPercDesconto.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if(!newValue){
					//CHAMA A FUNCAO QUE REFAZ O CALCULO DO VALOR
					txtPercDesconto.setText(Util.decimalBRtoBigDecimal(2, txtPercDesconto.getText()).toString());
					setFormacaoPrecoDetalhada(BigDecimal.ZERO,BigDecimal.ZERO);

				}
			}
		});

		txtPercIPI.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {	
				if(!newValue){		
					//CHAMA A FUNCAO QUE REFAZ O CALCULO DO VALOR
					txtPercIPI.setText(Util.decimalBRtoBigDecimal(2, txtPercIPI.getText()).toString());
					setFormacaoPrecoDetalhada(BigDecimal.ZERO,BigDecimal.ZERO);
				}
			}
		});

		txtPercFrete.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if(!newValue){
					//CHAMA A FUNCAO QUE REFAZ O CALCULO DO VALOR
					txtPercFrete.setText(Util.decimalBRtoBigDecimal(2, txtPercFrete.getText()).toString());
					setFormacaoPrecoDetalhada(BigDecimal.ZERO,BigDecimal.ZERO);
				}
			}
		});

		txtPercCredICMS.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if(!newValue){
					//CHAMA A FUNCAO QUE REFAZ O CALCULO DO VALOR
					txtPercCredICMS.setText(Util.decimalBRtoBigDecimal(2, txtPercCredICMS.getText()).toString());
					setFormacaoPrecoDetalhada(BigDecimal.ZERO,BigDecimal.ZERO);
				}
			}
		});

		txtPercICMSDesonerado.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if(!newValue){
					//CHAMA A FUNCAO QUE REFAZ O CALCULO DO VALOR
					txtPercICMSDesonerado.setText(Util.decimalBRtoBigDecimal(2, txtPercICMSDesonerado.getText()).toString());
					setFormacaoPrecoDetalhada(BigDecimal.ZERO,BigDecimal.ZERO);
				}
			}
		});


		txtPercSubstituicao.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if(!newValue){
					//CHAMA A FUNCAO QUE REFAZ O CALCULO DO VALOR
					txtPercSubstituicao.setText(Util.decimalBRtoBigDecimal(2, txtPercSubstituicao.getText()).toString());
					setFormacaoPrecoDetalhada(BigDecimal.ZERO,BigDecimal.ZERO);
				}
			}
		});


		txtPercCredPIS.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if(!newValue){
					//CHAMA A FUNCAO QUE REFAZ O CALCULO DO VALOR
					txtPercCredPIS.setText(Util.decimalBRtoBigDecimal(2, txtPercCredPIS.getText()).toString());
					setFormacaoPrecoDetalhada(BigDecimal.ZERO,BigDecimal.ZERO);
				}
			}
		});

		txtPercCredCOFINS.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if(!newValue){				
					//CHAMA A FUNCAO QUE REFAZ O CALCULO DO VALOR
					txtPercCredCOFINS.setText(Util.decimalBRtoBigDecimal(2, txtPercCredCOFINS.getText()).toString());
					setFormacaoPrecoDetalhada(BigDecimal.ZERO,BigDecimal.ZERO);
				}
			}
		});


		txtPercDespAcessoria.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if(!newValue){
					//CHAMA A FUNCAO QUE REFAZ O CALCULO DO VALOR
					txtPercDespAcessoria.setText(Util.decimalBRtoBigDecimal(2, txtPercDespAcessoria.getText()).toString());
					setFormacaoPrecoDetalhada(BigDecimal.ZERO,BigDecimal.ZERO);
				}
			}
		});


		txtPercSeguro.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if(!newValue){
					//CHAMA A FUNCAO QUE REFAZ O CALCULO DO VALOR
					txtPercSeguro.setText(Util.decimalBRtoBigDecimal(2, txtPercSeguro.getText()).toString());
					setFormacaoPrecoDetalhada(BigDecimal.ZERO,BigDecimal.ZERO);
				}
			}
		});

		txtPercOutrosCreditos.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if(!newValue){
					//CHAMA A FUNCAO QUE REFAZ O CALCULO DO VALOR
					txtPercOutrosCreditos.setText(Util.decimalBRtoBigDecimal(2, txtPercOutrosCreditos.getText()).toString());
					setFormacaoPrecoDetalhada(BigDecimal.ZERO,BigDecimal.ZERO);
				}
			}
		});

		txtPercOutrosCustos.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if(!newValue){
					//CHAMA A FUNCAO QUE REFAZ O CALCULO DO VALOR
					txtPercOutrosCustos.setText(Util.decimalBRtoBigDecimal(2, txtPercOutrosCustos.getText()).toString());
					setFormacaoPrecoDetalhada(BigDecimal.ZERO,BigDecimal.ZERO);
				}
			}
		});


		//// =================================== FIM INCIDENCIA COMPRA =================================== ////

		//// =================================== INCIDENCIA VENDA VAREJO ============================== ////

		txtPercMargenLiquidaVar.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if(!newValue){
					//CHAMA A FUNCAO QUE REFAZ O CALCULO DO VALOR
					txtPercMargenLiquidaVar.setText(Util.decimalBRtoBigDecimal(4, txtPercMargenLiquidaVar.getText()).toString());
					setFormacaoPrecoDetalhada(BigDecimal.ZERO,BigDecimal.ZERO);
				}
			}
		});

		// ***************** EVENTO TEXT DO TEXTFIELD DE MARGEM CASO SEJA NEGATIVO FIQUE VERMELHO *************** //1
		txtVlrMargenLiquidaVar.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				BigDecimal margem = Util.decimalBRtoBigDecimal(4, newValue);

				if (margem.signum() < 0) {

					txtVlrMargenLiquidaVar.setStyle("-fx-text-fill: #ff7575;");

				}else{
					txtVlrMargenLiquidaVar.setStyle("");
				}				
			}
		});


		txtPercMargenBrutaVar.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if(!newValue){
					//CHAMA A FUNCAO QUE REFAZ O CALCULO DO VALOR
					txtPercMargenBrutaVar.setText(Util.decimalBRtoBigDecimal(4, txtPercMargenBrutaVar.getText()).toString());
					setFormacaoPrecoDetalhada(BigDecimal.ZERO,BigDecimal.ZERO);
				}
			}
		});

		// ***************** EVENTO TEXT DO TEXTFIELD DE MARGEM CASO SEJA NEGATIVO FIQUE VERMELHO *************** //1
		txtPercMargenBrutaVar.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				BigDecimal margem = Util.decimalBRtoBigDecimal(4, newValue);

				if (margem.signum() < 0) {

					txtPercMargenBrutaVar.setStyle("-fx-text-fill: #ff7575;");

				}else{
					txtPercMargenBrutaVar.setStyle("");
				}				
			}
		});

		txtPercCustoOperacionalVar.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if(!newValue){	
					//CHAMA A FUNCAO QUE REFAZ O CALCULO DO VALOR
					txtPercCustoOperacionalVar.setText(Util.decimalBRtoBigDecimal(2, txtPercCustoOperacionalVar.getText()).toString());
					setFormacaoPrecoDetalhada(BigDecimal.ZERO,BigDecimal.ZERO);
				}
			}
		});


		txtPercPISVar.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if(!newValue){	
					//CHAMA A FUNCAO QUE REFAZ O CALCULO DO VALOR	
					txtPercPISVar.setText(Util.decimalBRtoBigDecimal(2, txtPercPISVar.getText()).toString());
					setFormacaoPrecoDetalhada(BigDecimal.ZERO,BigDecimal.ZERO);
				}
			}
		});


		txtPercCOFINSVar.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if(!newValue){	
					//CHAMA A FUNCAO QUE REFAZ O CALCULO DO VALOR	
					txtPercCOFINSVar.setText(Util.decimalBRtoBigDecimal(2, txtPercCOFINSVar.getText()).toString());
					setFormacaoPrecoDetalhada(BigDecimal.ZERO,BigDecimal.ZERO);
				}
			}
		});


		txtPercICMSVar.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if(!newValue){	
					//CHAMA A FUNCAO QUE REFAZ O CALCULO DO VALOR	
					txtPercICMSVar.setText(Util.decimalBRtoBigDecimal(2, txtPercICMSVar.getText()).toString());
					setFormacaoPrecoDetalhada(BigDecimal.ZERO,BigDecimal.ZERO);
				}
			}
		});

		txtPercComissaoVar.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if(!newValue){	
					//CHAMA A FUNCAO QUE REFAZ O CALCULO DO VALOR
					txtPercComissaoVar.setText(Util.decimalBRtoBigDecimal(2, txtPercComissaoVar.getText()).toString());
					setFormacaoPrecoDetalhada(BigDecimal.ZERO,BigDecimal.ZERO);
				}
			}
		});


		txtPercImpostoRendaVar.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if(!newValue){	
					//CHAMA A FUNCAO QUE REFAZ O CALCULO DO VALOR	
					txtPercImpostoRendaVar.setText(Util.decimalBRtoBigDecimal(2, txtPercImpostoRendaVar.getText()).toString());
					setFormacaoPrecoDetalhada(BigDecimal.ZERO,BigDecimal.ZERO);
				}
			}
		});


		txtPercContSocialVar.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if(!newValue){	
					//CHAMA A FUNCAO QUE REFAZ O CALCULO DO VALOR	
					txtPercContSocialVar.setText(Util.decimalBRtoBigDecimal(2, txtPercContSocialVar.getText()).toString());
					setFormacaoPrecoDetalhada(BigDecimal.ZERO,BigDecimal.ZERO);
				}
			}
		});

		txtPercFreteVendasVar.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if(!newValue){	
					//CHAMA A FUNCAO QUE REFAZ O CALCULO DO VALOR
					txtPercFreteVendasVar.setText(Util.decimalBRtoBigDecimal(2, txtPercFreteVendasVar.getText()).toString());
					setFormacaoPrecoDetalhada(BigDecimal.ZERO,BigDecimal.ZERO);
				}
			}
		});


		txtPercSimplesVar.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if(!newValue){	
					//CHAMA A FUNCAO QUE REFAZ O CALCULO DO VALOR	
					txtPercSimplesVar.setText(Util.decimalBRtoBigDecimal(2, txtPercSimplesVar.getText()).toString());
					setFormacaoPrecoDetalhada(BigDecimal.ZERO,BigDecimal.ZERO);
				}
			}
		});

		txtPercOutrasDespVendaVar.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if(!newValue){	
					//CHAMA A FUNCAO QUE REFAZ O CALCULO DO VALOR	
					txtPercOutrasDespVendaVar.setText(Util.decimalBRtoBigDecimal(2, txtPercOutrasDespVendaVar.getText()).toString());
					setFormacaoPrecoDetalhada(BigDecimal.ZERO,BigDecimal.ZERO);
				}
			}
		});

		txtValorVendaVar.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if(!newValue){	
					//CHAMA A FUNCAO QUE REFAZ O CALCULO DO VALOR	
					txtValorVendaVar.setText(Util.decimalBRtoBigDecimal(2, txtValorVendaVar.getText()).toString());
					txtPercMargenLiquidaVar.setText(Util.decimalBRtoBigDecimal(4, "0,0000").toString());
					txtVlrMargenLiquidaVar.setText(Util.decimalBRtoBigDecimal(4, "0,0000").toString());
					setFormacaoPrecoDetalhada(Util.decimalBRtoBigDecimal(2, txtValorVendaVar.getText()), BigDecimal.ZERO);

				}
			}
		});

		//// ================================= FIM INCIDENCIA VENDA VAREJO ================================ ////


		//// =================================== INCIDENCIA VENDA ATACADO ============================== ////

		txtPercMargenLiquidaAta.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if(!newValue){
					//CHAMA A FUNCAO QUE REFAZ O CALCULO DO VALOR
					txtPercMargenLiquidaAta.setText(Util.decimalBRtoBigDecimal(4, txtPercMargenLiquidaAta.getText()).toString());
					setFormacaoPrecoDetalhada(BigDecimal.ZERO, BigDecimal.ZERO);
				}
			}
		});

		// ***************** EVENTO TEXT DO TEXTFIELD DE MARGEM CASO SEJA NEGATIVO FIQUE VERMELHO *************** //1
		txtVlrMargenLiquidaAta.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				BigDecimal margem = Util.decimalBRtoBigDecimal(4, newValue);

				if (margem.signum() < 0) {

					txtVlrMargenLiquidaAta.setStyle("-fx-text-fill: #ff7575;");

				}else{
					txtVlrMargenLiquidaAta.setStyle("");
				}				
			}
		});

		txtPercMargenBrutaAta.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if(!newValue){
					//CHAMA A FUNCAO QUE REFAZ O CALCULO DO VALOR
					txtPercMargenBrutaAta.setText(Util.decimalBRtoBigDecimal(4, txtPercMargenBrutaAta.getText()).toString());
					setFormacaoPrecoDetalhada(BigDecimal.ZERO,BigDecimal.ZERO);
				}
			}
		});

		// ***************** EVENTO TEXT DO TEXTFIELD DE MARGEM CASO SEJA NEGATIVO FIQUE VERMELHO *************** //1
		txtPercMargenBrutaAta.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				BigDecimal margem = Util.decimalBRtoBigDecimal(4, newValue);

				if (margem.signum() < 0) {

					txtPercMargenBrutaAta.setStyle("-fx-text-fill: #ff7575;");

				}else{
					txtPercMargenBrutaAta.setStyle("");
				}				
			}
		});


		txtPercCustoOperacionalAta.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if(!newValue){	
					//CHAMA A FUNCAO QUE REFAZ O CALCULO DO VALOR
					txtPercCustoOperacionalAta.setText(Util.decimalBRtoBigDecimal(2, txtPercCustoOperacionalAta.getText()).toString());
					setFormacaoPrecoDetalhada(BigDecimal.ZERO, BigDecimal.ZERO);
				}
			}
		});


		txtPercPISAta.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if(!newValue){	
					//CHAMA A FUNCAO QUE REFAZ O CALCULO DO VALOR	
					txtPercPISAta.setText(Util.decimalBRtoBigDecimal(2, txtPercPISAta.getText()).toString());
					setFormacaoPrecoDetalhada(BigDecimal.ZERO, BigDecimal.ZERO);
				}
			}
		});


		txtPercCOFINSAta.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if(!newValue){	
					//CHAMA A FUNCAO QUE REFAZ O CALCULO DO VALOR	
					txtPercCOFINSAta.setText(Util.decimalBRtoBigDecimal(2, txtPercCOFINSAta.getText()).toString());
					setFormacaoPrecoDetalhada(BigDecimal.ZERO, BigDecimal.ZERO);
				}
			}
		});


		txtPercICMSAta.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if(!newValue){	
					//CHAMA A FUNCAO QUE REFAZ O CALCULO DO VALOR	
					txtPercICMSAta.setText(Util.decimalBRtoBigDecimal(2, txtPercICMSAta.getText()).toString());
					setFormacaoPrecoDetalhada(BigDecimal.ZERO, BigDecimal.ZERO);
				}
			}
		});

		txtPercComissaoAta.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if(!newValue){	
					//CHAMA A FUNCAO QUE REFAZ O CALCULO DO VALOR
					txtPercComissaoAta.setText(Util.decimalBRtoBigDecimal(2, txtPercComissaoAta.getText()).toString());
					setFormacaoPrecoDetalhada(BigDecimal.ZERO, BigDecimal.ZERO);
				}
			}
		});


		txtPercImpostoRendaAta.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if(!newValue){	
					//CHAMA A FUNCAO QUE REFAZ O CALCULO DO VALOR	
					txtPercImpostoRendaAta.setText(Util.decimalBRtoBigDecimal(2, txtPercImpostoRendaAta.getText()).toString());
					setFormacaoPrecoDetalhada(BigDecimal.ZERO, BigDecimal.ZERO);
				}
			}
		});


		txtPercContSocialAta.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if(!newValue){	
					//CHAMA A FUNCAO QUE REFAZ O CALCULO DO VALOR	
					txtPercContSocialAta.setText(Util.decimalBRtoBigDecimal(2, txtPercContSocialAta.getText()).toString());
					setFormacaoPrecoDetalhada(BigDecimal.ZERO, BigDecimal.ZERO);
				}
			}
		});

		txtPercFreteVendasAta.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if(!newValue){	
					//CHAMA A FUNCAO QUE REFAZ O CALCULO DO VALOR
					txtPercFreteVendasAta.setText(Util.decimalBRtoBigDecimal(2, txtPercFreteVendasAta.getText()).toString());
					setFormacaoPrecoDetalhada(BigDecimal.ZERO, BigDecimal.ZERO);
				}
			}
		});


		txtPercSimplesAta.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if(!newValue){	
					//CHAMA A FUNCAO QUE REFAZ O CALCULO DO VALOR	
					txtPercSimplesAta.setText(Util.decimalBRtoBigDecimal(2, txtPercSimplesAta.getText()).toString());
					setFormacaoPrecoDetalhada(BigDecimal.ZERO, BigDecimal.ZERO);
				}
			}
		});

		txtPercOutrasDespVendaAta.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if(!newValue){	
					//CHAMA A FUNCAO QUE REFAZ O CALCULO DO VALOR	
					txtPercOutrasDespVendaAta.setText(Util.decimalBRtoBigDecimal(2, txtPercOutrasDespVendaAta.getText()).toString());
					setFormacaoPrecoDetalhada(BigDecimal.ZERO, BigDecimal.ZERO);
				}
			}
		});

		txtValorVendaAta.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if(!newValue){	
					//CHAMA A FUNCAO QUE REFAZ O CALCULO DO VALOR	
					txtValorVendaAta.setText(Util.decimalBRtoBigDecimal(2, txtValorVendaAta.getText()).toString());
					txtPercMargenLiquidaAta.setText(Util.decimalBRtoBigDecimal(4, "0,0000").toString());
					txtVlrMargenLiquidaAta.setText(Util.decimalBRtoBigDecimal(4, "0,0000").toString());
					setFormacaoPrecoDetalhada(BigDecimal.ZERO,Util.decimalBRtoBigDecimal(2, txtValorVendaAta.getText()));


				}
			}
		});

		//// ================================= FIM INCIDENCIA VENDA ATACADO ================================ ////


		// ---------------------------------------- FIM EVENTOS TEXTFIELDS --------------------------------------------------------//

		aPanePrincipal.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			switch (event.getCode()) {
			case ESCAPE:
					actionBtnClose(null);
				break;
				
				default:
					break;
			}
			});
	}


}

