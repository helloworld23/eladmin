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
package me.zhengjie.modules.ym.formula.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.ym.formula.domain.YmFormula;
import me.zhengjie.modules.ym.formula.service.YmFormulaService;
import me.zhengjie.modules.ym.formula.service.dto.YmFormulaQueryCriteria;
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
@Api(tags = "配方管理")
@RequestMapping("/api/ymFormula")
public class YmFormulaController {

    private final YmFormulaService ymFormulaService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('ymFormula:list')")
    public void download(HttpServletResponse response, YmFormulaQueryCriteria criteria) throws IOException {
        ymFormulaService.download(ymFormulaService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询配方")
    @ApiOperation("查询配方")
    @PreAuthorize("@el.check('ymFormula:list')")
    public ResponseEntity<Object> query(YmFormulaQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(ymFormulaService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增配方")
    @ApiOperation("新增配方")
    @PreAuthorize("@el.check('ymFormula:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody YmFormula resources){
        return new ResponseEntity<>(ymFormulaService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改配方")
    @ApiOperation("修改配方")
    @PreAuthorize("@el.check('ymFormula:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody YmFormula resources){
        ymFormulaService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除配方")
    @ApiOperation("删除配方")
    @PreAuthorize("@el.check('ymFormula:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Integer[] ids) {
        ymFormulaService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}