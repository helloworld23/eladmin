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
package me.zhengjie.modules.ym.material.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.ym.material.domain.YmMaterial;
import me.zhengjie.modules.ym.material.service.YmMaterialService;
import me.zhengjie.modules.ym.material.service.dto.YmMaterialQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @author zhongzm
* @date 2020-12-09
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "原料管理")
@RequestMapping("/api/ymMaterial")
public class YmMaterialController {

    private final YmMaterialService ymMaterialService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('material:list')")
    public void download(HttpServletResponse response, YmMaterialQueryCriteria criteria) throws IOException {
        ymMaterialService.download(ymMaterialService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询原料")
    @ApiOperation("查询原料")
    @PreAuthorize("@el.check('material:list')")
    public ResponseEntity<Object> query(YmMaterialQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(ymMaterialService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增原料")
    @ApiOperation("新增原料")
    @PreAuthorize("@el.check('material:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody YmMaterial resources){
        return new ResponseEntity<>(ymMaterialService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改原料")
    @ApiOperation("修改原料")
    @PreAuthorize("@el.check('material:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody YmMaterial resources){
        ymMaterialService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除原料")
    @ApiOperation("删除原料")
    @PreAuthorize("@el.check('material:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Integer[] ids) {
        ymMaterialService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}