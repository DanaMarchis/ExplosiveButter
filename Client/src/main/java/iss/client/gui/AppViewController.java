package iss.client.gui;

import iss.model.*;
import iss.services.ConfException;
import iss.services.IConfClient;
import iss.services.IConfServer;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by Dana on 16-May-17.
 */
public class AppViewController implements IConfClient {
    private IConfServer server;
    private Stage stage;
    private User userlogat;

    //componentele fxml - MAIN PAGE
    @FXML
    TabPane mainTabPane;
    @FXML
    Tab tabAllConferences;
    @FXML
    Tab tabMyConferences;
    @FXML
    Tab tabCallForPapers;
    @FXML
    Tab tabReview;
    @FXML
    Tab tabSubmitAbstract;
    @FXML
    Tab tabSubmitFull;
    @FXML
    Button buttonLogout;
    @FXML
    ComboBox<String> comboboxType;

    //TAB All Conferences
    @FXML
    TableView<Conference> tabAllConf_tableConf;
    @FXML
    TableColumn<Conference, String> tabAllConf_columnNameConf;
    @FXML
    TableColumn<Conference, Date> tabAllConf_columnStartDateConf;
    @FXML
    TableColumn<Conference, Date> tabAllConf_columnEndDateConf;

    @FXML
    TableView<Session> tabAllConf_tableSessions;
    @FXML
    TableColumn<Session, String> tabAllConf_columnNameSes;
    @FXML
    TableColumn<Session, String> tabAllConf_columnDateSes;
    @FXML
    TableColumn<Session, String> tabAllConf_columnRoomSes;
    @FXML
    TableColumn<Session, Integer> tabAllConf_columnPriceSes;
    @FXML
    TableColumn<Session, String> tabAllConf_columnStartHourSes;
    @FXML
    TableColumn<Session, String> tabAllConf_columnEndHourSes;

    private ObservableList<Conference> tabAllConf_modelConf;
    private ObservableList<Session> tabAllConf_modelSes;

    @FXML
    Button tabAllConf_buttonPay;
    @FXML
    TextField tabAllConf_textfieldPay;

    //TAB My Conferences
    @FXML
    TableView<Conference> tabMyConf_tableConf;
    @FXML
    TableColumn<Conference, String> tabMyConf_columnNameConf;
    @FXML
    TableColumn<Conference, String> tabMyConf_columnStartDateConf;
    @FXML
    TableColumn<Conference, String> tabMyConf_columnEndDateConf;

    @FXML
    TableView<Session> tabMyConf_tableSessions;
    @FXML
    TableColumn<Session, String> tabMyConf_columnNameSes;
    @FXML
    TableColumn<Session, String> tabMyConf_columnDateSes;
    @FXML
    TableColumn<Session, String> tabMyConf_columnRoomSes;
    @FXML
    TableColumn<Session, Integer> tabMyConf_columnPriceSes;
    @FXML
    TableColumn<Session, String> tabMyConf_columnStartHourSes;
    @FXML
    TableColumn<Session, String> tabMyConf_columnEndHourSes;

    private ObservableList<Conference> tabMyConf_modelConf;
    private ObservableList<Session> tabMyConf_modelSes;
    @FXML
    Button tabMyConf_buttonReview;


    //TAB Call for papers
    @FXML
    TableView<Conference> tabCall_tableConf;
    @FXML
    TableColumn<Conference, String> tabCall_columnNameC;
    @FXML
    TableColumn<Conference, String> tabCall_columnStartDateC;
    @FXML
    TableColumn<Conference, String> tabCall_columnEndDateC;
    @FXML
    TableColumn<Conference, String> tabCall_columnDeadlineAbstractC;
    @FXML
    TableColumn<Conference, String> tabCall_columnDeadlineFullC;

    @FXML
    TableView<Session> tabCall_tableSessions;
    @FXML
    TableColumn<Session, String> tabCall_columnNameS;
    @FXML
    TableColumn<Session, String> tabCall_columnDateS;
    @FXML
    TableColumn<Session, String> tabCall_columnRoomS;
    @FXML
    TableColumn<Session, Integer> tabCall_columnPriceS;
    @FXML
    TableColumn<Session, String> tabCall_columnStartHourS;
    @FXML
    TableColumn<Session, String> tabCall_columnEndHourS;

