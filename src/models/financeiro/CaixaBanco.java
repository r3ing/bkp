package models.financeiro;

import java.io.Serializable;
import javax.persistence.*;

import java.time.LocalDateTime;
import java.math.BigInteger;


/**
 * The persistent class for the caixa_banco database table.
 * 
 */
@Entity
@Table(name="caixa_banco")
//@NamedQuery(name="CaixaBanco.findAll", query="SELECT c FROM CaixaBanco c")
@NamedQueries({
	@NamedQuery(name = "CaixaBancoById", query = "SELECT c FROM CaixaBanco c WHERE c.codigo =:codigo AND c.flagAtivo =1 AND c.codemp IN (:codemp)"),
	@NamedQuery(name = "CaixaBancoAll", query = "SELECT c FROM CaixaBanco c WHERE c.flagAtivo IN (:flag) AND c.codemp IN (:codemp)"),
	@NamedQuery(name = "CaixaBancoLast", query = "SELECT c FROM CaixaBanco c WHERE c.codemp IN (:codemp) AND c.flagAtivo =1 ORDER BY c.codigo DESC")
	})
@IdClass(value = CaixaBancoPK.class)
public class CaixaBanco implements Serializable {
	private static final long serialVersionUID = 1L;

//	@EmbeddedId
//	private CaixaBancoPK id;
	@Id
	private Integer codigo;

	@Column(name="CHECK_DELETE")
	private BigInteger checkDelete;

	private Integer codemp;

	private String descricao;
	
	private String abreviatura;

	@Column(name="AGENCIA_NUMERO")
	private String agenciaNumero;
	
	@Column(name="AGENCIA_DIGITO")
	private String agenciaDigito;
	
	@Column(name="CONTA_NUMERO")
	private String contaNumero;
	
	@Column(name="CONTA_DIGITO")
	private String contaDigito;

	private String bairro;

	private Integer carteira;

	@Column(name="CEDENTE_NUMERO")
	private String cedenteNumero;

	private String cep;

	private String cidade;

	@Column(name="COD_CIDADE")
	private Integer codCidade;

	private String complemento;	

	@Column(name="CPF_CNPJ")
	private String cpfCnpj;

	private String email;

	@Column(name="END_NUMERO")
	private String endNumero;

	private String endereco;

	@Column(name="FLAG_APURACAO_RESULTADO")
	private Integer flagApuracaoResultado;

	private String fone;

	@Column(name="INSTRUCAO_01")
	private String instrucao01;

	@Column(name="INSTRUCAO_02")
	private String instrucao02;

	@Column(name="INSTRUCAO_03")
	private String instrucao03;

	@Column(name="INSTRUCAO_04")
	private String instrucao04;

	@Column(name="RAZAO_SOCIAL")
	private String razaoSocial;

	@Column(name="TIPO_BANCO")
	private Integer tipoBanco;

	@Column(name="TIPO_CONTA")
	private Integer tipoConta;
	
	@Column(name="FLAG_TIPOPESSOA")
	private Integer tipoPessoa;
	
	@Column(name="FLAG_BOLETO")
	private Integer flagBoleto;

	private String uf;

	@Column(name="FLAG_ATIVO")
	private Integer flagAtivo;

	@Column(name="LAST_CODUSER")
	private Integer lastCoduser;

	@Column(name="LAST_MOVTO")
	private LocalDateTime lastMovto;

	@Column(name="RECORD_NO")
	private Integer recordNo;

	private BigInteger utctag;

	public CaixaBanco() {
	}

//	public CaixaBancoPK getId() {
//		return this.id;
//	}
//
//	public void setId(CaixaBancoPK id) {
//		this.id = id;
//	}
	
	

	public String getDescricao() {
		return this.descricao;
	}

	public Integer getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(Integer tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
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

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	

	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public String getAgenciaNumero() {
		return agenciaNumero;
	}

	public void setAgenciaNumero(String agenciaNumero) {
		this.agenciaNumero = agenciaNumero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public Integer getCarteira() {
		return carteira;
	}

	public void setCarteira(Integer carteira) {
		this.carteira = carteira;
	}

	public String getCedenteNumero() {
		return cedenteNumero;
	}

	public void setCedenteNumero(String cedenteNumero) {
		this.cedenteNumero = cedenteNumero;
	}
	
	

	public Integer getFlagBoleto() {
		return flagBoleto;
	}

	public void setFlagBoleto(Integer flagBoleto) {
		this.flagBoleto = flagBoleto;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public Integer getCodCidade() {
		return codCidade;
	}

	public void setCodCidade(Integer codCidade) {
		this.codCidade = codCidade;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getContaNumero() {
		return contaNumero;
	}

	public void setContaNumero(String contaNumero) {
		this.contaNumero = contaNumero;
	}	

	public String getAgenciaDigito() {
		return agenciaDigito;
	}

	public void setAgenciaDigito(String agenciaDigito) {
		this.agenciaDigito = agenciaDigito;
	}

	public String getContaDigito() {
		return contaDigito;
	}

	public void setContaDigito(String contaDigito) {
		this.contaDigito = contaDigito;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEndNumero() {
		return endNumero;
	}

	public void setEndNumero(String endNumero) {
		this.endNumero = endNumero;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Integer getFlagApuracaoResultado() {
		return flagApuracaoResultado;
	}

	public void setFlagApuracaoResultado(Integer flagApuracaoResultado) {
		this.flagApuracaoResultado = flagApuracaoResultado;
	}

	public String getFone() {
		return fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
	}

	public String getInstrucao01() {
		return instrucao01;
	}

	public void setInstrucao01(String instrucao01) {
		this.instrucao01 = instrucao01;
	}

	public String getInstrucao02() {
		return instrucao02;
	}

	public void setInstrucao02(String instrucao02) {
		this.instrucao02 = instrucao02;
	}

	public String getInstrucao03() {
		return instrucao03;
	}

	public void setInstrucao03(String instrucao03) {
		this.instrucao03 = instrucao03;
	}

	public String getInstrucao04() {
		return instrucao04;
	}

	public void setInstrucao04(String instrucao04) {
		this.instrucao04 = instrucao04;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public Integer getTipoBanco() {
		return tipoBanco;
	}

	public void setTipoBanco(Integer tipoBanco) {
		this.tipoBanco = tipoBanco;
	}

	public Integer getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(Integer tipoConta) {
		this.tipoConta = tipoConta;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
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

}