package com.example.farmmanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class WarehouseController {

    @FXML
    private Button button_add;

    @FXML
    private Button button_clear;

    @FXML
    private Button button_delete;

    @FXML
    private Button button_edit;

    @FXML
    private TableColumn<Warehouse, String> col_category;

    @FXML
    private TableColumn<Warehouse, Float> col_count;

    @FXML
    private TableColumn<Warehouse, Date> col_date;

    @FXML
    private TableColumn<Warehouse, Integer> col_id;

    @FXML
    private TableColumn<Warehouse, String> col_name;

    @FXML
    private TableColumn<Warehouse, String> col_type;

    @FXML
    private TableColumn<Warehouse, Float> col_value;

    @FXML
    private ComboBox<Warehouse> combobox_category;

    @FXML
    private ComboBox<Warehouse> combobox_type;

    @FXML
    private DatePicker date_expiration;

    @FXML
    private TableView<Warehouse> tableviewWarehouse;

    @FXML
    private TextField txt_count;

    @FXML
    private TextField txt_id;

    @FXML
    private TextField txt_name;

    @FXML
    private TextField txt_value;

    //    DATABASE TOOL
    private Connection connect;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;

    public void initialize(){
        showData();
        comboboxCategory();
        comboboxType();
    }

    @FXML
    void clear() {
        txt_id.setText("");
        txt_name.setText("");
        txt_count.setText("");
        //combobox_type.;
        txt_value.setText("");

        //combobox_category.getSelectionModel().getSelectedItem();
    }

    @FXML
    void delete() throws SQLException{
        String sql = "DELETE FROM magazyn WHERE id = '" + txt_id.getText() + "'";

        Alert alert;
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Wiadomość potwierdzająca");
        alert.setHeaderText(null);
        alert.setContentText("Czy napewno chcesz usunąć element o ID: " + txt_id.getText() + "?");
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

    @FXML
    void insert() {
        connect = database.connectDb();

        String sql = "INSERT INTO magazyn VALUES (?,?,?,?,?,?,?)";

        String txt_date_expiration = String.valueOf(date_expiration.getValue());

        try{
            if(txt_id.getText().isEmpty() | txt_name.getText().isEmpty()
                    | txt_count.getText().isEmpty()
                    | txt_value.getText().isEmpty()
                    | combobox_category.getSelectionModel().isEmpty()
                    | combobox_type.getSelectionModel().isEmpty()){

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Komunikat o błędzie");
                alert.setHeaderText(null);
                alert.setContentText("Uzupełnij wszystkie pola!");
                alert.showAndWait();
            }else {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, txt_id.getText());
                prepare.setString(2, txt_name.getText());
                prepare.setString(3, txt_count.getText());
                prepare.setString(4, String.valueOf(combobox_type.getSelectionModel().getSelectedItem()));
                prepare.setString(5, txt_value.getText());
                prepare.setString(6, txt_date_expiration);
                prepare.setString(7, String.valueOf(combobox_category.getSelectionModel().getSelectedItem()));

                prepare.executeUpdate();

                showData();
                clear();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void update() {
        connect = database.connectDb();

        String txt_date_expiration = String.valueOf(date_expiration.getValue());

        String sql = "UPDATE magazyn SET id = '" + txt_id.getText()
                + "', nazwa = '" + txt_name.getText()
                + "', ilosc = '" + txt_count.getText()
                + "', typ = '" + combobox_type.getSelectionModel().getSelectedItem()
                + "', wartosc = '" + txt_value.getText()
                + "', data_waznosci = '" + txt_date_expiration
                + "', kategoria = '" + combobox_category.getSelectionModel().getSelectedItem()
                + "' WHERE id = '" + txt_id.getText() + "'";

        try {
            if (txt_id.getText().isEmpty() | txt_name.getText().isEmpty()
                    | txt_count.getText().isEmpty()
                    | combobox_type.getSelectionModel().isEmpty()
                    | txt_value.getText().isEmpty()
                    | combobox_category.getSelectionModel().isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setTitle("Komunikat o błędzie");
                alert.setHeaderText(null);
                alert.setContentText("Sprawdź poprawność danych!");
                alert.showAndWait();

            } else {

                statement = connect.createStatement();
                statement.executeUpdate(sql);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("System do zarządzania gospodarstwem rolnym");
                alert.setHeaderText(null);
                alert.setContentText("Aktualizacja danych wykonana poprawnie!");
                alert.showAndWait();


                showData();
                clear();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void select() {
        int num = tableviewWarehouse.getSelectionModel().getSelectedIndex();

        Warehouse warehouse = tableviewWarehouse.getSelectionModel().getSelectedItem();

        if(num-1 < -1)
            return;

        txt_id.setText(String.valueOf(warehouse.getId()));
        txt_name.setText(warehouse.getName());
        txt_count.setText(String.valueOf(warehouse.getCount()));
        combobox_type.getSelectionModel().getSelectedItem();
        txt_value.setText(String.valueOf(warehouse.getValue()));
        String txt_date_review = String.valueOf(date_expiration.getValue());
        date_expiration.setAccessibleHelp(txt_date_review);
        combobox_category.getSelectionModel().getSelectedItem();
    }

    private String categoryData[] = {"Sadzonki", "Materiały siewne", "Nawozy", "ŚOR", "Produkty rolne", "Inne"};
    private String typeData[] = {"t", "kg", "g", "szt", "l", "ml"};

    public void comboboxCategory(){
        List<String> list = new ArrayList<>();
        for(String data: categoryData){
            list.add(data);
        }
        ObservableList datalist = FXCollections.observableArrayList(list);
        combobox_category.setItems(datalist);
    }
    public void comboboxType(){
        List<String> list = new ArrayList<>();
        for(String data: typeData){
            list.add(data);
        }
        ObservableList datalist = FXCollections.observableArrayList(list);
        combobox_type.setItems(datalist);
    }

    public ObservableList<Warehouse> listData(){
        ObservableList<Warehouse> dataList = FXCollections.observableArrayList();

        connect = database.connectDb();
        String sql = "SELECT * FROM magazyn";

        try{

            statement = connect.createStatement();
            result = statement.executeQuery(sql);

            Warehouse warehouse;

            while(result.next()){
//                MAKE SURE THAT THEY ARE SAME ON THE NAME OF EVERY COLUMN
                warehouse = new Warehouse(result.getInt("id"),
                        result.getString("nazwa")
                        , result.getFloat("ilosc")
                        , result.getString("typ")
                        , result.getFloat("wartosc")
                        , result.getString("data_waznosci")
                        , result.getString("kategoria"));

                dataList.add(warehouse);
            }
        }catch(Exception e){}

        return dataList;
    }

    public void showData(){
        ObservableList<Warehouse> show = listData();

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_count.setCellValueFactory(new PropertyValueFactory<>("count"));
        col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        col_value.setCellValueFactory(new PropertyValueFactory<>("value"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_category.setCellValueFactory(new PropertyValueFactory<>("category"));

        tableviewWarehouse.setItems(show);
    }

    public void exit(){
        System.exit(0);
    }
}
