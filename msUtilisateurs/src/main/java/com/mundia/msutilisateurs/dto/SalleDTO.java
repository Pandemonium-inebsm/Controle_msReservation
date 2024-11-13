package com.mundia.msutilisateurs.dto;

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
}
