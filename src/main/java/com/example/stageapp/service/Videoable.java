package com.example.stageapp.service;
import com.example.stageapp.dto.JsonParametersDTO;
import com.example.stageapp.dto.VideoDTO;
import com.example.stageapp.pojo.Movie;
import com.example.stageapp.pojo.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

public interface Videoable<E extends Video> {

	//<E> è la significance, cioè il datatype che verrà trasmesso da chi chiamerà il metodo
	public  void addAllMongoDBKeys();

	public List<VideoDTO> videoListContainsVideos(Query query,
												  PageRequest pageRequest,
												  Class<E> classType);
	public PageRequest getPageRequest(JsonParametersDTO jsonParameters) throws Exception;
	public List<VideoDTO> getAllVideoList(JsonParametersDTO jsonParameters, Class<E> classType) throws Exception;
	public VideoDTO getVideo(String id, Class<E> classType);
	public void saveVideo(E video);
	public void deleteVideoById(String id, Class<E> classType);
	public List<VideoDTO> getVideoListSearchedByTitle(JsonParametersDTO movieParameters, Class<E> classType) throws Exception;
	public List<VideoDTO> getVideoListSearchedByTitleAndGenre(JsonParametersDTO movieParameters, Class<E> classType) throws Exception;

}
