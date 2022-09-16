package com.library.management.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BOOK")
public class BookVO implements Serializable 
{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "book_isbn")
	private String id;
	
	@Column(name = "book_name")
	private String bookName;
	
	@Column(name = "book_type")
	private String type;
	
	@Column(name = "book_author")
	private String author;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	@Override
	public String toString() {
		return "BookVO [id=" + id + ", bookName=" + bookName + ", type=" + type + ", author=" + author + "]";
	}
	
}