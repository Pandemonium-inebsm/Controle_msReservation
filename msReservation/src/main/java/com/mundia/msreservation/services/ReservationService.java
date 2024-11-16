package com.mundia.msreservation.services;

import com.mundia.msreservation.dto.ReservationDTO;
import com.mundia.msreservation.dto.ReservationReq;
import com.mundia.msreservation.entities.Reservation;

import java.util.List;

public interface ReservationService {
    Reservation getReservationById(Long id);
    List<ReservationDTO> getAllReservation();
    Reservation addReservation(ReservationReq reservationReq);
    Reservation updateReservation(Long id, ReservationReq reservationReq);
    void deleteReservation(Long id);
    List<ReservationDTO> getReservationbySalleId(Long id);

    List<ReservationDTO> getReservationbyUserId(Long id);
}
