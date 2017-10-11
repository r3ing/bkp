package models.financeiro;

import java.io.Serializable;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.math.BigInteger;


/**
 * The persistent class for the moeda database table.
 * 
 */
@Entity
@Table(name = "MOEDA")
@NamedQueries({
	@NamedQuery(name = "MoedaById", query = "SELECT m FROM Moeda m WHERE m.codigo =:codigo AND m.flagAtivo =1 AND m.codemp IN (:codemp)"),
	@NamedQuery(name = "MoedaAll", query = "SELECT m FROM Moeda m WHERE m.flagAtivo IN (:flag) AND m.codemp IN (:codemp)"),
	@NamedQuery(name = "MoedaLast", query = "SELECT m FROM Moeda m WHERE m.codemp IN (:codemp) AND m.flagAtivo =1 ORDER BY m.codigo DESC")
	})
@IdClass(value = MoedaPK.class)
public class Moeda implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer codigo;
	
	@Id
	@Column(name="CHECK_DELETE")
	private BigInteger checkDelete;

	private Integer codemp;

	@Column(name="DESC_DECIMAL_PLURAL")
	private String descDecimalPlural;

	@Column(name="DESC_DECIMAL_SINGULAR")
	private String descDecimalSingular;

	@Column(name="DESC_INTEIRO_PLURAL")
	private String descInteiroPlural;

	@Column(name="DESC_INTEIRO_SINGULAR")
	private String descInteiroSingular;

	@Column(name="FLAG_ATIVO")
	private Integer flagAtivo;

	@Column(name="FLAG_CONVERSAO")
	private Integer flagConversao;

	@Column(name="LAST_CODUSER")
	private Integer lastCoduser;

	@Column(name="LAST_MOVTO")
	private LocalDateTime lastMovto;

	@Column(name="RECORD_NO")
	private Integer recordNo;

	private String simbolo;

	private BigInteger utctag;

	public Moeda() {
	}


	public String getDescDecimalPlural() {
		return this.descDecimalPlural;
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

	public void setDescDecimalPlural(String descDecimalPlural) {
		this.descDecimalPlural = descDecimalPlural;
	}

	public String getDescDecimalSingular() {
		return this.descDecimalSingular;
	}

	public void setDescDecimalSingular(String descDecimalSingular) {
		this.descDecimalSingular = descDecimalSingular;
	}

	public String getDescInteiroPlural() {
		return this.descInteiroPlural;
	}

	public void setDescInteiroPlural(String descInteiroPlural) {
		this.descInteiroPlural = descInteiroPlural;
	}

	public String getDescInteiroSingular() {
		return this.descInteiroSingular;
	}

	public void setDescInteiroSingular(String descInteiroSingular) {
		this.descInteiroSingular = descInteiroSingular;
	}

	public Integer getFlagAtivo() {
		return this.flagAtivo;
	}

	public void setFlagAtivo(Integer flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public Integer getFlagConversao() {
		return this.flagConversao;
	}

	public void setFlagConversao(Integer flagConversao) {
		this.flagConversao = flagConversao;
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

	public String getSimbolo() {
		return this.simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

	public BigInteger getUtctag() {
		return this.utctag;
	}

	public void setUtctag(BigInteger utctag) {
		this.utctag = utctag;
	}

}