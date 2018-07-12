package com.yukoon.codecenter;

import com.yukoon.codecenter.entities.User;
import com.yukoon.codecenter.mappers.UserMapper;
import com.yukoon.codecenter.services.RoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CodecenterApplicationTests {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RoleService roleService;
	@Test
	public void contextLoads() {
		System.out.println(roleService.findById(1));
	}

}
