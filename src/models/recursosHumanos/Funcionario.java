package models.recursosHumanos;

import java.io.Serializable;
import javax.persistence.*;

import models.financeiro.ContasReceber;
import models.vendas.Cliente;
import models.vendas.ConvenioPK;

import java.time.LocalDateTime;
import java.util.List;
import java.math.BigInteger;


/**
 * The persistent class for the funcionario database table.
 * 
 */
@Entity
//@NamedQuery(name="Funcionario.findAll", query="SELECT f FROM Funcionario f")
@NamedQueries({
	@NamedQuery(name = "FuncionarioById", query = "SELECT f FROM Funcionario f WHERE f.codigo =:codigo AND f.flagAtivo =1 AND f.codemp IN (:codemp)"),
	@NamedQuery(name = "FuncionarioAll", query = "SELECT f FROM Funcionario f WHERE f.flagAtivo IN (:flag) AND f.codemp IN (:codemp)"),
	@NamedQuery(name = "FuncionarioLast", query = "SELECT f FROM Funcionario f WHERE f.codemp IN (:codemp) AND f.flagAtivo =1 ORDER BY f.codigo DESC"),
	@NamedQuery(name = "VendedorById", query = "SELECT f FROM Funcionario f WHERE f.codigo =:codigo AND f.flagAtivo =1 AND f.flagTipoFuncionario=0 AND f.codemp IN (:codemp)")
	})
@IdClass(value = FuncionarioPK.class)
public class Funcionario implements Serializable {
	private static final long serialVersionUID = 1L;

//	@EmbeddedId
//	private FuncionarioPK id;
	
	@Id
	private Integer codigo;

	@Id
	@Column(name="CHECK_DELETE")
	private BigInteger checkDelete;

	private Integer codemp;

	private String descricao;	
	
	@Column(name="FLAG_TIPO_FUNCAO")
	private Integer flagTipoFuncionario;

	@Column(name="FLAG_ATIVO")
	private Integer flagAtivo;

	@Column(name="LAST_CODUSER")
	private Integer lastCoduser;

	@Column(name="LAST_MOVTO")
	private LocalDateTime lastMovto;

	@Column(name="RECORD_NO")
	private Integer recordNo;

	private BigInteger utctag;

	public Funcionario() {
	}
	
	@OneToMany(mappedBy="funcionario", fetch = FetchType.LAZY)
	private List<Cliente> cliente;
	
	@OneToMany(mappedBy="vendedor", fetch = FetchType.LAZY)
	private List<ContasReceber> contasReceber;

//	public FuncionarioPK getId() {
//		return this.id;
//	}
//
//	public void setId(FuncionarioPK id) {
//		this.id = id;
//	}

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
		return this.codemp;
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
	
	

	public Integer getFlagTipoFuncionario() {
		return flagTipoFuncionario;
	}



	public void setFlagTipoFuncionario(Integer flagTipoFuncionario) {
		this.flagTipoFuncionario = flagTipoFuncionario;
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