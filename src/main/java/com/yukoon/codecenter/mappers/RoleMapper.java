package com.yukoon.codecenter.mappers;

import com.yukoon.codecenter.entities.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RoleMapper {

	@Select("SELECT * FROM roles WHERE id = #{id}")
	public Role findById(Integer id);
}
