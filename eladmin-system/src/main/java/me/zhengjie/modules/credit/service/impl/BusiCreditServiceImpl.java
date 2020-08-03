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
package me.zhengjie.modules.credit.service.impl;

import me.zhengjie.modules.credit.domain.BusiCredit;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.credit.repository.BusiCreditRepository;
import me.zhengjie.modules.credit.service.BusiCreditService;
import me.zhengjie.modules.credit.service.dto.BusiCreditDto;
import me.zhengjie.modules.credit.service.dto.BusiCreditQueryCriteria;
import me.zhengjie.modules.credit.service.mapstruct.BusiCreditMapper;
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
* @date 2020-08-03
**/
@Service
@RequiredArgsConstructor
public class BusiCreditServiceImpl implements BusiCreditService {

    private final BusiCreditRepository busiCreditRepository;
    private final BusiCreditMapper busiCreditMapper;

    @Override
    public Map<String,Object> queryAll(BusiCreditQueryCriteria criteria, Pageable pageable){
        Page<BusiCredit> page = busiCreditRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(busiCreditMapper::toDto));
    }

    @Override
    public List<BusiCreditDto> queryAll(BusiCreditQueryCriteria criteria){
        return busiCreditMapper.toDto(busiCreditRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public BusiCreditDto findById(Long creditId) {
        BusiCredit busiCredit = busiCreditRepository.findById(creditId).orElseGet(BusiCredit::new);
        ValidationUtil.isNull(busiCredit.getCreditId(),"BusiCredit","creditId",creditId);
        return busiCreditMapper.toDto(busiCredit);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BusiCreditDto create(BusiCredit resources) {
        return busiCreditMapper.toDto(busiCreditRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(BusiCredit resources) {
        BusiCredit busiCredit = busiCreditRepository.findById(resources.getCreditId()).orElseGet(BusiCredit::new);
        ValidationUtil.isNull( busiCredit.getCreditId(),"BusiCredit","id",resources.getCreditId());
        busiCredit.copy(resources);
        busiCreditRepository.save(busiCredit);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long creditId : ids) {
            busiCreditRepository.deleteById(creditId);
        }
    }

    @Override
    public void download(List<BusiCreditDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (BusiCreditDto busiCredit : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("名称", busiCredit.getName());
            map.put("原因", busiCredit.getReason());
            map.put("金额", busiCredit.getMoney());
            map.put("创建时间", busiCredit.getCreateTime());
            map.put("更新时间", busiCredit.getUpdateTime());
            map.put("支付状态", busiCredit.getStatus());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}