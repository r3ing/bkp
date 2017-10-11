package models.compras;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.*;

/**
 * The primary key class for the item_valor database table.
 * 
 */
@Embeddable
public class ItemCodBarPK implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Item item;
	
	private String codBarras;
	
	private BigInteger checkDelete;
	
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public String getCodBarras() {
		return codBarras;
	}


	public void setCodBarras(String codBarras) {
		this.codBarras = codBarras;
	}

	public BigInteger getCheckDelete() {
		return checkDelete;
	}

	public void setCheckDelete(BigInteger checkDelete) {
		this.checkDelete = checkDelete;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((checkDelete == null) ? 0 : checkDelete.hashCode());
		result = prime * result + ((codBarras == null) ? 0 : codBarras.hashCode());
		result = prime * result + ((item == null) ? 0 : item.hashCode());
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
		ItemCodBarPK other = (ItemCodBarPK) obj;
		if (checkDelete == null) {
			if (other.checkDelete != null)
				return false;
		} else if (!checkDelete.equals(other.checkDelete))
			return false;
		if (codBarras == null) {
			if (other.codBarras != null)
				return false;
		} else if (!codBarras.equals(other.codBarras))
			return false;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		return true;
	}

	

	
	
}