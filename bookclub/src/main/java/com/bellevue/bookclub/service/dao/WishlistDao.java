package com.bellevue.bookclub.service.dao;

import com.bellevue.bookclub.model.WishlistItem;
import com.bellevue.bookclub.service.GenericDao;

public interface WishlistDao extends GenericDao<WishlistItem, String> {

    void add(WishlistItem entity);

    void update(WishlistItem entity);

    boolean remove(WishlistItem entity);
}
