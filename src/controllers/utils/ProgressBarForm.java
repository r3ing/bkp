package controllers.utils;


import org.hibernate.HibernateException;

import com.jfoenix.controls.JFXSpinner;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.effect.Effect;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tools.utils.Util;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class ProgressBarForm {
	
	private Class clase;
	private ProgressBar bar;
	private Task<String> task;
	
	
	
	public static Stage showProgressBar(Object clase, Task<String> task, String msg, boolean flagShowBtnclose){
		
		Stage initStage = new Stage();
		initStage.initStyle(StageStyle.TRANSPARENT);
		initStage.initModality(Modality.APPLICATION_MODAL);
		JFXSpinner loadProgress = new JFXSpinner();
		loadProgress.radiusProperty().set(28);
		
		Label lblInfo = new Label("Aguarde, Processando Informaçoes!");
		lblInfo.setId("info");
		
		Label lblMsg = new Label(msg);
		
		
		Button btnClose = new Button();
		FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
		icon.setSize("16");
		icon.setId("icons");	
		btnClose.setGraphic(icon);
		btnClose.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub				
			try {					
					task.cancel();
					initStage.close();										
					
		        } catch (HibernateException e) {
					// TODO: handle exception
					Util.alertError("Erro Cancelar Tarefa");
			    }
				
				
			}
		});
		
		AnchorPane anPane = new AnchorPane();
		
		anPane.getChildren().addAll(loadProgress, lblInfo, lblMsg);
		
		if(flagShowBtnclose){
			anPane.getChildren().add(btnClose);
		
		AnchorPane.setTopAnchor(btnClose, Double.valueOf(0));
		AnchorPane.setRightAnchor(btnClose, Double.valueOf(0));
		}
		AnchorPane.setTopAnchor(loadProgress, Double.valueOf(48));
		AnchorPane.setLeftAnchor(loadProgress, Double.valueOf(20));
		AnchorPane.setTopAnchor(lblInfo, Double.valueOf(60));
		AnchorPane.setLeftAnchor(lblInfo,Double.valueOf(90));
		AnchorPane.setTopAnchor(lblMsg, Double.valueOf(85));
		AnchorPane.setLeftAnchor(lblMsg,Double.valueOf(90));
		
		final Scene scene = new Scene(anPane, 430, 140);
		scene.setFill(Color.TRANSPARENT);
		initStage.setScene(scene);
		scene.getStylesheets().add("/styles/css/progressBar.css");
		initStage.show();

		return initStage;
		
	}
	
   

}
