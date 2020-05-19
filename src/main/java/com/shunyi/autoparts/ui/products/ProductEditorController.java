package com.shunyi.autoparts.ui.products;

import com.shunyi.autoparts.ui.common.*;
import com.shunyi.autoparts.ui.common.vo.*;
import com.shunyi.autoparts.ui.supplier.SupplierChooserController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description 产品编辑器Controller
 * @author Shunyi Chen
 * @date 2020/4/13
 */
public class ProductEditorController {
    /** 窗体 */
    private Stage dialog;
    /** SKU callback */
    private Callback<SKU, String> callback;
    /** 更新的配件 */
    private SKU sku;
    /** 单位 */
    private Unit unit;
    /** 分类 */
    private Category category;
    /** 车型 */
    private Car car;
    /** 产地 */
    private Place place;
    /** 进口 */
    private Import imported;
    /** 通用车型 */
    private String commonCars;
    /** 品牌 */
    private Brand brand;
    /** 所属公司 */
    private Company company;
    /** 供应商 */
    private Supplier supplier;
    @FXML
    private FlowPane buttonPanel;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnContinueToAdd;
    @FXML
    private Button btnSaveAndQuit;
    @FXML
    private TextField txtCode;
    @FXML
    private ComboBox<String> comboBoxUnit;
    @FXML
    private TextField txtName;
    @FXML
    private ComboBox<String> comboBoxCategory;
    @FXML
    private TextField txtBarCode;
    @FXML
    private ComboBox<String> comboBoxCar;
    @FXML
    private ComboBox<String> comboBoxPlace;
    @FXML
    private ComboBox<String> comboBoxCommonCar;
    @FXML
    private ComboBox<String> comboBoxBrand;
    @FXML
    private TextField txtEnglishName;
    @FXML
    private ComboBox<String> comboBoxImport;
    @FXML
    private TextField txtCommonNumber;
    @FXML
    private TextField txtMaterials;
    @FXML
    private ComboBox<String> comboBoxCompany;
    @FXML
    private TextField txtWeight;
    @FXML
    private TextField txtPackingQuantity;
    @FXML
    private ComboBox<String> comboBoxSupplier;
    @FXML
    private TextField txtManual;
    @FXML
    private TextField txtPurchasingPrice1;
    @FXML
    private TextField txtPurchasingPrice2;
    @FXML
    private TextField txtPurchasingPrice3;
    @FXML
    private TextField txtSellingPrice1;
    @FXML
    private TextField txtSellingPrice2;
    @FXML
    private TextField txtSellingPrice3;
    @FXML
    private TextField txtForeignCurrencyUnit;
    @FXML
    private TextField txtBottomPrice;
    @FXML
    private TextField txtForeignCurrencyPrice;
    @FXML
    private TextField txtSpec;
    @FXML
    private TextField txtDiscountPercentage;
    @FXML
    private TextField txtNotes;
    @FXML
    private CheckBox checkBoxShortage;
    @FXML
    private CheckBox checkBoxAvailable;

