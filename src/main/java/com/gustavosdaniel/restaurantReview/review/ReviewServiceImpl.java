package com.gustavosdaniel.restaurantReview.review;

import com.gustavosdaniel.restaurantReview.photo.Photo;
import com.gustavosdaniel.restaurantReview.restaurant.Restaurant;
import com.gustavosdaniel.restaurantReview.restaurant.RestaurantNotFoundException;
import com.gustavosdaniel.restaurantReview.restaurant.RestaurantRepository;
import com.gustavosdaniel.restaurantReview.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final RestaurantRepository restaurantRepository;

    @Override
    @Transactional
    public Review createReview(
            User author, String restaurantId, ReviewCreateUpdateRequest reviewCreateUpdateRequest
    ) {
        Restaurant restaurant = getRestaurantOrThrow(restaurantId);

        boolean hasExistingReview = restaurant.getReviews()
                .stream()
                .anyMatch(review -> // Verifica se algum elemento do Stream atende à condição
                        review.getWrittenBy().getId().equals(author.getId())); // Compara o ID do autor da avaliação com o ID do autor fornecido

        if(hasExistingReview) {
            throw new ReviewNotAllowedException("User has already reviewed restaurant");
        }

        LocalDateTime now = LocalDateTime.now();

        List<Photo> photos = reviewCreateUpdateRequest.getPhotosIds().stream().map(url -> {

            return Photo.builder()
                    .url(url)
                    .uploadDate(now)
                    .build();

        }).toList();

        String reviewId = UUID.randomUUID().toString();

        Review reviewToCreate = Review.builder()
                .id(reviewId)
                .comment(reviewCreateUpdateRequest.getContent())
                .rating(reviewCreateUpdateRequest.getRating())
                .photos(photos)
                .datePosted(now)
                .lastEdited(now)
                .writtenBy(author)
                .build();

        restaurant.getReviews().add(reviewToCreate);

        updateRestaurantAverageRating(restaurant);

        Restaurant restaurantSaved = restaurantRepository.save(restaurant);

        return restaurantSaved.getReviews()
                .stream()
                .filter(review -> reviewId.equals(review.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Error retrieving created review"));
    }

    @Override
    public Page<Review> listReviews(String restaurantId, Pageable pageable) {

        Restaurant restaurant = getRestaurantOrThrow(restaurantId);
        List<Review> reviews = restaurant.getReviews();

        Sort sort = pageable.getSort();

        if(sort.isSorted()) {

            Sort.Order order = sort.iterator().next();
            String property = order.getProperty();

            boolean isAscending = order.getDirection().isAscending();

            Comparator<Review> reviewComparator = switch (property) {
                case "datePosted" -> Comparator.comparing(Review::getDatePosted); // Ordenar por data de postagem
                case "rating" -> Comparator.comparing(Review::getRating); // ordena pelo numero de estrelas
                default -> Comparator.comparing(Review::getRating);
            };

            reviews.sort(isAscending ? reviewComparator : reviewComparator.reversed());

        } else {
                reviews.sort(Comparator.comparing(Review::getDatePosted).reversed());
            }

            int start = (int) pageable.getOffset();

            if (start >= reviews.size()) {
                return new PageImpl<>(Collections.emptyList(), pageable, reviews.size());
            }

            int end = Math.min(start + pageable.getPageSize(), reviews.size());

            return new PageImpl<>(reviews.subList(start, end), pageable, reviews.size());

    }

    private Restaurant getRestaurantOrThrow(String restaurantId) {

        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException(
                        "Restaurant not found" + restaurantId));
    }

    private void updateRestaurantAverageRating(Restaurant restaurant) {

        List<Review> reviews = restaurant.getReviews();

        if(reviews.isEmpty()) {
            restaurant.setAverageRating(0.0f);
        }else {
            // mapToDouble stream especializado para operações com doubles
            double averageRating = reviews.stream()
                    .mapToDouble(Review::getRating) // Transforma cada objeto Review em seu valor numérico de avaliação (rating)
                    // average Calcula a média aritmética de todos os ratings
                    .average() // Retorna um OptionalDouble (que pode ou não conter um valor)
                    .orElse(0.0f);

            restaurant.setAverageRating((float) averageRating);
        }
    }
}
