package ru.koigerov.carwashing.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.koigerov.carwashing.db.DBManager;
import ru.koigerov.carwashing.entities.Record;
import ru.koigerov.carwashing.entities.User;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public class UsersTableController {

    @FXML
    private Button ButtonGoToAdminPanel;

    @FXML
    private Button ButtonGoToUserCreate;

    @FXML
    private TableColumn<?, ?> TableColumnAction;

    @FXML
    private TableColumn<?, ?> TableColumnLogin;

    @FXML
    private TableColumn<?, ?> TableColumnPassword;

    @FXML
    private TableColumn<?, ?> TableColumnUserName;

    @FXML
    private TableView<User> TableViewLogs;

    @FXML
    void GoToAdminPanel(ActionEvent event) throws IOException {
        new SceneController().switchToAdminPanelScene(event);
    }

    @FXML
    void GoToUserCreate(ActionEvent event) throws IOException {
        new SceneController().switchToCreateUserScene(event);
    }

    @FXML
    public void initialize() throws SQLException {
        showRecord();
    }

    private void showRecord() throws SQLException {
        ObservableList<Record> list = getRecordList();

        TableColumnCar.setCellValueFactory(new PropertyValueFactory<Record, String>("car_name"));
        TableColumnDuration.setCellValueFactory(new PropertyValueFactory<Record, String>("time"));
        TableColumnDate.setCellValueFactory(new PropertyValueFactory<Record, LocalDate>("date"));
        TableColumnService.setCellValueFactory(new PropertyValueFactory<Record, Integer>("service_id"));
        TableColumnAction.setCellValueFactory(new PropertyValueFactory<Record, Date>("deletedAt"));

        TableViewLogs.setItems(list);
    }

    public ObservableList<Record> getRecordList() throws SQLException {
        ObservableList<Record> recordList = FXCollections.observableArrayList();

        var allRecords = DBManager.getAllRecords();
        while (allRecords.next()) {
            var record = new Record
                    (
                            allRecords.getInt("id"),
                            allRecords.getInt("user_id"),
                            allRecords.getString("car_name"),
                            allRecords.getInt("service_id"),
                            allRecords.getDate("date"),
                            allRecords.getString("time"),
                            allRecords.getTimestamp("deletedAt")
                    );
            recordList.add(record);
        }
        return recordList;
    }

}
