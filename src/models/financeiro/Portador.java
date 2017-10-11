package models.financeiro;

import java.io.Serializable;
import javax.persistence.*;

import models.configuracoes.OperacaoEntrada;
import models.vendas.Cliente;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.math.BigInteger;


/**
 * The persistent class for the portador database table.
 * 
 */
@Entity
@Table(name = "Portador")
//@NamedQuery(name="Portador.findAll", query="SELECT p FROM Portador p")
@NamedQueries({
	@NamedQuery(name = "PortadorById", query = "SELECT p FROM Portador p WHERE p.codigo =:codigo AND p.flagAtivo =1 AND p.codemp IN (:codemp)"),
	@NamedQuery(name = "PortadorAll", query = "SELECT p FROM Portador p WHERE p.flagAtivo IN (:flag) AND p.codemp IN (:codemp)"),
	@NamedQuery(name = "PortadorLast", query = "SELECT p FROM Portador p WHERE p.codemp IN (:codemp) AND p.flagAtivo =1 ORDER BY p.codigo DESC")
	})
@IdClass(value = PortadorPK.class)
public class Portador implements Serializable {
	private static final long serialVersionUID = 1L;

//	@EmbeddedId
//	private PortadorPK id;
	
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

	public Portador() {
	}
	
	@OneToMany(mappedBy="portador", fetch = FetchType.LAZY)
	private List<Cliente> cliente;
	
	@OneToMany(mappedBy="portador", fetch = FetchType.LAZY)
	private List<ContasReceber> contasReceber;

//	public PortadorPK getId() {
//		return this.id;
//	}
//
//	public void setId(PortadorPK id) {
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