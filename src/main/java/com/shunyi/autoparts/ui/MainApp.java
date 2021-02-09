package com.shunyi.autoparts.ui;

import com.shunyi.autoparts.ui.login.LoginController;
import com.shunyi.autoparts.ui.main.MainFrame;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @Description: 程序入口类
 * @Author: 陈顺谊
 * @CreateDate: 2020/1/8 16:24
 * @Version: 1.0
 */
public class MainApp extends Application {
    private Stage stage;
    private Scene scene;
    private MainFrame mainFrame;
    private static String Name = "AutoParts 客户端";
    private static String VERSION = "v1.0";

    @Override
    public void start(Stage stage) throws Exception {

        PasswordEncoder encoder = new BCryptPasswordEncoder(10);
        String pass = encoder.encode("123456");
        System.err.println(pass);

        //i18n
        Locale currentLocale = Locale.getDefault();
        Locale locale = new Locale("en_US");
        ResourceBundle hundle = ResourceBundle.getBundle("i18n.labelText", currentLocale);
//        System.out.println(hundle.getLocale().getCountry());
        // For example
//        FXMLLoader loader = new FXMLLoader(
//                getClass().getResource(
//                        "/fxml/products/CarChooser.fxml"
//                ), hundle
//        );


        this.stage = stage;
        scene = new Scene(new Pane());
        scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        gotoLogin();
        stage.setTitle(Name+" "+VERSION);
        stage.setScene(scene);
        stage.show();
    }

    public void gotoDashboard() {
        Platform.runLater(() ->  {
            mainFrame = new MainFrame(this);
            scene.setRoot(mainFrame);
            stage.setResizable(true);
            stage.setMaximized(true);
        });
    }

    public void gotoLogin()  {
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource(
                    "/fxml/login/login.fxml"
            )
        );
        StackPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LoginController controller = loader.getController();
        controller.prepare(this);
        scene.setRoot(root);
        stage.setResizable(false);
        stage.setMaximized(false);
    }

    public void quit() {
        Platform.exit();
        System.exit(0);
    }

    public Scene getScene() {
        return scene;
    }

    public Stage getStage() {
        return stage;
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
