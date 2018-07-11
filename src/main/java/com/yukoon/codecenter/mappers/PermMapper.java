package com.yukoon.codecenter.mappers;

import com.yukoon.codecenter.entities.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PermMapper {

	@Select("SELECT perm_name FROM perms WHERE role_id = #{role_id}")
	public List<String> findByRoleId(Integer role_id);
}
