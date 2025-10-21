package br.com.fiap.tripplan.service;

import br.com.fiap.tripplan.RestaurantRepository;
import br.com.fiap.tripplan.model.Activity;
import br.com.fiap.tripplan.repository.ReservationRepository;
import br.com.fiap.tripplan.tools.ReservationTools;
import br.com.fiap.tripplan.tools.RestaurantTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ChatService {

    private final ChatClient chatClient;

    private final PromptTemplate generatePromptTemplate = new PromptTemplate("""
            Crie uma sugestão roteiro de viagem com 5 atividades divertidas para fazer em {destination}.
            Para cada atividade forneça o título da atividade, descrição, tipo e duração em minutos.
            Os tipos válidos são: MUSEUM, PARK, RESTAURANT, SHOPPING, HOTEL, TOUR e OTHER.
            Para sugerir restaurantes, utilize a ferramenta get_restaurants_by_city para buscar restaurantes na cidade de destino.
            Se não houver restaurantes disponíveis, sugira outros restaurantes populares na cidade.
            """);
    private final RestaurantRepository restaurantRepository;
    private final ReservationRepository reservationRepository;

    public ChatService(ChatClient.Builder builder, RestaurantRepository restaurantRepository, ReservationRepository reservationRepository) {
        this.chatClient = builder.build();
        this.restaurantRepository = restaurantRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<Activity> getTravelItinerary(String destination) {
        return chatClient
                .prompt(generatePromptTemplate.create(Map.of("destination", destination)))
                .tools(new RestaurantTools(restaurantRepository))
                .call()
                .entity(new ParameterizedTypeReference<List<Activity>>() {});
    }

    public String executeCommand(String command) {
        return chatClient.prompt()
                .system("""
                        Você é um assistente de reservas de restaurantes.
                        Quando o usuário fornecer um comando para fazer uma reserva, utilize a ferramenta make_reservation para processar a reserva.
                        Responda com uma confirmação da reserva realizada, incluindo o nome da pessoa, a data e o id da reserva.
                        Quando o usuário fornecer um comando para cancelar uma reserva, utilize a ferramenta cancel_reservation para processar o cancelamento.
                        """)
                .tools(new ReservationTools(reservationRepository))
                .user(command).call().content();

    }
}
