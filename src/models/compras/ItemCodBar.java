package models.compras;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.*;

import org.hibernate.annotations.DynamicUpdate;

import javafx.beans.property.IntegerProperty;

import java.time.LocalDateTime;

/**
 * The persistent class for the item_valor database table.
 * 
 */
@Entity
@Table(name="ITEM_CODBAR")
@NamedQueries({
	@NamedQuery(name = "CodBarrasItem", query = "SELECT d FROM ItemCodBar d WHERE d.item.codigo = :codItem AND d.flagAtivo = 1"),
	@NamedQuery(name = "CodBarById", query = "SELECT d FROM ItemCodBar d WHERE d.codBarras  = :codBarras AND d.flagAtivo = 1 AND d.codemp IN (:codemp)")
	//@NamedQuery(name = "GrupoAll", query = "SELECT d FROM Grupo d WHERE d.flagAtivo IN (:flag) AND d.codemp IN (:codemp)"),
	//@NamedQuery(name = "GrupoLast", query = "SELECT d FROM Grupo d WHERE d.codemp IN (:codemp) AND d.flagAtivo =1 ORDER BY d.codigo DESC")
	})
@DynamicUpdate
@IdClass(value = ItemCodBarPK.class)
public class ItemCodBar implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@Column(name="CHECK_DELETE")
	private BigInteger checkDelete;
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
	@JoinColumn(name = "COD_ITEM_FK", referencedColumnName= "CHECK_DELETE", insertable=false, updatable = false),
	@JoinColumn(name = "COD_ITEM", referencedColumnName= "CODIGO", insertable=false, updatable = false)
	})
	private Item item;
	
	//@Id
	private Integer codemp;
	
	@Id
	@Column(name="COD_BARRAS")
	private String codBarras;
	
//	@Column(name="COD_ITEM")
//	private Integer codItem;
	
	@Column(name="RECORD_NO")
	private BigInteger recordNo;
	
	@Column(name="FLAG_ATIVO")
	private Integer flagAtivo;

	@Column(name="LAST_CODUSER")
	private Integer lastCoduser;

	@Column(name="LAST_MOVTO")
	private LocalDateTime lastMovto;

	private BigInteger utctag;
	
	@Column(name="QTD_EMBALAGEM")
	private BigDecimal qtdEmbalagem = new BigDecimal(1);
	
	@Column(name="FLAG_CODBAR_PRINCIPAL")
	private Integer flagCodbarPrincipal;
	
	public ItemCodBar() {
	}
	
	public BigInteger getCheckDelete() {
		return checkDelete;
	}
	
	public void setCheckDelete(BigInteger checkDelete) {
		this.checkDelete = checkDelete;
	}

	public Integer getCodemp() {
		return codemp;
	}

	public void setCodemp(Integer codemp) {
		this.codemp = codemp;
	}

	public Integer getFlagAtivo() {
		return this.flagAtivo;
	}

	public void setFlagAtivo(Integer flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public Integer getLastCoduser() {
		return this.lastCoduser;
	}

	public void setLastCoduser(Integer lastCoduser) {
		this.lastCoduser = lastCoduser;
	}

	public LocalDateTime getLastMovto() {
		return this.lastMovto;
	}

	public void setLastMovto(LocalDateTime lastMovto) {
		this.lastMovto = lastMovto;
	}

	public BigInteger getRecordNo() {
		return this.recordNo;
	}

	public void setRecordNo(BigInteger recordNo) {
		this.recordNo = recordNo;
	}
	
	public BigInteger getUtctag() {
		return this.utctag;
	}

	public void setUtctag(BigInteger utctag) {
		this.utctag = utctag;
	}

	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}


	public String getCodBarras() {
		return codBarras;
	}


	public BigDecimal getQtdEmbalagem() {
		return qtdEmbalagem;
	}


	public void setQtdEmbalagem(BigDecimal qtdEmbalagem) {
		this.qtdEmbalagem = qtdEmbalagem;
	}


	public void setCodBarras(String codBarras) {
		this.codBarras = codBarras;
	}


	public Integer getFlagCodbarPrincipal() {
		return flagCodbarPrincipal;
	}


	public void setFlagCodbarPrincipal(Integer flagCodbarPrincipal) {
		this.flagCodbarPrincipal = flagCodbarPrincipal;
	}

	@Override
	public ItemCodBar clone() {
	   try {
		return (ItemCodBar) super.clone();
	} catch (CloneNotSupportedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codBarras == null) ? 0 : codBarras.hashCode());
		result = prime * result + ((codemp == null) ? 0 : codemp.hashCode());
		result = prime * result + ((flagAtivo == null) ? 0 : flagAtivo.hashCode());
		result = prime * result + ((flagCodbarPrincipal == null) ? 0 : flagCodbarPrincipal.hashCode());
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		result = prime * result + ((lastCoduser == null) ? 0 : lastCoduser.hashCode());
		result = prime * result + ((lastMovto == null) ? 0 : lastMovto.hashCode());
		result = prime * result + ((qtdEmbalagem == null) ? 0 : qtdEmbalagem.hashCode());
		result = prime * result + ((recordNo == null) ? 0 : recordNo.hashCode());
		result = prime * result + ((utctag == null) ? 0 : utctag.hashCode());
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
		ItemCodBar other = (ItemCodBar) obj;
		if (codBarras == null) {
			if (other.codBarras != null)
				return false;
		} else if (!codBarras.equals(other.codBarras))
			return false;
		if (codemp == null) {
			if (other.codemp != null)
				return false;
		} else if (!codemp.equals(other.codemp))
			return false;
		if (flagAtivo == null) {
			if (other.flagAtivo != null)
				return false;
		} else if (!flagAtivo.equals(other.flagAtivo))
			return false;
		if (flagCodbarPrincipal == null) {
			if (other.flagCodbarPrincipal != null)
				return false;
		} else if (!flagCodbarPrincipal.equals(other.flagCodbarPrincipal))
			return false;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		if (lastCoduser == null) {
			if (other.lastCoduser != null)
				return false;
		} else if (!lastCoduser.equals(other.lastCoduser))
			return false;
		if (lastMovto == null) {
			if (other.lastMovto != null)
				return false;
		} else if (!lastMovto.equals(other.lastMovto))
			return false;
		if (qtdEmbalagem == null) {
			if (other.qtdEmbalagem != null)
				return false;
		} else if (!qtdEmbalagem.equals(other.qtdEmbalagem))
			return false;
		if (recordNo == null) {
			if (other.recordNo != null)
				return false;
		} else if (!recordNo.equals(other.recordNo))
			return false;
		if (utctag == null) {
			if (other.utctag != null)
				return false;
		} else if (!utctag.equals(other.utctag))
			return false;
		return true;
	}
	
}