package com.mb.springbootapisecurityjwt.controller;

import com.mb.springbootapisecurityjwt.model.Movie;
import com.mb.springbootapisecurityjwt.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public ResponseEntity<?> getMovies() {
        List<Movie> movies = movieService.getMovies();
        return ResponseEntity.ok(movies);
    }
}
