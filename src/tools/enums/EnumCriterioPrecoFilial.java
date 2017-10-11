package tools.enums;

import application.DadosGlobais;

public enum EnumCriterioPrecoFilial {

	CADA_EMPRESA_TEM_SEU_PRECO(DadosGlobais.resourceBundle.getString("ajustesSistemaController.rdbCpaCriterioprecosfiliais0"),0),
	PRECO_CUSTO_IGUAIS(DadosGlobais.resourceBundle.getString("ajustesSistemaController.rdbCpaCriterioprecosfiliais1"),1),
	PRECO_CUSTO_E_VENDA_IGUAIS(DadosGlobais.resourceBundle.getString("ajustesSistemaController.rdbCpaCriterioprecosfiliais2"),2),
	PRECO_CUSTO_E_VENDA_E_PROMOCAO_IGUAIS(DadosGlobais.resourceBundle.getString("ajustesSistemaController.rdbCpaCriterioprecosfiliais3"),3);
	
	
public String descCriterio;
public Integer criterioPreco;
	
	EnumCriterioPrecoFilial(String descCriterio,Integer criterioPreco ) {
			this.descCriterio = descCriterio;
			this.criterioPreco = criterioPreco;
		}
}
