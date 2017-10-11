package models.vendas;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;
import java.math.BigInteger;


/**
 * The persistent class for the rota database table.
 * 
 */
@Entity
@Table(name = "ROTA")
@NamedQueries({
		@NamedQuery(name = "RotaById", query = "SELECT r FROM Rota r WHERE r.codigo =:codigo AND r.flagAtivo =1 AND r.codemp IN (:codemp)"),
		@NamedQuery(name = "RotaAll", query = "SELECT r FROM Rota r WHERE r.flagAtivo IN (:flag) AND r.codemp IN (:codemp)"),
		@NamedQuery(name = "RotaLast", query = "SELECT r FROM Rota r WHERE r.codemp IN (:codemp) AND r.flagAtivo =1 ORDER BY r.codigo DESC")
		})
@IdClass(value = RotaPK.class)
public class Rota implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer codigo;

	
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

	@Column(name="TAXA_DESCONTO")
	private BigDecimal taxaDesconto;

	private BigInteger utctag;

	public Rota() {
	}
	
	@OneToMany(mappedBy="rota", fetch = FetchType.LAZY)
	private List<Cliente> cliente;

	public BigInteger getCheckDelete() {
		return this.checkDelete;
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

	public Integer getCodigo() {
		return this.codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
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

	public BigDecimal getTaxaDesconto() {
		return this.taxaDesconto;
	}

	public void setTaxaDesconto(BigDecimal taxaDesconto) {
		this.taxaDesconto = taxaDesconto;
	}

	public BigInteger getUtctag() {
		return this.utctag;
	}

	public void setUtctag(BigInteger utctag) {
		this.utctag = utctag;
	}

}