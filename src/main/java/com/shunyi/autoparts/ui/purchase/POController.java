package com.shunyi.autoparts.ui.purchase;

import com.shunyi.autoparts.ui.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

/**
 * @Description: 采购订单Controller
 * @Author: Shunyi
 * @CreateDate: 2020/4/4
 * @Version: 1.0
 */
public class POController {
    private MainApp application;
    private POEditorController poEditorController;

    @FXML
    private TextField txtNo;
    @FXML
    private TextField txtSupplier;
    @FXML
    private ComboBox comboBoxInvoiceType;
    @FXML
    private TextField txtInvoiceNo;
    @FXML
    private TextField txtNotes;
    @FXML
    private ComboBox comboBoxStatus;
    @FXML
    private DatePicker fromDate;
    @FXML
    private DatePicker toDate;
    @FXML
    private TableView<PO> tableView;
    @FXML
    private TableColumn colDate;
    @FXML
    private TableColumn colNo;
    @FXML
    private TableColumn colSupplierCode;
    @FXML
    private TableColumn colSupplierName;
    @FXML
    private TableColumn colContact;
    @FXML
    private TableColumn colPhone;
    @FXML
    private TableColumn colInvoiceType;
    @FXML
    private TableColumn colInvoiceNo;
    @FXML
    private TableColumn colFreight;
    @FXML
    private TableColumn colNotes;
    @FXML
    private TableColumn colPayment;
    @FXML
    private TableColumn colAccount;
    @FXML
    private TableColumn colOperator;
    @FXML
    private TableColumn colPaymentAmount;
    @FXML
    private TableColumn colTotalQty;
    @FXML
    private TableColumn colDiscountedAmount;
    @FXML
    private TableColumn colTotalAmount;
    @FXML
    private TableColumn colTaxIncludedAmount;
    @FXML
    private TableColumn colDiscount;
    @FXML
    private TableColumn colAmountAfterDiscount;
    @FXML
    private TableColumn colAccountPayable;
    @FXML
    private TableColumn colStatus;

    @FXML
    private void create() {
        Callback<PO, String> callback = new Callback<PO, String>() {
            @Override
            public String call(PO param) {
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
        poEditorController = loader.getController();
        poEditorController.initialize(dialog, callback,false);
        dialog.setTitle("新建进货单");
        dialog.initOwner(application.getStage());
        dialog.setResizable(true);
        dialog.setMaximized(true);
        dialog.initModality(Modality.APPLICATION_MODAL);
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
    private void clearQueryConditions() {
        txtNo.setText("");
        txtSupplier.setText("");
        comboBoxInvoiceType.setValue(null);
        txtInvoiceNo.setText("");
        txtNotes.setText("");
        comboBoxStatus.setValue(null);
        fromDate.setValue(null);
        toDate.setValue(null);
    }

    public void clean() {
        tableView.getItems().clear();
    }

    public void initialize(MainApp application) {
        this.application = application;
    }
}
