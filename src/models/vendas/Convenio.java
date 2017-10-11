package models.vendas;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.math.BigInteger;


/**
 * The persistent class for the convenio database table.
 * 
 */
@Entity
//@NamedQuery(name="Convenio.findAll", query="SELECT c FROM Convenio c")
@NamedQueries({
	@NamedQuery(name = "ConvenioById", query = "SELECT c FROM Convenio c WHERE c.codigo =:codigo AND c.flagAtivo =1 AND c.codemp IN (:codemp)"),
	@NamedQuery(name = "ConvenioAll", query = "SELECT c FROM Convenio c WHERE c.flagAtivo IN (:flag) AND c.codemp IN (:codemp)"),
	@NamedQuery(name = "ConvenioLast", query = "SELECT c FROM Convenio c WHERE c.codemp IN (:codemp) AND c.flagAtivo =1 ORDER BY c.codigo DESC")
	})
@IdClass(value = ConvenioPK.class)
public class Convenio implements Serializable {
	private static final long serialVersionUID = 1L;
//
//	@EmbeddedId
//	private ConvenioPK id;

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

	public Convenio() {
	}
	
	@OneToMany(mappedBy="convenio", fetch = FetchType.LAZY)
	private List<Cliente> cliente;

//	public ConvenioPK getId() {
//		return this.id;
//	}
//
//	public void setId(ConvenioPK id) {
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