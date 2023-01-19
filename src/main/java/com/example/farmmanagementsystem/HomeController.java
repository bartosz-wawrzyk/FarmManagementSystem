package com.example.farmmanagementsystem;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.web.WebView;

import java.util.ArrayList;
import java.util.Date;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.List;



public class HomeController {

    @FXML
    private Label label_garage, label_animals, label_plot;

    @FXML
    private Label label_benzyn, label_diesel, label_gas;

    @FXML
    private Label label_cerealprices, label_serviceprice;

    @FXML
    private Label label_time, label_date;

    @FXML
    private WebView webview;

    @FXML
    private ComboBox combobox_price, combobox_serviceprice;

    private final Double benzyn = 6.91;
    private final Double diesel = 8.07;
    private final Double gas = 3.03;

    private final Double consumption_wheat = 1544.46;
    private final Double consumption_rye = 1160.53;
    private final Double consumption_barley = 1260.00;
    private final Double feed_wheat = 1449.03;
    private final Double fodder_rye = 1112.12;
    private final Double triticale = 1297.00;
    private final Double feed_barley = 1242.50;
    private final Double rape = 2868.02;
    private final Double corn = 1360.75;
    private final Double consumption_peas = 1618.50;

    private Connection connect;
    private Statement statement;
    private ResultSet result;
    private PreparedStatement prepare;

    private volatile boolean stop = false;

    @FXML
    public void initialize(){
        selectGarage();
        selectAnimals();
        selectPlot();
        time();
        date();
        fuelPrices();
        cerealPrices();
        showCerealPrices();
        servicePrices();
        showServicePrice();
        weather();
    }

    @FXML
    public void weather(){
        webview.getEngine().load("https://meteo.imgw.pl/?model=hybrid&loc=kielce&ter=2604&mode=details");
    }

    private String servicePriceData[] = {"Agregat uprawowo-siewny", "Orka", "Siew zbóż", "Prasa zwijająca",
            "Prasa kostkująca", "Kombajn zbożowy", "Kombajn ziemniaczany", "Rzepak", "Kukurydza", "Groch konsumpcyjny"};

    private void servicePrices(){
        List<String> list = new ArrayList<>();
        for(String data: servicePriceData){
            list.add(data);
        }
        ObservableList datalist = FXCollections.observableArrayList(list);
        combobox_serviceprice.setItems(datalist);
    }

    public void showServicePrice(){
        String aggregate = "250-280 zł/ha";
        String tillage = "300-350 zł/ha";
        String sowing_cereals = "210-280 zł/ha";
        String round_baler = "12-15 zł/szt";
        String square_baler = "280 zł/ha";
        String Combine_harvester = "400-550 zł/ha";
        String Potato_harvester = "850 zł/ha";

        combobox_serviceprice.setOnAction(mouseEvent -> {
            String value = (String) combobox_serviceprice.getSelectionModel().getSelectedItem();
            if(value == "Agregat uprawowo-siewny"){
                label_serviceprice.setText(aggregate);
            } else if(value == "Orka"){
                label_serviceprice.setText(tillage);
            } else if(value == "Siew zbóż"){
                label_serviceprice.setText(sowing_cereals);
            } else if(value == "Prasa zwijająca"){
                label_serviceprice.setText(round_baler);
            } else if(value == "Prasa kostkująca"){
                label_serviceprice.setText(square_baler);
            } else if(value == "Kombajn zbożowy"){
                label_serviceprice.setText(Combine_harvester);
            } else if(value == "Kombajn ziemniaczany"){
                label_serviceprice.setText(Potato_harvester);
            }
        });
    }

    private String cerealData[] = {"Pszenica konsumpcyjna", "Żyto konsumpcyjne", "Jęczmień konsumpcyjny", "Pszenica paszowa",
                                    "Żyto paszowe", "Pszenżyto", "Jęczmień paszowy", "Rzepak", "Kukurydza", "Groch konsumpcyjny"};

    private void cerealPrices(){
        List<String> list = new ArrayList<>();
        for(String data: cerealData){
            list.add(data);
        }
        ObservableList datalist = FXCollections.observableArrayList(list);
        combobox_price.setItems(datalist);
    }

    public void showCerealPrices(){
        combobox_price.setOnAction(mouseEvent -> {
            String value = (String) combobox_price.getSelectionModel().getSelectedItem();
            if(value == "Pszenica konsumpcyjna"){
                label_cerealprices.setText(consumption_wheat + " zł/t");
            } else if(value == "Żyto konsumpcyjne"){
                label_cerealprices.setText(consumption_rye + " zł/t");
            } else if(value == "Jęczmień konsumpcyjny"){
                label_cerealprices.setText(consumption_barley + " zł/t");
            } else if(value == "Pszenica paszowa"){
                label_cerealprices.setText(feed_wheat + " zł/t");
            } else if(value == "Żyto paszowe"){
                label_cerealprices.setText(fodder_rye + " zł/t");
            } else if(value == "Pszenżyto"){
                label_cerealprices.setText(triticale + " zł/t");
            } else if(value == "Jęczmień paszowy"){
                label_cerealprices.setText(feed_barley + " zł/t");
            } else if(value == "Rzepak"){
                label_cerealprices.setText(rape + " zł/t");
            } else if(value == "Kukurydza"){
                label_cerealprices.setText(corn + " zł/t");
            } else if(value == "Groch konsumpcyjny"){
                label_cerealprices.setText(consumption_peas + " zł/t");
            }
        });

    }

    private void fuelPrices(){
        label_benzyn.setText("Benzyna: " + benzyn + " zł/l");
        label_diesel.setText("Diesel: " + diesel + " zł/l");
        label_gas.setText("LPG: " + gas + " zł/l");
    }

    private void time(){
        Thread thread = new Thread(() ->{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            while(!stop){
                try{
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                final String timenow = simpleDateFormat.format(new Date());
                Platform.runLater(() -> {
                    label_time.setText(timenow);
                });
            }
        });
        thread.start();

    }

    private void date(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strdate = simpleDateFormat.format(date);
        label_date.setText(strdate);
    }

    public void selectGarage(){

        connect = database.connectDb();

        String sql = "SELECT COUNT(id) FROM garaz";
        int countGarage = 0;

        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if(result.next()){
                countGarage = result.getInt("COUNT(id)");
            }

            label_garage.setText(String.valueOf(countGarage));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectAnimals(){
        connect = database.connectDb();

        String sql = "SELECT SUM(ilosc) FROM hodowla";
        int countAnimals = 0;

        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if(result.next()){
                countAnimals = result.getInt("SUM(ilosc)");
            }

            label_animals.setText(String.valueOf(countAnimals));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectPlot(){
        connect = database.connectDb();

        String sql = "SELECT COUNT(id) FROM dzialki";
        int countPlot = 0;

        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if(result.next()){
                countPlot = result.getInt("COUNT(id)");
            }

            label_plot.setText(String.valueOf(countPlot));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exit(){
        System.exit(0);
    }
}
