package com.shunyi.autoparts.ui.products;

import com.shunyi.autoparts.ui.MainApp;
import com.shunyi.autoparts.ui.common.*;
import com.shunyi.autoparts.ui.common.vo.Picture;
import com.shunyi.autoparts.ui.common.vo.Product;
import com.shunyi.autoparts.ui.common.vo.SKU;
import com.shunyi.autoparts.ui.common.vo.SKUSlotMapping;
import javafx.beans.binding.Bindings;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
     * 构造器
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
        skuTable.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){

                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource(
                                "/fxml/products/AttributeCombiner.fxml"
                        )
                );
                BorderPane root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene scene = new Scene(root);
                Stage dialog = new Stage();
                AttributeCombinerController controller = loader.getController();
                controller.prepare(dialog, product);
                dialog.setTitle("属性组合");
                dialog.initOwner(stage);
                dialog.setResizable(true);
                dialog.initModality(Modality.WINDOW_MODAL);
                dialog.setScene(scene);
                // center stage on screen
                dialog.centerOnScreen();
                dialog.show();

            }
        });

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
        colName.setCellFactory(cellFactory);
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

        colUnit.setCellFactory(cellFactory);
        colUnit.setCellValueFactory(
                new PropertyValueFactory<SKU, String>("unit")
        );
        colUnit.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<SKU, String>>) t -> {
            ObservableList<SKU> data = t.getTableView().getItems();
            if(data != null) {
                if(t.getTablePosition().getRow() < data.size()) {
                    SKU selected = data.get( t.getTablePosition().getRow());
                    if(selected != null) {
                        selected.setUnit(t.getNewValue());
                    }
                }
            }
        });


        colQuantity.setCellFactory(cellFactory);
        colQuantity.setCellValueFactory(
                new PropertyValueFactory<SKU, Integer>("quantity")
        );
        colQuantity.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<SKU, String>>) t -> {
            ObservableList<SKU> data = t.getTableView().getItems();
            if(data != null) {
                if(t.getTablePosition().getRow() < data.size()) {
                    SKU selected = data.get( t.getTablePosition().getRow());
                    if(selected != null) {
                        if(NumberValidationUtils.isWholeNumber(t.getNewValue())) {
                            selected.setQuantity(Integer.parseInt(t.getNewValue()));
                        }
                        data.set(t.getTablePosition().getRow(), selected);
                    }
                }
            }
        });

        colPrice.setCellFactory(cellFactory);
        colPrice.setCellValueFactory(
                new PropertyValueFactory<SKU, BigDecimal>("price")
        );
        colPrice.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<SKU, String>>) t -> {
            ObservableList<SKU> data = t.getTableView().getItems();
            if(data != null) {
                if(t.getTablePosition().getRow() < data.size()) {
                    SKU selected = data.get( t.getTablePosition().getRow());
                    if(selected != null) {
                        if(NumberValidationUtils.isRealNumber(t.getNewValue())) {
                            selected.setPrice(new BigDecimal(t.getNewValue()).setScale(2, RoundingMode.HALF_UP));
                        }
                        data.set(t.getTablePosition().getRow(), selected);
                    }
                }
            }
        });


        colDiscountedPrice.setCellFactory(cellFactory);
        colDiscountedPrice.setCellValueFactory(
                new PropertyValueFactory<SKU, String>("discountedPrice")
        );
        colDiscountedPrice.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<SKU, String>>) t -> {
            ObservableList<SKU> data = t.getTableView().getItems();
            if(data != null) {
                if(t.getTablePosition().getRow() < data.size()) {
                    SKU selected = data.get( t.getTablePosition().getRow());
                    if(selected != null) {
                        if(NumberValidationUtils.isRealNumber(t.getNewValue())) {
                            selected.setDiscountedPrice(new BigDecimal(t.getNewValue()).setScale(2, RoundingMode.HALF_UP));
                        }
                        data.set(t.getTablePosition().getRow(), selected);
                    }
                }
            }
        });


        ObservableList status = FXCollections.observableArrayList(Status.AVAILABLE.getText(), Status.DISABLED.getText());
        colStatus.setCellValueFactory(
                new PropertyValueFactory<String, String>("status")
        );
        colStatus.setCellFactory(ComboBoxTableCell.forTableColumn(status));
        colStatus.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<SKU, String>>) t -> {
            ObservableList<SKU> data = t.getTableView().getItems();
            if(data != null) {
                if(t.getTablePosition().getRow() < data.size()) {
                    SKU selected = data.get( t.getTablePosition().getRow());
                    if(selected != null) {
                        selected.setStatus(t.getNewValue());
                        data.set(t.getTablePosition().getRow(), selected);
                    }
                }
            }
        });


        colProperties.setCellValueFactory(
                new PropertyValueFactory<SKU, String>("properties")
        );

        colBarCode.setCellFactory(cellFactory);
        colBarCode.setCellValueFactory(
                new PropertyValueFactory<SKU, String>("barCode")
        );
        colBarCode.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<SKU, String>>) t -> {
            ObservableList<SKU> data = t.getTableView().getItems();
            if(data != null) {
                if(t.getTablePosition().getRow() < data.size()) {
                    SKU selected = data.get( t.getTablePosition().getRow());
                    if(selected != null) {
                        selected.setBarCode(t.getNewValue());
                    }
                }
            }
        });

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
        SKU sku = new SKU(Constants.ID, product, "", "" ,"", product.getUnit(), Constants.QUANTITY, BigDecimal.ZERO.setScale(2, RoundingMode.UP), BigDecimal.ZERO.setScale(2, RoundingMode.UP), Status.AVAILABLE.getText(), "", "", new HashSet<SKUSlotMapping>(), new HashSet<Picture>(), null, Env.getInstance().currentUser(), null, null, null, null, Constants.DELETE_FLAG_FALSE, null);
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
