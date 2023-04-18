package ru.koigerov.carwashing.controllers;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import ru.koigerov.carwashing.entities.Record;
import ru.koigerov.carwashing.store.Store;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Arrays;
import java.time.LocalDate;

public class RecordController {

    @FXML
    private Button ButtonGoToAdminPanel;

    @FXML
    private Button ButtonGoToHistory;

    @FXML
    private Button ButtonRecordSave;

    @FXML
    private DatePicker DatePicker;

    @FXML
    private ComboBox<String> SelectService;

    @FXML
    private ComboBox<String> SelectTime;

    @FXML
    private TextField TextFieldCar;

    @FXML
    void GoToAdminPanel(ActionEvent event) throws IOException {
        new SceneController().switchToAdminPanelScene(event);
    }

    @FXML
    void GoToHistory(ActionEvent event) throws IOException {
        new SceneController().switchToHistoryScene(event);
    }

    @FXML
    void SaveRecord(ActionEvent event) {
        var record = new Record(-1, Store.userId, TextFieldCar.getText().trim(), SelectService.getValue(),
                DatePicker.getValue(), SelectTime.getValue());
    }

    @FXML
    public void initialize() {
        var serviceList = Arrays.asList("A", "V");
        // @TODO: Dodelat!!!
        var timeList = Arrays.asList("1", "2");

        DatePicker.setValue(LocalDate.now());
        SelectService.setItems(FXCollections.observableArrayList(serviceList));
        SelectTime.setItems(FXCollections.observableArrayList(timeList));

        ButtonGoToAdminPanel.setVisible(Store.isAdmin);
        ButtonGoToHistory.setVisible(!Store.isAdmin);
    }
}
