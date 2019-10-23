package com.shunyi.autoparts.ui.model;

import java.io.Serializable;

/** SKU与货位映射关系表DTO */
public class SKUCargoSpaceMapping {

    public static class Id implements Serializable {
        /** SKU ID */
        protected Long skuId;
        /** 货位ID */
        protected Long cargoSpaceId;

        public Id() {}

        public Id(Long skuId, Long cargoSpaceId) {
            this.skuId = skuId;
            this.cargoSpaceId = cargoSpaceId;
        }

        public Long getSkuId() {
            return skuId;
        }

        public void setSkuId(Long skuId) {
            this.skuId = skuId;
        }

        public Long getCargoSpaceId() {
            return cargoSpaceId;
        }

        public void setCargoSpaceId(Long cargoSpaceId) {
            this.cargoSpaceId = cargoSpaceId;
        }
    }
    /** 内嵌ID */
    protected Id id = new Id();
    /** SKU */
    protected SKU sku;
    /** 货位 */
    protected CargoSpace cargoSpace;

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public SKU getSku() {
        return sku;
    }

    public void setSku(SKU sku) {
        this.sku = sku;
    }

    public CargoSpace getCargoSpace() {
        return cargoSpace;
    }

    public void setCargoSpace(CargoSpace cargoSpace) {
        this.cargoSpace = cargoSpace;
    }
}
