package com.tjr.common.shiro;

import com.tjr.base.Entity.SysResources;
import com.tjr.base.Entity.SysRole;
import com.tjr.base.Entity.SysUser;
import com.tjr.common.SystemConfig;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 * created by huxm on 2018/6/4
 */
public class ShiroRealm extends AuthorizingRealm {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		logger.error("doGetAuthorizationInfo+" + principalCollection.toString());

		SysUser userInfo = (SysUser) principalCollection.getPrimaryPrincipal();

		// 把principals放session中 key=userId value=principals
		SecurityUtils.getSubject().getSession().setAttribute(String.valueOf(userInfo.getId()),
				SecurityUtils.getSubject().getPrincipals());

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

		List<SysResources> sysResources = userInfo.getSysResources();
		List<SysRole> sysRoles = userInfo.getSysRoles();

		for (SysRole role : sysRoles) {
			info.addRole(role.getName());
		}
		for (SysResources resources : sysResources) {
			info.addStringPermission(resources.getPermission());
		}
		info.addStringPermission("swagger.v2.api-docs");
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		// 加这一步的目的是在Post请求的时候会先进认证，然后在到请求
		if (authenticationToken.getPrincipal() == null) {
			return null;
		}
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		// 获取用户信息
		String username = authenticationToken.getPrincipal().toString();
		List<SysUser> sysUserList = SystemConfig.sysUserList;
		Optional<SysUser> opt = sysUserList.stream().filter(o -> username.equalsIgnoreCase(o.getUsername()))
				.findFirst();
		if (!opt.isPresent()) {
			// 这里返回后会报出对应异常
			throw new AccountException("不存在此用户");
		}
		SysUser userInfo = opt.get();
		// 获取盐值，即用户名
		ByteSource salt = ByteSource.Util.bytes(username);
		// 这里验证authenticationToken和simpleAuthenticationInfo的信息
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo, //用户名
                userInfo.getPassword(), //密码
                getName()  //realm name
        );
		return authenticationInfo;

	}

}
