module desktopapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires org.apache.commons.lang3;
    
    opens com.shunyi.spareparts.ui to javafx.fxml;
    exports com.shunyi.spareparts.ui;
    exports com.shunyi.spareparts.ui.purchase;
    exports com.shunyi.spareparts.ui.model;
//    exports com.shunyi.spareparts.ui.purchase.example;
    exports com.shunyi.spareparts.ui.supplier;

    opens com.shunyi.spareparts.ui.supplier;
//    opens org.openjfx to javafx.fxml;
//    exports org.openjfx;
}
