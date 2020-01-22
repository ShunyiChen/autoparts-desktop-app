package com.shunyi.autoparts.ui.system;

import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.http.HttpClient;
import com.shunyi.autoparts.ui.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/** 编辑权限Controller */
public class EditPermissionController {
    private Stage dialog;
    private Callback<Permission, String> callback;
    private Permission selectedPermission;

    @FXML
    private VBox roleCheckBoxPane;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtDesc;
    @FXML
    private TextField txtCode;
    @FXML
    private Button btnOk;

    @FXML
    private void ok() {
        if(validate()) {
            Permission updatedPermission = new Permission();
            updatedPermission.setCode(selectedPermission.getCode());
            updatedPermission.setName(selectedPermission.getName());
            updatedPermission.setDescription(selectedPermission.getDescription());
            updatedPermission.setId(selectedPermission.getId());

            //更新权限与角色关系
            try {
                String json = HttpClient.GET("/rolepermissionmappings/permission/"+updatedPermission.getId());
                RolePermissionMapping[] oldRolePermissionMappings = GoogleJson.GET().fromJson(json, RolePermissionMapping[].class);
                List<RolePermissionMapping.Id> oldRolePermissionMappingIds = new ArrayList<>();
                for(RolePermissionMapping mapping : oldRolePermissionMappings) {
                    oldRolePermissionMappingIds.add(mapping.getId());
                }
                List<RolePermissionMapping.Id> newRolePermissionMappingIds = new ArrayList<>();
                roleCheckBoxPane.getChildren().forEach(item -> {
                    CheckBox checkBox = (CheckBox) item;
                    if(checkBox.isSelected()) {
                        String roleId = checkBox.getUserData().toString();
                        RolePermissionMapping.Id id = new RolePermissionMapping.Id(Long.valueOf(roleId), selectedPermission.getId());
                        RolePermissionMapping mapping = new RolePermissionMapping();
                        mapping.setId(id);
                        updatedPermission.getRolePermissionMappingSet().add(mapping);

                        newRolePermissionMappingIds.add(id);
                    }
                });

                List<RolePermissionMapping.Id> removableList = oldRolePermissionMappingIds.stream().filter(e -> !contains(newRolePermissionMappingIds, e)).collect(Collectors.toList());
                RolePermissionMapping.Id[] removableIds = removableList.toArray(new RolePermissionMapping.Id[removableList.size()]);
                json = GoogleJson.GET().toJson(removableIds);
                HttpClient.BATCH_DELETE("/rolepermissionmappings", json);

                List<RolePermissionMapping.Id> addibleList = newRolePermissionMappingIds.stream().filter(e -> !contains(oldRolePermissionMappingIds, e)).collect(Collectors.toList());
                RolePermissionMapping.Id[] addibleIds = addibleList.toArray(new RolePermissionMapping.Id[addibleList.size()]);
                json = GoogleJson.GET().toJson(addibleIds);
                HttpClient.POST("/rolepermissionmappings", json);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //更改权限
            updatedPermission.setName(txtName.getText());
            updatedPermission.setCode(txtCode.getText());
            updatedPermission.setDescription(txtDesc.getText());
            String json = GoogleJson.GET().toJson(updatedPermission);
            try {
                HttpClient.PUT("/permissions/"+updatedPermission.getId(), json);
            } catch (IOException e) {
                e.printStackTrace();
            }
            callback.call(updatedPermission);

            dialog.close();
        }
    }

    /**
     * Checks if the list contains specified id
     *
     * @param list
     * @param findId
     * @return
     */
    private boolean contains(List<RolePermissionMapping.Id> list, RolePermissionMapping.Id findId) {
        for(RolePermissionMapping.Id id : list) {
            if(id.getRoleId() == findId.getRoleId()
                    && id.getPermissionId() == findId.getPermissionId()) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @return
     */
    private boolean validate() {
        if(txtName.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("请输入名称");
            alert.show();
            return false;
        }
        return true;
    }

    @FXML
    private void cancel() {
        dialog.close();
    }

    /**
     * 准备
     *
     * @param dialog
     * @param selectedPermission
     * @param callback
     */
    public void prepare(Stage dialog, Permission selectedPermission, Callback<Permission, String> callback) {
        this.dialog = dialog;
        this.selectedPermission = selectedPermission;
        this.callback = callback;
        btnOk.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
        initRoleCheckBox();

        txtName.setText(selectedPermission.getName());
        txtDesc.setText(selectedPermission.getDescription());
        txtCode.setText(selectedPermission.getCode());
    }

    /**
     * 初始化角色选项
     */
    private void initRoleCheckBox() {
        String data;
        try {
            data = HttpClient.GET("/roles");
            Role[] roles = GoogleJson.GET().fromJson(data, Role[].class);
            for(Role role : roles) {
                CheckBox checkBox = new CheckBox(role.getName());
                checkBox.setUserData(role.getId());
                roleCheckBoxPane.getChildren().add(checkBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //加载选中的
        roleCheckBoxPane.getChildren().forEach(checkbox -> {
            Long roleId = Long.valueOf(checkbox.getUserData().toString());
            ((CheckBox)checkbox).setSelected(false);
            selectedPermission.getRolePermissionMappingSet().forEach(id -> {
                if(id.getId().getRoleId() == roleId) {
                    ((CheckBox)checkbox).setSelected(true);
                }
            });

        });
    }
}
