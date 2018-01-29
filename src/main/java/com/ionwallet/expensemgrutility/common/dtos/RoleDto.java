package com.ionwallet.expensemgrutility.common.dtos;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ionwallet.expensemgrutility.common.dtos.AbstractDTO;

@JsonIgnoreProperties(ignoreUnknown=true)
public class RoleDto extends AbstractDTO {

	private int roleId;

	private String roleName;
	
	private List<UsersDTO> users=new ArrayList<UsersDTO>();

	public List<UsersDTO> getUsers() {
		return users;
	}

	public void setUsers(List<UsersDTO> users) {
		this.users = users;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


}
