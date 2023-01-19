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

public class BreedingController {

    @FXML
    private Button button_add;

    @FXML
    private Button button_delete;

    @FXML
    private Button button_edit;

    @FXML
    private TableColumn<Breeding, Integer> col_amount;

    @FXML
    private TableColumn<Breeding, String> col_category;

    @FXML
    private TableColumn<Breeding, Integer> col_id;

    @FXML
    private TableColumn<Breeding, String> col_name;

    @FXML
    private ComboBox<Breeding> combobox_category;

    @FXML
    private TableView<Breeding> tableview_breeding;

    @FXML
    private TextField txt_amount;

    @FXML
    private TextField txt_id;

    @FXML
    private TextField txt_name;

    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public void initialize(){
        showData();
        combobox();
    }

    public ObservableList<Breeding> listData(){
        ObservableList<Breeding> dataList = FXCollections.observableArrayList();

        connection = database.connectDb();

        String sql = "SELECT * FROM hodowla";

        try{
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            Breeding breeding;

            while(resultSet.next()){
                breeding = new Breeding(resultSet.getInt("id"),
                                        resultSet.getString("nazwa"),
                                        resultSet.getInt("ilosc"),
                                        resultSet.getString("kategoria"));
                dataList.add(breeding);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    public void showData(){
        ObservableList<Breeding> show = listData();

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        col_category.setCellValueFactory(new PropertyValueFactory<>("category"));

        tableview_breeding.setItems(show);
    }

    private String categoryData[] = {"Bydło", "Trzoda chlewna", "Drób", "Inne"};

    public void combobox(){
        List<String> list = new ArrayList<>();
        for(String data: categoryData){
            list.add(data);
        }
        ObservableList datalist = FXCollections.observableArrayList(list);
        combobox_category.setItems(datalist);
    }

    @FXML
    void clear() {
        txt_id.setText("");
        txt_name.setText("");
        txt_amount.setText("");
        combobox_category.getSelectionModel().clearSelection();
    }

    @FXML
    void delete() throws SQLException {
        String sql = "DELETE FROM hodowla WHERE id = '" + txt_id.getText() + "'";

        Alert alert;
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Wiadomość potwierdzająca");
        alert.setHeaderText(null);
        alert.setContentText("Czy napewno chcesz usunąć zwierzę o ID: " + txt_id.getText() + "?");
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
    void exit() {System.exit(0);}

    @FXML
    void insert() {
        connection = database.connectDb();

        String sql = "INSERT INTO hodowla VALUES (?,?,?,?)";

        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, txt_id.getText());
            preparedStatement.setString(2, txt_name.getText());
            preparedStatement.setString(3, txt_amount.getText());
            preparedStatement.setString(4, String.valueOf(combobox_category.getSelectionModel().getSelectedItem()));

            preparedStatement.executeUpdate();

            showData();
            clear();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void update() {
        connection = database.connectDb();

        String sql = "UPDATE hodowla SET id = '" + txt_id.getText()
                + "', nazwa = '" + txt_name.getText()
                + "', ilosc = '" + txt_amount.getText()
                + "', kategoria = '" + combobox_category.getSelectionModel().getSelectedItem()
                + "' WHERE id = '" + txt_id.getText() + "'";

        try {
            if (txt_id.getText().isEmpty() | txt_name.getText().isEmpty()
                    | txt_amount.getText().isEmpty()
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

    public void select(){
        int num = tableview_breeding.getSelectionModel().getSelectedIndex();

        Breeding breeding = tableview_breeding.getSelectionModel().getSelectedItem();

        if(num-1 < -1)
            return;

        txt_id.setText(String.valueOf(breeding.getId()));
        txt_name.setText(breeding.getName());
        txt_amount.setText(String.valueOf(breeding.getAmount()));
        combobox_category.getSelectionModel().clearSelection();
    }

}
