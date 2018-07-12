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

    //通过record_id查找code
    @Select("SELECT code,status,reward_id,record_id,expiration_date FROM codes WHERE record_id = #{record_id}")
    public List<Code> findAllByRecordId(Integer record_id);

    //根据id更新code并清除flag
    @Update("UPDATE codes SET flag =null,code = #{code} WHERE id = #{id}")
    public void updateCodeAndClearFlag(Code code);
}
