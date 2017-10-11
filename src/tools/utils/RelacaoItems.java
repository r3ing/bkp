package tools.utils;

import java.math.BigDecimal;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import models.compras.Item;
import models.compras.ItemFornecedor;
import models.configuracoes.DepositoEstoque;


public class RelacaoItems {
	
	private int status;
	private Item item;
	private ItemFornecedor itemFornecedor;
	private String codRelProd;
	private String codItemFornecedor;
	private String descripcaoNFeRelProd;
	private String descripcaoEptusRelProd;	
	private String noFornecedorRelProd;
	private String codNCMNFeRelProd;
	private String codNCMEptusRelProd;
	private StringProperty codBarRelProd;
	private StringProperty codBarEptusRelProd;
	private StringProperty tipoOperacaoMat;
	private String cFOPRelProd;
	private String cSTICMSRelProd;
	private String qtdNFeRelProd;
	private String undRelProd;
	private String embComprasRelProd;
	private StringProperty qtdReposicaoRelProd;
	private StringProperty fatorConversao;
	private String vlrUndBrutoRelProd;
	private StringProperty vlrUnitBrutoNFe;
	private String vlrDescontoRelProd;
	private String vlrDespAcessRelProd;
	private String vlrUndLiqRelProd;
	private String aliqIPIRelProd;
	private String vlrImpIPITribVBCRelProd;
	private String vlrImpIPITribPIPIRelProd;
	private String vlrImpIPITribVIPIRelProd;
	private String aliqICMSRelProd;
	private String qtdConfRelProd;
	private String vlrFreteRelProd;
	private String vlrSegRelProd;
	private String vlrICMSDesonRelProd;
	private String reducBCICMSRelProd;
	private String baseCalcICMSRelProd;
	private String vlrICMSSTRelProd;
	private String modBCICMSSubRelProd;
	private String pautaMVARelProd;
	private String baseCalcICMSSubRelProd;
	private String vlrICMSSubRelProd;
	private DepositoEstoque subEstoqueDestRelProd;
	
	
	public RelacaoItems() {
		super();
		// TODO Auto-generated constructor stub
	}	
	
