package com.shunyi.autoparts.ui.stock;

import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.common.HttpClient;
import com.shunyi.autoparts.ui.common.vo.Slot;
import com.shunyi.autoparts.ui.common.vo.Warehouse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

public class EditSlotController {

    @FXML
    Button btnSaveAndClose;
    @FXML
    Button btnContinueAdding;
    @FXML
    TextField txtWarehouse;
    @FXML
    TextField txtName;
    @FXML
    TextField txtLevel_1;
    @FXML
    TextField txtLevel_2;
    @FXML
    TextField txtLevel_3;
    @FXML
    TextField txtLevel_4;
    @FXML
    TextField txtLevel_5;
    @FXML
    TextField txtBarCode;

    Stage subStage;
    Slot updatedSlot;
    Callback<Slot, Object> callback;
    Warehouse selectedWarehouse;
    @FXML
    void choose(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/stock/warehouse_chooser.fxml"
                )
        );
        VBox root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage dialog = new Stage();
        WarehouseChooserController controller = loader.getController();
        Callback<Warehouse, Object> cb = new Callback<Warehouse, Object>() {
            @Override
            public Object call(Warehouse param) {
                selectedWarehouse = param;
                txtWarehouse.setText(selectedWarehouse.getName());
                return null;
            }
        };
        controller.prepare(dialog, selectedWarehouse, cb);
        dialog.setTitle("选择仓库");
        dialog.initOwner(subStage);
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    @FXML
    void cancel(ActionEvent event) {
        subStage.close();
    }

    @FXML
    void saveAndClose(ActionEvent event) {
        if(updatedSlot == null) {
            add(true);
        } else {
            update(true);
        }
    }

    @FXML
    void continueAdd(ActionEvent event) {
        add(false);
    }

    private boolean checkInputs() {
        if(selectedWarehouse == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("仓库不能为空");
            alert.show();
            return false;
        }
        if(txtName.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("货位名称不能为空");
            alert.show();
            return false;
        }
        if(txtBarCode.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("条形码不能为空");
            alert.show();
            return false;
        }
        return true;
    }

     void update(boolean closeStage) {
        if(checkInputs()) {
//            if(closeStage)
//                subStage.close();
//            CargoSpace cargoSpace = new CargoSpace(selectedWarehouse, txtName.getText(), txtBarCode.getText(), txtLevel_1.getText(), txtLevel_2.getText(),txtLevel_3.getText(),txtLevel_4.getText(),txtLevel_5.getText());
//            cargoSpace.setId(selectedWarehouse.getId());
//            callback.call(cargoSpace);
        }
    }

    void add(Boolean closeStage) {
        if(checkInputs()) {
//            if(closeStage)
//                subStage.close();
//            CargoSpace cargoSpace = new CargoSpace(selectedWarehouse, txtName.getText(), txtBarCode.getText(), txtLevel_1.getText(), txtLevel_2.getText(),txtLevel_3.getText(),txtLevel_4.getText(),txtLevel_5.getText());
//            callback.call(cargoSpace);
        }
    }

    /**
     * 准备
     *
     * @param subStage
     * @param updatedSlot
     * @param callback
     */
    public void prepare(Stage subStage, Slot updatedSlot, Callback<Slot, Object> callback) {
        this.subStage = subStage;
        this.updatedSlot = updatedSlot;
        this.callback = callback;
        btnSaveAndClose.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
        this.txtWarehouse.setDisable(true);

        if(updatedSlot != null) {
            String json = null;
            try {
                json = HttpClient.GET("/warehouses/"+updatedSlot.getWarehouse().getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
            selectedWarehouse = GoogleJson.GET().fromJson(json, Warehouse.class);
            String name = selectedWarehouse.getId() == 0 ? "全部仓库":selectedWarehouse.getName();
            txtWarehouse.setText(name);
            txtName.setText(updatedSlot.getName());
            txtLevel_1.setText(updatedSlot.getLevel_1());
            txtLevel_2.setText(updatedSlot.getLevel_2());
            txtLevel_3.setText(updatedSlot.getLevel_3());
            txtLevel_4.setText(updatedSlot.getLevel_4());
            txtLevel_5.setText(updatedSlot.getLevel_5());
            txtBarCode.setText(updatedSlot.getBarCode());

            btnContinueAdding.setVisible(false);
        }
    }
}
