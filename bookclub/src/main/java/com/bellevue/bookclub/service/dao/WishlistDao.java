package com.bellevue.bookclub.service.dao;

import com.bellevue.bookclub.model.WishlistItem;
import com.bellevue.bookclub.service.GenericCrudDao;
import com.bellevue.bookclub.service.GenericDao;

import java.util.List;

public interface WishlistDao extends GenericCrudDao<WishlistItem, String> {

    void add(WishlistItem entity);

    void update(WishlistItem entity);

    boolean remove(String key);

    List<WishlistItem> list(String username);
	
	//List<WishlistItem> findByUsername(String username);
}
