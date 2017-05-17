package iss.client.gui;

import iss.model.User;
import iss.services.ConfException;
import iss.services.IConfClient;
import iss.services.IConfServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Dana on 16-May-17.
 */
public class AppViewController implements IConfClient {
    private IConfServer server;

    //componentele fxml - MAIN PAGE
    @FXML
    Button buttonLogout;

    //componentele fxml - LOGIN PAGE
    @FXML
    Button buttonLogin;
    @FXML
    TextField textfieldUsername;
    @FXML
    TextField textfieldPassword;
    @FXML
    Button buttonSignup;
    @FXML
    CheckBox checkboxDontHaveAccount;

    //componentele fxml - REGISTER PAGE
    @FXML
    TextField textfieldUsernameR;
    @FXML
    TextField textfieldPasswordR;
    @FXML
    TextField textfieldEmailR;
    @FXML
    TextField textfieldLastNameR;
    @FXML
    TextField textfieldFirstNameR;
    @FXML
    Button buttonSignupR;

    //constructor
    public AppViewController() {
    }

    public void setService(IConfServer server) {
        this.server = server;
    }

    //Alert for error
    private void showErrorMessage(String msg){
        Alert message = new Alert(Alert.AlertType.ERROR);
        message.setTitle("Whoops");
        message.setContentText(msg);
        message.showAndWait();
    }

    //Alert for succes
    private static void showMessage(Alert.AlertType type, String header, String msg){
        Alert message = new Alert(type);
        message.setHeaderText(header);
        message.setContentText(msg);
        message.showAndWait();
    }

    //Controller LOGIN PAGE
    //la apasarea butonului Login
    @FXML
    private void handleButtonLogin(ActionEvent e) {
        try {
            String username = textfieldUsername.getText();
            String password = textfieldPassword.getText();
            if (username.equals("") || password.equals(""))
                throw new NullPointerException();

            User user = new User(username, password);
            server.login(user, this);

            //incarca fereastra principala
            openNewPage(e, "/view/apppage.fxml", "Conference Management System");
        } catch (NullPointerException e1) {
            showErrorMessage("Empty fields");
        } catch (IOException | ConfException e1) {
            showErrorMessage("Invalid username or password.");
        }
    }

    //cand se bifeaza checkbox-ul pentru Terms => enable/disable la butonul Sign Up
    public void handleCheckbox(ActionEvent e) {
        if (buttonSignup.isDisable()) {
            buttonSignup.setDisable(false);
        } else {
            buttonSignup.setDisable(true);
        }
    }

    //cand se apasa butonul de Sign Up din LOGIN PAGE => se deschide fereastra de REGISTER
    @FXML
    public void handleSignUp(ActionEvent e) {
        //deschidere REGISTER PAGE
        try {
            openNewPage(e, "/view/registerpage.fxml", "Register");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    //deschiderea unei noi ferestre, loader = fisierul fxml, title = titlul ferestrei
    private void openNewPage(ActionEvent e, String loader, String title ) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(loader));
        Parent parent = fxmlLoader.load();
        Stage stage = new Stage();
        ((Node) (e.getSource())).getScene().getWindow().hide();
        AppViewController appViewController = fxmlLoader.getController();
        appViewController.setService(server);
        stage.setTitle(title);
        stage.setScene(new Scene(parent));
        stage.show();
    }

    //Controller REGISTER PAGE
    //butonul Sign Up de pe pagina de REGISTER
    @FXML
    public void handleButtonSignUpR(ActionEvent e) {
        try {
            String firstName = textfieldFirstNameR.getText();
            String lastName = textfieldLastNameR.getText();
            String email = textfieldEmailR.getText();
            String username = textfieldUsernameR.getText();
            String password = textfieldPasswordR.getText();
            User user = new User(username, password,lastName,firstName,email);
            server.register(user);

            //register cu succes
            showMessage(Alert.AlertType.CONFIRMATION, "Succes", "Account created.");

            //deschidere LOGIN PAGE
            openNewPage(e, "/view/loginpage.fxml", "Login");
        } catch (NullPointerException | ConfException e1){
            e1.printStackTrace();
            showErrorMessage("Register Error.");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    //cand se bifeaza checkbox-ul pentru Terms => enable/disable la butonul Sign Up
    public void handleCheckboxTerms(ActionEvent e) {
        if (buttonSignupR.isDisable()) {
            buttonSignupR.setDisable(false);
        } else {
            buttonSignupR.setDisable(true);
        }
    }

    //Controller MAIN PAGE
    //cand se apasa butonul de logout
    public void handleButtonLogout(ActionEvent e) {
        try {
            User userLogat = new User();
            server.logout(userLogat, this);
            //deschide LOGIN PAGE
            openNewPage(e, "/view/loginpage.fxml", "Login");
        } catch (ConfException e1) {
            showErrorMessage("Logout Error.");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}