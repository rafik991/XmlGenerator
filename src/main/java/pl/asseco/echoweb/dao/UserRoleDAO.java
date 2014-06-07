package pl.asseco.echoweb.dao;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.asseco.echoweb.model.entity.UserRoleEntity;

@Repository("UserRoleDAO")
public class UserRoleDAO implements IUserRoleDAO {

	@Autowired
	private SessionFactory mySessionFactory;

	@Override
	public UserRoleEntity getRole(String authority) {
		Criteria getByAuthority = mySessionFactory.getCurrentSession()
				.createCriteria(UserRoleEntity.class);
		UserRoleEntity role = (UserRoleEntity) getByAuthority.add(Restrictions
				.eq("authority", authority));
		return role;
	}

	@Override
	public Long createRole(UserRoleEntity role) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserRoleEntity getRole(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteRole(UserRoleEntity role) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void updateRole(UserRoleEntity role) {
		// TODO Auto-generated method stub

	}

}
