package com.shunyi.autoparts.ui.products;

import com.shunyi.autoparts.ui.common.AutoCompleteBox;
import com.shunyi.autoparts.ui.common.HttpClient;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description 产品编辑器Controller
 *
 * @author Shunyi Chen
 * @date 2020/4/7
 */
public class ProductEditorController {
    private Stage dialog;
    private Callback<Product, Object> callback;
    private Category category;
    private Car car;
    private List<Car> commonCars;
    private Place place;
    private Brand brand;



    private Product updatedProduct;
    private Supplier selectedSupplier;
    @FXML
    private Button btnSaveAndQuit;


    @FXML
    private TextField txtCode;
    @FXML
    private ComboBox<String> comboBoxUnit;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtCategory;
    @FXML
    private TextField txtBarCode;
    @FXML
    private TextField txtCar;
    @FXML
    private TextField txtPlace;
    @FXML
    private TextField txtCommonCar;
    @FXML
    private TextField txtBrand;
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
    private TextField txtSupplier;
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
    public void initialize(Stage dialog, Callback<Product, Object> callback, Category category) {
        this.dialog = dialog;
        this.callback = callback;
        this.category = category;
        btnSaveAndQuit.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
        //初始化单位
        try {
            Unit[] units = HttpClient.GET("/units", Unit[].class);
            comboBoxUnit.getItems().addAll(Arrays.asList(units).stream().map(e -> e.getName()).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        new AutoCompleteBox(comboBoxUnit);
        //初始化分类
        txtCategory.setText(category.getName());
        //初始化进口
        try {
            Import[] imports = HttpClient.GET("/imports", Import[].class);
            comboBoxImport.getItems().addAll(Arrays.asList(imports).stream().map(e -> e.getName()).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        new AutoCompleteBox(comboBoxImport);
        //所属公司
        try {
            Company[] companies = HttpClient.GET("/imports", Company[].class);
            comboBoxCompany.getItems().addAll(Arrays.asList(companies).stream().map(e -> e.getName()).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        new AutoCompleteBox(comboBoxCompany);
    }

    @FXML
    private void cancel() {
        dialog.close();
    }

    @FXML
    private void saveAndQuit() {
        if(validate()) {
            //新建Product
            Product product = new Product();
            product.setCode(txtCode.getText());
            product.setUnit(comboBoxUnit.getValue());
            product.setName(txtName.getText());
            product.setCategory(category);
            product.setBarCode(txtBarCode.getText());
            product.setCar(car);
            product.setPlace(place);
//            product.setProductCarMappingSet();


            System.out.println(product);

            //新建SKU
//            SKU sku = new SKU();

//            BigDecimal listPrice;
//            listPrice = new BigDecimal(Double.valueOf(txtPrice.getText())).setScale(2, RoundingMode.HALF_UP);
//            Product newProduct = new Product(0L, txtCode.getText(), txtBarCode.getText(), txtName.getText(), boxBrand.getValue(), selectedCar, selectedSupplier, txtUnit.getText(), listPrice, boxImported.getValue(), txtOrigin.getText(), txtNotes.getText(), null, Env.getInstance().getStringValue(Env.CURRENT_USER),null,null,null,null, Constants.DELETE_FLAG_FALSE,null);
//            callback.call(newProduct);

            dialog.close();
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
    private void openCategoryChooser() {
        Callback<Category, String> callback = new Callback<Category, String>() {
            @Override
            public String call(Category param) {
                category = param;
                txtCategory.setText(category.getName());
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
    private void openPlaceChooser() {
        Callback<Place, String> callback = new Callback<Place, String>() {
            @Override
            public String call(Place param) {
                place = param;
                txtPlace.setText(place.getName());
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
                commonCars = param;
                StringBuilder cars = new StringBuilder();
                commonCars.forEach(e -> {
                    cars.append(e.getName()+";");
                });
                txtCommonCar.setText(cars.toString());
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
                txtBrand.setText(brand.getName());
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
        Callback<Supplier, String> callback = supplier -> {
            selectedSupplier = supplier;
            txtSupplier.setText(selectedSupplier.getName());
            return null;
        };
        controller.prepare(subStage, selectedSupplier, callback);
        subStage.setTitle("选择供应商");
        subStage.initOwner(dialog);
        subStage.setResizable(false);
        subStage.initModality(Modality.APPLICATION_MODAL);
        subStage.setScene(scene);
        // center stage on screen
        subStage.centerOnScreen();
        subStage.show();
    }

    @FXML
    private void openCarChooser() {
        Callback<List<Car>, String> callback = new Callback<List<Car>, String>() {
            @Override
            public String call(List<Car> param) {
                car = param.get(0);
                txtCar.setText(car.getName());
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

}
