package tools.enums;

import application.DadosGlobais;

public enum EnumCboxCorItemDadosVeiculo 
{	
	DATA1(1, DadosGlobais.resourceBundle.getString("itemController.cboxCor1")),
	DATA2(2, DadosGlobais.resourceBundle.getString("itemController.cboxCor2")),
	DATA3(3, DadosGlobais.resourceBundle.getString("itemController.cboxCor3")),
	DATA4(4, DadosGlobais.resourceBundle.getString("itemController.cboxCor4")), 
	DATA5(5, DadosGlobais.resourceBundle.getString("itemController.cboxCor5")), 
	DATA6(6, DadosGlobais.resourceBundle.getString("itemController.cboxCor6")),	
	DATA7(7, DadosGlobais.resourceBundle.getString("itemController.cboxCor7")),
	DATA8(8, DadosGlobais.resourceBundle.getString("itemController.cboxCor8")),
	DATA9(9, DadosGlobais.resourceBundle.getString("itemController.cboxCor9")),
	DATA10(10, DadosGlobais.resourceBundle.getString("itemController.cboxCor10")),
	DATA11(11, DadosGlobais.resourceBundle.getString("itemController.cboxCor11")), 
	DATA12(12, DadosGlobais.resourceBundle.getString("itemController.cboxCor12")), 
	DATA13(13, DadosGlobais.resourceBundle.getString("itemController.cboxCor13")),	
	DATA14(14, DadosGlobais.resourceBundle.getString("itemController.cboxCor14")),
	DATA15(15, DadosGlobais.resourceBundle.getString("itemController.cboxCor15")),
	DATA16(16, DadosGlobais.resourceBundle.getString("itemController.cboxCor16"));

	public Integer index;
	public String text;

	EnumCboxCorItemDadosVeiculo(Integer index, String text) 
	{
		this.index = index;
		this.text = text;
	}
}
