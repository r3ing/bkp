package models.compras;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The persistent class for the fornecedor database table.
 * 
 */
@Entity
@Table(name = "FORNECEDOR")
@NamedQueries({
		@NamedQuery(name = "FornecedorById", query = "SELECT f FROM Fornecedor f WHERE f.codigo =:codigo AND f.flagAtivo =1 AND f.codemp IN (:codemp)"),
		@NamedQuery(name = "FornecedorByCNPJ", query = "SELECT f FROM Fornecedor f WHERE f.cpfCnpj =:cpfCnpj AND f.flagAtivo =1 AND f.codemp IN (:codemp)"),
		@NamedQuery(name = "FornecedorAll", query = "SELECT f FROM Fornecedor f WHERE f.flagAtivo IN (:flag) AND f.codemp IN (:codemp)"),
		@NamedQuery(name = "FornecedorLast", query = "SELECT f FROM Fornecedor f WHERE f.codemp IN (:codemp) AND f.flagAtivo =1 ORDER BY f.codigo DESC"),
		@NamedQuery(name = "TransportadoraById", query = "SELECT f FROM Fornecedor f WHERE f.codigo =:codigo AND f.flagAtivo =1 AND f.flagTransportadora =1 AND f.codemp IN (:codemp)")
		})
@IdClass(value = FornecedorPK.class)
public class Fornecedor implements Serializable {
	
	private static final long serialVersionUID = 1L;

	// ----  CHAVE PRIMARIA ---- //
	@Id
	private Integer codigo;
		
	
	private Integer codemp;
		
	@Id
	@Column(name="CHECK_DELETE")
	private BigInteger checkDelete;

	// ----  CHAVE PRIMARIA ---- //

	@Column(name = "RECORD_NO")
	private Integer recordNo;
	
	private String bairro;

	private String celular;

	private String cep;
	
	private String razao;

	private String uf;

	private BigInteger utctag;
	
	private String cidade;

	@Column(name="COD_CIDADE")
	private Integer codCidade;

	@Column(name="CONTATO1_NOME")
	private String contato1Nome;
	
	@Column(name="CONTATO1_CARGO")
	private String contato1Cargo;

	@Column(name="CONTATO2_NOME")
	private String contato2Nome;
	
	@Column(name="CONTATO2_CARGO")
	private String contato2Cargo;

	@Column(name="CPF_CNPJ")
	private String cpfCnpj;

	@Column(name="DIAS_ENTREGA")
	private Integer diasEntrega;

	private String email;

	@Column(name="END_NUMERO")
	private String endNumero;	
	
	@Column(name="COMPLEMENTO")
	private String complemento;	

	private String endereco;

	private String fantasia;

	@Column(name="FLAG_ATIVO")
	private Integer flagAtivo;

	@Column(name="FLAG_TIPOPESSOA")
	private Integer flagTipopessoa;
		
	@Column(name="FLAG_TRANSPORTADORA")
	private Integer flagTransportadora;	

	private String fone;

	private String fone2;

	@Column(name="IE_RG")
	private String ieRg;

	@Column(name="INSC_PRODRURAL")
	private String inscProdrural;

	@Column(name="INTERNET_SITE")
	private String internetSite;

	@Column(name="LAST_CODUSER")
	private Integer lastCoduser;

	@Column(name="LAST_MOVTO")
	private LocalDateTime lastMovto;
	
	@Column(name="BANCO1_DESCRICAO")
	private String banco1Descricao;
	
	@Column(name="BANCO1_AGENCIA")
	private String banco1Agencia;
	
	@Column(name="BANCO1_CONTA")
	private String banco1Conta;
	
	@Column(name="BANCO1_DESTINATARIO")
	private String banco1Destinatario;
	
	@Column(name="BANCO1_CPF_CNPJ")
	private String banco1CpfCnpj;
	
	@Column(name="BANCO2_DESCRICAO")
	private String banco2Descricao;
	
	@Column(name="BANCO2_AGENCIA")
	private String banco2Agencia;
	
	@Column(name="BANCO2_CONTA")
	private String banco2Conta;
	
	@Column(name="BANCO2_DESTINATARIO")
	private String banco2Destinatario;
	
	@Column(name="BANCO2_CPF_CNPJ")
	private String banco2CpfCnpj;
	
