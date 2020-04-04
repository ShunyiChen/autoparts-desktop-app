package com.shunyi.autoparts.ui.main;

import com.shunyi.autoparts.ui.MainApp;
import com.shunyi.autoparts.ui.common.Constants;
import com.shunyi.autoparts.ui.common.ICONS;
import com.shunyi.autoparts.ui.inventory.InventoryDetails;
import com.shunyi.autoparts.ui.inventory.InventoryingOrder;
import com.shunyi.autoparts.ui.inventory.PriceAdjustmentOrder;
import com.shunyi.autoparts.ui.purchase.PO;
import com.shunyi.autoparts.ui.purchase.PurchaseReturnOrder;
import com.shunyi.autoparts.ui.purchase.PurchaseView;
import com.shunyi.autoparts.ui.sale.SalesOrder;
import com.shunyi.autoparts.ui.sale.SalesReturnOrder;
import com.shunyi.autoparts.ui.sale.SalesView;
import com.shunyi.autoparts.ui.system.SystemSettings;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

/**
 * 主界面框架
 */
public class MainFrame extends BorderPane {
    private MainApp application;
    private TitleBar toolbar;
    private Navigation navigation;
    private ContentPane contentPane;
    private ICONS icons = ICONS.getInstance();
    private TabPane tabPane = new TabPane();
    private NewTab salesViewTab;
    private NewTab purchaseViewTab;
    private NewTab purchaseOrderTab;
    private NewTab purchaseReturnOrderTab;
    private NewTab salesOrderTab;
    private NewTab salesReturnOrderTab;
    private NewTab inventoryingOrderTab;
    private NewTab priceAdjustmentOrderTab;
    private NewTab inventoryDetailsTab;

    private NewTab systemSettingsTab;

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
        contentPane = new ContentPane(tabPane);
        this.setTop(toolbar);
        this.setLeft(navigation);
        this.setCenter(contentPane);
        initEvents();

    }

    private void initEvents() {
        //采购视图
        ClickableItem purchaseView = new ClickableItem(icons.view_dashboard(), Constants.PURCHASE_VIEW, new Callback() {
            @Override
            public Object call(Object param) {
                toolbar.setTitle(Constants.PURCHASE_VIEW);
                if(purchaseViewTab == null) {
                    purchaseViewTab = new NewTab(Constants.PURCHASE_VIEW, new PurchaseView(application));
                }
                contentPane.addNewTab(purchaseViewTab);
                return null;
            }
        });
        //销售视图
        ClickableItem salesView = new ClickableItem(icons.view_dashboard(), Constants.SALES_VIEW, new Callback() {
            @Override
            public Object call(Object param) {
                toolbar.setTitle(Constants.SALES_VIEW);
                if(salesViewTab == null) {
                    salesViewTab = new NewTab(Constants.SALES_VIEW, new SalesView(application));
                }
                contentPane.addNewTab(salesViewTab);
                return null;
            }
        });
        //采购单
        ClickableItem purchaseOrder = new ClickableItem(icons.cart(), Constants.PURCHASE_ORDER, new Callback() {
            @Override
            public Object call(Object param) {
                toolbar.setTitle(Constants.PURCHASE_ORDER);
                if(purchaseOrderTab == null) {
                    purchaseOrderTab = new NewTab(Constants.PURCHASE_ORDER, new PO(application));
                }
                contentPane.addNewTab(purchaseOrderTab);
                return null;
            }
        });
        //采购退货单
        ClickableItem purchaseReturnOrder = new ClickableItem(icons.cart_arrow_up(), Constants.PURCHASE_RETURN_ORDER, new Callback() {
            @Override
            public Object call(Object param) {
                toolbar.setTitle(Constants.PURCHASE_RETURN_ORDER);
                if(purchaseReturnOrderTab == null) {
                    purchaseReturnOrderTab = new NewTab(Constants.PURCHASE_RETURN_ORDER, new PurchaseReturnOrder());
                }
                contentPane.addNewTab(purchaseReturnOrderTab);
                return null;
            }
        });
        //销售单
        ClickableItem salesOrder = new ClickableItem(icons.shopping(), Constants.SALES_ORDER, new Callback() {
            @Override
            public Object call(Object param) {
                toolbar.setTitle(Constants.SALES_ORDER);
                if(salesOrderTab == null) {
                    salesOrderTab = new NewTab(Constants.SALES_ORDER, new SalesOrder());
                }
                contentPane.addNewTab(salesOrderTab);
                return null;
            }
        });
        //销售退货单
        ClickableItem salesReturnOrder = new ClickableItem(icons.shopping_outline(), Constants.SALES_RETURN_ORDER, new Callback() {
            @Override
            public Object call(Object param) {
                toolbar.setTitle(Constants.SALES_RETURN_ORDER);
                if(salesReturnOrderTab == null) {
                    salesReturnOrderTab = new NewTab(Constants.SALES_RETURN_ORDER, new SalesReturnOrder());
                }
                contentPane.addNewTab(salesReturnOrderTab);
                return null;
            }
        });
        //盘点单
        ClickableItem inventoryingOrder = new ClickableItem(icons.account_multiple(), Constants.INVENTORYING_ORDER, new Callback() {
            @Override
            public Object call(Object param) {
                toolbar.setTitle(Constants.INVENTORYING_ORDER);
                if(inventoryingOrderTab == null) {
                    inventoryingOrderTab = new NewTab(Constants.INVENTORYING_ORDER, new InventoryingOrder(application));
                }
                contentPane.addNewTab(inventoryingOrderTab);
                return null;
            }
        });
        //调价单
        ClickableItem priceAdjustmentOrder = new ClickableItem(icons.warehouse(), Constants.PRICE_ADJUSTMENT_ORDER, new Callback() {
            @Override
            public Object call(Object param) {
                toolbar.setTitle(Constants.PRICE_ADJUSTMENT_ORDER);
                if(priceAdjustmentOrderTab == null) {
                    priceAdjustmentOrderTab = new NewTab(Constants.PRICE_ADJUSTMENT_ORDER, new PriceAdjustmentOrder(application));
                }
                contentPane.addNewTab(priceAdjustmentOrderTab);
                return null;
            }
        });
        //库存明细
        ClickableItem inventoryDetails = new ClickableItem(icons.car_door(), Constants.INVENTORY_DETAILS, new Callback() {
            @Override
            public Object call(Object param) {
                toolbar.setTitle(Constants.INVENTORY_DETAILS);
                if(inventoryDetailsTab == null) {
                    inventoryDetailsTab = new NewTab(Constants.INVENTORY_DETAILS, new InventoryDetails(application));
//                    toolbar.addAffectedTabContent(productDetails);
                }
                contentPane.addNewTab(inventoryDetailsTab);
                return null;
            }
        });
        // 系统设置
        ClickableItem systemSettings = new ClickableItem(icons.settings_transfer_outline(), Constants.SYSTEM_SETTINGS, new Callback() {
            @Override
            public Object call(Object param) {
                toolbar.setTitle(Constants.SYSTEM_SETTINGS);
                if(systemSettingsTab == null) {
                    systemSettingsTab = new NewTab(Constants.SYSTEM_SETTINGS, new SystemSettings(application));
                }
                contentPane.addNewTab(systemSettingsTab);
                return null;
            }
        });
        navigation.addClickableItem(purchaseView, salesView, purchaseOrder, purchaseReturnOrder, salesOrder, salesReturnOrder, inventoryingOrder, priceAdjustmentOrder, inventoryDetails, systemSettings);
    }

    public Navigation getNavigation() {
        return navigation;
    }
}
