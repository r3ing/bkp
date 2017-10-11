package models.compras;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigInteger;

/**
 * The persistent class for the lc_servicos database table.
 * 
 */
@Entity
@Table(name="lc_servicos")
@NamedQuery(name="LcServico.findAll", query="SELECT l FROM LcServico l")
@IdClass(value = LcServicoPK.class)
public class LcServico implements Serializable {
	private static final long serialVersionUID = 1L;

	// ----  CHAVE PRIMARIA ---- //
	@Id
	@Column(name = "CODIGO")
	private Integer codigo;
		
	@Id
	@Column(name = "CHECK_DELETE")
	private BigInteger checkDelete;
	// ----  CHAVE PRIMARIA ---- //
		
	// --- ATRIBUTOS -------//
	
	@Column(name = "CODEMP")
	private Integer codemp;
	
	@Column(name="COD_LCNFSE")
	private String codLcnfse;

	@Lob
	@Column(name="DESC_COMPLETA")
	private String descCompleta;

	private String descricao;

	@Column(name="FLAG_ATIVO")
	private Integer flagAtivo;

	@Column(name="LAST_CODUSER")
	private Integer lastCoduser;

	@Column(name="LAST_MOVTO")
	private Timestamp lastMovto;

	private BigInteger utctag;

	public LcServico() {
	}

	
	public BigInteger getCheckDelete() {
		return this.checkDelete;
	}

	public void setCheckDelete(BigInteger checkDelete) {
		this.checkDelete = checkDelete;
	}

	public String getCodLcnfse() {
		return this.codLcnfse;
	}

	public void setCodLcnfse(String codLcnfse) {
		this.codLcnfse = codLcnfse;
	}

	public String getDescCompleta() {
		return this.descCompleta;
	}

	public void setDescCompleta(String descCompleta) {
		this.descCompleta = descCompleta;
	}

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

	public Timestamp getLastMovto() {
		return this.lastMovto;
	}

	public void setLastMovto(Timestamp lastMovto) {
		this.lastMovto = lastMovto;
	}

	public BigInteger getUtctag() {
		return this.utctag;
	}

	public void setUtctag(BigInteger utctag) {
		this.utctag = utctag;
	}

}