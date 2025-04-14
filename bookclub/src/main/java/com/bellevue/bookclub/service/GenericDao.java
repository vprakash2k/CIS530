package com.bellevue.bookclub.service;

import java.util.List;

public interface GenericDao<E, K> {
    List<E> list();

    E find(K key);
    /*E save(E entity);
    E findById(K id);
    List<E> findAll();
    void delete(K id);*/
}

