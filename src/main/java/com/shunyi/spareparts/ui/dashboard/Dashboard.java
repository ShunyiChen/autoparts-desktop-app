package com.shunyi.spareparts.ui.dashboard;

import com.shunyi.spareparts.ui.MainApp;
import com.shunyi.spareparts.ui.navigation.ClickableItem;
import com.shunyi.spareparts.ui.navigation.Navigation;
import com.shunyi.spareparts.ui.purchase.PurchaseOrderForm;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;


public class Dashboard extends BorderPane {
    private MainApp application;
    private Toolbar toolbar;
    private Navigation navigation;
    private ContentPane contentPane;

    private PurchaseOrderForm form = new PurchaseOrderForm();

    /**
     * Constructor
     *
     * @param application
     */
    public Dashboard(MainApp application) {
        this.application = application;
        initComponents();
    }

    private void initComponents() {
        toolbar = new Toolbar(application);
        navigation = new Navigation(application);
        contentPane = new ContentPane();

        String URL = "/img/dashboard.png";
        Image ICON_1 = new Image(getClass().getResourceAsStream(URL));
        Image ICON_2 = new Image(getClass().getResourceAsStream(URL));


        ClickableItem item1 = new ClickableItem(ICON_1, "仪表盘", new Callback() {
            @Override
            public Object call(Object param) {

                contentPane.getViewport().setCenter(form);
                return null;
            }
        });

        ClickableItem item2 = new ClickableItem(ICON_2, "进货单管理", new Callback() {
            @Override
            public Object call(Object param) {
                contentPane.getViewport().setCenter(new Pane());
                return null;
            }
        });

        ClickableItem item3 = new ClickableItem(ICON_2, "进货单管理", new Callback() {
            @Override
            public Object call(Object param) {
                contentPane.getViewport().setCenter(new Pane());
                return null;
            }
        });

        navigation.addClickableItem(item1);
        navigation.addClickableItem(item2);
        navigation.addClickableItem(item3);

        this.setTop(toolbar);
        this.setLeft(navigation);
        this.setCenter(contentPane);
    }

    public Navigation getNavigation() {
        return navigation;
    }
}
