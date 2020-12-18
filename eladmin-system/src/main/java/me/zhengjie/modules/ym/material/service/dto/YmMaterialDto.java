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
package me.zhengjie.modules.ym.material.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author zhongzm
* @date 2020-12-09
**/
@Data
public class YmMaterialDto implements Serializable {

    private Integer pkMaterial;

    private String materialCode;

    private String materialName;

    private String materialInci;

    private BigDecimal materialPrice;

    private Integer materialType;

    private String materialProvider;

    private String materialVender;

    private String materialEffect;

    private Timestamp createTime;

    private Timestamp updateTime;
}