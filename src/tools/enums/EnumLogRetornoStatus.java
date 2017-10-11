package tools.enums;

public enum EnumLogRetornoStatus {
	
	INICIOTRANSACAO(-1), ERRO(0), SUCESSO(1), REGDUPLICADO(2), EMPTY_DATA(3);
	
	public int codStatusLog;
	
	EnumLogRetornoStatus(int valor) {
		codStatusLog = valor;
	}
}
