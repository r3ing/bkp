package tools.enums;

public enum EnumCompartilhamento {
	
	
	//MODULO COMPRAS
	ITENS("C100"), UNIDADES("C101"), GRUPOS("C102"), SUBGRUPOS("C103"), FABRICANTES("C104"), DEPARTAMENTOS("C105"), GRADE_DE_PRODUTOS("C106"),
	COR_ESTILOS("C107"), FORNECEDORES("C108"), TRIBUTACAO("C109"), APLICACOES("C110"), FAMILIAS_DE_PRECOS("C111"), BOX_LOCAL_ESTOQUE("C112"),
	TABELA_LC_SERVICOS("C113"), TABELA_NCM("C114"), OPERACOES_DE_ENTRADA("C900"), 
	
	//MODULO VENDAS
	PAIS("V103"), CLIENTES("V100"), CLIENTE_ENDERECO("V108"), CIDADES("V101"), UF("V102"), ROTAS("V104"), CONVENIO("V105"), SEGMENTOS("V107"), RAMO_ATIVIDADE("V105"), REGIAO("V106"),
	PLANO_DE_PAGAMENTO("V106"), MENSAGEM_NOTA_FISCAL("V107"),
	
	//MODULO FINANCIERO
	SECAO("F101"), MOEDA("F106"), PORTADOR("F102"), CENTRO_DE_CUSTO("F103"), PLANO_DE_CONTA("F104"), CAIXA_BANCO("F105"),CONTAS_RECEBER("F106"),
	
	//MODULO RECURSOS HUMANOA
	FUNCIONARIO("H101"),
	
	//MODULO CONFIGURAÇÕES 

	USUARIOS("U100"), DEPOSITO_ESTOQUES("U600"), OPERACOES_DE_SAIDA("U901"), LAYOUT_ARQUIVO_CAB("U600"), OPERACOES_FINANCEIRO("U902");

	
	public String idMenu;
		
	EnumCompartilhamento(String valor) {
		idMenu = valor;
		}

}
