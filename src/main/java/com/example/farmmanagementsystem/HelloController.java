package com.example.farmmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelloController {

    @FXML
    private Button buttonlogin, buttonregister;

    @FXML
    private AnchorPane pane_login, pane_register;

    @FXML
    private Hyperlink hyperlink, register_hyperlink;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    private TextField register_email;

    @FXML
    private TextField register_username;

    @FXML
    private PasswordField register_password;

    private Connection connect;
    private Statement statement;
    private ResultSet result;
    private PreparedStatement prepare;

    private double xOffset = 0;
    private double yOffset = 0;


    public void exit(){
        System.exit(0);
    }

    public void changeForm(ActionEvent event){
        if(event.getSource() == hyperlink){
            pane_register.setVisible(true);
            pane_login.setVisible(false);
        }else if(event.getSource() == register_hyperlink){
            pane_login.setVisible(true);
            pane_register.setVisible(false);
        }
    }

    public void textfieldDesign(){
        if(username.isFocused()){
            username.setStyle("-fx-background-color: #fff;" + "-fx-border-width: 2px;");
            password.setStyle("-fx-background-color: transparent;" + "-fx-border-width: 1px;");
        }else if(password.isFocused()){
            username.setStyle("-fx-background-color: transparent;" + "-fx-border-width: 1px;");
            password.setStyle("-fx-background-color: #fff;" + "-fx-border-width: 2px;");
        }
    }

    public void login(){
        connect = database.connectDb();

        String sql = "SELECT * FROM konto WHERE nazwa =  ? AND haslo = ?;";

        try{
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, username.getText());
            prepare.setString(2, password.getText());
            result = prepare.executeQuery();

            if(result.next()){
                User.username = result.getString("nazwa");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("System do zarządzania gospodarstwem rolnym");
                alert.setHeaderText(null);
                alert.setContentText("Poprawne logowanie!");
                alert.showAndWait();

                buttonlogin.getScene().getWindow().hide();

                Parent root = FXMLLoader.load(getClass().getResource("account.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();

                root.setOnMousePressed(event -> {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                });
                root.setOnMouseDragged(event -> {
                    stage.setX(event.getScreenX() - xOffset);
                    stage.setY(event.getScreenY() - yOffset);
                });

                stage.initStyle(StageStyle.TRANSPARENT);
                stage.setScene(scene);
                stage.show();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Komunikat o błędzie");
                alert.setHeaderText(null);
                alert.setContentText("Błędna Nazwa użytkownika/Hasło!");
                alert.showAndWait();
            }
        }catch(Exception e){e.printStackTrace();}
    }

    public boolean validationEmail(){
//        Przykład: admin123@admin.pl [pierwsza czesc] [druga czesc] @ [poczta] . [pl]

        Pattern pattern = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher match = pattern.matcher(register_email.getText());

        if(match.find() && match.group().equals(register_email.getText())){
            return true;
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Komunikat o błędzie");
            alert.setHeaderText(null);
            alert.setContentText("Błędny e-mail");
            alert.showAndWait();

            return false;
        }
    }

    public void signup(){
        connect = database.connectDb();

        String sql = "INSERT INTO konto (nazwa, haslo, email) VALUES (?, ?, ?);";

        try{
            if(register_email.getText().isEmpty() | register_password.getText().isEmpty() | register_username.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Komunikat o błędzie");
                alert.setHeaderText(null);
                alert.setContentText("Wprowadź wszystkie dane!");
                alert.showAndWait();
            }else if(register_password.getText().length() < 8){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Komunikat o błędzie");
                alert.setHeaderText(null);
                alert.setContentText("Błędne hasło");
                alert.showAndWait();
            }else{
                if(validationEmail()) {

                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, register_username.getText());
                    prepare.setString(2, register_password.getText());
                    prepare.setString(3, register_email.getText());
                    prepare.execute();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("System do zarządzania gospodarstwem rolnym");
                    alert.setHeaderText(null);
                    alert.setContentText("Poprawnie utworzone konto!");
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize(){}
}