package com.kanyojozsef96.controller;

import com.kanyojozsef96.App;
import com.kanyojozsef96.dao.RoomDAOImpl;
import com.kanyojozsef96.dao.UserDAOImpl;
import com.kanyojozsef96.model.Room;
import com.kanyojozsef96.model.User;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class RoomsWindowController implements Initializable {
    private final RoomDAOImpl roomDAO = RoomDAOImpl.getInstance();

    @FXML
    private TableView<Room> roomTable;
    @FXML
    private TableColumn<Room, Integer> idColumn;
    @FXML
    private TableColumn<Room, String> nameColumn;
    @FXML
    private TableColumn<Room, String> roomTypeColumn;
    @FXML
    private TableColumn<Room, Void> actionsColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshTable();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        roomTypeColumn.setCellValueFactory(new PropertyValueFactory<>("roomType"));

        actionsColumn.setCellFactory(param -> new TableCell<>(){
            private final Button usersBtn = new Button("Users");
            private final Button rulesBtn = new Button("Rules");
            private final Button deleteBtn = new Button("Delete");


            {
                deleteBtn.setOnAction(event -> {
                     Room current = getTableRow().getItem();
                     roomDAO.deleteRoom(current);
                     refreshTable();
                });

                rulesBtn.setOnAction(event -> {
                    Room curr = getTableRow().getItem();
                    Stage stage = new Stage();
                    FXMLLoader loader = App.loadFXML("/fxml/window_rules.fxml", stage, "Rules of " + curr.getName());
                    RulesWindowController controller = loader.getController();
                    controller.fillInRules(curr);
                    stage.show();
                });

                usersBtn.setOnAction(event -> {
                    Room curr = getTableRow().getItem();
                    Stage stage = new Stage();
                    FXMLLoader loader = App.loadFXML("/fxml/window_roomusers.fxml", stage, "Users of " + curr.getName());
                    RoomUsersWindowController controller = loader.getController();
                    controller.fillInUsers(curr);
                    stage.show();
                });
            }

            @Override
            protected void updateItem(Void v, boolean b) {
                super.updateItem(v, b);
                if(b) {
                    setGraphic(null);
                } else {
                    HBox box = new HBox(usersBtn, rulesBtn, deleteBtn);
                    box.setSpacing(10.0);
                    setGraphic(box);
                }
            }
        });

        actionsColumn.prefWidthProperty().bind(roomTable.widthProperty()
                .subtract(idColumn.widthProperty()
                        .add(nameColumn.widthProperty()
                                .add(roomTypeColumn.widthProperty()))));
    }

    private void refreshTable() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                roomTable.getItems().setAll(roomDAO.findAllRooms());
                return null;
            }
        };

        Thread findThread = new Thread(task);
        findThread.start();
    }

    @FXML
    public void onBackMenuItemClicked(ActionEvent actionEvent) {
        App.loadFXML("/fxml/window_main.fxml", null, "Admin Application");
    }

    @FXML
    public void onCloseMenuItemClicked(ActionEvent actionEvent) {
        Platform.exit();
    }
}
