package com.bellevue.bookclub.web;

import com.bellevue.bookclub.model.WishlistItem;
import com.bellevue.bookclub.service.impl.MemWishlistDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/wishlist")
public class WishlistController {

    private final MemWishlistDao wishlistDao = new MemWishlistDao();

    @RequestMapping(method = RequestMethod.GET)
    public String showWishlist(Model model) {
        model.addAttribute("wishlist", wishlistDao.list());
        return "wishlist/list";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String wishlistForm(Model model) {
        model.addAttribute("wishlistItem", new WishlistItem());
        return "wishlist/new";
    }

    // Add a new wishlist item
    @RequestMapping(method = RequestMethod.POST)
    public String addWishlistItem(@Valid WishlistItem wishlistItem, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "wishlist/new";
        } else {
            wishlistDao.list().add(wishlistItem);  // Add item to the list (in memory)
            return "redirect:/wishlist";
        }
    }
}
