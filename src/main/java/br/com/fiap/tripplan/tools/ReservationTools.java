package br.com.fiap.tripplan.tools;

import br.com.fiap.tripplan.model.Reservation;
import br.com.fiap.tripplan.repository.ReservationRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

public class ReservationTools {

    private final ReservationRepository reservationRepository;

    public ReservationTools(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Tool(name = "make_reservation", description = "Faz uma reserva em um restaurante")
    public Reservation makeReservation(@ToolParam(description = "Os dados da reserva") Reservation reservation) {
        reservation.setId(null);
        return reservationRepository.save(reservation);
    }

    @Tool(name = "cancel_reservation", description = "Cancela uma reserva existente")
    public void cancelReservation(@ToolParam(description = "O ID da reserva") Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }

}
