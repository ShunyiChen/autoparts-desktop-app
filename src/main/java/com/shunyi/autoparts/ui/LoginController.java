package com.shunyi.autoparts.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
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
    private MainApp application;

    public void setApp(MainApp application){
        this.application = application;
    }

    @FXML
    public void loggingIn(ActionEvent event) {
        System.out.println("用户名:"+userNameTxtField.getText()+" 密码:"+passwordField.getText());
        if(StringUtils.isEmpty(userNameTxtField.getText().trim())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("用户名密码输入错误提醒");
            alert.setHeaderText("用户名不能为空！");
            alert.showAndWait();
        } else {
            System.out.println("application="+application);
            try {
                application.gotoDashboard();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void initialize() {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
//        label.setText("Hello, JavaFX " + javafxVersion + "\nRunning on Java " + javaVersion + ".");

        Image bg = new Image(getClass().getResourceAsStream("/img/LoginBG.png"), 745,406,false,true);
        BackgroundImage myBI = new BackgroundImage(bg,
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        bgBox.setBackground(new Background(myBI));
        bgBox.setOpacity(0.2);

        titleLink.setVisited(false);
//        titleLink.
    }

}
