package com.ectrip.service.impl;

import com.ectrip.mapper.EmppushMapper;
import com.ectrip.model.Emppush;
import com.ectrip.service.IEmppushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmppushService implements IEmppushService{

    @Autowired
    private EmppushMapper emppushMapper;

    @Override
    public Emppush selectById(Integer pid) {
        return emppushMapper.selectById(pid);
    }

    @Override
    public List<Emppush> selectByParams(Emppush emppush) {
        return emppushMapper.selectByParams(emppush);
    }

    @Override
    public Integer insertSelective(Emppush emppush) {
        return emppushMapper.insertSelective(emppush);
    }

    @Override
    public Integer updateByExampleSelective(Emppush emppush) {
        return emppushMapper.updateByExampleSelective(emppush);
    }

    @Override
    public Integer updateStatus(Emppush emppush) {
        return emppushMapper.updateStatus(emppush);
    }
}
