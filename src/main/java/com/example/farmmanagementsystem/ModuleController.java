package com.example.farmmanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;


public class ModuleController {

    @FXML
    private Button button_calculate;

    @FXML
    private TableView<Plot> tableview_before;

    @FXML
    private TableColumn<Plot, Float> col_area;

    @FXML
    private TableColumn<Plot, String> col_cropping;

    @FXML
    private TableColumn<Plot, String> col_number;

    @FXML
    private TableColumn<Plot, Integer> col_id;

    @FXML
    private TableColumn<Plot, Integer> col_year;

    @FXML
    private Label label_result;


    //    DATABASE TOOL
    private Connection connect;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;

    public void initialize(){

        showData();
    }


    public ObservableList<Plot> listData(){
        ObservableList<Plot> dataList = FXCollections.observableArrayList();

        connect = database.connectDb();

        String sql = "SELECT * FROM dzialki WHERE rok = 2022";

        try{
            statement = connect.createStatement();
            result = statement.executeQuery(sql);

            Plot plot;

            while(result.next()){
                plot = new Plot(result.getInt("id"),
                        result.getString("numer"),
                        result.getFloat("powierzchnia"),
                        result.getString("uprawa"),
                        result.getInt("rok"));
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

        tableview_before.setItems(show);
    }

    public void getPlot(){
        connect = database.connectDb();

        String sql = "SELECT numer, uprawa FROM dzialki WHERE rok = 2022";

        try{
            String[] crops = {"Pszenica", "Owies", "Jęczmień", "Soja", "Ziemniaki", "Kukurydza", "Groch", "Rzepak", "Żyto"};
            Random r = new Random();

            statement = connect.createStatement();
            result = statement.executeQuery(sql);

            String text = "";

            Set<String> set = new TreeSet<>();
            while(result.next()){
                set.add(result.getString(1) + " " + result.getString(2));
            }
            for(String s:set){
                    text += s + "   -   " + crops[r.nextInt(crops.length)] + "\n";
            }
            label_result.setText(text);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void exit() {
        System.exit(0);
    }
}
