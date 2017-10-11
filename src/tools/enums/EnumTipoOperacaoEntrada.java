package tools.enums;

public enum EnumTipoOperacaoEntrada {

	NOTA_FISCAL_ENTRADA(1, "Nota Fiscal Entrada"),
	PEDIDO_COMPRA(2, "Pedido de Compra"), 
	BALANCETE(3,"Balancete"), 
	SOLICITACAO_COMPRA(4, "Solicitação de Compra");
	
	public Integer index;
	public String  text;
	
	EnumTipoOperacaoEntrada(Integer valor, String text ) {
		this.index = valor;
		this.text = text;
	}
}
