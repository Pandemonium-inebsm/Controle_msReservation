package com.mundia.mssalle.web;

import com.mundia.mssalle.dto.SalleDTO;
import com.mundia.mssalle.dto.SalleReq;
import com.mundia.mssalle.entities.Salle;
import com.mundia.mssalle.services.SalleService;
import com.mundia.mssalle.services.SalleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salle")
@RequiredArgsConstructor
public class SalleController {
    final SalleServiceImpl salleService;
    //----------add--------------------------------------
    @PostMapping("/add")
    public Salle addSalle(@RequestBody SalleReq salle) {
        return salleService.addSalle(salle);
    }
    //----------------------------
    @GetMapping("/{id}")
    public Salle getSalleById(@PathVariable Long id) {
        return salleService.getSalleById(id);
    }
    //-----------list----------------------------------
    @GetMapping("/salles")
    public List<Salle> getSalle() {
        return salleService.getAllSalle();
    }
    //---------------------------
    @PutMapping("/{id}")
    public ResponseEntity<SalleDTO> updateSalle(@PathVariable Long id, @RequestBody SalleDTO salleDTO) {
        SalleDTO updatedSalle = salleService.updateSalle(id, salleDTO);
        return ResponseEntity.ok(updatedSalle);
    }
    // Delete an salle by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalle(@PathVariable Long id) {
        salleService.deleteSalle(id);
        return ResponseEntity.noContent().build();
    }
    //----------------------------------------
    @GetMapping("/SalleReservation/{id}")
    public SalleDTO getSalleWithReserv(@PathVariable Long id) {
        return salleService.getReservationDTOById(id);
    }
}
