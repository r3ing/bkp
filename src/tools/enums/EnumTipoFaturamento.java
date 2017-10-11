package tools.enums;

import application.DadosGlobais;

public enum EnumTipoFaturamento {

	VENDAS_SOMENTE_A_VISTA(0, DadosGlobais.resourceBundle.getString("clienteController.cboxTipoFaturamento0")),
	CHEQUES_PRE_DATADOS(1, DadosGlobais.resourceBundle.getString("clienteController.cboxTipoFaturamento1")),
	DUPLICATAS_CARNES(2, DadosGlobais.resourceBundle.getString("clienteController.cboxTipoFaturamento2")),
	CARTAO_DE_CREDITO(3, DadosGlobais.resourceBundle.getString("clienteController.cboxTipoFaturamento3")),
	TODOS(4, DadosGlobais.resourceBundle.getString("clienteController.cboxTipoFaturamento4"));
	
	public Integer index;
	public String  text;
	
	EnumTipoFaturamento(Integer valor, String text ) {
		this.index = valor;
		this.text = text;
	}	
	
}
