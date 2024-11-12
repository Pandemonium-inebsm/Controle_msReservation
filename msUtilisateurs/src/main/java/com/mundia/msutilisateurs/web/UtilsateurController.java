package com.mundia.msutilisateurs.web;


import com.mundia.msutilisateurs.dto.UtilisateurDTO;
import com.mundia.msutilisateurs.dto.UtilisateurReq;
import com.mundia.msutilisateurs.entities.Utilisateur;
import com.mundia.msutilisateurs.services.UtilisateurServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utilisateur")
@RequiredArgsConstructor
public class UtilsateurController {
    final UtilisateurServiceImp utilisateurService;
    //----------add--------------------------------------
    @PostMapping("/add")
    public Utilisateur addUtilisateur(@RequestBody UtilisateurReq utilisateur) {
        return utilisateurService.addUtilisateur(utilisateur);
    }
    //----------------------------
    @GetMapping("/{id}")
    public Utilisateur getUtilisateurById(@PathVariable Long id) {
        return utilisateurService.getUtilisateurById(id);
    }
    //-----------list----------------------------------
    @GetMapping("/utilisateurs")
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurService.getAllUtilisateurs();
    }
    //---------------------------
    @PutMapping("/{id}")
    public ResponseEntity<UtilisateurDTO> updateUtilisateur(@PathVariable Long id, @RequestBody UtilisateurDTO  utilisateurDTO) {
        UtilisateurDTO updatedUtilisateur = utilisateurService.updateUtilisateur(id, utilisateurDTO);
        return ResponseEntity.ok(updatedUtilisateur);
    }

    // Delete an salle by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable Long id) {
        utilisateurService.deleteUtilisateur(id);
        return ResponseEntity.noContent().build();
    }
}
