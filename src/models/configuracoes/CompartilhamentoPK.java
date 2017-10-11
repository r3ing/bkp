package models.configuracoes;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.*;

/**
 * The primary key class for the compartilhamento database table.
 * 
 */
@Embeddable
public class CompartilhamentoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int codemp;

//	@Column(name="RECORD_NO")
//	private int recordNo;
	@Column(name= "CHECK_DELETE")
	private BigInteger checkDelete;

	@Column(name="MOD_FUNCAO")
	private String modFuncao;

	public CompartilhamentoPK() {
	}
	public int getCodemp() {
		return this.codemp;
	}
	public void setCodemp(int codemp) {
		this.codemp = codemp;
	}
//	public int getRecordNo() {
//		return this.recordNo;
//	}
//	public void setRecordNo(int recordNo) {
//		this.recordNo = recordNo;
//	}
	
	public BigInteger getCheckDelete() {
		return checkDelete;
	}
	public void setCheckDelete(BigInteger checkDelete) {
		this.checkDelete = checkDelete;
	}
	
	public String getModFuncao() {
		return this.modFuncao;
	}
	
	public void setModFuncao(String modFuncao) {
		this.modFuncao = modFuncao;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((checkDelete == null) ? 0 : checkDelete.hashCode());
		result = prime * result + codemp;
		result = prime * result + ((modFuncao == null) ? 0 : modFuncao.hashCode());
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
		CompartilhamentoPK other = (CompartilhamentoPK) obj;
		if (checkDelete == null) {
			if (other.checkDelete != null)
				return false;
		} else if (!checkDelete.equals(other.checkDelete))
			return false;
		if (codemp != other.codemp)
			return false;
		if (modFuncao == null) {
			if (other.modFuncao != null)
				return false;
		} else if (!modFuncao.equals(other.modFuncao))
			return false;
		return true;
	}

	
}