package com.example.stageapp.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JsonParametersDTO {

	private String title;
	private String genre;
	@Min(value=0)
	private String page;
	private String numberOfElements;
	private String id;
	private String runtime;
	private Integer seasons;
}
