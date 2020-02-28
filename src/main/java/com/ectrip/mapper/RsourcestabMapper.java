package com.ectrip.mapper;


import com.ectrip.model.Rsourcestab;
import com.ectrip.model.dto.ResourcesVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RsourcestabMapper {
    int insert(Rsourcestab record);

    int insertSelective(Rsourcestab record);

    List<ResourcesVO> queryResourcePage(Map<String,Object> params);

    List<Rsourcestab> getRsourcesByEmp(Integer employeeid);

    int editResource(Rsourcestab rsourcestab);
}