	public RelacaoItems(int status, Item item, ItemFornecedor itemFornecedor, String codRelProd,
			String codItemFornecedor, String descripcaoNFeRelProd, String descripcaoEptusRelProd,
			String noFornecedorRelProd, String codNCMNFeRelProd, String codNCMEptusRelProd,
			String codBarRelProd, String codBarEptusRelProd, String cFOPRelProd, String cSTICMSRelProd, String qtdNFeRelProd,
			String undRelProd, String embComprasRelProd, String qtdReposicaoRelProd, String vlrUndBrutoRelProd, String vlrUnitBrutoNFe,
			String vlrDescontoRelProd, String vlrDespAcessRelProd, String fatorConversao,String vlrUndLiqRelProd, String aliqIPIRelProd,
			String vlrImpIPITribVBCRelProd, String vlrImpIPITribPIPIRelProd, String vlrImpIPITribVIPIRelProd,
			String aliqICMSRelProd, String qtdConfRelProd, String vlrFreteRelProd, String vlrSegRelProd,
			String vlrICMSDesonRelProd, String reducBCICMSRelProd, String baseCalcICMSRelProd, String vlrICMSSTRelProd,
			String modBCICMSSubRelProd, String pautaMVARelProd, String baseCalcICMSSubRelProd, String vlrICMSSubRelProd,
			DepositoEstoque subEstoqueDestRelProd, String tipoOperacaoMat) {
		super();
		this.status = status;
		this.item = item;
		this.itemFornecedor = itemFornecedor;
		this.codRelProd = codRelProd;
		this.codItemFornecedor = codItemFornecedor;
		this.descripcaoNFeRelProd = descripcaoNFeRelProd;
		this.descripcaoEptusRelProd = descripcaoEptusRelProd;
		this.noFornecedorRelProd = noFornecedorRelProd;
		this.codNCMNFeRelProd = codNCMNFeRelProd;
		this.codNCMEptusRelProd = codNCMEptusRelProd;
		this.codBarRelProd = new SimpleStringProperty(codBarRelProd);
		this.codBarEptusRelProd = new SimpleStringProperty(codBarEptusRelProd);
		this.cFOPRelProd = cFOPRelProd;
		this.cSTICMSRelProd = cSTICMSRelProd;
		this.qtdNFeRelProd = qtdNFeRelProd;
		this.undRelProd = undRelProd;
		this.embComprasRelProd = embComprasRelProd;
		this.qtdReposicaoRelProd = new SimpleStringProperty(qtdReposicaoRelProd);
		this.fatorConversao = new SimpleStringProperty(fatorConversao);
		this.vlrUndBrutoRelProd = vlrUndBrutoRelProd;
		this.vlrUnitBrutoNFe = new SimpleStringProperty(vlrUnitBrutoNFe);
		this.vlrDescontoRelProd = vlrDescontoRelProd;
		this.vlrDespAcessRelProd = vlrDespAcessRelProd;
		this.vlrUndLiqRelProd = vlrUndLiqRelProd;
		this.aliqIPIRelProd = aliqIPIRelProd;
		this.vlrImpIPITribVBCRelProd = vlrImpIPITribVBCRelProd;
		this.vlrImpIPITribPIPIRelProd = vlrImpIPITribPIPIRelProd;
		this.vlrImpIPITribVIPIRelProd = vlrImpIPITribVIPIRelProd;
		this.aliqICMSRelProd = aliqICMSRelProd;
		this.qtdConfRelProd = qtdConfRelProd;
		this.vlrFreteRelProd = vlrFreteRelProd;
		this.vlrSegRelProd = vlrSegRelProd;
		this.vlrICMSDesonRelProd = vlrICMSDesonRelProd;
		this.reducBCICMSRelProd = reducBCICMSRelProd;
		this.baseCalcICMSRelProd = baseCalcICMSRelProd;
		this.vlrICMSSTRelProd = vlrICMSSTRelProd;
		this.modBCICMSSubRelProd = modBCICMSSubRelProd;
		this.pautaMVARelProd = pautaMVARelProd;
		this.baseCalcICMSSubRelProd = baseCalcICMSSubRelProd;
		this.vlrICMSSubRelProd = vlrICMSSubRelProd;
		this.subEstoqueDestRelProd = subEstoqueDestRelProd;
		this.tipoOperacaoMat = new SimpleStringProperty(tipoOperacaoMat);
	}



	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	
	public ItemFornecedor getItemFornecedor() {
		return itemFornecedor;
	}

	public void setItemFornecedor(ItemFornecedor itemFornecedor) {
		this.itemFornecedor = itemFornecedor;
	}

	public String getCodRelProd() {
		return codRelProd;
	}

	public void setCodRelProd(String codRelProd) {
		this.codRelProd = codRelProd;
	}	
	
	public String getCodItemFornecedor() {
		return codItemFornecedor;
	}

	public void setCodItemFornecedor(String codItemFornecedor) {
		this.codItemFornecedor = codItemFornecedor;
	}

	public String getDescripcaoNFeRelProd() {
		return descripcaoNFeRelProd;
	}

	public void setDescripcaoNFeRelProd(String descripcaoNFeRelProd) {
		this.descripcaoNFeRelProd = descripcaoNFeRelProd;
	}

	public String getDescripcaoEptusRelProd() {
		return descripcaoEptusRelProd;
	}

	public void setDescripcaoEptusRelProd(String descripcaoEptusRelProd) {
		this.descripcaoEptusRelProd = descripcaoEptusRelProd;
	}

	public String getNoFornecedorRelProd() {
		return noFornecedorRelProd;
	}

	public void setNoFornecedorRelProd(String noFornecedorRelProd) {
		this.noFornecedorRelProd = noFornecedorRelProd;
	}

	public String getCodNCMNFeRelProd() {
		return codNCMNFeRelProd;
	}

