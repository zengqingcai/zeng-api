package com.ectrip.service;

import com.ectrip.model.Sysparv5;

import java.util.List;

public interface ISysparv5Service {
    //新增用户
    Object addSysparv5(Sysparv5 sysparv5);

    //根据用户ID删除用户
    void delSysparv5(String pmky);

    //修改用户
    Object editSysparv5(Sysparv5 sysparv5);

    //根据用户ID读取用户信息
    Sysparv5 getSysparv5(String pmky,String pmcd);

    //根据条件分页获取用户
    List<Sysparv5> getSysparv5ByPage(Sysparv5 sysparv5,String pmkys,int page, int pageSize);

    List<Sysparv5> getSysList(String pmky);
}
