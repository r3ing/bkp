package tools.enums;

import application.DadosGlobais;

public enum EnumStatusContasReceber {
	
	STATUS_ABERTO(0, DadosGlobais.resourceBundle.getString("contasReceberController.txtStatus_filt0")),
	RENEGOCIADO(1, DadosGlobais.resourceBundle.getString("contasReceberController.txtStatus_filt1")),
	BAIXADO_PARCIAL(2, DadosGlobais.resourceBundle.getString("contasReceberController.txtStatus_filt2")),
	BAIXADO(3, DadosGlobais.resourceBundle.getString("contasReceberController.txtStatus_filt3"));

	
	public Integer index;
	public String  text;
	
	EnumStatusContasReceber(Integer valor, String text ) {
		this.index = valor;
		this.text = text;
	}	
	
}
