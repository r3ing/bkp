package models.compras;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.math.BigInteger;


/**
 * The persistent class for the nf_entrada_fat database table.
 * 
 */
@Entity
@Table(name="nf_entrada_fat")
@NamedQuery(name="NfEntradaFat.findAll", query="SELECT n FROM NfEntradaFat n")
@IdClass(value = NfEntradaFatPK.class)
public class NfEntradaFat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CHECK_DELETE")
	private BigInteger checkDelete;
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
	@JoinColumn(name = "COD_NF_CAB_FX", referencedColumnName= "CHECK_DELETE", insertable=false, updatable = false),
	@JoinColumn(name = "NO_DOCTO", referencedColumnName= "NO_DOCTO", insertable=false, updatable = false)
	})
	private NfEntradaCab nfEntradaCab;
	
	@Id
	@Column(name="NO_PARCELA")
	private Integer noParcela;
	
	private Integer codemp;

	@Column(name="COBR_DUP_DVENC")
	private LocalDateTime cobrDupDvenc;

	@Column(name="COBR_DUP_NDUP")
	private String cobrDupNdup;

	@Column(name="COBR_DUP_VDUP")
	private BigDecimal cobrDupVdup;

	@Column(name="COBR_FAT_NFAT")
	private Integer cobrFatNfat;

	@Column(name="COBR_FAT_VDESC")
	private BigDecimal cobrFatVdesc;

	@Column(name="COBR_FAT_VLIQ")
	private BigDecimal cobrFatVliq;

	@Column(name="COBR_FAT_VORIG")
	private BigDecimal cobrFatVorig;

	@Column(name="FLAG_ATIVO")
	private Integer flagAtivo;

	@Column(name="LAST_CODUSER")
	private Integer lastCoduser;

	@Column(name="LAST_MOVTO")
	private LocalDateTime lastMovto;

	@Column(name="RECORD_NO")
	private Integer recordNo;

	private BigInteger utctag;

	public NfEntradaFat() {
	}

	public BigInteger getCheckDelete() {
		return checkDelete;
	}

	public void setCheckDelete(BigInteger checkDelete) {
		this.checkDelete = checkDelete;
	}

	public NfEntradaCab getNfEntradaCab() {
		return nfEntradaCab;
	}

	public void setNfEntradaCab(NfEntradaCab nfEntradaCab) {
		this.nfEntradaCab = nfEntradaCab;
	}

	public Integer getNoParcela() {
		return noParcela;
	}

	public void setNoParcela(Integer noParcela) {
		this.noParcela = noParcela;
	}
	
	public Integer getCodemp() {
		return codemp;
	}

	public void setCodemp(Integer codemp) {
		this.codemp = codemp;
	}

	public LocalDateTime getCobrDupDvenc() {
		return cobrDupDvenc;
	}

	public void setCobrDupDvenc(LocalDateTime cobrDupDvenc) {
		this.cobrDupDvenc = cobrDupDvenc;
	}

	public String getCobrDupNdup() {
		return cobrDupNdup;
	}

	public void setCobrDupNdup(String cobrDupNdup) {
		this.cobrDupNdup = cobrDupNdup;
	}

	public BigDecimal getCobrDupVdup() {
		return cobrDupVdup;
	}

	public void setCobrDupVdup(BigDecimal cobrDupVdup) {
		this.cobrDupVdup = cobrDupVdup;
	}

	public Integer getCobrFatNfat() {
		return cobrFatNfat;
	}

	public void setCobrFatNfat(Integer cobrFatNfat) {
		this.cobrFatNfat = cobrFatNfat;
	}

	public BigDecimal getCobrFatVdesc() {
		return cobrFatVdesc;
	}

	public void setCobrFatVdesc(BigDecimal cobrFatVdesc) {
		this.cobrFatVdesc = cobrFatVdesc;
	}

	public BigDecimal getCobrFatVliq() {
		return cobrFatVliq;
	}

	public void setCobrFatVliq(BigDecimal cobrFatVliq) {
		this.cobrFatVliq = cobrFatVliq;
	}

	public BigDecimal getCobrFatVorig() {
		return cobrFatVorig;
	}

	public void setCobrFatVorig(BigDecimal cobrFatVorig) {
		this.cobrFatVorig = cobrFatVorig;
	}

	public Integer getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Integer flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public Integer getLastCoduser() {
		return lastCoduser;
	}

	public void setLastCoduser(Integer lastCoduser) {
		this.lastCoduser = lastCoduser;
	}

	public LocalDateTime getLastMovto() {
		return lastMovto;
	}

	public void setLastMovto(LocalDateTime lastMovto) {
		this.lastMovto = lastMovto;
	}

	public Integer getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(Integer recordNo) {
		this.recordNo = recordNo;
	}

	public BigInteger getUtctag() {
		return utctag;
	}

	public void setUtctag(BigInteger utctag) {
		this.utctag = utctag;
	}

}