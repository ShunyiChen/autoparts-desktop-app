package com.shunyi.autoparts.ui.products;

import com.shunyi.autoparts.ui.common.*;
import com.shunyi.autoparts.ui.common.vo.*;
import com.shunyi.autoparts.ui.supplier.SupplierChooserController;
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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

/** 产品编辑器Controller */
public class ProductEditorController {
    private Stage dialog;
    private Product updatedProduct;
    private Callback<Product, Object> callback;
    private Car selectedCar;
    private Supplier selectedSupplier;
    private Category selectedCategory;
//    private BrandSeries selectedBrand;

    @FXML
    Button btnSaveAndClose;
    @FXML
    Button btnContinueAdd;
    @FXML
    TextField txtCode;
    @FXML
    TextField txtName;
//    @FXML
//    ComboBox<BrandSeries> boxBrand;
    @FXML
    TextField txtPrice;
    @FXML
    TextField txtUnit;
    @FXML
    ComboBox<String> boxImported;
    @FXML
    TextField txtCar;
    @FXML
    TextField txtOrigin;
    @FXML
    TextField txtSupplier;
    @FXML
    TextField txtBarCode;
    @FXML
    TextArea txtNotes;

    @FXML
    private void supplierChooser() {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/supplier/SupplierChooser.fxml"
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
        SupplierChooserController controller = loader.getController();
        Callback<Supplier, String> callback = supplier -> {
            selectedSupplier = supplier;
            txtSupplier.setText(selectedSupplier.getName());
            return null;
        };
        controller.prepare(subStage, selectedSupplier, callback);
        subStage.setTitle("选择供应商");
        subStage.initOwner(dialog);
        subStage.setResizable(false);
        subStage.initModality(Modality.APPLICATION_MODAL);
        subStage.setScene(scene);
        // center stage on screen
        subStage.centerOnScreen();
        subStage.show();
    }

    @FXML
    private void carChooser() {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/products/car_chooser.fxml"
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
        CarChooserController controller = loader.getController();
        Callback<Car, Void> cb = e -> {
            selectedCar = e;
//            txtCar.setText(selectedCar.getModel());
            return null;
        };
        controller.prepare(subStage, selectedCar, cb);
        subStage.setTitle("选择车型");
        subStage.initOwner(dialog);
        subStage.setResizable(false);
        subStage.initModality(Modality.APPLICATION_MODAL);
        subStage.setScene(scene);
        // center stage on screen
        subStage.centerOnScreen();
        subStage.show();
    }

    @FXML
    private void cancel() {
        dialog.close();
    }

    @FXML
    private void saveAndClose() {
        if(validate()) {
//            dialog.close();
//            BigDecimal listPrice;
//            listPrice = new BigDecimal(Double.valueOf(txtPrice.getText())).setScale(2, RoundingMode.HALF_UP);
//            Product newProduct = new Product(0L, txtCode.getText(), txtBarCode.getText(), txtName.getText(), boxBrand.getValue(), selectedCar, selectedSupplier, txtUnit.getText(), listPrice, boxImported.getValue(), txtOrigin.getText(), txtNotes.getText(), null, Env.getInstance().getStringValue(Env.CURRENT_USER),null,null,null,null, Constants.DELETE_FLAG_FALSE,null);
//            callback.call(newProduct);
        }
    }

    @FXML
    private void continueAdd() {
        if(validate()) {
//            BigDecimal listPrice;
//            listPrice = new BigDecimal(Double.valueOf(txtPrice.getText())).setScale(2, RoundingMode.HALF_UP);
//            Product newProduct = new Product(0L, txtCode.getText(), txtBarCode.getText(), txtName.getText(), boxBrand.getValue(), selectedCar, selectedSupplier, txtUnit.getText(), listPrice, boxImported.getValue(), txtOrigin.getText(), txtNotes.getText(), null, Env.getInstance().getStringValue(Env.CURRENT_USER),null,null,null,null, Constants.DELETE_FLAG_FALSE,null);
//            callback.call(newProduct);
        }
    }

    private boolean validate() {
        if(txtCode.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("编码不能为空");
            alert.show();
            return false;
        } else if(txtName.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("名称不能为空");
            alert.show();
            return false;
        }
//        else if(boxBrand.getValue() == null) {
//            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
//            alert.setHeaderText("品牌不能为空");
//            alert.show();
//            return false;
//        }
        else if(selectedCar== null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("车型不能为空");
            alert.show();
            return false;
        } else if(txtUnit.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("单位不能为空");
            alert.show();
            return false;
        } else if(txtPrice.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("列表价不能为空");
            alert.show();
            return false;
        } else if(txtUnit.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("单位不能为空");
            alert.show();
            return false;
        } else if(!NumberValidationUtils.isPositiveDecimal(txtPrice.getText()) && !NumberValidationUtils.isPositiveInteger(txtPrice.getText())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("请输入数字价格");
            alert.show();
            return false;
        }
        return true;
    }

//    /**
//     * 准备
//     * @param dialog
//     * @param updatedProduct
//     * @param callback
//     * @param selectedCategory
//     * @param selectedBrand
//     */
//    public void prepare(Stage dialog, Product updatedProduct, Callback<Product, Object> callback, Category selectedCategory, BrandSeries selectedBrand) {
//        this.dialog = dialog;
//        this.updatedProduct = updatedProduct;
//        this.callback = callback;
//        this.selectedCategory = selectedCategory;
//        this.selectedBrand = selectedBrand;
//        initButton();
//        initComboBox();
//        if(updatedProduct != null) {
//            txtCode.setText(updatedProduct.getCode());
//            txtName.setText(updatedProduct.getName());
//            txtBarCode.setText(updatedProduct.getBarCode());
//            boxBrand.setValue(selectedBrand);
//            txtPrice.setText(updatedProduct.getListPrice().doubleValue()+"");
//            txtUnit.setText(updatedProduct.getUnit());
//            boxImported.setValue(updatedProduct.getImported());
//            txtCar.setText(updatedProduct.getCar().getModel());
//            txtSupplier.setText(updatedProduct.getSupplier().getName());
//            selectedCar = updatedProduct.getCar();
//            selectedSupplier = updatedProduct.getSupplier();
//            txtOrigin.setText(updatedProduct.getOrigin());
//            txtNotes.setText(updatedProduct.getNotes());
//            btnContinueAdd.setVisible(false);
//        }
//    }

    private void initButton() {
        btnSaveAndClose.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
    }

    private void initComboBox() {
//        boxImported.getItems().addAll(Constants.ORIGINAL, Constants.HOMEMADE);
//        boxImported.getSelectionModel().select(0);
//        boxImported.setStyle("-fx-font-size: 14px;");
//        boxBrand.setStyle("-fx-font-size: 14px;");
//        //通过类目ID获取品牌
//        String json = null;
//        try {
//            json = HttpClient.GET("/brandSeries/category/"+selectedCategory.getId());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        BrandSeries[] brands = GoogleJson.GET().fromJson(json, BrandSeries[].class);
//        boxBrand.getItems().addAll(brands);
//        //默认选中品牌
//        if(selectedBrand != null) {
//            Arrays.stream(brands).forEach(e -> {
//                if(e.getId().equals(selectedBrand.getId())) {
//                    boxBrand.getSelectionModel().select(e);
//                }
//            });
//        }
    }

}
