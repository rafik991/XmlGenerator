package pl.asseco.echoweb.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.asseco.echoweb.model.entity.UserEntity;
import pl.asseco.echoweb.model.entity.UserRoleEntity;

@Repository("UserDAO")
public class UserDAO implements IUserDAO {

	@Autowired
	private SessionFactory mySessionFactory;

	@Override
	public UserEntity getUser(Long id) {
		Session session = mySessionFactory.getCurrentSession();
		UserEntity user = (UserEntity) session.get(UserEntity.class, id);
		return user;
	}

	@Override
	public Long createUser(UserEntity user) {

		return (Long) mySessionFactory.getCurrentSession().save(user);
	}

	@Override
	public boolean deleteUser(Long id) {
		mySessionFactory.getCurrentSession().delete(this.getUser(id));
		return true;
	}

	@Override
	public boolean updateUser(UserEntity user) {
		mySessionFactory.getCurrentSession().update(user);
		return true;
	}

	@Override
	public Boolean checkLogin(String login, String password) {
		Criteria getUserByLogin = mySessionFactory.getCurrentSession()
				.createCriteria(UserEntity.class);
		UserEntity user = (UserEntity) getUserByLogin.add(Restrictions.eq(
				"login", login));
		if (user.getPassword().equals(password))
			return true;
		else
			return false;

	}

	@Override
	public List<UserEntity> getUsers() {
		Criteria getUserList = mySessionFactory.getCurrentSession()
				.createCriteria(UserEntity.class);
		@SuppressWarnings("unchecked")
		List<UserEntity> userList = getUserList.list();
		return userList;
	}

	@Override
	public void setUserRole(Long userId, Long roleId) {
		UserEntity user = this.getUser(userId);
		UserRoleEntity role = (UserRoleEntity) mySessionFactory.getCurrentSession().get(
				UserRoleEntity.class, roleId);
		user.setRole(role);

	}

	@Override
	public UserRoleEntity getUserRole(Long userId) {
		UserEntity user = (UserEntity) mySessionFactory.getCurrentSession()
				.get(UserEntity.class, userId);
		return user.getRole();
	}

}
