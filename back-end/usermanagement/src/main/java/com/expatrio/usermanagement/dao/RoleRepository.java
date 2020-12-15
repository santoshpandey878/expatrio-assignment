package com.expatrio.usermanagement.dao;

import com.expatrio.usermanagement.core.constant.URole;
import com.expatrio.usermanagement.entity.Role;

/**
 * Repository to handle all database operation for role
 */
public interface RoleRepository extends BaseRepository<Role, Long>{

	/**
	 * Method to get role object by role name
	 * @param name
	 * @return
	 */
	Role findByName(URole name);
}