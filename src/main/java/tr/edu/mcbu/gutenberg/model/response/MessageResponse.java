package tr.edu.mcbu.gutenberg.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MessageResponse {

    private Boolean success;
    private String message;
}
