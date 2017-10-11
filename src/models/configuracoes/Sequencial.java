package models.configuracoes;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * The persistent class for the sequencial database table.
 * 
 */
@Entity
// @NamedQuery(name="Sequencial.findAll", query="SELECT s FROM Sequencial s")
@Table(name = "SEQUENCIAL")
@IdClass(value = SequencialPK.class)
public class Sequencial implements Serializable {
	private static final long serialVersionUID = 1L;

//	@EmbeddedId
//	private SequencialPK id;

	@Id
	private Integer codemp;
	
	@Id
	@Column(name = "CHECK_DELETE")
	private BigInteger checkDelete;
	
//	@OneToOne
//	@JoinColumns({  
//	  @JoinColumn(name = "CODEMP", referencedColumnName = "CODEMP"),    
//	  @JoinColumn(name = "CHECK_DELETE", referencedColumnName = "CHECK_DELETE")
//	})
//	private Empresa empresa;
//	
//	
//	public Empresa getEmpresa() {
//		return empresa;
//	}
//
//	public void setEmpresa(Empresa empresa) {
//		this.empresa = empresa;
//	}

	@Column(name = "RECORD_NO")
	private Integer recordNo;
	

	@Column(name = "LAST_CODUSER")
	private Integer lastCoduser;

	@Column(name = "LAST_MOVTO")
	private LocalDateTime lastMovto;

	@Column(name = "RECORD_DELETE")
	private Integer recordDelete;

	@Column(name = "RECORD_NUMBER")
	private Integer recordNumber;

	@Column(name = "RECORD_SESSION")
	private Integer recordSession;

	private BigInteger utctag;

	@Column(name = "CP_DEPARTAMENTO")
	private Integer cpDepartamento;

	@Column(name = "CP_UNIDADE")
	private Integer cpUnidade;

	@Column(name = "CP_FORNECEDOR")
	private Integer cpFornecedor;

	@Column(name = "CP_FABRICANTE")
	private Integer cpFabricante;
	
	@Column(name = "CP_GRUPO")
	private Integer cpGrupo;

	@Column(name = "CP_SUBGRUPO")
	private Integer cpSubGrupo;

	@Column(name = "CP_COR_ESTILO")
	private Integer cpCorEstilo;

	@Column(name = "CP_BOX_LOCAL_ESTOQUE")
	private Integer cpBoxLocalEstoque;
	
	@Column(name = "CP_APLICACAO")
	private Integer cpAplicacao;

	@Column(name = "CP_TABELA_NCM")
	private Integer cpTabelaNCM;

	@Column(name = "CP_TABELA_LC_SERVICO")
	private Integer cpTabelaLcServico;

	@Column(name = "CP_ITEM")
	private Integer cpItem;

	@Column(name = "CP_FAMILIA_PRECO")
	private Integer cpFamiliaPreco;
	
	@Column(name = "CP_GRADE")
	private Integer cpGrade;

	@Column(name = "CP_TRIBUTACAO")
	private Integer cpTributacao;

	
	@Column(name = "VD_PAIS")
	private Integer vdPais;
	
	@Column(name = "VD_UF")
	private Integer vdUF;
	
	@Column(name = "VD_CIDADE")
	private Integer vdCidade;
	
	@Column(name = "VD_CLIENTE")
	private Integer vdCliente;
	
	@Column(name = "VD_ROTA")
	private Integer vdRota;
	
	@Column(name = "VD_RAMO_ATIVIDADE")
	private Integer vdRamoAtividade;
	
	@Column(name = "VD_REGIAO")
	private Integer vdRegiao;
	
	@Column(name = "VD_PLANO_PAGAMENTO")
	private Integer vdPlanoPagamento;
	
	@Column(name = "VD_CONVENIO")
	private Integer vdConvenio;
	
	@Column(name = "VD_SEGMENTO")
	private Integer vdSegmento;	
	
	@Column(name = "VD_MENSAGEM_NF")
	private Integer vdMensagemNf;	
	
	@Column(name = "FC_SECAO")
	private Integer fcSecao;	
	
	@Column(name = "FC_MOEDA")
	private Integer fcMoeda;
	
	@Column(name = "FC_PORTADOR")
	private Integer fcPortador;
	
	@Column(name = "FC_CENTRO_CUSTO")
	private Integer fcCentroCusto;
	
	@Column(name = "FC_PLANO_CONTAS")
	private Integer fcPlanoConta;
	
	@Column(name = "FC_CAIXA_BANCO")
	private Integer fcCaixaBanco;
	
	@Column(name = "FC_CONTAS_RECEBER")
	private Integer fcContasReceber;	
	
	@Column(name = "CNF_USUARIO")
	private Integer cnfUsuario;
	
	@Column(name = "CNF_OPERACAO_ENTRADA")
	private Integer cnfOperacaoEntrada;
	
	@Column(name = "CNF_DEPOSITO_ESTOQUE")
	private Integer cnfDepositoEstoque;	
	
	@Column(name = "CNF_OPERACAO_SAIDA")
	private Integer cnfOperacaoSaida;
	
