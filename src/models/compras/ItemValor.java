package models.compras;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

import javax.persistence.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.DynamicUpdate;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDateTime;

/**
 * The persistent class for the item_valor database table.
 * 
 */
@Entity
@Table(name="ITEM_VALOR")
@IdClass(value = ItemValorPK.class)
@DynamicUpdate
public class ItemValor implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		
		@JoinColumn(name = "COD_ITEM", referencedColumnName= "CODIGO"),
		@JoinColumn(name = "COD_ITEM_FK", referencedColumnName= "CHECK_DELETE")
	})
	private Item item;
	
	@Id
	private Integer codemp;
	
	@Id
	@Column(name = "CHECK_DELETE")
	private BigInteger checkDelete;
	
	@Column(name="ALT_PRECO_DATA")
	private LocalDateTime altPrecoData;

	@Column(name="ALT_PRECO_USUARIO")
	private String altPrecoUsuario;

//	@Column(name="COD_TRIBUTACAO")
//	private Integer codTributacao;
//
//	@Column(name="COD_TRIBUTACAO_FK")
//	private BigInteger codTributacaoFk;

	@Column(name="FLAG_ATIVO")
	private Integer flagAtivo;

	@Column(name="FP_COFINS_VENDA_ATACADO")
	private BigDecimal fpCofinsVendaAtacado;

	@Column(name="FP_COFINS_VENDA_VAREJO")
	private BigDecimal fpCofinsVendaVarejo;

	@Column(name="FP_COMISSAO_VENDA_ATACADO")
	private BigDecimal fpComissaoVendaAtacado;

	@Column(name="FP_COMISSAO_VENDA_VAREJO")
	private BigDecimal fpComissaoVendaVarejo;

	@Column(name="FP_CONTRIBUICAO_SOCIAL_ATACADO")
	private BigDecimal fpContribuicaoSocialAtacado;

	@Column(name="FP_CONTRIBUICAO_SOCIAL_VAREJO")
	private BigDecimal fpContribuicaoSocialVarejo;

	@Column(name="FP_CREDITO_COFINS")
	private BigDecimal fpCreditoCofins;

	@Column(name="FP_CREDITO_ICMS")
	private BigDecimal fpCreditoIcms;

	@Column(name="FP_CREDITO_PIS")
	private BigDecimal fpCreditoPis;

	@Column(name="FP_CUSTO_OPERACIONAL_ATACADO")
	private BigDecimal fpCustoOperacionalAtacado;

	@Column(name="FP_CUSTO_OPERACIONAL_VAREJO")
	private BigDecimal fpCustoOperacionalVarejo;

	@Column(name="FP_CUSTO_REAL_COMPRA")
	private BigDecimal fpCustoRealCompra;

	@Column(name="FP_DESCONTO_COMPRA")
	private BigDecimal fpDescontoCompra;

	@Column(name="FP_DESP_ACESSORIA")
	private BigDecimal fpDespAcessoria;

	@Column(name="FP_FRETE_COMPRA")
	private BigDecimal fpFreteCompra;

	@Column(name="FP_FRETE_VENDA_ATACADO")
	private BigDecimal fpFreteVendaAtacado;

	@Column(name="FP_FRETE_VENDA_VAREJO")
	private BigDecimal fpFreteVendaVarejo;

	@Column(name="FP_ICMS_DESONERADO")
	private BigDecimal fpIcmsDesonerado;

	@Column(name="FP_ICMS_ISS_VENDA_ATACADO")
	private BigDecimal fpIcmsIssVendaAtacado;

	@Column(name="FP_ICMS_ISS_VENDA_VAREJO")
	private BigDecimal fpIcmsIssVendaVarejo;

	@Column(name="FP_IMPOSTO_RENDA_ATACADO")
	private BigDecimal fpImpostoRendaAtacado;

	@Column(name="FP_IMPOSTO_RENDA_VAREJO")
	private BigDecimal fpImpostoRendaVarejo;

	@Column(name="FP_IPI_COMPRA")
	private BigDecimal fpIpiCompra;

	@Column(name="FP_MARGEM_LIQUIDA_ATACADO")
	private BigDecimal fpMargemLiquidaAtacado;

	@Column(name="FP_MARGEM_LIQUIDA_VAREJO")
	private BigDecimal fpMargemLiquidaVarejo;

	@Column(name="FP_OUTRAS_DESPESAS_ATACADO")
	private BigDecimal fpOutrasDespesasAtacado;

	@Column(name="FP_OUTRAS_DESPESAS_VAREJO")
	private BigDecimal fpOutrasDespesasVarejo;

	@Column(name="FP_OUTROS_CREDITOS")
	private BigDecimal fpOutrosCreditos;

	@Column(name="FP_OUTROS_CUSTOS")
	private BigDecimal fpOutrosCustos;

	@Column(name="FP_PIS_VENDA_ATACADO")
	private BigDecimal fpPisVendaAtacado;

	@Column(name="FP_PIS_VENDA_VAREJO")
	private BigDecimal fpPisVendaVarejo;

	@Column(name="FP_SEGURO")
	private BigDecimal fpSeguro;

	@Column(name="FP_SIMPLES_NACIONAL_ATACADO")
	private BigDecimal fpSimplesNacionalAtacado;

	@Column(name="FP_SIMPLES_NACIONAL_VAREJO")
	private BigDecimal fpSimplesNacionalVarejo;

	@Column(name="FP_SUBSTITUICAO_TRIB")
	private BigDecimal fpSubstituicaoTrib;

	@Column(name="FP_VLR_EMBALAGEM")
	private BigDecimal fpVlrEmbalagem;

	@Column(name="LAST_CODUSER")
	private Integer lastCoduser;

	@Column(name="LAST_MOVTO")
	private LocalDateTime lastMovto;

	private String locacao;

	@Column(name="MARGEM_LUCRO_BRUTO_ATACADO")
	private BigDecimal margemLucroBrutoAtacado;

	@Column(name="MARGEM_LUCRO_BRUTO_VAREJO")
	private BigDecimal margemLucroBrutoVarejo;

	@Column(name="PROMOCAO_DATA_VALIDADE")
	private LocalDateTime promocaoDataValidade;

	@Column(name="QTD_ATUAL")
	private BigDecimal qtdAtual;
	
	@Column(name="QTD_DISPONIVEL")
	private BigDecimal qtdDisponivel;

	@Column(name="QTD_RESERVADA")
	private BigDecimal qtdReservada;
	
	@Column(name="QTD_CCUSTO1")
	private BigDecimal qtdCcusto1;

	@Column(name="QTD_CCUSTO2")
	private BigDecimal qtdCcusto2;

	@Column(name="QTD_MAX")
	private BigDecimal qtdMax;

	@Column(name="QTD_MIN")
	private BigDecimal qtdMin;

	@Column(name="RECORD_NO")
	private Integer recordNo;

	@Column(name="REP_COD_FORNECEDOR")
	private Integer repCodFornecedor;

	@Column(name="REP_DATA")
	private LocalDateTime repData;

	@Column(name="REP_DOCUMENTO")
	private Integer repDocumento;

	@Column(name="REP_FORNECEDOR")
	private String repFornecedor;

	@Column(name="REP_QUANTIDADE")
	private BigDecimal repQuantidade;
	
	@Column(name="REP_VLR_CUSTO")
	private BigDecimal repVlrCusto;

	private BigInteger utctag;
	
	@Column(name="VLR_CUSTO_COMPRA")
	private BigDecimal vlrCustoCompra;
	
	@Column(name="VLR_CUSTO_MEDIO")
	private BigDecimal vlrCustoMedio;
	
	@Column(name="VLR_CUSTO_ANTERIOR")
	private BigDecimal vlrCustoAnterior;
	
	@Column(name="VLR_CUSTO")
	private BigDecimal vlrCusto;

	@Column(name="VLR_VENDA_ATACADO")
	private BigDecimal vlrVendaAtacado;

	@Column(name="VLR_VENDA_PROMOCAO")
	private BigDecimal vlrVendaPromocao;

	@Column(name="VLR_VENDA_VAREJO")
	private BigDecimal vlrVendaVarejo;
	
	public String getTributacaoDescricao() {
		String tributacaoDescricao = "";
		if(Hibernate.isInitialized(tributacao)) { 
			try {
				tributacaoDescricao = tributacao.getDescricao();
			} catch(org.hibernate.ObjectNotFoundException one) {
				tributacao = null;
			}
		}
		return tributacaoDescricao;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="COD_TRIBUTACAO" , referencedColumnName="CODIGO"),
		@JoinColumn(name="COD_TRIBUTACAO_FK" , referencedColumnName="CHECK_DELETE")
	})
	private Tributacao tributacao;
	
	public Tributacao getTributacao() {
		 if(!Hibernate.isInitialized(this.tributacao)) {
	            try {
	                Hibernate.initialize(this.tributacao);
	            } catch(org.hibernate.ObjectNotFoundException one) {
	            	this.tributacao = null;
	            }
	        }
	        return this.tributacao;
	}
	
	public void setTributacao(Tributacao tributacao) {
		this.tributacao = tributacao;
	}

	@Transient
	private boolean flagChangeItemValor = false;
	
	@Transient
	private boolean flagChangeTributacaoItem = false;
	
	@Transient
	private boolean flagChangePrecoEmp = false;
	
	
	public boolean getFlagChangeItemValor() {
		return flagChangeItemValor;
	}

	public void setFlagChangeItemValor(boolean flagChangeItemValor) {
		this.flagChangeItemValor = flagChangeItemValor;
	}

	public boolean getFlagChangeTributacaoItem() {
		return flagChangeTributacaoItem;
	}

	public void setFlagChangeTributacaoItem(boolean flagChangeTributacaoItem) {
		this.flagChangeTributacaoItem = flagChangeTributacaoItem;
	}

	public boolean getFlagChangePrecoEmp() {
		return flagChangePrecoEmp;
	}

	public void setFlagChangePrecoEmp(boolean flagChangePrecoEmp) {
		this.flagChangePrecoEmp = flagChangePrecoEmp;
	}

	public ItemValor() {
		
	}
	

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Integer getCodemp() {
		return codemp;
	}
	
	

	public void setCodemp(Integer codemp) {
		this.codemp = codemp;
	}

	
	
	public BigInteger getCheckDelete() {
		return checkDelete;
	}

	public void setCheckDelete(BigInteger checkDelete) {
		this.checkDelete = checkDelete;
	}

	public LocalDateTime getAltPrecoData() {
		return altPrecoData;
	}

	public void setAltPrecoData(LocalDateTime altPrecoData) {
		this.altPrecoData = altPrecoData;
	}

	public String getAltPrecoUsuario() {
		return altPrecoUsuario;
	}

	public void setAltPrecoUsuario(String altPrecoUsuario) {
		this.altPrecoUsuario = altPrecoUsuario;
	}

