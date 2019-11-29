package com.shunyi.autoparts.ui.products;

import com.shunyi.autoparts.ui.common.EditingCell;
import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.http.HttpClient;
import com.shunyi.autoparts.ui.model.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/** SKU生成Controller */
public class SKUGeneratorController {
    private Stage subStage;
    private Product selectedProduct;
    private LinkedHashMap<Long, List<AttributeValueCheckBox>> checkboxGroup;
    private TableColumn<ObservableList<TableCellMetadata>, String> colUnit = new TableColumn<>("*单位");
    private TableColumn<ObservableList<TableCellMetadata>, String> colNum = new TableColumn<>("*数量");
    private TableColumn<ObservableList<TableCellMetadata>, String> colPrice = new TableColumn<>("*价格");
    private TableColumn<ObservableList<TableCellMetadata>, String> colStatus = new TableColumn<>("*状态");
    private TableColumn<ObservableList<TableCellMetadata>, String> colSKUName = new TableColumn<>("*SKU名称");
    private TableColumn<ObservableList<TableCellMetadata>, String> colBarCode = new TableColumn<>("*条形码");
    private TableColumn<ObservableList<TableCellMetadata>, String> colProductCode = new TableColumn<>("产品编码");
    private List<TableColumn<ObservableList<TableCellMetadata>, String>> otherColumns = new ArrayList<>();
    private Attribute[] attributes;

    @FXML
    ScrollPane scrollPane;
    @FXML
    VBox pnlRows;
    @FXML
    TableView<ObservableList<TableCellMetadata>> tableView;

    @FXML
    void closeWithoutSave() {
        subStage.close();
    }

