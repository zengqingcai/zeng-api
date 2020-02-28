package com.ectrip.service;

import com.ectrip.model.Emppush;

import java.util.List;

public interface IEmppushService {

    Emppush selectById(Integer pid);

    List<Emppush> selectByParams(Emppush emppush);

    Integer insertSelective(Emppush emppush);

    Integer updateByExampleSelective(Emppush emppush);


    Integer updateStatus(Emppush emppush);
}
