package tools.xml;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class entidade Servers
 * @author User
 *
 */
@XmlRootElement(name = "servers")
public class Servers {

	private int highID;

	private ArrayList<Server> server;
	
	@XmlAttribute(name="highID")
	public int getHighID() {
		return highID;
	}

	public void setHighID(int highID) {
		this.highID = highID;
	}


	/**
	 * @return the servers
	 */	
	@XmlElement(name = "server")
	public ArrayList<Server> getServer() {
		return server;
	}

	/**
	 * @param servers
	 *            the servers to set
	 */
	public void setServer(ArrayList<Server> server) {
		this.server = server;
	}


}
