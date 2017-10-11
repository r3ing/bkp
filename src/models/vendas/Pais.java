package models.vendas;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;
import java.math.BigInteger;
import java.time.LocalDateTime;
/**
 * The persistent class for the pais database table.
 * 
 */
@Entity
@Table(name="pais")
//@NamedQuery(name="PaisAll", query="SELECT p FROM Pais p WHERE p.flagAtivo IN (:flag) AND p.codemp IN (:codemp)")
@NamedQueries({
	@NamedQuery(name="PaisAll", query="SELECT p FROM Pais p WHERE p.flagAtivo IN (:flag) AND p.codemp IN (:codemp)"),
	@NamedQuery(name = "PaisById", query = "SELECT p FROM Pais p WHERE p.codigo = :codigo AND p.flagAtivo = 1 AND p.codemp IN (:codemp)"),
	@NamedQuery(name = "PaisLast", query = "SELECT p FROM Pais p WHERE p.codemp IN (:codemp) AND p.flagAtivo = 1 ORDER BY p.codigo DESC")
	})
@IdClass(value = PaisPK.class)
public class Pais implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer codigo;
	
	@Id
	@Column(name="CHECK_DELETE")
	private BigInteger checkDelete;
	
	private Integer codemp;
	
	@OneToMany(mappedBy="pais" , fetch = FetchType.LAZY)
	private List<Uf> ufs;
	
	@Column(name="CODIGO_BACEN")
	private String codigoBacen;

	@Column(name="CODIGO_SISCOMEX")
	private String codigoSiscomex;

	private String descricao;

	@Column(name="FLAG_ATIVO")
	private Integer flagAtivo;

	@Column(name="LAST_CODUSER")
	private Integer lastCoduser;

	@Column(name="LAST_MOVTO")
	private LocalDateTime lastMovto;

	@Column(name="RECORD_NO")
	private Integer recordNo;

	private BigInteger utctag;

	public Pais() {
	}


	public Integer getCodemp() {
		return codemp;
	}


	public void setCodemp(Integer codemp) {
		this.codemp = codemp;
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


	public List<Uf> getUfs() {
		return ufs;
	}


	public void setUfs(List<Uf> ufs) {
		this.ufs = ufs;
	}


	public String getCodigoBacen() {
		return this.codigoBacen;
	}

	public void setCodigoBacen(String codigoBacen) {
		this.codigoBacen = codigoBacen;
	}

	public String getCodigoSiscomex() {
		return this.codigoSiscomex;
	}

	public void setCodigoSiscomex(String codigoSiscomex) {
		this.codigoSiscomex = codigoSiscomex;
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

	@Override
	public String toString() {
		return "Pais [codigo=" + codigo + ", checkDelete=" + checkDelete + ", codemp=" + codemp + ", ufs=" + ufs
				+ ", codigoBacen=" + codigoBacen + ", codigoSiscomex=" + codigoSiscomex + ", descricao=" + descricao
				+ ", flagAtivo=" + flagAtivo + ", lastCoduser=" + lastCoduser + ", lastMovto=" + lastMovto
				+ ", recordNo=" + recordNo + ", utctag=" + utctag + "]";
	}
	
}