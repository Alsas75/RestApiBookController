package de.ait.javalessons.controller;

import de.ait.javalessons.model.Book;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/books")
public class RestApiBookController {

    private List<Book> bookList = new ArrayList<>();

    public RestApiBookController() {
        bookList.addAll(List.of(
                new Book("1", "Clean Code", "Robert C. Martin", 2008),
                new Book("2", "1984", "George Orwell", 1949),
                new Book("3", "Effective Java", "Joshua Bloch", 2018),
                new Book("4", "The Great Gatsby", "F. Scott Fitzgerald", 1925),
                new Book("5", "Refactoring", "Martin Fowler", 1999),
                new Book("6", "To Kill a Mockingbird", "Harper Lee", 1960),
                new Book("7", "The Pragmatic Programmer", "Andrew Hunt, David Thomas", 1999) // IT
        ));
    }

    @GetMapping
    Iterable<Book> getBooks() {
        log.info("Getting all books");
        return bookList;
    }

    @GetMapping("/{id}")
    ResponseEntity<Book> getBookById(@PathVariable String id) {
        for (Book book : bookList) {
            if (book.getId().equals(id)) {
                log.info("Book with id {} found", id);
                return ResponseEntity.ok(book);
            }
        }
        log.warn("Book with id {} not found", id);
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    ResponseEntity<Book> postBook(@Valid @RequestBody Book book) {
        bookList.add(book);
        log.info("Book with id {} added", book.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @PutMapping("/{id}")
    ResponseEntity<Book> putBook(@PathVariable String id, @RequestBody Book book) {
        int bookIndex = -1;
        for (Book bookInList : bookList) {
            if (bookInList.getId().equals(id)) {
                bookIndex = bookList.indexOf(bookInList);
                bookList.set(bookIndex, book);
                log.info("Book with id {} updated", id);
            }
        }
        return (bookIndex == -1) ? new ResponseEntity(postBook(book), HttpStatus.CREATED)
                : new ResponseEntity<>(book, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    void deleteBook(@PathVariable String id) {
        bookList.removeIf(book -> book.getId().equals(id));
        log.info("Book with id {} deleted", id);
    }
}