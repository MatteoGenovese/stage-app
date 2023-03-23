package com.example.stageapp.service;

import com.example.stageapp.dto.JsonParametersDTO;
import com.example.stageapp.dto.VideoDTO;
import com.example.stageapp.pojo.Video;
import com.example.stageapp.repository.VideoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//restringo i limiti del mio generic alla superclasse Video, se volessi restringere i limiti sia
//ad una classe e sia ad una interfaccia metterei <E extends classe & interfaccia>, l'ordine è importante
//non posso quindi fare <E extends interfaccia & classe>

@Slf4j
@Service
@AllArgsConstructor
public abstract class VideoService<E extends Video> implements Videoable<E> {


	protected MongoTemplate mongoTemplate;

	public abstract VideoRepository<E, String> getRepository();

	public List<VideoDTO> videoListContainsVideos(Query query,
												  PageRequest pageRequest,
												  Class<E> classType) {

		List<VideoDTO> videoList = Video.
				convertVideoListToVideoDTOList(
						mongoTemplate.find(
								query.with(pageRequest),
								classType));

		if (videoList.isEmpty()) {
			log.info("page doesn't contain movies");
			return new ArrayList<>();
		} else {
			log.info("page contains movies");
			return Video.convertVideoListToVideoDTOList(
					mongoTemplate.find(
							query.with(pageRequest), classType
					)
			);
		}

	}

	//<E> è la significance, cioè il datatype che verrà trasmesso da chi chiamerà il metodo
	public List<VideoDTO> getAllVideoList(JsonParametersDTO jsonParameters, Class<E> classType) throws Exception {

		Query query = new BasicQuery("{}");
		PageRequest pageRequest = getPageRequest(jsonParameters);
		return videoListContainsVideos(query, pageRequest, classType);
	}

	public PageRequest getPageRequest(JsonParametersDTO jsonParameters) throws Exception{
		if (accettableValuesOfJsonParameters(jsonParameters)){
			try {
				return PageRequest.of(
						Integer.parseInt(jsonParameters.getPage()),
						Integer.parseInt(jsonParameters.getNumberOfElements())
				);

			}catch (Exception e)
			{
				return PageRequest.of(0,10);
			}
		}
		return PageRequest.of(0,10);
	}
	public static boolean accettableValuesOfJsonParameters(JsonParametersDTO jsonParameters){
		if(Integer.parseInt(jsonParameters.getPage())>=0 &&
				Integer.parseInt(jsonParameters.getPage())<Integer.MAX_VALUE &&
				Integer.parseInt(jsonParameters.getNumberOfElements())>0 &&
				Integer.parseInt(jsonParameters.getNumberOfElements())<Integer.MAX_VALUE){
			return true;
		}
			return false;
	}

	public VideoDTO getVideo(String id, Class<E> classType) {
		Query query = new BasicQuery("{'_id': { $eq: \"" + id + "\" }}");
		//response test e integration test
		//bulk operations
		//annotation query
		try {
			log.info("video found");
			return Video.convertVideoToVideoDTO(mongoTemplate.findOne(query, classType));
		} catch (Exception e) {
			log.info("no video found");
			return null;
		}
	}

	public void saveVideo(E video) {

		video.setId(new ObjectId().toString());
		getRepository().save(video);
		log.info("video saved in db");
	}

	public void deleteVideoById(String id, Class<E> classType) throws Exception {

		Query query = new BasicQuery("{'_id': { $eq: \"" + id + "\" }}");

		if (getRepository().findById(id).isPresent()) {
			mongoTemplate.remove(query, classType);
			log.info("movie found and deleted");
		} else {
			log.info("no movie found");
			throw new Exception("Not found");
		}
	}

	public List<VideoDTO> getVideoListSearchedByTitle(JsonParametersDTO jsonParameters, Class<E> classType) throws Exception {

		Query query = new BasicQuery(
				"{'Title': { '$regex': '.*" + jsonParameters.getTitle() + ".*' , '$options': 'i'}}"
		);
		PageRequest pageRequest = getPageRequest(jsonParameters);
		return videoListContainsVideos(query, pageRequest, classType);

	}

	public List<VideoDTO> getVideoListSearchedByTitleAndGenre(JsonParametersDTO jsonParameters, Class<E> classType) throws Exception {

		Query query = new BasicQuery("{ " +
				"'Title': { '$regex': '.*" + jsonParameters.getTitle() + ".*' , '$options': 'i'}, " +
				"'Genre': { '$regex': '.*" + jsonParameters.getGenre() + ".*' , '$options': 'i'}}" +
				"");
		PageRequest pageRequest = getPageRequest(jsonParameters);

		return videoListContainsVideos(query, pageRequest, classType);

//		repository di solito page, service quello che si desidera

	}



}
