package com.mb.springbootapisecurityjwt.service;

import com.mb.springbootapisecurityjwt.model.Movie;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class MovieService {

    public List<Movie> getMovies() {
        return Arrays.asList(new Movie(1L, "Lagaan"),
                new Movie(2L, "Banva Banvi"));
    }
}
