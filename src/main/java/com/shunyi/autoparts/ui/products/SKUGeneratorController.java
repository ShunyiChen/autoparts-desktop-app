package com.shunyi.autoparts.ui.products;

import com.shunyi.autoparts.ui.common.EditingCell;
import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.http.HttpClient;
import com.shunyi.autoparts.ui.model.Attribute;
import com.shunyi.autoparts.ui.model.AttributeName;
import com.shunyi.autoparts.ui.model.Product;
import com.shunyi.autoparts.ui.model.SKU;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class SKUGeneratorController {
    private Stage subStage;
    private Product selectedProduct;
    private LinkedHashMap<Long, List<AttributeValueCheckBox>> checkboxGroup;
    private TableColumn<ObservableList<String>, String> colUnit = new TableColumn<>("*单位");
    private TableColumn<ObservableList<String>, String> colNum = new TableColumn<>("*数量");
    private TableColumn<ObservableList<String>, String> colPrice = new TableColumn<>("*价格");
    private TableColumn<ObservableList<String>, String> colStatus = new TableColumn<>("状态");
    private TableColumn<ObservableList<String>, String> colSKUName = new TableColumn<>("SKU名称");
    private TableColumn<ObservableList<String>, String> colBarCode = new TableColumn<>("条形码");
    private TableColumn<ObservableList<String>, String> colProductCode = new TableColumn<>("产品编码");
    private List<TableColumn<ObservableList<String>, String>> otherColumns = new ArrayList<>();
    private Attribute[] attributes;

    @FXML
    ScrollPane scrollPane;
    @FXML
    VBox pnlRows;
    @FXML
    TableView<ObservableList<String>> tableView;


    @FXML
    void saveOrUpdate() {
        subStage.close();
        List<ObservableList<String>> data = tableView.getItems();
        if(data.size() > 0) {
            for (ObservableList<String> row : data) {
//                SKU sku = new SKU(selectedProduct, row);

            }
        }
    }

    public void prepare(Stage subStage, Product selectedProduct) {
        this.subStage = subStage;
        this.selectedProduct = selectedProduct;
        this.checkboxGroup = new LinkedHashMap();
        pnlRows.prefWidthProperty().bind(subStage.widthProperty().subtract(20));
        try {
            String json = HttpClient.GET("/attributes/products/"+selectedProduct.getId());
            attributes = GoogleJson.GET().fromJson(json, Attribute[].class);
            initTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initTable() {
        final String css = getClass().getResource("/css/styles.css").toExternalForm();
        tableView.getStylesheets().add(css);
        tableView.setEditable(true);

        initTableColumns();
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

        int columnCount = 0;
        //销售属性列
        for (AttributeName attributeName : saleAttributes) {
            final int finalIdx = columnCount;
            TableColumn<ObservableList<String>, String> saleColumn = new TableColumn<>(
                    attributeName.getName()
            );

            TableColumnMetadata meteData = new TableColumnMetadata(finalIdx, attributeName.getId());
            saleColumn.setUserData(meteData);
            saleColumn.setPrefWidth(120);
            FlowPane row = createRow(attributeName, saleColumn, tableView);
            row.prefWidthProperty().bind(pnlRows.widthProperty());
            pnlRows.getChildren().add(row);
            columnCount++;
        }

        otherColumns.add(colUnit);
        otherColumns.add(colNum);
        otherColumns.add(colPrice);
        otherColumns.add(colStatus);
        otherColumns.add(colSKUName);
        otherColumns.add(colBarCode);
        otherColumns.add(colProductCode);

        initColumnWidth();

        for(TableColumn<ObservableList<String>, String> column : otherColumns) {
            TableColumnMetadata meteData = new TableColumnMetadata(columnCount, 0L);
            column.setUserData(meteData);

            Callback<TableColumn<ObservableList<String>, String>, TableCell<ObservableList<String>, String>> cellFactory =
                    new Callback<>() {
                        @Override
                        public TableCell call(TableColumn<ObservableList<String>, String> param) {
                            return new EditingCell();
                        }
                    };
            column.setCellFactory(cellFactory);
            column.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ObservableList<String>, String>>() {
                   @Override
                   public void handle(TableColumn.CellEditEvent<ObservableList<String>, String> t) {
                   }
               }
            );
            columnCount++;
            tableView.getColumns().add(column);
        }
    }

    private void initColumnWidth() {
        colUnit.setPrefWidth(80);
        colNum.setPrefWidth(80);
        colPrice.setPrefWidth(80);
        colStatus.setPrefWidth(120);
        colSKUName.setPrefWidth(220);
        colBarCode.setPrefWidth(120);
        colProductCode.setPrefWidth(120);
    }

    /**
     *
     * @param attributeName 属性名
     * @param tableColumn  表格列
     * @param tableView 表格
     * @return
     */
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
        controller.prepare(selectedProduct, attributeName, checkboxGroup, tableColumn, tableView, attributes);
        return root;
    }
}
