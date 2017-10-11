package tools.enums;

import application.DadosGlobais;

public enum EnumCboxTipoPinturaItemDadosVeiculo 
{	
	DATA1('S', DadosGlobais.resourceBundle.getString("itemController.cboxTipoPintura1")),
	DATA2('M', DadosGlobais.resourceBundle.getString("itemController.cboxTipoPintura2")),
	DATA3('P', DadosGlobais.resourceBundle.getString("itemController.cboxTipoPintura3"));

	public char index;
	public String text;

	EnumCboxTipoPinturaItemDadosVeiculo(char index, String text) 
	{
		this.index = index;
		this.text = text;
	}
}
