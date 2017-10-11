package models.financeiro;

import java.io.Serializable;
import javax.persistence.*;

import models.vendas.Cliente;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.math.BigInteger;


/**
 * The persistent class for the secao database table.
 * 
 */
@Entity
//@NamedQuery(name="Secao.findAll", query="SELECT s FROM Secao s")
@Table(name = "Secao")
@NamedQueries({
		@NamedQuery(name = "SecaoById", query = "SELECT s FROM Secao s WHERE s.codigo =:codigo AND s.flagAtivo =1 AND s.codemp IN (:codemp)"),
		@NamedQuery(name = "SecaoAll", query = "SELECT s FROM Secao s WHERE s.flagAtivo IN (:flag) AND s.codemp IN (:codemp)"),
		@NamedQuery(name = "SecaoLast", query = "SELECT s FROM Secao s WHERE s.codemp IN (:codemp) AND s.flagAtivo =1 ORDER BY s.codigo DESC")
		})
@IdClass(value = SecaoPK.class)
public class Secao implements Serializable {
	private static final long serialVersionUID = 1L;

//	@EmbeddedId
//	private SecaoPK id;
	
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

	public Secao() {
	}

	@OneToMany(mappedBy="secao", fetch = FetchType.LAZY)
	private List<Cliente> cliente;
	
	@OneToMany(mappedBy="secao", fetch = FetchType.LAZY)
	private List<ContasReceber> contasReceber;
	

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