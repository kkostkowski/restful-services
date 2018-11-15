package pl.algorit.restfulservices.services.movies.detailsandcomments;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.algorit.restfulservices.services.movies.details.MovieDetailsService;
import pl.algorit.restfulservices.services.movies.details.MovieMapper;

@AllArgsConstructor
@RestController
@RequestMapping("/rest/api/v1/detailsandcomments")
public class DetailsAndCommentsController {

    private MovieDetailsService movieDetailsService;

    private MovieMapper movieMapper;

    @GetMapping("/{movieId}")
    public DetailsAndCommentsDTO getMovieDetailsAndComments(@PathVariable Integer movieId) {
        val movie = movieDetailsService.getMovieDetails(movieId);
//        val movie = movieDetailsService.getMovieComments(movieId);

//        return movieMapper.map(movie);
        return null;
    }
}
