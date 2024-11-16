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

import java.util.*;

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
    //------------------------------------------
    @Override
    public List<ReservationDTO> getAllReservation() {
        List<Reservation> reservations = reservationRepo.findAll();
        List<ReservationDTO> reservationDTOs = new ArrayList<>();

        for (Reservation reservation : reservations) {
            // Récupérer la salle et l'utilisateur pour chaque réservation
            SalleDTO salleDTO = webClient.get()
                    .uri("http://MSSALLE/api/salle/" + reservation.getSallesIds())
                    .retrieve()
                    .bodyToMono(SalleDTO.class)
                    .block();

            UtilisateurDTO utilisateurDTO = webClient.get()
                    .uri("http://MSUTILISATEURS/api/utilisateur/" + reservation.getUtilisateurId())
                    .retrieve()
                    .bodyToMono(UtilisateurDTO.class)
                    .block();

            // Convertir la réservation en DTO
            ReservationDTO reservationDTO = new ReservationDTO();
            BeanUtils.copyProperties(reservation, reservationDTO);

            // Ajouter la salle et l'utilisateur dans le DTO
            reservationDTO.setSalles(Collections.singletonList(salleDTO));
            reservationDTO.setUtilisateurs(Collections.singletonList(utilisateurDTO));

            // Ajouter à la liste finale
            reservationDTOs.add(reservationDTO);
        }

        return reservationDTOs;
    }

    //--------------------------------------------------
    public Reservation addReservation(ReservationReq reservationReq) {
        Reservation reservation = Reservation.builder()
                .date(reservationReq.getDate())
                .heure(reservationReq.getHeure())
                .sallesIds(reservationReq.getSallesIds())
                .utilisateurId(reservationReq.getUtilisateurId())
                .build();

        reservationRepo.save(reservation);

        return reservation;
    }

    //---------------------------------------------------------------
    @Override
    public Reservation updateReservation(Long reservationId, ReservationReq reservationReq) {
        Reservation existingReservation = reservationRepo.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id " + reservationId));

        existingReservation.setDate(reservationReq.getDate());
        existingReservation.setHeure(reservationReq.getHeure());
        existingReservation.setSallesIds(reservationReq.getSallesIds());
        existingReservation.setUtilisateurId(reservationReq.getUtilisateurId());

        reservationRepo.save(existingReservation);

        return existingReservation;
    }
    //-----------------------------------------------
    @Override
    public void deleteReservation(Long id) {
        Reservation reservation=reservationRepo.findById(id).orElseThrow(()->new EntityNotFoundException("Reservation with id " + id + " not found"));
        reservationRepo.delete(reservation);
    }

    //--------------------------------------------
    @Override
    public List<ReservationDTO> getReservationbySalleId(Long salleId) {
        // Récupération des réservations ayant l'ID de la salle spécifié
        List<Reservation> reservations = reservationRepo.findAllBySallesIds(salleId);

        // Transformation en DTO avec récupération des informations utilisateur
        List<ReservationDTO> reservationDTOs = new ArrayList<>();

        for (Reservation reservation : reservations) {
            // Récupération de l'utilisateur pour chaque réservation
            UtilisateurDTO utilisateurDTO = webClient.get()
                    .uri("http://MSUTILISATEURS/api/utilisateur/" + reservation.getUtilisateurId())
                    .retrieve()
                    .bodyToMono(UtilisateurDTO.class)
                    .block();

            // Préparation de l'objet ReservationDTO
            ReservationDTO reservationDTO = new ReservationDTO();
            BeanUtils.copyProperties(reservation, reservationDTO);

            // Affectation de l'utilisateur dans le DTO
            reservationDTO.setUtilisateurs(Collections.singletonList(utilisateurDTO));

            // Ajout du DTO à la liste finale
            reservationDTOs.add(reservationDTO);
        }

        return reservationDTOs;
    }


    //--------------------------------------------
    @Override
    public List<ReservationDTO> getReservationbyUserId(Long userId) {
        // Récupération des réservations ayant l'ID de l'utilisateur spécifié
        List<Reservation> reservations = reservationRepo.findAllByUtilisateurId(userId);

        // Transformation en DTO avec récupération des informations de salle
        List<ReservationDTO> reservationDTOs = new ArrayList<>();

        for (Reservation reservation : reservations) {
            // Récupération de la salle pour chaque réservation
            SalleDTO salleDTO = webClient.get()
                    .uri("http://MSSALLE/api/salle/" + reservation.getSallesIds())
                    .retrieve()
                    .bodyToMono(SalleDTO.class)
                    .block();

            // Préparation de l'objet ReservationDTO
            ReservationDTO reservationDTO = new ReservationDTO();
            BeanUtils.copyProperties(reservation, reservationDTO);

            // Affectation de la salle dans le DTO
            reservationDTO.setSalles(Collections.singletonList(salleDTO));

            // Ajout du DTO à la liste finale
            reservationDTOs.add(reservationDTO);
        }

        return reservationDTOs;
    }



}
