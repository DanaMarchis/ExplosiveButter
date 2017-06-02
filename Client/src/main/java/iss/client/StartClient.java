package iss.client;

import iss.client.gui.AppViewController;
import iss.networking.rpcprotocol.ConfServerRpcProxy;
import iss.services.IConfServer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Created by Dana on 16-May-17.
 */
public class StartClient extends Application{

    private static int defaultPort=55555;
    private static String defaultServer="localhost";
    private static IConfServer server;

    public static void main(String[] args) {

        String serverIP=defaultServer;
        int serverPort=defaultPort;
        System.out.println("Using server IP "+serverIP);
        System.out.println("Using server port "+serverPort);
        server = new ConfServerRpcProxy(serverIP, serverPort);

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ClassLoader.getSystemResource("view/loginpage.fxml"));
        BorderPane root = loader.load();

        AppViewController appViewController = loader.getController();
        appViewController.setService(server);
        appViewController.setStage(primaryStage);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
    }
}
