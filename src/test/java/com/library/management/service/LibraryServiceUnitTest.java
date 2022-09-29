package com.library.management.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.library.management.dao.BookDAO;
import com.library.management.dao.UserDAO;
import com.library.management.model.BookVO;
import com.library.management.model.Login;

public class LibraryServiceUnitTest extends AbstractTestNGSpringContextTests {

	@Mock
	BookDAO bookDAO;
	
	@Mock
	UserDAO userDAO;

	@InjectMocks
	LibraryServiceImpl libraryService;

	@BeforeClass
	public void init() {
		MockitoAnnotations.openMocks(this);
	}

	@AfterClass
	public void afterClass() throws Exception {
		MockitoAnnotations.openMocks(this).close();
	}

	@Test
	public void givenBookExists_whenSearchedById_thenBookIsReturned() {

		BookVO book = getTestBook();
		when(bookDAO.findBookById(any())).thenReturn(book);
		BookVO bookFound = libraryService.findBookById("2");
		assertTrue(book.getId().equals(bookFound.getId()));
		assertTrue(book.getBookName().equals(bookFound.getBookName()));
		assertTrue(book.getType().equals(bookFound.getType()));
		assertTrue(book.getAuthor().equals(bookFound.getAuthor()));
	}

	@Test
	public void givenBooksExist_whenSelectedHomePage_thenBookListIsReturned() {

		List<BookVO> bookList = getBookList();
		when(bookDAO.getAllBooks()).thenReturn(bookList);
		List<BookVO> bookListFound = libraryService.getAllBooks();

		assertTrue(bookList.size() == bookListFound.size());

		assertTrue(bookList.get(0).getId().equals(bookListFound.get(0).getId()));
		assertTrue(bookList.get(1).getId().equals(bookListFound.get(1).getId()));

		assertTrue(bookList.get(0).getBookName().equals(bookListFound.get(0).getBookName()));
		assertTrue(bookList.get(0).getType().equals(bookListFound.get(0).getType()));
		assertTrue(bookList.get(1).getAuthor().equals(bookListFound.get(1).getAuthor()));
	}

	@Test
	public void givenBookDoesntExist_whenAdded_thenBookIsSavedSuccessfully() {

		BookVO book = getTestBook();
		when(bookDAO.addBook(book)).thenReturn(book);
		when(bookDAO.findBookById(any())).thenReturn(null);

		BookVO bookSaved = libraryService.addBook(book);
		assertTrue(book.getId().equals(bookSaved.getId()));

	}

	@Test
	public void givenBookExists_whenAdded_thenBookIsNotSaved() {

		BookVO book = getTestBook();
		when(bookDAO.addBook(book)).thenReturn(book);
		when(bookDAO.findBookById(book.getId())).thenReturn(book);

		BookVO bookSaved = libraryService.addBook(book);
		assertTrue(bookSaved.getId() == null);
		assertTrue(bookSaved.getBookName() == null);
		assertTrue(bookSaved.getType() == null);
		assertTrue(bookSaved.getAuthor() == null);
	}

	@Test
	public void givenBookExists_whenUpdated_thenBookIsUpdatedSuccessfully() {
		BookVO existingBook = getTestBook();
		BookVO updatedBook = getTestBook();
		updatedBook.setBookName("Planet");

		when(bookDAO.updateBook(existingBook)).thenReturn(updatedBook);
		when(bookDAO.findBookById(existingBook.getId())).thenReturn(existingBook);

		BookVO bookUpdated = libraryService.updateBook(existingBook);
		assertTrue("Planet".equalsIgnoreCase(bookUpdated.getBookName()));
	}

	@Test
	public void givenBookDoesNotExist_whenUpdated_thenBookIsNotUpdated() {

		BookVO bookToUpdate = getTestBook();

		when(bookDAO.updateBook(bookToUpdate)).thenReturn(bookToUpdate);
		when(bookDAO.findBookById(bookToUpdate.getId())).thenReturn(null);

		BookVO bookUpdated = libraryService.updateBook(bookToUpdate);
		assertTrue(bookUpdated.getId() == null);
		assertTrue(bookUpdated.getBookName() == null);
		assertTrue(bookUpdated.getType() == null);
		assertTrue(bookUpdated.getAuthor() == null);

	}

