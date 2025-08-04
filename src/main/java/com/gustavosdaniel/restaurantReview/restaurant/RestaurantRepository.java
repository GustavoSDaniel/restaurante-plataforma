package com.gustavosdaniel.restaurantReview.restaurant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends ElasticsearchRepository<Restaurant, String> {

    Page<Restaurant> findByAverageRatingGreaterThanEqual(Float minRating, Pageable pageable);

    @Query("SELECT r FROM Restaurant r WHERE " +
            "(:query IS NULL OR LOWER(r.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(r.description) LIKE LOWER(CONCAT('%', :query, '%'))) AND " +
            "(:minRating IS NULL OR r.rating >= :minRating)")
    Page<Restaurant> findByQueryAndMinRating(
            @Param("query") String query,
            @Param("minRating") Float minRating,
            Pageable pageable
    );


    @Query("""
        {
          "bool": {
            "filter": [
              {
                "geo_distance": {
                  "distance": "?#{#radiusKm}km",
                  "location": {
                    "lat": ?#{#latitude},
                    "lon": ?#{#longitude}
                  }
                }
              }
            ]
          },
          "sort": [
            {
              "_geo_distance": {
                "location": {
                  "lat": ?#{#latitude},
                  "lon": ?#{#longitude}
                },
                "order": "asc",
                "unit": "km"
              }
            }
          ]
        }
        """)
    Page<Restaurant> findByLocationNear(
            @Param("latitude") Float latitude,
            @Param("longitude") Float longitude,
            @Param("radiusKm") Float radiusKm,
            Pageable pageable
    );
}

