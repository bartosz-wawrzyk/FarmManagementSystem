package com.example.farmmanagementsystem.module;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class MapsController implements Initializable {

    @FXML
    private WebView webview_maps;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle){
        WebEngine webEngine = webview_maps.getEngine();
        webEngine.load("https://mapy.geoportal.gov.pl/imap/Imgp_2.html");
        WebHistory webHistory = webEngine.getHistory();
        System.out.println(webEngine.getUserAgent());
    }
}
