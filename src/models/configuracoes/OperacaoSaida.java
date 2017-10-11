package models.configuracoes;

import java.io.Serializable;
import javax.persistence.*;

import models.configuracoes.OperacaoSaidaPK;
import models.vendas.Cliente;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.math.BigInteger;


/**
 * The persistent class for the operacao_saida database table.
 * 
 */
@Entity
@Table(name="operacao_saida")
//@NamedQuery(name="OperacaoSaida.findAll", query="SELECT o FROM OperacaoSaida o")
@NamedQueries({
	@NamedQuery(name = "OperacaoSaidaById", query = "SELECT d FROM OperacaoSaida d WHERE d.codigo =:codigo AND d.flagAtivo =1 AND d.codemp IN (:codemp)"),
	@NamedQuery(name = "OperacaoSaidaAll", query = "SELECT d FROM OperacaoSaida d WHERE d.flagAtivo IN (:flag) AND d.codemp IN (:codemp)"),
	@NamedQuery(name = "OperacaoSaidaLast", query = "SELECT d FROM OperacaoSaida d WHERE d.codemp IN (:codemp) AND d.flagAtivo =1 ORDER BY d.codigo DESC")
	})
@IdClass(value = OperacaoSaidaPK.class)
public class OperacaoSaida implements Serializable {
	private static final long serialVersionUID = 1L;

	/*@EmbeddedId
	private OperacaoSaidaPK id;*/
	
	@Id
	@Column(name="CHECK_DELETE")
	private BigInteger checkDelete;

	@Id
	private Integer codigo;

	private Integer cfop;

	private Integer cfop2;

	private Integer codemp;

	@Column(name="DESC_FISCAL")
	private String descFiscal;

	@Column(name="DESC_FISCAL_SERVICO")
	private String descFiscalServico;

	private String descricao;

	@Column(name="FLAG_ATIVO")
	private Integer flagAtivo;

	@Column(name="FLAG_GERA_NFE")
	private Integer flagGeraNfe;

	@Column(name="FLAG_SOLICITA_AUTORIZACAO")
	private Integer flagSolicitaAutorizacao;

	@Column(name="LAST_CODUSER")
	private Integer lastCoduser;

	@Column(name="LAST_MOVTO")
	private LocalDateTime lastMovto;

	@Column(name="RECORD_NO")
	private Integer recordNo;

	@Column(name="TIPO_OPERACAO")
	private Integer tipoOperacao;

	private BigInteger utctag;

	public OperacaoSaida() {
	}
	
	@OneToMany(mappedBy="operacaoSaida", fetch = FetchType.LAZY)
	private List<Cliente> cliente;

	/*public OperacaoSaidaPK getId() {
		return this.id;
	}

	public void setId(OperacaoSaidaPK id) {
		this.id = id;
	}*/

	public BigInteger getCheckDelete() {
		return checkDelete;
	}

	public void setCheckDelete(BigInteger checkDelete) {
		this.checkDelete = checkDelete;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getCfop() {
		return this.cfop;
	}

	public void setCfop(Integer cfop) {
		this.cfop = cfop;
	}

	public Integer getCfop2() {
		return this.cfop2;
	}

	public void setCfop2(Integer cfop2) {
		this.cfop2 = cfop2;
	}

	public Integer getCodemp() {
		return this.codemp;
	}

	public void setCodemp(Integer codemp) {
		this.codemp = codemp;
	}

	public String getDescFiscal() {
		return this.descFiscal;
	}

	public void setDescFiscal(String descFiscal) {
		this.descFiscal = descFiscal;
	}

	public String getDescFiscalServico() {
		return this.descFiscalServico;
	}

	public void setDescFiscalServico(String descFiscalServico) {
		this.descFiscalServico = descFiscalServico;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getFlagAtivo() {
		return this.flagAtivo;
	}

	public void setFlagAtivo(Integer flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public Integer getFlagGeraNfe() {
		return this.flagGeraNfe;
	}

	public void setFlagGeraNfe(Integer flagGeraNfe) {
		this.flagGeraNfe = flagGeraNfe;
	}

	public Integer getFlagSolicitaAutorizacao() {
		return this.flagSolicitaAutorizacao;
	}

	public void setFlagSolicitaAutorizacao(Integer flagSolicitaAutorizacao) {
		this.flagSolicitaAutorizacao = flagSolicitaAutorizacao;
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

	public Integer getTipoOperacao() {
		return this.tipoOperacao;
	}

	public void setTipoOperacao(Integer tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}

	public BigInteger getUtctag() {
		return this.utctag;
	}

	public void setUtctag(BigInteger utctag) {
		this.utctag = utctag;
	}

}