package com.shunyi.autoparts.ui.products;

/** SKU表格列的元数据 */
public class TableColumnMetadata {
    private Integer columnId;
    private Long attributeNameId;
    public TableColumnMetadata(Integer columnId, Long attributeNameId) {
        this.columnId = columnId;
        this.attributeNameId = attributeNameId;
    }

    public Integer getColumnId() {
        return columnId;
    }

    public void setColumnId(Integer columnId) {
        this.columnId = columnId;
    }

    public Long getAttributeNameId() {
        return attributeNameId;
    }

    public void setAttributeNameId(Long attributeNameId) {
        this.attributeNameId = attributeNameId;
    }
}
