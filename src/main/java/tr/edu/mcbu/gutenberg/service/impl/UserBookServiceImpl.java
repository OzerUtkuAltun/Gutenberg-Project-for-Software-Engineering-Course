package tr.edu.mcbu.gutenberg.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tr.edu.mcbu.gutenberg.exception.BookNotFoundException;
import tr.edu.mcbu.gutenberg.exception.ExceptionMessages;
import tr.edu.mcbu.gutenberg.model.UserBook;
import tr.edu.mcbu.gutenberg.model.response.MessageResponse;
import tr.edu.mcbu.gutenberg.repository.UserBookRepository;
import tr.edu.mcbu.gutenberg.service.UserBookService;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserBookServiceImpl implements UserBookService {

    private final UserBookRepository repository;

    @Override
    public UserBook getUserBookByBookIdAndUserId(UUID bookId, UUID userId) {

        UserBook userBook = null;

        try {
            userBook = repository.findUserBookByBookIdAndUserId(bookId, userId).orElseThrow(() -> new BookNotFoundException(ExceptionMessages.BOOK_NOT_FOUND));
        } catch (BookNotFoundException e) {
            log.error(e.getMessage());
        }

        return userBook;

    }

    @Override
    public void save(UserBook userBook) {
        repository.save(userBook);
    }

    @Override
    public Set<UserBook> findAllByUserId(UUID userId) {
        return repository.findAllByUserId(userId);
    }

    @Override
    public Boolean existsByBookIdAndUserId(UUID bookId, UUID userId) {
        return repository.existsByBookIdAndUserId(bookId, userId);
    }

    @Override
    public MessageResponse removeUserBook(UUID bookId, UUID userId) {

        UserBook userBook = getUserBookByBookIdAndUserId(bookId, userId);

        if (Objects.isNull(userBook)) {
            return new MessageResponse(false, "This user has not this book");
        }

        repository.delete(userBook);
        return new MessageResponse(true, "User book removed.");
    }

}
