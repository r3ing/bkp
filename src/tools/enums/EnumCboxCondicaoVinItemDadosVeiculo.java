package tools.enums;

import application.DadosGlobais;

public enum EnumCboxCondicaoVinItemDadosVeiculo 
{		
	DATA1('R', DadosGlobais.resourceBundle.getString("itemController.cboxCondicaoVin1")),
	DATA2('N', DadosGlobais.resourceBundle.getString("itemController.cboxCondicaoVin2"));

	public char index;
	public String text;

	EnumCboxCondicaoVinItemDadosVeiculo(char index, String text) 
	{
		this.index = index;
		this.text = text;
	}
}
