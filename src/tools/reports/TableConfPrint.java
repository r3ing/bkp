package tools.reports;


/**
 * Class containing the attributes of the columns that can be exported or shown in a report.
 * @author User
 *
 */
public class TableConfPrint {

	public String titleColumn;
	public String id;
	public String field;
	public String type;
	public Double width;
	
	public TableConfPrint(String titleColumn, String id, String field, String type, Double width) {
		super();
		this.titleColumn = titleColumn;
		this.id = id;
		this.field = field;
		this.type = type;
		this.width = width;
	}
	
}
