package com.example.server.services;

import com.example.server.models.user.BlogEntry;
import com.example.server.repositories.BlogEntryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogEntryService implements IBlogEntryService {
    private final BlogEntryRepository blogEntryRepository;

    public BlogEntryService(BlogEntryRepository travelEntryRepository) {
        this.blogEntryRepository = travelEntryRepository;
    }

    public BlogEntry addBlogEntry(BlogEntry blogEntry) {
        return blogEntryRepository.save(blogEntry);
        }

    public List<BlogEntry> getAllBlockEntries() {
        return blogEntryRepository.findAll();
    }

    public BlogEntry getBlogEntryById(Long id) {
        return blogEntryRepository.getReferenceById(id);
    }

    public BlogEntry deleteBlogEntry(Long id) {
        BlogEntry entry = getBlogEntryById(id);
        blogEntryRepository.deleteById(id);
        return entry;
    }

    public BlogEntry editBlogEntry(Long id, BlogEntry blogEntry) {
        return blogEntryRepository.findById(id).map(entry -> {
            entry.setTitle(blogEntry.getTitle());
            entry.setMainContent(blogEntry.getMainContent());
            entry.setHeaderPicture(blogEntry.getHeaderPicture());
            return blogEntryRepository.save(blogEntry);
        }).orElseGet(() -> {
            blogEntry.setId(id);
            return blogEntryRepository.save(blogEntry);
        });
    }
}
