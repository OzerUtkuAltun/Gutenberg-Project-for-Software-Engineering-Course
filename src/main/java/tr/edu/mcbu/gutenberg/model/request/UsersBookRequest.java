package tr.edu.mcbu.gutenberg.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UsersBookRequest {

    private UUID userId;
    private UUID bookId;

}
