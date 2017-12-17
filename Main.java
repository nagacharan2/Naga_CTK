package com.weather.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONObject;

public class Main {

	public static void main(String[] args) {

		System.out.println("Welcome to weather service");
		System.out.println("1. Find weather by city name ");
		System.out.println("2. Find weather by city id");
		System.out.println("3. Find weather by city zip code");
		System.out.println("4. Any other number to exit");
		System.out.println(" Please enter option of your interest");

		Scanner sc = new Scanner(System.in);
		int optionSelected;
		try {
			optionSelected = sc.nextInt();
		} catch (Exception e) {
			System.out.println("Invalid selection ..");
			return;
		}
		
		APIEndpointEnum selectedService;

		if (optionSelected == 1)
			selectedService = APIEndpointEnum.FIND_BY_CITY_NAME;
		else if (optionSelected == 2)
			selectedService = APIEndpointEnum.FIND_BY_CITY_ID;
		else if (optionSelected == 3)
			selectedService = APIEndpointEnum.FIND_BY_ZIP;
		else
			return;
		
		System.out.println("Please enter the Name | Id | Zip");
		String query = sc.next();
		

		executeService(selectedService, query);

	}

	private static void executeService(APIEndpointEnum selectedService,
			String query) {
		BufferedReader in = null;
		try {
			URL url = new URL(selectedService.getApiEndpoint(query));
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("GET");
			int responseCode = connection.getResponseCode();
			if (responseCode == 200) {
				in = new BufferedReader(new InputStreamReader(
						connection.getInputStream()));
			} else {
				in = new BufferedReader(new InputStreamReader(
						connection.getErrorStream()));
			}
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			printWeatherDetails(response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void printWeatherDetails(String responseString) {
		JSONObject responseJSON = new JSONObject(responseString);
		if (!responseJSON.has("main")) {
			System.out.println(responseJSON.get("message"));
			return;
		}
		System.out.println("\nWeather Details for city "+ responseJSON.get("name") + "\n");
		JSONObject weatherDetails = (JSONObject) responseJSON.get("main");
		for (String key : weatherDetails.keySet()) {
			System.out.println(key + ":" + weatherDetails.get(key));
		}
	}

}