	@Column(name="FLAG_INDICADORINSCEST")
	private Integer flagIndicadorInscEst;
	
//	@OneToMany(mappedBy = "fornecedor", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
//	private NfEntradaCab nfEntradaCab;
	@OneToMany(mappedBy = "fornecedor", cascade = CascadeType.PERSIST)
	private List<ItemFornecedor> itemFornecedor;
	
	public Fornecedor() {
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

	public Integer getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(Integer recordNo) {
		this.recordNo = recordNo;
	}

	public String getBairro() {
		return this.bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCelular() {
		return this.celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getCep() {
		return this.cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public BigInteger getCheckDelete() {
		return this.checkDelete;
	}

	public void setCheckDelete(BigInteger checkDelete) {
		this.checkDelete = checkDelete;
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
	
	public String getCpfCnpj() {
		return this.cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public Integer getDiasEntrega() {
		return this.diasEntrega;
	}

	public void setDiasEntrega(Integer diasEntrega) {
		this.diasEntrega = diasEntrega;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEndNumero() {
		return this.endNumero;
	}

	public void setEndNumero(String endNumero) {
		this.endNumero = endNumero;
	}	
	
	
	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getEndereco() {
		return this.endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getFantasia() {
		return this.fantasia;
	}

	public void setFantasia(String fantasia) {
		this.fantasia = fantasia;
	}

	public Integer getFlagAtivo() {
		return this.flagAtivo;
	}

	public void setFlagAtivo(Integer flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public Integer getFlagTipopessoa() {
		return this.flagTipopessoa;
	}

	public void setFlagTipopessoa(Integer flagTipopessoa) {
		this.flagTipopessoa = flagTipopessoa;
	}	

	public Integer getFlagTransportadora() {
		return flagTransportadora;
	}

	public void setFlagTransportadora(Integer flagTransportadora) {
		this.flagTransportadora = flagTransportadora;
	}

	public String getFone() {
		return this.fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
	}

	public String getFone2() {
		return this.fone2;
	}

	public void setFone2(String fone2) {
		this.fone2 = fone2;
	}

	public String getIeRg() {
		return this.ieRg;
	}

	public void setIeRg(String ieRg) {
		this.ieRg = ieRg;
	}

	public String getInscProdrural() {
		return this.inscProdrural;
	}

	public void setInscProdrural(String inscProdrural) {
		this.inscProdrural = inscProdrural;
	}

	public String getInternetSite() {
		return this.internetSite;
	}

	public void setInternetSite(String internetSite) {
		this.internetSite = internetSite;
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

	public String getRazao() {
		return this.razao;
	}

	public void setRazao(String razao) {
		this.razao = razao;
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

	public String getContato1Nome() {
		return contato1Nome;
	}

	public void setContato1Nome(String contato1Nome) {
		this.contato1Nome = contato1Nome;
	}

	public String getContato1Cargo() {
		return contato1Cargo;
	}

	public void setContato1Cargo(String contato1Cargo) {
		this.contato1Cargo = contato1Cargo;
	}

	public String getContato2Nome() {
		return contato2Nome;
	}

	public void setContato2Nome(String contato2Nome) {
		this.contato2Nome = contato2Nome;
	}

	public String getContato2Cargo() {
		return contato2Cargo;
	}

	public void setContato2Cargo(String contato2Cargo) {
		this.contato2Cargo = contato2Cargo;
	}

	public String getBanco1Descricao() {
		return banco1Descricao;
	}

	public void setBanco1Descricao(String banco1Descricao) {
		this.banco1Descricao = banco1Descricao;
	}

	public String getBanco1Agencia() {
		return banco1Agencia;
	}

	public void setBanco1Agencia(String banco1Agencia) {
		this.banco1Agencia = banco1Agencia;
	}

	public String getBanco1Conta() {
		return banco1Conta;
	}

	public void setBanco1Conta(String banco1Conta) {
		this.banco1Conta = banco1Conta;
	}

	public String getBanco1Destinatario() {
		return banco1Destinatario;
	}

	public void setBanco1Destinatario(String banco1Destinatario) {
		this.banco1Destinatario = banco1Destinatario;
	}

	public String getBanco1CpfCnpj() {
		return banco1CpfCnpj;
	}

	public void setBanco1CpfCnpj(String banco1CpfCnpj) {
		this.banco1CpfCnpj = banco1CpfCnpj;
	}

	public String getBanco2Descricao() {
		return banco2Descricao;
	}

	public void setBanco2Descricao(String banco2Descricao) {
		this.banco2Descricao = banco2Descricao;
	}

	public String getBanco2Agencia() {
		return banco2Agencia;
	}

	public void setBanco2Agencia(String banco2Agencia) {
		this.banco2Agencia = banco2Agencia;
	}

	public String getBanco2Conta() {
		return banco2Conta;
	}

	public void setBanco2Conta(String banco2Conta) {
		this.banco2Conta = banco2Conta;
	}

	public String getBanco2Destinatario() {
		return banco2Destinatario;
	}

	public void setBanco2Destinatario(String banco2Destinatario) {
		this.banco2Destinatario = banco2Destinatario;
	}

	public String getBanco2CpfCnpj() {
		return banco2CpfCnpj;
	}

	public void setBanco2CpfCnpj(String banco2CpfCnpj) {
		this.banco2CpfCnpj = banco2CpfCnpj;
	}

	public Integer getFlagIndicadorInscEst() {
		return flagIndicadorInscEst;
	}

	public void setFlagIndicadorInscEst(Integer flagIndicadorInscEst) {
		this.flagIndicadorInscEst = flagIndicadorInscEst;
	}
	
	
	
//	public NfEntradaCab getNfEntradaCab() {
//		return nfEntradaCab;
//	}
//
//	public void setNfEntradaCab(NfEntradaCab nfEntradaCab) {
//		this.nfEntradaCab = nfEntradaCab;
//	}

	public List<ItemFornecedor> getItemFornecedor() {
		return itemFornecedor;
	}

	public void setItemFornecedor(List<ItemFornecedor> itemFornecedor) {
		this.itemFornecedor = itemFornecedor;
	}

	@Override
	public String toString() {
		return "Fornecedor [codigo=" + codigo + ", codemp=" + codemp + ", checkDelete=" + checkDelete + ", recordNo="
				+ recordNo + ", bairro=" + bairro + ", celular=" + celular + ", cep=" + cep + ", razao=" + razao
				+ ", uf=" + uf + ", utctag=" + utctag + ", cidade=" + cidade + ", codCidade=" + codCidade
				+ ", contato1Nome=" + contato1Nome + ", contato1Cargo=" + contato1Cargo + ", contato2Nome="
				+ contato2Nome + ", contato2Cargo=" + contato2Cargo + ", cpfCnpj=" + cpfCnpj + ", diasEntrega="
				+ diasEntrega + ", email=" + email + ", endNumero=" + endNumero + ", complemento=" + complemento
				+ ", endereco=" + endereco + ", fantasia=" + fantasia + ", flagAtivo=" + flagAtivo + ", flagTipopessoa="
				+ flagTipopessoa + ", flagTransportadora=" + flagTransportadora + ", fone=" + fone + ", fone2=" + fone2
				+ ", ieRg=" + ieRg + ", inscProdrural=" + inscProdrural + ", internetSite=" + internetSite
				+ ", lastCoduser=" + lastCoduser + ", lastMovto=" + lastMovto + ", banco1Descricao=" + banco1Descricao
				+ ", banco1Agencia=" + banco1Agencia + ", banco1Conta=" + banco1Conta + ", banco1Destinatario="
				+ banco1Destinatario + ", banco1CpfCnpj=" + banco1CpfCnpj + ", banco2Descricao=" + banco2Descricao
				+ ", banco2Agencia=" + banco2Agencia + ", banco2Conta=" + banco2Conta + ", banco2Destinatario="
				+ banco2Destinatario + ", banco2CpfCnpj=" + banco2CpfCnpj + ", flagIndicadorInscEst="
				+ flagIndicadorInscEst + ", nfEntradaCab=]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((checkDelete == null) ? 0 : checkDelete.hashCode());
		result = prime * result + ((codemp == null) ? 0 : codemp.hashCode());
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
		Fornecedor other = (Fornecedor) obj;
		if (checkDelete == null) {
			if (other.checkDelete != null)
				return false;
		} else if (!checkDelete.equals(other.checkDelete))
			return false;
		if (codemp == null) {
			if (other.codemp != null)
				return false;
		} else if (!codemp.equals(other.codemp))
			return false;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	

}