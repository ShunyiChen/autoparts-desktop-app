package com.shunyi.autoparts.ui.products;

import com.shunyi.autoparts.ui.common.*;
import com.shunyi.autoparts.ui.common.vo.*;
import com.shunyi.autoparts.ui.supplier.SupplierChooserController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    private CheckBox checkBoxShortage;

    /**
     *
     * @param dialog
     * @param callback
     * @param category
     */
    public void initialize(Stage dialog, Callback<SKU, String> callback, Category category) {
        this.dialog = dialog;
        this.callback = callback;
        this.category = category;
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
            Category[] categories = HttpClient.GET("/categories", Category[].class);
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
        save(true);
    }

    private void save(boolean closeDialog) {
        if(validate()) {
            //单位如果不存在则新建
            if(comboBoxUnit.getValue() != null && !comboBoxUnit.getValue().trim().equals("")) {
                Unit newUnit = new Unit();
                newUnit.setName(comboBoxUnit.getValue());
                String json = GoogleJson.GET().toJson(newUnit);
                try {
                    HttpClient.POST("/units", json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //车型如果不存在则新建
            if(comboBoxCar.getValue() != null && !comboBoxCar.getValue().trim().equals("")) {
                Car newCar = new Car();
                newCar.setName(comboBoxCar.getValue());
                String json = GoogleJson.GET().toJson(newCar);
                try {
                    HttpClient.POST("/cars", json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //产地如果不存在则新建
            if(comboBoxPlace.getValue() != null && !comboBoxPlace.getValue().trim().equals("")) {
                Place newPlace = new Place();
                newPlace.setName(comboBoxPlace.getValue());
                String json = GoogleJson.GET().toJson(newPlace);
                try {
                    HttpClient.POST("/places", json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //通用车型如果不存在则新建
            if(comboBoxCommonCar.getValue() != null && !comboBoxCommonCar.getValue().trim().equals("")) {
                Car newCar = new Car();
                newCar.setName(comboBoxCommonCar.getValue());
                String json = GoogleJson.GET().toJson(newCar);
                try {
                    HttpClient.POST("/cars", json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //品牌如果不存在则新建
            if(comboBoxBrand.getValue() != null && !comboBoxBrand.getValue().trim().equals("")) {
                Brand newBrand = new Brand();
                newBrand.setName(comboBoxBrand.getValue());
                String json = GoogleJson.GET().toJson(newBrand);
                try {
                    HttpClient.POST("/brands", json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //进口如果不存在则新建
            if(comboBoxImport.getValue() != null && !comboBoxImport.getValue().trim().equals("")) {
                Import newImport = new Import();
                newImport.setName(comboBoxImport.getValue());
                String json = GoogleJson.GET().toJson(newImport);
                try {
                    HttpClient.POST("/imports", json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //所属公司如果不存在则新建
            if(comboBoxCompany.getValue() != null && !comboBoxCompany.getValue().trim().equals("")) {
                Company newCompany = new Company();
                newCompany.setName(comboBoxCompany.getValue());
                String json = GoogleJson.GET().toJson(newCompany);
                try {
                    HttpClient.POST("/companies", json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //新建Product
            Product product = new Product();
            product.setCode(txtCode.getText());
            product.setUnit(comboBoxUnit.getValue());
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
            product.setPurchasingPrice1(txtPurchasingPrice1.getText().equals("")? BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP) : new BigDecimal(txtPurchasingPrice1.getText()).setScale(2, RoundingMode.HALF_UP));
            product.setPurchasingPrice2(new BigDecimal(txtPurchasingPrice2.getText()));
            product.setPurchasingPrice3(new BigDecimal(txtPurchasingPrice3.getText()));
            product.setSellingPrice1(new BigDecimal(txtSellingPrice1.getText()));
            product.setSellingPrice2(new BigDecimal(txtSellingPrice2.getText()));
            product.setSellingPrice3(new BigDecimal(txtSellingPrice3.getText()));
            product.setBottomPrice(new BigDecimal(txtBottomPrice.getText()));
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

            System.out.println(product);

            //新建SKU
            Set<SKUSlotMapping> skuSlotMappingSet = new HashSet<>();
            Set<SKUPhoto> photos = new HashSet<>();
            SKU sku = new SKU(Constants.ID, product, Constants.EMPTY, Constants.EMPTY, Constants.EMPTY, Constants.EMPTY, Constants.ZERO, Constants.EMPTY, Constants.EMPTY, Constants.AVAILABLE, Constants.EMPTY, Constants.AVG_PRICE, skuSlotMappingSet, photos, null, Env.getInstance().currentUser(), null, null, null ,null, null, null);
            json = GoogleJson.GET().toJson(sku);
            try {
                String idStr = HttpClient.POST("/sku", json);
                sku.setId(Long.valueOf(idStr));
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(sku);
            callback.call(sku);
            if(closeDialog) {
                dialog.close();
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
                unit = param;
                comboBoxUnit.setValue(unit.getName());
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
                car = param.get(0);
                comboBoxCar.setValue(car.getName());
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
