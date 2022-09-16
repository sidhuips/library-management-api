package com.library.management.dao;

import com.library.management.model.Login;

public interface UserDAO {
	Login findById(String username);
	Login findUserByUserName(String username);
}
