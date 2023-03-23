package com.example.stageapp.service;

import com.example.stageapp.dto.JsonParametersDTO;
import com.example.stageapp.dto.VideoDTO;
import com.example.stageapp.pojo.Movie;
import com.example.stageapp.pojo.Serie;
import com.example.stageapp.pojo.Video;
import com.example.stageapp.repository.MovieRepository;
import com.example.stageapp.repository.SerieRepository;

import com.mongodb.client.result.DeleteResult;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class VideoServiceTest {

	@Mock
	SerieRepository serieRepository;
	@Mock
	MovieRepository movieRepository;
	@Mock
	MongoTemplate mongoTemplate;


	VideoService<Movie> movieServiceUnderTest;
	VideoService<Serie> serieServiceUnderTest;

	@Before
	public void setUp(){

		MockitoAnnotations.openMocks(this);

		serieServiceUnderTest = new SerieService(
				mongoTemplate,
				serieRepository
		);

		movieServiceUnderTest = new MovieService(
				mongoTemplate,
				movieRepository
		);
	}

	@Test
	@SneakyThrows
	public void testVideoListContainsVideos(){
		//given
		JsonParametersDTO jsonParameters = MockUtility.returnJsonParameters();
		PageRequest pageRequest=movieServiceUnderTest.getPageRequest(jsonParameters);
		PageRequest expected = PageRequest.of(0, 15);
		movieServiceUnderTest.videoListContainsVideos(new Query(),pageRequest,Movie.class);
		serieServiceUnderTest.videoListContainsVideos(new Query(),pageRequest,Serie.class);
		//when
		when(mongoTemplate.find(
				any(),
				eq(Serie.class))
		).thenReturn(MockUtility.returnSerieList());
		when(mongoTemplate.find(
				any(),
				eq(Movie.class))
		).thenReturn(MockUtility.returnMovieList());

		//then
		assertThat(serieServiceUnderTest.videoListContainsVideos(new Query(),pageRequest,Serie.class)).isEqualTo(MockUtility.returnSerieListDTO());
		assertThat(movieServiceUnderTest.videoListContainsVideos(new Query(),pageRequest,Movie.class)).isEqualTo((MockUtility.returnMovieListDTO()));

	}

	@Test
	@SneakyThrows
	public void getAllVideoListTest_returnVideoList() {
		//given
		Query query = new BasicQuery("{}");
		JsonParametersDTO jsonParameters = MockUtility.returnJsonParameters();
		PageRequest expected = PageRequest.of(0, 15);

		//when
		when(mongoTemplate.find(
				any(),
				eq(Serie.class))
		).thenReturn(MockUtility.returnSerieList());
		when(mongoTemplate.find(
				any(),
				eq(Movie.class))
		).thenReturn(MockUtility.returnMovieList());
		movieServiceUnderTest.getAllVideoList(jsonParameters,Movie.class);
		serieServiceUnderTest.getAllVideoList(jsonParameters,Serie.class);

		//then

		assertThat(serieServiceUnderTest.getAllVideoList(jsonParameters,Serie.class).equals(MockUtility.returnSerieListDTO()));
		assertThat(movieServiceUnderTest.getAllVideoList(jsonParameters,Movie.class).equals(MockUtility.returnMovieListDTO()));

		assertEquals(
				expected,
				movieServiceUnderTest.getPageRequest(MockUtility.returnJsonParameters())
		);

	}

	@Test
	@SneakyThrows
	public void pageRequest_isValid() {
		JsonParametersDTO jsonParameters = MockUtility.returnJsonParameters();
		PageRequest expected = PageRequest.of(0, 15);
		assertEquals(
				expected,
				movieServiceUnderTest.getPageRequest(jsonParameters)
		);
	}

	@Test
	@SneakyThrows
	public void pageRequest_containsChars() throws Exception{
		//given
		JsonParametersDTO jsonParametersWithChars = MockUtility.returnJsonParametersWithChars();
		JsonParametersDTO jsonParameters = MockUtility.returnJsonParameters();
		//when

		//then
		Exception thrown = assertThrows(NumberFormatException.class, ()->  movieServiceUnderTest.getPageRequest(jsonParametersWithChars) ,"Expected PageRequest.of(0,10) to throw, but it didn' t");
		assertThat(thrown.getMessage().contentEquals(NumberFormatException.class.toString()));
	}

	@Test
	@SneakyThrows
	public void pageRequest_outOfBoundaries() throws Exception{
		//given
		JsonParametersDTO jsonParameters = MockUtility.returnJsonParametersOutOfBoundaries();
		//when

		//then
		assertThat(	movieServiceUnderTest.getPageRequest(jsonParameters)).isEqualTo(PageRequest.of(0,10));
	}



	@Test
	@SneakyThrows
	public void getVideoTest() {
		//given
		JsonParametersDTO jsonParameters = MockUtility.returnJsonParameters();
		PageRequest pageRequest = movieServiceUnderTest.getPageRequest(jsonParameters);
		Query query = new BasicQuery("{'_id': { $eq: \"" + jsonParameters.getId() + "\" }}");
		//when
		when(mongoTemplate.findOne(any(),eq(Movie.class))).thenReturn(
				Video.convertVideoListToVideo(
					MockUtility.returnMovieList().stream().filter(
							video->video.getId()==jsonParameters.getId()
					).toList()
				)
		);
		//then
		Assertions.assertEquals(
				Video.convertVideoToVideoDTO(Video.convertVideoListToVideo(
						MockUtility.returnMovieList().stream().filter(
								video->video.getId()==jsonParameters.getId()).toList()
				)),
				movieServiceUnderTest.getVideo(jsonParameters.getId(),Movie.class)
		);
	}

	@Test
	@SneakyThrows
	public void saveVideoTest() {
		//given
		Serie serie=MockUtility.getOneSerie();
		serieServiceUnderTest.saveVideo(serie);
		Movie movie=MockUtility.getOneMovie();
		movieServiceUnderTest.saveVideo(movie);
		//then
		verify(serieRepository).save(serie);
		verify(movieRepository).save(movie);
	}

	@Test
	@SneakyThrows
	@Disabled
	public void deleteVideoByIdTest() {
		//given
		Movie movie=MockUtility.getOneMovie();
		JsonParametersDTO jsonParameters = MockUtility.returnJsonParameters();
		String movieId=movie.getId();
		Query movieQuery = new BasicQuery("{'_id': { $eq: \"" + jsonParameters.getId() + "\" }}");



		movieServiceUnderTest.deleteVideoById(movieId,Movie.class);
		//when

		when(mongoTemplate.remove(any(),Movie.class)).thenReturn(DeleteResult.acknowledged(1));

		//then
		verify(mongoTemplate).remove(movieQuery, Movie.class);
	}

	@Test
	@SneakyThrows
	public void getVideoListSearchedByTitleTest() {
		//given
		JsonParametersDTO jsonParameters = MockUtility.returnJsonParameters();
		PageRequest pageRequest = movieServiceUnderTest.getPageRequest(jsonParameters);
		Query query = new BasicQuery(
				"{'Title': { '$regex': '.*" + jsonParameters.getTitle() + ".*' , '$options': 'i'}}"
		);

		//when
		when(mongoTemplate.find(
				any(),
				eq(Movie.class))
		).thenReturn(MockUtility.returnMovieList());


		//then
		assertThat(movieServiceUnderTest.getPageRequest(jsonParameters)).isEqualTo(PageRequest.of(0,15));
		assertThat(movieServiceUnderTest.getVideoListSearchedByTitle(jsonParameters,Movie.class)).isEqualTo(MockUtility.returnMovieListDTO());

	}

	@Test
	@SneakyThrows
	public void getVideoListSearchedByTitleAndGenreTest() {
		//given
		JsonParametersDTO jsonParameters = MockUtility.returnJsonParameters();
		PageRequest pageRequest = movieServiceUnderTest.getPageRequest(jsonParameters);
		Query query = new BasicQuery("{ " +
				"'Title': { '$regex': '.*" + jsonParameters.getTitle() + ".*' , '$options': 'i'}, " +
				"'Genre': { '$regex': '.*" + jsonParameters.getGenre() + ".*' , '$options': 'i'}}" +
				"");

		//when
		when(mongoTemplate.find(
				any(),
				eq(Movie.class))
		).thenReturn(MockUtility.returnMovieList());


		//then
		assertThat(movieServiceUnderTest.getPageRequest(jsonParameters)).isEqualTo(PageRequest.of(0,15));
		assertThat(movieServiceUnderTest.getVideoListSearchedByTitleAndGenre(jsonParameters,Movie.class)).isEqualTo(MockUtility.returnMovieListDTO());

	}





	@Test
	@SneakyThrows
	public void addAllMongoDBKeysTest() {
		//given

		//when

		//then
	}
}