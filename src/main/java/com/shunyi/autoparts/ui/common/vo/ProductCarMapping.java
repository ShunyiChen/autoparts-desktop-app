package com.shunyi.autoparts.ui.common.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Objects;

/**
 * @description 配件和车型关系VO
 * @author Shunyi Chen
 * @date 2020/4/2
 */
public class ProductCarMapping {
    /** 联合主键 */
    public static class Id implements Serializable {
        /** 配件ID */
        protected Long productId;
        /** 车型ID */
        protected Long carId;

        public Id() {}

        /**
         * Constructor
         *
         * @param productId 配件ID
         * @param carId 车型ID
         */
        public Id(Long productId, Long carId) {
            this.productId = productId;
            this.carId = carId;
        }

        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public Long getCarId() {
            return carId;
        }

        public void setCarId(Long carId) {
            this.carId = carId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            ProductCarMapping.Id id = (ProductCarMapping.Id) o;
            return Objects.equals(productId, id.productId) &&
                    Objects.equals(carId, id.carId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(productId, carId);
        }

        @Override
        public String toString() {
            return "Id{" +
                    "productId=" + productId +
                    ", carId=" + carId +
                    '}';
        }
    }

    protected ProductCarMapping.Id id = new ProductCarMapping.Id();

    @JsonIgnore
    protected Product product;

    @JsonIgnore
    protected Car car;

    public ProductCarMapping.Id getId() {
        return id;
    }

    public void setId(ProductCarMapping.Id id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "ProductCarMapping{" +
                "id=" + id +
                ", product=" + product +
                ", car=" + car +
                '}';
    }
}
