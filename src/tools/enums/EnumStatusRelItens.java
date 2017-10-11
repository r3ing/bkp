package tools.enums;

public enum EnumStatusRelItens {
	
	NAO_EXISTE(0),//O ITEM NÃO EXISTE NO BANCO DE DADOS
	EXISTE(1),// O ITEM EXISTE E FOI RELACIONADO
	NOVO(2),// O ITEM É NOVO
	PENDENTE(3);// O ITEM EXISTE NO BANCO, MAS NÃO FOI RELACIONADO E ESTÁ PENDENTE SER RELACIONADO

	public int status;
	
	EnumStatusRelItens(int valor){
		this.status = valor;
	}
}
