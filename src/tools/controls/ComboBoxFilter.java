package tools.controls;

public class ComboBoxFilter {
	
	String value;
	//TIPO DE BUSCA 1 BUSCA EXATA (=) , 2 BUSCA CONTIDA (LIKE)
	Integer action;
	String field;
	Enum<?> enumOpcao;
	
	public ComboBoxFilter(String value, int action, String field) {
		super();
		this.value = value;
		this.action = action;
		this.field = field;
	}
	
	public ComboBoxFilter(String value, int action, Enum<?> enumOpcao) {
		super();
		this.value = value;
		this.action = action;
		this.enumOpcao = enumOpcao;
	}

	public ComboBoxFilter() {
		// TODO Auto-generated constructor stub
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getAction() {
		return action;
	}

	public void setAction(Integer action) {
		this.action = action;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Enum<?> getEnumOpcao() {
		return enumOpcao;
	}

	public void setEnumOpcao(Enum<?> enumOpcao) {
		this.enumOpcao = enumOpcao;
	}
	
	
}
