package com.example.util.pdf;

import lombok.Data;

@Data
public class ScProductBatchCheckReport {
    /**
     * 产品批次检验报告表主键
     */
    private Integer id;

    /**
     * 产品批次主键id
     */
    private Integer batchId;

    /**
     * 产品条形码
     */
    private String code;

    /**
     * 生产批次号
     */
    private String batchNo;

    /**
     * 出厂检验编号
     */
    private String inspectNo;

    /**
     * 出厂检验报告等级
     */
    private String level;

    /**
     * 出厂检验报告:样品状态描述
     */
    private String sampleStatusDesc;

    /**
     * 出厂检疫报告:执行标准
     */
    private String executiveStandard;

    /**
     * 出厂检验报告:生产单位(标装)
     */
    private String productUnit;

    /**
     * 出厂检验报告:检验日期
     */
    private String checkTime;

    private String checkTimeBegin;
    private String checkTimeEnd;

    /**
     * 0 不合格 1合格
     */
    private Integer checkResultType;

    /**
     * 出厂检验报告:检验结论
     */
    private String checkResult;

    /**
     * 出厂检验报告:检验人
     */
    private String checkPeople;

    /**
     * 源数据id
     */
    private String oldId;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 创建人
     */
    private Integer createUser;

    /**
     * 修改时间
     */
    private String updateTime;

    /**
     * 修改人
     */
    private Integer updateUser;

    /**
     * 删除状态 1正常 2删除
     */
    private Integer status;

    /**
     * 数据来源 取字典表
     */
    private Integer fromType;

    private String address;
    private String organizationName;
    private Long organizationId;

    /**
     * 出场检验报告图片
     */
    private String inspectImg;

    /**
     * 出厂检验报告
     */
    private String checkItem;

    /**
     * 出厂检验报告:自检检验项目
     */
    private String selfCheckItem;

    /**
     * 出厂检验报告:委托检验项目
     */
    private String commissionCheckItem;

    private String electronicSeal;

    private String electronicName;
    private String productName;
}