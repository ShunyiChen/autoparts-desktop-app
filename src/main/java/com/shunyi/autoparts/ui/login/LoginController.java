package com.shunyi.autoparts.ui.login;

import com.shunyi.autoparts.ui.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class LoginController {
    @FXML
    private VBox bgBox;
    @FXML
    private Hyperlink titleLink;
    @FXML
    private TextField userNameTxtField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button button;
    private MainApp application;

    @FXML
    public void loggingIn(ActionEvent event) {
        if(StringUtils.isEmpty(userNameTxtField.getText().trim())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("用户名密码输入错误提醒");
            alert.setHeaderText("用户名不能为空！");
            alert.showAndWait();
        } else {
            try {
                application.gotoDashboard();
                System.out.println("Logged in successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void initialize(MainApp application) {
        this.application = application;
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
//        label.setText("Hello, JavaFX " + javafxVersion + "\nRunning on Java " + javaVersion + ".");

        Image bg = new Image(getClass().getResourceAsStream("/img/LoginBG.png"), 745,406,false,true);
        BackgroundImage myBI = new BackgroundImage(bg,
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        bgBox.setBackground(new Background(myBI));
        bgBox.setOpacity(0.2);

        application.getScene().getAccelerators().put(
                new KeyCodeCombination(KeyCode.ENTER),
                new Runnable() {
                    @FXML public void run() {

                        button.fire();
                    }
                }
        );
    }

}
