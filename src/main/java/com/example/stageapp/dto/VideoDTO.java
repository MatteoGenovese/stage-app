package com.example.stageapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

//
@Getter
@Setter
@AllArgsConstructor
public class VideoDTO {
	private String title;

	private String year;

	private String rated;

	private String released;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof VideoDTO videoDTO)) return false;
		return Objects.equals(title, videoDTO.title)  && Objects.equals(released, videoDTO.released);
	}

	@Override
	public int hashCode() {
		return Objects.hash(title, year, rated, released);
	}
}
