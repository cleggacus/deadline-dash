module com.gorup22 {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;

    opens com.gorup22 to javafx.fxml;
    exports com.gorup22;
}
