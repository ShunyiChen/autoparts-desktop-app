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
    private LinkedHashMap<Long, List<AttributeValueCheckBox>> checkboxGroup;

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
        this.checkboxGroup = new LinkedHashMap();
        pnlRows.prefWidthProperty().bind(subStage.widthProperty().subtract(20));

        initTable();
        initTableColumns();
    }

    private void initTable() {
        final String css = getClass().getResource("/css/styles.css").toExternalForm();
        tableView.getStylesheets().add(css);

        tableView.setEditable(true);
    }

    private void initTableColumns() {
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

        int columnCount = 0;
        //销售属性列
        for (AttributeName attributeName : saleAttributes) {
            final int finalIdx = columnCount;
            TableColumn<ObservableList<String>, String> tableColumn = new TableColumn<>(
                    attributeName.getName()
            );
            tableColumn.setId(finalIdx+"");
            tableColumn.setUserData("");
            tableColumn.setPrefWidth(120);
            FlowPane row = createRow(attributeName, tableColumn, tableView, inputAttributes);
            row.prefWidthProperty().bind(pnlRows.widthProperty());
            pnlRows.getChildren().add(row);
            columnCount++;
        }
        //输入属性列
        for (AttributeName attributeName : inputAttributes) {
            final int finalIdx = columnCount;
            TableColumn<ObservableList<String>, String> tableColumn = new TableColumn<>(
                    attributeName.getName()
            );
            tableColumn.setId(finalIdx+"");
            tableColumn.setUserData("input");
            tableColumn.setPrefWidth(120);
            tableView.getColumns().add(tableColumn);
            columnCount++;
        }
    }

    /**
     *
     * @param attributeName 属性名
     * @param tableColumn  表格列
     * @param tableView 表格
     * @param inputAttributes 所有输入属性
     * @return
     */
    private FlowPane createRow(AttributeName attributeName, TableColumn<ObservableList<String>, String> tableColumn, TableView tableView, List<AttributeName> inputAttributes) {
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
        controller.prepare(selectedProduct, attributeName, checkboxGroup, tableColumn, tableView, inputAttributes);
        return root;
    }
}
