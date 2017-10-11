package models.compras;

import java.io.Serializable;
import javax.persistence.*;

import models.vendas.Cliente;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.math.BigInteger;


/**
 * The persistent class for the segmento database table.
 * 
 */
@Entity
//@NamedQuery(name="Segmento.findAll", query="SELECT s FROM Segmento s")
@NamedQueries({
	@NamedQuery(name = "SegmentoById", query = "SELECT s FROM Segmento s WHERE s.codigo =:codigo AND s.flagAtivo = 1 AND s.codemp IN (:codemp)"),
	@NamedQuery(name = "SegmentoAll", query = "SELECT s FROM Segmento s WHERE s.flagAtivo IN (:flag) AND s.codemp IN (:codemp)"),
	@NamedQuery(name = "SegmentoLast", query = "SELECT s FROM Segmento s WHERE s.codemp IN (:codemp) AND s.flagAtivo = 1 ORDER BY s.codigo DESC")
	})
@IdClass(value = SegmentoPK.class)
public class Segmento implements Serializable {
	private static final long serialVersionUID = 1L;

//	@EmbeddedId
//	private SegmentoPK id;
	@Id
	@Column(name="CHECK_DELETE")
	private BigInteger checkDelete;

	@Id
	private Integer codigo;

	private Integer codemp;

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

	public Segmento() {
	}
	
	@OneToMany(mappedBy="segmento", fetch = FetchType.LAZY)
	private List<Cliente> cliente;

//	public SegmentoPK getId() {
//		return this.id;
//	}
//
//	public void setId(SegmentoPK id) {
//		this.id = id;
//	}

	
	public Integer getCodemp() {
		return this.codemp;
	}

	public BigInteger getCheckDelete() {
		return checkDelete;
	}

	public void setCheckDelete(BigInteger checkDelete) {
		this.checkDelete = checkDelete;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
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