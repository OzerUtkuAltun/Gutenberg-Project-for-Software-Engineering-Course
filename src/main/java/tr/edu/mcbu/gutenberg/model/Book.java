package tr.edu.mcbu.gutenberg.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Book extends BaseEntity {

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
