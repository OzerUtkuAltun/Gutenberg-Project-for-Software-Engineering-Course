package tr.edu.mcbu.gutenberg.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.edu.mcbu.gutenberg.model.Book;
import tr.edu.mcbu.gutenberg.model.request.BookRequest;
import tr.edu.mcbu.gutenberg.model.response.MessageDataResponse;
import tr.edu.mcbu.gutenberg.model.response.MessageResponse;
import tr.edu.mcbu.gutenberg.service.BookService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Slf4j
public class BooksController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<MessageDataResponse<List<Book>>> listAllBooks() {

        return ResponseEntity.ok(new MessageDataResponse<>(true,
                bookService.listBooks(), "All books listed."));
    }

    @PostMapping
    public ResponseEntity<MessageResponse> addBook(BookRequest bookRequest) {

        return ResponseEntity.ok(bookService.createBook(bookRequest));
    }
}
