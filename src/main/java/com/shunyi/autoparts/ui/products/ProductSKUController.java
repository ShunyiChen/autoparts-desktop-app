package com.shunyi.autoparts.ui.products;

import com.shunyi.autoparts.ui.common.vo.Product;
import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * 产品SKU
 * @author Shunyi
 */
public class ProductSKUController {
    private Stage stage;
    private Product selectedProduct;

    /**
     *
     * @param stage
     * @param selectedProduct
     */
    public void prepare(Stage stage, Product selectedProduct) {
        this.stage = stage;
        this.selectedProduct = selectedProduct;
    }

    @FXML
    private void createNewSKU() {

    }

    @FXML
    private void updateSKU() {

    }

    @FXML
    private void deleteSKU() {

    }
}
