package com.bellevue.bookclub.service.impl;

import com.bellevue.bookclub.model.BookOfTheMonth;
import com.bellevue.bookclub.service.dao.BookOfTheMonthDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("bookOfTheMonthDao")
public class MongoBookOfTheMonthDao implements BookOfTheMonthDao {
    //private List<BookOfTheMonth> storage = new ArrayList<>();
	@Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void add(BookOfTheMonth entity) {
        mongoTemplate.save(entity);
    }

    @Override
    public boolean remove(String key) {
        Query query = new Query(Criteria.where("id").is(key));
        return mongoTemplate.remove(query, BookOfTheMonth.class).getDeletedCount() > 0;
    }

    @Override
    public List<BookOfTheMonth> list(String key) {
       int month = Integer.parseInt(key);
        if (month == 999) {
            return mongoTemplate.findAll(BookOfTheMonth.class);
        }

        Query query = new Query();
        query.addCriteria(Criteria.where("month").is(month));

        return mongoTemplate.find(query, BookOfTheMonth.class);
    }

    @Override
    public void update(BookOfTheMonth entity) {
        mongoTemplate.save(entity); // Save acts as update in Mongo
    }

    @Override
    public BookOfTheMonth find(String key) {
        return mongoTemplate.findById(key, BookOfTheMonth.class);
    }
}
