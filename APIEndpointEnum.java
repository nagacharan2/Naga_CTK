package com.weather.api;

public enum APIEndpointEnum {

	FIND_BY_ZIP("http://api.openweathermap.org/data/2.5/weather", "zip"), 
	FIND_BY_CITY_ID("http://api.openweathermap.org/data/2.5/weather", "id"), 
	FIND_BY_CITY_NAME("http://api.openweathermap.org/data/2.5/weather", "q");

	private String apiUrl;
	private String apiQueryParameter;
	private final String appId = "4405fa4307f9073bc5b8867b56fd9f0a";

	APIEndpointEnum(String apiUrl, String apiQueryParameter) {
		this.apiUrl = apiUrl;
		this.apiQueryParameter = apiQueryParameter;
	}

	public String getApiEndpoint(String query) {
		return new StringBuilder(apiUrl).append("?appid=").append(appId)
				.append("&").append(apiQueryParameter).append("=").append(query).toString();
	}

}
