package com.bellevue.bookclub.service.impl;

import com.bellevue.bookclub.model.WishlistItem;
import com.bellevue.bookclub.service.dao.WishlistDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("wishlistDao")

public class MongoWishlistDao implements WishlistDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void add(WishlistItem entity) {
        mongoTemplate.save(entity);
    }

    @Override
    public void update(WishlistItem entity) {
        mongoTemplate.save(entity);
    }

    @Override
    public boolean remove(WishlistItem entity) {
        mongoTemplate.remove(entity);
        return true;
    }

    @Override
    public List<WishlistItem> list() {
        return mongoTemplate.findAll(WishlistItem.class);
    }

    @Override
    public WishlistItem find(String key) {
        return mongoTemplate.findById(key, WishlistItem.class);
    }
}
