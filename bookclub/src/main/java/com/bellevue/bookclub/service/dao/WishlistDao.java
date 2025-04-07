package com.bellevue.bookclub.service.dao;

import com.bellevue.bookclub.model.WishlistItem;
import com.bellevue.bookclub.service.GenericDao;

public interface WishlistDao extends GenericDao<WishlistItem, String> {
    WishlistItem find(String isbn);
}
