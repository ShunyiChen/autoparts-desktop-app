package com.shunyi.autoparts.ui.login;

import com.google.gson.Gson;
import com.shunyi.autoparts.ui.MainApp;
import com.shunyi.autoparts.ui.common.ENV;
import com.shunyi.autoparts.ui.h2.H2;
import com.shunyi.autoparts.ui.http.HttpClient;
import com.shunyi.autoparts.ui.http.AuthenticationResponse;
import com.shunyi.autoparts.ui.model.RemoteConnection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import java.sql.SQLException;

public class LoginController {
    @FXML
    private VBox bgBox;
    @FXML
    private Hyperlink titleLink;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private ComboBox<RemoteConnection> cbRemoteConnection;
    @FXML
    private CheckBox cBoxRemember;
    @FXML
    private Button btnSettings;
    @FXML
    private CheckBox cBoxAutoLogin;
    @FXML
    private Button button;
    private MainApp application;
    private Gson gson = new Gson();

    @FXML
    public void loggingIn(ActionEvent event) {
        if(StringUtils.isEmpty(txtUsername.getText().trim())
            || StringUtils.isEmpty(txtPassword.getText().trim())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("登录失败");
            alert.setHeaderText("用户名或密码不能为空。");
            alert.showAndWait();
        } else {
            RemoteConnection rc = cbRemoteConnection.getValue();
            ENV.getInstance().addToEnvironment("RemoteConnection", rc);
            String path = "/authenticate";
            String json = "{\"username\":\""+txtUsername.getText().trim()+"\",\"password\":\""+txtPassword.getText().trim()+"\"}";
            try {
                String data = HttpClient.POST(path, json, "");
                AuthenticationResponse res = gson.fromJson(data, AuthenticationResponse.class);
                progressBar.setProgress(0.8);
                if(res.getToken() != null) {
                    ENV.getInstance().addToEnvironment("Authorization", "Bearer "+res.getToken());
                    progressBar.setProgress(1);
                    application.gotoDashboard();
                    System.out.println("Logged in successfully.");
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("身份验证失败");
                    alert.setHeaderText("用户名和密码不匹配。");
                    alert.showAndWait();
                }
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("身份验证失败");
                alert.setHeaderText(e.toString());
                alert.showAndWait();
            }
        }
    }

    @FXML
    public void openRemoteConnectionEditor(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/login/RemoteConnectionEditor.fxml"
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
        dialog.setOnHiding( e -> {
            initComboBoxData();
        });
        RemoteConnectionEditorController controller = loader.getController();
        controller.prepare(dialog);
        dialog.setTitle("设置网关地址");
        dialog.initOwner(application.getStage());
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    private void initComboBoxData() {
        try {
            cbRemoteConnection.getItems().clear();
            cbRemoteConnection.getItems().addAll(H2.retrieveAll());
            cbRemoteConnection.getItems().stream().forEach(e -> {
                if(e.is_default()) {
                    cbRemoteConnection.setValue(e);
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void prepare(MainApp application) {
        this.application = application;
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
//        label.setText("Hello, JavaFX " + javafxVersion + "\nRunning on Java " + javaVersion + ".");

        progressBar.setProgress(0.1);

        txtUsername.setText("shunyichen");
        txtPassword.setText("123456");

        cbRemoteConnection.setStyle("-fx-font-size: 14px;");
        initComboBoxData();

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


        // 初始化网关按钮
        Image imgGear = new Image(getClass().getResourceAsStream("/img/Gear16.png"));
        btnSettings.setGraphic(new ImageView(imgGear));

        progressBar.setProgress(0.2);
    }

}
