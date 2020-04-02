package com.shunyi.autoparts.ui.supplier;

import com.shunyi.autoparts.ui.common.Constants;
import com.shunyi.autoparts.ui.common.Env;
import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.common.HttpClient;
import com.shunyi.autoparts.ui.common.vo.Supplier;
import com.shunyi.autoparts.ui.common.vo.SupplierCategory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

/** 编辑供应商Controller */
public class EditSupplierController {
    @FXML
    private TextField txtCategory;
    @FXML
    private TextField txtCode;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtContact;
    @FXML
    private TextField txtPhone1;
    @FXML
    private TextField txtPhone2;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPostcode;
//    @FXML
//    private ComboBox<Grade> comboGrade;
//    @FXML
//    private ComboBox<Payment> comboPayment;
    @FXML
    private TextField txtNotes;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnContinueAdding;
    private Stage dialog;
    private Supplier updatedSupplier;
    private SupplierCategory selectedCategory;
    private Callback<Supplier, Object> callback;

    @FXML
    private void choose(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource(
                    "/fxml/supplier/SupplierCategoryChooser.fxml"
            )
        );
        VBox root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage subStage = new Stage();
        subStage.setOnHiding( e -> {
        });
        SupplierCategoryChooserController controller = loader.getController();
        Callback<SupplierCategory, Object> cb = new Callback<SupplierCategory, Object>() {
            @Override
            public Object call(SupplierCategory param) {
                selectedCategory = param;
                txtCategory.setText(selectedCategory.getName());
                return null;
            }
        };
        controller.prepare(subStage, selectedCategory, cb);
        subStage.setTitle("选择供应商类目");
        subStage.initOwner(dialog);
        subStage.setResizable(false);
        subStage.initModality(Modality.APPLICATION_MODAL);
        subStage.setScene(scene);
        // center stage on screen
        subStage.centerOnScreen();
        subStage.show();
    }

    @FXML
    private void cancel(ActionEvent event) {
        dialog.close();
    }

    @FXML
    private void saveAndClose(ActionEvent event) {
        if(updatedSupplier == null) {
            add(true);
        } else {
            update(true);
        }
    }

    @FXML
    private void continueAdding(ActionEvent event) {
        add(false);
    }

    private boolean checkInputs() {
        if(selectedCategory == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("类目不能为空");
            alert.show();
            return false;
        }
        if(txtCode.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("编码不能为空");
            alert.show();
            return false;
        }
        if(txtName.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("供应商名称不能为空");
            alert.show();
            return false;
        }
        return true;
    }

    private void update(boolean closeStage) {
        if(checkInputs()) {
//            if(closeStage) {
//                dialog.close();
//            }
//            Supplier supplier = new Supplier(0L, txtCode.getText(), txtName.getText(), txtContact.getText(),txtPhone1.getText(),txtPhone2.getText(),txtEmail.getText(), txtAddress.getText(), txtPostcode.getText(), comboGrade.getValue(), comboPayment.getValue(), txtNotes.getText(), selectedCategory, null, null, null, Env.getInstance().currentUser(), null, null, Constants.DELETE_FLAG_FALSE, null);
//            supplier.setId(updatedSupplier.getId());
//            callback.call(supplier);
        }
    }

    private void add(boolean closeStage) {
        if(checkInputs()) {
//            if(closeStage) {
//                dialog.close();
//            }
//            Supplier supplier = new Supplier(0L, txtCode.getText(), txtName.getText(), txtContact.getText(),txtPhone1.getText(),txtPhone2.getText(),txtEmail.getText(), txtAddress.getText(), txtPostcode.getText(), comboGrade.getValue(), comboPayment.getValue(), txtNotes.getText(), selectedCategory, null, null, null, Env.getInstance().currentUser(), null, null, Constants.DELETE_FLAG_FALSE, null);
//            callback.call(supplier);
        }
    }

    /**
     *
     * @param dialog
     */
    public void prepare(Stage dialog, Supplier updatedSupplier, Callback<Supplier, Object> callback) {
        this.dialog = dialog;
        this.updatedSupplier = updatedSupplier;
        this.callback = callback;
        btnSave.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));

        initGradeList();
        initPaymentList();

        if(updatedSupplier != null) {
//            try {
//                String json = HttpClient.GET("/supplier/categories/"+updatedSupplier.getCategory().getId());
//                selectedCategory = GoogleJson.GET().fromJson(json, SupplierCategory.class);
//                String name = selectedCategory.getId() == 0 ? "所有供应商":selectedCategory.getName();
//                txtCategory.setText(name);
//                txtCode.setText(updatedSupplier.getCode());
//                txtName.setText(updatedSupplier.getName());
//                txtContact.setText(updatedSupplier.getContact());
//                txtPhone1.setText(updatedSupplier.getPhone1());
//                txtPhone2.setText(updatedSupplier.getNotes());
//                txtAddress.setText(updatedSupplier.getAddress());
//                txtEmail.setText(updatedSupplier.getEmail());
//                txtPostcode.setText(updatedSupplier.getPostCode());
//                comboGrade.setValue(updatedSupplier.getGrade());
//                comboPayment.setValue(updatedSupplier.getPayment());
//                txtNotes.setText(updatedSupplier.getNotes());
//                btnContinueAdding.setVisible(false);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }

    private void initGradeList() {
//        comboGrade.setStyle("-fx-font-size: 14px;");
//        try {
//            String json = HttpClient.GET("/grades");
//            Grade[] grades = GoogleJson.GET().fromJson(json, Grade[].class);
//            comboGrade.getItems().addAll(grades);
//            comboGrade.getSelectionModel().select(0);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private void initPaymentList() {
//        comboPayment.setStyle("-fx-font-size: 14px;");
//        try {
//            String json = HttpClient.GET("/payments");
//            Payment[] payments = GoogleJson.GET().fromJson(json, Payment[].class);
//            comboPayment.getItems().addAll(payments);
//            comboPayment.getSelectionModel().select(0);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
