package models.compras;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.List;
import java.math.BigInteger;


/**
 * The persistent class for the item_fornecedor database table.
 * 
 */
@Entity
@Table(name="ITEM_FORNECEDOR")
@NamedQueries({
	@NamedQuery(name = "AllByFornecedor", query = "SELECT i FROM ItemFornecedor i "
			+ "LEFT JOIN FETCH i.fornecedor "
			+ "LEFT JOIN FETCH i.item t "
			+ "LEFT JOIN FETCH t.itemCodBars cb "
			+ "LEFT JOIN FETCH t.itemValors vl "
			+ "LEFT JOIN FETCH vl.tributacao tb "			
			+ "LEFT JOIN FETCH t.fabricante "
			+ "LEFT JOIN FETCH t.grupo "
			+ "LEFT JOIN FETCH t.subGrupo "
			+ "LEFT JOIN FETCH t.ncm "
			+ "LEFT JOIN FETCH t.unidade "
			+ "LEFT JOIN FETCH t.unidadeEmb "
			+ "LEFT JOIN FETCH t.moeda "
			+ "WHERE i.fornecedor.codigo = :codForn AND i.flagAtivo = 1  AND (cb is null OR cb.flagCodbarPrincipal = 1)")
})
@IdClass(value = ItemFornecedorPK.class)
public class ItemFornecedor implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer codemp;

	@Id
	@Column(name="CHECK_DELETE")
	private BigInteger checkDelete;

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
	@JoinColumn(name = "COD_ITEM_FK", referencedColumnName= "CHECK_DELETE"),
	@JoinColumn(name = "COD_ITEM", referencedColumnName= "CODIGO")
	})
	private Item item;
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
	@JoinColumn(name = "COD_FORNECEDOR_FK", referencedColumnName= "CHECK_DELETE"),
	@JoinColumn(name = "COD_FORNECEDOR", referencedColumnName= "CODIGO")
	})
	private Fornecedor fornecedor;
	
	@Id
	@Column(name="COD_ITEM_FORNECEDOR")
	private Integer codItemFornecedor;

	@Column(name="FLAG_ATIVO")
	private Integer flagAtivo;

	@Column(name="LAST_CODUSER")
	private Integer lastCoduser;

	@Column(name="LAST_MOVTO")
	private LocalDateTime lastMovto;

	@Column(name="RECORD_NO")
	private Integer recordNo;

	private BigInteger utctag;	
	
	@Transient
	private String fornecedorDescricao;
	
	public String getFornecedorDescricao() {	
		this.fornecedorDescricao = "";
		if(Hibernate.isInitialized(fornecedor)) { 
			try {
				this.fornecedorDescricao = this.fornecedor.getRazao();
			} catch(org.hibernate.ObjectNotFoundException one) {
				this.fornecedor = null;
			}
		}
		return this.fornecedorDescricao;
	}
	

	public ItemFornecedor() {
	}

	public Integer getCodemp() {
		return codemp;
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

	public Integer getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Integer flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public Integer getLastCoduser() {
		return lastCoduser;
	}

	public void setLastCoduser(Integer lastCoduser) {
		this.lastCoduser = lastCoduser;
	}

	public LocalDateTime getLastMovto() {
		return lastMovto;
	}

	public void setLastMovto(LocalDateTime lastMovto) {
		this.lastMovto = lastMovto;
	}

	public Integer getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(Integer recordNo) {
		this.recordNo = recordNo;
	}

	public BigInteger getUtctag() {
		return utctag;
	}

	public void setUtctag(BigInteger utctag) {
		this.utctag = utctag;
	}

	@Override
	public String toString() {
		return "ItemFornecedor [codemp=" + codemp + ", checkDelete=" + checkDelete + ", item=" + item + ", fornecedor="
				+ fornecedor + ", codItemFornecedor=" + codItemFornecedor + ", flagAtivo=" + flagAtivo
				+ ", lastCoduser=" + lastCoduser + ", lastMovto=" + lastMovto + ", recordNo=" + recordNo + ", utctag="
				+ utctag + "]";
	}
	
}