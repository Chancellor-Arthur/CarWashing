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
import ru.koigerov.carwashing.entities.Service;
import ru.koigerov.carwashing.utils.ActionButtonTableCell;

import java.io.IOException;
import java.sql.SQLException;

public class ServiceTableController {

    @FXML
    private Button ButtonGoToAdminPanel;

    @FXML
    private Button ButtonGoToServiceCreate;

    @FXML
    private TableColumn<Service, Button> TableColumnAction;

    @FXML
    private TableColumn<Service, Integer> TableColumnDuration;

    @FXML
    private TableColumn<Service, String> TableColumnService;

    @FXML
    private TableView<Service> TableViewLogs;

    @FXML
    void GoToAdminPanel(ActionEvent event) throws IOException {
        new SceneController().switchToAdminPanelScene(event);
    }

    @FXML
    void GoToServiceCreate(ActionEvent event) throws IOException {
        new SceneController().switchToCreateServiceScene(event);
    }


    @FXML
    public void initialize() throws SQLException {
        showService();
    }

    private void showService() throws SQLException {
        ObservableList<Service> list = getServiceList();

        TableColumnDuration.setCellValueFactory(new PropertyValueFactory<Service, Integer>("duration"));
        TableColumnService.setCellValueFactory(new PropertyValueFactory<Service, String>("name"));

        TableColumnAction.setCellFactory(ActionButtonTableCell.<Service>forTableColumn("Remove", (Service service) -> {
            TableViewLogs.getItems().remove(service);
            try {
                DBManager.removeService(service.getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return service;
        }));

        TableViewLogs.setItems(list);
    }

    public ObservableList<Service> getServiceList() throws SQLException {
        ObservableList<Service> serviceList = FXCollections.observableArrayList();

        var allService = DBManager.getAllService();
        while (allService.next()) {
            var service = new Service
                    (
                            allService.getInt("id"),
                            allService.getString("service_name"),
                            allService.getInt("duration")
                    );
            serviceList.add(service);
        }
        return serviceList;
    }

}
