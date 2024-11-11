package com.mundia.mssalle.mappers;

import com.mundia.mssalle.dto.SalleDTO;
import com.mundia.mssalle.dto.SalleReq;
import com.mundia.mssalle.entities.Salle;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalleMapper {
    public SalleDTO toDto(Salle salle) {
        SalleDTO salleDTO = new SalleDTO();
        salleDTO.setId(salle.getId());
        salleDTO.setNom(salle.getNom());
        salleDTO.setCapacite(salle.getCapacite());
        salleDTO.setEquipement(salle.getEquipement());

        return salleDTO;
    }
    public Salle fromSalleReq(SalleReq salleReq) {
        Salle salle = new Salle();
        BeanUtils.copyProperties(salleReq, salle);
        return salle;
    }
}

