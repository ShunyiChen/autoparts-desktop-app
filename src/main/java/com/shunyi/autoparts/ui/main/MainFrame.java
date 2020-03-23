package com.shunyi.autoparts.ui.main;

import com.shunyi.autoparts.ui.MainApp;
import com.shunyi.autoparts.ui.common.ICONS;
import com.shunyi.autoparts.ui.dashboard.Dashboard;
import com.shunyi.autoparts.ui.products.ProductManagement;
import com.shunyi.autoparts.ui.buy.PurchaseOrder;
import com.shunyi.autoparts.ui.stock.StockManagement;
import com.shunyi.autoparts.ui.supplier.SupplierManagement;
import com.shunyi.autoparts.ui.system.Maintenance;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

/**
 * 主界面框架
 */
public class MainFrame extends BorderPane {
    private MainApp application;
    private TitleBar toolbar;
    private Navigation navigation;
    private ContentPane contentPane;
    private BaseContainer currentContainer;
    private PurchaseOrder purchaseContainer;
    private Dashboard dashboard;
    private ICONS icons = ICONS.getInstance();


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
//        purchaseContainer = new PurchaseOrder(application);

        this.setTop(toolbar);
        this.setLeft(navigation);
        this.setCenter(contentPane);
        initEvents();
    }

    private void initEvents() {
        ClickableItem dashboard = new ClickableItem(icons.view_dashboard(),"主界面", new Callback() {
            @Override
            public Object call(Object param) {
                toolbar.setTitle("主界面");
                contentPane.getViewport().setCenter(new Pane());
                return null;
            }
        });
        ClickableItem purchase = new ClickableItem(icons.cart(), "购货单", new Callback() {
            @Override
            public Object call(Object param) {
                toolbar.setTitle("购货单");
//                contentPane.getViewport().setCenter((PurchaseOrder)purchaseContainer);
                return null;
            }
        });
        ClickableItem refund = new ClickableItem(icons.cart_arrow_up(), "购货退货单", new Callback() {
            @Override
            public Object call(Object param) {
                toolbar.setTitle("购货退货单");
//                contentPane.getViewport().setCenter(new Pane());
                return null;
            }
        });
        ClickableItem item4 = new ClickableItem(icons.shopping(), "销售单", new Callback() {
            @Override
            public Object call(Object param) {
                toolbar.setTitle("销售单");
                contentPane.getViewport().setCenter(new Pane());
                return null;
            }
        });
        ClickableItem item5 = new ClickableItem(icons.shopping_outline(), "销售退回单", new Callback() {
            @Override
            public Object call(Object param) {
                toolbar.setTitle("销售退回单");
                contentPane.getViewport().setCenter(new Pane());
                return null;
            }
        });
        ClickableItem item6 = new ClickableItem(icons.account_multiple(), "供应商管理", new Callback() {
            @Override
            public Object call(Object param) {
                SupplierManagement supplierManagement = new SupplierManagement(application);
                toolbar.setTitle("供应商管理");
                contentPane.getViewport().setCenter(new Pane());
//                contentPane.getViewport().setCenter(supplierManagement);
                return null;
            }
        });
        ClickableItem item7 = new ClickableItem(icons.warehouse(), "库存管理", new Callback() {
            @Override
            public Object call(Object param) {
                StockManagement stockManagement = new StockManagement(application);
                toolbar.setTitle("库存管理");
//                contentPane.getViewport().setCenter(stockManagement);
                return null;
            }
        });
        ClickableItem item8 = new ClickableItem(icons.car_door(), "配件管理", new Callback() {
            @Override
            public Object call(Object param) {
//                ProductManagement ProductManagement = new ProductManagement(application);
                toolbar.setTitle("配件管理");
                contentPane.getViewport().setCenter(new Pane());
                return null;
            }
        });

        ClickableItem settings = new ClickableItem(icons.settings_transfer_outline(), "系统维护", new Callback() {
            @Override
            public Object call(Object param) {
//                Maintenance systemMaintenance = new Maintenance(application);
                toolbar.setTitle("系统维护");
                contentPane.getViewport().setCenter(new Pane());
                return null;
            }
        });

        navigation.addClickableItem(dashboard, purchase, refund, item4, item5, item6, item7, item8, settings);
    }

    public BaseContainer getCurrentContainer() {
        return currentContainer;
    }

    public Navigation getNavigation() {
        return navigation;
    }
}
