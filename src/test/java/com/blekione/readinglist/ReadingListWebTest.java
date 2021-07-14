package com.blekione.readinglist;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@SpringBootTest(classes = ReadingListApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class ReadingListWebTest {

   @Autowired
   TestRestTemplate restTemplate;

   @LocalServerPort
   private int      port;

   @Test
   void pageNotFound() throws Exception {
      // when
      final var actualResponseEntity = restTemplate.getForEntity("http://localhost:" + port + "/bogusPage",
            String.class);

      // then
      assertThat(actualResponseEntity.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));

   }
}
