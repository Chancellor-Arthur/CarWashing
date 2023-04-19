package ru.koigerov.carwashing.controllers;

import ru.koigerov.carwashing.db.DBManager;
import ru.koigerov.carwashing.store.Store;
import ru.koigerov.carwashing.utils.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class AuthorizationController {

    @FXML
    private Button ButtonSignIn;

    @FXML
    private Button ButtonGoToSignUp;

    @FXML
    private TextField InputFieldLogin;

    @FXML
    private PasswordField InputFieldPassword;

    @FXML
    void GoToSignUp(ActionEvent event) throws IOException {
        new SceneController().switchToRegistrationScene(event);
    }

    @FXML
    void SignIn(ActionEvent event) throws IOException, SQLException {
        var login = InputFieldLogin.getText().trim();
        var password = InputFieldPassword.getText().trim();

        if (login.equals("") || password.equals("")) {
            Alerts.showErrorAlert("Введите логин/пароль!");
            return;
        }

        var user = DBManager.getUser(InputFieldLogin.getText().trim(), InputFieldPassword.getText().trim());
        if (user.next()) {
            Store.isAdmin = user.getBoolean("is_admin");
            Store.userId = user.getInt("id");
            if (Store.isAdmin) {
                new SceneController().switchToAdminPanelScene(event);
                return;
            }
            new SceneController().switchToHistoryScene(event);
        } else {
            Alerts.showErrorAlert("Неверный логин/пароль!");
        }
    }

}

