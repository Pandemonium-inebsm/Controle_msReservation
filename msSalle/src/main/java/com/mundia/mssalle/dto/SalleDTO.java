package com.mundia.mssalle.dto;

import com.mundia.mssalle.entities.Salle;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class SalleDTO  {
    private Long id;
    private String nom;
    private int capacite;
    private String equipement;
    private List<ReservationDTO> reservations;
}
