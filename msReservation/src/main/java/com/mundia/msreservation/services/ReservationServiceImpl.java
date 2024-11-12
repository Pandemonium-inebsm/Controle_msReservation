package com.mundia.msreservation.services;


import com.mundia.msreservation.dto.ReservationDTO;
import com.mundia.msreservation.dto.ReservationReq;
import com.mundia.msreservation.dto.SalleDTO;
import com.mundia.msreservation.dto.UtilisateurDTO;
import com.mundia.msreservation.entities.Reservation;
import com.mundia.msreservation.mappers.ReservationMapper;
import com.mundia.msreservation.repositories.ReservationRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService{
    final ReservationRepo reservationRepo;
    final ReservationMapper reservationMapper;
    final WebClient webClient;

    @Override
    public Reservation getReservationById(Long id) {
        Optional<Reservation> optionalReservation = reservationRepo.findById(id);
        if(optionalReservation.isPresent()){
            return optionalReservation.get();
        }else {
            throw new EntityNotFoundException("Reservation with id " + id + " not found");
        }
    }

    @Override
    public List<Reservation> getAllReservation() {
        List<Reservation> reservations = reservationRepo.findAll();
        return reservations;

    }

    public Reservation addReservation(ReservationReq reservationReq) {
        // Conversion de ReservationReq en Reservation
        Reservation reservation = Reservation.builder()
                .date(reservationReq.getDate())
                .heure(reservationReq.getHeure())
                .sallesIds(reservationReq.getSallesIds())
                .utilisateurId(reservationReq.getUtilisateurId())
                .build();

        reservationRepo.save(reservation);

        return reservation;
    }


    @Override
    public ReservationDTO updateReservation(Long id, ReservationDTO reservationDTO) {
//        // Vérifier si la réservation avec l'ID donné existe
//        Reservation existingReservation = reservationRepo.findById(id)
//                .orElseThrow(() -> new RuntimeException("Réservation non trouvée avec l'ID : " + id));
//
//        // Mettre à jour les champs de la réservation existante avec les nouvelles données
//        existingReservation.setDate(reservationDTO.getDate());
//        existingReservation.setHeure(reservationDTO.getHeure());
//
//        // Si vous avez besoin de mettre à jour les IDs de salle et d'utilisateur
//        existingReservation.setSallesIds(reservationDTO.getSalles().get(0).getId());  // Exemple pour une seule salle
//        existingReservation.setUtilisateurId(reservationDTO.getUtilisateurs().get(0).getId());  // Exemple pour un seul utilisateur
//
//        // Sauvegarder la réservation mise à jour
//        Reservation updatedReservation = reservationRepo.save(existingReservation);
//
//        // Convertir l'entité mise à jour en DTO
//        return reservationMapper.ToDTO(updatedReservation);
        return null;
    }




    @Override
    public void deleteReservation(Long id) {
        Reservation reservation=reservationRepo.findById(id).orElseThrow(()->new EntityNotFoundException("Reservation with id " + id + " not found"));
        reservationRepo.delete(reservation);
    }
}
