package pl.asseco.echoweb.web.model;

public class UserModel {
	
	private String name;
	private String lastName;
	private String login;
	private String password;
	private String retypePasswordl;
	private String email;
	private String token;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRetypePasswordl() {
		return retypePasswordl;
	}

	public void setRetypePasswordl(String retypePasswordl) {
		this.retypePasswordl = retypePasswordl;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
