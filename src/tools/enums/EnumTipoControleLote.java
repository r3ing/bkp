package tools.enums;

import application.DadosGlobais;

public enum EnumTipoControleLote {

	DATA(0, DadosGlobais.resourceBundle.getString("ajustesSistemaController.cboxCpaTipolotedefault0")), NUM_LOTE(1,DadosGlobais.resourceBundle.getString("ajustesSistemaController.cboxCpaTipolotedefault1")),
	DATA_NUM_LOTE(2, DadosGlobais.resourceBundle.getString("ajustesSistemaController.cboxCpaTipolotedefault2")), VACINAS(3, DadosGlobais.resourceBundle.getString("ajustesSistemaController.cboxCpaTipolotedefault3")),
	SEMENTES(4, DadosGlobais.resourceBundle.getString("ajustesSistemaController.cboxCpaTipolotedefault4")), VEICULOS(5, DadosGlobais.resourceBundle.getString("ajustesSistemaController.cboxCpaTipolotedefault5")), 
	ADEFINIR(6, DadosGlobais.resourceBundle.getString("ajustesSistemaController.cboxCpaTipolotedefault6"));

	public Integer index;
	public String text;

	EnumTipoControleLote(Integer valor, String text) {
		index = valor;
		this.text = text;
	}
}
