package com.example.server.controller;

import com.example.server.models.user.BlogEntry;
import com.example.server.models.user.User;
import com.example.server.services.BlogEntryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class BlogEntryController {


    private final BlogEntryService blogEntryService;

    public BlogEntryController(BlogEntryService travelEntryService) {
        this.blogEntryService = travelEntryService;
    }


    @GetMapping("/blog/entries")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllBlogEntries() {
        List<BlogEntry> blogEntryList = blogEntryService.getAllBlockEntries();
        System.out.println(blogEntryList);
        return new ResponseEntity<>(blogEntryList, HttpStatus.OK);
    }

    @GetMapping("/blog/entries/{id}")
    @ResponseBody
    public ResponseEntity<?> getBlogEntryById(@PathVariable Long id) {
        BlogEntry blogEntry = blogEntryService.getBlogEntryById(id);
        return new ResponseEntity<>(blogEntry, HttpStatus.OK);
    }


    @PostMapping("/blog/entries")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addTravelEntry(BlogEntry blogEntry) {
        BlogEntry entry = blogEntryService.addBlogEntry(blogEntry);
        return new ResponseEntity<>(entry, HttpStatus.OK);
    }

    @DeleteMapping("/blog/entries/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteBlogEntry(@PathVariable Long id, User user) {

        return new ResponseEntity<>(blogEntryService.deleteBlogEntry(id), HttpStatus.OK);
    }

    @PutMapping("/blog/entries/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> editBlogEntry(@PathVariable Long id, @RequestBody BlogEntry newBlogEntry) {
        return new ResponseEntity<>(blogEntryService.editBlogEntry(id, newBlogEntry), HttpStatus.OK);
    }
}