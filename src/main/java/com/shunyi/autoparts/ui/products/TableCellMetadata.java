package com.shunyi.autoparts.ui.products;

/** SKU表格单元格内容的元数据 */
public class TableCellMetadata {
    private Long attributeValueId;
    private String text;

    public TableCellMetadata(Long attributeValueId, String text) {
        this.attributeValueId = attributeValueId;
        this.text = text;
    }

    public Long getAttributeValueId() {
        return attributeValueId;
    }

    public void setAttributeValueId(Long attributeValueId) {
        this.attributeValueId = attributeValueId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
