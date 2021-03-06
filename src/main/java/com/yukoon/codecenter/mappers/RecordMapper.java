package com.yukoon.codecenter.mappers;

import com.yukoon.codecenter.entities.Record;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface RecordMapper {

	@Insert("INSERT INTO records (total,user_id,reward_id,apply_date,flag) VALUES (#{total},#{user_id},#{reward_id},#{apply_date},#{flag})")
	public void addRecord(Record record);

	@Select("SELECT * FROM records WHERE flag = #{flag}")
	public Record findByFlag(int flag);

	@Select("SELECT * FROM records WHERE id = #{id}")
	public Record findById(Integer id);

	@Select("SELECT * FROM records WHERE user_id = #{user_id}")
	public List<Record> findByUserid(Integer user_id);

	@Select("SELECT * FROM records")
	public List<Record> findAll();

	@Update("UPDATE records SET flag = null")
	public void clearFlag();
}
