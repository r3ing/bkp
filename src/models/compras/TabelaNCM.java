package models.compras;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.math.BigInteger;


/**
 * The persistent class for the tabela_ncm database table.
 * 
 */
@Entity
@Table(name = "TABELA_NCM")
@NamedQueries({
		@NamedQuery(name = "TabelaNCMById", query = "SELECT d FROM TabelaNCM d WHERE d.codigo =:codigo AND d.flagAtivo =1 AND d.codemp IN (:codemp)"),
		@NamedQuery(name = "TabelaNCMByNCM", query = "SELECT d FROM TabelaNCM d WHERE d.codNCM =:codNCM AND d.flagAtivo =1 AND d.codemp IN (:codemp)"),
		@NamedQuery(name = "TabelaNCMAll", query = "SELECT d FROM TabelaNCM d WHERE d.flagAtivo IN (:flag) AND d.codemp IN (:codemp)"),
		@NamedQuery(name = "TabelaNCMLast", query = "SELECT d FROM TabelaNCM d WHERE d.codemp IN (:codemp) AND d.flagAtivo =1 ORDER BY d.codigo DESC")
		})
@IdClass(value = TabelaNCMPK.class)
public class TabelaNCM implements Serializable {
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
	
	@Column(name="COD_CEST")
	private Integer codCest;
	
	@Column(name="COD_NCM")
	private String codNCM;
	
	@Column(name="DESC_COMPLETA")
	private String descCompleta;
	
	@Column(name = "DESCRICAO")
	private String descricao;

	@Column(name="FLAG_ATIVO")
	private Integer flagAtivo;

	@Column(name="LAST_CODUSER")
	private Integer lastCoduser;

	@Column(name="LAST_MOVTO")
	private LocalDateTime lastMovto;

	@Column(name="PERC_CARGATRIBESTADO")
	private BigDecimal percCargaTribEstado;

	@Column(name="PERC_CARGATRIBFEDIMP")
	private BigDecimal percCargatribfedimp;

	@Column(name="PERC_CARGATRIBFEDNAC")
	private BigDecimal percCargatribfednac;

	@Column(name="PERC_CARGATRIBMUNICIPIO")
	private BigDecimal percCargatribmunicipio;

	private BigInteger utctag;

	public TabelaNCM() {
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

	public BigInteger getCheckDelete() {
		return this.checkDelete;
	}

	public void setCheckDelete(BigInteger checkDelete) {
		this.checkDelete = checkDelete;
	}

	public Integer getCodCest() {
		return this.codCest;
	}

	public void setCodCest(Integer codCest) {
		this.codCest = codCest;
	}

	public String getDescCompleta() {
		return this.descCompleta;
	}

	public void setDescCompleta(String descCompleta) {
		this.descCompleta = descCompleta;
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

	public BigDecimal getPercCargaTribEstado() {
		return this.percCargaTribEstado;
	}

	public void setPercCargaTribEstado(BigDecimal percCargaTribEstado) {
		this.percCargaTribEstado = percCargaTribEstado;
	}

	public BigDecimal getPercCargatribfedimp() {
		return this.percCargatribfedimp;
	}

	public void setPercCargatribfedimp(BigDecimal percCargatribfedimp) {
		this.percCargatribfedimp = percCargatribfedimp;
	}

	public BigDecimal getPercCargatribfednac() {
		return this.percCargatribfednac;
	}

	public void setPercCargatribfednac(BigDecimal percCargatribfednac) {
		this.percCargatribfednac = percCargatribfednac;
	}

	public BigDecimal getPercCargatribmunicipio() {
		return this.percCargatribmunicipio;
	}

	public void setPercCargatribmunicipio(BigDecimal percCargatribmunicipio) {
		this.percCargatribmunicipio = percCargatribmunicipio;
	}

	public BigInteger getUtctag() {
		return this.utctag;
	}

	public void setUtctag(BigInteger utctag) {
		this.utctag = utctag;
	}

	public String getCodNCM() {
		return codNCM;
	}

	public void setCodNCM(String codNcm) {
		this.codNCM = codNcm;
	}


}