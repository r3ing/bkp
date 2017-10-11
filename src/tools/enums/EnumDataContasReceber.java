package tools.enums;

import application.DadosGlobais;

public enum EnumDataContasReceber {
	
	EMISSAO(0, DadosGlobais.resourceBundle.getString("contasReceberController.cboxData_filt0")),
	VENCIMENTO(1, DadosGlobais.resourceBundle.getString("contasReceberController.cboxData_filt1")),
	LANCAMENTO(2, DadosGlobais.resourceBundle.getString("contasReceberController.cboxData_filt2")),
	PAGAMENTO(3, DadosGlobais.resourceBundle.getString("contasReceberController.cboxData_filt3"));

	
	public Integer index;
	public String  text;
	
	EnumDataContasReceber(Integer valor, String text ) {
		this.index = valor;
		this.text = text;
	}	
	
}
