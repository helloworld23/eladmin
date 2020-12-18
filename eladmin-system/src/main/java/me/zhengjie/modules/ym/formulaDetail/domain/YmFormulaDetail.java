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
package me.zhengjie.modules.ym.formulaDetail.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author zhongzm
* @date 2020-12-13
**/
@Entity
@Data
@Table(name="ym_formula_detail")
public class YmFormulaDetail implements Serializable {

    @Id
    @Column(name = "pk_formula_detail")
    @ApiModelProperty(value = "pkFormulaDetail")
    private Integer pkFormulaDetail;

    @Column(name = "pk_material",nullable = false)
    @NotNull
    @ApiModelProperty(value = "原料")
    private Integer pkMaterial;

    @Column(name = "percent",nullable = false)
    @NotNull
    @ApiModelProperty(value = "百分比")
    private BigDecimal percent;

    @Column(name = "act_amount",nullable = false)
    @NotNull
    @ApiModelProperty(value = "实际称料量")
    private BigDecimal actAmount;

    public void copy(YmFormulaDetail source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}