package tools.application;


public class Conexoes {
	// Dados da conexão
	private int id;
	private String nombre;
	private boolean connDefault;
	// Conexão
	private String host;
	private int port;
	private String bd;
	private String user;
	private String psw;
	// EmpresaDefault
	private int codEmp;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isConnDefault() {
		return connDefault;
	}

	public void setConnDefault(boolean connDefault) {
		this.connDefault = connDefault;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getBd() {
		return bd;
	}

	public void setBd(String bd) {
		this.bd = bd;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPsw() {
		return psw;
	}

	public void setPsw(String psw) {
		this.psw = psw;
	}

	public int getCodEmp() {
		return codEmp;
	}

	public void setCodEmp(int codEmp) {
		this.codEmp = codEmp;
	}
}
