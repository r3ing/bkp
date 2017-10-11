package models.vendas;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.*;

/**
 * The primary key class for the regiao database table.
 * 
 */
@Embeddable
public class RegiaoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	
	private Integer codigo;

	@Column(name="CHECK_DELETE")
	private BigInteger checkDelete;


	public RegiaoPK() {
	}
	public BigInteger getCheckDelete() {
		return this.checkDelete;
	}
	public void setCheckDelete(BigInteger checkDelete) {
		this.checkDelete = checkDelete;
	}
	
	public Integer getCodigo() {
		return this.codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RegiaoPK)) {
			return false;
		}
		RegiaoPK castOther = (RegiaoPK)other;
		return 
			this.checkDelete.equals(castOther.checkDelete)
		
			&& (this.codigo == castOther.codigo);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.checkDelete.hashCode();
	
		hash = hash * prime + this.codigo;
		
		return hash;
	}
}