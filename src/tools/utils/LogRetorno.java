package tools.utils;

import java.util.List;

import tools.enums.EnumLogRetornoStatus;

public class LogRetorno {
	
	private EnumLogRetornoStatus status;
	private String msg;
	private Integer lastRecord;
	private String log;
	private Object objeto;
	private List<?> listaObjetos;
	
	public LogRetorno(){}
	
	public  LogRetorno(EnumLogRetornoStatus status, String msg, Integer lastRecord, String log) {
		super();
		this.status = status;
		this.msg = msg;
		this.lastRecord = lastRecord;
		this.log = log;
	}
	
	public  LogRetorno(EnumLogRetornoStatus status, String msg, Integer lastRecord, String log, Object objeto, List<?> listaObjetos) {
		super();
		this.status = status;
		this.msg = msg;
		this.lastRecord = lastRecord;
		this.log = log;
		this.objeto = objeto;
		this.listaObjetos = listaObjetos;
	}
	
	public EnumLogRetornoStatus getStatus() {
		return status;
	}
	public void setStatus(EnumLogRetornoStatus status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Integer getLastRecord() {
		return lastRecord;
	}
	public void setLastRecord(Integer lastRecord) {
		this.lastRecord = lastRecord;
	}
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}

	public Object getObjeto() {
		return objeto;
	}

	public void setObjeto(Object objeto) {
		this.objeto = objeto;
	}

	public List<?> getListaObjetos() {
		return listaObjetos;
	}

	public void setListaObjetos(List<?> listaObjetos) {
		this.listaObjetos = listaObjetos;
	}
	
}
