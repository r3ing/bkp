package tools.enums;

import controllers.configuracoes.TabelaAuxiliarCfopController;

public enum EnumNivelAcesso {
	
	
	// ------------- MODULO COMPRAS ----------------
	//MENU HEAD
	COMPRAS("mnHeaCompras"),
	//MENUS ROOT
	COMPRAS_CADASTRO("mnRotComprasCadastros"),
	COMPRAS_CONSULTAS("mnRotComprasConsultas"),
	COMPRAS_RELATORIOS("mnRotComprasRelatorios"),
	COMPRAS_OPERACOES("mnRotComprasOperacoes"),
	//MENUITEM ACOES
	ITENS("miCadItem"), UNIDADES("miCadUnidade"), GRUPOS("miCadGrupo"), SUBGRUPOS("miCadSubGrupo"), FABRICANTES("miCadFabricante"),
	DEPARTAMENTOS("miCadDepartamento"), GRADE_DE_PRODUTOS("miCadGrade"), COR_ESTILOS("miCadCorEstilo"), FORNECEDORES("miCadFornecedor"), 
	TRIBUTACAO("miCadTributacao"), APLICACOES("miCadAplicacao"), FAMILIAS_DE_PRECIFICACAO("miCadFamiliaPreco"), 
	BOX_LOCAL_ESTOQUE("miCadBoxLocalEstoque"),TABELA_LC_SERVICOS("miCadTabelaLcServico"), TABELA_NCM("miCadTabelaNCM"), 
	OPERACOES_DE_ENTRADA("miCadOperacaoEntrada"), 
	NOTAS_FISCAIS_ENTRADA("miOpeNotaFiscais"), SEGMENTOS("miCadSegmento"),
	// ------------- // FIM // MODULO COMPRAS ----------------
	
	// ------------- MODULO VENDAS ----------------
		//MENU HEAD
	VENDAS("mnHeaVendas"),
	
	
		//MENUS ROOT
	VENDAS_CADASTRO("mnRotVendasCadastros"),
	VENDAS_CONSULTAS("mnRotVendasConsultas"),
	VENDAS_RELATORIOS("mnRotVendasRelatorios"),
	VENDAS_OPERACOES("mnRotVendasOperacoes"),
		//MENUITEM ACOES
	PAIS("miCadPais"), UF("miCadUF"), CIDADES("miCadCidade"), ROTAS("miCadRotas"), RAMO_ATIVIDADE("miCadRamoAtividade"), REGIOES_CLIENTES("miCadRegioesClientes"),
	CONVENIO("miCadConvenio"), CLIENTES("miCadCliente"), PLANO_DE_PAGAMENTO("miCadPlanoPagamento"), MENSAGEM("miCadMensagem"), OPERACOES_DE_SAIDA("miCadOperacaoSaida"),
	OPERACOES_FINANCEIRO("miCadOperacaoFinanceiro"),
	// ------------- // FIM // MODULO VENDAS ----------------
	
	
	// ------------- MODULO FINANCIERO ----------------
	//MENU HEAD
	FINANCIERO("mnHeaFinanceiro"),
	
	
	//MENUITEM ACOES
	SECAO("miCadSecao"), MOEDA("miCadMoeda"),  PORTADOR("miCadPortador"), CENTROS_DE_CUSTO("miCadCentroCusto"), CLIENTE("miCadClienteFin") , PLANO_DE_CONTAS("miCadPlanoContas"), 
	CAIXA_BANCO("miCadCaixaBanco"), CONTAS_A_RECEBER("miOpeContasReceberFin"),


// ------------- // FIM // MODULO FINANCIERO ----------------
	
	// ------------- MODULO RECURSOS HUMANOS----------------
	//MENU HEAD
	RECURSOS_HUMANOS("mnHeaRecHumanos"),
	
	
	//MENUITEM CADASTRO
	FUNCIONARIO("miCadFuncionario"),


// ------------- // FIM // RECURSOS HUMANOS ----------------
	
	
	
	//MODULO CONFIGURAÇÕES 
	USUARIOS("miCadUsuarios"), DEPOSITO_ESTOQUES("miCadDepositoEstoque"),
	
	TABELA_AUXILIAR_CFOP("miCadCfop"), 
	TABELA_AUXILIAR_CST_COFINS("miCadCstCofins"), 
	TABELA_AUXILIAR_CST_ICM("miCadCstIcms"), 
	TABELA_AUXILIAR_CST_IPI("miCadCstIpi"), 
	TABELA_AUXILIAR_CST_PIS("miCadCstPis"), 
	
	LAYOUT_ARQUIVO("miCadLayoutsArquivo");
	
	public String idMenu;
		
	EnumNivelAcesso(String valor) {
			idMenu = valor;
		}

}
