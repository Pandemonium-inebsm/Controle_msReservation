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
public class ReservationDTO {
    private Long id;
    private LocalDate date;
    private LocalTime heure;
    private List<SalleDTO> salles;          // Liste de salles
    private List<UtilisateurDTO> utilisateurs;
}

