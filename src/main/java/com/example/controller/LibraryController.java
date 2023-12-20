package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.Library;
import com.example.service.LibraryService;
import com.example.service.LogService;
import com.example.service.LoginUser;

@Controller
@RequestMapping("library")
public class LibraryController {

    private final LibraryService libraryService;
    
    private final LogService logService;

    @Autowired
    public LibraryController(LibraryService libraryService, LogService logService) {
        this.libraryService = libraryService;
		this.logService = logService;
    }

    @GetMapping
    public String index(Model model) {
        List<Library> libraries = this.libraryService.findAll();
        model.addAttribute("libraries", libraries);
        return "library/index";
    }
    
    //課題2 書籍貸し出しフォームを表示する
    @GetMapping("borrow")
    public String borrowingForm(@RequestParam("id") Integer id, Model model) {
    	Library librarys = this.libraryService.findById(id);
    	model.addAttribute("librarys", librarys);
    	return "library/borrowingForm";
    }
    
    @PostMapping("borrow")
    public String borrow(@RequestParam("id") Integer id, @RequestParam("return_due_date") String returnDueDate, @AuthenticationPrincipal LoginUser loginUser) {
    	
    	return "redirect:/library";
    }
}