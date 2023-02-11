package com.example.server.repositories;

import com.example.server.models.user.BlogEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogEntryRepository extends JpaRepository<BlogEntry, Long> {
}
