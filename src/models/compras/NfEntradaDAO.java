package models.compras; 

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;

import org.hibernate.HibernateException;

import application.DadosGlobais;
import br.com.samuelweb.nfe.util.NFCeUtil;
import br.inf.portalfiscal.nfe.schema.nfe.TNFe;
import br.inf.portalfiscal.nfe.schema.nfe.TNFe.InfNFe.Cobr.Dup;
import br.inf.portalfiscal.nfe.schema.nfe.TNFe.InfNFe.Emit;
import connect.ConnectionHib;
import models.configuracoes.DepositoEstoque;
import models.configuracoes.SequencialDAO;
import models.compras.Fornecedor;
import models.compras.NfEntradaCab;
import models.vendas.Cidade;
import models.vendas.CidadeDAO;
import tools.enums.EnumCompartilhamento;
import tools.enums.EnumLogRetornoStatus;
import tools.utils.LogRetorno;
import tools.utils.RelacaoItems;
import tools.utils.Util;

public class NfEntradaDAO {

	private LocalDateTime ts_now; 
	private BigInteger utctag;
	private EntityManager em = null;

	public LogRetorno insert(TNFe nfe, Fornecedor fornecedor, List<RelacaoItems> listRelItems, String xmlNFe){

		LogRetorno logRet = new LogRetorno();
		SequencialDAO seqDAO = new SequencialDAO();
		boolean itemNovo = false;

		logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);

		//		NfEntradaCab retrnNFentradaCab =null;
		//////////////////////////////////////////////////////
		Emit emitenteNFe = nfe.getInfNFe().getEmit();		

