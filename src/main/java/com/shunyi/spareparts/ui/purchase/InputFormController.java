package com.shunyi.spareparts.ui.purchase;

import com.shunyi.spareparts.ui.MainApp;
import com.shunyi.spareparts.ui.supplier.ChooserController;
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
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/supplier/supplier_chooser.fxml"
                )
        );
        SplitPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ChooserController controller = loader.getController();
        controller.initTree();

        Scene scene = new Scene(root);
        Stage dialog = new Stage();
        dialog.initOwner(application.getStage());
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        dialog.show();
        // center stage on screen
        dialog.centerOnScreen();
    }
}
