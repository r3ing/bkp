package models.compras;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The persistent class for the TRIBUTACAO database table. 
 */
@Entity
@Table(name = "TRIBUTACAO")
@NamedQueries({
		@NamedQuery(name = "TributacaoById", query = "SELECT d FROM Tributacao d WHERE d.codigo =:codigo AND d.flagAtivo = 1 AND d.codemp IN (:codemp)"),
		@NamedQuery(name = "TributacaoAll", query = "SELECT d FROM Tributacao d WHERE d.flagAtivo IN (:flag) AND d.codemp IN (:codemp)"),
		@NamedQuery(name = "TributacaoLast", query = "SELECT d FROM Tributacao d WHERE d.codemp IN (:codemp) AND d.flagAtivo = 1 ORDER BY d.codigo DESC")
		})
@IdClass(value = TributacaoPK.class)
public class Tributacao implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// ----  CHAVE PRIMARIA ---- //
	@Id
	@Column(name = "CODIGO")
	private Integer codigo;
	
	@Id
	@Column(name = "CHECK_DELETE")
	private BigInteger checkDelete;
	// ----  CHAVE PRIMARIA ---- //
	
	// --- ATRIBUTOS -------//
	
	@Column(name = "CODEMP")
	private Integer codemp;
	
	@Column(name = "RECORD_NO")
	private Integer recordNo;
	
	@Column(name = "DESCRICAO")
	private String descricao;

	@Column(name = "FLAG_ATIVO")
	private Integer flagAtivo;

	@Column(name = "LAST_CODUSER")
	private Integer lastCoduser;
	
	@Column(name = "LAST_MOVTO")
	private LocalDateTime lastMovto;
	
	@Column(name = "UTCTAG")
	private BigInteger utctag;

	//CONSTRUTOR PADRÃO DA CLASSE
	public Tributacao() {
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

	public void setRecordNo(Integer recordNo) {
		this.recordNo = recordNo;
	}

}