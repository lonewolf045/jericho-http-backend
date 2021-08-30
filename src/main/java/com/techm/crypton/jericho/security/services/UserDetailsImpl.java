package com.techm.crypton.jericho.security.services;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.techm.crypton.jericho.models.Users;

public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;
	
	private UUID id;
	
	private String username;
	
	@JsonIgnore
	private String password;
	
	//private Collection<? extends GrantedAuthority> authorities;
	
	
	
	public UserDetailsImpl(UUID id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
	}

	public static UserDetailsImpl build(Users user) {
		return new UserDetailsImpl(
				user.getId(),
				user.getUsername(),
				user.getPassword());
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
	}

	public UUID getId() {
		return id;
	}
	
}
