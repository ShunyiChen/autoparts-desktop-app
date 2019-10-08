package com.shunyi.autoparts.ui.login;

import com.google.gson.Gson;
import com.shunyi.autoparts.ui.MainApp;
import com.shunyi.autoparts.ui.common.HttpRequest;
import com.shunyi.autoparts.ui.common.HttpResponse;
import com.shunyi.autoparts.ui.supplier.ChooserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class LoginController {
    @FXML
    private VBox bgBox;
    @FXML
    private Hyperlink titleLink;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private ComboBox cBoxGateway;
    @FXML
    private CheckBox cBoxRemember;
    @FXML
    private Button btnSettings;
    @FXML
    private CheckBox cBoxAutoLogin;
    @FXML
    private Button button;
    private MainApp application;

    @FXML
    public void loggingIn(ActionEvent event) {
        if(StringUtils.isEmpty(txtUsername.getText().trim())
            || StringUtils.isEmpty(txtPassword.getText().trim())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("登录失败");
            alert.setHeaderText("用户名或密码不能为空。");
            alert.showAndWait();
        } else {
            String url = "http://localhost:8080/authenticate";
            String json = "{\"username\":\""+txtUsername.getText().trim()+"\",\"password\":\""+txtPassword.getText().trim()+"\"}";
            try {
                String data = HttpRequest.POST(url, json, "");
                Gson gson = new Gson();
                HttpResponse res = gson.fromJson(data, HttpResponse.class);
                if(res.getToken() != null) {
                    application.gotoDashboard();
                    System.out.println("Logged in successfully.");
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("身份验证失败");
                    alert.setHeaderText("用户名和密码不匹配。");
                    alert.showAndWait();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void openGatewayEditor(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/login/GatewayAddrEditor.fxml"
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
        GatewayAddrEditorController controller = loader.getController();
        controller.initComponents(dialog);
        dialog.setTitle("设置网关地址");
        dialog.initOwner(application.getStage());
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
//        dialog.show();
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    public void initComponents(MainApp application) {
        this.application = application;
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
//        label.setText("Hello, JavaFX " + javafxVersion + "\nRunning on Java " + javaVersion + ".");

        txtUsername.setText("shunyichen");
        txtPassword.setText("123456");

        String[] items = {"127.0.0.1"};
        cBoxGateway.getItems().addAll(items);
        cBoxGateway.setValue(items[0]);
        cBoxGateway.setStyle("-fx-font-size: 14px;");

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

//        cBoxGateway.getItems().addAll();

        // 初始化网关按钮
        Image imgGear = new Image(getClass().getResourceAsStream("/img/Gear16.png"));
        btnSettings.setGraphic(new ImageView(imgGear));
//        btnSettings.setBackground(Background.EMPTY);


    }

}
