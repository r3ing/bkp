package application;

import controllers.application.TelaPrincipalEptusController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Main extends Application {

	int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
	public static Stage stage = null;

	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;
		try {
			FXMLLoader fxml = new FXMLLoader(getClass().getResource("/views/application/TelaPrincipalEptus.fxml"));
			fxml.setController(new TelaPrincipalEptusController());
			Parent root = fxml.load();// FXMLLoader.load(Main.class.getResource("/views/TelaPrincipalEptus.fxml"));//
			
			// FXMLLoader.load(getClass().getResource("application/views/TelaPrincipalEptus.fxml"));
			Scene scene1 = new Scene(root, ancho, alto - 75);
			if (DadosGlobais.sistema.equals("Ultimate")) {
				scene1.getStylesheets()
						.add(getClass().getResource("/styles/css/themes Ultimate/style_Ultimate.css").toExternalForm());
			} else if (DadosGlobais.sistema.equals("Professional")) {
				scene1.getStylesheets().add(getClass()
						.getResource("/styles/css/themes Professional/style_Professional.css").toExternalForm());
			} else if (DadosGlobais.sistema.equals("Enterprise")) {
				scene1.getStylesheets().add(
						getClass().getResource("/styles/css/themes Enterprise/style_Enterprise.css").toExternalForm());
			}
			

			
			primaryStage.setScene(scene1);

			primaryStage.setTitle("Eptus Corporation");
			primaryStage.initStyle(StageStyle.TRANSPARENT);
//			primaryStage.centerOnScreen();
			primaryStage.setHeight(Screen.getPrimary().getVisualBounds().getHeight()); 
			primaryStage.setWidth(Screen.getPrimary().getVisualBounds().getWidth()); 

			primaryStage.getIcons().add(new Image(getClass().getResource("/styles/img/favicon.ico").toExternalForm()));

			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);

	}
}
