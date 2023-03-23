package com.example.stageapp.service;


import com.example.stageapp.pojo.Movie;


import com.example.stageapp.repository.MovieRepository;
import com.example.stageapp.repository.SerieRepository;
import com.example.stageapp.repository.VideoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;



@Service
@Slf4j
public class MovieService extends VideoService<Movie> {

	private final VideoRepository repository;

	public MovieService(@Autowired MongoTemplate mongoTemplate,
						@Autowired MovieRepository movieRepository) {
		super(mongoTemplate);
		this.repository = movieRepository;
	}

	@Override
	public VideoRepository getRepository() {
		return repository;
	}


}