	public void setCodNCMNFeRelProd(String codNCMNFeRelProd) {
		this.codNCMNFeRelProd = codNCMNFeRelProd;
	}

	public String getCodNCMEptusRelProd() {
		return codNCMEptusRelProd;
	}

	public void setCodNCMEptusRelProd(String codNCMEptusRelProd) {
		this.codNCMEptusRelProd = codNCMEptusRelProd;
	}		

	public String getCodBarRelProd() {
		return codBarRelProd.get();
	}
	
	public StringProperty getCodBarRelProdProperty(){
		return codBarRelProd;
	}

	public void setCodBarRelProd(String codBarRelProd) {
		this.codBarRelProd = new SimpleStringProperty(codBarRelProd);
	}
	
	//**************************
	public String getCodBarEptusRelProd() {
		return codBarEptusRelProd.get();
	}

	public void setCodBarEptusRelProd(String codBarEptusRelProd) {
		this.codBarEptusRelProd = new SimpleStringProperty(codBarEptusRelProd);
	}
	
	public StringProperty getCodBarEptusRelProdProperty(){
		return codBarEptusRelProd;
	}
	//****************************

	public String getCFOPRelProd() {
		return cFOPRelProd;
	}

	public void setCFOPRelProd(String cFOPRelProd) {
		this.cFOPRelProd = cFOPRelProd;
	}

	public String getCSTICMSRelProd() {
		return cSTICMSRelProd;
	}

	public void setCSTICMSRelProd(String cSTICMSRelProd) {
		this.cSTICMSRelProd = cSTICMSRelProd;
	}

	public String getQtdNFeRelProd() {
		return qtdNFeRelProd;
	}

	public void setQtdNFeRelProd(String qtdNFeRelProd) {
		this.qtdNFeRelProd = qtdNFeRelProd;
	}

	public String getUndRelProd() {
		return undRelProd;
	}

	public void setUndRelProd(String undRelProd) {
		this.undRelProd = undRelProd;
	}

	public String getEmbComprasRelProd() {
		return embComprasRelProd;
	}

	public void setEmbComprasRelProd(String embComprasRelProd) {
		this.embComprasRelProd = embComprasRelProd;
	}
	
	public StringProperty getQtdReposicaoRelProdProperty() {
		return qtdReposicaoRelProd;
	}

	public void setQtdReposicaoRelProd(String qtdReposicaoRelProd) {
		this.qtdReposicaoRelProd= new SimpleStringProperty(qtdReposicaoRelProd);
	}	

	public StringProperty fatorConversaoProperty() {
		return fatorConversao;
	}
	
	public String getfatorConversao() {
		return fatorConversao.get();
	}

	public void setFatorConversao(String fatorConversao) {
		this.fatorConversao = new SimpleStringProperty(fatorConversao);
	}

	public String getVlrUnitBrutoNFe() {
		return vlrUnitBrutoNFe.get();
	}
	
	public StringProperty getVlrUnitBrutoNFeProperty() {
		return vlrUnitBrutoNFe;
	}

	public void setVlrUnitBrutoNFe(String vlrUnitBrutoNFe) {
		this.vlrUnitBrutoNFe = new SimpleStringProperty(vlrUnitBrutoNFe);
	}

	public String getVlrUndBrutoRelProd() {
		return vlrUndBrutoRelProd;
	}

	public void setVlrUndBrutoRelProd(String vlrUndBrutoRelProd) {
		this.vlrUndBrutoRelProd = vlrUndBrutoRelProd;
	}

	public String getVlrDescontoRelProd() {
		return vlrDescontoRelProd;
	}

	public void setVlrDescontoRelProd(String vlrDescontoRelProd) {
		this.vlrDescontoRelProd = vlrDescontoRelProd;
	}

	public String getVlrDespAcessRelProd() {
		return vlrDespAcessRelProd;
	}

	public void setVlrDespAcessRelProd(String vlrDespAcessRelProd) {
		this.vlrDespAcessRelProd = vlrDespAcessRelProd;
	}

