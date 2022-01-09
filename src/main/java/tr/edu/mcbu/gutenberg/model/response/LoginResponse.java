package tr.edu.mcbu.gutenberg.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {

    private UUID userId;

    private Boolean success;

    private String message;

}
