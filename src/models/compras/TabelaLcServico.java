package models.compras;

import java.io.Serializable;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.math.BigInteger;


/**
 * The persistent class for the tabela_lc_servicos database table.
 * 
 */
@Entity
@Table(name="TABELA_LC_SERVICOS")
@NamedQueries({
	@NamedQuery(name = "TabelaLcServicoById", query = "SELECT d FROM TabelaLcServico d WHERE d.codigo =:codigo AND d.flagAtivo = 1 AND d.codemp IN (:codemp)"),
	@NamedQuery(name = "TabelaLcServicoAll", query = "SELECT d FROM TabelaLcServico d WHERE d.flagAtivo IN (:flag) AND d.codemp IN (:codemp)"),
	@NamedQuery(name = "TabelaLcServicoLast", query = "SELECT d FROM TabelaLcServico d WHERE d.codemp IN (:codemp) AND d.flagAtivo = 1 ORDER BY d.codigo DESC")
	})
@IdClass(value = TabelaLcServicoPK.class)
public class TabelaLcServico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer codigo;
	
	@Id
	@Column(name="CHECK_DELETE")
	private BigInteger checkDelete;
	
	private Integer codemp;
	
	@Column(name="COD_LCNFSE")
	private String codLcnfse;

	@Column(name="DESC_COMPLETA")
	private String descCompleta;

	private String descricao;

	@Column(name="FLAG_ATIVO")
	private Integer flagAtivo;

	@Column(name="LAST_CODUSER")
	private Integer lastCoduser;

	@Column(name="LAST_MOVTO")
	private LocalDateTime lastMovto;

	@Column(name="RECORD_NO")
	private Integer recordNo;

	private BigInteger utctag;

	public TabelaLcServico() {
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

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getCodLcnfse() {
		return codLcnfse;
	}

	public void setCodLcnfse(String codLcnfse) {
		this.codLcnfse = codLcnfse;
	}

	public String getDescCompleta() {
		return descCompleta;
	}

	public void setDescCompleta(String descCompleta) {
		this.descCompleta = descCompleta;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
		return "TabelaLcServico [checkDelete=" + checkDelete + ", codemp=" + codemp + ", codigo=" + codigo
				+ ", codLcnfse=" + codLcnfse + ", descCompleta=" + descCompleta + ", descricao=" + descricao
				+ ", flagAtivo=" + flagAtivo + ", lastCoduser=" + lastCoduser + ", lastMovto=" + lastMovto
				+ ", recordNo=" + recordNo + ", utctag=" + utctag + "]";
	}

	
}