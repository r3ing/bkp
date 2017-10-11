package controllers.configuracoes;


import java.net.URL;
import java.util.ResourceBundle;
import application.DadosGlobais;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import tools.utils.Util;

public class SearchLabelController implements Initializable {

    @FXML
    private AnchorPane apSearchLabel, 
    				   apSearchedText,
                       apInterno;

    @FXML
    private Pane titleBar;

    @FXML
    private Button btnCancel,
                   btnSave;  
    
    @FXML
    private ScrollPane scrPane; 
    
    @FXML
    private Label lblSearchedWord,
    			  lblSearchCount;
    
    @FXML
    private TabPane ctabPane = null;
    
    private String cPath, 
                   cSearchedWord; 
    
    public SearchLabelController(String path, TabPane tabPane, String searchedWord)
    {      
    	   cPath = path; 	
    	   ctabPane = tabPane;
    	   cSearchedWord = searchedWord;     	   
    }

    @FXML
    void actionBtnSave(ActionEvent event) {
    	Util.closeWindow(apSearchLabel);
    }

    @FXML
    void onActionBtnCancel(ActionEvent event) {
         Util.closeWindow(apSearchLabel);
    }
    

	public void initialize(URL location, ResourceBundle resources ) 
	{	 	    
			String btnText = "";
			
			double border = 10,
				   apTop = border,
				   apLeft = border;
			
			int inc = 40, 
				btnHeight = 25, 
				btnWith = 250,
				cntLines = 0;	
			
			BackgroundFill bf = new BackgroundFill(null, null, null);	
			lblSearchedWord.setText( DadosGlobais.resourceBundle.getString("searchLabelController.lblSearchedWord") + ": ' " + cSearchedWord + " '" );
			
		    Button btn = null;
			
			for(int i = 0; i < cPath.length(); i++)
			{
				char charText = cPath.charAt(i);
				
				if ( charText == '>' || charText == '\n')
				{					
		 	         btn = new Button();
				 	 btn.setPrefSize(btnWith, btnHeight);
				 	 btn.setText(btnText);
				 	 btn.setCursor(Cursor.HAND);
				 	 
				 	 btn.setDisable(false);
				 	 
				 	 //----------------BNT EVENT------------------
				 	    ObservableList <Tab> obsListTabs = ctabPane.getTabs();
				 	    int obsListTabsSize = obsListTabs.size();
				 	    
						for( int t = 0 ; t < obsListTabsSize ; t++ )
					 	{	   
					 	        Tab externalTab = obsListTabs.get(t);
					 	        String txtExtTab = externalTab.getText(); 						 	        
					 	        
				 	        	AnchorPane ap = (AnchorPane)externalTab.getContent();
				 	        	ObservableList <Node> obsList = ap.getChildren();					 	        	
				 	        	
				 	        	int size = obsList.size();
				 	        	
				 	        	if( size >= 1 )
				 	        	{
					 	        	TabPane internalTabPane = (TabPane)obsList.get(0);
					 	        	ObservableList <Tab> obsTabList = internalTabPane.getTabs();
					 	        	
					 	        	if( obsTabList != null )
					 	        	{						 	        		
						 	        	int obsTabListSize = obsTabList.size();
						 	        	
						 	        	for( int t1 = 0 ; t1 < obsTabListSize ; t1++ )
						 	        	{	
						 	        		 Tab internalTab = obsTabList.get(t1);
						 	        		 
						 	        		 String txtIntTab = "";
						 	        		
						 	        		 StackPane stackPn = (StackPane)internalTab.getGraphic();
						 	        		 
						 	        		 if ( stackPn != null )
						 	        		 {
						 	        			     ObservableList<?> tmp = stackPn.getChildren();
								 	        		 
								 	        		 if( tmp.size() > 0 )
								 	        		 {	 
									 	        		     Group groupStack = (Group)tmp.get(0);
										 	        		 ObservableList<?> tmp1 = groupStack.getChildren();
									 	        		 
										 	        		 if( tmp1.size() > 0 )
										 	        		 {	 								 	        			     
												 	        		 Label lblIntTab =  (Label)tmp1.get(0);										 	        		 
												 	        		 txtIntTab = lblIntTab.getText();
									 	        		     }
									 	        		     else{}
								 	        		  }
								 	        		  else{}
						 	        	      }
						 	        	      else{}
						 	        		 
							 	        	  if( txtIntTab == "" || txtIntTab.isEmpty() )	 
							 	        		 txtIntTab = internalTab.getText();
							 	        	  else{}
						 	        		 
								 	         if( btnText.contains(txtExtTab) && btnText.contains(txtIntTab) )
								 	         {		
								 	        	 
								 	        	 btn.setOnAction(event -> {
									 	        	    
								 	        		    onActionBtnCancel(event);
								 	        		    
										 	        	SingleSelectionModel<Tab> selectionModel = ctabPane.getSelectionModel();
										 	        	selectionModel.select(externalTab);
										 	        	
										 	        	SingleSelectionModel<Tab> selectionModel1 = internalTabPane.getSelectionModel();
									 	        		selectionModel1.select(internalTab);
									 	        		
									 	           });
								 	        	 
								 	          }
							 	              else{}	
						 	        		 
						 	        	  }	
				 	        		  }
				 	        		  else{}
				 	        		
				 	        	   }
				 	        	   else{}
					 	   }	  				 	 
				 	 
				 	 //---------------------------------------
					 	   
					 apInterno.getChildren().add(btn);
			 	     AnchorPane.setTopAnchor(btn, apTop);
			 	     AnchorPane.setLeftAnchor(btn, apLeft);
					 
					 if( charText == '>' )
					 {
						 
						 Button btn1 = new Button();
						 btn1.setPrefSize(25, btnHeight);
						 btn1.setText(">");	
						 btn1.setDisable(true);	
						 
						 apInterno.getChildren().add(btn1);
						 AnchorPane.setTopAnchor(btn1, apTop);
						 AnchorPane.setLeftAnchor(btn1, ( apLeft + btnWith + 8 ));	
						 
					 }
					 else{}
						
				 	 apLeft = apLeft + btnWith + inc;
				 	 btnText = "";	

					 if( charText == '\n' )
					 {			
						 cntLines++;
						 
						 if( !btn.isDisable() )
						 {						 
							  btn.setBackground(new Background(bf));							
							  btn.setCursor(Cursor.DEFAULT);
						 }
						 else{}						 
						 //btn.setDisable(true);
						 btn.setPrefWidth( btnWith * 1.5 );
						 apTop = apTop + btnHeight + (inc/2);
						 apLeft = border; 
						 
					 }	
					 else{}
					 
				}
				else
				{
					 btnText += charText;
				}
			
		}
			
		lblSearchCount.setText( cntLines + " " + DadosGlobais.resourceBundle.getString("searchLabelController.lblSearchCountMatches") + " " + DadosGlobais.resourceBundle.getString("searchLabelController.lblSearchCountFinded"));
 	    apSearchLabel.getStylesheets().add(getClass().getResource("/styles/css/themes "+DadosGlobais.empresaLogada.getSistema()+ "/style_"+DadosGlobais.empresaLogada.getSistema()+".css").toExternalForm());
	}

}
