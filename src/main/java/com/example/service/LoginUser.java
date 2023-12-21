package com.example.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.entity.User;

public class LoginUser implements UserDetails {
    // フィールド、メソッド
	private final User user;
	
	public LoginUser(User user) {
		this.user = user;
	}
	
	public User getUser() {
		return this.user;
	}
	
	@Override
	public String getPassword() {
		return this.user.getPassword();
	}
	
	public Integer getUserId() {
		return this.user.getId();
	}
	
	@Override
	public String getUsername() {
		return this.user.getEmail();
	}

	 @Override
	 public Collection<? extends GrantedAuthority> getAuthorities() {
	        // ロールカラムを見て、認証ユーザのロールを設定する
	 return AuthorityUtils.NO_AUTHORITIES;
	 }

	 // アカウントの有効期限の状態を判定する
	 @Override
	 public boolean isAccountNonExpired() {
	     return true;
	 }
	
	 // アカウントのロック状態を判定する
	 @Override
	 public boolean isAccountNonLocked() {
	     return true;
	 }
	
	 // 資格情報の有効期限の状態を判定する
	 @Override
	 public boolean isCredentialsNonExpired() {
	     return true;
	 }
	
	 // 有効なユーザかを判定する
	 @Override
	 public boolean isEnabled() {
	     return true;
	 }
 }