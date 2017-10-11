package models.vendas;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.Hibernate;

import models.compras.Fornecedor;
import models.compras.ItemCodBar;
import models.compras.Segmento;
import models.configuracoes.OperacaoSaida;
import models.financeiro.ContasReceber;
import models.financeiro.Portador;
import models.financeiro.Secao;
import models.recursosHumanos.Funcionario;
import models.vendas.Regiao;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The persistent class for the cliente database table.
 * 
 */
@Entity
@Table(name = "CLIENTE")
// @NamedQuery(name="ClienteAll", query="SELECT c FROM Cliente c")
@NamedQueries({
		@NamedQuery(name = "ClienteById", query = "SELECT c FROM Cliente c " 
				+ "LEFT JOIN FETCH c.regiao reg "
				+ "LEFT JOIN FETCH c.ramoAtividade ram "
				+ "LEFT JOIN FETCH c.convenio con "
				+ "LEFT JOIN FETCH c.rota rot "
				+ "LEFT JOIN FETCH c.vendedor ven "
				+ "LEFT JOIN FETCH c.segmento seg "
				+ "LEFT JOIN FETCH c.funcionario fun "
				+ "LEFT JOIN FETCH c.portador por "
				+ "LEFT JOIN FETCH c.secao sec "
				+ "LEFT JOIN FETCH c.operacaoSaida ops "
				+ "LEFT JOIN FETCH c.planoPagamento pla "
				+ "LEFT JOIN FETCH c.transportadora tra "
				+ " WHERE c.codigo =:codigo AND c.flagAtivo =1 AND c.codemp IN (:codemp)"),
		
		@NamedQuery(name = "ClienteByCNPJ", query = "SELECT c FROM Cliente c "
				+ "LEFT JOIN FETCH c.regiao reg "
				+ "LEFT JOIN FETCH c.ramoAtividade ram "
				+ "LEFT JOIN FETCH c.convenio con "
				+ "LEFT JOIN FETCH c.rota rot "
				+ "LEFT JOIN FETCH c.vendedor ven "
				+ "LEFT JOIN FETCH c.segmento seg "
				+ "LEFT JOIN FETCH c.funcionario fun "
				+ "LEFT JOIN FETCH c.portador por "
				+ "LEFT JOIN FETCH c.secao sec "
				+ "LEFT JOIN FETCH c.operacaoSaida ops "
				+ "LEFT JOIN FETCH c.planoPagamento pla "
				+ "LEFT JOIN FETCH c.transportadora tra "
				+ " WHERE c.cpfCnpj =:cpfCnpj AND c.flagAtivo =1 AND c.codemp IN (:codemp)"),
		
		@NamedQuery(name = "ClienteAll", query = "SELECT c FROM Cliente c "
				+ "LEFT JOIN FETCH c.regiao reg "
				+ "LEFT JOIN FETCH c.ramoAtividade ram "
				+ "LEFT JOIN FETCH c.convenio con "
				+ "LEFT JOIN FETCH c.rota rot "
				+ "LEFT JOIN FETCH c.vendedor ven "
				+ "LEFT JOIN FETCH c.segmento seg "
				+ "LEFT JOIN FETCH c.funcionario fun "
				+ "LEFT JOIN FETCH c.portador por "
				+ "LEFT JOIN FETCH c.secao sec "
				+ "LEFT JOIN FETCH c.operacaoSaida ops "
				+ "LEFT JOIN FETCH c.planoPagamento pla "
				+ "LEFT JOIN FETCH c.transportadora tra "
				+ " WHERE c.flagAtivo IN (:flag) AND c.codemp IN (:codemp)"),
		
		@NamedQuery(name = "ClienteLast", query = "SELECT c FROM Cliente c "
				+ "LEFT JOIN FETCH c.regiao reg "
				+ "LEFT JOIN FETCH c.ramoAtividade ram "
				+ "LEFT JOIN FETCH c.convenio con "
				+ "LEFT JOIN FETCH c.rota rot "
				+ "LEFT JOIN FETCH c.vendedor ven "
				+ "LEFT JOIN FETCH c.segmento seg "
				+ "LEFT JOIN FETCH c.funcionario fun "
				+ "LEFT JOIN FETCH c.portador por "
				+ "LEFT JOIN FETCH c.secao sec "
				+ "LEFT JOIN FETCH c.operacaoSaida ops "
				+ "LEFT JOIN FETCH c.planoPagamento pla "
				+ "LEFT JOIN FETCH c.transportadora tra "
				+ " WHERE c.codemp IN (:codemp) AND c.flagAtivo =1 ORDER BY c.codigo DESC") })
