package iss.client.gui;

import iss.model.Conference;
import iss.model.Sala;
import iss.model.Session;
import iss.model.User;
import iss.services.ConfException;
import iss.services.IConfClient;
import iss.services.IConfServer;
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
import java.util.List;
import java.util.concurrent.locks.Condition;

/**
 * Created by Dana on 16-May-17.
 */
public class AppViewController implements IConfClient {
    private IConfServer server;
    private Stage stage;

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
    Button buttonLogout;

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
    TableColumn<Session, Sala> tabAllConf_columnRoomSes;
    @FXML
    TableColumn<Session, Integer> tabAllConf_columnPriceSes;
    @FXML
    TableColumn<Session, String> tabAllConf_columnStartHourSes;
    @FXML
    TableColumn<Session, String> tabAllConf_columnEndHourSes;

    ObservableList<Conference> tabAllConf_modelConf;
    ObservableList<Session> tabAllConf_modelSes;


    //TAB My Conferences
    @FXML
    TableView<Conference> tabMyConf_tableConf;
    @FXML
    TableView<Session> tabMyConf_tableSessions;

    //TAB Call for papers
    @FXML
    Button buttonSubmitAbstract;
    @FXML
    Button buttonSubmitFull;
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
//    @FXML
//    TableColumn<Conference, String> tabCall_columnNameC;

    ObservableList<Conference> tabCall_modelConf;



    //TAB Review
    @FXML
    Button tabReview_buttonSendReview;
    @FXML
    ComboBox<String> tabReview_comboboxQualifier;
    @FXML
    Button tabReview_buttonClose;

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
    Button tabSubmitAbstract_buttonChooseAbstract;

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

    private void initButtonCloseSignUp() {
        //incarca imagine si tooltip pt butonul de Close din RegisterPage
        ImageView imageButtonCloseSignUp = new ImageView(closeImage);
        imageButtonCloseSignUp.setFitHeight(20d);
        imageButtonCloseSignUp.setFitWidth(20d);
        buttonCloseSignUp.setGraphic(imageButtonCloseSignUp);
        Tooltip tooltip = new Tooltip("I don't want to sign up anymore.");
        tooltip.setFont(Font.font("Times New Roman", 16));
        buttonCloseSignUp.setTooltip(tooltip);
    }

    public void tabAllConf_initTables(){
        tabAllConf_columnNameConf.setCellValueFactory(new PropertyValueFactory<>("nume"));
        tabAllConf_columnStartDateConf.setCellValueFactory(new PropertyValueFactory<>("data_inc"));
        tabAllConf_columnEndDateConf.setCellValueFactory(new PropertyValueFactory<>("data_sf"));
//        tabAllConf_tableConf.getSelectionModel().selectedItemProperty().addListener(changedTableItemListener());
//        this.tabAllConf_modelConf = FXCollections.observableArrayList(getAll());
//        tabAllConf_tableConf.setItems(tabAllConf_modelConf);
//        tabAllConf_tableConf.getSelectionModel().selectFirst(); //prima conferinta este selectata by default

        try {
            this.tabAllConf_modelConf = FXCollections.observableArrayList(server.getAllConferencesDeadline());
            tabAllConf_tableConf.setItems(tabAllConf_modelConf);
            tabAllConf_tableConf.getSelectionModel().selectFirst(); //prima conferinta este selectata by default
        } catch (ConfException e) {
            e.printStackTrace();
        }

//        tabAllConf_columnNameSes.setCellValueFactory(new PropertyValueFactory<Session, String>("nume"));
    }

