package iss.client;

import iss.client.gui.AppViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Created by Dana on 16-May-17.
 */
public class StartClient extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ClassLoader.getSystemResource("view/loginpage.fxml"));
        BorderPane root = loader.load();

        AppViewController appViewController = loader.getController();
        //appViewController.setService(server);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Autentificare");
        primaryStage.show();
    }
}
