package com.mundia.msreservation.mappers;

import com.mundia.msreservation.dto.ReservationDTO;
import com.mundia.msreservation.dto.ReservationReq;
import com.mundia.msreservation.dto.SalleDTO;
import com.mundia.msreservation.dto.UtilisateurDTO;
import com.mundia.msreservation.entities.Reservation;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReservationMapper {

    public Reservation fromReservationReq(ReservationReq reservationReq){
        Reservation reservation = new Reservation();
        BeanUtils.copyProperties(reservationReq, reservation);
        return reservation;
    }


    // Convertir un ReservationReq en entité Reservation
    public static Reservation toEntity(ReservationReq reservationReq) {
        if (reservationReq == null) {
            return null;
        }
        Reservation reservation = new Reservation();
        reservation.setDate(reservationReq.getDate());
        reservation.setHeure(reservationReq.getHeure());
        reservation.setSallesIds(reservationReq.getSallesIds());
        reservation.setUtilisateurId(reservationReq.getUtilisateurId());
        return reservation;
    }

    // Convertir une entité Reservation en ReservationReq (utilisé pour la mise à jour)
    public static ReservationReq toReq(Reservation reservation) {
        if (reservation == null) {
            return null;
        }
        return new ReservationReq(
                reservation.getDate(),
                reservation.getHeure(),
                reservation.getSallesIds(),
                reservation.getUtilisateurId()
        );
    }
    public ReservationDTO ToDTO(Reservation reservation) {
        return ReservationDTO.builder()
                .id(reservation.getId())
                .date(reservation.getDate())
                .heure(reservation.getHeure())
                // Ajoutez des mappers si vous avez des objets de type SalleDTO et UtilisateurDTO
                // .salles(salleService.getSalleById(reservation.getSallesIds()))
                // .utilisateurs(utilisateurService.getUtilisateurById(reservation.getUtilisateurId()))
                .build();
    }
}

