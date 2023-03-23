package com.example.stageapp.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class CountryInfo implements Serializable {
	private Map<String, Object> name;
	private List<String> tld;
	private String cca2;
	private String ccn3;
	private String cca3;
	private String cioc;
	private boolean independent;
	private String status;
	private boolean unMember;
	private Map<String, Object> currencies;
	private Map<String, Object> idd;
	private List<String> capital;
	private List<String> altSpellings;
	private String region;
	private String subregion;
	private Map<String, Object> languages;
	private Map<String, Map<String, String>> translations;
}