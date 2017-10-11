package tools.enums;

import application.DadosGlobais;

public enum EnumTipoConsultaProdutosDefaultBuscaItens {
	
	CODIGO_EPTUS(0, DadosGlobais.resourceBundle.getString("ajustesSistemaController.cboxVdaFiltrodefaultBuscaitens0")),
	DESCRICAO(1,DadosGlobais.resourceBundle.getString("ajustesSistemaController.cboxVdaFiltrodefaultBuscaitens1")),
	NOFABRICANTE(2,DadosGlobais.resourceBundle.getString("ajustesSistemaController.cboxVdaFiltrodefaultBuscaitens2")),
	NOORIGINAL(3,DadosGlobais.resourceBundle.getString("ajustesSistemaController.cboxVdaFiltrodefaultBuscaitens3")),
	GRUPO(4,DadosGlobais.resourceBundle.getString("ajustesSistemaController.cboxVdaFiltrodefaultBuscaitens4")),
	SUB_GRUPO(5,DadosGlobais.resourceBundle.getString("ajustesSistemaController.cboxVdaFiltrodefaultBuscaitens5")), 
	FABRICANTE(6,DadosGlobais.resourceBundle.getString("ajustesSistemaController.cboxVdaFiltrodefaultBuscaitens6")), 
	APLICACAO(7,DadosGlobais.resourceBundle.getString("ajustesSistemaController.cboxVdaFiltrodefaultBuscaitens7")),
	OBSERVACOES(8,DadosGlobais.resourceBundle.getString("ajustesSistemaController.cboxVdaFiltrodefaultBuscaitens8"));
	
	public Integer index;
	public String  text;
	
//	EnumTipoConsultaProdutosDefaultBuscaItens(Integer valor) {
//		index = valor;
//		}
	
	EnumTipoConsultaProdutosDefaultBuscaItens(Integer valor, String text ) {
		index = valor;
		this.text = text;
		}
}
