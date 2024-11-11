package com.mundia.mssalle.dto;

import com.mundia.mssalle.entities.Salle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalleDTO  {
    private Long id;
    private String nom;
    private int capacite;
    private String equipement;
}