    private ObservableList<Conference> tabCall_modelConf;
    private ObservableList<Session> tabCall_modelSes;

    @FXML
    Button tabCall_buttonSubmitAbstract;

    //TAB Review
    @FXML
    Button tabReview_buttonSendReview;
    @FXML
    ComboBox<String> tabReview_comboboxQualifier;
    @FXML
    TextArea tabReview_textareaRecommendations;
    @FXML
    Button tabReview_buttonClose;
    @FXML
    TableView<Abstract_Details> tabReview_tableview;
    @FXML
    TableColumn<Abstract_Details, String> tabReview_columnName;
    @FXML
    TableColumn<Abstract_Details, String> tabReview_columnTopic;

    private ObservableList<Abstract_Details> tabReview_model;


    //TAB Submit Abstract
    @FXML
    Button tabSubmitAbstract_buttonPlus3;
    @FXML
    Button tabSubmitAbstract_buttonPlus4;
    @FXML
    TextField tabSubmitAbstract_textfieldName1;
    @FXML
    TextArea tabSubmitAbstract_textareaInfo1;
    @FXML
    TextField tabSubmitAbstract_textfieldName2;
    @FXML
    TextArea tabSubmitAbstract_textareaInfo2;
    @FXML
    TextField tabSubmitAbstract_textfieldName3;
    @FXML
    TextArea tabSubmitAbstract_textareaInfo3;
    @FXML
    TextField tabSubmitAbstract_textfieldName4;
    @FXML
    TextArea tabSubmitAbstract_textareaInfo4;
    @FXML
    TextField tabSubmitAbstract_textfieldName5;
    @FXML
    TextArea tabSubmitAbstract_textareaInfo5;
    @FXML
    Button tabSubmitAbstract_buttonClose;
    @FXML
    TextField tabSubmitAbstract_textfieldChooseAbstract;
    @FXML
    TextField tabSubmitAbstract_textfieldNameOfProposal;
    @FXML
    TextField tabSubmitAbstract_textfieldTopics;
    @FXML
    TextField tabSubmitAbstract_textfieldKeywords;

    @FXML
    Button tabSubmitAbstract_buttonChooseAbstract;
    @FXML
    TextField tabSubmitAbstract_textfieldConf;
    @FXML
    TextField tabSubmitAbstract_textfieldSes;

    //TAB Submit Full
    @FXML
    TextField tabSubmitFull_textfieldConf;
    @FXML
    TextField tabSubmitFull_textfieldSes;
    @FXML
    Button tabSubmitFull_buttonChoose;
    @FXML
    Button tabSubmitFull_buttonDone;
    @FXML
    TextField tabSubmitFull_textfieldFile;
    @FXML
    Button tabSubmitFull_buttonClose;

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
    @FXML
    Button buttonCloseSignUp;
    @FXML
    CheckBox checkboxTerms;

    //images
    private Image closeImage = new Image(getClass().getResourceAsStream("/images/close_button.png"));
    private Image plusImage = new Image(getClass().getResourceAsStream("/images/plus3_button.png"));

    //constructor
    public AppViewController() {
    }

    public void setService(IConfServer server) {
        this.server = server;
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void initTabs(){
        //set visible = false for some tabs
        mainTabPane.getTabs().remove(tabSubmitAbstract);
        mainTabPane.getTabs().remove(tabReview);
        mainTabPane.getTabs().remove(tabSubmitFull);
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

    //deschiderea unei noi ferestre, loader = fisierul fxml, title = titlul ferestrei
    private void openNewPage(ActionEvent e, String loader, String title ) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(loader));
        Parent parent = fxmlLoader.load();
        Stage stage = new Stage();
        ((Node) (e.getSource())).getScene().getWindow().hide();
        AppViewController appViewController = fxmlLoader.getController();
        appViewController.setService(server);
        if (loader.equals("/view/registerpage.fxml")) {
            appViewController.initButtonCloseSignUp();
        }
        else if (loader.equals("/view/apppage.fxml")) {
            appViewController.initTabs();
            appViewController.initComboboxRol();
            appViewController.tabAllConf_initTables();
            appViewController.tabCall_initTables();
            appViewController.tabMyConferences_initTables();
        } else if (loader.equals("/view/loginpage.fxml")){
            appViewController.initComponentsLogin();
        }
        stage.setTitle(title);
        stage.setScene(new Scene(parent));
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();
    }

