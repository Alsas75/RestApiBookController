package de.ait.javalessons.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public class Book {

    @NotBlank(message = "id should not be empty")
    private String id;

    @NotBlank(message = "title should not be empty")
    private String title ;

    @NotBlank(message = "author should not be empty")
    private String author ;

    @Positive(message = "Publication year must be greater than 0")
    private int year ;

    public Book() {
    }

    public Book(String id, String title, String author, int year) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public Book(String title){
        this.id = UUID.randomUUID().toString();
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
