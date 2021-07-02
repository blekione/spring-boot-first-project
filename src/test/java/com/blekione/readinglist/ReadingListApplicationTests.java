package com.blekione.readinglist;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest

class ReadingListApplicationTests {

	@Test
	void contextLoads() {
	   String lol = null;;

	   assertThrows(NullPointerException.class, () -> lol.split(","));
	}

}