		//		System.out.println(11111111);
		//PREPARAR DADOS DO NFe CAB
		NfEntradaCab nfEntradaCab = null;
		try {
			nfEntradaCab = (NfEntradaCab)Util.class.newInstance().initializeAttribClass(new NfEntradaCab());
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TNFe tNFeCab = nfe;
		Item itemRel = null;

		nfEntradaCab.setIdeCuf(tNFeCab.getInfNFe().getIde() !=null && tNFeCab.getInfNFe().getIde().getCUF() !=null ? Integer.parseInt(tNFeCab.getInfNFe().getIde().getCUF()) : nfEntradaCab.getIdeCuf());
		nfEntradaCab.setChnfe(tNFeCab.getInfNFe().getIde() !=null && tNFeCab.getInfNFe().getIde().getCNF() !=null ? tNFeCab.getInfNFe().getIde().getCNF() : nfEntradaCab.getChnfe());
		//		nfEntradaCab.setCodOperacao(tNFeCab.getInfNFe().getIde().get);
		nfEntradaCab.setIdeNatop(tNFeCab.getInfNFe().getIde() !=null && tNFeCab.getInfNFe().getIde().getNatOp() !=null ? tNFeCab.getInfNFe().getIde().getNatOp() : nfEntradaCab.getIdeNatop());
		nfEntradaCab.setIdeIndpag(tNFeCab.getInfNFe().getIde() !=null && tNFeCab.getInfNFe().getIde().getIndPag() !=null ? Integer.parseInt(tNFeCab.getInfNFe().getIde().getIndPag()) : nfEntradaCab.getIdeIndpag());
		nfEntradaCab.setIdeMod(tNFeCab.getInfNFe().getIde() !=null && tNFeCab.getInfNFe().getIde().getMod() !=null ? Integer.parseInt(tNFeCab.getInfNFe().getIde().getMod()) : nfEntradaCab.getIdeMod());
		nfEntradaCab.setIdeSerie(tNFeCab.getInfNFe().getIde() !=null && tNFeCab.getInfNFe().getIde().getSerie() !=null ? Integer.parseInt(tNFeCab.getInfNFe().getIde().getSerie()) : nfEntradaCab.getIdeSerie());
		nfEntradaCab.setIdeNnf(tNFeCab.getInfNFe().getIde() !=null && tNFeCab.getInfNFe().getIde().getNNF() !=null ? Integer.parseInt(tNFeCab.getInfNFe().getIde().getNNF()) : nfEntradaCab.getIdeNnf());
		nfEntradaCab.setIdeDhemi(tNFeCab.getInfNFe().getIde() !=null && tNFeCab.getInfNFe().getIde().getDhEmi() !=null ? LocalDateTime.parse(tNFeCab.getInfNFe().getIde().getDhEmi(), DateTimeFormatter.ISO_DATE_TIME) : nfEntradaCab.getIdeDhemi());
		//arrumar
		nfEntradaCab.setIdeDhsaient(tNFeCab.getInfNFe().getIde() !=null && tNFeCab.getInfNFe().getIde().getDhSaiEnt() !=null ? LocalDateTime.parse(tNFeCab.getInfNFe().getIde().getDhSaiEnt(), DateTimeFormatter.ISO_DATE_TIME) : nfEntradaCab.getIdeDhsaient());
		//-------------------------
		nfEntradaCab.setIdeTpnf(tNFeCab.getInfNFe().getIde() !=null && tNFeCab.getInfNFe().getIde().getTpNF() !=null ? Integer.parseInt(tNFeCab.getInfNFe().getIde().getTpNF()) : nfEntradaCab.getIdeTpnf());
		nfEntradaCab.setIdeCmunfg(tNFeCab.getInfNFe().getIde() !=null && tNFeCab.getInfNFe().getIde().getCMunFG() !=null ? Integer.parseInt(tNFeCab.getInfNFe().getIde().getCMunFG()) : nfEntradaCab.getIdeCmunfg());
		nfEntradaCab.setIdeTpimp(tNFeCab.getInfNFe().getIde() !=null && tNFeCab.getInfNFe().getIde().getTpImp() !=null ? Integer.parseInt(tNFeCab.getInfNFe().getIde().getTpImp()) : nfEntradaCab.getIdeTpimp());
		nfEntradaCab.setIdeTpemis(tNFeCab.getInfNFe().getIde() !=null && tNFeCab.getInfNFe().getIde().getTpEmis() !=null ? Integer.parseInt(tNFeCab.getInfNFe().getIde().getTpEmis()) : nfEntradaCab.getIdeTpemis());		
		nfEntradaCab.setIdeCdv(tNFeCab.getInfNFe().getIde() !=null && tNFeCab.getInfNFe().getIde().getCDV() !=null ? Integer.parseInt(tNFeCab.getInfNFe().getIde().getCDV()) : nfEntradaCab.getIdeCdv());
		nfEntradaCab.setIdeTpamb(tNFeCab.getInfNFe().getIde() !=null && tNFeCab.getInfNFe().getIde().getTpAmb() !=null ? Integer.parseInt(tNFeCab.getInfNFe().getIde().getTpAmb()) : nfEntradaCab.getIdeTpamb());
		nfEntradaCab.setIdeFinnfe(tNFeCab.getInfNFe().getIde() !=null && tNFeCab.getInfNFe().getIde().getFinNFe() !=null ? Integer.parseInt(tNFeCab.getInfNFe().getIde().getFinNFe()) : nfEntradaCab.getIdeFinnfe());
		nfEntradaCab.setIdeProcemi(tNFeCab.getInfNFe().getIde() !=null && tNFeCab.getInfNFe().getIde().getProcEmi() !=null ? Integer.parseInt(tNFeCab.getInfNFe().getIde().getProcEmi()) : nfEntradaCab.getIdeProcemi());
		nfEntradaCab.setIdeVerproc(tNFeCab.getInfNFe().getIde() !=null && tNFeCab.getInfNFe().getIde().getVerProc() !=null ? tNFeCab.getInfNFe().getIde().getVerProc() : nfEntradaCab.getIdeVerproc());
		nfEntradaCab.setIdeDhcont(tNFeCab.getInfNFe().getIde() !=null && tNFeCab.getInfNFe().getIde().getDhCont() !=null ? LocalDateTime.parse(tNFeCab.getInfNFe().getIde().getDhCont(), DateTimeFormatter.ISO_DATE_TIME) : nfEntradaCab.getIdeDhcont());
		nfEntradaCab.setEmitCnpj(tNFeCab.getInfNFe().getEmit() !=null && tNFeCab.getInfNFe().getEmit().getCNPJ() !=null ? tNFeCab.getInfNFe().getEmit().getCNPJ() : nfEntradaCab.getEmitCnpj());
		nfEntradaCab.setEmitCpf(tNFeCab.getInfNFe().getEmit() !=null && tNFeCab.getInfNFe().getEmit().getCPF() !=null ? tNFeCab.getInfNFe().getEmit().getCPF() : nfEntradaCab.getEmitCpf());
		nfEntradaCab.setEmitXnome(tNFeCab.getInfNFe().getEmit() !=null && tNFeCab.getInfNFe().getEmit().getXNome() !=null ? tNFeCab.getInfNFe().getEmit().getXNome() : nfEntradaCab.getEmitXnome());		
		nfEntradaCab.setEmitXfant(tNFeCab.getInfNFe().getEmit() !=null && tNFeCab.getInfNFe().getEmit().getXFant() !=null ? tNFeCab.getInfNFe().getEmit().getXFant() : nfEntradaCab.getEmitXfant());
		nfEntradaCab.setEnderemitXlgr(tNFeCab.getInfNFe().getEmit().getEnderEmit() !=null && tNFeCab.getInfNFe().getEmit().getEnderEmit().getXLgr() !=null ? tNFeCab.getInfNFe().getEmit().getEnderEmit().getXLgr() : nfEntradaCab.getEnderemitXlgr());
		nfEntradaCab.setEnderemitNro(tNFeCab.getInfNFe().getEmit().getEnderEmit() !=null && tNFeCab.getInfNFe().getEmit().getEnderEmit().getNro() !=null ? tNFeCab.getInfNFe().getEmit().getEnderEmit().getNro() : nfEntradaCab.getEnderemitNro());
		nfEntradaCab.setEnderemitXcpl(tNFeCab.getInfNFe().getEmit().getEnderEmit() !=null && tNFeCab.getInfNFe().getEmit().getEnderEmit().getXCpl() !=null ? tNFeCab.getInfNFe().getEmit().getEnderEmit().getXCpl() : nfEntradaCab.getEnderemitXcpl());
		nfEntradaCab.setEnderemitXbairro(tNFeCab.getInfNFe().getEmit().getEnderEmit() !=null && tNFeCab.getInfNFe().getEmit().getEnderEmit().getXBairro() !=null ? tNFeCab.getInfNFe().getEmit().getEnderEmit().getXBairro() : nfEntradaCab.getEnderemitXbairro());
		nfEntradaCab.setEnderemitCmun(tNFeCab.getInfNFe().getEmit().getEnderEmit() !=null && tNFeCab.getInfNFe().getEmit().getEnderEmit().getCMun() !=null ? Integer.parseInt(tNFeCab.getInfNFe().getEmit().getEnderEmit().getCMun()) : nfEntradaCab.getEnderemitCmun());
		nfEntradaCab.setEnderemitXmun(tNFeCab.getInfNFe().getEmit().getEnderEmit() !=null && tNFeCab.getInfNFe().getEmit().getEnderEmit().getXMun() !=null ? tNFeCab.getInfNFe().getEmit().getEnderEmit().getXMun() : nfEntradaCab.getEnderemitXmun());
		nfEntradaCab.setEnderemitUf(tNFeCab.getInfNFe().getEmit().getEnderEmit() !=null && tNFeCab.getInfNFe().getEmit().getEnderEmit().getUF() !=null ? tNFeCab.getInfNFe().getEmit().getEnderEmit().getUF().value() : nfEntradaCab.getEnderemitUf());
		nfEntradaCab.setEnderemitCep(tNFeCab.getInfNFe().getEmit().getEnderEmit() !=null && tNFeCab.getInfNFe().getEmit().getEnderEmit().getCEP() !=null ? Integer.parseInt(tNFeCab.getInfNFe().getEmit().getEnderEmit().getCEP()) : nfEntradaCab.getEnderemitCep());
		nfEntradaCab.setEnderemitCpais(tNFeCab.getInfNFe().getEmit().getEnderEmit() !=null && tNFeCab.getInfNFe().getEmit().getEnderEmit().getCPais() !=null ? Integer.parseInt(tNFeCab.getInfNFe().getEmit().getEnderEmit().getCPais()) : nfEntradaCab.getEnderemitCpais());
		nfEntradaCab.setEnderemitXpais(tNFeCab.getInfNFe().getEmit().getEnderEmit() !=null && tNFeCab.getInfNFe().getEmit().getEnderEmit().getXPais() !=null ? tNFeCab.getInfNFe().getEmit().getEnderEmit().getXPais() : nfEntradaCab.getEnderemitXpais());
		nfEntradaCab.setEnderemitFone(tNFeCab.getInfNFe().getEmit().getEnderEmit() !=null && tNFeCab.getInfNFe().getEmit().getEnderEmit().getFone() !=null ? tNFeCab.getInfNFe().getEmit().getEnderEmit().getFone() : nfEntradaCab.getEnderemitFone());		
		nfEntradaCab.setEnderemitIe(tNFeCab.getInfNFe().getEmit() !=null && tNFeCab.getInfNFe().getEmit().getIE() !=null ? tNFeCab.getInfNFe().getEmit().getIE() : nfEntradaCab.getEnderemitIe());
		nfEntradaCab.setEnderemitIest(tNFeCab.getInfNFe().getEmit() !=null && tNFeCab.getInfNFe().getEmit().getIEST() !=null ? tNFeCab.getInfNFe().getEmit().getIEST() : nfEntradaCab.getEnderemitIest());
		nfEntradaCab.setEnderemitIm(tNFeCab.getInfNFe().getEmit() !=null && tNFeCab.getInfNFe().getEmit().getIM() !=null ? tNFeCab.getInfNFe().getEmit().getIM() : nfEntradaCab.getEnderemitIm());
		nfEntradaCab.setEnderemitCnae(tNFeCab.getInfNFe().getEmit().getCNAE() != null ? Integer.parseInt(tNFeCab.getInfNFe().getEmit().getCNAE()) : nfEntradaCab.getEnderemitCnae());
		nfEntradaCab.setEnderemitCrt(tNFeCab.getInfNFe().getEmit().getCRT() !=null ? Integer.parseInt(tNFeCab.getInfNFe().getEmit().getCRT()) : nfEntradaCab.getEnderemitCrt());
		nfEntradaCab.setDestCnpj(tNFeCab.getInfNFe().getDest() !=null && tNFeCab.getInfNFe().getDest() !=null ? tNFeCab.getInfNFe().getDest().getCNPJ() : nfEntradaCab.getDestCnpj());
		nfEntradaCab.setDestCpf(tNFeCab.getInfNFe().getDest() !=null && tNFeCab.getInfNFe().getDest().getCPF() !=null ? tNFeCab.getInfNFe().getDest().getCPF() : nfEntradaCab.getDestCpf());
		nfEntradaCab.setDestXnome(tNFeCab.getInfNFe().getDest() !=null && tNFeCab.getInfNFe().getDest().getXNome() !=null ? tNFeCab.getInfNFe().getDest().getXNome() : nfEntradaCab.getDestXnome());
		nfEntradaCab.setEnderdestEmail(tNFeCab.getInfNFe().getDest() !=null && tNFeCab.getInfNFe().getDest().getEmail() !=null ? tNFeCab.getInfNFe().getDest().getEmail() : nfEntradaCab.getEnderdestEmail());
		nfEntradaCab.setEnderdestXlgr(tNFeCab.getInfNFe().getDest().getEnderDest() !=null && tNFeCab.getInfNFe().getDest().getEnderDest().getXLgr() !=null ? tNFeCab.getInfNFe().getDest().getEnderDest().getXLgr() : nfEntradaCab.getEnderdestXlgr());
		nfEntradaCab.setEnderdestNro(tNFeCab.getInfNFe().getDest().getEnderDest() !=null && tNFeCab.getInfNFe().getDest().getEnderDest().getNro() !=null ? tNFeCab.getInfNFe().getDest().getEnderDest().getNro() : nfEntradaCab.getEnderdestNro());
		nfEntradaCab.setEnderdestXcpl(tNFeCab.getInfNFe().getDest().getEnderDest() !=null && tNFeCab.getInfNFe().getDest().getEnderDest().getXCpl() !=null ? tNFeCab.getInfNFe().getDest().getEnderDest().getXCpl() : nfEntradaCab.getEnderdestXcpl());
		nfEntradaCab.setEnderdestXbairro(tNFeCab.getInfNFe().getDest().getEnderDest() !=null && tNFeCab.getInfNFe().getDest().getEnderDest().getXBairro() !=null ? tNFeCab.getInfNFe().getDest().getEnderDest().getXBairro() : nfEntradaCab.getEnderdestXbairro());
		nfEntradaCab.setEnderdestCmun(tNFeCab.getInfNFe().getDest().getEnderDest() !=null && tNFeCab.getInfNFe().getDest().getEnderDest().getCMun() !=null ? Integer.parseInt(tNFeCab.getInfNFe().getDest().getEnderDest().getCMun()) : nfEntradaCab.getEnderdestCmun());
		nfEntradaCab.setEnderdestXmun(tNFeCab.getInfNFe().getDest().getEnderDest() !=null && tNFeCab.getInfNFe().getDest().getEnderDest().getXMun() !=null ? tNFeCab.getInfNFe().getDest().getEnderDest().getXMun() : nfEntradaCab.getEnderdestXmun());		
		nfEntradaCab.setEnderdestUf(tNFeCab.getInfNFe().getDest().getEnderDest() !=null && tNFeCab.getInfNFe().getDest().getEnderDest().getUF() !=null ? tNFeCab.getInfNFe().getDest().getEnderDest().getUF().value() : nfEntradaCab.getEnderdestUf());
		nfEntradaCab.setEnderdestCep(tNFeCab.getInfNFe().getDest().getEnderDest() !=null && tNFeCab.getInfNFe().getDest().getEnderDest().getCEP() !=null ? Integer.parseInt(tNFeCab.getInfNFe().getDest().getEnderDest().getCEP()) : nfEntradaCab.getEnderdestCep());
		nfEntradaCab.setEnderdestCpais(tNFeCab.getInfNFe().getDest().getEnderDest() !=null && tNFeCab.getInfNFe().getDest().getEnderDest().getCPais() !=null ? Integer.parseInt(tNFeCab.getInfNFe().getDest().getEnderDest().getCPais()) : nfEntradaCab.getEnderdestCpais());
		nfEntradaCab.setEnderdestXpais(tNFeCab.getInfNFe().getDest().getEnderDest() !=null && tNFeCab.getInfNFe().getDest().getEnderDest().getXPais() !=null ? tNFeCab.getInfNFe().getDest().getEnderDest().getXPais() : nfEntradaCab.getEnderdestXpais());
		nfEntradaCab.setEnderdestFone(tNFeCab.getInfNFe().getDest().getEnderDest() !=null && tNFeCab.getInfNFe().getDest().getEnderDest().getFone() !=null ? tNFeCab.getInfNFe().getDest().getEnderDest().getFone() : nfEntradaCab.getEnderdestFone());
		nfEntradaCab.setEnderdestNro(tNFeCab.getInfNFe().getDest().getEnderDest() !=null && tNFeCab.getInfNFe().getDest().getEnderDest().getNro() !=null ? tNFeCab.getInfNFe().getDest().getEnderDest().getNro() : nfEntradaCab.getEnderdestNro());
		nfEntradaCab.setEnderdestIe(tNFeCab.getInfNFe().getDest() !=null && tNFeCab.getInfNFe().getDest().getIE() !=null ? tNFeCab.getInfNFe().getDest().getIE() : nfEntradaCab.getEnderdestIe());
		nfEntradaCab.setEnderdestIsuf( tNFeCab.getInfNFe().getDest().getISUF()!=null ? Integer.parseInt(tNFeCab.getInfNFe().getDest().getISUF()) : nfEntradaCab.getEnderdestIsuf());
		nfEntradaCab.setEntregaCnpj(tNFeCab.getInfNFe().getEntrega() !=null && tNFeCab.getInfNFe().getEntrega().getCNPJ() != null ? tNFeCab.getInfNFe().getEntrega().getCNPJ() : nfEntradaCab.getEntregaCnpj());
		nfEntradaCab.setEntregaXlgr(tNFeCab.getInfNFe().getEntrega() !=null && tNFeCab.getInfNFe().getEntrega().getXLgr() !=null ? tNFeCab.getInfNFe().getEntrega().getXLgr() : nfEntradaCab.getEntregaXlgr());
		nfEntradaCab.setEntregaNro(tNFeCab.getInfNFe().getEntrega() !=null && tNFeCab.getInfNFe().getEntrega().getNro() !=null ? tNFeCab.getInfNFe().getEntrega().getNro() : nfEntradaCab.getEntregaNro());		
		nfEntradaCab.setEntregaXcpl(tNFeCab.getInfNFe().getEntrega() !=null && tNFeCab.getInfNFe().getEntrega().getXCpl() !=null ? tNFeCab.getInfNFe().getEntrega().getXCpl() : nfEntradaCab.getEntregaXcpl());
		nfEntradaCab.setEntregaXbairro(tNFeCab.getInfNFe().getEntrega() !=null && tNFeCab.getInfNFe().getEntrega().getXBairro() !=null ? tNFeCab.getInfNFe().getEntrega().getXBairro() : nfEntradaCab.getEntregaXbairro());
		nfEntradaCab.setEntregaCmun(tNFeCab.getInfNFe().getEntrega() !=null && tNFeCab.getInfNFe().getEntrega().getCMun() !=null ? Integer.parseInt(tNFeCab.getInfNFe().getEntrega().getCMun()) : nfEntradaCab.getEntregaCmun());
		nfEntradaCab.setEntregaXmun(tNFeCab.getInfNFe().getEntrega() !=null && tNFeCab.getInfNFe().getEntrega().getXMun() !=null ? tNFeCab.getInfNFe().getEntrega().getXMun() : nfEntradaCab.getEntregaXmun());
		nfEntradaCab.setEntregaUf(tNFeCab.getInfNFe().getEntrega() !=null && tNFeCab.getInfNFe().getEntrega().getUF() !=null ? tNFeCab.getInfNFe().getEntrega().getUF().value() : nfEntradaCab.getEntregaUf());
		nfEntradaCab.setTotalIcmstotVbc(tNFeCab.getInfNFe().getTotal().getICMSTot() !=null && tNFeCab.getInfNFe().getTotal().getICMSTot().getVBC() !=null ? BigDecimal.valueOf(Double.parseDouble(tNFeCab.getInfNFe().getTotal().getICMSTot().getVBC())) : nfEntradaCab.getTotalIcmstotVbc());
		nfEntradaCab.setTotalIcmstotVicms(tNFeCab.getInfNFe().getTotal().getICMSTot() !=null && tNFeCab.getInfNFe().getTotal().getICMSTot().getVICMS() !=null ? BigDecimal.valueOf(Double.parseDouble(tNFeCab.getInfNFe().getTotal().getICMSTot().getVICMS())) : nfEntradaCab.getTotalIcmstotVicms());
		nfEntradaCab.setTotalIcmstotVbcst(tNFeCab.getInfNFe().getTotal().getICMSTot() !=null && tNFeCab.getInfNFe().getTotal().getICMSTot().getVBCST() !=null ? BigDecimal.valueOf(Double.parseDouble(tNFeCab.getInfNFe().getTotal().getICMSTot().getVBCST())) : nfEntradaCab.getTotalIcmstotVbcst());
		nfEntradaCab.setTotalIcmstotVst(tNFeCab.getInfNFe().getTotal().getICMSTot() !=null && tNFeCab.getInfNFe().getTotal().getICMSTot().getVST() !=null ? BigDecimal.valueOf(Double.parseDouble(tNFeCab.getInfNFe().getTotal().getICMSTot().getVST())) : nfEntradaCab.getTotalIcmstotVst());
		nfEntradaCab.setTotalIcmstotVprod(tNFeCab.getInfNFe().getTotal().getICMSTot() !=null && tNFeCab.getInfNFe().getTotal().getICMSTot().getVProd() !=null ? BigDecimal.valueOf(Double.parseDouble(tNFeCab.getInfNFe().getTotal().getICMSTot().getVProd())) : nfEntradaCab.getTotalIcmstotVprod());
		nfEntradaCab.setTotalIcmstotVfrete(tNFeCab.getInfNFe().getTotal().getICMSTot() !=null && tNFeCab.getInfNFe().getTotal().getICMSTot().getVFrete() !=null ? BigDecimal.valueOf(Double.parseDouble(tNFeCab.getInfNFe().getTotal().getICMSTot().getVFrete())) : nfEntradaCab.getTotalIcmstotVfrete());
		nfEntradaCab.setTotalIcmstotVseg(tNFeCab.getInfNFe().getTotal().getICMSTot() !=null && tNFeCab.getInfNFe().getTotal().getICMSTot().getVSeg() !=null ? BigDecimal.valueOf(Double.parseDouble(tNFeCab.getInfNFe().getTotal().getICMSTot().getVSeg())) : nfEntradaCab.getTotalIcmstotVseg());
		nfEntradaCab.setTotalIcmstotVdesc(tNFeCab.getInfNFe().getTotal().getICMSTot() !=null && tNFeCab.getInfNFe().getTotal().getICMSTot().getVDesc() !=null ? BigDecimal.valueOf(Double.parseDouble(tNFeCab.getInfNFe().getTotal().getICMSTot().getVDesc())) : nfEntradaCab.getTotalIcmstotVdesc());
		nfEntradaCab.setTotalIcmstotVii(tNFeCab.getInfNFe().getTotal().getICMSTot() !=null && tNFeCab.getInfNFe().getTotal().getICMSTot().getVII() !=null ? BigDecimal.valueOf(Double.parseDouble(tNFeCab.getInfNFe().getTotal().getICMSTot().getVII())) : nfEntradaCab.getTotalIcmstotVii());
		nfEntradaCab.setTotalIcmstotVipi(tNFeCab.getInfNFe().getTotal().getICMSTot() !=null && tNFeCab.getInfNFe().getTotal().getICMSTot().getVIPI() !=null ? BigDecimal.valueOf(Double.parseDouble(tNFeCab.getInfNFe().getTotal().getICMSTot().getVIPI())) : nfEntradaCab.getTotalIcmstotVipi());
		nfEntradaCab.setTotalIcmstotVpis(tNFeCab.getInfNFe().getTotal().getICMSTot() !=null && tNFeCab.getInfNFe().getTotal().getICMSTot().getVPIS() !=null ? BigDecimal.valueOf(Double.parseDouble(tNFeCab.getInfNFe().getTotal().getICMSTot().getVPIS())) : nfEntradaCab.getTotalIcmstotVpis());
		nfEntradaCab.setTotalIcmstotVcofins(tNFeCab.getInfNFe().getTotal().getICMSTot() !=null && tNFeCab.getInfNFe().getTotal().getICMSTot().getVCOFINS() !=null ? BigDecimal.valueOf(Double.parseDouble(tNFeCab.getInfNFe().getTotal().getICMSTot().getVCOFINS())) : nfEntradaCab.getTotalIcmstotVcofins());
		nfEntradaCab.setTotalIcmstotVoutros(tNFeCab.getInfNFe().getTotal().getICMSTot() !=null && tNFeCab.getInfNFe().getTotal().getICMSTot().getVOutro() !=null ? BigDecimal.valueOf(Double.parseDouble(tNFeCab.getInfNFe().getTotal().getICMSTot().getVOutro())) : nfEntradaCab.getTotalIcmstotVoutros());
		nfEntradaCab.setTotalIssqntotVserv(tNFeCab.getInfNFe().getTotal().getISSQNtot() !=null && tNFeCab.getInfNFe().getTotal().getISSQNtot().getVServ() !=null ? BigDecimal.valueOf(Double.parseDouble(tNFeCab.getInfNFe().getTotal().getISSQNtot().getVServ())) : nfEntradaCab.getTotalIssqntotVserv());
		nfEntradaCab.setTotalIssqntotVbc(tNFeCab.getInfNFe().getTotal().getISSQNtot() !=null && tNFeCab.getInfNFe().getTotal().getISSQNtot().getVBC() !=null ? BigDecimal.valueOf(Double.parseDouble(tNFeCab.getInfNFe().getTotal().getISSQNtot().getVBC())) : nfEntradaCab.getTotalIssqntotVbc());
		nfEntradaCab.setTotalIssqntotViss(tNFeCab.getInfNFe().getTotal().getISSQNtot() !=null && tNFeCab.getInfNFe().getTotal().getISSQNtot().getVISS() !=null ? BigDecimal.valueOf(Double.parseDouble(tNFeCab.getInfNFe().getTotal().getISSQNtot().getVISS())) : nfEntradaCab.getTotalIssqntotViss());
		nfEntradaCab.setTotalIssqntotVpis(tNFeCab.getInfNFe().getTotal().getISSQNtot() !=null && tNFeCab.getInfNFe().getTotal().getISSQNtot().getVISS() !=null ? BigDecimal.valueOf(Double.parseDouble(tNFeCab.getInfNFe().getTotal().getISSQNtot().getVPIS())) : nfEntradaCab.getTotalIssqntotVpis());
		nfEntradaCab.setTotalIssqntotVcofins(tNFeCab.getInfNFe().getTotal().getISSQNtot() !=null && tNFeCab.getInfNFe().getTotal().getISSQNtot().getVISS() !=null ? BigDecimal.valueOf(Double.parseDouble(tNFeCab.getInfNFe().getTotal().getISSQNtot().getVCOFINS())) : nfEntradaCab.getTotalIssqntotVcofins());
		nfEntradaCab.setTotalRettribVretpis(tNFeCab.getInfNFe().getTotal().getISSQNtot() !=null && tNFeCab.getInfNFe().getTotal().getRetTrib().getVRetPIS() !=null ? BigDecimal.valueOf(Double.parseDouble(tNFeCab.getInfNFe().getTotal().getRetTrib().getVRetPIS())) : nfEntradaCab.getTotalRettribVretpis());
		nfEntradaCab.setTotalRettribVretcofins(tNFeCab.getInfNFe().getTotal().getISSQNtot() !=null && tNFeCab.getInfNFe().getTotal().getRetTrib().getVRetPIS() !=null ? BigDecimal.valueOf(Double.parseDouble(tNFeCab.getInfNFe().getTotal().getRetTrib().getVRetCOFINS())) : nfEntradaCab.getTotalRettribVretcofins());
		nfEntradaCab.setTotalRettribVretcsll(tNFeCab.getInfNFe().getTotal().getISSQNtot() !=null && tNFeCab.getInfNFe().getTotal().getRetTrib().getVRetPIS() !=null ? BigDecimal.valueOf(Double.parseDouble(tNFeCab.getInfNFe().getTotal().getRetTrib().getVRetCSLL())) : nfEntradaCab.getTotalRettribVretcsll());		
		nfEntradaCab.setTotalRettribVbcirrf(tNFeCab.getInfNFe().getTotal().getRetTrib() !=null && tNFeCab.getInfNFe().getTotal().getRetTrib().getVBCIRRF() !=null ? BigDecimal.valueOf(Double.parseDouble(tNFeCab.getInfNFe().getTotal().getRetTrib().getVBCIRRF())) : nfEntradaCab.getTotalRettribVbcirrf());
		nfEntradaCab.setTotalRettribVirrf(tNFeCab.getInfNFe().getTotal().getRetTrib() !=null && tNFeCab.getInfNFe().getTotal().getRetTrib().getVIRRF() !=null ? BigDecimal.valueOf(Double.parseDouble(tNFeCab.getInfNFe().getTotal().getRetTrib().getVIRRF())) : nfEntradaCab.getTotalRettribVirrf());
		nfEntradaCab.setTotalRettribVbcretprev(tNFeCab.getInfNFe().getTotal().getRetTrib() !=null && tNFeCab.getInfNFe().getTotal().getRetTrib().getVBCRetPrev() !=null ? BigDecimal.valueOf(Double.parseDouble(tNFeCab.getInfNFe().getTotal().getRetTrib().getVBCRetPrev())) : nfEntradaCab.getTotalRettribVbcretprev());
		nfEntradaCab.setTotalRettribVretprev(tNFeCab.getInfNFe().getTotal().getRetTrib() !=null && tNFeCab.getInfNFe().getTotal().getRetTrib().getVRetPrev() !=null ? BigDecimal.valueOf(Double.parseDouble(tNFeCab.getInfNFe().getTotal().getRetTrib().getVRetPrev())) : nfEntradaCab.getTotalRettribVretprev());
		nfEntradaCab.setTransporteTranspModfrete(tNFeCab.getInfNFe().getTransp() !=null && tNFeCab.getInfNFe().getTransp().getModFrete() !=null ?  Integer.parseInt(tNFeCab.getInfNFe().getTransp().getModFrete()) : nfEntradaCab.getTransporteTranspModfrete());
		nfEntradaCab.setTransporteTransportaCnpj(tNFeCab.getInfNFe().getTransp().getTransporta() !=null && tNFeCab.getInfNFe().getTransp().getTransporta().getCNPJ() !=null ? tNFeCab.getInfNFe().getTransp().getTransporta().getCNPJ() : nfEntradaCab.getTransporteTransportaCnpj());
		nfEntradaCab.setTransporteTransportaCpf(tNFeCab.getInfNFe().getTransp().getTransporta() !=null && tNFeCab.getInfNFe().getTransp().getTransporta().getCPF() !=null ? tNFeCab.getInfNFe().getTransp().getTransporta().getCPF() : nfEntradaCab.getTransporteTransportaCpf());
		nfEntradaCab.setTransporteVeictranspRntc(tNFeCab.getInfNFe().getTransp().getVeicTransp() !=null && tNFeCab.getInfNFe().getTransp().getVeicTransp().getRNTC() !=null ? tNFeCab.getInfNFe().getTransp().getVeicTransp().getRNTC() : nfEntradaCab.getTransporteVeictranspRntc());
		nfEntradaCab.setTransporteVeictranspPlaca(tNFeCab.getInfNFe().getTransp().getVeicTransp() !=null && tNFeCab.getInfNFe().getTransp().getVeicTransp().getPlaca() !=null ? tNFeCab.getInfNFe().getTransp().getVeicTransp().getPlaca() : nfEntradaCab.getTransporteVeictranspPlaca());
		nfEntradaCab.setTransporteVeictranspUf(tNFeCab.getInfNFe().getTransp().getVeicTransp() !=null && tNFeCab.getInfNFe().getTransp().getVeicTransp().getUF() !=null ?  tNFeCab.getInfNFe().getTransp().getVeicTransp().getUF().value() : nfEntradaCab.getTransporteVeictranspUf());
		nfEntradaCab.setTransporteTransportaXnome(tNFeCab.getInfNFe().getTransp().getTransporta() !=null && tNFeCab.getInfNFe().getTransp().getTransporta().getXNome() !=null ? tNFeCab.getInfNFe().getTransp().getTransporta().getXNome() : nfEntradaCab.getTransporteTransportaXnome());
		nfEntradaCab.setTransporteTransportaXender(tNFeCab.getInfNFe().getTransp().getTransporta() !=null && tNFeCab.getInfNFe().getTransp().getTransporta().getXEnder() !=null ? tNFeCab.getInfNFe().getTransp().getTransporta().getXEnder() : nfEntradaCab.getTransporteTransportaXender());
		nfEntradaCab.setTransporteTransportaXmun(tNFeCab.getInfNFe().getTransp().getTransporta() !=null && tNFeCab.getInfNFe().getTransp().getTransporta().getXMun() !=null ? tNFeCab.getInfNFe().getTransp().getTransporta().getXMun() : nfEntradaCab.getTransporteTransportaXmun());		
		nfEntradaCab.setTransporteTransportaUf(tNFeCab.getInfNFe().getTransp().getTransporta() !=null && tNFeCab.getInfNFe().getTransp().getTransporta().getUF() !=null ? tNFeCab.getInfNFe().getTransp().getTransporta().getUF().value() : nfEntradaCab.getTransporteTransportaUf());
		nfEntradaCab.setTransporteRettranspVserv(tNFeCab.getInfNFe().getTransp().getRetTransp() !=null && tNFeCab.getInfNFe().getTransp().getRetTransp().getVServ() !=null ?  BigDecimal.valueOf(Double.parseDouble(tNFeCab.getInfNFe().getTransp().getRetTransp().getVServ())) : nfEntradaCab.getTransporteRettranspVserv());
		nfEntradaCab.setTransporteRettranspVbcret(tNFeCab.getInfNFe().getTransp().getRetTransp() !=null && tNFeCab.getInfNFe().getTransp().getRetTransp().getVBCRet() !=null ? BigDecimal.valueOf(Double.parseDouble(tNFeCab.getInfNFe().getTransp().getRetTransp().getVBCRet())) : nfEntradaCab.getTransporteRettranspVbcret());
		nfEntradaCab.setTransporteRettranspPicmsret(tNFeCab.getInfNFe().getTransp().getRetTransp() !=null && tNFeCab.getInfNFe().getTransp().getRetTransp().getPICMSRet() !=null ? BigDecimal.valueOf(Double.parseDouble(tNFeCab.getInfNFe().getTransp().getRetTransp().getPICMSRet())) : nfEntradaCab.getTransporteRettranspPicmsret());
		nfEntradaCab.setTransporteRettranspVicmsret(tNFeCab.getInfNFe().getTransp().getRetTransp() !=null && tNFeCab.getInfNFe().getTransp().getRetTransp().getVICMSRet() !=null ? BigDecimal.valueOf(Double.parseDouble(tNFeCab.getInfNFe().getTransp().getRetTransp().getVICMSRet())) : nfEntradaCab.getTransporteRettranspVicmsret());
		nfEntradaCab.setTransporteRettranspCfop(tNFeCab.getInfNFe().getTransp().getRetTransp() !=null && tNFeCab.getInfNFe().getTransp().getRetTransp().getCFOP() !=null ? Integer.parseInt(tNFeCab.getInfNFe().getTransp().getRetTransp().getCFOP()) : nfEntradaCab.getTransporteRettranspCfop());
		nfEntradaCab.setTransporteRettranspCmunfg(tNFeCab.getInfNFe().getTransp().getRetTransp() !=null && tNFeCab.getInfNFe().getTransp().getRetTransp().getCMunFG() !=null ? Integer.parseInt(tNFeCab.getInfNFe().getTransp().getRetTransp().getCMunFG()) : nfEntradaCab.getTransporteRettranspCmunfg());
		nfEntradaCab.setTransporteVolQvol(tNFeCab.getInfNFe().getTransp() !=null && tNFeCab.getInfNFe().getTransp().getVol() !=null ? Integer.parseInt(tNFeCab.getInfNFe().getTransp().getVol().iterator().next().getQVol()) : nfEntradaCab.getTransporteVolQvol());
		//		nfEntradaCab.setTransporteVolEsp(tNFeCab.getInfNFe().getTransp() !=null && tNFeCab.getInfNFe().getTransp().getVol() !=null ? tNFeCab.getInfNFe().getTransp().getVol().iterator().next().getEsp() : nfEntradaCab.getTransporteVolEsp());
		//		nfEntradaCab.setTransporteVolMarca(tNFeCab.getInfNFe().getTransp() !=null && tNFeCab.getInfNFe().getTransp().getVol() !=null ? tNFeCab.getInfNFe().getTransp().getVol().iterator().next().getMarca() : nfEntradaCab.getTransporteVolMarca());
		//		nfEntradaCab.setTransporteVolPesol(tNFeCab.getInfNFe().getTransp() !=null && tNFeCab.getInfNFe().getTransp().getVol() !=null ? BigDecimal.valueOf(Double.parseDouble(tNFeCab.getInfNFe().getTransp().getVol().iterator().next().getPesoL())) : nfEntradaCab.getTransporteVolPesol());
		//		nfEntradaCab.setTransporteVolPesob(tNFeCab.getInfNFe().getTransp() !=null && tNFeCab.getInfNFe().getTransp().getVol() !=null ? BigDecimal.valueOf(Double.parseDouble(tNFeCab.getInfNFe().getTransp().getVol().iterator().next().getPesoB())) : nfEntradaCab.getTransporteVolPesol());
		//		nfEntradaCab.setTransporteLacres_nlacre(tNFeCab.getInfNFe().getTransp() !=null && tNFeCab.getInfNFe().getTransp().getVol() !=null && tNFeCab.getInfNFe().getTransp().getVol().iterator().next().getLacres() !=null ? tNFeCab.getInfNFe().getTransp().getVol().iterator().next().getLacres().iterator().next().getNLacre() : nfEntradaCab.getTransporteLacres_nlacre());
		nfEntradaCab.setExportaUfsaidapais(tNFeCab.getInfNFe().getExporta() !=null && tNFeCab.getInfNFe().getExporta().getUFSaidaPais() !=null ? tNFeCab.getInfNFe().getExporta().getUFSaidaPais().value() : nfEntradaCab.getExportaUfsaidapais());
		nfEntradaCab.setExportaXlocdespacho(tNFeCab.getInfNFe().getExporta() !=null && tNFeCab.getInfNFe().getExporta().getXLocDespacho() !=null ? tNFeCab.getInfNFe().getExporta().getXLocDespacho() : nfEntradaCab.getExportaXlocdespacho());
		nfEntradaCab.setExportaXlocexporta(tNFeCab.getInfNFe().getExporta() !=null && tNFeCab.getInfNFe().getExporta().getXLocExporta() !=null ? tNFeCab.getInfNFe().getExporta().getXLocExporta() : nfEntradaCab.getExportaXlocexporta());
		nfEntradaCab.setInfadicInfcpl(tNFeCab.getInfNFe().getInfAdic() !=null && tNFeCab.getInfNFe().getInfAdic().getInfCpl() !=null ? tNFeCab.getInfNFe().getInfAdic().getInfCpl() : nfEntradaCab.getInfadicInfcpl());
		nfEntradaCab.setInfadicNfadfisco(tNFeCab.getInfNFe().getInfAdic() !=null && tNFeCab.getInfNFe().getInfAdic().getInfAdFisco() !=null ? tNFeCab.getInfNFe().getInfAdic().getInfAdFisco() : nfEntradaCab.getInfadicNfadfisco());
		//////////////////////////////////////////////////////

		//



		try{
			em = ConnectionHib.emf.createEntityManager();
			em.getTransaction().begin();

			Date dataServer  = (Date) em.createNativeQuery(ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL").getSingleResult();
			utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));		
			ts_now = Util.dateToLocalDateTime(dataServer);

			nfEntradaCab.setCodemp(DadosGlobais.empresaLogada.getCodemp());
			nfEntradaCab.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
			nfEntradaCab.setNoDocto(Integer.parseInt(tNFeCab.getInfNFe().getIde().getNNF()));
			nfEntradaCab.setCheckDelete(Util.checkDeleteCreate());

			nfEntradaCab.setUtctag(utctag);
			nfEntradaCab.setLastMovto(ts_now);
			nfEntradaCab.setFlagAtivo(1);
			if(fornecedor == null){
				try {
					fornecedor =(Fornecedor) Util.class.newInstance().initializeAttribClass(new Fornecedor());
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				//UPDATE NO SEQUENCIAL
				em.createQuery(
						"UPDATE Sequencial s SET s.cpFornecedor = s.cpFornecedor+1 WHERE s.codemp= :codemp AND s.checkDelete = :checkDelete")
				.setParameter("codemp", DadosGlobais.empresaLogada.getCodigo())
				.setParameter("checkDelete", DadosGlobais.empresaLogada.getCheckDelete()).executeUpdate();
				Integer codigo = seqDAO.getLastInsertCodigo(DadosGlobais.empresaLogada.getCodigo(), "cpFornecedor") + 1;

				fornecedor.setCodigo(codigo);
				fornecedor.setCodemp(DadosGlobais.empresaLogada.getCodemp());
				fornecedor.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
				fornecedor.setCheckDelete(Util.checkDeleteCreate());
				fornecedor.setUtctag(utctag);
				fornecedor.setLastMovto(ts_now);
				fornecedor.setFlagAtivo(1);
				fornecedor.setFantasia(emitenteNFe.getXFant());
				fornecedor.setRazao(emitenteNFe.getXNome());
				fornecedor.setCpfCnpj(emitenteNFe.getCNPJ() !=null ? Util.getStringConverterCNPJ(emitenteNFe.getCNPJ()) : emitenteNFe.getCPF());
				fornecedor.setEndereco(emitenteNFe.getEnderEmit().getXLgr());
				fornecedor.setEndNumero(emitenteNFe.getEnderEmit().getNro());
				fornecedor.setComplemento(emitenteNFe.getEnderEmit().getXCpl()!=null ? emitenteNFe.getEnderEmit().getXCpl() : "");
				fornecedor.setBairro(emitenteNFe.getEnderEmit().getXBairro());
				fornecedor.setCep(emitenteNFe.getEnderEmit().getCEP());
				fornecedor.setUf(emitenteNFe.getEnderEmit().getUF().value());
				//BUSCAR CODIGO DE CIDADE PELO CODIGO IBGE
				if(emitenteNFe.getEnderEmit().getCMun() !=null){
					try {
						Cidade cidade = (Cidade)CidadeDAO.class.newInstance().getByIBGE(emitenteNFe.getEnderEmit().getCMun()).getObjeto();
						fornecedor.setCodCidade(cidade.getCodigo());
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}			

				fornecedor.setCidade(emitenteNFe.getEnderEmit().getXMun());
				fornecedor.setFone(emitenteNFe.getEnderEmit() !=null && emitenteNFe.getEnderEmit().getFone() !=null ? emitenteNFe.getEnderEmit().getFone() : "");
				fornecedor.setIeRg(emitenteNFe.getIE());

				nfEntradaCab.setFornecedor(fornecedor);
				em.persist(fornecedor);
			}else{
				nfEntradaCab.setFornecedor(fornecedor);
			}

			//XML NFe TABELA NF_ENTRADA_XML
			NfEntradaXml xmlEntrada = (NfEntradaXml)Util.class.newInstance().initializeAttribClass(new NfEntradaXml());
			xmlEntrada.setCheckDelete(Util.checkDeleteCreate());
			xmlEntrada.setUtctag(utctag);
			xmlEntrada.setCodemp(nfEntradaCab.getCodemp());
			xmlEntrada.setFlagAtivo(1);
			xmlEntrada.setLastCoduser(nfEntradaCab.getLastCoduser());
			xmlEntrada.setLastMovto(ts_now);
			xmlEntrada.setNfEntradaCab(nfEntradaCab);
			xmlEntrada.setUtctag(utctag);
			xmlEntrada.setXml(xmlNFe);


			//DETALHES DE PRODUTOS NFe
			NfEntradaDet detProdutos =null;
			ItemFornecedor itemFornecedor = null;
			ItemCodBar cdb = null;
			Set<NfEntradaDet> produtos = new HashSet<NfEntradaDet>();
			Set<ItemCodBar> itemCodbars = new HashSet<ItemCodBar>();
			for(int i =0; i < tNFeCab.getInfNFe().getDet().size(); i++){
				try {
					detProdutos = (NfEntradaDet)Util.class.newInstance().initializeAttribClass(new NfEntradaDet());
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				detProdutos.setNfEntradaCab(nfEntradaCab);
				detProdutos.setLastMovto(ts_now);
				detProdutos.setCodemp(DadosGlobais.empresaLogada.getCodemp());
				detProdutos.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
				detProdutos.setCheckDelete(Util.checkDeleteCreate());
				detProdutos.setCProd(Integer.parseInt(tNFeCab.getInfNFe().getDet().get(i).getProd().getCProd()));
				detProdutos.setXProd(tNFeCab.getInfNFe().getDet().get(i).getProd().getXProd());
				detProdutos.setFlagAtivo(1);
				detProdutos.setUnidadeReposta(listRelItems.get(i).getItem().getUnidadeEmb() != null ? listRelItems.get(i).getItem().getUnidadeEmb().getDescricao() : "");
				detProdutos.setEmbComp(new BigDecimal(listRelItems.get(i).getEmbComprasRelProd()));
				detProdutos.setQtdReposta(new BigDecimal(listRelItems.get(i).getQtdReposicaoRelProdProperty().get()));
				detProdutos.setVlrUnitBrutoEPTUS(new BigDecimal(listRelItems.get(i).getVlrUndBrutoRelProd()));
				detProdutos.setFatorConversao(new BigDecimal(listRelItems.get(i).getfatorConversao()));
				//				System.out.println(listRelItems.get(i).getItem().getUnidadeEmb());

				//ITEM			
				if(listRelItems.get(i).getItem().getCheckDelete().toString().equals("0")){				
					em.createQuery("UPDATE Sequencial s SET s.cpItem = s.cpItem+1 WHERE s.codemp= :codemp AND s.checkDelete = :checkDelete")
					.setParameter("codemp", DadosGlobais.empresaLogada.getCodigo())
					.setParameter("checkDelete", DadosGlobais.empresaLogada.getCheckDelete())
					.executeUpdate();				
					Integer codigo = seqDAO.getLastInsertCodigo(DadosGlobais.empresaLogada.getCodigo(), "cpItem") + 1;		

					itemRel = listRelItems.get(i).getItem();

					itemRel.setCodigo(codigo);
					itemRel.setCheckDelete(Util.checkDeleteCreate());
					itemRel.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
					itemRel.setUtctag(utctag);
					itemRel.setLastMovto(ts_now);
					itemRel.setCodemp(DadosGlobais.empresaLogada.getCodemp());
					itemRel.setFlagAtivo(1);

					itemNovo = true;

					if(itemNovo){						
						if(!listRelItems.get(i).getCodBarRelProd().equals("")){//
							//VALIDAR SE O CODIGO BARRAS NÃO EXISTA NO BANCO
							ItemDAO iten = new ItemDAO();					
							ItemCodBar itcbr = (ItemCodBar) iten.getCodBarById(listRelItems.get(i).getCodBarRelProd()).getObjeto();
							if(itcbr ==null){
								ItemCodBar cdbNFe = new ItemCodBar();
								cdbNFe.setCodemp(DadosGlobais.empresaLogada.getCodemp());
								cdbNFe.setFlagAtivo(1);
								cdbNFe.setCheckDelete(Util.checkDeleteCreate());
								cdbNFe.setCodBarras(listRelItems.get(i).getCodBarRelProd());
								cdbNFe.setQtdEmbalagem(BigDecimal.valueOf(1.00));
								cdbNFe.setLastMovto(ts_now);
								cdbNFe.setUtctag(utctag);
								cdbNFe.setFlagCodbarPrincipal(1);
								cdbNFe.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
								cdbNFe.setItem(itemRel);
								itemCodbars.add(cdbNFe);						

							}

						}
						//CODIGO BARRAS PATRÃO
						cdb = new ItemCodBar();
						cdb.setCodemp(itemRel.getCodemp());
						cdb.setFlagAtivo(1);
						cdb.setCheckDelete(Util.checkDeleteCreate());
						cdb.setCodBarras(itemRel.getCodigo().toString());
						cdb.setQtdEmbalagem(BigDecimal.valueOf(1.00));
						cdb.setLastMovto(ts_now);
						cdb.setUtctag(utctag);
						cdb.setFlagCodbarPrincipal(listRelItems.get(i).getCodBarRelProd().equals("") ? 1 : 0);
						cdb.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
						cdb.setItem(itemRel);
						itemCodbars.add(cdb);
						//					}

						itemRel.setItemCodBars(itemCodbars);

						//ITEM VALOR
						if(listRelItems.get(i).getItem().getItemValors() !=null){
							for(ItemValor iValor : listRelItems.get(i).getItem().getItemValors()){								
								iValor.setItem(itemRel);
								iValor.setCheckDelete(Util.checkDeleteCreate());
								iValor.setFlagAtivo(1);
								iValor.setLastMovto(ts_now);
								iValor.setUtctag(utctag);
								iValor.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());								
							}							
							itemRel.setItemValors(listRelItems.get(i).getItem().getItemValors());							
						}

						//DEPOSITO DE ESTOQUE PRA NOVO PRODUTO
						Set<ItemEstoqueDeposito> itemEstoqueDepositos = new HashSet<ItemEstoqueDeposito>();

						List<DepositoEstoque> listDepositos ;

						listDepositos = em.createNamedQuery("DepositoEstoqueAll")
								.setParameter("flag", 1)
								.setParameter("codemp",Util.getCompartilhamentoEntidade(EnumCompartilhamento.DEPOSITO_ESTOQUES))
								.getResultList();

						for (int j = 0; j < listDepositos.size(); j++) {							
							//							for (int i = 0; i < listValores.size(); i++) {
							for (int k = 0; k < listRelItems.get(i).getItem().getItemValors().size(); k++) {
								ItemEstoqueDeposito itemEstoqueDeposito = (ItemEstoqueDeposito) Util.class.newInstance().initializeAttribClass(new ItemEstoqueDeposito());

								itemEstoqueDeposito.setCodemp(listRelItems.get(i).getItem().getItemValors().get(k).getCodemp());
								itemEstoqueDeposito.setItem(itemRel);
								itemEstoqueDeposito.setCheckDelete(Util.checkDeleteCreate());
								itemEstoqueDeposito.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
								itemEstoqueDeposito.setLastMovto(ts_now);
								itemEstoqueDeposito.setUtctag(utctag);
								itemEstoqueDeposito.setDepositoEstoque(listDepositos.get(j));
								itemEstoqueDepositos.add(itemEstoqueDeposito);
							}

						}
						itemRel.setItemEstoqueDeposito(itemEstoqueDepositos);

					}

					itemFornecedor = new ItemFornecedor();
					itemFornecedor.setCheckDelete(Util.checkDeleteCreate());
					itemFornecedor.setRecordNo(0);
					itemFornecedor.setLastMovto(ts_now);
					itemFornecedor.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
					itemFornecedor.setCodemp(DadosGlobais.empresaLogada.getCodemp());
					itemFornecedor.setUtctag(utctag);
					itemFornecedor.setFlagAtivo(1);
					itemFornecedor.setFornecedor(fornecedor);
					itemFornecedor.setItem(itemRel);
					itemFornecedor.setCodItemFornecedor(Integer.parseInt(listRelItems.get(i).getCodItemFornecedor()));
					//					em.persist(itemFornecedor);

					//itemRel.setItemValors(itemValors);
				}else{
					itemRel = listRelItems.get(i).getItem();
					itemNovo = false;
				}				
				//ITEM NOVO = FALSE
				if(!listRelItems.get(i).getCodBarRelProd().equals("") && !itemNovo){//
					//VALIDAR SE O CODIGO BARRAS NÃO EXISTA NO BANCO
					ItemDAO iten = new ItemDAO();					
					ItemCodBar validaCodBar = (ItemCodBar) iten.getCodBarById(listRelItems.get(i).getCodBarRelProd()).getObjeto();
					if(validaCodBar.getItem() ==null){
						cdb.setCodemp(DadosGlobais.empresaLogada.getCodemp());
						cdb.setCheckDelete(Util.checkDeleteCreate());
						cdb.setFlagAtivo(1);
						cdb.setCodBarras(listRelItems.get(i).getCodBarRelProd());
						cdb.setQtdEmbalagem(BigDecimal.valueOf(1.00));
						cdb.setLastMovto(ts_now);
						cdb.setUtctag(utctag);
						cdb.setFlagCodbarPrincipal(0);
						cdb.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
						cdb.setItem(itemRel);
						itemCodbars.add(cdb);						
						itemRel.setItemCodBars(itemCodbars);
						em.persist(cdb);
					}



				}

				//TABELA ITEM_FORNECEDOR
				if(listRelItems.get(i).getItemFornecedor() == null && !itemNovo){
					itemFornecedor = new ItemFornecedor();
					itemFornecedor.setCheckDelete(Util.checkDeleteCreate());
					itemFornecedor.setRecordNo(0);
					itemFornecedor.setLastMovto(ts_now);
					itemFornecedor.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
					itemFornecedor.setCodemp(DadosGlobais.empresaLogada.getCodemp());
					itemFornecedor.setUtctag(utctag);
					itemFornecedor.setFlagAtivo(1);
					itemFornecedor.setFornecedor(fornecedor);
					itemFornecedor.setItem(itemRel);
					itemFornecedor.setCodItemFornecedor(Integer.parseInt(listRelItems.get(i).getCodItemFornecedor()));
					em.persist(itemFornecedor);
				}	

				if(itemNovo){
					System.out.println("Unidade de Emb "+itemRel.getUnidadeEmb());
					em.persist(itemRel);
					em.persist(itemFornecedor);
				}else{					
					em.merge(itemRel);
					//					ItemPK itemPK = new ItemPK();
					//					itemPK.setCodigo(itemRel.getCodigo());
					//					itemPK.setCheckDelete(itemRel.getCheckDelete());
					//					Item iv = em.find(Item.class, itemPK);
					//					iv.setDescricao("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
					//					JDOHelper.makeDirty(iv, "descricao");
				}

				detProdutos.setItem(itemRel);			
				produtos.add(detProdutos);
			}

			nfEntradaCab.setDetProdutos(produtos);	

			//FACTURAMENTO NFe
			NfEntradaFat faturamento = null;
			List<NfEntradaFat> faturamentos = new ArrayList<NfEntradaFat>();
			int incremento = 0;
			for(Dup dup : tNFeCab.getInfNFe().getCobr().getDup()){

				faturamento = (NfEntradaFat) Util.class.newInstance().initializeAttribClass(new NfEntradaFat());

				faturamento.setCheckDelete(Util.checkDeleteCreate());
				faturamento.setLastMovto(ts_now);
				faturamento.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
				faturamento.setCodemp(DadosGlobais.empresaLogada.getCodemp());
				faturamento.setUtctag(utctag);
				faturamento.setFlagAtivo(1);

				faturamento.setNfEntradaCab(nfEntradaCab);
				faturamento.setCobrFatNfat(Integer.parseInt(tNFeCab.getInfNFe().getCobr().getFat().getNFat()));				
				faturamento.setCobrFatVdesc(tNFeCab.getInfNFe().getCobr().getFat()!=null && tNFeCab.getInfNFe().getCobr().getFat().getVDesc()!=null ? BigDecimal.valueOf(Double.parseDouble(tNFeCab.getInfNFe().getCobr().getFat().getVDesc())) : BigDecimal.valueOf(Double.parseDouble("0.00")));
				faturamento.setCobrFatVliq(tNFeCab.getInfNFe().getCobr().getFat()!=null && tNFeCab.getInfNFe().getCobr().getFat().getVLiq()!=null ? BigDecimal.valueOf(Double.parseDouble(tNFeCab.getInfNFe().getCobr().getFat().getVLiq())) : BigDecimal.valueOf(Double.parseDouble("0.00")));
				faturamento.setCobrFatVorig(tNFeCab.getInfNFe().getCobr().getFat()!=null && tNFeCab.getInfNFe().getCobr().getFat().getVOrig()!=null ? BigDecimal.valueOf(Double.parseDouble(tNFeCab.getInfNFe().getCobr().getFat().getVOrig())) : BigDecimal.valueOf(Double.parseDouble("0.00")));

				String dataVenc = dup.getDVenc();
				int month = Integer.parseInt(dataVenc.substring(5, 7));
				int day = Integer.parseInt(dataVenc.substring(8, 10));
				int year = Integer.parseInt(dataVenc.substring(0, 4));	
				LocalDateTime data = LocalDateTime.of(year, month, day, 00, 00, 00);

				//GERAR NO DE PARCELAS
				String result = dup.getNDup();
				result = result.replaceAll("[^0-9 ]", "").replace(" ", "");

				faturamento.setNoParcela(Integer.parseInt(result));
				faturamento.setCobrDupDvenc(LocalDateTime.of(year, month, day, 00, 00, 00));				
				faturamento.setCobrDupNdup(dup.getNDup());
				faturamento.setCobrDupVdup(BigDecimal.valueOf(Double.parseDouble(dup.getVDup())));				
				faturamentos.add(faturamento);
			}

			nfEntradaCab.setDetFaturamento(faturamentos);

			em.persist(nfEntradaCab);
			em.persist(xmlEntrada);


			//movimentacao de estque
			for(RelacaoItems rel : listRelItems){
				
				ItemMovimentacao itemMovimentacao = new ItemMovimentacao();
				itemMovimentacao.setCheckDelete(Util.checkDeleteCreate());
				itemMovimentacao.setLastMovto(ts_now);
				itemMovimentacao.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
				itemMovimentacao.setCodemp(DadosGlobais.empresaLogada.getCodemp());
				itemMovimentacao.setUtctag(utctag);
				itemMovimentacao.setFlagAtivo(1);
				
				itemMovimentacao.setDtMovimento(ts_now);
				itemMovimentacao.setHistorico(fornecedor.getCodigo()+" - "+fornecedor.getRazao());
				itemMovimentacao.setItem(rel.getItem());
				itemMovimentacao.setNoDocto(nfEntradaCab.getNoDocto());
				itemMovimentacao.setNoDoctoFk(nfEntradaCab.getCheckDelete());
				itemMovimentacao.setQtdMovimentado(BigDecimal.valueOf(Double.parseDouble(rel.getQtdReposicaoRelProdProperty().get())));
				itemMovimentacao.setFlagModulo(1);
				itemMovimentacao.setTipoMovimentacao("E");
				itemMovimentacao.setVlrBruto(BigDecimal.valueOf(Double.parseDouble(rel.getVlrUndBrutoRelProd())));	
				
				em.persist(itemMovimentacao);
			}	
			
			//Update no itemValor qtdAtual, qtdDisponivel, qtdCentroDeCusto1

			//Update no itemEstoqueDep qtdAtual, qtdDisponivel, qtdCentroDeCusto1
			try{
				for(RelacaoItems rel : listRelItems){

					String queryItemValor = "UPDATE ItemValor iv SET "
							+ "iv.lastMovto = :lastMovto, "
							+ "iv.lastCoduser = :lastCoduser, "
							+ "iv.qtdAtual = iv.qtdAtual + :qtdReposicao, "
							+ "iv.qtdDisponivel = iv.qtdDisponivel + :qtdReposicao, "
							+ "iv.qtdCcusto1 = iv.qtdCcusto1 + :qtdReposicao, "
							+ "iv.repDocumento = :repDocumento, "
							+ "iv.repQuantidade = :repQuantidade, "
							+ "iv.repData = :repData, "
							+ "iv.repCodFornecedor = :repCodFornecedor, "
							+ "iv.repFornecedor = :repFornecedor, "
							+ "iv.repVlrCusto = :repVlrCusto "
							+ "WHERE iv.codemp = :codemp AND iv.item = :item";				

					em.createQuery(queryItemValor)
					.setParameter("lastMovto", ts_now)
					.setParameter("lastCoduser", DadosGlobais.usuarioLogado.getCodigo())
					.setParameter("qtdReposicao", BigDecimal.valueOf(Double.parseDouble(rel.getQtdReposicaoRelProdProperty().get())))
					.setParameter("repDocumento", nfEntradaCab.getNoDocto())
					.setParameter("repQuantidade", BigDecimal.valueOf(Double.parseDouble(rel.getQtdReposicaoRelProdProperty().get())))
					.setParameter("repData", nfEntradaCab.getDtSaidaChegada())
					.setParameter("repCodFornecedor", fornecedor.getCodigo())
					.setParameter("repFornecedor", fornecedor.getRazao())
					.setParameter("repVlrCusto", BigDecimal.valueOf(Double.parseDouble(rel.getVlrUndBrutoRelProd())))
					.setParameter("codemp", DadosGlobais.empresaLogada.getCodigo())
					.setParameter("item", rel.getItem())
					.executeUpdate();	

					String queryItemEstoqueDeposito = "UPDATE ItemEstoqueDeposito it SET "
							+ "it.lastMovto = :lastMovto, "
							+ "it.lastCoduser = :lastCoduser, "
							+ "it.qtdAtual = it.qtdAtual+ :qtdReposicao, "
							+ "it.qtdDisponivel = it.qtdDisponivel + :qtdReposicao, "
							+ "it.qtdCcusto1 = it.qtdCcusto1 + :qtdReposicao "
							+ "WHERE it.codemp = :codemp AND it.item = :item AND it.depositoEstoque = :deposito";
					em.createQuery(queryItemEstoqueDeposito)
					.setParameter("lastMovto", ts_now)
					.setParameter("lastCoduser", DadosGlobais.usuarioLogado.getCodigo())
					.setParameter("qtdReposicao", BigDecimal.valueOf(Double.parseDouble(rel.getQtdReposicaoRelProdProperty().get())))
					.setParameter("codemp", DadosGlobais.empresaLogada.getCodigo())
					.setParameter("item", rel.getItem())
					.setParameter("deposito", rel.getSubEstoqueDestRelProd()).executeUpdate();

				}
			}catch(Exception e){
				e.printStackTrace();
				em.getTransaction().rollback();
			}


			em.getTransaction().commit();
			logRet.setStatus(EnumLogRetornoStatus.SUCESSO);
			logRet.setMsg(EnumLogRetornoStatus.SUCESSO.name());

		}catch(Exception e){			
			em.getTransaction().rollback();
			logRet.setStatus(EnumLogRetornoStatus.ERRO);
			logRet.setMsg(e.getMessage());	
			e.printStackTrace();
		}finally{		
			em.close();
		}	

		//		logRet.setLastRecord(codigo);
		return logRet;
	}

	public LogRetorno getNFe(Fornecedor fornecedor, String chaveNFe){
		NfEntradaCab nfc = null;
		LogRetorno log = new LogRetorno();
		try{

			em = ConnectionHib.emf.createEntityManager();
			nfc = (NfEntradaCab)em.createNamedQuery("getByNoDoctoFornecedor")
					.setParameter("noDocto", Integer.parseInt(chaveNFe))
					.setParameter("fornecedor", fornecedor)
					.getSingleResult();
			log.setStatus(EnumLogRetornoStatus.SUCESSO);
			log.setMsg(EnumLogRetornoStatus.SUCESSO.name());
			log.setObjeto(nfc);

		}catch(Exception e){
			log.setStatus(EnumLogRetornoStatus.ERRO);
			log.setMsg(e.getMessage());	
		}finally {
			em.close();
		}		

		return log;

	}


}
