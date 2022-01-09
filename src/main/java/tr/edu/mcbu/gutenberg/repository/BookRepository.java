package tr.edu.mcbu.gutenberg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.edu.mcbu.gutenberg.model.Book;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {

}
