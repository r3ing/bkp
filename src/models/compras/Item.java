package models.compras;  

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.Hibernate;
import org.hibernate.annotations.DynamicUpdate;

import models.financeiro.Moeda;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The persistent class for the item database table. 
 * 
 */
@Entity
@Table(name = "ITEM")
@NamedQueries({
	@NamedQuery(name = "ItemById", query = "SELECT d FROM Item d "
			+ "LEFT JOIN FETCH d.fabricante f "
			+ "LEFT JOIN FETCH d.grupo g "
			+ "LEFT JOIN FETCH d.subGrupo sg "
			+ "LEFT JOIN FETCH d.departamento dep "
			+ "LEFT JOIN FETCH d.familiaPreco fam "
			+ "LEFT JOIN FETCH d.moeda moe "
			+ "LEFT JOIN FETCH d.unidade uni "
			+ "LEFT JOIN FETCH d.unidadeEmb uniEmb "
			+ "LEFT JOIN FETCH d.ncm nc "
			+ "LEFT JOIN FETCH d.lcServico lc "
			+ "LEFT JOIN FETCH d.itemSimilar1 sm1 "
			+ "LEFT JOIN FETCH d.itemSimilar2 sm2 "
			+ "LEFT JOIN FETCH d.itemSimilar3 sm3 "
			+ "LEFT JOIN FETCH d.itemSimilar4 sm4 "
			+ "LEFT JOIN FETCH d.itemSimilar5 sm5 "
			+ "LEFT JOIN FETCH d.itemSimilar6 sm6 "
			//+ "LEFT JOIN FETCH d.itemCodBars cb "
			//+ "LEFT JOIN FETCH d.itemValors vl "
			//+ "LEFT JOIN FETCH vl.tributacao tb "
			//+ "LEFT JOIN FETCH d.itemFornecedor iFr "
			+ "WHERE "
			+ "d.codigo =:codigo AND d.flagAtivo = 1 AND d.codemp IN (:codemp) "
			//+ "AND (cb is null OR cb.flagCodbarPrincipal = 1)"
			),
	@NamedQuery(name = "ItemCodBars", query = "SELECT cdb FROM ItemCodBar cdb LEFT JOIN cdb.item WHERE cdb.item = :item AND cdb.flagAtivo = 1 AND cdb.codemp = cdb.item.codemp"),
	@NamedQuery(name = "ItemValores", query = "SELECT v FROM ItemValor v JOIN v.item LEFT JOIN FETCH v.tributacao tb WHERE v.item = :item AND v.flagAtivo = 1"),
	@NamedQuery(name = "ItemFornecedores", query = "SELECT f FROM ItemFornecedor f JOIN f.item JOIN FETCH f.fornecedor WHERE f.item = :item AND f.flagAtivo = 1"),
	@NamedQuery(name = "ItemEstoqueDepositos", query = "SELECT g FROM ItemEstoqueDeposito g JOIN g.item JOIN FETCH g.depositoEstoque WHERE g.item = :item AND g.item.flagAtivo = 1"),
	@NamedQuery(name = "ItemOnlyAll", query = "SELECT d FROM Item d WHERE d.flagAtivo IN (:flag) AND d.codemp IN (:codemp)"),
	@NamedQuery(
	name = "ItemJoinEmpAll", 
	query = 
		"SELECT d FROM Item d "
		//+ "LEFT JOIN FETCH d.itemValors p "
		+ "LEFT JOIN FETCH d.itemCodBars c "
		+ "LEFT JOIN FETCH d.fabricante f "
		+ "LEFT JOIN FETCH d.grupo g "
		+ "LEFT JOIN FETCH d.subGrupo sg "
		+ "LEFT JOIN FETCH d.departamento dep "
		+ "LEFT JOIN FETCH d.familiaPreco fam "
		+ "LEFT JOIN FETCH d.unidade uni "
		+ "LEFT JOIN FETCH d.unidadeEmb uniEmb "
		+ "LEFT JOIN FETCH d.ncm nc "
		+ "LEFT JOIN FETCH d.lcServico lc "
		+ "WHERE d.flagAtivo IN (:flag) AND d.codemp IN (:codemp) AND (c is null OR c.flagCodbarPrincipal = 1)"),
	@NamedQuery(
			name = "ItemJoinValorAll", 
			query = 
				"SELECT d FROM ItemValor d "
				+ "LEFT JOIN FETCH d.item p "
				+ "WHERE p.flagAtivo IN (:flag) AND p.codemp IN (:codemp)"),
	//@NamedQuery(name = "ItemJoinEmpAll", query = "SELECT d FROM Item d LEFT JOIN FETCH d.itemValors p LEFT JOIN FETCH d.itemCodBars c WHERE (c is null OR c.flagCodbarPrincipal = 1) AND d.flagAtivo IN (:flag) AND d.codemp IN (:codemp)"),
   // @NamedQuery(name = "ItemJoinAll", query = "SELECT d FROM Item d JOIN d.itemValors p WHERE d.flagAtivo IN (:flag) AND d.codemp IN (:codemp)"),
	@NamedQuery(name = "ItemLast", query = "SELECT d FROM Item d "
			//+ "LEFT JOIN FETCH d.itemValors p "
			//+ "LEFT JOIN FETCH d.itemCodBars c "
			+ "LEFT JOIN FETCH d.fabricante f "
			+ "LEFT JOIN FETCH d.grupo g "
			+ "LEFT JOIN FETCH d.subGrupo sg "
			+ "LEFT JOIN FETCH d.departamento dep "
			+ "LEFT JOIN FETCH d.familiaPreco fam "
			+ "LEFT JOIN FETCH d.moeda moe "
			+ "LEFT JOIN FETCH d.unidade uni "
			+ "LEFT JOIN FETCH d.unidadeEmb uniEmb "
			+ "LEFT JOIN FETCH d.ncm nc "
			+ "LEFT JOIN FETCH d.lcServico lc "
			+ "LEFT JOIN FETCH d.itemSimilar1 sm1 "
			+ "LEFT JOIN FETCH d.itemSimilar2 sm2 "
			+ "LEFT JOIN FETCH d.itemSimilar3 sm3 "
			+ "LEFT JOIN FETCH d.itemSimilar4 sm4 "
			+ "LEFT JOIN FETCH d.itemSimilar5 sm5 "
			+ "LEFT JOIN FETCH d.itemSimilar6 sm6 "
			+ "WHERE d.codemp IN (:codemp) AND d.flagAtivo = 1 ORDER BY d.codigo DESC")
		})
