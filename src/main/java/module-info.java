module com.gorup22 {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;

    opens com.group22 to javafx.fxml;
    exports com.group22;
}
