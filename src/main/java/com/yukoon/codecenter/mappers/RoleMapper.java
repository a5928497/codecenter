package com.yukoon.codecenter.mappers;

import com.yukoon.codecenter.entities.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleMapper {

	@Select("SELECT id,role_name FROM roles WHERE id = #{id}")
	public List<Role> findById(Integer id);

	@Select("SELECT id,role_name FROM roles")
	public List<Role> findAll();
}
