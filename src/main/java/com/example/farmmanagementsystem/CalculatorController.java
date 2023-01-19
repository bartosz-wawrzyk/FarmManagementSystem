package com.example.farmmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculatorController {

    @FXML
    private Button button_calculate, button_info;

    @FXML
    private Button button_plantstocking, button_seedrate, button_spraying, button_dryingcorn;

    @FXML
    private Label label_result, label_resultCast;

    @FXML
    private AnchorPane pane_info, pane_seedrate, pane_cast, pane_spraying, pane_dryingcorn;

    @FXML
    private TextField txt_cast;

    @FXML
    private TextField txt_force;

    @FXML
    private TextField txt_mtz;

    @FXML
    private TextField txt_seeds;

    @FXML
    private TextField txt_length, txt_width, txt_amount;

    @FXML
    private TextField corn_mass, corn_initial, corn_final;

    @FXML
    private Button corn_calculate, corn_clear;

    @FXML
    private Label corn_result;

    @FXML
    private TextField spraying_dose, spraying_area;

    @FXML
    private Label spraying_result;

    public void initialize(){
        showInfo();
        changeCalculate();
    }

    public static double round(double value) {
        int precision = 2;
        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(precision, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

    // Seed calculate *******************************
    public void clear(){
        txt_mtz.setText("");
        txt_cast.setText("");
        txt_force.setText("");
        txt_seeds.setText("");
        label_result.setText("");
        label_result.setStyle("-fx-border-color: white; -fx-border-radius: 0px");
    }

    public void calculateSeedRate(){
        double mtz = Double.parseDouble(txt_mtz.getText());
        double cast = Double.parseDouble(txt_cast.getText());
        double force = Double.parseDouble(txt_force.getText());
        double seeds = Double.parseDouble(txt_seeds.getText());

        double result = ((mtz * cast) * 100 / (force * seeds)) * 1.2;

        label_result.setText(round(result) + " kg/ha");

        label_result.setStyle("-fx-border-color: linear-gradient(to bottom right, #168179, #25894A); -fx-border-radius: 10px");
    }

    public void showInfo(){
        button_info.setOnMouseEntered(mouseEvent -> {
            pane_info.setVisible(true);
        });
        button_info.setOnMouseExited(mouseEvent -> {
            pane_info.setVisible(false);
        });
    }

    // Plant calculate *****************************
    public void clearPlantStocking(){
        txt_amount.setText("");
        txt_length.setText("");
        txt_width.setText("");
        label_resultCast.setText("");
        label_resultCast.setStyle("-fx-border-color: white; -fx-border-radius: 0px");
    }

    public void calculatePlantStocking(){
        double amount = Double.parseDouble(txt_amount.getText());
        double width = Double.parseDouble(txt_width.getText());
        double length = Double.parseDouble(txt_length.getText());

        double result = amount * width * length;

        label_resultCast.setText(round(result) + " szt./m2");

        label_resultCast.setStyle("-fx-border-color: linear-gradient(to bottom right, #168179, #25894A); -fx-border-radius: 10px");
    }

    // Corn calculate *********************************
    public void CornCalculate(){
        double mass = Double.parseDouble(corn_mass.getText());
        double initial = Double.parseDouble(corn_initial.getText());
        double c_final = Double.parseDouble(corn_final.getText());

        double result = mass * (100 - initial) / (100-c_final);

        corn_result.setText(round(result) + " kg");

        corn_result.setStyle("-fx-border-color: linear-gradient(to bottom right, #168179, #25894A); -fx-border-radius: 10px");
    }

    public void CornClear(){
        corn_mass.setText("");
        corn_initial.setText("");
        corn_final.setText("");
        corn_result.setText("");
        corn_result.setStyle("-fx-border-color: white; -fx-border-radius: 0px");
    }

    // Spraying calcualte
    public void SprayingCalculate() {
        Double area = Double.parseDouble(spraying_area.getText());
        Double dose = Double.parseDouble(spraying_dose.getText());

        Double result = area * dose;

        spraying_result.setText(round(result) + " l");

        spraying_result.setStyle("-fx-border-color: linear-gradient(to bottom right, #168179, #25894A); -fx-border-radius: 10px");
    }

    public void SprayingClear(){
        spraying_area.setText("");
        spraying_dose.setText("");
        spraying_result.setText("");
        spraying_result.setStyle("-fx-border-color: white; -fx-border-radius: 0px");
    }

    // change pane *************************************
    public void changeCalculate(){
        button_seedrate.setOnMouseClicked(mouseEvent -> {
            pane_seedrate.setVisible(true);
            pane_cast.setVisible(false);
            pane_spraying.setVisible(false);
            pane_dryingcorn.setVisible(false);
        });
        button_plantstocking.setOnMouseClicked(mouseEvent -> {
            pane_seedrate.setVisible(false);
            pane_cast.setVisible(true);
            pane_spraying.setVisible(false);
            pane_dryingcorn.setVisible(false);
        });
        button_spraying.setOnMouseClicked(mouseEvent -> {
            pane_seedrate.setVisible(false);
            pane_cast.setVisible(false);
            pane_spraying.setVisible(true);
            pane_dryingcorn.setVisible(false);
        });
        button_dryingcorn.setOnMouseClicked(mouseEvent -> {
            pane_seedrate.setVisible(false);
            pane_cast.setVisible(false);
            pane_spraying.setVisible(false);
            pane_dryingcorn.setVisible(true);
        });
    }

    @FXML
    void exit() {System.exit(0);}
}
