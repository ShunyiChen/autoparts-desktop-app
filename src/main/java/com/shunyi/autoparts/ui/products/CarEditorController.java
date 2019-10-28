package com.shunyi.autoparts.ui.products;

import com.shunyi.autoparts.ui.model.Car;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

/** 车型类目编辑Controller */
public class CarEditorController {
    Stage dialog;
    Car updatedCar;
    Callback<Car, Object> callback;
    @FXML
    Button btnSaveAndClose;
    @FXML
    Button btnContinueAdd;
    @FXML
    TextField txtCode;
    @FXML
    TextField txtName;

    @FXML
    void cancel(ActionEvent actionEvent) {
        dialog.close();
    }

    @FXML
    void saveAndClose(ActionEvent actionEvent) {
        if(validate()) {
            dialog.close();
            Car car = new Car();
            car.setCode(txtCode.getText());
            car.setModel(txtName.getText());
            callback.call(car);
        }
    }

    @FXML
    void continueAdd(ActionEvent actionEvent) {
        if(validate()) {
            Car car = new Car();
            car.setCode(txtCode.getText());
            car.setModel(txtName.getText());
            callback.call(car);
        }
    }

    boolean validate() {
        if(txtCode.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("编号不能为空");
            alert.show();
            return false;
        }
        if(txtName.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("名称不能为空");
            alert.show();
            return false;
        }
        return true;
    }

    public void prepare(Stage dialog, Car updatedCar, Callback<Car, Object> callback) {
        this.dialog = dialog;
        this.updatedCar = updatedCar;
        this.callback = callback;
        initButton();

        if(updatedCar != null) {
            txtCode.setText(updatedCar.getCode());
            txtName.setText(updatedCar.getModel());
            btnContinueAdd.setVisible(false);
        }
    }

    void initButton() {
        btnSaveAndClose.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
    }

}
