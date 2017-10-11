package models.compras;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * The persistent class for the subGrupo database table.
 * 
 * 
 */
@Entity
@Table(name = "SUBGRUPO")
@NamedQueries({
		@NamedQuery(name = "SubGrupoById", query = "SELECT d FROM SubGrupo d WHERE d.codigo =:codigo AND d.flagAtivo =1 AND d.codemp IN (:codemp)"),
		@NamedQuery(name = "SubGrupoAll", query = "SELECT d FROM SubGrupo d WHERE d.flagAtivo IN (:flag) AND d.codemp IN (:codemp)"),
		@NamedQuery(name = "SubGrupoLast", query = "SELECT d FROM SubGrupo d WHERE d.codemp IN (:codemp) AND d.flagAtivo =1 ORDER BY d.codigo DESC")
		})
@IdClass(value = SubGrupoPK.class)
public class SubGrupo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// ----  CHAVE PRIMARIA ---- //
	@Id
	private Integer codigo;
	
	@Id
	@Column(name = "CHECK_DELETE")
	private BigInteger checkDelete;
	
	// ----  CHAVE PRIMARIA ---- //
	
	// --- ATRIBUTOS -------//
	
	private Integer codemp;
	
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

	//CONSTRUTOR PADRÃO DA CLASSE
	public SubGrupo() {
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Integer flagAtivo) {
		this.flagAtivo = flagAtivo;
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

	public BigInteger getUtctag() {
		return utctag;
	}

	public void setUtctag(BigInteger utctag) {
		this.utctag = utctag;
	}

}