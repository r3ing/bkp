package models.configuracoes;

import java.io.Serializable;
import javax.persistence.*;

import models.compras.Fabricante;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.math.BigInteger;


/**
 * The persistent class for the layout_arquivo_det database table.
 * 
 */
@Entity
@Table(name="LAYOUT_ARQUIVO_DET")
@NamedQuery(name="LayoutArquivoDet.findAll", query="SELECT l FROM LayoutArquivoDet l")
/*@NamedQueries({
	@NamedQuery(name = "LayoutArquivoDetById", query = "SELECT d FROM LayoutArquivoDet d WHERE d.codLayout =:codigo AND d.flagAtivo =1 AND d.codemp IN (:codemp)"),
	@NamedQuery(name = "LayoutArquivoDetAll", query = "SELECT d FROM LayoutArquivoDet d WHERE d.flagAtivo IN (:flag) AND d.codemp IN (:codemp)"),
	@NamedQuery(name = "LayoutArquivoDetLast", query = "SELECT d FROM LayoutArquivoDet d WHERE d.codemp IN (:codemp) AND d.flagAtivo =1 ORDER BY d.codLayout DESC")
	})*/
@IdClass(value = LayoutArquivoDetPK.class)
public class LayoutArquivoDet implements Serializable 
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "CHECK_DELETE")
	private BigInteger checkDelete;
	
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="COD_LAYOUT" , referencedColumnName="CODIGO"),
		@JoinColumn(name="COD_LAYOUT_FK" , referencedColumnName="CHECK_DELETE")		
	})
	private LayoutArquivoCab layoutArquivoCab;	


	@Column(name = "CODEMP")
	private Integer codemp;

	@Column(name="DESC_COLUNA")
	private String descColuna;

	@Column(name="FLAG_ATIVO")
	private Integer flagAtivo;

	@Column(name="LAST_CODUSER")
	private Integer lastCoduser;

	@Column(name="LAST_MOVTO")
	private LocalDateTime lastMovto;

	@Column(name="POSICAO_FILE")
	private String posicaoFile;

	@Column(name="RECORD_NO")
	private Integer recordNo;

	@Column(name="TIPO_DADOS")
	private String tipoDados;

	private BigInteger utctag;

	public LayoutArquivoDet() {
	}

	public BigInteger getCheckDelete() {
		return checkDelete;
	}


	public void setCheckDelete(BigInteger checkDelete) {
		this.checkDelete = checkDelete;
	}


	public LayoutArquivoCab getLayoutArquivoCab() {
		return layoutArquivoCab;
	}


	public void setLayoutArquivoCab(LayoutArquivoCab layoutArquivoCab) {
		this.layoutArquivoCab = layoutArquivoCab;
	}


	public void setFlagAtivo(Integer flagAtivo) {
		this.flagAtivo = flagAtivo;
	}


	public Integer getCodemp() {
		return this.codemp;
	}

	public void setCodemp(Integer codemp) 
	{
		this.codemp = codemp;
	}

	public String getDescColuna() 
	{
		return this.descColuna;
	}

	public void setDescColuna(String descColuna) 
	{
		this.descColuna = descColuna;
	}

	public int getFlagAtivo() 
	{
		return this.flagAtivo;
	}

	public void setFlagAtivo(int flagAtivo) 
	{
		this.flagAtivo = flagAtivo;
	}

	public Integer getLastCoduser() 
	{
		return this.lastCoduser;
	}

	public void setLastCoduser(Integer lastCoduser) 
	{
		this.lastCoduser = lastCoduser;
	}

	public LocalDateTime getLastMovto() 
	{
		return this.lastMovto;
	}

	public void setLastMovto(LocalDateTime lastMovto) 
	{
		this.lastMovto = lastMovto;
	}

	public String getPosicaoFile() 
	{
		return this.posicaoFile;
	}

	public void setPosicaoFile(String posicaoFile) 
	{
		this.posicaoFile = posicaoFile;
	}

	public Integer getRecordNo() 
	{
		return this.recordNo;
	}

	public void setRecordNo(Integer recordNo) 
	{
		this.recordNo = recordNo;
	}

	public String getTipoDados() 
	{
		return this.tipoDados;
	}

	public void setTipoDados(String tipoDados) 
	{
		this.tipoDados = tipoDados;
	}

	public BigInteger getUtctag() 
	{
		return this.utctag;
	}

	public void setUtctag(BigInteger utctag) 
	{
		this.utctag = utctag;
	}

}