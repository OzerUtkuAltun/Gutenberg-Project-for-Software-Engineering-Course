package tr.edu.mcbu.gutenberg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.edu.mcbu.gutenberg.model.File;
import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<File, UUID> {


}