    @FXML
    void saveOrUpdate() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"", ButtonType.YES, ButtonType.NO);
        alert.setTitle("请确认是否保存SKU");
        alert.setHeaderText("当保存新生成的SKU时，原有SKU将被删除，请确认是否要继续保存？");
        alert.showAndWait();
        if (alert.getResult() != ButtonType.YES) {
            return;
        }
        //保存所有选中的复选框到Attribute表
        pnlRows.getChildren().forEach(e -> {
            List<AttributeValueCheckBox> buttonGroup = (List<AttributeValueCheckBox>) e.getUserData();
            buttonGroup.forEach(c -> {
                if(c.isSelected()) {
                    Attribute attribute = new Attribute();
                    attribute.setAttributeNameId(c.getAttributeValue().getAttributeName().getId());
                    attribute.setAttributeValueId(c.getAttributeValue().getId());
                    attribute.setProduct(selectedProduct);
                    attribute.setSku(true);
                    attribute.setSkuId(0L);
                    String json = GoogleJson.GET().toJson(attribute);
                    try {
                        String idStr = HttpClient.POST("/attributes", json);
                        attribute.setId(Long.valueOf(idStr));
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                } else {
                    try {
                        HttpClient.DELETE("/attributes/"+selectedProduct.getId()+"/"+c.getAttributeValue().getId());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
        });

        //删除旧的SKU
        try {
            String allData = HttpClient.GET("/sku/products/"+selectedProduct.getId());
            SKU[] skuArray = GoogleJson.GET().fromJson(allData, SKU[].class);
            for(SKU sku : skuArray) {
                HttpClient.DELETE("/sku/"+sku.getId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //保存新的SKU
        List<ObservableList<TableCellMetadata>> data = tableView.getItems();
        if(data.size() > 0) {
            List<SKU> list = new ArrayList<>();
            int columnSize = tableView.getColumns().size();
            for (ObservableList<TableCellMetadata> row : data) {
                SKU sku = new SKU();
                sku.setProduct(selectedProduct);
                StringBuilder properties = new StringBuilder();
                for(int i = 0; i < columnSize; i++) {
                    TableColumn<ObservableList<TableCellMetadata>, String> column = (TableColumn<ObservableList<TableCellMetadata>, String>) tableView.getColumns().get(i);
                    if(column == colUnit) {
                        sku.setUnit(row.get(i).getText());
                    } else if(column == colNum) {
                        sku.setQuantity(Integer.valueOf(row.get(i).getText()));
                    } else if(column == colPrice) {
                        sku.setPrice(new BigDecimal(row.get(i).getText()));
                    } else if(column == colStatus) {
                        sku.setStatus(row.get(i).getText());
                    } else if(column == colSKUName) {
                        sku.setSkuName(row.get(i).getText());
                    } else if(column == colBarCode) {
                        sku.setBarCode(row.get(i).getText());
                    } else if(column == colProductCode) {
                        //none 因为已经setProduct了
                    } else {
                        TableColumnMetadata metadata = (TableColumnMetadata) column.getUserData();
                        properties.append(metadata.getAttributeNameId()).append(":").append(row.get(i).getAttributeValueId()).append(";");
                    }
                }
                sku.setProperties(properties.toString());
                list.add(sku);
            }
            list.forEach(e -> {
                try {
                    String json = GoogleJson.GET().toJson(e);
                    HttpClient.POST("/sku", json);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }
        closeWithoutSave();
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
        initTableData();
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
        List<AttributeName> saleAttributes = list.stream().filter(AttributeName::isSaleProperty).collect(Collectors.toList());
        int columnCount = 0;
        //销售属性列
        for (AttributeName attributeName : saleAttributes) {
            final int finalIdx = columnCount;
            TableColumn<ObservableList<TableCellMetadata>, String> saleColumn = new TableColumn<>(
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

        //初始化销售属性列
        pnlRows.getChildren().forEach(e -> {
            List<AttributeValueCheckBox> buttonGroup = (List<AttributeValueCheckBox>) e.getUserData();
            buttonGroup.forEach(c -> {
                if(c.isSelected()) {
                    if (!tableView.getColumns().contains(c.getTableColumn())) {
                        tableView.getColumns().add(c.getTableColumn());
                    }
                }
            });
        });
        otherColumns.add(colUnit);
        otherColumns.add(colNum);
        otherColumns.add(colPrice);
        otherColumns.add(colStatus);
        otherColumns.add(colSKUName);
        otherColumns.add(colBarCode);
        otherColumns.add(colProductCode);
        initColumnWidth();
        //初始化其它可输入列
        for(TableColumn<ObservableList<TableCellMetadata>, String> column : otherColumns) {
            final int finalIdx = columnCount;
            TableColumnMetadata meteData = new TableColumnMetadata(finalIdx, 0L);
            column.setUserData(meteData);
            if(column != colProductCode) {
                Callback<TableColumn<ObservableList<TableCellMetadata>, String>, TableCell<ObservableList<TableCellMetadata>, String>> cellFactory =
                        param -> new EditingCell();
                column.setCellFactory(cellFactory);
                column.setOnEditCommit(t -> {
                    for(int i = 0; i < tableView.getColumns().size(); i++) {
                        if(tableView.getColumns().get(i) == column) {
                            t.getRowValue().remove(i);
                            t.getRowValue().add(i, new TableCellMetadata(t.getNewValue()));
                            break;
                        }
                    }
                });
            }
            columnCount++;
            tableView.getColumns().add(column);
        }
    }

    /** 初始化表格数据 */
    private void initTableData() {
        for (int i = 0; i < tableView.getColumns().size(); i++) {
            final int index = i;
            TableColumn<ObservableList<TableCellMetadata>, String> column = (TableColumn<ObservableList<TableCellMetadata>, String>) tableView.getColumns().get(i);
            column.setCellValueFactory(param ->{
//                        if(index < param.getValue().size() ) {
//                            return new SimpleObjectProperty<>(param.getValue().get(index).getText());
//                        }
//                        return  new SimpleObjectProperty<>("呜呜呜");
                  return new SimpleObjectProperty<>(param.getValue().get(index).getText());
               }
            );
        }
        try {
            String json = HttpClient.GET("/sku/products/"+selectedProduct.getId());
            SKU[] skuArray = GoogleJson.GET().fromJson(json, SKU[].class);
            for(SKU sku : skuArray) {
                ObservableList<TableCellMetadata> rowData = FXCollections.observableArrayList();
                String properties = sku.getProperties();
                String[] p = properties.split(";");
                for(String kv : p) {
                    String attributeValueId = kv.substring(kv.indexOf(":")+1);
                    String data = HttpClient.GET("/attributes/value/"+attributeValueId);
                    AttributeValue attributeValue = GoogleJson.GET().fromJson(data, AttributeValue.class);
                    TableCellMetadata metadata = new TableCellMetadata(attributeValue.getId(), attributeValue.getName());
                    rowData.add(metadata);
                }
                //单位
                rowData.add(new TableCellMetadata(sku.getUnit()));
                //数量
                rowData.add(new TableCellMetadata(sku.getQuantity()+""));
                //单价
                rowData.add(new TableCellMetadata(sku.getPrice().toString()));
                //状态
                rowData.add(new TableCellMetadata(sku.getStatus()));
                //SKU名称
                rowData.add(new TableCellMetadata(sku.getSkuName()));
                //SKU二维码
                rowData.add(new TableCellMetadata(sku.getBarCode()));
                //产品编码
                rowData.add(new TableCellMetadata(sku.getProduct().getCode()));
                tableView.getItems().add(rowData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initColumnWidth() {
        colUnit.setPrefWidth(120);
        colNum.setPrefWidth(120);
        colPrice.setPrefWidth(120);
        colStatus.setPrefWidth(120);
        colSKUName.setPrefWidth(220);
        colBarCode.setPrefWidth(120);
        colProductCode.setPrefWidth(120);
    }

    /**
     * 初始化表格行数据
     *
     * @param attributeName 属性名
     * @param tableColumn  表格列
     * @param tableView 表格
     * @return 返回复选框行面板
     */
    private FlowPane createRow(AttributeName attributeName, TableColumn<ObservableList<TableCellMetadata>, String> tableColumn, TableView tableView) {
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
