package com.mundia.msutilisateurs.services;



import com.mundia.msutilisateurs.dto.UtilisateurDTO;
import com.mundia.msutilisateurs.dto.UtilisateurReq;
import com.mundia.msutilisateurs.entities.Utilisateur;

import java.util.List;

public interface UtilisateurService {
    Utilisateur getUtilisateurById(Long id);
    List<Utilisateur> getAllUtilisateurs();

    Utilisateur addUtilisateur(UtilisateurReq utilisateurReq);
    UtilisateurDTO updateUtilisateur(Long id, UtilisateurDTO utilisateurDTO);
    void deleteUtilisateur(Long id);

    UtilisateurDTO getReservationDTOById(Long id);

}
