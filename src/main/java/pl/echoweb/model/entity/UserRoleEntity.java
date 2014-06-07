package pl.echoweb.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "USER_ROLE")
public class UserRoleEntity {
	
	@Id
	@SequenceGenerator(name = "userRole_seq", sequenceName = "userRole_seq_name", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userRole_seq")
	@Column(name = "ID")
	private Long id;

	@Column(name = "AUTHORITY")
	String authority;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

}
