package com.shunyi.autoparts.ui.stock;

import com.shunyi.autoparts.ui.common.vo.Warehouse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

/** 编辑仓库Controller */
public class EditWarehouseController {
    @FXML
    Button btnCancel;
    @FXML
    Button btnOk;
    @FXML
    TextField txtCode;
    @FXML
    TextField txtName;

    private Stage subStage;
    private Warehouse updatedWarehouse;
    private Callback<Warehouse, Object> callback;

    @FXML
    private void ok(ActionEvent event) {
        if(txtCode.getText().equals("") || txtName.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("仓库编码或名称不能为空");
            alert.show();
            return;
        }
        Warehouse warehouse = new Warehouse();
        warehouse.setCode(txtCode.getText());
        warehouse.setName(txtName.getText());
        callback.call(warehouse);
        subStage.close();
    }
    @FXML
    private void cancel(ActionEvent event) {
        subStage.close();
    }

    /**
     * 准备方法
     *
     * @param subStage
     * @param updatedWarehouse
     * @param callback
     */
    public void prepare(Stage subStage, Warehouse updatedWarehouse, Callback<Warehouse, Object> callback) {
        this.subStage = subStage;
        this.updatedWarehouse = updatedWarehouse;
        this.callback = callback;
        btnOk.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
        if(updatedWarehouse != null) {
            txtCode.setText(updatedWarehouse.getCode());
            txtName.setText(updatedWarehouse.getName());
        }
    }
}
