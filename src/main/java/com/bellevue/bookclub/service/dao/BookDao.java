package com.bellevue.bookclub.service.dao;

import com.bellevue.bookclub.model.Book;
import com.bellevue.bookclub.service.GenericDao;

import java.util.List;

public interface BookDao extends GenericDao<Book, String> {
    List<Book> list(String username);

    Book find(String key);

}

