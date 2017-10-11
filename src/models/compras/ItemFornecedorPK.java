package models.compras;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.*;

/**
 * The primary key class for the item_fornecedor database table.
 * 
 */
@Embeddable
public class ItemFornecedorPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CHECK_DELETE")
	private BigInteger checkDelete;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
	@JoinColumn(name = "COD_ITEM_FK", referencedColumnName= "CHECK_DELETE"),
	@JoinColumn(name = "COD_ITEM", referencedColumnName= "CODIGO")
	})
	private Item item;
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
	@JoinColumn(name = "COD_FORNECEDOR_FK", referencedColumnName= "CHECK_DELETE"),
	@JoinColumn(name = "COD_FORNECEDOR", referencedColumnName= "CODIGO")
	})
	private Fornecedor fornecedor;
	
	@Column(name="COD_ITEM_FORNECEDOR")
	private Integer codItemFornecedor;


	public ItemFornecedorPK() {
	}


	public BigInteger getCheckDelete() {
		return checkDelete;
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


	public Fornecedor getFornecedor() {
		return fornecedor;
	}


	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}


	public Integer getCodItemFornecedor() {
		return codItemFornecedor;
	}


	public void setCodItemFornecedor(Integer codItemFornecedor) {
		this.codItemFornecedor = codItemFornecedor;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((checkDelete == null) ? 0 : checkDelete.hashCode());
		result = prime * result + ((codItemFornecedor == null) ? 0 : codItemFornecedor.hashCode());
		result = prime * result + ((fornecedor == null) ? 0 : fornecedor.hashCode());
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
		ItemFornecedorPK other = (ItemFornecedorPK) obj;
		if (checkDelete == null) {
			if (other.checkDelete != null)
				return false;
		} else if (!checkDelete.equals(other.checkDelete))
			return false;
		if (codItemFornecedor == null) {
			if (other.codItemFornecedor != null)
				return false;
		} else if (!codItemFornecedor.equals(other.codItemFornecedor))
			return false;
		if (fornecedor == null) {
			if (other.fornecedor != null)
				return false;
		} else if (!fornecedor.equals(other.fornecedor))
			return false;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		return true;
	}

	

	
	
}