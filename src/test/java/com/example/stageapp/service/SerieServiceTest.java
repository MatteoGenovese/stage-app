package com.example.stageapp.service;

import com.example.stageapp.dto.JsonParametersDTO;
import com.example.stageapp.dto.VideoDTO;
import com.example.stageapp.pojo.Serie;
import com.example.stageapp.repository.SerieRepository;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SerieServiceTest  {

	@Mock
	SerieRepository serieRepository;
	@Mock
	MongoTemplate mongoTemplate;

	SerieService serviceUnderTest;

	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		serviceUnderTest = new SerieService(
				mongoTemplate,
				serieRepository);
	}

	@Test
	public void testGetRepository() {
		assertThat(serieRepository)
				.isEqualTo(serviceUnderTest.getRepository());
	}

	@Test
	@SneakyThrows
	public void testGetSerieListWithSeasonsGreaterThan_IsValid() throws Exception {
		//given
		JsonParametersDTO jsonParameters = MockUtility.returnJsonParameters();
		PageRequest expectedPageRequest = PageRequest.of(0, 15);

		//when
		when(mongoTemplate.find(any(),eq(Serie.class)))
				.thenReturn(MockUtility.returnLoki());
		serviceUnderTest
				.getSerieListWithSeasonsGreaterThan(jsonParameters);

		//then
		assertThat(serviceUnderTest
				.getSerieListWithSeasonsGreaterThan(jsonParameters)
				.stream()
				.map(VideoDTO::getTitle)
				.toList())
					.isEqualTo(MockUtility.returnLokiDTO()
							.stream()
							.map(VideoDTO::getTitle)
							.toList());
		assertThat(expectedPageRequest)
				.isEqualTo(serviceUnderTest
						.getPageRequest(MockUtility.returnJsonParameters()));
	}
}