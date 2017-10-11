package models.compras;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.math.BigInteger;


/**
 * The persistent class for the nf_entrada_cab database table.
 * 
 */
@Entity
@Table(name="nf_entrada_cab")
@NamedQueries({

	@NamedQuery(name="NfEntradaCab.findAll", query="SELECT nfc FROM NfEntradaCab nfc "
			+ "LEFT JOIN FETCH nfc.fornecedor forn "
			+ "LEFT JOIN FETCH nfc.detProdutos prod "
			+ "LEFT JOIN FETCH nfc.detFaturamento fat "
			+ "LEFT JOIN FETCH nfc.nfEntradaXML xml "
			),

	@NamedQuery(name="getByNoDoctoFornecedor", query="SELECT nfc FROM NfEntradaCab nfc "
			+ "LEFT JOIN FETCH nfc.fornecedor forn "
			+ "LEFT JOIN FETCH nfc.detProdutos prod "
			+ "LEFT JOIN FETCH nfc.detFaturamento fat "
			+ "LEFT JOIN FETCH nfc.nfEntradaXML xml "
			+ "WHERE nfc.noDocto = :noDocto AND nfc.fornecedor = :fornecedor")
})
@IdClass(value = NfEntradaCabPK.class)
public class NfEntradaCab implements Serializable {
	private static final long serialVersionUID = 1L;

	//	@EmbeddedId
	//	private NfEntradaCabPK id;

	@Id
	@Column(name="CHECK_DELETE")
	private BigInteger checkDelete;

	@Id
	@Column(name="NO_DOCTO")
	private Integer noDocto;

	//@Id
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumns({
		@JoinColumn(name = "COD_FORNECEDOR_FK", referencedColumnName= "CHECK_DELETE"),
		@JoinColumn(name = "COD_FORNECEDOR", referencedColumnName= "CODIGO")
	})
	private Fornecedor fornecedor;

	private Integer codemp;

	private String chnfe;

	@Column(name="COD_OPERACAO")
	private Integer codOperacao;

	@Column(name="COD_OPERACAO_FK")
	private BigInteger codOperacaoFk;

	@Column(name="COD_SECAO")
	private Integer codSecao;

	@Column(name="DEST_CNPJ")
	private String destCnpj;

	@Column(name="DEST_CPF")
	private String destCpf;

	@Column(name="DEST_IDESTRANGEIRO")
	private String destIdestrangeiro;

	@Column(name="DEST_XNOME")
	private String destXnome;

	private Timestamp dhrecbto;

	@Column(name="DT_EMISSAO")
	private Timestamp dtEmissao;

	@Column(name="DT_SAIDA_CHEGADA")
	private Timestamp dtSaidaChegada;

	@Column(name="EMIT_CNPJ")
	private String emitCnpj;

	@Column(name="EMIT_CPF")
	private String emitCpf;

	@Column(name="EMIT_XFANT")
	private String emitXfant;

	@Column(name="EMIT_XNOME")
	private String emitXnome;

	@Column(name="ENDERDEST_CEP")
	private Integer enderdestCep;

	@Column(name="ENDERDEST_CMUN")
	private Integer enderdestCmun;

	@Column(name="ENDERDEST_CPAIS")
	private Integer enderdestCpais;

	@Column(name="ENDERDEST_EMAIL")
	private String enderdestEmail;

	@Column(name="ENDERDEST_FONE")
	private String enderdestFone;

	@Column(name="ENDERDEST_IE")
	private String enderdestIe;

	@Column(name="ENDERDEST_IM")
	private String enderdestIm;

	@Column(name="ENDERDEST_INDIEDEST")
	private Integer enderdestIndiedest;

	@Column(name="ENDERDEST_ISUF")
	private Integer enderdestIsuf;

	@Column(name="ENDERDEST_NRO")
	private String enderdestNro;

	@Column(name="ENDERDEST_UF")
	private String enderdestUf;

	@Column(name="ENDERDEST_XBAIRRO")
	private String enderdestXbairro;

	@Column(name="ENDERDEST_XCPL")
	private String enderdestXcpl;

	@Column(name="ENDERDEST_XLGR")
	private String enderdestXlgr;

	@Column(name="ENDERDEST_XMUN")
	private String enderdestXmun;

	@Column(name="ENDERDEST_XPAIS")
	private String enderdestXpais;

	@Column(name="ENDEREMIT_CEP")
	private Integer enderemitCep;

	@Column(name="ENDEREMIT_CMUN")
	private Integer enderemitCmun;

	@Column(name="ENDEREMIT_CNAE")
	private Integer enderemitCnae;

	@Column(name="ENDEREMIT_CPAIS")
	private Integer enderemitCpais;

	@Column(name="ENDEREMIT_CRT")
	private Integer enderemitCrt;

	@Column(name="ENDEREMIT_FONE")
	private String enderemitFone;

	@Column(name="ENDEREMIT_IE")
	private String enderemitIe;

	@Column(name="ENDEREMIT_IEST")
	private String enderemitIest;

	@Column(name="ENDEREMIT_IM")
	private String enderemitIm;

	@Column(name="ENDEREMIT_NRO")
	private String enderemitNro;

	@Column(name="ENDEREMIT_UF")
	private String enderemitUf;

	@Column(name="ENDEREMIT_XBAIRRO")
	private String enderemitXbairro;

	@Column(name="ENDEREMIT_XCPL")
	private String enderemitXcpl;

	@Column(name="ENDEREMIT_XLGR")
	private String enderemitXlgr;

	@Column(name="ENDEREMIT_XMUN")
	private String enderemitXmun;

	@Column(name="ENDEREMIT_XPAIS")
	private String enderemitXpais;

	@Column(name="ENTREGA_CMUN")
	private Integer entregaCmun;

	@Column(name="ENTREGA_CNPJ")
	private String entregaCnpj;

	@Column(name="ENTREGA_CPF")
	private String entregaCpf;

	@Column(name="ENTREGA_NRO")
	private String entregaNro;

	@Column(name="ENTREGA_UF")
	private String entregaUf;

	@Column(name="ENTREGA_XBAIRRO")
	private String entregaXbairro;

	@Column(name="ENTREGA_XCPL")
	private String entregaXcpl;

	@Column(name="ENTREGA_XLGR")
	private String entregaXlgr;

	@Column(name="ENTREGA_XMUN")
	private String entregaXmun;

	@Column(name="EXPORTA_UFSAIDAPAIS")
	private String exportaUfsaidapais;

	@Column(name="EXPORTA_XLOCDESPACHO")
	private String exportaXlocdespacho;

	@Column(name="EXPORTA_XLOCEXPORTA")
	private String exportaXlocexporta;

	@Column(name="FLAG_ATIVO")
	private Integer flagAtivo;

	@Column(name="IDE_CDV")
	private Integer ideCdv;

	@Column(name="IDE_CMUNFG")
	private Integer ideCmunfg;

