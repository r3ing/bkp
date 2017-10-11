package models.configuracoes;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.*;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import models.configuracoes.Sequencial;

/**
 * The persistent class for the empresa database table.
 * 
 */
@Entity
@Table(name = "EMPRESA")
@NamedQueries({
	@NamedQuery(name = "EmpresaById", query = "SELECT d FROM Empresa d LEFT JOIN FETCH d.config WHERE d.codigo=:codigo"),
	@NamedQuery(name = "EmpresaAll", query = "SELECT d FROM Empresa d LEFT JOIN FETCH d.config")
})
@IdClass(value = EmpresaPK.class)
public class Empresa implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CODIGO")
	private Integer codigo;

	@Id
	@Column(name="CHECK_DELETE")
	private BigInteger checkDelete;
	
	@OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, mappedBy = "empresa")
	private Config config;
	
	private Integer codemp;
		
	@Column(name = "RECORD_NO")
	private Integer recordNo;
	
	@Column(name = "FLAG_ATIVO")
	private Integer flagAtivo;

	private String bairro;

	@Column(name = "CENTRAL_SAC")
	private String centralSac;

	private String cep;

	private String cidade;

	@Column(name = "CNAE_FISCAL")
	private String cnaeFiscal;

	private String cnpj;

	@Column(name = "COD_CIDADE")
	private int codCidade;

	@Column(name = "COD_ENQUADRAPORTE")
	private int codEnquadraporte;

	@Column(name = "COD_NATJURIDICA")
	private String codNatjuridica;

	@Column(name = "CRC_CHECKSYS")
	private String crcChecksys;

	@Column(name = "DESC_REDUZIDA")
	private String descReduzida;

	@Column(name = "DT_IMPLANTACAO")
	private LocalDateTime dtImplantacao;

	@Column(name = "EMAIL_EMAIL")
	private String emailEmail;

	@Column(name = "EMAIL_FLAGAUTENTICACAO")
	private Integer emailFlagAutenticacao;

	@Column(name = "EMAIL_HOSTSAI")
	private String emailHostsai;

	@Column(name = "EMAIL_LOGIN")
	private String emailLogin;

	@Column(name = "EMAIL_NOME")
	private String emailNome;

	@Column(name = "EMAIL_PASSWORD")
	private String emailPassword;

	@Column(name = "EMAIL_PORTSAI")
	private String emailPortsai;

	@Column(name = "END_NUMERO")
	private String endNumero;

	private String endereco;

	private String celular;

	private String fone;

	private String ie;

	@Column(name = "INC_MUNICIPAL")
	private String incMunicipal;

	@Column(name = "IND_TIPOATIV")
	private int indTipoativ;

	@Column(name = "LAST_CODUSER")
	private int lastCoduser;

	@Column(name = "LAST_MOVTO")
	private LocalDateTime lastMovto;

	@Column(name = "NO_SERIE")
	private String noSerie;

	@Column(name = "NOME_FANTASIA")
	private String nomeFantasia;

	@Column(name = "COD_REGIMETRIB")
	private int regimeTrib;

	@Column(name = "RAZAO_SOCIAL")
	private String razaoSocial;

	private String sistema;

	private String suframa;

	@Column(name = "TELEFONE_SAC")
	private String telefoneSac;

	private String uf;

	private String utctag;

	private String versao;

	@Column(name = "LINHA_ADICIONAL")
	private String linhaAdicional;

	@Column(name = "COR_EMPRESA")
	private String corEmpresa;

	@Column(name = "WEB_HOMEPAGE")
	private String webHomepage;

	@Transient
	private Sequencial sequencial;
	

	public Sequencial getSequencial() {
		return sequencial;
	}

	public void setSequencial(Sequencial sequencial) {
		this.sequencial = sequencial;
	}

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public Empresa() {
	}

	
	/**
	 * @return the codigo
	 */
	public Integer getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the codemp
	 */
	public Integer getCodemp() {
		return codemp;
	}

	/**
	 * @param codemp
	 *            the codemp to set
	 */
	public void setCodemp(Integer codemp) {
		this.codemp = codemp;
	}

	/**
	 * @return the recordNo
	 */
	public Integer getRecordNo() {
		return recordNo;
	}

	/**
	 * @param recordNo
	 *            the recordNo to set
	 */
	public void setRecordNo(Integer recordNo) {
		this.recordNo = recordNo;
	}

	public String getBairro() {
		return this.bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCentralSac() {
		return this.centralSac;
	}

	public void setCentralSac(String centralSac) {
		this.centralSac = centralSac;
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

	public String getCnaeFiscal() {
		return this.cnaeFiscal;
	}

	public void setCnaeFiscal(String cnaeFiscal) {
		this.cnaeFiscal = cnaeFiscal;
	}

	public String getCnpj() {
		return this.cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public int getCodCidade() {
		return this.codCidade;
	}

	public void setCodCidade(int codCidade) {
		this.codCidade = codCidade;
	}

	public int getCodEnquadraporte() {
		return this.codEnquadraporte;
	}

	public void setCodEnquadraporte(int codEnquadraporte) {
		this.codEnquadraporte = codEnquadraporte;
	}

	public String getCodNatjuridica() {
		return this.codNatjuridica;
	}

	public void setCodNatjuridica(String codNatjuridica) {
		this.codNatjuridica = codNatjuridica;
	}

	public String getCrcChecksys() {
		return this.crcChecksys;
	}

	public void setCrcChecksys(String crcChecksys) {
		this.crcChecksys = crcChecksys;
	}

	public String getDescReduzida() {
		return this.descReduzida;
	}

	public void setDescReduzida(String descReduzida) {
		this.descReduzida = descReduzida;
	}

	public LocalDateTime getDtImplantacao() {
		return this.dtImplantacao;
	}

	public void setDtImplantacao(LocalDateTime dtImplantacao) {
		this.dtImplantacao = dtImplantacao;
	}

	public String getEmailEmail() {
		return this.emailEmail;
	}

	public void setEmailEmail(String emailEmail) {
		this.emailEmail = emailEmail;
	}

	public Integer getEmailFlagAutenticacao() {
		return this.emailFlagAutenticacao;
	}

	public void setEmailFlagAutenticacao(Integer emailFlagAutenticacao) {
		this.emailFlagAutenticacao = emailFlagAutenticacao;
	}

	public String getEmailHostsai() {
		return this.emailHostsai;
	}

	public void setEmailHostsai(String emailHostsai) {
		this.emailHostsai = emailHostsai;
	}

	public String getEmailLogin() {
		return this.emailLogin;
	}

	public void setEmailLogin(String emailLogin) {
		this.emailLogin = emailLogin;
	}

	public String getEmailNome() {
		return this.emailNome;
	}

	public void setEmailNome(String emailNome) {
		this.emailNome = emailNome;
	}

	public String getEmailPassword() {
		return this.emailPassword;
	}

	public void setEmailPassword(String emailPassword) {
		this.emailPassword = emailPassword;
	}

	public String getEmailPortsai() {
		return this.emailPortsai;
	}

	public void setEmailPortsai(String emailPortsai) {
		this.emailPortsai = emailPortsai;
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

	public String getCelular() {
		return this.celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getFone() {
		return this.fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
	}

	public String getIe() {
		return this.ie;
	}

	public void setIe(String ie) {
		this.ie = ie;
	}

	public String getIncMunicipal() {
		return this.incMunicipal;
	}

	public void setIncMunicipal(String incMunicipal) {
		this.incMunicipal = incMunicipal;
	}

	public int getIndTipoativ() {
		return this.indTipoativ;
	}

	public void setIndTipoativ(int indTipoativ) {
		this.indTipoativ = indTipoativ;
	}

	public int getLastCoduser() {
		return this.lastCoduser;
	}

	public void setLastCoduser(int lastCoduser) {
		this.lastCoduser = lastCoduser;
	}

	public LocalDateTime getLastMovto() {
		return this.lastMovto;
	}

	public void setLastMovto(LocalDateTime lastMovto) {
		this.lastMovto = lastMovto;
	}

	public String getNoSerie() {
		return this.noSerie;
	}

	public void setNoSerie(String noSerie) {
		this.noSerie = noSerie;
	}

	public String getNomeFantasia() {
		return this.nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public int getRegimeTrib() {
		return this.regimeTrib;
	}

	public void setRegimeTrib(int regimeTrib) {
		this.regimeTrib = regimeTrib;

	}

	public String getRazaoSocial() {
		return this.razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getSistema() {
		return this.sistema;
	}

	public void setSistema(String sistema) {
		this.sistema = sistema;
	}

	public String getSuframa() {
		return this.suframa;
	}

	public void setSuframa(String suframa) {
		this.suframa = suframa;
	}

	public String getTelefoneSac() {
		return this.telefoneSac;
	}

	public void setTelefoneSac(String telefoneSac) {
		this.telefoneSac = telefoneSac;
	}

	public String getUf() {
		return this.uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getUtctag() {
		return this.utctag;
	}

	public void setUtctag(String utctag) {
		this.utctag = utctag;
	}

	public String getVersao() {
		return this.versao;
	}

	public void setVersao(String versao) {
		this.versao = versao;
	}

	public String getWebHomepage() {
		return this.webHomepage;
	}

	public void setWebHomepage(String webHomepage) {
		this.webHomepage = webHomepage;
	}

	public String getLinhaAdicional() {
		return this.linhaAdicional;
	}

	public void setLinhaAdicional(String linhaAdicional) {
		this.linhaAdicional = linhaAdicional;
	}

	public String getCorEmpresa() {
		return corEmpresa;
	}

	public void setCorEmpresa(String corEmpresa) {
		this.corEmpresa = corEmpresa;
	}

	public Integer getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Integer flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

}