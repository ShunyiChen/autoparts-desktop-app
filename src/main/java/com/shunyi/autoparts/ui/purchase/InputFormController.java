package com.shunyi.autoparts.ui.purchase;

import com.shunyi.autoparts.ui.supplier.SupplierManagementController;
import com.shunyi.autoparts.ui.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class InputFormController {

    private MainApp application;

    public void setApplication(MainApp application) {
        this.application = application;
    }

    @FXML
    public void chooseSupplier(ActionEvent event) {
//        FXMLLoader loader = new FXMLLoader(
//                getClass().getResource(
//                        "/fxml/supplier/supplier_management.fxml"
//                )
//        );
//        SplitPane root = null;
//        try {
//            root = loader.load();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        SupplierManagementController controller = loader.getController();
//        controller.initTree();
//        Scene scene = new Scene(root);
//        Stage dialog = new Stage();
//        dialog.initOwner(application.getStage());
//        dialog.initModality(Modality.APPLICATION_MODAL);
//        dialog.setScene(scene);
//        dialog.show();
//        // center stage on screen
//        dialog.centerOnScreen();
    }
}
