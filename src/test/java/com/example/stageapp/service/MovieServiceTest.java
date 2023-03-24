package com.example.stageapp.service;

import com.example.stageapp.repository.MovieRepository;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.data.mongodb.core.MongoTemplate;


@RunWith(MockitoJUnitRunner.class)
public class MovieServiceTest {

	@Mock
	MovieRepository movieRepository;
	@Mock
	MongoTemplate mongoTemplate;

	MovieService serviceUnderTest;

	@Before
	public void setUp() {

		MockitoAnnotations.openMocks(this);
		serviceUnderTest=new MovieService(mongoTemplate,movieRepository);
	}



	@Test
	public void testGetRepository() {
		//jsonUtils
		Assertions.assertEquals(
				movieRepository,
				serviceUnderTest.getRepository());
	}
}