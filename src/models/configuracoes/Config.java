package models.configuracoes;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.math.BigInteger;

/**
 * The persistent class for the config database table.
 */
@Entity
@Table(name = "CONFIG")
@IdClass(value = EmpresaPK.class)
public class Config implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = "CHECK_DELETE", referencedColumnName= "CHECK_DELETE"),
		@JoinColumn(name = "CODEMP", referencedColumnName= "CODIGO")
	})
	@LazyToOne(LazyToOneOption.NO_PROXY)
	private Empresa empresa;

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@Column(name="RECORD_NO")
	private Integer recordNo;
	
	public Integer getFinTipointevaloTaxajuros() {
		return finTipointevaloTaxajuros;
	}

	@Column(name = "FLAG_ATIVO")
	private Integer flagAtivo;
	

	public Integer getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Integer flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	@Column(name="CPA_CRITERIOPRECOSFILIAIS")
	private Integer cpaCriterioprecosfiliais;

	@Column(name="CPA_FLAG_CONTROLELOTE")
	private Integer cpaFlagControlelote;

	@Column(name="CPA_FLAG_DADOSULTIMOPRODUTO")
	private Integer cpaFlagDadosultimoproduto;

	@Column(name="CPA_FLAG_DADOSVEICULO")
	private Integer cpaFlagDadosveiculo;

	@Column(name="CPA_FLAG_TABPROGRESSIVADESCPROD")
	private Integer cpaFlagTabprogressivadescprod;

	@Column(name="CPA_FLAG_TRIBAUTOMATICAFILIAIS")
	private Integer cpaFlagTribautomaticafiliais;
	
	@Column(name= "CPA_FLAG_ENVIAOBSITENS_VENDA")
	private Integer cpaFlagEnviaobsitensvenda;	

	@Column(name="CPA_QTDCASASDECIMAISESTOQUE")
	private Integer cpaQtdcasasdecimaisestoque;

	@Column(name="CPA_QTDCASASDECIMAISVALOR")
	private Integer cpaQtdcasasdecimaisvalor;

	@Column(name="CPA_QTDDIASCHEGADANFE")
	private Integer cpaQtddiaschegadanfe;

	@Column(name="CPA_TIPOCALCULOPRECO")
	private Integer cpaTipocalculopreco;

	@Column(name="CPA_TIPOCODPRODUTOCOMPRAS")
	private Integer cpaTipocodprodutocompras;

	@Column(name="CPA_TIPOLOTEDEFAULT")
	private Integer cpaTipolotedefault;

	@Column(name="CPA_TIPOSEQUENCIAPROD")
	private Integer cpaTiposequenciaprod;
	
	@Column(name="CPA_DIRETORIO_XML")
	private String cpaDiretorioXml;

	@Column(name="FIN_DIASCARENCIA_GERARJUROS")
	private Integer finDiascarenciaGerarjuros;

	@Column(name="FIN_MULTACTASREC")
	private BigDecimal finMultactasrec;

	@Column(name="FIN_TAXAJUROS")
	private BigDecimal finTaxajuros;

	@Column(name="FIN_TAXAJUROS_MINIMA")
	private BigDecimal finTaxajurosMinima;

	@Column(name="FIN_TIPOINTEVALO_TAXAJUROS")
	private Integer finTipointevaloTaxajuros;

	@Column(name="GER_EMAILOCULTA")
	private String gerEmailoculta;

	@Column(name="GER_FLAG_EMAILCOPYOCULTA")
	private Integer gerFlagEmailcopyoculta;

	@Column(name="GER_FLAG_MODULOFIDELIDADE")
	private Integer gerFlagModulofidelidade;

	@Column(name="GER_FLAG_NOTIFICACAO")
	private Integer gerFlagNotificacao;

	@Column(name="GER_NOTIFICACAOESTILO")
	private Integer gerNotificacaoestilo;

	@Column(name="GER_NOTIFICACAOPOSICAO")
	private Integer gerNotificacaoposicao;

	@Column(name="GER_PERCCARGATRIBGERAL")
	private BigDecimal gerPerccargatribgeral;

	@Column(name="GER_QTD_CARACTERBUSCA")
	private Integer gerQtdCaracterbusca;
	
	@Column(name="GER_CERTIFICADO_DIGITAL")
	private String gerCertificadoDigital;
	
	@Column(name="GER_CERTIFICADO_DT_VENCTO")
	private String gerCertificadoDtVencto;

	@Lob
	@Column(name="IMG_LOGOMARCA")
	private byte[] imgLogomarca;

	@Column(name="LAST_CODUSER")
	private Integer lastCoduser;

	@Column(name="LAST_MOVTO")
	private LocalDateTime lastMovto;

	@Column(name="SEG_FLAG_ALTSENHAPERIO")
	private Integer segFlagAltsenhaperio;

	@Column(name="SEG_FLAG_CONTEMLETRA")
	private Integer segFlagContemletra;

	@Column(name="SEG_FLAG_REUTILIZAPWD")
	private Integer segFlagReutilizapwd;

	@Column(name="SEG_PRAZO_ALTERASENHA")
	private Integer segPrazoAlterasenha;

	@Column(name="SEG_QTDCARACTER")
	private Integer segQtdcaracter;

	@Column(name="SEG_SEQDIGITOS")
	private Integer segSeqdigitos;

	private BigInteger utctag;

	@Column(name="VDA_CODIGOBALANCA_QTDDIGITOS")
	private Integer vdaCodigobalancaQtddigitos;

	@Column(name="VDA_COMISSAO_LOCALORIGEM")
	private Integer vdaComissaoLocalorigem;

	@Column(name="VDA_COMISSAO_TIPOPAGAMENTO")
	private Integer vdaComissaoTipopagamento;

	@Column(name="VDA_DIAS_BLOQVDACADVENCIDO")
	private Integer vdaDiasBloqvdacadvencido;

	@Column(name="VDA_DIAS_BLOQVDADESATUALIZADO")
	private Integer vdaDiasBloqvdadesatualizado;

	@Column(name="VDA_DIAS_BLOQVDAINADIMPLENTE")
	private Integer vdaDiasBloqvdainadimplente;

	@Column(name="VDA_DIAS_BLOQVDASEMMOVIMENTO")
	private Integer vdaDiasBloqvdasemmovimento;

	@Column(name="VDA_DIAS_ENVIOCARTORIO")
	private Integer vdaDiasEnviocartorio;

	@Column(name="VDA_DIASVALIDADE_PROPOSTA")
	private Integer vdaDiasvalidadeProposta;

	@Column(name="VDA_FILTRODEFAULT_BUSCAITENS")
	private Integer vdaFiltrodefaultBuscaitens;

	@Column(name="VDA_FLAG_BLOQVDACADVENCIDO")
	private Integer vdaFlagBloqvdacadvencido;

	@Column(name="VDA_FLAG_BLOQVDADESATUALIZADO")
	private Integer vdaFlagBloqvdadesatualizado;

	@Column(name="VDA_FLAG_BLOQVDAINADIMPLENTE")
	private Integer vdaFlagBloqvdainadimplente;

	@Column(name="VDA_FLAG_BLOQVDASEMMOVIMENTO")
	private Integer vdaFlagBloqvdasemmovimento;

	@Column(name="VDA_FLAG_CODIGOBALANCA")
	private Integer vdaFlagCodigobalanca;

	@Column(name="VDA_FLAG_DESCONTOPRODS_PROMOCAO")
	private Integer vdaFlagDescontoprodsPromocao;

	@Column(name="VDA_FLAG_ENVIACLICARTORIO")
	private Integer vdaFlagEnviaclicartorio;

	@Column(name="VDA_FLAG_EXIBEDADOSFILIAIS_VENDA")
	private Integer vdaFlagExibedadosfiliaisVenda;

	@Column(name="VDA_FLAG_EXIBEPRODS_SIMILARES")
	private Integer vdaFlagExibeprodsSimilares;

	@Column(name="VDA_FLAG_JUSTIFICALIBERACAO")
	private Integer vdaFlagJustificaliberacao;

	@Column(name="VDA_FLAG_UTILIZA_CLIASSOCIADOS")
	private Integer vdaFlagUtilizaCliassociados;

	@Column(name="VDA_FLAG_UTILIZATABELA_PRECOCLI")
	private Integer vdaFlagUtilizatabelaPrecocli;

	@Column(name="VDA_FLAG_VINCULA_VENDEDORCLI")
	private Integer vdaFlagVinculaVendedorcli;

	@Column(name="VDA_FRETE_TIPODEFAULT")
	private Integer vdaFreteTipodefault;

	@Column(name="VDA_LIMITE_CREDITO_DEFAULT")
	private BigDecimal vdaLimiteCreditoDefault;

	@Column(name="VDA_TIPO_EXIBEDADOSFILIAS_VENDA")
	private Integer vdaTipoExibedadosfiliasVenda;

	@Column(name="VDA_TIPOCODIGO_TELADEVENDAS")
	private Integer vdaTipocodigoTeladevendas;

	@Column(name="VDA_TIPOIDENTIFICACAO_VENDEDOR")
	private Integer vdaTipoidentificacaoVendedor;

	@Column(name="VDA_TIPOLIMCREDITO_CLIASSOCIADOS")
	private Integer vdaTipolimcreditoCliassociados;

	@Column(name="VDA_TIPOLIMCREDITO_CLIENTES")
	private Integer vdaTipolimcreditoClientes;

	@Column(name="VDA_TIPOSEQUENCIACLI")
	private Integer vdaTiposequenciacli;

	public Config() {
	}

