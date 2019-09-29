module desktopapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires org.apache.commons.lang3;
    
    opens com.shunyi.autoparts.ui to javafx.fxml;
    exports com.shunyi.autoparts.ui;
    exports com.shunyi.autoparts.ui.purchase;
    exports com.shunyi.autoparts.ui.model;
    exports com.shunyi.autoparts.ui.supplier;
    exports com.shunyi.autoparts.ui.login;
    exports com.shunyi.autoparts.ui.main;
    exports com.shunyi.autoparts.ui.dashboard;


    opens com.shunyi.autoparts.ui.purchase;
    opens com.shunyi.autoparts.ui.supplier;
    opens com.shunyi.autoparts.ui.login;
//    opens org.openjfx to javafx.fxml;
//    exports org.openjfx;
}
