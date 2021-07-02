package com.blekione.readinglist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blekione.readinglist.domain.Book;

public interface ReadingListRepository extends JpaRepository<Book, Long> {

   /**
    * @param reader
    *           - the book reader
    * @return books for the given reader
    */
   List<Book> findByReader(String reader);

}
