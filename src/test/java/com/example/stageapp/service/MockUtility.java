package com.example.stageapp.service;

import com.example.stageapp.dto.JsonParametersDTO;
import com.example.stageapp.dto.VideoDTO;
import com.example.stageapp.pojo.Movie;
import com.example.stageapp.pojo.Serie;
import com.example.stageapp.pojo.Video;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MockUtility {

	public static List<VideoDTO> returnVideoDtoList() {

		List<VideoDTO> videoList = new ArrayList<>();
		videoList.add(
				new VideoDTO("The Matrix", "1999", "R", "31 Mar 1999"));
		videoList.add(
				new VideoDTO("The Dark Knight", "2008", "PG-13", "18 Jul 2008"));
		return videoList;
	}

	public static List<Movie> returnMovieList() {

		List<Movie> videoList = new ArrayList<>();
		List<String> genre1 = new ArrayList<>();
		genre1.add("Fantasy");
		List<String> genre2 = new ArrayList<>();
		genre1.add("Marvel");
		videoList.add(
				new Movie(
						"1","The Matrix", "1999",
						"R", "31 Mar 1999",
						genre1, "130 min"));
		videoList.add(
				new Movie(
						"2","Avengers", "2008",
						"PG-13", "18 Jul 2008",
						genre2,"150 min"));
		return videoList;
	}
	public static List<VideoDTO> returnMovieListDTO(){
		return Video.convertVideoListToVideoDTOList(returnMovieList());
	}

	public static List<Movie> returnTheMatrix(){
		return returnMovieList().stream().filter( movie -> movie.getTitle()=="The Matrix").collect(Collectors.toList());
	}

	public static List<Movie> returnAvengersDTO(){
		return returnMovieList().stream().filter( movie -> movie.getTitle()=="Avengers").collect(Collectors.toList());
	}

	public static List<VideoDTO> returnTheMatrixDTO(){
		return Video.convertVideoListToVideoDTOList(returnMovieList().stream().filter( movie -> movie.getTitle()=="The Matrix").collect(Collectors.toList()));
	}

	public static List<VideoDTO> returnAvengers(){
		return Video.convertVideoListToVideoDTOList(returnMovieList().stream().filter( movie -> movie.getTitle()=="Avengers").collect(Collectors.toList()));
	}

	public static List<Serie> returnSerieList() {

		List<Serie> videoList = new ArrayList<>();
		List<String> genre1 = new ArrayList<>();
		genre1.add("Fantasy");
		List<String> genre2 = new ArrayList<>();
		genre1.add("Marvel");
		videoList.add(
				new Serie(
						"1","The Sandman", "1999",
						"R", "31 Jun 1999",
						genre1,3));
		videoList.add(
				new Serie(
						"2","Loki", "2008",
						"PG-13", "18 Jul 2008",
						genre2,4));
		return videoList;
	}

	public static List<VideoDTO> returnSerieListDTO(){
		return Video.convertVideoListToVideoDTOList(returnSerieList());
	}

	public static List<Serie> returnTheSandman(){
		return returnSerieList().stream().filter( serie -> serie.getTitle()=="The Sandman").collect(Collectors.toList());
	}

	public static List<VideoDTO> returnTheSandmanDTO(){
		return Video.convertVideoListToVideoDTOList(returnTheSandman());
	}

	public static List<Serie> returnLoki(){
		List<Serie> video = returnSerieList().stream().filter( serie -> serie.getTitle()=="Loki").collect(Collectors.toList());
		System.out.println(video);
		return video;
	}

	public static List<VideoDTO> returnLokiDTO(){
		return Video.convertVideoListToVideoDTOList(returnLoki());
	}





	public static Query getSerieListWithSeasonsGreaterThanQuery() {
		return new BasicQuery(
				"{'Seasons': { $gt: " + returnJsonParameters().getSeasons() +" }}"
		);
	}

	public static JsonParametersDTO returnJsonParameters() {
		return new JsonParametersDTO(
				null, null, "0", "15", null, null, 3);
	}

	public static JsonParametersDTO returnJsonParametersWithChars() {
		return new JsonParametersDTO(
				null, null, "a", "15", null, null, 3);
	}

	public static JsonParametersDTO returnJsonParametersOutOfBoundaries() {
		return new JsonParametersDTO(
				null, null, "-1", "15", null, null, 3);
	}

	public static Serie getOneSerie(){
		List<String> genreForSerie = new ArrayList<>();
		genreForSerie.add("Drama");
		return new Serie("1","You","1999", "PG-13","18 Dec 2009", genreForSerie, 9);
	}

	public static Movie getOneMovie(){
		List<String> genreForSerie = new ArrayList<>();
		genreForSerie.add("Drama");
		return new Movie("1","Joker","1999", "PG-13","18 Dec 2009", genreForSerie, "162 min");
	}

}
