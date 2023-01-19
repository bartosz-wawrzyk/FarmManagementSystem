package com.example.farmmanagementsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Optional;

public class AccountController {

    @FXML
    private Label label_name;

    @FXML
    private AnchorPane pane_main;

    @FXML
    private Button button_home, button_garage, button_logout, button_calculator, button_plot;

    @FXML
    private Button button_breeding, button_warehouse, button_income, button_expenses, button_module;

    public void account_name(){
        label_name.setText(User.username);
    }

    private double xOffset = 0;
    private double yOffset = 0;

    public void logout() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Czy napewno chcesz się wylogować?");
        Optional<ButtonType> option = alert.showAndWait();
        try {
            if (option.get().equals(ButtonType.OK)) {

                button_logout.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed(event -> {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                });
                root.setOnMouseDragged(event -> {
                    stage.setX(event.getScreenX() - xOffset);
                    stage.setY(event.getScreenY() - yOffset);
                });

                root.setOnMouseReleased( event -> {
                    stage.setOpacity(1);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void changePane(){
        if(button_home.isFocused()){
            try{
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("pane_main.fxml"));
                Pane pane = null;
                try {
                    pane = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                setScreen(pane);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if(button_garage.isFocused()){
            try{
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("pane_garage.fxml"));
                Pane pane = null;
                try {
                    pane = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                setScreen(pane);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if(button_breeding.isFocused()){
            try{
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("pane_breeding.fxml"));
                Pane pane = null;
                try {
                    pane = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                setScreen(pane);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if(button_warehouse.isFocused()){
            try{
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("pane_warehouse.fxml"));
                Pane pane = null;
                try {
                    pane = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                setScreen(pane);
            } catch (Exception e) {
                e.printStackTrace();
            }
         }else if(button_income.isFocused()){
            try{
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("pane_income.fxml"));
                Pane pane = null;
                try {
                    pane = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                setScreen(pane);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(button_expenses.isFocused()){
            try{
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("pane_expenses.fxml"));
                Pane pane = null;
                try {
                    pane = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                setScreen(pane);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if(button_calculator.isFocused()){
            try{
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("pane_calculator.fxml"));
                Pane pane = null;
                try {
                    pane = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                setScreen(pane);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if(button_plot.isFocused()){
            try{
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("pane_plot.fxml"));
                Pane pane = null;
                try {
                    pane = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                setScreen(pane);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if(button_module.isFocused()){
            try{
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("pane_module.fxml"));
                Pane pane = null;
                try {
                    pane = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                setScreen(pane);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void setScreen(Pane pane) {
        this.pane_main.getChildren().clear();
        this.pane_main.getChildren().add(pane);
    }


    public void menuButton(){
        button_home.setOnMouseClicked(mouseEvent -> {
            button_home.setStyle("-fx-background-color: linear-gradient(to bottom right," + "#126963, #1C6F3B);");
            button_garage.setStyle("-fx-background-color: transparent");
            button_calculator.setStyle("-fx-background-color: transparent");
            button_plot.setStyle("-fx-background-color: transparent");
            button_breeding.setStyle("-fx-background-color: transparent");
            button_warehouse.setStyle("-fx-background-color: transparent");
            button_income.setStyle("-fx-background-color: transparent");
            button_expenses.setStyle("-fx-background-color: transparent");
            button_module.setStyle("-fx-background-color: transparent");
        });

        button_garage.setOnMouseClicked(mouseEvent -> {
            button_home.setStyle("-fx-background-color: transparent");
            button_garage.setStyle("-fx-background-color: linear-gradient(to bottom right," + "#126963, #1C6F3B);");
            button_calculator.setStyle("-fx-background-color: transparent");
            button_plot.setStyle("-fx-background-color: transparent");
            button_breeding.setStyle("-fx-background-color: transparent");
            button_warehouse.setStyle("-fx-background-color: transparent");
            button_income.setStyle("-fx-background-color: transparent");
            button_expenses.setStyle("-fx-background-color: transparent");
            button_module.setStyle("-fx-background-color: transparent");
        });

        button_calculator.setOnMouseClicked(mouseEvent -> {
            button_home.setStyle("-fx-background-color: transparent");
            button_garage.setStyle("-fx-background-color: transparent");
            button_calculator.setStyle("-fx-background-color: linear-gradient(to bottom right," + "#126963, #1C6F3B);");
            button_plot.setStyle("-fx-background-color: transparent");
            button_breeding.setStyle("-fx-background-color: transparent");
            button_warehouse.setStyle("-fx-background-color: transparent");
            button_income.setStyle("-fx-background-color: transparent");
            button_expenses.setStyle("-fx-background-color: transparent");
            button_module.setStyle("-fx-background-color: transparent");
        });

        button_breeding.setOnMouseClicked(mouseEvent -> {
            button_home.setStyle("-fx-background-color: transparent");
            button_garage.setStyle("-fx-background-color: transparent");
            button_calculator.setStyle("-fx-background-color: transparent");
            button_plot.setStyle("-fx-background-color: transparent");
            button_breeding.setStyle("-fx-background-color: linear-gradient(to bottom right," + "#126963, #1C6F3B);");
            button_warehouse.setStyle("-fx-background-color: transparent");
            button_income.setStyle("-fx-background-color: transparent");
            button_expenses.setStyle("-fx-background-color: transparent");
            button_module.setStyle("-fx-background-color: transparent");
        });

        button_warehouse.setOnMouseClicked(mouseEvent -> {
            button_home.setStyle("-fx-background-color: transparent");
            button_garage.setStyle("-fx-background-color: transparent");
            button_calculator.setStyle("-fx-background-color: transparent");
            button_plot.setStyle("-fx-background-color: transparent");
            button_breeding.setStyle("-fx-background-color: transparent");
            button_warehouse.setStyle("-fx-background-color: linear-gradient(to bottom right," + "#126963, #1C6F3B);");
            button_income.setStyle("-fx-background-color: transparent");
            button_expenses.setStyle("-fx-background-color: transparent");
            button_module.setStyle("-fx-background-color: transparent");
        });

        button_income.setOnMouseClicked(mouseEvent -> {
            button_home.setStyle("-fx-background-color: transparent");
            button_garage.setStyle("-fx-background-color: transparent");
            button_calculator.setStyle("-fx-background-color: transparent");
            button_plot.setStyle("-fx-background-color: transparent");
            button_breeding.setStyle("-fx-background-color: transparent");
            button_warehouse.setStyle("-fx-background-color: transparent");
            button_income.setStyle("-fx-background-color: linear-gradient(to bottom right," + "#126963, #1C6F3B);");
            button_expenses.setStyle("-fx-background-color: transparent");
            button_module.setStyle("-fx-background-color: transparent");
        });

        button_expenses.setOnMouseClicked(mouseEvent -> {
            button_home.setStyle("-fx-background-color: transparent");
            button_garage.setStyle("-fx-background-color: transparent");
            button_calculator.setStyle("-fx-background-color: transparent");
            button_plot.setStyle("-fx-background-color: transparent");
            button_breeding.setStyle("-fx-background-color: transparent");
            button_warehouse.setStyle("-fx-background-color: transparent");
            button_income.setStyle("-fx-background-color: transparent");
            button_expenses.setStyle("-fx-background-color: linear-gradient(to bottom right," + "#126963, #1C6F3B);");
            button_module.setStyle("-fx-background-color: transparent");
        });

        button_plot.setOnMouseClicked(mouseEvent -> {
            button_home.setStyle("-fx-background-color: transparent");
            button_garage.setStyle("-fx-background-color: transparent");
            button_calculator.setStyle("-fx-background-color: transparent");
            button_plot.setStyle("-fx-background-color: linear-gradient(to bottom right," + "#126963, #1C6F3B);");
            button_breeding.setStyle("-fx-background-color: transparent");
            button_warehouse.setStyle("-fx-background-color: transparent");
            button_income.setStyle("-fx-background-color: transparent");
            button_expenses.setStyle("-fx-background-color: transparent");
            button_module.setStyle("-fx-background-color: transparent");
        });
        button_module.setOnMouseClicked(mouseEvent -> {
            button_home.setStyle("-fx-background-color: transparent");
            button_garage.setStyle("-fx-background-color: transparent");
            button_calculator.setStyle("-fx-background-color: transparent");
            button_plot.setStyle("-fx-background-color: transparent");
            button_breeding.setStyle("-fx-background-color: transparent");
            button_warehouse.setStyle("-fx-background-color: transparent");
            button_income.setStyle("-fx-background-color: transparent");
            button_expenses.setStyle("-fx-background-color: transparent");
            button_module.setStyle("-fx-background-color: linear-gradient(to bottom right," + "#126963, #1C6F3B);");
        });
    }

    public void initialize(){

        account_name();
        menuButton();

        try{
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("pane_main.fxml"));
            Pane pane = null;
            try {
                pane = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            setScreen(pane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
