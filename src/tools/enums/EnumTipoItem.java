package tools.enums;

public enum EnumTipoItem {

//	00: Mercadoria para Revenda;
//	01: Mat�ria-Prima;
//	02: Embalagem;
//	03: Produto em Processo;
//	04: Produto Acabado;
//	05: Subproduto;
//	06: Produto Intermedi�rio;
//	07: Material de Uso e Consumo;
//	08: Ativo Imobilizado;
//	09: Servi�os;
//	10: Outros insumos;
//	99: Outras.
	
	MERCADORIA_PARA_REVENDA("Mercadoria para Revenda","00"), 
	MATERIA_PRIMA("Mat�ria-Prima" ,  "01"),
	EMBALAGEM("Embalagem","02"),
	PRODUTO_EM_PROCESSO("Produto em Processo","03"),
	PRODUTO_ACABADO("Produto Acabado","04"),
	SUBPRDUTO("Subproduto","05"),
	PRODUTO_ITERMEDIARIO("Produto Intermedi�rio","06"),
	MATERIAL_USO_CONSUMO("Material de Uso e Consumo","07"),
	ATIVO_IMOBILIZADO("Ativo Imobilizado","08"),
	SERVICOS("Servi�os","09"),
	OUTROS_INSUMOS("Outros insumos","10"),
	OUTRAS("Outras","99");
	
	public String descTipoItem, codTipoItem;
	
	EnumTipoItem(String descTipoItem,String codTipoItem ) {
			this.descTipoItem = descTipoItem;
			this.codTipoItem = codTipoItem;
		}
}
