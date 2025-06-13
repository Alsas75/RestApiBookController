package de.ait.javalessons.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.ait.javalessons.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@WebMvcTest(RestApiBookController.class)
class RestApiBookControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllBooksShouldReturnListOfBooks() throws Exception {
        //Отправляем HTTP запрос на /books
        var result = mockMvc.perform(get("/books"))
                .andReturn();

        //Проверяем что статус 200 OK
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

        List<Book> books = objectMapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<>(){}
        );
        assertThat(books).hasSize(7);
        assertThat(books.getFirst().getTitle()).isEqualTo("Clean Code");
        assertThat(books.getLast().getTitle()).isEqualTo("The Pragmatic Programmer");

    }

    @Test
    void getBookByIdShouldReturnCurrentBook() throws Exception {
        //Отправляем HTTP запрос на /books/1
        var result = mockMvc.perform(get("/books/1"))
                .andReturn();

        //Проверяем что статус 200 OK
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

        //Читаем JSON и превращаем в объект класса Book
        Book book = objectMapper.readValue(result.getResponse().getContentAsString(), Book.class);

        //Проверяем поля полученной книги
        assertThat(book.getId()).isEqualTo("1");
        assertThat(book.getTitle()).isEqualTo("Clean Code");
        assertThat(book.getAuthor()).isEqualTo("Robert C. Martin");
        assertThat(book.getYear()).isEqualTo(2008);
    }

    @Test
    void getBookByIdShouldReturnEmpty() throws Exception {

        //Запрашиваем книгу с несуществующим Id
        var result = mockMvc.perform(get(("/books/9999")))
                .andReturn();

        //Проверяем что статус 200 OK
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());

        //Тело ответа пустое
        assertThat(result.getResponse().getContentAsString()).isEmpty();
    }

    @Test
    void postBookShouldAddNewBook() throws Exception{
        //Создаем новую книгу
        Book book = new Book("20", "Test Book", "Test Author", 2025);

        //Post запрос на /books c книгой в теле метода
        var result = mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)//Тип содержимого
                        .content(objectMapper.writeValueAsString(book))) //Преобразуем Java объект в JSON
                .andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());

        Book bookFromResponse = objectMapper.readValue(result.getResponse().getContentAsString(), Book.class);

        assertThat(bookFromResponse.getId()).isEqualTo(book.getId());
        assertThat(bookFromResponse.getTitle()).isEqualTo(book.getTitle());
        assertThat(bookFromResponse.getAuthor()).isEqualTo(book.getAuthor());
        assertThat(bookFromResponse.getYear()).isEqualTo(book.getYear());
    }

    @Test
    void deleteBookShouldRemoveBook() throws Exception{
        //удаляем существующую книгу
        mockMvc.perform(delete("/books/2"))
                .andReturn();

        //Проверяем удалилась ли книга
        var result = mockMvc.perform(get("/books/2"))
                .andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void postBookShouldReturnBadRequestForInvalidInput() throws Exception {

        // Создаем книгу с некорректными данными (например, пустой заголовок)
        Book invalidBook = new Book("21", "", "Invalid Author", 2025);

        var result = mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidBook)))
                .andReturn();

        // Проверяем что статус 400
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

        assertThat(result.getResponse().getContentAsString()).contains("Title cannot be empty");
    }

    @Test
    void postBookShouldReturnBadRequestForNegativeJear() throws Exception {

        // Создаем книгу с некорректными данными значение year = -1
        Book invalidBook = new Book("99", "Omen", "Unknown Author", -1);

        var result = mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidBook)))
                .andReturn();

        // Проверяем что статус 400
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

        assertThat(result.getResponse().getContentAsString()).contains("Year cannot be negative");
    }

}