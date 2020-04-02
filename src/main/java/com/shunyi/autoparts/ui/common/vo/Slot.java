package com.shunyi.autoparts.ui.common.vo;

import java.util.HashSet;
import java.util.Set;

/**
 * @description 货位VO
 * @author Shunyi Chen
 * @date 2020/3/23
 */
public class Slot {
    /** ID */
    private Long id;
    /** 仓库（仓库编号） */
    private Warehouse warehouse;
    /** 货位名称 */
    private String name;
    /** 条形码 */
    private String barCode;
    /** 第几区货架/库区编号 */
    private String level_1;
    /** 通道编号 */
    private String level_2;
    /** 第几个货架/货架组编号 */
    private String level_3;
    /** 第几层货架/货架层号 */
    private String level_4;
    /** 第几个存放盒位/货架层中库位 */
    private String level_5;
    /** SKU与货位映射集合 */
    private Set<SKUSlotMapping> SKUSlotMappings = new HashSet<>();

    public Slot() {}

    public Slot(Long id, Warehouse warehouse, String name, String barCode, String level_1, String level_2, String level_3, String level_4, String level_5, Set<SKUSlotMapping> SKUSlotMappings) {
        this.id = id;
        this.warehouse = warehouse;
        this.name = name;
        this.barCode = barCode;
        this.level_1 = level_1;
        this.level_2 = level_2;
        this.level_3 = level_3;
        this.level_4 = level_4;
        this.level_5 = level_5;
        this.SKUSlotMappings = SKUSlotMappings;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getLevel_1() {
        return level_1;
    }

    public void setLevel_1(String level_1) {
        this.level_1 = level_1;
    }

    public String getLevel_2() {
        return level_2;
    }

    public void setLevel_2(String level_2) {
        this.level_2 = level_2;
    }

    public String getLevel_3() {
        return level_3;
    }

    public void setLevel_3(String level_3) {
        this.level_3 = level_3;
    }

    public String getLevel_4() {
        return level_4;
    }

    public void setLevel_4(String level_4) {
        this.level_4 = level_4;
    }

    public String getLevel_5() {
        return level_5;
    }

    public void setLevel_5(String level_5) {
        this.level_5 = level_5;
    }

    public Set<SKUSlotMapping> getSKUSlotMappings() {
        return SKUSlotMappings;
    }

    public void setSKUSlotMappings(Set<SKUSlotMapping> SKUSlotMappings) {
        this.SKUSlotMappings = SKUSlotMappings;
    }
}
