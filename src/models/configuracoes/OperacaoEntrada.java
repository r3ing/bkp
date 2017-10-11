package models.configuracoes;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.Hibernate;

import models.financeiro.PlanoConta;
import models.financeiro.Portador;
import models.financeiro.Secao;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.math.BigInteger;


/**
 * The persistent class for the operacao_entrada database table.
 * 
 */
@Entity
@Table(name = "OPERACAO_ENTRADA")
@NamedQueries({
		@NamedQuery(name = "OperacaoEntradaById", query = "SELECT op FROM OperacaoEntrada op "
				+ "LEFT JOIN FETCH op.portador p "
				+ "LEFT JOIN FETCH op.depositoEstoque dp "
				+ "LEFT JOIN FETCH op.planoConta pc "
				+ "LEFT JOIN FETCH op.secao s "
				+ "WHERE op.codigo =:codigo AND op.flagAtivo =1 AND op.codemp IN (:codemp)"),
		@NamedQuery(name = "OperacaoEntradaAll", query = "SELECT d FROM OperacaoEntrada d WHERE d.flagAtivo IN (:flag) AND d.codemp IN (:codemp)"),
		@NamedQuery(name = "OperacaoEntradaLast", query = "SELECT d FROM OperacaoEntrada d WHERE d.codemp IN (:codemp) AND d.flagAtivo =1 ORDER BY d.codigo DESC")
		})
@IdClass(value = OperacaoEntradaPK.class)
public class OperacaoEntrada implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CHECK_DELETE")
	private BigInteger checkDelete;
