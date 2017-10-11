package models.compras;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * The persistent class for the unidade database table.
 * 
 * 
 */
@Entity
@Table(name = "OPERACAO_ENTRADA")
@NamedQueries({
		@NamedQuery(name = "OperacaoEntradaById1", query = "SELECT d FROM OperacaoEntrada d WHERE d.codigo =:codigo AND d.flagAtivo =1 AND d.codemp IN (:codemp)"),
		@NamedQuery(name = "OperacaoEntradaAll1", query = "SELECT d FROM OperacaoEntrada d WHERE d.flagAtivo IN (:flag) AND d.codemp IN (:codemp)"),
		@NamedQuery(name = "OperacaoEntradaLast1", query = "SELECT d FROM OperacaoEntrada d WHERE d.codemp IN (:codemp) AND d.flagAtivo =1 ORDER BY d.codigo DESC")
		})
@IdClass(value = OperacaoEntradaPK1.class)
public class OperacaoEntrada1 implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// ----  CHAVE PRIMARIA ---- //
	@Id
	private Integer codigo;
	
	@Id
	private Integer codemp;
	
	@Id
	@Column(name = "CHECK_DELETE")
	private BigInteger checkDelete;

	// ----  CHAVE PRIMARIA ---- //
	
	// --- ATRIBUTOS -------//
	@Column(name = "RECORD_NO")
	private Integer recordNo;
	
	private String descricao;

	@Column(name = "FLAG_ATIVO")
	private Integer flagAtivo;

	@Column(name = "LAST_CODUSER")
	private Integer lastCoduser;

	@Column(name = "LAST_MOVTO")
	private LocalDateTime lastMovto;

	private BigInteger utctag;
	
	@Column(name = "TIPO_OPERACAO")
	private Integer tipoOperacao;
	
	@Column(name = "DESC_FISCAL")
	private String descFiscal;
	
	@Column(name = "DESC_FISCAL_SERVICO")
	private String descFiscalServico;
	
	@Column(name = "CFOP")
	private Integer cfop;
	
	@Column(name = "CFOP2")
	private Integer cfop2;
	
	@Column(name = "FLAG_SOLICITA_AUTORIZACAO")
	private Integer flagSolicitaAutorizacao;
	
	@Column(name = "NF_FLAG_XML")
	private Integer nfFlagXml;
	
	
	//CONSTRUTOR PADRÃO DA CLASSE
	public OperacaoEntrada1() {
	}
	
	public BigInteger getCheckDelete() {
		return this.checkDelete;
	}

	public void setCheckDelete(BigInteger checkDelete) {
		this.checkDelete = checkDelete;
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

	public BigInteger getUtctag() {
		return this.utctag;
	}

	public void setUtctag(BigInteger utctag) {
		this.utctag = utctag;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getCodemp() {
		return codemp;
	}

	public void setCodemp(Integer codemp) {
		this.codemp = codemp;
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

	public void setTipoOperacao(Integer tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
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

	public Integer getCfop() {
		return cfop;
	}

	public void setCfop(Integer cfop) {
		this.cfop = cfop;
	}

	public Integer getCfop2() {
		return cfop2;
	}

	public void setCfop2(Integer cfop2) {
		this.cfop2 = cfop2;
	}

	public Integer getFlagSolicitaAutorizacao() {
		return flagSolicitaAutorizacao;
	}

	public void setFlagSolicitaAutorizacao(Integer flagSolicitaAutorizacao) {
		this.flagSolicitaAutorizacao = flagSolicitaAutorizacao;
	}

	public Integer getNfFlagXml() {
		return nfFlagXml;
	}

	public void setNfFlagXml(Integer nfFlagXml) {
		this.nfFlagXml = nfFlagXml;
	}

	public void setRecordNo(Integer recordNo) {
		this.recordNo = recordNo;
	}

	
}