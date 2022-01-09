package tr.edu.mcbu.gutenberg.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserResponse {

    private String firstName;

    private String lastName;

    private String username;

    private String city;

}
