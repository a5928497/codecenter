package com.yukoon.codecenter.mappers;

import com.yukoon.codecenter.entities.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

	@Insert("INSERT INTO users(username,password,realName,bank) VALUES (#{username},#{password},#{realName},#{bank})")
	public void addUser(User user);
}
