package tools.enums;

import application.DadosGlobais;

public enum EnumCboxTipoVeiculoItemDadosVeiculo 
{		
	DATA1(6, DadosGlobais.resourceBundle.getString("itemController.cboxTipoVeiculo1")),
	DATA2(14, DadosGlobais.resourceBundle.getString("itemController.cboxTipoVeiculo2")),
	DATA3(13, DadosGlobais.resourceBundle.getString("itemController.cboxTipoVeiculo3")),
	DATA4(24, DadosGlobais.resourceBundle.getString("itemController.cboxTipoVeiculo4")), 
	DATA5(2, DadosGlobais.resourceBundle.getString("itemController.cboxTipoVeiculo5")), 
	DATA6(22, DadosGlobais.resourceBundle.getString("itemController.cboxTipoVeiculo6")),	
	DATA7(7, DadosGlobais.resourceBundle.getString("itemController.cboxTipoVeiculo7")),
	DATA8(23, DadosGlobais.resourceBundle.getString("itemController.cboxTipoVeiculo8")),
	DATA9(4, DadosGlobais.resourceBundle.getString("itemController.cboxTipoVeiculo9")),
	DATA10(3, DadosGlobais.resourceBundle.getString("itemController.cboxTipoVeiculo10")),
	DATA11(8, DadosGlobais.resourceBundle.getString("itemController.cboxTipoVeiculo11")), 
	DATA12(10, DadosGlobais.resourceBundle.getString("itemController.cboxTipoVeiculo12")),
	DATA13(5, DadosGlobais.resourceBundle.getString("itemController.cboxTipoVeiculo13")),
	DATA14(17, DadosGlobais.resourceBundle.getString("itemController.cboxTipoVeiculo14"));

	public Integer index;
	public String text;

	EnumCboxTipoVeiculoItemDadosVeiculo(Integer index, String text) 
	{
		this.index = index;
		this.text = text;
	}
}
