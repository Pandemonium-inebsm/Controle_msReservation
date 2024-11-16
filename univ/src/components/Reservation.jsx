import React, { useState, useEffect } from "react";
import { Table, Button, Modal, Form } from "react-bootstrap";
import axios_client from "../config/host-app";
import { FaEdit, FaTrashAlt, FaPlus } from "react-icons/fa"; 

const Reservation = () => {
  const [reservations, setReservations] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [salles, setSalles] = useState([]);  // Liste des salles
  const [utilisateurs, setUtilisateurs] = useState([]);  // Liste des utilisateurs

  const [modalData, setModalData] = useState({
    date: "",
    heure: "",
    sallesIds: "",
    utilisateurId: "",
  });
  const [editMode, setEditMode] = useState(false);

  // Fonction pour récupérer les réservations, salles et utilisateurs
  const fetchReservations = async () => {
    try {
      const response = await axios_client.get("/api/reservation/reservations");
      setReservations(response.data);
    } catch (err) {
      console.error("Error fetching reservations:", err);
    }

    // Récupérer les listes de salles et utilisateurs
    try {
      const sallesResponse = await axios_client.get("/api/salle/salles");
      const utilisateursResponse = await axios_client.get("/api/utilisateur/utilisateurs");
      setSalles(sallesResponse.data);
      setUtilisateurs(utilisateursResponse.data);
    } catch (err) {
      console.error("Error fetching salles or utilisateurs:", err);
    }
  };

  // Sauvegarder une réservation (ajouter ou modifier)
  const saveReservation = async () => {
    try {
      if (editMode) {
        await axios_client.put(`/api/reservation/${modalData.id}`, modalData);
      } else {
        await axios_client.post("/api/reservation/add", modalData);
      }
      setShowModal(false);
      fetchReservations();
    } catch (err) {
      console.error("Error saving reservation:", err);
    }
  };

  // Supprimer une réservation
  const deleteReservation = async (id) => {
    try {
      await axios_client.delete(`/api/reservation/${id}`);
      fetchReservations();
    } catch (err) {
      console.error("Error deleting reservation:", err);
    }
  };

  // Ouvrir le modal pour Ajouter ou Modifier
const handleShowModal = (reservation = null) => {
    setEditMode(!!reservation);
  
    if (reservation) {
      // Récupérer l'ID de la première salle et de l'utilisateur (selon la structure de vos données)
      setModalData({
        date: reservation.date,
        heure: reservation.heure,
        sallesIds: reservation.salles && reservation.salles.length > 0 ? reservation.salles[0].id : "",
        utilisateurId: reservation.utilisateurs && reservation.utilisateurs.length > 0 ? reservation.utilisateurs[0].id : "",
        id: reservation.id  // Ajouter l'ID de la réservation pour l'édition
      });
    } else {
      setModalData({
        date: "",
        heure: "",
        sallesIds: "",
        utilisateurId: "",
        id: ""  // Aucune ID pour une nouvelle réservation
      });
    }
  
    setShowModal(true);
  };
  

  const handleCloseModal = () => setShowModal(false);

  useEffect(() => {
    fetchReservations();
  }, []);

  return (
    <div className="container mt-4">
      <div className="d-flex justify-content-between align-items-center mb-3">
        <h2>Liste des Réservations</h2>
        <Button variant="primary" onClick={() => handleShowModal()}>
        <FaPlus /> Ajouter Réservation
        </Button>
      </div>

      <Table striped bordered hover>
        <thead>
          <tr>
            <th>Date</th>
            <th>Heure</th>
            <th>Salle</th>
            <th>Utilisateur</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {reservations.map((reservation) => (
            <tr key={reservation.id}>
              <td>{reservation.date}</td>
              <td>{reservation.heure}</td>
              <td>
                {reservation.salles && reservation.salles.length > 0
                  ? reservation.salles.map((salle) => salle.nom).join(", ")
                  : "Salle inconnue"}
              </td>
              <td>
                {reservation.utilisateurs && reservation.utilisateurs.length > 0
                  ? reservation.utilisateurs.map((utilisateur) => utilisateur.nom).join(", ")
                  : "Utilisateur inconnu"}
              </td>
              <td>
                <Button
                  variant="success"
                  size="sm"
                  onClick={() => handleShowModal(reservation)}
                >
                   <FaEdit /> Modifier
                </Button>{" "}
                <Button
                  variant="danger"
                  size="sm"
                  onClick={() => deleteReservation(reservation.id)}
                >
                <FaTrashAlt /> Supprimer

                </Button>
              </td>
            </tr>
          ))}
        </tbody>
      </Table>

      {/* Modal pour Ajouter/Modifier */}
      <Modal show={showModal} onHide={handleCloseModal}>
  <Modal.Header closeButton>
    <Modal.Title>
      {editMode ? "Modifier la Réservation" : "Ajouter une Réservation"}
    </Modal.Title>
  </Modal.Header>
  <Modal.Body>
    <Form>
      <Form.Group className="mb-3">
        <Form.Label>Date</Form.Label>
        <Form.Control
          type="date"
          value={modalData.date}
          onChange={(e) => setModalData({ ...modalData, date: e.target.value })}
        />
      </Form.Group>
      <Form.Group className="mb-3">
        <Form.Label>Heure</Form.Label>
        <Form.Control
          type="time"
          value={modalData.heure}
          onChange={(e) => setModalData({ ...modalData, heure: e.target.value })}
        />
      </Form.Group>

      {/* Sélectionner la salle par nom */}
      <Form.Group className="mb-3">
        <Form.Label>Salle</Form.Label>
        <Form.Control
          as="select"
          value={modalData.sallesIds}
          onChange={(e) => setModalData({ ...modalData, sallesIds: e.target.value })}
        >
          <option value="">Choisissez une salle</option>
          {salles.map((salle) => (
            <option key={salle.id} value={salle.id}>
              {salle.nom}
            </option>
          ))}
        </Form.Control>
      </Form.Group>

      {/* Sélectionner l'utilisateur par nom */}
      <Form.Group className="mb-3">
        <Form.Label>Utilisateur</Form.Label>
        <Form.Control
          as="select"
          value={modalData.utilisateurId}
          onChange={(e) => setModalData({ ...modalData, utilisateurId: e.target.value })}
        >
          <option value="">Choisissez un utilisateur</option>
          {utilisateurs.map((utilisateur) => (
            <option key={utilisateur.id} value={utilisateur.id}>
              {utilisateur.nom}
            </option>
          ))}
        </Form.Control>
      </Form.Group>
    </Form>
  </Modal.Body>
  <Modal.Footer>
    <Button variant="secondary" onClick={handleCloseModal}>
      Annuler
    </Button>
    <Button variant="primary" onClick={saveReservation}>
      {editMode ? "Modifier" : "Ajouter"}
    </Button>
  </Modal.Footer>
</Modal>

    </div>
  );
};

export default Reservation;
