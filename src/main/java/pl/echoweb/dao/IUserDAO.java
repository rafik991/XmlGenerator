package pl.echoweb.dao;

import java.util.List;

import pl.echoweb.model.entity.UserEntity;
import pl.echoweb.model.entity.UserRoleEntity;

public interface IUserDAO {

	public UserEntity getUser(Long id);

	public Long createUser(UserEntity user);

	public boolean deleteUser(Long id);

	public boolean updateUser(UserEntity user);

	public Boolean checkLogin(String login, String password);

	public List<UserEntity> getUsers();

	public void setUserRole(Long userId, Long roleId);

	public UserRoleEntity getUserRole(Long id);
}
