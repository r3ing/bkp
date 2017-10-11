package tools.enums;

import application.DadosGlobais;

public enum EnumTipoOperacaoFinaceiro {
	
	CONTAS_A_RECEBER(0, DadosGlobais.resourceBundle.getString("operacaoFinanceiroController.cboxTipoOperacao0")),
	CONTAS_A_PAGAR(1, DadosGlobais.resourceBundle.getString("operacaoFinanceiroController.cboxTipoOperacao1")),
	MOVIMENTACAO_BANCARIA(2,DadosGlobais.resourceBundle.getString("operacaoFinanceiroController.cboxTipoOperacao2"));
	
	
	public Integer index;
	public String  text;
	
	EnumTipoOperacaoFinaceiro(Integer valor, String text ) {
		this.index = valor;
		this.text = text;
	}
}
