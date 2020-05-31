package com.tjr.common;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tjr.base.Entity.SysResources;
import com.tjr.base.Entity.SysRole;
import com.tjr.base.Entity.SysUser;
import com.tjr.base.dao.SysUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * 重启 赋值 初始化
 */
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

	@Autowired
	@PersistenceContext
	public EntityManager entityManager;

	public static JPAQueryFactory queryFactory;

	@PostConstruct
	public void init() {
		queryFactory = new JPAQueryFactory(entityManager);
	}

}
