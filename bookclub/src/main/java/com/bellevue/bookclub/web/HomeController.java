package com.bellevue.bookclub.web;

import com.bellevue.bookclub.model.Book;
import com.bellevue.bookclub.service.impl.MemBookDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/")
    public String showHome(Model model) {
        MemBookDao booksDao = new MemBookDao();
        List<Book> books = booksDao.list();
        model.addAttribute("books", books);
        return "index"; // name of the home template (index.html)
    }

    @RequestMapping("/about")
    public String showAboutUs() {
        return "about"; // name of the about template (about.html)
    }

    @RequestMapping("/contact")
    public String showContactUs() {
        return "contact"; // name of the contact template (contact.html)
    }

    @GetMapping("/{id}")
    public String getMonthlyBook(@PathVariable("id") String id, Model model) {
        MemBookDao booksDao = new MemBookDao();
        Book book = booksDao.find(id);
        model.addAttribute("book", book);
        return "monthly-books/view"; // view for the selected book
    }
}
