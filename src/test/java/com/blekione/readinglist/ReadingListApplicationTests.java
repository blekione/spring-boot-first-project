package com.blekione.readinglist;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.blekione.readinglist.domain.Book;

@SpringBootTest
@WebAppConfiguration
class ReadingListApplicationTests {

   @Autowired
   private WebApplicationContext webContext;

   private MockMvc               mockMvc;

   @BeforeEach
   void setupMockMvc() {
      mockMvc = MockMvcBuilders.webAppContextSetup(webContext)
            .build();
   }

   @Test
   void canReachHomePage() throws Exception {
      // when
      final var resultActions = mockMvc.perform(get("/readingList/blah"));

      // then
      resultActions.andExpect(status().isOk())
         .andExpect(view().name("readingList"))
         .andExpect(model().attributeExists("books"))
         .andExpect(model().attribute("books", is(empty())));
   }

   @Test
   void postsBook() throws Exception {
      // when
      final var resultActions = mockMvc.perform(post("/readingList/blah")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("title", "BOOK TITLE")
            .param("author", "BOOK AUTHOR")
            .param("isbn", "1234567890")
            .param("description", "DESCRIPTION"));

      final var resultActions2 = mockMvc.perform(post("/readingList/blah")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("title", "BOOK TITLE1")
            .param("author", "BOOK AUTHOR1")
            .param("isbn", "12345678901")
            .param("description", "DESCRIPTION1"));

      // then
      resultActions.andExpect(status().is3xxRedirection())
         .andExpect(header().string("Location", "/readingList/blah"));
      resultActions2.andExpect(status().is3xxRedirection())
         .andExpect(header().string("Location", "/readingList/blah"));

      final var expectedBook1 = new Book(2L, "blah", "1234567890", "BOOK TITLE", "BOOK AUTHOR", "DESCRIPTION");
      final var expectedBook2 = new Book(3L, "blah", "12345678901", "BOOK TITLE1", "BOOK AUTHOR1", "DESCRIPTION1");

      mockMvc.perform(get("/readingList/blah")).andExpect(status().isOk())
         .andExpect(view().name("readingList"))
         .andExpect(model().attributeExists("books"))
         .andExpect(model().attribute("books", hasSize(2)))
         .andExpect(model().attribute("books",
               containsInAnyOrder(samePropertyValuesAs(expectedBook1, "id"),
                     samePropertyValuesAs(expectedBook2, "id"))));
   }
}
