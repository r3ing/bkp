package controllers.compras;

import java.lang.reflect.Field;

import javax.xml.bind.JAXBException;

import br.com.samuelweb.nfe.exception.NfeException;
import br.com.samuelweb.nfe.util.XmlUtil;
import br.inf.portalfiscal.nfe.schema.nfe.TNFe;
import br.inf.portalfiscal.nfe.schema.nfe.TNfeProc;

public class Consola {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		DETALHES Prod
		Field[] tnfeDetProd = TNFe.InfNFe.Det.Prod.class.getDeclaredFields();
    	Field[] tnfeDetProdArm = TNFe.InfNFe.Det.Prod.Arma.class.getDeclaredFields();
//    	Field[] tnfeDetImpost = TNFe.InfNFe.Det.Imposto.ICMS.;
//    	Field[] tnfeDetImpost = TNFe.InfNFe.Det.Imposto.COFINS.COFINSAliq.class.getDeclaredFields();
//    	Field[] tnfeDetImpDev = TNFe.InfNFe.Det.ImpostoDevol.class.getDeclaredFields();
    	
//    	System.out.println("TNFe.InfNFe.Det.Imposto.class cantidad: "+tnfeDetImpost.length);    	
//    	for(int i=0; i<tnfeDetImpost.length; i++){
//    		System.out.println(i+".- "+tnfeDetImpost[i].getName()+"   "+tnfeDetImpost[i].getType());
//    	}
//    	
    	
    	TNfeProc xml = null;
		try {
			
			try {
				xml = XmlUtil.xmlToObject(XmlUtil.leXml("C:/Eptus/System.xml/35160843919968000129550000003797801014231141.xml"), TNfeProc.class);
//				for(int i=0; i <xml.getInfNFe().getDet().size(); i++){
					for(int y=0; y<xml.getNFe().getInfNFe().getDet().size(); y++)
						System.out.println(xml.getNFe().getInfNFe().getDet().get(y).getNItem());
//				}
//				System.out.println(xml.getNFe().getInfNFe().getDet().get);
			} catch (NfeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
