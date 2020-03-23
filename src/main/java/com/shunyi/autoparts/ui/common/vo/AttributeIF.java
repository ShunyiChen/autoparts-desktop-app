package com.shunyi.autoparts.ui.common.vo;

/**
 * @Description: 属性接口
 *
 * @Author: Shunyi Chen
 * @CreateDate: 2020/3/23 18:39
 * @Version: 1.0
 */
public interface AttributeIF {
    /** 取属性ID */
    Long getId();

    /** 取属性名称 */
    String getName();

    /** 设置属性名称 */
    void setName(String name);
}