    //-----------------------------------------------------------------------------------------------------------------
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
            userlogat = user;
            //incarca fereastra principala
            openNewPage(e, "/view/apppage.fxml", "Conference Management System");
        } catch (NullPointerException e1) {
            showErrorMessage("Empty fields");
        } catch (IOException | ConfException e1) {
            showErrorMessage("Invalid username or password.");
        }
    }

    public void initComponentsLogin() {
        BooleanBinding textFieldUsernameValid = Bindings.createBooleanBinding(() -> !textfieldUsername.getText().isEmpty(), textfieldUsername.textProperty());
        BooleanBinding textFieldPasswordValid = Bindings.createBooleanBinding(() -> !textfieldPassword.getText().isEmpty(), textfieldPassword.textProperty());

        buttonLogin.disableProperty().bind(textFieldUsernameValid.not().or(textFieldPasswordValid.not()));

    }

    //cand se bifeaza checkbox-ul pentru Terms => enable/disable la butonul Sign Up
    public void handleCheckbox() {
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
//-----------------------------------------------------------------------------------------------------------------
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

    //incarca imagine si tooltip pt butonul de Close din RegisterPage
    private void initButtonCloseSignUp() {
        ImageView imageButtonCloseSignUp = new ImageView(closeImage);
        imageButtonCloseSignUp.setFitHeight(20d);
        imageButtonCloseSignUp.setFitWidth(20d);
        buttonCloseSignUp.setGraphic(imageButtonCloseSignUp);
        Tooltip tooltip = new Tooltip("I don't want to sign up anymore.");
        tooltip.setFont(Font.font("Times New Roman", 16));
        buttonCloseSignUp.setTooltip(tooltip);

        BooleanBinding textFieldFirstNameValid = Bindings.createBooleanBinding(() -> !textfieldFirstNameR.getText().isEmpty(), textfieldFirstNameR.textProperty());
        BooleanBinding textFieldLastNameValid = Bindings.createBooleanBinding(() -> !textfieldLastNameR.getText().isEmpty(), textfieldLastNameR.textProperty());
        BooleanBinding textFieldEmailValid = Bindings.createBooleanBinding(() -> !textfieldEmailR.getText().isEmpty(), textfieldEmailR.textProperty());
        BooleanBinding textFieldUsernameValid = Bindings.createBooleanBinding(() -> !textfieldUsernameR.getText().isEmpty(), textfieldUsernameR.textProperty());
        BooleanBinding textFieldPasswordValid = Bindings.createBooleanBinding(() -> !textfieldPasswordR.getText().isEmpty(), textfieldPasswordR.textProperty());

        checkboxTerms.disableProperty().bind(textFieldUsernameValid.not().or(textFieldPasswordValid.not()).or(textFieldFirstNameValid.not()).or(textFieldLastNameValid.not()).or(textFieldEmailValid.not()));
    }

    //cand se bifeaza checkbox-ul pentru Terms => enable/disable la butonul Sign Up
    public void handleCheckboxTerms() {
        if (buttonSignupR.isDisable()) {
            buttonSignupR.setDisable(false);
        } else {
            buttonSignupR.setDisable(true);
        }
    }

    //revenire la LOGIN PAGE
    public void handleCloseSignUp(ActionEvent e) {
        try {
            openNewPage(e, "/view/loginpage.fxml", "Login");
        } catch (IOException e1) {
            e1.printStackTrace();
            showErrorMessage("Sorry. Cannot open login page.");
        }
    }

//-----------------------------------------------------------------------------------------------------------------
    //Controller MAIN PAGE
    //cand se apasa butonul de logout
    public void handleButtonLogout(ActionEvent e) {
        try {
            server.logout(userlogat, this);
            //deschide LOGIN PAGE
            openNewPage(e, "/view/loginpage.fxml", "Login");
        } catch (ConfException e1) {
            showErrorMessage("Logout Error.");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    //incarca rolurile userului logat
    private void initComboboxRol(){
        comboboxType.getItems().add("viewer");
        try {
            Role[] listRoles = server.getRoles(userlogat);
            for (Role r : listRoles){
                comboboxType.getItems().add(r.getDenumire());
            }
        } catch (ConfException e) {
            e.printStackTrace();
        }
        comboboxType.getSelectionModel().selectFirst();

        comboboxType.getSelectionModel().selectedItemProperty().addListener((ov, oldvalue, newvalue) -> {
            this.tabMyConferences_initTables();
            if (oldvalue.equals("viewer")){
                comboboxType.getItems().remove("viewer");
            }
            if (newvalue.equals("PC Member")){
                tabMyConf_buttonReview.setVisible(true);
            } else {
                tabMyConf_buttonReview.setVisible(false);
            }
        });
    }

    private Role getRol(String rol) throws ConfException {
        for (Role r : server.getRoles(userlogat)){
            if (r.getDenumire().equals(rol)){
                return r;
            }
        }
        return null;
    }

//-----------------------------------------------------------------------------------------------------------------
    //TAB My Conferences
    public void tabMyConferences_handleButtonReview() {
        //se deschide tabul Submit abstract
        mainTabPane.getTabs().add(tabReview);
        mainTabPane.getSelectionModel().select(tabReview);
        initComponentsTabReview();
    }

    private void tabMyConferences_initTables(){
        tabMyConf_columnNameConf.setCellValueFactory(new PropertyValueFactory<>("nume"));
        tabMyConf_columnStartDateConf.setCellValueFactory(new PropertyValueFactory<>("data_inc"));
        tabMyConf_columnEndDateConf.setCellValueFactory(new PropertyValueFactory<>("data_sf"));

        try {
            Role rol = getRol(comboboxType.getValue());
            tabMyConf_tableConf.getSelectionModel().selectedItemProperty().addListener(tabMyConf_changedTableItemListener());

            this.tabMyConf_modelConf = FXCollections.observableArrayList(server.getConferences(userlogat, rol));
            tabMyConf_tableConf.setItems(tabMyConf_modelConf);
            tabMyConf_tableConf.getSelectionModel().selectFirst(); //prima conferinta este selectata by default
        } catch (ConfException e) {
            e.printStackTrace();
        }
    }

    private ChangeListener<Conference> tabMyConf_changedTableItemListener(){
        return (observable, oldvalue, newvalue) -> tabMyConferences_loadSessions(newvalue);
    }

    private void tabMyConferences_loadSessions(Conference conference){
        try {
            tabMyConf_columnNameSes.setCellValueFactory(new PropertyValueFactory<>("nume"));
            tabMyConf_columnDateSes.setCellValueFactory(new PropertyValueFactory<>("data"));
            tabMyConf_columnPriceSes.setCellValueFactory(new PropertyValueFactory<>("pret"));
            tabMyConf_columnStartHourSes.setCellValueFactory(new PropertyValueFactory<>("ora_inc"));
            tabMyConf_columnEndHourSes.setCellValueFactory(new PropertyValueFactory<>("ora_sf"));
            tabMyConf_columnRoomSes.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getSala().getNume()));

            tabMyConf_tableSessions.getItems().clear();

            //aici sesiuni!!
            Role role = getRol(comboboxType.getValue());
            tabMyConf_modelSes = FXCollections.observableArrayList(server.getSesiuniConferintaUserRol(conference, userlogat, role));
            tabMyConf_tableSessions.setItems(tabMyConf_modelSes);
            tabMyConf_tableSessions.getSelectionModel().selectFirst();
        } catch (ConfException e) {
            e.printStackTrace();
        }
    }

//-----------------------------------------------------------------------------------------------------------------
    //TAB All Conferences
    private void tabAllConf_initTables(){
        tabAllConf_columnNameConf.setCellValueFactory(new PropertyValueFactory<>("nume"));
        tabAllConf_columnStartDateConf.setCellValueFactory(new PropertyValueFactory<>("data_inc"));
        tabAllConf_columnEndDateConf.setCellValueFactory(new PropertyValueFactory<>("data_sf"));

        tabAllConf_tableConf.getSelectionModel().selectedItemProperty().addListener(tabAllConf_changedTableItemListener());

        try {
            this.tabAllConf_modelConf = FXCollections.observableArrayList(server.getAllConferences());
            tabAllConf_tableConf.setItems(tabAllConf_modelConf);
            tabAllConf_tableConf.getSelectionModel().selectFirst(); //prima conferinta este selectata by default
        } catch (ConfException e) {
            e.printStackTrace();
        }
    }

    private ChangeListener<Conference> tabAllConf_changedTableItemListener(){
        visiblePay(false);
        return (observable, oldvalue, newvalue) -> tabAllConf_loadSessions(newvalue);
    }

    private void tabAllConf_loadSessions(Conference conference){
        tabAllConf_columnNameSes.setCellValueFactory(new PropertyValueFactory<>("nume"));
        tabAllConf_columnDateSes.setCellValueFactory(new PropertyValueFactory<>("data"));
        tabAllConf_columnPriceSes.setCellValueFactory(new PropertyValueFactory<>("pret"));
        tabAllConf_columnStartHourSes.setCellValueFactory(new PropertyValueFactory<>("ora_inc"));
        tabAllConf_columnEndHourSes.setCellValueFactory(new PropertyValueFactory<>("ora_sf"));
        tabAllConf_columnRoomSes.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getSala().getNume()));

        tabAllConf_tableSessions.getSelectionModel().selectedItemProperty().addListener(tabAllConf_changedTableSesItemListener());

        tabAllConf_tableSessions.getItems().clear();

        try {
            tabAllConf_modelSes = FXCollections.observableArrayList(server.getSessions(conference));
            tabAllConf_tableSessions.setItems(tabAllConf_modelSes);
            tabAllConf_tableSessions.getSelectionModel().selectFirst();
        } catch (ConfException e) {
            e.printStackTrace();
        }
    }

    private ChangeListener<Session> tabAllConf_changedTableSesItemListener() {
        return (observable, oldvalue, newvalue) -> visiblePay(false);
    }

    private void visiblePay(boolean b) {
        tabAllConf_textfieldPay.setVisible(b);
        tabAllConf_buttonPay.setVisible(b);
    }

    public void tabAllConf_handleButtonAttend() {
        Session session = tabAllConf_tableSessions.getSelectionModel().getSelectedItem();
        if (tabAllConf_textfieldPay.isVisible()){
            try {
                Role role = getRol(comboboxType.getValue());
                Conference conference = tabAllConf_tableConf.getSelectionModel().getSelectedItem();
                server.attend(userlogat, role,conference, session);
                showMessage(Alert.AlertType.INFORMATION, "Succes", "Attendance save");
            } catch (ConfException e) {
                showErrorMessage("Attending error");
                e.printStackTrace();
            }
        } else{
            visiblePay(true);
            tabAllConf_textfieldPay.setText(session.getPret().toString());
        }
    }

    public void tabAllConf_handleButtonPay() {
        showMessage(Alert.AlertType.INFORMATION, "Succes", "Successful payment");
    }

    //-----------------------------------------------------------------------------------------------------------------
    //TAB Call for papers
    //cand se apasa butonul "Submit Abstract"
    public void tabCall_handleButtonSubmitAbstract() {
        //se deschide tabul Submit abstract
        mainTabPane.getTabs().add(tabSubmitAbstract);
        mainTabPane.getSelectionModel().select(tabSubmitAbstract);

        initButtonsTabSubmitAbstract();

        //se incarca in textfield numele conferintei si al sesiunii
        tabSubmitAbstract_textfieldConf.setText(tabCall_tableConf.getSelectionModel().getSelectedItem().getNume());
        tabSubmitAbstract_textfieldSes.setText(tabCall_tableSessions.getSelectionModel().getSelectedItem().getNume());
    }

    public void tabCall_handleButtonSubmitFull() {
        //se deschide tabul Submit full
        mainTabPane.getTabs().add(tabSubmitFull);
        mainTabPane.getSelectionModel().select(tabSubmitFull);

        tabSubmitFull_initComponents();

        //se incarca in textfield numele conferintei si al sesiunii
        tabSubmitFull_textfieldConf.setText(tabCall_tableConf.getSelectionModel().getSelectedItem().getNume());
        tabSubmitFull_textfieldSes.setText(tabCall_tableSessions.getSelectionModel().getSelectedItem().getNume());

    }

    private void tabCall_initTables(){
        tabCall_columnNameC.setCellValueFactory(new PropertyValueFactory<>("nume"));
        tabCall_columnStartDateC.setCellValueFactory(new PropertyValueFactory<>("data_inc"));
        tabCall_columnEndDateC.setCellValueFactory(new PropertyValueFactory<>("data_sf"));
        tabCall_columnDeadlineAbstractC.setCellValueFactory(new PropertyValueFactory<>("deadline_abs"));
        tabCall_columnDeadlineFullC.setCellValueFactory(new PropertyValueFactory<>("deadline_full"));

        tabCall_tableConf.getSelectionModel().selectedItemProperty().addListener(changedTableItemListener());

        try {
            this.tabCall_modelConf = FXCollections.observableArrayList(server.getAllConferencesDeadline());
            tabCall_tableConf.setItems(tabCall_modelConf);
            tabCall_tableConf.getSelectionModel().selectFirst(); //prima conferinta este selectata by default
        } catch (ConfException e) {
            e.printStackTrace();
        }
    }

    private ChangeListener<Conference> changedTableItemListener(){
        return (observable, oldvalue, newvalue) -> tabCall_loadSessions(newvalue);
    }

    private void tabCall_loadSessions(Conference conference){
        tabCall_columnNameS.setCellValueFactory(new PropertyValueFactory<>("nume"));
        tabCall_columnDateS.setCellValueFactory(new PropertyValueFactory<>("data"));
        tabCall_columnPriceS.setCellValueFactory(new PropertyValueFactory<>("pret"));
        tabCall_columnStartHourS.setCellValueFactory(new PropertyValueFactory<>("ora_inc"));
        tabCall_columnEndHourS.setCellValueFactory(new PropertyValueFactory<>("ora_sf"));
        tabCall_columnRoomS.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getSala().getNume()));

        tabCall_tableSessions.getItems().clear();

        try {
            tabCall_modelSes = FXCollections.observableArrayList(server.getSessions(conference));
            tabCall_tableSessions.setItems(tabCall_modelSes);
            tabCall_tableSessions.getSelectionModel().selectFirst();
        } catch (ConfException e) {
            e.printStackTrace();
        }
    }

