package tr.edu.mcbu.gutenberg.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class BookRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String author;

    @Size(min = 15, max = 15000)
    private Integer totalPage;

    @NotBlank
    private String url;

    @NotNull
    private Long gutenbergId;

}
