package com.ectrip.controller;

import com.ectrip.model.Syslog;
import com.ectrip.model.dto.SyslogVO;
import com.ectrip.service.ISyslogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/syslog")
public class SyslogController {

    @Autowired
    private ISyslogService syslogService;

    /**
     * 日志列表
     * @param map
     * @return
     */
    @RequestMapping(value = "/logList")
    public String logList(ModelMap map,SyslogVO syslogVO) {
        Map<String,Object> result = syslogService.findPage(syslogVO);
        map.put("logList", result.get("logList"));
        map.put("page",result.get("page"));
        return "logList";
    }
}
