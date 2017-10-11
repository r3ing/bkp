package models.compras;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * The persistent class for the BoxLocalEstoque database table. 
 */
@Entity
@Table(name = "BOX_LOCAL_ESTOQUE")
@NamedQueries({
		@NamedQuery(name = "BoxLocalEstoqueById", query = "SELECT d FROM BoxLocalEstoque d WHERE d.codigo =:codigo AND d.flagAtivo = 1 AND d.codemp IN (:codemp)"),
		@NamedQuery(name = "BoxLocalEstoqueAll", query = "SELECT d FROM BoxLocalEstoque d WHERE d.flagAtivo IN (:flag) AND d.codemp IN (:codemp)"),
		@NamedQuery(name = "BoxLocalEstoqueLast", query = "SELECT d FROM BoxLocalEstoque d WHERE d.codemp IN (:codemp) AND d.flagAtivo = 1 ORDER BY d.codigo DESC")
		})
@IdClass(value = BoxLocalEstoquePK.class)
public class BoxLocalEstoque implements Serializable {
	
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
	public BoxLocalEstoque() {
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

	 @Override
	public String toString() {
		return "BoxLocalEstoque [codigo=" + codigo + ", codemp=" + codemp + ", recordNo=" + recordNo + ", checkDelete="
				+ checkDelete + ", descricao=" + descricao + ", flagAtivo=" + flagAtivo + ", lastCoduser=" + lastCoduser
				+ ", lastMovto=" + lastMovto + ", utctag=" + utctag + "]";
	}

}