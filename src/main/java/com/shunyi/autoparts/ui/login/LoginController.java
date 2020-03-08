package com.shunyi.autoparts.ui.login;

import com.shunyi.autoparts.ui.MainApp;
import com.shunyi.autoparts.ui.common.ENV;
import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.common.ICONS;
import com.shunyi.autoparts.ui.common.H2;
import com.shunyi.autoparts.ui.common.AuthenticationResponse;
import com.shunyi.autoparts.ui.common.HttpClient;
import com.shunyi.autoparts.ui.model.RemoteConnection;
import javafx.application.Platform;
import javafx.concurrent.Task;
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
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description: 登录界面
 * @Author: 陈顺谊
 * @CreateDate: 2020/1/8 16:24
 * @Version: 1.0
 */
public class LoginController {
    private ExecutorService executor = Executors.newFixedThreadPool(1);
    private KeyCodeCombination keyCodeCombination = new KeyCodeCombination(KeyCode.ENTER);
    private MainApp application;
    private ICONS icons = ICONS.getInstance();

    @FXML
    private VBox background;
    @FXML
    private Label title;
    @FXML
    private Label brand;
    @FXML
    private ImageView logo;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private ComboBox<RemoteConnection> comboboxConnections;
    @FXML
    private CheckBox checkboxRemember;
    @FXML
    private CheckBox checkboxAutomaticLogin;
    @FXML
    private Button btnSettings;
    @FXML
    private Button btnLogin;


    @FXML
    public void signIn(ActionEvent event) {

        Task<Integer> task = new Task<>() {

            @Override
            protected Integer call() {

                Platform.runLater(() ->  progressBar.setProgress(0.0d));

                if(StringUtils.isEmpty(txtUsername.getText().trim())
                        || StringUtils.isEmpty(txtPassword.getText().trim())) {
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("登录失败");
                        alert.setHeaderText("用户名或密码不能为空");
                        alert.show();
                    });
                    Platform.runLater(() ->  progressBar.setProgress(1.0d));
                } else {
                    Platform.runLater(() ->  progressBar.setProgress(0.1d));
                    RemoteConnection rc = comboboxConnections.getValue();
                    ENV.getInstance().addToEnvironment("RemoteConnection", rc);
                    String json = "{\"username\":\""+txtUsername.getText().trim()+"\",\"password\":\""+txtPassword.getText().trim()+"\"}";
                    Platform.runLater(() ->  progressBar.setProgress(0.2d));
                    try {
                        String data = HttpClient.POST("/authenticate", json, "");
                        Platform.runLater(() ->  progressBar.setProgress(0.9d));

                        AuthenticationResponse res = GoogleJson.GET().fromJson(data, AuthenticationResponse.class);
                        Platform.runLater(() ->  progressBar.setProgress(0.95d));

                        if(res.getToken() != null) {

                            //删除Enter监听
                            application.getScene().getAccelerators().remove(keyCodeCombination);

                            ENV.getInstance().addToEnvironment("Authorization", "Bearer "+res.getToken());
                            ENV.getInstance().addToEnvironment("loginUser", res.getUsername());

                            Platform.runLater(() -> progressBar.setProgress(1d));
                            application.gotoDashboard();
                            System.out.println("Logged in successfully.");

                        } else {
                            Platform.runLater(() -> {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("身份验证失败");
                                alert.setHeaderText(res.getError());
                                alert.show();
                            });
                            Platform.runLater(() ->  progressBar.setProgress(1.0d));
                        }

                    } catch (IOException e) {
                        Platform.runLater(() -> {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("身份验证失败");
                            alert.setHeaderText(e.toString());
                            alert.show();
                        });
                        Platform.runLater(() ->  progressBar.setProgress(1.0d));
                    }
                }
                return 0;
            }
        };

        executor.execute(task);
        executor.shutdown();
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
            comboboxConnections.getItems().clear();
            comboboxConnections.getItems().addAll(H2.retrieveAll());
            comboboxConnections.getItems().stream().forEach(e -> {
                if(e.is_default()) {
                    comboboxConnections.setValue(e);
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
        System.out.println("javaVersion="+javaVersion+",javafxVersion="+javafxVersion);

        //TODO remove username and password
        txtUsername.setText("root");
        txtPassword.setText("123456");

        title.setStyle("-fx-text-fill: rgb(255,255,255);");
        brand.setStyle("-fx-text-fill: rgb(255,255,255);");
        logo.setImage(icons.countdown_alex());

        comboboxConnections.setStyle("-fx-font-size: 14px;");
        initComboBoxData();

        Image bg = new Image(getClass().getResourceAsStream("/img/login/auto-parts.png"), 743, 349, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(bg,
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        background.setBackground(new Background(backgroundImage));
        background.setOpacity(0.2);

        application.getScene().getAccelerators().put(
                keyCodeCombination,
                new Runnable() {
                    @Override
                    public void run() {
                        btnLogin.fire();
                    }
                }
        );

        // 初始化网关按钮
        Image imgGear = new Image(getClass().getResourceAsStream("/img/login/Gear16.png"));
        btnSettings.setGraphic(new ImageView(imgGear));
        //获得焦点
        Platform.runLater(() -> btnLogin.requestFocus());
    }

}
