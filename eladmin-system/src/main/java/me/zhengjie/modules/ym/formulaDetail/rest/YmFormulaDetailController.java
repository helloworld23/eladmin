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
package me.zhengjie.modules.ym.formulaDetail.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.ym.formulaDetail.domain.YmFormulaDetail;
import me.zhengjie.modules.ym.formulaDetail.service.YmFormulaDetailService;
import me.zhengjie.modules.ym.formulaDetail.service.dto.YmFormulaDetailQueryCriteria;
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
* @date 2020-12-13
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "原料管理")
@RequestMapping("/api/ymFormulaDetail")
public class YmFormulaDetailController {

    private final YmFormulaDetailService ymFormulaDetailService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('ymFormulaDetail:list')")
    public void download(HttpServletResponse response, YmFormulaDetailQueryCriteria criteria) throws IOException {
        ymFormulaDetailService.download(ymFormulaDetailService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询原料")
    @ApiOperation("查询原料")
    @PreAuthorize("@el.check('ymFormulaDetail:list')")
    public ResponseEntity<Object> query(YmFormulaDetailQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(ymFormulaDetailService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增原料")
    @ApiOperation("新增原料")
    @PreAuthorize("@el.check('ymFormulaDetail:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody YmFormulaDetail resources){
        return new ResponseEntity<>(ymFormulaDetailService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改原料")
    @ApiOperation("修改原料")
    @PreAuthorize("@el.check('ymFormulaDetail:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody YmFormulaDetail resources){
        ymFormulaDetailService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除原料")
    @ApiOperation("删除原料")
    @PreAuthorize("@el.check('ymFormulaDetail:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Integer[] ids) {
        ymFormulaDetailService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}