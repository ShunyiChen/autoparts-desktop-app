package com.shunyi.autoparts.ui.products;

import com.shunyi.autoparts.ui.model.BrandSeries;
import com.shunyi.autoparts.ui.model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.w3c.dom.Text;

/** 产品编辑器Controller */
public class ProductEditorController {

    Stage dialog;
    Product updatedProduct;
    Callback<Product, Object> callback;
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

    }

    @FXML
    void cancel(ActionEvent actionEvent) {

    }

    @FXML
    void saveAndClose(ActionEvent actionEvent) {

    }

    @FXML
    void continueAdd(ActionEvent actionEvent) {

    }

    public void prepare(Stage dialog, Product updatedProduct, Callback<Product, Object> callback) {
        this.dialog = dialog;
        this.updatedProduct = updatedProduct;
        this.callback = callback;
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
    }

}
