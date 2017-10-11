package models.configuracoes;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.*;

/**
 * The primary key class for the layout_arquivo_det database table.
 * 
 */
@Embeddable
public class LayoutArquivoDetPK implements Serializable 
{
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	
	private BigInteger checkDelete;
	
	private LayoutArquivoCab layoutArquivoCab;	
	

	public LayoutArquivoDetPK() 
	{
	}


	public LayoutArquivoCab getLayoutArquivoCab() 
	{
		return layoutArquivoCab;
	}



	public void setLayoutArquivoCab(LayoutArquivoCab layoutArquivoCab) 
	{
		this.layoutArquivoCab = layoutArquivoCab;
	}


	public BigInteger getCheckDelete() 
	{
		return checkDelete;
	}


	public void setCheckDelete(BigInteger checkDelete) 
	{
		this.checkDelete = checkDelete;
	}
	
	

	

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((checkDelete == null) ? 0 : checkDelete.hashCode());
		result = prime * result + ((layoutArquivoCab == null) ? 0 : layoutArquivoCab.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LayoutArquivoDetPK other = (LayoutArquivoDetPK) obj;
		if (checkDelete == null) 
		{
			if (other.checkDelete != null)
				return false;
		} 
		else if (!checkDelete.equals(other.checkDelete))
			return false;
		if (layoutArquivoCab == null) 
		{
			if (other.layoutArquivoCab != null)
				return false;
		} else if (!layoutArquivoCab.equals(other.layoutArquivoCab))
			return false;
		return true;
	}	



}