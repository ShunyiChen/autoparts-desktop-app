package com.shunyi.autoparts.ui.common;

/**
 * @author Shunyi
 */
public enum Status {
    /**
     * 可用
     */
    AVAILABLE("可用"),
    /**
     * 不可用
     */
    DISABLED("不可用");

    /**
     * 文本名
     */
    private String text;

    Status(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
