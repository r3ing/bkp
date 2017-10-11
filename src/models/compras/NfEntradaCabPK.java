package models.compras;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.*;

/**
 * The primary key class for the nf_entrada_cab database table.
 * 
 */
@Embeddable
public class NfEntradaCabPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CHECK_DELETE")
	private BigInteger checkDelete;

//	private int codemp;

	@Column(name="NO_DOCTO")
	private Integer noDocto;

//	@Column(name="COD_FORNECEDOR")
//	private int codFornecedor;
//
//	@Column(name="COD_FORNECEDOR_FK")
//	private String codFornecedorFk;
	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumns({
//	@JoinColumn(name = "COD_FORNECEDOR_FK", referencedColumnName= "CHECK_DELETE"),
//	@JoinColumn(name = "COD_FORNECEDOR", referencedColumnName= "CODIGO")
//	})
//	private Fornecedor fornecedor;

	public NfEntradaCabPK() {
	}
	
	public BigInteger getCheckDelete() {
		return checkDelete;
	}

	public void setCheckDelete(BigInteger checkDelete) {
		this.checkDelete = checkDelete;
	}

	public Integer getNoDocto() {
		return noDocto;
	}

	public void setNoDocto(Integer noDocto) {
		this.noDocto = noDocto;
	}

//	public Fornecedor getFornecedor() {
//		return fornecedor;
//	}
//
//	public void setFornecedor(Fornecedor fornecedor) {
//		this.fornecedor = fornecedor;
//	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((checkDelete == null) ? 0 : checkDelete.hashCode());
//		result = prime * result + ((fornecedor == null) ? 0 : fornecedor.hashCode());
		result = prime * result + noDocto;
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
		NfEntradaCabPK other = (NfEntradaCabPK) obj;
		if (checkDelete == null) {
			if (other.checkDelete != null)
				return false;
		} else if (!checkDelete.equals(other.checkDelete))
			return false;
//		if (fornecedor == null) {
//			if (other.fornecedor != null)
//				return false;
//		} else if (!fornecedor.equals(other.fornecedor))
//			return false;
		if (noDocto != other.noDocto)
			return false;
		return true;
	}
}