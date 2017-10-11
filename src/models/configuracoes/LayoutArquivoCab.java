package models.configuracoes;

import java.io.Serializable;
import javax.persistence.*;

import javafx.collections.FXCollections;
import models.compras.Fabricante;
import models.compras.ItemCodBar;
import models.compras.ItemValor;
import models.configuracoes.LayoutArquivoCabPK;

//import java.sql.Timestamp;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The persistent class for the grupo database table.
 * 
 * 
 */
@Entity
@Table(name = "LAYOUT_ARQUIVO_CAB")
@NamedQueries({
		@NamedQuery(name = "LayoutArquivoCabById", query = "SELECT d FROM LayoutArquivoCab d WHERE d.codigo =:codigo AND d.flagAtivo =1 AND d.codemp IN (:codemp)"),
		@NamedQuery(name = "LayoutArquivoCabAll", query = "SELECT d FROM LayoutArquivoCab d WHERE d.flagAtivo IN (:flag) AND d.codemp IN (:codemp)"),
		@NamedQuery(name = "LayoutArquivoCabLast", query = "SELECT d FROM LayoutArquivoCab d WHERE d.codemp IN (:codemp) AND d.flagAtivo =1 ORDER BY d.codigo DESC")
		})
@IdClass(value = LayoutArquivoCabPK.class)
public class LayoutArquivoCab implements Serializable 
{
	
	private static final long serialVersionUID = 1L;
	
	// ----  CHAVE PRIMARIA ---- //

	@Id
	@Column(name = "CODIGO")
	private Integer codigo;
	
	@Id
	@Column(name = "CHECK_DELETE")
	private BigInteger checkDelete;	
	// ----  CHAVE PRIMARIA ---- //
	
	
	//bi-directional many-to-one association to ItemValor
		@OneToMany(mappedBy = "layoutArquivoCab", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
		private List<LayoutArquivoDet> layoutArquivoDet =  FXCollections.observableArrayList();
		//private Set<LayoutArquivoDet> layoutArquivoDet = new HashSet<LayoutArquivoDet>();		
	
	// --- ATRIBUTOS -------//
	
	public List<LayoutArquivoDet> getLayoutArquivoDet() 
	{
			return this.layoutArquivoDet;
	}

	public void setLayoutArquivoDet(List<LayoutArquivoDet> layoutArquivoDet) 
	{
		this.layoutArquivoDet = layoutArquivoDet;
	}

	@Column(name = "CODEMP")
	private Integer codemp;
	
	@Column(name = "RECORD_NO")
	private Integer recordNo;
	
	private String descricao;

	@Column(name = "FLAG_ATIVO")
	private Integer flagAtivo;

	@Column(name = "LAST_CODUSER")
	private Integer lastCoduser;

	@Column(name = "LAST_MOVTO")
	private LocalDateTime lastMovto;

	private BigInteger utctag;
	
	@Column(name="COMANDO_SQL")
	private String comandoSql;

	@Column(name="FLAG_TIPO_OPERACAO")
	private Integer flagTipoOperacao;

	@Column(name="SEPARADOR")
	private String separador;

	@Column(name="TABELA")
	private String tabela;

	@Column(name="TIPO_ARQUIVO_DADOS")
	private Integer tipoArquivoDados;	

	
	//CONSTRUTOR PADRÃO DA CLASSE
	public LayoutArquivoCab() 
	{
	}
	
	public BigInteger getCheckDelete() 
	{
		return this.checkDelete;
	}

	public void setCheckDelete(BigInteger checkDelete) 
	{
		this.checkDelete = checkDelete;
	}


	/*public Set<LayoutArquivoDet> getLayoutArquivoDet() {
		return layoutArquivoDet;
	}

	public void setLayoutArquivoDet(Set<LayoutArquivoDet> layoutArquivoDet) {
		this.layoutArquivoDet = layoutArquivoDet;
	}*/

	public String getDescricao() 
	{
		return this.descricao;
	}

	public void setDescricao(String descricao) 
	{
		this.descricao = descricao;
	}

	public Integer getFlagAtivo() 
	{
		return this.flagAtivo;
	}

	public void setFlagAtivo(Integer flagAtivo) 
	{
		this.flagAtivo = flagAtivo;
	}

	public Integer getLastCoduser() 
	{
		return this.lastCoduser;
	}

	public void setLastCoduser(Integer lastCoduser) 
	{
		this.lastCoduser = lastCoduser;
	}

	public LocalDateTime getLastMovto() 
	{
		return this.lastMovto;
	}

	public void setLastMovto(LocalDateTime lastMovto) 
	{
		this.lastMovto = lastMovto;
	}

	public BigInteger getUtctag() 
	{
		return this.utctag;
	}

	public void setUtctag(BigInteger utctag) 
	{
		this.utctag = utctag;
	}

	public Integer getCodigo() 
	{
		return codigo;
	}

	public void setCodigo(Integer codigo) 
	{
		this.codigo = codigo;
	}

	public Integer getCodemp() 
	{
		return codemp;
	}

	public void setCodemp(Integer codemp) 
	{
		this.codemp = codemp;
	}

	public Integer getRecordNo() 
	{
		return recordNo;
	}

	public void setRecordNo(Integer recordNo) 
	{
		this.recordNo = recordNo;
	}
	
	
	
	public String getComandoSql() 
	{
		return this.comandoSql;
	}

	public void setComandoSql(String comandoSql) 
	{
		this.comandoSql = comandoSql;
	}

	public Integer getFlagTipoOperacao() 
	{
		return this.flagTipoOperacao;
	}

	public void setFlagTipoOperacao(Integer flagTipoOperacao) 
	{
		this.flagTipoOperacao = flagTipoOperacao;
	}

	public String getSeparador() 
	{
		return this.separador;
	}

	public void setSeparador(String separador)
	{
		this.separador = separador;
	}

	public String getTabela() 
	{
		return this.tabela;
	}

	public void setTabela(String tabela) 
	{
		this.tabela = tabela;
	}

	public Integer getTipoArquivoDados() 
	{
		return this.tipoArquivoDados;
	}

	public void setTipoArquivoDados(Integer tipoArquivoDados) 
	{
		this.tipoArquivoDados = tipoArquivoDados;
	}
	

}