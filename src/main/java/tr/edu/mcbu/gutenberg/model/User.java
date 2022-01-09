package tr.edu.mcbu.gutenberg.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class User extends BaseEntity {

    private String firstName;

    private String lastName;

    private String username;

    @JsonIgnore
    private String password;

    private String city;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @NotAudited
    @JsonIgnore
    private File image;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<UserBook> favoriteBooks;

}