//	public ConfigPK getId() {
//		return this.id;
//	}
//
//	public void setId(ConfigPK id) {
//		this.id = id;
//	}

	public Integer getCpaCriterioprecosfiliais() {
		return this.cpaCriterioprecosfiliais;
	}

	public void setCpaCriterioprecosfiliais(Integer cpaCriterioprecosfiliais) {
		this.cpaCriterioprecosfiliais = cpaCriterioprecosfiliais;
	}

	public Integer getCpaFlagControlelote() {
		return this.cpaFlagControlelote;
	}

	public void setCpaFlagControlelote(Integer cpaFlagControlelote) {
		this.cpaFlagControlelote = cpaFlagControlelote;
	}

	public Integer getCpaFlagDadosultimoproduto() {
		return this.cpaFlagDadosultimoproduto;
	}

	public void setCpaFlagDadosultimoproduto(Integer cpaFlagDadosultimoproduto) {
		this.cpaFlagDadosultimoproduto = cpaFlagDadosultimoproduto;
	}

	public Integer getCpaFlagDadosveiculo() {
		return this.cpaFlagDadosveiculo;
	}

	public void setCpaFlagDadosveiculo(Integer cpaFlagDadosveiculo) {
		this.cpaFlagDadosveiculo = cpaFlagDadosveiculo;
	}

	public Integer getCpaFlagTabprogressivadescprod() {
		return this.cpaFlagTabprogressivadescprod;
	}

	public void setCpaFlagTabprogressivadescprod(Integer cpaFlagTabprogressivadescprod) {
		this.cpaFlagTabprogressivadescprod = cpaFlagTabprogressivadescprod;
	}

	public Integer getCpaFlagTribautomaticafiliais() {
		return this.cpaFlagTribautomaticafiliais;
	}

	public void setCpaFlagTribautomaticafiliais(Integer cpaFlagTribautomaticafiliais) {
		this.cpaFlagTribautomaticafiliais = cpaFlagTribautomaticafiliais;
	}
	
	

	public Integer getCpaFlagEnviaobsitensvenda() {
		return cpaFlagEnviaobsitensvenda;
	}

	public void setCpaFlagEnviaobsitensvenda(Integer cpaFlagEnviaobsitensvenda) {
		this.cpaFlagEnviaobsitensvenda = cpaFlagEnviaobsitensvenda;
	}

	public Integer getCpaQtdcasasdecimaisestoque() {
		return this.cpaQtdcasasdecimaisestoque;
	}

	public void setCpaQtdcasasdecimaisestoque(Integer cpaQtdcasasdecimaisestoque) {
		this.cpaQtdcasasdecimaisestoque = cpaQtdcasasdecimaisestoque;
	}

	public Integer getCpaQtdcasasdecimaisvalor() {
		return this.cpaQtdcasasdecimaisvalor;
	}

	public void setCpaQtdcasasdecimaisvalor(Integer cpaQtdcasasdecimaisvalor) {
		this.cpaQtdcasasdecimaisvalor = cpaQtdcasasdecimaisvalor;
	}

	public Integer getCpaQtddiaschegadanfe() {
		return this.cpaQtddiaschegadanfe;
	}

	public void setCpaQtddiaschegadanfe(Integer cpaQtddiaschegadanfe) {
		this.cpaQtddiaschegadanfe = cpaQtddiaschegadanfe;
	}

	public Integer getCpaTipocalculopreco() {
		return this.cpaTipocalculopreco;
	}

	public void setCpaTipocalculopreco(Integer cpaTipocalculopreco) {
		this.cpaTipocalculopreco = cpaTipocalculopreco;
	}

	public Integer getCpaTipocodprodutocompras() {
		return this.cpaTipocodprodutocompras;
	}

	public void setCpaTipocodprodutocompras(Integer cpaTipocodprodutocompras) {
		this.cpaTipocodprodutocompras = cpaTipocodprodutocompras;
	}

	public Integer getCpaTipolotedefault() {
		return this.cpaTipolotedefault;
	}

	public void setCpaTipolotedefault(Integer cpaTipolotedefault) {
		this.cpaTipolotedefault = cpaTipolotedefault;
	}

	public Integer getCpaTiposequenciaprod() {
		return this.cpaTiposequenciaprod;
	}

	public void setCpaTiposequenciaprod(Integer cpaTiposequenciaprod) {
		this.cpaTiposequenciaprod = cpaTiposequenciaprod;
	}

	
	
	public String getCpaDiretorioXml() {
		return cpaDiretorioXml;
	}

	public void setCpaDiretorioXml(String cpaDiretorioXml) {
		this.cpaDiretorioXml = cpaDiretorioXml;
	}

	public Integer getFinDiascarenciaGerarjuros() {
		return this.finDiascarenciaGerarjuros;
	}

	public void setFinDiascarenciaGerarjuros(Integer finDiascarenciaGerarjuros) {
		this.finDiascarenciaGerarjuros = finDiascarenciaGerarjuros;
	}

	public BigDecimal getFinMultactasrec() {
		return this.finMultactasrec;
	}

	public void setFinMultactasrec(BigDecimal finMultactasrec) {
		this.finMultactasrec = finMultactasrec;
	}

	public BigDecimal getFinTaxajuros() {
		return this.finTaxajuros;
	}

	public void setFinTaxajuros(BigDecimal finTaxajuros) {
		this.finTaxajuros = finTaxajuros;
	}

	public BigDecimal getFinTaxajurosMinima() {
		return this.finTaxajurosMinima;
	}

	public void setFinTaxajurosMinima(BigDecimal finTaxajurosMinima) {
		this.finTaxajurosMinima = finTaxajurosMinima;
	}

	public Integer getFinTipointevaloIntegerajuros() {
		return this.finTipointevaloTaxajuros;
	}

	public void setFinTipointevaloTaxajuros(Integer finTipointevaloTaxajuros) {
		this.finTipointevaloTaxajuros = finTipointevaloTaxajuros;
	}

	public String getGerEmailoculta() {
		return this.gerEmailoculta;
	}

	public void setGerEmailoculta(String gerEmailoculta) {
		this.gerEmailoculta = gerEmailoculta;
	}

	public Integer getGerFlagEmailcopyoculta() {
		return this.gerFlagEmailcopyoculta;
	}

	public void setGerFlagEmailcopyoculta(Integer gerFlagEmailcopyoculta) {
		this.gerFlagEmailcopyoculta = gerFlagEmailcopyoculta;
	}

	public Integer getGerFlagModulofidelidade() {
		return this.gerFlagModulofidelidade;
	}

	public void setGerFlagModulofidelidade(Integer gerFlagModulofidelidade) {
		this.gerFlagModulofidelidade = gerFlagModulofidelidade;
	}

	public Integer getGerFlagNotificacao() {
		return this.gerFlagNotificacao;
	}

	public void setGerFlagNotificacao(Integer gerFlagNotificacao) {
		this.gerFlagNotificacao = gerFlagNotificacao;
	}

	public Integer getGerNotificacaoestilo() {
		return this.gerNotificacaoestilo;
	}

	public void setGerNotificacaoestilo(Integer gerNotificacaoestilo) {
		this.gerNotificacaoestilo = gerNotificacaoestilo;
	}

	public Integer getGerNotificacaoposicao() {
		return this.gerNotificacaoposicao;
	}

	public void setGerNotificacaoposicao(Integer gerNotificacaoposicao) {
		this.gerNotificacaoposicao = gerNotificacaoposicao;
	}

	public BigDecimal getGerPerccargatribgeral() {
		return this.gerPerccargatribgeral;
	}

	public void setGerPerccargatribgeral(BigDecimal gerPerccargatribgeral) {
		this.gerPerccargatribgeral = gerPerccargatribgeral;
	}

	public Integer getGerQtdCaracterbusca() {
		return this.gerQtdCaracterbusca;
	}

	public void setGerQtdCaracterbusca(Integer gerQtdCaracterbusca) {
		this.gerQtdCaracterbusca = gerQtdCaracterbusca;
	}	

	public String getGerCertificadoDigital() {
		return gerCertificadoDigital;
	}

	public void setGerCertificadoDigital(String gerCertificadoDigital) {
		this.gerCertificadoDigital = gerCertificadoDigital;
	}

	public String getGerCertificadoDtVencto() {
		return gerCertificadoDtVencto;
	}

	public void setGerCertificadoDtVencto(String gerCertificadoDtVencto) {
		this.gerCertificadoDtVencto = gerCertificadoDtVencto;
	}

	public byte[] getImgLogomarca() {
		return this.imgLogomarca;
	}

	public void setImgLogomarca(byte[] imgLogomarca) {
		this.imgLogomarca = imgLogomarca;
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

	public Integer getSegFlagAltsenhaperio() {
		return this.segFlagAltsenhaperio;
	}

	public void setSegFlagAltsenhaperio(Integer segFlagAltsenhaperio) {
		this.segFlagAltsenhaperio = segFlagAltsenhaperio;
	}

	public Integer getSegFlagContemletra() {
		return this.segFlagContemletra;
	}

	public void setSegFlagContemletra(Integer segFlagContemletra) {
		this.segFlagContemletra = segFlagContemletra;
	}

	public Integer getSegFlagReutilizapwd() {
		return this.segFlagReutilizapwd;
	}

	public void setSegFlagReutilizapwd(Integer segFlagReutilizapwd) {
		this.segFlagReutilizapwd = segFlagReutilizapwd;
	}

	public Integer getSegPrazoAlterasenha() {
		return this.segPrazoAlterasenha;
	}

	public void setSegPrazoAlterasenha(Integer segPrazoAlterasenha) {
		this.segPrazoAlterasenha = segPrazoAlterasenha;
	}

	public Integer getSegQtdcaracter() {
		return this.segQtdcaracter;
	}

	public void setSegQtdcaracter(Integer segQtdcaracter) {
		this.segQtdcaracter = segQtdcaracter;
	}

	public Integer getSegSeqdigitos() {
		return this.segSeqdigitos;
	}

	public void setSegSeqdigitos(Integer segSeqdigitos) {
		this.segSeqdigitos = segSeqdigitos;
	}

	public BigInteger getUtctag() {
		return this.utctag;
	}

	public void setUtctag(BigInteger utctag) {
		this.utctag = utctag;
	}

	public Integer getVdaCodigobalancaQtddigitos() {
		return this.vdaCodigobalancaQtddigitos;
	}

	public void setVdaCodigobalancaQtddigitos(Integer vdaCodigobalancaQtddigitos) {
		this.vdaCodigobalancaQtddigitos = vdaCodigobalancaQtddigitos;
	}

	public Integer getVdaComissaoLocalorigem() {
		return this.vdaComissaoLocalorigem;
	}

	public void setVdaComissaoLocalorigem(Integer vdaComissaoLocalorigem) {
		this.vdaComissaoLocalorigem = vdaComissaoLocalorigem;
	}

	public Integer getVdaComissaoTipopagamento() {
		return this.vdaComissaoTipopagamento;
	}

	public void setVdaComissaoTipopagamento(Integer vdaComissaoTipopagamento) {
		this.vdaComissaoTipopagamento = vdaComissaoTipopagamento;
	}

	public Integer getVdaDiasBloqvdacadvencido() {
		return this.vdaDiasBloqvdacadvencido;
	}

	public void setVdaDiasBloqvdacadvencido(Integer vdaDiasBloqvdacadvencido) {
		this.vdaDiasBloqvdacadvencido = vdaDiasBloqvdacadvencido;
	}

	public Integer getVdaDiasBloqvdadesatualizado() {
		return this.vdaDiasBloqvdadesatualizado;
	}

	public void setVdaDiasBloqvdadesatualizado(Integer vdaDiasBloqvdadesatualizado) {
		this.vdaDiasBloqvdadesatualizado = vdaDiasBloqvdadesatualizado;
	}

	public Integer getVdaDiasBloqvdainadimplente() {
		return this.vdaDiasBloqvdainadimplente;
	}

	public void setVdaDiasBloqvdainadimplente(Integer vdaDiasBloqvdainadimplente) {
		this.vdaDiasBloqvdainadimplente = vdaDiasBloqvdainadimplente;
	}

	public Integer getVdaDiasBloqvdasemmovimento() {
		return this.vdaDiasBloqvdasemmovimento;
	}

	public void setVdaDiasBloqvdasemmovimento(Integer vdaDiasBloqvdasemmovimento) {
		this.vdaDiasBloqvdasemmovimento = vdaDiasBloqvdasemmovimento;
	}

	public Integer getVdaDiasEnviocartorio() {
		return this.vdaDiasEnviocartorio;
	}

	public void setVdaDiasEnviocartorio(Integer vdaDiasEnviocartorio) {
		this.vdaDiasEnviocartorio = vdaDiasEnviocartorio;
	}

	public Integer getVdaDiasvalidadeProposta() {
		return this.vdaDiasvalidadeProposta;
	}

	public void setVdaDiasvalidadeProposta(Integer vdaDiasvalidadeProposta) {
		this.vdaDiasvalidadeProposta = vdaDiasvalidadeProposta;
	}

	public Integer getVdaFiltrodefaultBuscaitens() {
		return this.vdaFiltrodefaultBuscaitens;
	}

	public void setVdaFiltrodefaultBuscaitens(Integer vdaFiltrodefaultBuscaitens) {
		this.vdaFiltrodefaultBuscaitens = vdaFiltrodefaultBuscaitens;
	}

	public Integer getVdaFlagBloqvdacadvencido() {
		return this.vdaFlagBloqvdacadvencido;
	}

	public void setVdaFlagBloqvdacadvencido(Integer vdaFlagBloqvdacadvencido) {
		this.vdaFlagBloqvdacadvencido = vdaFlagBloqvdacadvencido;
	}

	public Integer getVdaFlagBloqvdadesatualizado() {
		return this.vdaFlagBloqvdadesatualizado;
	}

	public void setVdaFlagBloqvdadesatualizado(Integer vdaFlagBloqvdadesatualizado) {
		this.vdaFlagBloqvdadesatualizado = vdaFlagBloqvdadesatualizado;
	}

	public Integer getVdaFlagBloqvdainadimplente() {
		return this.vdaFlagBloqvdainadimplente;
	}

	public void setVdaFlagBloqvdainadimplente(Integer vdaFlagBloqvdainadimplente) {
		this.vdaFlagBloqvdainadimplente = vdaFlagBloqvdainadimplente;
	}

	public Integer getVdaFlagBloqvdasemmovimento() {
		return this.vdaFlagBloqvdasemmovimento;
	}

	public void setVdaFlagBloqvdasemmovimento(Integer vdaFlagBloqvdasemmovimento) {
		this.vdaFlagBloqvdasemmovimento = vdaFlagBloqvdasemmovimento;
	}

	public Integer getVdaFlagCodigobalanca() {
		return this.vdaFlagCodigobalanca;
	}

	public void setVdaFlagCodigobalanca(Integer vdaFlagCodigobalanca) {
		this.vdaFlagCodigobalanca = vdaFlagCodigobalanca;
	}

	public Integer getVdaFlagDescontoprodsPromocao() {
		return this.vdaFlagDescontoprodsPromocao;
	}

	public void setVdaFlagDescontoprodsPromocao(Integer vdaFlagDescontoprodsPromocao) {
		this.vdaFlagDescontoprodsPromocao = vdaFlagDescontoprodsPromocao;
	}

	public Integer getVdaFlagEnviaclicartorio() {
		return this.vdaFlagEnviaclicartorio;
	}

	public void setVdaFlagEnviaclicartorio(Integer vdaFlagEnviaclicartorio) {
		this.vdaFlagEnviaclicartorio = vdaFlagEnviaclicartorio;
	}

	public Integer getVdaFlagExibedadosfiliaisVenda() {
		return this.vdaFlagExibedadosfiliaisVenda;
	}

	public void setVdaFlagExibedadosfiliaisVenda(Integer vdaFlagExibedadosfiliaisVenda) {
		this.vdaFlagExibedadosfiliaisVenda = vdaFlagExibedadosfiliaisVenda;
	}

	public Integer getVdaFlagExibeprodsSimilares() {
		return this.vdaFlagExibeprodsSimilares;
	}

	public void setVdaFlagExibeprodsSimilares(Integer vdaFlagExibeprodsSimilares) {
		this.vdaFlagExibeprodsSimilares = vdaFlagExibeprodsSimilares;
	}

	public Integer getVdaFlagJustificaliberacao() {
		return this.vdaFlagJustificaliberacao;
	}

	public void setVdaFlagJustificaliberacao(Integer vdaFlagJustificaliberacao) {
		this.vdaFlagJustificaliberacao = vdaFlagJustificaliberacao;
	}

	public Integer getVdaFlagUtilizaCliassociados() {
		return this.vdaFlagUtilizaCliassociados;
	}

	public void setVdaFlagUtilizaCliassociados(Integer vdaFlagUtilizaCliassociados) {
		this.vdaFlagUtilizaCliassociados = vdaFlagUtilizaCliassociados;
	}

	public Integer getVdaFlagUtilizatabelaPrecocli() {
		return this.vdaFlagUtilizatabelaPrecocli;
	}

	public void setVdaFlagUtilizatabelaPrecocli(Integer vdaFlagUtilizatabelaPrecocli) {
		this.vdaFlagUtilizatabelaPrecocli = vdaFlagUtilizatabelaPrecocli;
	}

	public Integer getVdaFlagVinculaVendedorcli() {
		return this.vdaFlagVinculaVendedorcli;
	}

	public void setVdaFlagVinculaVendedorcli(Integer vdaFlagVinculaVendedorcli) {
		this.vdaFlagVinculaVendedorcli = vdaFlagVinculaVendedorcli;
	}

	public Integer getVdaFreteTipodefault() {
		return this.vdaFreteTipodefault;
	}

	public void setVdaFreteTipodefault(Integer vdaFreteTipodefault) {
		this.vdaFreteTipodefault = vdaFreteTipodefault;
	}

	public BigDecimal getVdaLimiteCreditoDefault() {
		return this.vdaLimiteCreditoDefault;
	}

	public void setVdaLimiteCreditoDefault(BigDecimal vdaLimiteCreditoDefault) {
		this.vdaLimiteCreditoDefault = vdaLimiteCreditoDefault;
	}

	public Integer getVdaTipoExibedadosfiliasVenda() {
		return this.vdaTipoExibedadosfiliasVenda;
	}

	public void setVdaTipoExibedadosfiliasVenda(Integer vdaTipoExibedadosfiliasVenda) {
		this.vdaTipoExibedadosfiliasVenda = vdaTipoExibedadosfiliasVenda;
	}

	public Integer getVdaTipocodigoTeladevendas() {
		return this.vdaTipocodigoTeladevendas;
	}

	public void setVdaTipocodigoTeladevendas(Integer vdaTipocodigoTeladevendas) {
		this.vdaTipocodigoTeladevendas = vdaTipocodigoTeladevendas;
	}

	public Integer getVdaTipoidentificacaoVendedor() {
		return this.vdaTipoidentificacaoVendedor;
	}

	public void setVdaTipoidentificacaoVendedor(Integer vdaTipoidentificacaoVendedor) {
		this.vdaTipoidentificacaoVendedor = vdaTipoidentificacaoVendedor;
	}

	public Integer getVdaTipolimcreditoCliassociados() {
		return this.vdaTipolimcreditoCliassociados;
	}

	public void setVdaTipolimcreditoCliassociados(Integer vdaTipolimcreditoCliassociados) {
		this.vdaTipolimcreditoCliassociados = vdaTipolimcreditoCliassociados;
	}

	public Integer getVdaTipolimcreditoClientes() {
		return this.vdaTipolimcreditoClientes;
	}

	public void setVdaTipolimcreditoClientes(Integer vdaTipolimcreditoClientes) {
		this.vdaTipolimcreditoClientes = vdaTipolimcreditoClientes;
	}

	public Integer getVdaTiposequenciacli() {
		return this.vdaTiposequenciacli;
	}

	public void setVdaTiposequenciacli(Integer vdaTiposequenciacli) {
		this.vdaTiposequenciacli = vdaTiposequenciacli;
	}	

}