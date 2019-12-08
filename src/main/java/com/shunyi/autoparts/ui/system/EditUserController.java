package com.shunyi.autoparts.ui.system;

import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.http.HttpClient;
import com.shunyi.autoparts.ui.model.User;
import com.shunyi.autoparts.ui.model.UserShopMapping;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;


/** 编辑用户Controller */
public class EditUserController {

    private Stage dialog;
    private Callback<User, String> callback;

    @FXML
    private TextField txtUserName;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private PasswordField confirmTxtPassword;
    @FXML
    private CheckBox boxEnabled;
    @FXML
    private VBox list;
    @FXML
    private Button btnOk;
    @FXML
    private Button btnCancel;


    @FXML
    void cancel() {
        dialog.close();
    }

    @FXML
    void ok() {
        if(validate()) {

            User returnedUser = new User(txtUserName.getText(), txtPassword.getText(), boxEnabled.isSelected());
            String json = GoogleJson.GET().toJson(returnedUser);
            try {
                String idStr = HttpClient.POST("/users", json);
                list.getChildren().forEach(checkbox -> {
                    String shopId = checkbox.getUserData().toString();
                    UserShopMapping.Id mapping = new UserShopMapping.Id(Long.valueOf(idStr), Long.valueOf(shopId));


                });


            } catch (IOException e) {
                e.printStackTrace();
            }




            callback.call(returnedUser);
            dialog.close();
        }
    }

    private boolean validate() {
        if(txtUserName.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("用户名不能为空。");
            alert.show();
            return false;
        }
        else if(txtPassword.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("密码不能为空。");
            alert.show();
            return false;
        }
        else if(confirmTxtPassword.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("确认密码不能为空。");
            alert.show();
            return false;
        }
        else if(!txtPassword.getText().equals(confirmTxtPassword.getText())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("确认密码不正确。");
            alert.show();
            return false;
        }


        return true;
    }

    /**
     *
     * @param dialog
     * @param callback
     * @param selectedUser
     */
    public void prepare(Stage dialog, Callback<User, String> callback, User selectedUser) {
        this.dialog = dialog;
        this.callback = callback;
        list.getChildren().clear();
        btnOk.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
        if(selectedUser != null) {
//            txtName.setText(selectedShop.getName());
        }
    }
}
