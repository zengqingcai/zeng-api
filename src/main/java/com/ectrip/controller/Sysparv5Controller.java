package com.ectrip.controller;

import com.ectrip.ajax.AjaxResult;
import com.ectrip.model.Sysparv5;
import com.ectrip.service.ISysparv5Service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/sysparv5")
public class Sysparv5Controller {
    @Resource
    private ISysparv5Service sysparv5Service;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String List(ModelMap map,Sysparv5 sysparv5,String pmkys) {
        List sysparv5list=sysparv5Service.getSysparv5ByPage(sysparv5,pmkys,1,20);
        if (sysparv5.getSystp()!=null &&sysparv5.getSystp()!="0"){
            map.put("systp",Integer.parseInt(sysparv5.getSystp()));
        }else{
            map.put("systp",0);
        }
        if(!StringUtils.isBlank(sysparv5.getPmky())){
            map.put("sjpmky",sysparv5.getPmky());
        }
        if(!StringUtils.isBlank(sysparv5.getPmcd())){
            map.put("sjpmcd",sysparv5.getPmcd());
        }
        map.put("sysparv5list", sysparv5list);
        return "sysList";
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Object Add(Sysparv5 sysparv5) {
        try{
            return sysparv5Service.addSysparv5(sysparv5);
        }catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("服务器错误","500");
        }
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public Object Update(Sysparv5 sysparv5) {
        try{
            return sysparv5Service.editSysparv5(sysparv5);
        }catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("服务器错误","500");
        }
    }
    @RequestMapping(value = "/delete")
    public String Delete(Sysparv5 sysparv5) {
        sysparv5Service.delSysparv5(sysparv5.getPmky());
        return "redirect:../sysparv5/list";
    }

    @RequestMapping(value = "/sysView")
    public String sysView(ModelMap map,String pmky,String pmcd) {
        Sysparv5 sysparv5 = sysparv5Service.getSysparv5(pmky,pmcd);
        map.put("sysparv5", sysparv5);
        map.put("status","view");
        return "sysView";
    }

    @RequestMapping(value = "/sysEdit")
    public String sysEdit(ModelMap map,String pmky,String pmcd) {
        Sysparv5 sysparv5 = sysparv5Service.getSysparv5(pmky,pmcd);
        map.put("sysparv5", sysparv5);
        map.put("status","edit");
        return "sysView";
    }

    @RequestMapping(value = "/sysAdd")
    public String sysAdd(ModelMap map,Sysparv5 sysparv5) {
        map.put("spmcd",sysparv5.getPmcd());
        map.put("pmky",sysparv5.getPmky());
        map.put("systp",sysparv5.getSystp());
        return "sysAdd";
    }
}
