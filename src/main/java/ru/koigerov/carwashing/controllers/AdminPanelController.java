package ru.koigerov.carwashing.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.koigerov.carwashing.entities.Record;
import ru.koigerov.carwashing.db.DBManager;
import ru.koigerov.carwashing.entities.Service;
import ru.koigerov.carwashing.utils.ActionButtonTableCell;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class AdminPanelController {

    @FXML
    private Button ButtonGoToRecord;

    @FXML
    private Button ButtonGoToService;

    @FXML
    private Button ButtonGoToUsersTable;

    @FXML
    private ComboBox<String> ServiceFilter;

    @FXML
    private TableColumn<Record, Button> TableColumnAction;

    @FXML
    private TableColumn<Record, String> TableColumnCar;

    @FXML
    private TableColumn<Record, LocalDate> TableColumnDate;

    @FXML
    private TableColumn<Record, String> TableColumnDuration;

    @FXML
    private TableColumn<Record, Integer> TableColumnService;

    @FXML
    private TableView<Record> TableViewLogs;

    @FXML
    void GoToRecord(ActionEvent event) throws IOException {
        new SceneController().switchToRecordScene(event);
    }

    @FXML
    void GoToService(ActionEvent event) throws IOException {
        new SceneController().switchToShowServiceScene(event);
    }

    @FXML
    void GoToUsersTable(ActionEvent event) throws IOException {
        new SceneController().switchToShowUsersScene(event);
    }

    @FXML
    void FilterServices(ActionEvent event) throws SQLException {
        String service;
        if (Objects.equals(ServiceFilter.getValue(), "Не выбрано")) service = null;
        else service = ServiceFilter.getValue();
        showRecord(service);
    }

    @FXML
    public void initialize() throws SQLException {
        ObservableList<String> serviceNameList = FXCollections.observableArrayList();
        var service = DBManager.getAllService();
        while (service.next()) serviceNameList.add(service.getString("service_name"));
        serviceNameList.add("Не выбрано");
        ServiceFilter.setItems(serviceNameList);

        showRecord(null);
    }

    private void showRecord(String neededService) throws SQLException {
        ObservableList<Record> list = getRecordList(neededService);

        TableColumnCar.setCellValueFactory(new PropertyValueFactory<Record, String>("car"));
        TableColumnDuration.setCellValueFactory(new PropertyValueFactory<Record, String>("time"));
        TableColumnDate.setCellValueFactory(new PropertyValueFactory<Record, LocalDate>("date"));
        TableColumnService.setCellValueFactory(new PropertyValueFactory<Record, Integer>("service"));


        TableColumnAction.setCellFactory(ActionButtonTableCell.<Record>forTableColumn("Удалить", (Record record) -> {
            TableViewLogs.getItems().remove(record);
            try {
                DBManager.removeRecord(record.getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return record;
        }));

        TableViewLogs.setItems(list);
    }

    public ObservableList<Record> getRecordList(String neededService) throws SQLException {
        ObservableList<Record> recordList = FXCollections.observableArrayList();

        var allRecords = DBManager.getAllRecords(false);
        var allServices = DBManager.getAllService();
        List<Service> services = new ArrayList<>();

        while (allServices.next()) {
            var service = new Service(allServices.getInt("id"),
                    allServices.getString("service_name"), allServices.getInt("duration"));
            services.add(service);
        }

        while (allRecords.next()) {
            var record = new Record
                    (
                            allRecords.getInt("id"),
                            allRecords.getInt("user_id"),
                            allRecords.getString("car_name"),
                            services.stream().filter(service -> {
                                try {
                                    return service.getId() == allRecords.getInt("service_id");
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                            }).findFirst().get().getName(),
                            allRecords.getDate("date").toLocalDate(),
                            allRecords.getString("time")
                    );
            if (neededService == null) recordList.add(record);
            if (Objects.equals(neededService, record.getService())) recordList.add(record);
        }
        return recordList;
    }

}
