package com.yukoon.codecenter.mappers;

import com.yukoon.codecenter.entities.Code;
import com.yukoon.codecenter.providers.CodeMapperProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CodeMapper {

    //批量插入
    @InsertProvider(type = CodeMapperProvider.class,method = "insertAll")
    public void insertAll(@Param("list")List<Code> users);

    //通过flag查找code
    @Select("SELECT * FROM codes WHERE flag = #{flag}")
    public List<Code> findByFlag(int flag);

    //根据id更新code并清除flag
    @Update("UPDATE codes SET flag =null,code = #{code} WHERE id = #{id}")
    public void updateCodeAndClearFlag(Code code);
}
