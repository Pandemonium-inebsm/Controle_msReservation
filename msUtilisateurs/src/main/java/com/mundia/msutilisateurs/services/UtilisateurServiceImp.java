package com.mundia.msutilisateurs.services;


import com.mundia.msutilisateurs.dto.UtilisateurDTO;
import com.mundia.msutilisateurs.dto.UtilisateurReq;
import com.mundia.msutilisateurs.entities.Utilisateur;
import com.mundia.msutilisateurs.mappers.UserMappers;
import com.mundia.msutilisateurs.repositories.UtilisateurRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UtilisateurServiceImp implements UtilisateurService {
    final UtilisateurRepo utilisateurRepo;
    final UserMappers userMappers;
    final WebClient webClient;

    @Override
    public Utilisateur getUtilisateurById(Long id) {
        Optional<Utilisateur> optionalUtilisateur = utilisateurRepo.findById(id);
        if(optionalUtilisateur.isPresent()){
            return optionalUtilisateur.get();
        }else {
            throw new EntityNotFoundException("User with id " + id + " not found");
        }
    }

    @Override
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepo.findAll();
    }

    @Override
    public Utilisateur addUtilisateur(UtilisateurReq utilisateurReq) {
        Utilisateur utilisateur = userMappers.fromUtilisateurReq(utilisateurReq);
        utilisateurRepo.save(utilisateur);
        return utilisateur;
    }

    @Override
    public UtilisateurDTO updateUtilisateur(Long id, UtilisateurDTO utilisateurDTO) {
        Utilisateur utilisateur = utilisateurRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));

        utilisateur.setNom(utilisateurDTO.getNom());
        utilisateur.setEmail(utilisateurDTO.getEmail());

        Utilisateur updatedUtilisateur = utilisateurRepo.save(utilisateur);
        return userMappers.toDto(updatedUtilisateur);
    }



    @Override
    public void deleteUtilisateur(Long id) {
        Utilisateur utilisateur=utilisateurRepo.findById(id).orElseThrow(()->new EntityNotFoundException("User with id " + id + " not found"));
        utilisateurRepo.delete(utilisateur);
    }

}