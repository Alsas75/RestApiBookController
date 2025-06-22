package de.ait.javalessons.repository;

import org.springframework.data.repository.CrudRepository;

import de.ait.javalessons.model.Book;


public interface BookRepository extends CrudRepository<Book, String> {

}
