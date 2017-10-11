package models.compras;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.math.BigInteger;


/**
 * The persistent class for the nf_entrada_det database table.
 * 
 */
@Entity
@Table(name="nf_entrada_det")
@NamedQuery(name="NfEntradaDet.findAll", query="SELECT n FROM NfEntradaDet n")
@IdClass(value = NfEntradaDetPK.class)
public class NfEntradaDet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CHECK_DELETE")
	private BigInteger checkDelete;
	
//	@Column(name="COD_NF_CAB")
//	private int codNfCab;
//
//	@Column(name="COD_NF_CAB_FX")
//	private BigInteger codNfCabFx;
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
	@JoinColumn(name = "COD_NF_CAB_FK", referencedColumnName= "CHECK_DELETE"),
	@JoinColumn(name = "NO_DOCTO", referencedColumnName= "NO_DOCTO")
	})
	private NfEntradaCab nfEntradaCab;
	
	private int codemp;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
	@JoinColumn(name = "EPTUS_COD_ITEM", referencedColumnName= "CODIGO"),
	@JoinColumn(name = "EPTUS_COD_ITEM_FK", referencedColumnName= "CHECK_DELETE")
	})
	private Item item;

	@Column(name="ALIQ_ICMS")
	private BigDecimal aliqIcms;

	@Column(name="BC_ICMS")
	private BigDecimal bcIcms;

	@Column(name="BC_ICMS_SUB")
	private BigDecimal bcIcmsSub;

	@Column(name="C_EAN")
	private String cEan;

	@Column(name="C_EAN_TRIB")
	private String cEanTrib;

	@Column(name="C_PROD")
	private int cProd;

	private int cfop;	

	@Column(name="CST_ICMS")
	private int cstIcms;

	private int extipi;

	@Column(name="FLAG_ATIVO")
	private int flagAtivo;

	@Column(name="ICMS_00_CST")
	private int icms00Cst;

	@Column(name="ICMS_00_MODBC")
	private int icms00Modbc;

	@Column(name="ICMS_00_ORIG")
	private int icms00Orig;

	@Column(name="ICMS_00_PICMS")
	private BigDecimal icms00Picms;

	@Column(name="ICMS_00_VBC")
	private BigDecimal icms00Vbc;

	@Column(name="ICMS_00_VICMS")
	private BigDecimal icms00Vicms;

	@Column(name="ICMS_10_CST")
	private int icms10Cst;

	@Column(name="ICMS_10_MODBC")
	private int icms10Modbc;

	@Column(name="ICMS_10_MODBCST")
	private int icms10Modbcst;

	@Column(name="ICMS_10_ORIG")
	private int icms10Orig;

	@Column(name="ICMS_10_PICMS")
	private BigDecimal icms10Picms;

	@Column(name="ICMS_10_PICMSST")
	private BigDecimal icms10Picmsst;

	@Column(name="ICMS_10_PMVAST")
	private BigDecimal icms10Pmvast;

	@Column(name="ICMS_10_PREDBCST")
	private BigDecimal icms10Predbcst;

	@Column(name="ICMS_10_VBC")
	private BigDecimal icms10Vbc;

	@Column(name="ICMS_10_VBCST")
	private BigDecimal icms10Vbcst;

	@Column(name="ICMS_10_VICMS")
	private BigDecimal icms10Vicms;

	@Column(name="ICMS_10_VICMSST")
	private BigDecimal icms10Vicmsst;

	@Column(name="ICMS_20_CST")
	private int icms20Cst;

	@Column(name="ICMS_20_MODBC")
	private int icms20Modbc;

	@Column(name="ICMS_20_MOTDESICMS")
	private BigDecimal icms20Motdesicms;

	@Column(name="ICMS_20_ORIG")
	private int icms20Orig;

	@Column(name="ICMS_20_PICMS")
	private BigDecimal icms20Picms;

	@Column(name="ICMS_20_PREDBC")
	private BigDecimal icms20Predbc;

	@Column(name="ICMS_20_VBC")
	private BigDecimal icms20Vbc;

	@Column(name="ICMS_20_VICMS")
	private BigDecimal icms20Vicms;

	@Column(name="ICMS_20_VICMSDESON")
	private BigDecimal icms20Vicmsdeson;

	@Column(name="ICMS_30_CST")
	private int icms30Cst;

	@Column(name="ICMS_30_MODBCST")
	private int icms30Modbcst;

	@Column(name="ICMS_30_MOTDESICMS")
	private int icms30Motdesicms;

	@Column(name="ICMS_30_ORIG")
	private int icms30Orig;

	@Column(name="ICMS_30_PICMSST")
	private BigDecimal icms30Picmsst;

	@Column(name="ICMS_30_PMVAST")
	private BigDecimal icms30Pmvast;

	@Column(name="ICMS_30_PREDBCST")
	private BigDecimal icms30Predbcst;

	@Column(name="ICMS_30_VBCST")
	private BigDecimal icms30Vbcst;

	@Column(name="ICMS_30_VICMSDESON")
	private BigDecimal icms30Vicmsdeson;

	@Column(name="ICMS_30_VICMSST")
	private BigDecimal icms30Vicmsst;

	@Column(name="ICMS_40_CST")
	private int icms40Cst;

	@Column(name="ICMS_40_MOTDESICMS")
	private int icms40Motdesicms;

	@Column(name="ICMS_40_ORIG")
	private int icms40Orig;

	@Column(name="ICMS_40_VICMSDESON")
	private BigDecimal icms40Vicmsdeson;

	@Column(name="ICMS_51_CST")
	private int icms51Cst;

	@Column(name="ICMS_51_MODBC")
	private int icms51Modbc;

	@Column(name="ICMS_51_ORIG")
	private int icms51Orig;

	@Column(name="ICMS_51_PDIF")
	private BigDecimal icms51Pdif;

	@Column(name="ICMS_51_PICMS")
	private BigDecimal icms51Picms;

	@Column(name="ICMS_51_PREDBC")
	private BigDecimal icms51Predbc;

	@Column(name="ICMS_51_VBC")
	private BigDecimal icms51Vbc;

	@Column(name="ICMS_51_VICMS")
	private BigDecimal icms51Vicms;

	@Column(name="ICMS_51_VICMSDIF")
	private BigDecimal icms51Vicmsdif;

	@Column(name="ICMS_51_VICMSOP")
	private BigDecimal icms51Vicmsop;

	@Column(name="ICMS_60_CST")
	private int icms60Cst;

	@Column(name="ICMS_60_ORIG")
	private int icms60Orig;

	@Column(name="ICMS_60_VBCSTRET")
	private BigDecimal icms60Vbcstret;

	@Column(name="ICMS_60_VICMSSTRET")
	private BigDecimal icms60Vicmsstret;

	@Column(name="ICMS_70_CST")
	private int icms70Cst;

	@Column(name="ICMS_70_MODBC")
	private int icms70Modbc;

	@Column(name="ICMS_70_MODBCST")
	private int icms70Modbcst;

	@Column(name="ICMS_70_MOTDESICMS")
	private int icms70Motdesicms;

	@Column(name="ICMS_70_ORIG")
	private int icms70Orig;

	@Column(name="ICMS_70_PICMS")
	private BigDecimal icms70Picms;

	@Column(name="ICMS_70_PICMSST")
	private BigDecimal icms70Picmsst;

	@Column(name="ICMS_70_PMVAST")
	private BigDecimal icms70Pmvast;

	@Column(name="ICMS_70_PREDBC")
	private BigDecimal icms70Predbc;

	@Column(name="ICMS_70_PREDBCST")
	private BigDecimal icms70Predbcst;

	@Column(name="ICMS_70_VBC")
	private BigDecimal icms70Vbc;

	@Column(name="ICMS_70_VBCST")
	private BigDecimal icms70Vbcst;

	@Column(name="ICMS_70_VICMS")
	private BigDecimal icms70Vicms;

	@Column(name="ICMS_70_VICMSDESON")
	private BigDecimal icms70Vicmsdeson;

	@Column(name="ICMS_70_VICMSST")
	private BigDecimal icms70Vicmsst;

	@Column(name="ICMS_90_CST")
	private int icms90Cst;

	@Column(name="ICMS_90_MODBC")
	private int icms90Modbc;

	@Column(name="ICMS_90_MODBCST")
	private int icms90Modbcst;

	@Column(name="ICMS_90_MOTDESICMS")
	private int icms90Motdesicms;

	@Column(name="ICMS_90_ORIG")
	private int icms90Orig;

	@Column(name="ICMS_90_PICMS")
	private BigDecimal icms90Picms;

	@Column(name="ICMS_90_PICMSST")
	private BigDecimal icms90Picmsst;

	@Column(name="ICMS_90_PMVAST")
	private BigDecimal icms90Pmvast;

	@Column(name="ICMS_90_PREDBC")
	private BigDecimal icms90Predbc;

	@Column(name="ICMS_90_PREDBCST")
	private BigDecimal icms90Predbcst;

	@Column(name="ICMS_90_VBC")
	private BigDecimal icms90Vbc;

	@Column(name="ICMS_90_VBCST")
	private BigDecimal icms90Vbcst;

	@Column(name="ICMS_90_VICMS")
	private BigDecimal icms90Vicms;

	@Column(name="ICMS_90_VICMSDESON")
	private BigDecimal icms90Vicmsdeson;

	@Column(name="ICMS_90_VICMSST")
	private BigDecimal icms90Vicmsst;

	@Column(name="ICMS_PART_CST")
	private int icmsPartCst;

	@Column(name="ICMS_PART_MODBC")
	private int icmsPartModbc;

	@Column(name="ICMS_PART_MODBCST")
	private int icmsPartModbcst;

	@Column(name="ICMS_PART_ORIG")
	private int icmsPartOrig;

	@Column(name="ICMS_PART_PBCOP")
	private BigDecimal icmsPartPbcop;

	@Column(name="ICMS_PART_PICMS")
	private BigDecimal icmsPartPicms;

	@Column(name="ICMS_PART_PICMSST")
	private BigDecimal icmsPartPicmsst;

	@Column(name="ICMS_PART_PMVAST")
	private BigDecimal icmsPartPmvast;

	@Column(name="ICMS_PART_PREDBC")
	private BigDecimal icmsPartPredbc;

	@Column(name="ICMS_PART_PREDBCST")
	private BigDecimal icmsPartPredbcst;

	@Column(name="ICMS_PART_UFST")
	private int icmsPartUfst;

	@Column(name="ICMS_PART_VBC")
	private BigDecimal icmsPartVbc;

	@Column(name="ICMS_PART_VBCST")
	private BigDecimal icmsPartVbcst;

	@Column(name="ICMS_PART_VICMS")
	private BigDecimal icmsPartVicms;

	@Column(name="ICMS_PART_VICMSST")
	private BigDecimal icmsPartVicmsst;

	@Column(name="ICMS_SN_101_CSOSN")
	private int icmsSn101Csosn;

	@Column(name="ICMS_SN_101_ORIG")
	private int icmsSn101Orig;

	@Column(name="ICMS_SN_101_PCREDSN")
	private BigDecimal icmsSn101Pcredsn;

	@Column(name="ICMS_SN_101_VCREDICMSSN")
	private BigDecimal icmsSn101Vcredicmssn;

	@Column(name="ICMS_SN_102_CSOSN")
	private int icmsSn102Csosn;

	@Column(name="ICMS_SN_102_ORIG")
	private int icmsSn102Orig;

	@Column(name="ICMS_SN_201_CSOSN")
	private int icmsSn201Csosn;

	@Column(name="ICMS_SN_201_MODBCST")
	private int icmsSn201Modbcst;

	@Column(name="ICMS_SN_201_ORIG")
	private int icmsSn201Orig;

	@Column(name="ICMS_SN_201_PCREDSN")
	private BigDecimal icmsSn201Pcredsn;

	@Column(name="ICMS_SN_201_PICMSST")
	private BigDecimal icmsSn201Picmsst;

	@Column(name="ICMS_SN_201_PMVAST")
	private BigDecimal icmsSn201Pmvast;

	@Column(name="ICMS_SN_201_PREDBCST")
	private BigDecimal icmsSn201Predbcst;

	@Column(name="ICMS_SN_201_VBCST")
	private BigDecimal icmsSn201Vbcst;

	@Column(name="ICMS_SN_201_VCREDICMSSN")
	private BigDecimal icmsSn201Vcredicmssn;

	private BigDecimal ICMS_SN_201_vICMSST;

	@Column(name="ICMS_SN_202_CSOSN")
	private int icmsSn202Csosn;

	@Column(name="ICMS_SN_202_MODBCST")
	private int icmsSn202Modbcst;

	@Column(name="ICMS_SN_202_ORIG")
	private int icmsSn202Orig;

	@Column(name="ICMS_SN_202_PICMSST")
	private BigDecimal icmsSn202Picmsst;

	@Column(name="ICMS_SN_202_PMVAST")
	private BigDecimal icmsSn202Pmvast;

	@Column(name="ICMS_SN_202_PREDBCST")
	private BigDecimal icmsSn202Predbcst;

	@Column(name="ICMS_SN_202_VBCST")
	private BigDecimal icmsSn202Vbcst;

	@Column(name="ICMS_SN_202_VICMSST")
	private BigDecimal icmsSn202Vicmsst;

	@Column(name="ICMS_SN_500_CSOSN")
	private int icmsSn500Csosn;

	@Column(name="ICMS_SN_500_ORIG")
	private int icmsSn500Orig;

	@Column(name="ICMS_SN_500_VBCSTRET")
	private BigDecimal icmsSn500Vbcstret;

	@Column(name="ICMS_SN_500_VICMSSTRET")
	private BigDecimal icmsSn500Vicmsstret;

	@Column(name="ICMS_SN_900_CSOSN")
	private int icmsSn900Csosn;

	@Column(name="ICMS_SN_900_MODBC")
	private int icmsSn900Modbc;

	@Column(name="ICMS_SN_900_MODBCST")
	private BigDecimal icmsSn900Modbcst;

	@Column(name="ICMS_SN_900_ORIG")
	private int icmsSn900Orig;

	@Column(name="ICMS_SN_900_PCREDSN")
	private BigDecimal icmsSn900Pcredsn;

	@Column(name="ICMS_SN_900_PICMS")
	private BigDecimal icmsSn900Picms;

	@Column(name="ICMS_SN_900_PICMSST")
	private BigDecimal icmsSn900Picmsst;

	@Column(name="ICMS_SN_900_PMVAST")
	private BigDecimal icmsSn900Pmvast;

	@Column(name="ICMS_SN_900_PREDBC")
	private BigDecimal icmsSn900Predbc;

	@Column(name="ICMS_SN_900_PREDBCST")
	private BigDecimal icmsSn900Predbcst;

	@Column(name="ICMS_SN_900_VBC")
	private BigDecimal icmsSn900Vbc;

	@Column(name="ICMS_SN_900_VBCST")
	private BigDecimal icmsSn900Vbcst;

	@Column(name="ICMS_SN_900_VCREDICMSSN")
	private BigDecimal icmsSn900Vcredicmssn;

	@Column(name="ICMS_SN_900_VICMS")
	private BigDecimal icmsSn900Vicms;

	@Column(name="ICMS_SN_900_VICMSST")
	private BigDecimal icmsSn900Vicmsst;

	@Column(name="ICMS_ST_CST")
	private int icmsStCst;

	@Column(name="ICMS_ST_ORIG")
	private int icmsStOrig;

	@Column(name="ICMS_ST_VBCSTDEST")
	private BigDecimal icmsStVbcstdest;

	@Column(name="ICMS_ST_VBCSTRET")
	private BigDecimal icmsStVbcstret;

	@Column(name="ICMS_ST_VICMSSTDEST")
	private BigDecimal icmsStVicmsstdest;

	@Column(name="ICMS_ST_VICMSSTRET")
	private BigDecimal icmsStVicmsstret;

	@Column(name="IND_TOT")
	private int indTot;

	@Column(name="LAST_CODUSER")
	private int lastCoduser;

	@Column(name="LAST_MOVTO")
	private LocalDateTime lastMovto;

	@Column(name="MOD_BC_ICMS_SUB")
	private BigDecimal modBcIcmsSub;

	@Column(name="N_ITEM")
	private int nItem;

	private String ncm;

	private String nve;

	@Column(name="PAUTA_MVA")
	private BigDecimal pautaMva;

	@Column(name="Q_COM")
	private BigDecimal qCom;
	
	@Column(name="EMB_COMPRA")
	private BigDecimal embComp;
	
	@Column(name="Q_REP")
	private BigDecimal qtdReposta;
	
	@Column(name="FATOR_CONVERSAO")
	private BigDecimal fatorConversao;

	@Column(name="Q_TRIB")
	private BigDecimal qTrib;

	@Column(name="RECORD_NO")
	private int recordNo;

	@Column(name="REDUCAO_BC_ICMS")
	private BigDecimal reducaoBcIcms;

	@Column(name="U_COM")
	private String uCom;
	
	@Column(name="U_COM_EPTUS")
	private String unidadeReposta;//UNIDADE Q FOI REPOSTA NO BANCO

	@Column(name="U_TRIB")
	private String uTrib;

	@Column(name="V_DESC")
	private BigDecimal vDesc;

	@Column(name="V_FRETE")
	private BigDecimal vFrete;

	@Column(name="V_OUTROS")
	private BigDecimal vOutros;

	@Column(name="V_PROD")
	private BigDecimal vProd;

	@Column(name="V_SEG")
	private BigDecimal vSeg;

	@Column(name="V_UN_COM")
	private BigDecimal vUnCom;
	
	@Column(name="V_UN_BRUTO_EPTUS")
	private BigDecimal vlrUnitBrutoEPTUS;

	@Column(name="V_UN_TRIB")
	private BigDecimal vUnTrib;

	@Column(name="VLR_ICMS")
	private BigDecimal vlrIcms;

	@Column(name="VLR_ICMS_DESON")
	private BigDecimal vlrIcmsDeson;

	@Column(name="VLR_ICMS_SUB")
	private BigDecimal vlrIcmsSub;

	@Column(name="X_PROD")
	private String xProd;

	public NfEntradaDet() {
	}

	public NfEntradaCab getNfEntradaCab() {
		return nfEntradaCab;
	}

	public void setNfEntradaCab(NfEntradaCab nfEntradaCab) {
		this.nfEntradaCab = nfEntradaCab;
	}

	public BigInteger getCheckDelete() {
		return checkDelete;
	}

	public void setCheckDelete(BigInteger checkDelete) {
		this.checkDelete = checkDelete;
	}
	
	public int getCodemp() {
		return codemp;
	}

	public void setCodemp(int codemp) {
		this.codemp = codemp;
	}

	public BigDecimal getAliqIcms() {
		return this.aliqIcms;
	}
	
	public void setAliqIcms(BigDecimal aliqIcms) {
		this.aliqIcms = aliqIcms;
	}

	public BigDecimal getBcIcms() {
		return this.bcIcms;
	}

	public void setBcIcms(BigDecimal bcIcms) {
		this.bcIcms = bcIcms;
	}

	public BigDecimal getBcIcmsSub() {
		return this.bcIcmsSub;
	}

	public void setBcIcmsSub(BigDecimal bcIcmsSub) {
		this.bcIcmsSub = bcIcmsSub;
	}

	public String getCEan() {
		return this.cEan;
	}

	public void setCEan(String cEan) {
		this.cEan = cEan;
	}

	public String getCEanTrib() {
		return this.cEanTrib;
	}

	public void setCEanTrib(String cEanTrib) {
		this.cEanTrib = cEanTrib;
	}

	public int getCProd() {
		return this.cProd;
	}

	public void setCProd(int cProd) {
		this.cProd = cProd;
	}

	public int getCfop() {
		return this.cfop;
	}

	public void setCfop(int cfop) {
		this.cfop = cfop;
	}

