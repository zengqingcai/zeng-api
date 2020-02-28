package com.ectrip.mapper;

import com.ectrip.model.Sysparv5;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface Sysparv5Mapper {
    //新增
    int insertSelective(Sysparv5 sysparv5);

    //删除
    void delSysparv5(String pmky);

    //修改
    void editSysparv5(Sysparv5 sysparv5);

    //根据ID读取信息
    Sysparv5 getSysparv5(String pmky,String pmcd);

    //根据条件分页获取信息
    List<Sysparv5> getSysparv5ByPage(Map<String, Object> map);

    List<Sysparv5> getSysList(String pmky);

    List<Sysparv5> getSystpList(String systp);

    List<Sysparv5> getPmkyList(String pmky);
}