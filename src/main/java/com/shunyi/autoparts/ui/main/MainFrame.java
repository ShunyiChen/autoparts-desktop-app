package com.shunyi.autoparts.ui.main;

import com.shunyi.autoparts.ui.MainApp;
import com.shunyi.autoparts.ui.dashboard.Dashboard;
import com.shunyi.autoparts.ui.purchase.PurchaseOrder;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;


public class MainFrame extends BorderPane {
    private MainApp application;
    private TitleBar toolbar;
    private Navigation navigation;
    private ContentPane contentPane;
    private BaseContainer currentContainer;
    private PurchaseOrder purchaseContainer;
    private Dashboard dashboard;
    private Image ICON_1 = new Image(getClass().getResourceAsStream("/img/grid.png"));
    private Image ICON_2 = new Image(getClass().getResourceAsStream("/img/purchase.png"));
    private Image ICON_3 = new Image(getClass().getResourceAsStream("/img/return.png"));
    private Image ICON_4 = new Image(getClass().getResourceAsStream("/img/stats.png"));
    private Image ICON_5 = new Image(getClass().getResourceAsStream("/img/return.png"));

    /**
     * Constructor
     *
     * @param application
     */
    public MainFrame(MainApp application) {
        this.application = application;
        initComponents();
    }

    private void initComponents() {
        toolbar = new TitleBar(application);
        navigation = new Navigation(application);
        contentPane = new ContentPane();
        contentPane.getViewport().setCenter(new Label("无显示内容"));
        purchaseContainer = new PurchaseOrder(application);
        dashboard = new Dashboard(application);
        this.setTop(toolbar);
        this.setLeft(navigation);
        this.setCenter(contentPane);
        initEvents();
    }

    private void initEvents() {
        ClickableItem item1 = new ClickableItem(ICON_1, "仪表盘", new Callback() {
            @Override
            public Object call(Object param) {
                toolbar.setTitle("仪表盘");
                contentPane.getViewport().setCenter(dashboard);
                return null;
            }
        });
        ClickableItem item2 = new ClickableItem(ICON_2, "进货单", new Callback() {
            @Override
            public Object call(Object param) {
                toolbar.setTitle("进货单");
//                contentPane.getViewport().setCenter(new BorderPane());
                contentPane.getViewport().setCenter((PurchaseOrder)purchaseContainer);
//                currentContainer = purchaseContainer;
                return null;
            }
        });
        ClickableItem item3 = new ClickableItem(ICON_3, "采购退货单", new Callback() {
            @Override
            public Object call(Object param) {
                toolbar.setTitle("采购退货单");
                contentPane.getViewport().setCenter(new Pane());
                return null;
            }
        });
        ClickableItem item4 = new ClickableItem(ICON_4, "销售单", new Callback() {
            @Override
            public Object call(Object param) {
                toolbar.setTitle("销售单");
                contentPane.getViewport().setCenter(new Pane());
                return null;
            }
        });
        ClickableItem item5 = new ClickableItem(ICON_5, "销售退回单", new Callback() {
            @Override
            public Object call(Object param) {
                toolbar.setTitle("销售退回单");
                contentPane.getViewport().setCenter(new Pane());
                return null;
            }
        });
        navigation.addClickableItem(item1,item2,item3,item4,item5);
    }

    public BaseContainer getCurrentContainer() {
        return currentContainer;
    }

    public Navigation getNavigation() {
        return navigation;
    }
}
