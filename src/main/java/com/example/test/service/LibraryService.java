package com.example.test.service;

import com.example.test.dto.auth.LibraryDto;
import com.example.test.entity.Library;
import com.example.test.exception.BadRequest;
import com.example.test.repository.LibraryRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class LibraryService {
    private final LibraryRepo libraryRepo;

    private final UserService userService;

    private final MovieService movieService;

    public LibraryService(LibraryRepo libraryRepo, UserService userService, MovieService movieService) {
        this.libraryRepo = libraryRepo;
        this.userService = userService;
        this.movieService = movieService;
    }

    public LibraryDto watch(Integer movieId, Integer userId) {
        movieService.getEntity(movieId);
        userService.getEntity(userId);
        Library library = new Library();
        library.setMovieId(movieId);
        library.setUserId(userId);
        library.setCreatedAt(LocalDateTime.now());
        libraryRepo.save(library);
        return convertToDto(library, new LibraryDto());
    }

    public LibraryDto convertToDto(Library library, LibraryDto dto){
        dto.setUserDto(userService.get(library.getUserId()));
        dto.setMovieDto(movieService.get(library.getMovieId()));
        return dto;
    }

    public LibraryDto getLibraryOfUser(Integer userId) {
        Library library = libraryRepo.findById(userId).orElseThrow(() -> new BadRequest("library not found"));
        return convertToDto(library,new LibraryDto());
    }

    public LibraryDto getLibraryOfUserForAdmin(Integer userId) {
        Library library = libraryRepo.findById(userId).orElseThrow(() -> new BadRequest("Library not found"));
        return convertToDto(library,new LibraryDto());
    }
}
