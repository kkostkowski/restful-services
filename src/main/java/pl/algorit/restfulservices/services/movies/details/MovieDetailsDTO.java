package pl.algorit.restfulservices.services.movies.details;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(of = {"title", "description"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieDetailsDTO {
    Integer id;
    String title;
    String description;
}
