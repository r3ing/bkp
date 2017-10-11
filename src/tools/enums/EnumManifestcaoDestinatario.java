package tools.enums;

public enum EnumManifestcaoDestinatario {
	
	CONFIRMACAO_OPERACAO(1, "Confirmação da Operação", 210200),
	CIENCIA_EMISSAO(2, "Ciência da Emissão", 210210),
	DESCONHECIMENTO_OPERACAO(3, "Desconhecimento da Operação", 210220),
	OPERACAO_NAO_REALIZADA(4, "Operação não Realizada", 210240);
	
	
	public Integer id;
	public String manifesto;
	public Integer codigoEvento;
	
	EnumManifestcaoDestinatario(Integer id, String manifesto, Integer codigoEvento){
		this.id = id;
		this.manifesto = manifesto;
		this.codigoEvento = codigoEvento;
	}

}
