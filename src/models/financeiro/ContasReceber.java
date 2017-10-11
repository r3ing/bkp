package models.financeiro;

import java.io.Serializable;
import javax.persistence.*;
import javax.swing.text.StyledEditorKit.BoldAction;

import org.hibernate.Hibernate;

import models.configuracoes.OperacaoFinanceiro;
import models.recursosHumanos.Funcionario;
import models.vendas.Cliente;
import models.vendas.PlanoPagamento;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * The persistent class for the contas_receber database table.
 * 
 */
@Entity
@Table(name = "contas_receber")
// @NamedQuery(name="ContasReceber.findAll", query="SELECT c FROM ContasReceber
// c")
@NamedQueries({ @NamedQuery(name = "ContasReceberAll", query = "SELECT c FROM ContasReceber c "
		+ "LEFT JOIN FETCH c.cliente " + "LEFT JOIN FETCH c.opeFinanceiro " + "LEFT JOIN FETCH c.portador "
		+ "LEFT JOIN FETCH c.secao " + "LEFT JOIN FETCH c.vendedor " + "LEFT JOIN FETCH c.planoPagamento "
		+ "LEFT JOIN FETCH c.planoConta " + "WHERE c.flagAtivo IN (:flag) AND c.codemp IN (:codemp)"),

		@NamedQuery(name = "ContasReceberByDocumentParcel", query = "SELECT c FROM ContasReceber c "
				+ "LEFT JOIN FETCH c.cliente " + "LEFT JOIN FETCH c.opeFinanceiro " + "LEFT JOIN FETCH c.portador "
				+ "LEFT JOIN FETCH c.secao " + "LEFT JOIN FETCH c.vendedor " + "LEFT JOIN FETCH c.planoPagamento "
				+ "LEFT JOIN FETCH c.planoConta "
				+ "WHERE c.noDocto = :noDocto AND c.noParcela = :noParcela AND c.cliente = :cliente AND c.flagAtivo=1 AND c.codemp IN (:codemp)"),
		
		@NamedQuery(name = "ContasReceberByDocument", query = "SELECT c FROM ContasReceber c "
				+ "LEFT JOIN FETCH c.cliente " + "LEFT JOIN FETCH c.opeFinanceiro " + "LEFT JOIN FETCH c.portador "
				+ "LEFT JOIN FETCH c.secao " + "LEFT JOIN FETCH c.vendedor " + "LEFT JOIN FETCH c.planoPagamento "
				+ "LEFT JOIN FETCH c.planoConta "
				+ "WHERE c.noDocto = :noDocto AND c.cliente = :cliente AND c.flagAtivo=1 AND c.codemp IN (:codemp)")

})
@IdClass(value = ContasReceberPK.class)
public class ContasReceber implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CHECK_DELETE")
	private BigInteger checkDelete;

	@Id
	@Column(name = "NO_DOCTO")
	private Integer noDocto;

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "COD_CLIENTE_FK", referencedColumnName = "CHECK_DELETE"),
			@JoinColumn(name = "COD_CLIENTE", referencedColumnName = "CODIGO") })
	private Cliente cliente;

	private Integer codemp;

	// @EmbeddedId
	// private ContasReceberPK id;
	@Column(name = "COD_OPERACAO_FIN")
	private Integer codOperacaoFin;

	@Column(name = "COD_OPERACAO_FIN_FK")
	private BigInteger codOperacaoFinFk;

	@Column(name = "COD_PLANO_CONTAS")
	private Integer codPlanoContas;

	@Column(name = "COD_PLANO_CONTAS_FK")
	private BigInteger codPlanoContasFk;

	@Column(name = "COD_PLANO_PAGTO")
	private Integer codPlanoPagto;

	@Column(name = "COD_PLANO_PAGTO_FK")
	private BigInteger codPlanoPagtoFk;

	@Column(name = "COD_PORTADOR")
	private Integer codPortador;

	@Column(name = "COD_PORTADOR_FK")
	private BigInteger codPortadorFk;

	@Column(name = "COD_SECAO")
	private Integer codSecao;

	@Column(name = "COD_SECAO_FK")
	private BigInteger codSecaoFk;

	@Column(name = "COD_USUARIO_LANCAMENTO")
	private Integer codUsuarioLancamento;

	@Column(name = "COD_USUARIO_LANCAMENTO_FK")
	private BigInteger codUsuarioLancamentoFk;

	@Column(name = "COD_USUARIO_QUITACAO")
	private Integer codUsuarioQuitacao;

	@Column(name = "COD_USUARIO_QUITACAO_FK")
	private BigInteger codUsuarioQuitacaoFk;

	@Column(name = "COD_VENDEDOR")
	private Integer codVendedor;

	@Column(name = "COD_VENDEDOR_FK")
	private BigInteger codVendedorFk;

	@Column(name = "DATA_EMISSAO")
	private LocalDateTime dataEmissao;

	@Column(name = "DATA_LANCAMENTO")
	private LocalDateTime dataLancamento;

	@Column(name = "DATA_PAGAMENTO")
	private LocalDateTime dataPagamento;

	@Column(name = "DATA_PREVISAO_PAGTO")
	private LocalDateTime dataPrevisaoPagto;

	@Column(name = "DATA_VENCIMENTO")
	private LocalDateTime dataVencimento;

	@Column(name = "FLAG_ATIVO")
	private Integer flagAtivo;

	// @Column(name = "FLAG_ORIGEM_DOCTO")
	// private Integer flagOrigemDocto;

	@Column(name = "HISTORICO")
	private String historico;

	@Column(name = "FLAG_TIPO_BAIXA")
	private Integer flagTipoBaixa;

	@Column(name = "LAST_CODUSER")
	private Integer lastCoduser;

	@Column(name = "LAST_MOVTO")
	private LocalDateTime lastMovto;

	@Column(name = "NO_PARCELA")
	private Integer noParcela;

	@Column(name = "NOME_CLIENTE")
	private String nomeCliente;

	@Column(name = "RECORD_NO")
	private Integer recordNo;
	
	@Column(name = "ORIGEM_REGISTRO")
	private Integer origemRegistro;

	@Column(name = "SERIE_DOCTO")
	private String serieDocto;

	private BigInteger utctag;

	@Column(name = "VALOR_ACRESCIMO")
	private BigDecimal valorAcrescimo;

	@Column(name = "VALOR_BRUTO_DOCTO")
	private BigDecimal valorBrutoDocto;

	@Column(name = "VALOR_DESCONTO")
	private BigDecimal valorDesconto;

	@Column(name = "VALOR_JUROS")
	private BigDecimal valorJuros;

	@Column(name = "VALOR_JUROS_PAGTO")
	private BigDecimal valorJurosPagto;

	@Column(name = "VALOR_LIQUIDO")
	private BigDecimal valorLiquido;

	@Column(name = "VALOR_PAGTO")
	private BigDecimal valorPagto;

	@Column(name = "VALOR_SALDO")
	private BigDecimal valorSaldo;
	
	public ContasReceber() {
	}

	@Transient
	private Boolean selected = false;	
	
	@Transient
	private Integer atraso;	
	
