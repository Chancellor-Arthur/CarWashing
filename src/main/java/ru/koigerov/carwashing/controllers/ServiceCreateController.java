package ru.koigerov.carwashing.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
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
        var duration = InputFieldDuration.getText().trim();

        if (duration.equals("") || serviceName.equals("")) {
            Alerts.showErrorAlert("Введите название услуги/длительность!");
            return;
        }

        var oldService = DBManager.getService(serviceName);

        if (oldService.next()) {
            Alerts.showErrorAlert("Название услуги занято!");
            return;
        }

        if (!duration.matches("-?\\d+(\\.\\d+)?")) {
            Alerts.showErrorAlert("Длительность должно быть числом");
            return;
        }

        DBManager.createService(new Service(-1, serviceName, Integer.parseInt(duration)));
        new SceneController().switchToShowServiceScene(event);
        Alerts.showSuccessAlert("Сервис создан создан!");
    }

}
