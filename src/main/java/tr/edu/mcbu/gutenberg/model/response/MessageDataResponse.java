package tr.edu.mcbu.gutenberg.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MessageDataResponse<T> {

    private Boolean success;
    private T data;
    private String message;
}