	public String getVlrUndLiqRelProd() {
		return vlrUndLiqRelProd;
	}

	public void setVlrUndLiqRelProd(String vlrUndLiqRelProd) {
		this.vlrUndLiqRelProd = vlrUndLiqRelProd;
	}

	public String getAliqIPIRelProd() {
		return aliqIPIRelProd;
	}

	public void setAliqIPIRelProd(String aliqIPIRelProd) {
		this.aliqIPIRelProd = aliqIPIRelProd;
	}

	public String getVlrImpIPITribVBCRelProd() {
		return vlrImpIPITribVBCRelProd;
	}

	public void setVlrImpIPITribVBCRelProd(String vlrImpIPITribVBCRelProd) {
		this.vlrImpIPITribVBCRelProd = vlrImpIPITribVBCRelProd;
	}

	public String getVlrImpIPITribPIPIRelProd() {
		return vlrImpIPITribPIPIRelProd;
	}

	public void setVlrImpIPITribPIPIRelProd(String vlrImpIPITribPIPIRelProd) {
		this.vlrImpIPITribPIPIRelProd = vlrImpIPITribPIPIRelProd;
	}

	public String getVlrImpIPITribVIPIRelProd() {
		return vlrImpIPITribVIPIRelProd;
	}

	public void setVlrImpIPITribVIPIRelProd(String vlrImpIPITribVIPIRelProd) {
		this.vlrImpIPITribVIPIRelProd = vlrImpIPITribVIPIRelProd;
	}

	public String getAliqICMSRelProd() {
		return aliqICMSRelProd;
	}

	public void setAliqICMSRelProd(String aliqICMSRelProd) {
		this.aliqICMSRelProd = aliqICMSRelProd;
	}

	public String getQtdConfRelProd() {
		return qtdConfRelProd;
	}

	public void setQtdConfRelProd(String qtdConfRelProd) {
		this.qtdConfRelProd = qtdConfRelProd;
	}

	public String getVlrFreteRelProd() {
		return vlrFreteRelProd;
	}

	public void setVlrFreteRelProd(String vlrFreteRelProd) {
		this.vlrFreteRelProd = vlrFreteRelProd;
	}

	public String getVlrSegRelProd() {
		return vlrSegRelProd;
	}

	public void setVlrSegRelProd(String vlrSegRelProd) {
		this.vlrSegRelProd = vlrSegRelProd;
	}

	public String getVlrICMSDesonRelProd() {
		return vlrICMSDesonRelProd;
	}

	public void setVlrICMSDesonRelProd(String vlrICMSDesonRelProd) {
		this.vlrICMSDesonRelProd = vlrICMSDesonRelProd;
	}

	public String getReducBCICMSRelProd() {
		return reducBCICMSRelProd;
	}

	public void setReducBCICMSRelProd(String reducBCICMSRelProd) {
		this.reducBCICMSRelProd = reducBCICMSRelProd;
	}

	public String getBaseCalcICMSRelProd() {
		return baseCalcICMSRelProd;
	}

	public void setBaseCalcICMSRelProd(String baseCalcICMSRelProd) {
		this.baseCalcICMSRelProd = baseCalcICMSRelProd;
	}

	public String getVlrICMSSTRelProd() {
		return vlrICMSSTRelProd;
	}

	public void setVlrICMSSTRelProd(String vlrICMSSTRelProd) {
		this.vlrICMSSTRelProd = vlrICMSSTRelProd;
	}

	public String getModBCICMSSubRelProd() {
		return modBCICMSSubRelProd;
	}

	public void setModBCICMSSubRelProd(String modBCICMSSubRelProd) {
		this.modBCICMSSubRelProd = modBCICMSSubRelProd;
	}

	public String getPautaMVARelProd() {
		return pautaMVARelProd;
	}

	public void setPautaMVARelProd(String pautaMVARelProd) {
		this.pautaMVARelProd = pautaMVARelProd;
	}