	@Column(name="IDE_CNF")
	private Integer ideCnf;

	@Column(name="IDE_CUF")
	private Integer ideCuf;

	@Column(name="IDE_DHCONT")
	private LocalDateTime ideDhcont;

	@Column(name="IDE_DHEMI")
	private LocalDateTime ideDhemi;

	@Column(name="IDE_DHSAIENT")
	private LocalDateTime ideDhsaient;

	@Column(name="IDE_FINNFE")
	private Integer ideFinnfe;

	@Column(name="IDE_IDDEST")
	private Integer ideIddest;

	@Column(name="IDE_INDFINAL")
	private Integer ideIndfinal;

	@Column(name="IDE_INDPAG")
	private Integer ideIndpag;

	@Column(name="IDE_INDPRES")
	private Integer ideIndpres;

	@Column(name="IDE_MOD")
	private Integer ideMod;

	@Column(name="IDE_NATOP")
	private String ideNatop;

	@Column(name="IDE_NNF")
	private Integer ideNnf;

	@Column(name="IDE_PROCEMI")
	private Integer ideProcemi;

	@Column(name="IDE_SERIE")
	private Integer ideSerie;

	@Column(name="IDE_TPAMB")
	private Integer ideTpamb;

	@Column(name="IDE_TPEMIS")
	private Integer ideTpemis;

	@Column(name="IDE_TPIMP")
	private Integer ideTpimp;

	@Column(name="IDE_TPNF")
	private Integer ideTpnf;

	@Column(name="IDE_VERPROC")
	private String ideVerproc;

	@Column(name="IDE_XJUST")
	private String ideXjust;

	//	@Lob
	@Column(name="INFADIC_INFCPL")
	private String infadicInfcpl;

	//	@L ob
	@Column(name="INFADIC_NFADFISCO")
	private String infadicNfadfisco;

	@Column(name="LAST_CODUSER")
	private Integer lastCoduser;

	@Column(name="LAST_MOVTO")
	private LocalDateTime lastMovto;

	private String nprot;

	@Column(name="OBSCONT_XCAMPO")
	private String obscontXcampo;

	@Column(name="OBSCONT_XTEXTO")
	private String obscontXtexto;

	@Column(name="OBSFISCO_XCAMPO")
	private String obsfiscoXcampo;

	@Column(name="OBSFISCO_XTEXTO")
	private String obsfiscoXtexto;

	@Column(name="PROCREF_INDPROC")
	private Integer procrefIndproc;

	@Column(name="PROCREF_NPROC")
	private String procrefNproc;

	@Column(name="RECORD_NO")
	private Integer recordNo;

	@Column(name="RETIRADA_CMUN")
	private Integer retiradaCmun;

	@Column(name="RETIRADA_CNPJ")
	private String retiradaCnpj;

	@Column(name="RETIRADA_CPF")
	private String retiradaCpf;

	@Column(name="RETIRADA_NRO")
	private String retiradaNro;

	@Column(name="RETIRADA_UF")
	private String retiradaUf;

	@Column(name="RETIRADA_XBAIRRO")
	private String retiradaXbairro;

	@Column(name="RETIRADA_XCPL")
	private String retiradaXcpl;

	@Column(name="RETIRADA_XLGR")
	private String retiradaXlgr;

	@Column(name="RETIRADA_XMUN")
	private String retiradaXmun;

	@Column(name="TOTAL_ICMSTOT_VBC")
	private BigDecimal totalIcmstotVbc;

	@Column(name="TOTAL_ICMSTOT_VBCST")
	private BigDecimal totalIcmstotVbcst;

	@Column(name="TOTAL_ICMSTOT_VCOFINS")
	private BigDecimal totalIcmstotVcofins;

	@Column(name="TOTAL_ICMSTOT_VDESC")
	private BigDecimal totalIcmstotVdesc;

	@Column(name="TOTAL_ICMSTOT_VFRETE")
	private BigDecimal totalIcmstotVfrete;

	@Column(name="TOTAL_ICMSTOT_VICMS")
	private BigDecimal totalIcmstotVicms;

	@Column(name="TOTAL_ICMSTOT_VICMSDESON")
	private BigDecimal totalIcmstotVicmsdeson;

	@Column(name="TOTAL_ICMSTOT_VII")
	private BigDecimal totalIcmstotVii;

	@Column(name="TOTAL_ICMSTOT_VIPI")
	private BigDecimal totalIcmstotVipi;

	@Column(name="TOTAL_ICMSTOT_VNF")
	private BigDecimal totalIcmstotVnf;

	@Column(name="TOTAL_ICMSTOT_VOUTROS")
	private BigDecimal totalIcmstotVoutros;

	@Column(name="TOTAL_ICMSTOT_VPIS")
	private BigDecimal totalIcmstotVpis;

	@Column(name="TOTAL_ICMSTOT_VPROD")
	private BigDecimal totalIcmstotVprod;

	@Column(name="TOTAL_ICMSTOT_VSEG")
	private BigDecimal totalIcmstotVseg;

	@Column(name="TOTAL_ICMSTOT_VST")
	private BigDecimal totalIcmstotVst;

	@Column(name="TOTAL_ICMSTOT_VTOTTRIB")
	private BigDecimal totalIcmstotVtottrib;

	@Column(name="TOTAL_ISSQNTOT_CREGTRIB")
	private Integer totalIssqntotCregtrib;

	@Column(name="TOTAL_ISSQNTOT_DCOMPET")
	private Timestamp totalIssqntotDcompet;

	@Column(name="TOTAL_ISSQNTOT_VBC")
	private BigDecimal totalIssqntotVbc;

	@Column(name="TOTAL_ISSQNTOT_VCOFINS")
	private BigDecimal totalIssqntotVcofins;

	@Column(name="TOTAL_ISSQNTOT_VDEDUCAO")
	private BigDecimal totalIssqntotVdeducao;

	@Column(name="TOTAL_ISSQNTOT_VDESCCOND")
	private BigDecimal totalIssqntotVdesccond;

	@Column(name="TOTAL_ISSQNTOT_VDESCINCOND")
	private BigDecimal totalIssqntotVdescincond;

	@Column(name="TOTAL_ISSQNTOT_VISS")
	private BigDecimal totalIssqntotViss;

	@Column(name="TOTAL_ISSQNTOT_VISSRET")
	private BigDecimal totalIssqntotVissret;

	@Column(name="TOTAL_ISSQNTOT_VOUTRO")
	private BigDecimal totalIssqntotVoutro;

	@Column(name="TOTAL_ISSQNTOT_VPIS")
	private BigDecimal totalIssqntotVpis;

	@Column(name="TOTAL_ISSQNTOT_VSERV")
	private BigDecimal totalIssqntotVserv;

	@Column(name="TOTAL_RETTRIB_VBCIRRF")
	private BigDecimal totalRettribVbcirrf;

