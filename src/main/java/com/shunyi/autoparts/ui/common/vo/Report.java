package com.shunyi.autoparts.ui.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @Description: 报表VO
 * @Author: Shunyi
 * @CreateDate: 2020/5/23
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Report {
    /** 自增ID */
    private Long id;
    /** 单号 */
    private String orderNo;
    /** 模板名称 */
    private String templateName;
    /** 报表文件类型 */
    private String reportFileType;
    /** 创建时间 */
    private Date dateCreated;
    /** 创建者 */
    private String creator;
}
