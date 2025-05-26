package com.bellevue.bookclub.service.impl;

import com.bellevue.bookclub.model.BookOfTheMonth;
import com.mongodb.client.result.DeleteResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MongoBookOfTheMonthDaoTest {

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private MongoBookOfTheMonthDao dao;

    @Test
    public void testRemoveBookOfTheMonth() {
        String id = "test-id";
        DeleteResult mockResult = DeleteResult.acknowledged(1);

        when(mongoTemplate.remove(any(Query.class), eq(BookOfTheMonth.class))).thenReturn(mockResult);

        boolean removed = dao.remove(id);
        assertTrue(removed);
        verify(mongoTemplate).remove(any(Query.class), eq(BookOfTheMonth.class));
    }

    @Test
    public void testListByMonth() {
        BookOfTheMonth botm = new BookOfTheMonth();
        botm.setIsbn("123");
        botm.setMonth(5);

        when(mongoTemplate.find(any(Query.class), eq(BookOfTheMonth.class)))
                .thenReturn(List.of(botm));

        List<BookOfTheMonth> result = dao.list("5");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("123", result.get(0).getIsbn());

        verify(mongoTemplate).find(any(Query.class), eq(BookOfTheMonth.class));
    }

    @Test
    public void testListAllMonths() {
        BookOfTheMonth botm1 = new BookOfTheMonth();
        botm1.setIsbn("123");
        botm1.setMonth(1);
        BookOfTheMonth botm2 = new BookOfTheMonth();
        botm2.setIsbn("456");
        botm2.setMonth(2);

        when(mongoTemplate.findAll(BookOfTheMonth.class))
                .thenReturn(List.of(botm1, botm2));

        List<BookOfTheMonth> result = dao.list("999"); // special case for all

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(mongoTemplate).findAll(BookOfTheMonth.class);
    }

    @Test
    public void testFindById() {
        BookOfTheMonth botm = new BookOfTheMonth();
        botm.setIsbn("123");
        botm.setMonth(5);

        when(mongoTemplate.findById("test-id", BookOfTheMonth.class))
                .thenReturn(botm);

        BookOfTheMonth found = dao.find("test-id");

        assertNotNull(found);
        assertEquals("123", found.getIsbn());

        verify(mongoTemplate).findById("test-id", BookOfTheMonth.class);
    }
}
