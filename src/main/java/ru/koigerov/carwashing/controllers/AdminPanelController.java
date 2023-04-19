package ru.koigerov.carwashing.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;

public class AdminPanelController {

    @FXML
    private Button ButtonGoToRecord;

    @FXML
    private Button ButtonGoToService;

    @FXML
    private Button ButtonGoToUsersTable;

    @FXML
    private TableColumn<?, ?> TableColumnAction;

    @FXML
    private TableColumn<?, ?> TableColumnCar;

    @FXML
    private TableColumn<?, ?> TableColumnDate;

    @FXML
    private TableColumn<?, ?> TableColumnDuration;

    @FXML
    private TableColumn<?, ?> TableColumnService;

    @FXML
    private TableView<?> TableViewLogs;

    @FXML
    void GoToRecord(ActionEvent event) throws IOException {
        new SceneController().switchToRecordScene(event);
    }

    @FXML
    void GoToService(ActionEvent event) throws IOException {
        new SceneController().switchToShowServiceScene(event);
    }

    @FXML
    void GoToUsersTable(ActionEvent event) throws IOException {
        new SceneController().switchToShowUsersScene(event);
    }

}
