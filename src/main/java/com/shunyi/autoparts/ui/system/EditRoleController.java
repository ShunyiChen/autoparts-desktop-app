package com.shunyi.autoparts.ui.system;

import com.shunyi.autoparts.ui.common.Constants;
import com.shunyi.autoparts.ui.common.Env;
import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.common.HttpClient;
import com.shunyi.autoparts.ui.common.vo.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/** 编辑角色Controller */
public class EditRoleController {
    private Stage dialog;
    private Callback<Role, String> callback;
    private Role selectedRole;

    @FXML
    private VBox userCheckBoxPane;
    @FXML
    private VBox permissionCheckBoxPane;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtDesc;
    @FXML
    private Button btnOk;
    @FXML
    private Button btnContinue;

    @FXML
    private void ok() {
        if(validate()) {
            //更改角色
            if(selectedRole != null) {
                Role updatedRole = new Role();
                updatedRole.setId(selectedRole.getId());
                updatedRole.setName(selectedRole.getName());
                updatedRole.setDescription(selectedRole.getDescription());

                //更新角色与用户关系
                try {
                    String json = HttpClient.GET("/userrolemappings/role/"+selectedRole.getId());
                    UserRoleMapping[] oldUserRoleMappings = GoogleJson.GET().fromJson(json, UserRoleMapping[].class);
                    List<UserRoleMapping.Id> oldUserRoleMappingIds = new ArrayList<>();
                    for(UserRoleMapping mapping : oldUserRoleMappings) {
                        oldUserRoleMappingIds.add(mapping.getId());
                    }

                    List<UserRoleMapping.Id> newUserRoleMappingIds = new ArrayList<>();
                    userCheckBoxPane.getChildren().forEach(item -> {
                        CheckBox checkBox = (CheckBox)item;
                        if(checkBox.isSelected()) {
                            String userId = checkBox.getUserData().toString();
                            UserRoleMapping.Id id = new UserRoleMapping.Id(Long.valueOf(userId), selectedRole.getId());
                            UserRoleMapping mapping = new UserRoleMapping();
                            mapping.setId(id);
                            updatedRole.getUserRoleMappingSet().add(mapping);

                            newUserRoleMappingIds.add(id);
                        }
                    });
                    List<UserRoleMapping.Id> removableList = oldUserRoleMappingIds.stream().filter(e -> !contains(newUserRoleMappingIds, e)).collect(Collectors.toList());
                    UserRoleMapping.Id[] removableIds = removableList.toArray(new UserRoleMapping.Id[removableList.size()]);
                    json = GoogleJson.GET().toJson(removableIds);
                    HttpClient.BATCH_DELETE("/userrolemappings", json);

                    List<UserRoleMapping.Id> addibleList = newUserRoleMappingIds.stream().filter(e -> !contains(oldUserRoleMappingIds, e)).collect(Collectors.toList());
                    UserRoleMapping.Id[] addibleIds = addibleList.toArray(new UserRoleMapping.Id[addibleList.size()]);
                    json = GoogleJson.GET().toJson(addibleIds);
                    HttpClient.POST("/userrolemappings", json);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                //更新角色与权限关系
                try {
                    String json = HttpClient.GET("/rolepermissionmappings/role/"+selectedRole.getId());
                    RolePermissionMapping[] oldRolePermissionMappings = GoogleJson.GET().fromJson(json, RolePermissionMapping[].class);
                    List<RolePermissionMapping.Id> oldRolePermissionMappingIds = new ArrayList<>();
                    for(RolePermissionMapping mapping : oldRolePermissionMappings) {
                        oldRolePermissionMappingIds.add(mapping.getId());
                    }
                    List<RolePermissionMapping.Id> newRolePermissionMappingIds = new ArrayList<>();
                    permissionCheckBoxPane.getChildren().forEach(item -> {
                        CheckBox checkBox = (CheckBox) item;
                        if(checkBox.isSelected()) {
                            String permissionId = checkBox.getUserData().toString();
                            RolePermissionMapping.Id id = new RolePermissionMapping.Id(selectedRole.getId(), Long.valueOf(permissionId));
                            RolePermissionMapping mapping = new RolePermissionMapping();
                            mapping.setId(id);
                            updatedRole.getRolePermissionMappingSet().add(mapping);

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
                //更改角色
                updatedRole.setName(txtName.getText());
                updatedRole.setDescription(txtDesc.getText());
                String json = GoogleJson.GET().toJson(updatedRole);
                try {
                    HttpClient.PUT("/roles/"+updatedRole.getId(), json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                callback.call(updatedRole);

            }
            //新建角色
            else {
                creatingRole();
            }
            dialog.close();
        }
    }

    @FXML
    private void continueCreating() {
        if(validate()) {
            creatingRole();
        }
    }

    private void creatingRole() {
        //新建一个角色
        Role createdRole = new Role(0L, txtName.getText(), txtDesc.getText(), new HashSet<UserRoleMapping>(), new HashSet<RolePermissionMapping>());
        String json = GoogleJson.GET().toJson(createdRole);
        //新建角色与用户关系
        try {
            String idStr = HttpClient.POST("/roles", json);
            createdRole.setId(Long.valueOf(idStr));
            List<UserRoleMapping.Id> listIds = new ArrayList<>();
            userCheckBoxPane.getChildren().forEach(item -> {
                CheckBox checkBox = (CheckBox) item;
                if(checkBox.isSelected()) {
                    String userId = checkBox.getUserData().toString();
                    UserRoleMapping.Id id = new UserRoleMapping.Id(Long.valueOf(userId), Long.valueOf(idStr));
                    UserRoleMapping mapping = new UserRoleMapping();
                    mapping.setId(id);
                    createdRole.getUserRoleMappingSet().add(mapping);
                    listIds.add(id);
                }
            });
            json = GoogleJson.GET().toJson(listIds.toArray(new UserRoleMapping.Id[listIds.size()]));
            HttpClient.POST("/userrolemappings", json);

        } catch (IOException e) {
            e.printStackTrace();
        }

        //新建角色与权限关系
        try {
            List<RolePermissionMapping.Id> listIds = new ArrayList<>();
            permissionCheckBoxPane.getChildren().forEach(item -> {
                CheckBox checkBox = (CheckBox) item;
                if(checkBox.isSelected()) {
                    String permissionId = checkBox.getUserData().toString();
                    RolePermissionMapping.Id id = new RolePermissionMapping.Id(createdRole.getId(), Long.valueOf(permissionId));
                    RolePermissionMapping mapping = new RolePermissionMapping();
                    mapping.setId(id);
                    createdRole.getRolePermissionMappingSet().add(mapping);
                    listIds.add(id);
                }
            });
            json = GoogleJson.GET().toJson(listIds.toArray(new RolePermissionMapping.Id[listIds.size()]));
            HttpClient.POST("/rolepermissionmappings", json);

        } catch (IOException e) {
            e.printStackTrace();
        }
        createdRole.setName(txtName.getText());
        createdRole.setDescription(txtDesc.getText());

        callback.call(createdRole);
    }

    /**
     * Checks if the list contains specified id
     *
     * @param list
     * @param findId
     * @return
     */
    private boolean contains(List<UserRoleMapping.Id> list, UserRoleMapping.Id findId) {
        for(UserRoleMapping.Id id : list) {
            if(id.getRoleId() == findId.getRoleId()
                    && id.getUserId() == findId.getUserId()) {
                return true;
            }
        }
        return false;
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
     *
     * @param dialog
     * @param selectedRole
     * @param callback
     */
    public void prepare(Stage dialog, Role selectedRole, Callback<Role, String> callback) {
        this.dialog = dialog;
        this.selectedRole = selectedRole;
        this.callback = callback;
        btnContinue.setVisible(true);
        btnOk.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));

        initUserCheckBox();

        initPermissionCheckBox();

        if(selectedRole != null) {
            txtName.setText(selectedRole.getName());
            txtDesc.setText(selectedRole.getDescription());
            txtName.selectAll();
            btnOk.setText("更  改");
            btnContinue.setVisible(false);
        }
    }

    private void initUserCheckBox() {
        String data;
        try {
            data = HttpClient.GET("/users");
            User[] users = GoogleJson.GET().fromJson(data, User[].class);
            for(User user : users) {
                CheckBox checkBox = new CheckBox(user.getUsername());
                checkBox.setUserData(user.getId());
                userCheckBoxPane.getChildren().add(checkBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //加载选中的
        if(selectedRole != null) {
            userCheckBoxPane.getChildren().forEach(checkbox -> {
                Long userId = Long.valueOf(checkbox.getUserData().toString());
                ((CheckBox)checkbox).setSelected(false);
                selectedRole.getUserRoleMappingSet().forEach(id -> {
                    if(id.getId().getUserId() == userId) {
                        ((CheckBox)checkbox).setSelected(true);
                    }
                });

            });
        }
    }

    private void initPermissionCheckBox() {
        String data;
        try {
            data = HttpClient.GET("/permissions");
            Permission[] permissions = GoogleJson.GET().fromJson(data, Permission[].class);
            for(Permission permission : permissions) {
                CheckBox checkBox = new CheckBox(permission.getName());
                checkBox.setUserData(permission.getId());
                permissionCheckBoxPane.getChildren().add(checkBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //加载选中的
        if(selectedRole != null) {
            permissionCheckBoxPane.getChildren().forEach(checkbox -> {
                Long permissionId = Long.valueOf(checkbox.getUserData().toString());
                ((CheckBox)checkbox).setSelected(false);
                selectedRole.getRolePermissionMappingSet().forEach(id -> {
                    if(id.getId().getPermissionId() == permissionId) {
                        ((CheckBox)checkbox).setSelected(true);
                    }
                });

            });
        }
    }
}
