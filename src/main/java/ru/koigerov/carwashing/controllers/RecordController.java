package ru.koigerov.carwashing.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import ru.koigerov.carwashing.db.DBManager;
import ru.koigerov.carwashing.entities.Record;
import ru.koigerov.carwashing.store.Store;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import ru.koigerov.carwashing.utils.Alerts;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.*;
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
    void SaveRecord(ActionEvent event) throws SQLException {
        var record = new Record(-1, Store.userId, TextFieldCar.getText().trim(), SelectService.getValue(),
                DatePicker.getValue(), SelectTime.getValue());
        DBManager.createRecord(record);
        Alerts.showSuccessAlert("Запись успешно создана!");
    }

    @FXML
    public void initialize() throws SQLException {
        ObservableList<String> serviceNameList = FXCollections.observableArrayList();
        var service = DBManager.getAllService();
        while (service.next()) serviceNameList.add(service.getString("service_name"));

        var records = DBManager.getAllRecordForDay(Date.valueOf(LocalDate.now()));

        var servicesDuration = new HashMap<String, Integer>();

        while (records.next()) {
            var services = DBManager.getServiceById(records.getInt("service_id"));
            if (services.next()) servicesDuration.put(records.getString("time"),
                    services.getInt("duration"));
        }

        var times = Arrays.asList("10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30",
                "14:00", "14:30", "15:00", "15:30", "16:00");

        List<String> availableTimes = new ArrayList<>(times);
        for (var i = 0; i < times.size(); i++) {
            var duration = servicesDuration.get(times.get(i));
            if (duration == null) continue;
            if (duration.compareTo(30) <= 0) {
                availableTimes.remove(times.get(i));
                continue;
            }
            if (duration.compareTo(60) <= 0) {
                availableTimes.remove(times.get(i));
                availableTimes.remove(times.get(i + 1));
                i += 1;
                continue;
            }
            if (duration.compareTo(90) <= 0) {
                availableTimes.remove(times.get(i));
                availableTimes.remove(times.get(i + 1));
                availableTimes.remove(times.get(i + 2));
                i += 2;
                continue;
            }
            if (duration.compareTo(120) <= 0) {
                availableTimes.remove(times.get(i));
                availableTimes.remove(times.get(i + 1));
                availableTimes.remove(times.get(i + 2));
                availableTimes.remove(times.get(i + 3));
                i += 3;
            }
        }

        DatePicker.setValue(LocalDate.now());
        SelectService.setItems(serviceNameList);
        SelectTime.setItems(FXCollections.observableList(availableTimes));

        ButtonGoToAdminPanel.setVisible(Store.isAdmin);
        ButtonGoToHistory.setVisible(!Store.isAdmin);
    }
}
