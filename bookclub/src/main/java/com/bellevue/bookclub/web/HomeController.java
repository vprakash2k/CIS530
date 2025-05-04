package com.bellevue.bookclub.web;

import com.bellevue.bookclub.model.Book;
import com.bellevue.bookclub.service.impl.RestBookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController {

    private final RestBookDao restBookDao;

    @Autowired
    public HomeController(RestBookDao restBookDao) {
        this.restBookDao = restBookDao;
    }

    @GetMapping("/")
    public String listBooks(Model model) {
        System.out.println("inside listBooks");
        List<Book> books = restBookDao.list();
        System.out.println("books::" + books.toString());
        model.addAttribute("books", books);
        return "index";
    }


    @GetMapping("/{isbn}")
    public String getBook(@PathVariable String isbn, Model model) {
        if (isbn.equalsIgnoreCase("favicon.ico")) {
            System.out.println("inside favicon.ico");
            return "redirect:/"; // or return nothing
        }

        Book book = restBookDao.find(isbn);
        if (book == null) {
            model.addAttribute("error", "Book not found");
            return "redirect:/";
        }
        model.addAttribute("book", book);
        return "monthly-books/view";
    }

    /*@GetMapping("/books/{id}")
    public String getMonthlyBook(@PathVariable("id") String id, Model model) {
        Book book = restBookDao.find(id);
        if (book == null) {
            return "redirect:/";
        }
        model.addAttribute("book", book);
        return "monthly-books/view";
    }*/

    @RequestMapping("/about")
    public String showAboutUs() {
        return "about";
    }

    @RequestMapping("/contact")
    public String showContactUs() {
        return "contact";
    }
}
