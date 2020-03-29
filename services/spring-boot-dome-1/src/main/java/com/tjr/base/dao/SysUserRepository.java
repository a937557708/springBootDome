package com.tjr.base.dao;

import com.tjr.base.Entity.SysResources;
import com.tjr.base.Entity.SysRole;
import com.tjr.base.Entity.SysUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserRepository extends BaseRepository<SysUser, Long> , QuerydslPredicateExecutor<SysUser> {

	@Query(nativeQuery = true, value = "SELECT sr.* " + " FROM sys_role sr\r\n"
			+ "LEFT JOIN sys_user_role sur ON sur.role_id=sr.id" + " where sur.user_id=:userId")
	public List<SysRole> getSysRoleList(@Param("userId") Long userId);

	@Query(nativeQuery = true, value = "SELECT sre.*  " + " FROM sys_resources sre \r\n"
			+ "LEFT JOIN  sys_role_resources srr ON srr.resources_id=sre.id\r\n"
			+ "LEFT JOIN  sys_user_role sur  ON sur.role_id=srr.role_id" + " where sur.user_id=:userId")
	public List<SysResources> getUserSysResourcesList(@Param("userId") Long userId);

	@Query(nativeQuery = true, value = "SELECT sre.* \r\n"
			+ "LEFT JOIN  sys_role_resources srr ON srr.resources_id=sre.id\r\n" + "" + " where srr.role_id=:roleId")
	public List<SysResources> getSysResourcesList(@Param("roleId") Long roleId);

}
