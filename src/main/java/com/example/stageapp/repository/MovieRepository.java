package com.example.stageapp.repository;

import com.example.stageapp.pojo.Movie;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends VideoRepository<Movie, String> {


}
