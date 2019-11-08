package com.shunyi.autoparts.ui.products;

import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.http.HttpClient;
import com.shunyi.autoparts.ui.model.AttributeName;
import com.shunyi.autoparts.ui.model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

public class SKUGeneratorController {

    Stage subStage;
    Product selectedProduct;

    @FXML
    ScrollPane scrollPane;
    @FXML
    VBox pnlRows;
    @FXML
    TreeTableView treeTableView;

    @FXML
    void saveOrUpdate(ActionEvent actionEvent) {
        subStage.close();
    }

    public void prepare(Stage subStage, Product selectedProduct) {
        this.subStage = subStage;
        this.selectedProduct = selectedProduct;

        pnlRows.prefWidthProperty().bind(subStage.widthProperty().subtract(20));

        initRows();
    }

    void initRows() {
        String json = "";
        try {
            json = HttpClient.GET("/attributes/name/category/"+selectedProduct.getBrandSeries().getCategory().getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
        AttributeName[] attributeNames = GoogleJson.GET().fromJson(json, AttributeName[].class);
        for(AttributeName attributeName : attributeNames) {
            if(attributeName.isSaleProperty()) {
                FlowPane row = createRow(attributeName);
                row.prefWidthProperty().bind(pnlRows.widthProperty());
                pnlRows.getChildren().add(row);
            }
        }
    }

    FlowPane createRow(AttributeName attributeName) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/products/sku_generator_row.fxml"
                )
        );
        FlowPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SKUGeneratorRowController controller = loader.getController();
        controller.prepare(selectedProduct, attributeName, refreshTreeTable);
        return root;
    }

    Callback refreshTreeTable = new Callback() {
        @Override
        public Object call(Object param) {


//            pnlRows.getChildren().stream().forEach(e -> {
//
//                FlowPane rootPanel = (FlowPane) e;
//                rootPanel.getChildren().stream().forEach(e2 -> {
//                    if(e2 instanceof AttributeValueCheckBox) {
//                        AttributeValueCheckBox checkbox = (AttributeValueCheckBox) e2;
//                        if(checkbox.isSelected()) {
//
//                        }
//                    }
//                    else if(e2 instanceof Label) {
//                        Label title = (Label) e2;
//                        TreeTableColumn<String, String> column = new TreeTableColumn<>(title.getText());
//                        column.setPrefWidth(150);
//                        treeTableView.getColumns().add(column);
//                    }
//                });
//            });
//            TreeTableColumn<String, String> columnPrice = new TreeTableColumn<>("价格");
//            treeTableView.getColumns().add(columnPrice);
//            TreeTableColumn<String, String> columnQuantity = new TreeTableColumn<>("数量");
//            treeTableView.getColumns().add(columnQuantity);


            return null;
        }
    };
}
