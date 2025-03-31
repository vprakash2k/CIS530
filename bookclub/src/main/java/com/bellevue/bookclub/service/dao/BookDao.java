package com.bellevue.bookclub.service.dao;

import com.bellevue.bookclub.model.Book;
import com.bellevue.bookclub.service.GenericDao;

public interface BookDao extends GenericDao<Book, String> {
    // Additional Book-specific methods can be added here
}

