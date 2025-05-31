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
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

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
    public void testListBooks() throws Exception {
        Book book = new Book();
        book.setIsbn("1234567890");
        book.setTitle("Test Book");
        List<Book> books = List.of(book);

        Authentication auth = Mockito.mock(Authentication.class);
        when(auth.getName()).thenReturn("user");
        when(restBookDao.list("user")).thenReturn(books);

        mockMvc.perform(get("/books").principal(auth))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("books"));
    }

    @Test
    public void testShowHome() throws Exception {
        BookOfTheMonth botm = new BookOfTheMonth();
        botm.setIsbn("123");
        when(bookOfTheMonthDao.list(Mockito.anyString())).thenReturn(List.of(botm));

        Book book = new Book();
        book.setIsbn("123");
        book.setTitle("Monthly Book");
        when(bookDao.list(Mockito.anyString())).thenReturn(List.of(book));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("monthlyBooks"))
                .andExpect(model().attributeExists("books"));
    }

  

    @Test
    public void testGetBookFound() throws Exception {
        Book book = new Book();
        book.setIsbn("1234567890");
        book.setTitle("Found Book");
        when(restBookDao.find("1234567890")).thenReturn(book);

        mockMvc.perform(get("/1234567890"))
                .andExpect(status().isOk())
                .andExpect(view().name("monthly-books/view"))
                .andExpect(model().attributeExists("book"));
    }

    @Test
    public void testGetBookNotFound() throws Exception {
        when(restBookDao.find("notfound")).thenReturn(null);

        mockMvc.perform(get("/notfound"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}
