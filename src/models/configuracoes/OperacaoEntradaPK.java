package models.configuracoes;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.*;

/**
 * The primary key class for the operacao_entrada database table.
 * 
 */
@Embeddable
public class OperacaoEntradaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CHECK_DELETE")
	private BigInteger checkDelete;

//	private int codemp;

	private int codigo;

	public OperacaoEntradaPK() {
	}
	public BigInteger getCheckDelete() {
		return this.checkDelete;
	}
	public void setCheckDelete(BigInteger checkDelete) {
		this.checkDelete = checkDelete;
	}
//	public int getCodemp() {
//		return this.codemp;
//	}
//	public void setCodemp(int codemp) {
//		this.codemp = codemp;
//	}
	public int getCodigo() {
		return this.codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof OperacaoEntradaPK)) {
			return false;
		}
		OperacaoEntradaPK castOther = (OperacaoEntradaPK)other;
		return 
			this.checkDelete.equals(castOther.checkDelete)
//			&& (this.codemp == castOther.codemp)
			&& (this.codigo == castOther.codigo);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.checkDelete.hashCode();
//		hash = hash * prime + this.codemp;
		hash = hash * prime + this.codigo;
		
		return hash;
	}
}