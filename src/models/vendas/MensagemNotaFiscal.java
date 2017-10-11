package models.vendas;

import java.io.Serializable;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.math.BigInteger;


/**
 * The persistent class for the mensagem_nota_fiscal database table.
 * 
 */
@Entity
@Table(name="mensagem_nota_fiscal")
//@NamedQuery(name="MensagemNotaFiscal.findAll", query="SELECT m FROM MensagemNotaFiscal m")
@NamedQueries({
	@NamedQuery(name = "MensagemNotaFiscalById", query = "SELECT m FROM MensagemNotaFiscal m WHERE m.codigo =:codigo AND m.flagAtivo =1 AND m.codemp IN (:codemp)"),
	@NamedQuery(name = "MensagemNotaFiscalAll", query = "SELECT m FROM MensagemNotaFiscal m WHERE m.flagAtivo IN (:flag) AND m.codemp IN (:codemp)"),
	@NamedQuery(name = "MensagemNotaFiscalLast", query = "SELECT m FROM MensagemNotaFiscal m WHERE m.codemp IN (:codemp) AND m.flagAtivo =1 ORDER BY m.codigo DESC")
	})
@IdClass(value = MensagemNotaFiscalPK.class)
public class MensagemNotaFiscal implements Serializable {
	private static final long serialVersionUID = 1L;

//	@EmbeddedId
//	private MensagemNotaFiscalPK id;
	@Id
	private Integer codigo;

	@Column(name="CHECK_DELETE")
	private BigInteger checkDelete;

	private Integer codemp;

	private String descricao;

	@Column(name="FLAG_ATIVO")
	private Integer flagAtivo;

	@Column(name="LAST_CODUSER")
	private Integer lastCoduser;

	@Column(name="LAST_MOVTO")
	private LocalDateTime lastMovto;

	private String observacao;

	@Column(name="RECORD_NO")
	private Integer recordNo;

	private BigInteger utctag;

	public MensagemNotaFiscal() {
	}

//	public MensagemNotaFiscalPK getId() {
//		return this.id;
//	}
//
//	public void setId(MensagemNotaFiscalPK id) {
//		this.id = id;
//	}
	
	

	public String getDescricao() {
		return this.descricao;
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

	public String getObservacao() {
		return this.observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
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