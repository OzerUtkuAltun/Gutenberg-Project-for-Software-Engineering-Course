package tr.edu.mcbu.gutenberg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.edu.mcbu.gutenberg.model.UserBook;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface UserBookRepository extends JpaRepository<UserBook, UUID> {

    Optional<UserBook> findUserBookByBookIdAndUserId(UUID bookId, UUID userId);

    Set<UserBook> findAllByUserId(UUID userId);

    Boolean existsByBookIdAndUserId(UUID bookId, UUID userId);

}
