package models.vendas;

import java.io.Serializable;
import javax.persistence.*;

import models.financeiro.ContasReceber;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;
import java.math.BigInteger;


/**
 * The persistent class for the plano_pagamento database table.
 * 
 */
@Entity
@Table(name="plano_pagamento")
//@NamedQuery(name="PlanoPagamento.findAll", query="SELECT p FROM PlanoPagamento p")
@NamedQueries({
	@NamedQuery(name = "PlanoPagamentoById", query = "SELECT p FROM PlanoPagamento p WHERE p.codigo =:codigo AND p.flagAtivo =1 AND p.codemp IN (:codemp)"),
	@NamedQuery(name = "PlanoPagamentoAll", query = "SELECT p FROM PlanoPagamento p WHERE p.flagAtivo IN (:flag) AND p.codemp IN (:codemp)"),
	@NamedQuery(name = "PlanoPagamentoLast", query = "SELECT p FROM PlanoPagamento p WHERE p.codemp IN (:codemp) AND p.flagAtivo =1 ORDER BY p.codigo DESC")
	})
@IdClass(value = PlanoPagamentoPK.class)
public class PlanoPagamento implements Serializable {
	private static final long serialVersionUID = 1L;

//	@EmbeddedId
//	private PlanoPagamentoPK id;
	@Id
	private Integer codigo;

	@Id
	@Column(name="CHECK_DELETE")
	private BigInteger checkDelete;

	private Integer codemp;

	private String descricao;
	
	@Column(name="DESC_REDUZIDA")
	private String descReduzida;
	
	@Column(name="FLAG_TIPO_TXJUROS")
	private Integer flagTipoTxJuros;
	
	@Column(name="NUM_PARCELAS")
	private Integer numParcelas;
	
	@Column(name="INTERVALO_PARCELAS")
	private Integer intervaloParcelas;
	
	@Column(name="VARIACAO_MAXIMA_VENCTO")
	private Integer variacaoMaxVencto;
	
	@Column(name="VALOR_MINIMO_RECEBIMENTO")
	private BigDecimal valorMinRecebimento;
	
	@Column(name="DIAS_PRIMEIRO_RECEBIMENTO")
	private Integer diasPrimeiroRecebimento;
	
	@Column(name="TIPO_PRAZO")
	private Integer tipoPrazo;
	
	@Column(name="VENCTO_DIA_COMBINADO")
	private Integer venctoDiaCombinado;
	
	@Column(name="TAXA_DESCONTO_ACRECIMO")
	private BigDecimal taxaDescontoAcrecimo;	
	
	@Column(name="FLAG_AUTORIZACAO")
	private Integer flagAutorizacao;
	
	@Column(name="FLAG_SHOW_VENDAS")
	private Integer flagShowVendas;
	
	@Column(name="VALOR_MINIMO_VENDA")
	private BigDecimal valorMinVenda;
	
	@Column(name="DESCONTO_MAXIMO")
	private BigDecimal descontoMax;	

	@Column(name="FLAG_ATIVO")
	private Integer flagAtivo;

	@Column(name="LAST_CODUSER")
	private Integer lastCoduser;

	@Column(name="LAST_MOVTO")
	private LocalDateTime lastMovto;

	@Column(name="RECORD_NO")
	private Integer recordNo;

	private BigInteger utctag;

	public PlanoPagamento() {
	}
	
	@OneToMany(mappedBy="planoPagamento", fetch = FetchType.LAZY)
	private List<Cliente> cliente;
	
	@OneToMany(mappedBy="planoPagamento", fetch = FetchType.LAZY)
	private List<ContasReceber> contasReceber;

//	public PlanoPagamentoPK getId() {
//		return this.id;
//	}
//
//	public void setId(PlanoPagamentoPK id) {
//		this.id = id;
//	}
	

