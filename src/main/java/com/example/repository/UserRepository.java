package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    // 以降の設問で必要に応じて機能を実装すること
	public User findByEmail(String email);
}