@IdClass(value = ClientePK.class)
public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CHECK_DELETE")
	private BigInteger checkDelete;

	@Id
	private Integer codigo;

	private Integer codemp;

	private String bairro;

	@Column(name = "BANCO_AGENCIA")
	private String bancoAgencia;

	@Column(name = "BANCO_CONTA")
	private String bancoConta;

	@Column(name = "BANCO_CPF_CNPJ")
	private String bancoCpfCnpj;

	@Column(name = "BANCO_DESTINATARIO")
	private String bancoDestinatario;

	@Column(name = "BANCO1_DESCRICAO")
	private String banco1Descricao;

	private String celular;

	private String cep;

	private String cidade;

	@Column(name = "COD_CIDADE")
	private Integer codCidade;

	private String complemento;

	@Column(name = "CONTATO_NOME")
	private String contatoNome;

	@Column(name = "CPF_CNPJ")
	private String cpfCnpj;

	@Column(name = "DATA_ATUALIZACAO")
	private LocalDateTime dataAtualizacao;

	// @Column(name = "DATA_VALIDADE")
	// private LocalDateTime dataValidade;

	@Column(name = "DATA_CADASTRO")
	private LocalDateTime dataCadastro;

	private String email;

	@Column(name = "END_NUMERO")
	private String endNumero;

	private String endereco;

	private String fantasia;

	@Column(name = "FLAG_ATIVO")
	private Integer flagAtivo;

	@Column(name = "FLAG_CONSUMIDOR_FINAL")
	private Integer flagConsumidorFinal;

	@Column(name = "FLAG_INDICADORINSCEST")
	private Integer flagIndicadorinscest;

	@Column(name = "FLAG_TIPOPESSOA")
	private Integer flagTipopessoa;

	private String fone;

	private String fone2;

	@Lob
	private byte[] foto;

	@Column(name = "IDENTIFICACAO_ESTRANGEIRO")
	private String identificacaoEstrangeiro;

	@Column(name = "IE_RG")
	private String ieRg;

	@Column(name = "INSC_PRODRURAL")
	private String inscProdrural;

	@Column(name = "INTERNET_SITE")
	private String internetSite;

	@Column(name = "LAST_CODUSER")
	private Integer lastCoduser;

	@Column(name = "LAST_MOVTO")
	private LocalDateTime lastMovto;

	@Column(name = "LIMITE_CREDITO")
	private BigDecimal limiteCredito;

	@Column(name = "ORIGEM_LIMITE_CREDITO")
	private Integer origemLimiteCredito;

	private String razao;

	@Column(name = "RECORD_NO")
	private Integer recordNo;

	private String suframa;

	@Column(name = "TIPO_FATURAMENTO")
	private Integer tipoFaturamento;

	private String uf;

	private BigInteger utctag;

	@Column(name = "TIPO_PRECO_VENDA")
	private Integer tipoPrecoVenda;

	@Column(name = "TIPO_FRETE")
	private Integer tipoFrete;

	@Column(name = "DESCONTO_MAXIMO")
	private BigDecimal descontoMaximo;

	@Column(name = "FLAG_VENDA_ATACADO")
	private Integer flagVendaAtacado;
	
	@Column(name = "OBSERVACAO")
	private String observacao;
	
	@Column(name = "OBS_RESTRITIVA")
	private String obsRestritiva;
	
	@Column(name = "FLAG_COBRANCA_CARTORIO")
	private Integer flagCobrancaCartorio;
	
	@Column(name = "COBRANCA_CARTORIO_DIAS")
	private Integer cobrancaCartorioDias;
	

	// ------ REGIAO --------------

	@Column(name = "COD_REGIAO")
	private Integer codRegiao;
	
	@Column(name = "COD_REGIAO_FK")
	private BigInteger codRegiaoFk;
	
	
	
	public Integer getCodRegiao() {
		return codRegiao;
	}

	public void setCodRegiao(Integer codRegiao) {
		this.codRegiao = codRegiao;
	}

	public BigInteger getCodRegiaoFk() {
		return codRegiaoFk;
	}

	public void setCodRegiaoFk(BigInteger codRegiaoFk) {
		this.codRegiaoFk = codRegiaoFk;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "COD_REGIAO", referencedColumnName = "CODIGO" , insertable=false, updatable=false),
			@JoinColumn(name = "COD_REGIAO_FK", referencedColumnName = "CHECK_DELETE", insertable=false, updatable=false), })
	private Regiao regiao;

	public void setRegiao(Regiao regiao) {
		this.regiao = regiao;
	}

	public Regiao getRegiao() {
		if (!Hibernate.isInitialized(this.regiao)) {
			try {
				Hibernate.initialize(this.regiao);
			} catch (Exception one) {
				this.regiao = null;
			}
		}
		return this.regiao;
	}

	@Transient
	private String regiaoDescricao;

	public String getRegiaoDescricao() {
		this.regiaoDescricao = "";
		if (Hibernate.isInitialized(this.regiao)) {
			try {
				this.regiaoDescricao = this.regiao.getDescricao();
			} catch (Exception o) {
				regiao = null;
			}
		}
		return this.regiaoDescricao;
	}

	// ------ RAMO DE ATIVIDADES --------------
	
	@Column(name = "COD_RAMO_ATIVIDADE")
	private Integer codRamoAtividade;
	
	@Column(name = "COD_RAMO_ATIVIDADE_FK")
	private BigInteger codRamoAtividadeFk;	
	

	public Integer getCodRamoAtividade() {
		return codRamoAtividade;
	}

	public void setCodRamoAtividade(Integer codRamoAtividade) {
		this.codRamoAtividade = codRamoAtividade;
	}

	public BigInteger getCodRamoAtividadeFk() {
		return codRamoAtividadeFk;
	}

	public void setCodRamoAtividadeFk(BigInteger codRamoAtividadeFk) {
		this.codRamoAtividadeFk = codRamoAtividadeFk;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "COD_RAMO_ATIVIDADE", referencedColumnName = "CODIGO", insertable=false, updatable=false),
			@JoinColumn(name = "COD_RAMO_ATIVIDADE_FK", referencedColumnName = "CHECK_DELETE", insertable=false, updatable=false), })
	private RamoAtividade ramoAtividade;

	public void setRamoAtividade(RamoAtividade ramoAtividade) {
		this.ramoAtividade = ramoAtividade;
	}

	public RamoAtividade getRamoAtividade() {
		if (!Hibernate.isInitialized(this.ramoAtividade)) {
			try {
				Hibernate.initialize(this.ramoAtividade);
			} catch (Exception one) {
				this.ramoAtividade = null;
			}
		}
		return this.ramoAtividade;
	}

	@Transient
	private String ramoAtividadeDescricao;

	public String getRamoAtividadeDescricao() {
		this.ramoAtividadeDescricao = "";
		if (Hibernate.isInitialized(this.ramoAtividade)) {
			try {
				this.ramoAtividadeDescricao = this.ramoAtividade.getDescricao();
			} catch (Exception o) {
				ramoAtividade = null;
			}
		}
		return this.ramoAtividadeDescricao;
	}

	// ------ CONVENIO --------------
	
	@Column(name = "COD_CONVENIO")
	private Integer codConvenio;
	
	@Column(name = "COD_CONVENIO_FK")
	private BigInteger codConvenioFk;	
	
	public Integer getCodConvenio() {
		return codConvenio;
	}

	public void setCodConvenio(Integer codConvenio) {
		this.codConvenio = codConvenio;
	}

	public BigInteger getCodConvenioFk() {
		return codConvenioFk;
	}

	public void setCodConvenioFk(BigInteger codConvenioFk) {
		this.codConvenioFk = codConvenioFk;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "COD_CONVENIO", referencedColumnName = "CODIGO", insertable=false, updatable=false),
			@JoinColumn(name = "COD_CONVENIO_FK", referencedColumnName = "CHECK_DELETE", insertable=false, updatable=false), })
	private Convenio convenio;

	public void setConvenio(Convenio convenio) {
		this.convenio = convenio;
	}

	public Convenio getConvenio() {
		if (!Hibernate.isInitialized(this.convenio)) {
			try {
				Hibernate.initialize(this.convenio);
			} catch (Exception one) {
				this.convenio = null;
			}
		}
		return this.convenio;
	}

	@Transient
	private String convenioDescricao;

	public String getConvenioDescricao() {
		this.convenioDescricao = "";
		if (Hibernate.isInitialized(this.convenio)) {
			try {
				this.convenioDescricao = this.convenio.getDescricao();
			} catch (Exception o) {
				convenio = null;
			}
		}
		return this.convenioDescricao;
	}

	// ------ ROTAS --------------
	
	@Column(name = "COD_ROTA")
	private Integer codRota;
	
	@Column(name = "COD_ROTA_FK")
	private BigInteger codRotaFk;
	
	public Integer getCodRota() {
		return codRota;
	}

	public void setCodRota(Integer codRota) {
		this.codRota = codRota;
	}

	public BigInteger getCodRotaFk() {
		return codRotaFk;
	}

	public void setCodRotaFk(BigInteger codRotaFk) {
		this.codRotaFk = codRotaFk;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "COD_ROTA", referencedColumnName = "CODIGO", insertable=false, updatable=false),
			@JoinColumn(name = "COD_ROTA_FK", referencedColumnName = "CHECK_DELETE", insertable=false, updatable=false), })
	private Rota rota;

	public void setRota(Rota rota) {
		this.rota = rota;
	}

	public Rota getRota() {
		if (!Hibernate.isInitialized(this.rota)) {
			try {
				Hibernate.initialize(this.rota);
			} catch (Exception one) {
				this.rota = null;
			}
		}
		return this.rota;
	}

	@Transient
	private String rotaDescricao;

	public String getRotaDescricao() {
		this.rotaDescricao = "";
		if (Hibernate.isInitialized(this.rota)) {
			try {
				this.rotaDescricao = this.rota.getDescricao();
			} catch (Exception o) {
				rota = null;
			}
		}
		return this.rotaDescricao;
	}

	// ------ FUNCIONARIO --------------
	
	@Column(name = "COD_FUNCIONARIO")
	private Integer codFuncionario;
	
	@Column(name = "COD_FUNCIONARIO_FK")
	private BigInteger codFuncionarioFk;
	
	public Integer getCodFuncionario() {
		return codFuncionario;
	}

	public void setCodFuncionario(Integer codFuncionario) {
		this.codFuncionario = codFuncionario;
	}

	public BigInteger getCodFuncionarioFk() {
		return codFuncionarioFk;
	}

	public void setCodFuncionarioFk(BigInteger codFuncionarioFk) {
		this.codFuncionarioFk = codFuncionarioFk;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "COD_FUNCIONARIO", referencedColumnName = "CODIGO", insertable=false, updatable=false),
			@JoinColumn(name = "COD_FUNCIONARIO_FK", referencedColumnName = "CHECK_DELETE", insertable=false, updatable=false), })
	private Funcionario funcionario;

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Funcionario getFuncionario() {
		if (!Hibernate.isInitialized(this.funcionario)) {
			try {
				Hibernate.initialize(this.funcionario);
			} catch (Exception one) {
				this.funcionario = null;
			}
		}
		return this.funcionario;
	}

	@Transient
	private String funcionarioDescricao;

	public String getFuncionarioDescricao() {
		this.funcionarioDescricao = "";
		if (Hibernate.isInitialized(this.funcionario)) {
			try {
				this.funcionarioDescricao = this.funcionario.getDescricao();
			} catch (Exception o) {
				funcionario = null;
			}
		}
		return this.funcionarioDescricao;
	}

	// ------ VENDEDOR --------------
	
	@Column(name = "COD_VENDEDOR")
	private Integer codVendedor;
	
	@Column(name = "COD_VENDEDOR_FK")
	private BigInteger codVendedorFk;
	
	public Integer getCodVendedor() {
		return codVendedor;
	}

	public void setCodVendedor(Integer codVendedor) {
		this.codVendedor = codVendedor;
	}

	public BigInteger getCodVendedorFk() {
		return codVendedorFk;
	}

	public void setCodVendedorFk(BigInteger codVendedorFk) {
		this.codVendedorFk = codVendedorFk;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "COD_VENDEDOR", referencedColumnName = "CODIGO", insertable=false, updatable=false),
			@JoinColumn(name = "COD_VENDEDOR_FK", referencedColumnName = "CHECK_DELETE", insertable=false, updatable=false), })
	private Funcionario vendedor;

	public void setVendedor(Funcionario vendedor) {
		this.vendedor = vendedor;
	}

	public Funcionario getVendedor() {
		if (!Hibernate.isInitialized(this.vendedor)) {
			try {
				Hibernate.initialize(this.vendedor);
			} catch (Exception one) {
				this.vendedor = null;
			}
		}
		return this.vendedor;
	}

	@Transient
	private String vendedorDescricao;

	public String getVendedorDescricao() {
		this.vendedorDescricao = "";
		if (Hibernate.isInitialized(this.vendedor)) {
			try {
				this.vendedorDescricao = this.vendedor.getDescricao();
			} catch (Exception o) {
				vendedor = null;
			}
		}
		return this.vendedorDescricao;
	}

	// --------- SEGMENTO --------------
	
	@Column(name = "COD_SEGMENTO")
	private Integer codSegmento;
	
	@Column(name = "COD_SEGMENTO_FK")
	private BigInteger codSegmentoFk;
	
	public Integer getCodSegmento() {
		return codSegmento;
	}

	public void setCodSegmento(Integer codSegmento) {
		this.codSegmento = codSegmento;
	}

	public BigInteger getCodSegmentoFk() {
		return codSegmentoFk;
	}

	public void setCodSegmentoFk(BigInteger codSegmentoFk) {
		this.codSegmentoFk = codSegmentoFk;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "COD_SEGMENTO", referencedColumnName = "CODIGO", insertable=false, updatable=false),
			@JoinColumn(name = "COD_SEGMENTO_FK", referencedColumnName = "CHECK_DELETE", insertable=false, updatable=false), })
	private Segmento segmento;

	public void setSegmento(Segmento segmento) {
		this.segmento = segmento;
	}

	public Segmento getSegmento() {
		if (!Hibernate.isInitialized(this.segmento)) {
			try {
				Hibernate.initialize(this.segmento);
			} catch (Exception one) {
				this.segmento = null;
			}
		}
		return this.segmento;
	}

	@Transient
	private String segmentoDescricao;

	public String getSegmentoDescricao() {
		this.segmentoDescricao = "";
		if (Hibernate.isInitialized(this.segmento)) {
			try {
				this.segmentoDescricao = this.segmento.getDescricao();
			} catch (Exception o) {
				segmento = null;
			}
		}
		return this.segmentoDescricao;
	}

	// ------ SECAO --------------
	
	@Column(name = "COD_SECAO")
	private Integer codSecao;
	
	@Column(name = "COD_SECAO_FK")
	private BigInteger codSecaoFk;	

	public Integer getCodSecao() {
		return codSecao;
	}

	public void setCodSecao(Integer codSecao) {
		this.codSecao = codSecao;
	}

	public BigInteger getCodSecaoFk() {
		return codSecaoFk;
	}

	public void setCodSecaoFk(BigInteger codSecaoFk) {
		this.codSecaoFk = codSecaoFk;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "COD_SECAO", referencedColumnName = "CODIGO", insertable=false, updatable=false),
			@JoinColumn(name = "COD_SECAO_FK", referencedColumnName = "CHECK_DELETE", insertable=false, updatable=false), })
	private Secao secao;

	public void setSecao(Secao secao) {
		this.secao = secao;
	}

	public Secao getSecao() {
		if (!Hibernate.isInitialized(this.secao)) {
			try {
				Hibernate.initialize(this.secao);
			} catch (Exception one) {
				this.secao = null;
			}
		}
		return this.secao;
	}

	@Transient
	private String secaoDescricao;

	public String getSecaoDescricao() {
		this.secaoDescricao = "";
		if (Hibernate.isInitialized(this.secao)) {
			try {
				this.secaoDescricao = this.secao.getDescricao();
			} catch (Exception o) {
				secao = null;
			}
		}
		return this.secaoDescricao;
	}

	// ------ PORTADOR --------------
	
	@Column(name = "COD_PORTADOR")
	private Integer codPortador;
	
	@Column(name = "COD_PORTADOR_FK")
	private BigInteger codPortadorFk;
	
	public Integer getCodPortador() {
		return codPortador;
	}

	public void setCodPortador(Integer codPortador) {
		this.codPortador = codPortador;
	}

	public BigInteger getCodPortadorFk() {
		return codPortadorFk;
	}

	public void setCodPortadorFk(BigInteger codPortadorFk) {
		this.codPortadorFk = codPortadorFk;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "COD_PORTADOR", referencedColumnName = "CODIGO", insertable=false, updatable=false),
			@JoinColumn(name = "COD_PORTADOR_FK", referencedColumnName = "CHECK_DELETE", insertable=false, updatable=false), })
	private Portador portador;

	public void setPortador(Portador portador) {
		this.portador = portador;
	}

	public Portador getPortador() {
		if (!Hibernate.isInitialized(this.portador)) {
			try {
				Hibernate.initialize(this.portador);
			} catch (Exception one) {
				this.portador = null;
			}
		}
		return this.portador;
	}

	@Transient
	private String portadorDescricao;

	public String getPortadorDescricao() {
		this.portadorDescricao = "";
		if (Hibernate.isInitialized(this.portador)) {
			try {
				this.portadorDescricao = this.portador.getDescricao();
			} catch (Exception o) {
				portador = null;
			}
		}
		return this.portadorDescricao;
	}

	// ------ OPERACAO SAIDA --------------
	
	@Column(name = "COD_OPERACAO_SAIDA")
	private Integer codOperacaoSaida;
	
	@Column(name = "COD_OPERACAO_SAIDA_FK")
	private BigInteger codOperacaoSaidaFk;
	
	public Integer getCodOperacaoSaida() {
		return codOperacaoSaida;
	}

	public void setCodOperacaoSaida(Integer codOperacaoSaida) {
		this.codOperacaoSaida = codOperacaoSaida;
	}

	public BigInteger getCodOperacaoSaidaFk() {
		return codOperacaoSaidaFk;
	}

	public void setCodOperacaoSaidaFk(BigInteger codOperacaoSaidaFk) {
		this.codOperacaoSaidaFk = codOperacaoSaidaFk;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "COD_OPERACAO_SAIDA", referencedColumnName = "CODIGO", insertable=false, updatable=false),
			@JoinColumn(name = "COD_OPERACAO_SAIDA_FK", referencedColumnName = "CHECK_DELETE", insertable=false, updatable=false), })
	private OperacaoSaida operacaoSaida;

	public void setOperacaoSaida(OperacaoSaida operacaoSaida) {
		this.operacaoSaida = operacaoSaida;
	}

	public OperacaoSaida getOperacaoSaida() {
		if (!Hibernate.isInitialized(this.operacaoSaida)) {
			try {
				Hibernate.initialize(this.operacaoSaida);
			} catch (Exception one) {
				this.operacaoSaida = null;
			}
		}
		return this.operacaoSaida;
	}

	@Transient
	private String operacaoSaidaDescricao;

	public String getOperacaoSaidaDescricao() {
		this.operacaoSaidaDescricao = "";
		if (Hibernate.isInitialized(this.operacaoSaida)) {
			try {
				this.operacaoSaidaDescricao = this.operacaoSaida.getDescricao();
			} catch (Exception o) {
				operacaoSaida = null;
			}
		}
		return this.operacaoSaidaDescricao;
	}

	// ------ PLANO PAGAMENTO --------------
	
	@Column(name = "COD_PLANO_PAGAMENTO")
	private Integer codPlanoPagamento;
	
	@Column(name = "COD_PLANO_PAGAMENTO_FK")
	private BigInteger codPlanoPagamentoFk;
	
	public Integer getCodPlanoPagamento() {
		return codPlanoPagamento;
	}

	public void setCodPlanoPagamento(Integer codPlanoPagamento) {
		this.codPlanoPagamento = codPlanoPagamento;
	}

	public BigInteger getCodPlanoPagamentoFk() {
		return codPlanoPagamentoFk;
	}

	public void setCodPlanoPagamentoFk(BigInteger codPlanoPagamentoFk) {
		this.codPlanoPagamentoFk = codPlanoPagamentoFk;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "COD_PLANO_PAGAMENTO", referencedColumnName = "CODIGO", insertable=false, updatable=false),
			@JoinColumn(name = "COD_PLANO_PAGAMENTO_FK", referencedColumnName = "CHECK_DELETE", insertable=false, updatable=false), })
	private PlanoPagamento planoPagamento;

	public void setPlanoPagamento(PlanoPagamento planoPagamento) {
		this.planoPagamento = planoPagamento;
	}

	public PlanoPagamento getPlanoPagamento() {
		if (!Hibernate.isInitialized(this.planoPagamento)) {
			try {
				Hibernate.initialize(this.planoPagamento);
			} catch (Exception one) {
				this.planoPagamento = null;
			}
		}
		return this.planoPagamento;
	}

	@Transient
	private String planoPagamentoDescricao;

	public String getPlanoPagamentoDescricao() {
		this.planoPagamentoDescricao = "";
		if (Hibernate.isInitialized(this.planoPagamento)) {
			try {
				this.planoPagamentoDescricao = this.planoPagamento.getDescricao();
			} catch (Exception o) {
				planoPagamento = null;
			}
		}
		return this.planoPagamentoDescricao;
	}

	
	// ------ TRASNPORTADORA --------------
	
	@Column(name = "COD_TRANSPORTADORA")
	private Integer codTransportadora;
	
	@Column(name = "COD_TRANSPORTADORA_FK")
	private BigInteger codTransportadoraFk;
	
	public Integer getCodTransportadora() {
		return codTransportadora;
	}

	public void setCodTransportadora(Integer codTransportadora) {
		this.codTransportadora = codTransportadora;
	}

	public BigInteger getCodTransportadoraFk() {
		return codTransportadoraFk;
	}

	public void setCodTransportadoraFk(BigInteger codTransportadoraFk) {
		this.codTransportadoraFk = codTransportadoraFk;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "COD_TRANSPORTADORA", referencedColumnName = "CODIGO", insertable=false, updatable=false),
			@JoinColumn(name = "COD_TRANSPORTADORA_FK", referencedColumnName = "CHECK_DELETE", insertable=false, updatable=false), })
	private Fornecedor transportadora;

	public void setTransportadora(Fornecedor transportadora) {
		this.transportadora = transportadora;
	}

	public Fornecedor getTransportadora() {
		if (!Hibernate.isInitialized(this.transportadora)) {
			try {
				Hibernate.initialize(this.transportadora);
			} catch (Exception one) {
				this.transportadora = null;
			}
		}
		return this.transportadora;
	}

	@Transient
	private String transportadoraDescricao;

	public String getTransportadoraDescricao() {
		this.transportadoraDescricao = "";
		if (Hibernate.isInitialized(this.transportadora)) {
			try {
				this.transportadoraDescricao = this.transportadora.getRazao();
			} catch (Exception o) {
				transportadora = null;
			}
		}
		return this.transportadoraDescricao;
	}
	
	
	// bi-directional many-to-one association to ClienteEndEntrega
	@OneToMany(mappedBy = "cliente", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	private Set<ClienteEndEntrega> clienteEndEntrega = new HashSet<ClienteEndEntrega>();
	
	@OneToMany(mappedBy = "cliente", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	private Set<ContasReceber> contasReceber = new HashSet<ContasReceber>();
	
	public Cliente() {
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

	public String getBairro() {
		return this.bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getBancoAgencia() {
		return this.bancoAgencia;
	}

	public void setBancoAgencia(String bancoAgencia) {
		this.bancoAgencia = bancoAgencia;
	}

	public String getBancoConta() {
		return this.bancoConta;
	}

	public void setBancoConta(String bancoConta) {
		this.bancoConta = bancoConta;
	}

	public String getBancoCpfCnpj() {
		return this.bancoCpfCnpj;
	}

	public void setBancoCpfCnpj(String bancoCpfCnpj) {
		this.bancoCpfCnpj = bancoCpfCnpj;
	}

	public String getBancoDestinatario() {
		return this.bancoDestinatario;
	}

	public void setBancoDestinatario(String bancoDestinatario) {
		this.bancoDestinatario = bancoDestinatario;
	}

	public String getBanco1Descricao() {
		return this.banco1Descricao;
	}

	public void setBanco1Descricao(String banco1Descricao) {
		this.banco1Descricao = banco1Descricao;
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

	// public Integer getCodConvenio() {
	// return this.codConvenio;
	// }
	//
	// public void setCodConvenio(Integer codConvenio) {
	// this.codConvenio = codConvenio;
	// }

	// public Integer getCodFuncionario() {
	// return this.codFuncionario;
	// }
	//
	// public void setCodFuncionario(Integer codFuncionario) {
	// this.codFuncionario = codFuncionario;
	// }

	// public Integer getCodRamoAtividade() {
	// return this.codRamoAtividade;
	// }
	//
	// public void setCodRamoAtividade(Integer codRamoAtividade) {
	// this.codRamoAtividade = codRamoAtividade;
	// }

	// public Integer getCodRegiao() {
	// return this.codRegiao;
	// }
	//
	// public void setCodRegiao(Integer codRegiao) {
	// this.codRegiao = codRegiao;
	// }

	// public Integer getCodRota() {
	// return this.codRota;
	// }
	//
	// public void setCodRota(Integer codRota) {
	// this.codRota = codRota;
	// }

	// public Integer getCodSegmento() {
	// return this.codSegmento;
	// }
	//
	// public void setCodSegmento(Integer codSegmento) {
	// this.codSegmento = codSegmento;
	// }

	// public Integer getCodVendedor() {
	// return this.codVendedor;
	// }
	//
	// public void setCodVendedor(Integer codVendedor) {
	// this.codVendedor = codVendedor;
	// }

	public String getComplemento() {
		return this.complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getContatoNome() {
		return this.contatoNome;
	}

	public void setContatoNome(String contatoNome) {
		this.contatoNome = contatoNome;
	}

	public String getCpfCnpj() {
		return this.cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public LocalDateTime getDataAtualizacao() {
		return this.dataAtualizacao;
	}

	public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	// public LocalDateTime getDataValidade() {
	// return dataValidade;
	// }
	//
	// public void setDataValidade(LocalDateTime dataValidade) {
	// this.dataValidade = dataValidade;
	// }

	public Integer getTipoPrecoVenda() {
		return tipoPrecoVenda;
	}

	public void setTipoPrecoVenda(Integer tipoPrecoVenda) {
		this.tipoPrecoVenda = tipoPrecoVenda;
	}

	public Integer getTipoFrete() {
		return tipoFrete;
	}

	public void setTipoFrete(Integer tipoFrete) {
		this.tipoFrete = tipoFrete;
	}

	public BigDecimal getDescontoMaximo() {
		return descontoMaximo;
	}

	public void setDescontoMaximo(BigDecimal descontoMaximo) {
		this.descontoMaximo = descontoMaximo;
	}

	public Integer getFlagVendaAtacado() {
		return flagVendaAtacado;
	}

	public void setFlagVendaAtacado(Integer flagVendaAtacado) {
		this.flagVendaAtacado = flagVendaAtacado;
	}

	public LocalDateTime getDataCadastro() {
		return this.dataCadastro;
	}

	public void setDataCadastro(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
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

	public Integer getFlagConsumidorFinal() {
		return this.flagConsumidorFinal;
	}

	public void setFlagConsumidorFinal(Integer flagConsumidorFinal) {
		this.flagConsumidorFinal = flagConsumidorFinal;
	}

	public Integer getFlagIndicadorinscest() {
		return this.flagIndicadorinscest;
	}

	public void setFlagIndicadorinscest(Integer flagIndicadorinscest) {
		this.flagIndicadorinscest = flagIndicadorinscest;
	}

	public Integer getFlagTipopessoa() {
		return this.flagTipopessoa;
	}

	public void setFlagTipopessoa(Integer flagTipopessoa) {
		this.flagTipopessoa = flagTipopessoa;
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

	public byte[] getFoto() {
		return this.foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public String getIdentificacaoEstrangeiro() {
		return this.identificacaoEstrangeiro;
	}

	public void setIdentificacaoEstrangeiro(String identificacaoEstrangeiro) {
		this.identificacaoEstrangeiro = identificacaoEstrangeiro;
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

	public void setLastCoduser(int lastCoduser) {
		this.lastCoduser = lastCoduser;
	}

	public LocalDateTime getLastMovto() {
		return this.lastMovto;
	}

	public void setLastMovto(LocalDateTime lastMovto) {
		this.lastMovto = lastMovto;
	}

	public BigDecimal getLimiteCredito() {
		return this.limiteCredito;
	}

	public void setLimiteCredito(BigDecimal limiteCredito) {
		this.limiteCredito = limiteCredito;
	}

	public Integer getOrigemLimiteCredito() {
		return this.origemLimiteCredito;
	}

	public void setOrigemLimiteCredito(Integer origemLimiteCredito) {
		this.origemLimiteCredito = origemLimiteCredito;
	}

	public String getRazao() {
		return this.razao;
	}

	public void setRazao(String razao) {
		this.razao = razao;
	}

	public Integer getRecordNo() {
		return this.recordNo;
	}

	public void setRecordNo(Integer recordNo) {
		this.recordNo = recordNo;
	}

	public String getSuframa() {
		return this.suframa;
	}

	public void setSuframa(String suframa) {
		this.suframa = suframa;
	}

	public Integer getTipoFaturamento() {
		return this.tipoFaturamento;
	}

	public void setTipoFaturamento(Integer tipoFaturamento) {
		this.tipoFaturamento = tipoFaturamento;
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

	public Set<ClienteEndEntrega> getClienteEndEntregas() {
		return clienteEndEntrega;
	}

	public void setClienteEndEntregas(Set<ClienteEndEntrega> clienteEndEntregas) {
		this.clienteEndEntrega = clienteEndEntregas;
	}

	public Set<ContasReceber> getContasReceber() {
		return contasReceber;
	}

	public void setContasReceber(Set<ContasReceber> contasReceber) {
		this.contasReceber = contasReceber;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getObsRestritiva() {
		return obsRestritiva;
	}

	public void setObsRestritiva(String obsRestritiva) {
		this.obsRestritiva = obsRestritiva;
	}

	public Integer getFlagCobrancaCartorio() {
		return flagCobrancaCartorio;
	}

	public void setFlagCobrancaCartorio(Integer flagCobrancaCartorio) {
		this.flagCobrancaCartorio = flagCobrancaCartorio;
	}

	public Integer getCobrancaCartorioDias() {
		return cobrancaCartorioDias;
	}

	public void setCobrancaCartorioDias(Integer cobrancaCartorioDias) {
		this.cobrancaCartorioDias = cobrancaCartorioDias;
	}
	
	
	

//	public Set<ClienteEndEntrega> getClienteEndEntregas() {
//		return this.clienteEndEntregas;
//	}
//
//	public void setClienteEndEntregas(Set<ClienteEndEntrega> clienteEndEntregas) {
//		this.clienteEndEntregas = clienteEndEntregas;
//	}

//	public ClienteEndEntrega addClienteEndEntrega(ClienteEndEntrega clienteEndEntrega) {
//		getClienteEndEntregas().add(clienteEndEntrega);
//		clienteEndEntrega.setCliente(this);
//
//		return clienteEndEntrega;
//	}
//
//	public ClienteEndEntrega removeClienteEndEntrega(ClienteEndEntrega clienteEndEntrega) {
//		getClienteEndEntregas().remove(clienteEndEntrega);
//		clienteEndEntrega.setCliente(null);
//
//		return clienteEndEntrega;
//	}

}