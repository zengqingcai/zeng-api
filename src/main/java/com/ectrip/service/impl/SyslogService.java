package com.ectrip.service.impl;

import com.ectrip.ajax.CommonPage;
import com.ectrip.mapper.SyslogMapper;
import com.ectrip.model.Employees;
import com.ectrip.model.Syslog;
import com.ectrip.model.dto.SyslogVO;
import com.ectrip.service.ISyslogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SyslogService implements ISyslogService {

    @Autowired
    private SyslogMapper syslogMapper;

    @Override
    public List<SyslogVO> findPage(Map<String, Object> params, Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        return syslogMapper.findPage(params);
    }

    @Override
    public Map<String, Object> findPage(SyslogVO syslogVO) {
        Map<String, Object> result = new HashMap<>();
        PageHelper.startPage(syslogVO.getCurrentPage(), syslogVO.getPageSize());
        List<SyslogVO> list = syslogMapper.findPage(null);
        PageInfo<Employees> pageInfo = new PageInfo(list);
        CommonPage commonPage = new CommonPage();
        commonPage.setCurrentPage(pageInfo.getPageNum());
        commonPage.setPages(pageInfo.getPages());
        commonPage.setTotals(pageInfo.getTotal());
        commonPage.setPageSize(pageInfo.getPageSize());
        result.put("page",commonPage);
        result.put("logList",pageInfo.getList());
        return result;
    }
}
