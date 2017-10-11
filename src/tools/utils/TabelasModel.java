package tools.utils;

import org.controlsfx.control.textfield.CustomTextField;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;

public class TabelasModel 
{
	    private CheckBox fieldChk;
	    private final SimpleStringProperty fieldName;
	    private final SimpleStringProperty fieldType;	    
	    private CustomTextField fieldVal;
	 
	    public TabelasModel(String fChk, String fName, String fType, String fVal) 
	    {	
 
	        this.fieldChk = new CheckBox(fChk);
	        this.fieldName = new SimpleStringProperty(fName);
	        this.fieldType = new SimpleStringProperty(fType);	        
	        this.fieldVal = new CustomTextField();		

	        fieldChk.setOnAction(new EventHandler<ActionEvent>()
	  		{				
					@Override
					public void handle(ActionEvent event) 
					{
						// TODO Auto-generated method stub
						fieldVal.setDisable(!fieldChk.isSelected());						
					}
					
	  		});	        
	        
	        /*Button btn = Util.customSaveTextField("right", 100, this.fieldVal);
	        btn.setOnAction(new EventHandler<ActionEvent>() 
	        {			
				@Override
				public void handle(ActionEvent event) 
				{
					System.out.println("OK");
					
				}
			});*/
	    }
	    
	    public CheckBox getFieldChk() 
	    {
	        return fieldChk;
	    }
	    public void setFieldChk(CheckBox fChk) 
	    {
	    	fieldChk = fChk;
	    }		    
	 
	    public String getFieldName() 
	    {
	        return fieldName.get();
	    }
	    public void setFieldName(String fName) 
	    {
	    	fieldName.set(fName);
	    }
	        
	    public String getFieldType() 
	    {
	        return fieldType.get();
	    }
	    public void setFieldType(String fName) 
	    {
	    	fieldType.set(fName);
	    }	
	    
	    public CustomTextField getFieldVal() 
	    {
	        return fieldVal;
	    }
	    public void setFieldVal(CustomTextField fVal) 
	    {
	    	fieldVal = fVal;
	    }		    
}