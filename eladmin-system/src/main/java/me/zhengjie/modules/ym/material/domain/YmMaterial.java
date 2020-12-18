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
package me.zhengjie.modules.ym.material.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author zhongzm
* @date 2020-12-09
**/
@Entity
@Data
@Table(name="ym_material")
public class YmMaterial implements Serializable {

    @Id
    @Column(name = "pk_material")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "pkMaterial")
    private Integer pkMaterial;

    @Column(name = "material_code")
    @ApiModelProperty(value = "materialCode")
    private String materialCode;

    @Column(name = "material_name")
    @ApiModelProperty(value = "materialName")
    private String materialName;

    @Column(name = "material_inci")
    @ApiModelProperty(value = "materialInci")
    private String materialInci;

    @Column(name = "material_price")
    @ApiModelProperty(value = "materialPrice")
    private BigDecimal materialPrice;

    @Column(name = "material_type")
    @ApiModelProperty(value = "materialType")
    private Integer materialType;

    @Column(name = "material_provider")
    @ApiModelProperty(value = "materialProvider")
    private String materialProvider;

    @Column(name = "material_vender")
    @ApiModelProperty(value = "materialVender")
    private String materialVender;

    @Column(name = "material_effect")
    @ApiModelProperty(value = "materialEffect")
    private String materialEffect;

    @Column(name = "create_time")
    @ApiModelProperty(value = "createTime")
    private Timestamp createTime;

    @Column(name = "update_time")
    @ApiModelProperty(value = "updateTime")
    private Timestamp updateTime;

    public void copy(YmMaterial source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}