package com.mundia.msreservation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationReq {
    private Long salleId;
    private Long utilisateurId;
    private LocalDate date;
    private LocalTime heure;
    private List<Long> salleIDS;

}

