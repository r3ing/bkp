package tools.enums;

import application.DadosGlobais;

public enum EnumConsumidorFinal {

	SIM(0, DadosGlobais.resourceBundle.getString("clienteController.cboxConsumidorFinal0")), 
	NAO(1, DadosGlobais.resourceBundle.getString("clienteController.cboxConsumidorFinal1")),
	A_DEFINIR(2, DadosGlobais.resourceBundle.getString("clienteController.cboxConsumidorFinal2"));
	
	public Integer index;
	public String text;

	EnumConsumidorFinal(Integer valor, String text) {
		index = valor;
		this.text = text;
	}
}
