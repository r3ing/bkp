package tools.enums;

import application.DadosGlobais;

public enum EnumCboxTipoOperacao 
{
	DATA1(1, DadosGlobais.resourceBundle.getString("layoutsArquivoController.cboxTipoOperacao1")),
	DATA2(2, DadosGlobais.resourceBundle.getString("layoutsArquivoController.cboxTipoOperacao2"));

	public Integer index;
	public String text;

	EnumCboxTipoOperacao(Integer index, String text) 
	{
		this.index = index;
		this.text = text;
	}
}
