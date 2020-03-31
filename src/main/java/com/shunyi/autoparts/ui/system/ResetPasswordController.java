package com.shunyi.autoparts.ui.system;

import com.shunyi.autoparts.ui.common.HttpClient;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;

/**
 * @Description: 重置密码Controller
 * @Author: 陈顺谊
 * @CreateDate: 2020/1/10 14:32
 * @Version: 1.0
 */
public class ResetPasswordController {
    private Stage dialog;
    private Long userId;
    private PasswordEncoder encoder = new BCryptPasswordEncoder(10);

    @FXML
    private PasswordField txtPassword;
    @FXML
    private PasswordField txtConfirmPassword;
    @FXML
    private Button btnReset;

    public void prepare(Stage dialog, Long userId) {
        this.dialog = dialog;
        this.userId = userId;
        btnReset.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
    }

    @FXML
    private void cancel() {
        dialog.close();
    }

    @FXML
    private void reset() {
        if(validate()) {
            String password = txtPassword.getText();
            String encryptedPassword = encoder.encode(password);
            try {
                HttpClient.PUT("/users/changepassword/"+userId, encryptedPassword);
            } catch (IOException e) {
                e.printStackTrace();
            }
            dialog.close();
        }
    }

    private boolean validate() {
        if(txtPassword.getText().equals("")
            || txtConfirmPassword.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("密码不能为空");
            alert.show();
            return false;

        } else if (!txtPassword.getText().equals(txtConfirmPassword.getText())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("确认密码不一致");
            alert.show();
            return false;
        }
        return true;
    }
}
