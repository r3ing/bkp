package tools.enums;

import application.DadosGlobais;

public enum EnumTipoFreteModuloVendas {
	
	EPTUS(0, DadosGlobais.resourceBundle.getString("ajustesSistemaController.cboxVdaFreteTipodefault0")),
	NUMFABRICANTE(1,DadosGlobais.resourceBundle.getString("ajustesSistemaController.cboxVdaFreteTipodefault1")),
	CODBARRAS(2,DadosGlobais.resourceBundle.getString("ajustesSistemaController.cboxVdaFreteTipodefault2")),
	EPTUS_GRADES(3,DadosGlobais.resourceBundle.getString("ajustesSistemaController.cboxVdaFreteTipodefault3"));
	
	public Integer index;
	public String  text;
	
	EnumTipoFreteModuloVendas(Integer valor, String text ) {
		this.index = valor;
		this.text = text;
		}
}
