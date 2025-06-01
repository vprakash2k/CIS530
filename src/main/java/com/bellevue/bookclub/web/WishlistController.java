package com.bellevue.bookclub.web;

import com.bellevue.bookclub.model.WishlistItem;
import com.bellevue.bookclub.service.dao.WishlistDao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/wishlist")
public class WishlistController {

    private WishlistDao wishlistDao;

    @Autowired
    public void setWishlistDao(WishlistDao wishlistDao) {
        this.wishlistDao = wishlistDao;
    }

    @Operation(summary = "View all wishlist items", description = "Displays a list of all wishlist items.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the wishlist")
    @RequestMapping(method = RequestMethod.GET)
    public String showWishlist(Model model, Authentication authentication) {
        String username = authentication.getName();
        List<WishlistItem> wishlist = wishlistDao.list(username);
        model.addAttribute("wishlist", wishlist);
        return "wishlist/list";
    }

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
    public String addWishlistItem(@Valid @ModelAttribute WishlistItem wishlistItem, BindingResult bindingResult, Authentication authentication) {
        if (bindingResult.hasErrors()) {
            return "wishlist/new";
        } else {
            wishlistItem.setUsername(authentication.getName());
            wishlistDao.add(wishlistItem);
            return "redirect:/wishlist";
        }
    }

    @Operation(summary = "Edit wishlist item", description = "Displays the form to edit a wishlist item.")
    @RequestMapping(path = "/{id}/edit", method = RequestMethod.GET)
    public String editWishlistItem(@PathVariable String id, Model model) {
        WishlistItem wishlistItem = wishlistDao.find(id);
        System.out.println("wishlistItem--"+wishlistItem.getId());
        System.out.println("wishlistItem--"+wishlistItem.getUsername());
        model.addAttribute("wishlistItem", wishlistItem);
        return "wishlist/edit";
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Update wishlist item", description = "Processes the form submission to update a wishlist item.")
    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public String updateWishlistItem(@Valid @ModelAttribute WishlistItem wishlistItem, BindingResult bindingResult, Authentication authentication) {
        if (bindingResult.hasErrors()) {
            return "wishlist/edit";
        }


        if (wishlistItem.getId() != null) {
            wishlistItem.setUsername(authentication.getName());
            wishlistDao.update(wishlistItem);  // Update the existing item
        } else {
            System.err.println("No ID provided for WishlistItem!");
        }
        return "redirect:/wishlist";
    }

    @Operation(summary = "Remove a wishlist item", description = "Deletes a wishlist item from the user's list.")
    @RequestMapping(path = "/{id}/remove", method = RequestMethod.GET)
    public String removeWishlistItem(@PathVariable String id) {
        wishlistDao.remove(id);
        return "redirect:/wishlist";
    }
}
