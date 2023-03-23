package com.example.stageapp.service;

import com.example.stageapp.dto.JsonParametersDTO;
import com.example.stageapp.pojo.Movie;
import com.example.stageapp.pojo.Serie;
import com.example.stageapp.repository.MovieRepository;
import com.example.stageapp.repository.SerieRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class VideoServiceTest {

	@Mock
	private SerieRepository serieRepository;
	@Mock
	private MovieRepository movieRepository;
	@Mock
	private MongoTemplate mongoTemplate;


	private VideoService movieServiceUnderTest;
	private VideoService serieServiceUnderTest;

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
	public void testVideoListContainsVideos() throws Exception {
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
	public void getAllVideoListTest_returnVideoList() throws Exception {
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
		Assert.assertTrue(serieServiceUnderTest.getAllVideoList(jsonParameters,Serie.class).equals(MockUtility.returnSerieListDTO()));
		Assert.assertTrue(movieServiceUnderTest.getAllVideoList(jsonParameters,Movie.class).equals(MockUtility.returnMovieListDTO()));

		assertEquals(
				expected,
				movieServiceUnderTest.getPageRequest(MockUtility.returnJsonParameters())
		);

	}

	@Test
	public void pageRequest_isValid() throws Exception {
		JsonParametersDTO jsonParameters = MockUtility.returnJsonParameters();
		PageRequest expected = PageRequest.of(0, 15);
		assertEquals(
				expected,
				movieServiceUnderTest.getPageRequest(jsonParameters)
		);
	}

	@Test(expected = NumberFormatException.class)
	public void pageRequest_containsChars() throws Exception{
		//given
		JsonParametersDTO jsonParameters = MockUtility.returnJsonParametersWithChars();
		//when

		//then
		movieServiceUnderTest.getPageRequest(jsonParameters);
		assertThat(movieServiceUnderTest.getPageRequest(jsonParameters)).isEqualTo(PageRequest.of(0,10));
		assertThrows(NumberFormatException.class,()->{},"error handled");
	}

	@Test
	public void pageRequest_outOfBoundaries() throws Exception{
		//given
		JsonParametersDTO jsonParameters = MockUtility.returnJsonParametersOutOfBoundaries();
		//when

		//then
		assertThat(	movieServiceUnderTest.getPageRequest(jsonParameters)).isEqualTo(PageRequest.of(0,10));
	}



	@Test
	public void getVideoTest() {
		//given
		String id="1";
		Query query = new BasicQuery("{'_id': { $eq: \"" + id + "\" }}");
		//when
		serieServiceUnderTest.getVideo("1", Movie.class);
		//then
		verify(mongoTemplate).findOne(query,Movie.class);
	}

	@Test
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
	@Disabled
	public void deleteVideoByIdTest() {
//		//given
//		Movie movie=MockUtility.getOneMovie();
//		Serie serie=MockUtility.getOneSerie();
//
//		String movieId=movie.getId();
//		String serieId=serie.getId();
//
//		Query movieQuery = new BasicQuery("{'_id': { $eq: \"" + movieId + "\" }}");
//		Query serieQuery = new BasicQuery("{'_id': { $eq: \"" + serieId + "\" }}");
//
//		movieServiceUnderTest.deleteVideoById(movieId,Movie.class);
//		serieServiceUnderTest.deleteVideoById(serieId,Serie.class);
//		//when
//
//		//then
//		verify(mongoTemplate).remove(movieQuery, Movie.class);
//		verify(mongoTemplate).remove(serieQuery,Serie.class);
	}

	@Test
	public void getVideoListSearchedByTitleTest() throws Exception {
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
	public void getVideoListSearchedByTitleAndGenreTest() throws Exception {
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
	public void addAllMongoDBKeysTest() {
		//given

		//when

		//then
	}
}