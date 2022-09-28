package com.example.test.controller;

import com.example.test.dto.auth.LibraryDto;
import com.example.test.service.LibraryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/library")
public class LibraryController {

    private final LibraryService libraryService;

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }


    @GetMapping("/watch")
    public ResponseEntity<?> watch(@RequestParam("movie") Integer movieId,
                                   @RequestParam("user") Integer userId){
        LibraryDto libraryDto = libraryService.watch(movieId, userId);
        return ResponseEntity.ok(libraryDto);
    }

    @GetMapping("/library/{id}")
    public ResponseEntity<?> library(@PathVariable("id") Integer userId){
        LibraryDto libraryDto = libraryService.getLibraryOfUser(userId);
        return ResponseEntity.ok(libraryDto);
    }

    @GetMapping("/secured/user/{id}")
    public ResponseEntity<?> libraryUser(@PathVariable("id") Integer userId){
        LibraryDto result = libraryService.getLibraryOfUserForAdmin(userId);
        return ResponseEntity.ok(result);
    }

    // TODO: GEt Watching count By Movie ID

    //   @GetMapping("/watchcount/{}")


    // TODO: Remove my History
}
