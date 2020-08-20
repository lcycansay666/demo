/**
 * @Auther: PC-8
 * @Date: 2020/1/9 09:03
 * @Description:
 */
package com.example.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: lcy
 * @Date: 2020/1/9 09:03
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TestVo extends BaseRowModel {
    @ExcelProperty(value = "名字", index = 0)
    private String name;
    /*@ExcelProperty(value = "监管局名字", index = 1)
    private String supName;*/

    @ExcelProperty(value = "名字1", index = 1)
    private String name1;

    @ExcelProperty(value = "名字2", index = 2)
    private String name2;

}
