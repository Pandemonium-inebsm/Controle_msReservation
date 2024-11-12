package com.mundia.msreservation.mappers;

import com.mundia.msreservation.dto.ReservationDTO;
import com.mundia.msreservation.dto.ReservationReq;
import com.mundia.msreservation.entities.Reservation;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class ReservationMapper {

    public ReservationDTO toDto(Reservation reservation) {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(reservation.getId());
        reservationDTO.setDate(reservation.getDate());
        reservationDTO.setHeure(reservation.getHeure());

        return reservationDTO;
    }
    public Reservation fromReservationReq(ReservationReq reservationReq) {
        Reservation reservation = new Reservation();
        BeanUtils.copyProperties(reservationReq, reservation);
        return reservation;
    }

}
