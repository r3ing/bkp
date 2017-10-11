package tools.enums;

import application.DadosGlobais;

public enum EnumOrigemRegistro {
	
	INCLUCAO_MANUAL(0),
	RENEGOCIACAO(1),
	VENDA(2);
	
	public Integer index;
	
	EnumOrigemRegistro(Integer valor ) {
		this.index = valor;	
	}
	
}
