module ru.dediev.geekcloudclient.geekcloudclient {
    requires javafx.controls;
    requires javafx.fxml;


    opens ru.dediev.geekcloudclient.geekcloudclient to javafx.fxml;
    exports ru.dediev.geekcloudclient.geekcloudclient;
}