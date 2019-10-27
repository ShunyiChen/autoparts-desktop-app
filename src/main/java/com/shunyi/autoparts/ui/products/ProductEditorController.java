package com.shunyi.autoparts.ui.products;

import com.shunyi.autoparts.ui.common.GSON;
import com.shunyi.autoparts.ui.http.HttpClient;
import com.shunyi.autoparts.ui.model.*;
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

/** 产品编辑器Controller */
public class ProductEditorController {
    Stage dialog;
    Product updatedProduct;
    Callback<Product, Object> callback;
    Car selectedCar;
    Category selectedCategory;
    BrandSeries selectedBrand;

    @FXML
    Button btnSaveAndClose;
    @FXML
    Button btnContinueAdd;
    @FXML
    TextField txtCode;
    @FXML
    TextField txtName;
    @FXML
    ComboBox<BrandSeries> boxBrand;
    @FXML
    TextField txtPrice;
    @FXML
    TextField txtUnit;
    @FXML
    ComboBox<String> boxImported;
    @FXML
    TextField txtCar;
    @FXML
    TextField txtOther;

    @FXML
    void choose(ActionEvent actionEvent) {
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
            txtCar.setText(selectedCar.getModel());
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
    void cancel(ActionEvent actionEvent) {
        dialog.close();
    }

    @FXML
    void saveAndClose(ActionEvent actionEvent) {
        if(validate()) {
            dialog.close();
            Product newProduct = new Product();
            //TODO
        }
    }

    boolean validate() {

        return true;
    }

    @FXML
    void continueAdd(ActionEvent actionEvent) {

    }

    public void prepare(Stage dialog, Product updatedProduct, Callback<Product, Object> callback, Category selectedCategory, BrandSeries selectedBrand) {
        this.dialog = dialog;
        this.updatedProduct = updatedProduct;
        this.callback = callback;
        this.selectedCategory = selectedCategory;
        this.selectedBrand = selectedBrand;
        initButton();
        initComboBox();

        if(updatedProduct != null) {

        }
    }

    void initButton() {
        btnSaveAndClose.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
    }

    void initComboBox() {
        boxImported.getItems().addAll("进口","国产");
        boxImported.getSelectionModel().select(0);
        boxImported.setStyle("-fx-font-size: 14px;");
        boxBrand.setStyle("-fx-font-size: 14px;");
        //通过类目ID获取品牌
        String json = null;
        try {
            json = HttpClient.GET("/brandSeries/category/"+selectedCategory.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
        BrandSeries[] brands = GSON.getInstance().fromJson(json, BrandSeries[].class);
        boxBrand.getItems().addAll(brands);
        //默认选中品牌
        Arrays.stream(brands).forEach(e -> {
            if(e.getId() == selectedBrand.getId()) {
                boxBrand.getSelectionModel().select(e);
            }
        });
    }

}