//-----------------------------------------------------------------------------------------------------------------
    //TAB Review
    public void tabReview_handleButtonClose() {
        mainTabPane.getTabs().remove(tabReview);
        mainTabPane.getSelectionModel().select(tabMyConferences);
    }

    private void initComponentsTabReview(){
        //init combobox
        tabReview_comboboxQualifier.getItems().add("strong accept");
        tabReview_comboboxQualifier.getItems().add("accept");
        tabReview_comboboxQualifier.getItems().add("weak accept");
        tabReview_comboboxQualifier.getItems().add("borderline paper");
        tabReview_comboboxQualifier.getItems().add("weak reject");
        tabReview_comboboxQualifier.getItems().add("reject");
        tabReview_comboboxQualifier.getItems().add("strong reject");

        //button close
        ImageView imageButtonCloseReview = new ImageView(closeImage);
        imageButtonCloseReview.setFitHeight(20d);
        imageButtonCloseReview.setFitWidth(20d);
        tabReview_buttonClose.setGraphic(imageButtonCloseReview);
        Tooltip tooltip = new Tooltip("Close review");
        tooltip.setFont(Font.font("Times New Roman", 16));
        tabReview_buttonClose.setTooltip(tooltip);

        //init table
        tabReview_columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tabReview_columnTopic.setCellValueFactory(new PropertyValueFactory<>("topics"));

        tabReview_tableview.getItems().clear();
        try {
            Session session = tabMyConf_tableSessions.getSelectionModel().getSelectedItem();
            tabReview_model = FXCollections.observableArrayList(server.getNameAndTopic(session));
            tabReview_tableview.setItems(tabReview_model);
            tabReview_tableview.getSelectionModel().selectFirst();
        } catch (ConfException e) {
            e.printStackTrace();
        }
    }

    public void tabReview_handleButtonOpenAbstract() {
        try {
            Abstract_Details abstract_details = tabReview_tableview.getSelectionModel().getSelectedItem();
            openFile(server.getAbstract(abstract_details));
        } catch (ConfException e) {
            showErrorMessage("Can't open abstract file");
            e.printStackTrace();
        }
    }

    public void tabReview_handleButtonOpenFull() {
        try{
            Abstract_Details abstract_details = tabReview_tableview.getSelectionModel().getSelectedItem();
            openFile(server.getFull(abstract_details));
        } catch (ConfException e){
            showErrorMessage("Can't open file");
            e.printStackTrace();
        }
    }


    public void tabReview_handleButtonSendReview() {
        try {
            Abstract_Details abstract_details = tabReview_tableview.getSelectionModel().getSelectedItem();
            server.review(abstract_details, tabReview_comboboxQualifier.getSelectionModel().getSelectedItem(), tabReview_textareaRecommendations.getText(), userlogat);
            showMessage(Alert.AlertType.INFORMATION, "Succes", "Successful reviewed");
        } catch (ConfException e) {
            showErrorMessage("Review Error");
            e.printStackTrace();
        }
    }

