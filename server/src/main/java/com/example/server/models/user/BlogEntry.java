package com.example.server.models.user;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "blogEntries")
@NoArgsConstructor
public class BlogEntry {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    @Column(name = "headerPicture")
    private String headerPicture;

    @Getter
    @Setter
    @Column(name = "mainContent")
    private String mainContent;

    public BlogEntry(String title, String headerPicture, String mainContent) {
        this.title = title;
        this.headerPicture = headerPicture;
        this.mainContent = mainContent;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof BlogEntry))
            return false;
        BlogEntry blogEntry = (BlogEntry) o;
        return Objects.equals(this.id, blogEntry.id) && Objects.equals(this.title, blogEntry.title)
                && Objects.equals(this.headerPicture, blogEntry.headerPicture) && Objects.equals(this.mainContent, blogEntry.mainContent);
    }

    @Override
    public String toString() {
        return "BlogEntry{" + "id=" + this.id + ", title='" + this.title + '\'' + ", headerPicture='" + this.headerPicture + '\'' + ", mainContent='" + this.mainContent + '\'' + '}';
    }
}
