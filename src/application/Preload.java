package application;

import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.application.Preloader.StateChangeNotification;
import javafx.application.Preloader.StateChangeNotification.Type;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Preload extends Preloader {
	private Stage preloaderStage;
	 
    public void start(Stage primaryStage) throws Exception {
       this.preloaderStage = primaryStage;
 
       VBox loading = new VBox(20);
       loading.setMaxWidth(Region.USE_PREF_SIZE);
       loading.setMaxHeight(Region.USE_PREF_SIZE);
       loading.getChildren().add(new ProgressBar());
       loading.getChildren().add(new Label("Please wait..."));
 
       BorderPane root = new BorderPane(loading);
       Scene scene = new Scene(root);
 
       primaryStage.setWidth(800);
       primaryStage.setHeight(600);
       primaryStage.setScene(scene);
       primaryStage.show();
   }
    
    
    private void mayBeHide() {
        if (preloaderStage.isShowing()) {
//            consumer.setCredential(username, password);
            Platform.runLater(new Runnable() {
                public void run() {
                	preloaderStage.hide();
                }
            });
        }
    }
    
    @Override
    public void handleStateChangeNotification(StateChangeNotification evt) {
        if (evt.getType() == StateChangeNotification.Type.BEFORE_START) {
            //application is loaded => hide progress bar
//            bar.setVisible(false);
            
//            consumer = (CredentialsConsumer) evt.getApplication();
            //hide preloader if credentials are entered
            mayBeHide();
        }
    }    
 
//    @Override
//    public void handleApplicationNotification(PreloaderNotification notification) {
//        if (notification instanceof StateChangeNotification) {
//        	preloaderStage.hide();
//        }
//    }
    
//   public void handleStateChangeNotification(StateChangeNotification stateChangeNotification) {
//      if (stateChangeNotification.getType() == Type.BEFORE_INIT) {
//         preloaderStage.hide();
//      }
//   }

}
