package com.library.management.dao;

import com.library.management.model.Login;

public interface UserDAO {
	Login findUserByUserName(String username);
}
