package com.example.stageapp.service;

import com.example.stageapp.repository.MovieRepository;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.data.mongodb.core.MongoTemplate;


@RunWith(MockitoJUnitRunner.class)
class MovieServiceTest {

	@Mock
	private MovieRepository movieRepository;
	@Mock
	private MongoTemplate mongoTemplate;

	private MovieService serviceUnderTest;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		//oppure
//		MongoTemplate mongotemplate = Mockito.mock(MongoTemplate.class);
		serviceUnderTest=new MovieService(mongoTemplate,movieRepository);
	}

	@Test
	void CanGetRepository() {
		//jsonUtils
		Assertions.assertEquals(movieRepository,serviceUnderTest.getRepository());
//		assertThat(serviceUnderTest.getRepository()).isEqualTo(movieRepository);
	}
}