package com.bellevue.bookclub.web;

import com.bellevue.bookclub.model.WishlistItem;
import com.bellevue.bookclub.service.dao.WishlistDao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
@RequestMapping("/wishlist")
@Tag(name = "Wishlist", description = "Operations related to the wishlist")
public class WishlistController {

    private WishlistDao wishlistDao;

    @Autowired
    private void setWishlistDao(WishlistDao wishlistDao) {
        this.wishlistDao = wishlistDao;
    }

    @Operation(summary = "View all wishlist items", description = "Displays a list of all wishlist items.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the wishlist")
    @RequestMapping(method = RequestMethod.GET)
    public String showWishlist(Model model) {
        List<WishlistItem> wishlist = wishlistDao.list();
        model.addAttribute("wishlist", wishlist);
        return "wishlist/list";
    }

    /*@RequestMapping(method = RequestMethod.GET)
    public String showWishlist() {
        return "wishlist/list";
    }*/
	@Operation(summary = "Create a new wishlist item", description = "Displays a form to create a new wishlist item.")
    @ApiResponse(responseCode = "200", description = "Successfully loaded the wishlist creation form")

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String wishlistForm(Model model) {
        model.addAttribute("wishlistItem", new WishlistItem());
        return "wishlist/new";
    }

    @Operation(summary = "Add a new wishlist item", description = "Adds a new item to the wishlist.")
    @ApiResponse(responseCode = "302", description = "Successfully added the item to the wishlist and redirected")
    @ApiResponse(responseCode = "400", description = "Validation error for the wishlist item")
    @RequestMapping(method = RequestMethod.POST)
    public String addWishlistItem(@Valid @ModelAttribute WishlistItem wishlistItem, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "wishlist/new";
        } else {
            //wishlistDao.list().add(wishlistItem);
            wishlistDao.add(wishlistItem);
            return "redirect:/wishlist";
        }
    }


/*    @PostMapping("/wishlist/add")
    public String addWishlistItem(@ModelAttribute WishlistItem item) {
        wishlistDao.add(item);
        return "redirect:/wishlist";
    }*/
}
