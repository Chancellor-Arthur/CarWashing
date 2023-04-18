package ru.koigerov.carwashing.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class UsersTableController {

    @FXML
    private Button ButtonGoToAdminPanel;

    @FXML
    private Button ButtonGoToUserCreate;

    @FXML
    private TableColumn<?, ?> TableColumnAction;

    @FXML
    private TableColumn<?, ?> TableColumnLogin;

    @FXML
    private TableColumn<?, ?> TableColumnPassword;

    @FXML
    private TableColumn<?, ?> TableColumnUserName;

    @FXML
    private TableView<?> TableViewLogs;

    @FXML
    void GoToAdminPanel(ActionEvent event) {

    }

    @FXML
    void GoToUserCreate(ActionEvent event) {

    }

}
