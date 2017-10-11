package tools.enums;

import application.DadosGlobais;

public enum EnumTipoPrazo {

	VARIAVEL(0, DadosGlobais.resourceBundle.getString("planoPagamentoController.cboxTipoPrazo0")), COMBINADO(1, DadosGlobais.resourceBundle.getString("planoPagamentoController.cboxTipoPrazo1")),
	ALEATORIO(2, DadosGlobais.resourceBundle.getString("planoPagamentoController.cboxTipoPrazo2")), DATA_INICIAL(3, DadosGlobais.resourceBundle.getString("planoPagamentoController.cboxTipoPrazo3"));
	public Integer index;
	public String text;

	EnumTipoPrazo(Integer valor, String text) {
		index = valor;
		this.text = text;
	}
}
