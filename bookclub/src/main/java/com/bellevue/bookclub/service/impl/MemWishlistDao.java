package com.bellevue.bookclub.service.impl;

import com.bellevue.bookclub.model.WishlistItem;
import com.bellevue.bookclub.service.dao.WishlistDao;
import java.util.ArrayList;
import java.util.List;

public class MemWishlistDao implements WishlistDao {

    private List<WishlistItem> wishlist = new ArrayList<>();

    // Constructor to populate wishlist (example)
    public MemWishlistDao() {
        wishlist.add(new WishlistItem("123456789", "The Hobbitt are back again"));
        wishlist.add(new WishlistItem("987654322", "The fellowship of the Ring"));
        wishlist.add(new WishlistItem("654329878", "The Return of the King"));
    }

    @Override
    public List<WishlistItem> list() {
        return wishlist;
    }

    @Override
    public WishlistItem find(String isbn) {
        return wishlist.stream().filter(item -> item.getIsbn().equals(isbn)).findFirst().orElse(null);
    }

    @Override
    public WishlistItem save(WishlistItem entity) {
        wishlist.add(entity);
        return entity;
    }

    @Override
    public WishlistItem findById(String id) {
        return wishlist.stream().filter(item -> item.getIsbn().equals(id)).findFirst().orElse(null);
    }

    @Override
    public List<WishlistItem> findAll() {
        return wishlist;
    }

    @Override
    public void delete(String id) {
        wishlist.removeIf(item -> item.getIsbn().equals(id));
    }
}
