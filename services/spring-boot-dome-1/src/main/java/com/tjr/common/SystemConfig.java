package com.tjr.common;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.tjr.Entity.SysResources;
import com.tjr.Entity.SysRole;
import com.tjr.Entity.SysUser;
import com.tjr.dao.SysUserRepository;

@Component
public class SystemConfig {

	@Autowired
	private Environment env;

	@Autowired
	private SysUserRepository userRepository;
	public static List<SysUser> sysUserList;

	@PostConstruct
	public void readConfig() {
		sysUserList = userRepository.findAll();
		for (SysUser sysUser : sysUserList) {
			Long userId=sysUser.getId();
			List<SysRole> roles=userRepository.getSysRoleList(userId);
			sysUser.setSysRoles(roles);
			List<SysResources> sysResources=userRepository.getUserSysResourcesList(userId);
			sysUser.setSysResources(sysResources);;
		}
	}

}
