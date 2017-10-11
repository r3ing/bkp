package tools.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "column")
@XmlType(propOrder = { "name", "title", "position", "width" })
/**
 * Class with attributes of user-defined columns that will be saved to a file.
 * @author User
 *
 */
public class UserDefinedColumn {

	private String name;
	private String title;
	private Integer position;
	private Double width;

	public UserDefinedColumn() {
		this(null, null, 0, 0.0);
	}

	public UserDefinedColumn(String name, String title, Integer position, Double width) {
		super();
		this.name = name;
		this.title = title;
		this.position = position;
		this.width = width;
	}

	public String getName() {
		return name;
	}

	@XmlAttribute
	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	@XmlElement
	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getPosition() {
		return position;
	}
	
	@XmlElement
	public void setPosition(Integer position) {
		this.position = position;
	}

	public Double getWidth() {
		return width;
	}
	
	@XmlElement
	public void setWidth(Double width) {
		this.width = width;
	}

}
