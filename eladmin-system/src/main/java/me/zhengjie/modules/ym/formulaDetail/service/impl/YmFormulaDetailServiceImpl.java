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
package me.zhengjie.modules.ym.formulaDetail.service.impl;

import me.zhengjie.modules.ym.formulaDetail.domain.YmFormulaDetail;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.ym.formulaDetail.repository.YmFormulaDetailRepository;
import me.zhengjie.modules.ym.formulaDetail.service.YmFormulaDetailService;
import me.zhengjie.modules.ym.formulaDetail.service.dto.YmFormulaDetailDto;
import me.zhengjie.modules.ym.formulaDetail.service.dto.YmFormulaDetailQueryCriteria;
import me.zhengjie.modules.ym.formulaDetail.service.mapstruct.YmFormulaDetailMapper;
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
public class YmFormulaDetailServiceImpl implements YmFormulaDetailService {

    private final YmFormulaDetailRepository ymFormulaDetailRepository;
    private final YmFormulaDetailMapper ymFormulaDetailMapper;

    @Override
    public Map<String,Object> queryAll(YmFormulaDetailQueryCriteria criteria, Pageable pageable){
        Page<YmFormulaDetail> page = ymFormulaDetailRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(ymFormulaDetailMapper::toDto));
    }

    @Override
    public List<YmFormulaDetailDto> queryAll(YmFormulaDetailQueryCriteria criteria){
        return ymFormulaDetailMapper.toDto(ymFormulaDetailRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public YmFormulaDetailDto findById(Integer pkFormulaDetail) {
        YmFormulaDetail ymFormulaDetail = ymFormulaDetailRepository.findById(pkFormulaDetail).orElseGet(YmFormulaDetail::new);
        ValidationUtil.isNull(ymFormulaDetail.getPkFormulaDetail(),"YmFormulaDetail","pkFormulaDetail",pkFormulaDetail);
        return ymFormulaDetailMapper.toDto(ymFormulaDetail);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public YmFormulaDetailDto create(YmFormulaDetail resources) {
        return ymFormulaDetailMapper.toDto(ymFormulaDetailRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(YmFormulaDetail resources) {
        YmFormulaDetail ymFormulaDetail = ymFormulaDetailRepository.findById(resources.getPkFormulaDetail()).orElseGet(YmFormulaDetail::new);
        ValidationUtil.isNull( ymFormulaDetail.getPkFormulaDetail(),"YmFormulaDetail","id",resources.getPkFormulaDetail());
        ymFormulaDetail.copy(resources);
        ymFormulaDetailRepository.save(ymFormulaDetail);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer pkFormulaDetail : ids) {
            ymFormulaDetailRepository.deleteById(pkFormulaDetail);
        }
    }

    @Override
    public void download(List<YmFormulaDetailDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (YmFormulaDetailDto ymFormulaDetail : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("原料", ymFormulaDetail.getPkMaterial());
            map.put("百分比", ymFormulaDetail.getPercent());
            map.put("实际称料量", ymFormulaDetail.getActAmount());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}