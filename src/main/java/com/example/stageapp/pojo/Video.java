package com.example.stageapp.pojo;

import com.example.stageapp.dto.VideoDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Setter
@Getter
public abstract class Video {
	@Id
	private String id;

	@Field("Title")
	@NotNull
	private String title;

	@Field("Year")
	@NotNull
	private String year;

	@Field("Rated")
	@NotNull
	private String rated;

	@Field("Released")
	@NotNull
	private String released;

	@Field("Genre")
	@NotNull
	private List<String> genreList;

	protected Video() {
	}
	protected Video(String id, String title, String year, String rated, String released, List<String> genreList) {
		this.id = id;
		this.title = title;
		this.year = year;
		this.rated = rated;
		this.released = released;
		this.genreList = genreList;
	}

	public String getGenreString(){

		String genreToString = "";
		for (String genre: getGenreList()){
			genreToString+= genre+" ";
		}

		return genreToString;
	}

	public static List<VideoDTO> convertVideoListToVideoDTOList(List<? extends Video> videoList){
		return videoList.stream()
				.map(video -> new VideoDTO(
						video.getTitle(),
						video.getYear(),
						video.getRated(),
						video.getReleased()))
				.toList();
	}

	public static VideoDTO convertVideoToVideoDTO(Video video){
		return new VideoDTO(
				video.getTitle(),
				video.getYear(),
				video.getRated(),
				video.getReleased()
		);
	}

	public static <E extends Video> VideoDTO convertSerieOrMovieToVideoDTO(E video){
		return new VideoDTO(
				video.getTitle(),
				video.getYear(),
				video.getRated(),
				video.getReleased()
		);

	}

	public static <E extends Video> E convertVideoListToVideo(List<E> videolist){
		if (videolist.size()==1) {
			for (E video : videolist) {
				return video;
			}
		}
		return null;
	}

	@Override
	public String toString(){
		return ""+getTitle()+"" +
				"\n"+getYear()+"" +
				"\n"+getReleased()+"" +
				"\n"+getRated();
	}

}
