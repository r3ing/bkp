package tools.enums;

import application.DadosGlobais;

public enum EnumCboxTabela 
{
	DATA1(1, DadosGlobais.resourceBundle.getString("itemController.cboxTipoArquivo1")),
	DATA2(2, DadosGlobais.resourceBundle.getString("itemController.cboxTipoArquivo2"));

	public Integer index;
	public String text;

	EnumCboxTabela(Integer index, String text) 
	{
		this.index = index;
		this.text = text;
	}
}
