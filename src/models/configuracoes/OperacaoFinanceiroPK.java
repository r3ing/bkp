package models.configuracoes;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.*;

/**
 * The primary key class for the operacao_financeiro database table.
 * 
 */
@Embeddable
public class OperacaoFinanceiroPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CHECK_DELETE")
	private BigInteger checkDelete;

	//private Integer codemp;

	private Integer codigo;

	public OperacaoFinanceiroPK() {
	}
	public BigInteger getCheckDelete() {
		return this.checkDelete;
	}
	public void setCheckDelete(BigInteger checkDelete) {
		this.checkDelete = checkDelete;
	}
//	public Integer getCodemp() {
//		return this.codemp;
//	}
//	public void setCodemp(Integer codemp) {
//		this.codemp = codemp;
//	}
	public Integer getCodigo() {
		return this.codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof OperacaoFinanceiroPK)) {
			return false;
		}
		OperacaoFinanceiroPK castOther = (OperacaoFinanceiroPK)other;
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