package com.kanyojozsef96.controller;

import com.kanyojozsef96.dao.RulesDAO;
import com.kanyojozsef96.dao.RulesDAOImpl;
import com.kanyojozsef96.model.Room;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class RulesWindowController {
    private final RulesDAO rulesDAO = RulesDAOImpl.getInstance();

    @FXML
    private TableView<String> rulesTable;
    @FXML
    private TableColumn<String, String> nameColumn;

    @FXML
    public void onCloseMenuItemClicked(ActionEvent actionEvent) {
        Stage stage = (Stage) rulesTable.getScene().getWindow();
        stage.close();
    }

    public void fillInRules(Room current) {
        ObservableList<String> tmp = FXCollections.observableArrayList(rulesDAO.findAllRulesForRoom(current));
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        rulesTable.setItems(tmp);
    }
}
