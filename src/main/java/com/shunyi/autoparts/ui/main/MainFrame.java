package com.shunyi.autoparts.ui.main;

import com.shunyi.autoparts.ui.MainApp;
import com.shunyi.autoparts.ui.adjustment.PAO;
import com.shunyi.autoparts.ui.common.Constants;
import com.shunyi.autoparts.ui.common.ICONS;
import com.shunyi.autoparts.ui.inventory.IA;
import com.shunyi.autoparts.ui.maintenance.Maintenance;
import com.shunyi.autoparts.ui.purchase.PO;
import com.shunyi.autoparts.ui.purchase.PRO;
import com.shunyi.autoparts.ui.purchase.PurchaseView;
import com.shunyi.autoparts.ui.sales.SO;
import com.shunyi.autoparts.ui.sales.SRO;
import com.shunyi.autoparts.ui.sales.SalesView;
import com.shunyi.autoparts.ui.stocktaking.STO;
import com.shunyi.autoparts.ui.system.SystemSettings;
import javafx.application.Platform;
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
    /** 销售视图Tab */
    private TabExt salesViewTab;
    /** 采购视图Tab */
    private TabExt purchaseViewTab;
    /** 采购单Tab */
    private TabExt purchaseOrderTab;
    /** 采购退货单Tab */
    private TabExt purchaseReturnOrderTab;
    /** 销售单Tab */
    private TabExt salesOrderTab;
    /** 销售退货单Tab */
    private TabExt salesReturnOrderTab;
    /** 盘点单Tab */
    private TabExt stocktakingTab;
    /** 调价单Tab */
    private TabExt priceAdjustmentOrderTab;
    /** 库存配件Tab */
    private TabExt inventoryAccessoriesTab;
    /** 数据维护Tab */
    private TabExt dataMaintainingTab;
    /** 系统设置Tab */
    private TabExt systemSettingsTab;

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

    /**
     * 初始化左侧Items
     */
    private void initEvents() {
        //采购视图
        ClickableItem purchaseView = new ClickableItem(icons.view_dashboard(), Constants.PURCHASE_VIEW, new Callback() {
            @Override
            public Object call(Object param) {
                toolbar.setTitle(Constants.PURCHASE_VIEW);
                if(purchaseViewTab == null) {
                    purchaseViewTab = new TabExt(new PurchaseView(application));
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
                    salesViewTab = new TabExt(new SalesView(application));
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
                    purchaseOrderTab = new TabExt(new PO(application));
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
                    purchaseReturnOrderTab = new TabExt(new PRO(application));
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
                    salesOrderTab = new TabExt(new SO(application));
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
                    salesReturnOrderTab = new TabExt(new SRO(application));
                }
                contentPane.addNewTab(salesReturnOrderTab);
                return null;
            }
        });
        //调价单
        ClickableItem priceAdjustmentOrder = new ClickableItem(icons.warehouse(), Constants.PRICE_ADJUSTMENT_ORDER, new Callback() {
            @Override
            public Object call(Object param) {
                toolbar.setTitle(Constants.PRICE_ADJUSTMENT_ORDER);
                if(priceAdjustmentOrderTab == null) {
                    priceAdjustmentOrderTab = new TabExt(new PAO(application));
                }
                contentPane.addNewTab(priceAdjustmentOrderTab);
                return null;
            }
        });
        //盘点单
        ClickableItem inventoryingOrder = new ClickableItem(icons.account_multiple(), Constants.STOCKTAKING_ORDER, new Callback() {
            @Override
            public Object call(Object param) {
                toolbar.setTitle(Constants.STOCKTAKING_ORDER);
                if(stocktakingTab == null) {
                    stocktakingTab = new TabExt(new STO(application));
                }
                contentPane.addNewTab(stocktakingTab);
                return null;
            }
        });
        //库存配件
        ClickableItem inventoryDetails = new ClickableItem(icons.car_door(), Constants.INVENTORY_ACCESSORIES, new Callback() {
            @Override
            public Object call(Object param) {
                toolbar.setTitle(Constants.INVENTORY_ACCESSORIES);
                if(inventoryAccessoriesTab == null) {
                    inventoryAccessoriesTab = new TabExt(new IA(application));
                }
                contentPane.addNewTab(inventoryAccessoriesTab);
                return null;
            }
        });
        //数据维护
        ClickableItem dataMaintaining = new ClickableItem(icons.car_door(), Constants.DATA_MAINTENANCE, new Callback() {
            @Override
            public Object call(Object param) {
                toolbar.setTitle(Constants.DATA_MAINTENANCE);
                if(dataMaintainingTab == null) {
                    dataMaintainingTab = new TabExt(new Maintenance(application));
                }
                contentPane.addNewTab(dataMaintainingTab);
                return null;
            }
        });

        // 系统设置
        ClickableItem systemSettings = new ClickableItem(icons.settings_transfer_outline(), Constants.SYSTEM_SETTINGS, new Callback() {
            @Override
            public Object call(Object param) {
                toolbar.setTitle(Constants.SYSTEM_SETTINGS);
                if(systemSettingsTab == null) {
                    systemSettingsTab = new TabExt(new SystemSettings(application));
                }
                contentPane.addNewTab(systemSettingsTab);
                return null;
            }
        });
        navigation.addClickableItem(purchaseView, salesView, purchaseOrder, purchaseReturnOrder, salesOrder, salesReturnOrder, priceAdjustmentOrder, inventoryingOrder, inventoryDetails, dataMaintaining, systemSettings);

        Platform.runLater(() -> purchaseView.fire());
    }

    public Navigation getNavigation() {
        return navigation;
    }
}
