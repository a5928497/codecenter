package com.yukoon.codecenter.services;

import com.yukoon.codecenter.mappers.PermMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermService {
	@Autowired
	private PermMapper permMapper;

	public List<String> findByRoleId(Integer role_id) {
		return permMapper.findByRoleId(role_id);
	}
}
