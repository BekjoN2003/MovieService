package com.example.test.controller;

import com.example.test.dto.movie.MovieCreateDto;
import com.example.test.dto.movie.MovieDto;
import com.example.test.dto.movie.MovieFilterDto;
import com.example.test.dto.movie.MovieListResponse;
import com.example.test.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/movies")
public class MovieController {
    private final MovieService movieService;

    @PostMapping("/create")
    public ResponseEntity<?> createMovie(@RequestBody @Valid MovieCreateDto dto) {
        MovieDto result = movieService.create(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMovie(@PathVariable("id") Integer id) {
        MovieDto result = movieService.get(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filterMovie(@RequestBody @Valid MovieFilterDto dto) {
        List<MovieDto> result = movieService.filter(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllMovies(@RequestParam("page") Integer page,
                                          @RequestParam("size") Integer size) {
        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = 10;
        }
        MovieListResponse movieListResponse = new MovieListResponse();
        movieListResponse.setDtoList(movieService.getAll(page, size));
        movieListResponse.setCount(movieService.getMoviesCount());
        return ResponseEntity.ok(movieListResponse);
    }


    // TODO: Update Movie (ADMIN)
    @PutMapping("/update/secured/{id}")
    public ResponseEntity<?> updateMovie(@PathVariable("id") @Valid MovieDto movieDto,
                                         Integer id) {
        MovieDto result = movieService.update(id, movieDto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/secured/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable("id") Integer id) {
        boolean result = movieService.delete(id);
        return ResponseEntity.ok(result);
    }
    // TODO: Change Visible (ADMIN)


}
