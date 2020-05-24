package com.authValidation.jwttoken.model.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity()
@Table(name = "USER")
public class User implements Serializable {
   
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "USER_ID", updatable=false, nullable=false)
    private long id;
   
    private String email;
    
    private String password;
    
    private String name;
    
    private String lastName;
   
    private Long contact;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Role.class)
    @JoinTable(name = "USER_ROLE", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
    private Set<Role> role = new HashSet<Role>(0);

    public User() {
    	
    }
    
    
	public User(long id, String email, String password, String name, String lastName, Long contact, Set<Role> role) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.lastName = lastName;
		this.contact = contact;
		this.role = role;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

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

	public Long getContact() {
		return contact;
	}

	public void setContact(Long contact) {
		this.contact = contact;
	}

	public Set<Role> getRole() {
		return role;
	}
	
	public void setRole(Set<Role> role) {
		this.role = role;
	}

}
