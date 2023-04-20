package ru.koigerov.carwashing.controllers;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public final class SceneController {
    public void switchToRegistrationScene(Event event) throws IOException {
        defaultSwitch(event, "/ru/koigerov/carwashing/scenes/Registration.fxml");
    }

    public void switchToAuthorizationScene(Event event) throws IOException {
        defaultSwitch(event, "/ru/koigerov/carwashing/scenes/Authorization.fxml");
    }

    public void switchToRecordScene(Event event) throws IOException {
        defaultSwitch(event, "/ru/koigerov/carwashing/scenes/Record.fxml");
    }

    public void switchToHistoryScene(Event event) throws IOException {
        defaultSwitch(event, "/ru/koigerov/carwashing/scenes/UserHistory.fxml");
    }

    public void switchToCreateServiceScene(Event event) throws IOException {
        defaultSwitch(event, "/ru/koigerov/carwashing/scenes/ServiceCreate.fxml");
    }

    public void switchToShowServiceScene(Event event) throws IOException {
        defaultSwitch(event, "/ru/koigerov/carwashing/scenes/ServiceTable.fxml");
    }

    public void switchToCreateUserScene(Event event) throws IOException {
        defaultSwitch(event, "/ru/koigerov/carwashing/scenes/UserCreate.fxml");
    }

    public void switchToProfile(Event event) throws IOException {
        defaultSwitch(event, "/ru/koigerov/carwashing/scenes/Profile.fxml");
    }


    public void switchToShowUsersScene(Event event) throws IOException {
        defaultSwitch(event, "/ru/koigerov/carwashing/scenes/UsersTable.fxml");
    }

    public void switchToAdminPanelScene(Event event) throws IOException {
        defaultSwitch(event, "/ru/koigerov/carwashing/scenes/AdminPanel.fxml");
    }

    public void defaultSwitch(Event event, String path) throws IOException {
        Parent root = FXMLLoader.load(
                Objects.requireNonNull(getClass().getResource(path)));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void init(Stage stage) throws IOException {
        FXMLLoader authorizationLoader = new FXMLLoader(
                getClass().getResource("/ru/koigerov/carwashing/scenes/Authorization.fxml"));
        Scene scene = new Scene(authorizationLoader.load(), 800, 600);
        stage.setTitle("Автомойка");
        stage.setScene(scene);
        stage.show();
    }
}
