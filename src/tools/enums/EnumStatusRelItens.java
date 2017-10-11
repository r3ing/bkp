package tools.enums;

public enum EnumStatusRelItens {
	
	NAO_EXISTE(0),//O ITEM N�O EXISTE NO BANCO DE DADOS
	EXISTE(1),// O ITEM EXISTE E FOI RELACIONADO
	NOVO(2),// O ITEM � NOVO
	PENDENTE(3);// O ITEM EXISTE NO BANCO, MAS N�O FOI RELACIONADO E EST� PENDENTE SER RELACIONADO

	public int status;
	
	EnumStatusRelItens(int valor){
		this.status = valor;
	}
}
