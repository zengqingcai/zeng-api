package com.ectrip.service.impl;


import com.ectrip.ajax.AjaxResult;
import com.ectrip.mapper.EmployeesMapper;
import com.ectrip.mapper.Sysparv5Mapper;
import com.ectrip.model.Employees;
import com.ectrip.model.Sysparv5;
import com.ectrip.service.IEmployeesService;
import com.ectrip.service.ISysparv5Service;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Sysparv5Service implements ISysparv5Service {

    @Autowired
    private Sysparv5Mapper sysparv5Mapper;


    //新增
    @Override
    public Object addSysparv5(Sysparv5 sysparv5){
        StringBuffer error = new StringBuffer("");
        if (StringUtils.isBlank(sysparv5.getPmky())) {
            error.append("参数KEY不能为空;");
        }
        if (StringUtils.isBlank(sysparv5.getPmcd())){
            error.append("参数码不能为空;");
        }
        if (StringUtils.isBlank(sysparv5.getSystp())){
            error.append("参数级别不能为空");
        }
        if (StringUtils.isBlank(sysparv5.getPmva())){
            error.append("参数A不能为空");
        }
        if (sysparv5.getSystp().equals("0")){
            List<Sysparv5> sysparv5List=sysparv5Mapper.getSystpList(sysparv5.getSystp());
            if (sysparv5List!=null){
                for (int i=0;i<sysparv5List.size();i++) {
                    if (sysparv5.getPmky() .equals(sysparv5List.get(i).getPmky())) {
                        error.append("顶级参数KEY不能相同");
                    }
                }
            }
        }
       if (!sysparv5.getSystp().equals("0")){
           List<Sysparv5> sysparv5List=sysparv5Mapper.getPmkyList(sysparv5.getPmky());
           if (sysparv5List!=null){
               for (int i=0;i<sysparv5List.size();i++){
                   if (sysparv5.getPmcd().equals(sysparv5List.get(i).getPmcd())){
                       error.append("同一参数下参数码不能相同");
                   }
               }
           }
       }
        if (!StringUtils.isBlank(error.toString())) {
            return AjaxResult.error(error.toString().substring(0, error.length() - 1), "400");
        }else {
            sysparv5Mapper.insertSelective(sysparv5);
            return new AjaxResult(true, "200", "用户修改成功");
        }
    }

    //删除
    @Override
    public void delSysparv5(String pmky){
        sysparv5Mapper.delSysparv5(pmky);
    }

    //修改
    @Override
    public Object editSysparv5(Sysparv5 sysparv5){
        StringBuffer error = new StringBuffer("");
        if (StringUtils.isBlank(sysparv5.getPmky())) {
            error.append("参数KEY不能为空;");
        }
        if (StringUtils.isBlank(sysparv5.getPmcd())){
            error.append("参数码不能为空;");
        }
        if (StringUtils.isBlank(sysparv5.getPmva())){
            error.append("参数A不能为空");
        }
        if (!StringUtils.isBlank(error.toString())) {
            return AjaxResult.error(error.toString().substring(0, error.length() - 1), "400");
        }else {
            sysparv5Mapper.editSysparv5(sysparv5);
            return new AjaxResult(true, "200", "用户修改成功");
        }
    }

    //根据ID读取信息
    @Override
    public Sysparv5 getSysparv5(String pmky,String pmcd){
        return sysparv5Mapper.getSysparv5(pmky,pmcd);
    }

    //根据条件分页获取信息
    @Override
    public List<Sysparv5> getSysparv5ByPage(Sysparv5 sysparv5,String pmkys,int page,int pageSize){
        PageHelper.startPage(page, pageSize);
        Map<String, Object > map=new HashMap<String, Object>();
        map.put("pmky", sysparv5.getPmky());
        map.put("systp",sysparv5.getSystp());
        map.put("pmkys", pmkys);
        List<Sysparv5> sysparv5List = sysparv5Mapper.getSysparv5ByPage(map);
        return sysparv5List;
    }
    public List<Sysparv5> getSysList(String pmky){
        List<Sysparv5> sysparv5List = sysparv5Mapper.getSysList(pmky);
        return sysparv5List;
    }
}
