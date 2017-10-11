package models.configuracoes;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.math.BigInteger;


/**
 * The persistent class for the tabela_auxiliar_cst_pis database table.
 * 
 */
@Entity
@Table(name="tabela_auxiliar_cst_pis")
@NamedQueries({
	@NamedQuery(name = "TabelaAuxiliarCstPiById", query = "SELECT d FROM TabelaAuxiliarCstPi d WHERE d.cst=:cst AND d.flagAtivo =1 AND d.codemp IN (:codemp)"),
	@NamedQuery(name = "TabelaAuxiliarCstPiAll", query = "SELECT d FROM TabelaAuxiliarCstPi d WHERE d.flagAtivo IN (:flag) AND d.codemp IN (:codemp)"),
	@NamedQuery(name = "TabelaAuxiliarCstPiLast", query = "SELECT d FROM TabelaAuxiliarCstPi d WHERE d.codemp IN (:codemp) AND d.flagAtivo =1 ORDER BY d.cst DESC")
	})
@IdClass(value = TabelaAuxiliarCstPiPK.class)
public class TabelaAuxiliarCstPi implements Serializable 
{
	
	@Id
	@Column(name = "CHECK_DELETE")
	private BigInteger checkDelete;		

	@Id
	@Column(name = "CODEMP")		
	private Integer codemp;

	private String cst;	

	@Column(name="DESCRICAO", nullable=false, length=255)
	private String descricao;

	@Column(name="FLAG_ATIVO", nullable=false)
	private Integer flagAtivo;

	@Column(name="LAST_CODUSER", nullable=false)
	private Integer lastCoduser;

	@Column(name="LAST_MOVTO", nullable=false)
	private LocalDateTime lastMovto;

	@Column(name="RECORD_NO")
	private Integer recordNo;

	@Column(nullable=false)
	private BigInteger utctag;

	@Column(nullable=false)
	private Integer versao;

	public TabelaAuxiliarCstPi() {
	}

	/*public TabelaAuxiliarCstPiPK getId() {
		return this.id;
	}

	public void setId(TabelaAuxiliarCstPiPK id) {
		this.id = id;
	}*/

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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

	public Integer getRecordNo() {
		return this.recordNo;
	}

	public void setRecordNo(Integer recordNo) {
		this.recordNo = recordNo;
	}

	public BigInteger getUtctag() {
		return this.utctag;
	}

	public void setUtctag(BigInteger utctag) {
		this.utctag = utctag;
	}

	public Integer getVersao() {
		return this.versao;
	}

	public void setVersao(Integer versao) {
		this.versao = versao;
	}
	
	
	public BigInteger getCheckDelete() {
		return checkDelete;
	}

	public void setCheckDelete(BigInteger checkDelete) {
		this.checkDelete = checkDelete;
	}

	private static final long serialVersionUID = 1L;
	
	public Integer getCodemp() {
		return codemp;
	}

	public void setCodemp(Integer codemp) {
		this.codemp = codemp;
	}

	public String getCst() 
	{
		return cst;
	}

	public void setCst(String cst) 
	{
		this.cst = cst;
	}	

}