package com.ectrip.mapper;


import com.ectrip.model.Syslog;
import com.ectrip.model.dto.SyslogVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SyslogMapper {
    int insert(Syslog record);

    int insertSelective(Syslog record);

    List<SyslogVO> findPage(Map<String,Object> params);
}