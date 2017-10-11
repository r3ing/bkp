package tools.enums;

import application.DadosGlobais;

public enum EnumTipoFuncionario {
	
	VENDEDOR(0, DadosGlobais.resourceBundle.getString("funcionarioController.cboxTipoFuncionario0")),
	GERENTE(1, DadosGlobais.resourceBundle.getString("funcionarioController.cboxTipoFuncionario1")),
	TECNICO(2, DadosGlobais.resourceBundle.getString("funcionarioController.cboxTipoFuncionario2")),
	AUXILIAR(3, DadosGlobais.resourceBundle.getString("funcionarioController.cboxTipoFuncionario3")),
	PARCEIRO(3, DadosGlobais.resourceBundle.getString("funcionarioController.cboxTipoFuncionario4"));
	
	public Integer index;
	public String  text;
	
	EnumTipoFuncionario(Integer valor, String text ) {
		this.index = valor;
		this.text = text;
	}	
	
}
