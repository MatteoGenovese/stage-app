package com.example.stageapp.service;

import com.example.stageapp.dto.JsonParametersDTO;
import com.example.stageapp.pojo.Serie;
import com.example.stageapp.repository.SerieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class SerieServiceTest  {

	@Mock
	private SerieRepository serieRepository;
	@Mock
	private MongoTemplate mongoTemplate;

	private SerieService serviceUnderTest;

	@BeforeEach
	void setUp() {

		MockitoAnnotations.openMocks(this);
		List<Serie> videoList = MockUtility.returnSerieList();
		serviceUnderTest = new SerieService(
				mongoTemplate, serieRepository);
//		try {
//			for (Serie serie : videoList){
//				serieRepository.save(serie);
//			}
//		}catch (Exception e)
//		{
//			e.printStackTrace();
//		}
	}

	@Test
	void CanGetRepository() {

		assertThat(serieRepository).isEqualTo(serviceUnderTest.getRepository());
	}

	@Test
	void getSerieListWithSeasonsGreaterThan_IsValid() throws Exception {
		JsonParametersDTO jsonParameters = MockUtility.returnJsonParameters();
		PageRequest expected = PageRequest.of(0, 15);
		when(mongoTemplate.find(
				any(),
				eq(Serie.class))
		).thenReturn(MockUtility.returnLoki());
		assertThat(serviceUnderTest.getSerieListWithSeasonsGreaterThan(jsonParameters).
				stream().map(serie -> serie.getTitle()).collect(Collectors.toList()))
				.isEqualTo(MockUtility.returnLokiDTO().
						stream().map(serie -> serie.getTitle()).collect(Collectors.toList()));
		serviceUnderTest.getSerieListWithSeasonsGreaterThan(
				jsonParameters);
		assertThat(expected).isEqualTo(serviceUnderTest.getPageRequest(MockUtility.returnJsonParameters()));
	}
}