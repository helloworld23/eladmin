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
import java.math.BigDecimal;
import java.util.List;
import me.zhengjie.annotation.Query;

/**
* @website https://el-admin.vip
* @author zhongzm
* @date 2020-12-09
**/
@Data
public class YmMaterialQueryCriteria{

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String materialCode;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String materialName;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String materialInci;

    /** 精确 */
    @Query
    private BigDecimal materialPrice;

    /** 精确 */
    @Query
    private Integer materialType;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String materialProvider;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String materialVender;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String materialEffect;
}