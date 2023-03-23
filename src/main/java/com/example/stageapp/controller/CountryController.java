package com.example.stageapp.controller;

import com.example.stageapp.feignclient.RestCountriesClient;
import com.example.stageapp.pojo.CountryInfo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/countries")
public class CountryController {


	private RestCountriesClient restCountriesClient;

	public CountryController(RestCountriesClient restCountriesClient) {

		this.restCountriesClient = restCountriesClient;
	}

	@GetMapping("/{name}")
	public List<CountryInfo> getCountryInfo(@PathVariable String name) {
		try {
			return restCountriesClient.getCountryInfo(name);
		} catch (Exception e) {
			return null;
		}
	}

	@GetMapping("/all")
	public List<CountryInfo> getAllCountries() {
		return restCountriesClient.getAllCountries();
	}
}
