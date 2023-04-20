package ru.koigerov.carwashing.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;

public class UserHistoryController {

    @FXML
    private Button ButtonGoToRecord;

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
    private TableView<?> TableViewRecord;

    @FXML
    void GoToRecord(ActionEvent event) throws IOException {
        new SceneController().switchToRecordScene(event);
    }

}

