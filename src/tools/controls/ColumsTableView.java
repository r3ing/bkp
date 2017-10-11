package tools.controls;


/**
 * Class containing the main attributes of a column to be changed.
 * @author User
 *
 */
public class ColumsTableView {

	private String title;
	private String width;
	
	public ColumsTableView(String title, String width) {
		super();
		this.title = title;
		this.width = width;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}
	
	@Override
	public String toString(){
		return title;
		
	}
	
}