//	@Id
	private Integer codemp;
	@Id
	private Integer codigo;

	private Integer cfop;

	private Integer cfop2;

	@Column(name = "DESC_FISCAL")
	private String descFiscal;

	@Column(name = "DESC_FISCAL_SERVICO")
	private String descFiscalServico;

	private String descricao;

	@Column(name = "FLAG_ATIVO")
	private Integer flagAtivo;

	@Column(name = "FLAG_SOLICITA_AUTORIZACAO")
	private Integer flagSolicitaAutorizacao;

	@Column(name = "LAST_CODUSER")
	private Integer lastCoduser;

	@Column(name = "LAST_MOVTO")
	private LocalDateTime lastMovto;

	@Column(name = "NF_FECHAR_NF_REPOSICAO")
	private Integer nfFecharNfReposicao;

	@Column(name = "NF_FLAG_XML")
	private Integer nfFlagXml;

	@Column(name = "NF_REPOSICAO_MULT_ESTOQUE")
	private Integer nfReposicaoMultEstoque;
	
	@Column(name = "NF_GERAR_CONTA_PAGAR")
	private Integer nfGerarContaPagar;

	@Column(name = "RECORD_NO")
	private Integer recordNo;

	@Column(name = "TIPO_OPERACAO")
	private Integer tipoOperacao;

	private BigInteger utctag;

	// --------------------------------
	@Column(name="NF_COD_DEPOSITO_ESTOQUE")
	private Integer codDepositoEstoque = 0;
	
	@Column(name="NF_COD_DEPOSITO_ESTOQUE_FK")
	private BigInteger codDepositoEstoqueFK = BigInteger.valueOf(0);
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = "NF_COD_DEPOSITO_ESTOQUE", referencedColumnName="codigo", insertable=false, updatable=false),
		@JoinColumn(name = "NF_COD_DEPOSITO_ESTOQUE_FK", referencedColumnName="CHECK_DELETE", insertable=false, updatable=false)
	})
	private DepositoEstoque depositoEstoque;	
	
	@Column(name="NF_COD_PLANOS_CONTA")
	private Integer codPlanoConta = 0;
	
	@Column(name="NF_COD_PLANOS_CONTA_FK")
	private BigInteger codPlanoContaFK = BigInteger.valueOf(0);
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = "NF_COD_PLANOS_CONTA", referencedColumnName="codigo", insertable=false, updatable=false),
		@JoinColumn(name = "NF_COD_PLANOS_CONTA_FK", referencedColumnName="CHECK_DELETE", insertable=false, updatable=false)
	})
	private PlanoConta planoConta;
	
	@Column(name="NF_COD_PORTADOR")
	private Integer codPortador = 0;
	
	@Column(name="NF_COD_PORTADOR_FK")
	private BigInteger codPortadorFK = BigInteger.valueOf(0);
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = "NF_COD_PORTADOR", referencedColumnName="codigo", insertable=false, updatable=false),
		@JoinColumn(name = "NF_COD_PORTADOR_FK", referencedColumnName="CHECK_DELETE", insertable=false, updatable=false)
	})
	private Portador portador;
	
	@Column(name="NF_COD_SECAO")
	private Integer codSecao = 0;
	
	@Column(name="NF_COD_SECAO_FK")
	private BigInteger codSecaoFK = BigInteger.valueOf(0);
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = "NF_COD_SECAO", referencedColumnName="codigo", insertable=false, updatable=false),
		@JoinColumn(name = "NF_COD_SECAO_FK", referencedColumnName="CHECK_DELETE", insertable=false, updatable=false)
	})
	private Secao secao;

	// --------------------------------

	public OperacaoEntrada() {
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

	public void setCodemp(int codemp) {
		this.codemp = codemp;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public Integer getCfop() {
		return cfop;
	}

	public void setCfop(int cfop) {
		this.cfop = cfop;
	}

	public Integer getCfop2() {
		return cfop2;
	}

	public void setCfop2(int cfop2) {
		this.cfop2 = cfop2;
	}

	public String getDescFiscal() {
		return descFiscal;
	}

	public void setDescFiscal(String descFiscal) {
		this.descFiscal = descFiscal;
	}

	public String getDescFiscalServico() {
		return descFiscalServico;
	}

	public void setDescFiscalServico(String descFiscalServico) {
		this.descFiscalServico = descFiscalServico;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(int flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public Integer getFlagSolicitaAutorizacao() {
		return flagSolicitaAutorizacao;
	}

	public void setFlagSolicitaAutorizacao(int flagSolicitaAutorizacao) {
		this.flagSolicitaAutorizacao = flagSolicitaAutorizacao;
	}

	public Integer getLastCoduser() {
		return lastCoduser;
	}

	public void setLastCoduser(int lastCoduser) {
		this.lastCoduser = lastCoduser;
	}

	public LocalDateTime getLastMovto() {
		return lastMovto;
	}

	public void setLastMovto(LocalDateTime lastMovto) {
		this.lastMovto = lastMovto;
	}

	public Integer getNfFecharNfReposicao() {
		return nfFecharNfReposicao;
	}

	public void setNfFecharNfReposicao(int nfFecharNfReposicao) {
		this.nfFecharNfReposicao = nfFecharNfReposicao;
	}

	public Integer getNfFlagXml() {
		return nfFlagXml;
	}

	public void setNfFlagXml(int nfFlagXml) {
		this.nfFlagXml = nfFlagXml;
	}

	public Integer getNfReposicaoMultEstoque() {
		return nfReposicaoMultEstoque;
	}

	public void setNfReposicaoMultEstoque(int nfReposicaoMultEstoque) {
		this.nfReposicaoMultEstoque = nfReposicaoMultEstoque;
	}	

	public Integer getNfGerarContaPagar() {
		return nfGerarContaPagar;
	}

	public void setNfGerarContaPagar(Integer nfGerarContaPagar) {
		this.nfGerarContaPagar = nfGerarContaPagar;
	}

	public Integer getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(int recordNo) {
		this.recordNo = recordNo;
	}

	public Integer getTipoOperacao() {
		return tipoOperacao;
	}

	public void setTipoOperacao(int tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}

	public BigInteger getUtctag() {
		return utctag;
	}

	public void setUtctag(BigInteger utctag) {
		this.utctag = utctag;
	}
	
	//*-**-**-*-*-**-*-*-*-*-*-**-*-*-*-*---*-*-*-*-
	
	public DepositoEstoque getDepositoEstoque() {
		if(!Hibernate.isInitialized(this.depositoEstoque)) {
            try {
                Hibernate.initialize(this.depositoEstoque);
            } catch(Exception one) {
            	this.depositoEstoque = null;
            }
        }
		return depositoEstoque;
	}

	public Integer getCodDepositoEstoque() {
		return codDepositoEstoque;
	}

	public void setCodDepositoEstoque(Integer codDepositoEstoque) {
		this.codDepositoEstoque = codDepositoEstoque;
	}

	public BigInteger getCodDepositoEstoqueFK() {
		return codDepositoEstoqueFK;
	}

	public void setCodDepositoEstoqueFK(BigInteger codDepositoEstoqueFK) {
		this.codDepositoEstoqueFK = codDepositoEstoqueFK;
	}

	public void setDepositoEstoque(DepositoEstoque depositoEstoque) {
		this.depositoEstoque = depositoEstoque;
	}

	public PlanoConta getPlanoConta() {
		if(!Hibernate.isInitialized(this.planoConta)) {
            try {
                Hibernate.initialize(this.planoConta);
            } catch(Exception one) {
            	this.planoConta = null;
            }
        }
		return planoConta;
	}

	public void setPlanoConta(PlanoConta planoConta) {
		this.planoConta = planoConta;
	}	

	public Integer getCodPlanoConta() {
		return codPlanoConta;
	}

	public void setCodPlanoConta(Integer codPlanoConta) {
		this.codPlanoConta = codPlanoConta;
	}

	public BigInteger getCodPlanoContaFK() {
		return codPlanoContaFK;
	}

	public void setCodPlanoContaFK(BigInteger codPlanoContaFK) {
		this.codPlanoContaFK = codPlanoContaFK;
	}

	public Portador getPortador() {
		if(!Hibernate.isInitialized(this.portador)) {
            try {
                Hibernate.initialize(this.portador);
            } catch(Exception one) {
            	this.portador = null;
            }
        }
		return portador;
	}

	public void setPortador(Portador portador) {
		this.portador = portador;
	}
	
	public Integer getCodPortador() {
		return codPortador;
	}

	public void setCodPortador(Integer codPortador) {
		this.codPortador = codPortador;
	}

	public BigInteger getCodPortadorFK() {
		return codPortadorFK;
	}

	public void setCodPortadorFK(BigInteger codPortadorFK) {
		this.codPortadorFK = codPortadorFK;
	}

	public Secao getSecao() {
		if(!Hibernate.isInitialized(this.secao)) {
            try {
                Hibernate.initialize(this.secao);
            } catch(Exception one) {
            	this.secao = null;
            }
        }
		return secao;
	}

	public void setSecao(Secao secao) {
		this.secao = secao;
	}

	public Integer getCodSecao() {
		return codSecao;
	}

	public void setCodSecao(Integer codSecao) {
		this.codSecao = codSecao;
	}

	public BigInteger getCodSecaoFK() {
		return codSecaoFK;
	}

	public void setCodSecaoFK(BigInteger codSecaoFK) {
		this.codSecaoFK = codSecaoFK;
	}
	
	

	
}