package tr.edu.mcbu.gutenberg.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Setter
public class UserUpdateRequest {

    @NotNull
    private UUID userId;

    @NotBlank
    @Size(min = 3, max = 25)
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 25)
    private String lastName;

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(min = 6, max = 30)
    private String password;

    @NotBlank
    @Size(min = 2, max = 40)
    private String city;
}
