package tools.enums;

import application.DadosGlobais;

public enum EnumCboxTipoCombustiveltemDadosVeiculo 
{		
	DATA1(1, DadosGlobais.resourceBundle.getString("itemController.cboxTipoCombustivel1")),
	DATA2(9, DadosGlobais.resourceBundle.getString("itemController.cboxTipoCombustivel2")),
	DATA3(3, DadosGlobais.resourceBundle.getString("itemController.cboxTipoCombustivel3")),
	DATA4(10, DadosGlobais.resourceBundle.getString("itemController.cboxTipoCombustivel4")), 
	DATA5(7, DadosGlobais.resourceBundle.getString("itemController.cboxTipoCombustivel5")), 
	DATA6(6, DadosGlobais.resourceBundle.getString("itemController.cboxTipoCombustivel6")),	
	DATA7(5, DadosGlobais.resourceBundle.getString("itemController.cboxTipoCombustivel7")),
	DATA8(4, DadosGlobais.resourceBundle.getString("itemController.cboxTipoCombustivel8")),
	DATA9(8, DadosGlobais.resourceBundle.getString("itemController.cboxTipoCombustivel9")),
	DATA10(2, DadosGlobais.resourceBundle.getString("itemController.cboxTipoCombustivel10")),
	DATA11(12, DadosGlobais.resourceBundle.getString("itemController.cboxTipoCombustivel11")), 
	DATA12(13, DadosGlobais.resourceBundle.getString("itemController.cboxTipoCombustivel12"));

	public Integer index;
	public String text;

	EnumCboxTipoCombustiveltemDadosVeiculo(Integer index, String text) 
	{
		this.index = index;
		this.text = text;
	}
}
