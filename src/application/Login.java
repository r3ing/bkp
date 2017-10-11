package application;

import controllers.application.LoginController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tools.utils.Util;

public class Login extends Application {
	
	public static Stage stage;
	Util ut = new Util();

	@Override
	public void start(Stage primaryStage) {
		try {
			
			Scene scene1 = new Scene(ut.openWindow("/views/application/login.fxml", new LoginController()), 600 , 400);
			scene1.getStylesheets().add(getClass().getResource("/styles/css/login.css").toExternalForm());
			primaryStage.setScene(scene1);
			primaryStage.setTitle("Eptus Corporation");
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.getIcons().add(new Image(getClass().getResource("/styles/img/favicon.ico").toExternalForm()));
			primaryStage.show();			
			
		} catch (Exception e) {
			ut.alertException(DadosGlobais.msgErroException, e.getMessage(), true);
		}

	}

	public static void main(String[] args) {
		launch(args);	
	}
}
