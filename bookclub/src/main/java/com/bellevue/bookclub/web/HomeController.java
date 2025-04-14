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
        MemBookDao bookDao = new MemBookDao();
        List<Book> books = bookDao.list();
        model.addAttribute("books", books);
        return "index";
    }

    @RequestMapping("/about")
    public String showAboutUs() {
        return "about";
    }

    @RequestMapping("/contact")
    public String showContactUs() {
        return "contact";
    }

    @GetMapping("/{id}")
    public String getMonthlyBook(@PathVariable("id") String id, Model model) {
        MemBookDao bookDao = new MemBookDao();
        Book book = bookDao.find(id);
        if (book == null) {
            return "redirect:/";
        }
        model.addAttribute("book", book);
        return "monthly-books/view";
    }
}
