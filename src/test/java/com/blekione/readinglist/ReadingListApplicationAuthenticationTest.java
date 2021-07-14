package com.blekione.readinglist;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.blekione.readinglist.domain.Reader;

@SpringBootTest
@AutoConfigureMockMvc
public class ReadingListApplicationAuthenticationTest {

   @Autowired
   private MockMvc               mockMvc;

   @Test
   void redirectToLoginPageUnauthenticatedUser() throws Exception {
      // when
      final var resultActions = mockMvc.perform(get("/readingList/blah"));

      // then
      resultActions.andExpect(status().is3xxRedirection())
         .andExpect(header().string("Location", "http://localhost/login"));
   }

   @Test
   @WithMockUser(username = "bleki", password = "pass", roles = "bob")
   void givesAccessToAuthenticatedUser() throws Exception {
      // when
      final var resultActions = mockMvc.perform(get("/readingList/bleki"));

      // then
      final var expectedReader = new Reader("bleki", "Marcin Kruglik", "pass");
      resultActions.andExpect(status().isOk())
         .andExpect(view().name("readingList"))
         .andExpect(model().attribute("reader", samePropertyValuesAs(expectedReader)))
         .andExpect(model().attribute("books", hasSize(0)));
   }
}
