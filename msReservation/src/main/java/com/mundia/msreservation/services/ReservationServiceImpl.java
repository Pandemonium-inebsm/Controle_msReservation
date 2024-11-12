package com.mundia.msreservation.services;


import com.mundia.msreservation.dto.ReservationDTO;
import com.mundia.msreservation.dto.ReservationReq;
import com.mundia.msreservation.entities.Reservation;
import com.mundia.msreservation.mappers.ReservationMapper;
import com.mundia.msreservation.repositories.ReservationRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

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
        return reservationRepo.findAll();
    }

    @Override
    public Reservation addReservation(ReservationReq reservationReq) {
        Reservation reservation = reservationMapper.fromReservationReq(reservationReq);
        reservationRepo.save(reservation);
        return reservation;
    }

    @Override
    public ReservationDTO updateReservation(Long id, ReservationDTO reservationDTO) {
        return null;
    }

//    @Override
//    public ReservationDTO updateReservation(Long id, ReservationDTO reservationDTO) {
//        Reservation reservation = reservationRepo.findById(id)
//                .orElseThrow(() -> new RuntimeException("Reservation not found with id " + id));
//
//        reservation.setDate(reservationDTO.getDate());
//        reservation.setHeure(reservationDTO.getHeure());
//        //reservation.set(salleDTO.getEquipement());
//
//        //reservation updatedReservation = reservationRepo.save(reservation);
//        return reservationMapper.toDto(updatedReservation);
//    }



    @Override
    public void deleteReservation(Long id) {
        Reservation reservation=reservationRepo.findById(id).orElseThrow(()->new EntityNotFoundException("Salle with id " + id + " not found"));
        reservationRepo.delete(reservation);
    }
}
