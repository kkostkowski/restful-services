package pl.algorit.restfulservices.services.movies.details;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/rest/api/v1/movies")
public class MovieDetailsController {

    private MovieDetailsService movieDetailsService;

    private MovieMapper movieMapper;

    @GetMapping("/")
    public Collection<MovieDetailsDTO> getAllMovieDetails() {
        val all = movieDetailsService.getAllMovieDetails();

        return all.stream()
                .map(movieMapper::map)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public MovieDetailsDTO getMovieDetails(@PathVariable Integer id) {
        val movie = movieDetailsService.getMovieDetails(id);

        return movieMapper.map(movie);
    }

    @PostMapping(value = "/")
    public ResponseEntity<Object> createMovie(@RequestBody MovieDetailsDTO movieDetailsDTO) {
        val movie = movieMapper.map(movieDetailsDTO);
        val created = movieDetailsService.createMovie(movie);
        val location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .build();
    }
}
