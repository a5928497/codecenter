package com.yukoon.codecenter.mappers;

import com.yukoon.codecenter.entities.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

	@Insert("INSERT INTO users(username,password,realName,bank,role_id) VALUES (#{username},#{password},#{realName},#{bank},2)")
	public void addUser(User user);

	@Insert("INSERT INTO users(username,password,realName,bank,role_id) VALUES (#{username},#{password},#{realName},#{bank},1)")
	public void addAdmin(User user);

	@Select("SELECT id,username,realName,bank,role_id FROM users")
	public List<User> findAll();

	@Select("SELECT id,username,realName,bank,role_id FROM users WHERE id = #{id}")
	public User findById(Integer id);

	@Select("SELECT id,username,realName,bank,role_id FROM users WHERE realName LIKE #{realName}")
	public List<User> searchByRealName(String realName);

	@Select("SELECT username FROM users WHERE username = #{username}")
	public List<String> isUnique(String username);

	@Select("SELECT id,username,password,realName,bank,role_id FROM users WHERE username = #{username}")
	public User findByUsername(String username);

	@Select("SELECT id,username,password,role_id FROM users WHERE username = #{username}")
	public User login(String username);
}
