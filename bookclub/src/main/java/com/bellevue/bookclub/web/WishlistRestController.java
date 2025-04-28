package com.bellevue.bookclub.web;

import com.bellevue.bookclub.service.dao.WishlistDao;
import com.bellevue.bookclub.service.impl.MongoWishlistDao;
import com.bellevue.bookclub.model.WishlistItem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/wishlist", produces = "application/json")
@CrossOrigin(origins = "*")
@Tag(name = "Wishlist", description = "Operations related to the wishlist")
public class WishlistRestController {

    private WishlistDao wishlistDao = new MongoWishlistDao();

    @Autowired
    public void setWishlistDao(WishlistDao wishlistDao) {
        this.wishlistDao = wishlistDao;
    }

    @Operation(summary = "View all wishlist items", description = "Returns a list of all wishlist items")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the wishlist items")
    @RequestMapping(method = RequestMethod.GET)
    public List<WishlistItem> showWishlist() {
        return wishlistDao.list();
    }

    @Operation(summary = "Get wishlist item by ID", description = "Fetches a specific wishlist item based on the provided ID.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the wishlist item")
    @ApiResponse(responseCode = "404", description = "Wishlist item not found")
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public WishlistItem findById(@PathVariable String id) {
        return wishlistDao.find(id);
    }
}
