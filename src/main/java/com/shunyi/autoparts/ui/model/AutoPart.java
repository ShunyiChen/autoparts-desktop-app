package com.shunyi.autoparts.ui.model;

import com.shunyi.autoparts.ui.common.BaseModel;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.util.Callback;

/**
 * 配件模型
 */
public class AutoPart extends BaseModel {

    /** ID */
    private SimpleLongProperty id;
    /** 配件编码 */
    private SimpleStringProperty code;
    /** 名称 */
    private SimpleStringProperty name;
    /** 单位 */
    private SimpleStringProperty unit;
    /** 数量 */
    private SimpleIntegerProperty count;
    /** 不含税单价 */
    private SimpleDoubleProperty priceExcludingTax;
    /** 不含税金额 */
    private SimpleDoubleProperty amountExcludingTax;
    /** 仓库 */
    private SimpleStringProperty warehouse;
    /** 本库现库存数 */
    private SimpleIntegerProperty num;
    /** 备注 */
    private SimpleStringProperty notes;
    /** 车型 */
    private SimpleStringProperty model;
    /** 品牌 */
    private SimpleStringProperty brand;
    /** 均摊单价 */
    private SimpleDoubleProperty capitationPrice;
    /** 货位 */
    private SimpleStringProperty position;

    private Callback<AutoPart, Void> comparator;


    /**
     * Constructor
     *
     * @param code 配件编码
     * @param name 名称
     * @param unit 单位
     * @param count 数量
     * @param priceExcludingTax 不含税单价
     * @param amountExcludingTax 不含税金额
     * @param warehouse 仓库
     * @param num 本库现库存数
     * @param notes 备注
     * @param model 车型
     * @param brand 品牌
     * @param capitationPrice 均摊单价
     * @param position 货位
     */
    public AutoPart(String code, String name, String unit, Integer count, Double priceExcludingTax, Double amountExcludingTax, String warehouse, Integer num, String notes, String model, String brand, Double capitationPrice, String position) {
        this.code = new SimpleStringProperty(code);
        this.name = new SimpleStringProperty(name);
        this.unit = new SimpleStringProperty(unit);
        this.count = new SimpleIntegerProperty(count);
        this.priceExcludingTax = new SimpleDoubleProperty(priceExcludingTax);
        this.amountExcludingTax = new SimpleDoubleProperty(amountExcludingTax);
        this.warehouse = new SimpleStringProperty(warehouse);
        this.num = new SimpleIntegerProperty(num);
        this.notes = new SimpleStringProperty(notes);
        this.model = new SimpleStringProperty(model);
        this.brand = new SimpleStringProperty(brand);
        this.capitationPrice = new SimpleDoubleProperty(capitationPrice);
        this.position = new SimpleStringProperty(position);
        initEvents();
    }

    private void initEvents() {
        this.code.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(comparator != null)
                    comparator.call(AutoPart.this);
            }
        });
        this.name.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(comparator != null)
                    comparator.call(AutoPart.this);
            }
        });
    }

    public long getId() {
        return id.get();
    }

    public SimpleLongProperty idProperty() {
        return id;
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public String getCode() {
        return code.get();
    }

    public SimpleStringProperty codeProperty() {
        return code;
    }

    public void setCode(String code) {
        this.code.set(code);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getUnit() {
        return unit.get();
    }

    public SimpleStringProperty unitProperty() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit.set(unit);
    }

    public int getCount() {
        return count.get();
    }

    public SimpleIntegerProperty countProperty() {
        return count;
    }

    public void setCount(int count) {
        this.count.set(count);
    }

    public double getPriceExcludingTax() {
        return priceExcludingTax.get();
    }

    public SimpleDoubleProperty priceExcludingTaxProperty() {
        return priceExcludingTax;
    }

    public void setPriceExcludingTax(double priceExcludingTax) {
        this.priceExcludingTax.set(priceExcludingTax);
    }

    public double getAmountExcludingTax() {
        return amountExcludingTax.get();
    }

    public SimpleDoubleProperty amountExcludingTaxProperty() {
        return amountExcludingTax;
    }

    public void setAmountExcludingTax(double amountExcludingTax) {
        this.amountExcludingTax.set(amountExcludingTax);
    }

    public String getWarehouse() {
        return warehouse.get();
    }

    public SimpleStringProperty warehouseProperty() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse.set(warehouse);
    }

    public int getNum() {
        return num.get();
    }

    public SimpleIntegerProperty numProperty() {
        return num;
    }

    public void setNum(int num) {
        this.num.set(num);
    }

    public String getNotes() {
        return notes.get();
    }

    public SimpleStringProperty notesProperty() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes.set(notes);
    }

    public String getModel() {
        return model.get();
    }

    public SimpleStringProperty modelProperty() {
        return model;
    }

    public void setModel(String model) {
        this.model.set(model);
    }

    public String getBrand() {
        return brand.get();
    }

    public SimpleStringProperty brandProperty() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand.set(brand);
    }

    public double getCapitationPrice() {
        return capitationPrice.get();
    }

    public SimpleDoubleProperty capitationPriceProperty() {
        return capitationPrice;
    }

    public void setCapitationPrice(double capitationPrice) {
        this.capitationPrice.set(capitationPrice);
    }

    public String getPosition() {
        return position.get();
    }

    public SimpleStringProperty positionProperty() {
        return position;
    }

    public void setPosition(String position) {
        this.position.set(position);
    }

    public Callback<AutoPart, Void> getComparator() {
        return comparator;
    }

    public void setComparator(Callback<AutoPart, Void> comparator) {
        this.comparator = comparator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AutoPart sparepart = (AutoPart) o;
        if(code.get().equals(sparepart.getCode())
                && name.get().equals(sparepart.getName())) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + code.hashCode();
        return result;
    }
}
