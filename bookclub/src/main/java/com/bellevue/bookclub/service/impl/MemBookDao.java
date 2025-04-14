package com.bellevue.bookclub.service.impl;

import com.bellevue.bookclub.model.Book;
import com.bellevue.bookclub.service.dao.BookDao;

import java.util.ArrayList;
import java.util.List;

public class MemBookDao implements BookDao {
    private List<Book> books = new ArrayList<>();

    // Constructor to add some books
    public MemBookDao() {
        books.add(new Book("123", "Book One", "Description of Book One", 200, List.of("Author A", "Author B")));
        books.add(new Book("456", "Book Two", "Description of Book Two", 300, List.of("Author C")));
        books.add(new Book("789", "Book Three", "Description of Book Three", 150, List.of("Author D", "Author E")));
        books.add(new Book("101", "Book Four", "Description of Book Four", 400, List.of("Author F")));
        books.add(new Book("202", "Book Five", "Description of Book Five", 250, List.of("Author G", "Author H")));
    }

    @Override
    public List<Book> list() {
        return books;
    }

    @Override
    public Book find(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }

    /*
    @Override
    public Book save(Book entity) {
        books.add(entity);
        return entity;
    }

    @Override
    public Book findById(String id) {
        return books.stream().filter(book -> book.getIsbn().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<Book> findAll() {
        return books;
    }

    @Override
    public void delete(String id) {
        books.removeIf(book -> book.getIsbn().equals(id));
    }

     */
}

