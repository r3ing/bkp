package tools.enums;

import application.DadosGlobais;

public enum EnumCboxEspVeiculoItemDadosVeiculo 
{		 
	DATA1(2, DadosGlobais.resourceBundle.getString("itemController.cboxEspVeiculo1")),
	DATA2(4, DadosGlobais.resourceBundle.getString("itemController.cboxEspVeiculo2")),
	DATA3(6, DadosGlobais.resourceBundle.getString("itemController.cboxEspVeiculo3")),
	DATA4(3, DadosGlobais.resourceBundle.getString("itemController.cboxEspVeiculo4")), 
	DATA5(1, DadosGlobais.resourceBundle.getString("itemController.cboxEspVeiculo5")), 
	DATA6(5, DadosGlobais.resourceBundle.getString("itemController.cboxEspVeiculo6"));

	public Integer index;
	public String text;

	EnumCboxEspVeiculoItemDadosVeiculo(Integer index, String text) 
	{
		this.index = index;
		this.text = text;
	}
}
