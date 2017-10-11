package models.configuracoes;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.*;

/**
 * The primary key class for the auditoria database table.
 * 
 */

public class AuditoriaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CHECK_DELETE")
	private BigInteger checkDelete;
	
	@Id
	@Column(name="COD_USUARIO")
	private Integer codUsuario;
	
	@Id
	private Integer codemp;
	
	
	public AuditoriaPK() {
	}


	public BigInteger getCheckDelete() {
		return checkDelete;
	}


	public void setCheckDelete(BigInteger checkDelete) {
		this.checkDelete = checkDelete;
	}


	public Integer getCodUsuario() {
		return codUsuario;
	}


	public void setCodUsuario(Integer codUsuario) {
		this.codUsuario = codUsuario;
	}


	public Integer getCodemp() {
		return codemp;
	}


	public void setCodemp(Integer codemp) {
		this.codemp = codemp;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((checkDelete == null) ? 0 : checkDelete.hashCode());
		result = prime * result + ((codUsuario == null) ? 0 : codUsuario.hashCode());
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
		AuditoriaPK other = (AuditoriaPK) obj;
		if (checkDelete == null) {
			if (other.checkDelete != null)
				return false;
		} else if (!checkDelete.equals(other.checkDelete))
			return false;
		if (codUsuario == null) {
			if (other.codUsuario != null)
				return false;
		} else if (!codUsuario.equals(other.codUsuario))
			return false;
		if (codemp == null) {
			if (other.codemp != null)
				return false;
		} else if (!codemp.equals(other.codemp))
			return false;
		return true;
	}
	
	
}