package ru.koigerov.carwashing;

import ru.koigerov.carwashing.controllers.SceneController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class CarWashing extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        new SceneController().init(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}
