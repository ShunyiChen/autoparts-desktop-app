package com.shunyi.autoparts.ui.main;

import com.shunyi.autoparts.ui.MainApp;
import com.shunyi.autoparts.ui.common.Constants;
import com.shunyi.autoparts.ui.common.ICONS;
import com.shunyi.autoparts.ui.inventory.InventoryDetails;
import com.shunyi.autoparts.ui.products.ProductDetails;
import com.shunyi.autoparts.ui.purchase.PurchaseDetails;
import com.shunyi.autoparts.ui.purchase.PurchaseReturnDetails;
import com.shunyi.autoparts.ui.sale.SalesDetails;
import com.shunyi.autoparts.ui.sale.SalesReturnDetails;
import com.shunyi.autoparts.ui.sale.SalesView;
import com.shunyi.autoparts.ui.supplier.SupplierDetails;
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
    private NewTab purchaseDetailsTab;
    private NewTab purchaseReturnDetailsTab;
    private NewTab salesDetailsTab;
    private NewTab salesReturnDetailsTab;
    private NewTab supplierDetailsTab;
    private NewTab inventoryDetailsTab;
    private NewTab productDetailsTab;
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
//        dashboard = new Dashboard(application);
        this.setTop(toolbar);
        this.setLeft(navigation);
        this.setCenter(contentPane);
        initEvents();

    }

    private void initEvents() {
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
        ClickableItem purchaseDetails = new ClickableItem(icons.cart(), Constants.PURCHASE_DETAILS, new Callback() {
            @Override
            public Object call(Object param) {
                toolbar.setTitle(Constants.PURCHASE_DETAILS);
                if(purchaseDetailsTab == null) {
                    purchaseDetailsTab = new NewTab(Constants.PURCHASE_DETAILS, new PurchaseDetails(application));
                }
                contentPane.addNewTab(purchaseDetailsTab);
                return null;
            }
        });
        ClickableItem purchaseReturnDetails = new ClickableItem(icons.cart_arrow_up(), Constants.PURCHASE_RETURN_DETAILS, new Callback() {
            @Override
            public Object call(Object param) {
                toolbar.setTitle(Constants.PURCHASE_RETURN_DETAILS);
                if(purchaseReturnDetailsTab == null) {
                    purchaseReturnDetailsTab = new NewTab(Constants.PURCHASE_RETURN_DETAILS, new PurchaseReturnDetails());
                }
                contentPane.addNewTab(purchaseReturnDetailsTab);
                return null;
            }
        });
        ClickableItem salesDetails = new ClickableItem(icons.shopping(), Constants.SALES_DETAILS, new Callback() {
            @Override
            public Object call(Object param) {
                toolbar.setTitle(Constants.SALES_DETAILS);
                if(salesDetailsTab == null) {
                    salesDetailsTab = new NewTab(Constants.SALES_DETAILS, new SalesDetails());
                }
                contentPane.addNewTab(salesDetailsTab);
                return null;
            }
        });
        ClickableItem salesReturnDetails = new ClickableItem(icons.shopping_outline(), Constants.SALES_RETURN_DETAILS, new Callback() {
            @Override
            public Object call(Object param) {
                toolbar.setTitle(Constants.SALES_RETURN_DETAILS);
                if(salesReturnDetailsTab == null) {
                    salesReturnDetailsTab = new NewTab(Constants.SALES_RETURN_DETAILS, new SalesReturnDetails());
                }
                contentPane.addNewTab(salesReturnDetailsTab);
                return null;
            }
        });
        ClickableItem supplierDetails = new ClickableItem(icons.account_multiple(), Constants.SUPPLIER_DETAILS, new Callback() {
            @Override
            public Object call(Object param) {
                toolbar.setTitle(Constants.SUPPLIER_DETAILS);
                if(supplierDetailsTab == null) {
                    supplierDetailsTab = new NewTab(Constants.SUPPLIER_DETAILS, new SupplierDetails(application));
                }
                contentPane.addNewTab(supplierDetailsTab);
                return null;
            }
        });
        ClickableItem inventoryDetails = new ClickableItem(icons.warehouse(), Constants.INVENTORY_DETAILS, new Callback() {
            @Override
            public Object call(Object param) {
                toolbar.setTitle(Constants.INVENTORY_DETAILS);
                if(inventoryDetailsTab == null) {
                    inventoryDetailsTab = new NewTab(Constants.INVENTORY_DETAILS, new InventoryDetails());
                }
                contentPane.addNewTab(inventoryDetailsTab);
                return null;
            }
        });
        ClickableItem productDetails = new ClickableItem(icons.car_door(), Constants.PRODUCT_DETAILS, new Callback() {
            @Override
            public Object call(Object param) {
                toolbar.setTitle(Constants.PRODUCT_DETAILS);
                if(productDetailsTab == null) {
                    productDetailsTab = new NewTab(Constants.PRODUCT_DETAILS, new ProductDetails(application));
                }
                contentPane.addNewTab(productDetailsTab);
                return null;
            }
        });

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

        navigation.addClickableItem(salesView, purchaseDetails, purchaseReturnDetails, salesDetails, salesReturnDetails, supplierDetails, inventoryDetails, productDetails, systemSettings);
    }

    public Navigation getNavigation() {
        return navigation;
    }
}
