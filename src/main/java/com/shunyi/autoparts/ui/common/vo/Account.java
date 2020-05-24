package com.shunyi.autoparts.ui.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @description 进货单账号VO
 * @author Shunyi Chen
 * @date 2020/4/18
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Account {
    /** ID */
    private Long id;
    /** 编号 */
    private String code;
    /** 名称 */
    private String name;
    /** 备注 */
    private String notes;
}
