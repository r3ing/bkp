package models.configuracoes;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.*;

/**
 * The primary key class for the tabela_auxiliar_cst_icms database table.
 * 
 */
@Embeddable
public class TabelaAuxiliarCstIcmPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	//@Column(name="CST")
	private Integer codemp;//private Integer cst;

	//@Column(name="CHECK_DELETE")
	private BigInteger checkDelete;	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((checkDelete == null) ? 0 : checkDelete.hashCode());
		result = prime * result + ((codemp == null) ? 0 : codemp.hashCode());
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
		TabelaAuxiliarCstIcmPK other = (TabelaAuxiliarCstIcmPK) obj;
		if (checkDelete == null) {
			if (other.checkDelete != null)
				return false;
		} else if (!checkDelete.equals(other.checkDelete))
			return false;
		if (codemp == null) {
			if (other.codemp != null)
				return false;
		} else if (!codemp.equals(other.codemp))
			return false;
		return true;
	}


	public Integer getCodemp() {
		return codemp;
	}


	public void setCodemp(Integer codemp) {
		this.codemp = codemp;
	}


	public BigInteger getCheckDelete() {
		return checkDelete;
	}


	public void setCheckDelete(BigInteger checkDelete) {
		this.checkDelete = checkDelete;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public TabelaAuxiliarCstIcmPK() {
	}
}