//	public int getCodNfCab() {
//		return this.codNfCab;
//	}
//
//	public void setCodNfCab(int codNfCab) {
//		this.codNfCab = codNfCab;
//	}
//
//	public BigInteger getCodNfCabFx() {
//		return this.codNfCabFx;
//	}
//
//	public void setCodNfCabFx(BigInteger codNfCabFx) {
//		this.codNfCabFx = codNfCabFx;
//	}

	public int getCstIcms() {
		return this.cstIcms;
	}

	public void setCstIcms(int cstIcms) {
		this.cstIcms = cstIcms;
	}

	public int getExtipi() {
		return this.extipi;
	}

	public void setExtipi(int extipi) {
		this.extipi = extipi;
	}

	public int getFlagAtivo() {
		return this.flagAtivo;
	}

	public void setFlagAtivo(int flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public int getIcms00Cst() {
		return this.icms00Cst;
	}

	public void setIcms00Cst(int icms00Cst) {
		this.icms00Cst = icms00Cst;
	}

	public int getIcms00Modbc() {
		return this.icms00Modbc;
	}

	public void setIcms00Modbc(int icms00Modbc) {
		this.icms00Modbc = icms00Modbc;
	}

	public int getIcms00Orig() {
		return this.icms00Orig;
	}

	public void setIcms00Orig(int icms00Orig) {
		this.icms00Orig = icms00Orig;
	}

	public BigDecimal getIcms00Picms() {
		return this.icms00Picms;
	}

	public void setIcms00Picms(BigDecimal icms00Picms) {
		this.icms00Picms = icms00Picms;
	}

	public BigDecimal getIcms00Vbc() {
		return this.icms00Vbc;
	}

	public void setIcms00Vbc(BigDecimal icms00Vbc) {
		this.icms00Vbc = icms00Vbc;
	}

	public BigDecimal getIcms00Vicms() {
		return this.icms00Vicms;
	}

	public void setIcms00Vicms(BigDecimal icms00Vicms) {
		this.icms00Vicms = icms00Vicms;
	}

	public int getIcms10Cst() {
		return this.icms10Cst;
	}

	public void setIcms10Cst(int icms10Cst) {
		this.icms10Cst = icms10Cst;
	}

	public int getIcms10Modbc() {
		return this.icms10Modbc;
	}

	public void setIcms10Modbc(int icms10Modbc) {
		this.icms10Modbc = icms10Modbc;
	}

	public int getIcms10Modbcst() {
		return this.icms10Modbcst;
	}

	public void setIcms10Modbcst(int icms10Modbcst) {
		this.icms10Modbcst = icms10Modbcst;
	}

	public int getIcms10Orig() {
		return this.icms10Orig;
	}

	public void setIcms10Orig(int icms10Orig) {
		this.icms10Orig = icms10Orig;
	}

	public BigDecimal getIcms10Picms() {
		return this.icms10Picms;
	}

	public void setIcms10Picms(BigDecimal icms10Picms) {
		this.icms10Picms = icms10Picms;
	}

	public BigDecimal getIcms10Picmsst() {
		return this.icms10Picmsst;
	}

	public void setIcms10Picmsst(BigDecimal icms10Picmsst) {
		this.icms10Picmsst = icms10Picmsst;
	}

	public BigDecimal getIcms10Pmvast() {
		return this.icms10Pmvast;
	}

	public void setIcms10Pmvast(BigDecimal icms10Pmvast) {
		this.icms10Pmvast = icms10Pmvast;
	}

	public BigDecimal getIcms10Predbcst() {
		return this.icms10Predbcst;
	}

	public void setIcms10Predbcst(BigDecimal icms10Predbcst) {
		this.icms10Predbcst = icms10Predbcst;
	}

	public BigDecimal getIcms10Vbc() {
		return this.icms10Vbc;
	}

	public void setIcms10Vbc(BigDecimal icms10Vbc) {
		this.icms10Vbc = icms10Vbc;
	}

	public BigDecimal getIcms10Vbcst() {
		return this.icms10Vbcst;
	}

	public void setIcms10Vbcst(BigDecimal icms10Vbcst) {
		this.icms10Vbcst = icms10Vbcst;
	}

	public BigDecimal getIcms10Vicms() {
		return this.icms10Vicms;
	}

	public void setIcms10Vicms(BigDecimal icms10Vicms) {
		this.icms10Vicms = icms10Vicms;
	}

	public BigDecimal getIcms10Vicmsst() {
		return this.icms10Vicmsst;
	}

	public void setIcms10Vicmsst(BigDecimal icms10Vicmsst) {
		this.icms10Vicmsst = icms10Vicmsst;
	}

	public int getIcms20Cst() {
		return this.icms20Cst;
	}

	public void setIcms20Cst(int icms20Cst) {
		this.icms20Cst = icms20Cst;
	}

	public int getIcms20Modbc() {
		return this.icms20Modbc;
	}

	public void setIcms20Modbc(int icms20Modbc) {
		this.icms20Modbc = icms20Modbc;
	}

	public BigDecimal getIcms20Motdesicms() {
		return this.icms20Motdesicms;
	}

	public void setIcms20Motdesicms(BigDecimal icms20Motdesicms) {
		this.icms20Motdesicms = icms20Motdesicms;
	}

	public int getIcms20Orig() {
		return this.icms20Orig;
	}

	public void setIcms20Orig(int icms20Orig) {
		this.icms20Orig = icms20Orig;
	}

	public BigDecimal getIcms20Picms() {
		return this.icms20Picms;
	}

	public void setIcms20Picms(BigDecimal icms20Picms) {
		this.icms20Picms = icms20Picms;
	}

	public BigDecimal getIcms20Predbc() {
		return this.icms20Predbc;
	}

	public void setIcms20Predbc(BigDecimal icms20Predbc) {
		this.icms20Predbc = icms20Predbc;
	}

	public BigDecimal getIcms20Vbc() {
		return this.icms20Vbc;
	}

	public void setIcms20Vbc(BigDecimal icms20Vbc) {
		this.icms20Vbc = icms20Vbc;
	}

	public BigDecimal getIcms20Vicms() {
		return this.icms20Vicms;
	}

	public void setIcms20Vicms(BigDecimal icms20Vicms) {
		this.icms20Vicms = icms20Vicms;
	}

	public BigDecimal getIcms20Vicmsdeson() {
		return this.icms20Vicmsdeson;
	}

	public void setIcms20Vicmsdeson(BigDecimal icms20Vicmsdeson) {
		this.icms20Vicmsdeson = icms20Vicmsdeson;
	}

	public int getIcms30Cst() {
		return this.icms30Cst;
	}

	public void setIcms30Cst(int icms30Cst) {
		this.icms30Cst = icms30Cst;
	}

	public int getIcms30Modbcst() {
		return this.icms30Modbcst;
	}

	public void setIcms30Modbcst(int icms30Modbcst) {
		this.icms30Modbcst = icms30Modbcst;
	}

	public int getIcms30Motdesicms() {
		return this.icms30Motdesicms;
	}

	public void setIcms30Motdesicms(int icms30Motdesicms) {
		this.icms30Motdesicms = icms30Motdesicms;
	}

	public int getIcms30Orig() {
		return this.icms30Orig;
	}

	public void setIcms30Orig(int icms30Orig) {
		this.icms30Orig = icms30Orig;
	}

	public BigDecimal getIcms30Picmsst() {
		return this.icms30Picmsst;
	}

	public void setIcms30Picmsst(BigDecimal icms30Picmsst) {
		this.icms30Picmsst = icms30Picmsst;
	}

	public BigDecimal getIcms30Pmvast() {
		return this.icms30Pmvast;
	}

	public void setIcms30Pmvast(BigDecimal icms30Pmvast) {
		this.icms30Pmvast = icms30Pmvast;
	}

	public BigDecimal getIcms30Predbcst() {
		return this.icms30Predbcst;
	}

	public void setIcms30Predbcst(BigDecimal icms30Predbcst) {
		this.icms30Predbcst = icms30Predbcst;
	}

	public BigDecimal getIcms30Vbcst() {
		return this.icms30Vbcst;
	}

	public void setIcms30Vbcst(BigDecimal icms30Vbcst) {
		this.icms30Vbcst = icms30Vbcst;
	}

	public BigDecimal getIcms30Vicmsdeson() {
		return this.icms30Vicmsdeson;
	}

	public void setIcms30Vicmsdeson(BigDecimal icms30Vicmsdeson) {
		this.icms30Vicmsdeson = icms30Vicmsdeson;
	}

	public BigDecimal getIcms30Vicmsst() {
		return this.icms30Vicmsst;
	}

	public void setIcms30Vicmsst(BigDecimal icms30Vicmsst) {
		this.icms30Vicmsst = icms30Vicmsst;
	}

	public int getIcms40Cst() {
		return this.icms40Cst;
	}

	public void setIcms40Cst(int icms40Cst) {
		this.icms40Cst = icms40Cst;
	}

	public int getIcms40Motdesicms() {
		return this.icms40Motdesicms;
	}

	public void setIcms40Motdesicms(int icms40Motdesicms) {
		this.icms40Motdesicms = icms40Motdesicms;
	}

	public int getIcms40Orig() {
		return this.icms40Orig;
	}

	public void setIcms40Orig(int icms40Orig) {
		this.icms40Orig = icms40Orig;
	}

	public BigDecimal getIcms40Vicmsdeson() {
		return this.icms40Vicmsdeson;
	}

	public void setIcms40Vicmsdeson(BigDecimal icms40Vicmsdeson) {
		this.icms40Vicmsdeson = icms40Vicmsdeson;
	}

	public int getIcms51Cst() {
		return this.icms51Cst;
	}

	public void setIcms51Cst(int icms51Cst) {
		this.icms51Cst = icms51Cst;
	}

	public int getIcms51Modbc() {
		return this.icms51Modbc;
	}

	public void setIcms51Modbc(int icms51Modbc) {
		this.icms51Modbc = icms51Modbc;
	}

	public int getIcms51Orig() {
		return this.icms51Orig;
	}

	public void setIcms51Orig(int icms51Orig) {
		this.icms51Orig = icms51Orig;
	}

	public BigDecimal getIcms51Pdif() {
		return this.icms51Pdif;
	}

	public void setIcms51Pdif(BigDecimal icms51Pdif) {
		this.icms51Pdif = icms51Pdif;
	}

	public BigDecimal getIcms51Picms() {
		return this.icms51Picms;
	}

	public void setIcms51Picms(BigDecimal icms51Picms) {
		this.icms51Picms = icms51Picms;
	}

	public BigDecimal getIcms51Predbc() {
		return this.icms51Predbc;
	}

	public void setIcms51Predbc(BigDecimal icms51Predbc) {
		this.icms51Predbc = icms51Predbc;
	}

	public BigDecimal getIcms51Vbc() {
		return this.icms51Vbc;
	}

	public void setIcms51Vbc(BigDecimal icms51Vbc) {
		this.icms51Vbc = icms51Vbc;
	}

	public BigDecimal getIcms51Vicms() {
		return this.icms51Vicms;
	}

	public void setIcms51Vicms(BigDecimal icms51Vicms) {
		this.icms51Vicms = icms51Vicms;
	}

	public BigDecimal getIcms51Vicmsdif() {
		return this.icms51Vicmsdif;
	}

	public void setIcms51Vicmsdif(BigDecimal icms51Vicmsdif) {
		this.icms51Vicmsdif = icms51Vicmsdif;
	}

	public BigDecimal getIcms51Vicmsop() {
		return this.icms51Vicmsop;
	}

	public void setIcms51Vicmsop(BigDecimal icms51Vicmsop) {
		this.icms51Vicmsop = icms51Vicmsop;
	}

	public int getIcms60Cst() {
		return this.icms60Cst;
	}

	public void setIcms60Cst(int icms60Cst) {
		this.icms60Cst = icms60Cst;
	}

	public int getIcms60Orig() {
		return this.icms60Orig;
	}

	public void setIcms60Orig(int icms60Orig) {
		this.icms60Orig = icms60Orig;
	}

	public BigDecimal getIcms60Vbcstret() {
		return this.icms60Vbcstret;
	}

	public void setIcms60Vbcstret(BigDecimal icms60Vbcstret) {
		this.icms60Vbcstret = icms60Vbcstret;
	}

	public BigDecimal getIcms60Vicmsstret() {
		return this.icms60Vicmsstret;
	}

	public void setIcms60Vicmsstret(BigDecimal icms60Vicmsstret) {
		this.icms60Vicmsstret = icms60Vicmsstret;
	}

	public int getIcms70Cst() {
		return this.icms70Cst;
	}

	public void setIcms70Cst(int icms70Cst) {
		this.icms70Cst = icms70Cst;
	}

	public int getIcms70Modbc() {
		return this.icms70Modbc;
	}

	public void setIcms70Modbc(int icms70Modbc) {
		this.icms70Modbc = icms70Modbc;
	}

	public int getIcms70Modbcst() {
		return this.icms70Modbcst;
	}

	public void setIcms70Modbcst(int icms70Modbcst) {
		this.icms70Modbcst = icms70Modbcst;
	}

	public int getIcms70Motdesicms() {
		return this.icms70Motdesicms;
	}

	public void setIcms70Motdesicms(int icms70Motdesicms) {
		this.icms70Motdesicms = icms70Motdesicms;
	}

	public int getIcms70Orig() {
		return this.icms70Orig;
	}

	public void setIcms70Orig(int icms70Orig) {
		this.icms70Orig = icms70Orig;
	}

	public BigDecimal getIcms70Picms() {
		return this.icms70Picms;
	}

	public void setIcms70Picms(BigDecimal icms70Picms) {
		this.icms70Picms = icms70Picms;
	}

	public BigDecimal getIcms70Picmsst() {
		return this.icms70Picmsst;
	}

	public void setIcms70Picmsst(BigDecimal icms70Picmsst) {
		this.icms70Picmsst = icms70Picmsst;
	}

	public BigDecimal getIcms70Pmvast() {
		return this.icms70Pmvast;
	}

	public void setIcms70Pmvast(BigDecimal icms70Pmvast) {
		this.icms70Pmvast = icms70Pmvast;
	}

	public BigDecimal getIcms70Predbc() {
		return this.icms70Predbc;
	}

	public void setIcms70Predbc(BigDecimal icms70Predbc) {
		this.icms70Predbc = icms70Predbc;
	}

	public BigDecimal getIcms70Predbcst() {
		return this.icms70Predbcst;
	}

	public void setIcms70Predbcst(BigDecimal icms70Predbcst) {
		this.icms70Predbcst = icms70Predbcst;
	}

	public BigDecimal getIcms70Vbc() {
		return this.icms70Vbc;
	}

	public void setIcms70Vbc(BigDecimal icms70Vbc) {
		this.icms70Vbc = icms70Vbc;
	}

	public BigDecimal getIcms70Vbcst() {
		return this.icms70Vbcst;
	}

	public void setIcms70Vbcst(BigDecimal icms70Vbcst) {
		this.icms70Vbcst = icms70Vbcst;
	}

	public BigDecimal getIcms70Vicms() {
		return this.icms70Vicms;
	}

	public void setIcms70Vicms(BigDecimal icms70Vicms) {
		this.icms70Vicms = icms70Vicms;
	}

	public BigDecimal getIcms70Vicmsdeson() {
		return this.icms70Vicmsdeson;
	}

	public void setIcms70Vicmsdeson(BigDecimal icms70Vicmsdeson) {
		this.icms70Vicmsdeson = icms70Vicmsdeson;
	}

	public BigDecimal getIcms70Vicmsst() {
		return this.icms70Vicmsst;
	}

	public void setIcms70Vicmsst(BigDecimal icms70Vicmsst) {
		this.icms70Vicmsst = icms70Vicmsst;
	}

	public int getIcms90Cst() {
		return this.icms90Cst;
	}

	public void setIcms90Cst(int icms90Cst) {
		this.icms90Cst = icms90Cst;
	}

	public int getIcms90Modbc() {
		return this.icms90Modbc;
	}

	public void setIcms90Modbc(int icms90Modbc) {
		this.icms90Modbc = icms90Modbc;
	}

	public int getIcms90Modbcst() {
		return this.icms90Modbcst;
	}

	public void setIcms90Modbcst(int icms90Modbcst) {
		this.icms90Modbcst = icms90Modbcst;
	}

	public int getIcms90Motdesicms() {
		return this.icms90Motdesicms;
	}

	public void setIcms90Motdesicms(int icms90Motdesicms) {
		this.icms90Motdesicms = icms90Motdesicms;
	}

	public int getIcms90Orig() {
		return this.icms90Orig;
	}

	public void setIcms90Orig(int icms90Orig) {
		this.icms90Orig = icms90Orig;
	}

	public BigDecimal getIcms90Picms() {
		return this.icms90Picms;
	}

	public void setIcms90Picms(BigDecimal icms90Picms) {
		this.icms90Picms = icms90Picms;
	}

	public BigDecimal getIcms90Picmsst() {
		return this.icms90Picmsst;
	}

	public void setIcms90Picmsst(BigDecimal icms90Picmsst) {
		this.icms90Picmsst = icms90Picmsst;
	}

	public BigDecimal getIcms90Pmvast() {
		return this.icms90Pmvast;
	}

	public void setIcms90Pmvast(BigDecimal icms90Pmvast) {
		this.icms90Pmvast = icms90Pmvast;
	}

	public BigDecimal getIcms90Predbc() {
		return this.icms90Predbc;
	}

	public void setIcms90Predbc(BigDecimal icms90Predbc) {
		this.icms90Predbc = icms90Predbc;
	}

	public BigDecimal getIcms90Predbcst() {
		return this.icms90Predbcst;
	}

	public void setIcms90Predbcst(BigDecimal icms90Predbcst) {
		this.icms90Predbcst = icms90Predbcst;
	}

	public BigDecimal getIcms90Vbc() {
		return this.icms90Vbc;
	}

	public void setIcms90Vbc(BigDecimal icms90Vbc) {
		this.icms90Vbc = icms90Vbc;
	}

	public BigDecimal getIcms90Vbcst() {
		return this.icms90Vbcst;
	}

	public void setIcms90Vbcst(BigDecimal icms90Vbcst) {
		this.icms90Vbcst = icms90Vbcst;
	}

	public BigDecimal getIcms90Vicms() {
		return this.icms90Vicms;
	}

	public void setIcms90Vicms(BigDecimal icms90Vicms) {
		this.icms90Vicms = icms90Vicms;
	}

	public BigDecimal getIcms90Vicmsdeson() {
		return this.icms90Vicmsdeson;
	}

	public void setIcms90Vicmsdeson(BigDecimal icms90Vicmsdeson) {
		this.icms90Vicmsdeson = icms90Vicmsdeson;
	}

	public BigDecimal getIcms90Vicmsst() {
		return this.icms90Vicmsst;
	}

	public void setIcms90Vicmsst(BigDecimal icms90Vicmsst) {
		this.icms90Vicmsst = icms90Vicmsst;
	}

	public int getIcmsPartCst() {
		return this.icmsPartCst;
	}

	public void setIcmsPartCst(int icmsPartCst) {
		this.icmsPartCst = icmsPartCst;
	}

	public int getIcmsPartModbc() {
		return this.icmsPartModbc;
	}

	public void setIcmsPartModbc(int icmsPartModbc) {
		this.icmsPartModbc = icmsPartModbc;
	}

	public int getIcmsPartModbcst() {
		return this.icmsPartModbcst;
	}

	public void setIcmsPartModbcst(int icmsPartModbcst) {
		this.icmsPartModbcst = icmsPartModbcst;
	}

	public int getIcmsPartOrig() {
		return this.icmsPartOrig;
	}

	public void setIcmsPartOrig(int icmsPartOrig) {
		this.icmsPartOrig = icmsPartOrig;
	}

	public BigDecimal getIcmsPartPbcop() {
		return this.icmsPartPbcop;
	}

	public void setIcmsPartPbcop(BigDecimal icmsPartPbcop) {
		this.icmsPartPbcop = icmsPartPbcop;
	}

	public BigDecimal getIcmsPartPicms() {
		return this.icmsPartPicms;
	}

	public void setIcmsPartPicms(BigDecimal icmsPartPicms) {
		this.icmsPartPicms = icmsPartPicms;
	}

	public BigDecimal getIcmsPartPicmsst() {
		return this.icmsPartPicmsst;
	}

	public void setIcmsPartPicmsst(BigDecimal icmsPartPicmsst) {
		this.icmsPartPicmsst = icmsPartPicmsst;
	}

	public BigDecimal getIcmsPartPmvast() {
		return this.icmsPartPmvast;
	}

	public void setIcmsPartPmvast(BigDecimal icmsPartPmvast) {
		this.icmsPartPmvast = icmsPartPmvast;
	}

	public BigDecimal getIcmsPartPredbc() {
		return this.icmsPartPredbc;
	}

	public void setIcmsPartPredbc(BigDecimal icmsPartPredbc) {
		this.icmsPartPredbc = icmsPartPredbc;
	}

	public BigDecimal getIcmsPartPredbcst() {
		return this.icmsPartPredbcst;
	}

	public void setIcmsPartPredbcst(BigDecimal icmsPartPredbcst) {
		this.icmsPartPredbcst = icmsPartPredbcst;
	}

	public int getIcmsPartUfst() {
		return this.icmsPartUfst;
	}

	public void setIcmsPartUfst(int icmsPartUfst) {
		this.icmsPartUfst = icmsPartUfst;
	}

	public BigDecimal getIcmsPartVbc() {
		return this.icmsPartVbc;
	}

	public void setIcmsPartVbc(BigDecimal icmsPartVbc) {
		this.icmsPartVbc = icmsPartVbc;
	}

	public BigDecimal getIcmsPartVbcst() {
		return this.icmsPartVbcst;
	}

	public void setIcmsPartVbcst(BigDecimal icmsPartVbcst) {
		this.icmsPartVbcst = icmsPartVbcst;
	}

	public BigDecimal getIcmsPartVicms() {
		return this.icmsPartVicms;
	}

	public void setIcmsPartVicms(BigDecimal icmsPartVicms) {
		this.icmsPartVicms = icmsPartVicms;
	}

	public BigDecimal getIcmsPartVicmsst() {
		return this.icmsPartVicmsst;
	}

	public void setIcmsPartVicmsst(BigDecimal icmsPartVicmsst) {
		this.icmsPartVicmsst = icmsPartVicmsst;
	}

	public int getIcmsSn101Csosn() {
		return this.icmsSn101Csosn;
	}

	public void setIcmsSn101Csosn(int icmsSn101Csosn) {
		this.icmsSn101Csosn = icmsSn101Csosn;
	}

	public int getIcmsSn101Orig() {
		return this.icmsSn101Orig;
	}

	public void setIcmsSn101Orig(int icmsSn101Orig) {
		this.icmsSn101Orig = icmsSn101Orig;
	}

	public BigDecimal getIcmsSn101Pcredsn() {
		return this.icmsSn101Pcredsn;
	}

	public void setIcmsSn101Pcredsn(BigDecimal icmsSn101Pcredsn) {
		this.icmsSn101Pcredsn = icmsSn101Pcredsn;
	}

	public BigDecimal getIcmsSn101Vcredicmssn() {
		return this.icmsSn101Vcredicmssn;
	}

	public void setIcmsSn101Vcredicmssn(BigDecimal icmsSn101Vcredicmssn) {
		this.icmsSn101Vcredicmssn = icmsSn101Vcredicmssn;
	}

	public int getIcmsSn102Csosn() {
		return this.icmsSn102Csosn;
	}

	public void setIcmsSn102Csosn(int icmsSn102Csosn) {
		this.icmsSn102Csosn = icmsSn102Csosn;
	}

	public int getIcmsSn102Orig() {
		return this.icmsSn102Orig;
	}

	public void setIcmsSn102Orig(int icmsSn102Orig) {
		this.icmsSn102Orig = icmsSn102Orig;
	}

	public int getIcmsSn201Csosn() {
		return this.icmsSn201Csosn;
	}

	public void setIcmsSn201Csosn(int icmsSn201Csosn) {
		this.icmsSn201Csosn = icmsSn201Csosn;
	}

	public int getIcmsSn201Modbcst() {
		return this.icmsSn201Modbcst;
	}

	public void setIcmsSn201Modbcst(int icmsSn201Modbcst) {
		this.icmsSn201Modbcst = icmsSn201Modbcst;
	}

	public int getIcmsSn201Orig() {
		return this.icmsSn201Orig;
	}

	public void setIcmsSn201Orig(int icmsSn201Orig) {
		this.icmsSn201Orig = icmsSn201Orig;
	}

	public BigDecimal getIcmsSn201Pcredsn() {
		return this.icmsSn201Pcredsn;
	}

	public void setIcmsSn201Pcredsn(BigDecimal icmsSn201Pcredsn) {
		this.icmsSn201Pcredsn = icmsSn201Pcredsn;
	}

	public BigDecimal getIcmsSn201Picmsst() {
		return this.icmsSn201Picmsst;
	}

	public void setIcmsSn201Picmsst(BigDecimal icmsSn201Picmsst) {
		this.icmsSn201Picmsst = icmsSn201Picmsst;
	}

	public BigDecimal getIcmsSn201Pmvast() {
		return this.icmsSn201Pmvast;
	}

	public void setIcmsSn201Pmvast(BigDecimal icmsSn201Pmvast) {
		this.icmsSn201Pmvast = icmsSn201Pmvast;
	}

	public BigDecimal getIcmsSn201Predbcst() {
		return this.icmsSn201Predbcst;
	}

	public void setIcmsSn201Predbcst(BigDecimal icmsSn201Predbcst) {
		this.icmsSn201Predbcst = icmsSn201Predbcst;
	}

	public BigDecimal getIcmsSn201Vbcst() {
		return this.icmsSn201Vbcst;
	}

	public void setIcmsSn201Vbcst(BigDecimal icmsSn201Vbcst) {
		this.icmsSn201Vbcst = icmsSn201Vbcst;
	}

	public BigDecimal getIcmsSn201Vcredicmssn() {
		return this.icmsSn201Vcredicmssn;
	}

	public void setIcmsSn201Vcredicmssn(BigDecimal icmsSn201Vcredicmssn) {
		this.icmsSn201Vcredicmssn = icmsSn201Vcredicmssn;
	}

	public BigDecimal getICMS_SN_201_vICMSST() {
		return this.ICMS_SN_201_vICMSST;
	}

	public void setICMS_SN_201_vICMSST(BigDecimal ICMS_SN_201_vICMSST) {
		this.ICMS_SN_201_vICMSST = ICMS_SN_201_vICMSST;
	}

	public int getIcmsSn202Csosn() {
		return this.icmsSn202Csosn;
	}

	public void setIcmsSn202Csosn(int icmsSn202Csosn) {
		this.icmsSn202Csosn = icmsSn202Csosn;
	}

	public int getIcmsSn202Modbcst() {
		return this.icmsSn202Modbcst;
	}

	public void setIcmsSn202Modbcst(int icmsSn202Modbcst) {
		this.icmsSn202Modbcst = icmsSn202Modbcst;
	}

	public int getIcmsSn202Orig() {
		return this.icmsSn202Orig;
	}

	public void setIcmsSn202Orig(int icmsSn202Orig) {
		this.icmsSn202Orig = icmsSn202Orig;
	}

	public BigDecimal getIcmsSn202Picmsst() {
		return this.icmsSn202Picmsst;
	}

	public void setIcmsSn202Picmsst(BigDecimal icmsSn202Picmsst) {
		this.icmsSn202Picmsst = icmsSn202Picmsst;
	}

	public BigDecimal getIcmsSn202Pmvast() {
		return this.icmsSn202Pmvast;
	}

	public void setIcmsSn202Pmvast(BigDecimal icmsSn202Pmvast) {
		this.icmsSn202Pmvast = icmsSn202Pmvast;
	}

	public BigDecimal getIcmsSn202Predbcst() {
		return this.icmsSn202Predbcst;
	}

	public void setIcmsSn202Predbcst(BigDecimal icmsSn202Predbcst) {
		this.icmsSn202Predbcst = icmsSn202Predbcst;
	}

	public BigDecimal getIcmsSn202Vbcst() {
		return this.icmsSn202Vbcst;
	}

	public void setIcmsSn202Vbcst(BigDecimal icmsSn202Vbcst) {
		this.icmsSn202Vbcst = icmsSn202Vbcst;
	}

	public BigDecimal getIcmsSn202Vicmsst() {
		return this.icmsSn202Vicmsst;
	}

	public void setIcmsSn202Vicmsst(BigDecimal icmsSn202Vicmsst) {
		this.icmsSn202Vicmsst = icmsSn202Vicmsst;
	}

	public int getIcmsSn500Csosn() {
		return this.icmsSn500Csosn;
	}

	public void setIcmsSn500Csosn(int icmsSn500Csosn) {
		this.icmsSn500Csosn = icmsSn500Csosn;
	}

	public int getIcmsSn500Orig() {
		return this.icmsSn500Orig;
	}

	public void setIcmsSn500Orig(int icmsSn500Orig) {
		this.icmsSn500Orig = icmsSn500Orig;
	}

	public BigDecimal getIcmsSn500Vbcstret() {
		return this.icmsSn500Vbcstret;
	}

	public void setIcmsSn500Vbcstret(BigDecimal icmsSn500Vbcstret) {
		this.icmsSn500Vbcstret = icmsSn500Vbcstret;
	}

	public BigDecimal getIcmsSn500Vicmsstret() {
		return this.icmsSn500Vicmsstret;
	}

	public void setIcmsSn500Vicmsstret(BigDecimal icmsSn500Vicmsstret) {
		this.icmsSn500Vicmsstret = icmsSn500Vicmsstret;
	}

	public int getIcmsSn900Csosn() {
		return this.icmsSn900Csosn;
	}

	public void setIcmsSn900Csosn(int icmsSn900Csosn) {
		this.icmsSn900Csosn = icmsSn900Csosn;
	}

	public int getIcmsSn900Modbc() {
		return this.icmsSn900Modbc;
	}

	public void setIcmsSn900Modbc(int icmsSn900Modbc) {
		this.icmsSn900Modbc = icmsSn900Modbc;
	}

	public BigDecimal getIcmsSn900Modbcst() {
		return this.icmsSn900Modbcst;
	}

	public void setIcmsSn900Modbcst(BigDecimal icmsSn900Modbcst) {
		this.icmsSn900Modbcst = icmsSn900Modbcst;
	}

	public int getIcmsSn900Orig() {
		return this.icmsSn900Orig;
	}

	public void setIcmsSn900Orig(int icmsSn900Orig) {
		this.icmsSn900Orig = icmsSn900Orig;
	}

	public BigDecimal getIcmsSn900Pcredsn() {
		return this.icmsSn900Pcredsn;
	}

	public void setIcmsSn900Pcredsn(BigDecimal icmsSn900Pcredsn) {
		this.icmsSn900Pcredsn = icmsSn900Pcredsn;
	}

	public BigDecimal getIcmsSn900Picms() {
		return this.icmsSn900Picms;
	}

	public void setIcmsSn900Picms(BigDecimal icmsSn900Picms) {
		this.icmsSn900Picms = icmsSn900Picms;
	}

	public BigDecimal getIcmsSn900Picmsst() {
		return this.icmsSn900Picmsst;
	}

	public void setIcmsSn900Picmsst(BigDecimal icmsSn900Picmsst) {
		this.icmsSn900Picmsst = icmsSn900Picmsst;
	}

	public BigDecimal getIcmsSn900Pmvast() {
		return this.icmsSn900Pmvast;
	}

	public void setIcmsSn900Pmvast(BigDecimal icmsSn900Pmvast) {
		this.icmsSn900Pmvast = icmsSn900Pmvast;
	}

	public BigDecimal getIcmsSn900Predbc() {
		return this.icmsSn900Predbc;
	}

	public void setIcmsSn900Predbc(BigDecimal icmsSn900Predbc) {
		this.icmsSn900Predbc = icmsSn900Predbc;
	}

	public BigDecimal getIcmsSn900Predbcst() {
		return this.icmsSn900Predbcst;
	}

	public void setIcmsSn900Predbcst(BigDecimal icmsSn900Predbcst) {
		this.icmsSn900Predbcst = icmsSn900Predbcst;
	}

	public BigDecimal getIcmsSn900Vbc() {
		return this.icmsSn900Vbc;
	}

	public void setIcmsSn900Vbc(BigDecimal icmsSn900Vbc) {
		this.icmsSn900Vbc = icmsSn900Vbc;
	}

	public BigDecimal getIcmsSn900Vbcst() {
		return this.icmsSn900Vbcst;
	}

	public void setIcmsSn900Vbcst(BigDecimal icmsSn900Vbcst) {
		this.icmsSn900Vbcst = icmsSn900Vbcst;
	}

	public BigDecimal getIcmsSn900Vcredicmssn() {
		return this.icmsSn900Vcredicmssn;
	}

	public void setIcmsSn900Vcredicmssn(BigDecimal icmsSn900Vcredicmssn) {
		this.icmsSn900Vcredicmssn = icmsSn900Vcredicmssn;
	}

	public BigDecimal getIcmsSn900Vicms() {
		return this.icmsSn900Vicms;
	}

	public void setIcmsSn900Vicms(BigDecimal icmsSn900Vicms) {
		this.icmsSn900Vicms = icmsSn900Vicms;
	}

	public BigDecimal getIcmsSn900Vicmsst() {
		return this.icmsSn900Vicmsst;
	}

	public void setIcmsSn900Vicmsst(BigDecimal icmsSn900Vicmsst) {
		this.icmsSn900Vicmsst = icmsSn900Vicmsst;
	}

	public int getIcmsStCst() {
		return this.icmsStCst;
	}

	public void setIcmsStCst(int icmsStCst) {
		this.icmsStCst = icmsStCst;
	}

	public int getIcmsStOrig() {
		return this.icmsStOrig;
	}

	public void setIcmsStOrig(int icmsStOrig) {
		this.icmsStOrig = icmsStOrig;
	}

	public BigDecimal getIcmsStVbcstdest() {
		return this.icmsStVbcstdest;
	}

	public void setIcmsStVbcstdest(BigDecimal icmsStVbcstdest) {
		this.icmsStVbcstdest = icmsStVbcstdest;
	}

	public BigDecimal getIcmsStVbcstret() {
		return this.icmsStVbcstret;
	}

	public void setIcmsStVbcstret(BigDecimal icmsStVbcstret) {
		this.icmsStVbcstret = icmsStVbcstret;
	}

	public BigDecimal getIcmsStVicmsstdest() {
		return this.icmsStVicmsstdest;
	}

	public void setIcmsStVicmsstdest(BigDecimal icmsStVicmsstdest) {
		this.icmsStVicmsstdest = icmsStVicmsstdest;
	}

	public BigDecimal getIcmsStVicmsstret() {
		return this.icmsStVicmsstret;
	}

	public void setIcmsStVicmsstret(BigDecimal icmsStVicmsstret) {
		this.icmsStVicmsstret = icmsStVicmsstret;
	}

	public int getIndTot() {
		return this.indTot;
	}

	public void setIndTot(int indTot) {
		this.indTot = indTot;
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

	public BigDecimal getModBcIcmsSub() {
		return this.modBcIcmsSub;
	}

	public void setModBcIcmsSub(BigDecimal modBcIcmsSub) {
		this.modBcIcmsSub = modBcIcmsSub;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getNItem() {
		return this.nItem;
	}

	public void setNItem(int nItem) {
		this.nItem = nItem;
	}

	public String getNcm() {
		return this.ncm;
	}

	public void setNcm(String ncm) {
		this.ncm = ncm;
	}

	public String getNve() {
		return this.nve;
	}

	public void setNve(String nve) {
		this.nve = nve;
	}

	public BigDecimal getPautaMva() {
		return this.pautaMva;
	}

	public void setPautaMva(BigDecimal pautaMva) {
		this.pautaMva = pautaMva;
	}

	public BigDecimal getQCom() {
		return this.qCom;
	}

	public void setQCom(BigDecimal qCom) {
		this.qCom = qCom;
	}	

	public BigDecimal getEmbComp() {
		return embComp;
	}

	public void setEmbComp(BigDecimal embComp) {
		this.embComp = embComp;
	}

	public BigDecimal getQtdReposta() {
		return qtdReposta;
	}

	public void setQtdReposta(BigDecimal qtdReposta) {
		this.qtdReposta = qtdReposta;
	}

	public String getUnidadeReposta() {
		return unidadeReposta;
	}

	public void setUnidadeReposta(String unidadeReposta) {
		this.unidadeReposta = unidadeReposta;
	}

	public BigDecimal getVlrUnitBrutoEPTUS() {
		return vlrUnitBrutoEPTUS;
	}

	public void setVlrUnitBrutoEPTUS(BigDecimal vlrUnitBrutoEPTUS) {
		this.vlrUnitBrutoEPTUS = vlrUnitBrutoEPTUS;
	}

	public BigDecimal getFatorConversao() {
		return fatorConversao;
	}

	public void setFatorConversao(BigDecimal fatorConversao) {
		this.fatorConversao = fatorConversao;
	}

	public BigDecimal getQTrib() {
		return this.qTrib;
	}

	public void setQTrib(BigDecimal qTrib) {
		this.qTrib = qTrib;
	}

	public int getRecordNo() {
		return this.recordNo;
	}

	public void setRecordNo(int recordNo) {
		this.recordNo = recordNo;
	}

	public BigDecimal getReducaoBcIcms() {
		return this.reducaoBcIcms;
	}

	public void setReducaoBcIcms(BigDecimal reducaoBcIcms) {
		this.reducaoBcIcms = reducaoBcIcms;
	}

	public String getUCom() {
		return this.uCom;
	}

	public void setUCom(String uCom) {
		this.uCom = uCom;
	}

	public String getUTrib() {
		return this.uTrib;
	}

	public void setUTrib(String uTrib) {
		this.uTrib = uTrib;
	}

	public BigDecimal getVDesc() {
		return this.vDesc;
	}

	public void setVDesc(BigDecimal vDesc) {
		this.vDesc = vDesc;
	}

	public BigDecimal getVFrete() {
		return this.vFrete;
	}

	public void setVFrete(BigDecimal vFrete) {
		this.vFrete = vFrete;
	}

	public BigDecimal getVOutros() {
		return this.vOutros;
	}

	public void setVOutros(BigDecimal vOutros) {
		this.vOutros = vOutros;
	}

	public BigDecimal getVProd() {
		return this.vProd;
	}

	public void setVProd(BigDecimal vProd) {
		this.vProd = vProd;
	}

	public BigDecimal getVSeg() {
		return this.vSeg;
	}

	public void setVSeg(BigDecimal vSeg) {
		this.vSeg = vSeg;
	}

	public BigDecimal getVUnCom() {
		return this.vUnCom;
	}

	public void setVUnCom(BigDecimal vUnCom) {
		this.vUnCom = vUnCom;
	}

	public BigDecimal getVUnTrib() {
		return this.vUnTrib;
	}

	public void setVUnTrib(BigDecimal vUnTrib) {
		this.vUnTrib = vUnTrib;
	}

	public BigDecimal getVlrIcms() {
		return this.vlrIcms;
	}

	public void setVlrIcms(BigDecimal vlrIcms) {
		this.vlrIcms = vlrIcms;
	}

	public BigDecimal getVlrIcmsDeson() {
		return this.vlrIcmsDeson;
	}

	public void setVlrIcmsDeson(BigDecimal vlrIcmsDeson) {
		this.vlrIcmsDeson = vlrIcmsDeson;
	}

	public BigDecimal getVlrIcmsSub() {
		return this.vlrIcmsSub;
	}

	public void setVlrIcmsSub(BigDecimal vlrIcmsSub) {
		this.vlrIcmsSub = vlrIcmsSub;
	}

	public String getXProd() {
		return this.xProd;
	}

	public void setXProd(String xProd) {
		this.xProd = xProd;
	}

}