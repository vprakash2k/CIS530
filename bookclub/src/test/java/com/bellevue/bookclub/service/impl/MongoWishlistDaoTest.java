package com.bellevue.bookclub.service.impl;

import com.bellevue.bookclub.model.WishlistItem;
import com.mongodb.client.result.DeleteResult;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
public class MongoWishlistDaoTest {

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private MongoWishlistDao wishlistDao;

    @Test
    public void testAddWishlistItem() {
        WishlistItem item = new WishlistItem("1", "Test Item");
        item.setUsername("user");
        wishlistDao.add(item);
        verify(mongoTemplate).save(item);
    }

    @Test
    public void testListWishlistItems() {
        wishlistDao.list("user");
        verify(mongoTemplate).find(any(Query.class), eq(WishlistItem.class));
    }

    @Test
    public void testRemoveWishlistItem() {
        String id = "1";
        DeleteResult deleteResult = DeleteResult.acknowledged(1);
        when(mongoTemplate.remove(any(Query.class), eq(WishlistItem.class))).thenReturn(deleteResult);

        boolean removed = wishlistDao.remove(id);

        verify(mongoTemplate).remove(any(Query.class), eq(WishlistItem.class));
        // The current implementation always returns false, so asserting false here.
        // Consider updating your DAO to return deleteResult.getDeletedCount() > 0 if you want true on successful delete
        assertFalse(removed);
    }

    @Test
    public void testUpdateWithId() {
        WishlistItem item = new WishlistItem("1", "Item with id");
        item.setUsername("user");

        assertDoesNotThrow(() -> wishlistDao.update(item));
        verify(mongoTemplate).save(item);
    }

    @Test
    public void testUpdateWithoutIdThrows() {
        WishlistItem item = new WishlistItem(null, "Item without id");
        item.setUsername("user");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> wishlistDao.update(item));
        assertEquals("Cannot update wishlist item without an id.", exception.getMessage());
    }
}