	@Column(name="TOTAL_RETTRIB_VBCRETPREV")
	private BigDecimal totalRettribVbcretprev;

	@Column(name="TOTAL_RETTRIB_VIRRF")
	private BigDecimal totalRettribVirrf;

	@Column(name="TOTAL_RETTRIB_VRETCOFINS")
	private BigDecimal totalRettribVretcofins;

	@Column(name="TOTAL_RETTRIB_VRETCSLL")
	private BigDecimal totalRettribVretcsll;

	@Column(name="TOTAL_RETTRIB_VRETPIS")
	private BigDecimal totalRettribVretpis;

	@Column(name="TOTAL_RETTRIB_VRETPREV")
	private BigDecimal totalRettribVretprev;

	@Column(name="`TRANSPORTE_LACRES_ NLACRE`")
	private String transporteLacres_nlacre;

	@Column(name="TRANSPORTE_REBOQUE_BALSA")
	private String transporteReboqueBalsa;

	@Column(name="TRANSPORTE_REBOQUE_PLACA")
	private String transporteReboquePlaca;

	@Column(name="TRANSPORTE_REBOQUE_RNTC")
	private String transporteReboqueRntc;

	@Column(name="TRANSPORTE_REBOQUE_UF")
	private String transporteReboqueUf;

	@Column(name="TRANSPORTE_REBOQUE_VAGAO")
	private String transporteReboqueVagao;

	@Column(name="TRANSPORTE_RETTRANSP_CFOP")
	private Integer transporteRettranspCfop;

	@Column(name="TRANSPORTE_RETTRANSP_CMUNFG")
	private Integer transporteRettranspCmunfg;

	@Column(name="TRANSPORTE_RETTRANSP_PICMSRET")
	private BigDecimal transporteRettranspPicmsret;

	@Column(name="TRANSPORTE_RETTRANSP_VBCRET")
	private BigDecimal transporteRettranspVbcret;

	@Column(name="TRANSPORTE_RETTRANSP_VICMSRET")
	private BigDecimal transporteRettranspVicmsret;

	@Column(name="TRANSPORTE_RETTRANSP_VSERV")
	private BigDecimal transporteRettranspVserv;

	@Column(name="TRANSPORTE_TRANSP_MODFRETE")
	private Integer transporteTranspModfrete;

	@Column(name="TRANSPORTE_TRANSPORTA_CNPJ")
	private String transporteTransportaCnpj;

	@Column(name="TRANSPORTE_TRANSPORTA_CPF")
	private String transporteTransportaCpf;

	@Column(name="TRANSPORTE_TRANSPORTA_IE")
	private String transporteTransportaIe;

	@Column(name="TRANSPORTE_TRANSPORTA_UF")
	private String transporteTransportaUf;

	@Column(name="TRANSPORTE_TRANSPORTA_XENDER")
	private String transporteTransportaXender;

	@Column(name="TRANSPORTE_TRANSPORTA_XMUN")
	private String transporteTransportaXmun;

	@Column(name="TRANSPORTE_TRANSPORTA_XNOME")
	private String transporteTransportaXnome;

	@Column(name="TRANSPORTE_VEICTRANSP_PLACA")
	private String transporteVeictranspPlaca;

	@Column(name="TRANSPORTE_VEICTRANSP_RNTC")
	private String transporteVeictranspRntc;

	@Column(name="TRANSPORTE_VEICTRANSP_UF")
	private String transporteVeictranspUf;

	@Column(name="TRANSPORTE_VOL_ESP")
	private String transporteVolEsp;

	@Column(name="TRANSPORTE_VOL_MARCA")
	private String transporteVolMarca;

	@Column(name="TRANSPORTE_VOL_NVOL")
	private String transporteVolNvol;

	@Column(name="TRANSPORTE_VOL_PESOB")
	private BigDecimal transporteVolPesob;

	@Column(name="TRANSPORTE_VOL_PESOL")
	private BigDecimal transporteVolPesol;

	@Column(name="TRANSPORTE_VOL_QVOL")
	private Integer transporteVolQvol;

