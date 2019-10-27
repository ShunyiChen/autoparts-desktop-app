module desktopapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires org.apache.commons.lang3;
    requires okhttp3;
    requires com.google.gson;
    requires com.h2database;

    opens com.shunyi.autoparts.ui to javafx.fxml;
    opens com.shunyi.autoparts.ui.stock to javafx.fxml;
    opens com.shunyi.autoparts.ui.products to javafx.fxml;
    opens com.shunyi.autoparts.ui.common to com.google.gson;
    opens com.shunyi.autoparts.ui.http to com.google.gson;
    opens com.shunyi.autoparts.ui.model to com.google.gson;


    exports com.shunyi.autoparts.ui;
    exports com.shunyi.autoparts.ui.purchase;
    exports com.shunyi.autoparts.ui.model;
    exports com.shunyi.autoparts.ui.supplier;
    exports com.shunyi.autoparts.ui.login;
    exports com.shunyi.autoparts.ui.main;
    exports com.shunyi.autoparts.ui.dashboard;
    exports com.shunyi.autoparts.ui.stock;
    exports com.shunyi.autoparts.ui.products;

    opens com.shunyi.autoparts.ui.purchase;
    opens com.shunyi.autoparts.ui.supplier;
    opens com.shunyi.autoparts.ui.login;
//    opens org.openjfx to javafx.fxml;
//    exports org.openjfx;
}
