module desktopapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.commons.lang3;
    
    opens com.shunyi.spareparts.ui to javafx.fxml;
    exports com.shunyi.spareparts.ui;
//    opens org.openjfx to javafx.fxml;
//    exports org.openjfx;
}
