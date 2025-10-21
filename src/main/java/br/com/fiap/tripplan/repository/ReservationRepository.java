package br.com.fiap.tripplan.repository;

import br.com.fiap.tripplan.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
