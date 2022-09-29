package com.library.management.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.library.management.model.BookVO;

@Repository
public class BookDAOImpl implements BookDAO {
	
    @PersistenceContext
    private EntityManager entityManager;
	
	@Override
	@Transactional
	public List<BookVO> getAllBooks() 
	{
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<BookVO> cr = cb.createQuery(BookVO.class);
        Root <BookVO> root = cr.from(BookVO.class);
        cr.select(root);
        Query<BookVO> query = (Query<BookVO>) entityManager.createQuery(cr);
        List<BookVO> results = query.getResultList();
        return results;
	}
	
	@Transactional
	@Override
	public BookVO addBook(BookVO book) 
	{
		entityManager.persist(book);
		return book;
	}
	
	@Transactional
	@Override
	public BookVO findBookById(String isbn) {
		BookVO book = entityManager.find(BookVO.class, isbn);
		return book;
	}
	
	@Transactional
	@Override
	public BookVO updateBook(BookVO book)
	{
		//BookVO bookToUpdate = entityManager.find(BookVO.class, book.getId());
		entityManager.merge(book);
		return book;
	}
	
	@Transactional
	@Override
	public BookVO removeBook(BookVO book)
	{
		BookVO bookToRemove = entityManager.getReference(BookVO.class, book.getId());
		entityManager.remove(bookToRemove);
		return book;
	}
		
	@Transactional
	@Override
	@SuppressWarnings("unchecked")
	public List<BookVO> searchBooks(String keyword) {
		String hql = "from BookVO where bookName like :keyword";		 
		Query<BookVO> query = (Query<BookVO>) entityManager.createQuery(hql);
		query.setParameter("keyword", "%" + keyword + "%");
		 
		List<BookVO> listBooks = query.list();
		return listBooks;
    }
}