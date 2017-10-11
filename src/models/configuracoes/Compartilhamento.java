package models.configuracoes;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;


/**
 * The persistent class for the compartilhamento database table.
 * 
 */
@Entity
@NamedQuery(name="Compartilhamento.findAll", query="SELECT c FROM Compartilhamento c")
public class Compartilhamento implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CompartilhamentoPK id;

//	@Column(name="CHECK_DELETE")
//	private int checkDelete;
	@Column(name="RECORD_NO")
	private int recordNo;
	
	@Column(name="COD_EMPCOMPARTILHADA")
	private String codEmpcompartilhada;

	@Column(name="FLAG_ATIVO")
	private int flagAtivo;

	@Column(name="LAST_CODUSER")
	private int lastCoduser;

	@Column(name="LAST_MOVTO")
	private LocalDateTime lastMovto;

	private String utctag;

	public Compartilhamento() {
	}

	public CompartilhamentoPK getId() {
		return this.id;
	}

	public void setId(CompartilhamentoPK id) {
		this.id = id;
	}

//	public int getCheckDelete() {
//		return this.checkDelete;
//	}
//
//	public void setCheckDelete(int checkDelete) {
//		this.checkDelete = checkDelete;
//	}
	
	public int getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(int recordNo) {
		this.recordNo = recordNo;
	}
	

	public String getCodEmpcompartilhada() {
		return this.codEmpcompartilhada;
	}

	
	public void setCodEmpcompartilhada(String codEmpcompartilhada) {
		this.codEmpcompartilhada = codEmpcompartilhada;
	}

	public int getFlagAtivo() {
		return this.flagAtivo;
	}

	public void setFlagAtivo(int flagAtivo) {
		this.flagAtivo = flagAtivo;
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

	public String getUtctag() {
		return this.utctag;
	}

	public void setUtctag(String utctag) {
		this.utctag = utctag;
	}

}