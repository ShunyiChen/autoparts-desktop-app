package com.shunyi.autoparts.ui.purchase;

import com.shunyi.autoparts.ui.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class InputFormController {

    private MainApp application;

    public void setApplication(MainApp application) {
        this.application = application;
    }

    @FXML
    public void chooseSupplier(ActionEvent event) {
//        FXMLLoader loader = new FXMLLoader(
//                getClass().getResource(
//                        "/fxml/supplier/SupplierDetails.fxml"
//                )
//        );
//        SplitPane root = null;
//        try {
//            root = loader.load();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        SupplierDetailsController controller = loader.getController();
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
