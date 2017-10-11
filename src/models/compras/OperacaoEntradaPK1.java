package models.compras;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.*;

/**
 * The primary key class for the unidade database table.
 * 
 */
public class OperacaoEntradaPK1 implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer codigo;
	@Id
	private Integer codemp;

	@Column(name="CHECK_DELETE")
	private BigInteger checkDelete;

	public OperacaoEntradaPK1() {
	}
	public Integer getCodigo() {
		return this.codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public Integer getCodemp() {
		return this.codemp;
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((checkDelete == null) ? 0 : checkDelete.hashCode());
		result = prime * result + ((codemp == null) ? 0 : codemp.hashCode());
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
		OperacaoEntradaPK1 other = (OperacaoEntradaPK1) obj;
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
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
}