package com.yukoon.codecenter.realms;

import com.yukoon.codecenter.entities.User;
import com.yukoon.codecenter.services.PermService;
import com.yukoon.codecenter.services.RoleService;
import com.yukoon.codecenter.services.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class UserRealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PermService permService;

	//认证的方法
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		//1. 把 AuthenticationToken 转换为 UsernamePasswordToken
		UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
		//2. 从 UsernamePasswordToken 中获取 username,password构建user
		String username = upToken.getUsername();
		System.out.println(upToken.getPassword());
		User user = new User().setUsername(username).setPassword(String.valueOf(upToken.getPassword()));
		//3. 从数据库获取User准备进行比对
		User user_temp = userService.login(user);
		//4. 异常用户抛出异常
		if (user_temp == null) {
			throw new UnknownAccountException("用户不存在或用户密码错误!");
		}
		//5. 根据用户的情况, 来构建 AuthenticationInfo 对象并返回.
		Object principal = user_temp.getUsername();
		ByteSource credentialsSalt = ByteSource.Util.bytes(user_temp.getUsername());
		Object credentials = new SimpleHash("MD5", upToken.getCredentials(),credentialsSalt, 1024);
		String realmName = getName();
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal,credentials,credentialsSalt,realmName);
		return info;
	}

	//授权方法
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		//1. 从 principalCollection 中来获取登录用户的信息
		Object principal = principalCollection.getPrimaryPrincipal();
		//2. 利用登录的用户的信息来当前用户的角色
		User user_temp = userService.findByUsername(principal.toString());
		//3. 创建 SimpleAuthorizationInfo, 并设置其 roles 属性并返回
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		Set<String> roles = new HashSet<>();
		Integer role_id  = user_temp.getRole_id();
		roles.add(roleService.findById(role_id).getRoleName());
		info.addRoles(roles);
		for (String perm: permService.findByRoleId(role_id)) {
			info.addStringPermission(perm);
		}
		return info;
	}


}
