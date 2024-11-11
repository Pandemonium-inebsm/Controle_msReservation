package com.mundia.mssalle.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalleReq {
    private String nom;
    private int capacite;
    private String equipement;
}
