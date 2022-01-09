package tr.edu.mcbu.gutenberg.service;

import tr.edu.mcbu.gutenberg.model.Book;
import tr.edu.mcbu.gutenberg.model.request.BookRequest;
import tr.edu.mcbu.gutenberg.model.response.MessageResponse;

import java.util.List;
import java.util.UUID;

public interface BookService {

    void save(Book book);

    MessageResponse createBook(BookRequest bookRequest);

    Book getBookById(UUID bookId);

    List<Book> listBooks();



}
