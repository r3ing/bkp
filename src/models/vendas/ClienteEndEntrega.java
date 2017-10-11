package models.vendas;

import java.io.Serializable;
import javax.persistence.*;

import models.compras.ItemCodBar;

import java.time.LocalDateTime;
import java.math.BigInteger;


/**
 * The persistent class for the cliente_end_entrega database table.
 * 
 */
@Entity
@Table(name="CLIENTE_END_ENTREGA")
@NamedQueries({
	@NamedQuery(name = "ClienteEndEntregaAll", query = "SELECT c FROM ClienteEndEntrega c WHERE c.cliente.codigo = :codCliente AND c.flagAtivo = 1"),
	@NamedQuery(name = "EndrecosByCliente", query = "SELECT c FROM ClienteEndEntrega c LEFT JOIN c.cliente WHERE c.cliente = :cliente AND c.flagAtivo = 1 AND c.codemp = c.cliente.codemp"),
	@NamedQuery(name = "ClienteEndEntregaById", query = "SELECT c FROM ClienteEndEntrega c WHERE c.cliente.codigo  = :codCliente AND c.checkDelete = :checkDelete AND c.flagAtivo = 1 AND c.codemp IN (:codemp)")
	})
@IdClass(value = ClienteEndEntregaPK.class)
public class ClienteEndEntrega implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CHECK_DELETE")
	private BigInteger checkDelete;

	@Id
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = "COD_CLIENTE_FK", referencedColumnName= "CHECK_DELETE"),
		@JoinColumn(name = "COD_CLIENTE", referencedColumnName= "CODIGO")
		})
	private Cliente cliente;
	

	private Integer codemp;
	
	private String bairro;

	private String cep;

	private String cidade;

	@Column(name="COD_CIDADE")
	private Integer codCidade;

	private String complemento;

	@Column(name="END_NUMERO")
	private String endNumero;

	private String endereco;

	@Column(name="FLAG_ATIVO")
	private Integer flagAtivo;

	private String fone;

	@Column(name="LAST_CODUSER")
	private Integer lastCoduser;

	@Column(name="LAST_MOVTO")
	private LocalDateTime lastMovto;
	
	@Column(name="PONTO_REFERENCIA")
	private String pontoReferencia;

	@Column(name="RECORD_NO")
	private Integer recordNo;

	private String uf;

	private BigInteger utctag;
	
	
	public ClienteEndEntrega() {
	}

	public String getBairro() {
		return this.bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return this.cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCidade() {
		return this.cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public Integer getCodCidade() {
		return this.codCidade;
	}

	public void setCodCidade(Integer codCidade) {
		this.codCidade = codCidade;
	}

	public String getComplemento() {
		return this.complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getEndNumero() {
		return this.endNumero;
	}

	public void setEndNumero(String endNumero) {
		this.endNumero = endNumero;
	}

	public String getEndereco() {
		return this.endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Integer getFlagAtivo() {
		return this.flagAtivo;
	}

	public void setFlagAtivo(Integer flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public String getFone() {
		return this.fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
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

	public String getPontoReferencia() {
		return this.pontoReferencia;
	}

	public void setPontoReferencia(String pontoReferencia) {
		this.pontoReferencia = pontoReferencia;
	}

	public Integer getRecordNo() {
		return this.recordNo;
	}

	public void setRecordNo(Integer recordNo) {
		this.recordNo = recordNo;
	}

	public String getUf() {
		return this.uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public BigInteger getUtctag() {
		return this.utctag;
	}

	public void setUtctag(BigInteger utctag) {
		this.utctag = utctag;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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
	
	@Override
	public ClienteEndEntrega clone() {
	   try {
		return (ClienteEndEntrega) super.clone();
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
		result = prime * result + ((bairro == null) ? 0 : bairro.hashCode());
		result = prime * result + ((cep == null) ? 0 : cep.hashCode());
		result = prime * result + ((checkDelete == null) ? 0 : checkDelete.hashCode());
		result = prime * result + ((cidade == null) ? 0 : cidade.hashCode());
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((codCidade == null) ? 0 : codCidade.hashCode());
		result = prime * result + ((codemp == null) ? 0 : codemp.hashCode());
		result = prime * result + ((complemento == null) ? 0 : complemento.hashCode());
		result = prime * result + ((endNumero == null) ? 0 : endNumero.hashCode());
		result = prime * result + ((endereco == null) ? 0 : endereco.hashCode());
		result = prime * result + ((flagAtivo == null) ? 0 : flagAtivo.hashCode());
		result = prime * result + ((fone == null) ? 0 : fone.hashCode());
		result = prime * result + ((lastCoduser == null) ? 0 : lastCoduser.hashCode());
		result = prime * result + ((lastMovto == null) ? 0 : lastMovto.hashCode());
		result = prime * result + ((pontoReferencia == null) ? 0 : pontoReferencia.hashCode());
		result = prime * result + ((recordNo == null) ? 0 : recordNo.hashCode());
		result = prime * result + ((uf == null) ? 0 : uf.hashCode());
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
		ClienteEndEntrega other = (ClienteEndEntrega) obj;
		if (bairro == null) {
			if (other.bairro != null)
				return false;
		} else if (!bairro.equals(other.bairro))
			return false;
		if (cep == null) {
			if (other.cep != null)
				return false;
		} else if (!cep.equals(other.cep))
			return false;
		if (checkDelete == null) {
			if (other.checkDelete != null)
				return false;
		} else if (!checkDelete.equals(other.checkDelete))
			return false;
		if (cidade == null) {
			if (other.cidade != null)
				return false;
		} else if (!cidade.equals(other.cidade))
			return false;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (codCidade == null) {
			if (other.codCidade != null)
				return false;
		} else if (!codCidade.equals(other.codCidade))
			return false;
		if (codemp == null) {
			if (other.codemp != null)
				return false;
		} else if (!codemp.equals(other.codemp))
			return false;
		if (complemento == null) {
			if (other.complemento != null)
				return false;
		} else if (!complemento.equals(other.complemento))
			return false;
		if (endNumero == null) {
			if (other.endNumero != null)
				return false;
		} else if (!endNumero.equals(other.endNumero))
			return false;
		if (endereco == null) {
			if (other.endereco != null)
				return false;
		} else if (!endereco.equals(other.endereco))
			return false;
		if (flagAtivo == null) {
			if (other.flagAtivo != null)
				return false;
		} else if (!flagAtivo.equals(other.flagAtivo))
			return false;
		if (fone == null) {
			if (other.fone != null)
				return false;
		} else if (!fone.equals(other.fone))
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
		if (pontoReferencia == null) {
			if (other.pontoReferencia != null)
				return false;
		} else if (!pontoReferencia.equals(other.pontoReferencia))
			return false;
		if (recordNo == null) {
			if (other.recordNo != null)
				return false;
		} else if (!recordNo.equals(other.recordNo))
			return false;
		if (uf == null) {
			if (other.uf != null)
				return false;
		} else if (!uf.equals(other.uf))
			return false;
		if (utctag == null) {
			if (other.utctag != null)
				return false;
		} else if (!utctag.equals(other.utctag))
			return false;
		return true;
	}
	
	


}