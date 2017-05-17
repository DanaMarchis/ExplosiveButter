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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Dana on 16-May-17.
 */
public class AppViewController implements IConfClient {
    IConfServer server;

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

    //Controller LOGIN PAGE
    //la apasarea butonului Login
    @FXML
    private void handleButtonLogin(ActionEvent e) {
        try {
            String username = textfieldUsername.getText();
            String password = textfieldPassword.getText();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/apppage.fxml"));
            try {
                Parent parent = fxmlLoader.load();
                Stage stage = new Stage();
                ((Node) (e.getSource())).getScene().getWindow().hide();
                User user = new User(username, password);

                //se deschide fereastra principala
                stage.setTitle("Conference Management System");
                stage.setScene(new Scene(parent));
                stage.show();

                //server.login(user, this);

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (NullPointerException e1){
            e1.printStackTrace();
            //showErrorMessage()
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/registerpage.fxml"));
        try {
            Parent parent = fxmlLoader.load();
            Stage stage = new Stage();
            ((Node) (e.getSource())).getScene().getWindow().hide();

            //se deschide fereastra pentru register
            stage.setTitle("Register");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
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

            //sa fac cumva sa revina la pagina de login?

        } catch (NullPointerException | ConfException e1){
            e1.printStackTrace();
            //showErrorMessage()
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
}