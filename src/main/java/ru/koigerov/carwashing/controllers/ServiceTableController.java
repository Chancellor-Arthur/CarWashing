package ru.koigerov.carwashing.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ServiceTableController {

    @FXML
    private Button ButtonGoToAdminPanel;

    @FXML
    private Button ButtonGoToServiceCreate;

    @FXML
    private TableColumn<?, ?> TableColumnAction;

    @FXML
    private TableColumn<?, ?> TableColumnDuration;

    @FXML
    private TableColumn<?, ?> TableColumnService;

    @FXML
    private TableView<?> TableViewLogs;

    @FXML
    void GoToAdminPanel(ActionEvent event) {

    }

    @FXML
    void GoToServiceCreate(ActionEvent event) {

    }

}
