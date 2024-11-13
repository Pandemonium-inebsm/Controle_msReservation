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
    //------------------------------------------
    @Override
    public List<Reservation> getAllReservation() {
        List<Reservation> reservations = reservationRepo.findAll();
        return reservations;

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
    //-------------------------------------------------
    @Override
    public ReservationDTO getReservationAvecSalleEtUtilisateur(Long id) {
        Reservation reservation = getReservationById(id);

        // Récupération de la salle
        SalleDTO salleDTO = webClient.get()
                .uri("http://MSSALLE/api/salle/" + reservation.getSallesIds())
                .retrieve()
                .bodyToMono(SalleDTO.class)
                .block();

        // Récupération de l'utilisateur
        UtilisateurDTO utilisateurDTO = webClient.get()
                .uri("http://MSUTILISATEURS/api/utilisateur/" + reservation.getUtilisateurId())
                .retrieve()
                .bodyToMono(UtilisateurDTO.class)
                .block();

        // Préparation de l'objet ReservationDTO
        ReservationDTO reservationDTO = new ReservationDTO();
        BeanUtils.copyProperties(reservation, reservationDTO);

        // Affectation des salles et utilisateurs
        List<SalleDTO> salles = Collections.singletonList(salleDTO);
        reservationDTO.setSalles(salles);
        reservationDTO.setUtilisateurs(Collections.singletonList(utilisateurDTO));

        return reservationDTO;
    }
    //--------------------------------------------
    @Override
    public List<Reservation> getReservationbySalleId(Long id) {

        return reservationRepo.findAllBySallesIds(id);
    }
    //--------------------------------------------
    @Override
    public List<Reservation> getReservationbyUserId(Long id) {

        return reservationRepo.findAllByUtilisateurId(id);
    }


}
