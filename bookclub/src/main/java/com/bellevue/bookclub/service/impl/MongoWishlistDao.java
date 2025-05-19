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
    public void update(WishlistItem entity) {
        if (entity.getId() != null) {
            System.out.println("entity.getId()--"+entity.getId());
            mongoTemplate.save(entity);
        } else {
            throw new IllegalArgumentException("Cannot update wishlist item without an id.");
        }
    }

    @Override
    public boolean remove(String key) {
        Query query = new Query(Criteria.where("id").is(key));
        mongoTemplate.remove(query, WishlistItem.class);
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
