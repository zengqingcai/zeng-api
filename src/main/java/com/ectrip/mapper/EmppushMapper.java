package com.ectrip.mapper;

import com.ectrip.model.Emppush;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmppushMapper {

    Emppush selectById(Integer pid);

    List<Emppush> selectByParams(Emppush emppush);

    Integer insertSelective(Emppush emppush);

    Integer updateByExampleSelective(Emppush emppush);

    Integer updateStatus(Emppush emppush);
}
