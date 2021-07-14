package com.blekione.readinglist;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@SpringBootTest(classes = ReadingListApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReadingListEndToEndTest {

   @LocalServerPort
   private int              port;

   private static WebDriver browser;

   @BeforeAll
   static void openBrowser() {
      final var options = new FirefoxOptions();
      options.setHeadless(true);
      browser = new FirefoxDriver(options);
      browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
   }

   @AfterAll
   static void closeBrowser() {
      browser.quit();
   }

   static void logIn() {
      browser.findElement(By.name("username")).sendKeys("bleki");
      browser.findElement(By.name("password")).sendKeys("pass");
      browser.findElement(By.tagName("button")).click();
   }

   @Test
   void redirectsToLoginPageAtFirstAppLading() throws Exception {
      // given
      final var baseUrl = "http://localhost:" + port + "/readingList/bleki";

      // when
      browser.get(baseUrl);

      // then
      assertThat(browser.getCurrentUrl(), equalTo("http://localhost:" + port + "/login"));
   }

   @Test
   void returnsToRequestedPageAfterLoggedIn() throws Exception {
      // given
      final var baseUrl = "http://localhost:" + port + "/readingList/bleki";

      // when
      browser.get(baseUrl);
      logIn();

      // then
      assertThat(browser.getCurrentUrl(), equalTo(baseUrl));
   }

   @Test
   void addBookToEmptyListOfBooks() throws Exception {
      // given
      final var baseUrl = "http://localhost:" + port + "/readingList/bleki";

      // when
      browser.get(baseUrl);
      logIn();

      assertThat(browser.findElement(By.id("reading-list")).getText(), equalTo("You have no books in your book list"));

      browser.findElement(By.name("title")).sendKeys("Dune");
      browser.findElement(By.name("author")).sendKeys("Frank Herbert");
      browser.findElement(By.name("isbn")).sendKeys("1234567890");
      browser.findElement(By.name("description")).sendKeys("Arrakis, Freemen, worms and spice.");
      browser.findElement(By.tagName("form")).submit();

      // then
      assertThat(browser.findElement(By.cssSelector("dt.bookHeadline")).getText(),
            equalTo("Dune by Frank Herbert (ISBN 1234567890)"));
      assertThat(browser.findElement(By.cssSelector("dd.bookDescription")).getText(),
            equalTo("Arrakis, Freemen, worms and spice."));
   }

}
