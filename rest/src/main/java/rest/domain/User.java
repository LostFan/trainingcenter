package rest.domain;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.codehaus.jackson.annotate.JsonIgnore;

public class User {
	private int id;
	
	private String name;
	@JsonIgnore
	private String password;
	
	private Role role;
	
	
	
	public User(int id, String name, String password,  Role role) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.role = role;
	}
	
	public User() {
		
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Enumerated(EnumType.STRING)
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
