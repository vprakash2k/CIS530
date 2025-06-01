package com.bellevue.bookclub.web;

import com.bellevue.bookclub.model.WishlistItem;
import com.bellevue.bookclub.service.dao.WishlistDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WishlistController.class)
class WishlistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WishlistDao wishlistDao;

    @Test
    @WithMockUser(username = "testuser")
    public void testShowWishlist_shouldReturnWishlistViewWithItems() throws Exception {
        WishlistItem item = new WishlistItem();
        item.setId("1");
        item.setUsername("testuser");

        when(wishlistDao.list(anyString())).thenReturn(List.of(item));

        mockMvc.perform(get("/wishlist"))
                .andExpect(status().isOk())
                .andExpect(view().name("wishlist/list"))
                .andExpect(model().attributeExists("wishlist"));
    }

    @Test
    @WithMockUser
    public void addWishlistItem_shouldRedirectOnSuccess() throws Exception {
        mockMvc.perform(post("/wishlist")
                .param("title", "Test Title")
                .param("author", "Test Author")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/wishlist"));
    }

    @Test
    @WithMockUser
    public void editWishlistItem_shouldReturnEditView() throws Exception {
        WishlistItem item = new WishlistItem();
        item.setId("123");
        item.setUsername("user");

        when(wishlistDao.find("123")).thenReturn(item);

        mockMvc.perform(get("/wishlist/123/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("wishlist/edit"))
                .andExpect(model().attributeExists("wishlistItem"));
    }
}
