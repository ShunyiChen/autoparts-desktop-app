package com.shunyi.autoparts.ui.purchase;

import com.shunyi.autoparts.ui.MainApp;
import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.common.HttpClient;
import com.shunyi.autoparts.ui.common.vo.PurchaseOrder;
import com.shunyi.autoparts.ui.common.vo.Supplier;
import com.shunyi.autoparts.ui.supplier.EditSupplierController;
import com.shunyi.autoparts.ui.system.EditVFSController;
import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

/**
 * @Description: 采购明细Controller
 * @Author: 陈顺谊
 * @CreateDate: 2020/4/1 3:17
 * @Version: 1.0
 */
public class PurchaseDetailsController {
    private MainApp application;
    private POEditorController controller;

//    @FXML
//    private ScrollPane scrollPane;
    @FXML
    private TableView tableView;

    @FXML
    private void create() {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        Callback<PurchaseOrder, String> callback = new Callback<PurchaseOrder, String>() {
            @Override
            public String call(PurchaseOrder param) {
                return null;
            }
        };
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/purchase/POEditor.fxml"
                )
        );
        BorderPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage dialog = new Stage();
        POEditorController controller = loader.getController();
        controller.prepare(dialog, callback, purchaseOrder);
        dialog.setTitle("新建进货单");
        dialog.initOwner(application.getStage());
        dialog.setResizable(true);
//        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();

    }

    @FXML
    private void update() {

    }

    @FXML
    private void delete() {

    }

    @FXML
    private void refresh() {

    }

    @FXML
    private void search() {

    }

    @FXML
    private void clearCondition() {

    }

    public void prepare(MainApp application) {
        this.application = application;
//
//        DoubleBinding wBinding = application.getStage().widthProperty().subtract(application.getDashboard().getNavigation().widthProperty().add(35));
//        scrollPane.prefWidthProperty().bind(wBinding);
//        tableView.prefWidthProperty().bind(wBinding);

    }
}
