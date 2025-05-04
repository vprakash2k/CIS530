package com.bellevue.bookclub.service.impl;

import com.bellevue.bookclub.model.Book;
import com.bellevue.bookclub.service.dao.BookDao;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RestBookDao implements BookDao {

    @Override
    public Book find(String key) {
        System.out.println("find isbn::" + key);
        String isbnString = "ISBN:" + key;
        System.out.println("isbnString::" + isbnString);
        Object doc = getBooksDoc(isbnString);
        if (doc == null) return null;

        List<String> titles = JsonPath.read(doc, "$..title");
        List<String> isbns = JsonPath.read(doc, "$..bib_key");
        List<String> descs = JsonPath.read(doc, "$..description");
        List<String> infoUrls = JsonPath.read(doc, "$..info_url");
        List<Integer> pages = JsonPath.read(doc, "$..number_of_pages");

        String isbn = !isbns.isEmpty() ? isbns.get(0) : "N/A";
        String title = !titles.isEmpty() ? titles.get(0) : "N/A";
        String desc = !descs.isEmpty() ? descs.get(0) : "N/A";
        String infoUrl = !infoUrls.isEmpty() ? infoUrls.get(0) : "N/A";
        int numOfPages = !pages.isEmpty() ? pages.get(0) : 0;

        return new Book(isbn, title, desc, infoUrl, numOfPages);
    }


    public Object getBooksDoc(String isbnString) {
        try {
            System.out.println("getBooksDoc--isbnString::" + isbnString);

            String openLibraryURL = "https://openlibrary.org/api/books";

            RestTemplate rest = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(openLibraryURL)
                .queryParam("bibkeys", isbnString)
                .queryParam("format", "json")
                .queryParam("jscmd", "details");

        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = rest.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                String.class
        );

            String jsonBookList = response.getBody();
            System.out.println("jsonBookList: " + jsonBookList);
            return Configuration.defaultConfiguration().jsonProvider().parse(jsonBookList);
        } catch (HttpServerErrorException e) {
            System.err.println("OpenLibrary API returned error: " + e.getStatusCode());
            return null;
        } catch (Exception e) {
            System.err.println("Unexpected error while calling OpenLibrary: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Book> list() {
        String isbnString = "9780593099322,9780261102361,9780261102378,9780590302715,9780316769532";
        System.out.println("list isbn::" + isbnString);
        Object doc = getBooksDoc(isbnString);

        List<String> titles = JsonPath.read(doc, "$..title");
        List<String> isbns = JsonPath.read(doc, "$..bib_key");
        List<String> infoUrls = JsonPath.read(doc, "$..info_url");

        List<Book> books = new ArrayList<>();
        for (int i = 0; i < titles.size(); i++) {
            String isbn = isbns.size() > i ? isbns.get(i) : "N/A";
            String title = titles.size() > i ? titles.get(i) : "N/A";
            String infoUrl = infoUrls.size() > i ? infoUrls.get(i) : "N/A";

            books.add(new Book(isbn, title, "N/A", infoUrl, 0));
        }
        return books;
    }

}
