package models.compras;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.Hibernate;

import models.configuracoes.DepositoEstoque;

import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * The persistent class for the item_fornecedor database table.
 * 
 */
@Entity
@Table(name="ITEM_ESTOQUE_DEPOSITO")
//@NamedQueries({
//	@NamedQuery(name = "AllByFornecedor", query = "SELECT i FROM ItemFornecedor i "
//			+ "LEFT JOIN FETCH i.fornecedor "
//			+ "LEFT JOIN FETCH i.item t "
//			+ "LEFT JOIN FETCH t.itemCodBars cb "
//			+ "LEFT JOIN FETCH t.itemValors vl "
//			+ "LEFT JOIN FETCH vl.tributacao tb "			
//			+ "LEFT JOIN FETCH t.fabricante "
//			+ "LEFT JOIN FETCH t.grupo "
//			+ "LEFT JOIN FETCH t.subGrupo "
//			+ "LEFT JOIN FETCH t.ncm "
//			+ "LEFT JOIN FETCH t.unidade "
//			+ "LEFT JOIN FETCH t.unidadeEmb "
//			+ "LEFT JOIN FETCH t.moeda "
//			+ "WHERE i.fornecedor.codigo = :codForn AND i.flagAtivo = 1  AND (cb is null OR cb.flagCodbarPrincipal = 1)")
//})
@IdClass(value = ItemEstoqueDepositoPK.class)
public class ItemEstoqueDeposito implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
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
	@JoinColumn(name = "COD_DEPOSITO_FK", referencedColumnName= "CHECK_DELETE"),
	@JoinColumn(name = "COD_DEPOSITO", referencedColumnName= "CODIGO")
	})
	private DepositoEstoque depositoEstoque;
	
	@Column(name="FLAG_ATIVO")
	private Integer flagAtivo;

	@Column(name="LAST_CODUSER")
	private Integer lastCoduser;

	@Column(name="LAST_MOVTO")
	private LocalDateTime lastMovto;

	@Column(name="RECORD_NO")
	private Integer recordNo;

	private BigInteger utctag;	
	
	@Column(name="QTD_ATUAL")
	private BigDecimal qtdAtual;

	@Column(name="QTD_DISPONIVEL")
	private BigDecimal qtdDisponivel;

	@Column(name="QTD_RESERVADA")
	private BigDecimal qtdReservada;

	@Column(name="QTD_CCUSTO1")
	private BigDecimal qtdCcusto1;

	@Column(name="QTD_CCUSTO2")
	private BigDecimal qtdCcusto2;

	
	public ItemEstoqueDeposito() {
	}
	
	@Transient
	private String depositoDescricao;
	
	public String getDepositoDescricao() {	
		this.depositoDescricao = "";
		if(Hibernate.isInitialized(depositoEstoque)) { 
			try {
				this.depositoDescricao = this.depositoEstoque.getDescricao();
			} catch(org.hibernate.ObjectNotFoundException one) {
				this.depositoEstoque = null;
			}
		}
		return this.depositoDescricao;
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

	public DepositoEstoque getDepositoEstoque() {
		return depositoEstoque;
	}

	public void setDepositoEstoque(DepositoEstoque depositoEstoque) {
		this.depositoEstoque = depositoEstoque;
	}

	public BigDecimal getQtdAtual() {
		return qtdAtual;
	}

	public void setQtdAtual(BigDecimal qtdAtual) {
		this.qtdAtual = qtdAtual;
	}

	public BigDecimal getQtdDisponivel() {
		return qtdDisponivel;
	}

	public void setQtdDisponivel(BigDecimal qtdDisponivel) {
		this.qtdDisponivel = qtdDisponivel;
	}

	public BigDecimal getQtdReservada() {
		return qtdReservada;
	}

	public void setQtdReservada(BigDecimal qtdReservada) {
		this.qtdReservada = qtdReservada;
	}

	public BigDecimal getQtdCcusto1() {
		return qtdCcusto1;
	}

	public void setQtdCcusto1(BigDecimal qtdCcusto1) {
		this.qtdCcusto1 = qtdCcusto1;
	}

	public BigDecimal getQtdCcusto2() {
		return qtdCcusto2;
	}

	public void setQtdCcusto2(BigDecimal qtdCcusto2) {
		this.qtdCcusto2 = qtdCcusto2;
	}

	
}