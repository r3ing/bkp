package models.configuracoes;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@Table(name = "USUARIO")
@NamedQueries({
	@NamedQuery(name = "UsuarioById", query = "SELECT d FROM Usuario d WHERE d.flagAtivo IN (:flag) AND d.codemp=:codemp"),
	@NamedQuery(name = "UsuarioLogin", query = "SELECT us FROM Usuario us LEFT JOIN FETCH us.niveisAcesso WHERE us.pwd =:pwd AND us.flagAtivo = 1"),
	@NamedQuery(name = "UsuarioJoinAcessoAll", query = "SELECT d FROM Usuario d LEFT JOIN FETCH d.niveisAcesso WHERE d.flagAtivo IN (:flag) AND d.codemp=:codemp"),
	@NamedQuery(name = "UsuarioAll", query = "SELECT d FROM Usuario d WHERE d.flagAtivo IN (:flag) AND d.codemp=:codemp"),
	@NamedQuery(name = "UsuarioLast", query = "SELECT d FROM Usuario d WHERE d.codemp =:codemp AND d.flagAtivo = 1 ORDER BY d.codigo DESC")
})
@IdClass(value = UsuarioPK.class)
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer codigo;
	
	@Id
	private Integer codemp;
	
	@Column(name = "RECORD_NO")
	private Integer recordNo;
	
	@Id
	@Column(name="CHECK_DELETE")
	private BigInteger checkDelete;

	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private List<NivelAcesso> niveisAcesso;
	
	@Column(name="COD_FUNCIONARIO")
	private Integer codFuncionario;
	
	@Column(name="COD_FUNCIOARIO_FK")
	private BigInteger codFuncionarioFk;	

	@Column(name="LAST_CODUSER")
	private Integer lastCoduser;

	@Column(name="LAST_MOVTO")
	private LocalDateTime lastMovto;
	
	@Column(name = "FLAG_ATIVO")
	private Integer flagAtivo;
	
	private String idioma;
	
	private String nome;

	private String pwd;

	private String pwd1;

	private String pwd2;

	private String pwd3;

	private BigInteger utctag;
	
	@Column(name="COD_EMPDISPLOGIN")
	private String codEmpDispLogin;
	
	@Column(name="ACTION_DEFNIVACESSO")
	private Integer actionDefNivAcesso;
	
	@Column(name="ACTION_COPYUSUARIO")
	private Integer actionCopyNivAcesso;
	
	@Column(name="ACTION_COPYNIVACESSO")
	private Integer actionCopyUsuario;
	
	// Configuracoes	
	@Column(name="CONF_FLAG_ALTDTMOVTO")
	private Integer confFlagAltDtMovto;
	
	@Column(name="CONF_DIAS_RETRODTMOVTO")
	private Integer confDiasRetroDtMovto;
	
	@Column(name="CONF_DIAS_AVANDTMOVTO")
	private Integer confDiasAvanDtMovto;
	
	//Vendas	
	@Column(name="VDA_ALT_OBS_RESTRITIVA")
	private Integer vdaAltObsRestritiva;	
	
	@Column(name="VDA_ALT_LIMITE_CREDITO")
	private Integer vdaAltLimiteCredito;
	
	@Column(name="VDA_MAX_LIMITE_CREDITO")
	private BigDecimal vdaMaxLimiteCredito;
	
	@Column(name="VDA_FATURAMENTO_ALT_TIPO")
	private Integer vdaFaturamentoAltTipo;
	
	@Column(name="VDA_ALT_VENDEDOR_PADRAO")
	private Integer vdaAltVendedorPadrao;
	
	@Column(name="VDA_FATURAMENTO_ALT_CONVENIO")
	private Integer vdaFaturamentoAltConvenio;
	
	@Column(name="VDA_FATURAMENTO_ALT_TIPO_PRECO")
	private Integer vdaFaturamentoAltTipoPreco;
	
	@Column(name="VDA_ALT_DADOS_PROTESTO")
	private Integer vdaAltDadosProtesto;
	
	@Column(name="VDA_ALT_CPF_CNPJ")
	private Integer vdaAltCpfCnpj;
	
	@Column(name="VDA_CLI_ALT_CONSULT_PROPOSTA")
	private Integer vdaCliAltConsultProposta;
	
	@Column(name="VDA_VEN_ALT_CONSULT_PROPOSTA")
	private Integer vdaVenAltConsultProposta;
	
	@Column(name="VDA_AUTORIZA_CPF_DUPLICADO")
	private Integer vdaAutorizaCpfDuplicado;
	
	@Column(name="VDA_AUTORIZA_CPF_ZERADO")
	private Integer vdaAutorizaCpfZerado;
	
	@Column(name="VDA_AUTORIZA_CLIENTE_INADIMPLENTE")
	private Integer vdaAutorizaClienteInadimplente;	
	
	@Column(name="VDA_AUTORIZA_LIMITE_EXCEDIDO")
	private Integer vdaAutorizaLimiteExedido;
	
	@Column(name="VDA_MAX_LIMITE_EXECEDIDO")
	private BigDecimal vdaMaxLimiteExedido;
	
	@Column(name="VDA_AUTORIZA_ESTOQUE_NEGATIVO")
	private Integer vdaAutorizaEstoqueNegativo;
	
	@Column(name="VDA_AUTORIZA_PLANO_PAGTO")
	private Integer vdaAutorizaPlanoPagto;
	
	@Column(name="VDA_AUTORIZA_DESCONTO_EXCEDIDO")
	private Integer vdaAutorizaDescontoExcedido;
	
	@Column(name="VDA_MAX_DESCONTO_EXCEDIDO")
	private BigDecimal vdaMaxDescontoExcedido;
	
	@Column(name="VDA_AUTORIZA_MARKUP_MINIMO")
	private Integer vdaAutorizaMarkupMinimo;
	
	@Column(name="VDA_AUTORIZA_DEVOLUCAO")
	private Integer vdaAutorizaDevolucao;
	
	@Column(name="VDA_AUTORIZA_CLIENTE_OBS_RESTRITIVA")
	private Integer vdaAutorizaClienteObsRestritiva;
	
	@Column(name="VDA_AUTORIZA_CLIENTE_CAD_VENCIDO")
	private Integer vdaAutorizaClienteCadVencido;
	
	@Column(name="VDA_AUTORIZA_OPERACAO_SAIDA")
	private Integer vdaAutorizaOperacaoSaida;
	
	@Column(name="VDA_AUTORIZA_PORTADOR_DIFERENTE_CLIENTE")
	private Integer vdaAutorizaPortadorDiferenteCliente;
	
	@Column(name="VDA_AUTORIZA_DESCONTO_PROMOCAO")
	private Integer vdaAutorizaDescontoPromocao;
	
	@Column(name="VDA_AUTORIZA_CLIENTE_INATIVO")
	private Integer vdaAutorizaClienteInativo;	
	
	@Column(name="VDA_AUTORIZA_ACRESCIMO")
	private Integer vdaAutorizaAcresimo;
	
	@Column(name="VDA_MAX_ACRESCIMO")
	private BigDecimal vdaMaxAcresimo;
	
	@Column(name="VDA_AUTORIZA_PORTADOR_RESTRITO")
	private Integer vdaAutorizaPortadorRestrito;
	
	@Column(name="VDA_SHOW_PRECO_CUSTO")
	private Integer vdaShowPrecoCusto;
	
	@Column(name="VDA_SHOW_PRECO_ATACADO")
	private Integer vdaShowPrecoAtacado;
	
	@Column(name="VDA_SHOW_DEBITO_CLIENTES")
	private Integer vdaShowDebitoClientes;
	
	@Column(name="VDA_SHOW_MARGEM_LUCRO")
	private Integer vdaShowMargemLucro;
	
	@Column(name="VDA_SHOW_CREDITO_CLIENTE")
	private Integer vdaShowCreditoCliente;
	
	@Column(name="VDA_LIBERACAO_REMOTA")
	private Integer vdaLiberacaoRemota;
	
	@Column(name="VDA_ACESSA_CLIENTE_PRECO_CUSTO")
	private Integer vdaAcessaClientePrecoCusto;
	
	@Column(name="FIN_ALT_VALOR_CTAS_RECEBER")
	private Integer finAltValorCtasReceber;
	
	@Column(name="FIN_ALT_DT_VENCTO_CTAS_RECEBER")
	private Integer finAltDtVenctoCtasReceber;
	
	public Usuario() {
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public Integer getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(Integer recordNo) {
		this.recordNo = recordNo;
	}

	public Integer getCodemp() {
		return codemp;
	}

	public void setCodemp(Integer codemp) {
		this.codemp = codemp;
	}

	public Integer getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Integer flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public BigInteger getCheckDelete() {
		return this.checkDelete;
	}

	public void setCheckDelete(BigInteger checkDelete) {
		this.checkDelete = checkDelete;
	}

	public Integer getCodFuncionario() {
		return this.codFuncionario;
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

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPwd1() {
		return this.pwd1;
	}

	public void setPwd1(String pwd1) {
		this.pwd1 = pwd1;
	}

	public String getPwd2() {
		return this.pwd2;
	}

	public void setPwd2(String pwd2) {
		this.pwd2 = pwd2;
	}

	public String getPwd3() {
		return this.pwd3;
	}

	public void setPwd3(String pwd3) {
		this.pwd3 = pwd3;
	}

	public BigInteger getUtctag() {
		return this.utctag;
	}

	public void setUtctag(BigInteger utctag) {
		this.utctag = utctag;
	}

	/**
	 * @return the idioma
	 */
	public String getIdioma() {
		return idioma;
	}

	/**
	 * @param idioma the idioma to set
	 */
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	
	public String getCodEmpDispLogin() {
		return codEmpDispLogin;
	}

	public void setCodEmpDispLogin(String codEmpDispLogin) {
		this.codEmpDispLogin = codEmpDispLogin;
	}

	public Integer getActionDefNivAcesso() {
		return actionDefNivAcesso;
	}

	public void setActionDefNivAcesso(Integer actionDefNivAcesso) {
		this.actionDefNivAcesso = actionDefNivAcesso;
	}

	public Integer getActionCopyNivAcesso() {
		return actionCopyNivAcesso;
	}

	public void setActionCopyNivAcesso(Integer actionCopyNivAcesso) {
		this.actionCopyNivAcesso = actionCopyNivAcesso;
	}

	public Integer getActionCopyUsuario() {
		return actionCopyUsuario;
	}

	public void setActionCopyUsuario(Integer actionCopyUsuario) {
		this.actionCopyUsuario = actionCopyUsuario;
	}

	public Integer getConfDiasRetroDtMovto() {
		return confDiasRetroDtMovto;
	}

	public void setConfDiasRetroDtMovto(Integer confDiasRetroDtMovto) {
		this.confDiasRetroDtMovto = confDiasRetroDtMovto;
	}

	public Integer getConfDiasAvanDtMovto() {
		return confDiasAvanDtMovto;
	}

	public void setConfDiasAvanDtMovto(Integer confDiasAvanDtMovto) {
		this.confDiasAvanDtMovto = confDiasAvanDtMovto;
	}

	public Integer getConfFlagAltDtMovto() {
		return confFlagAltDtMovto == null ? 0 : confFlagAltDtMovto;
	}

	public void setConfFlagAltDtMovto(Integer confFlagAltDtMovto) {
		this.confFlagAltDtMovto = confFlagAltDtMovto;
	}

	public List<NivelAcesso> getNiveisAcesso() 
	{
		return niveisAcesso;
	}

	public void setNiveisAcesso(List<NivelAcesso> niveisAcesso) {
		this.niveisAcesso = niveisAcesso;
	}

	public Integer getVdaAltObsRestritiva() {
		return vdaAltObsRestritiva;
	}

	public void setVdaAltObsRestritiva(Integer vdaAltObsRestritiva) {
		this.vdaAltObsRestritiva = vdaAltObsRestritiva;
	}

	public Integer getVdaAltLimiteCredito() {
		return vdaAltLimiteCredito;
	}

	public void setVdaAltLimiteCredito(Integer vdaAltLimiteCredito) {
		this.vdaAltLimiteCredito = vdaAltLimiteCredito;
	}

	public BigDecimal getVdaMaxLimiteCredito() {
		return vdaMaxLimiteCredito;
	}

	public void setVdaMaxLimiteCredito(BigDecimal vdaMaxLimiteCredito) {
		this.vdaMaxLimiteCredito = vdaMaxLimiteCredito;
	}

	public Integer getVdaFaturamentoAltTipo() {
		return vdaFaturamentoAltTipo;
	}

	public void setVdaFaturamentoAltTipo(Integer vdaFaturamentoAltTipo) {
		this.vdaFaturamentoAltTipo = vdaFaturamentoAltTipo;
	}

	public Integer getVdaAltVendedorPadrao() {
		return vdaAltVendedorPadrao;
	}

	public void setVdaAltVendedorPadrao(Integer vdaAltVendedorPadrao) {
		this.vdaAltVendedorPadrao = vdaAltVendedorPadrao;
	}

	public Integer getVdaFaturamentoAltConvenio() {
		return vdaFaturamentoAltConvenio;
	}

	public void setVdaFaturamentoAltConvenio(Integer vdaFaturamentoAltConvenio) {
		this.vdaFaturamentoAltConvenio = vdaFaturamentoAltConvenio;
	}

	public Integer getVdaFaturamentoAltTipoPreco() {
		return vdaFaturamentoAltTipoPreco;
	}

	public void setVdaFaturamentoAltTipoPreco(Integer vdaFaturamentoAltTipoPreco) {
		this.vdaFaturamentoAltTipoPreco = vdaFaturamentoAltTipoPreco;
	}

	public Integer getVdaAltDadosProtesto() {
		return vdaAltDadosProtesto;
	}

	public void setVdaAltDadosProtesto(Integer vdaAltDadosProtesto) {
		this.vdaAltDadosProtesto = vdaAltDadosProtesto;
	}

	public Integer getVdaAltCpfCnpj() {
		return vdaAltCpfCnpj;
	}

	public void setVdaAltCpfCnpj(Integer vdaAltCpfCnpj) {
		this.vdaAltCpfCnpj = vdaAltCpfCnpj;
	}

	public Integer getVdaCliAltConsultProposta() {
		return vdaCliAltConsultProposta;
	}

	public void setVdaCliAltConsultProposta(Integer vdaCliAltConsultProposta) {
		this.vdaCliAltConsultProposta = vdaCliAltConsultProposta;
	}

	public Integer getVdaVenAltConsultProposta() {
		return vdaVenAltConsultProposta;
	}

	public void setVdaVenAltConsultProposta(Integer vdaVenAltConsultProposta) {
		this.vdaVenAltConsultProposta = vdaVenAltConsultProposta;
	}

	public Integer getVdaAutorizaCpfDuplicado() {
		return vdaAutorizaCpfDuplicado;
	}

	public void setVdaAutorizaCpfDuplicado(Integer vdaAutorizaCpfDuplicado) {
		this.vdaAutorizaCpfDuplicado = vdaAutorizaCpfDuplicado;
	}

	public Integer getVdaAutorizaCpfZerado() {
		return vdaAutorizaCpfZerado;
	}

	public void setVdaAutorizaCpfZerado(Integer vdaAutorizaCpfZerado) {
		this.vdaAutorizaCpfZerado = vdaAutorizaCpfZerado;
	}

	public Integer getVdaAutorizaClienteInadimplente() {
		return vdaAutorizaClienteInadimplente;
	}

	public void setVdaAutorizaClienteInadimplente(Integer vdaAutorizaClienteInadimplente) {
		this.vdaAutorizaClienteInadimplente = vdaAutorizaClienteInadimplente;
	}

	public Integer getVdaAutorizaLimiteExedido() {
		return vdaAutorizaLimiteExedido;
	}

	public void setVdaAutorizaLimiteExedido(Integer vdaAutorizaLimiteExedido) {
		this.vdaAutorizaLimiteExedido = vdaAutorizaLimiteExedido;
	}

	public BigDecimal getVdaMaxLimiteExedido() {
		return vdaMaxLimiteExedido;
	}

	public void setVdaMaxLimiteExedido(BigDecimal vdaMaxLimiteExedido) {
		this.vdaMaxLimiteExedido = vdaMaxLimiteExedido;
	}

	public Integer getVdaAutorizaEstoqueNegativo() {
		return vdaAutorizaEstoqueNegativo;
	}

	public void setVdaAutorizaEstoqueNegativo(Integer vdaAutorizaEstoqueNegativo) {
		this.vdaAutorizaEstoqueNegativo = vdaAutorizaEstoqueNegativo;
	}

	public Integer getVdaAutorizaPlanoPagto() {
		return vdaAutorizaPlanoPagto;
	}

	public void setVdaAutorizaPlanoPagto(Integer vdaAutorizaPlanoPagto) {
		this.vdaAutorizaPlanoPagto = vdaAutorizaPlanoPagto;
	}

	public Integer getVdaAutorizaDescontoExcedido() {
		return vdaAutorizaDescontoExcedido;
	}

	public void setVdaAutorizaDescontoExcedido(Integer vdaAutorizaDescontoExcedido) {
		this.vdaAutorizaDescontoExcedido = vdaAutorizaDescontoExcedido;
	}

	public BigDecimal getVdaMaxDescontoExcedido() {
		return vdaMaxDescontoExcedido;
	}

	public void setVdaMaxDescontoExcedido(BigDecimal vdaMaxDescontoExcedido) {
		this.vdaMaxDescontoExcedido = vdaMaxDescontoExcedido;
	}

	public Integer getVdaAutorizaMarkupMinimo() {
		return vdaAutorizaMarkupMinimo;
	}

	public void setVdaAutorizaMarkupMinimo(Integer vdaAutorizaMarkupMinimo) {
		this.vdaAutorizaMarkupMinimo = vdaAutorizaMarkupMinimo;
	}

	public Integer getVdaAutorizaDevolucao() {
		return vdaAutorizaDevolucao;
	}

	public void setVdaAutorizaDevolucao(Integer vdaAutorizaDevolucao) {
		this.vdaAutorizaDevolucao = vdaAutorizaDevolucao;
	}

	public Integer getVdaAutorizaClienteObsRestritiva() {
		return vdaAutorizaClienteObsRestritiva;
	}

	public void setVdaAutorizaClienteObsRestritiva(Integer vdaAutorizaClienteObsRestritiva) {
		this.vdaAutorizaClienteObsRestritiva = vdaAutorizaClienteObsRestritiva;
	}

	public Integer getVdaAutorizaClienteCadVencido() {
		return vdaAutorizaClienteCadVencido;
	}

	public void setVdaAutorizaClienteCadVencido(Integer vdaAutorizaClienteCadVencido) {
		this.vdaAutorizaClienteCadVencido = vdaAutorizaClienteCadVencido;
	}

	public Integer getVdaAutorizaOperacaoSaida() {
		return vdaAutorizaOperacaoSaida;
	}

	public void setVdaAutorizaOperacaoSaida(Integer vdaAutorizaOperacaoSaida) {
		this.vdaAutorizaOperacaoSaida = vdaAutorizaOperacaoSaida;
	}

	public Integer getVdaAutorizaPortadorDiferenteCliente() {
		return vdaAutorizaPortadorDiferenteCliente;
	}

	public void setVdaAutorizaPortadorDiferenteCliente(Integer vdaAutorizaPortadorDiferenteCliente) {
		this.vdaAutorizaPortadorDiferenteCliente = vdaAutorizaPortadorDiferenteCliente;
	}

	public Integer getVdaAutorizaDescontoPromocao() {
		return vdaAutorizaDescontoPromocao;
	}

	public void setVdaAutorizaDescontoPromocao(Integer vdaAutorizaDescontoPromocao) {
		this.vdaAutorizaDescontoPromocao = vdaAutorizaDescontoPromocao;
	}

	public Integer getVdaAutorizaClienteInativo() {
		return vdaAutorizaClienteInativo;
	}

	public void setVdaAutorizaClienteInativo(Integer vdaAutorizaClienteInativo) {
		this.vdaAutorizaClienteInativo = vdaAutorizaClienteInativo;
	}

	public Integer getVdaAutorizaAcresimo() {
		return vdaAutorizaAcresimo;
	}

	public void setVdaAutorizaAcresimo(Integer vdaAutorizaAcresimo) {
		this.vdaAutorizaAcresimo = vdaAutorizaAcresimo;
	}

	public BigDecimal getVdaMaxAcresimo() {
		return vdaMaxAcresimo;
	}

	public void setVdaMaxAcresimo(BigDecimal vdaMaxAcresimo) {
		this.vdaMaxAcresimo = vdaMaxAcresimo;
	}

	public Integer getVdaAutorizaPortadorRestrito() {
		return vdaAutorizaPortadorRestrito;
	}

	public void setVdaAutorizaPortadorRestrito(Integer vdaAutorizaPortadorRestrito) {
		this.vdaAutorizaPortadorRestrito = vdaAutorizaPortadorRestrito;
	}

	public Integer getVdaShowPrecoCusto() {
		return vdaShowPrecoCusto;
	}

	public void setVdaShowPrecoCusto(Integer vdaShowPrecoCusto) {
		this.vdaShowPrecoCusto = vdaShowPrecoCusto;
	}

	public Integer getVdaShowPrecoAtacado() {
		return vdaShowPrecoAtacado;
	}

	public void setVdaShowPrecoAtacado(Integer vdaShowPrecoAtacado) {
		this.vdaShowPrecoAtacado = vdaShowPrecoAtacado;
	}

	public Integer getVdaShowDebitoClientes() {
		return vdaShowDebitoClientes;
	}

	public void setVdaShowDebitoClientes(Integer vdaShowDebitoClientes) {
		this.vdaShowDebitoClientes = vdaShowDebitoClientes;
	}

	public Integer getVdaShowMargemLucro() {
		return vdaShowMargemLucro;
	}

	public void setVdaShowMargemLucro(Integer vdaShowMargemLucro) {
		this.vdaShowMargemLucro = vdaShowMargemLucro;
	}

	public Integer getVdaShowCreditoCliente() {
		return vdaShowCreditoCliente;
	}

	public void setVdaShowCreditoCliente(Integer vdaShowCreditoCliente) {
		this.vdaShowCreditoCliente = vdaShowCreditoCliente;
	}

	public Integer getVdaLiberacaoRemota() {
		return vdaLiberacaoRemota;
	}

	public void setVdaLiberacaoRemota(Integer vdaLiberacaoRemota) {
		this.vdaLiberacaoRemota = vdaLiberacaoRemota;
	}

	public Integer getVdaAcessaClientePrecoCusto() {
		return vdaAcessaClientePrecoCusto;
	}

	public void setVdaAcessaClientePrecoCusto(Integer vdaAcessaClientePrecoCusto) {
		this.vdaAcessaClientePrecoCusto = vdaAcessaClientePrecoCusto;
	}

	public Integer getFinAltValorCtasReceber() {
		return finAltValorCtasReceber;
	}

	public void setFinAltValorCtasReceber(Integer finAltValorCtasReceber) {
		this.finAltValorCtasReceber = finAltValorCtasReceber;
	}

	public Integer getFinAltDtVenctoCtasReceber() {
		return finAltDtVenctoCtasReceber;
	}

	public void setFinAltDtVenctoCtasReceber(Integer finAltDtVenctoCtasReceber) {
		this.finAltDtVenctoCtasReceber = finAltDtVenctoCtasReceber;
	}
	
	
}