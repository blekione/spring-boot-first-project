package com.blekione.readinglist.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "books")
public class Book {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "book_id", nullable = false)
   private Long   id;

   @Column(name = "book_reader", length = 128, nullable = true)
   private String reader;

   @Column(name = "book_isbn", length = 24, nullable = true)
   private String isbn;

   @Column(name = "book_title", length = 128, nullable = false)
   private String title;

   @Column(name = "book_author", length = 128, nullable = false)
   private String author;

   @Column(name = "book_description", length = 256, nullable = true)
   private String description;

   /**
    * Default constructor. Required by JPA specification
    */
   protected Book() {
   }

   /**
    * Constructor.
    *
    * @param id
    * @param reader
    * @param isbn
    * @param title
    * @param author
    * @param description
    */
   public Book(Long id, String reader, String isbn, String title, String author, String description) {
      this.id = id;
      this.reader = reader;
      this.isbn = isbn;
      this.title = title;
      this.author = author;
      this.description = description;
   }

   public Long getId() {
      return id;
   }

   public String getReader() {
      return reader;
   }

   public void setReader(String reader) {
      this.reader = reader;
   }

   public String getIsbn() {
      return isbn;
   }

   public String getTitle() {
      return title;
   }

   public String getAuthor() {
      return author;
   }

   public String getDescription() {
      return description;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((author == null) ? 0 : author.hashCode());
      result = prime * result + ((description == null) ? 0 : description.hashCode());
      result = prime * result + ((id == null) ? 0 : id.hashCode());
      result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
      result = prime * result + ((reader == null) ? 0 : reader.hashCode());
      result = prime * result + ((title == null) ? 0 : title.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      }
      if (obj == null) {
         return false;
      }
      if (getClass() != obj.getClass()) {
         return false;
      }
      final Book other = (Book) obj;
      if (author == null) {
         if (other.author != null) {
            return false;
         }
      } else if (!author.equals(other.author)) {
         return false;
      }
      if (description == null) {
         if (other.description != null) {
            return false;
         }
      } else if (!description.equals(other.description)) {
         return false;
      }
      if (id == null) {
         if (other.id != null) {
            return false;
         }
      } else if (!id.equals(other.id)) {
         return false;
      }
      if (isbn == null) {
         if (other.isbn != null) {
            return false;
         }
      } else if (!isbn.equals(other.isbn)) {
         return false;
      }
      if (reader == null) {
         if (other.reader != null) {
            return false;
         }
      } else if (!reader.equals(other.reader)) {
         return false;
      }
      if (title == null) {
         if (other.title != null) {
            return false;
         }
      } else if (!title.equals(other.title)) {
         return false;
      }
      return true;
   }
}
