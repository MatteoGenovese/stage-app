package com.example.stageapp.service;

import com.example.stageapp.dto.JsonParametersDTO;
import com.example.stageapp.dto.VideoDTO;
import com.example.stageapp.pojo.Serie;

import com.example.stageapp.repository.SerieRepository;
import com.example.stageapp.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class SerieService extends VideoService<Serie> {

	private final VideoRepository<Serie, String> repository;

	public SerieService(@Autowired MongoTemplate mongoTemplate,
						@Autowired SerieRepository serieRepository) {
		super(mongoTemplate);
		this.repository = serieRepository;
	}

	@Override
	public VideoRepository getRepository() {
		return repository;
	}



	public List<VideoDTO> getSerieListWithSeasonsGreaterThan(
			JsonParametersDTO jsonParameters) throws Exception {
		PageRequest pageRequest=getPageRequest(jsonParameters);
		Query query = new BasicQuery(
				"{'Seasons': { $gt: " + jsonParameters.getSeasons() +" }}"
		);
		return videoListContainsVideos( query , pageRequest, Serie.class);
	}


}
