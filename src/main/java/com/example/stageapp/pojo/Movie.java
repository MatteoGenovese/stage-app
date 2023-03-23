package com.example.stageapp.pojo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document("movie")
@Getter
@Setter
public class Movie extends Video {

	@Field("Runtime")
	@NotNull
	private String runtime;

	public Movie() {
	}

	public Movie(String id, String title, String year, String rated, String released, List<String> genreList, String runtime) {
		super(id, title, year, rated, released, genreList);
		setRuntime(runtime);
	}
}