	@Column(name = "CNF_OPERACAO_FINANCEIRO")
	private Integer cnfOperacaoFinaceiro;
	
	@Column(name = "CNF_LAYOUT_ARQUIVOS")
	private Integer cnfLayoutArquivos;	
	
	@Column(name = "RH_FUNCIONARIO")
	private Integer rhFuncionario;
	
	

//	// bi-directional one-to-one association to Sequencial
//	@OneToOne
//	//@JoinColumn(name = "CHECK_DELETE")
//	@PrimaryKeyJoinColumn
//	private Empresa empresa;	

//	@Version
//	private Integer versao;
	
	public Sequencial() {
	}

//	public SequencialPK getId() {
//		return this.id;
//	}
//
//	public void setId(SequencialPK id) {
//		this.id = id;
//	}
	
	

	public BigInteger getCheckDelete() {
		return this.checkDelete;
	}

	public Integer getRhFuncionario() {
		return rhFuncionario;
	}

	public void setRhFuncionario(Integer rhFuncionario) {
		this.rhFuncionario = rhFuncionario;
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

	public void setCheckDelete(BigInteger checkDelete) {
		this.checkDelete = checkDelete;
	}

	public Integer getCpDepartamento() {
		return this.cpDepartamento;
	}

	public void setCpDepartamento(Integer cpDepartamento) {
		this.cpDepartamento = cpDepartamento;
	}

	public Integer getCpUnidade() {
		return this.cpUnidade;
	}

	public void setCpUnidade(Integer cpUnidade) {
		this.cpUnidade = cpUnidade;
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

	public Integer getRecordDelete() {
		return this.recordDelete;
	}

	public void setRecordDelete(Integer recordDelete) {
		this.recordDelete = recordDelete;
	}

	public Integer getRecordNumber() {
		return this.recordNumber;
	}

	public void setRecordNumber(Integer recordNumber) {
		this.recordNumber = recordNumber;
	}

	public Integer getRecordSession() {
		return this.recordSession;
	}

	public void setRecordSession(Integer recordSession) {
		this.recordSession = recordSession;
	}

	public BigInteger getUtctag() {
		return this.utctag;
	}

	public void setUtctag(BigInteger utctag) {
		this.utctag = utctag;
	}


	public Integer getCpFornecedor() {
		return cpFornecedor;
	}

	public void setCpFornecedor(Integer cpFornecedor) {
		this.cpFornecedor = cpFornecedor;
	}

	public Integer getCpFabricante() {
		return cpFabricante;
	}

	public void setCpFabricante(Integer cpFabricante) {
		this.cpFabricante = cpFabricante;
	}

	public Integer getCpGrupo() {
		return cpGrupo;
	}

	public void setCpGrupo(Integer cpGrupo) {
		this.cpGrupo = cpGrupo;
	}

	public Integer getCpSubGrupo() {
		return cpSubGrupo;
	}

	public void setCpSubGrupo(Integer cpSubGrupo) {
		this.cpSubGrupo = cpSubGrupo;
	}

	public Integer getCpCorEstilo() {
		return cpCorEstilo;
	}

	public void setCpCorEstilo(Integer cpCorEstilo) {
		this.cpCorEstilo = cpCorEstilo;
	}

	public Integer getCpBoxLocalEstoque() {
		return cpBoxLocalEstoque;
	}

	public void setCpBoxLocalEstoque(Integer cpBoxLocalEstoque) {
		this.cpBoxLocalEstoque = cpBoxLocalEstoque;
	}

	public Integer getCpTabelaNCM() {
		return cpTabelaNCM;
	}

	public void setCpTabelaNCM(Integer cpTabelaNCM) {
		this.cpTabelaNCM = cpTabelaNCM;
	}

	public Integer getCpTabelaLcServico() {
		return cpTabelaLcServico;
	}

	public void setCpTabelaLcServico(Integer cpTabelaLcServico) {
		this.cpTabelaLcServico = cpTabelaLcServico;
	}

	public Integer getCpItem() {
		return cpItem;
	}

	public void setCpItem(Integer cpItem) {
		this.cpItem = cpItem;
	}

	public Integer getCpFamiliaPreco() {
		return cpFamiliaPreco;
	}

	public void setCpFamiliaPreco(Integer cpFamiliaPreco) {
		this.cpFamiliaPreco = cpFamiliaPreco;
	}

	public Integer getCpGrade() {
		return cpGrade;
	}

	public void setCpGrade(Integer cpGrade) {
		this.cpGrade = cpGrade;
	}

	
	public Integer getCnfOperacaoSaida() {
		return cnfOperacaoSaida;
	}

	public void setCnfOperacaoSaida(Integer cnfOperacaoSaida) {
		this.cnfOperacaoSaida = cnfOperacaoSaida;
	}	

	public Integer getVdSegmento() {
		return vdSegmento;
	}

	public void setVdSegmento(Integer vdSegmento) {
		this.vdSegmento = vdSegmento;
	}

	public Integer getCnfOperacaoFinaceiro() {
		return cnfOperacaoFinaceiro;
	}

	public void setCnfOperacaoFinaceiro(Integer cnfOperacaoFinaceiro) {
		this.cnfOperacaoFinaceiro = cnfOperacaoFinaceiro;
	}

	public Integer getCpAplicacao() {
		return cpAplicacao;
	}

	public void setCpAplicacao(Integer cpAplicacao) {
		this.cpAplicacao = cpAplicacao;
	}

//	public Integer getVersao() {
//		return versao;
//	}
//
//	public void setVersao(Integer versao) {
//		this.versao = versao;
//	}

	public Integer getCpTributacao() {
		return cpTributacao;
	}

	public void setCpTributacao(Integer cpTributacao) {
		this.cpTributacao = cpTributacao;
	}

	public Integer getVdPais() {
		return vdPais;
	}

	public void setVdPais(Integer vdPais) {
		this.vdPais = vdPais;
	}

	public Integer getVdUF() {
		return vdUF;
	}

	public void setVdUF(Integer vdUF) {
		this.vdUF = vdUF;
	}

	public Integer getVdCidade() {
		return vdCidade;
	}

	public void setVdCidade(Integer vdCidade) {
		this.vdCidade = vdCidade;
	}

	public Integer getVdCliente() {
		return vdCliente;
	}

	public void setVdCliente(Integer vdCliente) {
		this.vdCliente = vdCliente;
	}

	public Integer getVdRota() {
		return vdRota;
	}

	public void setVdRota(Integer vdRotas) {
		this.vdRota = vdRotas;
	}	

	public Integer getVdRamoAtividade() {
		return vdRamoAtividade;
	}

	public void setVdRamoAtividade(Integer vdRamoAtividade) {
		this.vdRamoAtividade = vdRamoAtividade;
	}

	public Integer getVdRegiao() {
		return vdRegiao;
	}

	public void setVdRegiao(Integer vdRegiao) {
		this.vdRegiao = vdRegiao;
	}

	public Integer getVdPlanoPagamento() {
		return vdPlanoPagamento;
	}

	public void setVdPlanoPagamento(Integer vdPlanoPagamento) {
		this.vdPlanoPagamento = vdPlanoPagamento;
	}

	public Integer getVdConvenio() {
		return vdConvenio;
	}

	public void setVdConvenio(Integer vdConvenio) {
		this.vdConvenio = vdConvenio;
	}

	public Integer getVdMensagemNf() {
		return vdMensagemNf;
	}

	public void setVdMensagemNf(Integer vdMensagemNf) {
		this.vdMensagemNf = vdMensagemNf;
	}

	public Integer getFcSecao() {
		return fcSecao;
	}

	public void setFcSecao(Integer fcSecao) {
		this.fcSecao = fcSecao;
	}

	public Integer getFcMoeda() {
		return fcMoeda;
	}

	public void setFcMoeda(Integer fcMoeda) {
		this.fcMoeda = fcMoeda;
	}

	public Integer getFcPortador() {
		return fcPortador;
	}

	public void setFcPortador(Integer fcPortador) {
		this.fcPortador = fcPortador;
	}

	public Integer getFcCentroCusto() {
		return fcCentroCusto;
	}

	public void setFcCentroCusto(Integer fcCentroCusto) {
		this.fcCentroCusto = fcCentroCusto;
	}

	public Integer getFcPlanoConta() {
		return fcPlanoConta;
	}

	public void setFcPlanoConta(Integer fcPlanoConta) {
		this.fcPlanoConta = fcPlanoConta;
	}

	public Integer getFcCaixaBanco() {
		return fcCaixaBanco;
	}

	public void setFcCaixaBanco(Integer fcCaixaBanco) {
		this.fcCaixaBanco = fcCaixaBanco;
	}	

	public Integer getFcContasReceber() {
		return fcContasReceber;
	}

	public void setFcContasReceber(Integer fcContasReceber) {
		this.fcContasReceber = fcContasReceber;
	}

	public Integer getCnfDepositoEstoque() {
		return cnfDepositoEstoque;
	}

	public void setCnfDepositoEstoque(Integer cnfDepositoEstoque) {
		this.cnfDepositoEstoque = cnfDepositoEstoque;
	}

	public Integer getCnfUsuario() {
		return cnfUsuario;
	}

	public void setCnfUsuario(Integer cnfUsuario) {
		this.cnfUsuario = cnfUsuario;
	}

	public Integer getCnfOperacaoEntrada() {
		return cnfOperacaoEntrada;
	}

	public void setCnfOperacaoEntrada(Integer cnfOperacaoEntrada) {
		this.cnfOperacaoEntrada = cnfOperacaoEntrada;
	}
	
	
	public Integer getCnfLayoutArquivos() 
	{
		return cnfLayoutArquivos;
	}

	public void setCnfLayoutArquivos(Integer cnfLayoutArquivos) 
	{
		this.cnfLayoutArquivos = cnfLayoutArquivos;
	}	
	
	
	
	

}