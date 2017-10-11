package tools.xml;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Columns")
/**
 * Class with an array of class Column to be saved in a file.
 * @author User
 *
 */
public class UserDefinedColumns {
	
	private ArrayList<UserDefinedColumn> userDefinedColumn;

	public ArrayList<UserDefinedColumn> getColumn() {
		return userDefinedColumn;
	}

	@XmlElement
	public void setColumn(ArrayList<UserDefinedColumn> userDefinedColumn) {
		this.userDefinedColumn = userDefinedColumn;
	}
	
	
	
	

}
