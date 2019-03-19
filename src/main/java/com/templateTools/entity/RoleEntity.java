package com.templateTools.entity;

import com.templateTools.base.entity.BaseEntity;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Title: Role
 * @Description: 

 * @author 
 * @date 
 */
@Table(name = "sys_role")
public class RoleEntity extends BaseEntity implements Serializable {

	@Id
	private String id; // 

	private String roleCode; // 

	private String roleName; // 

	/**  */
	public String getId() {
		return id;
	}
	/**  */
	public void setId(String id) {
		this.id = id;
	}
	/**  */
	public String getRoleCode() {
		return roleCode;
	}
	/**  */
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	/**  */
	public String getRoleName() {
		return roleName;
	}
	/**  */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}







