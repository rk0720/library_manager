package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Log;

@Repository
public interface LogRepository extends JpaRepository<Log, Integer>{
    // 以降の設問で必要に応じて機能を実装すること
	public Log findTopByLibraryIdAndUserIdOrderByRentDateDesc(Integer libraryId, Integer userId);
	
	public List<Log> findByUserId(Integer userId);
}