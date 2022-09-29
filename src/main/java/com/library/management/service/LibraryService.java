package com.library.management.service;

import java.util.List;

import com.library.management.model.BookVO;
import com.library.management.model.Login;

public interface LibraryService 
{
	public List<BookVO> getAllBooks();
	public Login getUser(String username);
	public BookVO addBook(BookVO book);
	public BookVO updateBook(BookVO book);
	BookVO removeBook(BookVO book);
	List<BookVO> findBooksByName(String keyword);
	BookVO findBookById(String bookId);
}
