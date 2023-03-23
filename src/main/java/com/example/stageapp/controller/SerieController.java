package com.example.stageapp.controller;

import com.example.stageapp.dto.JsonParametersDTO;
import com.example.stageapp.dto.VideoDTO;
import com.example.stageapp.pojo.Movie;
import com.example.stageapp.pojo.Serie;
import com.example.stageapp.service.SerieService;
import com.example.stageapp.service.Videoable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/series")
@CrossOrigin("*")
public class SerieController {

	@Autowired
	private SerieService serieService;

	@Qualifier("serieService")
	@Autowired
	private Videoable service;

	@PostMapping
	public List<VideoDTO> findSerieList(@RequestBody JsonParametersDTO jsonParameters) throws Exception {
//		return serieService.returnAllTheVideoList();
		return service.getAllVideoList(jsonParameters, Serie.class);
	}

	@PostMapping("/content")
	public VideoDTO findSerieById(@RequestBody String id){
		return service.getVideo(id, Serie.class);
	}

	@PostMapping("/create")
	public void saveSerie(@RequestBody Serie serie){
		service.saveVideo(serie);
	}

	@PostMapping("/delete")
	public void deleteById(@RequestBody String id){
		service.deleteVideoById(id, Serie.class);
	}

	@PostMapping(value ="/search/title", consumes = APPLICATION_JSON_VALUE)
	public List<VideoDTO> searchSeriesByTitle(@RequestBody JsonParametersDTO jsonParameters) throws Exception {
		return service.getVideoListSearchedByTitle(jsonParameters, Serie.class);
	}

	@PostMapping(value = "/search/titleandgenre", consumes = APPLICATION_JSON_VALUE)
	public List<VideoDTO> searchSeriesByTitleAndGenre(@RequestBody JsonParametersDTO jsonParameters) throws Exception {
		return service.getVideoListSearchedByTitleAndGenre(jsonParameters, Serie.class);
	}

	@PostMapping(value = "/search/seasons", consumes = APPLICATION_JSON_VALUE)
	public List<VideoDTO> getSerieListWithSeasonsGreaterThan(@RequestBody JsonParametersDTO jsonParameters) throws Exception {
		return serieService.getSerieListWithSeasonsGreaterThan(jsonParameters);
	}
}
