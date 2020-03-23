module desktopapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires org.apache.commons.lang3;
    requires okhttp3;
    requires com.google.gson;
    requires com.h2database;
    requires com.fasterxml.jackson.annotation;
    requires itextpdf;
    requires spring.security.crypto;
    requires commons.vfs2;
    requires commons.logging;
    requires org.apache.commons.io;

    exports com.shunyi.autoparts.ui;
    exports com.shunyi.autoparts.ui.buy;
    exports com.shunyi.autoparts.ui.system;
    exports com.shunyi.autoparts.ui.common.vo;
    exports com.shunyi.autoparts.ui.supplier;
    exports com.shunyi.autoparts.ui.login;
    exports com.shunyi.autoparts.ui.main;
    exports com.shunyi.autoparts.ui.dashboard;
    exports com.shunyi.autoparts.ui.stock;
    exports com.shunyi.autoparts.ui.products;
    exports com.shunyi.autoparts.ui.common.deleteme;

    opens com.shunyi.autoparts.ui.buy;
    opens com.shunyi.autoparts.ui.supplier;
    opens com.shunyi.autoparts.ui.login;
    opens com.shunyi.autoparts.ui to javafx.fxml;
    opens com.shunyi.autoparts.ui.main to javafx.fxml;
    opens com.shunyi.autoparts.ui.stock to javafx.fxml;
    opens com.shunyi.autoparts.ui.products to javafx.fxml;
    opens com.shunyi.autoparts.ui.system to javafx.fxml;
    opens com.shunyi.autoparts.ui.common to com.google.gson;
    opens com.shunyi.autoparts.ui.common.vo to com.google.gson;
}
