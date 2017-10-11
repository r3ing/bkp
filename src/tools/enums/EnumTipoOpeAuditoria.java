package tools.enums;

public enum EnumTipoOpeAuditoria {
	
	EXCLUSAO(1), REATIVACAO(2), ALTERACAO(3), CANCELAMENTO(4); 
	
	public Integer idTipoOpe;
	
	EnumTipoOpeAuditoria(Integer valor) {
		idTipoOpe = valor;
		}
}