//	public Integer getAtraso () {
//		this.atraso = 0;
//		
//		if(Hibernate.isInitialized(this.dataVencimento)){
//			try {				
//				long date1 = dataVencimento.toLocalDate().toEpochDay();
//				long date2 = LocalDate.now().toEpochDay();
//				this.atraso = (int) (date2 - date1);				
//			} catch (Exception e) {
//				this.atraso = 0;
//			}
//		}		
//		return this.atraso;
//	}
	
	
	
	@Transient
	private BigDecimal juros;
	


	@Transient
	private BigDecimal saldo;
	
	@Transient
	private BigDecimal valorTotal;
	
	

	// ----- CLIENTE ------
	@Transient
	private String clienteNome;

	public String getClienteNome() {
		this.clienteNome = "";
		if (Hibernate.isInitialized(this.cliente)) {
			try {
				this.clienteNome = this.cliente.getFantasia();
			} catch (Exception o) {
				cliente = null;
			}
		}
		return this.clienteNome;
	}

	@Transient
	private Integer clienteId;

	public Integer getClienteId() {
		this.clienteId = null;
		if (Hibernate.isInitialized(this.cliente)) {
			try {
				this.clienteId = this.cliente.getCodigo();
			} catch (Exception o) {
				cliente = null;
			}
		}
		return this.clienteId;
	}

	// ----- OPERACAO FINACEIRO ------

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "COD_OPERACAO_FIN", referencedColumnName = "CODIGO", insertable = false, updatable = false),
			@JoinColumn(name = "COD_OPERACAO_FIN_FK", referencedColumnName = "CHECK_DELETE", insertable = false, updatable = false), })
	private OperacaoFinanceiro opeFinanceiro;

	public void setPortador(OperacaoFinanceiro opeFinanceiro) {
		this.opeFinanceiro = opeFinanceiro;
	}

	public OperacaoFinanceiro getOperacaoFinanceiro() {
		if (!Hibernate.isInitialized(this.opeFinanceiro)) {
			try {
				Hibernate.initialize(this.opeFinanceiro);
			} catch (Exception one) {
				this.opeFinanceiro = null;
			}
		}
		return this.opeFinanceiro;
	}

	@Transient
	private String opeFinanceiroDescricao;

	public String getOperacaoFinanceiroDescricao() {
		this.opeFinanceiroDescricao = "";
		if (Hibernate.isInitialized(this.opeFinanceiro)) {
			try {
				this.opeFinanceiroDescricao = this.opeFinanceiro.getDescricao();
			} catch (Exception o) {
				opeFinanceiro = null;
			}
		}
		return this.opeFinanceiroDescricao;
	}

	// ----- PORTADOR ------

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "COD_PORTADOR", referencedColumnName = "CODIGO", insertable = false, updatable = false),
			@JoinColumn(name = "COD_PORTADOR_FK", referencedColumnName = "CHECK_DELETE", insertable = false, updatable = false), })
	private Portador portador;

	public void setPortador(Portador portador) {
		this.portador = portador;
	}

	public Portador getPortador() {
		if (!Hibernate.isInitialized(this.portador)) {
			try {
				Hibernate.initialize(this.portador);
			} catch (Exception one) {
				this.portador = null;
			}
		}
		return this.portador;
	}

	@Transient
	private String portadorDescricao;

	public String getPortadorDescricao() {
		this.portadorDescricao = "";
		if (Hibernate.isInitialized(this.portador)) {
			try {
				this.portadorDescricao = this.portador.getDescricao();
			} catch (Exception o) {
				portador = null;
			}
		}
		return this.portadorDescricao;
	}

	// ----- SECAO -----

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "COD_SECAO", referencedColumnName = "CODIGO", insertable = false, updatable = false),
			@JoinColumn(name = "COD_SECAO_FK", referencedColumnName = "CHECK_DELETE", insertable = false, updatable = false), })
	private Secao secao;

	public void setSecao(Secao secao) {
		this.secao = secao;
	}

	public Secao getSecao() {
		if (!Hibernate.isInitialized(this.secao)) {
			try {
				Hibernate.initialize(this.secao);
			} catch (Exception one) {
				this.secao = null;
			}
		}
		return this.secao;
	}

	@Transient
	private String secaoDescricao;

	public String getSecaoDescricao() {
		this.secaoDescricao = "";
		if (Hibernate.isInitialized(this.secao)) {
			try {
				this.secaoDescricao = this.secao.getDescricao();
			} catch (Exception o) {
				secao = null;
			}
		}
		return this.secaoDescricao;
	}

	// ------ VENDEDOR ------

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "COD_VENDEDOR", referencedColumnName = "CODIGO", insertable = false, updatable = false),
			@JoinColumn(name = "COD_VENDEDOR_FK", referencedColumnName = "CHECK_DELETE", insertable = false, updatable = false), })
	private Funcionario vendedor;

	public void setVendedor(Funcionario vendedor) {
		this.vendedor = vendedor;
	}

	public Funcionario getVendedor() {
		if (!Hibernate.isInitialized(this.vendedor)) {
			try {
				Hibernate.initialize(this.vendedor);
			} catch (Exception one) {
				this.vendedor = null;
			}
		}
		return this.vendedor;
	}

	@Transient
	private String vendedorDescricao;

	public String getVendedorDescricao() {
		this.vendedorDescricao = "";
		if (Hibernate.isInitialized(this.vendedor)) {
			try {
				this.vendedorDescricao = this.vendedor.getDescricao();
			} catch (Exception o) {
				vendedor = null;
			}
		}
		return this.vendedorDescricao;
	}

	// ------ PLANO PAGAMENTO --------------

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "COD_PLANO_PAGTO", referencedColumnName = "CODIGO", insertable = false, updatable = false),
			@JoinColumn(name = "COD_PLANO_PAGTO_FK", referencedColumnName = "CHECK_DELETE", insertable = false, updatable = false), })
	private PlanoPagamento planoPagamento;

	public void setPlanoPagamento(PlanoPagamento planoPagamento) {
		this.planoPagamento = planoPagamento;
	}

	public PlanoPagamento getPlanoPagamento() {
		if (!Hibernate.isInitialized(this.planoPagamento)) {
			try {
				Hibernate.initialize(this.planoPagamento);
			} catch (Exception one) {
				this.planoPagamento = null;
			}
		}
		return this.planoPagamento;
	}

	@Transient
	private String planoPagamentoDescricao;

	public String getPlanoPagamentoDescricao() {
		this.planoPagamentoDescricao = "";
		if (Hibernate.isInitialized(this.planoPagamento)) {
			try {
				this.planoPagamentoDescricao = this.planoPagamento.getDescricao();
			} catch (Exception o) {
				planoPagamento = null;
			}
		}
		return this.planoPagamentoDescricao;
	}

	// ------ PLANO DE CONTAS---------------

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "COD_PLANO_CONTAS", referencedColumnName = "CODIGO", insertable = false, updatable = false),
			@JoinColumn(name = "COD_PLANO_CONTAS_FK", referencedColumnName = "CHECK_DELETE", insertable = false, updatable = false), })
	private PlanoConta planoConta;

	public void setPlanoConta(PlanoConta planoConta) {
		this.planoConta = planoConta;
	}

	public PlanoConta getPlanoConta() {
		if (!Hibernate.isInitialized(this.planoConta)) {
			try {
				Hibernate.initialize(this.planoConta);
			} catch (Exception one) {
				this.planoConta = null;
			}
		}
		return this.planoConta;
	}

	@Transient
	private String planoContaDescricao;

	public String getPlanoContaDescricao() {
		this.planoContaDescricao = "";
		if (Hibernate.isInitialized(this.planoConta)) {
			try {
				this.planoContaDescricao = this.planoConta.getDescricao();
			} catch (Exception o) {
				planoConta = null;
			}
		}
		return this.planoContaDescricao;
	}

	// -------------------------------

	public BigInteger getCheckDelete() {
		return checkDelete;
	}

	public void setCheckDelete(BigInteger checkDelete) {
		this.checkDelete = checkDelete;
	}

	public Integer getNoDocto() {
		return noDocto;
	}

	public void setNoDocto(Integer noDocto) {
		this.noDocto = noDocto;
	}

	public Integer getCodemp() {
		return codemp;
	}

	public void setCodemp(Integer codemp) {
		this.codemp = codemp;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Integer getCodOperacaoFin() {
		return codOperacaoFin;
	}

	public void setCodOperacaoFin(Integer codOperacaoFin) {
		this.codOperacaoFin = codOperacaoFin;
	}

	public BigInteger getCodOperacaoFinFk() {
		return codOperacaoFinFk;
	}

	public void setCodOperacaoFinFk(BigInteger codOperacaoFinFk) {
		this.codOperacaoFinFk = codOperacaoFinFk;
	}

	public Integer getCodPlanoContas() {
		return this.codPlanoContas;
	}

	public void setCodPlanoContas(Integer codPlanoContas) {
		this.codPlanoContas = codPlanoContas;
	}

	public BigInteger getCodPlanoContasFk() {
		return this.codPlanoContasFk;
	}

	public void setCodPlanoContasFk(BigInteger codPlanoContasFk) {
		this.codPlanoContasFk = codPlanoContasFk;
	}

	public Integer getCodPlanoPagto() {
		return this.codPlanoPagto;
	}

	public void setCodPlanoPagto(Integer codPlanoPagto) {
		this.codPlanoPagto = codPlanoPagto;
	}

	public BigInteger getCodPlanoPagtoFk() {
		return this.codPlanoPagtoFk;
	}

	public void setCodPlanoPagtoFk(BigInteger codPlanoPagtoFk) {
		this.codPlanoPagtoFk = codPlanoPagtoFk;
	}

	public Integer getCodPortador() {
		return this.codPortador;
	}

	public void setCodPortador(Integer codPortador) {
		this.codPortador = codPortador;
	}

	public BigInteger getCodPortadorFk() {
		return this.codPortadorFk;
	}

	public void setCodPortadorFk(BigInteger codPortadorFk) {
		this.codPortadorFk = codPortadorFk;
	}

	public Integer getCodSecao() {
		return this.codSecao;
	}

	public void setCodSecao(Integer codSecao) {
		this.codSecao = codSecao;
	}

	public BigInteger getCodSecaoFk() {
		return this.codSecaoFk;
	}

	public void setCodSecaoFk(BigInteger codSecaoFk) {
		this.codSecaoFk = codSecaoFk;
	}

	public Integer getCodUsuarioLancamento() {
		return this.codUsuarioLancamento;
	}

	public void setCodUsuarioLancamento(Integer codUsuarioLancamento) {
		this.codUsuarioLancamento = codUsuarioLancamento;
	}

	public BigInteger getCodUsuarioLancamentoFk() {
		return this.codUsuarioLancamentoFk;
	}

	public void setCodUsuarioLancamentoFk(BigInteger codUsuarioLancamentoFk) {
		this.codUsuarioLancamentoFk = codUsuarioLancamentoFk;
	}

	public Integer getCodUsuarioQuitacao() {
		return this.codUsuarioQuitacao;
	}

	public void setCodUsuarioQuitacao(Integer codUsuarioQuitacao) {
		this.codUsuarioQuitacao = codUsuarioQuitacao;
	}

	public BigInteger getCodUsuarioQuitacaoFk() {
		return this.codUsuarioQuitacaoFk;
	}

	public void setCodUsuarioQuitacaoFk(BigInteger codUsuarioQuitacaoFk) {
		this.codUsuarioQuitacaoFk = codUsuarioQuitacaoFk;
	}

	public Integer getCodVendedor() {
		return this.codVendedor;
	}

	public void setCodVendedor(Integer codVendedor) {
		this.codVendedor = codVendedor;
	}

	public BigInteger getCodVendedorFk() {
		return this.codVendedorFk;
	}

	public void setCodVendedorFk(BigInteger codVendedorFk) {
		this.codVendedorFk = codVendedorFk;
	}

	public LocalDateTime getDataEmissao() {
		return this.dataEmissao;
	}

	public void setDataEmissao(LocalDateTime dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public LocalDateTime getDataLancamento() {
		return this.dataLancamento;
	}

	public void setDataLancamento(LocalDateTime dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	public LocalDateTime getDataPagamento() {
		return this.dataPagamento;
	}

	public void setDataPagamento(LocalDateTime dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public LocalDateTime getDataPrevisaoPagto() {
		return this.dataPrevisaoPagto;
	}

	public void setDataPrevisaoPagto(LocalDateTime dataPrevisaoPagto) {
		this.dataPrevisaoPagto = dataPrevisaoPagto;
	}

	public LocalDateTime getDataVencimento() {
		return this.dataVencimento;
	}

	public void setDataVencimento(LocalDateTime dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public Integer getFlagAtivo() {
		return this.flagAtivo;
	}

	public void setFlagAtivo(Integer flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	// public Integer getFlagOrigemDocto() {
	// return this.flagOrigemDocto;
	// }
	//
	// public void setFlagOrigemDocto(Integer flagOrigemDocto) {
	// this.flagOrigemDocto = flagOrigemDocto;
	// }

	public String getHistorico() {
		return historico;
	}

	public void setHistorico(String historico) {
		this.historico = historico;
	}

	public Integer getFlagTipoBaixa() {
		return this.flagTipoBaixa;
	}

	public void setFlagTipoBaixa(Integer flagTipoBaixa) {
		this.flagTipoBaixa = flagTipoBaixa;
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

	public String getNomeCliente() {
		return this.nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public Integer getNoParcela() {
		return noParcela;
	}

	public void setNoParcela(Integer noParcela) {
		this.noParcela = noParcela;
	}	

	public Integer getRecordNo() {
		return this.recordNo;
	}	

	public Integer getOrigemRegistro() {
		return origemRegistro;
	}

	public void setOrigemRegistro(Integer origemRegistro) {
		this.origemRegistro = origemRegistro;
	}

	public void setRecordNo(Integer recordNo) {
		this.recordNo = recordNo;
	}

	public String getSerieDocto() {
		return this.serieDocto;
	}

	public void setSerieDocto(String serieDocto) {
		this.serieDocto = serieDocto;
	}

	public BigInteger getUtctag() {
		return this.utctag;
	}

	public void setUtctag(BigInteger utctag) {
		this.utctag = utctag;
	}

	public BigDecimal getValorAcrescimo() {
		return this.valorAcrescimo;
	}

	public void setValorAcrescimo(BigDecimal valorAcrescimo) {
		this.valorAcrescimo = valorAcrescimo;
	}

	public BigDecimal getValorBrutoDocto() {
		return this.valorBrutoDocto;
	}

	public void setValorBrutoDocto(BigDecimal valorBrutoDocto) {
		this.valorBrutoDocto = valorBrutoDocto;
	}

	public BigDecimal getValorDesconto() {
		return this.valorDesconto;
	}

	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	public BigDecimal getValorJuros() {
		return this.valorJuros;
	}

	public void setValorJuros(BigDecimal valorJuros) {
		this.valorJuros = valorJuros;
	}

	public BigDecimal getValorJurosPagto() {
		return this.valorJurosPagto;
	}

	public void setValorJurosPagto(BigDecimal valorJurosPagto) {
		this.valorJurosPagto = valorJurosPagto;
	}

	public BigDecimal getValorLiquido() {
		return this.valorLiquido;
	}

	public void setValorLiquido(BigDecimal valorLiquido) {
		this.valorLiquido = valorLiquido;
	}

	public BigDecimal getValorPagto() {
		return this.valorPagto;
	}

	public void setValorPagto(BigDecimal valorPagto) {
		this.valorPagto = valorPagto;
	}

	public BigDecimal getValorSaldo() {
		return this.valorSaldo;
	}

	public void setValorSaldo(BigDecimal valorSaldo) {
		this.valorSaldo = valorSaldo;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public void setAtraso(Integer atraso) {
		this.atraso = atraso;
	}
	
	public Integer getAtraso() {
		return atraso;
	}

	public BigDecimal getJuros() {
		return juros;
	}

	public void setJuros(BigDecimal juros) {
		this.juros = juros;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	@Override
	public ContasReceber clone() {

		try {
			return (ContasReceber) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}

}