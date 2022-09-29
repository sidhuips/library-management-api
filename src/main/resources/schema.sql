DROP TABLE IF EXISTS user_login;
CREATE TABLE USER_LOGIN ( 
   id INT NOT NULL, 
   user_name VARCHAR(50) NOT NULL, 
   password VARCHAR(20) NOT NULL
);

DROP TABLE IF EXISTS book;
CREATE TABLE BOOK ( 
   book_isbn VARCHAR(50) NOT NULL, 
   book_name VARCHAR(50) NOT NULL,
   book_author VARCHAR(20),
   book_type VARCHAR(20)
);