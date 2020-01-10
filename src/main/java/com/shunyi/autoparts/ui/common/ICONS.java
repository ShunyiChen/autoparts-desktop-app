package com.shunyi.autoparts.ui.common;

import javafx.scene.image.Image;

/**
 * @Description: 图标公共类
 * @Author: 陈顺谊
 * @CreateDate: 2020/1/8 16:24
 * @Version: 1.0
 */
public class ICONS {

    //create an object of SingleObject
    private static ICONS instance = new ICONS();

    //make the constructor private so that this class cannot be
    //instantiated
    private ICONS(){}

    //Get the only object available
    public static ICONS getInstance(){
        return instance;
    }

    public Image cart(){
        return new Image(getClass().getResourceAsStream("/img/clickable_items/cart.png"));
    }

    public Image view_dashboard(){
        return new Image(getClass().getResourceAsStream("/img/clickable_items/view-dashboard.png"));
    }

    public Image account_multiple(){
        return new Image(getClass().getResourceAsStream("/img/clickable_items/account-multiple.png"));
    }

    public Image file_chart(){
        return new Image(getClass().getResourceAsStream("/img/clickable_items/file-chart.png"));
    }

    public Image cart_arrow_up(){
        return new Image(getClass().getResourceAsStream("/img/clickable_items/cart-arrow-up.png"));
    }

    public Image shopping(){
        return new Image(getClass().getResourceAsStream("/img/clickable_items/shopping.png"));
    }

    public Image shopping_outline(){
        return new Image(getClass().getResourceAsStream("/img/clickable_items/shopping-outline.png"));
    }

    public Image car_door(){
        return new Image(getClass().getResourceAsStream("/img/clickable_items/car-door.png"));
    }

    public Image settings_transfer_outline(){
        return new Image(getClass().getResourceAsStream("/img/clickable_items/settings-transfer-outline.png"));
    }

    public Image warehouse(){
        return new Image(getClass().getResourceAsStream("/img/clickable_items/warehouse.png"));
    }


    public Image countdown_alex(){
        return new Image(getClass().getResourceAsStream("/img/login/countdown-alex.png"));
    }

    public Image account_circle(){
        return new Image(getClass().getResourceAsStream("/img/login/account-circle.png"));
    }
}
