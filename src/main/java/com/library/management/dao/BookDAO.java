package com.library.management.dao;

import java.util.List;

import com.library.management.model.BookVO;

public interface BookDAO 
{
	public List<BookVO> getAllBooks();

	BookVO addBook(BookVO book);

	BookVO findBookById(String isbn);

	BookVO updateBook(BookVO book);

	BookVO removeBook(BookVO book);

	List<BookVO> searchBooks(String keyword);
}