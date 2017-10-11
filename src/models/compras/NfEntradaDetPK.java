package models.compras;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.*;

/**
 * The primary key class for the nf_entrada_det database table.
 * 
 */
@Embeddable
public class NfEntradaDetPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CHECK_DELETE")
	private BigInteger checkDelete;	
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumns({
	@JoinColumn(name = "COD_NF_CAB_FK", referencedColumnName= "CHECK_DELETE"),
	@JoinColumn(name = "NO_DOCTO", referencedColumnName= "NO_DOCTO")
	})
	private NfEntradaCab nfEntradaCab;

//	private int codemp;

//	@Column(name="EPTUS_COD_ITEM")
//	private int eptusCodItem;
//
//	@Column(name="EPTUS_COD_ITEM_FK")
//	private String eptusCodItemFk;

	public NfEntradaDetPK() {
	}
	public BigInteger getCheckDelete() {
		return this.checkDelete;
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
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NfEntradaDetPK other = (NfEntradaDetPK) obj;
		if (checkDelete == null) {
			if (other.checkDelete != null)
				return false;
		} else if (!checkDelete.equals(other.checkDelete))
			return false;
		if (nfEntradaCab == null) {
			if (other.nfEntradaCab != null)
				return false;
		} else if (!nfEntradaCab.equals(other.nfEntradaCab))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((checkDelete == null) ? 0 : checkDelete.hashCode());
		result = prime * result + ((nfEntradaCab == null) ? 0 : nfEntradaCab.hashCode());
		return result;
	}
}