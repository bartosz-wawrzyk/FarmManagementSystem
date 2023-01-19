package com.example.farmmanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExpensesController {

    @FXML
    private Button button_add;

    @FXML
    private Button button_clear;

    @FXML
    private Button button_delete;

    @FXML
    private Button button_edit;

    @FXML
    private TableColumn<Expanses, String> col_classification;

    @FXML
    private TableColumn<Expanses, Float> col_count;

    @FXML
    private TableColumn<Expanses, String> col_date;

    @FXML
    private TableColumn<Expanses, Integer> col_id;

    @FXML
    private TableColumn<Expanses, String> col_name;

    @FXML
    private TableColumn<Expanses, String> col_type;

    @FXML
    private TableColumn<Expanses, Float> col_value;

    @FXML
    private ComboBox<Expanses> combobox_category;

    @FXML
    private ComboBox<Expanses> combobox_type;

    @FXML
    private DatePicker date_expiration;

    @FXML
    private TableView<Expanses> tableviewExpanses;

    @FXML
    private TextField txt_count;

    @FXML
    private TextField txt_id;

    @FXML
    private TextField txt_name;

    @FXML
    private TextField txt_value;

    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public void initialize(){
        showData();
        comboboxCategory();
        comboboxType();
    }

    public ObservableList<Expanses> listData(){
        ObservableList<Expanses> dataList = FXCollections.observableArrayList();

        connection = database.connectDb();

        String sql = "SELECT * FROM wydatki";

        try{
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            Expanses expanses;

            while(resultSet.next()){
                expanses = new Expanses(resultSet.getInt("id"),
                        resultSet.getFloat("wartosc"),
                        resultSet.getString("data"),
                        resultSet.getString("nazwa"),
                        resultSet.getFloat("ilosc"),
                        resultSet.getString("typ"),
                        resultSet.getString("klasyfikacja"));
                dataList.add(expanses);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    public void showData(){
        ObservableList<Expanses> show = listData();

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_value.setCellValueFactory(new PropertyValueFactory<>("value"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_count.setCellValueFactory(new PropertyValueFactory<>("amount"));
        col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        col_classification.setCellValueFactory(new PropertyValueFactory<>("classification"));

        tableviewExpanses.setItems(show);
    }

    @FXML
    void clear() {
        txt_id.setText("");
        txt_name.setText("");
        txt_count.setText("");
        txt_value.setText("");
        combobox_category.getSelectionModel().clearSelection();
        combobox_type.getSelectionModel().clearSelection();
    }

    private String categoryData[] = {"klas1", "klas2", "klas3", "klas4"};
    private String typeData[] = {"szt", "l", "kg", "t", "g", "ml"};

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

    @FXML
    void delete() throws SQLException {
        String sql = "DELETE FROM wydatki WHERE id = '" + txt_id.getText() + "'";

        connection = database.connectDb();

        Alert alert;
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Wiadomość potwierdzająca");
        alert.setHeaderText(null);
        alert.setContentText("Czy napewno chcesz usunąć pojazd o ID: " + txt_id.getText() + "?");
        Optional<ButtonType> option = alert.showAndWait();

        if (option.get().equals(ButtonType.OK)) {
            statement = connection.createStatement();
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
        connection = database.connectDb();

        String sql = "INSERT INTO wydatki VALUES(?, ?, ?, ?, ?, ?, ?)";

        String txt_date_expiration = String.valueOf(date_expiration.getValue());

        try{
            if(txt_id.getText().isEmpty() | txt_name.getText().isEmpty()
                    | txt_count.getText().isEmpty()
                    | txt_value.getText().isEmpty()
                    ){

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Komunikat o błędzie");
                alert.setHeaderText(null);
                alert.setContentText("Uzupełnij wszystkie pola!");
                alert.showAndWait();
            }else{
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, txt_id.getText());
                preparedStatement.setString(2, txt_value.getText());
                preparedStatement.setString(3, txt_date_expiration);
                preparedStatement.setString(4, txt_name.getText());
                preparedStatement.setString(5, txt_count.getText());
                preparedStatement.setString(6, String.valueOf(combobox_type.getSelectionModel().getSelectedItem()));
                preparedStatement.setString(7, String.valueOf(combobox_category.getSelectionModel().getSelectedItem()));

                preparedStatement.executeUpdate();

                showData();
                clear();
            }
        }catch(Exception e){}
    }

    @FXML
    void update() {
        connection = database.connectDb();

        String txt_date_expiration = String.valueOf(date_expiration.getValue());

        String sql = "UPDATE wydatki SET id = '" + txt_id.getText()
                + "', wartosc = '" + txt_value.getText()
                + "', data = '" + txt_date_expiration
                + "', nazwa = '" + txt_name.getText()
                + "', ilosc = '" + txt_count.getText()
                + "', typ = '" + combobox_type.getSelectionModel().getSelectedItem()
                + "', klasyfikacja = '" + combobox_category.getSelectionModel().getSelectedItem()
                + "' WHERE id = '" + txt_id.getText() + "'";

        try {
            if (txt_id.getText().isEmpty()
                    | txt_value.getText().isEmpty()
                    | txt_name.getText().isEmpty()
                    | combobox_type.getSelectionModel().isEmpty()
                    | txt_count.getText().isEmpty()
                    | combobox_category.getSelectionModel().isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setTitle("Komunikat o błędzie");
                alert.setHeaderText(null);
                alert.setContentText("Sprawdź poprawność danych!");
                alert.showAndWait();

            } else {

                statement = connection.createStatement();
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
        int num = tableviewExpanses.getSelectionModel().getSelectedIndex();

        Expanses expanses = tableviewExpanses.getSelectionModel().getSelectedItem();

        if(num-1 < -1)
            return;

        txt_id.setText(String.valueOf(expanses.getId()));
        txt_value.setText(String.valueOf(expanses.getValue()));
        String txt_date_review = String.valueOf(date_expiration.getValue());
        date_expiration.setAccessibleHelp(txt_date_review);
        txt_name.setText(expanses.getName());
        txt_count.setText(String.valueOf(expanses.getAmount()));
        combobox_type.getSelectionModel().clearSelection();
        combobox_category.getSelectionModel().clearSelection();
    }

    public void exit(){
        System.exit(0);
    }

}
