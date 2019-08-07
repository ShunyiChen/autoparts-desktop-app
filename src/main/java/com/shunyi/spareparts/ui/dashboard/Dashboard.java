package com.shunyi.spareparts.ui.dashboard;

import com.shunyi.spareparts.ui.MainApp;
import com.shunyi.spareparts.ui.navigation.ClickableItem;
import com.shunyi.spareparts.ui.navigation.Navigation;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;

public class Dashboard extends BorderPane {

    private MainApp application;
    private Toolbar toolbar;
    private Navigation navigation;

    public Dashboard(MainApp application) {
        this.application = application;
        initComponents();
    }

    private void initComponents() {
        toolbar = new Toolbar(application);
        navigation = new Navigation(application);

        String URL = "/img/dashboard.png";
        Image ICON_1 = new Image(getClass().getResourceAsStream(URL));
        Image ICON_2 = new Image(getClass().getResourceAsStream(URL));
        ClickableItem item1 = new ClickableItem(ICON_1, "仪表盘");
        ClickableItem item2 = new ClickableItem(ICON_2, "进货单管理");
        navigation.addClickableItem(item1);
        navigation.addClickableItem(item2);

        this.setTop(toolbar);
        this.setLeft(navigation);
    }

    public Navigation getNavigation() {
        return navigation;
    }
}
