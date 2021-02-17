package com.cpms.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cpms.pojos.Role;
import com.cpms.pojos.UserAccount;

//@SuppressWarnings("unused")
public class UserDetailsImpl  implements UserDetails {
	private static final long serialVersionUID = 1L;

	UserAccount userAccount;
	Object user;
	private Collection<? extends GrantedAuthority> authorities;
	
	public UserDetailsImpl() {
		
	}
	
	public UserDetailsImpl(UserAccount userAccount, Object user, Collection<? extends GrantedAuthority> authorities) {
		this.userAccount = userAccount;
		this.user = user;
		this.authorities = authorities;
	}
	
	public static UserDetailsImpl build(UserAccount userAccount, Object user) {
		System.out.println("UserDetailsImpl: build");
		//System.out.println(userAccount);
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		//List<Role> rl =  Arrays.asList(user.getRole());
		List<Role> roles = Arrays.asList(userAccount.getRole());
//		Role role = user.getRole();
//		 authorities.add(new SimpleGrantedAuthority(role.toString()));
	    for (Role role: roles) {
	        authorities.add(new SimpleGrantedAuthority(role.toString()));
	    }
//		List<GrantedAuthority> authorities = user.getRoles().stream()
//				.map(role -> new SimpleGrantedAuthority(role.getName().name()))
//				.collect(Collectors.toList());

	    System.out.println("UserDetailsImpl_1: build");
	    return new UserDetailsImpl(userAccount, user, authorities);
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public Object getUser() {
		return user;
	}

	public void setUser(Object user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Integer getId() {
		return userAccount.getId();
	}

	@Override
	public String getPassword() {
		return userAccount.getPassword();
	}

	@Override
	public String getUsername() {
		return userAccount.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userAccount == null) ? 0 : userAccount.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDetailsImpl other = (UserDetailsImpl) obj;
		if (userAccount == null) {
			if (other.userAccount != null)
				return false;
		} else if (!userAccount.equals(other.userAccount))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserDetailsImpl [userAccount=" + userAccount + ", user=" + user + ", authorities=" + authorities + "]";
	}
	
}
