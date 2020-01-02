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
import java.util.Optional;
import java.util.stream.Collectors;


/** 编辑用户Controller */
public class EditUserController {

    private Stage dialog;
    private Callback<User, String> callback;
    private User selectedUser;

    @FXML
    private TextField txtUserName;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private PasswordField confirmTxtPassword;
    @FXML
    private CheckBox boxEnabled;
    @FXML
    private VBox vBoxShop;
    @FXML
    private VBox vBoxRole;
    @FXML
    private Button btnOk;

    @FXML
    void cancel() {
        dialog.close();
    }

    @FXML
    void ok() {
        if(validate()) {
            if(selectedUser != null) {
                //更新用户与店铺关系
                try {
                    String json = HttpClient.GET("usershopmappings/user/"+selectedUser.getId());
                    UserShopMapping[] oldUserShopMappings = GoogleJson.GET().fromJson(json, UserShopMapping[].class);
                    List<UserShopMapping.Id> oldUserShopMappingIds = new ArrayList<>();
                    for(UserShopMapping mapping : oldUserShopMappings) {
                        oldUserShopMappingIds.add(mapping.getId());
                    }

                    List<UserShopMapping.Id> newUserShopMappingIds = new ArrayList<>();
                    vBoxShop.getChildren().forEach(checkbox -> {
                        String shopId = checkbox.getUserData().toString();
                        newUserShopMappingIds.add(new UserShopMapping.Id(selectedUser.getId(), Long.valueOf(shopId)));
                    });

                    List<UserShopMapping.Id> removableList = oldUserShopMappingIds.stream().filter(e -> !contains(newUserShopMappingIds, e)).collect(Collectors.toList());
                    UserShopMapping.Id[] removableIds = removableList.toArray(new UserShopMapping.Id[removableList.size()]);
                    json = GoogleJson.GET().toJson(removableIds);
                    HttpClient.BATCH_DELETE("/usershopmappings", json);

                    List<UserShopMapping.Id> addibleList = newUserShopMappingIds.stream().filter(e -> !contains(oldUserShopMappingIds, e)).collect(Collectors.toList());
                    UserShopMapping.Id[] addibleIds = addibleList.toArray(new UserShopMapping.Id[addibleList.size()]);
                    json = GoogleJson.GET().toJson(addibleIds);
                    HttpClient.POST("/usershopmappings", json);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //更新用户与角色关系
                try {
                    String json = HttpClient.GET("userrolemappings/user/"+selectedUser.getId());
                    UserRoleMapping[] oldUserRoleMappings = GoogleJson.GET().fromJson(json, UserRoleMapping[].class);
                    List<UserRoleMapping.Id> oldUserRoleMappingIds = new ArrayList<>();
                    for(UserRoleMapping mapping : oldUserRoleMappings) {
                        oldUserRoleMappingIds.add(mapping.getId());
                    }

                    List<UserRoleMapping.Id> newUserRoleMappingIds = new ArrayList<>();
                    vBoxRole.getChildren().forEach(checkbox -> {
                        String roleId = checkbox.getUserData().toString();
                        newUserRoleMappingIds.add(new UserRoleMapping.Id(selectedUser.getId(), Long.valueOf(roleId)));
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

                selectedUser.setPassword(txtPassword.getText());
                selectedUser.setEnabled(boxEnabled.isSelected());
                callback.call(selectedUser);
            } else {
                //新建用户与店铺关系
                User createdUser = new User(txtUserName.getText(), txtPassword.getText(), boxEnabled.isSelected());
                String json = GoogleJson.GET().toJson(createdUser);
                System.out.println("jsn="+json);
                try {
                    String idStr = HttpClient.POST("/users", json);
                    createdUser.setId(Long.valueOf(idStr));
                    UserShopMapping.Id[] ids = new UserShopMapping.Id[vBoxShop.getChildren().size()];
                    int i = 0;
                    vBoxShop.getChildren().forEach(checkbox -> {
                        String shopId = checkbox.getUserData().toString();
                        ids[i] = new UserShopMapping.Id(Long.valueOf(idStr), Long.valueOf(shopId));
                    });
                    json = GoogleJson.GET().toJson(ids);
                    HttpClient.POST("/usershopmappings", json);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //新建用户与角色关系
                try {
                    UserRoleMapping.Id[] ids = new UserRoleMapping.Id[vBoxRole.getChildren().size()];
                    int i = 0;
                    vBoxRole.getChildren().forEach(checkbox -> {
                        String roleId = checkbox.getUserData().toString();
                        ids[i] = new UserRoleMapping.Id(createdUser.getId(), Long.valueOf(roleId));
                    });
                    json = GoogleJson.GET().toJson(ids);
                    HttpClient.POST("/userrolemappings", json);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                createdUser.setPassword(txtPassword.getText());
                createdUser.setEnabled(boxEnabled.isSelected());

                callback.call(createdUser);
            }
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
    private boolean contains(List<UserShopMapping.Id> list, UserShopMapping.Id findId) {
        for(UserShopMapping.Id id : list) {
            if(id.getShopId() == findId.getShopId()
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
    private boolean contains(List<UserRoleMapping.Id> list, UserRoleMapping.Id findId) {
        for(UserRoleMapping.Id id : list) {
            if(id.getRoleId() == findId.getRoleId()
                    && id.getUserId() == findId.getUserId()) {
                return true;
            }
        }
        return false;
    }

    private boolean validate() {
        if(txtUserName.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("用户名不能为空。");
            alert.show();
            return false;
        }
        else if(txtPassword.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("密码不能为空。");
            alert.show();
            return false;
        }
        else if(confirmTxtPassword.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("确认密码不能为空。");
            alert.show();
            return false;
        }
        else if(!txtPassword.getText().equals(confirmTxtPassword.getText())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("确认密码不正确。");
            alert.show();
            return false;
        }
//        else if(cBoxCompany.getValue() == null) {
//            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
//            alert.setHeaderText("请选择一个公司。");
//            alert.show();
//            return false;
//        }
        return true;
    }

    /**
     *
     * @param dialog
     * @param callback
     * @param selectedUser
     */
    public void prepare(Stage dialog, Callback<User, String> callback, User selectedUser) {
        this.dialog = dialog;
        this.callback = callback;
        this.selectedUser = selectedUser;
        vBoxShop.getChildren().clear();
        vBoxRole.getChildren().clear();
        btnOk.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
//        cBoxCompany.setStyle("-fx-font-size: 14px;");
        boxEnabled.setSelected(true);
//        initShops()
        initRoles();
    }

//    private void initCompanies() {
//        String json = null;
//        try {
//            json = HttpClient.GET("/companies");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Company[] companies = GoogleJson.GET().fromJson(json, Company[].class);
//        for(Company com : companies) {
//            cBoxCompany.getItems().add(com);
//        }
//        cBoxCompany.valueProperty().addListener((observable, oldValue, newValue) -> {
//            initShops(newValue);
//        });
//
//        //选中复选框
//        if(selectedUser == null) {
//            //默认选中首个公司
//            if(companies.length > 0) {
//                cBoxCompany.setValue(companies[0]);
//            }
//        } else {
//            //选中公司
//            Optional<UserShopMapping> opt = selectedUser.getUserShopMappingSet().stream().findFirst();
//            if(opt.isPresent()) {
//                Long shopId = opt.get().getId().getShopId();
//                String data = null;
//                try {
//                    data = HttpClient.GET("/shops/"+shopId);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                Shop shop = GoogleJson.GET().fromJson(data, Shop.class);
//                cBoxCompany.setValue(shop.getCompany());
//            }
//        }
//    }

//    private void initShops(Company selectedCompany) {
//        vBoxShop.getChildren().clear();
//        String json = null;
//        try {
//            json = HttpClient.GET("/shops/company/"+selectedCompany.getId());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Shop[] shops = GoogleJson.GET().fromJson(json, Shop[].class);
//        for (Shop shop : shops) {
//            CheckBox checkBox = new CheckBox(shop.getName());
//            checkBox.setUserData(shop.getId());
//            vBoxShop.getChildren().add(checkBox);
//        }
//
//        if(selectedUser != null) {
//            vBoxShop.getChildren().forEach(checkbox -> {
//                Long shopId = Long.valueOf(checkbox.getUserData().toString());
//                ((CheckBox)checkbox).setSelected(false);
//                selectedUser.getUserShopMappingSet().forEach(e -> {
//                    if(e.getId().getShopId() == shopId) {
//                        ((CheckBox)checkbox).setSelected(true);
//                    }
//                });
//
//            });
//        }
//
//    }

    private void initRoles() {
        vBoxRole.getChildren().clear();
        String json = null;
        try {
            json = HttpClient.GET("/roles");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Role[] roles = GoogleJson.GET().fromJson(json, Role[].class);
        for (Role role : roles) {
            CheckBox checkBox = new CheckBox(role.getName());
            checkBox.setUserData(role.getId());
            vBoxRole.getChildren().add(checkBox);
        }

        if(selectedUser != null) {
            vBoxRole.getChildren().forEach(checkbox -> {
                Long roleId = Long.valueOf(checkbox.getUserData().toString());
                ((CheckBox)checkbox).setSelected(false);
                selectedUser.getUserRoleMappingSet().forEach(id -> {
                    if(id.getId().getRoleId() == roleId) {
                        ((CheckBox)checkbox).setSelected(true);
                    }
                });

            });
        }
    }
}
