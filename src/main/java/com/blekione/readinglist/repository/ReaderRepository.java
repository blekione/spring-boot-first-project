package com.blekione.readinglist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blekione.readinglist.domain.Reader;

public interface ReaderRepository extends JpaRepository<Reader, String> {

   Reader findByUsername(String username);
}
