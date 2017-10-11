package models.financeiro;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.*;

import models.vendas.Cliente;

/**
 * The primary key class for the contas_receber database table.
 * 
 */
@Embeddable
public class ContasReceberPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CHECK_DELETE")
	private BigInteger checkDelete;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = "COD_CLIENTE_FK", referencedColumnName= "CHECK_DELETE"),
		@JoinColumn(name = "COD_CLIENTE", referencedColumnName= "CODIGO")
		})
	private Cliente cliente;

	@Column(name="NO_DOCTO")
	private Integer noDocto;

	public ContasReceberPK() {
	}
	
	public BigInteger getCheckDelete() {
		return this.checkDelete;
	}
	
	public void setCheckDelete(BigInteger checkDelete) {
		this.checkDelete = checkDelete;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Integer getNoDocto() {
		return this.noDocto;
	}
	public void setNoDocto(Integer noDocto) {
		this.noDocto = noDocto;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((checkDelete == null) ? 0 : checkDelete.hashCode());
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((noDocto == null) ? 0 : noDocto.hashCode());
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
		ContasReceberPK other = (ContasReceberPK) obj;
		if (checkDelete == null) {
			if (other.checkDelete != null)
				return false;
		} else if (!checkDelete.equals(other.checkDelete))
			return false;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (noDocto == null) {
			if (other.noDocto != null)
				return false;
		} else if (!noDocto.equals(other.noDocto))
			return false;
		return true;
	}

	


}