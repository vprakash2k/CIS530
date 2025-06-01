package com.bellevue.bookclub.web;

import com.bellevue.bookclub.model.Book;
import com.bellevue.bookclub.model.BookOfTheMonth;
import com.bellevue.bookclub.service.dao.BookDao;
import com.bellevue.bookclub.service.dao.BookOfTheMonthDao;
import com.bellevue.bookclub.service.impl.RestBookDao;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HomeController.class)
@Import(HomeControllerTest.TestConfig.class)
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public RestBookDao restBookDao() {
            return Mockito.mock(RestBookDao.class);
        }

        @Bean
        public BookOfTheMonthDao bookOfTheMonthDao() {
            return Mockito.mock(BookOfTheMonthDao.class);
        }

        @Bean
        public BookDao bookDao() {
            return Mockito.mock(BookDao.class);
        }
    }

    @Autowired
    private RestBookDao restBookDao;

    @Autowired
    private BookOfTheMonthDao bookOfTheMonthDao;

    @Autowired
    private BookDao bookDao;


    @Test
    @WithMockUser(username = "user", roles = "USER")
    void listBooks_shouldReturnIndexWithBooks() throws Exception {
        when(restBookDao.list("user")).thenReturn(List.of(new Book()));

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("books"));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")  // Added this annotation
    public void testShowHome() throws Exception {
        BookOfTheMonth botm = new BookOfTheMonth();
        botm.setIsbn("123");
        when(bookOfTheMonthDao.list(anyString())).thenReturn(List.of(botm));

        Book book = new Book();
        book.setIsbn("123");
        book.setTitle("Monthly Book");
        when(bookDao.list(anyString())).thenReturn(List.of(book));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("monthlyBooks"))
                .andExpect(model().attributeExists("books"));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testShowAboutUs() throws Exception {
        mockMvc.perform(get("/about"))
                .andExpect(status().isOk())
                .andExpect(view().name("about"));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void getBook_shouldReturnBookView_whenBookExists() throws Exception {
        Book book = new Book();
        when(restBookDao.find("123456")).thenReturn(book);

        mockMvc.perform(get("/123456"))
                .andExpect(status().isOk())
                .andExpect(view().name("monthly-books/view"))
                .andExpect(model().attributeExists("book"));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void getBook_shouldRedirectToHome_whenBookNotFound() throws Exception {
        when(restBookDao.find("999")).thenReturn(null);

        mockMvc.perform(get("/999"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}
