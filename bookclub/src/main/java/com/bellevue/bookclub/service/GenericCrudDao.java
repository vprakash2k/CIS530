package com.bellevue.bookclub.service;

import java.util.List;

public interface GenericCrudDao<E, K> {
    void add(E entity);
    void update(E entity);
    boolean remove(K key);
    List<E> list(K key); // fix: K should be capital
    E find(K key);
}
