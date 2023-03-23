package com.example.stageapp.controller;




import com.example.stageapp.dto.JsonParametersDTO;
import com.example.stageapp.dto.VideoDTO;
import com.example.stageapp.pojo.Movie;
import com.example.stageapp.service.Videoable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController //Controller + ResponseBody
@RequestMapping("/api/movies")
@CrossOrigin("*")
public class MovieController {

	@Qualifier("movieService")
	@Autowired
	private Videoable<Movie> service;

	@PostMapping(value ="", consumes = APPLICATION_JSON_VALUE)
	public List<VideoDTO> findMovies(@RequestBody JsonParametersDTO jsonParameters) throws Exception {
		return service.getAllVideoList(jsonParameters, Movie.class);
	}

	@PostMapping("/content")
	public VideoDTO findMovieById(@RequestBody String id){
		return service.getVideo(id, Movie.class);
	}

	@PostMapping("/create")
	public void saveMovie(@RequestBody Movie movie){
		service.saveVideo(movie);
	}

	@PostMapping("/delete")
	public void deleteById(@RequestBody String id) throws Exception {
		service.deleteVideoById(id, Movie.class);
	}

	@PostMapping(value ="/search/title", consumes = APPLICATION_JSON_VALUE)
	public List<VideoDTO> searchMoviesByTitle(@RequestBody JsonParametersDTO jsonParameters) throws Exception {
		return service.getVideoListSearchedByTitle(jsonParameters, Movie.class);
	}

	@PostMapping(value = "/search/titleandgenre", consumes = APPLICATION_JSON_VALUE)
	public List<VideoDTO> searchMoviesByTitleAndGenre(@RequestBody JsonParametersDTO jsonParameters) throws Exception {
		return service.getVideoListSearchedByTitleAndGenre(jsonParameters, Movie.class);
	}



}
