package models.configuracoes;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.*;


import java.sql.Timestamp;
import java.time.LocalDateTime;


/**
 * The persistent class for the auditoria database table.
 * 
 */
@Entity
@Table(name = "Auditoria")
@NamedQuery(name="Auditoria.findAll", query="SELECT a FROM Auditoria a WHERE a.codemp =:codemp")
@IdClass(value = AuditoriaPK.class)
public class Auditoria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="COD_USUARIO")
	private Integer codUsuario;
	
	@Id
	private Integer codemp;
	
	@Id
	@Column(name="CHECK_DELETE")
	private BigInteger checkDelete;
	
	@Column(name="RECORD_NO")
	private Integer recordNo;
	
	@Column(name="DOC_CODIGO")
	private String docCodigo;

	@Column(name="DT_MOVTO")
	private LocalDateTime dtMovto;

	@Column(name="FLAG_ATIVO")
	private Integer flagAtivo;

	private String historico;

	@Column(name="LAST_CODUSER")
	private Integer lastCoduser;

	@Column(name="LAST_MOVTO")
	private LocalDateTime lastMovto;

	@Column(name="NOME_USUARIO")
	private String nomeUsuario;

	private String sistema;

	@Column(name="TIPO_OPERACAO")
	private Integer tipoOperacao;
	
	@Column(name="TIPO_REGISTRO")
	private String tipoRegistro;

	private BigInteger utctag;

	@Column(name="VALOR_ANTERIOR")
	private String valorAnterior;

	@Column(name="VALOR_NOVO")
	private String valorNovo;

	public Auditoria() {
	}

	public Integer getCodUsuario() {
		return codUsuario;
	}

	public void setCodUsuario(Integer codUsuario) {
		this.codUsuario = codUsuario;
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

	public Integer getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(Integer recordNo) {
		this.recordNo = recordNo;
	}

	public String getDocCodigo() {
		return docCodigo;
	}

	public void setDocCodigo(String docCodigo) {
		this.docCodigo = docCodigo;
	}

	public LocalDateTime getDtMovto() {
		return dtMovto;
	}

	public void setDtMovto(LocalDateTime dtMovto) {
		this.dtMovto = dtMovto;
	}

	public Integer getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Integer flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public String getHistorico() {
		return historico;
	}

	public void setHistorico(String historico) {
		this.historico = historico;
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

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getSistema() {
		return sistema;
	}

	public void setSistema(String sistema) {
		this.sistema = sistema;
	}

	public Integer getTipoOperacao() {
		return tipoOperacao;
	}

	public void setTipoOperacao(Integer tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}

	public String getTipoRegistro() {
		return tipoRegistro;
	}

	public void setTipoRegistro(String tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}

	public BigInteger getUtctag() {
		return utctag;
	}

	public void setUtctag(BigInteger utctag) {
		this.utctag = utctag;
	}

	public String getValorAnterior() {
		return valorAnterior;
	}

	public void setValorAnterior(String valorAnterior) {
		this.valorAnterior = valorAnterior;
	}

	public String getValorNovo() {
		return valorNovo;
	}

	public void setValorNovo(String valorNovo) {
		this.valorNovo = valorNovo;
	}

	@Override
	public String toString() {
		return "Auditoria [codUsuario=" + codUsuario + ", codemp=" + codemp + ", checkDelete=" + checkDelete
				+ ", recordNo=" + recordNo + ", docCodigo=" + docCodigo + ", dtMovto=" + dtMovto + ", flagAtivo="
				+ flagAtivo + ", historico=" + historico + ", lastCoduser=" + lastCoduser + ", lastMovto=" + lastMovto
				+ ", nomeUsuario=" + nomeUsuario + ", sistema=" + sistema + ", tipoOperacao=" + tipoOperacao
				+ ", tipoRegistro=" + tipoRegistro + ", utctag=" + utctag + ", valorAnterior=" + valorAnterior
				+ ", valorNovo=" + valorNovo + "]";
	}


}