	@OneToMany(mappedBy = "nfEntradaCab", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private Set<NfEntradaDet> detProdutos = new HashSet<NfEntradaDet>();

	@OneToMany(mappedBy = "nfEntradaCab", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private List<NfEntradaFat> detFaturamento;
//
	@OneToOne(mappedBy="nfEntradaCab")
	private NfEntradaXml nfEntradaXML;

	
	private BigInteger utctag;

	public NfEntradaCab() {
	}

	public BigInteger getCheckDelete() {
		return checkDelete;
	}

	public void setCheckDelete(BigInteger checkDelete) {
		this.checkDelete = checkDelete;
	}

	public Integer getNoDocto() {
		return noDocto;
	}

	public void setNoDocto(Integer noDocto) {
		this.noDocto = noDocto;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Integer getCodemp() {
		return codemp;
	}

	public void setCodemp(Integer codemp) {
		this.codemp = codemp;
	}

	public String getChnfe() {
		return chnfe;
	}

	public void setChnfe(String chnfe) {
		this.chnfe = chnfe;
	}

	public Integer getCodOperacao() {
		return codOperacao;
	}

	public void setCodOperacao(Integer codOperacao) {
		this.codOperacao = codOperacao;
	}

	public BigInteger getCodOperacaoFk() {
		return codOperacaoFk;
	}

	public void setCodOperacaoFk(BigInteger codOperacaoFk) {
		this.codOperacaoFk = codOperacaoFk;
	}

	public Integer getCodSecao() {
		return codSecao;
	}

	public void setCodSecao(Integer codSecao) {
		this.codSecao = codSecao;
	}

	public String getDestCnpj() {
		return destCnpj;
	}

	public void setDestCnpj(String destCnpj) {
		this.destCnpj = destCnpj;
	}

	public String getDestCpf() {
		return destCpf;
	}

	public void setDestCpf(String destCpf) {
		this.destCpf = destCpf;
	}

	public String getDestIdestrangeiro() {
		return destIdestrangeiro;
	}

	public void setDestIdestrangeiro(String destIdestrangeiro) {
		this.destIdestrangeiro = destIdestrangeiro;
	}

	public String getDestXnome() {
		return destXnome;
	}

	public void setDestXnome(String destXnome) {
		this.destXnome = destXnome;
	}

	public Timestamp getDhrecbto() {
		return dhrecbto;
	}

	public void setDhrecbto(Timestamp dhrecbto) {
		this.dhrecbto = dhrecbto;
	}

	public Timestamp getDtEmissao() {
		return dtEmissao;
	}

	public void setDtEmissao(Timestamp dtEmissao) {
		this.dtEmissao = dtEmissao;
	}

	public Timestamp getDtSaidaChegada() {
		return dtSaidaChegada;
	}

	public void setDtSaidaChegada(Timestamp dtSaidaChegada) {
		this.dtSaidaChegada = dtSaidaChegada;
	}

	public String getEmitCnpj() {
		return emitCnpj;
	}

	public void setEmitCnpj(String emitCnpj) {
		this.emitCnpj = emitCnpj;
	}

	public String getEmitCpf() {
		return emitCpf;
	}

	public void setEmitCpf(String emitCpf) {
		this.emitCpf = emitCpf;
	}

	public String getEmitXfant() {
		return emitXfant;
	}

	public void setEmitXfant(String emitXfant) {
		this.emitXfant = emitXfant;
	}

	public String getEmitXnome() {
		return emitXnome;
	}

	public void setEmitXnome(String emitXnome) {
		this.emitXnome = emitXnome;
	}

	public Integer getEnderdestCep() {
		return enderdestCep;
	}

	public void setEnderdestCep(Integer enderdestCep) {
		this.enderdestCep = enderdestCep;
	}

	public Integer getEnderdestCmun() {
		return enderdestCmun;
	}

	public void setEnderdestCmun(Integer enderdestCmun) {
		this.enderdestCmun = enderdestCmun;
	}

	public Integer getEnderdestCpais() {
		return enderdestCpais;
	}

	public void setEnderdestCpais(Integer enderdestCpais) {
		this.enderdestCpais = enderdestCpais;
	}

	public String getEnderdestEmail() {
		return enderdestEmail;
	}

	public void setEnderdestEmail(String enderdestEmail) {
		this.enderdestEmail = enderdestEmail;
	}

	public String getEnderdestFone() {
		return enderdestFone;
	}

	public void setEnderdestFone(String enderdestFone) {
		this.enderdestFone = enderdestFone;
	}

	public String getEnderdestIe() {
		return enderdestIe;
	}

	public void setEnderdestIe(String enderdestIe) {
		this.enderdestIe = enderdestIe;
	}

	public String getEnderdestIm() {
		return enderdestIm;
	}

	public void setEnderdestIm(String enderdestIm) {
		this.enderdestIm = enderdestIm;
	}

	public Integer getEnderdestIndiedest() {
		return enderdestIndiedest;
	}

	public void setEnderdestIndiedest(Integer enderdestIndiedest) {
		this.enderdestIndiedest = enderdestIndiedest;
	}

	public Integer getEnderdestIsuf() {
		return enderdestIsuf;
	}

	public void setEnderdestIsuf(Integer enderdestIsuf) {
		this.enderdestIsuf = enderdestIsuf;
	}

	public String getEnderdestNro() {
		return enderdestNro;
	}

	public void setEnderdestNro(String enderdestNro) {
		this.enderdestNro = enderdestNro;
	}

	public String getEnderdestUf() {
		return enderdestUf;
	}

	public void setEnderdestUf(String enderdestUf) {
		this.enderdestUf = enderdestUf;
	}

	public String getEnderdestXbairro() {
		return enderdestXbairro;
	}

	public void setEnderdestXbairro(String enderdestXbairro) {
		this.enderdestXbairro = enderdestXbairro;
	}

	public String getEnderdestXcpl() {
		return enderdestXcpl;
	}

	public void setEnderdestXcpl(String enderdestXcpl) {
		this.enderdestXcpl = enderdestXcpl;
	}

	public String getEnderdestXlgr() {
		return enderdestXlgr;
	}

	public void setEnderdestXlgr(String enderdestXlgr) {
		this.enderdestXlgr = enderdestXlgr;
	}

	public String getEnderdestXmun() {
		return enderdestXmun;
	}

	public void setEnderdestXmun(String enderdestXmun) {
		this.enderdestXmun = enderdestXmun;
	}

	public String getEnderdestXpais() {
		return enderdestXpais;
	}

	public void setEnderdestXpais(String enderdestXpais) {
		this.enderdestXpais = enderdestXpais;
	}

	public Integer getEnderemitCep() {
		return enderemitCep;
	}

	public void setEnderemitCep(Integer enderemitCep) {
		this.enderemitCep = enderemitCep;
	}

	public Integer getEnderemitCmun() {
		return enderemitCmun;
	}

	public void setEnderemitCmun(Integer enderemitCmun) {
		this.enderemitCmun = enderemitCmun;
	}

	public Integer getEnderemitCnae() {
		return enderemitCnae;
	}

	public void setEnderemitCnae(Integer enderemitCnae) {
		this.enderemitCnae = enderemitCnae;
	}

	public Integer getEnderemitCpais() {
		return enderemitCpais;
	}

	public void setEnderemitCpais(Integer enderemitCpais) {
		this.enderemitCpais = enderemitCpais;
	}

	public Integer getEnderemitCrt() {
		return enderemitCrt;
	}

	public void setEnderemitCrt(Integer enderemitCrt) {
		this.enderemitCrt = enderemitCrt;
	}

	public String getEnderemitFone() {
		return enderemitFone;
	}

	public void setEnderemitFone(String enderemitFone) {
		this.enderemitFone = enderemitFone;
	}

	public String getEnderemitIe() {
		return enderemitIe;
	}

	public void setEnderemitIe(String enderemitIe) {
		this.enderemitIe = enderemitIe;
	}

	public String getEnderemitIest() {
		return enderemitIest;
	}

	public void setEnderemitIest(String enderemitIest) {
		this.enderemitIest = enderemitIest;
	}

	public String getEnderemitIm() {
		return enderemitIm;
	}

	public void setEnderemitIm(String enderemitIm) {
		this.enderemitIm = enderemitIm;
	}

	public String getEnderemitNro() {
		return enderemitNro;
	}

	public void setEnderemitNro(String enderemitNro) {
		this.enderemitNro = enderemitNro;
	}

	public String getEnderemitUf() {
		return enderemitUf;
	}

	public void setEnderemitUf(String enderemitUf) {
		this.enderemitUf = enderemitUf;
	}

	public String getEnderemitXbairro() {
		return enderemitXbairro;
	}

	public void setEnderemitXbairro(String enderemitXbairro) {
		this.enderemitXbairro = enderemitXbairro;
	}

	public String getEnderemitXcpl() {
		return enderemitXcpl;
	}

	public void setEnderemitXcpl(String enderemitXcpl) {
		this.enderemitXcpl = enderemitXcpl;
	}

	public String getEnderemitXlgr() {
		return enderemitXlgr;
	}

	public void setEnderemitXlgr(String enderemitXlgr) {
		this.enderemitXlgr = enderemitXlgr;
	}

	public String getEnderemitXmun() {
		return enderemitXmun;
	}

	public void setEnderemitXmun(String enderemitXmun) {
		this.enderemitXmun = enderemitXmun;
	}

	public String getEnderemitXpais() {
		return enderemitXpais;
	}

	public void setEnderemitXpais(String enderemitXpais) {
		this.enderemitXpais = enderemitXpais;
	}

	public Integer getEntregaCmun() {
		return entregaCmun;
	}

	public void setEntregaCmun(Integer entregaCmun) {
		this.entregaCmun = entregaCmun;
	}

	public String getEntregaCnpj() {
		return entregaCnpj;
	}

	public void setEntregaCnpj(String entregaCnpj) {
		this.entregaCnpj = entregaCnpj;
	}

	public String getEntregaCpf() {
		return entregaCpf;
	}

	public void setEntregaCpf(String entregaCpf) {
		this.entregaCpf = entregaCpf;
	}

	public String getEntregaNro() {
		return entregaNro;
	}

	public void setEntregaNro(String entregaNro) {
		this.entregaNro = entregaNro;
	}

	public String getEntregaUf() {
		return entregaUf;
	}

	public void setEntregaUf(String entregaUf) {
		this.entregaUf = entregaUf;
	}

	public String getEntregaXbairro() {
		return entregaXbairro;
	}

	public void setEntregaXbairro(String entregaXbairro) {
		this.entregaXbairro = entregaXbairro;
	}

	public String getEntregaXcpl() {
		return entregaXcpl;
	}

	public void setEntregaXcpl(String entregaXcpl) {
		this.entregaXcpl = entregaXcpl;
	}

	public String getEntregaXlgr() {
		return entregaXlgr;
	}

	public void setEntregaXlgr(String entregaXlgr) {
		this.entregaXlgr = entregaXlgr;
	}

	public String getEntregaXmun() {
		return entregaXmun;
	}

	public void setEntregaXmun(String entregaXmun) {
		this.entregaXmun = entregaXmun;
	}

	public String getExportaUfsaidapais() {
		return exportaUfsaidapais;
	}

	public void setExportaUfsaidapais(String exportaUfsaidapais) {
		this.exportaUfsaidapais = exportaUfsaidapais;
	}

	public String getExportaXlocdespacho() {
		return exportaXlocdespacho;
	}

	public void setExportaXlocdespacho(String exportaXlocdespacho) {
		this.exportaXlocdespacho = exportaXlocdespacho;
	}

	public String getExportaXlocexporta() {
		return exportaXlocexporta;
	}

	public void setExportaXlocexporta(String exportaXlocexporta) {
		this.exportaXlocexporta = exportaXlocexporta;
	}

	public Integer getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Integer flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public Integer getIdeCdv() {
		return ideCdv;
	}

	public void setIdeCdv(Integer ideCdv) {
		this.ideCdv = ideCdv;
	}

	public Integer getIdeCmunfg() {
		return ideCmunfg;
	}

	public void setIdeCmunfg(Integer ideCmunfg) {
		this.ideCmunfg = ideCmunfg;
	}

	public Integer getIdeCnf() {
		return ideCnf;
	}

	public void setIdeCnf(Integer ideCnf) {
		this.ideCnf = ideCnf;
	}

	public Integer getIdeCuf() {
		return ideCuf;
	}

	public void setIdeCuf(Integer ideCuf) {
		this.ideCuf = ideCuf;
	}

	public LocalDateTime getIdeDhcont() {
		return ideDhcont;
	}

	public void setIdeDhcont(LocalDateTime ideDhcont) {
		this.ideDhcont = ideDhcont;
	}

	public LocalDateTime getIdeDhemi() {
		return ideDhemi;
	}

	public void setIdeDhemi(LocalDateTime ideDhemi) {
		this.ideDhemi = ideDhemi;
	}

	public LocalDateTime getIdeDhsaient() {
		return ideDhsaient;
	}

	public void setIdeDhsaient(LocalDateTime ideDhsaient) {
		this.ideDhsaient = ideDhsaient;
	}

	public Integer getIdeFinnfe() {
		return ideFinnfe;
	}

	public void setIdeFinnfe(Integer ideFinnfe) {
		this.ideFinnfe = ideFinnfe;
	}

	public Integer getIdeIddest() {
		return ideIddest;
	}

	public void setIdeIddest(Integer ideIddest) {
		this.ideIddest = ideIddest;
	}

	public Integer getIdeIndfinal() {
		return ideIndfinal;
	}

	public void setIdeIndfinal(Integer ideIndfinal) {
		this.ideIndfinal = ideIndfinal;
	}

	public Integer getIdeIndpag() {
		return ideIndpag;
	}

	public void setIdeIndpag(Integer ideIndpag) {
		this.ideIndpag = ideIndpag;
	}

	public Integer getIdeIndpres() {
		return ideIndpres;
	}

	public void setIdeIndpres(Integer ideIndpres) {
		this.ideIndpres = ideIndpres;
	}

	public Integer getIdeMod() {
		return ideMod;
	}

	public void setIdeMod(Integer ideMod) {
		this.ideMod = ideMod;
	}

	public String getIdeNatop() {
		return ideNatop;
	}

	public void setIdeNatop(String ideNatop) {
		this.ideNatop = ideNatop;
	}

	public Integer getIdeNnf() {
		return ideNnf;
	}

	public void setIdeNnf(Integer ideNnf) {
		this.ideNnf = ideNnf;
	}

	public Integer getIdeProcemi() {
		return ideProcemi;
	}

	public void setIdeProcemi(Integer ideProcemi) {
		this.ideProcemi = ideProcemi;
	}

	public Integer getIdeSerie() {
		return ideSerie;
	}

	public void setIdeSerie(Integer ideSerie) {
		this.ideSerie = ideSerie;
	}

	public Integer getIdeTpamb() {
		return ideTpamb;
	}

	public void setIdeTpamb(Integer ideTpamb) {
		this.ideTpamb = ideTpamb;
	}

	public Integer getIdeTpemis() {
		return ideTpemis;
	}

	public void setIdeTpemis(Integer ideTpemis) {
		this.ideTpemis = ideTpemis;
	}

	public Integer getIdeTpimp() {
		return ideTpimp;
	}

	public void setIdeTpimp(Integer ideTpimp) {
		this.ideTpimp = ideTpimp;
	}

	public Integer getIdeTpnf() {
		return ideTpnf;
	}

	public void setIdeTpnf(Integer ideTpnf) {
		this.ideTpnf = ideTpnf;
	}

	public String getIdeVerproc() {
		return ideVerproc;
	}

	public void setIdeVerproc(String ideVerproc) {
		this.ideVerproc = ideVerproc;
	}

	public String getIdeXjust() {
		return ideXjust;
	}

	public void setIdeXjust(String ideXjust) {
		this.ideXjust = ideXjust;
	}

	public String getInfadicInfcpl() {
		return infadicInfcpl;
	}

	public void setInfadicInfcpl(String infadicInfcpl) {
		this.infadicInfcpl = infadicInfcpl;
	}

	public String getInfadicNfadfisco() {
		return infadicNfadfisco;
	}

	public void setInfadicNfadfisco(String infadicNfadfisco) {
		this.infadicNfadfisco = infadicNfadfisco;
	}

	public Integer getLastCoduser() {
		return lastCoduser;
	}

	public void setLastCoduser(Integer lastCoduser) {
		this.lastCoduser = lastCoduser;
	}

	public LocalDateTime getLastMovto() {
		return lastMovto;
	}

	public void setLastMovto(LocalDateTime lastMovto) {
		this.lastMovto = lastMovto;
	}

	public String getNprot() {
		return nprot;
	}

	public void setNprot(String nprot) {
		this.nprot = nprot;
	}

	public String getObscontXcampo() {
		return obscontXcampo;
	}

	public void setObscontXcampo(String obscontXcampo) {
		this.obscontXcampo = obscontXcampo;
	}

	public String getObscontXtexto() {
		return obscontXtexto;
	}

	public void setObscontXtexto(String obscontXtexto) {
		this.obscontXtexto = obscontXtexto;
	}

	public String getObsfiscoXcampo() {
		return obsfiscoXcampo;
	}

	public void setObsfiscoXcampo(String obsfiscoXcampo) {
		this.obsfiscoXcampo = obsfiscoXcampo;
	}

	public String getObsfiscoXtexto() {
		return obsfiscoXtexto;
	}

	public void setObsfiscoXtexto(String obsfiscoXtexto) {
		this.obsfiscoXtexto = obsfiscoXtexto;
	}

	public Integer getProcrefIndproc() {
		return procrefIndproc;
	}

	public void setProcrefIndproc(Integer procrefIndproc) {
		this.procrefIndproc = procrefIndproc;
	}

	public String getProcrefNproc() {
		return procrefNproc;
	}

	public void setProcrefNproc(String procrefNproc) {
		this.procrefNproc = procrefNproc;
	}

	public Integer getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(Integer recordNo) {
		this.recordNo = recordNo;
	}

	public Integer getRetiradaCmun() {
		return retiradaCmun;
	}

	public void setRetiradaCmun(Integer retiradaCmun) {
		this.retiradaCmun = retiradaCmun;
	}

	public String getRetiradaCnpj() {
		return retiradaCnpj;
	}

	public void setRetiradaCnpj(String retiradaCnpj) {
		this.retiradaCnpj = retiradaCnpj;
	}

	public String getRetiradaCpf() {
		return retiradaCpf;
	}

	public void setRetiradaCpf(String retiradaCpf) {
		this.retiradaCpf = retiradaCpf;
	}

	public String getRetiradaNro() {
		return retiradaNro;
	}

	public void setRetiradaNro(String retiradaNro) {
		this.retiradaNro = retiradaNro;
	}

	public String getRetiradaUf() {
		return retiradaUf;
	}

	public void setRetiradaUf(String retiradaUf) {
		this.retiradaUf = retiradaUf;
	}

	public String getRetiradaXbairro() {
		return retiradaXbairro;
	}

	public void setRetiradaXbairro(String retiradaXbairro) {
		this.retiradaXbairro = retiradaXbairro;
	}

	public String getRetiradaXcpl() {
		return retiradaXcpl;
	}

	public void setRetiradaXcpl(String retiradaXcpl) {
		this.retiradaXcpl = retiradaXcpl;
	}

	public String getRetiradaXlgr() {
		return retiradaXlgr;
	}

	public void setRetiradaXlgr(String retiradaXlgr) {
		this.retiradaXlgr = retiradaXlgr;
	}

	public String getRetiradaXmun() {
		return retiradaXmun;
	}

	public void setRetiradaXmun(String retiradaXmun) {
		this.retiradaXmun = retiradaXmun;
	}

	public BigDecimal getTotalIcmstotVbc() {
		return totalIcmstotVbc;
	}

	public void setTotalIcmstotVbc(BigDecimal totalIcmstotVbc) {
		this.totalIcmstotVbc = totalIcmstotVbc;
	}

	public BigDecimal getTotalIcmstotVbcst() {
		return totalIcmstotVbcst;
	}

	public void setTotalIcmstotVbcst(BigDecimal totalIcmstotVbcst) {
		this.totalIcmstotVbcst = totalIcmstotVbcst;
	}

	public BigDecimal getTotalIcmstotVcofins() {
		return totalIcmstotVcofins;
	}

	public void setTotalIcmstotVcofins(BigDecimal totalIcmstotVcofins) {
		this.totalIcmstotVcofins = totalIcmstotVcofins;
	}

	public BigDecimal getTotalIcmstotVdesc() {
		return totalIcmstotVdesc;
	}

	public void setTotalIcmstotVdesc(BigDecimal totalIcmstotVdesc) {
		this.totalIcmstotVdesc = totalIcmstotVdesc;
	}

	public BigDecimal getTotalIcmstotVfrete() {
		return totalIcmstotVfrete;
	}

	public void setTotalIcmstotVfrete(BigDecimal totalIcmstotVfrete) {
		this.totalIcmstotVfrete = totalIcmstotVfrete;
	}

	public BigDecimal getTotalIcmstotVicms() {
		return totalIcmstotVicms;
	}

	public void setTotalIcmstotVicms(BigDecimal totalIcmstotVicms) {
		this.totalIcmstotVicms = totalIcmstotVicms;
	}

	public BigDecimal getTotalIcmstotVicmsdeson() {
		return totalIcmstotVicmsdeson;
	}

	public void setTotalIcmstotVicmsdeson(BigDecimal totalIcmstotVicmsdeson) {
		this.totalIcmstotVicmsdeson = totalIcmstotVicmsdeson;
	}

	public BigDecimal getTotalIcmstotVii() {
		return totalIcmstotVii;
	}

	public void setTotalIcmstotVii(BigDecimal totalIcmstotVii) {
		this.totalIcmstotVii = totalIcmstotVii;
	}

	public BigDecimal getTotalIcmstotVipi() {
		return totalIcmstotVipi;
	}

	public void setTotalIcmstotVipi(BigDecimal totalIcmstotVipi) {
		this.totalIcmstotVipi = totalIcmstotVipi;
	}

	public BigDecimal getTotalIcmstotVnf() {
		return totalIcmstotVnf;
	}

	public void setTotalIcmstotVnf(BigDecimal totalIcmstotVnf) {
		this.totalIcmstotVnf = totalIcmstotVnf;
	}

	public BigDecimal getTotalIcmstotVoutros() {
		return totalIcmstotVoutros;
	}

	public void setTotalIcmstotVoutros(BigDecimal totalIcmstotVoutros) {
		this.totalIcmstotVoutros = totalIcmstotVoutros;
	}

	public BigDecimal getTotalIcmstotVpis() {
		return totalIcmstotVpis;
	}

	public void setTotalIcmstotVpis(BigDecimal totalIcmstotVpis) {
		this.totalIcmstotVpis = totalIcmstotVpis;
	}

	public BigDecimal getTotalIcmstotVprod() {
		return totalIcmstotVprod;
	}

	public void setTotalIcmstotVprod(BigDecimal totalIcmstotVprod) {
		this.totalIcmstotVprod = totalIcmstotVprod;
	}

	public BigDecimal getTotalIcmstotVseg() {
		return totalIcmstotVseg;
	}

	public void setTotalIcmstotVseg(BigDecimal totalIcmstotVseg) {
		this.totalIcmstotVseg = totalIcmstotVseg;
	}

	public BigDecimal getTotalIcmstotVst() {
		return totalIcmstotVst;
	}

	public void setTotalIcmstotVst(BigDecimal totalIcmstotVst) {
		this.totalIcmstotVst = totalIcmstotVst;
	}

	public BigDecimal getTotalIcmstotVtottrib() {
		return totalIcmstotVtottrib;
	}

	public void setTotalIcmstotVtottrib(BigDecimal totalIcmstotVtottrib) {
		this.totalIcmstotVtottrib = totalIcmstotVtottrib;
	}

	public Integer getTotalIssqntotCregtrib() {
		return totalIssqntotCregtrib;
	}

	public void setTotalIssqntotCregtrib(Integer totalIssqntotCregtrib) {
		this.totalIssqntotCregtrib = totalIssqntotCregtrib;
	}

	public Timestamp getTotalIssqntotDcompet() {
		return totalIssqntotDcompet;
	}

	public void setTotalIssqntotDcompet(Timestamp totalIssqntotDcompet) {
		this.totalIssqntotDcompet = totalIssqntotDcompet;
	}

	public BigDecimal getTotalIssqntotVbc() {
		return totalIssqntotVbc;
	}

	public void setTotalIssqntotVbc(BigDecimal totalIssqntotVbc) {
		this.totalIssqntotVbc = totalIssqntotVbc;
	}

	public BigDecimal getTotalIssqntotVcofins() {
		return totalIssqntotVcofins;
	}

	public void setTotalIssqntotVcofins(BigDecimal totalIssqntotVcofins) {
		this.totalIssqntotVcofins = totalIssqntotVcofins;
	}

	public BigDecimal getTotalIssqntotVdeducao() {
		return totalIssqntotVdeducao;
	}

	public void setTotalIssqntotVdeducao(BigDecimal totalIssqntotVdeducao) {
		this.totalIssqntotVdeducao = totalIssqntotVdeducao;
	}

	public BigDecimal getTotalIssqntotVdesccond() {
		return totalIssqntotVdesccond;
	}

	public void setTotalIssqntotVdesccond(BigDecimal totalIssqntotVdesccond) {
		this.totalIssqntotVdesccond = totalIssqntotVdesccond;
	}

	public BigDecimal getTotalIssqntotVdescincond() {
		return totalIssqntotVdescincond;
	}

	public void setTotalIssqntotVdescincond(BigDecimal totalIssqntotVdescincond) {
		this.totalIssqntotVdescincond = totalIssqntotVdescincond;
	}

	public BigDecimal getTotalIssqntotViss() {
		return totalIssqntotViss;
	}

	public void setTotalIssqntotViss(BigDecimal totalIssqntotViss) {
		this.totalIssqntotViss = totalIssqntotViss;
	}

	public BigDecimal getTotalIssqntotVissret() {
		return totalIssqntotVissret;
	}

	public void setTotalIssqntotVissret(BigDecimal totalIssqntotVissret) {
		this.totalIssqntotVissret = totalIssqntotVissret;
	}

	public BigDecimal getTotalIssqntotVoutro() {
		return totalIssqntotVoutro;
	}

	public void setTotalIssqntotVoutro(BigDecimal totalIssqntotVoutro) {
		this.totalIssqntotVoutro = totalIssqntotVoutro;
	}

	public BigDecimal getTotalIssqntotVpis() {
		return totalIssqntotVpis;
	}

	public void setTotalIssqntotVpis(BigDecimal totalIssqntotVpis) {
		this.totalIssqntotVpis = totalIssqntotVpis;
	}

	public BigDecimal getTotalIssqntotVserv() {
		return totalIssqntotVserv;
	}

	public void setTotalIssqntotVserv(BigDecimal totalIssqntotVserv) {
		this.totalIssqntotVserv = totalIssqntotVserv;
	}

	public BigDecimal getTotalRettribVbcirrf() {
		return totalRettribVbcirrf;
	}

	public void setTotalRettribVbcirrf(BigDecimal totalRettribVbcirrf) {
		this.totalRettribVbcirrf = totalRettribVbcirrf;
	}

	public BigDecimal getTotalRettribVbcretprev() {
		return totalRettribVbcretprev;
	}

	public void setTotalRettribVbcretprev(BigDecimal totalRettribVbcretprev) {
		this.totalRettribVbcretprev = totalRettribVbcretprev;
	}

	public BigDecimal getTotalRettribVirrf() {
		return totalRettribVirrf;
	}

	public void setTotalRettribVirrf(BigDecimal totalRettribVirrf) {
		this.totalRettribVirrf = totalRettribVirrf;
	}

	public BigDecimal getTotalRettribVretcofins() {
		return totalRettribVretcofins;
	}

	public void setTotalRettribVretcofins(BigDecimal totalRettribVretcofins) {
		this.totalRettribVretcofins = totalRettribVretcofins;
	}

	public BigDecimal getTotalRettribVretcsll() {
		return totalRettribVretcsll;
	}

	public void setTotalRettribVretcsll(BigDecimal totalRettribVretcsll) {
		this.totalRettribVretcsll = totalRettribVretcsll;
	}

	public BigDecimal getTotalRettribVretpis() {
		return totalRettribVretpis;
	}

	public void setTotalRettribVretpis(BigDecimal totalRettribVretpis) {
		this.totalRettribVretpis = totalRettribVretpis;
	}

	public BigDecimal getTotalRettribVretprev() {
		return totalRettribVretprev;
	}

	public void setTotalRettribVretprev(BigDecimal totalRettribVretprev) {
		this.totalRettribVretprev = totalRettribVretprev;
	}

	public String getTransporteLacres_nlacre() {
		return transporteLacres_nlacre;
	}

	public void setTransporteLacres_nlacre(String transporteLacres_nlacre) {
		this.transporteLacres_nlacre = transporteLacres_nlacre;
	}

	public String getTransporteReboqueBalsa() {
		return transporteReboqueBalsa;
	}

	public void setTransporteReboqueBalsa(String transporteReboqueBalsa) {
		this.transporteReboqueBalsa = transporteReboqueBalsa;
	}

	public String getTransporteReboquePlaca() {
		return transporteReboquePlaca;
	}

	public void setTransporteReboquePlaca(String transporteReboquePlaca) {
		this.transporteReboquePlaca = transporteReboquePlaca;
	}

	public String getTransporteReboqueRntc() {
		return transporteReboqueRntc;
	}

	public void setTransporteReboqueRntc(String transporteReboqueRntc) {
		this.transporteReboqueRntc = transporteReboqueRntc;
	}

	public String getTransporteReboqueUf() {
		return transporteReboqueUf;
	}

	public void setTransporteReboqueUf(String transporteReboqueUf) {
		this.transporteReboqueUf = transporteReboqueUf;
	}

	public String getTransporteReboqueVagao() {
		return transporteReboqueVagao;
	}

	public void setTransporteReboqueVagao(String transporteReboqueVagao) {
		this.transporteReboqueVagao = transporteReboqueVagao;
	}

	public Integer getTransporteRettranspCfop() {
		return transporteRettranspCfop;
	}

	public void setTransporteRettranspCfop(Integer transporteRettranspCfop) {
		this.transporteRettranspCfop = transporteRettranspCfop;
	}

	public Integer getTransporteRettranspCmunfg() {
		return transporteRettranspCmunfg;
	}

	public void setTransporteRettranspCmunfg(Integer transporteRettranspCmunfg) {
		this.transporteRettranspCmunfg = transporteRettranspCmunfg;
	}

	public BigDecimal getTransporteRettranspPicmsret() {
		return transporteRettranspPicmsret;
	}

	public void setTransporteRettranspPicmsret(BigDecimal transporteRettranspPicmsret) {
		this.transporteRettranspPicmsret = transporteRettranspPicmsret;
	}

	public BigDecimal getTransporteRettranspVbcret() {
		return transporteRettranspVbcret;
	}

	public void setTransporteRettranspVbcret(BigDecimal transporteRettranspVbcret) {
		this.transporteRettranspVbcret = transporteRettranspVbcret;
	}

	public BigDecimal getTransporteRettranspVicmsret() {
		return transporteRettranspVicmsret;
	}

	public void setTransporteRettranspVicmsret(BigDecimal transporteRettranspVicmsret) {
		this.transporteRettranspVicmsret = transporteRettranspVicmsret;
	}

	public BigDecimal getTransporteRettranspVserv() {
		return transporteRettranspVserv;
	}

	public void setTransporteRettranspVserv(BigDecimal transporteRettranspVserv) {
		this.transporteRettranspVserv = transporteRettranspVserv;
	}

	public Integer getTransporteTranspModfrete() {
		return transporteTranspModfrete;
	}

	public void setTransporteTranspModfrete(Integer transporteTranspModfrete) {
		this.transporteTranspModfrete = transporteTranspModfrete;
	}

	public String getTransporteTransportaCnpj() {
		return transporteTransportaCnpj;
	}

	public void setTransporteTransportaCnpj(String transporteTransportaCnpj) {
		this.transporteTransportaCnpj = transporteTransportaCnpj;
	}

	public String getTransporteTransportaCpf() {
		return transporteTransportaCpf;
	}

	public void setTransporteTransportaCpf(String transporteTransportaCpf) {
		this.transporteTransportaCpf = transporteTransportaCpf;
	}

	public String getTransporteTransportaIe() {
		return transporteTransportaIe;
	}

	public void setTransporteTransportaIe(String transporteTransportaIe) {
		this.transporteTransportaIe = transporteTransportaIe;
	}

	public String getTransporteTransportaUf() {
		return transporteTransportaUf;
	}

	public void setTransporteTransportaUf(String transporteTransportaUf) {
		this.transporteTransportaUf = transporteTransportaUf;
	}

	public String getTransporteTransportaXender() {
		return transporteTransportaXender;
	}

	public void setTransporteTransportaXender(String transporteTransportaXender) {
		this.transporteTransportaXender = transporteTransportaXender;
	}

	public String getTransporteTransportaXmun() {
		return transporteTransportaXmun;
	}

	public void setTransporteTransportaXmun(String transporteTransportaXmun) {
		this.transporteTransportaXmun = transporteTransportaXmun;
	}

	public String getTransporteTransportaXnome() {
		return transporteTransportaXnome;
	}

	public void setTransporteTransportaXnome(String transporteTransportaXnome) {
		this.transporteTransportaXnome = transporteTransportaXnome;
	}

	public String getTransporteVeictranspPlaca() {
		return transporteVeictranspPlaca;
	}

	public void setTransporteVeictranspPlaca(String transporteVeictranspPlaca) {
		this.transporteVeictranspPlaca = transporteVeictranspPlaca;
	}

	public String getTransporteVeictranspRntc() {
		return transporteVeictranspRntc;
	}

	public void setTransporteVeictranspRntc(String transporteVeictranspRntc) {
		this.transporteVeictranspRntc = transporteVeictranspRntc;
	}

	public String getTransporteVeictranspUf() {
		return transporteVeictranspUf;
	}

	public void setTransporteVeictranspUf(String transporteVeictranspUf) {
		this.transporteVeictranspUf = transporteVeictranspUf;
	}

	public String getTransporteVolEsp() {
		return transporteVolEsp;
	}

	public void setTransporteVolEsp(String transporteVolEsp) {
		this.transporteVolEsp = transporteVolEsp;
	}

	public String getTransporteVolMarca() {
		return transporteVolMarca;
	}

	public void setTransporteVolMarca(String transporteVolMarca) {
		this.transporteVolMarca = transporteVolMarca;
	}

	public String getTransporteVolNvol() {
		return transporteVolNvol;
	}

	public void setTransporteVolNvol(String transporteVolNvol) {
		this.transporteVolNvol = transporteVolNvol;
	}

	public BigDecimal getTransporteVolPesob() {
		return transporteVolPesob;
	}

	public void setTransporteVolPesob(BigDecimal transporteVolPesob) {
		this.transporteVolPesob = transporteVolPesob;
	}

	public BigDecimal getTransporteVolPesol() {
		return transporteVolPesol;
	}

	public void setTransporteVolPesol(BigDecimal transporteVolPesol) {
		this.transporteVolPesol = transporteVolPesol;
	}

	public Integer getTransporteVolQvol() {
		return transporteVolQvol;
	}

	public void setTransporteVolQvol(Integer transporteVolQvol) {
		this.transporteVolQvol = transporteVolQvol;
	}	

	public Set<NfEntradaDet> getDetProdutos() {
		return detProdutos;
	}

	public void setDetProdutos(Set<NfEntradaDet> detProdutos) {
		this.detProdutos = detProdutos;
	}

	public BigInteger getUtctag() {
		return utctag;
	}

	public void setUtctag(BigInteger utctag) {
		this.utctag = utctag;
	}

	public List<NfEntradaFat> getDetFaturamento() {
		return detFaturamento;
	}

	public void setDetFaturamento(List<NfEntradaFat> detFaturamento) {
		this.detFaturamento = detFaturamento;
	}

	public NfEntradaXml getNfEntradaXML() {
		return nfEntradaXML;
	}

	public void setNfEntradaXML(NfEntradaXml nfEntradaXML) {
		this.nfEntradaXML = nfEntradaXML;
	}


}