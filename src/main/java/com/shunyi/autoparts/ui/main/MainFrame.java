package com.shunyi.autoparts.ui.main;

import com.shunyi.autoparts.ui.MainApp;
import com.shunyi.autoparts.ui.dashboard.Dashboard;
import com.shunyi.autoparts.ui.products.ProductManagement;
import com.shunyi.autoparts.ui.buy.PurchaseOrder;
import com.shunyi.autoparts.ui.stock.StockManagement;
import com.shunyi.autoparts.ui.supplier.SupplierManagement;
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
        dashboard = new Dashboard(application);
        purchaseContainer = new PurchaseOrder(application);

        this.setTop(toolbar);
        this.setLeft(navigation);
        this.setCenter(contentPane);
        initEvents();
    }

    private void initEvents() {
        ClickableItem item1 = new ClickableItem(ICON_1, "进销存统计", new Callback() {
            @Override
            public Object call(Object param) {
                toolbar.setTitle("仪表盘");
                contentPane.getViewport().setCenter(dashboard);
                return null;
            }
        });
        ClickableItem item2 = new ClickableItem(ICON_2, "采购单", new Callback() {
            @Override
            public Object call(Object param) {
                toolbar.setTitle("采购单");
                contentPane.getViewport().setCenter((PurchaseOrder)purchaseContainer);
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
        ClickableItem item6 = new ClickableItem(ICON_5, "供应商管理", new Callback() {
            @Override
            public Object call(Object param) {
                SupplierManagement supplierManagement = new SupplierManagement(application);
                toolbar.setTitle("供应商管理");
                contentPane.getViewport().setCenter(supplierManagement);
                return null;
            }
        });
        ClickableItem item7 = new ClickableItem(ICON_5, "库存管理", new Callback() {
            @Override
            public Object call(Object param) {
                StockManagement stockManagement = new StockManagement(application);
                toolbar.setTitle("库存管理");
                contentPane.getViewport().setCenter(stockManagement);
                return null;
            }
        });
        ClickableItem item8 = new ClickableItem(ICON_5, "配件管理", new Callback() {
            @Override
            public Object call(Object param) {
                ProductManagement ProductManagement = new ProductManagement(application);
                toolbar.setTitle("配件管理");
                contentPane.getViewport().setCenter(ProductManagement);
                return null;
            }
        });

        ClickableItem item11 = new ClickableItem(ICON_5, "系统维护", new Callback() {
            @Override
            public Object call(Object param) {
                toolbar.setTitle("系统维护");
                contentPane.getViewport().setCenter(new Pane());
                return null;
            }
        });

        navigation.addClickableItem(item1,item2,item3,item4,item5,item6,item7,item8,item11);
    }

    public BaseContainer getCurrentContainer() {
        return currentContainer;
    }

    public Navigation getNavigation() {
        return navigation;
    }
}
