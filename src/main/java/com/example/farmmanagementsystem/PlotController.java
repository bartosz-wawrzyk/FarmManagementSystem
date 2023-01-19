package com.example.farmmanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import java.util.Optional;

public class PlotController {
    @FXML
    private Button button_add;

    @FXML
    private Button button_clear;

    @FXML
    private Button button_delete;

    @FXML
    private Button button_edit;

    @FXML
    private TableColumn<Plot, Float> col_area;

    @FXML
    private TableColumn<Plot, String> col_cropping;

    @FXML
    private TableColumn<Plot, Integer> col_id;

    @FXML
    private TableColumn<Plot, String> col_number;

    @FXML
    private TableColumn<Plot, Integer> col_year;

    @FXML
    private TableView<Plot> tableviewPlot;

    @FXML
    private TextField txt_area;

    @FXML
    private TextField txt_cropping;

    @FXML
    private TextField txt_id;

    @FXML
    private TextField txt_number;

    @FXML
    private TextField txt_year;

    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public void initialize(){
        showData();
    }

    @FXML
    void clear() {
        txt_id.setText("");
        txt_number.setText("");
        txt_area.setText("");
        txt_cropping.setText("");
        txt_year.setText("");
    }

    @FXML
    void delete() throws SQLException{
        String sql = "DELETE FROM dzialki WHERE id = '" + txt_id.getText() + "'";

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

        String sql = "INSERT INTO dzialki VALUES (?,?,?,?,?)";

        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, txt_id.getText());
            preparedStatement.setString(2, txt_number.getText());
            preparedStatement.setString(3, txt_area.getText());
            preparedStatement.setString(4, txt_cropping.getText());
            preparedStatement.setString(5, txt_year.getText());

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

        String sql = "UPDATE dzialki SET id = '" + txt_id.getText()
                + "', numer = '" + txt_number.getText()
                + "', powierzchnia = '" + txt_area.getText()
                + "', uprawa = '" + txt_cropping.getText()
                + "', rok = '" + txt_year.getText()
                + "' WHERE id = '" + txt_id.getText() + "'";

        try {
            if (txt_id.getText().isEmpty() | txt_number.getText().isEmpty()
                    | txt_area.getText().isEmpty()
                    | txt_cropping.getText().isEmpty()
                    | txt_year.getText().isEmpty()) {

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
        int num = tableviewPlot.getSelectionModel().getSelectedIndex();

        Plot plot = tableviewPlot.getSelectionModel().getSelectedItem();

        if(num-1 < -1)
            return;

        txt_id.setText(String.valueOf(plot.getId()));
        txt_number.setText(plot.getNumber());
        txt_area.setText(String.valueOf(plot.getArea()));
        txt_cropping.setText(plot.getCropping());
        txt_year.setText(String.valueOf(plot.getYear()));
    }

    public ObservableList<Plot> listData(){
        ObservableList<Plot> dataList = FXCollections.observableArrayList();

        connection = database.connectDb();

        String sql = "SELECT * FROM dzialki";

        try{
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            Plot plot;

            while(resultSet.next()){
                plot = new Plot(resultSet.getInt("id"),
                        resultSet.getString("numer"),
                        resultSet.getFloat("powierzchnia"),
                        resultSet.getString("uprawa"),
                        resultSet.getInt("rok"));
                dataList.add(plot);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    public void showData(){
        ObservableList<Plot> show = listData();

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_number.setCellValueFactory(new PropertyValueFactory<>("number"));
        col_area.setCellValueFactory(new PropertyValueFactory<>("area"));
        col_cropping.setCellValueFactory(new PropertyValueFactory<>("cropping"));
        col_year.setCellValueFactory(new PropertyValueFactory<>("year"));

        tableviewPlot.setItems(show);
    }

}
