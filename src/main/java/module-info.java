module ru.koigerov.carwashing {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens ru.koigerov.carwashing.controllers to javafx.fxml;
    opens ru.koigerov.carwashing.entities to javafx.base;
    exports ru.koigerov.carwashing;
    opens ru.koigerov.carwashing.utils to javafx.base;
}