	@Test
	public void givenBookExists_whenRemoved_thenBookIsRemovedSuccessfully() {
		BookVO bookToRemove = getTestBook();
		BookVO removedBook = new BookVO();
		removedBook.setId(bookToRemove.getId());

		when(bookDAO.removeBook(bookToRemove)).thenReturn(removedBook);
		when(bookDAO.findBookById(bookToRemove.getId())).thenReturn(bookToRemove);

		BookVO bookRemoved = libraryService.removeBook(bookToRemove);
		assertTrue(bookToRemove.getId().equalsIgnoreCase(bookRemoved.getId()));
	}
	
	@Test
	public void givenBookDoesNotExist_whenRemoved_thenEmptyObjectisReturned() {
		BookVO bookToRemove = getTestBook();
		BookVO removedBook = new BookVO();
		removedBook.setId(bookToRemove.getId());

		when(bookDAO.removeBook(bookToRemove)).thenReturn(removedBook);
		when(bookDAO.findBookById(bookToRemove.getId())).thenReturn(null);

		BookVO bookRemoved = libraryService.removeBook(bookToRemove);
		assertTrue(bookRemoved.getId() == null);
		assertTrue(bookRemoved.getBookName() == null);
		assertTrue(bookRemoved.getAuthor() == null);
		assertTrue(bookRemoved.getType() == null);
	}	

	@Test
	public void givenBooksExist_whenSearched_thenAllBooksReturnedHavingSrchStringInName() {
		String searchString = "the";
		List<BookVO> bookList = getBookList();

		when(bookDAO.searchBooks(any())).thenReturn(bookList);
		

		List<BookVO> booksFound = libraryService.findBooksByName(searchString);
		
		assertTrue(booksFound.size() == 2);

		assertTrue(booksFound.get(0).getBookName().contains(searchString));
		assertTrue(booksFound.get(1).getBookName().contains(searchString));
	}
	
	@Test
	public void givenBooksExist_whenSearchedWithStringHavingNoMatchingNames_thenEmptyListisReturned() {
		String searchString = "the";
		List<BookVO> bookList = new ArrayList<BookVO>();

		when(bookDAO.searchBooks(any())).thenReturn(bookList);
		

		List<BookVO> booksFound = libraryService.findBooksByName(searchString);
		
		assertTrue(booksFound.size() == 0);
	}
	
	@Test
	public void givenUserExists_whenLogsIn_thenValidUserAccountIsReturned() {
		Login loginCredentials = new Login();
		loginCredentials.setUserName("user1");
		loginCredentials.setPassword("password");

		Login dbCredentials = new Login();
		dbCredentials.setUserName("user1");
		dbCredentials.setPassword("password");


		when(userDAO.findUserByUserName(any())).thenReturn(dbCredentials);

		Login credentialsFetched = libraryService.getUser(loginCredentials.getUserName());
		assertTrue(loginCredentials.getUserName().equals(credentialsFetched.getUserName()));
		assertTrue(loginCredentials.getPassword().equals(credentialsFetched.getPassword()));
	}
	
	@Test
	public void givenUserDoesNotExist_whenLogsIn_thenEmptyUserAccountIsReturned() {
		Login loginCredentials = new Login();
		loginCredentials.setUserName("user1");
		loginCredentials.setPassword("password");

		Login dbCredentials = new Login();


		when(userDAO.findUserByUserName(any())).thenReturn(dbCredentials);

		Login credentialsFetched = libraryService.getUser(loginCredentials.getUserName());
		assertFalse(loginCredentials.getUserName().equals(credentialsFetched.getUserName()));
		assertFalse(loginCredentials.getPassword().equals(credentialsFetched.getPassword()));
	}
	
	private BookVO getTestBook() {
		BookVO book = new BookVO();
		book.setId("2");
		book.setBookName("History in the making");
		book.setType("History");
		book.setAuthor("author");
		return book;
	}

	private List<BookVO> getBookList() {

		List<BookVO> bookList = new ArrayList<BookVO>();
		BookVO book1 = new BookVO();
		book1.setId("2");
		book1.setBookName("History in the making");
		book1.setType("History");
		book1.setAuthor("author");
		bookList.add(book1);

		BookVO book2 = new BookVO();
		book2.setId("3");
		book2.setBookName("the Earth");
		book2.setType("Geography");
		book2.setAuthor("author");
		bookList.add(book2);

		return bookList;
	}
}
