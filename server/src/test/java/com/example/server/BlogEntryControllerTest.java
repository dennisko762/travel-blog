package com.example.server;

import com.example.server.controller.BlogEntryController;
import com.example.server.models.user.Role;
import com.example.server.models.user.BlogEntry;
import com.example.server.models.user.User;
import com.example.server.repositories.BlogEntryRepository;
import com.example.server.services.BlogEntryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.Filter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = BlogEntryController.class)
@ExtendWith(SpringExtension.class)
public class BlogEntryControllerTest {


    @Autowired
    private WebApplicationContext context;

    @Autowired
    private Filter springSecurityFilterChain;




    @TestConfiguration
    static class TravelEntryControllerTestConfiguration {

        @Autowired
        BlogEntryRepository blogEntryRepository;
        @Bean
        public BlogEntryService blogEntryService() {
            return new BlogEntryService(blogEntryRepository);
        }
    }

    private BlogEntryController blogEntryController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    BlogEntryService blogEntryService;

    @MockBean
    private BlogEntryRepository blogEntryRepository;

    @Autowired
    private ObjectMapper objectMapper;


    //As an administrator, I want to add, edit and delete entries
    //As a normal user I shall only get and comment on entries
    // I need to create mechanisms that will only allow functions for certain user groups and disallow the
    //functions for all other groups

    @Test
    void addTravelEntryTestWhenRegularUser() throws Exception {
        User user = new User("regularUser", "password", Role.REGULAR_USER);
        user.setId(1L);

        BlogEntry blogEntry = new BlogEntry("winter Tenerife", "source", "This is the main content");

        mockMvc.perform(post("/blog/entries").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(blogEntry))).andExpect(status().isForbidden());


        //addTravelEntry(User user) --> if user.role == ADMIN --> addEntry | throw some kind of exception

        //I want to test that first of all, only the Admin, who owns the site, can post entries
        //Second, when the admin wants to add an entry it should return a success message with the newly added entry
        //The admin can potentially add "duplicate" entries i.e.

    }

    @Test
    @WithMockUser(roles="ADMIN")
    void addTravelEntryTestWhenAdminUser() throws Exception {
        BlogEntry blogEntry = new BlogEntry("winter Tenerife", "source", "This is the main content");
        blogEntry.setId(1L);


        mockMvc.perform(post("/blog/entries").with(csrf()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(blogEntry))).andExpect(status().isOk());


        //addTravelEntry(User user) --> if user.role == ADMIN --> addEntry | throw some kind of exception

        //I want to test that first of all, only the Admin, who owns the site, can post entries
        //Second, when the admin wants to add an entry it should return a success message with the newly added entry
        //The admin can potentially add "duplicate" entries i.e.

    }

    @Test
    @WithMockUser(roles="USER")
    void getAllBlogEntriesTest() throws Exception {
        mockMvc.perform(get("/blog/entries")).andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithMockUser(roles="ADMIN")
    void deleteTravelBlogAsAdmin() throws Exception {
        mockMvc.perform(delete("/blog/entries/{id}", "1").with(csrf()).param("id", "1")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void deleteTravelBlogAsRegularUser() throws Exception {
        mockMvc.perform(delete("/blog/entries/1")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles="ADMIN")
    void editBlogEntryBlogAsAdmin() throws Exception {
        mockMvc.perform(put("/blog/entries/1").with(csrf()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new BlogEntry("title", "source", "content"))).with(user("user").password("password").roles("ADMIN"))).andExpect(status().isOk());
    }

}
