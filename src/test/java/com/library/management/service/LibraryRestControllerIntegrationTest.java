package com.library.management.service;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.library.management.LibraryApplication;
import com.library.management.model.BookVO;
import com.library.management.model.Login;
import com.library.management.model.SearchVO;

@SpringBootTest(classes = LibraryApplication.class)
public class LibraryRestControllerIntegrationTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@BeforeClass
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void givenBooksExist_whenSelectedHome_thenBookListIsReturned() throws Exception {
		mockMvc.perform(get("/libraryService/books")).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.[0].id").value("1"))
				.andExpect(jsonPath("$.[0].bookName").value("The Tribute"));
	}
	
	@Test
	public void givenBookExists_whenSearchedById_thenBookIsReturned() throws Exception {
		mockMvc.perform(get("/libraryService/findBookById/1")).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.id").value("1"))
				.andExpect(jsonPath("$.bookName").value("The Tribute"));
	}
	
	@Test
	public void givenBookExists_whenSearchedByNAme_thenBookIsReturned() throws Exception {
		SearchVO search = new SearchVO();
		search.setSearchString("te");
		mockMvc.perform(post("/libraryService/searchBook").content(new Gson().toJson(search)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.[0].id").value("1"))
				.andExpect(jsonPath("$.[0].bookName").value("The Tribute"));
	}

	@Test
	public void givenBookExists_whenUpdated_thenBookIsUpdatedSuccessfully() throws Exception {
		BookVO book = getTestBook();
		mockMvc.perform(post("/libraryService/updateBook").content(new Gson().toJson(book)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.id").value("2"))
				.andExpect(jsonPath("$.author").value("author"));
	}
	
	@Test
	public void givenBookDoesNotExist_whenUpdated_thenBookIsNotUpdated() throws Exception {
		BookVO book = getTestBook();
		book.setId("45");
		mockMvc.perform(post("/libraryService/updateBook").content(new Gson().toJson(book)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").isEmpty());
	}
	
	@Test
	public void givenBookDoesntExist_whenAdded_thenBookIsSavedSuccessfully() throws Exception {
		BookVO book = getTestBook();
		book.setId("3");
		mockMvc.perform(post("/libraryService/addBook").content(new Gson().toJson(book)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.id").value("3"))
				.andExpect(jsonPath("$.author").value("author"));
	}
		
	@Test
	public void givenBookExists_whenAdded_thenBookIsNotSaved() throws Exception {
		BookVO book = new BookVO();
		book.setId("1");
		mockMvc.perform(post("/libraryService/addBook").content(new Gson().toJson(book)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.id").isEmpty());
	}
	
	@Test
	public void givenBookExists_whenRemoved_thenBookIsRemovedSuccessfully() throws Exception {
		BookVO book = getTestBook();
		book.setId("4");
		mockMvc.perform(post("/libraryService/removeBook").content(new Gson().toJson(book)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.id").value("4"));
	}
	
	@Test
	public void givenBookDoesNotExist_whenRemoved_thenEmptyObjectisReturned() throws Exception {
		BookVO book = getTestBook();
		book.setId("44");
		mockMvc.perform(post("/libraryService/removeBook").content(new Gson().toJson(book)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.id").isEmpty());
	}
	
	@Test
	public void givenUserExists_whenLogsIn_thenValidUserAccountIsReturned() throws Exception {
		Login login = new Login();
		login.setUserName("inder");
		mockMvc.perform(post("/libraryService/login").content(new Gson().toJson(login)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.password").value("inder123"));
	}
	
	@Test
	public void givenUserDoesNotExist_whenLogsIn_thenEmptyUserAccountIsReturned() throws Exception {
		Login login = new Login();
		login.setUserName("user");
		mockMvc.perform(post("/libraryService/login").content(new Gson().toJson(login)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.password").isEmpty());
	}

	private BookVO getTestBook() {
		BookVO book = new BookVO();
		book.setId("2");
		book.setBookName("History in the making");
		book.setType("History");
		book.setAuthor("author");
		return book;
	}
	
}