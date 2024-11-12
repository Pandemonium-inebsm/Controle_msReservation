package com.mundia.msutilisateurs.mappers;


import com.mundia.msutilisateurs.dto.UtilisateurDTO;
import com.mundia.msutilisateurs.dto.UtilisateurReq;
import com.mundia.msutilisateurs.entities.Utilisateur;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class UserMappers {
    public UtilisateurDTO toDto(Utilisateur utilisateur) {
        UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
        utilisateurDTO.setId(utilisateur.getId());
        utilisateurDTO.setNom(utilisateur.getNom());
        utilisateurDTO.setEmail(utilisateur.getEmail());
        return utilisateurDTO;
    }
    public Utilisateur fromUtilisateurReq(UtilisateurReq utilisateurReq) {
        Utilisateur utilisateur = new Utilisateur();
        BeanUtils.copyProperties(utilisateurReq, utilisateur);
        return utilisateur;
    }
}

