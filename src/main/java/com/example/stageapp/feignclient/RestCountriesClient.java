package com.example.stageapp.feignclient;

import com.example.stageapp.pojo.CountryInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@FeignClient(name = "restcountries", url = "${countriesApi.url}")
//https://restcountries.com/v3.1/
public interface RestCountriesClient {

	@GetMapping("/name/{name}")
	List<CountryInfo> getCountryInfo(@PathVariable String name);

	@GetMapping("/all")
	List<CountryInfo> getAllCountries();

}