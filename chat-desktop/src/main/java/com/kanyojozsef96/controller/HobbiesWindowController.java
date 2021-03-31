package com.kanyojozsef96.controller;

import com.kanyojozsef96.dao.HobbiesDAOImpl;
import com.kanyojozsef96.model.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

public class HobbiesWindowController {
    private final HobbiesDAOImpl hobbiesDAO = HobbiesDAOImpl.getInstance();

    @FXML
    private TableView<String> hobbiesTable;
    @FXML
    private TableColumn<String, String> nameColumn;

    @FXML
    public void onCloseMenuItemClicked(ActionEvent actionEvent) {
        Stage stage = (Stage) hobbiesTable.getScene().getWindow();
        stage.close();
    }

    public void fillInHobbies(User current) {
        ObservableList<String> tmp = FXCollections.observableArrayList(hobbiesDAO.findAllHobbiesForUser(current));
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        hobbiesTable.setItems(tmp);
    }
}
