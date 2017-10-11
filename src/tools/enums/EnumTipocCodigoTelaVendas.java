package tools.enums;

import application.DadosGlobais;

public enum EnumTipocCodigoTelaVendas {
	
	SIF(0, DadosGlobais.resourceBundle.getString("ajustesSistemaController.cboxVdaTipocodigoTeladevendas0")),
	FOB(1,DadosGlobais.resourceBundle.getString("ajustesSistemaController.cboxVdaTipocodigoTeladevendas1")),
	PTERCEIROS(2,DadosGlobais.resourceBundle.getString("ajustesSistemaController.cboxVdaTipocodigoTeladevendas2")),
	SEMFRETE(3,DadosGlobais.resourceBundle.getString("ajustesSistemaController.cboxVdaTipocodigoTeladevendas3"));
	
	public Integer index;
	public String  text;
	
//	EnumTipocCodigoTelaVendas(Integer valor) {
//		index = valor;
//		}
	
	EnumTipocCodigoTelaVendas(Integer valor, String text ) {
		index = valor;
		this.text = text;
		}
}
