package models.compras;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.*;

import models.configuracoes.DepositoEstoque;

/**
 * The primary key class for the item_fornecedor database table.
 * 
 */
@Embeddable
public class ItemEstoqueDepositoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Integer codemp;
	
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
	@JoinColumn(name = "COD_DEPOSITO_FK", referencedColumnName= "CHECK_DELETE"),
	@JoinColumn(name = "COD_DEPOSITO", referencedColumnName= "CODIGO")
	})
	private DepositoEstoque depositoEstoque;
	
	
	public ItemEstoqueDepositoPK() {
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


	public Integer getCodemp() {
		return codemp;
	}


	public void setCodemp(Integer codemp) {
		this.codemp = codemp;
	}


	public DepositoEstoque getDepositoEstoque() {
		return depositoEstoque;
	}


	public void setDepositoEstoque(DepositoEstoque depositoEstoque) {
		this.depositoEstoque = depositoEstoque;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((checkDelete == null) ? 0 : checkDelete.hashCode());
		result = prime * result + ((codemp == null) ? 0 : codemp.hashCode());
		result = prime * result + ((depositoEstoque == null) ? 0 : depositoEstoque.hashCode());
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
		ItemEstoqueDepositoPK other = (ItemEstoqueDepositoPK) obj;
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
		if (depositoEstoque == null) {
			if (other.depositoEstoque != null)
				return false;
		} else if (!depositoEstoque.equals(other.depositoEstoque))
			return false;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		return true;
	}


	
	
}