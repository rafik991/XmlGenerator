package pl.echoweb.service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import pl.echoweb.dao.IUserDAO;
import pl.echoweb.dao.IUserRoleDAO;
import pl.echoweb.model.dto.UserDTO;
import pl.echoweb.model.entity.UserEntity;
import pl.echoweb.util.SHA1;

@Service("UserService")
@Transactional
public class UserService implements IUserService {

	@Autowired
	@Qualifier("UserDAO")
	IUserDAO userDao;

	@Autowired
	@Qualifier("UserRoleDAO")
	IUserRoleDAO roleDao;

	@Override
	public boolean checkLogin(String login, String password)
			throws NoSuchAlgorithmException {

		if (password.equals(SHA1.sha1(login)))
			return true;
		return false;
	}

	@Override
	public Long createUser(UserDTO user) {
		UserEntity ent = new UserEntity();
		ent.setActive(user.isActive());
		ent.setEmail(user.getEmail());
		ent.setName(user.getName());
		ent.setLastName(user.getLastName());
		try {
			ent.setPassword(SHA1.sha1(user.getPassword()));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Brak algorytmu!!!");
			e.printStackTrace();
		}
		ent.setRole(roleDao.getRole(user.getRole()));
		return userDao.createUser(ent);
	}

	@Override
	public UserDTO getUser(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateUser(UserDTO user) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean deleteUser(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<UserDTO> getUsers() {
		List<UserEntity> userEntityList = userDao.getUsers();
		List<UserDTO> userList = new ArrayList<UserDTO>();
		for (UserEntity ent : userEntityList) {
			UserDTO dto = new UserDTO();
			dto.setActive(ent.getActive());
			dto.setEmail(ent.getEmail());
			dto.setLogin(ent.getLogin());
			dto.setName(ent.getName());
			dto.setPassword(ent.getPassword());
			dto.setLastName(ent.getLastName());
			userList.add(dto);
		}
		return userList;
	}

}
