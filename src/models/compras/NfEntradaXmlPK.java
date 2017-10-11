package models.compras;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.*;

/**
 * The primary key class for the nf_entrada_xml database table.
 * 
 */
@Embeddable
public class NfEntradaXmlPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CHECK_DELETE")
	private BigInteger checkDelete;

//	private int codemp;

//	@Column(name="NO_DOCTO")
//	private int noDocto;
	
//	@OneToOne(optional=true, fetch = FetchType.LAZY)
////	@JoinColumns({
////		@JoinColumn(name = "COD_NF_CAB_FK", referencedColumnName= "CHECK_DELETE"),
////		@JoinColumn(name = "NO_DOCTO", referencedColumnName= "NO_DOCTO")
////	})
//	@PrimaryKeyJoinColumns({
//		 @PrimaryKeyJoinColumn(name = "COD_NF_CAB_FK", referencedColumnName = "CHECK_DELETE"),  
//		 @PrimaryKeyJoinColumn(name = "NO_DOCTO", referencedColumnName = "NO_DOCTO")
//	})
//	private NfEntradaCab nfEntradaCab;
//	

	public NfEntradaXmlPK() {
	}
	
	
	public BigInteger getCheckDelete() {
		return this.checkDelete;
	}
	public void setCheckDelete(BigInteger checkDelete) {
		this.checkDelete = checkDelete;
	}
//	public NfEntradaCab getNfEntradaCab() {
//		return nfEntradaCab;
//	}
//	public void setNfEntradaCab(NfEntradaCab nfEntradaCab) {
//		this.nfEntradaCab = nfEntradaCab;
//	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((checkDelete == null) ? 0 : checkDelete.hashCode());
//		result = prime * result + ((nfEntradaCab == null) ? 0 : nfEntradaCab.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NfEntradaXmlPK other = (NfEntradaXmlPK) obj;
		if (checkDelete == null) {
			if (other.checkDelete != null)
				return false;
		} else if (!checkDelete.equals(other.checkDelete))
			return false;
//		if (nfEntradaCab == null) {
//			if (other.nfEntradaCab != null)
//				return false;
//		} else if (!nfEntradaCab.equals(other.nfEntradaCab))
//			return false;
		return true;
	}

}