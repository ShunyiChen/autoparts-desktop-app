package com.shunyi.autoparts.ui.products;

import com.shunyi.autoparts.ui.common.Constants;
import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.common.HttpClient;
import com.shunyi.autoparts.ui.common.vo.AttributeName;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;

import java.io.IOException;

public class AttributeNameEditorController {

    private AttributeName updated;
    @FXML
    private CheckBox boxAllowAlias;
    @FXML
    private CheckBox boxIsColor;
    @FXML
    private CheckBox boxIsEnum;
    @FXML
    private CheckBox boxIsInput;
    @FXML
    private CheckBox boxIsKey;
    @FXML
    private CheckBox boxIsSales;
    @FXML
    private CheckBox boxIsSearch;
    @FXML
    private CheckBox boxIsRequired;
    @FXML
    private CheckBox boxIsMultiSelected;
    @FXML
    private ComboBox<String> boxStatus;
    @FXML
    private ComboBox<String> boxSort;

    public void prepare(AttributeName selectedAttributeName) {
        this.updated = selectedAttributeName;
        initComboBox();

        boxAllowAlias.setSelected(selectedAttributeName.getAliasAllowed());
        boxIsColor.setSelected(selectedAttributeName.getColorProperty());
        boxIsEnum.setSelected(selectedAttributeName.getEnumProperty());
        boxIsInput.setSelected(selectedAttributeName.getInputProperty());
        boxIsKey.setSelected(selectedAttributeName.getKeyProperty());
        boxIsSales.setSelected(selectedAttributeName.getSaleProperty());
        boxIsSearch.setSelected(selectedAttributeName.getSearchField());
        boxIsRequired.setSelected(selectedAttributeName.getRequired());
        boxIsMultiSelected.setSelected(selectedAttributeName.getMultiple());
        boxStatus.setValue(selectedAttributeName.getStatus());
        boxSort.setValue(selectedAttributeName.getSort() == 1? Constants.ASC : Constants.DESC);

        initEvents();
    }

    private void initComboBox() {
        boxStatus.getItems().addAll(Constants.AVAILABLE, Constants.UNAVAILABLE);
        boxSort.getItems().addAll(Constants.ASC, Constants.DESC);
        boxStatus.setStyle("-fx-font-size: 14px;");
        boxSort.setStyle("-fx-font-size: 14px;");
    }

    private void initEvents() {
        boxAllowAlias.setOnAction(e -> {
            updated.setAliasAllowed(boxAllowAlias.isSelected());
            update();
        });
        boxIsColor.setOnAction(e -> {
            updated.setColorProperty(boxIsColor.isSelected());
            update();
        });
        boxIsEnum.setOnAction(e -> {
            updated.setEnumProperty(boxIsEnum.isSelected());
            update();
        });
        boxIsInput.setOnAction(e -> {
            updated.setInputProperty(boxIsInput.isSelected());
            update();
        });
        boxIsKey.setOnAction(e -> {
            updated.setKeyProperty(boxIsKey.isSelected());
            update();
        });
        boxIsSales.setOnAction(e -> {
            updated.setSaleProperty(boxIsSales.isSelected());
            update();
        });
        boxIsSearch.setOnAction(e -> {
            updated.setSearchField(boxIsSearch.isSelected());
            update();
        });
        boxIsRequired.setOnAction(e -> {
            updated.setRequired(boxIsRequired.isSelected());
            update();
        });
        boxIsMultiSelected.setOnAction(e -> {
            updated.setMultiple(boxIsMultiSelected.isSelected());
            update();
        });
        boxStatus.setOnAction(e -> {
            updated.setStatus(boxStatus.getValue());
            update();
        });
        boxSort.setOnAction(e -> {
            updated.setSort(boxSort.getValue().equals(Constants.ASC)?1: -1);
            update();
        });
    }

    private void update() {
        String json = GoogleJson.GET().toJson(updated);
        try {
            HttpClient.PUT("/attributes/name/"+updated.getId(), json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
