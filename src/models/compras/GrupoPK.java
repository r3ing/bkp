  package models.compras;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.*;

/**
 * The primary key class for the grupo database table.
 * 
 */
@Embeddable
public class GrupoPK implements Serializable {
		
	private static final long serialVersionUID = 1L;
	
	private Integer codigo;
	
	@Column(name="CHECK_DELETE")
	private BigInteger checkDelete;

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

	public GrupoPK() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((checkDelete == null) ? 0 : checkDelete.hashCode());
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
		GrupoPK other = (GrupoPK) obj;
		if (checkDelete == null) {
			if (other.checkDelete != null)
				return false;
		} else if (!checkDelete.equals(other.checkDelete))
			return false;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	
}