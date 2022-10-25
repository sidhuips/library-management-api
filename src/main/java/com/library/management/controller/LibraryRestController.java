package com.library.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.library.management.model.BookVO;
import com.library.management.model.Login;
import com.library.management.model.SearchVO;
import com.library.management.service.LibraryService;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/libraryService")
public class LibraryRestController {

	@Autowired
	LibraryService libraryService;

	@RequestMapping(value = "/books", method = RequestMethod.GET)
	public ResponseEntity<List<BookVO>> getBooks() {
		
		List<BookVO> books = libraryService.getAllBooks();
		return ResponseEntity.ok(books);
	}
	
	@RequestMapping(value = "/findBookById/{id}", method = RequestMethod.GET)
	public ResponseEntity<BookVO> findBook(@PathVariable String id) {
		BookVO book = libraryService.findBookById(id);
		return ResponseEntity.ok(book);
	}
	
	@RequestMapping(value = "/searchBook", method = RequestMethod.POST)
	public ResponseEntity<List<BookVO>> searchBooks(@RequestBody SearchVO search) {
				List<BookVO> books = libraryService.findBooksByName(search.getSearchString());
		return ResponseEntity.ok(books);
	}
	
	@RequestMapping(value = "/updateBook", method = RequestMethod.POST)
    public ResponseEntity<BookVO> updateBook(@RequestBody BookVO book) {
		BookVO bookToReturn = libraryService.updateBook(book);
		return ResponseEntity.ok(bookToReturn);
    }
	
	@RequestMapping(value = "/addBook", method = RequestMethod.POST)
    public ResponseEntity<BookVO> addBook(@RequestBody BookVO book) {
		BookVO bookToReturn = libraryService.addBook(book);
		return ResponseEntity.ok(bookToReturn);
    }
	
	@RequestMapping(value = "/removeBook", method = RequestMethod.POST)
    public ResponseEntity<BookVO> removeBook(@RequestBody BookVO book) {
		BookVO bookToReturn = libraryService.removeBook(book);
		return ResponseEntity.ok(bookToReturn);
    }
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Login> login(@RequestBody Login login) {
		Login loggedIn = libraryService.getUser(login.getUserName());
		return ResponseEntity.ok(loggedIn);
    }
}