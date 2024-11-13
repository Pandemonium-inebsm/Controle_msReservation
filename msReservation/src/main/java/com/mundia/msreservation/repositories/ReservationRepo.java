package com.mundia.msreservation.repositories;

import com.mundia.msreservation.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepo extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllBySallesIds(Long salleIds);
    List<Reservation> findAllByUtilisateurId(Long utilisateurIds);
}
