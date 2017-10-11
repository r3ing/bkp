package models.compras;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.*;

/**
 * The primary key class for the nf_entrada_fat database table.
 * 
 */
@Embeddable
public class NfEntradaFatPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CHECK_DELETE")
	private BigInteger checkDelete;

//	private int codemp;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumns({
	@JoinColumn(name = "COD_NF_CAB_FX", referencedColumnName= "CHECK_DELETE"),
	@JoinColumn(name = "NO_DOCTO", referencedColumnName= "NO_DOCTO")
	})
	private NfEntradaCab nfEntradaCab;

	@Column(name="NO_PARCELA")
	private Integer noParcela;

	public NfEntradaFatPK() {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((checkDelete == null) ? 0 : checkDelete.hashCode());
		result = prime * result + ((nfEntradaCab == null) ? 0 : nfEntradaCab.hashCode());
		result = prime * result + ((noParcela == null) ? 0 : noParcela.hashCode());
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
		NfEntradaFatPK other = (NfEntradaFatPK) obj;
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
		if (noParcela == null) {
			if (other.noParcela != null)
				return false;
		} else if (!noParcela.equals(other.noParcela))
			return false;
		return true;
	}
	
}