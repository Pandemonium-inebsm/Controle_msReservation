package com.mundia.msutilisateurs.repositories;

import com.mundia.msutilisateurs.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepo extends JpaRepository<Utilisateur, Long> {

}
