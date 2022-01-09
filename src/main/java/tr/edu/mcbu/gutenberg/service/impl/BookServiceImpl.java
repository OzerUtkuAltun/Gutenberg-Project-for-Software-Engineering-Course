package tr.edu.mcbu.gutenberg.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tr.edu.mcbu.gutenberg.exception.BookNotFoundException;
import tr.edu.mcbu.gutenberg.exception.ExceptionMessages;
import tr.edu.mcbu.gutenberg.model.Book;
import tr.edu.mcbu.gutenberg.model.request.BookRequest;
import tr.edu.mcbu.gutenberg.model.response.MessageResponse;
import tr.edu.mcbu.gutenberg.repository.BookRepository;
import tr.edu.mcbu.gutenberg.service.BookService;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository repository;

    @Override
    public void save(Book book) {
        repository.save(book);
    }

    @Override
    public MessageResponse createBook(BookRequest bookRequest) {
        Book book = Book.builder()
                .author(bookRequest.getAuthor())
                .name(bookRequest.getName())
                .url(bookRequest.getUrl())
                .currentPage(0)
                .gutenbergId(bookRequest.getGutenbergId())
                .build();

        save(book);

        return new MessageResponse(true, book.getName() + " is added to system.");
    }


    @Override
    public Book getBookById(UUID bookId) {

        Book book = null;
        try {
            book = repository.findById(bookId).orElseThrow(() -> new BookNotFoundException(ExceptionMessages.BOOK_NOT_FOUND));
        } catch (BookNotFoundException e) {
            log.error(e.getMessage());
        }
        return book;
    }

    @Override
    public List<Book> listBooks() {
        return repository.findAll();
    }

}
