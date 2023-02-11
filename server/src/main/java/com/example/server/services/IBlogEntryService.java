package com.example.server.services;

import com.example.server.models.user.BlogEntry;
import java.util.List;

public interface IBlogEntryService {

    BlogEntry addBlogEntry(BlogEntry blogEntry) throws Exception;

    List<BlogEntry> getAllBlockEntries();

    BlogEntry deleteBlogEntry(Long id);

    BlogEntry editBlogEntry(Long id, BlogEntry blogEntry);



}
