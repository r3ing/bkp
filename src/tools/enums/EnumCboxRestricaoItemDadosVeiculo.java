package tools.enums;

import application.DadosGlobais;

public enum EnumCboxRestricaoItemDadosVeiculo 
{		
	DATA1(3, DadosGlobais.resourceBundle.getString("itemController.cboxRestricao1")),
	DATA2(1, DadosGlobais.resourceBundle.getString("itemController.cboxRestricao2")),
	DATA3(2, DadosGlobais.resourceBundle.getString("itemController.cboxRestricao3")),
	DATA4(4, DadosGlobais.resourceBundle.getString("itemController.cboxRestricao4")), 
	DATA5(7, DadosGlobais.resourceBundle.getString("itemController.cboxRestricao5")), 
	DATA6(6, DadosGlobais.resourceBundle.getString("itemController.cboxRestricao6"));

	public Integer index;
	public String text;

	EnumCboxRestricaoItemDadosVeiculo(Integer index, String text) 
	{
		this.index = index;
		this.text = text;
	}
}
