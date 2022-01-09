package tr.edu.mcbu.gutenberg.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.UUID;

@Getter
@Setter
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserBook extends BaseEntity {

    @Type(type = "uuid-char")
    @Column(length = 36)
    private UUID bookId;

    @Type(type = "uuid-char")
    @Column(length = 36)
    private UUID userId;

    private Long gutenbergId;

    @Column(length = 70)
    private String name;

    @Column(length = 70)
    private String author;

    private Integer currentPage;

    @Column(length = 250)
    private String url;

    @Column(length = 250)
    private String imageUrl;

}
