package com.shunyi.autoparts.ui.products;

import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.http.HttpClient;
import com.shunyi.autoparts.ui.model.AttributeName;
import com.shunyi.autoparts.ui.model.Product;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class SKUGeneratorController {
    private Stage subStage;
    private Product selectedProduct;
    private LinkedHashMap<Long, List<AttributeValueCheckBox>> groupMap;

    @FXML
    ScrollPane scrollPane;
    @FXML
    VBox pnlRows;
    @FXML
    TableView<ObservableList<String>> tableView;

    @FXML
    void saveOrUpdate() {
        subStage.close();
    }

    public void prepare(Stage subStage, Product selectedProduct) {
        this.subStage = subStage;
        this.selectedProduct = selectedProduct;
        this.groupMap = new LinkedHashMap();
        pnlRows.prefWidthProperty().bind(subStage.widthProperty().subtract(20));

        initRows();
    }

    private void initRows() {
        String json = "";
        try {
            json = HttpClient.GET("/attributes/name/category/" + selectedProduct.getBrandSeries().getCategory().getId());
        } catch (IOException e) {
            e.printStackTrace();
        }


        AttributeName[] attributeNames = GoogleJson.GET().fromJson(json, AttributeName[].class);
        List<AttributeName> list = Arrays.asList(attributeNames);
        //销售属性
        List<AttributeName> saleAttributes = list.stream().filter(e -> e.isSaleProperty()).collect(Collectors.toList());
        //输入属性
        List<AttributeName> inputAttributes = list.stream().filter(e -> e.isInputProperty()).collect(Collectors.toList());

        int i = 0;
        for (AttributeName attributeName : saleAttributes) {
            final int finalIdx = i;
            TableColumn<ObservableList<String>, String> tableColumn = new TableColumn<>(
                    attributeName.getName()
            );
            tableColumn.setPrefWidth(120);
            tableColumn.setCellValueFactory(param ->
                    new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx))
            );
            tableColumn.setVisible(false);
            tableView.getColumns().add(tableColumn);

            FlowPane row = createRow(attributeName, tableColumn, tableView);
            row.prefWidthProperty().bind(pnlRows.widthProperty());
            pnlRows.getChildren().add(row);
            i++;
        }

        for (AttributeName attributeName : inputAttributes) {
            final int finalIdx = i;
            TableColumn<ObservableList<String>, String> tableColumn = new TableColumn<>(
                    attributeName.getName()
            );
            tableColumn.setPrefWidth(120);
            tableColumn.setCellValueFactory(param ->
                    new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx))
            );
            tableColumn.setVisible(true);
            tableView.getColumns().add(tableColumn);
            i++;
        }
    }

    private FlowPane createRow(AttributeName attributeName, TableColumn<ObservableList<String>, String> tableColumn, TableView tableView) {
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
        controller.prepare(selectedProduct, attributeName, groupMap, tableColumn, tableView);
        return root;
    }
}
