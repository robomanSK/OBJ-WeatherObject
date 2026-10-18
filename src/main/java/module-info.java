module com.example.weatherobject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.weatherobject to javafx.fxml;
    exports com.example.weatherobject;
}