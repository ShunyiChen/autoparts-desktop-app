package com.shunyi.autoparts.ui.system;

import com.shunyi.autoparts.ui.common.AutoCompleteBox;
import com.shunyi.autoparts.ui.common.HttpClient;
import com.shunyi.autoparts.ui.common.vo.Store;
import com.shunyi.autoparts.ui.common.vo.Warehouse;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Arrays;

/** 编辑店铺Controller */
public class EditStoreController {
    private Stage dialog;
    private Callback<Store, String> callback;
    private Store selectedStore;
    private Warehouse warehouse;

    @FXML
    private TextField txtCode;
    @FXML
    private TextField txtName;
    @FXML
    private ComboBox<Warehouse> comboboxWarehouse;
    @FXML
    private Button btnSettings;
    @FXML
    private Button btnOk;
    @FXML
    private void cancel() {
        dialog.close();
    }

    @FXML
    private void ok() {
        if(validate()) {
            Store store = new Store();
            store.setCode(txtCode.getText());
            store.setName(txtName.getText());
            store.setWarehouse(warehouse);
            callback.call(store);
            dialog.close();
        }
    }

    private boolean validate() {
        if(txtName.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("仓库名称不能为空");
            alert.show();
            return false;
        } else if(warehouse == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("请选择一个可用的仓库");
            alert.show();
            return false;
        }
        return true;
    }

    public void prepare(Stage dialog, Callback<Store, String> callback, Store selectedStore) {
        this.dialog = dialog;
        this.callback = callback;
        this.selectedStore = selectedStore;

        initButtons();

        initWarehouse();

        if(selectedStore != null) {
            txtCode.setText(selectedStore.getCode());
            txtName.setText(selectedStore.getName());
            comboboxWarehouse.setValue(selectedStore.getWarehouse());

            System.out.println("========="+selectedStore.getWarehouse());

        }
    }

    private void initButtons() {
        btnOk.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
        Image imgGear = new Image(getClass().getResourceAsStream("/img/login/Gear16.png"));
        btnSettings.setGraphic(new ImageView(imgGear));
        btnSettings.setOnAction(e -> {

            Callback<Warehouse, String> callback = new Callback<Warehouse, String>() {
                @Override
                public String call(Warehouse selected) {
                    if(selected != null) {
                        comboboxWarehouse.getItems().clear();
                        try {
                            Warehouse[] warehouses = HttpClient.GET("/warehouses", Warehouse[].class);
                            comboboxWarehouse.getItems().addAll(warehouses);
                            comboboxWarehouse.getSelectionModel().select(Arrays.asList(warehouses).stream().filter(e -> e.getId().equals(selected.getId())).findFirst().get());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    return null;
                }
            };

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/fxml/system/WarehouseChooser.fxml"
                    )
            );
            BorderPane root = null;
            try {
                root = loader.load();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            Scene scene = new Scene(root);
            Stage dialog = new Stage();
            WarehouseChooserController controller = loader.getController();
            controller.prepare(dialog, callback, selectedStore);
            dialog.setTitle("仓库管理");
            dialog.setResizable(false);
            dialog.initOwner(this.dialog);
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setScene(scene);
            // center stage on screen
            dialog.centerOnScreen();
            dialog.show();
        });
    }

    private void initWarehouse() {
        try {
            Warehouse[] warehouses = HttpClient.GET("/warehouses", Warehouse[].class);
            comboboxWarehouse.getItems().addAll(warehouses);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new AutoCompleteBox(comboboxWarehouse);

        comboboxWarehouse.setOnAction(e -> {
            try {
                 warehouse = HttpClient.GET("/warehouses/name/"+comboboxWarehouse.getValue(), Warehouse.class);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

//        comboboxWarehouse.getSelectionModel().selectedItemProperty().addListener(
//                (ChangeListener) (observable, oldValue, newValue) -> {
//                    System.out.println("newvalue="+newValue);
//
//                });
    }
}
