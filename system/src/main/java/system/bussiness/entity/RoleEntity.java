package system.bussiness.entity;

import com.common.bussiness.entity.BaseEntity;
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

	private String roleCode; //

	private String roleName; // 

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







