package pl.echoweb.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import pl.echoweb.model.dto.UserDTO;

public interface IUserService {

	public boolean checkLogin(String login, String password)
			throws NoSuchAlgorithmException;

	public Long createUser(UserDTO user);

	public UserDTO getUser(Long id);

	public void updateUser(UserDTO user);

	public boolean deleteUser(Long id);

	public List<UserDTO> getUsers();
}
