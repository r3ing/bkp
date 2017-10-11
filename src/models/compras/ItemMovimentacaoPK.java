package models.compras;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.*;

/**
 * The primary key class for the item_movimentacao database table.
 * 
 */
@Embeddable
public class ItemMovimentacaoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CHECK_DELETE")
	private BigInteger checkDelete;

//	private int codemp;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
	@JoinColumn(name = "COD_ITEM_FK", referencedColumnName= "CHECK_DELETE"),
	@JoinColumn(name = "COD_ITEM", referencedColumnName= "CODIGO")
	})
	private Item item;


	public ItemMovimentacaoPK() {
	}
	public BigInteger getCheckDelete() {
		return this.checkDelete;
	}
	public void setCheckDelete(BigInteger checkDelete) {
		this.checkDelete = checkDelete;
	}	
	
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((checkDelete == null) ? 0 : checkDelete.hashCode());
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
		ItemMovimentacaoPK other = (ItemMovimentacaoPK) obj;
		if (checkDelete == null) {
			if (other.checkDelete != null)
				return false;
		} else if (!checkDelete.equals(other.checkDelete))
			return false;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		return true;
	}
	
}