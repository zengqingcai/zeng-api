package com.ectrip.service;


import com.ectrip.model.Rsourcestab;
import com.ectrip.model.dto.ResourcesVO;

import java.util.List;
import java.util.Map;

public interface IRsourcestabService {

    List<ResourcesVO> queryResourcePage(Map<String,Object> params,int page,int pageSize);

    List<Rsourcestab> getRsourcesByEmp(Integer employeeid);

    void editResource(Rsourcestab rsourcestab);
}
