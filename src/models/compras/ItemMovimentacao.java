package models.compras;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.math.BigInteger;


/**
 * The persistent class for the item_movimentacao database table.
 * 
 */
@Entity
@Table(name="item_movimentacao")
@NamedQuery(name="ItemMovimentacao.findAll", query="SELECT i FROM ItemMovimentacao i")
@IdClass(value=ItemMovimentacaoPK.class)
public class ItemMovimentacao implements Serializable {
	private static final long serialVersionUID = 1L;

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
	
	private Integer codemp;

	@Column(name="DT_MOVIMENTO")
	private LocalDateTime dtMovimento;

	@Column(name="FLAG_ATIVO")
	private Integer flagAtivo;

	@Column(name="FLAG_MODULO")
	private Integer flagModulo;

	@Column(name="LAST_CODUSER")
	private Integer lastCoduser;

	@Column(name="LAST_MOVTO")
	private LocalDateTime lastMovto;

	@Column(name="QTD_MOVIMENTADO")
	private BigDecimal qtdMovimentado;

	@Column(name="RECORD_NO")
	private Integer recordNo;
	
	private String historico;

	@Column(name="TIPO_MOVIMENTACAO")
	private String tipoMovimentacao;

	private BigInteger utctag;

	@Column(name="VLR_BRUTO")
	private BigDecimal vlrBruto;
	
	@Column(name="NO_DOCTO")
	private Integer noDocto;

	@Column(name="NO_DOCTO_FK")
	private BigInteger noDoctoFk;	

	public ItemMovimentacao() {
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

	public Integer getCodemp() {
		return codemp;
	}

	public void setCodemp(Integer codemp) {
		this.codemp = codemp;
	}

	public LocalDateTime getDtMovimento() {
		return dtMovimento;
	}

	public void setDtMovimento(LocalDateTime dtMovimento) {
		this.dtMovimento = dtMovimento;
	}

	public Integer getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(int flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public Integer getFlagModulo() {
		return flagModulo;
	}

	public void setFlagModulo(Integer flagModulo) {
		this.flagModulo = flagModulo;
	}

	public Integer getLastCoduser() {
		return lastCoduser;
	}

	public void setLastCoduser(int lastCoduser) {
		this.lastCoduser = lastCoduser;
	}

	public LocalDateTime getLastMovto() {
		return lastMovto;
	}

	public void setLastMovto(LocalDateTime lastMovto) {
		this.lastMovto = lastMovto;
	}

	public BigDecimal getQtdMovimentado() {
		return qtdMovimentado;
	}

	public void setQtdMovimentado(BigDecimal qtdMovimentado) {
		this.qtdMovimentado = qtdMovimentado;
	}

	public Integer getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(int recordNo) {
		this.recordNo = recordNo;
	}

	public String getHistorico() {
		return historico;
	}

	public void setHistorico(String historico) {
		this.historico = historico;
	}

	public String getTipoMovimentacao() {
		return tipoMovimentacao;
	}

	public void setTipoMovimentacao(String tipoMovimentacao) {
		this.tipoMovimentacao = tipoMovimentacao;
	}

	public BigInteger getUtctag() {
		return utctag;
	}

	public void setUtctag(BigInteger utctag) {
		this.utctag = utctag;
	}

	public BigDecimal getVlrBruto() {
		return vlrBruto;
	}

	public void setVlrBruto(BigDecimal vlrBruto) {
		this.vlrBruto = vlrBruto;
	}

	public Integer getNoDocto() {
		return noDocto;
	}

	public void setNoDocto(int noDocto) {
		this.noDocto = noDocto;
	}

	public BigInteger getNoDoctoFk() {
		return noDoctoFk;
	}

	public void setNoDoctoFk(BigInteger noDoctoFk) {
		this.noDoctoFk = noDoctoFk;
	}	

}