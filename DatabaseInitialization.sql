CREATE DATABASE IF NOT EXISTS g7_library;

DROP TABLE IF EXISTS g7_library.e_book;

CREATE TABLE IF NOT EXISTS g7_library.e_book(
	id INT PRIMARY KEY AUTO_INCREMENT,
	bookName VARCHAR(20) NOT NULL,
	authorName VARCHAR(20) NOT NULL,
	version VARCHAR(10),
	pages INT NOT NULL,
	stock INT NOT NULL,
	price DOUBLE NOT NULL,
	fileFormat VARCHAR(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS g7_library.paper_book;

CREATE TABLE IF NOT EXISTS g7_library.paper_book (
	id INT PRIMARY KEY AUTO_INCREMENT,
	bookName VARCHAR(20) NOT NULL,
	authorName VARCHAR(20) NOT NULL,
	version VARCHAR(10),
	pages INT NOT NULL,
	stock INT NOT NULL,
	price DOUBLE NOT NULL,
	paperType VARCHAR(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

