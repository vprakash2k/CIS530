package com.bellevue.bookclub.web;

import com.bellevue.bookclub.model.Book;
import com.bellevue.bookclub.model.BookOfTheMonth;
import com.bellevue.bookclub.service.dao.BookDao;
import com.bellevue.bookclub.service.dao.BookOfTheMonthDao;
import com.bellevue.bookclub.service.impl.RestBookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.Authentication;


import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

@Controller
public class HomeController {

    private final RestBookDao restBookDao;

    @Autowired
    public HomeController(RestBookDao restBookDao) {
        this.restBookDao = restBookDao;
    }

    @Autowired
    private BookOfTheMonthDao bookOfTheMonthDao;

    @Autowired
    private BookDao bookDao;

    @GetMapping("/books")
    public String listBooks(Model model, Authentication authentication) {
        System.out.println("inside listBooks");
        String username = authentication.getName();
        List<Book> books = restBookDao.list(username);
        System.out.println("listBooks::" + books.toString());
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

    @GetMapping("/")
    public String showHome(Model model) {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        System.out.println("showHome month::" + month);

        List<BookOfTheMonth> monthlyBooks = bookOfTheMonthDao.list(String.valueOf(month));
        System.out.println("showHome monthlyBooks::" + monthlyBooks.toString());

        StringBuilder isbnBuilder = new StringBuilder("ISBN:");
        for (BookOfTheMonth book : monthlyBooks) {
            isbnBuilder.append(book.getIsbn()).append(",");
        }

        String isbnString = isbnBuilder.toString();
        if (isbnString.length() > 5) {
            isbnString = isbnString.substring(5, isbnString.length() - 1);
        }

        model.addAttribute("monthlyBooks", monthlyBooks);
        model.addAttribute("books", bookDao.list(isbnString));
        return "index";
    }
}
