package com.library.management.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.library.management.model.Login;

@Repository("employeeDao")
public class UserDAOImpl implements UserDAO{
	
    @PersistenceContext
    private EntityManager entityManager;
		
	@Transactional
	@Override
	@SuppressWarnings("unchecked")
	public Login findUserByUserName(String username) {
		String hql = "from Login where user_name = :username";
		Query<Login> query = (Query<Login>) entityManager.createQuery(hql);
		query.setParameter("username", username);

		List<Login> loginList = query.getResultList();
		if (!CollectionUtils.isEmpty(loginList))
			return loginList.get(0);
		else
			return new Login();
	}
}