@DynamicUpdate
@IdClass(value = ItemPK.class)
public class Item implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODIGO")
	private Integer codigo;
	
	@Id
	@Column(name = "CHECK_DELETE")
	private BigInteger checkDelete;
	
	private Integer codemp;

	@Column(name = "RECORD_NO")
	private Integer recordNo;
	
	@Column(name="COD_CEST")
	private String codCest;

	@Column(name="DESC_REDUZIDA")
	private String descReduzida;

	@Column(name="DESC_TECNICA")
	private String descTecnica;

	private String descricao;

	@Column(name="TIPO_ITEM")
	private String tipoItem;
	
	@Column(name="FATOR_CONVERSAO")
	private BigDecimal fatorConversao;

	@Column(name="FLAG_ATIVO")
	private Integer flagAtivo;

	@Column(name="LAST_CODUSER")
	private Integer lastCoduser;

	@Column(name="LAST_MOVTO")
	private LocalDateTime lastMovto;

	@Column(name="NUM_FRABRICATE")
	private String numFrabricate;

	@Column(name="NUM_ORIGINAL")
	private String numOriginal;

	@Column(name="QTD_EMB_ATACADO")
	private BigDecimal qtdEmbAtacado;

	@Column(name="QTD_EMB_COMPRA")
	private BigDecimal qtdEmbCompra;

	@Column(name="QTD_EMB_VENDA")
	private BigDecimal qtdEmbVenda;

	@Column(name="QTD_LITROS")
	private BigDecimal qtdLitros;

	@Column(name="MEDIDA_M3")
	private BigDecimal medidaM3;

	@Column(name="QTD_PALLET")
	private BigDecimal qtdPallet;
	
	@Column(name="QTD_VOLUMES")
	private BigDecimal qtdVolumes;

	@Column(name="PESO_BRUTO")
	private BigDecimal pesoBruto;

	@Column(name="PESO_LIQUIDO")
	private BigDecimal pesoLiquido;
	
	private String observacao;
	
	private BigInteger utctag;
	
	
	//-----------------Dados adicionais
	
	@Column(name="BALANCA_DIAS_VALIDADE")
	private Integer balancaDiasValidade;

	@Column(name="BALANCA_INSTRUCOES")
	private String balancaInstrucoes;

	@Column(name="BALANCA_TIPO_PRECO")
	private Integer balancaTipoPreco;

	@Column(name="FLAG_BALANCA")
	private Integer flagBalanca;	
	
    @Column(name="FLAG_DISPONIVEL_NET")
	private Integer flagDisponivelNet;
	
	@Column(name="FLAG_MOBILE")
	private Integer flagMobile;
	
	@Column(name="PROCEDIMENTO_ESTOQUE_NEGATIVO")
	private Integer procedimentoEstoqueNegativo;
	
	@Column(name="FLAG_ITEM_COMPOSTO")
	private Integer flagItemComposto;
	
	@Column(name="DADOS_ADICIONAIS")
	private String dadosAdicionais;
	
	@Column(name="CODIGO_ANP")
	private String codigoAnp;
	
	@Column(name="PERCENTUAL_GAS_ANP")
	private BigDecimal percentualGasAnp;
	
	
	@Column(name="COD_ITEM_SIMILAR1")
	private Integer codItemSimilar1 = 0;
	
	@Column(name="COD_ITEM_SIMILAR1_FK")
	private BigInteger codItemSimilar1Fk = BigInteger.valueOf(0);
	
	@Column(name="COD_ITEM_SIMILAR2")
	private Integer codItemSimilar2 = 0;
	
	@Column(name="COD_ITEM_SIMILAR2_FK")
	private BigInteger codItemSimilar2Fk  = BigInteger.valueOf(0);
	
	@Column(name="COD_ITEM_SIMILAR3")
	private Integer codItemSimilar3 = 0;
	
	@Column(name="COD_ITEM_SIMILAR3_FK")
	private BigInteger codItemSimilar3Fk  = BigInteger.valueOf(0);
	
	@Column(name="COD_ITEM_SIMILAR4")
	private Integer codItemSimilar4 = 0;
	
	@Column(name="COD_ITEM_SIMILAR4_FK")
	private BigInteger codItemSimilar4Fk =  BigInteger.valueOf(0);
	
	@Column(name="COD_ITEM_SIMILAR5")
	private Integer codItemSimilar5 = 0;
	
	@Column(name="COD_ITEM_SIMILAR5_FK")
	private BigInteger codItemSimilar5Fk = BigInteger.valueOf(0);
	
	@Column(name="COD_ITEM_SIMILAR6")
	private Integer codItemSimilar6 = 0;
	
	@Column(name="COD_ITEM_SIMILAR6_FK")
	private BigInteger codItemSimilar6Fk  = BigInteger.valueOf(0);
	
	//----------------------------- Begin Components of Cadastro Itens - Dados Veiculos ------------------------------------------------
	@Column(name="FLAG_VEICULO")
	private Integer flagVeiculo;
	
	@Column(name="VEICULO_ANO_FABRICACAO")
	private Integer veiculoAnoFabricacao;

	@Column(name="VEICULO_ANO_MODELO")
	private Integer veiculoAnoModelo;

	@Column(name="VEICULO_CHASSI")
	private String veiculoChassi;

	@Column(name="VEICULO_CM3")
	private BigDecimal veiculoCm3;

	@Column(name="VEICULO_CMKG")
	private BigDecimal veiculoCmkg;

	@Column(name="VEICULO_COD_MARCA_MODELO")
	private String veiculoCodMarcaModelo;

	@Column(name="VEICULO_CONDICAO")
	private Integer veiculoCondicao;

	@Column(name="VEICULO_CONDICAO_VIN")
	private char veiculoCondicaoVin;

	@Column(name="VEICULO_COR")
	private Integer veiculoCor;

	@Column(name="VEICULO_DISTANCIA_EIXOS")
	private BigDecimal veiculoDistanciaEixos;

	@Column(name="VEICULO_ESPECIE")
	private Integer veiculoEspecie;

	@Column(name="VEICULO_FLAG_ENVIA_VENDA")
	private Integer veiculoFlagEnviaVenda;

	@Column(name="VEICULO_LOTACAO")
	private Integer veiculoLotacao;

	@Column(name="VEICULO_NO_MOTOR")
	private String veiculoNoMotor;

	@Column(name="VEICULO_NO_SERIE")
	private String veiculoNoSerie;

	@Column(name="VEICULO_PESO_BRUTO")
	private BigDecimal veiculoPesoBruto;

	@Column(name="VEICULO_PESO_LIQUIDO")
	private BigDecimal veiculoPesoLiquido;

	@Column(name="VEICULO_POTENCIA")
	private BigDecimal veiculoPotencia;

	@Column(name="VEICULO_RENAVAM")
	private String veiculoRenavam;

	@Column(name="VEICULO_RESTRICAO")
	private Integer veiculoRestricao;

	@Column(name="VEICULO_TIPO")
	private Integer veiculoTipo;

	@Column(name="VEICULO_TIPO_COMBUSTIVEL")
	private Integer veiculoTipoCombustivel;

	@Column(name="VEICULO_TIPO_PINTURA")
	private char veiculoTipoPintura;	
	
	//----------------------------- Begin Components of Cadastro Itens - Dados Veiculos ------------------------------------------------
	

	//bi-directional many-to-one association to ItemValor
	@OneToMany(mappedBy = "item", fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
	private List<ItemValor> itemValors;	
	
	@OneToMany(mappedBy = "item", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private Set<ItemCodBar> itemCodBars = new HashSet<ItemCodBar>();
	
	public Set<ItemCodBar> getItemCodBars() {
		return itemCodBars;
	}

	public void setItemCodBars(Set<ItemCodBar> itemCodBars) {
		this.itemCodBars = itemCodBars;
	}
	
	@OneToMany(mappedBy = "item",cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private Set<ItemFornecedor> itemFornecedor = new HashSet<ItemFornecedor>();
	

	public Set<ItemFornecedor> getItemFornecedor() {
		return itemFornecedor;
	}

	public void setItemFornecedor(Set<ItemFornecedor> itemFornecedor) {
		this.itemFornecedor = itemFornecedor;
	}

	@OneToMany(mappedBy = "item",cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private Set<ItemEstoqueDeposito> itemEstoqueDeposito = new HashSet<ItemEstoqueDeposito>();
	
	public Set<ItemEstoqueDeposito> getItemEstoqueDeposito() {
		return itemEstoqueDeposito;
	}

	public void setItemEstoqueDeposito(Set<ItemEstoqueDeposito> itemEstoqueDeposito) {
		this.itemEstoqueDeposito = itemEstoqueDeposito;
	}
	
	@OneToMany(mappedBy = "item", fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
	private List<ItemMovimentacao> itemMovimentacao;	

	public List<ItemMovimentacao> getItemMovimentacao() {
		return itemMovimentacao;
	}

	public void setItemMovimentacao(List<ItemMovimentacao> itemMovimentacao) {
		this.itemMovimentacao = itemMovimentacao;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="COD_FABRICANTE" , referencedColumnName="CODIGO"),
		@JoinColumn(name="COD_FABRICANTE_FK" , referencedColumnName="CHECK_DELETE")
	})
	private Fabricante fabricante;
	
	public Fabricante getFabricante() {
		 try {
            Hibernate.initialize(this.fabricante);
        } catch(org.hibernate.ObjectNotFoundException one) {
        	this.fabricante = null;
        }
		return this.fabricante;
	}
	
	
	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}
	
	@Transient
	private String fabricanteDescricao;
	
	public String getFabricanteDescricao() {	
		this.fabricanteDescricao = "";
		if(Hibernate.isInitialized(fabricante)) { 
			try {
				this.fabricanteDescricao = this.fabricante.getDescricao();
			} catch(org.hibernate.ObjectNotFoundException one) {
				this.fabricante = null;
			}
		}
		return this.fabricanteDescricao;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="COD_GRUPO" , referencedColumnName="CODIGO"),
		@JoinColumn(name="COD_GRUPO_FK" , referencedColumnName="CHECK_DELETE")
	})
	private Grupo grupo;
	
	public Grupo getGrupo() {
        if(!Hibernate.isInitialized(this.grupo)) {
            try {
                Hibernate.initialize(this.grupo);
            } catch(Exception one) {
            	this.grupo = null;
            }
        }
        return this.grupo;
    }

	
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	@Transient
	private String grupoDescricao;
	
	public String getGrupoDescricao() {
		this.grupoDescricao = "";
		if(Hibernate.isInitialized(this.grupo)) { 
			try {
				this.grupoDescricao = this.grupo.getDescricao();
			} catch(Exception o) {
				this.grupo = null;
			}
		}
		return this.grupoDescricao;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="COD_SUBGRUPO" , referencedColumnName="CODIGO"),
		@JoinColumn(name="COD_SUBGRUPO_FK" , referencedColumnName="CHECK_DELETE")
	})
	private SubGrupo subGrupo;
	
	public SubGrupo getSubGrupo() {
		 if(!Hibernate.isInitialized(this.subGrupo)) {
	            try {
	                Hibernate.initialize(this.subGrupo);
	            } catch(Exception one) {
	            	this.subGrupo = null;
	            }
	        }
	        return this.subGrupo;
	}
	
	public void setSubGrupo(SubGrupo subGrupo) {
		this.subGrupo = subGrupo;
	}

	@Transient
	private String subGrupoDescricao;
	
	public String getSubGrupoDescricao() {
		this.subGrupoDescricao = "";
		if(Hibernate.isInitialized(subGrupo)) { 
			try {
				this.subGrupoDescricao = subGrupo.getDescricao();
			} catch(Exception one) {
				subGrupo = null;
			}
		}
		return this.subGrupoDescricao;
	}
	
	@Column(name="COD_DEPARTAMENTO")
	private Integer codDepartamento = 0;
	
	@Column(name="COD_DEPARTAMENTO_FK")
	private BigInteger codDepartamentoFk = BigInteger.valueOf(0);
	
	public Integer getCodDepartamento() {
		return codDepartamento;
	}

	public void setCodDepartamento(Integer codDepartamento) {
		this.codDepartamento = codDepartamento;
	}

	public BigInteger getCodDepartamentoFk() {
		return codDepartamentoFk;
	}

	public void setCodDepartamentoFk(BigInteger codDepartamentoFk) {
		this.codDepartamentoFk = codDepartamentoFk;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="COD_DEPARTAMENTO" , referencedColumnName="CODIGO", insertable=false, updatable=false),
		@JoinColumn(name="COD_DEPARTAMENTO_FK" , referencedColumnName="CHECK_DELETE", insertable=false, updatable=false)
	})
	private Departamento departamento;

	public Departamento getDepartamento() {
		 if(!Hibernate.isInitialized(this.departamento)) {
	            try {
	                Hibernate.initialize(this.departamento);
	            } catch(Exception one) {
	            	this.departamento = null;
	            }
	        }
	        return this.departamento;
	}
	
	
	
	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	@Transient
	private String departamentoDescricao;
	
	
	
	public String getDepartamentoDescricao() {
		 this.departamentoDescricao = "";
		if(Hibernate.isInitialized(departamento)) { 
			try {
				this.departamentoDescricao = departamento.getDescricao();
			} catch(Exception one) {
				departamento = null;
			}
		}
		return this.departamentoDescricao;
	}

	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="COD_UNIDADE" , referencedColumnName="CODIGO"),
		@JoinColumn(name="COD_UNIDADE_FK" , referencedColumnName="CHECK_DELETE")
	})
	private Unidade unidade;
	
	public Unidade getUnidade() {
		 if(!Hibernate.isInitialized(this.unidade)) {
	            try {
	                Hibernate.initialize(this.unidade);
	            } catch(Exception one) {
	            	this.unidade = null;
	            }
	        }
	        return this.unidade;
	}
	
	
	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	@Transient
	private String unidadeDescricao;
	
	public String getUnidadeDescricao() {
		this.unidadeDescricao = "";
		if(Hibernate.isInitialized(unidade)) { 
			try {
				this.unidadeDescricao = unidade.getDescricao();
			} catch(Exception one) {
				unidade = null;
			}
		}
		return this.unidadeDescricao;
	}

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="COD_UNIDADE_EMB" , referencedColumnName="CODIGO"),
		@JoinColumn(name="COD_UNIDADE_EMB_FK" , referencedColumnName="CHECK_DELETE")
	})
	private Unidade unidadeEmb;
	
	public Unidade getUnidadeEmb() {
		 if(!Hibernate.isInitialized(this.unidadeEmb)) {
	            try {
	                Hibernate.initialize(this.unidadeEmb);
	            } catch(Exception one) {
	            	this.unidadeEmb = null;
	            }
	        }
	        return this.unidadeEmb;
	}
	
	public void setUnidadeEmb(Unidade unidadeEmb) {
		this.unidadeEmb = unidadeEmb;
	}

	@Transient
	private String unidadeEmbDescricao;
	
	public String getUnidadeEmbDescricao() {
		this.unidadeEmbDescricao = "";
		if(Hibernate.isInitialized(unidadeEmb)) { 
			try {
				this.unidadeEmbDescricao = unidadeEmb.getDescricao();
			} catch(org.hibernate.ObjectNotFoundException one) {
				unidadeEmb = null;
			}
		}
		return this.unidadeEmbDescricao;
	}

	@Column(name="COD_FAMILIA_PRECO")
	private Integer codFamiliaPreco = 0;
	
	@Column(name="COD_FAMILIA_PRECO_FK")
	private BigInteger codFamiliaPrecoFk = BigInteger.valueOf(0) ;
	
	
	public Integer getCodFamiliaPreco() {
		return codFamiliaPreco;
	}

	public void setCodFamiliaPreco(Integer codFamiliaPreco) {
		this.codFamiliaPreco = codFamiliaPreco;
	}

	public BigInteger getCodFamiliaPrecoFk() {
		return codFamiliaPrecoFk;
	}

	public void setCodFamiliaPrecoFk(BigInteger codFamiliaPrecoFk) {
		this.codFamiliaPrecoFk = codFamiliaPrecoFk;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="COD_FAMILIA_PRECO" , referencedColumnName="CODIGO", insertable=false, updatable=false),
		@JoinColumn(name="COD_FAMILIA_PRECO_FK" , referencedColumnName="CHECK_DELETE",insertable=false,updatable=false)
	})
	private FamiliaPreco familiaPreco;
	
	public FamiliaPreco getFamiliaPreco() {
		 if(!Hibernate.isInitialized(this.familiaPreco)) {
	            try {
	                Hibernate.initialize(this.familiaPreco);
	            } catch(Exception one) {
	            	this.familiaPreco = null;
	            }
	        }
	        return this.familiaPreco;
	}
	
	public void setFamiliaPreco(FamiliaPreco familiaPreco) {
		
			this.familiaPreco = familiaPreco;
			
	}

	@Transient
	private String familiaPrecoDescricao;

	public String getFamiliaPrecoDescricao() {
		this.familiaPrecoDescricao = "";
		if(Hibernate.isInitialized(familiaPreco)) { 
			try {
				this.familiaPrecoDescricao = familiaPreco.getDescricao();
			} catch(Exception one) {
				familiaPreco = null;
			}
		}
		return this.familiaPrecoDescricao;
	}
	

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="COD_MOEDA" , referencedColumnName="CODIGO"),
		@JoinColumn(name="COD_MOEDA_FK" , referencedColumnName="CHECK_DELETE")
	})
	private Moeda moeda;
	
	public Moeda getMoeda() {
		 if(!Hibernate.isInitialized(this.moeda)) {
	            try {
	                Hibernate.initialize(this.moeda);
	            } catch(Exception one) {
	            	this.moeda = null;
	            }
	        }
	        return this.moeda;
	}
	
	public void setMoeda(Moeda moeda) {
		this.moeda = moeda;
	}

	
	@Column(name="COD_NCM")
	private Integer codNcm = 0;
	
	@Column(name="COD_NCM_FK")
	private BigInteger codNcmFk = BigInteger.valueOf(0);
	
	public Integer getCodNcm() {
		return codNcm;
	}

	public void setCodNcm(Integer codNcm) {
		this.codNcm = codNcm;
	}

	public BigInteger getCodNcmFk() {
		return codNcmFk;
	}

	public void setCodNcmFk(BigInteger codNcmFk) {
		this.codNcmFk = codNcmFk;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="COD_NCM" , referencedColumnName="CODIGO", insertable = false, updatable = false),
		@JoinColumn(name="COD_NCM_FK" , referencedColumnName="CHECK_DELETE", insertable = false, updatable = false)
	})
	private TabelaNCM ncm;
	
	public TabelaNCM getNcm() {
		 if(!Hibernate.isInitialized(this.ncm)) {
	            try {
	                Hibernate.initialize(this.ncm);
	            } catch(Exception one) {
	            	this.ncm = null;
	            }
	        }
	        return this.ncm;
	}

	
	public void setNcm(TabelaNCM ncm) {
		this.ncm = ncm;
		setNcmCodigo(ncm.getCodNCM());
	}

	
	@Transient
	private String ncmDescricao;
	
	public String getNcmDescricao() {
		this.ncmDescricao = "";
		if(Hibernate.isInitialized(ncm)) { 
			try {
				this.ncmDescricao = ncm.getDescricao();
			} catch(Exception one) {
				ncm = null;
			}
		}
		return this.ncmDescricao;
	}

	@Column(name="NCM")
	private String ncmCodigo = "";
	
	
	public String getNcmCodigo() {
		return this.ncmCodigo;
	}
	
	
	public void setNcmCodigo(String ncmCodigo) {
		this.ncmCodigo = ncmCodigo;
	}
	
	@Column(name="COD_LCSERVICO")
	private Integer codLcServico = 0;
	
	@Column(name="COD_LCSERVICO_FK")
	private BigInteger codLcServicoFk = BigInteger.valueOf(0);
	
	public Integer getCodLcServico() {
		return codLcServico;
	}

	public void setCodLcServico(Integer codLcServico) {
		this.codLcServico = codLcServico;
	}

	public BigInteger getCodLcServicoFk() {
		return codLcServicoFk;
	}

	public void setCodLcServicoFk(BigInteger codLcServicoFk) {
		this.codLcServicoFk = codLcServicoFk;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="COD_LCSERVICO" , referencedColumnName="CODIGO", insertable=false, updatable=false),
		@JoinColumn(name="COD_LCSERVICO_FK" , referencedColumnName="CHECK_DELETE", insertable=false, updatable=false)
	})
	private TabelaLcServico lcServico;
	
	public TabelaLcServico getLcServico() {
		 if(!Hibernate.isInitialized(this.lcServico)) {
	            try {
	                Hibernate.initialize(this.lcServico);
	            } catch(Exception one) {
	            	this.lcServico = null;
	            }
	        }
	        return this.lcServico;
	}
	
	
	public void setLcServico(TabelaLcServico lcServico) {
		this.lcServico = lcServico;
		setLcServicoCodigo(this.lcServico.getCodLcnfse());
	}

	@Transient
	private String lcServicoDescricao;
	
	public String getLcServicoDescricao() {
		this.lcServicoDescricao = "";
		if(Hibernate.isInitialized(lcServico)) { 
			try {
				this.lcServicoDescricao = lcServico.getDescricao();
			} catch(Exception one) {
				lcServico = null;
			}
		}
		return this.lcServicoDescricao;
	}

	@Column(name="LCSERVICO")
	private String lcServicoCodigo = "";

	public String getLcServicoCodigo() {
		return this.lcServicoCodigo;
	}

	public void setLcServicoCodigo(String lcServicoCodigo) {
		this.lcServicoCodigo = lcServicoCodigo;
	}
	
	public Item() {
	}

	/**
	 * @return the checkDelete
	 */
	public BigInteger getCheckDelete() {
		return checkDelete;
	}
	/**
	 * @param checkDelete the checkDelete to set
	 */
	public void setCheckDelete(BigInteger checkDelete) {
		this.checkDelete = checkDelete;
	}

	/**
	 * @return the codemp
	 */
	public Integer getCodemp() {
		return codemp;
	}

	/**
	 * @param codemp the codemp to set
	 */
	public void setCodemp(Integer codemp) {
		this.codemp = codemp;
	}

	/**
	 * @return the codigo
	 */
	public Integer getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
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
	
	public String getCodCest() {
		return codCest;
	}

	public void setCodCest(String codCest) {
		this.codCest = codCest;
	}

	public String getDescReduzida() {
		return descReduzida;
	}

	public void setDescReduzida(String descReduzida) {
		this.descReduzida = descReduzida;
	}

	public String getDescTecnica() {
		return descTecnica;
	}

	public void setDescTecnica(String descTecnica) {
		this.descTecnica = descTecnica;
	}

	
	public String getTipoItem() {
		return tipoItem;
	}

	public void setTipoItem(String tipoItem) {
		this.tipoItem = tipoItem;
	}

	public BigDecimal getFatorConversao() {
		return fatorConversao;
	}

	public void setFatorConversao(BigDecimal fatorConversao) {
		this.fatorConversao = fatorConversao;
	}

	public String getNumFrabricate() {
		return numFrabricate;
	}

	public void setNumFrabricate(String numFrabricate) {
		this.numFrabricate = numFrabricate;
	}

	public String getNumOriginal() {
		return numOriginal;
	}

	public void setNumOriginal(String numOriginal) {
		this.numOriginal = numOriginal;
	}

	public BigDecimal getQtdEmbAtacado() {
		return qtdEmbAtacado;
	}

	public void setQtdEmbAtacado(BigDecimal qtdEmbAtacado) {
		this.qtdEmbAtacado = qtdEmbAtacado;
	}

	public BigDecimal getQtdEmbCompra() {
		return qtdEmbCompra;
	}

	public void setQtdEmbCompra(BigDecimal qtdEmbCompra) {
		this.qtdEmbCompra = qtdEmbCompra;
	}

	public BigDecimal getQtdEmbVenda() {
		return qtdEmbVenda;
	}

	public void setQtdEmbVenda(BigDecimal qtdEmbVenda) {
		this.qtdEmbVenda = qtdEmbVenda;
	}

	public BigDecimal getQtdLitros() {
		return qtdLitros;
	}

	public void setQtdLitros(BigDecimal qtdLitros) {
		this.qtdLitros = qtdLitros;
	}

	public BigDecimal getMedidaM3() {
		return medidaM3;
	}

	public void setMedidaM3(BigDecimal medidaM3) {
		this.medidaM3 = medidaM3;
	}

	public BigDecimal getQtdPallet() {
		return qtdPallet;
	}

	public void setQtdPallet(BigDecimal qtdPallet) {
		this.qtdPallet = qtdPallet;
	}	
	
	public BigDecimal getQtdVolumes() {
		return qtdVolumes;
	}

	public void setQtdVolumes(BigDecimal qtdVolumes) {
		this.qtdVolumes = qtdVolumes;
	}	

	public BigDecimal getPesoBruto() {
		return pesoBruto;
	}

	public void setPesoBruto(BigDecimal pesoBruto) {
		this.pesoBruto = pesoBruto;
	}

	public BigDecimal getPesoLiquido() {
		return pesoLiquido;
	}

	public void setPesoLiquido(BigDecimal pesoLiquido) {
		this.pesoLiquido = pesoLiquido;
	}

	public BigInteger getUtctag() {
		return this.utctag;
	}

	public void setUtctag(BigInteger utctag) {
		this.utctag = utctag;
	}

	public List<ItemValor> getItemValors() {
		return this.itemValors;
	}

	public void setItemValors(List<ItemValor> itemValors) {
		this.itemValors = itemValors;
	}

	public Integer getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(Integer recordNo) {
		this.recordNo = recordNo;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public void adicionaItemValor(ItemValor item) {
	        this.itemValors.add(item);
	        item.setItem(this); // mantém a consistência
	 }
	
	//-------------------
	
	public Integer getBalancaDiasValidade() {
		return this.balancaDiasValidade;
	}

	public void setBalancaDiasValidade(Integer balancaDiasValidade) {
		this.balancaDiasValidade = balancaDiasValidade;
	}

	public String getBalancaInstrucoes() {
		return this.balancaInstrucoes;
	}

	public void setBalancaInstrucoes(String balancaInstrucoes) {
		this.balancaInstrucoes = balancaInstrucoes;
	}

	public Integer getBalancaTipoPreco() {
		return this.balancaTipoPreco;
	}

	public void setBalancaTipoPreco(Integer balancaTipoPreco) {
		this.balancaTipoPreco = balancaTipoPreco;
	}


	public Integer getFlagBalanca() {
		return this.flagBalanca;
	}

	public void setFlagBalanca(Integer flagBalanca) {
		this.flagBalanca = flagBalanca;
	}	
	
	public Integer getFlagDisponivelNet() {
		return this.flagDisponivelNet;
	}

	public void setFlagDisponivelNet(Integer flagDisponivelNet) {
		this.flagDisponivelNet = flagDisponivelNet;
	}


	public Integer getFlagMobile() {
		return this.flagMobile;
	}

	public void setFlagMobile(Integer flagMobile) {
		this.flagMobile = flagMobile;
	}


	public Integer getProcedimentoEstoqueNegativo() {
		return this.procedimentoEstoqueNegativo;
	}

	public void setProcedimentoEstoqueNegativo(Integer procedimentoEstoqueNegativo) {
		this.procedimentoEstoqueNegativo = procedimentoEstoqueNegativo;
	}


	public Integer getFlagItemComposto() {
		return this.flagItemComposto;
	}

	public void setFlagItemComposto(Integer flagItemComposto) {
		this.flagItemComposto = flagItemComposto;
	}

	public String getDadosAdicionais() {
		return this.dadosAdicionais;
	}

	public void setDadosAdicionais(String dadosAdicionais) {
		this.dadosAdicionais = dadosAdicionais;
	}

	public String getCodigoAnp() {
		return this.codigoAnp;
	}

	public void setCodigoAnp(String codigoAnp) {
		this.codigoAnp = codigoAnp;
	}
	public BigDecimal getPercentualGasAnp() {
		return this.percentualGasAnp;
	}

	public void setPercentualGasAnp(BigDecimal percentualGasAnp) {
		this.percentualGasAnp = percentualGasAnp;
	}


	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="COD_ITEM_SIMILAR1" , referencedColumnName="CODIGO", insertable=false, updatable=false),
		@JoinColumn(name="COD_ITEM_SIMILAR1_FK" , referencedColumnName="CHECK_DELETE", insertable=false, updatable=false)
	})
	private Item itemSimilar1;
	
	public Item getItemSimilar1() {
		 if(!Hibernate.isInitialized(this.itemSimilar1)) {
	            try {
	                Hibernate.initialize(this.itemSimilar1);
	            } catch(Exception one) {
	            	this.itemSimilar1 = null;
	            }
	        }
	        return this.itemSimilar1;
	}
	
	public void setItemSimilar1(Item itemSimilar1) {
		this.itemSimilar1 = itemSimilar1;
	}

	@Transient
	private String itemSimilar1Descricao;
	
	public String getItemSimilar1Descricao() {
		this.itemSimilar1Descricao = "";
		if(Hibernate.isInitialized(itemSimilar1)) { 
			try {
				this.itemSimilar1Descricao = itemSimilar1.getDescricao();
			} catch(Exception one) {
				itemSimilar1 = null;
			}
		}
		return this.itemSimilar1Descricao;
	}
	
	public Integer getCodItemSimilar1() {
		return this.codItemSimilar1;
	}

	public void setCodItemSimilar1(Integer codItemSimilar1) {
		this.codItemSimilar1 = codItemSimilar1;
	}

	public BigInteger getCodItemSimilar1Fk() {
		return this.codItemSimilar1Fk;
	}

	public void setCodItemSimilar1Fk(BigInteger codItemSimilar1Fk) {
		this.codItemSimilar1Fk = codItemSimilar1Fk;
	}

	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="COD_ITEM_SIMILAR2" , referencedColumnName="CODIGO", insertable=false, updatable=false),
		@JoinColumn(name="COD_ITEM_SIMILAR2_FK" , referencedColumnName="CHECK_DELETE", insertable=false, updatable=false)
	})
	private Item itemSimilar2;
	
	public Item getItemSimilar2() {
		 if(!Hibernate.isInitialized(this.itemSimilar2)) {
	            try {
	                Hibernate.initialize(this.itemSimilar2);
	            } catch(Exception one) {
	            	this.itemSimilar2 = null;
	            }
	        }
	        return this.itemSimilar2;
	}
	
	public void setItemSimilar2(Item itemSimilar2) {
		this.itemSimilar2 = itemSimilar2;
	}

	@Transient
	private String itemSimilar2Descricao;
	
	public String getItemSimilar2Descricao() {
		this.itemSimilar2Descricao = "";
		if(Hibernate.isInitialized(itemSimilar2)) { 
			try {
				this.itemSimilar2Descricao = itemSimilar2.getDescricao();
			} catch(Exception one) {
				itemSimilar2 = null;
			}
		}
		return this.itemSimilar2Descricao;
	}
	
	public Integer getCodItemSimilar2() {
		return this.codItemSimilar2;
	}

	public void setCodItemSimilar2(Integer codItemSimilar2) {
		this.codItemSimilar2 = codItemSimilar2;
	}

	public BigInteger getCodItemSimilar2Fk() {
		return this.codItemSimilar2Fk;
	}

	public void setCodItemSimilar2Fk(BigInteger codItemSimilar2Fk) {
		this.codItemSimilar2Fk = codItemSimilar2Fk;
	}

	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="COD_ITEM_SIMILAR3" , referencedColumnName="CODIGO", insertable=false, updatable=false),
		@JoinColumn(name="COD_ITEM_SIMILAR3_FK" , referencedColumnName="CHECK_DELETE", insertable=false, updatable=false)
	})
	private Item itemSimilar3;
	
	public Item getItemSimilar3() {
		 if(!Hibernate.isInitialized(this.itemSimilar3)) {
	            try {
	                Hibernate.initialize(this.itemSimilar3);
	            } catch(Exception one) {
	            	this.itemSimilar3 = null;
	            }
	        }
	        return this.itemSimilar3;
	}
	
	public void setItemSimilar3(Item itemSimilar3) {
		this.itemSimilar3 = itemSimilar3;
	}
	
	@Transient
	private String itemSimilar3Descricao;
	
	public String getItemSimilar3Descricao() {
		this.itemSimilar3Descricao = "";
		if(Hibernate.isInitialized(itemSimilar3)) { 
			try {
				this.itemSimilar3Descricao = itemSimilar3.getDescricao();
			} catch(Exception one) {
				itemSimilar3 = null;
			}
		}
		return this.itemSimilar3Descricao;
	}
	
	public Integer getCodItemSimilar3() {
		return this.codItemSimilar3;
	}

	public void setCodItemSimilar3(Integer codItemSimilar3) {
		this.codItemSimilar3 = codItemSimilar3;
	}

	public BigInteger getCodItemSimilar3Fk() {
		return this.codItemSimilar3Fk;
	}

	public void setCodItemSimilar3Fk(BigInteger itemSimilar3Fk) {
		this.codItemSimilar3Fk = itemSimilar3Fk;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="COD_ITEM_SIMILAR4" , referencedColumnName="CODIGO", insertable=false, updatable=false),
		@JoinColumn(name="COD_ITEM_SIMILAR4_FK" , referencedColumnName="CHECK_DELETE", insertable=false, updatable=false)
	})
	private Item itemSimilar4;
	
	public Item getItemSimilar4() {
		 if(!Hibernate.isInitialized(this.itemSimilar4)) {
	            try {
	                Hibernate.initialize(this.itemSimilar4);
	            } catch(Exception one) {
	            	this.itemSimilar4 = null;
	            }
	        }
	        return this.itemSimilar4;
	}
	
	public void setItemSimilar4(Item itemSimilar4) {
		this.itemSimilar4 = itemSimilar4;
	}

	@Transient
	private String itemSimilar4Descricao;
	
	public String getItemSimilar4Descricao() {
		this.itemSimilar4Descricao = "";
		if(Hibernate.isInitialized(itemSimilar4)) { 
			try {
				this.itemSimilar4Descricao = itemSimilar4.getDescricao();
			} catch(Exception one) {
				itemSimilar4 = null;
			}
		}
		return this.itemSimilar4Descricao;
	}
	
	public Integer getCodItemSimilar4() {
		return this.codItemSimilar4;
	}

	public void setCodItemSimilar4(Integer codItemSimilar4) {
		this.codItemSimilar4 = codItemSimilar4;
	}

	public BigInteger getCodItemSimilar4Fk() {
		return this.codItemSimilar4Fk;
	}

	public void setCodItemSimilar4Fk(BigInteger codItemSimilar4Fk) {
		this.codItemSimilar4Fk = codItemSimilar4Fk;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="COD_ITEM_SIMILAR5" , referencedColumnName="CODIGO", insertable=false, updatable=false),
		@JoinColumn(name="COD_ITEM_SIMILAR5_FK" , referencedColumnName="CHECK_DELETE", insertable=false, updatable=false)
	})
	private Item itemSimilar5;
	
	public Item getItemSimilar5() {
		 if(!Hibernate.isInitialized(this.itemSimilar5)) {
	            try {
	                Hibernate.initialize(this.itemSimilar5);
	            } catch(Exception one) {
	            	this.itemSimilar5 = null;
	            }
	        }
	        return this.itemSimilar5;
	}
	
	public void setItemSimilar5(Item itemSimilar5) {
		this.itemSimilar5 = itemSimilar5;
	}

	@Transient
	private String itemSimilar5Descricao;
	
	public String getItemSimilar5Descricao() {
		this.itemSimilar5Descricao = "";
		if(Hibernate.isInitialized(itemSimilar5)) { 
			try {
				this.itemSimilar5Descricao = itemSimilar5.getDescricao();
			} catch(Exception one) {
				itemSimilar5 = null;
			}
		}
		return this.itemSimilar5Descricao;
	}
	
	
	public Integer getCodItemSimilar5() {
		return this.codItemSimilar5;
	}

	public void setCodItemSimilar5(Integer codItemSimilar5) {
		this.codItemSimilar5 = codItemSimilar5;
	}

	public BigInteger getCodItemSimilar5Fk() {
		return this.codItemSimilar5Fk;
	}

	public void setCodItemSimilar5Fk(BigInteger codItemSimilar5Fk) {
		this.codItemSimilar5Fk = codItemSimilar5Fk;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="COD_ITEM_SIMILAR6" , referencedColumnName="CODIGO", insertable=false, updatable=false),
		@JoinColumn(name="COD_ITEM_SIMILAR6_FK" , referencedColumnName="CHECK_DELETE", insertable=false, updatable=false)
	})
	private Item itemSimilar6;
	
	public Item getItemSimilar6() {
		 if(!Hibernate.isInitialized(this.itemSimilar6)) {
	            try {
	                Hibernate.initialize(this.itemSimilar6);
	            } catch(Exception one) {
	            	this.itemSimilar6 = null;
	            }
	        }
	        return this.itemSimilar6;
	}
	
	public void setItemSimilar6(Item itemSimilar6) {
		this.itemSimilar6 = itemSimilar6;
	}

	@Transient
	private String itemSimilar6Descricao;
	
	public String getItemSimilar6Descricao() {
		this.itemSimilar6Descricao = "";
		if(Hibernate.isInitialized(itemSimilar6)) { 
			try {
				this.itemSimilar6Descricao = itemSimilar6.getDescricao();
			} catch(Exception one) {
				itemSimilar6 = null;
			}
		}
		return this.itemSimilar6Descricao;
	}
	
	public Integer getCodItemSimilar6() {
		return this.codItemSimilar6;
	}

	public void setCodItemSimilar6(Integer codItemSimilar6) {
		this.codItemSimilar6 = codItemSimilar6;
	}

	public BigInteger getCodItemSimilar6Fk() {
		return this.codItemSimilar6Fk;
	}

	public void setCodItemSimilar6Fk(BigInteger codItemSimilar6Fk) {
		this.codItemSimilar6Fk = codItemSimilar6Fk;
	}

	//--------------------
	
	public Integer getVeiculoFlag() 
	{
		return this.flagVeiculo;
	}

	public void setVeiculoFlag(Integer flagVeiculo)
	{
		this.flagVeiculo = flagVeiculo;
	}
	
	public Integer getVeiculoAnoFabricacao() 
	{
		return this.veiculoAnoFabricacao;
	}

	public void setVeiculoAnoFabricacao(Integer veiculoAnoFabricacao) 
	{
		this.veiculoAnoFabricacao = veiculoAnoFabricacao;
	}

	public Integer getVeiculoAnoModelo() 
	{
		return this.veiculoAnoModelo;
	}

	public void setVeiculoAnoModelo(Integer veiculoAnoModelo) 
	{
		this.veiculoAnoModelo = veiculoAnoModelo;
	}

	public String getVeiculoChassi() 
	{
		return this.veiculoChassi;
	}

	public void setVeiculoChassi(String veiculoChassi) 
	{
		this.veiculoChassi = veiculoChassi;
	}

	public BigDecimal getVeiculoCm3() 
	{
		return this.veiculoCm3;
	}

	public void setVeiculoCm3(BigDecimal veiculoCm3) 
	{
		this.veiculoCm3 = veiculoCm3;
	}

	public BigDecimal getVeiculoCmkg() 
	{
		return this.veiculoCmkg;
	}

	public void setVeiculoCmkg(BigDecimal veiculoCmkg) 
	{
		this.veiculoCmkg = veiculoCmkg;
	}

	public String getVeiculoCodMarcaModelo() 
	{
		return this.veiculoCodMarcaModelo;
	}

	public void setVeiculoCodMarcaModelo(String veiculoCodMarcaModelo) 
	{
		this.veiculoCodMarcaModelo = veiculoCodMarcaModelo;
	}

	public Integer getVeiculoCondicao() {
		return this.veiculoCondicao;
	}

	public void setVeiculoCondicao(Integer veiculoCondicao) {
		this.veiculoCondicao = veiculoCondicao;
	}

	public char getVeiculoCondicaoVin() {
		return this.veiculoCondicaoVin;
	}

	public void setVeiculoCondicaoVin(char veiculoCondicaoVin) {
		this.veiculoCondicaoVin = veiculoCondicaoVin;
	}

	public Integer getVeiculoCor() {
		return this.veiculoCor;
	}

	public void setVeiculoCor(Integer veiculoCor) {
		this.veiculoCor = veiculoCor;
	}

	public BigDecimal getVeiculoDistanciaEixos() {
		return this.veiculoDistanciaEixos;
	}

	public void setVeiculoDistanciaEixos(BigDecimal veiculoDistanciaEixos) {
		this.veiculoDistanciaEixos = veiculoDistanciaEixos;
	}

	public Integer getVeiculoEspecie() {
		return this.veiculoEspecie;
	}

	public void setVeiculoEspecie(Integer veiculoEspecie) {
		this.veiculoEspecie = veiculoEspecie;
	}

	public Integer getVeiculoFlagEnviaVenda() {
		return this.veiculoFlagEnviaVenda;
	}

	public void setVeiculoFlagEnviaVenda(Integer veiculoFlagEnviaVenda) {
		this.veiculoFlagEnviaVenda = veiculoFlagEnviaVenda;
	}

	public Integer getVeiculoLotacao() {
		return this.veiculoLotacao;
	}

	public void setVeiculoLotacao(Integer veiculoLotacao) {
		this.veiculoLotacao = veiculoLotacao;
	}

	public String getVeiculoNoMotor() {
		return this.veiculoNoMotor;
	}

	public void setVeiculoNoMotor(String veiculoNoMotor) {
		this.veiculoNoMotor = veiculoNoMotor;
	}

	public String getVeiculoNoSerie() {
		return this.veiculoNoSerie;
	}

	public void setVeiculoNoSerie(String veiculoNoSerie) {
		this.veiculoNoSerie = veiculoNoSerie;
	}

	public BigDecimal getVeiculoPesoBruto() {
		return this.veiculoPesoBruto;
	}

	public void setVeiculoPesoBruto(BigDecimal veiculoPesoBruto) {
		this.veiculoPesoBruto = veiculoPesoBruto;
	}

	public BigDecimal getVeiculoPesoLiquido() {
		return this.veiculoPesoLiquido;
	}

	public void setVeiculoPesoLiquido(BigDecimal veiculoPesoLiquido) {
		this.veiculoPesoLiquido = veiculoPesoLiquido;
	}

	public BigDecimal getVeiculoPotencia() {
		return this.veiculoPotencia;
	}

	public void setVeiculoPotencia(BigDecimal veiculoPotencia) {
		this.veiculoPotencia = veiculoPotencia;
	}

	public String getVeiculoRenavam() {
		return this.veiculoRenavam;
	}

	public void setVeiculoRenavam(String veiculoRenavam) {
		this.veiculoRenavam = veiculoRenavam;
	}

	public Integer getVeiculoRestricao() {
		return this.veiculoRestricao;
	}

	public void setVeiculoRestricao(Integer veiculoRestricao) {
		this.veiculoRestricao = veiculoRestricao;
	}

	public Integer getVeiculoTipo() {
		return this.veiculoTipo;
	}

	public void setVeiculoTipo(Integer veiculoTipo) {
		this.veiculoTipo = veiculoTipo;
	}

	public Integer getVeiculoTipoCombustivel() {
		return this.veiculoTipoCombustivel;
	}

	public void setVeiculoTipoCombustivel(Integer veiculoTipoCombustivel) {
		this.veiculoTipoCombustivel = veiculoTipoCombustivel;
	}

	public char getVeiculoTipoPintura() {
		return this.veiculoTipoPintura;
	}

	public void setVeiculoTipoPintura(char veiculoTipoPintura) {
		this.veiculoTipoPintura = veiculoTipoPintura;
	}

		
}