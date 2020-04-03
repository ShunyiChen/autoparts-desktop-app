package com.shunyi.autoparts.ui.main;

import com.shunyi.autoparts.ui.common.Constants;
import com.shunyi.autoparts.ui.common.Env;
import com.shunyi.autoparts.ui.common.HttpClient;
import com.shunyi.autoparts.ui.common.vo.Store;
import com.shunyi.autoparts.ui.common.vo.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

/**
 * @Description: 门店下拉框controller
 *
 * @Author: 陈顺谊
 * @CreateDate: 2020/2/6 10:29
 * @Version: 1.0
 */
public class StoreDropListController {

    private Set<TabContent> affectedTabContentSet;
    @FXML
    private Label storeLabel;
    @FXML
    private ComboBox<Store> storeComboBox;


    public void prepare(Set<TabContent> affectedTabContentSet) {
        this.affectedTabContentSet = affectedTabContentSet;
        storeLabel.setStyle("-fx-font-size: 14;-fx-text-fill: rgb(255,255,255);");
        storeComboBox.setStyle("-fx-font-size: 14;");
        String userName = Env.getInstance().getStringValue(Env.CURRENT_USER);
        try {
            User user = HttpClient.GET("/users/username/"+userName, User.class);
            if(Constants.ROOT.equals(user.getUsername())) {
                Store[] userStores = HttpClient.GET("/stores", Store[].class);
                storeComboBox.getItems().addAll(userStores);
                storeComboBox.getSelectionModel().select(0);
                Env.getInstance().put(Env.CURRENT_STORE, storeComboBox.getValue());
                Env.getInstance().put(Env.STORES, userStores);

            } else {
                Store[] userStores = HttpClient.GET("/stores/user/"+user.getId(), Store[].class);
                if(userStores.length > 0) {
                    storeComboBox.getItems().addAll(userStores);
                    storeComboBox.getSelectionModel().select(0);
                    Env.getInstance().put(Env.CURRENT_STORE, storeComboBox.getValue());
                    Env.getInstance().put(Env.STORES, userStores);

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        storeComboBox.getSelectionModel().selectedItemProperty().addListener(
            (ChangeListener<Store>) (observable, oldValue, newValue) -> {
                Env.getInstance().put(Env.CURRENT_STORE, storeComboBox.getValue());
                affectedTabContentSet.forEach(e -> {
                    e.reload();
                });
            });
    }
}
