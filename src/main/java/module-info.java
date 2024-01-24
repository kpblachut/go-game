module org.example {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example to javafx.fxml;
    exports org.example;

    exports org.example.client;
    opens org.example.client to javafx.fxml;
}