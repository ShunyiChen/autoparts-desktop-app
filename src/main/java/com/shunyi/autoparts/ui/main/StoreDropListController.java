package com.shunyi.autoparts.ui.main;

import com.shunyi.autoparts.ui.common.ENV;
import com.shunyi.autoparts.ui.common.HttpClient;
import com.shunyi.autoparts.ui.common.vo.Shop;
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
public class ShopDropListController {

    @FXML
    private Label shopLabel;

    @FXML
    private ComboBox<Shop> shopComboBox;


    public void prepare() {
        shopLabel.setStyle("-fx-font-size: 14;-fx-text-fill: rgb(255,255,255);");
        shopComboBox.setStyle("-fx-font-size: 14;");

        String userName = (String) ENV.getInstance().lookup("loginUser");
        try {
            User user = HttpClient.GET("/users/username/"+userName, User.class);
            if("root".equals(user.getUsername())) {
                Shop[] userShops = HttpClient.GET("/shops", Shop[].class);
                shopComboBox.getItems().addAll(userShops);
                shopComboBox.getSelectionModel().select(0);

            } else {
                Shop[] userShops = HttpClient.GET("/shops/user/"+user.getId(), Shop[].class);
                if(userShops.length > 0) {
                    shopComboBox.getItems().addAll(userShops);
                    shopComboBox.getSelectionModel().select(0);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
