package com.kanyojozsef96.controller;

import com.kanyojozsef96.App;
import com.kanyojozsef96.dao.UserDAOImpl;
import com.kanyojozsef96.model.User;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class UsersWindowController implements Initializable {
    private final UserDAOImpl userDAO = UserDAOImpl.getInstance();

    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, Integer> idColumn;
    @FXML
    private TableColumn<User, String> nameColumn;
    @FXML
    private TableColumn<User, String> emailColumn;
    @FXML
    private TableColumn<User, Void> actionsColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshTable();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        actionsColumn.setCellFactory(param -> new TableCell<>(){
            private final Button hobbiesBtn = new Button("Hobbies");
            private final Button deleteBtn = new Button("Delete");


            {
                deleteBtn.setOnAction(event -> {
                    User current = getTableRow().getItem();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, null, ButtonType.YES, ButtonType.NO);
                    alert.setHeaderText("Are you sure, you want to delete this user?");
                    alert.setTitle("Are you sure?");

                    Optional<ButtonType> bty = alert.showAndWait();
                    bty.ifPresent(buttonType -> {
                        if(buttonType == ButtonType.YES) {
                            userDAO.deleteUser(current);
                            refreshTable();
                        }
                    });
                });

                hobbiesBtn.setOnAction(event -> {
                    User curr = getTableRow().getItem();
                    Stage stage = new Stage();
                    FXMLLoader loader = App.loadFXML("/fxml/window_hobbies.fxml", stage, "Hobbies of " + curr.getUsername());
                    HobbiesWindowController controller = loader.getController();
                    controller.fillInHobbies(curr);
                    stage.show();
                });
            }

            @Override
            protected void updateItem(Void v, boolean b) {
                super.updateItem(v, b);
                if(b) {
                    setGraphic(null);
                } else {
                    HBox box = new HBox(hobbiesBtn, deleteBtn);
                    box.setSpacing(10.0);
                    setGraphic(box);
                }
            }
        });

        actionsColumn.prefWidthProperty().bind(userTable.widthProperty()
                .subtract(idColumn.widthProperty()
                        .add(nameColumn.widthProperty()
                                .add(emailColumn.widthProperty()))));
    }

    private void refreshTable() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                userTable.getItems().setAll(userDAO.findAllUsers());
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
