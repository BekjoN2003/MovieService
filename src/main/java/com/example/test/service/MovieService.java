package com.example.test.service;

import com.example.test.dto.movie.MovieCreateDto;
import com.example.test.dto.movie.MovieDto;
import com.example.test.dto.movie.MovieFilterDto;
import com.example.test.dto.user.UserDto;
import com.example.test.entity.Movie;
import com.example.test.exception.BadRequest;
import com.example.test.repository.MovieRepo;
import com.example.test.utill.SpringSecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MovieService {
    private final MovieRepo movieRepo;
    private final UserService userService;


    public MovieService(MovieRepo movieRepo, UserService userService) {
        this.movieRepo = movieRepo;
        this.userService = userService;
    }

    public MovieDto create(MovieCreateDto createDto) {
        Movie movie = new Movie();
        movie.setName(createDto.getName());
        movie.setDescription(createDto.getDescription());
        movie.setCreatorId(SpringSecurityUtil.getUserId());
        movie.setCreatedAt(LocalDateTime.now());
        movieRepo.save(movie);
        return convertToDto(movie, new MovieDto());
    }

    public MovieDto get(Integer id) {
        return convertToDto(getEntity(id), new MovieDto());
    }

    public Movie getEntity(Integer id) {
        Optional<Movie> optional = movieRepo.findByIdAndDeletedAtIsNull(id);
        if (optional.isEmpty()) {
            throw new BadRequest("Movie not found");
        }
        return optional.get();
    }

    public MovieDto convertToDto(Movie movie, MovieDto dto) {
        dto.setId(movie.getId());
        dto.setName(movie.getName());
        dto.setDescription(movie.getDescription());
        dto.setUser(userService.convertToDto(userService.getEntity(movie.getCreatorId()), new UserDto()));
        return dto;
    }

    public List<MovieDto> filter(MovieFilterDto dto) {
        String sortBy = "createdAt";
        if (dto.getSortBy() != null) {
            sortBy = dto.getSortBy();
        }

        PageRequest pageable = PageRequest.of(dto.getPage(), dto.getSize(), dto.getDirection(), sortBy);
        List<Predicate> predicateList = new LinkedList<>();
        Specification<Movie> specification = ((root, query, criteriaBuilder) -> {
            if (dto.getName() != null) {
                predicateList.add(criteriaBuilder.like(root.get("name"), "%" +
                        dto.getName() + "%"));
            }
            if (dto.getCreatorId() != null) {
                predicateList.add(criteriaBuilder.equal(root.get("user"),
                        dto.getCreatorId()));
            }
            if (dto.getDescription() != null) {
                predicateList.add(criteriaBuilder.equal(root.get("description"),
                        dto.getDescription()));
            }
            if (dto.getMinCreatedDate() != null && dto.getMaxCreatedDate() != null) {
                predicateList.add(criteriaBuilder.between(root.get("createdAt"),
                        dto.getMinCreatedDate(), dto.getMaxCreatedDate()));
            }
            if (dto.getMinCreatedDate() == null && dto.getMaxCreatedDate() != null) {
                predicateList.add(criteriaBuilder.lessThan(root.get("createdAt"),
                        dto.getMinCreatedDate()));
            }
            if (dto.getMinCreatedDate() != null && dto.getMaxCreatedDate() == null) {
                predicateList.add(criteriaBuilder.greaterThan(root.get("createdAt"),
                        dto.getMaxCreatedDate()));
            }
            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        });
        Page<Movie> page = movieRepo.findAll(specification, pageable);
        return page.stream().map(movie -> convertToDto(movie, new MovieDto())).collect(Collectors.toList());
    }

    public List<MovieDto> getAll(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Movie> pageList = movieRepo.pageMovie(pageRequest);
        log.info("movies get");
        List<MovieDto> movieDtoList = new ArrayList<>();
        for (Movie m : pageList) {
            if (m.getDeletedAt() == null) {
                movieDtoList.add(convertToDto(m, new MovieDto()));
            }
        }
        return movieDtoList;
/*        return pageList.stream().map(movie -> {
                    if (movie.getDeletedAt() == null) {
                        return convertToDto(movie, new MovieDto());
                    }
                    return null;
                }
        ).collect(Collectors.toList());*/

        /*return pageList.stream().map(movie -> {
            if (movie.getDeletedAt() == null) return convertToDto(movie, new MovieDto());
            return null;
        }
        ) .collect(Collectors.toList());//Lambda expression*/
    }

    public boolean delete(Integer id) {
        Movie movie = getEntity(id);
        movie.setDeletedAt(LocalDateTime.now());
        movieRepo.save(movie);
        return true;
    }

    public MovieDto update(Integer id, MovieDto movieDto) {
        Movie movie = getEntity(id);
        movie.setName(movieDto.getName());
        movie.setDescription(movieDto.getDescription());
        return convertToDto(movie, new MovieDto());
    }

    public Long getMoviesCount() {
        return movieRepo.movieCount();
    }
}
