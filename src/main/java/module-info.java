module ma.enset.demo3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens ma.enset.demo3 to javafx.fxml;
    opens ma.enset.demo3.entity to javafx.base;
    exports ma.enset.demo3;
}