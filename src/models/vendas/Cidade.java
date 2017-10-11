package models.vendas;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.Hibernate;
import java.math.BigInteger;
import java.time.LocalDateTime;
/**
 * The persistent class for the cidade database table.
 * 
 */
@Entity
@Table(name = "CIDADE")
@NamedQueries({
	@NamedQuery(name="CidadeAll", query="SELECT c FROM Cidade c LEFT JOIN FETCH c.uf WHERE c.flagAtivo IN (:flag) AND c.codemp IN (:codemp) "),
	@NamedQuery(name = "CidadeById", query = "SELECT c FROM Cidade c LEFT JOIN FETCH c.uf WHERE c.codigo = :codigo AND c.flagAtivo = 1 AND c.codemp IN (:codemp)"),
	@NamedQuery(name = "CidadeByIBGE", query = "SELECT c FROM Cidade c LEFT JOIN FETCH c.uf WHERE c.codigoIbge = :codigoIbge AND c.flagAtivo = 1 AND c.codemp IN (:codemp)"),
	@NamedQuery(name = "CidadeByName", query = "SELECT c FROM Cidade c LEFT JOIN FETCH c.uf WHERE c.descricao = :descricao AND c.uf.sigla = :uf AND c.flagAtivo = 1 AND c.uf.codemp IN (:codemp)"),
	@NamedQuery(name = "CidadeLast", query = "SELECT c FROM Cidade c LEFT JOIN FETCH c.uf WHERE c.codemp IN (:codemp) AND c.flagAtivo = 1 ORDER BY c.codigo DESC")
	})
@IdClass(value = CidadePK.class)
public class Cidade implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer codigo;

	@Id
	@Column(name="CHECK_DELETE")
	private BigInteger checkDelete;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
	@JoinColumn(name = "COD_UF", referencedColumnName= "CODIGO"),
	@JoinColumn(name = "COD_UF_FK", referencedColumnName= "CHECK_DELETE")
		})
	private Uf uf;
		
	@Column(name="CODEMP")
	private Integer codemp;

	@Column(name = "RECORD_NO")
	private Integer recordNo;
	
	@Column(name="CODIGO_IBGE")
	private String codigoIbge;

	private String descricao;

	@Column(name="FLAG_ATIVO")
	private Integer flagAtivo;

	@Column(name="LAST_CODUSER")
	private Integer lastCoduser;

	@Column(name="LAST_MOVTO")
	private LocalDateTime lastMovto;

	private BigInteger utctag;
	
	@Transient
	private String ufSigla;

	public String getUfSigla() {
		
		 this.ufSigla = "";
		if(Hibernate.isInitialized(uf)) { 
			try {
				this.ufSigla = uf.getSigla();
			} catch(org.hibernate.ObjectNotFoundException one) {
				uf = null;
			}
		}
		return this.ufSigla;
	}
	
	
	
	public Cidade() {
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Uf getUf() {
		 if(!Hibernate.isInitialized(this.uf)) {
	            try {
	                Hibernate.initialize(this.uf);
	            } catch(org.hibernate.ObjectNotFoundException one) {
	            	this.uf = null;
	            }
	        }
	        return this.uf;
	}

	public void setUf(Uf uf) {
		this.uf = uf;
	}

	public BigInteger getCheckDelete() {
		return checkDelete;
	}

	public void setCheckDelete(BigInteger checkDelete) {
		this.checkDelete = checkDelete;
	}

	public Integer getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(Integer recordNo) {
		this.recordNo = recordNo;
	}

	public String getCodigoIbge() {
		return codigoIbge;
	}

	public void setCodigoIbge(String codigoIbge) {
		this.codigoIbge = codigoIbge;
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

	public BigInteger getUtctag() {
		return utctag;
	}

	public void setUtctag(BigInteger utctag) {
		this.utctag = utctag;
	}

	public void setUfSigla(String ufSigla) {
		this.ufSigla = ufSigla;
	}


	public Integer getCodemp() {
		return codemp;
	}


	public void setCodemp(Integer codemp) {
		this.codemp = codemp;
	}


	@Override
	public String toString() {
		return "Cidade [codigo=" + codigo + ", checkDelete=" + checkDelete + ", uf=" + uf + ", codemp=" + codemp
				+ ", recordNo=" + recordNo + ", codigoIbge=" + codigoIbge + ", descricao=" + descricao + ", flagAtivo="
				+ flagAtivo + ", lastCoduser=" + lastCoduser + ", lastMovto=" + lastMovto + ", utctag=" + utctag
				+ ", ufSigla=" + ufSigla + "]";
	}

	
}