package com.database.user.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.database.authprovider.AuthenticationProvider;
import com.database.post.entity.Post;
import com.database.role.entites.Role;
import javax.persistence.JoinColumn;
@Entity
public class User {

	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id")
	private Long id;
	
	@Column(name="firstname", length=255)
	private String firstname;
	
	@Column(name="lastname")
	private String lastname;
	
	@Column(name="email", unique=true, length=255)
	private String email;
	
	@Column(name="verification_code", updatable=false, length=255)
	private String VerificationCode;
	
	@Column(length=255, name="user_image")
	private String user_image;
	
	@Column(length=64, name="reset_password_token")
	private String resetPasswordToken;
	
	@Enumerated(EnumType.STRING)
	@Column(name="auth_provider")
	private AuthenticationProvider  authprovider;
	
	public String getResetPasswordToken() {
		return resetPasswordToken;
	}

	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}


	public String getVerificationCode() {
		return VerificationCode;
	}


	public void setVerificationCode(String verificationCode) {
		VerificationCode = verificationCode;
	}









	@NotBlank
	@Column(length=64, name="password")
	private String password;
	
	@Column(name="enabled", columnDefinition="tinyint(1) default 0")
	private boolean enabled=false;
	
	
	@OneToMany(mappedBy="user", fetch=FetchType.EAGER)
	List<Post> posts= new ArrayList<Post>();
	
	@ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	@JoinTable(
			  name = "user_roles", 
			  joinColumns = @JoinColumn(name = "user_id"), 
			  inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles= new HashSet<>();
	
	public User() {
		
	}


	public User(Long id, String firstname, String lastname, String email, String verificationCode,
			@NotBlank String user_image, @NotBlank String password, boolean enabled, List<Post> posts,
			Set<Role> roles) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		VerificationCode = verificationCode;
		this.user_image = user_image;
		this.password = password;
		this.enabled = enabled;
		this.posts = posts;
		this.roles = roles;
	}




	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public String getUser_image() {
		return user_image;
	}

	public void setUser_image(String user_image) {
		this.user_image = user_image;
	}

	public void setRoles(Set<Role> roles) {
		Role role= new Role();
		role.setName("ADMIN");
		roles.add(role);
		this.roles = roles;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public String getPassword() {
		return  password;
	}

	public void setPassword(String password) {
		this.password = new BCryptPasswordEncoder().encode(password) ;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	
	
	public AuthenticationProvider getAuthprovider() {
		return authprovider;
	}

	public void setAuthprovider(AuthenticationProvider authprovider) {
		this.authprovider = authprovider;
	}
	
	
	@Transient
	public String getPhotoPath() {
		if(user_image==null | id == null) return null;
		 
		return "post-dir/"+id +"/"+user_image;
	}














	





	
}
