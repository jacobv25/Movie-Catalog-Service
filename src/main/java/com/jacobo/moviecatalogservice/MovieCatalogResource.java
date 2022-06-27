package com.jacobo.moviecatalogservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
        //get all rated movie IDs
        UserRating ratings = restTemplate.getForObject("http://localhost:8083/ratingsdata/users/" + userId, UserRating.class);

        return ratings.getUserRating().stream().map(rating -> {

            //For each movie ID, call movie info service and get details
            //Using RestTemplate
            Movie movie = restTemplate.getForObject("http://localhost:8081/movies/" + rating.getMovieId(), Movie.class);

            //Using WebClient and Reactive, Asynchronous Programming
            /*
            Movie movie = webClientBuilder.build()// using a Builder pattern and giving you a client
                    //the type of request/method
                    .get()
                    //the url you need to access.
                    // Where do you need the request to be made?
                    .uri("http://localhost:8081/movies/" + rating.getMovieId())
                    //now that i have given you the URL, retrieve the data
                    .retrieve()
                    //whatever body you get back, convert to instance of Movie class
                    //What is mono? Reactive programming way of saying, a promise that this thing
                    //is going to get you what you want, but not now, later. An asynchronous object.
                    .bodyToMono(Movie.class)
                    .block();//block execution until mono container is filled
            */

            //Put them all together
            return new CatalogItem(movie.getName(),
                    "Hard coded description", rating.getRating());
        }).collect(Collectors.toList());
    }
}
