package tools.reports;


/**
 * Class containing the attributes and action of a column for generate reports and export. 
 * @author User
 *
 */
public class TableActionsPrint {

	public String column;
	public String id;
	public String field;
	public String type;
	public boolean print;
	public String action;
	public Double width;
	
	
	public TableActionsPrint(String column, String id, String field,String type, boolean print, String action, Double width) {
		super();
		this.column = column;
		this.id = id;
		this.field = field;
		this.type = type;
		this.print = print;
		this.action = action;
		this.width = width;
	}

	
	
}
