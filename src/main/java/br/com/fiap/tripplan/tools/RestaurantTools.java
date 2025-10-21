package br.com.fiap.tripplan.tools;

import br.com.fiap.tripplan.RestaurantRepository;
import br.com.fiap.tripplan.model.Restaurant;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

public class RestaurantTools {

    private final RestaurantRepository restaurantRepository;

    public RestaurantTools(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Tool(name = "get_restaurants_by_city", description = "Busca restaurantes em uma cidade específica")
    public List<Restaurant> getRestaurantsByCity(@ToolParam(description = "A cidade de destino") String city){
        return restaurantRepository.findByCity(city);
    }

}
