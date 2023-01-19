package com.example.farmmanagementsystem;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

import java.sql.*;
import java.util.Date;
import java.util.Optional;

public class GarageController {

    @FXML
    private Button button_add;

    @FXML
    private Button button_delete;

    @FXML
    private Button button_edit;

    @FXML
    private TableView<Garage> tableviewGarage;

    @FXML
    private TableColumn<Garage, Integer> col_id;

    @FXML
    private TableColumn<Garage, String> col_name;

    @FXML
    private TableColumn<Garage, String> col_model;

    @FXML
    private TableColumn<Garage, String> col_type;

    @FXML
    private TableColumn<Garage, Integer> col_year;

    @FXML
    private TableColumn<Garage, String> col_review;

    @FXML
    private TableColumn<Garage, String> col_number;

    @FXML
    private TextField id;

    @FXML
    private TextField model;

    @FXML
    private TextField name;

    @FXML
    private TextField number;

    @FXML
    private TextField type_vehical;

    @FXML
    private TextField year;

    @FXML
    private DatePicker date_review;


    //    DATABASE TOOL
    private Connection connect;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;

    public void initialize(){
        showData();
    }

    public void insert(){

        connect = database.connectDb();

        String sql = "INSERT INTO garaz VALUES(?, ?, ?, ?, ?, ?, ?)";

        String txt_date_review = String.valueOf(date_review.getValue());

        try{
            if(id.getText().isEmpty() | name.getText().isEmpty()
                    | model.getText().isEmpty()
                    | year.getText().isEmpty()
                    | type_vehical.getText().isEmpty()
                    | number.getText().isEmpty()){

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Komunikat o błędzie");
                alert.setHeaderText(null);
                alert.setContentText("Uzupełnij wszystkie pola!");
                alert.showAndWait();
            }else{
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, id.getText());
                prepare.setString(2, name.getText());
                prepare.setString(3, model.getText());
                prepare.setString(4, type_vehical.getText());
                prepare.setString(5, year.getText());
                prepare.setString(6, txt_date_review);
                prepare.setString(7, number.getText());

                prepare.executeUpdate();

                showData();
                clear();
            }
        }catch(Exception e){}

    }

    public void clear(){
        id.setText("");
        name.setText("");
        model.setText("");
        type_vehical.setText("");
        year.setText("");
        number.setText("");
    }

    public void select(){
        int num = tableviewGarage.getSelectionModel().getSelectedIndex();

        Garage garage = tableviewGarage.getSelectionModel().getSelectedItem();

        if(num-1 < -1)
            return;

        id.setText(String.valueOf(garage.getId()));
        name.setText(garage.getName());
        model.setText(garage.getModel());
        type_vehical.setText(garage.getType_vehical());
        year.setText(String.valueOf(garage.getYear()));
        number.setText(String.valueOf(garage.getNumber()));
        String txt_date_review = String.valueOf(date_review.getValue());
        date_review.setAccessibleHelp(txt_date_review);
    }

    public void delete() throws SQLException {
        String sql = "DELETE FROM garaz WHERE id = '" + id.getText() + "'";

        connect = database.connectDb();

        Alert alert;
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Wiadomość potwierdzająca");
        alert.setHeaderText(null);
        alert.setContentText("Czy napewno chcesz usunąć pojazd o ID: " + id.getText() + "?");
        Optional<ButtonType> option = alert.showAndWait();

        if (option.get().equals(ButtonType.OK)) {
            statement = connect.createStatement();
            statement.executeUpdate(sql);

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Wiadomość informacyjna");
            alert.setHeaderText(null);
            alert.setContentText("Usuwanie poprawne!");
            alert.showAndWait();

            showData();
            clear();
        }
    }

    public ObservableList<Garage> listData(){
        ObservableList<Garage> dataList = FXCollections.observableArrayList();

        connect = database.connectDb();
        String sql = "SELECT * FROM garaz";

        try{

            statement = connect.createStatement();
            result = statement.executeQuery(sql);

            Garage garage;

            while(result.next()){
//                MAKE SURE THAT THEY ARE SAME ON THE NAME OF EVERY COLUMN
                garage = new Garage(result.getInt("id"),
                        result.getString("nazwa")
                        , result.getString("model")
                        , result.getString("typ")
                        , result.getInt("rok")
                        , result.getString("przeglad")
                        , result.getString("numer"));

                dataList.add(garage);
            }
        }catch(Exception e){}

        return dataList;
    }

    public void showData(){
        ObservableList<Garage> show = listData();

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_model.setCellValueFactory(new PropertyValueFactory<>("model"));
        col_type.setCellValueFactory(new PropertyValueFactory<>("type_vehical"));
        col_year.setCellValueFactory(new PropertyValueFactory<>("year"));
        col_review.setCellValueFactory(new PropertyValueFactory<>("date_review"));
        col_number.setCellValueFactory(new PropertyValueFactory<>("number"));

        tableviewGarage.setItems(show);
    }

    public void exit(){
        System.exit(0);
    }
}