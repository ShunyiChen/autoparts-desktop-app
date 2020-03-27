package com.shunyi.autoparts.ui.products;

import com.shunyi.autoparts.ui.common.Constants;
import com.shunyi.autoparts.ui.common.EditingCell;
import com.shunyi.autoparts.ui.common.Env;
import com.shunyi.autoparts.ui.common.Status;
import com.shunyi.autoparts.ui.common.vo.Product;
import com.shunyi.autoparts.ui.common.vo.SKU;
import com.shunyi.autoparts.ui.common.vo.SKUSlotMapping;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.math.BigDecimal;
import java.util.HashSet;

/**
 * 产品SKU
 * @author Shunyi
 */
public class ProductSKUController {
    private Stage stage;
    private Product product;
    private Callback<TableColumn, TableCell> cellFactory;

    @FXML
    private TableView<SKU> skuTable;
    @FXML
    private TableColumn colCode;
    @FXML
    private TableColumn colName;
    @FXML
    private TableColumn colSpecification;
    @FXML
    private TableColumn colUnit;
    @FXML
    private TableColumn colQuantity;
    @FXML
    private TableColumn colPrice;
    @FXML
    private TableColumn colDiscountedPrice;
    @FXML
    private TableColumn colStatus;
    @FXML
    private TableColumn colProperties;
    @FXML
    private TableColumn colBarCode;
    @FXML
    private TableColumn colSkuSlot;
    @FXML
    private TableColumn colSateCreated;
    @FXML
    private TableColumn colCreator;
    @FXML
    private TableColumn colSateUpdated;
    @FXML
    private TableColumn colUpdater;

    /**
     *
     * @param stage
     * @param product
     */
    public void prepare(Stage stage, Product product) {
        this.stage = stage;
        this.product = product;

        initCellFactory();

        initSKUTable();
    }

    private void initCellFactory() {
        cellFactory = p -> new EditingCell();
    }

    private void initSKUTable() {
        String css = getClass().getResource("/css/styles.css").toExternalForm();
        skuTable.getStylesheets().add(css);
        skuTable.setId("my-table");
        skuTable.setEditable(true);

        colCode.setCellValueFactory(
            new PropertyValueFactory<SKU, String>("skuCode")
        );
        colCode.setCellFactory(cellFactory);
//        colCode.setCellFactory(TextFieldTableCell.forTableColumn());
        colCode.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<SKU, String>>) t -> {
            ObservableList<SKU> data = t.getTableView().getItems();
            if(data != null) {
                if(t.getTablePosition().getRow() < data.size()) {
                    SKU selected = data.get( t.getTablePosition().getRow());
                    if(selected != null) {
                        selected.setSkuCode(t.getNewValue());
                    }
                }
            }
        });


        colName.setCellValueFactory(
                new PropertyValueFactory<SKU, String>("skuName")
        );
        colName.setCellFactory(TextFieldTableCell.forTableColumn());
        colName.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<SKU, String>>) t -> {
            ObservableList<SKU> data = t.getTableView().getItems();
            if(data != null) {
                if(t.getTablePosition().getRow() < data.size()) {
                    SKU selected = data.get( t.getTablePosition().getRow());
                    if(selected != null) {
                        selected.setSkuName(t.getNewValue());
                    }
                }
            }
        });


        colSpecification.setCellValueFactory(
                new PropertyValueFactory<SKU, String>("specification")
        );
        colUnit.setCellValueFactory(
                new PropertyValueFactory<SKU, String>("unit")
        );
        colQuantity.setCellValueFactory(
                new PropertyValueFactory<SKU, String>("quantity")
        );
        colPrice.setCellValueFactory(
                new PropertyValueFactory<SKU, String>("price")
        );
        colDiscountedPrice.setCellValueFactory(
                new PropertyValueFactory<SKU, String>("discountedPrice")
        );
        colStatus.setCellValueFactory(
                new PropertyValueFactory<SKU, String>("status")
        );
        colProperties.setCellValueFactory(
                new PropertyValueFactory<SKU, String>("properties")
        );
        colBarCode.setCellValueFactory(
                new PropertyValueFactory<SKU, String>("barCode")
        );
        colSkuSlot.setCellValueFactory(
                new PropertyValueFactory<SKU, String>("skuSlotMappings")
        );
        colSateCreated.setCellValueFactory(
                new PropertyValueFactory<SKU, String>("dateCreated")
        );
        colCreator.setCellValueFactory(
                new PropertyValueFactory<SKU, String>("creator")
        );
        colSateUpdated.setCellValueFactory(
                new PropertyValueFactory<SKU, String>("dateUpdated")
        );
        colUpdater.setCellValueFactory(
                new PropertyValueFactory<SKU, String>("updater")
        );
    }

    @FXML
    private void createNewSKU() {
        SKU sku = new SKU(Constants.ID, product, "", "" ,"", "", Constants.QUANTITY, BigDecimal.ZERO, BigDecimal.ZERO, Status.AVAILABLE.getText(), "", "", new HashSet<SKUSlotMapping>(), null, Env.getInstance().currentUser(), null, null, null, null, Constants.DELETE_FLAG_FALSE, null);
        skuTable.getItems().add(sku);
        int index = skuTable.getItems().size() - 1;
        // Start editing
        skuTable.edit(index, colCode);
        // Select current row
        TableView.TableViewSelectionModel selectionModel = skuTable.getSelectionModel();
        selectionModel.clearSelection();
        selectionModel.select(index);
    }

    @FXML
    private void save() {

    }

    @FXML
    private void delete() {
        skuTable.getItems().removeAll(skuTable.getSelectionModel().getSelectedItems());
        skuTable.getSelectionModel().clearSelection();
    }
}
