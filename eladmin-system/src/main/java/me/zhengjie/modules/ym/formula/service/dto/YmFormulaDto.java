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
package me.zhengjie.modules.ym.formula.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author zhongzm
* @date 2020-12-13
**/
@Data
public class YmFormulaDto implements Serializable {

    private Integer pkFormula;

    /** 配方名称 */
    private String formulaName;

    /** 配方编码 */
    private String formulaCode;

    /** 生产日期 */
    private Timestamp createTime;

    /** 生产数量 */
    private Integer formulaAmount;

    /** 更新日期 */
    private Timestamp updateTime;

    /** 创建人 */
    private Integer createBy;

    /** 更信任 */
    private Integer updateBy;
}