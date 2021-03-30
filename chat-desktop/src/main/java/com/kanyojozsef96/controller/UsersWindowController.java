package com.kanyojozsef96.controller;

import com.kanyojozsef96.App;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class UsersWindowController {

    @FXML
    public void onBackMenuItemClicked(ActionEvent actionEvent) {
        App.loadFXML("/fxml/window_main.fxml", null);
    }

    @FXML
    public void onCloseMenuItemClicked(ActionEvent actionEvent) {
        Platform.exit();
    }
}
