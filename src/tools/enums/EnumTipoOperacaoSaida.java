package tools.enums;

import application.DadosGlobais;

public enum EnumTipoOperacaoSaida {
	
	NOTA_FISCAL_SAIDA(0, DadosGlobais.resourceBundle.getString("notaFiscalSaida")),
	PEDIDO_VENDA(1, DadosGlobais.resourceBundle.getString("pedidoDeVenda")),
	BALANCETE(2,DadosGlobais.resourceBundle.getString("balancete"));
	
	
	public Integer index;
	public String  text;
	
	EnumTipoOperacaoSaida(Integer valor, String text ) {
		this.index = valor;
		this.text = text;
	}
}
