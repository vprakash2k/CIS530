package com.bellevue.bookclub.web;

import com.bellevue.bookclub.model.WishlistItem;
import com.bellevue.bookclub.service.dao.WishlistDao;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WishlistController.class)
@Import(WishlistControllerTest.MockConfig.class)
public class WishlistControllerTest {

    @Configuration
    static class MockConfig {
        @Bean
        public WishlistDao wishlistDao() {
            return Mockito.mock(WishlistDao.class);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WishlistDao wishlistDao;

    @Test
    public void testShowWishlist() throws Exception {
        WishlistItem item = new WishlistItem("1", "Test Item");
        item.setUsername("user");

        List<WishlistItem> items = List.of(item);

        Authentication auth = Mockito.mock(Authentication.class);
        when(auth.getName()).thenReturn("user");
        when(wishlistDao.list("user")).thenReturn(items);

        mockMvc.perform(get("/wishlist").principal(auth))
                .andExpect(status().isOk())
                .andExpect(view().name("wishlist/list"))
                .andExpect(model().attributeExists("wishlist"));
    }

    @Test
    public void testAddWishlistItem() throws Exception {
        Authentication auth = Mockito.mock(Authentication.class);
        when(auth.getName()).thenReturn("user");

        mockMvc.perform(post("/wishlist")
                        .param("isbn", "111222333")
                        .param("title", "JUnit in Action")
                        .principal(auth))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist"));

        Mockito.verify(wishlistDao).add(Mockito.argThat(item ->
                "111222333".equals(item.getIsbn()) &&
                        "JUnit in Action".equals(item.getTitle()) &&
                        "user".equals(item.getUsername())));
    }

    @Test
    public void testEditWishlistItem() throws Exception {
        WishlistItem item = new WishlistItem("9876543210", "Edit This Book");
        item.setUsername("user");
        item.setId("abc123");

        when(wishlistDao.find("abc123")).thenReturn(item);

        mockMvc.perform(get("/wishlist/abc123/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("wishlist/edit"))
                .andExpect(model().attributeExists("wishlistItem"));
    }

}
