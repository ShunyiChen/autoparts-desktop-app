package com.shunyi.autoparts.ui.main;

import com.shunyi.autoparts.ui.common.Env;
import com.shunyi.autoparts.ui.common.HttpClient;
import com.shunyi.autoparts.ui.common.vo.Store;
import com.shunyi.autoparts.ui.common.vo.User;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.io.IOException;

/**
 * @Description: 门店下拉框controller
 *
 * @Author: 陈顺谊
 * @CreateDate: 2020/2/6 10:29
 * @Version: 1.0
 */
public class StoreDropListController {
    @FXML
    private Label shopLabel;
    @FXML
    private ComboBox<Store> shopComboBox;

    public void prepare() {
        shopLabel.setStyle("-fx-font-size: 14;-fx-text-fill: rgb(255,255,255);");
        shopComboBox.setStyle("-fx-font-size: 14;");

        String userName = (String) Env.getInstance().lookup("loginUser");
        try {
            User user = HttpClient.GET("/users/username/"+userName, User.class);
            if("root".equals(user.getUsername())) {
                Store[] userStores = HttpClient.GET("/stores", Store[].class);
                shopComboBox.getItems().addAll(userStores);
                shopComboBox.getSelectionModel().select(0);

            } else {
                Store[] userStores = HttpClient.GET("/stores/user/"+user.getId(), Store[].class);
                if(userStores.length > 0) {
                    shopComboBox.getItems().addAll(userStores);
                    shopComboBox.getSelectionModel().select(0);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
