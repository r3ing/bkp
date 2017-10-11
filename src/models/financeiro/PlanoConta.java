package models.financeiro;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.math.BigInteger;


/**
 * The persistent class for the plano_contas database table.
 * 
 */
@Entity
@Table(name="plano_contas")
//@NamedQuery(name="PlanoConta.findAll", query="SELECT p FROM PlanoConta p")
@NamedQueries({
	@NamedQuery(name = "PlanoContaById", query = "SELECT p FROM PlanoConta p WHERE p.codigo =:codigo AND p.flagAtivo =1 AND p.codemp IN (:codemp)"),
	@NamedQuery(name = "PlanoContaAll", query = "SELECT p FROM PlanoConta p WHERE p.flagAtivo IN (:flag) AND p.codemp IN (:codemp)"),
	@NamedQuery(name = "PlanoContaLast", query = "SELECT p FROM PlanoConta p WHERE p.codemp IN (:codemp) AND p.flagAtivo =1 ORDER BY p.codigo DESC")
	})
@IdClass(value = PlanoContaPK.class)
public class PlanoConta implements Serializable {
	private static final long serialVersionUID = 1L;

//	@EmbeddedId
//	private PlanoContaPK id;
	@Id
	private Integer codigo;

	@Id
	@Column(name="CHECK_DELETE")
	private BigInteger checkDelete;

	private Integer codemp;

	private String descricao;
	
	@Column(name="COD_PLANO_CONTA")
	private String codPlanoConta;
	
	@Column(name="TIPO_CONTA")
	private Integer tipoConta;
	
	@Column(name="NATUREZA_CONTA")
	private Integer naturezaConta;
	
	@Column(name="FLAG_APURACAO_RESULTADO")
	private Integer flagApuracaoResultado;
	
	@Column(name="FLAG_CONTRA_PARTIDA")
	private Integer flagContraPartida;
	
	@Column(name="FLAG_ATIVO")
	private Integer flagAtivo;

	@Column(name="LAST_CODUSER")
	private Integer lastCoduser;

	@Column(name="LAST_MOVTO")
	private LocalDateTime lastMovto;

	@Column(name="RECORD_NO")
	private Integer recordNo;

	private BigInteger utctag;

	public PlanoConta() {
	}

//	public PlanoContaPK getId() {
//		return this.id;
//	}
//
//	public void setId(PlanoContaPK id) {
//		this.id = id;
//	}
	
	@OneToMany(mappedBy="planoConta", fetch = FetchType.LAZY)
	private List<ContasReceber> contasReceber;	

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

	public String getCodPlanoConta() {
		return codPlanoConta;
	}

	public void setCodPlanoConta(String codPlanoConta) {
		this.codPlanoConta = codPlanoConta;
	}

	public Integer getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(Integer tipoConta) {
		this.tipoConta = tipoConta;
	}

	public Integer getNaturezaConta() {
		return naturezaConta;
	}

	public void setNaturezaConta(Integer naturezaConta) {
		this.naturezaConta = naturezaConta;
	}

	public Integer getFlagApuracaoResultado() {
		return flagApuracaoResultado;
	}

	public void setFlagApuracaoResultado(Integer flagApuracaoResultado) {
		this.flagApuracaoResultado = flagApuracaoResultado;
	}

	public Integer getFlagContraPartida() {
		return flagContraPartida;
	}

	public void setFlagContraPartida(Integer flagContraPartida) {
		this.flagContraPartida = flagContraPartida;
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