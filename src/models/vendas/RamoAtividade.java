package models.vendas;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.math.BigInteger;


/**
 * The persistent class for the ramo_atividade database table.
 * 
 */
@Entity
@Table(name="ramo_atividade")
//@NamedQuery(name="RamoAtividade.findAll", query="SELECT r FROM RamoAtividade r")
@NamedQueries({
	@NamedQuery(name="RamoAtividadeAll", query="SELECT r FROM RamoAtividade r WHERE r.flagAtivo IN (:flag) AND r.codemp IN (:codemp)"),
	@NamedQuery(name = "RamoAtividadeById", query = "SELECT r FROM RamoAtividade r WHERE r.codigo = :codigo AND r.flagAtivo = 1 AND r.codemp IN (:codemp)"),
	@NamedQuery(name = "RamoAtividadeLast", query = "SELECT r FROM RamoAtividade r WHERE r.codemp IN (:codemp) AND r.flagAtivo = 1 ORDER BY r.codigo DESC")
	})
@IdClass(value = RamoAtividadePK.class)
public class RamoAtividade implements Serializable {
	private static final long serialVersionUID = 1L;

//	@EmbeddedId
//	private RamoAtividadePK id;
	@Id
	private Integer codigo;
	
	@Id
	@Column(name="CHECK_DELETE")
	private BigInteger checkDelete;

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
	

	public RamoAtividade() {
	}
	
	@OneToMany(mappedBy="ramoAtividade", fetch = FetchType.LAZY)
	private List<Cliente> cliente;

//	public RamoAtividadePK getId() {
//		return this.id;
//	}
//
//	public void setId(RamoAtividadePK id) {
//		this.id = id;
//	}
	
	public String getDescricao() {
		return this.descricao;
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

	public Integer getCodemp() {
		return codemp;
	}

	public void setCodemp(Integer codemp) {
		this.codemp = codemp;
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