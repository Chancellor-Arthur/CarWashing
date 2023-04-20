package ru.koigerov.carwashing.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ru.koigerov.carwashing.db.DBManager;
import ru.koigerov.carwashing.entities.Service;
import ru.koigerov.carwashing.utils.Alerts;

import java.io.IOException;
import java.sql.SQLException;

public class ServiceCreateController {

    @FXML
    private Button ButtonServiceCancel;

    @FXML
    private Button ButtonServiceCreate;

    @FXML
    private TextField InputFieldDuration;

    @FXML
    private TextField InputFieldService;

    @FXML
    void ServiceCancel(ActionEvent event) throws IOException {
        new SceneController().switchToShowServiceScene(event);
    }

    @FXML
    void ServiceCreate(ActionEvent event) throws IOException, SQLException {
        var serviceName = InputFieldService.getText().trim();
        var durationInput = InputFieldDuration.getText().trim();

        if (durationInput.equals("") || serviceName.equals("")) {
            Alerts.showErrorAlert("Введите название услуги/длительность!");
            return;
        }

        if (!durationInput.matches("-?\\d+(\\.\\d+)?")) {
            Alerts.showErrorAlert("Длительность должно быть числом");
            return;
        }

        var duration = Integer.parseInt(durationInput);

        if (duration <= 15 || duration >= 120) {
            Alerts.showErrorAlert("Услуга длиться минимум 15 минут, максимум 120");
            return;
        }

        var oldService = DBManager.getService(serviceName);

        if (oldService.next()) {
            Alerts.showErrorAlert("Название услуги занято!");
            return;
        }



        DBManager.createService(new Service(-1, serviceName, duration));
        new SceneController().switchToShowServiceScene(event);
        Alerts.showSuccessAlert("Сервис создан создан!");
    }

}
