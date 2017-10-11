package tools.enums;

import application.DadosGlobais;

public enum EnumCboxTipoArquivo 
{
	DATA1(1, DadosGlobais.resourceBundle.getString("layoutsArquivoController.cboxTipoArquivo1")),
	DATA2(2, DadosGlobais.resourceBundle.getString("layoutsArquivoController.cboxTipoArquivo2"));

	public Integer index;
	public String text;

	EnumCboxTipoArquivo(Integer index, String text) 
	{
		this.index = index;
		this.text = text;
	}
}
