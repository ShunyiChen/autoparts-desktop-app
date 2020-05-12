package com.shunyi.autoparts.ui.consumer;

import com.shunyi.autoparts.ui.common.AutoCompleteBox;
import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.common.HttpClient;
import com.shunyi.autoparts.ui.common.NumberValidationUtils;
import com.shunyi.autoparts.ui.common.vo.Company;
import com.shunyi.autoparts.ui.common.vo.Consumer;
import com.shunyi.autoparts.ui.common.vo.ConsumerCategory;
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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @Description: 客户编辑器Controller
 * @Author: Shunyi
 * @CreateDate: 2020/5/12
 */
public class ConsumerEditorController {
    private Stage dialog;
    private Callback<Consumer, String> callback;
    private Consumer consumer;
    private ConsumerCategory consumerCategory;
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
    private TextField txtAddress;
    @FXML
    private TextField txtPhone;
    @FXML
    private TextField txtLineOfCredit;
    @FXML
    private TextField txtTotalAmountReceived;
    @FXML
    private TextField txtLicensePlate;
    @FXML
    private TextField txtEmail;
    @FXML
    private ComboBox<String> comboBoxCompany;
    @FXML
    private TextField txtAmountOwning;
    @FXML
    private TextField txtNotes;

    /**
     *
     * @param dialog
     * @param callback
     * @param consumer
     */
    public void initialize(Stage dialog, Callback<Consumer, String> callback, Consumer consumer) {
        this.dialog = dialog;
        this.callback = callback;
        this.consumer = consumer;
        btnSaveAndQuit.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
        //初始化客户分类
        try {
            ConsumerCategory[] categories = HttpClient.GET("/consumer/categories", ConsumerCategory[].class);
            comboBoxCategory.getItems().addAll(Arrays.asList(categories).stream().map(e -> e.getName()).collect(Collectors.toList()));
            new AutoCompleteBox(comboBoxCategory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //初始化客户下拉框
        try {
            comboBoxCompany.getItems().clear();
            Company[] companies = HttpClient.GET("/companies", Company[].class);
            comboBoxCompany.getItems().addAll(Arrays.asList(companies).stream().map(e->e.getName()).collect(Collectors.toList()));
            new AutoCompleteBox(comboBoxCompany);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(consumer != null) {
            buttonPanel.getChildren().clear();
            buttonPanel.getChildren().addAll(btnCancel, btnSaveAndQuit);
            txtCode.setText(consumer.getCode());
            comboBoxCategory.setValue(consumer.getConsumerCategory().getName());
            txtAddress.setText(consumer.getAddress());
            txtName.setText(consumer.getName());
            txtContact.setText(consumer.getContact());
            txtPhone.setText(consumer.getPhone());
            txtLicensePlate.setText(consumer.getLicensePlate());
            txtAmountOwning.setText(consumer.getAmountOwing().toString());
            txtLineOfCredit.setText(consumer.getLineOfCredit().toString());
            txtTotalAmountReceived.setText(consumer.getTotalAmountReceived().toString());
            txtEmail.setText(consumer.getEmail());
            comboBoxCompany.setValue(consumer.getCompany());
            txtNotes.setText(consumer.getNotes());
        }
    }

    @FXML
    private void openCategoryChooser() {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/consumer/ConsumerCategoryChooser.fxml"
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
        ConsumerCategoryChooserController controller = loader.getController();
        Callback<ConsumerCategory, String> callback = new Callback<ConsumerCategory, String>() {
            @Override
            public String call(ConsumerCategory param) {
                consumerCategory = param;
                comboBoxCategory.setValue(consumerCategory.getName());
                return null;
            }
        };
        controller.initialize(subStage, callback, consumerCategory);
        subStage.setTitle("选择客户分类");
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
        if(consumer == null) {
            save(true);
        } else {
            update(true);
        }

    }

    private void save(boolean closeDialog) {
        if(validate()) {
            insertItemsIFNotExist();
            Consumer consumer = new Consumer();
            consumer.setCode(txtCode.getText());
            consumer.setName(txtName.getText());
            consumer.setConsumerCategory(consumerCategory);
            consumer.setContact(txtContact.getText());
            consumer.setPhone(txtPhone.getText());
            consumer.setAddress(txtAddress.getText());
            consumer.setEmail(txtEmail.getText());
            consumer.setLicensePlate(txtLicensePlate.getText());
            consumer.setAmountOwing(NumberValidationUtils.isRealNumber(txtAmountOwning.getText())? new BigDecimal(txtAmountOwning.getText()).setScale(2, RoundingMode.HALF_UP):BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
            consumer.setLineOfCredit(NumberValidationUtils.isRealNumber(txtLineOfCredit.getText())? new BigDecimal(txtLineOfCredit.getText()).setScale(2, RoundingMode.HALF_UP):BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
            consumer.setTotalAmountReceived(NumberValidationUtils.isRealNumber(txtTotalAmountReceived.getText())? new BigDecimal(txtTotalAmountReceived.getText()).setScale(2, RoundingMode.HALF_UP):BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
            consumer.setCompany(comboBoxCompany.getValue());
            consumer.setNotes(txtNotes.getText());
            try {
                String json = GoogleJson.GET().toJson(consumer);
                String idStr = HttpClient.POST("/consumers", json);
                consumer.setId(Long.valueOf(idStr));
                callback.call(consumer);
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
            Consumer consumer = new Consumer();
            consumer.setCode(txtCode.getText());
            consumer.setName(txtName.getText());
            consumer.setConsumerCategory(consumerCategory);
            consumer.setContact(txtContact.getText());
            consumer.setPhone(txtPhone.getText());
            consumer.setAddress(txtAddress.getText());
            consumer.setEmail(txtEmail.getText());
            consumer.setLicensePlate(txtLicensePlate.getText());
            consumer.setAmountOwing(NumberValidationUtils.isRealNumber(txtAmountOwning.getText())? new BigDecimal(txtAmountOwning.getText()).setScale(2, RoundingMode.HALF_UP):BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
            consumer.setLineOfCredit(NumberValidationUtils.isRealNumber(txtLineOfCredit.getText())? new BigDecimal(txtLineOfCredit.getText()).setScale(2, RoundingMode.HALF_UP):BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
            consumer.setTotalAmountReceived(NumberValidationUtils.isRealNumber(txtTotalAmountReceived.getText())? new BigDecimal(txtTotalAmountReceived.getText()).setScale(2, RoundingMode.HALF_UP):BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
            consumer.setCompany(comboBoxCompany.getValue());
            consumer.setNotes(txtNotes.getText());
            try {
                String json = GoogleJson.GET().toJson(consumer);
                HttpClient.PUT("/consumers/"+this.consumer.getId(), json);
                callback.call(consumer);
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
        //客户分类如果不存在则新建
        if(comboBoxCategory.getValue() != null && !comboBoxCategory.getValue().trim().equals("")) {
            try {
                ConsumerCategory rootConsumerCategory = HttpClient.GET("/consumer/categories/root", ConsumerCategory.class);
                consumerCategory = new ConsumerCategory();
                consumerCategory.setName(comboBoxCategory.getValue());
                consumerCategory.setParent(false);
                consumerCategory.setParentId(rootConsumerCategory.getId());
                String json = GoogleJson.GET().toJson(consumerCategory);
                String idStr = HttpClient.POST("/consumer/categories", json);
                consumerCategory.setId(Long.valueOf(idStr));
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
            alert.setHeaderText("客户名称不能为空");
            alert.show();
            return false;
        }
        return true;
    }

}
