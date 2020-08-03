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
package me.zhengjie.modules.credit.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.credit.domain.BusiCredit;
import me.zhengjie.modules.credit.service.BusiCreditService;
import me.zhengjie.modules.credit.service.dto.BusiCreditQueryCriteria;
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
* @date 2020-08-03
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "credit管理")
@RequestMapping("/api/busiCredit")
public class BusiCreditController {

    private final BusiCreditService busiCreditService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('busiCredit:list')")
    public void download(HttpServletResponse response, BusiCreditQueryCriteria criteria) throws IOException {
        busiCreditService.download(busiCreditService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询credit")
    @ApiOperation("查询credit")
    @PreAuthorize("@el.check('busiCredit:list')")
    public ResponseEntity<Object> query(BusiCreditQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(busiCreditService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增credit")
    @ApiOperation("新增credit")
    @PreAuthorize("@el.check('busiCredit:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody BusiCredit resources){
        return new ResponseEntity<>(busiCreditService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改credit")
    @ApiOperation("修改credit")
    @PreAuthorize("@el.check('busiCredit:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody BusiCredit resources){
        busiCreditService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除credit")
    @ApiOperation("删除credit")
    @PreAuthorize("@el.check('busiCredit:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        busiCreditService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}