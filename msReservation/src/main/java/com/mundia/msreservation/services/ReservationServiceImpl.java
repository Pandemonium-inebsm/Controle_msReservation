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

            ReservationDTO reservationDTO = new ReservationDTO();
            BeanUtils.copyProperties(reservation, reservationDTO);
            reservationDTO.setSalles(Collections.singletonList(salleDTO));
            reservationDTO.setUtilisateurs(Collections.singletonList(utilisateurDTO));
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
        List<Reservation> reservations = reservationRepo.findAllBySallesIds(salleId);
        List<ReservationDTO> reservationDTOs = new ArrayList<>();

        for (Reservation reservation : reservations) {
            UtilisateurDTO utilisateurDTO = webClient.get()
                    .uri("http://MSUTILISATEURS/api/utilisateur/" + reservation.getUtilisateurId())
                    .retrieve()
                    .bodyToMono(UtilisateurDTO.class)
                    .block();

            ReservationDTO reservationDTO = new ReservationDTO();
            BeanUtils.copyProperties(reservation, reservationDTO);
            reservationDTO.setUtilisateurs(Collections.singletonList(utilisateurDTO));

            reservationDTOs.add(reservationDTO);
        }

        return reservationDTOs;
    }


    //--------------------------------------------
    @Override
    public List<ReservationDTO> getReservationbyUserId(Long userId) {
        List<Reservation> reservations = reservationRepo.findAllByUtilisateurId(userId);
        List<ReservationDTO> reservationDTOs = new ArrayList<>();

        for (Reservation reservation : reservations) {
            SalleDTO salleDTO = webClient.get()
                    .uri("http://MSSALLE/api/salle/" + reservation.getSallesIds())
                    .retrieve()
                    .bodyToMono(SalleDTO.class)
                    .block();

            ReservationDTO reservationDTO = new ReservationDTO();
            BeanUtils.copyProperties(reservation, reservationDTO);
            reservationDTO.setSalles(Collections.singletonList(salleDTO));
            reservationDTOs.add(reservationDTO);
        }

        return reservationDTOs;
    }



}
