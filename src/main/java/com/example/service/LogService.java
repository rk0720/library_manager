package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Log;
import com.example.repository.LogRepository;

@Service
public class LogService {

    private final LogRepository logRepository;

    @Autowired
    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }
    
    public Log findById(Integer id) {
    	Optional<Log> optionalLogs = this.logRepository.findById(id);
        Log logs = optionalLogs.get();
    	return logs;
    }
    
    public Log findByLibraryId(Integer libraryId) {
    	return logRepository.findByLibraryId(libraryId);
    }
    
    public Log save(Log log) {
    	return logRepository.save(log);
    }
}