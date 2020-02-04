package com.shunyi.autoparts.ui.products;

import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.common.HttpClient;
import com.shunyi.autoparts.ui.common.VFSClient;
import com.shunyi.autoparts.ui.model.Logo;
import com.shunyi.autoparts.ui.model.VFS;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * @Description: Logo管理controller
 *
 * @Author: 陈顺谊
 * @CreateDate: 2020/2/3 12:51
 * @Version: 1.0
 */
public class LogoManagementController {
    private VFS defaultVFS;
    private Stage dialog;
    private Callback<FlowPane, String> callback;

    @FXML
    private FlowPane pane;
    @FXML
    private Button btnChoose;

    @FXML
    private void upload() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("打开文件");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.setInitialFileName("Log Entry Graphs.png");
        FileChooser.ExtensionFilter pngExtensionFilter =
                new FileChooser.ExtensionFilter(
                        "PNG - 图像格式文件 (.png.jpg)", "*.png", "*jpg");
        fileChooser.getExtensionFilters().add(pngExtensionFilter);
        fileChooser.setSelectedExtensionFilter(pngExtensionFilter);
        List<File> selectedFileList = fileChooser.showOpenMultipleDialog(dialog);
        if(selectedFileList != null)
        selectedFileList.forEach(selectedFile -> {
            String ext = getFileExtension(selectedFile);
            String newFileName = UUID.randomUUID().toString()+"."+ext;
            try {
                if(defaultVFS == null) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
                    alert.setHeaderText("找不到默认VFS");
                    alert.show();
                } else {
                    String path = "/logo/"+newFileName;
                    //上传文件到vfs
                    VFSClient.uploadSingleFile(selectedFile, defaultVFS, path);

                    Logo newLogo = new Logo(path, defaultVFS);
                    String data = GoogleJson.GET().toJson(newLogo);
                    String logoId = HttpClient.POST("/logos", data);
                    newLogo.setId(Long.valueOf(logoId));

                    FileInputStream fis = new FileInputStream(selectedFile);
                    //展现缩略图
                    pane.getChildren().add(createImageView(fis, newLogo));

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private FlowPane createImageView(InputStream fis, Logo logo) throws IOException {
        FlowPane flowPane = new FlowPane();
        flowPane.setAlignment(Pos.CENTER_LEFT);
        flowPane.setPrefWidth(130);
        flowPane.setHgap(3);

        CheckBox checkBox = new CheckBox();
        checkBox.setUserData(logo);

        Image image = new Image(fis);
        fis.close();
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.setOnMouseReleased(e ->{
            pane.getChildren().forEach(child -> {
                if(child == flowPane) {
                    child.setUserData(true);
                    child.setStyle(String.format("-fx-background-color: %s;", "rgb(63,81,181)")+";-fx-border-radius:5px;-fx-background-radius: 12;");
                } else {
                    child.setUserData(false);
                    child.setStyle("");
                }
            });
        });

        flowPane.getChildren().addAll(checkBox, imageView);

        return flowPane;
    }

    private String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }

    @FXML
    private void choose() {
        pane.getChildren().forEach(child -> {
            if((boolean)child.getUserData()) {
                callback.call((FlowPane) child);
                dialog.close();
            }
        });
    }

    @FXML
    private void remove() {
        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.NO, ButtonType.YES);
        alertConfirm.setHeaderText("请确认是否删除选中的Logo");
        alertConfirm.showAndWait().filter(response -> response == ButtonType.YES).ifPresent(response -> {
            List<FlowPane> removableList = new ArrayList<>();
            pane.getChildren().forEach(e -> {
                FlowPane subFlowPane = (FlowPane) e;
                CheckBox checkBox = (CheckBox) subFlowPane.getChildren().get(0);
                if(checkBox.isSelected()) {
                    removableList.add(subFlowPane);
                }
            });

            Iterator<FlowPane> iter = removableList.iterator();
            while(iter.hasNext()) {
                FlowPane flowPane = iter.next();

                CheckBox checkBox = (CheckBox) flowPane.getChildren().get(0);
                Logo logo = (Logo) checkBox.getUserData();

                try {
                    VFSClient.deleteSingleFile(defaultVFS, logo.getPath());
                } catch (FileSystemException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                try {
                    HttpClient.DELETE("/logos/"+logo.getId());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                pane.getChildren().remove(flowPane);
            }
        });
    }

    public void prepare(Stage dialog, boolean isChooser, Callback<FlowPane, String> callback) {
        this.dialog = dialog;
        this.callback = callback;
        try {
            defaultVFS = HttpClient.GET("/vfs/default", VFS.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(defaultVFS == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("找不到默认VFS");
            alert.show();
            return;
        }
        try {
            Logo[] logos = HttpClient.GET("/logos", Logo[].class);
            for(Logo logo : logos) {
                FileObject fileObject = VFSClient.resolveFile(defaultVFS, logo.getPath());
                InputStream is = fileObject.getContent().getInputStream();
                pane.getChildren().add(createImageView(is, logo));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(isChooser) {
            btnChoose.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
        } else {
            btnChoose.setVisible(false);
        }
    }

}
