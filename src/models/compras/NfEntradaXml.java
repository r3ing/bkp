package models.compras;

import java.io.Serializable;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.math.BigInteger;


/**
 * The persistent class for the nf_entrada_xml database table.
 * 
 */
@Entity
@Table(name="nf_entrada_xml")
@NamedQuery(name="NfEntradaXml.findAll", query="SELECT n FROM NfEntradaXml n")
@IdClass(value=NfEntradaXmlPK.class)
public class NfEntradaXml implements Serializable {
	private static final long serialVersionUID = 1L;

//	@EmbeddedId
//	private NfEntradaXmlPK id;
	@Id
	@Column(name="CHECK_DELETE")
	private BigInteger checkDelete;
	
//	@Id
	@OneToOne
	@JoinColumns({
		@JoinColumn(name = "COD_NF_CAB_FK", referencedColumnName= "CHECK_DELETE"),
		@JoinColumn(name = "NO_DOCTO", referencedColumnName= "NO_DOCTO")
	})
	
	private NfEntradaCab nfEntradaCab;

	private int codemp;

	@Column(name="FLAG_ATIVO")
	private int flagAtivo;

	@Column(name="LAST_CODUSER")
	private int lastCoduser;

	@Column(name="LAST_MOVTO")
	private LocalDateTime lastMovto;

	@Column(name="RECORD_NO")
	private int recordNo;

	private BigInteger utctag;

//	@Lob
	private String xml;
	
	

//	public NfEntradaXml() {
//	}
//
//	public NfEntradaXmlPK getId() {
//		return this.id;
//	}
	
	public int getFlagAtivo() {
		return this.flagAtivo;
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

	public int getCodemp() {
		return codemp;
	}

	public void setCodemp(int codemp) {
		this.codemp = codemp;
	}

	public void setFlagAtivo(int flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public int getLastCoduser() {
		return this.lastCoduser;
	}

	public void setLastCoduser(int lastCoduser) {
		this.lastCoduser = lastCoduser;
	}

	public LocalDateTime getLastMovto() {
		return this.lastMovto;
	}

	public void setLastMovto(LocalDateTime lastMovto) {
		this.lastMovto = lastMovto;
	}

	public int getRecordNo() {
		return this.recordNo;
	}

	public void setRecordNo(int recordNo) {
		this.recordNo = recordNo;
	}

	public BigInteger getUtctag() {
		return this.utctag;
	}

	public void setUtctag(BigInteger utctag) {
		this.utctag = utctag;
	}

	public String getXml() {
		return this.xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

}