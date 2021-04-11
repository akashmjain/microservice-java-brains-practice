package com.akashmjain.moviecatalogueservice.resources;

import com.akashmjain.moviecatalogueservice.models.CatalogueItem;
import com.akashmjain.moviecatalogueservice.models.Movie;
import com.akashmjain.moviecatalogueservice.models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogueResource {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/{userId}")
    public List<CatalogueItem> getCatalogues(@PathVariable("userId") String userId) {

        List<Rating> ratings = Arrays.asList(
                new Rating("1234", 5),
                new Rating("5678", 4)
        );

        return ratings.stream().map(rating -> {
            Movie movie = restTemplate.getForObject("http://localhost:8082/movies/"+rating.getMovieId(), Movie.class);
            return new CatalogueItem(movie.getName(), "Test", rating.getRating());
        })
        .collect(Collectors.toList());
    }
}
