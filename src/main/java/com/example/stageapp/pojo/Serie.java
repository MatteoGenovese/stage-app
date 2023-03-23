package com.example.stageapp.pojo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document("serie")
@Setter
@Getter
public class Serie extends Video{
	@Field("Seasons")
	@NotNull
	private Integer seasons;

	public Serie() {
	}

	public Serie(String id, String title, String year, String rated, String released, List<String> genreList, Integer seasons) {
		super(id, title, year, rated, released, genreList);
		this.seasons = seasons;
	}




}
