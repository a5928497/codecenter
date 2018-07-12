package com.yukoon.codecenter.services;

import com.yukoon.codecenter.entities.Role;
import com.yukoon.codecenter.mappers.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleService {

	@Autowired
	private RoleMapper roleMapper;

	@Transactional
	public List<Role> findById(Integer id) {
		return roleMapper.findById(id);
	}

	@Transactional
	public List<Role> findAll() {
		return roleMapper.findAll();
	}
}
