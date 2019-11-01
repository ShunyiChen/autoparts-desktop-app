package com.shunyi.autoparts.ui.products;

import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.http.HttpClient;
import com.shunyi.autoparts.ui.model.AttributeName;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;

import java.io.IOException;

public class AttributeNameEditorController {

    AttributeName updated;

    @FXML
    CheckBox boxAllowAlias;
    @FXML
    CheckBox boxIsColor;
    @FXML
    CheckBox boxIsEnum;
    @FXML
    CheckBox boxIsInput;
    @FXML
    CheckBox boxIsKey;
    @FXML
    CheckBox boxIsSales;
    @FXML
    CheckBox boxIsSearch;
    @FXML
    CheckBox boxIsRequired;
    @FXML
    CheckBox boxIsMultiSelected;
    @FXML
    ComboBox<String> boxStatus;
    @FXML
    ComboBox<String> boxSort;

    public void prepare(AttributeName selectedAttributeName) {
        this.updated = selectedAttributeName;
        initComboBox();

        boxAllowAlias.setSelected(selectedAttributeName.isAliasAllowed());
        boxIsColor.setSelected(selectedAttributeName.isColorProperty());
        boxIsEnum.setSelected(selectedAttributeName.isEnumProperty());
        boxIsInput.setSelected(selectedAttributeName.isInputProperty());
        boxIsKey.setSelected(selectedAttributeName.isKeyProperty());
        boxIsSales.setSelected(selectedAttributeName.isSaleProperty());
        boxIsSearch.setSelected(selectedAttributeName.isSearchField());
        boxIsRequired.setSelected(selectedAttributeName.isRequired());
        boxIsMultiSelected.setSelected(selectedAttributeName.isMultiple());
        boxStatus.setValue(selectedAttributeName.getStatus());
        boxSort.setValue(selectedAttributeName.getSort() == 1?Sort.ASC.getName():Sort.DESC.getName());

        initEvents();
    }

    void initComboBox() {
        boxStatus.getItems().addAll(AttributeStatus.AVAILABLE.getName(), AttributeStatus.DISABLED.getName());
        boxSort.getItems().addAll(Sort.ASC.getName(), Sort.DESC.getName());
        boxStatus.setStyle("-fx-font-size: 14px;");
        boxSort.setStyle("-fx-font-size: 14px;");
    }

    void initEvents() {
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
            updated.setSort(boxSort.getValue().equals(Sort.ASC.getName())?1:-1);
            update();
        });
    }

    void update() {
        String json = GoogleJson.GET().toJson(updated);
        try {
            HttpClient.PUT("/attributes/name/"+updated.getId(), json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
