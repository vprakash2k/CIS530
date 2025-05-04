package com.bellevue.bookclub.web;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FaviconController {

    @RequestMapping("favicon.ico")
    public ResponseEntity<Resource> favicon() {
        Resource resource = new ClassPathResource("static/favicon.ico");
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("image/x-icon"))
                .body(resource);
    }
}
