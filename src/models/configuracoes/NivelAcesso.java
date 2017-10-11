package models.configuracoes;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;


/**
 * The persistent class for the nivel_acesso database table.
 * 
 */
@Entity
@Table(name="NIVEL_ACESSO")
@NamedQuery(name="NivelAcessoAll", query="SELECT n FROM NivelAcesso n WHERE n.usuario = :user")
@IdClass(value= NivelAcessoPK.class)
public class NivelAcesso implements Serializable {
	private static final long serialVersionUID = 1L;


	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
	@JoinColumn(name = "CHECK_DELETE", referencedColumnName= "CHECK_DELETE"),
	@JoinColumn(name = "COD_USUARIO", referencedColumnName= "CODIGO"),
	@JoinColumn(name = "CODEMP", referencedColumnName= "CODEMP")
	})
	private Usuario usuario;
	
	@Id
	private String menu;
	
	@Column(name="FLAG_ATIVO")
	private Integer flagAtivo;

	@Column(name="FLAG_DISABLE")
	private Integer flagDisable;

	@Column(name="FLAG_ENABLE")
	private Integer flagEnable;

	@Column(name="FLAG_INSERT")
	private Integer flagInsert;

	@Column(name="FLAG_UPDATE")
	private Integer flagUpdate;

	@Column(name="FLAG_VIEW")
	private Integer flagView;
	
	@Column (name="FLAG_PRINT")
	private Integer flagPrint;

	@Column(name="LAST_CODUSER")
	private Integer lastCoduser;

	@Column(name="LAST_MOVTO")
	private LocalDateTime lastMovto;

	

	private Integer utctag;

	public NivelAcesso() {
	}
	
	

//	public NivelAcessoPK getId() {
//		return this.id;
//	}

//	public void setId(NivelAcessoPK id) {
//		this.id = id;
//	}

	public Usuario getUsuario() {
		return usuario;
	}



	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Integer getFlagAtivo() {
		return this.flagAtivo;
	}

	public void setFlagAtivo(Integer flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public Integer getFlagDisable() {
		return this.flagDisable;
	}

	public void setFlagDisable(Integer flagDisable) {
		this.flagDisable = flagDisable;
	}

	public Integer getFlagEnable() {
		return this.flagEnable;
	}

	public void setFlagEnable(Integer flagEnable) {
		this.flagEnable = flagEnable;
	}

	public Integer getFlagInsert() {
		return this.flagInsert;
	}

	public void setFlagInsert(Integer flagInsert) {
		this.flagInsert = flagInsert;
	}

	public Integer getFlagUpdate() {
		return this.flagUpdate;
	}

	public void setFlagUpdate(Integer flagUpdate) {
		this.flagUpdate = flagUpdate;
	}

	public Integer getFlagView() {
		return this.flagView;
	}

	public void setFlagView(Integer flagView) {
		this.flagView = flagView;
	}
	

	/**
	 * @return the flagPrint
	 */ 
	
	public Integer getFlagPrint() {
		return flagPrint;
	}

	/**
	 * @param flagPrint the flagPrint to set
	 */
	public void setFlagPrint(Integer flagPrint) {
		this.flagPrint = flagPrint;
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

	public String getMenu() {
		return this.menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public Integer getUtctag() {
		return this.utctag;
	}

	public void setUtctag(Integer utctag) {
		this.utctag = utctag;
	}

	@Override
	public String toString() {
		return "NivelAcesso [usuario=" + usuario + ", menu=" + menu +  ", flagAtivo="
				+ flagAtivo + ", flagDisable=" + flagDisable + ", flagEnable=" + flagEnable + ", flagInsert="
				+ flagInsert + ", flagUpdate=" + flagUpdate + ", flagView=" + flagView + ", flagPrint=" + flagPrint
				+ ", lastCoduser=" + lastCoduser + ", lastMovto=" + lastMovto + ", utctag=" + utctag + "]";
	}


}