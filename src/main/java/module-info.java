module desktopapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires org.apache.commons.lang3;
    
    opens com.shunyi.spareparts.ui to javafx.fxml;
    exports com.shunyi.spareparts.ui;
    exports com.shunyi.spareparts.ui.purchase;
    exports com.shunyi.spareparts.ui.purchase.model;
    exports com.shunyi.spareparts.ui.purchase.example;
//    opens org.openjfx to javafx.fxml;
//    exports org.openjfx;
}
