package tools.enums;

import application.DadosGlobais;

public enum EnumCboxSeparador 
{
	DATA1(1, DadosGlobais.resourceBundle.getString("layoutsArquivoController.cboxSeparador1")),
	DATA2(2, DadosGlobais.resourceBundle.getString("layoutsArquivoController.cboxSeparador2"));

	public Integer index;
	public String text;

	EnumCboxSeparador(Integer index, String text) 
	{
		this.index = index;
		this.text = text;
	}
}
