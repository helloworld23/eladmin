/*
*  Copyright 2019-2020 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package me.zhengjie.modules.ym.formula.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author zhongzm
* @date 2020-12-13
**/
@Entity
@Data
@Table(name="ym_formula")
public class YmFormula implements Serializable {

    @Id
    @Column(name = "pk_formula")
    @ApiModelProperty(value = "pkFormula")
    private Integer pkFormula;

    @Column(name = "formula_name",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "配方名称")
    private String formulaName;

    @Column(name = "formula_code",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "配方编码")
    private String formulaCode;

    @Column(name = "create_time")
    @ApiModelProperty(value = "生产日期")
    private Timestamp createTime;

    @Column(name = "formula_amount",nullable = false)
    @NotNull
    @ApiModelProperty(value = "生产数量")
    private Integer formulaAmount;

    @Column(name = "update_time")
    @ApiModelProperty(value = "更新日期")
    private Timestamp updateTime;

    @Column(name = "create_by")
    @ApiModelProperty(value = "创建人")
    private Integer createBy;

    @Column(name = "update_by")
    @ApiModelProperty(value = "更信任")
    private Integer updateBy;

    public void copy(YmFormula source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}