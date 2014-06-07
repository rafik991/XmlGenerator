package pl.asseco.echoweb.dao;

import pl.asseco.echoweb.model.entity.UserRoleEntity;

public interface IUserRoleDAO {

	public UserRoleEntity getRole(String authority);
	
	public Long createRole(UserRoleEntity role);
	
	public UserRoleEntity getRole(Long id);
	
	public boolean deleteRole(UserRoleEntity role);
	
	public void updateRole(UserRoleEntity role);
}
