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
package me.zhengjie.modules.ym.material.service.impl;

import me.zhengjie.modules.ym.material.domain.YmMaterial;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.ym.material.repository.YmMaterialRepository;
import me.zhengjie.modules.ym.material.service.YmMaterialService;
import me.zhengjie.modules.ym.material.service.dto.YmMaterialDto;
import me.zhengjie.modules.ym.material.service.dto.YmMaterialQueryCriteria;
import me.zhengjie.modules.ym.material.service.mapstruct.YmMaterialMapper;
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
* @date 2020-12-09
**/
@Service
@RequiredArgsConstructor
public class YmMaterialServiceImpl implements YmMaterialService {

    private final YmMaterialRepository ymMaterialRepository;
    private final YmMaterialMapper ymMaterialMapper;

    @Override
    public Map<String,Object> queryAll(YmMaterialQueryCriteria criteria, Pageable pageable){
        Page<YmMaterial> page = ymMaterialRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(ymMaterialMapper::toDto));
    }

    @Override
    public List<YmMaterialDto> queryAll(YmMaterialQueryCriteria criteria){
        return ymMaterialMapper.toDto(ymMaterialRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public YmMaterialDto findById(Integer pkMaterial) {
        YmMaterial ymMaterial = ymMaterialRepository.findById(pkMaterial).orElseGet(YmMaterial::new);
        ValidationUtil.isNull(ymMaterial.getPkMaterial(),"YmMaterial","pkMaterial",pkMaterial);
        return ymMaterialMapper.toDto(ymMaterial);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public YmMaterialDto create(YmMaterial resources) {
        return ymMaterialMapper.toDto(ymMaterialRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(YmMaterial resources) {
        YmMaterial ymMaterial = ymMaterialRepository.findById(resources.getPkMaterial()).orElseGet(YmMaterial::new);
        ValidationUtil.isNull( ymMaterial.getPkMaterial(),"YmMaterial","id",resources.getPkMaterial());
        ymMaterial.copy(resources);
        ymMaterialRepository.save(ymMaterial);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer pkMaterial : ids) {
            ymMaterialRepository.deleteById(pkMaterial);
        }
    }

    @Override
    public void download(List<YmMaterialDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (YmMaterialDto ymMaterial : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" materialCode",  ymMaterial.getMaterialCode());
            map.put(" materialName",  ymMaterial.getMaterialName());
            map.put(" materialInci",  ymMaterial.getMaterialInci());
            map.put(" materialPrice",  ymMaterial.getMaterialPrice());
            map.put(" materialType",  ymMaterial.getMaterialType());
            map.put(" materialProvider",  ymMaterial.getMaterialProvider());
            map.put(" materialVender",  ymMaterial.getMaterialVender());
            map.put(" materialEffect",  ymMaterial.getMaterialEffect());
            map.put(" createTime",  ymMaterial.getCreateTime());
            map.put(" updateTime",  ymMaterial.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}