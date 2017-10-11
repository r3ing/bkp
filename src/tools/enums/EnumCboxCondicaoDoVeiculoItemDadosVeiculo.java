package tools.enums;

import application.DadosGlobais;

public enum EnumCboxCondicaoDoVeiculoItemDadosVeiculo 
{
	DATA1(1, DadosGlobais.resourceBundle.getString("itemController.cboxCondicaoDoVeiculo1")),
	DATA2(2, DadosGlobais.resourceBundle.getString("itemController.cboxCondicaoDoVeiculo2")),
	DATA3(3, DadosGlobais.resourceBundle.getString("itemController.cboxCondicaoDoVeiculo3"));

	public Integer index;
	public String text;

	EnumCboxCondicaoDoVeiculoItemDadosVeiculo(Integer index, String text) 
	{
		this.index = index;
		this.text = text;
	}
}
