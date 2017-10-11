package tools.enums;

import application.DadosGlobais;

public enum EnumCboxProceProposVendaItemAdicionais 
{		
	DATA1(0, DadosGlobais.resourceBundle.getString("itemController.cboxProceProposVenda1")),
	DATA2(1, DadosGlobais.resourceBundle.getString("itemController.cboxProceProposVenda2")),
	DATA3(2, DadosGlobais.resourceBundle.getString("itemController.cboxProceProposVenda3"));

	public Integer index;
	public String text;

	EnumCboxProceProposVendaItemAdicionais(Integer index, String text) 
	{
		this.index = index;
		this.text = text;
	}
}
