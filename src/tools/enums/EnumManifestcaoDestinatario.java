package tools.enums;

public enum EnumManifestcaoDestinatario {
	
	CONFIRMACAO_OPERACAO(1, "Confirma��o da Opera��o", 210200),
	CIENCIA_EMISSAO(2, "Ci�ncia da Emiss�o", 210210),
	DESCONHECIMENTO_OPERACAO(3, "Desconhecimento da Opera��o", 210220),
	OPERACAO_NAO_REALIZADA(4, "Opera��o n�o Realizada", 210240);
	
	
	public Integer id;
	public String manifesto;
	public Integer codigoEvento;
	
	EnumManifestcaoDestinatario(Integer id, String manifesto, Integer codigoEvento){
		this.id = id;
		this.manifesto = manifesto;
		this.codigoEvento = codigoEvento;
	}

}