//-----------------------------------------------------------------------------------------------------------------
    //TAB Submit Abstract
    //apar field'urile pt urmatorul autor
    public void tabSubmitAbstract_handleButtonPlus3() {
        tabSubmitAbstract_buttonPlus4.setVisible(true);
        tabSubmitAbstract_buttonPlus3.setVisible(false);
        tabSubmitAbstract_textareaInfo4.setVisible(true);
        tabSubmitAbstract_textfieldName4.setVisible(true);
    }
    public void tabSubmitAbstract_handleButtonPlus4() {
        tabSubmitAbstract_buttonPlus4.setVisible(false);
        tabSubmitAbstract_textareaInfo5.setVisible(true);
        tabSubmitAbstract_textfieldName5.setVisible(true);
    }

    private void initButtonsTabSubmitAbstract(){
        ImageView imageButtonPlus3 = new ImageView(plusImage);
        imageButtonPlus3.setFitHeight(18d);
        imageButtonPlus3.setFitWidth(18d);
        ImageView imageButtonPlus4 = new ImageView(plusImage);
        imageButtonPlus4.setFitHeight(18d);
        imageButtonPlus4.setFitWidth(18d);
        tabSubmitAbstract_buttonPlus3.setGraphic(imageButtonPlus3);
        tabSubmitAbstract_buttonPlus4.setGraphic(imageButtonPlus4);

        //init button Close
        ImageView imageButtonClose = new ImageView(closeImage);
        imageButtonClose.setFitHeight(18d);
        imageButtonClose.setFitWidth(18d);
        tabSubmitAbstract_buttonClose.setGraphic(imageButtonClose);
        Tooltip tooltip = new Tooltip("I don't want to submit abstract anymore.");
        tooltip.setFont(Font.font("Times New Roman", 16));
        tabSubmitAbstract_buttonClose.setTooltip(tooltip);
    }

    public void handleTabSubmitAbstract_buttonClose() {
        mainTabPane.getTabs().remove(tabSubmitAbstract);
        mainTabPane.getSelectionModel().select(tabCallForPapers);
    }

    //choose a file
    private Desktop desktop = Desktop.getDesktop();
    private final FileChooser fileChooser = new FileChooser();

    private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException ex) {
            ex.printStackTrace();
            showErrorMessage("Error openFile");
        }
    }

    @FXML
    public void tabSubmitAbstract_handleButtonChooseAbstract(){
        configureFileChooser(fileChooser);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            tabSubmitAbstract_textfieldChooseAbstract.setText(file.getAbsolutePath());
            openFile(file);
        }
    }

    private static void configureFileChooser(final FileChooser fileChooser) {
        fileChooser.setTitle("View Files");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*"),
                new FileChooser.ExtensionFilter("PDF", "*.pdf"),
                new FileChooser.ExtensionFilter("DOC", "*.docx","*.doc")
        );
    }

    public void tabSubmitAbstract_handleButtonDone() {
        Session session = tabCall_tableSessions.getSelectionModel().getSelectedItem();
        String detalii_autori = "";
        if (!tabSubmitAbstract_textfieldName1.getText().isEmpty() && !tabSubmitAbstract_textareaInfo1.getText().isEmpty()){
            detalii_autori += tabSubmitAbstract_textfieldName1.getText()+","+tabSubmitAbstract_textareaInfo1.getText()+"|";
        }
        if (!tabSubmitAbstract_textfieldName2.getText().isEmpty() && !tabSubmitAbstract_textareaInfo2.getText().isEmpty()){
            detalii_autori += tabSubmitAbstract_textfieldName2.getText()+","+tabSubmitAbstract_textareaInfo2.getText()+"|";
        }
        if (!tabSubmitAbstract_textfieldName3.getText().isEmpty() && !tabSubmitAbstract_textareaInfo3.getText().isEmpty()){
            detalii_autori += tabSubmitAbstract_textfieldName3.getText()+","+tabSubmitAbstract_textareaInfo3.getText()+"|";
        }
        if (!tabSubmitAbstract_textfieldName4.getText().isEmpty() && !tabSubmitAbstract_textareaInfo4.getText().isEmpty()){
            detalii_autori += tabSubmitAbstract_textfieldName4.getText()+","+tabSubmitAbstract_textareaInfo4.getText()+"|";
        }
        if (!tabSubmitAbstract_textfieldName5.getText().isEmpty() && !tabSubmitAbstract_textareaInfo5.getText().isEmpty()){
            detalii_autori += tabSubmitAbstract_textfieldName5.getText()+","+tabSubmitAbstract_textareaInfo5.getText()+"|";
        }

        try {
            server.submitAbstract(tabSubmitAbstract_textfieldNameOfProposal.getText(), tabSubmitAbstract_textfieldTopics.getText(), tabSubmitAbstract_textfieldKeywords.getText(), tabSubmitAbstract_textfieldChooseAbstract.getText(), detalii_autori, session, userlogat );
            showMessage(Alert.AlertType.INFORMATION, "Succes", "Successful submitted");
        } catch (ConfException e) {
            showErrorMessage("Submit abstract error");
            e.printStackTrace();
        }
    }
