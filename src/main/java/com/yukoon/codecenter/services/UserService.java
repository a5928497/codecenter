package com.yukoon.codecenter.services;

import com.yukoon.codecenter.entities.Page;
import com.yukoon.codecenter.entities.User;
import com.yukoon.codecenter.mappers.UserMapper;
import com.yukoon.codecenter.utils.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;

	//检查用户名是否唯一
	@Transactional
	public boolean isUnique(String username) {
		boolean flag = false;
		if (userMapper.isUnique(username).size() < 1) {
			flag = true;
		}
		return flag;
	}

	//添加经办者
	@Transactional
	public void addUser(User user) {
		userMapper.addUser(user);
	}

	//添加管理员
	@Transactional
	public void addAdmin(User user) {
		userMapper.addAdmin(user);
	}

	//根据用户名传入用户名获取数据进行比对，若不正确，返回null
	@Transactional
	public User login(User user) {
		User userFromDB  = userMapper.login(user.getUsername());
		if (null != userFromDB &&user.getPassword().equals(userFromDB.getPassword())) {
			return userFromDB;
		}else {
			return null;
		}
	}

	//根据用户名获取用户对象，不带password
	@Transactional
	public User findByUsername(String username) {
		User userFromDB = userMapper.findByUsername(username);
		return userFromDB;
	}

	//分页查找所有用户信息
	@Transactional
	public Page findAll(Integer pageNo, Integer pageSize) {
		return PageableUtil.page(pageNo,pageSize,userMapper.findAll());
	}

	//分页按姓名搜索用户信息
	@Transactional
	public Page seearchByRealName(Integer pageNo, Integer pageSize,String realName) {
		realName = "%" + realName + "%";
		return PageableUtil.page(pageNo,pageSize,userMapper.searchByRealName(realName));
	}

	//根据Id查找客户
	@Transactional
	public User findByid(Integer id) {
		return userMapper.findById(id);
	}

	//更新客户资料
	@Transactional
	public void updateUser(User user)  {
		userMapper.updateUser(user);
	}

	//重置用户密码
	@Transactional
	public void resetPsw(User user)  {
		userMapper.resetPsw(user);
	}

	//删除用户
	@Transactional
	public void delete(Integer id) {
		userMapper.delUser(id);
	}

	//获得用户id
	@Transactional
	public Integer findIdByUsername(String username) {
		return userMapper.findIdByUsername(username);
	}
}
