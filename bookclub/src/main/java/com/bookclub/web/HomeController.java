package com.bookclub.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/") // Home page
    public String showHome() {
        return "index"; // name of the home template (index.html)
    }

    @RequestMapping("/about") // About Us page
    public String showAboutUs() {
        return "about"; // name of the about template (about.html)
    }

    @RequestMapping("/contact") // Contact Us page
    public String showContactUs() {
        return "contact"; // name of the contact template (contact.html)
    }
}
