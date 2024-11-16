package com.mundia.msreservation.web;


import com.mundia.msreservation.dto.ReservationDTO;
import com.mundia.msreservation.dto.ReservationReq;
import com.mundia.msreservation.entities.Reservation;
import com.mundia.msreservation.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservation")
@RequiredArgsConstructor
public class ReservationController {
    final ReservationService reservationService;
    //----------add--------------------------------------
    @PostMapping("/add")
    public Reservation addReservation(@RequestBody ReservationReq reservation) {
        return reservationService.addReservation(reservation);
    }
    //----------------------------
    @GetMapping("/{id}")
    public Reservation getReservationById(@PathVariable Long id) {
        return reservationService.getReservationById(id);
    }
    //-----------list----------------------------------
    @GetMapping("/reservations")
    public List<ReservationDTO> getReservations() {
        return reservationService.getAllReservation();
    }
    //---------------------------
    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id, @RequestBody ReservationReq reservationReq) {
        Reservation updatedReservation = reservationService.updateReservation(id, reservationReq);
        return ResponseEntity.ok(updatedReservation);
    }
    // Delete an salle by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

    //-----------reservation par isDalle------------
    @GetMapping("/reservationbyidsalle/{id}")
    public List<ReservationDTO> getReservationbySalleId(@PathVariable Long id) {
        return reservationService.getReservationbySalleId(id);
    }
    //-----------reservation par isDalle------------
    @GetMapping("/reservationbyiduser/{id}")
    public List<ReservationDTO> getReservationbyUserId(@PathVariable Long id) {
        return reservationService.getReservationbyUserId(id);
    }

}