	public String getBaseCalcICMSSubRelProd() {
		return baseCalcICMSSubRelProd;
	}

	public void setBaseCalcICMSSubRelProd(String baseCalcICMSSubRelProd) {
		this.baseCalcICMSSubRelProd = baseCalcICMSSubRelProd;
	}

	public String getVlrICMSSubRelProd() {
		return vlrICMSSubRelProd;
	}

	public void setVlrICMSSubRelProd(String vlrICMSSubRelProd) {
		this.vlrICMSSubRelProd = vlrICMSSubRelProd;
	}

	public DepositoEstoque getSubEstoqueDestRelProd() {
		return subEstoqueDestRelProd;
	}

	public void setSubEstoqueDestRelProd(DepositoEstoque subEstoqueDestRelProd) {
		this.subEstoqueDestRelProd = subEstoqueDestRelProd;
	}
	

//	public String getTipoOperacaoMat(){
//		return tipoOperacaoMat.get();
//	}

	public StringProperty getTipoOperacaoMatProperty() {
		return tipoOperacaoMat;
	}

	public void setTipoOperacaoMat(String tipoOperacaoMat) {
		this.tipoOperacaoMat = new SimpleStringProperty(tipoOperacaoMat);
	}

	@Override
	public String toString() {
		return "RelacaoItems [status=" + status + ", item=" + item + ", itemFornecedor=" + itemFornecedor
				+ ", codRelProd=" + codRelProd + ", codItemFornecedor=" + codItemFornecedor + ", descripcaoNFeRelProd="
				+ descripcaoNFeRelProd + ", descripcaoEptusRelProd=" + descripcaoEptusRelProd
				+ ", noFornecedorRelProd=" + noFornecedorRelProd + ", codNCMNFeRelProd="
				+ codNCMNFeRelProd + ", codNCMEptusRelProd=" + codNCMEptusRelProd + ", codBarrasRelProd="
				+ codBarRelProd + ", cFOPRelProd=" + cFOPRelProd + ", cSTICMSRelProd=" + cSTICMSRelProd
				+ ", qtdNFeRelProd=" + qtdNFeRelProd + ", undRelProd=" + undRelProd + ", embComprasRelProd="
				+ embComprasRelProd + ", qtdReposicaoRelProd=" + qtdReposicaoRelProd + ", vlrUndBrutoRelProd="
				+ vlrUndBrutoRelProd + ", vlrDescontoRelProd=" + vlrDescontoRelProd + ", vlrDespAcessRelProd="
				+ vlrDespAcessRelProd + ", vlrUndLiqRelProd=" + vlrUndLiqRelProd + ", aliqIPIRelProd=" + aliqIPIRelProd
				+ ", vlrImpIPITribVBCRelProd=" + vlrImpIPITribVBCRelProd + ", vlrImpIPITribPIPIRelProd="
				+ vlrImpIPITribPIPIRelProd + ", vlrImpIPITribVIPIRelProd=" + vlrImpIPITribVIPIRelProd
				+ ", aliqICMSRelProd=" + aliqICMSRelProd + ", qtdConfRelProd=" + qtdConfRelProd + ", vlrFreteRelProd="
				+ vlrFreteRelProd + ", vlrSegRelProd=" + vlrSegRelProd + ", vlrICMSDesonRelProd=" + vlrICMSDesonRelProd
				+ ", reducBCICMSRelProd=" + reducBCICMSRelProd + ", baseCalcICMSRelProd=" + baseCalcICMSRelProd
				+ ", vlrICMSSTRelProd=" + vlrICMSSTRelProd + ", modBCICMSSubRelProd=" + modBCICMSSubRelProd
				+ ", pautaMVARelProd=" + pautaMVARelProd + ", baseCalcICMSSubRelProd=" + baseCalcICMSSubRelProd
				+ ", vlrICMSSubRelProd=" + vlrICMSSubRelProd + ", subEstoqueDestRelProd=" + subEstoqueDestRelProd + "]";
	}
	
}
