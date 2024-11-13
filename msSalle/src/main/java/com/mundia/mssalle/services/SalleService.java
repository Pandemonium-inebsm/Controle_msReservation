package com.mundia.mssalle.services;

import com.mundia.mssalle.dto.ReservationDTO;
import com.mundia.mssalle.dto.SalleDTO;
import com.mundia.mssalle.dto.SalleReq;
import com.mundia.mssalle.entities.Salle;

import java.util.List;

public interface SalleService {
    Salle getSalleById(Long id);
    List<Salle> getAllSalle();

    Salle addSalle(SalleReq salleReq);
    SalleDTO updateSalle(Long id, SalleDTO salleDTO);
    void deleteSalle(Long id);
    //-------------Salles avec reservations
    SalleDTO getReservationDTOById(Long id);

}
