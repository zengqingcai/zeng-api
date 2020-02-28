package com.ectrip.service.impl;

import com.ectrip.mapper.RsourcestabMapper;
import com.ectrip.model.Rsourcestab;
import com.ectrip.model.dto.ResourcesVO;
import com.ectrip.service.IRsourcestabService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RsourcestabService implements IRsourcestabService {

    @Autowired
    private RsourcestabMapper rsourcestabMapper;

    @Override
    public List<ResourcesVO> queryResourcePage(Map<String, Object> params,int page,int pageSize) {
        PageHelper.startPage(page, pageSize);
        return rsourcestabMapper.queryResourcePage(params);
    }

    @Override
    public List<Rsourcestab> getRsourcesByEmp(Integer employeeid){
        return rsourcestabMapper.getRsourcesByEmp(employeeid);
    }

    @Override
    public void editResource(Rsourcestab rsourcestab){
        rsourcestabMapper.editResource(rsourcestab);
    }
}
