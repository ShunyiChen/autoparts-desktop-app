package com.shunyi.autoparts.ui.products;

import com.google.gson.Gson;
import com.shunyi.autoparts.ui.http.HttpClient;
import com.shunyi.autoparts.ui.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Date;

public class BrandSeriesEditorController {
    Stage dialog;
    BrandSeries brandSeries;
    Callback<BrandSeries, Object> callback;
    Category  selectedCategory;
    Gson gson = new Gson();
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
    void upload(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("打开文件");
        fileChooser.showOpenDialog(dialog);
    }

    @FXML
    void choose(ActionEvent event) {
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
    void cancel(ActionEvent event) {
        dialog.close();
    }

    @FXML
    void saveAndClose(ActionEvent event) {
        if(validation()) {
            dialog.close();

            String json = null;
            try {
                json = HttpClient.GET("/logos/"+1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Logo logo = gson.fromJson(json, Logo.class);
            BrandSeries newBrandSeries = new BrandSeries(selectedCategory, txtChineseName.getText(), txtEnglishName.getText(), txtDesc.getText(), logo, boxStatus.getValue(), txtOfficialWebSite.getText());
            callback.call(newBrandSeries);
        }
    }

    @FXML
    void continueAdd(ActionEvent event) {
        if(validation()) {
            String json = null;
            try {
                json = HttpClient.GET("/logos/"+1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Logo logo = gson.fromJson(json, Logo.class);
            BrandSeries newBrandSeries = new BrandSeries(selectedCategory, txtChineseName.getText(), txtEnglishName.getText(), txtDesc.getText(), logo, boxStatus.getValue(), txtOfficialWebSite.getText());
            callback.call(newBrandSeries);
        }
    }

    boolean validation() {
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

    void initComboBox() {
        boxStatus.getItems().addAll("可用","禁用");
        boxStatus.getSelectionModel().select(0);
        boxStatus.setStyle("-fx-font-size: 14px;");
    }

    void initButton() {
        btnSave.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
    }

    void populate() {
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
        }
    }
}
