package tr.edu.mcbu.gutenberg.service;

import tr.edu.mcbu.gutenberg.model.UserBook;
import tr.edu.mcbu.gutenberg.model.response.MessageResponse;

import java.util.Set;
import java.util.UUID;

public interface UserBookService {

    UserBook getUserBookByBookIdAndUserId(UUID bookId, UUID userId);

    void save(UserBook userBook);

    Set<UserBook> findAllByUserId(UUID userId);

    Boolean existsByBookIdAndUserId(UUID bookId, UUID userId);

    MessageResponse removeUserBook(UUID bookId, UUID userId);

}
