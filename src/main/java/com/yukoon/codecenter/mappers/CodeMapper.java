package com.yukoon.codecenter.mappers;

import com.yukoon.codecenter.entities.Code;
import com.yukoon.codecenter.providers.CodeMapperProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CodeMapper {

    //批量插入
    @InsertProvider(type = CodeMapperProvider.class,method = "insertAll")
    public void insertAll(@Param("list")List<Code> users);
}
