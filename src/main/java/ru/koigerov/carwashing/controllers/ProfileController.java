package ru.koigerov.carwashing.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ru.koigerov.carwashing.db.DBManager;
import ru.koigerov.carwashing.store.Store;
import ru.koigerov.carwashing.utils.Alerts;

import java.io.IOException;
import java.sql.SQLException;

public class ProfileController {

    @FXML
    private Button ButtonGoToHistory;

    @FXML
    private Button ButtonSaveProfile;

    @FXML
    private TextField InputFieldLogin;

    @FXML
    private PasswordField InputFieldPassword;

    @FXML
    private PasswordField InputFieldPasswordAgain;

    @FXML
    private Label ProfileLabel;

    @FXML
    void GoToHistory(ActionEvent event) throws IOException {
        new SceneController().switchToHistoryScene(event);
    }

    @FXML
    public void initialize() throws SQLException {
        var user = DBManager.getUser(Store.userId);
        if (user.next()) {
            ProfileLabel.setText("Профиль: " + user.getString("login"));
            InputFieldLogin.setText(user.getString("login"));
            InputFieldPassword.setText(user.getString("password"));
        }
    }

    @FXML
    void SaveProfile(ActionEvent event) throws SQLException, IOException {
        var login = InputFieldLogin.getText().trim();
        var password = InputFieldPassword.getText().trim();
        var passwordAgain = InputFieldPasswordAgain.getText().trim();

        if (login.equals("") || password.equals("") || passwordAgain.equals("")) {
            Alerts.showErrorAlert("Введите логин/пароль/подтверждение пароля!");
            return;
        }

        var oldUser = DBManager.getUser(login);

        if (oldUser.next()) {
            if (oldUser.getInt("id") != Store.userId) {
                Alerts.showErrorAlert("Логин занят!");
                return;
            }
        }

        if (!password.equals(passwordAgain)) {
            Alerts.showErrorAlert("Пароли не совпадают!");
            return;
        }

        DBManager.updateUser(InputFieldLogin.getText().trim(), InputFieldPassword.getText().trim(), false, Store.userId);
        Alerts.showSuccessAlert("Пользователь изменен!");
        ProfileLabel.setText("Профиль: " + login);
    }
}
