package com.ectrip.service;

import com.ectrip.model.Syslog;
import com.ectrip.model.dto.SyslogVO;

import java.util.List;
import java.util.Map;

public interface ISyslogService {

    List<SyslogVO> findPage(Map<String,Object> params, Integer currentPage, Integer pageSize);


    Map<String,Object> findPage(SyslogVO syslogVO);
}
