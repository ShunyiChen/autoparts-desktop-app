package com.shunyi.autoparts.ui.common.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Objects;

/**
 * @description SKU与货位映射关系
 * @author Shunyi Chen
 * @date 2020/3/23
 */
public class SKUSlotMapping {

    public static class Id implements Serializable {
        /** SKU ID */
        protected Long skuId;
        /** 货位ID */
        protected Long slotId;

        public Id() {}

        public Id(Long skuId, Long slotId) {
            this.skuId = skuId;
            this.slotId = slotId;
        }

        public Long getSkuId() {
            return skuId;
        }

        public void setSkuId(Long skuId) {
            this.skuId = skuId;
        }

        public Long getSlotId() {
            return slotId;
        }

        public void setSlotId(Long slotId) {
            this.slotId = slotId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Id id = (Id) o;
            return Objects.equals(skuId, id.skuId) &&
                    Objects.equals(slotId, id.slotId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(skuId, slotId);
        }
    }
    /** 内嵌ID */
    protected Id id = new Id();
    /** SKU */
    @JsonIgnore
    protected SKU sku;
    /** 货位 */
    @JsonIgnore
    protected Slot slot;

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

    public Slot getSlot() {
        return slot;
    }

    public void setSlot
            (Slot slot) {
        this.slot = slot;
    }
}
