package com.shunyi.autoparts.ui.products;

import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.common.HttpClient;
import com.shunyi.autoparts.ui.common.VFSClient;
import com.shunyi.autoparts.ui.model.BrandSeries;
import com.shunyi.autoparts.ui.model.Category;
import com.shunyi.autoparts.ui.model.Logo;
import com.shunyi.autoparts.ui.model.VFS;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class BrandSeriesEditorController {
    private Stage dialog;
    private BrandSeries brandSeries;
    private Callback<BrandSeries, Object> callback;
    private Category  selectedCategory;
    private Logo choosedLogo;
    @FXML
    ImageView imgLogo;
    @FXML
    TextField txtCategory;
    @FXML
    TextField txtChineseName;
    @FXML
    TextField txtEnglishName;
    @FXML
    ComboBox<String> boxStatus;
    @FXML
    TextField txtOfficialWebSite;
    @FXML
    TextArea txtDesc;
    @FXML
    Button btnSave;
    @FXML
    Button btnContinueAdd;

    @FXML
    private void logoManagement() {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/products/logo_management.fxml"
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
        LogoManagementController controller = loader.getController();
        controller.prepare(dialog, false, null);

        dialog.setTitle("Logo管理");
        dialog.initOwner(this.dialog);
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }


    @FXML
    private void chooseLogo() {
        Callback<FlowPane, String> callback = selectedFlowPane -> {
            CheckBox checkBox = (CheckBox) selectedFlowPane.getChildren().get(0);
            ImageView imageView = (ImageView) selectedFlowPane.getChildren().get(1);
            imgLogo.setImage(imageView.getImage());
            choosedLogo = (Logo)checkBox.getUserData();
            return "";
        };
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/products/logo_management.fxml"
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
        LogoManagementController controller = loader.getController();
        controller.prepare(dialog, true, callback);

        dialog.setTitle("Logo管理");
        dialog.initOwner(this.dialog);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    @FXML
    private void choose() {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/products/category_chooser.fxml"
                )
        );
        VBox root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage subStage = new Stage();
        CategoryChooserController controller = loader.getController();

        Callback<Category, Object> cb = new Callback<Category, Object>() {
            @Override
            public Object call(Category param) {
                selectedCategory = param;
                txtCategory.setText(selectedCategory.getName());
                return null;
            }
        };
        controller.prepare(subStage, selectedCategory, cb);
        subStage.setTitle("选择产品类目");
        subStage.initOwner(dialog);
        subStage.setResizable(false);
        subStage.initModality(Modality.APPLICATION_MODAL);
        subStage.setScene(scene);
        // center stage on screen
        subStage.centerOnScreen();
        subStage.show();
    }

    @FXML
    private void cancel() {
        dialog.close();
    }

    @FXML
    private void saveAndClose() {
        if(validation()) {
            continueAdd();
            dialog.close();
        }
    }

    @FXML
    private void continueAdd() {
        if(validation()) {
            BrandSeries newBrandSeries = new BrandSeries(selectedCategory, txtChineseName.getText(), txtEnglishName.getText(), txtDesc.getText(), choosedLogo, boxStatus.getValue(), txtOfficialWebSite.getText());
            callback.call(newBrandSeries);
        }
    }

    private boolean validation() {
        if(selectedCategory == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("产品类目不能为空");
            alert.show();
            return false;
        }
        else if(selectedCategory .getId() == 0L) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("不能在根节点下创建或更改品牌，请选中其他类目");
            alert.show();
            return false;
        }
        else if(txtChineseName.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("中文名不能为空");
            alert.show();
            return false;
        }
        else if(choosedLogo == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("请选择Logo");
            alert.show();
            return false;
        }
        return true;
    }

    public void prepare(Stage dialog, BrandSeries brandSeries, Callback<BrandSeries, Object> callback, Category selectedCategory) {
        this.dialog = dialog;
        this.brandSeries = brandSeries;
        this.callback = callback;
        this.selectedCategory = selectedCategory;
        initComboBox();
        initButton();
        populate();
    }

    private void initComboBox() {
        boxStatus.getItems().addAll("可用","禁用");
        boxStatus.getSelectionModel().select(0);
        boxStatus.setStyle("-fx-font-size: 14px;");
    }

    private void initButton() {
        btnSave.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
    }

    private void populate() {
        txtCategory.setDisable(true);
        if(selectedCategory != null) {
            txtCategory.setText(selectedCategory.getName());
        }
        if(brandSeries != null) {
            selectedCategory = brandSeries.getCategory();
            txtCategory.setText(selectedCategory.getName());
            txtChineseName.setText(brandSeries.getChineseName());
            txtEnglishName.setText(brandSeries.getEnglishName());
            boxStatus.setValue(brandSeries.getStatus());
            txtOfficialWebSite.setText(brandSeries.getOfficialSite());
            txtDesc.setText(brandSeries.getDescription());
            btnContinueAdd.setVisible(false);

            //初始化选中Logo
            choosedLogo = brandSeries.getLogo();
            VFS defaultVFS = null;
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
                FileObject fileObject = VFSClient.resolveFile(defaultVFS, choosedLogo.getPath());
                InputStream is = fileObject.getContent().getInputStream();
                Image image = new Image(is);
                imgLogo.setImage(image);
                is.close();
            } catch (FileSystemException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
