package tr.edu.mcbu.gutenberg.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
public class UpdatePageRequest {

    @NotNull
    private UUID userId;

    @NotNull
    private UUID bookId;

    @NotNull
    private Integer currentPage;

}
