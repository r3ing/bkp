package tools.reports;


/**
 * Class containing the attributes of a column for generate reports and export. 
 * @author User
 *
 */
public class ColumnsPrint {

	public String title;
	public String field;
	public String dataType;
	public String action;
	public Double width;
	

	public ColumnsPrint(String title, String field, String dataType, String action, Double width) {
		
		this.title = title;
		this.field = field;
		this.dataType = dataType;
		this.action = action;
		this.width = width;
	}
	
}
