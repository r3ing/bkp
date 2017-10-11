package tools.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Class entidade Server
 * @author Julio Cesar
 *
 */

@XmlRootElement(name = "server")
@XmlType(propOrder = { "host", "port", "bd", "user", "psw", "empresaDefault" })
public class Server {

	private final IntegerProperty idServer;
	private final BooleanProperty serverDefault;
	private final StringProperty nome;
	private final StringProperty sgbd;//Sistema Gestion de Bases de Datos
	private final StringProperty host;
	private final IntegerProperty port;
	private final StringProperty bd;
	private final StringProperty user;
	private final StringProperty psw;
	private final IntegerProperty empresaDefault;

	public Server() {
		this(0, false, null, null, null, 0, null, null, null, 0);
	}

	public Server(int idServer, boolean serverDefault, String nome, String sgbd, String host, int port, String bd, String user,
			String psw, int empresaDefault) {
		// TODO Auto-generated constructor stub
		this.idServer = new SimpleIntegerProperty(idServer);
		this.serverDefault = new SimpleBooleanProperty(serverDefault);
		this.nome = new SimpleStringProperty(nome);
		this.sgbd = new SimpleStringProperty(sgbd);
		this.host = new SimpleStringProperty(host);
		this.port = new SimpleIntegerProperty(port);
		this.bd = new SimpleStringProperty(bd);
		this.user = new SimpleStringProperty(user);
		this.psw = new SimpleStringProperty(psw);
		this.empresaDefault = new SimpleIntegerProperty(empresaDefault);
	}

	
	/**
	 * @return the serverDefault
	 */
	@XmlAttribute(name = "default")
	public boolean isServerDefault() {
		return serverDefault.get();
	}

	/**
	 * @param serverDefault
	 *            the serverDefault to set
	 */
	public void setServerDefault(boolean serverDefault) {
		this.serverDefault.set(serverDefault);

	}

	/**
	 * @return the idServer
	 */
	@XmlAttribute(name = "id")
	public int getIdServer() {
		return idServer.get();
	}

	/**
	 * @param idServer
	 *            the idServer to set
	 */
	public void setIdServer(int idServer) {
		this.idServer.set(idServer);
	}

	/**
	 * @return the nome
	 */
	@XmlAttribute(name = "label")
	public String getNome() {
		return nome.get();
	}

	/**
	 * @param nome
	 *            the nome to set
	 */
	public void setNome(String nome) {
		this.nome.set(nome);
		;
	}
	
	/**
	 * @return the sgbd
	 */
	@XmlAttribute(name = "sgbd")
	public String getSgbd() {
		return sgbd.get();
	}

	/**
	 * @param sgbd the sgbd to set
	 */
	public void setSgbd(String sgbd) {
		this.sgbd.set(sgbd);;
	}

	/**
	 * @return the host
	 */

	public String getHost() {
		return host.get();
	}

	/**
	 * @param host
	 *            the host to set
	 */
	public void setHost(String host) {
		this.host.set(host);
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port.get();
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort(int port) {
		this.port.set(port);
	}

	/**
	 * @return the bd
	 */
	@XmlElement(name = "dataBases")
	public String getBd() {
		return bd.get();
	}

	/**
	 * @param bd
	 *            the bd to set
	 */
	public void setBd(String bd) {
		this.bd.set(bd);
	}

	/**
	 * @return the user
	 */
	@XmlElement(name = "usuario")
	public String getUser() {
		return user.get();
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(String user) {
		this.user.set(user);
		;
	}

	/**
	 * @return the psw
	 */
	@XmlElement(name = "senha")
	public String getPsw() {
		return psw.get();
	}

	/**
	 * @param psw
	 *            the psw to set
	 */
	public void setPsw(String psw) {
		this.psw.set(psw);
		;
	}

	/**
	 * @return the empresaDefault
	 */
//	@XmlElementWrapper(name="empresaDefault")
	@XmlElement(name = "codEmpresa")
	public int getEmpresaDefault() {
		return empresaDefault.get();
	}

	/**
	 * @param empresaDefault
	 *            the empresaDefault to set
	 */
	public void setEmpresaDefault(int empresaDefault) {
		this.empresaDefault.set(empresaDefault);
	}

}
