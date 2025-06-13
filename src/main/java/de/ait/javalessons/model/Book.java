package de.ait.javalessons.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Book {

    @NotBlank(message = "Id не должно быть пустым")
    private  String id;

    @NotBlank(message = "Название не должно быть пустым")
    private String title;

    @NotBlank (message = "Автор книги должен быть обязательно")
    private String author;

    @NotBlank (message = "Год выпуска не может быть пустым")
    private int year;
}