//-------------------------------------------------------------------------------------------------------------------------------
    //TAB Submit Full
    public void handleTabSubmitFull_buttonClose() {
        mainTabPane.getTabs().remove(tabSubmitFull);
        mainTabPane.getSelectionModel().select(tabCallForPapers);
    }

    private void tabSubmitFull_initComponents(){
        //init button Close
        ImageView imageButtonClose = new ImageView(closeImage);
        imageButtonClose.setFitHeight(18d);
        imageButtonClose.setFitWidth(18d);
        tabSubmitFull_buttonClose.setGraphic(imageButtonClose);
        Tooltip tooltip = new Tooltip("I don't want to submit paper anymore.");
        tooltip.setFont(Font.font("Times New Roman", 16));
        tabSubmitFull_buttonClose.setTooltip(tooltip);

        BooleanBinding tabSubmitFull_textfieldFileValid = Bindings.createBooleanBinding(() -> !tabSubmitFull_textfieldFile.getText().isEmpty(), tabSubmitFull_textfieldFile.textProperty());
        tabSubmitFull_buttonDone.disableProperty().bind(tabSubmitFull_textfieldFileValid.not());
    }

    public void tabSubmitFull_handleButtonChoose() {
        configureFileChooser(fileChooser);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            tabSubmitFull_textfieldFile.setText(file.getAbsolutePath());
            openFile(file);
        }
    }

    public void tabSubmitFull_handleButtonDone() {
        try {
            Session session = tabCall_tableSessions.getSelectionModel().getSelectedItem();
            server.submitFull(tabSubmitFull_textfieldFile.getText(), session, userlogat );
            showMessage(Alert.AlertType.INFORMATION, "Succes", "Successful submitted");
        } catch (ConfException e) {
            showErrorMessage("Submit full error");
            e.printStackTrace();
        }
    }
}