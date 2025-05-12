package com.bellevue.bookclub.service.impl;

import com.bellevue.bookclub.model.WishlistItem;
import com.bellevue.bookclub.service.dao.WishlistDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
    public void update(WishlistItem wishlistItem) {
        if (wishlistItem.getId() != null) {
            System.out.println("entity.getId()--"+wishlistItem.getId());
            mongoTemplate.save(wishlistItem);
        } else {
            throw new IllegalArgumentException("Cannot update wishlist item without an id.");
        }
    }

    @Override
    public boolean remove(String key) {
        WishlistItem item = mongoTemplate.findById(key, WishlistItem.class);
        if (item != null) {
            mongoTemplate.remove(item);
            return true;
        }
        return false;
    }

    @Override
    public List<WishlistItem> list(String username) {
	Query query = new Query(Criteria.where("username").is(username));
        return mongoTemplate.find(query, WishlistItem.class);
    }

    @Override
    public WishlistItem find(String key) {
        return mongoTemplate.findById(key, WishlistItem.class);
    }
}
