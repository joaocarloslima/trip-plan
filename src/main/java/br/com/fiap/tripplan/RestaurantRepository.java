package br.com.fiap.tripplan;

import br.com.fiap.tripplan.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findByCity(String city);
}