    public void tabCall_initTables(){
        tabCall_columnNameC.setCellValueFactory(new PropertyValueFactory<>("nume"));
        tabCall_columnStartDateC.setCellValueFactory(new PropertyValueFactory<>("data_inc"));
        tabCall_columnEndDateC.setCellValueFactory(new PropertyValueFactory<>("data_sf"));
        tabCall_columnDeadlineAbstractC.setCellValueFactory(new PropertyValueFactory<>("deadline_abs"));
        tabCall_columnDeadlineFullC.setCellValueFactory(new PropertyValueFactory<>("deadline_full"));

        try {
            this.tabAllConf_modelConf = FXCollections.observableArrayList(server.getAllConferences());
            tabAllConf_tableConf.setItems(tabAllConf_modelConf);
            tabAllConf_tableConf.getSelectionModel().selectFirst(); //prima conferinta este selectata by default
        } catch (ConfException e) {
            e.printStackTrace();
        }

//        tabAllConf_tableConf.getSelectionModel().selectedItemProperty().addListener(changedTableItemListener());
//        this.tabCall_modelConf = FXCollections.observableArrayList(getAll());
//        tabCall_tableConf.setItems(tabCall_modelConf);
//        tabCall_tableConf.getSelectionModel().selectFirst(); //prima conferinta este selectata by default
    }

//    private List<Conference> getAll(){
//        Conference c1 = new Conference(1,"Forbes Women's Summit", "2017-20-01","2017-02-02", "2017-30-01","2017-30-02");
//        Conference c2 = new Conference(2,"Grace Hopper Celebration of Women in Computing", "2017-20-01","2017-02-02", "2017-30-01","2017-30-02");
//        Conference c3 = new Conference(3,"Social Media Week", "2017-20-01","2017-02-02", "2017-30-01","2017-30-02");
//        Conference c4 = new Conference(4,"Funnel Network of Marketing", "2017-20-01","2017-02-02", "2017-30-01","2017-30-02");
//        Conference c5 = new Conference(5,"Content Marketing World", "2017-20-01","2017-02-02", "2017-30-01","2017-30-02");
//        Conference c6 = new Conference(6,"UserConf", "2017-20-01","2017-02-02", "2017-30-01","2017-30-02");
//        Conference c7 = new Conference(7,"Social Media for Customer Service Summit", "2017-20-01","2017-02-02", "2017-30-01","2017-30-02");
//        Conference c8 = new Conference(8,"CX Impact", "2017-20-01","2017-02-02", "2017-30-01","2017-30-02");
//        Conference c9 = new Conference(9,"Funnel Network of Marketing", "2017-20-01","2017-02-02", "2017-30-01","2017-30-02");
//
//        List<Conference> conf = new ArrayList<>();
//        conf.add(c1);
//        conf.add(c2);
//        conf.add(c3);
//        conf.add(c4);
//        conf.add(c5);
//        conf.add(c6);
//        conf.add(c7);
//        conf.add(c8);
//        conf.add(c9);
//
//        return conf;
//    }

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

    private void initTabs(){
        //set visible = false for some tabs
        mainTabPane.getTabs().remove(tabSubmitAbstract);
        mainTabPane.getTabs().remove(tabReview);
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
            appViewController.initButtonsTabSubmitAbstract();
            appViewController.initComponentsTabReview();
        } else if (loader.equals("/view/apppage.fxml")){
            appViewController.tabAllConf_initTables();
            appViewController.tabCall_initTables();
        }
        stage.setTitle(title);
        stage.setScene(new Scene(parent));
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();
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

    //revenire la LOGIN PAGE
    public void handleCloseSignUp(ActionEvent e) {
        try {
            openNewPage(e, "/view/loginpage.fxml", "Login");
        } catch (IOException e1) {
            e1.printStackTrace();
            showErrorMessage("Sorry. Cannot open login page.");
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

    //TAB My Conferences
    public void tabMyConferences_handleButtonReview(ActionEvent actionEvent) {
        //se deschide tabul Submit abstract
        mainTabPane.getTabs().add(tabReview);
        mainTabPane.getSelectionModel().select(tabReview);
    }

    //TAB Call for papers
    //cand se apasa butonul "Submit Abstract"
    public void tabCallForPapers_handleButtonSubmitAbstract(ActionEvent actionEvent) {
        //se deschide tabul Submit abstract
        mainTabPane.getTabs().add(tabSubmitAbstract);
        mainTabPane.getSelectionModel().select(tabSubmitAbstract);
    }

    //TAB Review
    public void tabReview_handleButtonClose(ActionEvent actionEvent) {
        mainTabPane.getTabs().remove(tabReview);
        mainTabPane.getSelectionModel().select(tabMyConferences);

    }

    //TAB Submit Abstract
    //apar field'urile pt urmatorul autor
    public void tabSubmitAbstract_handleButtonPlus3(ActionEvent actionEvent) {
        tabSubmitAbstract_buttonPlus4.setVisible(true);
        tabSubmitAbstract_buttonPlus3.setVisible(false);

        tabSubmitAbstract_textareaInfo4.setVisible(true);
        tabSubmitAbstract_textfieldName4.setVisible(true);
    }
    public void tabSubmitAbstract_handleButtonPlus4(ActionEvent actionEvent) {
        tabSubmitAbstract_buttonPlus4.setVisible(false);

        tabSubmitAbstract_textareaInfo5.setVisible(true);
        tabSubmitAbstract_textfieldName5.setVisible(true);
    }

    public void handleTabSubmitAbstract_buttonClose(ActionEvent actionEvent) {
        mainTabPane.getTabs().remove(tabSubmitAbstract);
        mainTabPane.getSelectionModel().select(tabCallForPapers);
    }

    //choose a file
    private Desktop desktop = Desktop.getDesktop();
    final FileChooser fileChooser = new FileChooser();

    private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException ex) {
            ex.printStackTrace();
            showErrorMessage("Error openFile");
        }
    }

    @FXML
    public void handleButtonChooseAbstract(ActionEvent e){
        configureFileChooser(fileChooser);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            tabSubmitAbstract_textfieldChooseAbstract.setText(file.getName());
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
}