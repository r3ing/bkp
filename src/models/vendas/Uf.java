package models.vendas;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;
import javax.persistence.*;
import org.hibernate.Hibernate;
import java.util.List;
/**
 * The persistent class for the uf database table.
 * 
 */
@Entity
@Table(name = "UF")
@NamedQueries({
	@NamedQuery(name="UfAll", query="SELECT u FROM Uf u LEFT JOIN FETCH u.pais  WHERE u.flagAtivo IN (:flag) AND u.codemp IN (:codemp)"),
	@NamedQuery(name = "UfById", query = "SELECT u FROM Uf u LEFT JOIN FETCH u.pais WHERE u.codigo = :codigo AND u.flagAtivo = 1 AND u.codemp IN (:codemp)"),
	@NamedQuery(name = "UfLast", query = "SELECT u FROM Uf u LEFT JOIN FETCH u.pais WHERE u.codemp IN (:codemp) AND u.flagAtivo = 1 ORDER BY u.codigo DESC")
	})
@IdClass(value = UfPK.class)
public class Uf implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer codigo;

	@Id
	@Column(name="CHECK_DELETE")
	private BigInteger checkDelete;
	
	private Integer codemp;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
	@JoinColumn(name = "COD_PAIS", referencedColumnName= "CODIGO"),
	@JoinColumn(name = "COD_PAIS_FK", referencedColumnName= "CHECK_DELETE"),
	})
	private Pais pais;

	@OneToMany(mappedBy="uf", fetch = FetchType.LAZY)
	private List<Cidade> cidades;

	@Column(name = "RECORD_NO")
	private Integer recordNo;
	
	@Column(name="CODIGO_IBGE")
	private Integer codigoIbge;

	private String descricao;

	@Column(name="FLAG_ATIVO")
	private Integer flagAtivo;

	@Column(name="LAST_CODUSER")
	private Integer lastCoduser;

	@Column(name="LAST_MOVTO")
	private LocalDateTime lastMovto;

	private String sigla;

	private BigInteger utctag;
	
	@Transient
	private String paisDescricao;
	
	public String getPaisDescricao() {
		this.paisDescricao  = "";
		if(Hibernate.isInitialized(pais)) { 
			try {
				this.paisDescricao = pais.getDescricao();
			} catch(org.hibernate.ObjectNotFoundException one) {
				pais = null;
			}
		}
		return this.paisDescricao;
	}
	
	public Uf() {
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getCodemp() {
		return codemp;
	}

	public void setCodemp(Integer codemp) {
		this.codemp = codemp;
	}

	public Pais getPais() {
        if(!Hibernate.isInitialized(this.pais)) {
            try {
                Hibernate.initialize(this.pais);
            } catch(org.hibernate.ObjectNotFoundException one) {
            	this.pais = null;
            }
        }
        return this.pais;
    }
	

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public BigInteger getCheckDelete() {
		return checkDelete;
	}

	public void setCheckDelete(BigInteger checkDelete) {
		this.checkDelete = checkDelete;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public Integer getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(Integer recordNo) {
		this.recordNo = recordNo;
	}

	public Integer getCodigoIbge() {
		return codigoIbge;
	}

	public void setCodigoIbge(Integer codigoIbge) {
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

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public BigInteger getUtctag() {
		return utctag;
	}

	public void setUtctag(BigInteger utctag) {
		this.utctag = utctag;
	}

	@Override
	public String toString() {
		return "Uf [codigo=" + codigo + ", checkDelete=" + checkDelete + ", codemp=" + codemp + ", pais=" + pais
				+ ", cidades=" + cidades + ", recordNo=" + recordNo + ", codigoIbge=" + codigoIbge + ", descricao="
				+ descricao + ", flagAtivo=" + flagAtivo + ", lastCoduser=" + lastCoduser + ", lastMovto=" + lastMovto
				+ ", sigla=" + sigla + ", utctag=" + utctag + "]";
	}
}