    /**
     *
     * @param dialog
     * @param callback
     * @param sku
     */
    public void initialize(Stage dialog, Callback<SKU, String> callback, SKU sku) {
        this.dialog = dialog;
        this.callback = callback;
        this.sku = sku;
        btnSaveAndQuit.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
        comboBoxUnit.getItems().clear();
        comboBoxCategory.getItems().clear();
        comboBoxCar.getItems().clear();
        comboBoxPlace.getItems().clear();
        comboBoxCommonCar.getItems().clear();
        comboBoxBrand.getItems().clear();
        comboBoxImport.getItems().clear();
        comboBoxCompany.getItems().clear();
        comboBoxSupplier.getItems().clear();
        //初始化单位
        try {
            Unit[] units = HttpClient.GET("/units", Unit[].class);
            comboBoxUnit.getItems().addAll(Arrays.asList(units).stream().map(e -> e.getName()).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        new AutoCompleteBox(comboBoxUnit);
        //初始化分类
        try {
            Category[] categories = HttpClient.GET("/categories/warehouse/"+Env.getInstance().currentStore().getWarehouse().getId(), Category[].class);
            comboBoxCategory.getItems().addAll(Arrays.asList(categories).stream().map(e -> e.getName()).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        new AutoCompleteBox(comboBoxCategory);
        //初始化车型
        try {
            Car[] cars = HttpClient.GET("/cars", Car[].class);
            comboBoxCar.getItems().addAll(Arrays.asList(cars).stream().map(e -> e.getName()).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        new AutoCompleteBox(comboBoxCar);
        //初始化产地
        try {
            Place[] places = HttpClient.GET("/places", Place[].class);
            comboBoxPlace.getItems().addAll(Arrays.asList(places).stream().map(e -> e.getName()).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        new AutoCompleteBox(comboBoxPlace);
        //初始化通用车型
        try {
            Car[] commonCars = HttpClient.GET("/cars", Car[].class);
            comboBoxCommonCar.getItems().addAll(Arrays.asList(commonCars).stream().map(e -> e.getName()).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        new AutoCompleteBox(comboBoxCommonCar);
        //初始化品牌
        try {
            Brand[] brands = HttpClient.GET("/brands", Brand[].class);
            comboBoxBrand.getItems().addAll(Arrays.asList(brands).stream().map(e -> e.getName()).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        new AutoCompleteBox(comboBoxBrand);
        //初始化进口
        try {
            Import[] imports = HttpClient.GET("/imports", Import[].class);
            comboBoxImport.getItems().addAll(Arrays.asList(imports).stream().map(e -> e.getName()).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        new AutoCompleteBox(comboBoxImport);
        //初始化所属公司
        try {
            Company[] companies = HttpClient.GET("/companies", Company[].class);
            comboBoxCompany.getItems().addAll(Arrays.asList(companies).stream().map(e -> e.getName()).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        new AutoCompleteBox(comboBoxCompany);
        //初始化供应商
        try {
            Supplier[] suppliers = HttpClient.GET("/suppliers", Supplier[].class);
            comboBoxSupplier.getItems().addAll(Arrays.asList(suppliers).stream().map(e -> e.getName()).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        new AutoCompleteBox(comboBoxSupplier);

        //初始化选中的值
        if(sku != null) {
            btnSaveAndQuit.setText("更 新");
            Product product = sku.getProduct();
            buttonPanel.getChildren().clear();
            buttonPanel.getChildren().addAll(btnCancel, btnSaveAndQuit);

            unit = product.getUnit();
            category = product.getCategory();
            car = product.getCar();
            comboBoxCategory.getSelectionModel().select(product.getCategory() != null?product.getCategory().getName():"");
            comboBoxUnit.getSelectionModel().select(product.getUnit() != null?product.getUnit().getName():"");
            comboBoxCar.getSelectionModel().select(product.getCar() != null?product.getCar().getName():"");
            comboBoxPlace.getSelectionModel().select(product.getPlace() != null?product.getPlace().getName():"");
            comboBoxCommonCar.getSelectionModel().select(product.getRelevantModels());
            comboBoxBrand.getSelectionModel().select(product.getBrand() != null?product.getBrand().getName():"");
            comboBoxImport.getSelectionModel().select(product.getImported() != null?product.getImported().getName():"");
            comboBoxCompany.getSelectionModel().select(product.getCompany() != null?product.getCompany().getName():"");
            comboBoxSupplier.getSelectionModel().select(product.getSupplier() != null?product.getSupplier().getName():"");
            txtManual.setText(product.getManual());
            txtCode.setText(product.getCode());
            txtName.setText(product.getName());
            txtBarCode.setText(product.getBarCode());
            txtEnglishName.setText(product.getEnglishName());
            txtCommonNumber.setText(product.getCommonNumber());
            txtMaterials.setText(product.getMaterials());
            txtWeight.setText(product.getWeight());
            txtPackingQuantity.setText(product.getPackingQuantity()+"");
            txtPurchasingPrice1.setText(product.getPurchasingPrice1().toString());
            txtPurchasingPrice2.setText(product.getPurchasingPrice2().toString());
            txtPurchasingPrice3.setText(product.getPurchasingPrice3().toString());
            txtSellingPrice1.setText(product.getSellingPrice1().toString());
            txtSellingPrice2.setText(product.getSellingPrice2().toString());
            txtSellingPrice3.setText(product.getSellingPrice3().toString());
            txtForeignCurrencyUnit.setText(product.getForeignCurrencyUnit());
            txtForeignCurrencyPrice.setText(product.getForeignCurrencyPrice());
            txtBottomPrice.setText(product.getBottomPrice().toString());
            txtSpec.setText(sku.getSpecification());
            txtDiscountPercentage.setText(sku.getDiscount() != null ? sku.getDiscount().toString():"");
            txtNotes.setText(sku.getNotes());
            checkBoxShortage.setSelected(product.getShortage());
        }
    }

    @FXML
    private void cancel() {
        dialog.close();
    }

    @FXML
    private void continueToAdd() {
        save(false);
    }

    @FXML
    private void saveAndQuit() {
        if(sku == null) {
            save(true);
        } else {
            update(true);
        }
    }

    private void update(boolean closeDialog) {
        if(validate()) {
            insertItemsIFNotExist();
            //插入新Product
            Product product = sku.getProduct();
            product.setCode(txtCode.getText());
            product.setUnit(unit);
            product.setName(txtName.getText());
            product.setCategory(category);
            product.setBarCode(txtBarCode.getText());
            product.setCar(car);
            product.setPlace(place);
            product.setRelevantModels(commonCars);
            product.setBrand(brand);
            product.setEnglishName(txtEnglishName.getText());
            product.setImported(imported);
            product.setCommonNumber(txtCommonNumber.getText());
            product.setMaterials(txtMaterials.getText());
            product.setCompany(company);
            product.setWeight(txtWeight.getText());
            product.setPackingQuantity(NumberValidationUtils.isRealNumber(txtPackingQuantity.getText())?Integer.valueOf(txtPackingQuantity.getText()):0);
            product.setSupplier(supplier);
            product.setManual(txtManual.getText());
            product.setPurchasingPrice1(NumberValidationUtils.isRealNumber(txtPurchasingPrice1.getText())? new BigDecimal(txtPurchasingPrice1.getText()).setScale(2, RoundingMode.HALF_UP) : BigDecimal.ZERO);
            product.setPurchasingPrice2(NumberValidationUtils.isRealNumber(txtPurchasingPrice2.getText())? new BigDecimal(txtPurchasingPrice2.getText()).setScale(2, RoundingMode.HALF_UP) : BigDecimal.ZERO);
            product.setPurchasingPrice3(NumberValidationUtils.isRealNumber(txtPurchasingPrice3.getText())? new BigDecimal(txtPurchasingPrice3.getText()).setScale(2, RoundingMode.HALF_UP) : BigDecimal.ZERO);
            product.setSellingPrice1(NumberValidationUtils.isRealNumber(txtSellingPrice1.getText())? new BigDecimal(txtSellingPrice1.getText()).setScale(2, RoundingMode.HALF_UP) : BigDecimal.ZERO);
            product.setSellingPrice2(NumberValidationUtils.isRealNumber(txtSellingPrice2.getText())? new BigDecimal(txtSellingPrice2.getText()).setScale(2, RoundingMode.HALF_UP) : BigDecimal.ZERO);
            product.setSellingPrice3(NumberValidationUtils.isRealNumber(txtSellingPrice3.getText())? new BigDecimal(txtSellingPrice3.getText()).setScale(2, RoundingMode.HALF_UP) : BigDecimal.ZERO);
            product.setBottomPrice(NumberValidationUtils.isRealNumber(txtBottomPrice.getText())? new BigDecimal(txtBottomPrice.getText()).setScale(2, RoundingMode.HALF_UP) : BigDecimal.ZERO);
            product.setForeignCurrencyUnit(txtForeignCurrencyUnit.getText());
            product.setForeignCurrencyPrice(txtForeignCurrencyPrice.getText());
            product.setShortage(checkBoxShortage.isSelected());
            try {
                String json = GoogleJson.GET().toJson(product);
                HttpClient.PUT("/products/"+product.getId(), json);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //更改SKU
            try {
                SKU sku = HttpClient.GET("/sku/"+this.sku.getId(), SKU.class);
                sku.setProduct(product);
                sku.setSkuCode(product.getCode());
                sku.setSkuName(product.getName());
                sku.setSkuBarCode(product.getBarCode());
                sku.setSpecification(txtSpec.getText());
                sku.setDiscount(new BigDecimal(txtDiscountPercentage.getText()));
                sku.setNotes(txtNotes.getText());
                sku.setEnabled(checkBoxAvailable.isSelected());
                try {
                    String json = GoogleJson.GET().toJson(sku);
                    HttpClient.PUT("/sku/"+sku.getId(), json);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                callback.call(sku);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(closeDialog) {
                dialog.close();
            }
        }
    }

    private void save(boolean closeDialog) {
        if(validate()) {
            insertItemsIFNotExist();
            //插入新Product
            Product product = new Product();
            product.setCode(txtCode.getText());
            product.setUnit(unit);
            product.setName(txtName.getText());
            product.setCategory(category);
            product.setBarCode(txtBarCode.getText());
            product.setCar(car);
            product.setPlace(place);
            product.setRelevantModels(commonCars);
            product.setBrand(brand);
            product.setEnglishName(txtEnglishName.getText());
            product.setImported(imported);
            product.setCommonNumber(txtCommonNumber.getText());
            product.setMaterials(txtMaterials.getText());
            product.setCompany(company);
            product.setWeight(txtWeight.getText());
            product.setPackingQuantity(NumberValidationUtils.isRealNumber(txtPackingQuantity.getText())?Integer.valueOf(txtPackingQuantity.getText()):0);
            product.setSupplier(supplier);
            product.setManual(txtManual.getText());
            product.setPurchasingPrice1(NumberValidationUtils.isRealNumber(txtPurchasingPrice1.getText())? new BigDecimal(txtPurchasingPrice1.getText()).setScale(2, RoundingMode.HALF_UP) : BigDecimal.ZERO);
            product.setPurchasingPrice2(NumberValidationUtils.isRealNumber(txtPurchasingPrice2.getText())? new BigDecimal(txtPurchasingPrice2.getText()).setScale(2, RoundingMode.HALF_UP) : BigDecimal.ZERO);
            product.setPurchasingPrice3(NumberValidationUtils.isRealNumber(txtPurchasingPrice3.getText())? new BigDecimal(txtPurchasingPrice3.getText()).setScale(2, RoundingMode.HALF_UP) : BigDecimal.ZERO);
            product.setSellingPrice1(NumberValidationUtils.isRealNumber(txtSellingPrice1.getText())? new BigDecimal(txtSellingPrice1.getText()).setScale(2, RoundingMode.HALF_UP) : BigDecimal.ZERO);
            product.setSellingPrice2(NumberValidationUtils.isRealNumber(txtSellingPrice2.getText())? new BigDecimal(txtSellingPrice2.getText()).setScale(2, RoundingMode.HALF_UP) : BigDecimal.ZERO);
            product.setSellingPrice3(NumberValidationUtils.isRealNumber(txtSellingPrice3.getText())? new BigDecimal(txtSellingPrice3.getText()).setScale(2, RoundingMode.HALF_UP) : BigDecimal.ZERO);
            product.setBottomPrice(NumberValidationUtils.isRealNumber(txtBottomPrice.getText())? new BigDecimal(txtBottomPrice.getText()).setScale(2, RoundingMode.HALF_UP) : BigDecimal.ZERO);
            product.setForeignCurrencyUnit(txtForeignCurrencyUnit.getText());
            product.setForeignCurrencyPrice(txtForeignCurrencyPrice.getText());
            product.setShortage(checkBoxShortage.isSelected());
            String json = GoogleJson.GET().toJson(product);
            try {
                String idStr = HttpClient.POST("/products", json);
                product.setId(Long.valueOf(idStr));
            } catch (IOException e) {
                e.printStackTrace();
            }
            //新建SKU
            SKU sku = new SKU();
            sku.setProduct(product);
            sku.setSkuCode(product.getCode());
            sku.setSkuName(product.getName());
            sku.setSkuBarCode(product.getBarCode());
            sku.setSpecification(txtSpec.getText());
            sku.setStockQty(Constants.ZERO);
            sku.setDiscount(NumberValidationUtils.isRealNumber(txtDiscountPercentage.getText())?new BigDecimal(txtDiscountPercentage.getText()):BigDecimal.ZERO);
            sku.setNotes(txtNotes.getText());
            sku.setEnabled(checkBoxAvailable.isSelected());
            json = GoogleJson.GET().toJson(sku);
            try {
                String idStr = HttpClient.POST("/sku", json);
                sku.setId(Long.valueOf(idStr));
            } catch (IOException e) {
                e.printStackTrace();
            }
            callback.call(sku);
            if(closeDialog) {
                dialog.close();
            }
        }
    }

    /**
     * 插入新选项如果它不存在
     */
    private void insertItemsIFNotExist() {
        //分类如果不存在则新建
        if(comboBoxCategory.getValue() != null && !comboBoxCategory.getValue().trim().equals("")) {

            try {
                Category rootCategory = HttpClient.GET("/category/root/"+Env.getInstance().currentStore().getWarehouse().getId(), Category.class);
                category = new Category();
                category.setName(comboBoxCategory.getValue());
                category.setParent(false);
                category.setParentId(rootCategory.getId());
                category.setStore(Env.getInstance().currentStore());
                String json = GoogleJson.GET().toJson(category);
                String idStr = HttpClient.POST("/categories", json);
                category.setId(Long.valueOf(idStr));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //供应商如果不存在则新建
        if(comboBoxSupplier.getValue() != null && !comboBoxSupplier.getValue().trim().equals("")) {
            try {
                SupplierCategory rootSupplierCategory = HttpClient.GET("/supplier/categories/root", SupplierCategory.class);
                supplier = new Supplier();
                supplier.setName(comboBoxSupplier.getValue());
                supplier.setSupplierCategory(rootSupplierCategory);
                String json = GoogleJson.GET().toJson(supplier);
                String idStr = HttpClient.POST("/suppliers", json);
                supplier.setId(Long.valueOf(idStr));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //单位如果不存在则新建
        if(comboBoxUnit.getValue() != null && !comboBoxUnit.getValue().trim().equals("")) {
            unit = new Unit();
            unit.setName(comboBoxUnit.getValue());
            String json = GoogleJson.GET().toJson(unit);
            try {
                String idStr = HttpClient.POST("/units", json);
                unit.setId(Long.valueOf(idStr));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //车型如果不存在则新建
        if(comboBoxCar.getValue() != null && !comboBoxCar.getValue().trim().equals("")) {
            car = new Car();
            car.setName(comboBoxCar.getValue());
            String json = GoogleJson.GET().toJson(car);
            try {
                String idStr = HttpClient.POST("/cars", json);
                car.setId(Long.valueOf(idStr));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //产地如果不存在则新建
        if(comboBoxPlace.getValue() != null && !comboBoxPlace.getValue().trim().equals("")) {
            place = new Place();
            place.setName(comboBoxPlace.getValue());
            String json = GoogleJson.GET().toJson(place);
            try {
                String idStr = HttpClient.POST("/places", json);
                place.setId(Long.valueOf(idStr));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //通用车型如果不存在则新建
        if(comboBoxCommonCar.getValue() != null && !comboBoxCommonCar.getValue().trim().equals("")) {
            StringBuilder stb = new StringBuilder();
            String[] carNames = comboBoxCommonCar.getValue().split(";");
            for(String name : carNames) {
                stb.append(name).append(";");
                Car car = new Car();
                car.setName(name);
                String json = GoogleJson.GET().toJson(car);
                try {
                    String idStr = HttpClient.POST("/cars", json);
                    car.setId(Long.valueOf(idStr));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            commonCars = stb.toString();
        }
        //品牌如果不存在则新建
        if(comboBoxBrand.getValue() != null && !comboBoxBrand.getValue().trim().equals("")) {
            brand = new Brand();
            brand.setName(comboBoxBrand.getValue());
            String json = GoogleJson.GET().toJson(brand);
            try {
                String idStr = HttpClient.POST("/brands", json);
                brand.setId(Long.valueOf(idStr));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //进口如果不存在则新建
        if(comboBoxImport.getValue() != null && !comboBoxImport.getValue().trim().equals("")) {
            imported = new Import();
            imported.setName(comboBoxImport.getValue());
            String json = GoogleJson.GET().toJson(imported);
            try {
                String idStr = HttpClient.POST("/imports", json);
                imported.setId(Long.valueOf(idStr));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //所属公司如果不存在则新建
        if(comboBoxCompany.getValue() != null && !comboBoxCompany.getValue().trim().equals("")) {
            company = new Company();
            company.setName(comboBoxCompany.getValue());
            String json = GoogleJson.GET().toJson(company);
            try {
                String idStr = HttpClient.POST("/companies", json);
                company.setId(Long.valueOf(idStr));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean validate() {
        if(txtCode.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("编码不能为空");
            alert.show();
            return false;
        } else if(txtName.getText().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("名称不能为空");
            alert.show();
            return false;
        } else if(comboBoxUnit.getValue().trim().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
            alert.setHeaderText("单位不能为空");
            alert.show();
            return false;
        }
        return true;
    }

    @FXML
    private void openUnitChooser() {
        Callback<Unit, String> callback = new Callback<Unit, String>() {
            @Override
            public String call(Unit param) {
                comboBoxUnit.setValue(param.getName());
                return null;
            }
        };
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/products/UnitChooser.fxml"
                )
        );
        BorderPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage dialog = new Stage();
        UnitChooserController controller = loader.getController();
        controller.initialize(dialog, callback, unit);
        dialog.setTitle("单位选择器");
        dialog.initOwner(this.dialog);
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    @FXML
    private void openCategoryChooser() {
        Callback<Category, String> callback = new Callback<Category, String>() {
            @Override
            public String call(Category param) {
                category = param;
                comboBoxCategory.setValue(category.getName());
                return null;
            }
        };
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/products/CategoryChooser.fxml"
                )
        );
        VBox root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage dialog = new Stage();
        CategoryChooserController controller = loader.getController();
        controller.initialize(dialog, callback, category);
        dialog.setTitle("选择产品分类");
        dialog.initOwner(this.dialog);
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    @FXML
    private void openCarChooser() {
        Callback<List<Car>, String> callback = new Callback<List<Car>, String>() {
            @Override
            public String call(List<Car> param) {
                if(param.size() > 0) {
                    comboBoxCar.setValue(param.get(0).getName());
                }
                return null;
            }
        };
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/products/CarChooser.fxml"
                )
        );
        BorderPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage dialog = new Stage();
        CarChooserController controller = loader.getController();
        controller.initialize(dialog, callback, car, false);
        dialog.setTitle("车型选择器");
        dialog.initOwner(this.dialog);
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    @FXML
    private void openPlaceChooser() {
        Callback<Place, String> callback = new Callback<Place, String>() {
            @Override
            public String call(Place param) {
                place = param;
                comboBoxPlace.setValue(place.getName());
                return null;
            }
        };
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/products/PlaceChooser.fxml"
                )
        );
        BorderPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage dialog = new Stage();
        PlaceChooserController controller = loader.getController();
        controller.initialize(dialog, callback, place);
        dialog.setTitle("产地选择器");
        dialog.initOwner(this.dialog);
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    @FXML
    private void openCommonCarChooser() {
        Callback<List<Car>, String> callback = new Callback<List<Car>, String>() {
            @Override
            public String call(List<Car> param) {
                StringBuilder cars = new StringBuilder();
                param.forEach(e -> {
                    cars.append(e.getName()+";");
                });
                commonCars = cars.toString();
                comboBoxCommonCar.setValue(cars.toString());
                return null;
            }
        };
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/products/CarChooser.fxml"
                )
        );
        BorderPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage dialog = new Stage();
        CarChooserController controller = loader.getController();
        controller.initialize(dialog, callback, car, true);
        dialog.setTitle("车型选择器");
        dialog.initOwner(this.dialog);
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    @FXML
    private void openBrandChooser() {
        Callback<Brand, String> callback = new Callback<Brand, String>() {
            @Override
            public String call(Brand param) {
                brand = param;
                comboBoxBrand.setValue(brand.getName());
                return null;
            }
        };
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/products/BrandChooser.fxml"
                )
        );
        BorderPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage dialog = new Stage();
        BrandChooserController controller = loader.getController();
        controller.initialize(dialog, callback, brand);
        dialog.setTitle("品牌选择器");
        dialog.initOwner(this.dialog);
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    @FXML
    private void openImportList() {
        Callback<Import, String> callback = new Callback<Import, String>() {
            @Override
            public String call(Import param) {
                imported = param;
                comboBoxImport.setValue(imported.getName());
                return null;
            }
        };
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/products/ImportChooser.fxml"
                )
        );
        BorderPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage dialog = new Stage();
        ImportChooserController controller = loader.getController();
        controller.initialize(dialog, callback, imported);
        dialog.setTitle("进口选择器");
        dialog.initOwner(this.dialog);
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    @FXML
    private void openCompanyChooser() {
        Callback<Company, String> callback = new Callback<Company, String>() {
            @Override
            public String call(Company param) {
                company = param;
                comboBoxCompany.setValue(company.getName());
                return null;
            }
        };
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/products/CompanyChooser.fxml"
                )
        );
        BorderPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage dialog = new Stage();
        CompanyChooserController controller = loader.getController();
        controller.initialize(dialog, callback, company);
        dialog.setTitle("公司选择器");
        dialog.initOwner(this.dialog);
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setScene(scene);
        // center stage on screen
        dialog.centerOnScreen();
        dialog.show();
    }

    @FXML
    private void openSupplierChooser() {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/supplier/SupplierChooser.fxml"
                )
        );
        VBox root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        Stage subStage = new Stage();
        SupplierChooserController controller = loader.getController();
        Callback<Supplier, String> callback = returnedSupplier -> {
            supplier = returnedSupplier;
            comboBoxSupplier.setValue(supplier.getName());
            return null;
        };
        controller.initialize(subStage, callback, supplier);
        subStage.setTitle("选择供应商");
        subStage.initOwner(dialog);
        subStage.setResizable(false);
        subStage.initModality(Modality.APPLICATION_MODAL);
        subStage.setScene(scene);
        // center stage on screen
        subStage.centerOnScreen();
        subStage.show();
    }
}