	public String getDescricao() {
		return this.descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public BigInteger getCheckDelete() {
		return checkDelete;
	}

	public void setCheckDelete(BigInteger checkDelete) {
		this.checkDelete = checkDelete;
	}

	public Integer getCodemp() {
		return codemp;
	}

	public void setCodemp(Integer codemp) {
		this.codemp = codemp;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescReduzida() {
		return descReduzida;
	}

	public void setDescReduzida(String descReduzida) {
		this.descReduzida = descReduzida;
	}

	public Integer getFlagTipoTxJuros() {
		return flagTipoTxJuros;
	}

	public void setFlagTipoTxJuros(Integer flagTipoTxJuros) {
		this.flagTipoTxJuros = flagTipoTxJuros;
	}

	public Integer getNumParcelas() {
		return numParcelas;
	}

	public void setNumParcelas(Integer numParcelas) {
		this.numParcelas = numParcelas;
	}

	public Integer getIntervaloParcelas() {
		return intervaloParcelas;
	}

	public void setIntervaloParcelas(Integer intervaloParcelas) {
		this.intervaloParcelas = intervaloParcelas;
	}

	public Integer getVariacaoMaxVencto() {
		return variacaoMaxVencto;
	}

	public void setVariacaoMaxVencto(Integer variacaoMaxVencto) {
		this.variacaoMaxVencto = variacaoMaxVencto;
	}

	public BigDecimal getValorMinRecebimento() {
		return valorMinRecebimento;
	}

	public void setValorMinRecebimento(BigDecimal valorMinRecebimento) {
		this.valorMinRecebimento = valorMinRecebimento;
	}

	public Integer getDiasPrimeiroRecebimento() {
		return diasPrimeiroRecebimento;
	}

	public void setDiasPrimeiroRecebimento(Integer diasPrimeiroRecebimento) {
		this.diasPrimeiroRecebimento = diasPrimeiroRecebimento;
	}

	public Integer getTipoPrazo() {
		return tipoPrazo;
	}

	public void setTipoPrazo(Integer tipoPrazo) {
		this.tipoPrazo = tipoPrazo;
	}

	public Integer getVenctoDiaCombinado() {
		return venctoDiaCombinado;
	}

	public void setVenctoDiaCombinado(Integer venctoDiaCombinado) {
		this.venctoDiaCombinado = venctoDiaCombinado;
	}
	
	

	public BigDecimal getTaxaDescontoAcrecimo() {
		return taxaDescontoAcrecimo;
	}

	public void setTaxaDescontoAcrecimo(BigDecimal taxaDescontoAcrecimo) {
		this.taxaDescontoAcrecimo = taxaDescontoAcrecimo;
	}

	public Integer getFlagAutorizacao() {
		return flagAutorizacao;
	}

	public void setFlagAutorizacao(Integer flagAutorizacao) {
		this.flagAutorizacao = flagAutorizacao;
	}

	public Integer getFlagShowVendas() {
		return flagShowVendas;
	}

	public void setFlagShowVendas(Integer flagShowVendas) {
		this.flagShowVendas = flagShowVendas;
	}

	public BigDecimal getValorMinVenda() {
		return valorMinVenda;
	}

	public void setValorMinVenda(BigDecimal valorMinVenda) {
		this.valorMinVenda = valorMinVenda;
	}

	public BigDecimal getDescontoMax() {
		return descontoMax;
	}

	public void setDescontoMax(BigDecimal descontoMax) {
		this.descontoMax = descontoMax;
	}

	public Integer getFlagAtivo() {
		return this.flagAtivo;
	}

	public void setFlagAtivo(Integer flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public Integer getLastCoduser() {
		return this.lastCoduser;
	}

	public void setLastCoduser(Integer lastCoduser) {
		this.lastCoduser = lastCoduser;
	}

	public LocalDateTime getLastMovto() {
		return this.lastMovto;
	}

	public void setLastMovto(LocalDateTime lastMovto) {
		this.lastMovto = lastMovto;
	}

	public Integer getRecordNo() {
		return this.recordNo;
	}

	public void setRecordNo(Integer recordNo) {
		this.recordNo = recordNo;
	}

	public BigInteger getUtctag() {
		return this.utctag;
	}

	public void setUtctag(BigInteger utctag) {
		this.utctag = utctag;
	}

}