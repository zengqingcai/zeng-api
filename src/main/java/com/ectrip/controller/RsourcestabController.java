package com.ectrip.controller;

import com.ectrip.model.dto.ResourcesVO;
import com.ectrip.service.IRsourcestabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/resource")
public class RsourcestabController {

    @Autowired
    private IRsourcestabService rsourcestabService;

    @RequestMapping(value = "/resouceList")
    public String showAllEmployees(ModelMap map) {
        List<ResourcesVO> list=rsourcestabService.queryResourcePage(null,1,10);
        map.put("resouces", list);
        return "resourceList";
    }

}
