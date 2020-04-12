package com.shunyi.autoparts.ui.supplier;

import com.shunyi.autoparts.ui.common.AutoCompleteBox;
import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.common.HttpClient;
import com.shunyi.autoparts.ui.common.vo.Company;
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
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @Description: 供应商编辑器Controller
 * @Author: Shunyi Chen
 * @CreateDate: 2020/4/12
 */
public class SupplierEditorController {
    private Stage dialog;
    private Callback<Supplier, String> callback;
    private Supplier supplier;
    private SupplierCategory supplierCategory;


    @FXML
    private TextField txtCategory;
    @FXML
    private TextField txtCode;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtContact;
    @FXML
    private TextField txtCity;
    @FXML
    private TextField txtPhone;
    @FXML
    private TextField txtFax;
    @FXML
    private TextField txtWebsite;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtCompanyFullName;
    @FXML
    private TextField txtLegal;
    @FXML
    private TextField txtBank;
    @FXML
    private TextField txtAccount;
    @FXML
    private TextField txtTaxNo;
    @FXML
    private TextField txtBilling;
    @FXML
    private ComboBox<String> combBoxCompany;
    @FXML
    private TextField txtMobile;
    @FXML
    private TextField txtNotes;
    @FXML
    private TextField txtPostcode;
    @FXML
    private Button btnSaveAndQuit;
    @FXML
    private Button btnContinueAdding;

    @FXML
    private void openCategoryChooser() {
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
        Callback<SupplierCategory, String> callback = new Callback<SupplierCategory, String>() {
            @Override
            public String call(SupplierCategory param) {
                supplierCategory = param;
                txtCategory.setText(supplierCategory.getName());
                return null;
            }
        };
        controller.initialize(subStage, callback, supplierCategory);
        subStage.setTitle("选择供应商分类");
        subStage.initOwner(dialog);
        subStage.setResizable(false);
        subStage.initModality(Modality.APPLICATION_MODAL);
        subStage.setScene(scene);
        // center stage on screen
        subStage.centerOnScreen();
        subStage.show();
    }

    @FXML
    private void saveAndQuit() {
        Callback<Supplier, String> cb = new Callback<Supplier, String>() {
            @Override
            public String call(Supplier param) {
                callback.call(param);
                dialog.close();
                return null;
            }
        };
        save(cb);
    }

    @FXML
    private void cancel() {
        dialog.close();
    }

    @FXML
    private void continueAdding() {
        Callback<Supplier, String> cb = new Callback<Supplier, String>() {
            @Override
            public String call(Supplier param) {
                callback.call(param);
                return null;
            }
        };
        save(cb);
    }

    private void save(Callback<Supplier, String> callback) {
        if(validate()) {
            Supplier supplier = new Supplier();
            supplier.setCode(txtCode.getText());
            supplier.setName(txtName.getText());
            supplier.setSupplierCategory(supplierCategory);
            supplier.setCity(txtCity.getText());
            supplier.setContact(txtContact.getText());
            supplier.setPostCode(txtPostcode.getText());
            supplier.setAddress(txtAddress.getText());
            supplier.setPhone(txtPhone.getText());
            supplier.setFax(txtFax.getText());
            supplier.setWebSite(txtWebsite.getText());
            supplier.setEmail(txtEmail.getText());
            supplier.setFullName(txtCompanyFullName.getText());
            supplier.setCorp(txtLegal.getText());
            supplier.setBank(txtBank.getText());
            supplier.setAccount(txtAccount.getText());
            supplier.setTaxFileNumber(txtTaxNo.getText());
            supplier.setBillingAddress(txtBilling.getText());
            supplier.setCompany(combBoxCompany.getValue());
            supplier.setMobile(txtMobile.getText());
            supplier.setNotes(txtNotes.getText());

            Company company = new Company();
            company.setName(combBoxCompany.getValue());
            System.out.println("gos :"+combBoxCompany.getValue());
            String json = GoogleJson.GET().toJson(company);
            try {
                HttpClient.POST("/companies", json);
            } catch (IOException e) {
                e.printStackTrace();
            }
            json = GoogleJson.GET().toJson(supplier);
            try {
                String idStr = HttpClient.POST("/suppliers", json);
                supplier.setId(Long.valueOf(idStr));
            } catch (IOException e) {
                e.printStackTrace();
            }
            callback.call(supplier);
        }
    }

    private boolean validate() {
        if(supplierCategory == null) {
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

    public void initialize(Stage dialog, Callback<Supplier, String> callback, Supplier supplier) {
        this.dialog = dialog;
        this.callback = callback;
        this.supplier = supplier;
        btnSaveAndQuit.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
        new AutoCompleteBox(combBoxCompany);
        try {
            Company[] companies = HttpClient.GET("/companies", Company[].class);
            combBoxCompany.getItems().clear();
            combBoxCompany.getItems().addAll(Arrays.asList(companies).stream().map(e->e.getName()).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(supplier != null) {
            txtCode.setText(supplier.getCode());
            txtCategory.setText(supplier.getSupplierCategory().getName());
            txtCity.setText(supplier.getCity());
            txtName.setText(supplier.getName());
            txtContact.setText(supplier.getContact());
            txtPostcode.setText(supplier.getPostCode());
            txtAddress.setText(supplier.getAddress());
            txtPhone.setText(supplier.getPhone());
            txtFax.setText(supplier.getFax());
            txtWebsite.setText(supplier.getWebSite());
            txtEmail.setText(supplier.getEmail());
            txtCompanyFullName.setText(supplier.getCompany());
            txtLegal.setText(supplier.getCorp());
            txtBank.setText(supplier.getBank());
            txtAccount.setText(supplier.getAccount());
            txtFax.setText(supplier.getTaxFileNumber());
            txtBilling.setText(supplier.getBillingAddress());
            int index = combBoxCompany.getItems().indexOf(supplier.getCompany());
            combBoxCompany.setValue(combBoxCompany.getItems().get(index));
            txtMobile.setText(supplier.getMobile());
            txtNotes.setText(supplier.getNotes());
            btnContinueAdding.setVisible(false);
        }
    }
}
