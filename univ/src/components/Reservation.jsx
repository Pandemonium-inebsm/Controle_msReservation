import React, { useState, useEffect } from "react";
import { Table, Button, Modal, Form } from "react-bootstrap";
import axios_client from "../config/host-app";
import { FaEdit, FaTrashAlt, FaPlus } from "react-icons/fa";
import Layout from "./Layout";
import Swal from "sweetalert2"; // SweetAlert2 pour confirmation

const Reservation = () => {
  const [reservations, setReservations] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [salles, setSalles] = useState([]);
  const [utilisateurs, setUtilisateurs] = useState([]);
  const [modalData, setModalData] = useState({
    date: "",
    heure: "",
    sallesIds: "",
    utilisateurId: "",
  });
  const [editMode, setEditMode] = useState(false);
  const token = localStorage.getItem("token");
  const config = {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };
  const fetchReservations = async () => {
    try {
      const response = await axios_client.get("/api/reservation/reservations",config);
      setReservations(response.data);
    } catch (err) {
      console.error("Error fetching reservations:", err);
    }

    try {
      const sallesResponse = await axios_client.get("/api/salle/salles",config);
      const utilisateursResponse = await axios_client.get("/api/utilisateur/utilisateurs",config);
      setSalles(sallesResponse.data);
      setUtilisateurs(utilisateursResponse.data);
    } catch (err) {
      console.error("Error fetching salles or utilisateurs:", err);
    }
  };

  const saveReservation = async () => {
    try {
      if (editMode) {
        await axios_client.put(`/api/reservation/${modalData.id}`, modalData,config);
      } else {
        await axios_client.post("/api/reservation/add", modalData,config);
      }
      setShowModal(false);
      fetchReservations();
    } catch (err) {
      console.error("Error saving reservation:", err);
    }
  };

  const deleteReservation = async (id) => {
    Swal.fire({
      title: "Êtes-vous sûr ?",
      text: "Cette action est irréversible !",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#d33",
      cancelButtonColor: "#3085d6",
      confirmButtonText: "Oui, supprimer",
      cancelButtonText: "Annuler",
    }).then(async (result) => {
      if (result.isConfirmed) {
        try {
          await axios_client.delete(`/api/reservation/${id}`,config);
          fetchReservations();
        } catch (err) {
          console.error("Error deleting reservation:", err);
        }
      }
    });
  };

  const handleShowModal = (reservation = null) => {
    setEditMode(!!reservation);

    if (reservation) {
      setModalData({
        date: reservation.date,
        heure: reservation.heure,
        sallesIds: reservation.salles && reservation.salles.length > 0 ? reservation.salles[0].id : "",
        utilisateurId: reservation.utilisateurs && reservation.utilisateurs.length > 0 ? reservation.utilisateurs[0].id : "",
        id: reservation.id,
      });
    } else {
      setModalData({
        date: "",
        heure: "",
        sallesIds: "",
        utilisateurId: "",
        id: "",
      });
    }

    setShowModal(true);
  };

  const handleCloseModal = () => setShowModal(false);

  useEffect(() => {
    fetchReservations();
  }, []);

  return (
    <Layout>
      <div className="container mt-4">
        <div className="d-flex justify-content-between align-items-center mb-3">
          <h2>Liste des Réservations</h2>
          <Button variant="primary" onClick={() => handleShowModal()}>
            <FaPlus /> Ajouter Réservation
          </Button>
        </div>

        <Table striped bordered hover responsive className="text-center">
        <thead className="table-primary">
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
                    className="me-2"
                  >
                    <FaEdit /> Modifier
                  </Button>
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
    </Layout>
  );
};

export default Reservation;
