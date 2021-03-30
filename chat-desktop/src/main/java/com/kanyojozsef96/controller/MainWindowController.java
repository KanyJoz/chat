package com.kanyojozsef96.controller;

import com.kanyojozsef96.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MainWindowController {

    @FXML
    public void onListUsersBtnClicked(ActionEvent actionEvent) {
        App.loadFXML("/fxml/window_users.fxml", null);
    }

    @FXML
    public void onListRoomsBtnClicked(ActionEvent actionEvent) {
        App.loadFXML("/fxml/window_rooms.fxml", null);
    }
}
