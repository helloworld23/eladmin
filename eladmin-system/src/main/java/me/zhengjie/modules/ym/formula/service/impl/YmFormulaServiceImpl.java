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
package me.zhengjie.modules.ym.formula.service.impl;

import me.zhengjie.modules.ym.formula.domain.YmFormula;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.ym.formula.repository.YmFormulaRepository;
import me.zhengjie.modules.ym.formula.service.YmFormulaService;
import me.zhengjie.modules.ym.formula.service.dto.YmFormulaDto;
import me.zhengjie.modules.ym.formula.service.dto.YmFormulaQueryCriteria;
import me.zhengjie.modules.ym.formula.service.mapstruct.YmFormulaMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author zhongzm
* @date 2020-12-13
**/
@Service
@RequiredArgsConstructor
public class YmFormulaServiceImpl implements YmFormulaService {

    private final YmFormulaRepository ymFormulaRepository;
    private final YmFormulaMapper ymFormulaMapper;

    @Override
    public Map<String,Object> queryAll(YmFormulaQueryCriteria criteria, Pageable pageable){
        Page<YmFormula> page = ymFormulaRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(ymFormulaMapper::toDto));
    }

    @Override
    public List<YmFormulaDto> queryAll(YmFormulaQueryCriteria criteria){
        return ymFormulaMapper.toDto(ymFormulaRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public YmFormulaDto findById(Integer pkFormula) {
        YmFormula ymFormula = ymFormulaRepository.findById(pkFormula).orElseGet(YmFormula::new);
        ValidationUtil.isNull(ymFormula.getPkFormula(),"YmFormula","pkFormula",pkFormula);
        return ymFormulaMapper.toDto(ymFormula);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public YmFormulaDto create(YmFormula resources) {
        return ymFormulaMapper.toDto(ymFormulaRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(YmFormula resources) {
        YmFormula ymFormula = ymFormulaRepository.findById(resources.getPkFormula()).orElseGet(YmFormula::new);
        ValidationUtil.isNull( ymFormula.getPkFormula(),"YmFormula","id",resources.getPkFormula());
        ymFormula.copy(resources);
        ymFormulaRepository.save(ymFormula);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer pkFormula : ids) {
            ymFormulaRepository.deleteById(pkFormula);
        }
    }

    @Override
    public void download(List<YmFormulaDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (YmFormulaDto ymFormula : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("配方名称", ymFormula.getFormulaName());
            map.put("配方编码", ymFormula.getFormulaCode());
            map.put("生产日期", ymFormula.getCreateTime());
            map.put("生产数量", ymFormula.getFormulaAmount());
            map.put("更新日期", ymFormula.getUpdateTime());
            map.put("创建人", ymFormula.getCreateBy());
            map.put("更信任", ymFormula.getUpdateBy());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}