package com.kanyojozsef96.controller;

import com.kanyojozsef96.dao.RoomDAOImpl;
import com.kanyojozsef96.model.Room;
import com.kanyojozsef96.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class RoomUsersWindowController {
    private final RoomDAOImpl roomDao = RoomDAOImpl.getInstance();

    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, String> nameColumn;
    @FXML
    private TableColumn<User, String> emailColumn;

    public void fillInUsers(Room current) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        userTable.getItems().setAll(roomDao.findAllUsersForRoom(current));
    }

    @FXML
    public void onCloseMenuItemClicked(ActionEvent actionEvent) {
        Stage stage = (Stage) userTable.getScene().getWindow();
        stage.close();
    }
}
