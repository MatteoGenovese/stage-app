package com.example.stageapp.repository;

import com.example.stageapp.pojo.Serie;
import org.springframework.stereotype.Repository;

@Repository
public interface SerieRepository extends VideoRepository<Serie, String> {
}
