package com.shunyi.autoparts.ui.supplier;

import com.shunyi.autoparts.ui.common.AutoCompleteBox;
import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.common.HttpClient;
import com.shunyi.autoparts.ui.common.vo.Company;
import com.shunyi.autoparts.ui.common.vo.Supplier;
import com.shunyi.autoparts.ui.common.vo.SupplierCategory;
import com.shunyi.autoparts.ui.products.CompanyChooserController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.commons.lang3.StringUtils;

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
    private Company company;

    @FXML
    private FlowPane buttonPanel;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSaveAndQuit;
    @FXML
    private Button btnAdd;
    @FXML
    private ComboBox<String> comboBoxCategory;
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
    private TextField txtFullName;
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
    private ComboBox<String> comboBoxCompany;
    @FXML
    private TextField txtMobile;
    @FXML
    private TextField txtNotes;
    @FXML
    private TextField txtPostcode;

    /**
     *
     * @param dialog
     * @param callback
     * @param supplier
     */
    public void initialize(Stage dialog, Callback<Supplier, String> callback, Supplier supplier) {
        this.dialog = dialog;
        this.callback = callback;
        this.supplier = supplier;
        btnSaveAndQuit.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
        //初始化供应商分类
        try {
            SupplierCategory[] categories = HttpClient.GET("/supplier/categories", SupplierCategory[].class);
            comboBoxCategory.getItems().addAll(Arrays.asList(categories).stream().map(e -> e.getName()).collect(Collectors.toList()));
            new AutoCompleteBox(comboBoxCategory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //初始化供应商下拉框
        try {
            comboBoxCompany.getItems().clear();
            Company[] companies = HttpClient.GET("/companies", Company[].class);
            comboBoxCompany.getItems().addAll(Arrays.asList(companies).stream().map(e->e.getName()).collect(Collectors.toList()));
            new AutoCompleteBox(comboBoxCompany);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(supplier != null) {
            buttonPanel.getChildren().clear();
            buttonPanel.getChildren().addAll(btnCancel, btnSaveAndQuit);
            txtCode.setText(supplier.getCode());
            comboBoxCategory.setValue(supplier.getSupplierCategory().getName());
            txtCity.setText(supplier.getCity());
            txtName.setText(supplier.getName());
            txtContact.setText(supplier.getContact());
            txtPostcode.setText(supplier.getPostCode());
            txtAddress.setText(supplier.getAddress());
            txtPhone.setText(supplier.getPhone());
            txtFax.setText(supplier.getFax());
            txtWebsite.setText(supplier.getWebSite());
            txtEmail.setText(supplier.getEmail());
            txtFullName.setText(supplier.getFullName());
            txtLegal.setText(supplier.getCorp());
            txtBank.setText(supplier.getBank());
            txtAccount.setText(supplier.getAccount());
            txtTaxNo.setText(supplier.getTaxFileNumber());
            txtBilling.setText(supplier.getBillingAddress());
            comboBoxCompany.setValue(supplier.getCompany());
            txtMobile.setText(supplier.getMobile());
            txtNotes.setText(supplier.getNotes());
        }
    }

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
                comboBoxCategory.setValue(supplierCategory.getName());
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
    private void openCompanyChooser() {
        Callback<Company, String> callback = new Callback<Company, String>() {
            @Override
            public String call(Company param) {
                company = param;
                comboBoxCompany.setValue(company.getName());
                return null;
            }
        };
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/products/CompanyChooser.fxml"
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
        CompanyChooserController controller = loader.getController();
        controller.initialize(dialog, callback, company);
        dialog.setTitle("公司选择器");
        dialog.initOwner(this.dialog);
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    @FXML
    private void cancel() {
        dialog.close();
    }

    @FXML
    private void continueAdding() {
        save(false);
    }

    @FXML
    private void saveAndQuit() {
        if(supplier == null) {
            save(true);
        } else {
            update(true);
        }

    }

    private void save(boolean closeDialog) {
        if(validate()) {
            insertItemsIFNotExist();
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
            supplier.setFullName(txtFullName.getText());
            supplier.setCorp(txtLegal.getText());
            supplier.setBank(txtBank.getText());
            supplier.setAccount(txtAccount.getText());
            supplier.setTaxFileNumber(txtTaxNo.getText());
            supplier.setBillingAddress(txtBilling.getText());
            supplier.setCompany(comboBoxCompany.getValue());
            supplier.setMobile(txtMobile.getText());
            supplier.setNotes(txtNotes.getText());
            try {
                String json = GoogleJson.GET().toJson(supplier);
                String idStr = HttpClient.POST("/suppliers", json);
                supplier.setId(Long.valueOf(idStr));
                callback.call(supplier);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(closeDialog) {
                dialog.close();
            }
        }
    }

    private void update(boolean closeDialog) {
        if(validate()) {
            insertItemsIFNotExist();
            Supplier supplier = new Supplier();
            supplier.setId(this.supplier.getId());
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
            supplier.setFullName(txtFullName.getText());
            supplier.setCorp(txtLegal.getText());
            supplier.setBank(txtBank.getText());
            supplier.setAccount(txtAccount.getText());
            supplier.setTaxFileNumber(txtTaxNo.getText());
            supplier.setBillingAddress(txtBilling.getText());
            supplier.setCompany(comboBoxCompany.getValue());
            supplier.setMobile(txtMobile.getText());
            supplier.setNotes(txtNotes.getText());
            try {
                String json = GoogleJson.GET().toJson(supplier);
                HttpClient.PUT("/suppliers/"+supplier.getId(), json);
                callback.call(supplier);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(closeDialog) {
                dialog.close();
            }
        }
    }

    /**
     * 插入新选项如果它不存在
     */
    private void insertItemsIFNotExist() {
        //供应商分类如果不存在则新建
        if(comboBoxCategory.getValue() != null && !comboBoxCategory.getValue().trim().equals("")) {
            try {
                SupplierCategory rootSupplierCategory = HttpClient.GET("/supplier/categories/root", SupplierCategory.class);
                supplierCategory = new SupplierCategory();
                supplierCategory.setName(comboBoxCategory.getValue());
                supplierCategory.setParent(false);
                supplierCategory.setParentId(rootSupplierCategory.getId());
                String json = GoogleJson.GET().toJson(supplierCategory);
                String idStr = HttpClient.POST("/supplier/categories", json);
                supplierCategory.setId(Long.valueOf(idStr));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //公司如果不存在则新建
        if(comboBoxCompany.getValue() != null && !comboBoxCompany.getValue().trim().equals("")) {
            try {
                company = new Company();
                company.setName(comboBoxCompany.getValue());
                String json = GoogleJson.GET().toJson(company);
                String idStr = HttpClient.POST("/companies", json);
                company.setId(Long.valueOf(idStr));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean validate() {
        if(StringUtils.isEmpty(comboBoxCategory.getValue())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("类目不能为空");
            alert.show();
            return false;
        }
        if(StringUtils.isEmpty(txtCode.getText())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("编码不能为空");
            alert.show();
            return false;
        }
        if(StringUtils.isEmpty(txtName.getText())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("供应商名称不能为空");
            alert.show();
            return false;
        }
        return true;
    }

}
