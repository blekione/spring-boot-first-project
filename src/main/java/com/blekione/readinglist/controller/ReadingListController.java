package com.blekione.readinglist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.blekione.readinglist.domain.AmazonProperties;
import com.blekione.readinglist.domain.Book;
import com.blekione.readinglist.repository.ReaderRepository;
import com.blekione.readinglist.repository.ReadingListRepository;

@Controller
@RequestMapping("/readingList")
public class ReadingListController {
   private final ReadingListRepository readingListRepository;
   private final ReaderRepository readerRepository;
   private final AmazonProperties amazonProperties;

   @Autowired
   public ReadingListController(ReadingListRepository readingListRepository, ReaderRepository readerRepository, AmazonProperties amazonProperties) {
      this.readingListRepository = readingListRepository;
      this.readerRepository = readerRepository;
      this.amazonProperties = amazonProperties;
   }

   @RequestMapping(value = "/{reader}")
   public String readersBooks(@PathVariable("reader") String readerName, Model model) {
      final var readingList = readingListRepository.findByReader(readerName);
      final var reader = readerRepository.findByUsername(readerName);
      if (readingList != null) {
         model.addAttribute("books", readingList);
         model.addAttribute("reader", reader);
         model.addAttribute("amazonID", amazonProperties.getAssociateId());
      }
      return "readingList";
   }

   @RequestMapping(value = "/{reader}", method = RequestMethod.POST)
   public String addToReadingList(@PathVariable("reader") String reader, Book book) {
      book.setReader(reader);
      readingListRepository.save(book);
      return "redirect:/readingList/{reader}";
   }

}
