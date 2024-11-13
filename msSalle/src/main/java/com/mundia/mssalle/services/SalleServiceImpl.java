package com.mundia.mssalle.services;

import com.mundia.mssalle.dto.ReservationDTO;
import com.mundia.mssalle.dto.SalleDTO;
import com.mundia.mssalle.dto.SalleReq;
import com.mundia.mssalle.dto.UtilisateurDTO;
import com.mundia.mssalle.entities.Salle;
import com.mundia.mssalle.mappers.SalleMapper;
import com.mundia.mssalle.repositories.SalleRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SalleServiceImpl implements SalleService{
    final SalleRepo salleRepo;
    final SalleMapper salleMapper;
    final WebClient webClient;

    @Override
    public Salle getSalleById(Long id) {
        Optional<Salle> optionalSalle = salleRepo.findById(id);
        if(optionalSalle.isPresent()){
            return optionalSalle.get();
        }else {
            throw new EntityNotFoundException("Salle with id " + id + " not found");
        }
    }

    @Override
    public List<Salle> getAllSalle() {
        return salleRepo.findAll();
    }

    @Override
    public Salle addSalle(SalleReq salleReq) {
        Salle salle = salleMapper.fromSalleReq(salleReq);
        salleRepo.save(salle);
        return salle;
    }

    @Override
    public SalleDTO updateSalle(Long id, SalleDTO salleDTO) {
        Salle salle = salleRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Salle not found with id " + id));

        salle.setNom(salleDTO.getNom());
        salle.setCapacite(salleDTO.getCapacite());
        salle.setEquipement(salleDTO.getEquipement());

        Salle updatedSalle = salleRepo.save(salle);
        return salleMapper.toDto(updatedSalle);
    }
    //--------------------------------------------------
    @Override
    public void deleteSalle(Long id) {
        Salle salle=salleRepo.findById(id).orElseThrow(()->new EntityNotFoundException("Salle with id " + id + " not found"));
        salleRepo.delete(salle);
    }
    //-----------------------------------------------------

    @Override
    public SalleDTO getReservationDTOById(Long id) {
        Salle salle = getSalleById(id);
        ReservationDTO[] reservationDTOS = webClient.get()
                .uri("http://MSRESERVATION/api/reservation/reservationbyidsalle/"+id)
                .retrieve()
                .bodyToMono(ReservationDTO[].class)
                .share()
                .block();

        //---------------------------------------------
        SalleDTO salleDTO = new SalleDTO();
        BeanUtils.copyProperties(salle, salleDTO);
        salleDTO.setReservations(Arrays.asList(reservationDTOS));

        return salleDTO;

    }

}