package models.configuracoes;

import java.io.Serializable;
import javax.persistence.*;

import models.financeiro.ContasReceber;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;


/**
 * The persistent class for the operacao_financeiro database table.
 * 
 */
@Entity
@Table(name="operacao_financeiro")
//@NamedQuery(name="OperacaoFinanceiro.findAll", query="SELECT o FROM OperacaoFinanceiro o")
@NamedQueries({
	@NamedQuery(name = "OperacaoFinanceiroById", query = "SELECT o FROM OperacaoFinanceiro o WHERE o.codigo =:codigo AND o.flagAtivo =1 AND o.codemp IN (:codemp)"),
	@NamedQuery(name = "OperacaoFinanceiroAll", query = "SELECT o FROM OperacaoFinanceiro o WHERE o.flagAtivo IN (:flag) AND o.codemp IN (:codemp)"),
	@NamedQuery(name = "OperacaoFinanceiroLast", query = "SELECT o FROM OperacaoFinanceiro o WHERE o.codemp IN (:codemp) AND o.flagAtivo =1 ORDER BY o.codigo DESC"),
	@NamedQuery(name = "OperacaoContasReceberById", query = "SELECT o FROM OperacaoFinanceiro o WHERE o.codigo =:codigo AND o.flagAtivo =1 AND o.tipoOperacao=0 AND o.codemp IN (:codemp)")
	})
@IdClass(value = OperacaoFinanceiroPK.class)
public class OperacaoFinanceiro implements Serializable {
	private static final long serialVersionUID = 1L;

//	@EmbeddedId
//	private OperacaoFinanceiroPK id;
	
	@Id
	private Integer codigo;
	
	@Id
	@Column(name="CHECK_DELETE")
	private BigInteger checkDelete;
	
	private Integer codemp;

	private String descricao;

	@Column(name="FLAG_ATIVO")
	private Integer flagAtivo;

	@Column(name="FLAG_SOLICITA_AUTORIZACAO")
	private Integer flagSolicitaAutorizacao;

	@Column(name="LAST_CODUSER")
	private Integer lastCoduser;

	@Column(name="LAST_MOVTO")
	private LocalDateTime lastMovto;

	private String observacao;

	@Column(name="RECORD_NO")
	private Integer recordNo;

	@Column(name="TIPO_OPERACAO")
	private Integer tipoOperacao;

	private BigInteger utctag;

	public OperacaoFinanceiro() {
	}
	
	@OneToMany(mappedBy="opeFinanceiro", fetch = FetchType.LAZY)
	private List<ContasReceber> contasReceber;	

//	public OperacaoFinanceiroPK getId() {
//		return this.id;
//	}
//
//	public void setId(OperacaoFinanceiroPK id) {
//		this.id = id;
//	}

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



	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getFlagAtivo() {
		return this.flagAtivo;
	}

	public void setFlagAtivo(Integer flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public Integer getFlagSolicitaAutorizacao() {
		return this.flagSolicitaAutorizacao;
	}

	public void setFlagSolicitaAutorizacao(Integer flagSolicitaAutorizacao) {
		this.flagSolicitaAutorizacao = flagSolicitaAutorizacao;
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

	public String getObservacao() {
		return this.observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Integer getRecordNo() {
		return this.recordNo;
	}

	public void setRecordNo(Integer recordNo) {
		this.recordNo = recordNo;
	}

	public Integer getTipoOperacao() {
		return this.tipoOperacao;
	}

	public void setTipoOperacao(Integer tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}

	public BigInteger getUtctag() {
		return this.utctag;
	}

	public void setUtctag(BigInteger utctag) {
		this.utctag = utctag;
	}

}