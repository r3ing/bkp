package application;

import controllers.application.InstallController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class InstallMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {	
						
			FXMLLoader fxml = new FXMLLoader(getClass().getResource("/views/application/viewInstall.fxml"));
		
			fxml.setController(new InstallController());
				
			Scene scene = new Scene(fxml.load(),772,405);
			scene.getStylesheets().add(getClass().getResource("/styles/css/install/style_Install.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.show();
			
		} catch(Exception e) {
			e.getStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
		
	}
}
