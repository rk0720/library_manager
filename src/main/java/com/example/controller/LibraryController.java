package com.example.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import com.example.entity.Log;
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
    	Library library = this.libraryService.findById(id);
    	model.addAttribute("library", library);
    	return "library/borrowingForm";
    }
    
    @PostMapping("borrow")
    public String borrow(@RequestParam("id") Integer id, @RequestParam("return_due_date") String returnDueDate, @AuthenticationPrincipal LoginUser loginUser) {
    	// 変数 library を定義し、リクエストパラメータで渡された書籍IDに該当する書籍情報を1件取得し代入する
    	Library library = this.libraryService.findById(id);
    	// 取得した書籍情報の USER_ID を現在ログインしているユーザーのIDで上書きし LIBRARIES テーブルの情報を更新する
    	library.setUserId(loginUser.getUserId());
    	
    	Log log = new Log();
    	log.setLibraryId(library.getId());
    	log.setUserId(library.getUserId());
    	
    	LocalDateTime rentDate = LocalDateTime.now();
    	log.setRentDate(rentDate);
    	
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    	LocalDate parsedReturnDueDate = LocalDate.parse(returnDueDate, formatter);
    	log.setReturnDueDate(parsedReturnDueDate);
    	
    	log.setReturnDate(null);
    	
    	logService.save(log);
    	libraryService.save(library);
    	
    	return "redirect:/library";
    }
    
    @PostMapping("return")
    public String returnBook(@RequestParam("id") Integer id, @AuthenticationPrincipal LoginUser loginUser) {
    	Library library = this.libraryService.findById(id);
    	library.setUserId(0);
    	libraryService.save(library);
    	
    	Log log = this.logService.findByLibraryIdAndUserId(id, loginUser.getUserId());
    	log.setReturnDate(LocalDateTime.now());
    	
    	logService.save(log);
    	
    	return "redirect:/library"; 
    }
    
    @GetMapping("history")
    public String history(Model model, @AuthenticationPrincipal LoginUser loginUser) {
    	List<Log> logs = this.logService.findByUserId(loginUser.getUser().getId());
    	model.addAttribute("logs", logs);
    	return "library/borrowHistory";
    }
}