//	public Integer getCodTributacao() {
//		return codTributacao;
//	}
//
//	public void setCodTributacao(Integer codTributacao) {
//		this.codTributacao = codTributacao;
//	}
//
//	public BigInteger getCodTributacaoFk() {
//		return codTributacaoFk;
//	}
//
//	public void setCodTributacaoFk(BigInteger codTributacaoFk) {
//		this.codTributacaoFk = codTributacaoFk;
//	}

	public Integer getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Integer flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public BigDecimal getFpCofinsVendaAtacado() {
		return fpCofinsVendaAtacado;
	}

	public void setFpCofinsVendaAtacado(BigDecimal fpCofinsVendaAtacado) {
		this.fpCofinsVendaAtacado = fpCofinsVendaAtacado;
	}

	public BigDecimal getFpCofinsVendaVarejo() {
		return fpCofinsVendaVarejo;
	}

	public void setFpCofinsVendaVarejo(BigDecimal fpCofinsVendaVarejo) {
		this.fpCofinsVendaVarejo = fpCofinsVendaVarejo;
	}

	public BigDecimal getFpComissaoVendaAtacado() {
		return fpComissaoVendaAtacado;
	}

	public void setFpComissaoVendaAtacado(BigDecimal fpComissaoVendaAtacado) {
		this.fpComissaoVendaAtacado = fpComissaoVendaAtacado;
	}

	public BigDecimal getFpComissaoVendaVarejo() {
		return fpComissaoVendaVarejo;
	}

	public void setFpComissaoVendaVarejo(BigDecimal fpComissaoVendaVarejo) {
		this.fpComissaoVendaVarejo = fpComissaoVendaVarejo;
	}

	public BigDecimal getFpContribuicaoSocialAtacado() {
		return fpContribuicaoSocialAtacado;
	}

	public void setFpContribuicaoSocialAtacado(BigDecimal fpContribuicaoSocialAtacado) {
		this.fpContribuicaoSocialAtacado = fpContribuicaoSocialAtacado;
	}

	public BigDecimal getFpContribuicaoSocialVarejo() {
		return fpContribuicaoSocialVarejo;
	}

	public void setFpContribuicaoSocialVarejo(BigDecimal fpContribuicaoSocialVarejo) {
		this.fpContribuicaoSocialVarejo = fpContribuicaoSocialVarejo;
	}

	public BigDecimal getFpCreditoCofins() {
		return fpCreditoCofins;
	}

	public void setFpCreditoCofins(BigDecimal fpCreditoCofins) {
		this.fpCreditoCofins = fpCreditoCofins;
	}

	public BigDecimal getFpCreditoIcms() {
		return fpCreditoIcms;
	}

	public void setFpCreditoIcms(BigDecimal fpCreditoIcms) {
		this.fpCreditoIcms = fpCreditoIcms;
	}

	public BigDecimal getFpCreditoPis() {
		return fpCreditoPis;
	}

	public void setFpCreditoPis(BigDecimal fpCreditoPis) {
		this.fpCreditoPis = fpCreditoPis;
	}

	public BigDecimal getFpCustoOperacionalAtacado() {
		return fpCustoOperacionalAtacado;
	}

	public void setFpCustoOperacionalAtacado(BigDecimal fpCustoOperacionalAtacado) {
		this.fpCustoOperacionalAtacado = fpCustoOperacionalAtacado;
	}

	public BigDecimal getFpCustoOperacionalVarejo() {
		return fpCustoOperacionalVarejo;
	}

	public void setFpCustoOperacionalVarejo(BigDecimal fpCustoOperacionalVarejo) {
		this.fpCustoOperacionalVarejo = fpCustoOperacionalVarejo;
	}

	public BigDecimal getFpCustoRealCompra() {
		return fpCustoRealCompra;
	}

	public void setFpCustoRealCompra(BigDecimal fpCustoRealCompra) {
		this.fpCustoRealCompra = fpCustoRealCompra;
	}

	public BigDecimal getFpDescontoCompra() {
		return fpDescontoCompra;
	}

	public void setFpDescontoCompra(BigDecimal fpDescontoCompra) {
		this.fpDescontoCompra = fpDescontoCompra;
	}

	public BigDecimal getFpDespAcessoria() {
		return fpDespAcessoria;
	}

	public void setFpDespAcessoria(BigDecimal fpDespAcessoria) {
		this.fpDespAcessoria = fpDespAcessoria;
	}

	public BigDecimal getFpFreteCompra() {
		return fpFreteCompra;
	}

	public void setFpFreteCompra(BigDecimal fpFreteCompra) {
		this.fpFreteCompra = fpFreteCompra;
	}

	public BigDecimal getFpFreteVendaAtacado() {
		return fpFreteVendaAtacado;
	}

	public void setFpFreteVendaAtacado(BigDecimal fpFreteVendaAtacado) {
		this.fpFreteVendaAtacado = fpFreteVendaAtacado;
	}

	public BigDecimal getFpFreteVendaVarejo() {
		return fpFreteVendaVarejo;
	}

	public void setFpFreteVendaVarejo(BigDecimal fpFreteVendaVarejo) {
		this.fpFreteVendaVarejo = fpFreteVendaVarejo;
	}

	public BigDecimal getFpIcmsDesonerado() {
		return fpIcmsDesonerado;
	}

	public void setFpIcmsDesonerado(BigDecimal fpIcmsDesonerado) {
		this.fpIcmsDesonerado = fpIcmsDesonerado;
	}

	public BigDecimal getFpIcmsIssVendaAtacado() {
		return fpIcmsIssVendaAtacado;
	}

	public void setFpIcmsIssVendaAtacado(BigDecimal fpIcmsIssVendaAtacado) {
		this.fpIcmsIssVendaAtacado = fpIcmsIssVendaAtacado;
	}

	public BigDecimal getFpIcmsIssVendaVarejo() {
		return fpIcmsIssVendaVarejo;
	}

	public void setFpIcmsIssVendaVarejo(BigDecimal fpIcmsIssVendaVarejo) {
		this.fpIcmsIssVendaVarejo = fpIcmsIssVendaVarejo;
	}

	public BigDecimal getFpImpostoRendaAtacado() {
		return fpImpostoRendaAtacado;
	}

	public void setFpImpostoRendaAtacado(BigDecimal fpImpostoRendaAtacado) {
		this.fpImpostoRendaAtacado = fpImpostoRendaAtacado;
	}

	public BigDecimal getFpImpostoRendaVarejo() {
		return fpImpostoRendaVarejo;
	}

	public void setFpImpostoRendaVarejo(BigDecimal fpImpostoRendaVarejo) {
		this.fpImpostoRendaVarejo = fpImpostoRendaVarejo;
	}

	public BigDecimal getFpIpiCompra() {
		return fpIpiCompra;
	}

	public void setFpIpiCompra(BigDecimal fpIpiCompra) {
		this.fpIpiCompra = fpIpiCompra;
	}

	public BigDecimal getFpMargemLiquidaAtacado() {
		return fpMargemLiquidaAtacado;
	}

	public void setFpMargemLiquidaAtacado(BigDecimal fpMargemLiquidaAtacado) {
		this.fpMargemLiquidaAtacado = fpMargemLiquidaAtacado;
	}

	public BigDecimal getFpMargemLiquidaVarejo() {
		return fpMargemLiquidaVarejo;
	}

	public void setFpMargemLiquidaVarejo(BigDecimal fpMargemLiquidaVarejo) {
		this.fpMargemLiquidaVarejo = fpMargemLiquidaVarejo;
	}

	public BigDecimal getFpOutrasDespesasAtacado() {
		return fpOutrasDespesasAtacado;
	}

	public void setFpOutrasDespesasAtacado(BigDecimal fpOutrasDespesasAtacado) {
		this.fpOutrasDespesasAtacado = fpOutrasDespesasAtacado;
	}

	public BigDecimal getFpOutrasDespesasVarejo() {
		return fpOutrasDespesasVarejo;
	}

	public void setFpOutrasDespesasVarejo(BigDecimal fpOutrasDespesasVarejo) {
		this.fpOutrasDespesasVarejo = fpOutrasDespesasVarejo;
	}

	public BigDecimal getFpOutrosCreditos() {
		return fpOutrosCreditos;
	}

	public void setFpOutrosCreditos(BigDecimal fpOutrosCreditos) {
		this.fpOutrosCreditos = fpOutrosCreditos;
	}

	public BigDecimal getFpOutrosCustos() {
		return fpOutrosCustos;
	}

	public void setFpOutrosCustos(BigDecimal fpOutrosCustos) {
		this.fpOutrosCustos = fpOutrosCustos;
	}

	public BigDecimal getFpPisVendaAtacado() {
		return fpPisVendaAtacado;
	}

	public void setFpPisVendaAtacado(BigDecimal fpPisVendaAtacado) {
		this.fpPisVendaAtacado = fpPisVendaAtacado;
	}

	public BigDecimal getFpPisVendaVarejo() {
		return fpPisVendaVarejo;
	}

	public void setFpPisVendaVarejo(BigDecimal fpPisVendaVarejo) {
		this.fpPisVendaVarejo = fpPisVendaVarejo;
	}

	public BigDecimal getFpSeguro() {
		return fpSeguro;
	}

	public void setFpSeguro(BigDecimal fpSeguro) {
		this.fpSeguro = fpSeguro;
	}

	public BigDecimal getFpSimplesNacionalAtacado() {
		return fpSimplesNacionalAtacado;
	}

	public void setFpSimplesNacionalAtacado(BigDecimal fpSimplesNacionalAtacado) {
		this.fpSimplesNacionalAtacado = fpSimplesNacionalAtacado;
	}

	public BigDecimal getFpSimplesNacionalVarejo() {
		return fpSimplesNacionalVarejo;
	}

	public void setFpSimplesNacionalVarejo(BigDecimal fpSimplesNacionalVarejo) {
		this.fpSimplesNacionalVarejo = fpSimplesNacionalVarejo;
	}

	public BigDecimal getFpSubstituicaoTrib() {
		return fpSubstituicaoTrib;
	}

	public void setFpSubstituicaoTrib(BigDecimal fpSubstituicaoTrib) {
		this.fpSubstituicaoTrib = fpSubstituicaoTrib;
	}

	public BigDecimal getFpVlrEmbalagem() {
		return fpVlrEmbalagem;
	}

	public void setFpVlrEmbalagem(BigDecimal fpVlrEmbalagem) {
		this.fpVlrEmbalagem = fpVlrEmbalagem;
	}

	public Integer getLastCoduser() {
		return lastCoduser;
	}

	public void setLastCoduser(Integer lastCoduser) {
		this.lastCoduser = lastCoduser;
	}

	public LocalDateTime getLastMovto() {
		return lastMovto;
	}

	public void setLastMovto(LocalDateTime lastMovto) {
		this.lastMovto = lastMovto;
	}

	public String getLocacao() {
		return locacao;
	}

	public void setLocacao(String locacao) {
		this.locacao = locacao;
	}

	public BigDecimal getMargemLucroBrutoAtacado() {
		return margemLucroBrutoAtacado;
	}

	public void setMargemLucroBrutoAtacado(BigDecimal margemLucroBrutoAtacado) {
		this.margemLucroBrutoAtacado = margemLucroBrutoAtacado;
	}

	public BigDecimal getMargemLucroBrutoVarejo() {
		return margemLucroBrutoVarejo;
	}

	public void setMargemLucroBrutoVarejo(BigDecimal margemLucroBrutoVarejo) {
		this.margemLucroBrutoVarejo = margemLucroBrutoVarejo;
	}

	public LocalDateTime getPromocaoDataValidade() {
		return promocaoDataValidade;
	}

	public void setPromocaoDataValidade(LocalDateTime promocaoDataValidade) {
		this.promocaoDataValidade = promocaoDataValidade;
	}

	public BigDecimal getQtdAtual() {
		return qtdAtual;
	}

	public void setQtdAtual(BigDecimal qtdAtual) {
		this.qtdAtual = qtdAtual;
	}

	public BigDecimal getQtdDisponivel() {
		return qtdDisponivel;
	}

	public void setQtdDisponivel(BigDecimal qtdDisponivel) {
		this.qtdDisponivel = qtdDisponivel;
	}

	public BigDecimal getQtdReservada() {
		return qtdReservada;
	}

	public void setQtdReservada(BigDecimal qtdReservada) {
		this.qtdReservada = qtdReservada;
	}

	public BigDecimal getQtdCcusto1() {
		return qtdCcusto1;
	}

	public void setQtdCcusto1(BigDecimal qtdCcusto1) {
		this.qtdCcusto1 = qtdCcusto1;
	}

	public BigDecimal getQtdCcusto2() {
		return qtdCcusto2;
	}

	public void setQtdCcusto2(BigDecimal qtdCcusto2) {
		this.qtdCcusto2 = qtdCcusto2;
	}

	public BigDecimal getQtdMax() {
		
		return qtdMax.setScale(2, RoundingMode.HALF_EVEN);
	}

	public void setQtdMax(BigDecimal qtdMax) {
		this.qtdMax = qtdMax;
	}

	public BigDecimal getQtdMin() {
		return qtdMin.setScale(2, RoundingMode.HALF_EVEN);
	}

	public void setQtdMin(BigDecimal qtdMin) {
		this.qtdMin = qtdMin;
	}

	public Integer getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(Integer recordNo) {
		this.recordNo = recordNo;
	}

	public Integer getRepCodFornecedor() {
		return repCodFornecedor;
	}

	public void setRepCodFornecedor(Integer repCodFornecedor) {
		this.repCodFornecedor = repCodFornecedor;
	}

	public LocalDateTime getRepData() {
		return repData;
	}

	public void setRepData(LocalDateTime repData) {
		this.repData = repData;
	}

	public Integer getRepDocumento() {
		return repDocumento;
	}

	public void setRepDocumento(Integer repDocumento) {
		this.repDocumento = repDocumento;
	}

	public String getRepFornecedor() {
		return repFornecedor;
	}

	public void setRepFornecedor(String repFornecedor) {
		this.repFornecedor = repFornecedor;
	}

	public BigDecimal getRepQuantidade() {
		return repQuantidade;
	}

	public void setRepQuantidade(BigDecimal repQuantidade) {
		this.repQuantidade = repQuantidade;
	}

	public BigInteger getUtctag() {
		return utctag;
	}

	public void setUtctag(BigInteger utctag) {
		this.utctag = utctag;
	}

	public BigDecimal getVlrCusto() {
		return vlrCusto;
	}

	public void setVlrCusto(BigDecimal vlrCusto) {
		this.vlrCusto = vlrCusto;
	}

	public BigDecimal getVlrVendaAtacado() {
		return vlrVendaAtacado;
	}

	public void setVlrVendaAtacado(BigDecimal vlrVendaAtacado) {
		this.vlrVendaAtacado = vlrVendaAtacado;
	}

	public BigDecimal getVlrVendaPromocao() {
		return vlrVendaPromocao;
	}

	public void setVlrVendaPromocao(BigDecimal vlrVendaPromocao) {
		this.vlrVendaPromocao = vlrVendaPromocao;
	}

	public BigDecimal getVlrVendaVarejo() {
		return vlrVendaVarejo;
	}

	public void setVlrVendaVarejo(BigDecimal vlrVendaVarejo) {
		this.vlrVendaVarejo = vlrVendaVarejo;
	}
	
	
	public BigDecimal getRepVlrCusto() {
		return repVlrCusto;
	}

	public void setRepVlrCusto(BigDecimal repVlrCusto) {
		this.repVlrCusto = repVlrCusto;
	}

	public BigDecimal getVlrCustoCompra() {
		return vlrCustoCompra;
	}

	public void setVlrCustoCompra(BigDecimal vlrCustoCompra) {
		this.vlrCustoCompra = vlrCustoCompra;
	}

	public BigDecimal getVlrCustoMedio() {
		return vlrCustoMedio;
	}

	public void setVlrCustoMedio(BigDecimal vlrCustoMedio) {
		this.vlrCustoMedio = vlrCustoMedio;
	}

	public BigDecimal getVlrCustoAnterior() {
		return vlrCustoAnterior;
	}

	public void setVlrCustoAnterior(BigDecimal vlrCustoAnterior) {
		this.vlrCustoAnterior = vlrCustoAnterior;
	}

	@Override
	public ItemValor clone() {
	   try {
		return (ItemValor) super.clone();
	} catch (CloneNotSupportedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((altPrecoData == null) ? 0 : altPrecoData.hashCode());
		result = prime * result + ((altPrecoUsuario == null) ? 0 : altPrecoUsuario.hashCode());
		result = prime * result + ((codemp == null) ? 0 : codemp.hashCode());
		result = prime * result + ((flagAtivo == null) ? 0 : flagAtivo.hashCode());
		result = prime * result + ((fpCofinsVendaAtacado == null) ? 0 : fpCofinsVendaAtacado.hashCode());
		result = prime * result + ((fpCofinsVendaVarejo == null) ? 0 : fpCofinsVendaVarejo.hashCode());
		result = prime * result + ((fpComissaoVendaAtacado == null) ? 0 : fpComissaoVendaAtacado.hashCode());
		result = prime * result + ((fpComissaoVendaVarejo == null) ? 0 : fpComissaoVendaVarejo.hashCode());
		result = prime * result + ((fpContribuicaoSocialAtacado == null) ? 0 : fpContribuicaoSocialAtacado.hashCode());
		result = prime * result + ((fpContribuicaoSocialVarejo == null) ? 0 : fpContribuicaoSocialVarejo.hashCode());
		result = prime * result + ((fpCreditoCofins == null) ? 0 : fpCreditoCofins.hashCode());
		result = prime * result + ((fpCreditoIcms == null) ? 0 : fpCreditoIcms.hashCode());
		result = prime * result + ((fpCreditoPis == null) ? 0 : fpCreditoPis.hashCode());
		result = prime * result + ((fpCustoOperacionalAtacado == null) ? 0 : fpCustoOperacionalAtacado.hashCode());
		result = prime * result + ((fpCustoOperacionalVarejo == null) ? 0 : fpCustoOperacionalVarejo.hashCode());
		result = prime * result + ((fpCustoRealCompra == null) ? 0 : fpCustoRealCompra.hashCode());
		result = prime * result + ((fpDescontoCompra == null) ? 0 : fpDescontoCompra.hashCode());
		result = prime * result + ((fpDespAcessoria == null) ? 0 : fpDespAcessoria.hashCode());
		result = prime * result + ((fpFreteCompra == null) ? 0 : fpFreteCompra.hashCode());
		result = prime * result + ((fpFreteVendaAtacado == null) ? 0 : fpFreteVendaAtacado.hashCode());
		result = prime * result + ((fpFreteVendaVarejo == null) ? 0 : fpFreteVendaVarejo.hashCode());
		result = prime * result + ((fpIcmsDesonerado == null) ? 0 : fpIcmsDesonerado.hashCode());
		result = prime * result + ((fpIcmsIssVendaAtacado == null) ? 0 : fpIcmsIssVendaAtacado.hashCode());
		result = prime * result + ((fpIcmsIssVendaVarejo == null) ? 0 : fpIcmsIssVendaVarejo.hashCode());
		result = prime * result + ((fpImpostoRendaAtacado == null) ? 0 : fpImpostoRendaAtacado.hashCode());
		result = prime * result + ((fpImpostoRendaVarejo == null) ? 0 : fpImpostoRendaVarejo.hashCode());
		result = prime * result + ((fpIpiCompra == null) ? 0 : fpIpiCompra.hashCode());
		result = prime * result + ((fpMargemLiquidaAtacado == null) ? 0 : fpMargemLiquidaAtacado.hashCode());
		result = prime * result + ((fpMargemLiquidaVarejo == null) ? 0 : fpMargemLiquidaVarejo.hashCode());
		result = prime * result + ((fpOutrasDespesasAtacado == null) ? 0 : fpOutrasDespesasAtacado.hashCode());
		result = prime * result + ((fpOutrasDespesasVarejo == null) ? 0 : fpOutrasDespesasVarejo.hashCode());
		result = prime * result + ((fpOutrosCreditos == null) ? 0 : fpOutrosCreditos.hashCode());
		result = prime * result + ((fpOutrosCustos == null) ? 0 : fpOutrosCustos.hashCode());
		result = prime * result + ((fpPisVendaAtacado == null) ? 0 : fpPisVendaAtacado.hashCode());
		result = prime * result + ((fpPisVendaVarejo == null) ? 0 : fpPisVendaVarejo.hashCode());
		result = prime * result + ((fpSeguro == null) ? 0 : fpSeguro.hashCode());
		result = prime * result + ((fpSimplesNacionalAtacado == null) ? 0 : fpSimplesNacionalAtacado.hashCode());
		result = prime * result + ((fpSimplesNacionalVarejo == null) ? 0 : fpSimplesNacionalVarejo.hashCode());
		result = prime * result + ((fpSubstituicaoTrib == null) ? 0 : fpSubstituicaoTrib.hashCode());
		result = prime * result + ((fpVlrEmbalagem == null) ? 0 : fpVlrEmbalagem.hashCode());
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		result = prime * result + ((lastCoduser == null) ? 0 : lastCoduser.hashCode());
		result = prime * result + ((lastMovto == null) ? 0 : lastMovto.hashCode());
		result = prime * result + ((locacao == null) ? 0 : locacao.hashCode());
		result = prime * result + ((margemLucroBrutoAtacado == null) ? 0 : margemLucroBrutoAtacado.hashCode());
		result = prime * result + ((margemLucroBrutoVarejo == null) ? 0 : margemLucroBrutoVarejo.hashCode());
		result = prime * result + ((promocaoDataValidade == null) ? 0 : promocaoDataValidade.hashCode());
		result = prime * result + ((qtdAtual == null) ? 0 : qtdAtual.hashCode());
		result = prime * result + ((qtdCcusto1 == null) ? 0 : qtdCcusto1.hashCode());
		result = prime * result + ((qtdCcusto2 == null) ? 0 : qtdCcusto2.hashCode());
		result = prime * result + ((qtdMax == null) ? 0 : qtdMax.hashCode());
		result = prime * result + ((qtdMin == null) ? 0 : qtdMin.hashCode());
		result = prime * result + ((recordNo == null) ? 0 : recordNo.hashCode());
		result = prime * result + ((repCodFornecedor == null) ? 0 : repCodFornecedor.hashCode());
		result = prime * result + ((repData == null) ? 0 : repData.hashCode());
		result = prime * result + ((repDocumento == null) ? 0 : repDocumento.hashCode());
		result = prime * result + ((repFornecedor == null) ? 0 : repFornecedor.hashCode());
		result = prime * result + ((repQuantidade == null) ? 0 : repQuantidade.hashCode());
		result = prime * result + ((tributacao == null) ? 0 : tributacao.hashCode());
		result = prime * result + ((utctag == null) ? 0 : utctag.hashCode());
		result = prime * result + ((vlrCusto == null) ? 0 : vlrCusto.hashCode());
		result = prime * result + ((vlrVendaAtacado == null) ? 0 : vlrVendaAtacado.hashCode());
		result = prime * result + ((vlrVendaPromocao == null) ? 0 : vlrVendaPromocao.hashCode());
		result = prime * result + ((vlrVendaVarejo == null) ? 0 : vlrVendaVarejo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemValor other = (ItemValor) obj;
		if (altPrecoData == null) {
			if (other.altPrecoData != null)
				return false;
		} else if (!altPrecoData.equals(other.altPrecoData))
			return false;
		if (altPrecoUsuario == null) {
			if (other.altPrecoUsuario != null)
				return false;
		} else if (!altPrecoUsuario.equals(other.altPrecoUsuario))
			return false;
		if (codemp == null) {
			if (other.codemp != null)
				return false;
		} else if (!codemp.equals(other.codemp))
			return false;
		if (flagAtivo == null) {
			if (other.flagAtivo != null)
				return false;
		} else if (!flagAtivo.equals(other.flagAtivo))
			return false;
		if (fpCofinsVendaAtacado == null) {
			if (other.fpCofinsVendaAtacado != null)
				return false;
		} else if (!fpCofinsVendaAtacado.equals(other.fpCofinsVendaAtacado))
			return false;
		if (fpCofinsVendaVarejo == null) {
			if (other.fpCofinsVendaVarejo != null)
				return false;
		} else if (!fpCofinsVendaVarejo.equals(other.fpCofinsVendaVarejo))
			return false;
		if (fpComissaoVendaAtacado == null) {
			if (other.fpComissaoVendaAtacado != null)
				return false;
		} else if (!fpComissaoVendaAtacado.equals(other.fpComissaoVendaAtacado))
			return false;
		if (fpComissaoVendaVarejo == null) {
			if (other.fpComissaoVendaVarejo != null)
				return false;
		} else if (!fpComissaoVendaVarejo.equals(other.fpComissaoVendaVarejo))
			return false;
		if (fpContribuicaoSocialAtacado == null) {
			if (other.fpContribuicaoSocialAtacado != null)
				return false;
		} else if (!fpContribuicaoSocialAtacado.equals(other.fpContribuicaoSocialAtacado))
			return false;
		if (fpContribuicaoSocialVarejo == null) {
			if (other.fpContribuicaoSocialVarejo != null)
				return false;
		} else if (!fpContribuicaoSocialVarejo.equals(other.fpContribuicaoSocialVarejo))
			return false;
		if (fpCreditoCofins == null) {
			if (other.fpCreditoCofins != null)
				return false;
		} else if (!fpCreditoCofins.equals(other.fpCreditoCofins))
			return false;
		if (fpCreditoIcms == null) {
			if (other.fpCreditoIcms != null)
				return false;
		} else if (!fpCreditoIcms.equals(other.fpCreditoIcms))
			return false;
		if (fpCreditoPis == null) {
			if (other.fpCreditoPis != null)
				return false;
		} else if (!fpCreditoPis.equals(other.fpCreditoPis))
			return false;
		if (fpCustoOperacionalAtacado == null) {
			if (other.fpCustoOperacionalAtacado != null)
				return false;
		} else if (!fpCustoOperacionalAtacado.equals(other.fpCustoOperacionalAtacado))
			return false;
		if (fpCustoOperacionalVarejo == null) {
			if (other.fpCustoOperacionalVarejo != null)
				return false;
		} else if (!fpCustoOperacionalVarejo.equals(other.fpCustoOperacionalVarejo))
			return false;
		if (fpCustoRealCompra == null) {
			if (other.fpCustoRealCompra != null)
				return false;
		} else if (!fpCustoRealCompra.equals(other.fpCustoRealCompra))
			return false;
		if (fpDescontoCompra == null) {
			if (other.fpDescontoCompra != null)
				return false;
		} else if (!fpDescontoCompra.equals(other.fpDescontoCompra))
			return false;
		if (fpDespAcessoria == null) {
			if (other.fpDespAcessoria != null)
				return false;
		} else if (!fpDespAcessoria.equals(other.fpDespAcessoria))
			return false;
		if (fpFreteCompra == null) {
			if (other.fpFreteCompra != null)
				return false;
		} else if (!fpFreteCompra.equals(other.fpFreteCompra))
			return false;
		if (fpFreteVendaAtacado == null) {
			if (other.fpFreteVendaAtacado != null)
				return false;
		} else if (!fpFreteVendaAtacado.equals(other.fpFreteVendaAtacado))
			return false;
		if (fpFreteVendaVarejo == null) {
			if (other.fpFreteVendaVarejo != null)
				return false;
		} else if (!fpFreteVendaVarejo.equals(other.fpFreteVendaVarejo))
			return false;
		if (fpIcmsDesonerado == null) {
			if (other.fpIcmsDesonerado != null)
				return false;
		} else if (!fpIcmsDesonerado.equals(other.fpIcmsDesonerado))
			return false;
		if (fpIcmsIssVendaAtacado == null) {
			if (other.fpIcmsIssVendaAtacado != null)
				return false;
		} else if (!fpIcmsIssVendaAtacado.equals(other.fpIcmsIssVendaAtacado))
			return false;
		if (fpIcmsIssVendaVarejo == null) {
			if (other.fpIcmsIssVendaVarejo != null)
				return false;
		} else if (!fpIcmsIssVendaVarejo.equals(other.fpIcmsIssVendaVarejo))
			return false;
		if (fpImpostoRendaAtacado == null) {
			if (other.fpImpostoRendaAtacado != null)
				return false;
		} else if (!fpImpostoRendaAtacado.equals(other.fpImpostoRendaAtacado))
			return false;
		if (fpImpostoRendaVarejo == null) {
			if (other.fpImpostoRendaVarejo != null)
				return false;
		} else if (!fpImpostoRendaVarejo.equals(other.fpImpostoRendaVarejo))
			return false;
		if (fpIpiCompra == null) {
			if (other.fpIpiCompra != null)
				return false;
		} else if (!fpIpiCompra.equals(other.fpIpiCompra))
			return false;
		if (fpMargemLiquidaAtacado == null) {
			if (other.fpMargemLiquidaAtacado != null)
				return false;
		} else if (!fpMargemLiquidaAtacado.equals(other.fpMargemLiquidaAtacado))
			return false;
		if (fpMargemLiquidaVarejo == null) {
			if (other.fpMargemLiquidaVarejo != null)
				return false;
		} else if (!fpMargemLiquidaVarejo.equals(other.fpMargemLiquidaVarejo))
			return false;
		if (fpOutrasDespesasAtacado == null) {
			if (other.fpOutrasDespesasAtacado != null)
				return false;
		} else if (!fpOutrasDespesasAtacado.equals(other.fpOutrasDespesasAtacado))
			return false;
		if (fpOutrasDespesasVarejo == null) {
			if (other.fpOutrasDespesasVarejo != null)
				return false;
		} else if (!fpOutrasDespesasVarejo.equals(other.fpOutrasDespesasVarejo))
			return false;
		if (fpOutrosCreditos == null) {
			if (other.fpOutrosCreditos != null)
				return false;
		} else if (!fpOutrosCreditos.equals(other.fpOutrosCreditos))
			return false;
		if (fpOutrosCustos == null) {
			if (other.fpOutrosCustos != null)
				return false;
		} else if (!fpOutrosCustos.equals(other.fpOutrosCustos))
			return false;
		if (fpPisVendaAtacado == null) {
			if (other.fpPisVendaAtacado != null)
				return false;
		} else if (!fpPisVendaAtacado.equals(other.fpPisVendaAtacado))
			return false;
		if (fpPisVendaVarejo == null) {
			if (other.fpPisVendaVarejo != null)
				return false;
		} else if (!fpPisVendaVarejo.equals(other.fpPisVendaVarejo))
			return false;
		if (fpSeguro == null) {
			if (other.fpSeguro != null)
				return false;
		} else if (!fpSeguro.equals(other.fpSeguro))
			return false;
		if (fpSimplesNacionalAtacado == null) {
			if (other.fpSimplesNacionalAtacado != null)
				return false;
		} else if (!fpSimplesNacionalAtacado.equals(other.fpSimplesNacionalAtacado))
			return false;
		if (fpSimplesNacionalVarejo == null) {
			if (other.fpSimplesNacionalVarejo != null)
				return false;
		} else if (!fpSimplesNacionalVarejo.equals(other.fpSimplesNacionalVarejo))
			return false;
		if (fpSubstituicaoTrib == null) {
			if (other.fpSubstituicaoTrib != null)
				return false;
		} else if (!fpSubstituicaoTrib.equals(other.fpSubstituicaoTrib))
			return false;
		if (fpVlrEmbalagem == null) {
			if (other.fpVlrEmbalagem != null)
				return false;
		} else if (!fpVlrEmbalagem.equals(other.fpVlrEmbalagem))
			return false;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		if (lastCoduser == null) {
			if (other.lastCoduser != null)
				return false;
		} else if (!lastCoduser.equals(other.lastCoduser))
			return false;
		if (lastMovto == null) {
			if (other.lastMovto != null)
				return false;
		} else if (!lastMovto.equals(other.lastMovto))
			return false;
		if (locacao == null) {
			if (other.locacao != null)
				return false;
		} else if (!locacao.equals(other.locacao))
			return false;
		if (margemLucroBrutoAtacado == null) {
			if (other.margemLucroBrutoAtacado != null)
				return false;
		} else if (!margemLucroBrutoAtacado.equals(other.margemLucroBrutoAtacado))
			return false;
		if (margemLucroBrutoVarejo == null) {
			if (other.margemLucroBrutoVarejo != null)
				return false;
		} else if (!margemLucroBrutoVarejo.equals(other.margemLucroBrutoVarejo))
			return false;
		if (promocaoDataValidade == null) {
			if (other.promocaoDataValidade != null)
				return false;
		} else if (!promocaoDataValidade.equals(other.promocaoDataValidade))
			return false;
		if (qtdAtual == null) {
			if (other.qtdAtual != null)
				return false;
		} else if (!qtdAtual.equals(other.qtdAtual))
			return false;
		if (qtdCcusto1 == null) {
			if (other.qtdCcusto1 != null)
				return false;
		} else if (!qtdCcusto1.equals(other.qtdCcusto1))
			return false;
		if (qtdCcusto2 == null) {
			if (other.qtdCcusto2 != null)
				return false;
		} else if (!qtdCcusto2.equals(other.qtdCcusto2))
			return false;
		if (qtdMax == null) {
			if (other.qtdMax != null)
				return false;
		} else if (!qtdMax.equals(other.qtdMax))
			return false;
		if (qtdMin == null) {
			if (other.qtdMin != null)
				return false;
		} else if (!qtdMin.equals(other.qtdMin))
			return false;
		if (recordNo == null) {
			if (other.recordNo != null)
				return false;
		} else if (!recordNo.equals(other.recordNo))
			return false;
		if (repCodFornecedor == null) {
			if (other.repCodFornecedor != null)
				return false;
		} else if (!repCodFornecedor.equals(other.repCodFornecedor))
			return false;
		if (repData == null) {
			if (other.repData != null)
				return false;
		} else if (!repData.equals(other.repData))
			return false;
		if (repDocumento == null) {
			if (other.repDocumento != null)
				return false;
		} else if (!repDocumento.equals(other.repDocumento))
			return false;
		if (repFornecedor == null) {
			if (other.repFornecedor != null)
				return false;
		} else if (!repFornecedor.equals(other.repFornecedor))
			return false;
		if (repQuantidade == null) {
			if (other.repQuantidade != null)
				return false;
		} else if (!repQuantidade.equals(other.repQuantidade))
			return false;
		if (tributacao == null) {
			if (other.tributacao != null)
				return false;
		} else if (!tributacao.equals(other.tributacao))
			return false;
		if (utctag == null) {
			if (other.utctag != null)
				return false;
		} else if (!utctag.equals(other.utctag))
			return false;
		if (vlrCusto == null) {
			if (other.vlrCusto != null)
				return false;
		} else if (!vlrCusto.equals(other.vlrCusto))
			return false;
		if (vlrVendaAtacado == null) {
			if (other.vlrVendaAtacado != null)
				return false;
		} else if (!vlrVendaAtacado.equals(other.vlrVendaAtacado))
			return false;
		if (vlrVendaPromocao == null) {
			if (other.vlrVendaPromocao != null)
				return false;
		} else if (!vlrVendaPromocao.equals(other.vlrVendaPromocao))
			return false;
		if (vlrVendaVarejo == null) {
			if (other.vlrVendaVarejo != null)
				return false;
		} else if (!vlrVendaVarejo.equals(other.vlrVendaVarejo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ItemValor [item=" + item + ", codemp=" + codemp + ", altPrecoData=" + altPrecoData
				+ ", altPrecoUsuario=" + altPrecoUsuario + ", flagAtivo=" + flagAtivo + ", fpCofinsVendaAtacado="
				+ fpCofinsVendaAtacado + ", fpCofinsVendaVarejo=" + fpCofinsVendaVarejo + ", fpComissaoVendaAtacado="
				+ fpComissaoVendaAtacado + ", fpComissaoVendaVarejo=" + fpComissaoVendaVarejo
				+ ", fpContribuicaoSocialAtacado=" + fpContribuicaoSocialAtacado + ", fpContribuicaoSocialVarejo="
				+ fpContribuicaoSocialVarejo + ", fpCreditoCofins=" + fpCreditoCofins + ", fpCreditoIcms="
				+ fpCreditoIcms + ", fpCreditoPis=" + fpCreditoPis + ", fpCustoOperacionalAtacado="
				+ fpCustoOperacionalAtacado + ", fpCustoOperacionalVarejo=" + fpCustoOperacionalVarejo
				+ ", fpCustoRealCompra=" + fpCustoRealCompra + ", fpDescontoCompra=" + fpDescontoCompra
				+ ", fpDespAcessoria=" + fpDespAcessoria + ", fpFreteCompra=" + fpFreteCompra + ", fpFreteVendaAtacado="
				+ fpFreteVendaAtacado + ", fpFreteVendaVarejo=" + fpFreteVendaVarejo + ", fpIcmsDesonerado="
				+ fpIcmsDesonerado + ", fpIcmsIssVendaAtacado=" + fpIcmsIssVendaAtacado + ", fpIcmsIssVendaVarejo="
				+ fpIcmsIssVendaVarejo + ", fpImpostoRendaAtacado=" + fpImpostoRendaAtacado + ", fpImpostoRendaVarejo="
				+ fpImpostoRendaVarejo + ", fpIpiCompra=" + fpIpiCompra + ", fpMargemLiquidaAtacado="
				+ fpMargemLiquidaAtacado + ", fpMargemLiquidaVarejo=" + fpMargemLiquidaVarejo
				+ ", fpOutrasDespesasAtacado=" + fpOutrasDespesasAtacado + ", fpOutrasDespesasVarejo="
				+ fpOutrasDespesasVarejo + ", fpOutrosCreditos=" + fpOutrosCreditos + ", fpOutrosCustos="
				+ fpOutrosCustos + ", fpPisVendaAtacado=" + fpPisVendaAtacado + ", fpPisVendaVarejo=" + fpPisVendaVarejo
				+ ", fpSeguro=" + fpSeguro + ", fpSimplesNacionalAtacado=" + fpSimplesNacionalAtacado
				+ ", fpSimplesNacionalVarejo=" + fpSimplesNacionalVarejo + ", fpSubstituicaoTrib=" + fpSubstituicaoTrib
				+ ", fpVlrEmbalagem=" + fpVlrEmbalagem + ", lastCoduser=" + lastCoduser + ", lastMovto=" + lastMovto
				+ ", locacao=" + locacao + ", margemLucroBrutoAtacado=" + margemLucroBrutoAtacado
				+ ", margemLucroBrutoVarejo=" + margemLucroBrutoVarejo + ", promocaoDataValidade="
				+ promocaoDataValidade + ", qtdAtual=" + qtdAtual + ", qtdCcusto1=" + qtdCcusto1 + ", qtdCcusto2="
				+ qtdCcusto2 + ", qtdMax=" + qtdMax + ", qtdMin=" + qtdMin + ", recordNo=" + recordNo
				+ ", repCodFornecedor=" + repCodFornecedor + ", repData=" + repData + ", repDocumento=" + repDocumento
				+ ", repFornecedor=" + repFornecedor + ", repQuantidade=" + repQuantidade + ", utctag=" + utctag
				+ ", vlrCusto=" + vlrCusto + ", vlrVendaAtacado=" + vlrVendaAtacado + ", vlrVendaPromocao="
				+ vlrVendaPromocao + ", vlrVendaVarejo=" + vlrVendaVarejo + ", tributacao=" + tributacao + "]";
	}

	
	
}